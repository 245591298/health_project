package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.MemberDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Member;
import com.itheima.pojo.PycBody;
import com.itheima.pojo.PycIllness;
import com.itheima.pojo.PycPsychology;
import com.itheima.service.PycMemberService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pyc
 */
@Service(interfaceClass = PycMemberService.class)
@Transactional
public class PycMemberServiceImpl implements PycMemberService {

    @Autowired
    private MemberDao memberDao;

    /**
     * 查询所有的会员信息
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult pycFindPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Member> members = memberDao.pycFindPage(queryPageBean.getQueryString());
        return new PageResult(members.getTotal(), members.getResult());
    }

    /**
     *  查询所有的illness
     * @return
     */
    @Override
    public List<PycIllness> pycFindAllillness() {
        return memberDao.pycFindAllillness();
    }

    /**
     *  新增会员
     * @param map
     * @param illnessIds
     */
    @Override
    public void pycAddMember(Map map, Integer[] illnessIds) throws InvocationTargetException, IllegalAccessException {
        //member信息
        Map<String,Object> formData = (Map<String, Object>) map.get("formData");
        //body信息
        Map<String,Object> bodyData= (Map<String, Object>) map.get("bodyData");
        //心理信息
        Map<String,Object> psychologyData= (Map<String, Object>) map.get("psychologyData");

        //添加会员信息
        Member member=new Member();
        BeanUtils.populate(member,formData);
        member.setRegTime(new Date());
        memberDao.add(member);

        //添加会员与illness中间表信息
        Integer id = member.getId();//member的自增主键
        this.setMemberAndillness(id,illnessIds);

        //添加 member  body信息
        PycBody body=new PycBody();
        BeanUtils.populate(body,bodyData);
        body.setAssessment_data(new Date());
        body.setMember_id(id);
        memberDao.pycAddBodyInfo(body);

        //添加 member  psychology信息
        PycPsychology psychology=new PycPsychology() ;
        BeanUtils.populate(psychology,psychologyData);
        psychology.setAssessment_date(new Date());
        psychology.setMember_id(id);
        memberDao.pycAddPsychologyInfo(psychology);
    }


    /**
     *  建立member与illness中间表关系
     * @param memberId
     * @param illnessIds
     */
    public void setMemberAndillness(Integer memberId,Integer[] illnessIds){
        if(illnessIds!=null&&illnessIds.length>0){
            for (Integer illnessId : illnessIds) {
                memberDao.pycSetMemberAndillness(memberId,illnessId);
            }
        }
    }

    /**
     * 根据id查询member信息
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> pycFindMemberInfoById(Integer id) {
        //会员信息
       Member member= memberDao.pycFindMemberById(id);
        //illness info
        List<PycIllness> illnesses = memberDao.pycFindAllillness();
        //会员对应的illness info的ids
       List<PycIllness> illnessIds= memberDao.pycFindillnessIdsByMemberId(id);
       //根据memberId查询出body info
        PycBody body=memberDao.pycFindBodyByMemberId(id);
        //根据memberId查询出psychology info
        PycPsychology psychology=memberDao.pycFindPsychologyByMember(id);
        //封装数据的map
        Map<String,Object> map=new HashMap<>();
        map.put("formData",member);
        map.put("tableData",illnesses);
        map.put("illnessIds",illnessIds);
        map.put("bodyData",body);
        map.put("psychologyData",psychology);
        return map;
    }

    /**
     * 编辑member Info
     * @param map
     * @param illnessIds
     */
    @Override
    public void pycEditMemberInfo(Map map, Integer[] illnessIds) throws InvocationTargetException, IllegalAccessException {
        //member info
        Map<String,Object> formData = (Map<String, Object>) map.get("formData");
        // body info
        Map<String,Object> bodyData = (Map<String, Object>) map.get("bodyData");
        //psychology info
        Map<String,Object> psychologyData = (Map<String, Object>) map.get("psychologyData");

        //更新member信息
        Member member=new Member();
        //注册一个转换器   String 转为 Date
        ConvertUtils.register(new Converter() {
            @Override
            public Object convert(Class aClass, Object obj) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = df.parse(aClass.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return date;
            }
        }, Date.class);
        BeanUtils.populate(member,formData);
        memberDao.pycUpdateMember(member);
        Integer id = member.getId();

        //删除member 与 illness 中间表信息
        this.deleteAssociation(id);

        //添加member 与 illness 中间表信息
        this.setMemberAndillness(id,illnessIds);

        //更新body信息
        PycBody body=new PycBody();
        BeanUtils.populate(body,bodyData);
        body.setMember_id(id);
        body.setAssessment_data(new Date());
        memberDao.pycUpdateBody(body);

        //更新psychology信息
        PycPsychology psychology=new PycPsychology();
        BeanUtils.populate(psychology,psychologyData);
        psychology.setMember_id(id);
        psychology.setAssessment_date(new Date());
        memberDao.pycUpdatePsychology(psychology);
    }


    /**
     *  删除 member与 illness的中间关系
     * @param memberId
     */
    public void deleteAssociation(Integer memberId){

          memberDao.pycDeleteAssociation(memberId);
    }


}

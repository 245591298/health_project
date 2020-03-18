package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Member;
import com.itheima.pojo.XgwMemberDifference;
import com.itheima.pojo.PycBody;
import com.itheima.pojo.PycIllness;
import com.itheima.pojo.PycPsychology;
import com.itheima.pojo.*;

import java.util.List;

/**
 * @ClassName MemberDao
 * @Date 2020/3/2 21:02
 * @User 王骁
 */
public interface MemberDao {
    //通过手机号查询是否存在这个会员
    Member findByTelephone(String telephone);
    //添加会员
    void add(Member member);
    //根据日期获取当日新增会员数量
    Integer getMembersByDate();

    Integer getMembersAfterDate(String thisWeekMonday);

    Integer getTotalMembers();

    void zs_deleteMemberById(Integer id);

    //xgw_健康干预分页查询
    Page<XgwMemberDifference> selectPageByCondition(String queryString);

    Page<Member> pycFindPage(String queryString);

    List<PycIllness> pycFindAllillness();

    void pycSetMemberAndillness(Integer memberId,Integer illnessId);

    void pycAddBodyInfo(PycBody body);

    void pycAddPsychologyInfo(PycPsychology psychology);

    Member pycFindMemberById(Integer id);

    List<PycIllness> pycFindillnessIdsByMemberId(Integer id);

    PycBody pycFindBodyByMemberId(Integer id);

    PycPsychology pycFindPsychologyByMember(Integer id);

    void pycDeleteAssociation(Integer memberId);

    void pycUpdateMember(Member member);

    void pycUpdateBody(PycBody body);

    void pycUpdatePsychology(PycPsychology psychology);

    Page<ZS_Psychology> zs_findByCondition(String queryString);

    Page<ZS_BodyList> zs_findBodyByCondition(String queryString);
}

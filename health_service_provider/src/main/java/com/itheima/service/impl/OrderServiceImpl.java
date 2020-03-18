package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderSettingDao;
import com.itheima.dao.SetMealDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.PycOrderManager;
import com.itheima.pojo.OrderSetting;
import com.itheima.pojo.Setmeal;
import com.itheima.service.OrderService;
import com.itheima.utils.DateUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @ClassName OrderServiceImpl
 * @Date 2020/3/2 19:23
 * @User 王骁
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private SetMealDao setMealDao;

    @Override
    public Result order(Map map,String addressId) throws Exception {
        //1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
        String orderDate = (String) map.get("orderDate");
        Date date = DateUtils.parseString2Date(orderDate);
            //调用orderSettingDao查询当前日期是否有设置预约情况
        OrderSetting orderSetting = orderSettingDao.findByDate(date);
        if (orderSetting == null){
            return new Result(false,MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }

        //2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
        int number = orderSetting.getNumber();              //当日预约总数
        int reservations = orderSetting.getReservations();  //当日已预约数
        if(number <= reservations){
            return new Result(false,MessageConstant.ORDER_FULL);
        }

        //3、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约
            //检查当前用户是否为会员，根据手机号判断
        String telephone = (String) map.get("telephone");
        Member member = memberDao.findByTelephone(telephone);
            //防止重复预约
        if (member != null){
            //如果是会员则通过 会员id 日期 套餐id 查询预约表t_order 确定 当天此套餐此人是否重复预约
            Integer memberId = member.getId();
            Integer setmealId = Integer.parseInt((String)map.get("setmealId"));
            Order order = new Order(memberId,date,null,null,setmealId);
            List<Order> orderList = orderDao.findByCondition(order);
            if (orderList !=null && orderList.size()>0){
                //已有此日此套餐的预约,不能重复预约
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
        }
        //当日可以预约，设置预约人数加一
        orderSetting.setReservations(orderSetting.getReservations()+1);
        orderSettingDao.editReservationsByOrderDate(orderSetting);
        //4、检查当前用户是否为会员，如果是会员则直接完成预约，如果不是会员则自动完成注册并进行预约
        if (member == null){
            member = new Member();
            member.setName((String) map.get("name"));
            member.setPhoneNumber(telephone);
            member.setIdCard((String) map.get("idCard"));
            member.setSex((String) map.get("sex"));
            member.setRegTime(new Date());
            //添加会员
            memberDao.add(member);
        }
        //5、预约成功，更新当日的已预约人数
        //保存预约信息到预约表
        Order order = new Order(member.getId(), date, (String)map.get("orderType"),Order.ORDERSTATUS_NO, Integer.parseInt((String) map.get("setmealId")));
        orderDao.add(order);

        Map addressMap=new HashMap();
        addressMap.put("addressId",Integer.parseInt(addressId));
        addressMap.put("orderId",order.getId());
        orderDao.addAddress(addressMap);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order.getId());

    }

    @Override
    public Result wxAddOrder(Map<String,Object> map,Integer[] setmealIds) throws Exception{
        //检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
        String orderDate = (String) map.get("orderDate");
        System.out.println(orderDate);
        Date date = DateUtils.parseString2Date(orderDate);
        //调用orderSettingDao查询当前日期是否有设置预约情况
        OrderSetting orderSetting = orderSettingDao.findByDate(date);
        if (orderSetting == null){
            return new Result(false,MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
        int number = orderSetting.getNumber();              //当日预约总数
        int reservations = orderSetting.getReservations();  //当日已预约数
        if(number <= reservations){
            return new Result(false,MessageConstant.ORDER_FULL);
        }
        //检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约
        //检查当前用户是否为会员，根据手机号判断
        String telephone = (String) map.get("phoneNumber");
        Member member = memberDao.findByTelephone(telephone);




        List<Order> orderList = new ArrayList<>();
        //防止重复预约
        if (member != null){
            //如果是会员则通过 会员id 日期 套餐id 查询预约表t_order 确定 当天此套餐此人是否重复预约
            Integer memberId = member.getId();
            for (Integer setmealId : setmealIds) {
                //order1封装条件数据
                Order order1 = new Order(memberId,date,null,null,setmealId);
                System.out.println(DateUtils.parseDate2String(date));
                Order order = orderDao.wxFindByCondition(order1);
                System.out.println(order);
                if (order!=null){
                    orderList.add(order);
                }
            }
        }

        //当日可以预约，设置预约人数加一
        orderSetting.setReservations(orderSetting.getReservations()+1);
        orderSettingDao.editReservationsByOrderDate(orderSetting);
        //4、检查当前用户是否为会员，如果是会员则直接完成预约，如果不是会员则自动完成注册并进行预约
        if (member == null){
            member = new Member();
            member.setName((String) map.get("name"));
            member.setPhoneNumber(telephone);
            member.setIdCard((String) map.get("idCard"));
            member.setSex((String) map.get("sex"));
            member.setRegTime(new Date());
            member.setBirthday(DateUtils.parseString2Date((String) map.get("birthday")));
            //添加会员
            memberDao.add(member);
        }
        //预约成功，更新当日的已预约人数
        //保存预约信息到预约表
        ArrayList<Integer> setmealIdsList = new ArrayList<>();//存储页面传递过来的id
        for (Integer setmealId : setmealIds) {
            setmealIdsList.add(setmealId);
        }
        for (Integer integer : setmealIdsList) {
            Order orderNow = new Order(member.getId(), date,"后台进行预约",Order.ORDERSTATUS_NO,integer);
            orderDao.add(orderNow);
        }


        if (orderList !=null && orderList.size()>0){
            //已有此日此套餐的预约,不能重复预约 返回已预约过的套餐的名字
            StringBuilder stringBuilder = new StringBuilder();
            for (Order order : orderList) {
                //根据seatmealId查询seatmeal的名字
                Integer id = order.getId();
                System.out.println(id);
                String setmealName = setMealDao.findNameById(order.getId());
                stringBuilder.append(setmealName+",");
            }
            //已预约过的套餐名称
            String seatmealNameBeOrdered = stringBuilder.toString();
            return new Result(true,MessageConstant.HAS_ORDERED,seatmealNameBeOrdered);
        }

        return new Result(true,MessageConstant.ORDER_SUCCESS);

    }

    //根据id查询预约信息，包括体检人信息、套餐信息
    @Override
    public Map findById(Integer id) throws Exception {
        Map map = orderDao.findById4Detail(id);
        if(map != null){
            //处理日期格式
            Date orderDate = (Date) map.get("orderDate");
            map.put("orderDate",DateUtils.parseDate2String(orderDate));
        }
        return map;
    }



    /**
     *   PuYunCong 分页查询 order
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult pycFindPage(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        Page<PycOrderManager> page = orderDao.selectByCondition(queryPageBean.getQueryString());

        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void affirmOrder(Integer id) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderStatus",Order.ORDERSTATUS_YES);
        map.put("id",id);
        orderDao.affirmOrder(map);
    }
    @Override
    public void zs_editOrderStatus(Integer id) {
        Order order = new Order();
        order.setOrderStatus(Order.ORDERSTATUS_YES);
        order.setId(id);
        orderDao.zs_editOrderStatus(order);
    }

    @Override
    public PageResult zs_findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);

        Page<Order> page= orderDao.zs_selectByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }
    //取消预约
    @Override
    public void zs_deleteOrder(Integer id) {
        orderDao.zs_deleteOrderById(id);
        memberDao.zs_deleteMemberById(id);
        setMealDao.zs_deleteSetMealById(id);
    }


}

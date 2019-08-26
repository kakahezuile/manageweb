package com.xj.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.Page;
import com.xj.bean.PayAndUser;
import com.xj.bean.PayTop;
import com.xj.bean.XjPay;
import com.xj.dao.XjPayDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("xjPayDao")
public class XjPayDaoImpl extends MyBaseDaoImpl implements XjPayDao {

	@Override
	public Integer addXjPay(XjPay xjPay) throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(xjPay, key);
		return key.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateXjPay(XjPay xjPay) throws Exception {
		// TODO Auto-generated method stub
		String sql = " UPDATE xj_pay SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(xjPay);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false;
		}
		
		sql += " WHERE pay_id = ? ";
		System.out.println(sql);
		list.add(xjPay.getPayId());
		int result = this.updateData(sql, list, null);
		return result > 0;
	}

	@Override
	public Page<PayAndUser> getPayList(Integer type , Integer status , Integer pageNum , Integer pageSize , Integer navNum) throws Exception {
		// TODO Auto-generated method stub
   		String sql = "SELECT u.username,u.user_id , u.nickname , u.phone , u.avatar , u.emob_id , xp.pay_address" + 

					 " , u.room , u.user_floor , u.user_unit , xp.pay_id , xp.handle_time , xp.create_time , xp.complete_time " +

					 " , xp.pay_status , xp.pay_sum , xp.pay_type , xp.pay_no , xp.wait_time , xp.owner_name , xp.next_pay , xp.last_pay " +

					 " FROM xj_pay xp left join users u on xp.user_id = u.user_id WHERE 1 = 1 ";
		Page<PayAndUser> page = null;
		if(type == -1 && status == -1){
			page = this.getData4Page(sql, new Object[]{}, pageNum, pageSize, navNum, PayAndUser.class);			
		}else{
			if(status == -1){
				sql += " AND xp.pay_type = ? ";
				page = this.getData4Page(sql, new Object[]{type}, pageNum, pageSize, navNum, PayAndUser.class);
			}
			if(type == -1){
				sql += " AND xp.pay_status = ? ";
				page = this.getData4Page(sql, new Object[]{status}, pageNum, pageSize, navNum, PayAndUser.class);
			}
			if(status != -1 && type != -1){
				sql += " AND xp.pay_type = ? AND xp.pay_status = ? ";
				page = this.getData4Page(sql, new Object[]{type,status}, pageNum, pageSize, navNum, PayAndUser.class);
			}
			sql+=" ORDER BY DESC";
			
		}
		return page;
	}

	@Override
	public PayTop getPayTop(Integer type, Integer status) throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT count(*) as pay_sum ,sum(pay_sum) as pay_par_sum"+

					 "  FROM xj_pay x WHERE pay_type = ? and pay_status = ? ";
		List<PayTop> list = this.getList(sql, new Object[]{type , status}, PayTop.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public XjPay getXjPay(Integer payId) throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT * FROM xj_pay WHERE pay_id = ?";
		List<XjPay> list = this.getList(sql, new Object[]{payId}, XjPay.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

}

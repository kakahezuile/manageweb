package com.xj.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xj.bean.Admin;
import com.xj.bean.ExtNode;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.Users;
import com.xj.bean.XjPay;
import com.xj.dao.AdminDao;
import com.xj.dao.XjPayDao;

@Service("payService")
public class PayService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private XjPayDao xjPayDao;
	
	public ResultStatusBean addPayService(XjPay xjPay) throws Exception{
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		xjPay.setCreateTime((int)(System.currentTimeMillis() / 1000l));
		Integer resultId = xjPayDao.addXjPay(xjPay);
		Users users = userService.getUserByUserId(xjPay.getUserId());
		if(resultId > 0){
			resultStatusBean.setResultId(resultId);
			resultStatusBean.setStatus("yes");
			// 通知所有admin 缴费了
			List<Admin> list = adminDao.getAdminList();
			int len = list.size();
			List<Users> listUser = new ArrayList<Users>();
			for(int i = 0 ; i < len ; i++){
				Users u = new Users();
				u.setEmobId(list.get(i).getEmobId());
				listUser.add(u);
			}
			
			ExtNode ext = new ExtNode();
		
			ext.setAvatar(users.getAvatar());
			ext.setContent("缴费啦");
			ext.setCMD_CODE(100);
			ext.setTimestamp(System.currentTimeMillis() / 1000l );
			ext.setNickname(users.getNickname());
			
//			EasemobChatMessage.testChatMessages(listUser, users.getEmobId(), "缴费啦", ext);
//			pushtoList.pushtoTransmission(listUser, new Gson().toJson(ext));
		}
		return resultStatusBean;
	}
}

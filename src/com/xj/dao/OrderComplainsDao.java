package com.xj.dao;

import com.xj.bean.ComplaintTop;
import com.xj.bean.ComplaintsUserMsg;
import com.xj.bean.Orders;
import com.xj.bean.Page;
import com.xj.bean.ShopUserMsg;

public interface OrderComplainsDao extends MyBaseDao{
	
	public ShopUserMsg getShopUserMsg(String emobId) throws Exception;
	
	public Page<ComplaintsUserMsg> getComplaintsUserMsg(Integer pageNum , Integer pageSize , Integer nvm) throws Exception;
	
	public Page<ComplaintsUserMsg> getComplaintsUserMsg(Integer communityId,String sort , Integer pageNum , Integer pageSize , Integer nvm) throws Exception;
	
	public Integer getUserToShopCount(String emobId , String shopEmobId) throws Exception;
	
	public Orders getThisOrderLastOrder(Integer orderId , String emobIdUser , String emobIdShop) throws Exception; // 获取当前订单的上一个订单

	public ComplaintTop getComplaints(Integer complaint_id) throws Exception;

	public Page<ComplaintsUserMsg> getComplaintsUser(String userName,
			Integer pageNum, Integer pageSize,Integer nvm)throws Exception;
	
}

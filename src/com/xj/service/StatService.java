package com.xj.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.xj.bean.Orders;
import com.xj.bean.ShopsStatisticsTop;
import com.xj.bean.TryOut;
import com.xj.bean.Users;
import com.xj.bean.UsersApp;
import com.xj.dao.MyUserDao2;
import com.xj.dao.ShopTypeDao;
import com.xj.dao.ShopsOrderHistoryDao;
import com.xj.utils.MongoUtils;

@Service("statService")
public class StatService {
	
	@Autowired
	private MyUserDao2 myUserDao2;
	
	@Autowired
	private ShopsOrderHistoryDao shopsOrderHistoryDao;
	
	@Autowired
	private ShopTypeDao shopTypeDao;
	
	public Map<String, String> statBycommunityAndType(Integer communityId, String shopType,Integer startTime,Integer endTime,int modules) throws Exception {
		/**
		 * 查询所有指定时间内的的已完成订单
		 */
		ShopsStatisticsTop sst = shopsOrderHistoryDao.getQuickShopTop(communityId,"2", startTime, endTime);
		Map<String ,String> map = new  HashMap<String, String>();
	//	List<Orders> ordersList =  myUserDao2.getAllEndedOrdersInTime(communityId,shopType,startTime,endTime);
		String shoptypeName = shopTypeDao.getShopTypeName(shopType);
		map.put("shoptypeName", shoptypeName);
		
//		map.put("completeCount", ordersList.size()+"");
		
		
		if(sst.getOrderQuantity()==null||sst.getOrderQuantity().equals("null")){
			map.put("orderQuantity","0");
		}else{
			map.put("orderQuantity", sst.getOrderQuantity());
		}
		
		if(sst.getUserNum()==null||sst.getUserNum().equals("null")){
			map.put("userNum","0");
		}else{
			map.put("userNum", sst.getUserNum());
		}
		
		if(sst.getEndOrderQuantity()==null||sst.getEndOrderQuantity().equals("null")){
			map.put("completeCount","0");
		}else{
			map.put("completeCount", sst.getEndOrderQuantity());
		}
		
//		BigDecimal totalPrice = new BigDecimal(0);
//		for (Orders orders : ordersList) {
//			System.out.println(orders.getOrderPrice()+"   ====");
//			BigDecimal price = new BigDecimal(orders.getOrderPrice());
//			
//			totalPrice = totalPrice.add(price);
//		}
		
	//	BigDecimal price = new BigDecimal(sst.getOrderPrice());
		
		
		if(sst.getOrderPrice()==null||sst.getOrderPrice().equals("null")){
			map.put("totalPrice", "0");
		}else{
			map.put("totalPrice", sst.getOrderPrice());
		}
	
		
//		获取快店点击量
		Map<String, Integer> circumDetails = getCircumDetails(communityId, modules, startTime, endTime);
		Integer clickNum = circumDetails.get("common");
		Integer commonUser = circumDetails.get("commonUser");
		map.put("clickNum", clickNum+"");
		map.put("commonUser", commonUser+"");
		
		Integer rate = myUserDao2.getCommissionRate(communityId);
		map.put("rate",rate+"");

		
		return map;
		
	}
	
	public Map<String, String> statBycommunityAndType2(Integer communityId, String shopType,Integer startTime,Integer endTime,int modules) throws Exception {
		
		/**
		 * 查询所有指定时间内的的已完成订单
		 */
		Map<String ,String> map = new  HashMap<String, String>();
		List<Orders> ordersList =  myUserDao2.getAllEndedOrdersInTime(communityId,shopType,startTime,endTime);
		String shoptypeName = shopTypeDao.getShopTypeName(shopType);
		map.put("shoptypeName", shoptypeName);
		
		map.put("completeCount", ordersList.size()+"");
		BigDecimal totalPrice = new BigDecimal(0);
		for (Orders orders : ordersList) {
			System.out.println(orders.getOrderPrice()+"   ====");
			BigDecimal price = new BigDecimal(orders.getOrderPrice());
			
			totalPrice = totalPrice.add(price);
		}
		map.put("totalPrice", totalPrice+"");
		
//		获取快店点击量
		Map<String, Integer> circumDetails = getCircumDetails(communityId, modules, startTime, endTime);
		Integer clickNum = circumDetails.get("common");
		map.put("clickNum", clickNum+"");
		
		Integer rate = myUserDao2.getCommissionRate(communityId);
		map.put("rate",rate+"");
		
		
		return map;
		
	}
	
	/**
	 * 按社区 模块  时间段 统计
	 * @param communityid
	 * @param modules
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Integer>  getCircumDetails(int communityid,int  modules, Integer start,Integer end) throws Exception{
		Map<String, Integer> map = new HashMap<String, Integer>();
		String detailsDaily = MongoUtils.getEventsDetailsDailyInTime(communityid, modules, start, end);
		DBObject dbobject =(DBObject)JSON.parse(detailsDaily);
		if( !"".equals(dbobject.get("info"))){
			ArrayList<DBObject> info = (ArrayList) dbobject.get("info");
			for (DBObject dbObject : info) {
				if(!"".equals(dbObject.get("userClick"))){
					ArrayList<DBObject> userclick  = (ArrayList)dbObject.get("userClick");
					if(userclick!=null && userclick.size()>0){
						for (DBObject dbObject2 : userclick) {
							String emobId = (String) dbObject2.get("emobId");
							
							Integer clickNum = (Integer) dbObject2.get("click");
							
							if(emobId!=null && !"".equals(emobId) && emobId.length()==32){
								if(map.containsKey(emobId)){
									map.put(emobId, clickNum+map.get(emobId));
								}else{
									map.put(emobId, clickNum);
								}
							}
						}
					}
				}
			}
		}
		
		Map<String,Integer> map2 = new  HashMap<String, Integer>();
		
		/**
		 * 去小区
		 */
		List<Users> users = myUserDao2.findUsersByCommunityIdWithNull(communityid);
		
		for (Users user : users) {
			String emobId = user.getEmobId();
			if(map.containsKey(emobId)){
				map2.put(emobId,map.get(emobId) );
			}
		}
		
		/**
		 * 去水军
		 */
		List<TryOut> allTryOut = myUserDao2.getAllTryOut();
		//水军
		Map<String, Integer> map3 = new  HashMap<String, Integer>();
		
		for (TryOut tryOut : allTryOut) {
			String emobId = tryOut.getEmobId();
			if(map.containsKey(tryOut.getEmobId())){
				map3.put(emobId, map.get(emobId));
			}
		}
		
		Set<String> keySet2 = map2.keySet();
		Map<String, Integer> map4 = new  HashMap<String, Integer>();
		for (String key : keySet2) {
			if(!map3.containsKey(key)){
				map4.put(key, map2.get(key));
			}
		}
		
		int sumClick = 0;
		if(map3!=null && map3.size()>0){
			Set<String> keySet = map3.keySet();
			for (String key : keySet) {
				Integer num = map3.get(key);
				sumClick= sumClick+num;
			}
		}
		
		int  click = 0;
		if(map4!=null && map4.size()>0){
			Set<String> keySet = map4.keySet();
			for (String key : keySet) {
				Integer num = map4.get(key);
				click= click+num;
			}
		}
		
		Map<String,Integer>  data = new  HashMap<String, Integer>();
		data.put("commonUser",map4.size());
		data.put("common",click);
		data.put("test",sumClick);
		data.put("testUser",map3.size());
		
		return data;
	}

	/**
	 * 按社区 模块  时间段 统计
	 * @param communityid
	 * @param modules
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Integer>  getCircumDetails2(int communityid,int  modules, Integer start,Integer end) throws Exception{
		Map<String, Integer> map = new  HashMap<String, Integer>();
		String detailsDaily = MongoUtils.getEventsDetailsDailyInTime(communityid, modules, start, end);
		DBObject dbobject =(DBObject)JSON.parse(detailsDaily);
		if( !"".equals(dbobject.get("info"))){
			ArrayList<DBObject> info =   (ArrayList) dbobject.get("info");
			for (DBObject dbObject : info) {
				
				if(!"".equals(dbObject.get("userClick"))){
					ArrayList<DBObject> userclick  = (ArrayList)dbObject.get("userClick");
					if(userclick!=null && userclick.size()>0){
						for (DBObject dbObject2 : userclick) {
							String	emobId = (String) dbObject2.get("emobId");
							
							Integer  clickNum = (Integer) dbObject2.get("click");
							
							if(emobId!=null && !"".equals(emobId) && emobId.length()==32){
								if(map.containsKey(emobId)){
									map.put(emobId, clickNum+map.get(emobId));
								}else{
									map.put(emobId, clickNum);
								}
							}
						}
					}
				}
			}
		}
		
		Map<String,Integer> map2 = new  HashMap<String, Integer>();
		
		/**
		 * 去小区
		 */
		List<Users> users = myUserDao2.findUsersByCommunityIdWithNull2(communityid);
		
		for (Users user : users) {
			String emobId = user.getEmobId();
			if(map.containsKey(emobId)){
				map2.put(emobId,map.get(emobId) );
			}
		}
		
		/**
		 * 去水军
		 */
		List<TryOut> allTryOut = myUserDao2.getAllTryOut();
		//水军
		Map<String, Integer> map3 = new  HashMap<String, Integer>();
		
		for (TryOut tryOut : allTryOut) {
			String emobId = tryOut.getEmobId();
			if(map.containsKey(tryOut.getEmobId())){
				map3.put(emobId, map.get(emobId));
			}
		}
		
		Set<String> keySet2 = map2.keySet();
		Map<String, Integer> map4 = new  HashMap<String, Integer>();
		for (String key : keySet2) {
			if(!map3.containsKey(key)){
				map4.put(key, map2.get(key));
			}
		}
		
		int sumClick = 0;
		if(map3!=null && map3.size()>0){
			Set<String> keySet = map3.keySet();
			for (String key : keySet) {
				Integer num = map3.get(key);
				sumClick= sumClick+num;
			}
		}
		
		int click = 0;
		if(map4!=null && map4.size()>0){
			Set<String> keySet = map4.keySet();
			for (String key : keySet) {
				Integer num = map4.get(key);
				click= click+num;
			}
		}
		
		Map<String,Integer>  data = new  HashMap<String, Integer>();
		data.put("commonUser",map4.size());
		data.put("common",click);
		data.put("test",sumClick);
		data.put("testUser",map3.size());
		
		return data;
	}
	
	public Integer getCommissionRate(Integer communityId) throws Exception {
		return myUserDao2.getCommissionRate(communityId);
	}

	public List<Orders> getOrderDetailByCondition(Integer communityId,
			String shopType,Integer startTime,Integer endTime) throws Exception {
		List<Orders> ordersList =  myUserDao2.getAllEndedOrdersInTime(communityId,shopType,startTime,endTime);
		for (Orders orders : ordersList) {
			String emobIdUser = orders.getEmobIdUser();
			UsersApp user = myUserDao2.getUserByEmobId(emobIdUser);
			String nickname = user.getNickname();
			String username = user.getUsername();
			String userFloor = user.getUserFloor();
			
			String userUnit = user.getUserUnit();
			String room = user.getRoom();
			String addr = nickname+"("+username+")"+"    "+userFloor+" "+userUnit+" "+room;
			orders.setOrderAddress(addr);
			Long time = orders.getEndTime()*1000l;
			String format = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(time));
			orders.setOrderDetail(format);
		}
		return ordersList;
	}
}

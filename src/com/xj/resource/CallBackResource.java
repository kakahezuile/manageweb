package com.xj.resource;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.ActivityPhoto;
import com.xj.bean.Facilities;
import com.xj.bean.LifePhoto;
import com.xj.bean.QiNiuBody;
import com.xj.bean.ShopItem;
import com.xj.bean.Shops;
import com.xj.bean.Users;
import com.xj.bean.XjTest;
import com.xj.dao.ActivityPhotoDao;
import com.xj.dao.FacilitiesDao;
import com.xj.dao.LifePhotoDao;
import com.xj.dao.ShopDao;
import com.xj.dao.ShopsDao;
import com.xj.dao.XjTestDao;
import com.xj.httpclient.utils.QiniuFileSystemUtil;
import com.xj.service.UserService;

@Component
@Path("/myCallbackUrl")
@Scope("prototype")
public class CallBackResource {
	
	private Logger logger = LoggerFactory.getLogger(CallBackResource.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ActivityPhotoDao activityPhotoDao;
	
	@Autowired
	private ShopDao shopDao;
	
	@Autowired
	private ShopsDao shopsDao;
	
	@Autowired
	private FacilitiesDao facilitiesDao;
	
	@Autowired
	private XjTestDao xjTestDao;
	
	@Autowired
	private LifePhotoDao lifePhotoDao;
	
	private final String myUrl = "http://7d9lcl.com2.z0.glb.qiniucdn.com/";
	/**
	 * 七牛上传回调方法
	 * @param jsonStr
	 * @return
	 */
	@POST
	public String getCallBack(String jsonStr){
		logger.info("当前七牛callBack被调用 -----------------------------");
		logger.info(jsonStr + " ----------------------------------------");
		QiNiuBody qiNiuBody = null;
		try {
			qiNiuBody = new Gson().fromJson(jsonStr, QiNiuBody.class);
			if("user".equals(qiNiuBody.getQiNiuType())){ // 更改用户头像url
				Users users = new Users();
				users.setAvatar(myUrl+qiNiuBody.getKey());
				users.setUserId(qiNiuBody.getQiNiuId());
				Users userTemp = userService.getUserByUserId(users.getUserId());
				String avatar = userTemp.getAvatar().replace("http://7d9lcl.com2.z0.glb.qiniucdn.com/", "");
				boolean result = userService.updateUser(users);
				if(result){
					QiniuFileSystemUtil.delete(avatar);
				}
			}else if("activity".equals(qiNiuBody.getQiNiuType())){ // 群图片
				Integer activityId = qiNiuBody.getQiNiuId();
				ActivityPhoto activityPhoto = new ActivityPhoto();
				activityPhoto.setCreateTime((int)(System.currentTimeMillis() / 1000l));
				activityPhoto.setPhotoUrl(myUrl+qiNiuBody.getKey());
				activityPhoto.setActivityId(activityId);
				activityPhotoDao.addPhoto(activityPhoto);
			}else if("goodsDetail".equals(qiNiuBody.getQiNiuType())){ //商品详情图片
				Integer serviceId = qiNiuBody.getQiNiuId();
				ShopItem shopItem = new ShopItem();
				shopItem.setServiceImg(myUrl+qiNiuBody.getKey());
				shopItem.setServiceId(new Long(serviceId));
				boolean result = shopDao.updateShop(shopItem);
				if(result){
					logger.info("商品明细"+serviceId + " : 修改成功");
				}
			}else if("shop".equals(qiNiuBody.getQiNiuType())){ // 店家logo
				Shops shops = new Shops();
				shops.setShopId(new Long(qiNiuBody.getQiNiuId()));
				shops.setLogo(myUrl+qiNiuBody.getKey());
				boolean b = shopsDao.updateShops(shops);
				if(b){
					logger.info("商品店铺"+ " : 修改成功");
				}
			}else if("facilities".equals(qiNiuBody.getQiNiuType())){ // 周边设施图片
				Facilities facilities = new Facilities();
				facilities.setLogo(myUrl+qiNiuBody.getKey());
				facilities.setFacilityId(new Long(qiNiuBody.getQiNiuId()));
				boolean result = facilitiesDao.updateFacilities(facilities);
				if(result){
					logger.info("周边设施"+ " : 修改成功");
				}
			}else if("circle".equals(qiNiuBody.getQiNiuType())){ // 生活圈 图片
				LifePhoto lifePhoto = new LifePhoto();
				lifePhoto.setCreateTime((int)(System.currentTimeMillis() / 1000));
				lifePhoto.setPhotoUrl(myUrl+qiNiuBody.getKey());
				lifePhoto.setLifeCircleId(qiNiuBody.getQiNiuId());
				lifePhotoDao.addLifePhoto(lifePhoto);
			}
		} catch (Exception e) {
		}
		qiNiuBody.setKey(myUrl+qiNiuBody.getKey()); 
		return new Gson().toJson(qiNiuBody);
	}
	
	@SuppressWarnings("rawtypes")
	@POST
	@Path("/alipayCallBack")
	public String alipayCallBack(@Context HttpServletRequest request
			){
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
	
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		try {
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			String subject = new String(request.getParameter("subject").getBytes("ISO-8859-1"),"UTF-8"); 
			XjTest xjTest = new XjTest();
			xjTest.setCreateTime((int)(System.currentTimeMillis() / 1000l));
			xjTest.setStrOne(out_trade_no);
			xjTest.setStrOne(subject);
			try {
				xjTestDao.addXjTest(xjTest);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
//		System.out.println("45666666666666666666666666");
//		String resultStr = "";
//		Integer orderId = 0;
//		Integer userBonusId = 0;
//		UserBonus userBonus = new UserBonus();
//		userBonus.setUserBonusId(userBonusId);
//		userBonus.setOrderId(orderId);
//		try {
//			boolean result = userBonusDao.updateUserBonus(userBonus);
//			if(result){
//				resultStr = "success";
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		return "success";
	}
}

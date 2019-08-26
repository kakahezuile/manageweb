package com.xj.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.xj.bean.BonuscoinExchange;
import com.xj.bean.ExtNode;
import com.xj.bean.LifeCircleNumer;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.ShopItem;
import com.xj.bean.Shops;
import com.xj.bean.UserPercent;
import com.xj.bean.Users;
import com.xj.bean.UsersApp;
import com.xj.dao.BonuscoinExchangeDao;
import com.xj.dao.LifeCircleDao;
import com.xj.dao.MyUserDao;
import com.xj.dao.ShopDao;
import com.xj.dao.ShopsDao;
import com.xj.httpclient.apidemo.EasemobIMUsers;
import com.xj.httpclient.utils.QiniuFileSystemUtil;
import com.xj.service.UserService;
import com.xj.utils.MD5Util;
import com.xj.utils.SingletonClient;

@Component
@Scope("prototype")
@Path("/communities/{communityId}/users")
public class UserResource {
	
	private Gson gson = new Gson();
	
	@Autowired
	private ShopsDao shopsDao;
	
	@Autowired
	private ShopDao shopDao;
	
	@Autowired
	private MyUserDao myUserDao;
	
	@Autowired
	private LifeCircleDao lifeCircleDao;
	
	@Autowired
	private BonuscoinExchangeDao bonuscoinExchangeDao;
	
	@Autowired
	private UserService userService;
	
	@Path("/add")
	@POST
	public String insertShop(String requestJson , @PathParam("communityId") Integer communityId){
		ResultStatusBean result = new ResultStatusBean();
		if(requestJson == null || "".equals(requestJson)){
			result.setStatus("no");
		}else{
			result.setStatus("yes");
			
			Users u = gson.fromJson(requestJson, Users.class);
			u.setCommunityId(communityId);
			u.setClientId(MD5Util.getMD5Str(System.currentTimeMillis()+""));
			u.setCreateTime((int)(System.currentTimeMillis() / 1000l));
			try {
				result = userService.addUsers(u);
			} catch (Exception e) {
				e.printStackTrace();
				return gson.toJson(result).replace("\"null\"", "\"\"");
			}
		}
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}

	@POST
	public String insert(@PathParam("communityId") Integer communityId , String json) throws Exception{// 添加用户
		ResultStatusBean result = new ResultStatusBean();
		result.setStatus("yes");
		
		Users u = null;
		try {
			u = gson.fromJson(json , Users.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String equipment = "";
		if(u != null){
			equipment = u.getEquipment();
		}
		
		result.setInfo(userService.defaultAddUsers(communityId,equipment));
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}

	/**
	 * 修改用户内容
	 * @param username
	 * @param json
	 * @return
	 */
	@PUT
	@Path("update/{username}")
	public String updateUserByUser(@PathParam("username") String username , String json){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		Users u = gson.fromJson(json, Users.class);
	
		try {
			Users userResult = userService.getUserByName(username);
			if(userResult != null){
				if(u.getPassword() != null && !"".equals(u.getPassword())){
					EasemobIMUsers.updateUserPass(userResult.getEmobId(), MD5Util.getMD5Str("&bang#bang@"+u.getPassword()));
					u.setPassword(MD5Util.getMD5Str(u.getPassword()+userResult.getSalt()));
				}
				
				u.setUserId(userResult.getUserId());
			}
			
			boolean b = userService.updateUser(u);
			if(b){
				resultStatusBean.setStatus("yes");
			}else{
				resultStatusBean.setStatus("no");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@PUT
	@Path("{emobId}")
	public String update(String json , @PathParam("emobId") String emobId){  // 修改密码 及其他信息
		ResultStatusBean result = new ResultStatusBean();
		if(json == null || "".equals(json) || "".equals(emobId) ){
			result.setStatus("no");
		}else{
			Users u = gson.fromJson(json, Users.class);
			if(emobId != null && !"".equals(emobId)){
				u.setEmobId(emobId);
			}
			try {
				UsersApp userResult = userService.getUserByEmobId(emobId);
				if(userResult != null){
					u.setUserId(userResult.getUserId());
					if(u.getPassword() != null && !"".equals(u.getPassword())){
						EasemobIMUsers.updateUserPass(userResult.getEmobId(), MD5Util.getMD5Str("&bang#bang@"+u.getPassword()));
						u.setPassword(MD5Util.getMD5Str(u.getPassword()+userResult.getSalt()));
					}
				}
				boolean b = userService.updateUser(u); 
				if(b){
					result.setStatus("yes");
				}else{
					result.setStatus("no");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}
	
	@GET
	@Path("/isEmpty/{username}")
	public String userIsEmpty(@PathParam("username") String username){
		ResultStatusBean result = new ResultStatusBean();
		try {
			Users u = userService.getUserByName(username);
			if(u == null){
				result.setStatus("yes");
				result.setMessage("当前用户可以注册");
			}else{
				result.setStatus("no");
				result.setMessage("当前用户已存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus("error");
			return gson.toJson(result);
		}
		return gson.toJson(result);
	}
	
	@POST
	@Path("/getListUser/{from}")
	public String getListUser(@PathParam("communityId") Integer communityId , @PathParam("from") String from , String json){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			if(json == null || "".equals(json)){
				resultStatusBean.setMessage("内容不能为空");
				resultStatusBean.setStatus("no");
				return gson.toJson(resultStatusBean);
			}else{
				ExtNode extTemp = gson.fromJson(json, ExtNode.class);
				ExtNode extNode = new ExtNode();
				
				/**
				 * 2015-8-7修正messageid相关
				 */
				Long messageId = System.currentTimeMillis();
				extNode.setCount(extTemp.getCount()+1);
				extNode.setMessageId(messageId);
				extNode.setTitle(extTemp.getTitle());
				extNode.setCommunityId(communityId);
				extNode.setCMD_CODE(extTemp.getCMD_CODE());
				extNode.setContent(extTemp.getContent());
				extNode.setNickname(extTemp.getTitle());
				extNode.setTitle(extTemp.getTitle());
				extNode.setTimestamp(System.currentTimeMillis() / 1000l);
				userService.pushMessage(communityId,extNode);
				resultStatusBean.setInfo(messageId);
				resultStatusBean.setMessage("发送成功");
				resultStatusBean.setStatus("yes");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("no");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	@POST
	@Path("/getExceptionUser/{from}")
	public String getExceptionUser(@PathParam("communityId") Integer communityId , @PathParam("from") String from , String json, @QueryParam("user_id") List<Integer> userId ){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		if(StringUtils.isEmpty(json) || CollectionUtils.isEmpty(userId)){
			resultStatusBean.setMessage("请输入推送内容并选择推送目标");
			resultStatusBean.setStatus("no");
			return gson.toJson(resultStatusBean);
		}
				
		ExtNode extTemp = gson.fromJson(json, ExtNode.class);
		ExtNode extNode = new ExtNode();
		Long messageId = System.currentTimeMillis();
		extNode.setMessageId(messageId);
		extNode.setCount(extTemp.getCount()+1);	
		extNode.setTitle(extTemp.getTitle());
		extNode.setCMD_CODE(extTemp.getCMD_CODE());
		extNode.setContent(extTemp.getContent());
		extNode.setTimestamp(System.currentTimeMillis() / 1000l);
		extNode.setNickname(extNode.getTitle());
		extNode.setCommunityId(communityId);
		
		userService.pushMessageByIds(userId,extNode);
		
		resultStatusBean.setInfo(messageId);
		resultStatusBean.setMessage("发送成功");
		resultStatusBean.setStatus("yes");
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@GET
	@Path("/{emobId}/shopSort")
	public String getShopsSort(@PathParam("emobId") String emobId){
		String result = "";
		try {
			UsersApp users = userService.getUserByEmobId(emobId);
			if(users == null){
				result = "{\"status\":\"error\" , \"message\":\"当前emobId不存在\"}";
				return result;
			}
			String sort = shopsDao.getShopsSortWithUserId(users.getUserId());
			if(sort != null && !"".equals(sort)){
				result = "{\"sort\":\""+sort+"\" , \"status\":\"yes\"}";
			}else{
				result = "{\"message\":\"no sort id\" , \"status\":\"no\"}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "{\"status\":\"error\"}";
			return result;
		}
		return result;
	}
	
	@Path("/{emobId}")
	@GET
	public String getUserByEmobId(@PathParam("communityId") Integer communityId,@PathParam("emobId") String emobId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		UsersApp users = null;
		try {
			users = userService.getUserByEmobId(emobId);
			if(users != null){
				resultStatusBean.setStatus("yes");
				users.setPassword("");
				users.setSalt("");
				if("owner".equals(users.getRole())){
					UserPercent userPercent = myUserDao.getUserPercent(communityId , users.getEmobId());
					Double characterPercent = (double)userPercent.getCharacterCount() / (double)userPercent.getUserCount() * (double)100;
					if(characterPercent != null){
						if(userPercent.getUserCount() == 0){
							users.setCharacterPercent(new Double(0));
						}else{
							users.setCharacterPercent(characterPercent);
						}
					}
					LifeCircleNumer lifeCircleNumer = lifeCircleDao.getLifeCircleNumber(emobId);
					if(lifeCircleNumer != null && lifeCircleNumer.getSum() != null){
						users.setLifeCircleSum(lifeCircleNumer.getSum());
					}
					resultStatusBean.setInfo(users);
				}else{
					Shops shops = shopsDao.getShopsByEmobId(emobId);
					resultStatusBean.setInfo(shops);
				}
				BonuscoinExchange bonuscoinExchange = bonuscoinExchangeDao.getBonuscoinExchange(communityId);
				if(bonuscoinExchange != null){
					users.setBonuscoin(bonuscoinExchange.getBonuscoin());
					users.setExchange(bonuscoinExchange.getExchange());
				}
			}else{
				resultStatusBean.setMessage("当前用户不存在");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
	
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@GET
	@Path("/getShopUser")
	public String getShopUserList(){
		ResultStatusBean resultStatusBean= new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<Users> list = userService.getShopList();
			if(list != null){
				resultStatusBean.setStatus("yes");
				resultStatusBean.setInfo(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean);
	}
	
	/**
	 * 发送验证码
	 */
	@GET
	@Path("/{username}/authCode")
	public String authCode(@PathParam("username") String username, @Context HttpServletRequest request){
		ResultStatusBean resultStatusBean= new ResultStatusBean();
		resultStatusBean.setStatus("no");
		String authCode= RandomStringUtils.random(4, "0123456789");//返回4为的字符串
		int i=SingletonClient.getClient().sendSMS(new String[] {username}, "【帮帮】您的验证码是"+authCode+"，验证码很重要打死都不能告诉别人哦！","",5);//带扩展码
		request.getSession().setMaxInactiveInterval(60*3);
		request.getSession().setAttribute(username, authCode);
		
		if(i>=0){
			resultStatusBean.setStatus("yes");
		}
		resultStatusBean.setInfo(authCode);
		return gson.toJson(resultStatusBean);
	}
	
	private final String myUrl = "http://7d9lcl.com2.z0.glb.qiniucdn.com/";
	@POST
	@Path("/uploadFile")
	@Consumes(MediaType.MULTIPART_FORM_DATA) 
	@Produces(MediaType.TEXT_HTML) 
	public void uploadFile(@FormDataParam("uploadFile") InputStream uploadFile ,  @FormDataParam("uploadFile") FormDataContentDisposition fileDisposition ,
			@FormDataParam("community_service_main_nickname") String nickName , @FormDataParam("community_service_main_shopname") String shopName , 
			@FormDataParam("community_service_main_phone") String phone , @FormDataParam("community_service_main_adress") String address , 
			@FormDataParam("community_service_main_idcard") String idCard , @FormDataParam("community_service_main_service_array") String serviceArray , 
			@FormDataParam("community_service_main_shopId") Integer shopId , @FormDataParam("community_service_main_userId") Integer userId ,
			@Context HttpServletRequest request , @Context HttpServletResponse response , @PathParam("communityId") Integer communityId
					){
		// 1  -  强电   2 - 弱电  3 - 上下水  4 - 综合
		
		String fileFullName = fileDisposition.getFileName();
		//获取工程根目录
		String path = this.getClass().getClassLoader().getResource("/").getPath();
		Users u = new Users();
		u.setUserId(userId);
		u.setPhone(phone);
		u.setIdcard(idCard);
		u.setNickname(nickName);
		u.setAvatar(myUrl+fileFullName);
		try {
			userService.update(u);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String array[] = serviceArray.split(",");
		Shops shops = new Shops();
		shops.setShopId(new Long(shopId));
		shops.setAddress(address);
		shops.setShopName(shopName);
		shops.setPhone(phone);
		shops.setStatus("normal");
		shops.setLogo(myUrl+fileFullName);
		try {
			shopsDao.updateShops(shops);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int len = array.length;
		for(int i = 0 ; i < len ; i++){
			ShopItem shopItem = new ShopItem();
			shopItem.setShopId(new Long(shopId));
			if("1".equals(array[i])){
				shopItem.setServiceName("强电");
			}else if("2".equals(array[i])){
				shopItem.setServiceName("弱电");
			}else if("3".equals(array[i])){
				shopItem.setServiceName("上下水");
			}else if("4".equals(array[i])){
				shopItem.setServiceName("综合");
			}
			shopItem.setBrandId(Integer.parseInt(array[i]));
			try {
				shopDao.updateShop(shopItem);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		File file = writeToFile(uploadFile, path+fileFullName);
		try {
			QiniuFileSystemUtil.upload(file, fileFullName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			request.setAttribute("communityId", communityId);
			request.getRequestDispatcher("/jsp/home.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private File writeToFile(InputStream is, String uploadedFileLocation) {
		File file = new File(uploadedFileLocation);
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			byte buffer[] = new byte[4 * 1024];
			while ((is.read(buffer)) != -1) {
				os.write(buffer);
			}
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (file.length()<5) {
			file.delete();
			return null;
		}
		return file;
	}
	
	@GET
	@Path("/list/inweb")
	public String getUserListInWeb(){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<Users> list = userService.getUserList();
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(list);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@GET
	@Path("/selectUser")
	public String selectUser(@PathParam("communityId") Integer communityId,@QueryParam("phone")String phone,@QueryParam("room")String room,@QueryParam("userFloor")String userFloor){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<Users> list = userService.getUsers(communityId,phone,room,userFloor);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(list);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@GET
	@Path("/selectFloorUnitUser")
	public String selectFloorUnitUser(@PathParam("communityId") Integer communityId,@QueryParam("room")String room,@QueryParam("userFloor")String userFloor,@QueryParam("userUnit")String userUnit){
		
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<Users> list = userService.selectFloorUnitUser(communityId,userFloor,userUnit,room);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(list);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@GET
	@Path("/selectUserFloor")
	public String selectUserFloor(@PathParam("communityId") Integer communityId){
		
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<String> list = userService.selectUserFloor(communityId);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(list);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@GET
	@Path("/selectUserUnit")
	public String selectUserUnit(@PathParam("communityId") Integer communityId,@QueryParam("userFloor")String userFloor){
		
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<String> list = userService.selectUserNuit(communityId,userFloor);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(list);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@GET
	@Path("/selectUserRoom")
	public String selectUserRoom(@PathParam("communityId") Integer communityId,@QueryParam("userFloor")String userFloor,@QueryParam("userUnit")String userUnit){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<String> list = userService.selectUserRoom(communityId,userFloor,userUnit);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(list);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@GET
	@Path("/simple")
	public String getSimpleUser(@PathParam("communityId") Integer communityId) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		if (null == communityId) {
			resultStatusBean.setStatus("no");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		
		try {
			resultStatusBean.setInfo(myUserDao.getSimpleUser(communityId));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@GET
	@Path("/{emobId}/address")
	public String getUserAddress(@PathParam("emobId") String emobId) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		if (StringUtils.isBlank(emobId)) {
			resultStatusBean.setStatus("no");
			resultStatusBean.setMessage("请提供用户环信id");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		
		try {
			resultStatusBean.setInfo(myUserDao.getUserAddress(emobId));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@GET
	@Path("/password")
	public String getUserPassword(@QueryParam("password") String password) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
			resultStatusBean.setStatus("no");
		
		try {
			resultStatusBean.setInfo(MD5Util.getMD5Str("&bang#bang@" + MD5Util.getMD5Str(password)));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	
	
}
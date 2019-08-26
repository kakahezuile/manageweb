package com.xj.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.xj.bean.ItemCategory;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.Rule;
import com.xj.bean.ShopItem;
import com.xj.bean.ShopType;
import com.xj.bean.Shops;
import com.xj.bean.ShopsCategory;
import com.xj.bean.ShopsUser;
import com.xj.bean.Users;
import com.xj.bean.UsersApp;
import com.xj.dao.RuleDao;
import com.xj.dao.ShopDao;
import com.xj.dao.ShopTypeDao;
import com.xj.dao.ShopsCategoryDao;
import com.xj.dao.ShopsDao;
import com.xj.httpclient.apidemo.EasemobIMUsers;
import com.xj.httpclient.utils.QiniuFileSystemUtil;
import com.xj.service.UserService;
import com.xj.utils.MD5Util;

@Path("/communities/page/index")
@Component
@Scope("prototype")
public class PageResource {
	private Gson gson = new Gson();
	@Autowired
	private RuleDao ruleDao;
	
	@Autowired
	private ShopTypeDao shopTypeDao;
	

	
	@Autowired  // spring 注入userService 对象
	private UserService userService;  // 操作用户service
	
	@Autowired
	private ShopsDao shopsDao;
	
	@Autowired
	private ShopDao shopDao;
	
	@Autowired
	private ShopsCategoryDao shopsCategoryDao;
	
	private final String myUrl = "http://7d9lcl.com2.z0.glb.qiniucdn.com/";
	
	
	
	


	
	
	@Path("/statHome")
	@POST
	public void statHome(@Context HttpServletRequest request , @Context HttpServletResponse response , @FormParam("hiddenCommunityId") Integer hiddenCommunityId ,
			@FormParam("hiddenAdminId") Integer adminId,@FormParam("hiddenUserName") String userName , @FormParam("hiddenPassWord") String passWord,
			@FormParam("UserName") String newUserName,@FormParam("emobId") String emobId,@FormParam("adminStatus") String adminStatus){
		
		try {
			List<Rule> listRule = ruleDao.getRuleByAdminId(adminId,"operations");
			List<ShopType> listShopType = shopTypeDao.getTypeList();
			request.setAttribute("username", userName);
			request.setAttribute("password", passWord);
			request.setAttribute("communityId", hiddenCommunityId);
			request.setAttribute("mytype", 1);
			request.setAttribute("list", listRule);
			request.setAttribute("listShopType", listShopType);
			request.setAttribute("adminId", adminId);
			request.setAttribute("newUserName", newUserName);
			request.setAttribute("emobId", emobId);
			request.setAttribute("adminStatus", adminStatus);
//			System.out.println(listRule.size());
			request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
			//response.sendRedirect(path +"/jsp/home.jsp?username="+userName+"&password="+passWord+"&communityId="+hiddenCommunityId);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Path("/partnerHome")
	@POST
	public void partnerHome(@Context HttpServletRequest request , @Context HttpServletResponse response , @FormParam("hiddenCommunityId") Integer hiddenCommunityId ,
			@FormParam("hiddenAdminId") Integer adminId,@FormParam("hiddenUserName") String userName , @FormParam("hiddenPassWord") String passWord,
			@FormParam("UserName") String newUserName,@FormParam("emobId") String emobId,@FormParam("adminStatus") String adminStatus){
		
		try {
			
			HttpSession session = request.getSession(); 
			session.setAttribute("emobId", emobId); 
			session.setAttribute("newUserName", newUserName); 
			
			List<Rule> listRule = ruleDao.getRuleByAdminId(adminId,"operations");
			List<ShopType> listShopType = shopTypeDao.getTypeList();
			request.setAttribute("username", userName);
			request.setAttribute("password", passWord);
			request.setAttribute("community_id", "0");
			request.setAttribute("mytype", 1);
			request.setAttribute("list", listRule);
			request.setAttribute("listShopType", listShopType);
			request.setAttribute("adminId", adminId);
			//request.setAttribute("newUserName", newUserName);
			request.setAttribute("emobId", emobId);
			request.setAttribute("adminStatus", adminStatus);
//			System.out.println(listRule.size());
			request.getRequestDispatcher("/jsp/teamwork/teamwork-survey.jsp").forward(request, response);
			//response.sendRedirect(path +"/jsp/home.jsp?username="+userName+"&password="+passWord+"&communityId="+hiddenCommunityId);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	@Path("/goHome")
	@POST
	public void Home(@FormParam("adminStatus") String adminStatus,@Context HttpServletRequest request , @Context HttpServletResponse response , @FormParam("hiddenCommunityId") Integer hiddenCommunityId ,
			@FormParam("hiddenAdminId") Integer adminId,@FormParam("emobId") String emobId,@FormParam("hiddenUserName") String userName , @FormParam("hiddenPassWord") String passWord,@FormParam("UserName") String newUserName){

		try {
			List<Rule> listRule = ruleDao.getRuleByAdminId(adminId,"owner");
			List<ShopType> listShopType = shopTypeDao.getTypeList();
			
			
			
			request.setAttribute("username", userName);
			request.setAttribute("password", passWord);
			request.setAttribute("communityId", hiddenCommunityId);
			request.setAttribute("mytype", 1);
			request.setAttribute("list", listRule);
			request.setAttribute("listShopType", listShopType);
			request.setAttribute("adminId", adminId);
			request.setAttribute("newUserName", newUserName);
			request.setAttribute("adminStatus", adminStatus);
			request.setAttribute("emobId", emobId);
//			System.out.println(listRule.size());
			request.getRequestDispatcher("/jsp/home.jsp").forward(request, response);
			//response.sendRedirect(path +"/jsp/home.jsp?username="+userName+"&password="+passWord+"&communityId="+hiddenCommunityId);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Path("/goHome2")
	@POST
	public void Home2(@FormParam("adminStatus") String adminStatus,@Context HttpServletRequest request , @Context HttpServletResponse response , @FormParam("hiddenCommunityId") Integer hiddenCommunityId ,
			@FormParam("hiddenAdminId") Integer adminId,@FormParam("hiddenUserName") String userName ,@FormParam("emobId") String emobId, @FormParam("hiddenPassWord") String passWord,@FormParam("UserName") String newUserName){
		
		try {
			List<Rule> listRule = ruleDao.getRuleByAdminId(adminId,"owner");
			List<ShopType> listShopType = shopTypeDao.getTypeList();
			request.setAttribute("username", userName);
			request.setAttribute("password", passWord);
			request.setAttribute("communityId", hiddenCommunityId);
			request.setAttribute("mytype", 1);
			request.setAttribute("list", listRule);
			request.setAttribute("listShopType", listShopType);
			request.setAttribute("adminId", adminId);
			request.setAttribute("newUserName", newUserName);
			request.setAttribute("adminStatus", adminStatus);
			request.setAttribute("emobId", emobId);
//			System.out.println(listRule.size());
			request.getRequestDispatcher("/jsp/index2.jsp").forward(request, response);
			//response.sendRedirect(path +"/jsp/home.jsp?username="+userName+"&password="+passWord+"&communityId="+hiddenCommunityId);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@GET
	@Path("/goHome")
	public void goHome(@Context HttpServletResponse response) throws Exception{
		response.sendRedirect("/index.jsp");
	}
	
	@GET
	@Path("/goHome2")
	public void goHome2(@Context HttpServletResponse response) throws Exception{
		response.sendRedirect("/index.jsp");
	}
	
	@GET
	@Path("/statHome")
	public void headGoHome(@Context HttpServletResponse response) throws Exception{
		response.sendRedirect("/index.jsp");
	}
	
	@GET
	@Path("/partnerHome")
	public void partnerHome(@Context HttpServletResponse response) throws Exception{
		response.sendRedirect("/index.jsp");
	}
	
	@Path("/head/goHome")
	@POST
	public void headGoHome(@Context HttpServletRequest request , @Context HttpServletResponse response , @FormParam("head_communityId") Integer hiddenCommunityId ,
			@FormParam("head_adminId") Integer adminId,@FormParam("head_username") String userName , @FormParam("head_password") String passWord){

		try {
			List<Rule> listRule = ruleDao.getRuleByAdminId(adminId);
			List<ShopType> listShopType = shopTypeDao.getTypeList();
			request.setAttribute("username", userName);
			request.setAttribute("password", passWord);
			request.setAttribute("communityId", hiddenCommunityId);
			request.setAttribute("mytype", 1);
			request.setAttribute("list", listRule);
			request.setAttribute("listShopType", listShopType);
			request.setAttribute("adminId", adminId);
//			System.out.println(listRule.size());
			request.getRequestDispatcher("/jsp/home.jsp").forward(request, response);
			//response.sendRedirect(path +"/jsp/home.jsp?username="+userName+"&password="+passWord+"&communityId="+hiddenCommunityId);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * 获取要修改的商家
	 */
	
	@Path("/getShopsUser/{shopId}")
	@GET
	public String  getShopsUser(@PathParam("shopId") Integer shopId){
		Shops shops = null;
		try {
			shops = shopsDao.getShopsByShopId(shopId);
			if(shops==null){
				return "没有找到";
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		ShopsUser su=new ShopsUser();
		UsersApp user=null;
		try {
			user=userService.getUserByEmobId(shops.getEmobId());
			List<ItemCategory> list=shopDao.getItemCategory(shops.getShopId());
			su.setAuthCode(shops.getAuthCode());
			
			su.setList(list);
			
			su.setShopPhone(shops.getPhone());
			su.setUsername(shops.getShopName());
		    su.setAvatar(shops.getAddress());
			
			if(user!=null){
			//	su.setAvatar(user.getAvatar());
				su.setIdcard(user.getIdcard());
				su.setNickname(user.getNickname());
				su.setPhone(user.getPhone());
				su.setUserId(user.getUserId());
			}
		     	su.setStatus(shops.getStatus());
				su.setLogo(shops.getLogo());
				resultStatusBean.setInfo(su);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gson.toJson(resultStatusBean);
	}
	
	
	
	
	
	/**
	 * 添加维修师傅
	 */
	@POST
	@Path("{communityId}/uploadFile")
	@Consumes(MediaType.MULTIPART_FORM_DATA) 
    @Produces(MediaType.TEXT_HTML) 
	public String uploadFile(@FormDataParam("addMasterUploadFile") InputStream uploadFile ,  @FormDataParam("addMasterUploadFile") FormDataContentDisposition fileDisposition ,
			@FormDataParam("community_service_main_nickname") String nickName , @FormDataParam("community_service_main_username") String userName , 
			@FormDataParam("community_service_main_phone") String phone , @FormDataParam("community_service_main_adress") String address , 
			@FormDataParam("community_service_main_idcard") String idCard , @FormDataParam("community_service_main_service_array") String serviceArray , 
			@FormDataParam("community_service_main_shopId") Integer shopId , @FormDataParam("community_service_main_shopPhone") String shopPhone ,
			@Context HttpServletRequest request , @Context HttpServletResponse response , @PathParam("communityId") Integer communityId ,
			@FormDataParam("community_service_admin_id") Integer adminId, @FormDataParam("community_service_password") String passWord, @FormDataParam("community_service_role") String role
			
					){
		// 1  -  强电   2 - 弱电  3 - 上下水  4 - 综合
		
		String fileFullName = fileDisposition.getFileName();
		//获取工程根目录
		String path = this.getClass().getClassLoader().getResource("/").getPath();
		Users u = new Users();
		try {
			Users userTemp = userService.getUserByName(phone);
			if(userTemp != null){
				return "";
			}
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		u.setPhone(phone);
		u.setIdcard(idCard);
		u.setNickname(nickName);
		u.setUsername(phone);
		u.setSalt(MD5Util.getStr());
	
		u.setPassword(MD5Util.getMD5Str(passWord+u.getSalt()));
		u.setEmobId(MD5Util.getMD5Str(u.getPhone()));
//		u.setPassword(passWord);
		u.setRole(role);
		//u.setAvatar(myUrl+fileFullName);
		Integer userId = 0;
		Users tempUser = new Users();
		try {
			//userService.update(u);
			userId = userService.insert(u);
			tempUser = userService.getUserByUserId(userId);
			EasemobIMUsers.addUser(MD5Util.getMD5Str(u.getPhone()), MD5Util.getMD5Str("&bang#bang@"+passWord));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String array[] = serviceArray.split(",");
		Shops shops = new Shops();
		shops.setShopId(new Long(shopId));
		shops.setAddress(address);
		shops.setShopName(userName);
		shops.setPhone(shopPhone);
		shops.setStatus("normal");
		//shops.setStatus("normal");
		shops.setLogo(myUrl+fileFullName);
		shops.setEmobId(tempUser.getEmobId());
		try {
			shopsDao.updateShops(shops);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(shops != null && "5".equals(shops.getSort())){
			int len = array.length;
			for(int i = 0 ; i < len ; i++){
				ShopsCategory shopsCategory = new ShopsCategory();
				shopsCategory.setShopId(shopId);
				Integer catId = Integer.parseInt(array[i]);
				try {
					ShopsCategory shopsCategory2 = shopsCategoryDao.getShopsCategory(shopId, catId);
					if(shopsCategory2 != null){
						continue;
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				shopsCategory.setCategoryId(catId);
				shopsCategory.setStatus("true");
				shopsCategory.setCreateTime((int)(System.currentTimeMillis() / 1000l));
				try {
					shopsCategoryDao.addShopsCategory(shopsCategory);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					List<ShopItem> list = shopDao.findAllByCatIdGroupBy(catId);
					Iterator<ShopItem> it = list.iterator();
					while(it.hasNext()){
						ShopItem shopItem = it.next();
						List<ShopItem> listShop = shopDao.getShopItemByParam(shopId, catId, shopItem.getServiceName());
						if(listShop != null){
							continue;
						}
						shopItem.setServiceId(null);
						shopItem.setCreateTime(System.currentTimeMillis() / 1000l);
						shopItem.setShopId(new Long(shopId));
						shopDao.insertShop(shopItem);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		File file = writeToFile(uploadFile, path+fileFullName);
		try {
			QiniuFileSystemUtil.upload(file, fileFullName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(fileFullName);
//		try {
//			
//		
//			List<Rule> listRule = ruleDao.getRuleByAdminId(adminId);
//			List<ShopType> listShopType = shopTypeDao.getTypeList();
//			request.setAttribute("username", userName);
//			request.setAttribute("password", passWord);
//			request.setAttribute("communityId", communityId );
//			request.setAttribute("mytype", 1);
//			request.setAttribute("list", listRule);
//			request.setAttribute("listShopType", listShopType);
//			request.setAttribute("adminId", adminId);
//			request.getRequestDispatcher("/jsp/home.jsp").forward(request, response);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return "/add.jsp";
	}
	
	/**
	 * 修改维修师傅
	 */
	@POST
	@Path("{communityId}/uploadFile/up")
	@Consumes(MediaType.MULTIPART_FORM_DATA) 
	@Produces(MediaType.TEXT_HTML) 
	public String upShopsUserPhone(@FormDataParam("addMasterUploadFile") InputStream uploadFile ,  @FormDataParam("addMasterUploadFile") FormDataContentDisposition fileDisposition ,
			@FormDataParam("community_service_main_nickname") String nickName , @FormDataParam("community_service_main_username") String userName , 
			@FormDataParam("community_service_main_phone") String phone , @FormDataParam("community_service_main_adress") String address , 
			@FormDataParam("community_service_main_idcard") String idCard , @FormDataParam("community_service_main_service_array") String serviceArray , 
			@FormDataParam("community_service_main_shopId") Integer shopId , @FormDataParam("community_service_main_shopPhone") String shopPhone ,
			@Context HttpServletRequest request , @Context HttpServletResponse response , @PathParam("communityId") Integer communityId ,
			@FormDataParam("community_service_admin_id") Integer adminId, @FormDataParam("community_service_password") String passWord, @FormDataParam("community_service_role") String role,
			@FormDataParam("user_up_master_id") Integer userId,@FormDataParam("community_service_main_status") String status
			
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
		u.setUsername(phone);
		u.setAvatar(address);
//		u.setPassword(passWord);
		//u.setAvatar(myUrl+fileFullName);
		try {
			//userService.update(u);
			 userService.update(u);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String array[] = serviceArray.split(",");
		Shops shops = new Shops();
		shops.setShopId(new Long(shopId));
		shops.setAddress(address);
		//shops.setShopName(userName);
		shops.setPhone(shopPhone);
		shops.setStatus(status);
		//shops.setStatus("normal");
		if(fileFullName!=null&&!fileFullName.equals("")){
		   shops.setLogo(myUrl+fileFullName);
		}
		try {
			shopsDao.updateShops(shops);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			List<ItemCategory> listIt=shopDao.getItemCategory(shops.getShopId());
			for (ItemCategory itemCategory : listIt) {
				ShopsCategory shopsCategory2 = shopsCategoryDao.getShopsCategory(shopId, itemCategory.getCatId());
				if(shopsCategory2 != null){
					shopsCategory2.setStatus("false");
					shopsCategoryDao.updateShopsCategory(shopsCategory2);
					continue;
				}
			}
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		int len = array.length;
		for(int i = 0 ; i < len ; i++){
			ShopsCategory shopsCategory = new ShopsCategory();
			shopsCategory.setShopId(shopId);
			Integer catId = Integer.parseInt(array[i]);
			try {
				ShopsCategory shopsCategory2 = shopsCategoryDao.getShopsCategory(shopId, catId);
				if(shopsCategory2 != null){
					shopsCategory2.setStatus("true");
					shopsCategoryDao.updateShopsCategory(shopsCategory2);
					continue;
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			shopsCategory.setCategoryId(catId);
			shopsCategory.setStatus("true");
			shopsCategory.setCreateTime((int)(System.currentTimeMillis() / 1000l));
			try {
				shopsCategoryDao.addShopsCategory(shopsCategory);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				List<ShopItem> list = shopDao.findAllByCatIdGroupBy(catId);
				Iterator<ShopItem> it = list.iterator();
				while(it.hasNext()){
					ShopItem shopItem = it.next();
					List<ShopItem> listShop = shopDao.getShopItemByParam(shopId, catId, shopItem.getServiceName());
					if(listShop != null){
						continue;
					}
					shopItem.setServiceId(null);
					shopItem.setCreateTime(System.currentTimeMillis() / 1000l);
					shopItem.setShopId(new Long(shopId));
					shopDao.insertShop(shopItem);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		if(fileFullName!=null&&!fileFullName.equals("")){
			File file = writeToFile(uploadFile, path+fileFullName);
			try {
				QiniuFileSystemUtil.upload(file, fileFullName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			System.out.println(fileFullName);
			
		}
		
		return "/add.jsp";
	
	}
	
	
	/**
	 * 添加保洁
	 */
	@POST
	@Path("/{communityId}/cleaning/uploadFile")
	@Consumes(MediaType.MULTIPART_FORM_DATA) 
    @Produces(MediaType.TEXT_HTML) 
	public void cleaningUploadFile(@FormDataParam("cleaningUploadFile") InputStream uploadFile ,  @FormDataParam("cleaningUploadFile") FormDataContentDisposition fileDisposition ,
			@FormDataParam("community_cleaning_main_nickname") String nickName , @FormDataParam("community_cleaning_main_shopname") String shopName , 
			@FormDataParam("community_cleaning_main_phone") String phone , @FormDataParam("community_cleaning_main_adress") String address , 
			@FormDataParam("community_cleaning_main_idcard") String idCard , 
			@FormDataParam("community_cleaning_main_shopId") Integer shopId , @FormDataParam("community_cleaning_main_userId") Integer userId ,
			@Context HttpServletRequest request , @Context HttpServletResponse response , @PathParam("communityId") Integer communityId ,
			@FormDataParam("community_cleaning_admin_id") Integer adminId , @FormDataParam("community_cleaning_username") String userName ,
			@FormDataParam("community_cleaning_password") String passWord , @FormDataParam("community_cleaning_main_detail") String detail
					){
		
		
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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	//	String array[] = serviceArray.split(",");
		Shops shops = new Shops();
		shops.setShopId(new Long(shopId));
		shops.setAddress(address);
		shops.setShopName(shopName);
		shops.setPhone(phone);
		shops.setStatus("normal");
		shops.setShopsDesc(detail);
		shops.setLogo(myUrl+fileFullName);
		try {
			shopsDao.updateShops(shops);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		File file = writeToFile(uploadFile, path+fileFullName);
		try {
			QiniuFileSystemUtil.upload(file, fileFullName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(fileFullName);
		try {
			
		
			List<Rule> listRule = ruleDao.getRuleByAdminId(adminId);
			List<ShopType> listShopType = shopTypeDao.getTypeList();
			request.setAttribute("username", userName);
			request.setAttribute("password", passWord);
			request.setAttribute("communityId", communityId );
			request.setAttribute("mytype", 1);
			request.setAttribute("list", listRule);
			request.setAttribute("listShopType", listShopType);
			request.setAttribute("adminId", adminId);
			request.getRequestDispatcher("/jsp/home.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private File writeToFile(InputStream is, String uploadedFileLocation) {
		// TODO Auto-generated method stub
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
//		System.out.println(uploadedFileLocation+"文件大小"+file.length());
		if (file.length()<5) {
			file.delete();
			return null;
		}
		return file;

	}
}

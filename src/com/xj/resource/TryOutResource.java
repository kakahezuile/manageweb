package com.xj.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.sun.jersey.api.view.Viewable;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.xj.bean.LifeCircle;
import com.xj.bean.Page;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.Shops;
import com.xj.bean.Users;
import com.xj.httpclient.utils.QiniuFileSystemUtil;
import com.xj.service.TryOutService;
import com.xj.utils.MD5Util;

/**
 * 水军模块
 * @author 王磊
 *
 */
@Path("/communities/{communityId}/tryOuts")
@Component
@Scope("prototype")
public class TryOutResource {

	private static final String qiniuUrlRoot = "http://7d9lcl.com2.z0.glb.qiniucdn.com/";
	
	@Autowired
	private TryOutService tryOutService;
	
	
	private Gson gson = new Gson();
	/**
	 * 添加水军  ---向用户表里面添加一条数据 ，同时向水军表添加一条数据
	 * @return
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_HTML)
	public Viewable addTryOut(
							@Context HttpServletRequest request,
							@Context HttpServletResponse response,
							@FormDataParam("username") String username,
							@FormDataParam("password") String password,
							@FormDataParam("nickname") String nickname,
							@FormDataParam("communityId") Integer communityId,
							@FormDataParam("userFloor") String userFloor,
							@FormDataParam("userUnit") String userUnit,
							@FormDataParam("room") String room,
							@FormDataParam("signature") String signature,
							@FormDataParam("remarks") String remarks,
							@FormDataParam("poster") InputStream posterInputStream,
							@FormDataParam("poster") FormDataContentDisposition posterFileDisposition,
							@FormDataParam("posterValue") String posterValue
							) throws ServletException, IOException{
		String uri = "/jsp/navy/error.jsp";
		String message = "";
		String posterFile = StringUtils.isBlank(posterValue) ? uploadImage(posterInputStream, posterFileDisposition) : posterValue;
		if (StringUtils.isBlank(posterFile)) {
			message += "上传头像失败!";
		}
		
		Users users = new Users();
		users.setUsername(username);
		users.setPassword(MD5Util.getMD5Str(password));
		users.setNickname(nickname);
		users.setCommunityId(communityId);
		users.setAvatar(posterFile);
		users.setRemarks(remarks);
		users.setUserFloor(userFloor+"号楼");
		users.setUserUnit(userUnit+"单元");
		users.setRoom(room);
		users.setSignature(signature);
		try {
			String result = tryOutService.addTryOut(users);
			if(StringUtils.isBlank(result)){
				uri = "/jsp/navy/success.jsp";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message += "添加用户过程出现了错误！";
		}
		request.setAttribute("message", message);
		return  new Viewable(uri, null);
	}
	
	/**
	 * 添加水军   店铺
	 * @param json
	 * @return
	 */
	@POST
	@Path("/shop")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_HTML)
	public Viewable addTryOutShop(@Context HttpServletRequest request,
			@FormDataParam("username") String username,
			@FormDataParam("password") String password,
			@FormDataParam("shopName") String shopName,
			@FormDataParam("sort") String sort,
			@FormDataParam("communityId") Integer communityId,
			@FormDataParam("remarks") String remarks,
			@FormDataParam("poster") InputStream posterInputStream,
			@FormDataParam("poster") FormDataContentDisposition posterFileDisposition){
		String uri = "/jsp/navy/error.jsp";
		String message = "";
		Shops shops = new Shops();
		shops.setShopName(shopName);
		shops.setCommunityId(communityId);
		shops.setPhone(username);
		shops.setSort(sort);
		Users users = new Users();
		users.setUsername(username);
		users.setCommunityId(communityId);
		users.setRemarks(remarks);
		shops.setUser(users);
		
		try {
			String result = tryOutService.addTryOutShop(shops);
			if(StringUtils.isBlank(result)){
				tryOutService.addTryOut(users);
				uri = "/jsp/navy/success.jsp";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message += "添加用户过程出现了错误！";
		}
		request.setAttribute("message", message);
		return  new Viewable(uri, null);
	}
	
	
	/**
	 * 获取水军列表
	 * @param communityId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@GET
	public String getTryOuts(@PathParam("communityId") Integer communityId,@DefaultValue("1")@QueryParam("pageNum")Integer pageNum ,@DefaultValue("20")@QueryParam("pageSize")Integer pageSize){
		ResultStatusBean resultBean = new ResultStatusBean();
		List<Users> users = tryOutService.getTryOuts(communityId,pageNum,pageSize);
		Integer sum = tryOutService.getTryOutNum(communityId);//根据小区id获取水军总数
		Page<Users> page = new Page<Users>(pageNum, sum, pageSize, 10); 
		page.setPageData(users);
		if(CollectionUtils.isNotEmpty(users)){
			resultBean.setStatus("yes");
			resultBean.setInfo(page);
			return gson.toJson(resultBean).replace("\"null\"", "\"\"");
		}
		resultBean.setStatus("no");
		resultBean.setMessage("暂时没有水军数据！");
		return gson.toJson(resultBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 获取生活圈
	 * @param communityId
	 * @param emobId 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@GET
	@Path("/user/{emobId}")
	public String getLifeCircles(@PathParam("emobId") String emobId,@DefaultValue("1")@QueryParam("pageNum")Integer pageNum ,@DefaultValue("20")@QueryParam("pageSize")Integer pageSize){
		ResultStatusBean resultBean = new ResultStatusBean();
		List<LifeCircle> lifeCircles = tryOutService.getLifeCircles(emobId,pageNum,pageSize);
		int sum =  tryOutService.getLifeCirclesSum(emobId);
		Page<LifeCircle> page = new Page<LifeCircle>(pageNum, sum, pageSize, 10); 
		page.setPageData(lifeCircles);
		if(CollectionUtils.isNotEmpty(lifeCircles)){
			resultBean.setStatus("yes");
			resultBean.setInfo(page);
			return gson.toJson(resultBean).replace("\"null\"", "\"\"");
		}
		resultBean.setStatus("no");
		resultBean.setMessage("暂时没有生活圈！");
		return gson.toJson(resultBean).replace("\"null\"", "\"\"");
	}
	
	
	private String uploadImage(InputStream inputStream, FormDataContentDisposition posterFileDisposition) {
		if (null != posterFileDisposition && StringUtils.isBlank(posterFileDisposition.getFileName())) {
			return null;
		}
		
		String fileName = UUID.randomUUID().toString().replaceAll("-", "");
		File file = writeToFile(inputStream, this.getClass().getClassLoader().getResource("/").getPath() + fileName);
		if (null == file) {
			return null;
		}
		
		try {
			QiniuFileSystemUtil.upload(file, fileName);
			
			return qiniuUrlRoot + fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private File writeToFile(InputStream is, String location) {
		File file = new File(location);
		try (OutputStream os = new FileOutputStream(file)) {
			byte buffer[] = new byte[4 * 1024];
			while ((is.read(buffer)) != -1) {
				os.write(buffer);
			}
			os.flush();
			
			return file;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@GET
	@Path("/users/{emobId}")
	public String getNavyByEmobId(@PathParam("emobId")String emobId){
		ResultStatusBean resultBean = new ResultStatusBean();
		resultBean.setInfo(tryOutService.getNavyByEmobId(emobId));
		return gson.toJson(resultBean).replace("\"null\"", "\"\"");
	}
	@POST
	@Path("/edit")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_HTML)
	public Viewable editNavy(@Context HttpServletRequest request,
						@Context HttpServletResponse response,
						@FormDataParam("emobId") String emobId,
						@FormDataParam("userId") Integer userId,
						@FormDataParam("username") String username,
						@FormDataParam("password") String password,
						@FormDataParam("nickname") String nickname,
						@FormDataParam("signature") String signature,
						@FormDataParam("poster") InputStream posterInputStream,
						@FormDataParam("poster") FormDataContentDisposition posterFileDisposition,
						@FormDataParam("posterValue") String posterValue
							){
		
		String message = "";
		String posterFile = StringUtils.isBlank(posterValue) ? uploadImage(posterInputStream, posterFileDisposition) : posterValue;
		if (StringUtils.isBlank(posterFile)) {
			message += "上传头像失败!";
		}
		
		Users users = new Users();
		users.setUserId(userId);
		users.setUsername(username);
		users.setPassword(MD5Util.getMD5Str(password));
		users.setNickname(nickname);
		users.setAvatar(posterFile);
		users.setSignature(signature);
		users.setEmobId(emobId);
		
		Boolean result = tryOutService.editNavy(users);
		String	uri = "/jsp/navy/success.jsp";
		if(!result){
			uri = "/jsp/navy/success.jsp";
			request.setAttribute("message", message);
		}
		return  new Viewable(uri, null);
	}
	
	/**
	 * 通过用户名和昵称查找水军
	 * @param query
	 * @return
	 */
	@GET
	@Path("/search")
	public String searchNavy(@QueryParam("query")String query){
		ResultStatusBean resultBean = new ResultStatusBean();
		try {
			resultBean.setInfo(tryOutService.searchNavy(query));
		} catch (Exception e) {
			e.printStackTrace();
			resultBean.setStatus("no");
			resultBean.setMessage("查询失败!");
		}
		return gson.toJson(resultBean).replace("\"null\"", "\"\"");
		
	}
	
}

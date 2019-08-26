package com.xj.resource;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.AppUpdate;
import com.xj.bean.AppVersion;
import com.xj.bean.Communities;
import com.xj.bean.Page;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.Users;
import com.xj.service.AppVersionService;
import com.xj.service.UserService;

@Path("/communities/appversion")
@Component
@Scope("prototype")
public class AppVersionResource {
	
	private Gson gson = new Gson();
	
	
	@Autowired
	private AppVersionService appVersionService;
	
	@Autowired
	private UserService userService;
	/**
	 * 获取版本信息
	 * @return
	 */
	@GET
	public String getAppVersion(){ 
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<AppVersion> list = appVersionService.getAppVersion();
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	
	/**
	 * app升级配置,可配置指定用户，指定小区，全部用户升级
	 * @param json
	 * @return
	 */
	@POST
	@Path("/config/update")
	public String appUpdateConfig(String json){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		AppUpdate update = gson.fromJson(json, AppUpdate.class);
		try {
			appVersionService.configAppUpdate(update);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("no");
		}
		resultStatusBean.setStatus("yes");
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 获取小区app版本信息
	 * @return
	 */
	@Path("/communityAppVersion")
	@GET
	public String getListCommunityAppVersion(@DefaultValue("1")@QueryParam("pageNum")Integer pageNum,@DefaultValue("15")@QueryParam("pageSize")Integer pageSize,@QueryParam("query")String query){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			Page<Communities> communities = appVersionService.getListCommunityAppVersion(pageNum,pageSize,query);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(communities);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("no");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	
	
	@Path("/all")
	@GET
	public String getAppversions() {
		ResultStatusBean result = new ResultStatusBean();
		try {
			result.setInfo(appVersionService.getAppversions());
			result.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage("获取版本信息时出现了异常");
			result.setStatus("no");
		}
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}
	
	@Path("/users")
	@GET
	public String getCommunityUsers(@QueryParam("communityId")Integer communityId,@DefaultValue("1")@QueryParam("pageNum")Integer pageNum,@DefaultValue("15")@QueryParam("pageSize")Integer pageSize,@QueryParam("query")String query){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			 Page<Users> page = userService.getUserByCommunityIdWithPage(communityId,pageNum,pageSize,query);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(page);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("no");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
}

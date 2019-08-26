package com.xj.resource;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.AppDownload;
import com.xj.bean.ResultStatusBean;
import com.xj.service.AppService;
import com.xj.service.ConfigService;

@Component
@Path("/apps/{appName}/version")
@Scope("prototype")
public class AppResource {

	private Gson gson = new Gson();

	@Autowired
	private AppService appService;

	@Autowired
	private ConfigService configService;
	
	@GET
	public String getVersion(@PathParam("appName") String appName) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			resultStatusBean.setInfo(appService.getVersion(appName));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@Path("/code/{type}")
	@GET
	public void code(@Context HttpServletRequest request, @Context HttpServletResponse response, @PathParam("appName") String appName, @PathParam("type") String type) throws Exception {
		request.setAttribute("communityId", appName);
		request.setAttribute("type", type);
		request.getRequestDispatcher("/jsp/app/download-o2.jsp").forward(request, response);
	}
	
	@Path("/addAppDownload/android/{communityId}/{type}")
	@GET
	public void addAppDownloadAndroid(@Context HttpServletRequest request, @Context HttpServletResponse response, @PathParam("communityId") Integer communityId, @PathParam("type") Integer type)throws Exception{
		AppDownload appDownload = new AppDownload();
		appDownload.setCreateTime((int) (new Date().getTime()/1000));
		appDownload.setTerrace("android");
		appDownload.setCommunityId(communityId);
		appDownload.setType(type);
		try {
			appService.addAppDownload(appDownload);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.sendRedirect(configService.get("android_download_url").getValue());
	}
	
	@Path("/addAppDownload/ios/{communityId}/{type}")
	@GET
	public void addAppDownloadIos(@Context HttpServletRequest request, @Context HttpServletResponse response, @PathParam("communityId") Integer communityId, @PathParam("type") Integer type)throws Exception{
		AppDownload appDownload = new AppDownload();
		appDownload.setCreateTime((int) (new Date().getTime()/1000));
		appDownload.setTerrace("ios");
		appDownload.setCommunityId(communityId);
		appDownload.setType(type);
		try {
			appService.addAppDownload(appDownload);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.sendRedirect(configService.get("iOs_download_url").getValue());
	}
}
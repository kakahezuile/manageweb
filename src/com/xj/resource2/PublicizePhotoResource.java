package com.xj.resource2;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.PublicizePhotos;
import com.xj.bean.ResultStatusBean;
import com.xj.dao.PublicizePhotoDao;

@Path("v2/communities/{communityId}/publicizePhoto")
@Component
@Scope("prototype")
public class PublicizePhotoResource {
	
	private Gson gson = new Gson();
	
	@Autowired
	private PublicizePhotoDao publicizePhotoDao;
	/**
	 * 根据模块id 获取广告图
	 * @param communityId
	 * @param serviceId
	 * @return
	 */
	@GET
	@Path("{serviceId}")
	public String getPublicizePhotos(@PathParam("communityId") Integer communityId , @PathParam("serviceId") Integer serviceId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<PublicizePhotos> list = publicizePhotoDao.getPhotos(communityId, serviceId);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
}

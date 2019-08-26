package com.xj.resource;

import java.util.Iterator;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.xj.bean.ResultStatusBean;
import com.xj.bean.Services;
import com.xj.dao.ServiceCategoryDao;

@Path("/communities/serviceCategory")
@Component
@Scope("prototype")
public class ServiceCategoryInWebResource {
	
	private Gson gson = new Gson();
	
	@Autowired
	private ServiceCategoryDao serviceCategoryDao;
	
	@GET
	public String getCategoryList(){  // 获取所有服务内容
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		List<Services> list = null;
		try {
			list = serviceCategoryDao.getCategoryList();
			if(list != null){
				resultStatusBean.setStatus("yes");
				resultStatusBean.setInfo(list);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		return gson.toJson(resultStatusBean);
	}
	
	@POST
	public String addCategory(String json){  // 添加服务类别
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		List<Services> list = gson.fromJson(json, new TypeToken<List<Services>>(){}.getType());
		Iterator<Services> it = list.iterator();
		resultStatusBean.setStatus("no");
		
		while(it.hasNext()){
			try {
				serviceCategoryDao.addCategory(it.next());
				resultStatusBean.setStatus("yes");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resultStatusBean.setMessage("add category is error");
				resultStatusBean.setStatus("error");
				return gson.toJson(resultStatusBean);
				
			}
		}
		return gson.toJson(resultStatusBean);
	}
	
	@PUT
	@Path("{categoryId}")
	public String updateCategory(String json ,  @PathParam("categoryId") Integer categoryId){  //修改服务类别
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			Services serviceCategory = gson.fromJson(json, Services.class);
			serviceCategory.setServiceId(categoryId);
			boolean result = serviceCategoryDao.updateCategory(serviceCategory);
			if(result){
				resultStatusBean.setStatus("yes");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setMessage("param is error");
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		
		return gson.toJson(resultStatusBean);
	}
	
	@DELETE
	public String deleteCcategory(String json){ // 删除服务类别
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		List<Integer> list = gson.fromJson(json, new TypeToken<List<Integer>>(){}.getType());
		Iterator<Integer> it = list.iterator();
		resultStatusBean.setStatus("no");
		while(it.hasNext()){
			try {
				serviceCategoryDao.deleteCategory(it.next());
				resultStatusBean.setStatus("yes");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resultStatusBean.setStatus("error");
				resultStatusBean.setMessage("param or delete is error");
				return gson.toJson(resultStatusBean);
			}
		}
		return gson.toJson(resultStatusBean);
	}
}

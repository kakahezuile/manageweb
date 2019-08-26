package com.xj.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.xj.bean.Promotions;
import com.xj.bean.ResultStatusBean;
import com.xj.dao.PromotionsDao;
import com.xj.httpclient.utils.QiniuFileSystemUtil;

@Path("/communities/{communityId}/promotions")
@Component
@Scope("prototype")
public class PromotionsResource {
	
	private Gson gson = new Gson();
	
	private final String myUrl = "http://ltzmaxwell.qiniudn.com/";
	
	@Autowired
	private PromotionsDao promotionsDao;
	
	@Path("/upload")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA) 
    @Produces(MediaType.TEXT_HTML) 
	public void upLoad(@FormDataParam("promotionUploadFile") InputStream uploadFile 
			,  @FormDataParam("promotionUploadFile") FormDataContentDisposition fileDisposition
			, @FormDataParam("promotionServiceSelect") String sort , @PathParam("communityId") Integer communityId ){
		String fileFullName = fileDisposition.getFileName();
		//获取工程根目录
		String path = this.getClass().getClassLoader().getResource("/").getPath();
		Promotions promotions = new Promotions();
		promotions.setCommunityId(communityId);
		promotions.setCreateTime((int)(System.currentTimeMillis() / 1000l));
		promotions.setUpdateTime((int)(System.currentTimeMillis() / 1000l));
		promotions.setSort(sort);
	
		if(fileFullName!=null&&!fileFullName.equals("")){
			File file = writeToFile(uploadFile, path+fileFullName);
			try {
				QiniuFileSystemUtil.upload(file, fileFullName);
				promotions.setImgUrl(myUrl+fileFullName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			System.out.println(fileFullName);
			
		}
		try {
			promotionsDao.addPromotion(promotions);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@GET
	public String getPromotionService(@PathParam("communityId") Integer communityId , @QueryParam("q") String sort){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			 
			Promotions promotions = promotionsDao.getPromotion(communityId, sort);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(promotions); 
		} catch (Exception e) {
			// TODO: handle exception
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
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

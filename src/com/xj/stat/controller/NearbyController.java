package com.xj.stat.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.click.ClickAndModuleStatistics;
import com.xj.httpclient.utils.QiniuFileSystemUtil;
import com.xj.service.UserService;
import com.xj.stat.bean.parameter.nearby.CrazySales;
import com.xj.stat.bean.resource.nearby.CrazySalesImg;
import com.xj.stat.service.ClickService;
import com.xj.stat.service.NearbyService;
import com.xj.utils.DateUtils;

/**
 * @author lence
 * @date 2015年7月9日上午1:25:43
 */
@Component
@Path("/nearby")
@Scope("prototype")
public class NearbyController {

	@Autowired
	private NearbyService nearbyService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ClickService clickService;
	
	private Gson gson = new Gson();
	
	private final String myUrl = "http://7d9lcl.com2.z0.glb.qiniucdn.com/";
	
	@Path("/selectNearbyCrazySalesShop")
	@POST
	public String selectNearbyCrazySalesShop(String json) throws Exception {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setInfo(userService.selectNearbyCrazySalesShop(gson.fromJson(json, CrazySales.class)));
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@Path("/delectCrazySales")
	@POST
	public String delectCrazySales(String json) {
		nearbyService.delectCrazySales(gson.fromJson(json, CrazySales.class));
		
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setInfo("yes");
		return  gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@Path("/getCrazySales")
	@POST
	public String getCrazySales(String json) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setInfo(nearbyService.getCrazySales(gson.fromJson(json, CrazySales.class)));
		return  gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@Path("/upCrazySales")
	@POST
	public String upCrazySales(String json) {
		nearbyService.upCrazySales(gson.fromJson(json, CrazySalesImg.class));
		
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setInfo("yes");
		return  gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@Path("/upImgCrazySales")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA) 
	@Produces(MediaType.TEXT_HTML) 
	public String upImgCrazySales(@FormDataParam("crazySales_file_edit") InputStream uploadFile, @FormDataParam("crazySales_file_edit") FormDataContentDisposition fileDisposition,@QueryParam("crazySalesId") Integer crazySalesId) {
		CrazySalesImg crazySales = new CrazySalesImg();
		String fileFullName = fileDisposition.getFileName();

		// 获取工程根目录
		String path = this.getClass().getClassLoader().getResource("/").getPath();
		if (fileFullName != null&&!fileFullName.equals("")) {
			File file = writeToFile(uploadFile, path + fileFullName);
			try {
				QiniuFileSystemUtil.upload(file, fileFullName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			crazySales.setCrazySalesId(crazySalesId);
			crazySales.setImgUrl(myUrl + fileFullName);
			nearbyService.upImgCrazySales(crazySales);
		}
		
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setInfo("yes");
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@Path("/statCrazySales")
	@GET
	public String statCrazySales(@QueryParam("communityId") Integer communityId, @QueryParam("amount") Integer amount, @QueryParam("type") Integer type, @QueryParam("start") Integer start, @QueryParam("end") Integer end) throws Exception {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		
		ClickAndModuleStatistics statistics = new ClickAndModuleStatistics();
		if (null != amount && null != type) {
			Date date = DateUtils.getOffsetDate(amount, type);
			Integer startSecond = null;
			Integer endSecond = null;
			if (NearbyService.STATISTICS_DAY == type.intValue()) {
				startSecond = Long.valueOf(DateUtils.getDayBegin(date).getTime() / 1000L).intValue();
				endSecond = Long.valueOf(DateUtils.getDayEnd(date).getTime() / 1000L).intValue();
			} else if (NearbyService.STATISTICS_WEEK == type.intValue()) {
				startSecond = Long.valueOf(DateUtils.getWeekBegin(date).getTime() / 1000L).intValue();
				endSecond = Long.valueOf(DateUtils.getWeekEnd(date).getTime() / 1000L).intValue();
			} else if (NearbyService.STATISTICS_MONTH == type.intValue()) {
				startSecond = Long.valueOf(DateUtils.getMonthBegin(date).getTime() / 1000L).intValue();
				endSecond = Long.valueOf(DateUtils.getMonthEnd(date).getTime() / 1000L).intValue();
			}
			
			statistics.setModule(nearbyService.statCrazySales(communityId, startSecond, endSecond));
			statistics.setClick(clickService.getDayModuleStatistic(communityId, startSecond, endSecond));
		} else if (null != start && null != end) {
			start = Long.valueOf(DateUtils.getDayBegin(start * 1000L).getTime() / 1000L).intValue();
			end = Long.valueOf(DateUtils.getDayEnd(end * 1000L).getTime() / 1000L).intValue();
			
			statistics.setModule(nearbyService.statCrazySales(communityId, start, end));
			statistics.setClick(clickService.getDayModuleStatistic(communityId, start, end));
		} else {
			statistics.setModule(nearbyService.statCrazySales(communityId));
			statistics.setClick(clickService.getStatistic(communityId));
		}
		
		resultStatusBean.setInfo(statistics);
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	/**
	 * 图片上传的方法
	 */
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
		if (file.length() < 5) {
			file.delete();
			return null;
		}
		return file;
	}
}
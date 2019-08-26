package com.xj.resource.welfare;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.google.gson.Gson;
import com.sun.jersey.api.view.Viewable;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.FormDataParam;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.welfare.Welfare;
import com.xj.dao.welfare.WelfareDao;
import com.xj.httpclient.utils.QiniuFileSystemUtil;
import com.xj.jodis.JodisUtils;
import com.xj.service.welfare.WelfareService;
import com.xj.utils.DateUtils;

@Path("/communities/{communityId}/welfares")
@Component
@Scope("prototype")
public class WelfareResource {
	
	private static final String qiniuUrlRoot = "http://7d9lcl.com2.z0.glb.qiniucdn.com/";

	private Gson gson = new Gson();
	
	@Autowired
	private WelfareService welfareService;
	
	/**
	 * 保存福利信息
	 * @param request
	 * @param form 用于获取上传的多张福利内容图片
	 * @param communityId 小区ID
	 * @param welfareId 福利ID，编辑时不为空
	 * @param posterInputStream 海报图片输入流
	 * @param posterFileDisposition 海报图片描述信息
	 * @param posterValue 编辑时，如未重新上传海报，那么该值为之前上传的海报的图片地址，如果重新上传，这个值为空
	 * @param title 福利标题
	 * @param contentValues 编辑时，未删除的之前上传的福利内容图片地址
	 * @param charactervalues 人品值
	 * @param total 福利总数
	 * @param rule 福利规则说明
	 * @param phone 联系电话
	 * @param endTime 福利截止时间
	 * @param price 福利价格
	 * @param provideInstruction 福利发放说明
	 * @return
	 * @throws Exception
	 */
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_HTML)
	public Viewable saveWelfare(
			@Context HttpServletRequest request,
			FormDataMultiPart form,
			@PathParam("communityId") Integer communityId,
			@FormDataParam("welfareId") Integer welfareId,
			@FormDataParam("poster") InputStream posterInputStream,
			@FormDataParam("poster") FormDataContentDisposition posterFileDisposition,
			@FormDataParam("posterValue") String posterValue,
			@FormDataParam("title") String title,
			@FormDataParam("contentValue") List<FormDataBodyPart> contentValues,
			@FormDataParam("charactervalues") Integer charactervalues,
			@FormDataParam("total") Integer total,
			@FormDataParam("rule") String rule,
			@FormDataParam("phone") String phone,
			@FormDataParam("endTime") String endTime,
			@FormDataParam("price") BigDecimal price,
			@FormDataParam("provideInstruction") String provideInstruction) throws Exception {
		String message = "";
		String posterFile = StringUtils.isBlank(posterValue) ? uploadImage(posterInputStream, posterFileDisposition) : posterValue;
		if (StringUtils.isBlank(posterFile)) {
			message += "上传海报失败!";
		}
		
		StringBuilder contentFiles = new StringBuilder();
		List<FormDataBodyPart> contents = form.getFields("content");
		if (!CollectionUtils.isEmpty(contents)) {
			for (int i = 0; i < contents.size(); i++) {
				FormDataBodyPart formDataBodyPart = contents.get(i);
				InputStream contentInputStream = formDataBodyPart.getValueAs(InputStream.class);
				
				String contentFile = uploadImage(contentInputStream, null);
				if (null == contentFile) {
					message += "上传福利详情失败!";
				} else {
					contentFiles.append(contentFile).append(",");
				}
			}
		}
		if (!CollectionUtils.isEmpty(contentValues)) {
			for (int i = 0; i < contentValues.size(); i++) {
				String contentValue = contentValues.get(i).getValue();
				if (StringUtils.isNotBlank(contentValue)) {
					contentFiles.append(contentValue).append(",");
				}
			}
		}
		int length = contentFiles.length();
		if (length > 0) {
			contentFiles.deleteCharAt(length - 1);
		}
		
		Welfare welfare = new Welfare();
		boolean isUpdate = null != welfareId && welfareId.intValue() > 0;
		if (isUpdate) {
			welfare.setWelfareId(welfareId);
		} else {
			welfare.setStatus(WelfareDao.STATUS_OFFLINE);
		}
		
		int endTimeSecond = Long.valueOf(DateUtils.parseTimeStamp(endTime).getTime() / 1000L).intValue();
		welfare.setPoster(posterFile);
		welfare.setContent(contentFiles.length() > 0 ? contentFiles.toString() : null);
		welfare.setCommunityId(communityId);
		welfare.setTitle(title);
		welfare.setCharactervalues(charactervalues);
		welfare.setTotal(total);
		welfare.setRemain(total);
		welfare.setRule(rule);
		welfare.setPhone(phone);
		welfare.setEndTime(endTimeSecond);
		welfare.setPrice(price);
		welfare.setProvideInstruction(provideInstruction);
		
		if (isUpdate) {
			if (welfareService.updateWelfare(welfare)) {
				JodisUtils.set("welfare_" + welfareId + "_" + communityId, total + "_" + endTimeSecond + "_" + charactervalues);
			} else {
				message += "更新失败!";
			}
		} else {
			welfareId = welfareService.addWelfare(welfare);
			if (null != welfareId && welfareId.intValue() > 0) {
				JodisUtils.set("welfare_" + welfareId + "_" + communityId, total + "_" + endTimeSecond + "_" + charactervalues);
			} else {
				message += "新增失败!";
			}
		}
		
		String uri = null;
		if (StringUtils.isBlank(message)) {
			uri = "/jsp/operation/shop/welfare.jsp";
		} else {
			uri = "/jsp/operation/shop/add-welfare.jsp";
			
			request.setAttribute("message", message);
			request.setAttribute("welfareId", welfareId);
		}
		
		return new Viewable(uri, null);
	}
	
	/**
	 * 获取小区下所有的福利信息，结果按结束时间倒序，如果福利正在进行中，将位于第一个位置
	 * @param communityId
	 * @return
	 */
	@GET
	public String getWelfares(@PathParam("communityId") Integer communityId) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			resultStatusBean.setInfo(welfareService.getWelfares(communityId));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("获取福利列表失败:" + e.getMessage());
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 获取指定的福利信息
	 * @param communityId
	 * @param welfareId
	 * @return
	 */
	@GET
	@Path("/{welfareId}")
	public String getWelfare(@PathParam("welfareId") Integer welfareId) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			resultStatusBean.setInfo(welfareService.getWelfare(welfareId));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("获取福利失败:" + e.getMessage());
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 上架福利
	 * @param communityId
	 * @param welfareId
	 * @return
	 */
	@POST
	@Path("/online/{welfareId}")
	public String online(@PathParam("communityId") Integer communityId, @PathParam("welfareId") Integer welfareId) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			String message = welfareService.online(communityId, welfareId);
			resultStatusBean.setStatus("success".equals(message) ? "yes" : "no");
			resultStatusBean.setMessage(message);
		} catch (Exception e) {
			e.printStackTrace();
			
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("上架福利失败:" + e.getMessage());
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 下架福利
	 * @param welfareId
	 * @return
	 */
	@POST
	@Path("/offline/{welfareId}")
	public String offline(@PathParam("welfareId") Integer welfareId) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			resultStatusBean.setStatus(welfareService.offline(welfareId) ? "yes" : "no");
		} catch (Exception e) {
			e.printStackTrace();
			
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("下架福利失败:" + e.getMessage());
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 标记福利为成功
	 * @param welfareId
	 * @return
	 */
	@POST
	@Path("/succeed/{welfareId}")
	public String succeed(@PathParam("welfareId") Integer welfareId) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			resultStatusBean.setStatus(welfareService.succeed(welfareId) ? "yes" : "no");
		} catch (Exception e) {
			e.printStackTrace();
			
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("标记福利为成功出错:" + e.getMessage());
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 标记福利为失败
	 * @param welfareId
	 * @return
	 */
	@POST
	@Path("/fail/{welfareId}")
	public String fail(@PathParam("welfareId") Integer welfareId, @FormParam("reason") String reason) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			resultStatusBean.setStatus(welfareService.fail(welfareId, reason) ? "yes" : "no");
		} catch (Exception e) {
			e.printStackTrace();
			
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("标记福利为失败出错:" + e.getMessage());
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 删除福利
	 * @param welfareId
	 * @return
	 */
	@DELETE
	@Path("/{welfareId}")
	public String delete(@PathParam("welfareId") Integer welfareId) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			resultStatusBean.setStatus(welfareService.delete(welfareId) ? "yes" : "no");
		} catch (Exception e) {
			e.printStackTrace();
			
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("删除福利失败:" + e.getMessage());
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
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
}
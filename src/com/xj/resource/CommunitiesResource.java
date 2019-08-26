package com.xj.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.sun.jersey.api.view.Viewable;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.xj.bean.Communities;
import com.xj.bean.IdCount;
import com.xj.bean.PartnerPermission;
import com.xj.bean.PublicizePhotos;
import com.xj.bean.PublishPrice;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.Users;
import com.xj.bean.life.CommunitiesUser;
import com.xj.dao.CommunitiesDao;
import com.xj.httpclient.utils.QiniuFileSystemUtil;
import com.xj.service.CommunitiesService;
import com.xj.service.UserService;

@Component
@Scope("prototype")
@Path("/communities/summary")
public class CommunitiesResource {

	private Gson gson = new Gson();

	private final String myUrl = "http://7d9lcl.com2.z0.glb.qiniucdn.com/";

	@Autowired
	private CommunitiesDao communitiesDao;

	@Autowired
	private UserService userService;
	
	@Autowired
	private CommunitiesService communitiesService ;

	@GET
	@Path("/goods")
	public String getCommunityByCityId(@QueryParam("q") Integer cityId) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		if (cityId == null) {
			resultStatusBean.setMessage("城市id不能为空");
			resultStatusBean.setStatus("no");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}

		try {
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(communitiesDao
					.getCommunitiesByCityId(cityId));
		} catch (Exception e) {
			resultStatusBean.setMessage("查询小区信息时发生错误");
			resultStatusBean.setStatus("error");
			e.printStackTrace();
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	/**
	 * 获取社区信息
	 * 
	 * @param communityId
	 * @return
	 */
	@GET
	@Path("{communityId}")
	public String findCommunityById(
			@PathParam("communityId") Integer communityId) {
		ResultStatusBean result = new ResultStatusBean();
		try {
			result.setInfo(communitiesDao.getCommunities(communityId));
			result.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage("获取社区信息时报异常了");
			result.setStatus("no");
		}
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}

	/**
	 * 修改社区信息
	 */
	@PUT
	@Path("{communityId}")
	public String updateCommunity(String json,
			@PathParam("communityId") Integer communityId) {
		ResultStatusBean result = new ResultStatusBean();
		if (json != null && !"".equals(json)) {
			try {
				Communities communities = gson
						.fromJson(json, Communities.class);
				communities.setCommunityId(communityId);
				boolean resultBoolean = communitiesDao
						.updateCommunities(communities);
				if (resultBoolean) {
					result.setStatus("yes");
					result.setMessage("社区信息修改成功");
				} else {
					result.setStatus("no");
					result.setMessage("社区信息修改失败");
				}
			} catch (Exception e) {
				e.printStackTrace();
				result.setMessage("参数转换json时发生错误");
				result.setStatus("no");
			}
		} else {
			result.setMessage("参数不能为空");
			result.setStatus("no");
		}
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}

	@POST
	public String addCommunity(String json) {
		ResultStatusBean result = new ResultStatusBean(); 
		Communities communities = gson.fromJson(json, Communities.class);
		try {
			int id = communitiesService.addCommunities(communities);
			if (id > 0) {
				result.setMessage("添加社区成功");
				result.setResultId(id);
				result.setStatus("yes");
			} 
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage("添加社区失败");
			result.setStatus("no");
		}
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}

	/**
	 * 获取社区列表
	 */
	@GET
	@Path("/getListCommunity/{communityId}")
	public String getListCommunity(@PathParam("communityId") Integer communityId) {
		ResultStatusBean result = new ResultStatusBean();
		try {
			result.setInfo(communitiesDao.getListCommunity(communityId));
			result.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage("获取社区信息时报异常了");
			result.setStatus("no");
		}
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}

	/**
	 * 获取社区列表
	 */
	@GET
	@Path("/getListCommunityQ")
	public String getListCommunityQ() {
		ResultStatusBean result = new ResultStatusBean();
		try {
			result.setInfo(communitiesDao.getListCommunityQ());
			result.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage("获取社区信息时报异常了");
			result.setStatus("no");
		}
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}

	/**
	 * 检查小区更新消息条数
	 */
	@GET
	@Path("/getCommunityUpdate")
	public String getLifeCircleUpdate(
			@QueryParam("lastQuitTime") Integer lastQuitTime) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			List<IdCount> icList = communitiesDao
					.getCommunityUpdate(lastQuitTime);
			resultStatusBean.setInfo(icList);
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("no");
		}

		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	/**
	 * 获取社区列表
	 */
	@GET
	@Path("/getListCommunityEmobId/{emobId}")
	public String getListCommunityEmobId(@PathParam("emobId") String emobId) {
		ResultStatusBean result = new ResultStatusBean();
		try {
			result.setInfo(communitiesDao.getListCommunityEmobId(emobId));
			result.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage("获取社区信息时报异常了");
			result.setStatus("no");
		}
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}

	/**
	 * 添加合伙人管理的小区
	 */
	@POST
	@Path("/addPartnerPermission")
	public String addPartnerPermission(String json) {
		ResultStatusBean result = new ResultStatusBean();
		try {
			result.setInfo(communitiesDao.addPartnerPermission(gson.fromJson(
					json, PartnerPermission.class)));
			result.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage("获取社区信息时报异常了");
			result.setStatus("no");
		}
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}

	/**
	 * 添加合伙人管理的小区
	 */
	@POST
	@Path("/delPartnerPermission")
	public String delPartnerPermission(String json) {
		ResultStatusBean result = new ResultStatusBean();
		try {
			result.setInfo(communitiesDao
					.delPartnerPermission(
							gson.fromJson(json, PartnerPermission.class)
									.getEmobId(), 0));
			result.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage("获取社区信息时报异常了");
			result.setStatus("no");
		}
		return gson.toJson(result).replace("\"null\"", "\"\"");
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

	@Path("/addPublicizePhotos/{communityId}")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_HTML)
	public String addPublicizePhotos(
			@PathParam("communityId") Integer communityId,
			@FormDataParam("shops_file") InputStream uploadFile,
			@FormDataParam("shops_file") FormDataContentDisposition fileDisposition) {
		PublicizePhotos publicizePhotos = new PublicizePhotos();
		String fileFullName = fileDisposition.getFileName();

		// 获取工程根目录
		String path = this.getClass().getClassLoader().getResource("/")
				.getPath();

		if (fileFullName != null && !fileFullName.equals("")) {
			File file = writeToFile(uploadFile, path + fileFullName);
			try {
				QiniuFileSystemUtil.upload(file, fileFullName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			publicizePhotos.setImgUrl(myUrl + fileFullName);
		}

		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			publicizePhotos.setAppVersion("v2");
			publicizePhotos.setCommunityId(communityId);
			publicizePhotos
					.setCreateTime((int) (System.currentTimeMillis() / 1000l));
			publicizePhotos.setPhotoModule("top");
			publicizePhotos.setRole("owner");
			publicizePhotos
					.setUpdateTime((int) (System.currentTimeMillis() / 1000l));
			publicizePhotos.setServiceId(16);

			boolean resultBoolean = communitiesDao
					.addPublicizePhotos(publicizePhotos);
			if (resultBoolean) {
				resultStatusBean.setStatus("yes");
			} else {
				resultStatusBean.setStatus("no");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setMessage("error");
			resultStatusBean.setStatus("error");
		}
		return gson.toJson(resultStatusBean);
	}

	@Path("/getPublicizePhotos/{communityId}")
	@POST
	public String getPublicizePhotos(
			@PathParam("communityId") Integer communityId) {
		ResultStatusBean result = new ResultStatusBean();
		try {
			result.setInfo(communitiesDao.getPublicizePhotos(communityId));
			result.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus("no");
		}
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}

	@Path("/communitiesSequence")
	@POST
	public String communitiesSequence() {
		ResultStatusBean result = new ResultStatusBean();
		try {
			result.setInfo(communitiesDao.communitiesSequence());
			result.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus("no");
		}
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}

	@Path("/communitiesUser")
	@GET
	public String communitiesUser() {
		ResultStatusBean result = new ResultStatusBean();
		try {
			List<Communities> listCommunities = communitiesDao.getListCommunityQ();
			List<CommunitiesUser> list = new ArrayList<CommunitiesUser>(listCommunities.size());
			for (Communities communities : listCommunities) {
				CommunitiesUser communitiesUser = new CommunitiesUser();
				List<Users> listUser = userService.getListTryOutUser(communities.getCommunityId());
				communitiesUser.setCommunities(communities);
				communitiesUser.setListUser(listUser);
				list.add(communitiesUser);
			}
			result.setStatus("yes");
			result.setInfo(list);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus("no");
		}
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}

	@GET
	@Path("/getListTryOutUser")
	public String getListTryOutUser(@QueryParam("communityId")  Integer communityId) {
		ResultStatusBean result = new ResultStatusBean();
		try {
			List<Users> listUser = userService.getListTryOutUser(communityId);
			result.setInfo(listUser);
			result.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus("no");
		}
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}

	@Path("/addPublishPrice")
	@POST
	public String addPublishPrice(String json) {
		ResultStatusBean result = new ResultStatusBean();
		try {
			PublishPrice publishPrice = gson.fromJson(json, PublishPrice.class);
			publishPrice.setPublishPriceId((int) (new Date().getTime() / 1000));
			publishPrice.setStatus("ongoing");

			communitiesDao.addPublishPrice(publishPrice);
			result.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus("no");
		}
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}

	@Path("/getPublishPrice/{communityId}")
	@POST
	public String getPublishPrice(@PathParam("communityId") Integer communityId) {
		ResultStatusBean result = new ResultStatusBean();
		try {
			result.setStatus("yes");
			result.setInfo(communitiesDao.getPublishPrice(communityId));
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus("no");
		}
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}

	@Path("city/{cityId}/cityCommunities")
	@GET
	public String getCityCommunities(@PathParam("cityId") Integer cityId) {
		ResultStatusBean result = new ResultStatusBean();
		try {
			result.setInfo(communitiesDao.getCityCommunities(cityId));
			result.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage("获取小区信息时报异常了");
			result.setStatus("no");
		}
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 获取首页图片
	 * @return
	 */
	@GET
	@Path("/homepic")
	public String getHomePic(){
		ResultStatusBean result = new ResultStatusBean();
		String homePic = communitiesService.getHomePic(0);
		if(StringUtils.isNotBlank(homePic)){
			result.setStatus("yes");
			result.setInfo(homePic);
		}else{
			result.setStatus("no");
		}
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 添加或修改首页图片
	 * @return
	 */
	@POST
	@Path("/homepic")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_HTML)
	public Viewable updateHomePic(@FormDataParam("poster") InputStream posterInputStream,
								  @FormDataParam("posterValue") String posterValue,
								  @FormDataParam("poster") FormDataContentDisposition posterFileDisposition){
		try {
			String posterFile = StringUtils.isBlank(posterValue) ? uploadImage(posterInputStream, posterFileDisposition) : posterValue;
			if (StringUtils.isBlank(posterFile)) {
				throw new RuntimeException();
			}
			communitiesService.saveOrUpdateHomePic(0,posterFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  new Viewable("/jsp/admin/home_pic.jsp", null);
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
			
			return myUrl + fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
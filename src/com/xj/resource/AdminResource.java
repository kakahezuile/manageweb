package com.xj.resource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.xj.bean.Admin;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.TryOut;
import com.xj.bean.Users;
import com.xj.dao.AdminDao;
import com.xj.dao.FacilitiesDao;
import com.xj.httpclient.apidemo.EasemobIMUsers;
import com.xj.utils.MD5Util;

/**
 * 管理员模块
 */
@Component
@Scope("prototype")
@Path("/communities/{communityId}/admin")
public class AdminResource {

	private Gson gson = new Gson();

	@Autowired
	private AdminDao adminDao;

	@Autowired
	private FacilitiesDao facilitiesDao;

	/**
	 * 添加管理员
	 * 
	 * @param json
	 * @param communityId
	 * @return
	 */
	@POST
	public String regist(String json, @PathParam("communityId") Integer communityId) {
		ResultStatusBean resultBean = new ResultStatusBean();
		try {
			Admin admin = gson.fromJson(json, Admin.class);
			if (communityId != null) {
				admin.setCommunityId(communityId);
			}
			Admin adminTemp = adminDao.getAdmin(admin.getUsername());
			if (adminTemp != null) {
				resultBean.setMessage("当前管理员已存在");
				resultBean.setStatus("no");
				return gson.toJson(resultBean).replace("\"null\"", "\"\"");
			}
			admin.setSalt(MD5Util.getStr());
			admin.setEmobId(MD5Util.getMD5Str(admin.getUsername()));
			String passWord = admin.getPassword();
			admin.setPassword(MD5Util.getMD5Str(passWord + admin.getSalt()));
			try {
				EasemobIMUsers.addUser(admin.getEmobId(), passWord);
				admin.setAdminStatus("other");
				Integer resultId = adminDao.insert(admin);
				resultBean.setResultId(resultId);
				resultBean.setStatus("yes");
			} catch (Exception e) {
				e.printStackTrace();
				resultBean.setMessage("insert resource is error");
				resultBean.setStatus("no");
				return gson.toJson(resultBean).replace("\"null\"", "\"\"");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultBean.setMessage("paremeter is error");
			resultBean.setStatus("error");
			return gson.toJson(resultBean);
		}
		return gson.toJson(resultBean).replace("\"null\"", "\"\"");
	}

	@Path("/user")
	@POST
	public String registUser(String json, @PathParam("communityId") Integer communityId) {
		ResultStatusBean resultBean = new ResultStatusBean();
		try {
			Users user = gson.fromJson(json, Users.class);
			if (communityId != null) {
				user.setCommunityId(communityId);
			}
			Users userTemp = adminDao.getUser(user.getUsername());
			if (userTemp != null) {
				resultBean.setMessage("当前管理员已存在");
				resultBean.setStatus("no");
				return gson.toJson(resultBean).replace("\"null\"", "\"\"");
			}
			user.setSalt(MD5Util.getStr());
			int time = (int) (System.currentTimeMillis() / 1000l);
			user.setCreateTime(time);
			user.setEquipment("android");
			user.setUserFloor("1号楼");
			user.setUserUnit("1单元");
			user.setRoom("101");
			user.setCharacterValues(0);
			user.setEmobId(MD5Util.getMD5Str(user.getUsername()));
			String passWord = user.getPassword();
			String pr = MD5Util.getMD5Str("&bang#bang@" + passWord);
			user.setPassword(MD5Util.getMD5Str(passWord + user.getSalt()));
			try {
				EasemobIMUsers.addUser(user.getEmobId(), pr);
				user.setRole("owner");
				Integer resultId = adminDao.insertUser(user);

				TryOut to = new TryOut();
				to.setCommunityId(communityId);
				to.setEmobId(user.getEmobId());
				to.setUsersId(resultId);
				adminDao.insertTryOut(to);
				resultBean.setResultId(resultId);
				resultBean.setStatus("yes");
			} catch (Exception e) {
				e.printStackTrace();
				resultBean.setMessage("insert resource is error");
				resultBean.setStatus("no");
				return gson.toJson(resultBean).replace("\"null\"", "\"\"");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultBean.setMessage("paremeter is error");
			resultBean.setStatus("error");
			return gson.toJson(resultBean);
		}
		return gson.toJson(resultBean).replace("\"null\"", "\"\"");
	}

	/**
	 * 管理员登陆
	 * 
	 * @param json
	 * @param request
	 * @return
	 */
	@POST
	@Path("{username}")
	public String login(String json, @Context HttpServletRequest request) {
		ResultStatusBean resultBean = new ResultStatusBean();
		try {
			Admin admin = gson.fromJson(json, Admin.class);
			String userName = admin.getUsername();
			String passWord = admin.getPassword();
			Admin adminResult = null;
			try {
				adminResult = adminDao.getAdmin(userName);
			} catch (Exception e) {
				e.printStackTrace();
				resultBean.setMessage("login is error");
				resultBean.setStatus("no");
				return gson.toJson(resultBean);
			}
			if (adminResult != null) {
				String pwd = adminResult.getPassword();
				String md5Pwd = MD5Util.getMD5Str(passWord + adminResult.getSalt());
				if (pwd.equals(md5Pwd)) {
					resultBean.setStatus("yes");
					resultBean.setInfo(adminResult);
					HttpSession session = request.getSession();
					session.setMaxInactiveInterval(7200);
					session.setAttribute("adminObject", adminResult);
				} else {
					resultBean.setStatus("no");
					resultBean.setMessage("密码错误");
				}
			} else {
				resultBean.setMessage("用户不存在");
				resultBean.setStatus("no");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultBean.setMessage("json parameter is error");
			resultBean.setStatus("error");
			return gson.toJson(resultBean);
		}
		return gson.toJson(resultBean);
	}

	/**
	 * 修改管理员信息
	 * 
	 * @param json
	 * @param emobId
	 * @return
	 */
	@PUT
	@Path("/updateEmobId/{emobId}")
	public String updateNickname(String json, @PathParam("emobId") String emobId) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			Admin admin = gson.fromJson(json, Admin.class);
			ObjectNode objectNode = EasemobIMUsers.updateIMUser(emobId, admin.getNickname());
			if (objectNode.get("statusCode") != null) {
				Integer code = objectNode.get("statusCode").asInt();
				if (code != null && code == 200) {
					if (objectNode.get("entities") != null) {
						ArrayNode arrayNode = (ArrayNode) objectNode.get("entities");
						if (arrayNode.size() > 0) {
							JsonNode jsonNode = arrayNode.get(0);
							if (jsonNode.get("activated").asBoolean()) {
								adminDao.updateAdmin(admin);
								resultStatusBean.setStatus("yes");
								resultStatusBean.setMessage("修改成功");
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(resultStatusBean);
	}

	@PUT
	public String updateAdmin(String json) {
		ResultStatusBean resultBean = new ResultStatusBean();
		try {
			Admin admin = gson.fromJson(json, Admin.class);
			Admin adminResult = adminDao.getAdmin(admin.getUsername());
			String passWord = admin.getPassword();
			String pwd = adminResult.getPassword();
			String md5Pwd = MD5Util.getMD5Str(passWord + adminResult.getSalt());
			if (pwd.equals(md5Pwd)) {
				resultBean.setStatus("yes");
			} else {
				resultBean.setStatus("no");
				resultBean.setMessage("密码错误");
				return gson.toJson(resultBean);
			}
			String xPassWord = admin.getxPassWord();
			EasemobIMUsers.updateUserPass(adminResult.getEmobId(), xPassWord);

			admin.setPassword(MD5Util.getMD5Str(xPassWord + adminResult.getSalt()));
			Integer result = adminDao.update(admin);
			if (result != null) {
				EasemobIMUsers.updateUserPass(admin.getEmobId(), passWord);
				resultBean.setStatus("yes");
			} else {
				resultBean.setMessage("update error");
				resultBean.setStatus("no");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultBean.setMessage("parameter is error ");
			resultBean.setStatus("error");
			return gson.toJson(resultBean);
		}
		return gson.toJson(resultBean);
	}

	@GET
	public String getAdminList() {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<Admin> list = adminDao.getAdminList();
			resultStatusBean.setInfo(list);
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(resultStatusBean);
	}

	@GET
	@Path("/staffs")
	public String getAminList(@QueryParam("role") String role, @QueryParam("status") String status, @QueryParam("sort") String sort, @PathParam("communityId") Integer communityId) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		String type = "";
		if ("complaints".equals(status)) {
			if ("2".equals(sort)) { // 运营投诉
				type = "1";
			} else { // 物业投诉
				type = "2";
			}
		} else if ("service".equals(status)) {
			if ("user".equals(role)) { // 物业客服
				type = "3";
			} else if ("shop".equals(role)) { // 运营客服
				type = "4";
			}
		}

		try {
			List<Admin> list = adminDao.getAdminList(communityId, type);
			if (list != null && list.size() > 0) {
				Admin admin = list.get(0);
				String startTime = admin.getStartTime();
				String endTime = admin.getEndTime();
				SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
				String time = s.format(new Date());
				startTime = time + " " + startTime;
				endTime = time + " " + endTime;
				Long start = ss.parse(startTime).getTime();
				Long end = ss.parse(endTime).getTime();
				Long thisTime = System.currentTimeMillis();
				if (thisTime > start && thisTime < end) {
					resultStatusBean.setStatus("yes");
					resultStatusBean.setInfo(list.get(0));
				} else {
					resultStatusBean.setStatus("yes");
					resultStatusBean.setMessage(list.get(0).getStartTime() + "-" + list.get(0).getEndTime());
					resultStatusBean.setInfo(list.get(0));
				}
			} else {
				resultStatusBean.setStatus("no");
				resultStatusBean.setMessage("没有找到对应类型的客服");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	@GET
	@Path("/{adminId}/Statistics")
	public String getAdminStatistics(@PathParam("adminId") Integer adminId) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			Integer result = facilitiesDao.getNumberByAdminId(adminId, null, null);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(result);
		} catch (Exception e) {
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("select is error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	@POST
	@Path("/setAdminAsKefu")
	public String setAdminAsKefu(String json) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			if (adminDao.updateAdminAsKefu(gson.fromJson(json, Admin.class))) {
				resultStatusBean.setStatus("yes");
			} else {
				resultStatusBean.setStatus("no");
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage(e.getMessage());
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 判断admin在对应小区是否是唯一的客服
	 * @param adminId
	 * @param ruleId
	 * @return
	 */
	@GET
	@Path("/{adminId}/isUniqueKf")
	public String isUniqueKf(@PathParam("communityId") Integer communityId, @PathParam("adminId") Integer adminId, @QueryParam("adminStatus") String adminStatus) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			resultStatusBean.setInfo(adminDao.isUniqueKefu(communityId, adminId, adminStatus));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage(e.getMessage());
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
}
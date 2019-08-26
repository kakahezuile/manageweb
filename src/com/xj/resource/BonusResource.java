package com.xj.resource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.Bonus;
import com.xj.bean.BonusAndService;
import com.xj.bean.BonusStatistics;
import com.xj.bean.BonusUser;
import com.xj.bean.BonusUserOrder;
import com.xj.bean.ExtNode;
import com.xj.bean.Page;
import com.xj.bean.PushTask;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.Shops;
import com.xj.bean.StatisticsBonusUser;
import com.xj.bean.UserBonus;
import com.xj.bean.Users;
import com.xj.dao.BonusDao;
import com.xj.dao.ShopsDao;
import com.xj.dao.UserBonusDao;
import com.xj.service.UserService;
import com.xj.thread.PushThread;

@Path("/communities/{communityId}/bonuses")
@Component
@Scope("prototype")
public class BonusResource {

	private Gson gson = new Gson();

	@Autowired
	private BonusDao bonusDao;

	@Autowired
	private UserBonusDao userBonusDao;

	@Autowired
	private UserService userService;

	@Autowired
	private ShopsDao shopsDao;

	/**
	 * 添加帮帮劵
	 */
	// @FormDataParam("bangbang_file") InputStream uploadFile ,
	// @FormDataParam("bangbang_file") FormDataContentDisposition
	// fileDisposition ,
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_HTML)
	public String addBonus(@PathParam("communityId") Integer communityId,
			@QueryParam("bangbang_name") String bangBangName,
			@QueryParam("bangbang_par") String bangBangPar,
			@QueryParam("bangbang_check_value") List<String> bangBangChek,
			@QueryParam("bonus_type") Integer bonus_type,
			@QueryParam("bangbang_detail") String bangBangDetail,
			@QueryParam("bangbang_username") String userName,
			@QueryParam("bangbang_password") String passWord,
			@QueryParam("bonusR") String bonusR,
			@QueryParam("bonusG") String bonusG,
			@QueryParam("bonusB") String bonusB,
			@QueryParam("bangbang_adminId") Integer adminId) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		// String fileFullName = fileDisposition.getFileName();
		try {
			Bonus bonus = new Bonus();
			bonus.setBonusName(bangBangName);
			bonus.setBonusPar(bangBangPar);
			bonus.setBonusDetail(bangBangDetail);
			bonus.setBonusR(bonusR);
			bonus.setBonusG(bonusG);
			bonus.setBonusB(bonusB);
			bonus.setCreateTime((int) (System.currentTimeMillis() / 10001));
			Integer bonusType = -1;
			if (bangBangChek == null || bangBangChek.equals("")) {
				bonusType = -1;
			} else {
				// String array[] = bangBangChek.split(",");

				// int len = array.length;
				int len = bangBangChek.size();

				for (int i = 0; i < len; i++) {
					if (bangBangChek.get(i) != null
							&& !"".equals(bangBangChek.get(i))) {
						try {
							bonusType = Integer.parseInt(bangBangChek.get(i));
							break;
						} catch (Exception e) {
							// TODO: handle exception
						}
					}

				}
			}
			bonus.setBonusType(bonusType);

			Integer resultId = bonusDao.addBonus(bonus);
			if (resultId > 0) {

				resultStatusBean.setStatus("yes");
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");

		}
		return gson.toJson(resultStatusBean);
	}

	/**
	 * 发送帮帮卷通知
	 */
	@Path("/userBonus")
	@POST
	public String addUserBonus(@PathParam("communityId") Integer communityId,
			String json, @QueryParam("bangbang_password") String passWord) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			UserBonus userBonus = gson.fromJson(json, UserBonus.class);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd");
			Date startTime = simpleDateFormat
					.parse(userBonus.getStartTimeStr());
			Date endTime = simpleDateFormat.parse(userBonus.getExpireTimeStr());

			String startTimeStr = userBonus.getStartTimeStr();
			String expireTimeStr = userBonus.getExpireTimeStr();
			Integer startTimeInt = (int) (startTime.getTime() / 1000l);
			Integer endTimeInt = (int) (endTime.getTime() / 1000l);
			Integer createTime = (int) (System.currentTimeMillis() / 1000l);
			String messageValue = userBonus.getMessageValue();
			Integer messageIsSend = userBonus.getMessageIsSend();

			Integer type = userBonus.getBonusType();
			List<Users> list = new ArrayList<Users>();
			if (userBonus.getMessageIsSend() == 1) {
				list = userService.getList(communityId, "owner");
			} else {
				String[] Array = userBonus.getPhoneList().split(",");
				for (String phone : Array) {
					Users users = userService.getUserByPhone(phone);
					list.add(users);
				}

			}

			Iterator<Users> it = list.iterator();

			List<UserBonus> ubList = new ArrayList<UserBonus>();
			while (it.hasNext()) {
				UserBonus ub = new UserBonus();
				ub.setBonusId(userBonus.getBonusId());
				ub.setCreateTime(createTime);
				ub.setMessageIsSend(messageIsSend);
				ub.setMessageValue(messageValue);
				ub.setStartTime(startTimeInt);
				ub.setExpireTime(endTimeInt);
				ub.setExpireTimeStr(expireTimeStr);
				ub.setStartTimeStr(startTimeStr);
				ub.setStartTimeStr(startTimeStr);
				ub.setBonusStatus("unused");
				ub.setBonusType(type);
				// ub.setBonusType(messageIsSend);
				Users users = it.next();
				ub.setUserId(users.getUserId());
				ubList.add(ub);

			}
			int resultId = userBonusDao.addThingUserBonus(ubList);
			if (resultId == -1) {
				resultStatusBean.setStatus("no");
			} else {
				resultStatusBean.setStatus("yes");
				// 发送消息
				ExtNode ext = new ExtNode();
				ext.setContent(userBonus.getMessageValue());
				ext.setNickname("通知");
				ext.setTitle("通知公告");
				ext.setCMD_CODE(106);
				ext.setMessageId(System.currentTimeMillis());
				ext.setTimestamp(System.currentTimeMillis() / 1000l);

				try {
					PushTask pushTask = new PushTask();
					pushTask.setTimestamp((int) (System.currentTimeMillis() / 1000l));
					// pushTask.setPushStr(result);
					pushTask.setScene("发放帮帮卷");
					pushTask.setServiceName("bonus");
					// pushTaskDao.addPushTask(pushTask);
					ExecutorService executorService = Executors .newSingleThreadExecutor();
					executorService.execute(new PushThread(ext, list, userBonus .getMessageValue()));
					executorService.shutdown();

				} catch (Exception e) {
					// TODO: handle exception
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean);
	}

	@GET
	public String getBonusList2(@QueryParam("emobIdShop") String emobIdShop,
			@QueryParam("emobIdUser") String emobIdUser) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		if (emobIdShop != null && emobIdUser != null) {
			try {
				Shops shops = shopsDao.getShopsByEmobId(emobIdShop);
				String sort = shops.getSort();
				List<BonusAndService> list = userBonusDao.getBonusAndService(
						emobIdUser, sort);
				resultStatusBean.setStatus("yes");
				resultStatusBean.setInfo(list);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				resultStatusBean.setStatus("error");
				return gson.toJson(resultStatusBean);
			}
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}

		try {
			List<Bonus> list = bonusDao.getBonusList();
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	@GET
	@Path("/getUserCount")
	public String getUserCount() {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			Integer count = userService.getUserCount();
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(count);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	@GET
	@Path("/users/{emobIdUser}")
	public String getBonusList(@PathParam("emobIdUser") String emobIdUser) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<BonusAndService> list = userBonusDao
					.getBonusAndService(emobIdUser);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	/**
	 * 获取帮帮卷
	 */
	@GET
	@Path("/shops/{emobIdShop}/users/{emobIdUser}")
	public String BonusByShopId(@PathParam("emobIdUser") String emobIdUser,
			@PathParam("emobIdShop") String emobIdShop) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			Shops shops = shopsDao.getShopsByEmobId(emobIdShop);
			String sort = shops.getSort();
			List<BonusAndService> list = userBonusDao.getBonusAndService(
					emobIdUser, sort);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	/**
	 * 更新帮帮劵
	 */

	@PUT
	@Path("{userBonusId}")
	public String updateUserBonus(
			@PathParam("userBonusId") Integer userBonusId, String json) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			UserBonus userBonus = gson.fromJson(json, UserBonus.class);
			userBonus.setUserBonusId(userBonusId);
			boolean result = userBonusDao.updateUserBonus(userBonus);
			if (result) {
				resultStatusBean.setStatus("yes");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	/**
	 * 帮帮卷 按发放时间 统计
	 */
	@GET
	@Path("/statisticsTimeBonus")
	public String statisticsTimeBonus(@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize,
			@QueryParam("bonusName") String bonusName,
			@QueryParam("bonusPar") String bonusPar,@QueryParam("startTime") String startTime,@QueryParam("endTime") String endTime) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		  Integer startTimeInt = 0;  
		  Integer endTimeInt =0;  
		  if(!startTime.equals("")){
			  try {  
		    	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		           Date date = sdf.parse(startTime);  
		           startTimeInt = (int) (date.getTime()/1000);  
		           
		           
		           SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");  
		           Date date2 = sdf2.parse(endTime);
		           endTimeInt = (int) (date2.getTime()/1000);  
		       }  
		       catch (Exception e) {  
		           e.printStackTrace();  
		       } 
			  
			  
		  }
	       
		
		
		try {

			resultStatusBean.setStatus("yes");
			Page<BonusStatistics> page = userBonusDao.statisticsTimeBonus(
					pageNum, pageSize,bonusName,bonusPar,startTimeInt,endTimeInt);
			resultStatusBean.setInfo(page);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}

		return gson.toJson(resultStatusBean);
	}

	/**
	 * 帮帮卷发放对象
	 */
	@GET
	@Path("/getBonusUser")
	public String getBonusUser(@QueryParam("createTime") Integer createTime) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {

			resultStatusBean.setStatus("yes");
			List<BonusUserOrder> page = userBonusDao.getBonusUser(createTime);
			resultStatusBean.setInfo(page);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}

		return gson.toJson(resultStatusBean);
	}

	/**
	 * 帮帮使用明细
	 */
	@GET
	@Path("/getOrdersBonus")
	public String getOrdersBonus(@QueryParam("serial") String serial) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {

			resultStatusBean.setStatus("yes");
			List<BonusUser> page = userBonusDao.getOrdersBonus(serial);
			resultStatusBean.setInfo(page);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}

		return gson.toJson(resultStatusBean);
	}
	/**
	 *统计帮帮券
	 */
	@GET
	@Path("/statisticsBonusUser")
	public String statisticsBonusUser(@QueryParam("createTime") Integer createTime) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			
			resultStatusBean.setStatus("yes");
			StatisticsBonusUser page = userBonusDao.statisticsBonusUser(createTime);
			resultStatusBean.setInfo(page);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		
		return gson.toJson(resultStatusBean);
	}

}

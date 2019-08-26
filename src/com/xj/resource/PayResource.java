package com.xj.resource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.ExtNode;
import com.xj.bean.Page;
import com.xj.bean.PayAmount;
import com.xj.bean.PayAndUser;
import com.xj.bean.PayMessage;
import com.xj.bean.PayTop;
import com.xj.bean.PayTurnover;
import com.xj.bean.PushTask;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.Users;
import com.xj.bean.UsersApp;
import com.xj.bean.XjPay;
import com.xj.bean.XjStandard;
import com.xj.dao.PayMessageDao;
import com.xj.dao.XjPayDao;
import com.xj.dao.XjStandardDao;
import com.xj.service.UserService;
import com.xj.thread.PushThread;

@Path("/communities/{communityId}/users/{emobId}/payments")
@Component
@Scope("prototype")
public class PayResource {

	private Gson gson = new Gson();

	@Autowired
	private XjPayDao xjPayDao;

	@Autowired
	private PayMessageDao payMessageDao;

	@Autowired
	private UserService userService;
	
	@Autowired
	private XjStandardDao xjStandardDao;

	@PUT
	@Path("/{payId}")
	public String updatePay(String json, @PathParam("payId") Integer payId,
			@PathParam("communityId") Integer communityId) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			PayAndUser payAndUser = gson.fromJson(json, PayAndUser.class);
			XjPay xjPay = new XjPay();
			xjPay.setPayStatus(payAndUser.getPayStatus());
			xjPay.setPayId(payId);
			if (payAndUser.getPayStatus() == 1) {
				xjPay.setHandleTime((int) (System.currentTimeMillis() / 1000l));
			} else if (payAndUser.getPayStatus() == 2) {
				xjPay.setNextPay(payAndUser.getNextPay());
				xjPay.setLastPay(payAndUser.getLastPay());
				xjPay.setCompleteTime((int) (System.currentTimeMillis() / 1000l));
			}
			boolean result = xjPayDao.updateXjPay(xjPay);
			if (result) {
				resultStatusBean.setStatus("yes");
				String emobId = payAndUser.getEmobId();
				if (emobId != null && !"null".equals(emobId)) {
					// 发送消息
					Integer status = payAndUser.getPayStatus();
					PayMessage payMessage = payMessageDao
							.getPayMessageByType(status);
					ExtNode ext = new ExtNode();
					// ext.setTitle("通知");
					// ext.setContent(payMessage.getMessageValue());
					XjPay xjPay2 = xjPayDao.getXjPay(payId);
					Integer payType = xjPay2.getPayType();
					String title = "";
					Integer CMD_CODE = 100;
					String sort = "";
					if (payType == 1) { // 电费
						sort = "1";
						title = "电费通知";
						CMD_CODE = 101;
					} else if (payType == 2) { // 水费
						title = "水费通知";
						CMD_CODE = 103;
					} else if (payType == 3) { // 燃气费
						title = "燃气费通知";
						CMD_CODE = 102;
					} else if (payType == 4) { // 宽带费
						title = "宽带费通知";
						CMD_CODE = 105;
					} else {
						title = "普通通知";

					}
					CMD_CODE = 100;

					ext.setTitle(title);
					ext.setCMD_CODE(CMD_CODE);
					Users u = new Users();
					List<Users> list = new ArrayList<Users>();
					u.setEmobId(emobId);
					list.add(u);

					XjPay xj= xjPayDao.getXjPay(payId);
					XjStandard xjStandard = xjStandardDao.getXjStandard(
							communityId, sort);
					Calendar c = Calendar.getInstance();// 可以对每个时间域单独修改

					int month = c.get(Calendar.MONTH) + 1;
					int date = c.get(Calendar.DATE);
					int hour = c.get(Calendar.HOUR_OF_DAY);
					int minute = c.get(Calendar.MINUTE);
					String text = payMessage.getMessageValue().replace("MM",
							month + "");
					String last = payAndUser.getLastPay();
					String next = payAndUser.getNextPay();

					double n1 = 0;
					double n2 = 0;
					try {
						n1 = Double.parseDouble(next);
						n2 = Double.parseDouble(last);
						double n = n1 - n2;
						text = text.replace("N0", n + "");
						text = text.replace("N1", last);
						text = text.replace("N2", next);
					} catch (Exception e) {

					}

					double price = 0;
					double paySum = 0;
					try {
						price = Double.parseDouble(xjStandard.getPrice());
						paySum = Double.parseDouble(xj.getPaySum());
						double nn = paySum / price;
						
						text = text.replace("NNN", nn + "");
					} catch (Exception e) {
						text = text.replace("NNN",  "0");
					}
					if( xj.getPaySum()!=null){
						text = text.replace("YYY", xj.getPaySum());
					}
					
					UsersApp ua = userService.getUserByEmobId(emobId);
					String floor = ua.getUserFloor();
					String unit = ua.getUserUnit();
					String room = ua.getRoom();

					text = text.replace("dd", date + "");
					text = text.replaceAll("HH", hour + "");
					text = text.replaceAll("mm", minute + "");

					text = text.replace("FFF", floor);
					text = text.replace("UUU", unit);
					text = text.replace("RRR", room);
					ext.setContent(text);
//					EasemobChatMessage.testChatMessages(list, EasemobIMUsers.getAminEmobId(), text,
//							ext);
//					String resultStr = pushtoList.pushtoTransmissionList1000(list, gson.toJson(ext),title);
					try {
						PushTask pushTask = new PushTask();
						pushTask.setTimestamp((int)(System.currentTimeMillis() / 1000l));
//						pushTask.setPushStr(resultStr);
						pushTask.setScene("快递通知");
						pushTask.setServiceName("express");
//						pushTaskDao.addPushTask(pushTask);
						ExecutorService executorService = Executors.newSingleThreadExecutor();
						executorService.execute(new PushThread(ext, list, title));
						executorService.shutdown();
						
					} catch (Exception e) {
						// TODO: handle exception
					}
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

	public boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
//			System.out.println(str.charAt(i));
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	@GET
	public String getPayList(@QueryParam("type") Integer type,
			@QueryParam("status") Integer status,
			@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			Page<PayAndUser> page = xjPayDao.getPayList(type, status, pageNum,
					pageSize, 10);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(page);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean);
	}

	@GET
	@Path("/getTop")
	public String getPayTop(@QueryParam("status") Integer status,
			@QueryParam("type") Integer type) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			PayTop payTop = xjPayDao.getPayTop(type, status);
			if (payTop != null) {
				resultStatusBean.setStatus("yes");
				resultStatusBean.setInfo(payTop);
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
	@Path("/getPayMessage")
	public String getPayMessge() {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			resultStatusBean.setStatus("yes");
			List<PayMessage> list = payMessageDao.getPayMessageList();
			resultStatusBean.setInfo(list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean);
	}

	@POST
	@Path("/addPayMessage")
	public String addPayMessage(String json) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			PayMessage payMessage = gson.fromJson(json, PayMessage.class);
			Integer messageType = payMessage.getMessageType();
			PayMessage payMessage2 = payMessageDao
					.getPayMessageByType(messageType);
			if (payMessage2 != null) {
				payMessage.setMessageId(payMessage2.getMessageId());
				payMessage
						.setUpdateTime((int) (System.currentTimeMillis() / 1000l));
				boolean result = payMessageDao.updatePayMessage(payMessage);
				if (result) {
					resultStatusBean.setStatus("yes");
				}
			} else {
				payMessage
						.setCreateTime((int) (System.currentTimeMillis() / 1000l));
				Integer resultId = payMessageDao.addPayMessage(payMessage);
				if (resultId > 0) {
					resultStatusBean.setStatus("yes");
					resultStatusBean.setResultId(resultId);
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

	/**
	 * 运营 缴费统计头部
	 * 
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/getPayTop")
	public String getPayTop(@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime) {

		ResultStatusBean resultStatusBean = new ResultStatusBean();
		Integer startTimeInt = 0;
		Integer endTimeInt = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Date date = sdf.parse(startTime);
			// startTimeInt = date.getTime();
			String strTime = date.getTime() + "";
			strTime = strTime.substring(0, 10);
			startTimeInt = Integer.parseInt(strTime);

			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Date date2 = sdf2.parse(endTime);
			String strTime2 = date2.getTime() + "";
			strTime2 = strTime2.substring(0, 10);
			endTimeInt = Integer.parseInt(strTime2);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			PayAmount pa = payMessageDao.getPayTop(startTimeInt, endTimeInt);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(pa);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}

		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	/**
	 * 运营缴费列表
	 */
	@GET
	@Path("/getPayList")
	public String getPayList(@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize,
			@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime) {

		ResultStatusBean resultStatusBean = new ResultStatusBean();
		Integer startTimeInt = 0;
		Integer endTimeInt = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Date date = sdf.parse(startTime);
			// startTimeInt = date.getTime();
			String strTime = date.getTime() + "";
			strTime = strTime.substring(0, 10);
			startTimeInt = Integer.parseInt(strTime);

			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Date date2 = sdf2.parse(endTime);
			String strTime2 = date2.getTime() + "";
			strTime2 = strTime2.substring(0, 10);
			endTimeInt = Integer.parseInt(strTime2);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Page<PayAmount> pa = payMessageDao.getPayList(pageNum, pageSize,
					startTimeInt, endTimeInt);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(pa);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}

		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	/**
	 * 缴费金额统计
	 */
	@GET
	@Path("/getPayTurnoverTop")
	public String getPayTurnover(@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		Integer startTimeInt = 0;
		Integer endTimeInt = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Date date = sdf.parse(startTime);
			// startTimeInt = date.getTime();
			String strTime = date.getTime() + "";
			strTime = strTime.substring(0, 10);
			startTimeInt = Integer.parseInt(strTime);

			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Date date2 = sdf2.parse(endTime);
			String strTime2 = date2.getTime() + "";
			strTime2 = strTime2.substring(0, 10);
			endTimeInt = Integer.parseInt(strTime2);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			PayTurnover pa = payMessageDao.getPayTurnoverTop(startTimeInt,
					endTimeInt);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(pa);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}

		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	/**
	 * 缴费金额列表
	 */
	@GET
	@Path("/getPayTurnoverList")
	public String getPayTurnoverList(@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize,
			@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		Integer startTimeInt = 0;
		Integer endTimeInt = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Date date = sdf.parse(startTime);
			// startTimeInt = date.getTime();
			String strTime = date.getTime() + "";
			strTime = strTime.substring(0, 10);
			startTimeInt = Integer.parseInt(strTime);

			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Date date2 = sdf2.parse(endTime);
			String strTime2 = date2.getTime() + "";
			strTime2 = strTime2.substring(0, 10);
			endTimeInt = Integer.parseInt(strTime2);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Page<PayTurnover> pa = payMessageDao.getPayTurnoverList(pageNum,
					pageSize, startTimeInt, endTimeInt);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(pa);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}

		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
}

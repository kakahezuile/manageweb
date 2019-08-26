package com.xj.resource;

import java.text.DecimalFormat;

import java.util.Date;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.DateSpecification;
import com.xj.bean.Deduction;
import com.xj.bean.IncomeStatistics;
import com.xj.bean.IncomeStatisticsOrders;
import com.xj.bean.OrderDetailBean;
import com.xj.bean.Page;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.ShopIncomeStatistics;
import com.xj.dao.DateSpecificationDao;
import com.xj.dao.DeductionDao;
import com.xj.dao.IncomeStatisticsDao;
import com.xj.httpclient.utils.MyDateUtiles;
/**
 * 店家端 收入统计模块
 * @author Administrator
 *
 */
@Component
@Path("/communities/{communityId}/incomeStatistics")
@Scope("prototype")
public class IncomeStatisticsResource {

	private Gson gson = new Gson();

	@Autowired
	private IncomeStatisticsDao incomeStatisticsDao;
	
	@Autowired
	private DateSpecificationDao dateSpecificationDao;
	
	@Autowired
	private DeductionDao deductionDao;

	// public String getIncomeStatistics(@QueryParam("status") String status){ f3505c2bddeadb58a69bbee48b445277
	// // 个人收入
	// ResultStatusBean resultStatusBean = new ResultStatusBean();
	// resultStatusBean.setStatus("no");
	// try {
	// Integer time[] = getTime(status);
	//
	// } catch (Exception e) {
	// // TODO: handle exception
	// e.printStackTrace();
	// }
	// return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	// }
	
	@Path("/shops")
	@GET
	public String getIncome(@QueryParam("q") String emobId ){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			DateSpecification dateSpecification = dateSpecificationDao.getSpecification();
			int num = dateSpecification.getSpecificationNum();
			Date date = new Date();
			Integer startTime = MyDateUtiles.getWeekStartTime(date, "last", num);
			Integer endTime = (int)(date.getTime() / 1000l);
			ShopIncomeStatistics shopIncomeStatistics = incomeStatisticsDao.getShopIncome(startTime, endTime, emobId);
			Deduction deduction = deductionDao.getDeduction(emobId, startTime, endTime);
			if(deduction != null){
				try {
					DecimalFormat df = new DecimalFormat("#####0.00");
					double temp = Double.parseDouble(shopIncomeStatistics.getOrderPrice()) - Double.parseDouble(deduction.getDeductionPrice());
					shopIncomeStatistics.setOrderPrice(df.format(temp));
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
			shopIncomeStatistics.setStartTime(startTime);
			shopIncomeStatistics.setEndTime(endTime);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(shopIncomeStatistics);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@Path("{emobId}")
	@GET
	public String getDetail(@PathParam("emobId") String emobId,
			@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize,
			@QueryParam("q") String status , @QueryParam("type") String type , @QueryParam("thisTime") Integer thisTime) { // 个人收入
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		Integer time[] = null;
		if(type != null && !"".equals(type) && thisTime != null){
			time = MyDateUtiles.getTime(status, thisTime, type);
		}else{
			time = MyDateUtiles.getTime(status);
		}
		resultStatusBean.setStartTime(time[0]);
		resultStatusBean.setEndTime(time[1]);
		if (pageNum == null && pageSize == null) {
			try {
				IncomeStatistics incomeStatistics = incomeStatisticsDao
						.getIncome(time[0], time[1], emobId);
				incomeStatistics.setStartTime(time[0]);
				incomeStatistics.setEndTime(time[1]);
				Date d2 = new Date();
				Date d = new Date(time[0] * 1000l);
				Integer sum = MyDateUtiles.daysBetween(d, d2);
				sum += 1;
				DecimalFormat df = new DecimalFormat("#####0.0");
				if (incomeStatistics.getOrderPrice() != null
						&& !"".equals(incomeStatistics.getOrderPrice())
						&& !"null".equals(incomeStatistics.getOrderPrice())) {
					incomeStatistics 
							.setAvgOrderPrice(df.format((Double
									.parseDouble(incomeStatistics
											.getOrderPrice()) / (double) sum)));
				} else {
					incomeStatistics.setAvgOrderPrice("0.00");
				}
				DecimalFormat df2 = new DecimalFormat("#####0.0");
				Double orderSum = df2.parse(incomeStatistics.getOrderSum()+"").doubleValue();
				Double avgOrderSum = orderSum / (double)sum;
				incomeStatistics.setAvgOrderSum(df2.format(avgOrderSum));
				if("".equals(incomeStatistics.getOnlinePrice()) || "null".equals(incomeStatistics.getOnlinePrice()) || incomeStatistics.getOnlinePrice() == null){
					incomeStatistics.setOnlinePrice("0.00");
				}
				if("".equals(incomeStatistics.getOrderPrice()) || "null".equals(incomeStatistics.getOrderPrice()) || incomeStatistics.getOrderPrice() == null){
					incomeStatistics.setOrderPrice("0.00");
				}
				resultStatusBean.setStatus("yes");
				resultStatusBean.setInfo(incomeStatistics);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} else { // 详细订单列表
			try {
				if (time != null) {
					Page<IncomeStatisticsOrders> page = incomeStatisticsDao
							.getOrders(time[0], time[1], emobId, pageNum,
									pageSize, 10);
					List<IncomeStatisticsOrders> list = page.getPageData();
					int len = list.size();
					for (int i = 0; i < len; i++) {
						IncomeStatisticsOrders incomeStatisticsOrders = list
								.get(i);
						Integer orderId = incomeStatisticsOrders.getOrderId();
						List<OrderDetailBean> detailList = incomeStatisticsDao
								.getOrderDetailList(orderId);
						list.get(i).setList(detailList);
					}
					resultStatusBean.setStatus("yes");
					page.setPageData(list);
					resultStatusBean.setInfo(page);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	@Path("{emobId}/ranking")
	@GET
	public String getRanking(@PathParam("emobId") String emobId,
			@QueryParam("pageSize") Integer pageSize,
			@QueryParam("pageNum") Integer pageNum,
			@QueryParam("q") String status, @QueryParam("type") String type , @QueryParam("thisTime") Integer thisTime) { // 销量排行
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			Integer time[] = null;
			if(type != null && !"".equals(type) && thisTime != null){
				time = MyDateUtiles.getTime(status, thisTime, type);
			}else{
				time = MyDateUtiles.getTime(status);
			}
			resultStatusBean.setStartTime(time[0]);
			resultStatusBean.setEndTime(time[1]);
			Page<OrderDetailBean> page = incomeStatisticsDao.getRanking(
					time[0], time[1], emobId, pageNum, pageSize, 10);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(page);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	
}

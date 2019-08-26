package com.xj.resource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import com.xj.bean.Clearing;
import com.xj.bean.DeductMoney;
import com.xj.bean.Deduction;
import com.xj.bean.Page;
import com.xj.bean.ReckoningHistory;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.ShopsDeduction;
import com.xj.service.DeductionService;

@Scope("prototype")
@Component
@Path("/communities/{communityId}/deduction")
public class DeductionResource {

	private Gson gson = new Gson();

	@Autowired
	private DeductionService deductionService;

	@POST
	public String addDeduction(String json) { // 添加扣款
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		Deduction deduction = gson.fromJson(json, Deduction.class);
		Date date = new Date();
		Integer startTime = (int) (date.getTime() / 1000);
		deduction.setStartTime(startTime);
		deduction.setStatus("ongoing");
		try {
			Integer i = deductionService.addDeduction(deduction);
			if (i > 0) {
				resultStatusBean.setStatus("yes");
			} else {
				resultStatusBean.setStatus("no");
			}

		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("no");
		}

		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	@PUT
	@Path("{deductionId}")
	public String updateDeduction(
			@PathParam("deductionId") Integer deductionId, String json) { // 修改扣款
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		Deduction deduction = gson.fromJson(json, Deduction.class);
		Date date = new Date();
		Integer endTime = (int) (date.getTime() / 1000);
		deduction.setEndTime(endTime);
		deduction.setDeductionId(deductionId);
		deduction.setStatus("ended");
		try {
			boolean s = deductionService.updateDeduction(deduction);
			if (s) {
				resultStatusBean.setStatus("yes");
			} else {
				resultStatusBean.setStatus("no");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("no");
		}

		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	/**
	 * 扣款记录
	 */
	@GET
	@Path("/getDeductMoney")
	public String getDeductMoney(@QueryParam("emobId") String emobId,
			@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize,
			@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		Integer startTimeInt = 0;
		Integer endTimeInt = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Date date = sdf.parse(startTime);
			startTimeInt = (int) (date.getTime() / 1000);

			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Date date2 = sdf2.parse(endTime);
			endTimeInt = (int) (date2.getTime() / 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Page<DeductMoney> page = deductionService.getDeductMoney(emobId,
					pageNum, pageSize, startTimeInt, endTimeInt);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(page);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	/**
	 *  结款历史
	 */
	@GET
	@Path("/getReckoningHistory")
	public String getReckoningHistory(@PathParam("communityId") Integer communityId,@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize,@QueryParam("sort") String sort){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			Page<ReckoningHistory> page = deductionService.getReckoningHistory(communityId,sort,pageNum,pageSize);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(page);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		
	}
	/**
	 * 添加结款表
	 */
	@POST
	@Path("/addClearing")
	public String addClearing(String json){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		Clearing clearing = gson.fromJson(json, Clearing.class);
		clearing.setStatus("ended");
		try {
			Integer i= deductionService.addClearing(clearing);
			if(i>0){
				resultStatusBean.setStatus("yes");
			}else{
				resultStatusBean.setStatus("no");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("no");
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@GET
	public String getShopsDeduction(@QueryParam("q") String emobId) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<ShopsDeduction> shopsDeductions = deductionService
					.getShopsDeduction(emobId);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(shopsDeductions);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}

		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
}

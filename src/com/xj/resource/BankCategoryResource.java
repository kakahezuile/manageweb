package com.xj.resource;

import java.util.List;


import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.BankCategory;
import com.xj.bean.ResultStatusBean;
import com.xj.dao.BankCategoryDao;

@Component
@Path("/communities/{communityId}/bankCategories")
@Scope("prototype")
public class BankCategoryResource {
	
	private Gson gson = new Gson();
	
	@Autowired
	private BankCategoryDao bankCategoryDao;

	
	@GET
	
	public String getBankCategoryList(){ // 获取银行类别
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			List<BankCategory> list = bankCategoryDao.getBankCategoryList();
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(list);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	/**
	 * 绑定银行卡
	 * @param json
	 * @return
	 */
	@POST
	public String addBankCategory(String json){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			BankCategory bankCategory = gson.fromJson(json, BankCategory.class);
			Integer resultId = bankCategoryDao.addBankCategory(bankCategory);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setResultId(resultId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean);
	}
	
}

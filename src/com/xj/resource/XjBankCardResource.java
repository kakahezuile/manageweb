package com.xj.resource;

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
import com.xj.bean.ResultStatusBean;
import com.xj.bean.XjBankCard;
import com.xj.dao.XjBankCardDao;

@Component
@Path("/communities/{communityId}/bankcards")
@Scope("prototype")
public class XjBankCardResource {
	
	private Gson gson = new Gson();
	
	@Autowired
	private XjBankCardDao xjBankCardDao;
	
	@POST
	public String addBankCard(String json , @PathParam("communityId") Integer communityId){ // 为用户绑定银行卡
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			XjBankCard xjBankCard = gson.fromJson(json, XjBankCard.class);
			xjBankCard.setCreateTime((int)(System.currentTimeMillis() / 1000l));
			xjBankCard.setCommunityId(communityId);
			XjBankCard xjBankCard2 = xjBankCardDao.getBankCard(xjBankCard.getEmobId(), xjBankCard.getCardNo());
			if(xjBankCard2 == null){
				Integer result = xjBankCardDao.addXjBankCard(xjBankCard);
				if(result > 0){
					resultStatusBean.setStatus("yes");
					resultStatusBean.setResultId(result);
				}
			}else{// 当前用户已绑定此银行卡
				resultStatusBean.setStatus("isEmpty");
				resultStatusBean.setMessage("当前用户已 绑定此银行卡");
				return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@GET
	
	public String getBankCardList(@QueryParam("q") String emobId){ // 获取用户拥有的所有银行卡
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			List<XjBankCard> list = xjBankCardDao.getBankCardList(emobId);
			
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
	
	@PUT
	@Path("{bankCardId}")
	public String updateBankCard(@PathParam("bankCardId") Integer bankCardId , String json){ // 修改绑定银行卡
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			XjBankCard xjBankCard = gson.fromJson(json, XjBankCard.class);
			xjBankCard.setBankCardId(bankCardId);
			boolean result = xjBankCardDao.updateXjBankCard(xjBankCard);
			if(result){
				resultStatusBean.setStatus("yes");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@GET
	@Path("{emobId}/isEmpty")
	public String isEmpty(@PathParam("emobId") String emobId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no"); //没绑定
		try {
			List<XjBankCard> list = xjBankCardDao.getBankCardList(emobId);
			if(list != null && list.size() > 0){
				resultStatusBean.setStatus("yes"); // 绑定了
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
}

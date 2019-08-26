package com.xj.resource;

import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.xj.bean.Admin;
import com.xj.bean.Communities;
import com.xj.bean.CommunityService;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.Rule;
import com.xj.bean.RuleItem;
import com.xj.bean.RuleRelation;

import com.xj.dao.AdminDao;
import com.xj.dao.CommunitiesDao;
import com.xj.dao.CommunityRelationDao;
import com.xj.dao.RuleDao;
import com.xj.dao.RuleItemDao;
import com.xj.dao.RuleRelationDao;

@Component
@Scope("prototype")
@Path("/rule")
public class RuleResouce {
	
	private Gson gson = new Gson();;
	
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private CommunitiesDao communitiesDao;
	
	@Autowired
	private RuleItemDao ruleItemDao;
	
	@Autowired
	private RuleDao ruleDao;
	
	@Autowired
	private RuleRelationDao ruleRelationDao;
	
	@Autowired
	private CommunityRelationDao communityRelationDao;
	
	@GET
	@Path("/getAdminList")
	public String getAdminList(){
		List<Admin> list = null;
		try {
			list = adminDao.getAdminList();
		} catch (Exception e) {
			e.printStackTrace();
			ResultStatusBean resultStatusBean = new ResultStatusBean();
			resultStatusBean.setMessage("获取管理员列表出错了");
			resultStatusBean.setStatus("no");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(list).replace("\"null\"", "\"\"");
	}
	
	@GET
	@Path("/getCommunityList")
	public String getCommunityList(){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<Communities> list = communitiesDao.getCommunityList();
			if(list != null){
				resultStatusBean.setStatus("yes");
				resultStatusBean.setInfo(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(resultStatusBean);
	}
	
	@GET
	@Path("/getCommunity/{adminId}")
	public String getCommunityByAdminId(@PathParam("adminId") Integer adminId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			RuleItem ruleItem = ruleItemDao.getRuleItem(adminId);
			if(ruleItem != null){
				resultStatusBean.setStatus("yes");
				resultStatusBean.setInfo(ruleItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(resultStatusBean);
	}
	
	@GET
	@Path("/getAllModel")
	public String getAllModel(@QueryParam("adminId") Integer adminId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			Admin admin = adminDao.getAdminById(adminId);
			if(admin != null){
				List<Rule> list = ruleDao.getRuleList(admin.getRole());
				if(list != null){
					resultStatusBean.setStatus("yes");
					resultStatusBean.setInfo(list);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(resultStatusBean);
	}
	
	@GET
	@Path("/isTrueModel/{adminId}")
	public String isTrueMole(@PathParam("adminId") Integer adminId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<RuleRelation> list = ruleRelationDao.getRelationList(adminId);
			if(list != null){
				resultStatusBean.setStatus("yes");
				resultStatusBean.setInfo(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(resultStatusBean);
	}
	
	@POST
	@Path("/addCommunity/{adminId}/{communityId}")
	public String addCommunity(@PathParam("adminId") Integer adminId , @PathParam("communityId") Integer communityId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			ruleItemDao.deleteRuleByAdminIdAndCommunityId(adminId, communityId);
			RuleItem ruleItem = ruleItemDao.getRuleByAminAndCommunityId(adminId, communityId);
			Admin admin = new Admin();
			admin.setCommunityId(communityId);
			admin.setAdminId(adminId);
			adminDao.updateAdmin(admin);
			if(ruleItem == null ){
				ruleItem = new RuleItem();
				ruleItem.setAdminId(adminId);
				ruleItem.setCommunityId(communityId);
				ruleItem.setStatus("block");
				ruleItem.setCreateTime((int)(System.currentTimeMillis() / 1000l));
				Integer result = ruleItemDao.addRuleItem(ruleItem);
				if(result > 0){
					resultStatusBean.setStatus("yes");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(resultStatusBean);
	}
	
	@POST
	@Path("{communityId}/addModel")
	public String addModel(String json , @PathParam("communityId") Integer communityId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("yes");
		try {
			List<RuleRelation> list = gson.fromJson(json, new TypeToken<List<RuleRelation>>(){}.getType());
			Iterator<RuleRelation> it = list.iterator();
			RuleRelation ruleRelation = null;
			while(it.hasNext()){
				ruleRelation = it.next();
				Integer adminId = ruleRelation.getAdminId();
				Integer ruleId = ruleRelation.getRuleId();
				if(ruleId == 1 || ruleId == 2 || ruleId == 13){
					List<CommunityService> relationList = communityRelationDao.getRelationList(communityId);
					boolean type = false;
					int len = 0 ;
					for(int i = 0 ; i < len ; i++){
						CommunityService communityService = relationList.get(i);
						Integer serviceId = communityService.getServiceId();
						if(serviceId == 7){
							type = true;
						}
					}
					if(type){
						continue;
					}
				}
				String status = ruleRelation.getStatus();
				RuleRelation relation = ruleRelationDao.getRelationByAdminIdAndRuleId(adminId, ruleId);
				if(relation != null){
					relation.setStatus(ruleRelation.getStatus());
					ruleRelationDao.updateRuleRelation(relation);
				}else{
					relation = new RuleRelation();
					relation.setAdminId(adminId);
					relation.setRuleId(ruleId);
					relation.setStatus(status);
					relation.setCreateTime((int)(System.currentTimeMillis() / 1000l));
					ruleRelationDao.addRuleRelation(relation);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("no");
		}
		return gson.toJson(resultStatusBean);
	}
}

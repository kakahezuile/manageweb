package com.xj.resource;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.BlackList;
import com.xj.bean.ResultStatusBean;
import com.xj.dao.BlackListDao;
import com.xj.httpclient.apidemo.EasemobIMUsers;

@Component
@Path("/communities/{communityId}/blacklist")
@Scope("prototype")
public class BlackListResource {
	
	private Gson gson = new Gson();
	
	@Autowired
	private BlackListDao blackListDao;
	
	private boolean isNum(String str){
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
	/**
	 * 添加黑名单
	 * @param json
	 * @param communityId
	 * @return
	 */
	@POST
	public String addBlackList(String json , @PathParam("communityId") Integer communityId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			BlackList blackList = gson.fromJson(json, BlackList.class);
			blackList.setCommunityId(communityId);
			int time = (int)(System.currentTimeMillis() / 1000l);
			blackList.setCreateTime(time);
			blackList.setUpdateTime(time);
			blackList.setStatus("activity");
			int result = blackListDao.addBlackList(blackList);
			if(result > 0){
				try {
					if(!isNum(blackList.getEmobIdTo())){
						EasemobIMUsers.addBlack(blackList.getEmobIdFrom(), blackList.getEmobIdTo());
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(blackList);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("add black list error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	/**
	 * 根据emobId获取黑名单列表
	 * @param emobId
	 * @param communityId
	 * @return
	 */
	@GET
	@Path("{emobId}")
	public String getBlackList(@PathParam("emobId") String emobId , @PathParam("communityId") Integer communityId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<BlackList> list = blackListDao.getBlackList(communityId, emobId,"activity");
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("get black list error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	public boolean isNumeric(String str){ 
		Pattern pattern = Pattern.compile("[0-9]*"); 
		Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
		      return false; 
		} 
		return true; 
	}
	/**
	 * 从黑名单中移除
	 * @param emobId
	 * @param emobIdTo
	 * @param communityId
	 * @return
	 */
	@DELETE
	@Path("{emobId}")
	public String deleteBlackList(@PathParam("emobId") String emobId , @QueryParam("emobIdTo") String emobIdTo , @PathParam("communityId") Integer communityId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<BlackList> blackList = blackListDao.getBlackList(communityId, emobId, emobIdTo , "activity");
			if(blackList != null){
				Iterator<BlackList> iterator = blackList.iterator();
				while(iterator.hasNext()){
					boolean result = blackListDao.deleteBlackList(iterator.next());
					if(result){
						resultStatusBean.setStatus("yes");
						if(!isNum(emobIdTo)){
							EasemobIMUsers.deleteBlack(emobId, emobIdTo);
						}
						
					}
				}
				
			}else{
				resultStatusBean.setMessage("当前黑名单用户不存在");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("delete black list error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
}

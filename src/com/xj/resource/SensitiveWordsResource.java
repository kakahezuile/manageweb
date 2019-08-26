package com.xj.resource;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.SensitiveWords;
import com.xj.dao.SensitiveWordsDao;

@Path("/communities/{communityId}/sensitiveWords")
@Component
@Scope("prototype")
public class SensitiveWordsResource {
	
	private Gson gson = new Gson();
	
	@Autowired
	private SensitiveWordsDao sensitiveWordsDao;
	
	@GET
	public String getSensitiveWords(){  // 获取敏感词
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<SensitiveWords> list = sensitiveWordsDao.getSensitiveWordsList();
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
	@Path("{sensitiveWordsId}")
	public String updateSensitiveWords(String json , @PathParam("sensitiveWordsId") Integer sensitiveWordsId){ // 修改敏感词
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			SensitiveWords sensitiveWords = gson.fromJson(json, SensitiveWords.class);
			sensitiveWords.setSensitiveWordsId(sensitiveWordsId);
			Integer time = (int)(System.currentTimeMillis() / 1000l);
//			sensitiveWords.setCreateTime(time);
			sensitiveWords.setUpdateTime(time);
			boolean result = sensitiveWordsDao.updateSensitiveWords(sensitiveWords);
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
	
	@POST
	public String addSensitiveWords(String json){ // 添加敏感词
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			SensitiveWords sensitiveWords = gson.fromJson(json, SensitiveWords.class);
			Integer time = (int)(System.currentTimeMillis() / 1000l);
			sensitiveWords.setCreateTime(time);
			sensitiveWords.setUpdateTime(time);
			Integer resultId = sensitiveWordsDao.addSensitiveWords(sensitiveWords);
			if(resultId > 0){
				resultStatusBean.setStatus("yes");
			}
			resultStatusBean.setResultId(resultId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@DELETE
	@Path("{sensitiveWordsId}")
	public String deleteSensitiveWords(@PathParam("sensitiveWordsId") Integer sensitiveWordsId){ // 删除敏感词
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			SensitiveWords sensitiveWords = new SensitiveWords();
			sensitiveWords.setSensitiveWordsId(sensitiveWordsId);
			boolean result = sensitiveWordsDao.deleteSensitiveWords(sensitiveWords);
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
}

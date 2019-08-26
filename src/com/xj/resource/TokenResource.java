package com.xj.resource;


import javax.ws.rs.POST;
import javax.ws.rs.Path;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.QiNiuBody;
import com.xj.bean.ResultStatusBean;
import com.xj.httpclient.utils.QiniuFileSystemUtil;
import com.xj.httpclient.vo.QiNiuToken;


@Scope("prototype")
@Component
@Path("/communities/{communityId}/qiniuToken")
public class TokenResource {
	
	private Gson gson = new Gson();
	
	/**
	 * 获取 七牛token
	 * @param json
	 * @return
	 */
	@POST
	public String getToken(String json){
		
		if(json == null || "".equals(json)){
			ResultStatusBean resultStatusBean = new ResultStatusBean();
			resultStatusBean.setMessage("参数不能为空");
			resultStatusBean.setStatus("no");
			return gson.toJson(resultStatusBean);
		}
		
		QiNiuBody qiNiuBody = gson.fromJson(json, QiNiuBody.class);
		
		String token = "";
		token = QiniuFileSystemUtil.getUptoken(qiNiuBody);
		QiNiuToken qiNiuToken = new QiNiuToken();
		qiNiuToken.setToken(token);
		return gson.toJson(qiNiuToken);
	}
}

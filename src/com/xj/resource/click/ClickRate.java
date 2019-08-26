package com.xj.resource.click;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xj.utils.HttpUtil;

@Component
@Scope("prototype")
@Path("/communities/{communityId}/clickRate")
public class ClickRate {

	/**
	 * 访问量统计
	 */
	@GET
	@Path("/getClickAmount")
	public String getClickAmount(@QueryParam("url") String url) {
		return HttpUtil.httpUrl(url);
	}
}
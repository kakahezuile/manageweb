package com.xj.resource;


import com.google.gson.Gson;
import com.xj.bean.Comments;
import com.xj.bean.CommentsWithUser;
import com.xj.bean.Page;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.ShopsComments;
import com.xj.bean.ShopsCommentsHistory;
import com.xj.bean.UserComments;
import com.xj.dao.CommentsDao;
import com.xj.service.ShopsCommentsService;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

/**
 * 
 */
@Component
@Scope("prototype")
@Path("/communities/{communityId}/shops/{emobId}/comments")
public class CommentsResource {
    //logger
    Logger logger = Logger.getLogger(CommentsResource.class);
    //dao
    @Autowired
    private CommentsDao commentsDao;
    
    @Autowired
    private ShopsCommentsService shopsCommentsService;
    
    private Gson gson = new Gson();
    /**
     * 添加评论
     * @param requestJson
     * @param emobId
     * @return
     */
    @POST
    public String addComments(String requestJson ,@PathParam("emobId") String emobId){
        Gson gson = new Gson();
        logger.info("request json is :"+requestJson);
        //parse request to bean
        
        
        //result bean
        ResultStatusBean resultStatusBean = new ResultStatusBean();

        int id;
		try {
			Comments commentsBean = gson.fromJson(requestJson, Comments.class);
			commentsBean.setEmobIdTo(emobId);
			
			commentsBean.setCreateTime((int)(System.currentTimeMillis() / 1000));
			id = commentsDao.addComments(commentsBean);
			logger.info("id of new shop is :"+id);
	        if(id>0){
	            resultStatusBean.setStatus("yes");
	            return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	        }else{
	            resultStatusBean.setStatus("no");
	            resultStatusBean.setMessage("评论失败");
	            return gson.toJson(resultStatusBean);
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 resultStatusBean.setStatus("no");
			 resultStatusBean.setMessage("评论失败");
	         return gson.toJson(resultStatusBean);
		}
      
    }
    /**
     * 根据emobId获取评论列表
     * @param emobId
     * @param pageNum
     * @param pageSize
     * @param scoreType
     * @return
     */
    @GET
    public String getComments(@PathParam("emobId") String emobId , @QueryParam("pageNum") Integer pageNum , @QueryParam("pageSize") Integer pageSize , @QueryParam("q") Integer scoreType ){
    	ResultStatusBean result = new ResultStatusBean();
    	Page<Comments> page = null;
    	if(pageNum == null && pageSize == null){ // 查看店铺个人总评价
    		ShopsComments shopsComments = null;
			try {
				shopsComments = commentsDao.getShopsCommentsByEmobId(emobId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(shopsComments != null){
				result.setStatus("yes");
				result.setInfo(shopsComments);
				return gson.toJson(result).replace("\"null\"", "\"\"");
			}
    	}
    	Page<CommentsWithUser> page2 = null;
    	if(scoreType != null){
    		try {
				page2 = commentsDao.getCommentsListWithPageBySize(emobId, pageNum, pageSize, 10 , scoreType);
				result.setStatus("yes");
				result.setInfo(page2);
				return gson.toJson(result).replace("\"null\"", "\"\"");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result.setMessage("查询评论出现异常");
    			result.setStatus("no");
    			return gson.toJson(result);
			}
			
    	}
    	
    	if(emobId != null && !"".equals(emobId)){ // 查看对本店的评价列表
    		try {
    			page = commentsDao.getCommentsListWithPage(emobId, pageNum, pageSize, 10);
    			result.setStatus("yes");
    			result.setInfo(page);
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			
    			result.setMessage("查询评论出现异常");
    			result.setStatus("no");
    			return gson.toJson(result);
    		}
    	}else{
    		
    		result.setMessage("shopId不能为空");
    		result.setStatus("no");
    		return gson.toJson(result);
    	}
    	return gson.toJson(result).replace("\"null\"", "\"\"");
    }
    
//    @GET
//    @Path("/{scoreType}")
//    public String getCommentsByScore(@PathParam("emobId") String emobId , @PathParam("scoreType") Integer scoreType , @QueryParam("pageNum") Integer pageNum , @QueryParam("pageSize") Integer pageSize ){
//    	ResultStatusBean result = null;
//    	Page<CommentsWithUser> page = null;
//    	if(emobId != null && !"".equals(emobId)){
//    		try {
//    			page = commentsDao.getCommentsListWithPageBySize(emobId, pageNum, pageSize, 10 , scoreType);
//
//    		} catch (Exception e) {
//    			// TODO Auto-generated catch block
//    			e.printStackTrace();
//    			result = new ResultStatusBean();
//    			result.setMessage("查询评论出现异常");
//    			result.setStatus("no");
//    			return gson.toJson(result);
//    		}
//    	}else{
//    		result = new ResultStatusBean();
//    		result.setMessage("shopId不能为空");
//    		result.setStatus("no");
//    		return gson.toJson(result);
//    	}
//    	return gson.toJson(page);
//    }
    
//    @GET
//    @Path("/myShops")
//    public String getMyShopsComments(@PathParam("emobId") String emobId){
//    	ResultStatusBean resultStatusBean = new ResultStatusBean();
//    	resultStatusBean.setStatus("no");
//    	try {
//			ShopsComments shopsComments = commentsDao.getShopsCommentsByEmobId(emobId);
//			if(shopsComments != null){
//				resultStatusBean.setStatus("yes");
//				resultStatusBean.setInfo(shopsComments);
//			}
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			resultStatusBean.setStatus("error");
//			resultStatusBean.setMessage("select is error");
//			return gson.toJson(resultStatusBean);
//		}
//    	return gson.toJson(resultStatusBean);
//    }

    @GET
    @Path("/detail")
    public String getShopsComments(@PathParam("communityId") Integer communityId 
    		, @PathParam("emobId") String emobId 
    		, @QueryParam("pageNum") Integer pageNum
    		, @QueryParam("pageSize") Integer pageSize) throws Exception{
    	ResultStatusBean resultStatusBean = new ResultStatusBean();
    	resultStatusBean.setStatus("no");
    	if(pageNum != null && pageNum > 1){
    		Page<UserComments> page = shopsCommentsService.getUserComments(communityId, emobId, pageNum, pageSize);
    		resultStatusBean.setStatus("yes");
    		resultStatusBean.setInfo(page);
    	}else{
    		ShopsCommentsHistory shopsCommentsHistory = shopsCommentsService.getShopsCommentsHistory(communityId, emobId,pageNum,pageSize);
        	if(shopsCommentsHistory != null){
        		resultStatusBean.setStatus("yes");
        		resultStatusBean.setInfo(shopsCommentsHistory);
        	}else{
        		resultStatusBean.setStatus("no");
        		resultStatusBean.setMessage("没有查询到相关内容");
        	}
    	}
    	
    	return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
    }
}

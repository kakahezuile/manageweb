package com.xj.stat.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xj.bean.Page;
import com.xj.bean.PushMessage;
import com.xj.bean.StatPushMessageBean;
import com.xj.stat.po.UserVo;
import com.xj.stat.service.StatPushMessageService;

/**
 * 统计推送信息
 * @author Administrator
 */
@RequestMapping("/stat/PushMessage")
@Controller
public class StatPushMessageController {
	
	@Autowired
	private StatPushMessageService statPushMessageService;
	
	/**
	 * 获取小区发送的通知，查询公告通知表
	 * @param communityId
	 * @param cmdCode
	 * @param page
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/communities/{communityId}/{cmdCode}/statNotificationPushMessage.do")
	public String statNotificationPushMessage(@PathVariable("communityId")Integer communityId,@PathVariable("cmdCode")Integer  cmdCode,int page,int pageSize,Model model) throws Exception{

		Page<StatPushMessageBean> pageBean = statPushMessageService.getAllBulletinByCommunityId(communityId,page,pageSize,cmdCode);
		
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("communityId", communityId);
		model.addAttribute("flag", "push");
		return "push/push-statistics";
	}
	
	/**
	 * 获取发送详情
	 * @param communityId
	 * @param messageId
	 * @param page
	 * @param pageSize
	 * @param model
	 * @return
	 */
	@RequestMapping("/communities/{communityId}/{messageId}/notificationDetail.do")
	public String notificationDetail(@PathVariable("communityId")Integer communityId,@PathVariable("messageId")long  messageId,int page,int pageSize,Model model){
		Page<UserVo> pageBean = statPushMessageService.getBulletinsDetailsByMessageId(communityId, messageId, page, pageSize);
		
		String  time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(messageId));
		
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("time", time);
		model.addAttribute("flag", "push");
		model.addAttribute("messageId", messageId);
		model.addAttribute("communityId", communityId);
		
		return "push/push-detail";
	}
	
	/**
	 * 获取用户推送
	 * @param communityId
	 * @param cmdCode
	 * @param page
	 * @param pageSize
	 * @param model
	 * @return
	 */
	@RequestMapping("/communities/{communityId}/{cmdCode}/getPushMessagesUsers.do")
	public String getPushMessagesUsers(@PathVariable("communityId")Integer communityId,@PathVariable("cmdCode")Integer  cmdCode,int page,int pageSize,Model model){
		
		Page<UserVo> pageBean = statPushMessageService.getPushMessagesUsers(communityId,page,pageSize,cmdCode);
		
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("communityId", communityId);
		model.addAttribute("flag", "user");
		return "push/push-user-statistics";
	}
	
	/**
	 * 获取这个用户的推送详情
	 * @param communityId
	 * @param emobId
	 * @param cmdCode
	 * @param page
	 * @param pageSize
	 * @param model
	 * @return
	 */
	@RequestMapping("/communities/{communityId}/{emobId}/{cmdCode}/getPushUserDetail.do")
	public String getPushUserDetail(@PathVariable("communityId")Integer communityId,@PathVariable("emobId")String  emobId,@PathVariable("cmdCode")Integer  cmdCode,int page,int pageSize,Model model){
		Page<PushMessage> pageBean = statPushMessageService.getPushUserDetail(communityId,emobId,page,pageSize,cmdCode);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("communityId", communityId);
		model.addAttribute("flag", "user");
		model.addAttribute("emobId", emobId);
		return "push/push-user-detail";
	}
	
}


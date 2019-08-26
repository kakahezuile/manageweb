<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String webTitle = properties.getProperty("webTitle"); 
%>

<!doctype html>
<!--[if lt IE 7]> <html class="ie6 oldie"> <![endif]-->
<!--[if IE 7]>    <html class="ie7 oldie"> <![endif]-->
<!--[if IE 8]>    <html class="ie8 oldie"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="">
<!--<![endif]-->


<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
<meta name="keywords" content="小间物业,小间物业管理系统" />
<meta name="Description" content="小间物业,小间物业管理系统" />
<title>等待审核的活动_小间物业管理系统</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->
<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>

<script type="text/javascript">
	
    function schedule_tousu(){
		$("#tousu_schedule").attr("style","display:block");
		$("#bg_tousu_schedule").attr("style","display:block");
		
	}
	
	function onSchedule_tousu(){
		$("#tousu_schedule").attr("style","display:none");
		$("#bg_tousu_schedule").attr("style","display:none");
	}
	
	function scroll(from){
	
	    document.getElementById(from+"-tousu-nouser").scrollTop = document.getElementById(from+"-tousu-nouser").scrollHeight;
		// document.getElementByIdx("tousu_null-nouser").scrollTop = document.getElementByIdx("tousu_null-nouser").scrollHeight;
		// document.getElementByIdx("tousu_null-nouser").scrollTo(0,document.body.scrollHeight)
	}
	function scrollTop(){
	
    	document.getElementById("tousu_contractlist").scrollTop = 0;
		// document.getElementByIdx("tousu_null-nouser").scrollTop = document.getElementByIdx("tousu_null-nouser").scrollHeight;
		// document.getElementByIdx("tousu_null-nouser").scrollTo(0,document.body.scrollHeight)
	}



	setTimeout("setTime()",1000*60);
</script>

<style type="text/css">

.upload-master-face-bg{
	background:url("../../../images/public/bg-blank-60.png") repeat;
	position:fixed;
	top:0;
	width:100%;
	height:100%;
	z-index:21;
	left:0;
}
.loadingbox{
    height: 32px;
    width:32px;
    left: 50%;
    margin-left: -16px;
    margin-top: -16px;
    position: absolute;
    top: 50%;
    z-index: 22;
}


</style>
</head>
<body>

<input id="admin_status" value="${adminStatus}" type="hidden"/>
<input id="community_id" value="${communityId}" type="hidden"/>
<input id="complaint_id" type="hidden"/>
<input id="tousu_emob_id_from" type="hidden"/>
<input id="tousu_emob_id_to" type="hidden"/>
<input id="tousu_admin_user" type="hidden" value="${newUserName}"/>




<div class="loadingbox" id="tousu_schedule" style="display: none;">
		<img alt="" src="<%= basePath %>/images/chat/loading.gif">
	</div>
	<div class="upload-master-face-bg" id="bg_tousu_schedule" style="display: none;">&nbsp;</div>

<!--<div class="blank-background" style="display: none;z-index:200;" id="blank-background"></div>
<div class="signed-creat-box" style="display: none;z-index:300;" id="signed-creat-box">
	<div>扣款申请</div>
	<div>
		<textarea id="reason">请输入扣款理由</textarea>
	</div>
	<input type="button" onclick="applyFor();" value="确定"/>
</div>
-->

<div class="blank-background" style="display: none;z-index:200;" id="blank-background"></div>
<div class="activity-refuse"  style="display: none;z-index:300;" id="signed-creat-box">
	<div>
		<textarea id="reason" rows="" cols="" placeholder="请输入处理结果"></textarea>
	</div> 
	<div class="activity-check-opt">
		<a href="javascript:applyCancel();">取消</a>
		<a href="javascript:void(0);" onclick="applyFor();">提交处理</a>
	</div>
</div>



		<div class="hx-content clearfix" id="tousu_content" style="display: block;margin:0;width:1232px;">
			<div class="hx-leftcontact" id="leftcontact">
				<div class="chat-search" id="form_id_tousu">
					<input type="text" placeholder="搜索用户" id="tousu_seek" onKeyUp="tousu_seek();" class="search" style="display: none;">
				</div>
				<div id="tousu_contractlist" style="height:596px; overflow-y: auto; overflow-x: auto;">

					<div class="accordion" id="tousu_accordionDiv">
						<div class="accordion-group">
							<div class="accordion-heading">
								<a id="tousu_accordion" class="accordion-toggle"
									data-toggle="collapse" data-parent="#accordionDiv"
									href="#collapseOne">所有投诉 </a>
							</div>
							<div id="tousu_collapseOne" class="accordion-body collapse in">
								<div class="accordion-inner" id="contractlist">
									<ul id="tousu_contactlistUL" class="chat03_content_ul">
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div id="tousu_rightTop" style="height: 0;"></div>
			<!-- 聊天页面 -->
			<div class="chatRight">
				<div class="chat01_title" overflow: hidden; margin-left: 0;">
					<ul class="talkTo clearfix">
						<li id="tousu_talkTo">
							<a title="编辑地址" class="edit-address" href="#">&nbsp;</a><span id="name_room"></span>
						</li>
						<li class="complain-remark"><div class="complain-remark-box"><a title="编辑备注" href="javascript:remark();">&nbsp;</a><input id="remark" readonly="readonly" class="readonly" type="text" placeholder="添加备注" value="这人精神不太正常" title="这人精神不太正常"></div></li>
						<li class="chat-history"><div class="ask-record"><a onclick="isHistory();" href="javascript:void(0);" class="ask-history">聊天历史 </a></div></li>
					</ul>
				</div>
				<div class="complain-content">
					<p class="complain-content-title">
						<b>投诉内容：</b><span id="tousu_detail"></span>
					</p>
				</div>
				<div class="complain-master clearfix">
					<div class="complain-master-face" >
						<img alt="头像" id="weixiu_shifu_avir"
							src="${pageContext.request.contextPath }/images/complain/master_face.png" />
					</div>
					<div class="complain-master-info">
						<div class="complain-master-name">
							<span id="weixiu_shifu_name" class="complain-name"></span><span>(</span><span id="tousu_phone"></span><span>)</span>
						</div>
						<div class="complain-master-attribute">
							<p>
								下单量：<span id="chengjiao_danshu"></span>
								投诉量：<span id="tousu_num"></span>
							</p>
						</div>
						<div class="complain-master-attribute" id="tousu_pingjia">

						</div>
					</div>
					<div class="complain-master-opearation">
						<a href="javascript:;"  class="disable" id="tousu_disable">无效投诉</a>
						<a href="javascript:;" id="tousu_signed">解决投诉</a>
					</div>
					<div class="complain-date">投诉时间&nbsp;&nbsp;<span id="tousu_time"></span></div>
				</div>
				
				<div id="tousu_chat01">
					<div id="tousu_null-nouser" name="chat_pages_nouser_tousu" class="chat01_content" style="margin-left: 0;height:333px; ">
					</div>
				</div>
				<div class="chat02">
					<div class="chat02_title">
						<a class="chat02_title_btn" onclick="showEmotionDialogTousu()" title="选择表情"></a>
						<label id="tousu_chat02_title_t"></label>
						<div id="tousu_wl_faces_box" class="wl_faces_box">
							<div class="wl_faces_content">
								<div class="title">
									<ul>
										<li class="title_name">
											常用表情
										</li>
										<li class="wl_faces_close">
											<span onclick='turnoffFaces_box_tousu()'>&nbsp;</span>
										</li>
									</ul>
								</div>
								<div id="tousu_wl_faces_main" class="wl_faces_main">
									<ul id="tousu_emotionUL">
									</ul>
								</div>
							</div>
							<div class="wlf_icon"></div>
						</div>
					</div>
					<div id="tousu_input_content" class="chat02_content">
						<textarea id="tousu_talkInputId" style="resize: none;" onkeydown="if(event.keyCode==13){tousu_send();}"></textarea>
					</div>
					<div class="chat02_bar">
						<ul>
							<li style="right: 5px; top: 0px;">
								<img src="${pageContext.request.contextPath }/easymob-webim1.0/img/send_btn.jpg"
									onclick="tousu_send();" />
							</li>
						</ul>
					</div>
					<div style="clear: both;"></div>
				</div>
			</div>
			<div class="complain-show">
				<div class="complain-record">交易实录<a class="record-history" href="javascript:void(0);" onclick="isTransactionMap();">历史记录 </a></div>
				<div class="complain-table">
					<table align="center"  id="message_table" style="width:100%">
						<tr align="center">
							<td align="center">
								<a hef="javascript:void(0);" onclick="isTransactionMap();" class="record-to-shop">用户与商家的消息记录</a>
							</td>
						</tr>
						<tr>
							<td height="50">
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</body>
	<script src="${pageContext.request.contextPath }/js/complain.js"></script>
	<script type="text/javascript">
	open();
	</script>
</html>
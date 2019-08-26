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

<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
<meta name="keywords" content="小间物业,小间物业管理系统" />
<meta name="Description" content="小间物业,小间物业管理系统" />
<title>发放帮帮券_间物业管理系统</title>

<script src="${pageContext.request.contextPath }/js/calendar.js"></script>
<link href="${pageContext.request.contextPath }/css/calendar.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	
	var bangBangMap = {};
	
	var bangBangList;
	
	function getBonusList(){
		var path = "<%= path%>/api/v1/communities/"+communityId+"/bonuses";
		
			$.ajax({

				url: path,

				type: "GET",

				dataType:'json',

				success:function(data){
					
					console.info(data);
					if(data.status == "yes"){
						bangBangGetUserCount();
						$("#bangbang_select").html("");
						data = data.info;
						bangBangList = data;
						for(var i = 0 ; i < data.length ; i++){
							bonusId = data[i].bonusId;
							bonusName = data[i].bonusName;
							bonusDetail = data[i].bonusDetail;
							bangBangMap[bonusId] = data[i].bonusPar;
							$("#bangbang_select").append("<option value = \""+i+"\">"+bonusName+" "+bonusDetail+"</option>");
						/*	if(i == 0){
								document.getElementById("bangbang_send_par").value = "";
								document.getElementById("bangbang_send_par").value = data[0].bonusPar;
								$("#bangbang_send_par_sum").html("");
								$("#bangbang_send_par_sum").html(data[0].bonusPar);
								
								$("#bangbang_send_bangbang_name").html("");
								$("#bangbang_send_bangbang_name").html(bonusName+ " "+bonusDetail);
							}*/
						}
					}
					
					
				},

				error:function(er){
			
					alert(er);
			
				}

			});
	}
	
	function bangBangGetUserCount(){
		
		var path = "<%= path%>/api/v1/communities/"+communityId+"/bonuses/getUserCount";
	
		$.ajax({

				url: path,

				type: "GET",

				dataType:'json',

				success:function(data){
					console.info(data);
					if(data.status == "yes"){
						data = data.info;
						$("#bangbang_send_user_sum").html("");
						$("#bangbang_send_user_sum").html(data);
					}
					
				},

				error:function(er){
			
					alert(er);
			
				}

			});
	}
	
	function bangbang_select_onchange(){
		var send_bangbang_select_value = $("#bangbang_select option:selected").val();
		//document.getElementById("bangbang_send_par").value = bangBangMap[send_bangbang_select_value];
		$("#bangbang_send_par_sum").html("");
		$("#bangbang_send_par_sum").html(bangBangMap[send_bangbang_select_value]);
		var bangbang_send_bangbang_name = $("#bangbang_select option:selected").html();
		$("#bangbang_send_bangbang_name").html("");
		$("#bangbang_send_bangbang_name").html(bangbang_send_bangbang_name);
		
		
	}
	
	function send_bangbang_submit(){
	
	   var send_value=  document.getElementById("send_bonus_assign_value").value;
	   // bangbang_send_bangbang_sub();
		var path = "<%= path%>/api/v1/communities/"+communityId+"/bonuses/userBonus";
		
		var bangbang_send_start_time = $("#bangbang_send_start_time").val();
		var	bangbang_send_end_time = $("#bangbang_send_end_time").val();
		
		if(bangbang_send_start_time == ""||bangbang_send_end_time == ""){
			alert("时间不能为空");
			return false;
		}
		
		var bangbang_message_radio = $(':radio[name="bangbang_send_radio"]:checked').val(); 
		
		var send_bangbang_select_value = $("#bangbang_select option:selected").val();
		
	    var	messageValue = $("#send_bangbang_message_value").val();
	    	bonusId = bangBangList[send_bangbang_select_value].bonusId;
	    	bonusType = bangBangList[send_bangbang_select_value].bonusType;
		
		if(messageValue==""){
		 var bonusPay = 	bangBangMap[bonusId];
		    messageValue="报告帮主，在本次活动中，您获得了一张价值为"+bonusPay+"元的帮帮券一张，您可以通过点击屏幕下方“我的”中的“帮帮券”一项进行查看，还请帮主速速使用！ ";
		}
		var myJson = {
			"bonusId":bonusId ,
			"startTimeStr":bangbang_send_start_time ,
			"expireTimeStr":bangbang_send_end_time ,
			"messageValue":messageValue ,
			"messageIsSend":bangbang_message_radio,
			"phoneList":send_value,
			"bonusType":bonusType
		};
		
		$.ajax({

				url: path,

				type: "POST",
				
				data:JSON.stringify(myJson),

				dataType:'json',

				success:function(data){
					console.info(data);
					if(data.status == "yes"){
						alert("成功");
					}
					
				},

				error:function(er){
			
					alert(er);
			
				}

			});	
	}
	
	function send_bonus_assign_open(){
	
	   document.getElementById("send_bonus_assign").style.display="";
	}
	function send_bonus_assign_off(){
	   document.getElementById("send_bonus_assign").style.display="none";
	}
	//日历控件
    $(function () {
        $("#bangbang_send_start_time").calendar({
            //controlId: "divDate",                                 // 弹出的日期控件ID，默认: $(this).attr("id") + "Calendar"
            speed: 200,                                           // 三种预定速度之一的字符串("slow", "normal", or "fast")或表示动画时长的毫秒数值(如：1000),默认：200
            complement: true,                                     // 是否显示日期或年空白处的前后月的补充,默认：true
            readonly: true,                                       // 目标对象是否设为只读，默认：true
          //  upperLimit: new Date(),                               // 日期上限，默认：NaN(不限制)
            lowerLimit: new Date("2011/01/01"),                   // 日期下限，默认：NaN(不限制)
            callback: function () {                               // 点击选择日期后的回调函数
             //   alert("您选择的日期是：" + $("#txtBeginDate").val());
            }
        });
        $("#bangbang_send_end_time").calendar({
            //controlId: "divDate",                                 // 弹出的日期控件ID，默认: $(this).attr("id") + "Calendar"
            speed: 200,                                           // 三种预定速度之一的字符串("slow", "normal", or "fast")或表示动画时长的毫秒数值(如：1000),默认：200
            complement: true,                                     // 是否显示日期或年空白处的前后月的补充,默认：true
            readonly: true,                                       // 目标对象是否设为只读，默认：true
          //  upperLimit: new Date(),                               // 日期上限，默认：NaN(不限制)
            lowerLimit: new Date(),                   // 日期下限，默认：NaN(不限制)
            callback: function () {                               // 点击选择日期后的回调函数
             //   alert("您选择的日期是：" + $("#txtBeginDate").val());
            }
        });
    });
</script>
</head>
<body>
	<section>
		<div class="content clearfix" >
			<jsp:include flush="true" page="./bangbang_left.jsp"/>
			<div class="express-right-body">
				<div class="repair-title-line">
					<p class="bangbang-ticket-title">发放帮帮券</p>
				</div>
				<div class="send_bangbang">
					<div>
						<span>类型面额</span>
				    	<select name="bangbang_select" id="bangbang_select" onchange="bangbang_select_onchange();">
			    					
			    		</select>
					</div>
					<div class="send_bangbang_date">
						<span>使用期限</span>
						<input type="text" value="" id="bangbang_send_start_time" readonly="readonly" placeholder="开始时间" name="bangbang_send_start_time" />
						<label>&nbsp;&nbsp;至&nbsp;&nbsp;</label>
						<input type="text" value="" id="bangbang_send_end_time" readonly="readonly" placeholder="结束时间" name="bangbang_send_end_time" />
					</div>
					<div>
						<span>发放对象</span>
						<input type="radio" value="1" name="bangbang_send_radio" id="bangbang_send_radio" checked="checked" onclick="send_bonus_assign_off();"/><label>全部用户</label>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" value="2" name="bangbang_send_radio" id="bangbang_send_radio" onclick="send_bonus_assign_open();" /><label>指定用户</label>
					</div>
					<div id="send_bonus_assign" style="display: none;">
						<div class="send-bangbang-font"><span>请输入<b>电话号码</b>，多个号码请用<b>英文</b>的<b>逗号</b>隔开</span></div>
						<div><textarea class="send_bangbang_user" id="send_bonus_assign_value"></textarea></div>
						
					</div>
					<div>
						<div>
							<textarea class="send_bangbang_message" id="send_bangbang_message_value" placeholder="报告帮主，在本次活动中，您获得了一张价值为5元的帮帮券一张，您可以通过点击屏幕下方“我的”中的“帮帮券”一项进行查看，还请帮主速速使用！ "></textarea>
						</div>
					</div>
					<div class="send_bangbang_btn">
						<a href="javascript:void(0);" onclick="send_bangbang_submit();" id="send_bangbang_sutmit" name="send_bangbang_sutmit">立即发放</a>
					</div>
				</div>
			</div>
		</div>
	</section>
  </body>
</html>

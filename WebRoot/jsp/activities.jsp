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
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
<meta name="keywords" content="小间物业,小间物业管理系统" />
<meta name="Description" content="小间物业,小间物业管理系统" />
<title>等待审核的活动_小间物业管理系统</title>


<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->
<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>
<script type="text/javascript">
	
	$(function(){
		 
	});
	
	var activityThisPageNum = 1;
	
	var activityThisPageSize = 10;
	
	var activities_sum = 0;
	
	var activityIsgoing = 0;
	
	var activityIsreview = 0;
	
	var activityEnded = 0;
	
	var activitySum = 0;
	
	var activityPageSize = 0;
	
	var activityPageNum = 0;
	
	var activityPageFirst = 0;
	
	var activityPageLast = 0;
	
	var activityPageNext = 0;
	
	var activityPrev = 0;
	
	function getActivitiesList(activities_text){
	
		activities_sum = 0;
	
		activityIsgoing = 0;
	
		activityIsreview = 0;
	
		activityEnded = 0;
		$("#activities-list-ul").find("tr").remove();
		
		var activityId = 0;
		
		var activityTitle = "";
		
		var activityDetail = "";
		
		var emobIdOwner = "";
		
		var emobGroupId = "";
		
		var place = "";
		
		var status = "";
		
		var nickName = "";
		
		var createTime = 0;
		
		var communityId = 0;
		
		var phone = "";
		
		var room = "";
	
		if(activities_text == ""){
			path = "<%= path%>/api/v1/communities/${communityId}/activities/webIm?pageNum="+activityThisPageNum+"&pageSize="+activityThisPageSize;
		}else{
			path = "<%= path%>/api/v1/communities/${communityId}/activities/webIm/"+activities_text+"?pageNum="+activityThisPageNum+"&pageSize="+activityThisPageSize;
		}
		

		
		$.ajax({

			url: path,

			type: "GET",

			dataType:'json',

			success:function(data){
			
				console.info(data);
				
				var daohang = data.info;
				
				activitySum = daohang.rowCount; // 总条数
				
				activityPageFirst = daohang.first; // 首页
				
				activityPageLast = daohang.last; // 尾页
				
				activityPageNext = daohang.next; // 下一页
				
				activityPageNum = daohang.num; // 当前页
				
				activityPrev = daohang.prev; // 上一页
			
				pageList = data.info.pageData; 
				
				activityPageSize = daohang.pageCount; // 总页数
				
				$("#activityPageNum").html(activityPageNum);
				
				$("#activityPageSize").html(activityPageSize);
				
				$("#activitySum").html(activitySum);
				
				console.info(pageList);
				
				for(var i = 0 ; i < pageList.length ; i++){
				
					activityId = pageList[i].activityId;
					
					activityTitle = pageList[i].activityTitle;
		
					activityDetail = pageList[i].activityDetail;
					
					activityDetail = activityDetail.replace("'", "\\'");
					
					activityTitle = activityTitle.replace("'", "\\'");
		
					emobIdOwner = pageList[i].emobIdOwner;
		
					emobGroupId = pageList[i].emobGroupId;
		
					place = pageList[i].place;
					
					status = pageList[i].status;
					
					if(status == "ongoing"){
						activityIsgoing++;
					}else if(status == "review"){
						activityIsreview++;	
					}else if(status == "ended"){
						activityEnded++;
					}
		
					nickName = pageList[i].nickName;
		
					createTime = pageList[i].createTime;
		
					communityId = pageList[i].communityId;
		
					phone = pageList[i].phone;
		
					room = pageList[i].room;
					
					activities_sum++;
					
					re=/(\n(?=(\n+)))+/g;  
					
					activityDetail = activityDetail.replace(re,""); 
					
					re=/\r\n/;  
					
					activityDetail = activityDetail.replace(re,"\n"); 
					
					
					
					addLiForActivitiesListUl(status, activityId, activityTitle, activityDetail, emobGroupId, nickName, phone, room , emobIdOwner);
					
					if(i == pageList.length - 1){
						$("#activities_ongoing_sum").html(activityIsgoing+"");
						$("#activities_review_sum").html(activityIsreview+"");
						$("#activities_end_sum").html(activityEnded+"");
					}
					
				}
			},

			error:function(er){
			
				alert(er);
			
			}

		});


	}
	function addLiForActivitiesListUl(status , activityId , activityTitle , activityDetail , emobGroupId , nickName , phone , room , emobIdOwner){
		var activityDetailTemp = activityDetail.replace("\\'" , "'");
					
		var activityTitleTemp = activityTitle.replace("\\'" , "'");
		
		var activitesUl = $("#activities-list-ul");
		if(status == "ended"){
			if(activities_sum % 2 == 0){
				activitesUl.append("<tr class=\"odd\"><td><a href=\"${pageContext.request.contextPath }/jsp/activity/activity-detail.jsp\">"+activityTitleTemp+"</a></div></td><td><div title=\""+activityDetailTemp+"\" class=\"activity-detail\">"+activityDetailTemp+"</div></td><td><a href=\"javascript:void(0);\" onclick=\"showImage();\" title=\"点击看大图\"><img src=\"${pageContext.request.contextPath }/images/service/activity-img.jpg\"/></a></td><td class=\"activity-opeation\"><a id=\"activities_aid_"+activityId+"\" href=\"javascript:;\" onclick=\"updateActivitiesType("+activityId+",'review','"+emobIdOwner+"','"+activityTitle+"','"+activityDetail+"','"+emobGroupId+"')\" class=\"refuse\">关闭群组</a></td></tr>");
				//activitesUl.append("<li class=\"odd disable\"><span class=\"signed-shop-name\">发起人："+nickName+" 联系电话："+phone+"</span><span class=\"signed-shop-name\"><span>活动已完成</span><a href=\"javascript:;\"  onclick=\"deleteActivities("+activityId+",'"+emobGroupId+"');\" class=\"red-button activities-opt activities-del\">删除活动</a></li>");
			}else{
				activitesUl.append("<tr class=\"even\"><td><a href=\"${pageContext.request.contextPath }/jsp/activity/activity-detail.jsp\">"+activityTitleTemp+"</a></div></td><td><div title=\""+activityDetailTemp+"\" class=\"activity-detail\">"+activityDetailTemp+"</div></td><td><a href=\"javascript:void(0);\" onclick=\"showImage();\" title=\"点击看大图\"><img src=\"${pageContext.request.contextPath }/images/service/activity-img.jpg\"/></a></td><td class=\"activity-opeation\"><a id=\"activities_aid_"+activityId+"\" href=\"javascript:;\" onclick=\"updateActivitiesType("+activityId+",'review','"+emobIdOwner+"','"+activityTitle+"','"+activityDetail+"','"+emobGroupId+"')\" class=\"refuse\">关闭群组</a></td></tr>");
				//activitesUl.append("<li class=\"even disable\"><span class=\"signed-shop-name\">发起人："+nickName+" 联系电话："+phone+"</span><span class=\"signed-shop-name\"><span>活动已完成</span><a href=\"javascript:;\"  onclick=\"deleteActivities("+activityId+",'"+emobGroupId+"');\" class=\"red-button activities-opt activities-del\">删除活动</a></li>");
			}
		}else if(status == "ongoing"){
			if(activities_sum % 2 == 0){
				activitesUl.append("<tr class=\"odd\"><td><a href=\"${pageContext.request.contextPath }/jsp/activity/activity-detail.jsp\">"+activityTitleTemp+"</a></div></td><td><div title=\""+activityDetailTemp+"\" class=\"activity-detail\">"+activityDetailTemp+"</div></td><td><a href=\"javascript:void(0);\" onclick=\"showImage();\" title=\"点击看大图\"><img src=\"${pageContext.request.contextPath }/images/service/activity-img.jpg\"/></a></td><td class=\"activity-opeation\"><a id=\"activities_aid_"+activityId+"\" href=\"javascript:;\" onclick=\"updateActivitiesType("+activityId+",'review','"+emobIdOwner+"','"+activityTitle+"','"+activityDetail+"','"+emobGroupId+"')\" class=\"refuse\">关闭群组</a></td></tr>");
				//activitesUl.append("<li class=\"odd\"><span class=\"signed-shop-name\">发起人："+nickName+" 联系电话："+phone+"</span><a href=\"javascript:;\" class=\"signed-shop-name\"><b>"+activityTitleTemp+"</b> "+activityDetailTemp+"</a><span id=\"activities_spanid_"+activityId+"\">活动进行中</span><a id=\"activities_aid_"+activityId+"\" href=\"javascript:;\" onclick=\"updateActivitiesType("+activityId+",'review','"+emobIdOwner+"','"+activityTitle+"','"+activityDetail+"','"+emobGroupId+"')\" class=\"red-button activities-opt activities-hide\">屏蔽</a>  <a href=\"javascript:;\"  onclick=\"deleteActivities("+activityId+",'"+emobGroupId+"');\" class=\"red-button activities-opt activities-del\">删除活动</a>");
			}else{
				activitesUl.append("<tr class=\"even\"><td><a href=\"${pageContext.request.contextPath }/jsp/activity/activity-detail.jsp\">"+activityTitleTemp+"</a></div></td><td><div title=\""+activityDetailTemp+"\" class=\"activity-detail\">"+activityDetailTemp+"</div></td><td><a href=\"javascript:void(0);\" onclick=\"showImage();\" title=\"点击看大图\"><img src=\"${pageContext.request.contextPath }/images/service/activity-img.jpg\"/></a></td><td class=\"activity-opeation\"><a id=\"activities_aid_"+activityId+"\" href=\"javascript:;\" onclick=\"updateActivitiesType("+activityId+",'review','"+emobIdOwner+"','"+activityTitle+"','"+activityDetail+"','"+emobGroupId+"')\" class=\"refuse\">关闭群组</a></td></tr>");
				//activitesUl.append("<li class=\"even\"><span class=\"signed-shop-name\">发起人："+nickName+" 联系电话："+phone+"</span><a href=\"javascript:;\" class=\"signed-shop-name\"><b>"+activityTitleTemp+"</b> "+activityDetailTemp+"</a><span id=\"activities_spanid_"+activityId+"\">活动进行中</span><a id=\"activities_aid_"+activityId+"\" href=\"javascript:;\" onclick=\"updateActivitiesType("+activityId+",'review','"+emobIdOwner+"','"+activityTitle+"','"+activityDetail+"','"+emobGroupId+"')\" class=\"red-button activities-opt activities-hide\">屏蔽</a>  <a href=\"javascript:;\"  onclick=\"deleteActivities("+activityId+",'"+emobGroupId+"');\" class=\"red-button activities-opt activities-del\">删除活动</a>");
			}
			
		}else if(status == "review"){
			if(activities_sum % 2 == 0){
				activitesUl.append("<tr class=\"odd\"><td><a href=\"${pageContext.request.contextPath }/jsp/activity/activity-detail.jsp\">"+activityTitleTemp+"</a></div></td><td><div title=\""+activityDetailTemp+"\" class=\"activity-detail\">"+activityDetailTemp+"</div></td><td><a href=\"javascript:void(0);\" onclick=\"showImage();\" title=\"点击看大图\"><img src=\"${pageContext.request.contextPath }/images/service/activity-img.jpg\"/></a></td><td class=\"activity-opeation\"><a id=\"activities_aid_"+activityId+"\" href=\"javascript:;\" onclick=\"updateActivitiesType("+activityId+",'review','"+emobIdOwner+"','"+activityTitle+"','"+activityDetail+"','"+emobGroupId+"')\" class=\"refuse\">关闭群组</a></td></tr>");
				//activitesUl.append("<li class=\"odd disable\"><span class=\"signed-shop-name\">发起人："+nickName+" 联系电话："+phone+"</span><a href=\"javascript:;\" class=\"signed-shop-name\"><b>"+activityTitleTemp+"</b> "+activityDetailTemp+"</a><span id=\"activities_spanid_"+activityId+"\">待审核活动</span><a id=\"activities_aid_"+activityId+"\" href=\"javascript:;\" onclick=\"updateActivitiesType("+activityId+",'ongoing','"+emobIdOwner+"','"+activityTitle+"','"+activityDetail+"','"+emobGroupId+"')\" class=\"green-button activities-opt activities-start\">开启</a>  <a href=\"javascript:;\"  onclick=\"deleteActivities("+activityId+",'"+emobGroupId+"');\" class=\"red-button activities-opt activities-del\">删除活动</a>");
			}else{
				activitesUl.append("<tr class=\"even\"><td><a href=\"${pageContext.request.contextPath }/jsp/activity/activity-detail.jsp\">"+activityTitleTemp+"</a></div></td><td><div title=\""+activityDetailTemp+"\" class=\"activity-detail\">"+activityDetailTemp+"</div></td><td><a href=\"javascript:void(0);\" onclick=\"showImage();\" title=\"点击看大图\"><img src=\"${pageContext.request.contextPath }/images/service/activity-img.jpg\"/></a></td><td class=\"activity-opeation\"><a id=\"activities_aid_"+activityId+"\" href=\"javascript:;\" onclick=\"updateActivitiesType("+activityId+",'review','"+emobIdOwner+"','"+activityTitle+"','"+activityDetail+"','"+emobGroupId+"')\" class=\"refuse\">关闭群组</a></td></tr>");
				//activitesUl.append("<li class=\"even disable\"><span class=\"signed-shop-name\">发起人："+nickName+" 联系电话："+phone+"</span><a href=\"javascript:;\" class=\"signed-shop-name\"><b>"+activityTitleTemp+"</b> "+activityDetailTemp+"</a><span id=\"activities_spanid_"+activityId+"\">待审核活动</span><a id=\"activities_aid_"+activityId+"\" href=\"javascript:;\" onclick=\"updateActivitiesType("+activityId+",'ongoing','"+emobIdOwner+"','"+activityTitle+"','"+activityDetail+"','"+emobGroupId+"')\" class=\"green-button activities-opt activities-start\">开启</a>  <a href=\"javascript:;\" onclick=\"deleteActivities("+activityId+",'"+emobGroupId+"');\" class=\"red-button activities-opt activities-del\">删除活动</a>");
			}
		}
		
	}
	
	function deleteActivities(id,emobGroupId){
		if(emobGroupId == "null"){
			console.info("群根本还没开启");
			updateActivitiesStatus(id,"rejected");
			getActivitiesList("");
		}else{
			var path = "<%= path%>/api/v1/communities/${communityId}/activities/webIm/deleteActivity/"+id;
			var myJon = "{\"activityId\":"+id+" , \"emobGroupId\":"+emobGroupId+"}";
			$.post(path , myJon , function(data){
				if(data.status == "yes"){
					getActivitiesList("");
				}
			},"json");
		}
	}
	
	function updateActivitiesType(id,status,emob_id,activityTitle,activityDetail,emobGroupId){
		alert(1);
		activityRefuse();
		if(status == "review"){
			
			$("#activities_spanid_"+id+"").html("待审核活动");
			$("#activities_aid_"+id+"").html("开通群组");
			$("#activities_aid_"+id+"").removeClass();
			$("#activities_aid_"+id+"").addClass("open");
			var sum = $("#activities_review_sum").html();
			$("#activities_review_sum").html(parseInt(sum)+1);
			var sum2 = $("#activities_ongoing_sum").html();
			$("#activities_ongoing_sum").html(parseInt(sum2)-1);
			
			$("#activities_aid_"+id+"").attr("onclick", "updateActivitiesType("+id+",'ongoing','"+emob_id+"','"+activityTitle+"','"+activityDetail+"','"+emobGroupId+"')");
			
			updateActivitiesStatus(id, status);
			
			
		}else if(status == "ongoing"){
			$("#activities_spanid_"+id+"").html("活动进行中");
			$("#activities_aid_"+id+"").html("关闭群组");
			$("#activities_aid_"+id+"").removeClass();
			$("#activities_aid_"+id+"").addClass("refuse");
			var sum = $("#activities_ongoing_sum").html();
			$("#activities_ongoing_sum").html(parseInt(sum)+1);
			var sum2 = $("#activities_review_sum").html();
			$("#activities_review_sum").html(parseInt(sum2)-1);
			
			$("#activities_aid_"+id+"").attr("onclick", "updateActivitiesType("+id+",'review','"+emob_id+"','"+activityTitle+"','"+activityDetail+"','"+emobGroupId+"')");
			
			var activityDetailTemp = activityDetail.replace("\\'" , "'");
					
			var activityTitleTemp = activityTitle.replace("\\'" , "'");
			if(emobGroupId == "null" || emobGroupId == ""){
				var path = "<%= path%>/api/v1/communities/${communityId}/users/"+emob_id+"/activities/"+id;
				var myJson = "{ \"groupname\": \""+activityTitleTemp+"\"  ,  \"desc\": \""+activityDetailTemp+"\" ,  \"ispublic\": 1  , \"maxusers\" : 500 , \"approval\" : 0 , \"owner\" : "+emob_id+"  }";
				$.ajax({

					url: path,

					type: "PUT",
			
					data: myJson ,

					dataType:'json',

					success:function(data){
						console.info(data);
						getActivitiesList("");
					},

					error:function(er){
			
						alert(er);
			
					}

				});
			}else{
				updateActivitiesStatus(id, status);
			}
			
		
		}
		
		
	}
	
	
	function updateActivitiesStatus(id,status){
		var path = "<%= path%>/api/v1/communities/${communityId}/activities/webIm/"+id;
			//	var myJson = "{ \"groupname\": \""+activityTitle+"\"  ,  \"desc\": \""+activityDetail+"\" ,  \"ispublic\": true  , \"maxusers\" : 500  \"approval\" : true   }";
				$.ajax({

					url: path,

					type: "PUT",
			
					data: status ,

					dataType:'json',

					success:function(data){
						console.info(data);
					},

					error:function(er){
			
						alert(er);
			
					}

				});
	}
	
	function activitiesSumLoaing(normalSum,suspendSum,blockSum){
		$("#activities_suspend_sum").html(suspendSum);
		$("#activities_normal_sum").html(normalSum);
		$("#activities_block_sum").html(blockSum);
	}
	
	function activities_search(){
		var activities_search_text = $("#activities_search_text").val();
		if(activities_search_text != "" && activities_search_text != null){
			getActivitiesList(activities_search_text);
		}
	}
	
	function getMyActivityList(num){
		if(num == -1){ // 上一页
			if(activityThisPageNum != 1){
				activityThisPageNum = activityPrev;
				getActivitiesList("");
			}
			
		}else if(num == -2){ // 下一页
			if(activityThisPageNum < activityPageSize){
				activityThisPageNum = activityPageNext;
				getActivitiesList("");
			}
			
		}else if(num == -3){ // 首页
			if(activityThisPageNum != activityPageFirst){
				activityThisPageNum = activityPageFirst;
				getActivitiesList("");
			}
		}else if(num == -4){ // 尾页
			if(activityThisPageNum != activityPageLast){
				activityThisPageNum = activityPageLast;
				getActivitiesList("");
			}
		}
	}
	
	function activityRefuse(){
		$(".activity-refuse").show();
		$(".activity-blank-bg").show();
	}
	function activityRefuseClose(){
		$(".activity-refuse").hide();
		$(".activity-blank-bg").hide();
	}
	function showImage(){
		$("#activity_image").show();
		$(".activity-blank-bg").show();
		var width = $("#activity_image img").width();
		var height = $("#activity_image img").height();
		$("#activity_image").css("margin-left",-(width)/2);
		$("#activity_image").css("margin-top",-(height)/2);
	}
	function hideImage(){
		$("#activity_image").hide();
		$(".activity-blank-bg").hide();
	}
</script>
</head>
<body>

<div class="content clearfix" style="border:none;background:none;">
	<jsp:include flush="true" page="./public/activity_left.jsp"></jsp:include>
	<div class="activity-right-body">
		<div class="activity-search">
			<input class="activities-search" type="text" value="" onkeydown="if(event.keyCode==13){activities_search();}" id="activities_search_text" placeholder="搜索  活动/话题 "/>
			<a href="javascript:;" onclick="activities_search()" >搜索</a>
		</div>
		<div class="activity-check">
			<table id="activities-list-ul" >
				
			</table>
		</div>
		<div class="divide-page">
			<a href="javascript:void(0);" onclick="getMyActivityList(-3);">首页</a>
			<a href="javascript:void(0);" onclick="getMyActivityList(-1);">上一页</a>
			当前第 <font id="activityPageNum"></font> 页
			共 <font id="activityPageSize"></font> 页
			共 <font id="activitySum"></font> 条
			
			<a href="javascript:void(0);" onclick="getMyActivityList(-2);">下一页</a>
			<a href="javascript:void(0);" onclick="getMyActivityList(-4);">尾页</a>
		</div>
	</div>
</div>
<div class="activity-image" id="activity_image" style="display:none;">
	<div class="activity-img-title"><span>第二届社区拔河比赛</span></div>
	<a href="javascript:hideImage();">&nbsp;</a>
	<img alt="" src="${pageContext.request.contextPath }/images/service/big.jpg"/>
</div>
<div class="activity-blank-bg" style="display:none;">&nbsp;</div>
<div class="activity-refuse"  style="display: none;z-index:300;" id="signed-creat-box">
	<div>
		<textarea id="reason" rows="" cols="" placeholder="请输入关闭理由"></textarea>
	</div> 
	<div class="activity-check-opt">
		<a href="javascript:activityRefuseClose();">取消</a>
		<a href="javascript:void(0);" onclick="applyFor();">确认并发通知</a>
	</div>
</div>


	<!--<div class="signed-total">
		<span><b id="activities_ongoing_sum">loading...</b> 正在进行</span>
		<span><b id="activities_review_sum">loading...</b> 待审核</span>
		<span><b id="activities_end_sum">loading...</b> 已完成</span>
	</div>
	<div class="activities-search">
		<input class="activities-search" type="text" value="" id="activities_search_text" placeholder="搜索活动 "/>
		<a href="javascript:;" onclick="activities_search()" class="green-button">搜索</a>
	
	</div>
	<div class="activities-list">
		<ul id="activities-list-ul">
			
		</ul>
	</div>
				
	<div style="float: left; width:  100% ; height: 50px; clear: both; text-align: center;">
	<a href="javascript:void(0);" onclick="getMyActivityList(-3);">首页</a>
	<a href="javascript:void(0);" onclick="getMyActivityList(-1);">上一页</a>
	当前第 <font id="activityPageNum"></font> 页
	共 <font id="activityPageSize"></font> 页
	共 <font id="activitySum"></font> 条
	
	<a href="javascript:void(0);" onclick="getMyActivityList(-2);">下一页</a>
	<a href="javascript:void(0);" onclick="getMyActivityList(-4);">尾页</a>
	</div>
--></body>


</html>
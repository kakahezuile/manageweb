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
		<link
			href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>"
			type="image/x-icon" rel="shortcut icon">
		<link
			href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<link
			href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<link
			href="${pageContext.request.contextPath }/css/calendar.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>
		<script src="${pageContext.request.contextPath }/js/jquery-1.6.js"></script>
		<script src="${pageContext.request.contextPath }/js/calendar.js"></script>


<%


String status=request.getParameter("status");
 %>
<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>
<script type="text/javascript">
	
   var communityId = window.parent.document
			.getElementById("community_id_index").value;
	
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
	
	function getActivitiesList(pageNum,activities_text,status2){
	   scheduleAll();
	   document.getElementById("activity_status_ongoing_rejected").value=status2;
		activities_sum = 0;
	
		activityIsgoing = 0;
	
		activityIsreview = 0;
	
		activityEnded = 0;
		$("#activities-list-table").empty();
		
		var activityId = 0;
		
		var activityTitle = "";
		
		var activityDetail = "";
		
		var emobIdOwner = "";
		
		var emobGroupId = "";
		
		var place = "";
		
		var status = "";
		
		var nickName = "";
		
		var createTime = 0;
		
		
		var phone = "";
		
		var room = "";
	
		path = "<%= path%>/api/v1/communities/"+communityId+"/lifeCircle/selectLifeCircleList?startTime=2015-6-1 00:00:00&endTime=2015-7-17 00:00:00&type=time";
		

		
		$.ajax({

			url: path,

			type: "GET",

			dataType:'json',

			success:function(data){
		    	onScheduleAll();
				console.info(data);
				
				var pageList = data.info;
			
				
			
				
				for(var i = 0 ; i <pageList.length ; i++){
				
					activityId = pageList[i].lifeCircleId;
					
					activityTitle = pageList[i].lifeContent;
		
					activityDetail = pageList[i].lifeContent;
					
					activityDetail = activityDetail.replace("'", "\\'");
					
					activityTitle = activityTitle.replace("'", "\\'");
		
					
		
					nickName = pageList[i].nickname;
		
					createTime = pageList[i].createTime;
		
		
		
					room = pageList[i].room;
					var listPono=pageList[i].list;
					activities_sum++;
					
					re=/(\n(?=(\n+)))+/g;  
					
					activityDetail = activityDetail.replace(re,""); 
					
					re=/\r\n/;  
					
					activityDetail = activityDetail.replace(re,"\n"); 
					var array_element_ar="";
						for ( var j = 0; j < listPono.length; j++) {
				
					var array_element = listPono[j];
					//alert(array_element.photoUrl);
					array_element_ar+=	"<span class=\"images\"><a href=\"javascript:void(0);\" ><img src=\""+array_element.photoUrl+"\"/></a></span>";
					
				}

					addLiForActivitiesListUl('ongoing', activityId, activityTitle, activityDetail,  nickName,pageList[i].userFloor,pageList[i].userUnit,  room , array_element_ar);
					
					if(i == pageList.length - 1){
						$("#activities_ongoing_sum").html(activityIsgoing+"");
						$("#activities_review_sum").html(activityIsreview+"");
						$("#activities_end_sum").html(activityEnded+"");
					}
					
				}
			},

			error:function(er){
			    onScheduleAll();
			
			}

		});


	}
	function addLiForActivitiesListUl(status , activityId , activityTitle , activityDetail ,  nickName ,userFloor,userUnit, room , array_element_ar){
	//document.getElementById("activity_Title"+activityId).value=activityTitle;
	    
		var activityDetailTemp = activityDetail.replace("\\'" , "'");
					
		var activityTitleTemp = activityTitle.replace("\\'" , "'");
		
		var activitesUl = $("#activities-list-table");
		var activity_Title = $("#activity_Title");
		
		activity_Title.append("<input id=\"activity_Title"+activityId+"\" value=\""+activityTitle+"\" type=\"hidden\"/>");
		 if(status == "ongoing"){
			if(activities_sum % 2 == 0){
			
			
				activitesUl.append("<tr  class=\"odd\"><td class=\"lifecircle-title-td\"><div class=\"lifecircle-title-div\"><a  href=\"<%=path %>/jsp/estate/lifecircle/lifecircle-detail.jsp?activitiesId="+activityId+"\">"+activityTitleTemp+"</a></div></td><td><div title=\""+activityDetailTemp+"\" class=\"lifecircle-user\"><p>"+nickName+"</p><p>"+userFloor+userUnit+room+"</p></div></td><td class=\"pic\">"
			+array_element_ar+
				
				"</td></tr>");
			
			
			
			
			}else{
			
				activitesUl.append("<tr class=\"even\"><td class=\"lifecircle-title-td\"><div class=\"lifecircle-title-div\"><a  href=\"<%=path %>/jsp/estate/lifecircle/lifecircle-detail.jsp?activitiesId="+activityId+"\">"+activityTitleTemp+"</a></div></td><td><div title=\""+activityDetailTemp+"\" class=\"lifecircle-user\"><p>"+nickName+"</p><p>"+userFloor+userUnit+room+"</p></div></td><td class=\"pic\">"
			+array_element_ar+
				
				"</div></td></tr>");
			}
			
		}else if(status=="rejected"){
		
		    if(activities_sum % 2 == 0){
				activitesUl.append("<tr class=\"odd\"><td class=\"lifecircle-title-td\"><div class=\"lifecircle-title-div\"><a  href=\"<%=path %>/jsp/estate/lifecircle/lifecircle-detail.jsp?activitiesId="+activityId+"\">"+activityTitleTemp+"</a></div></td><td><div title=\""+activityDetailTemp+"\" class=\"lifecircle-user\"><p>刘文静</p><p>3号楼2单元102</p></div></td><td class=\"pic\">"
				
				+array_element_ar+
				
				
				"</td></tr>");
			}else{
				activitesUl.append("<tr class=\"even\"><td class=\"lifecircle-title-td\"><div class=\"lifecircle-title-div\"><a  href=\"<%=path %>/jsp/estate/lifecircle/lifecircle-detail.jsp?activitiesId="+activityId+"\">"+activityTitleTemp+"</a></div></td><td><div title=\""+activityDetailTemp+"\" class=\"lifecircle-user\"><p>刘文静</p><p>3号楼2单元102</p></div></td><td class=\"pic\">"
				
				
				+array_element_ar+
				
				
				"</td></tr>");
			}
		}
		
	}
	
	function deleteActivities(id,emobGroupId){
	
	 var activityTitle=document.getElementById("activity_Title"+id).value;
	if(confirm(" 是否关闭\n"+activityTitle+"\n此操作不可逆！")){
	 
		if(emobGroupId == "null"){
			console.info("群根本还没开启");
			updateActivitiesStatus(id,"rejected");
			getActivitiesList("");
		}else{
			var path = "<%= path%>/api/v1/communities/"+communityId+"/activities/webIm/deleteActivity/"+id;
			var myJon = "{\"activityId\":"+id+" , \"emobGroupId\":"+emobGroupId+",\"activityTitle\":\""+activityTitle+"\"}";
			$.post(path , myJon , function(data){
				if(data.status == "yes"){
					//getActivitiesList("");
					getActivitiesList(1,"","ongoing");
				}
			},"json");
		}
	
	
	}
	
	}
	
	function updateActivitiesType(id,status){
		if(status == "rejected"){
			updateActivitiesStatus(id, status);
				getActivitiesList(1,"","ongoing");
		}else if(status == "ongoing"){
	       	updateActivitiesStatus(id, status);
	       	getActivitiesList(1,"","rejected");
		}else{
				updateActivitiesStatus(id, status);
		}
			
		
		
		
	}
	
	
	function updateActivitiesStatus(id,status){
		var path = "<%= path%>/api/v1/communities/"+communityId+"/activities/webIm/"+id;
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
			
			
					}

				});
	}
	
	function activitiesSumLoaing(normalSum,suspendSum,blockSum){
		$("#activities_suspend_sum").html(suspendSum);
		$("#activities_normal_sum").html(normalSum);
		$("#activities_block_sum").html(blockSum);
	}
	
	function activities_search(){
	   var status=	document.getElementById("activity_status_ongoing_rejected").value;
		var activities_search_text = $("#activities_search_text_detil").val();
			getActivitiesList(1,activities_search_text,status);
	}
	
	function getMyActivityList(num){
	    var status=	document.getElementById("activity_status_ongoing_rejected").value;
		var activities_search_text = $("#activities_search_text_detil").val();
		if(num == -1){ // 上一页
			if(activityThisPageNum != 1){
				activityThisPageNum = activityPrev;
			}else{
			 alert("已经是第一页了");
			 return;
			}
			
		}else if(num == -2){ // 下一页
			if(activityThisPageNum < activityPageSize){
				activityThisPageNum = activityPageNext;
			}else{
			 alert("已经是 最后一页了");
			 return;
			}
			
		}else if(num == -3){ // 首页
			if(activityThisPageNum != activityPageFirst){
				activityThisPageNum = activityPageFirst;
			}else{
			 alert("已经是首页了");
			 return;
			}
		}else if(num == -4){ // 尾页
			if(activityThisPageNum != activityPageLast){
				activityThisPageNum = activityPageLast;
			}else{
			 alert("已经是尾页了");
			 return;
			}
		}
		getActivitiesList(activityThisPageNum,activities_search_text,status);
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
	
	function activityDetail(activitiesId){
	    setNone();
	    $("#activity_detail_jsp").attr("style", "display:block");
	    getActivitiesDetail(activitiesId);
	}
	
	function scheduleAll(){
		$("#schedule_all").attr("style","display:block");
		$("#bg_schedule").attr("style","display:block");
		
	}
	
	function onScheduleAll(){
		$("#schedule_all").attr("style","display:none");
		$("#bg_schedule").attr("style","display:none");
	}
</script>
</head>
<body>
<div id="activity_Title" style="display: none;">

</div>
  

		<div class="loadingbox" id="schedule_all" style="display: none;">
		<img alt="" src="<%= basePath %>/images/chat/loading.gif">
	</div>
	<div class="upload-master-face-bg" id="bg_schedule" style="display: none;">&nbsp;</div>
	


<input id="activity_status_ongoing_rejected" type="hidden" value=""/>
<div class="content clearfix">
	<jsp:include flush="true" page="../common/activity_left.jsp"></jsp:include>
	<div class="activity-right-body">
		<!--<div class="activity-search">
			<input class="activities-search" onkeydown="if(event.keyCode==13){activities_search();}" type="text" value="" id="activities_search_text_detil" placeholder="搜索  生活圈/用户名 "/>
			<a href="javascript:;" onclick="activities_search()" >搜索</a>
		</div>
		--><div class="activity-check">
			<table id="activities-list-table" >
			</table>
		</div>
		<!--<div   class="divide-page"
		 style="float: left; width:  100% ; height: 50px; clear: both; text-align: center;">
			<a href="javascript:void(0);" onclick="getMyActivityList(-3);">首页</a>
			<a href="javascript:void(0);" onclick="getMyActivityList(-1);">上一页</a>
			当前第 <font id="activity_Page_Num"></font> 页
			共 <font id="activity_Page_Size"></font> 页
			共 <font id="activity_Sum"></font> 条
			
			<a href="javascript:void(0);" onclick="getMyActivityList(-2);">下一页</a>
			<a href="javascript:void(0);" onclick="getMyActivityList(-4);">尾页</a>
		</div>
	-->
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
</body>
<script type="text/javascript">
getActivitiesList(1,"","<%=status%>");

activity_left_List("activity_left_lifecircle");
</script>
</html>
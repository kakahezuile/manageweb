<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String webTitle = properties.getProperty("webTitle"); 
%>
<%


String activitiesId=request.getParameter("activitiesId");
String emob_id_to=request.getParameter("emob_id_to");
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
		<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>
		<script src="${pageContext.request.contextPath }/js/jquery-1.6.js"></script>


</head>
<body>

	<section>
		<div class="content clearfix">
			<jsp:include flush="true" page="../common/activity_left.jsp"></jsp:include>
			
			<input type="button" value="屏蔽" onclick="pingbi()"/>
			<div class="activity-right-body"  id="activity_detail_id">
				<!--
				<div class="activity-head">
					<div class="activity-title">
						一起去踢球
					</div>
					<div class="activity-info">
						<p>时间：<span> 4月22日  6：32</span></p>
						<p>地点：<span>  东城区法华南里36号</span></p>
					</div>
				</div>
			
				<div class="activity-content">
					<!--<p>活动是由共同目的联合起来并完成一定社会职能的动作的总和。活动由目的、动机和动作构成，具有完整的结构系统。苏联心理学家从20年代起就对活动进行了一系列研究。其中Α.Н.列昂节夫的活动理论对苏联心理学的发展影响很大，成为现代苏联心.活动是由共同目的联合起来并完成一定社会职能的动作的总和。活动由目的、动机和动作构成，具有完整的结构系统。苏联心理学家从20年代起就对活动进行了一系列研究。其中Α.Н.列昂节夫的活动理论对苏联心理学的发展影响很大，成为现代苏联心.</p>
					<p class="activity-pic"><img src="${pageContext.request.contextPath }/images/upload-img.jpg"/></p>
					<p class="activity-pic"><img src="${pageContext.request.contextPath }/images/upload-img-preview.jpg"/></p>
				</div>
				-->
			</div>
		</div>
		<script type="text/javascript">
			var communityId = window.parent.document
			.getElementById("community_id_index").value;
	      function getActivitiesDetail(activitiesId){
	            var path = "<%= path%>/api/v1/communities/1/lifeCircle/getLifeCireDelit?life_circle_id="+activitiesId;
				$.ajax({

					url: path,

					type: "GET",
			

					dataType:'json',

					success:function(data){
					data=data.info;
					var activitesDate=$("#activity_detail_id");
					//var activityStatus=$("#activity_status_ongoing_rejected").val();
					var activityPower = "";
					
					
					
					var myDate=new Date(data.createTime*1000);
					
					
					 var activitesDatail="<div class=\"activity-head\">"+activityPower+"";
					 activitesDatail+="<div class=\"activity-title\">";
					 activitesDatail+=data.nickname+"("+data.userFloor+data.userUnit+")";
					 activitesDatail+="</div>";
					 activitesDatail+="<div class=\"activity-info\">";
					 activitesDatail+="<p>时间：<span> "+myDate.getFullYear()+"-"+(myDate.getMonth()+1)+"-"+myDate.getDate()+"</span></p>";
					 activitesDatail+="</div></div>";
					 activitesDatail+="<div class=\"lifecircle-content\" id=\"test_test\">";
					 activitesDatail+="<p>"+data.lifeContent+"</p>";
					 var list=data.list;
					 for ( var i = 0; i < list.length; i++) {
						 activitesDatail+="<p class=\"lifecircle-pic\"><img src=\""+list[i].photoUrl+"\"/></p>";
					 }
					
					
					 
					activitesDate.append(activitesDatail);
					lifecirel("<%=activitiesId%>",data.nickname);
					document.getElementById("tile_del").value=data.activityTitle;
			
					},

					error:function(er){
			
			
					}

				});
	      
	      }	
	      
	      
	      
	      
	      
	      
	     function examine(id,lifeCircleId){
		
		    var path = "<%= path%>/api/v1/communities/"+communityId+"/lifeCircle/upStatus?id="+id+"&status=check" ;
			
			$.ajax({

				url: path,

				type: "GET",
				dataType:"json",

				success:function(data){
		              	window.location="/jsp/estate/lifecircle/lifecircle-detail.jsp?activitiesId="+lifeCircleId;	
				},

				error:function(er){
			
			
				}

			});
		}
	      
	      function lifecirel(emob_id_to,thisName){
	        var path = "<%= path%>/api/v1/communities/1/lifeCircle/getLifeCircleDetail?emob_id_to="+emob_id_to;
				$.ajax({

					url: path,

					type: "GET",
			

					dataType:'json',

					success:function(data){
					data=data.info;
					var activitesDate=$("#test_test");
					 var activitesDatail="";
					 for ( var i = 0; i < data.length; i++) {
					 if(data[i].nicknameTo==thisName){
					 
					   activitesDatail+="<p class=\"clearfix\">";
					    activitesDatail+="<p><span>"+data[i].nicknameFrom+"</span>："+data[i].detailContent+"</p>";
					 }else{
					   activitesDatail+="<p class=\"clearfix\">";
					    activitesDatail+="<p><span>"+data[i].nicknameFrom+"  回复  "+data[i].nicknameTo+"</span>："+data[i].detailContent+"</p>";
					 }
						
					   
					
					}
					
					activitesDate.append(activitesDatail);
			
					},

					error:function(er){
			
			
					}

				});
	      
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
						alert("成功");
						 getActivitiesDetail("<%=activitiesId%>");
					},

					error:function(er){
			
			
					}

				});
	}
	
	
	function pingbi(){
	        var path = "<%= path%>/api/v1/communities/1/lifeCircle/pingbi/<%=activitiesId%>";
				$.ajax({

					url: path,

					type: "GET",
			
					data: status ,

					dataType:'json',

					success:function(data){
					alert("成功");
					},

					error:function(er){
			
			
					}

				});
	
	}
	
		function deleteActivities(id,emobGroupId){
	
	    var activityTitle=document.getElementById("tile_del").value;
	    if(confirm(" 是否关闭\n"+activityTitle+"\n此操作不可逆！")){
	    var activityTitle=document.getElementById("tile_del").value;
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
		
			function activityRefuse(){
				$("#activity-refuse").show();
				$(".activity-blank-bg").show();
			}
			function activityRefuseClose(){
				$("#activity-refuse").hide();
				$(".activity-blank-bg").hide();
			}
		</script>
	</section>
</body>
<input id="tile_del" value="" type="hidden"/> 
<div class="activity-image" id="activity_image" style="display:none;">
	<div class="activity-img-title"><span>第二届社区拔河比赛</span></div>
	<a href="javascript:hideImage();">&nbsp;</a>
	<img alt="" src="${pageContext.request.contextPath }/images/service/big.jpg"/>
</div>
<div class="activity-blank-bg" style="display:none;">&nbsp;</div>
<div class="activity-refuse" id="activity-refuse" style="display:none;">
	<div>
		<textarea rows="" cols="">请输入关闭理由</textarea>
	</div> 
	<div class="activity-check-opt">
		<a href="javascript:activityRefuseClose();">取消</a>
		<a href="javascript:void(0);">发送后台通知</a>
	</div>
</div>
<script type="text/javascript">

 getActivitiesDetail("<%=activitiesId%>");
 activity_left_List("activity_left_lifecircle");
</script>
</html>
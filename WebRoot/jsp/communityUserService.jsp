<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String webTitle = properties.getProperty("webTitle"); 
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>为用户添加模块</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->
<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>
<script src="${pageContext.request.contextPath }/js/jquery-1.6.js"></script>
<script src="${pageContext.request.contextPath }/js/admin/admin_community.js"></script>
<style type="text/css">
	.communityUserServiceMain{
		width: 100%;
		height: 100%;
	}
	.communityUserServiceLeft{
		width: 15%;
		height: 100%;
		float: left;
		border: solid 1px #D6D6D6;
		overflow-Y:scroll;
	}
	.communityUserServiceRight{
		width: 84%;
		height: 100%;
		float: right;
		border: solid 1px #D6D6D6;
	}
</style>

<script type="text/javascript">
	communityUserServiceGetUserList();
	getAllModel(21);
	var communityUserServiceList;
	var communityUserServiceEmobId;
	var communityUserServiceStartList;
	var communityUserServiceStopList;
	function communityUserServiceGetUserList(){
		var path = "<%= path%>/api/v1/communities/1/users/list/inweb";
		$.ajax({

				url: path,

				type: "GET",

				dataType:"json",

				success:function(data){
					console.info(data);
					if(data.status == "yes"){
						data = data.info;
						communityUserServiceList = data;
						var len = data.length;
						for(var i = 0 ; i < len ; i++){
							if(i == 0){
								communityUserServiceUserClick(i);
								communityUserServiceEmobId = data[i].emobId;
							}
							emobId = data[i].emobId;
							nickname = data[i].nickname;
							if(nickname == ""){
								nickname = "未命名  "+i;
							}
							$("#communityUserServiceLeftUl").append("<li><a href=\"javascript:void(0);\" onclick=\"communityUserServiceUserClick("+i+");\" >"+nickname+"</a></li>");
						}
					}else{
						alert(data.message);
					}	
				},

				error:function(er){
			
					alert(er);
			
				}

			});
	}
	
	function getAllModel(adminId){
	var path = "<%= path%>/api/v1/rule/getAllModel?adminId="+adminId;
	$.ajax({

			url: path,

			type: "GET",

			dataType:'json',

			success:function(data){
				if(data.status == "yes"){
					data = data.info;
				
				}
				
			},

			error:function(er){
			
				alert(er);
			
			}

		});
	}
	
	function communityUserServiceUserClick(index){
	
		data = communityUserServiceList[index];
		nickname = data.nickname;
		if(nickname == ""){
			nickname = "未命名  "+index;
		}
		$("#communityUserServiceUserName").html(nickname);
		communityUserServiceEmobId = data.emobId;
		getCommunityUserServiceService(1);
	}
	
	function getCommunityUserServiceStartService(emobId,communityId){
		var path = "<%= path%>/api/v1/communities/"+communityId+"/communityUserService/isStartService/"+emobId;
		$.ajax({

			url: path,

			type: "GET",

			dataType:'json',

			success:function(data){
				data = data.info;
				communityUserServiceStartList = data;
				for(var i = 0 ; i < data.length ; i ++){
					addCommunityUserServiceStart(i,communityId);
				}			
			},

			error:function(er){
			
				alert(er);
			
			}

		});
	}
	
	function getCommunityUserServiceStopServiceCategory(emobId,communityId){
		var path = "<%= path%>/api/v1/communities/"+communityId+"/communityUserService/isStopService/"+emobId;
		$.ajax({

			url: path,

			type: "GET",

			dataType:'json',

			success:function(data){
				data = data.info;
				communityUserServiceStopList = data;
				for(var i = 0 ; i < data.length ; i ++){
					addCommunityUserServiceStop(i,communityId);
				}
			},

			error:function(er){
			
				alert(er);
			
			}

		});
	}
	
	function getCommunityUserServiceService(communityId){
		$("#communityUserServiceStart").find("li").remove();
		$("#communityUserServiceStop").find("li").remove();
		getCommunityUserServiceStartService(communityUserServiceEmobId, communityId);
		getCommunityUserServiceStopServiceCategory(communityUserServiceEmobId, communityId);
	}
	
	function addCommunityUserServiceStart(index,communityId){
		serviceName = communityUserServiceStartList[index].serviceName;
		$("#communityUserServiceStart").append("<li><a href=\"javascript:;\" onclick=\"updateCommunityUserServiceCategory("+index+","+communityId+");\" title=\"关闭服务\">"+serviceName+"</a></li>");
	}

	function addCommunityUserServiceStop(index,communityId){
		serviceName = communityUserServiceStopList[index].serviceName;
		$("#communityUserServiceStop").append("<li><a href=\"javascript:;\" onclick=\"addCommunityUserServiceCategory("+index+","+communityId+");\" title=\"开启服务\">"+serviceName+"</a></li>");	
	}
	function addCommunityUserServiceCategory(index,communityId){
		var path = "<%= path%>/api/v1/communities/"+communityId+"/communityUserService/"+communityUserServiceEmobId;
	
		var myJson = {
			"serviceId":communityUserServiceStopList[index].serviceId
		};
		$.ajax({

			url: path,

			type: "POST",
			
			data:JSON.stringify(myJson),

			dataType:'json',

			success:function(data){
				getCommunityUserServiceService(communityId);
			},

			error:function(er){
			
				alert(er);
			
			}

		});
	}
	function updateCommunityUserServiceCategory(index,communityId){
		var path = "<%= path%>/api/v1/communities/"+communityId+"/communityUserService/"+communityUserServiceEmobId;
	
		var myJson = {
			"status":"none",
			"serviceId":communityUserServiceStartList[index].serviceId
		};
		$.ajax({

			url: path,

			type: "PUT",
			
			data:JSON.stringify(myJson),

			dataType:'json',

			success:function(data){
				getCommunityUserServiceService(communityId);
			},

			error:function(er){
			
				alert(er);
			
			}

		});
	}
</script>
	
  </head>
  
  <body>
    <div class="communityUserServiceMain">
    	<div class="communityUserServiceLeft">
    		<ul id="communityUserServiceLeftUl">
    		
    		</ul>
    	</div>
    	
    	<div class="communityUserServiceRight">
    		<div class="main clearfix">
				<div class="user-titile">用户  <span id="communityUserServiceUserName">李静</span>  模块分配</div>
			</div>
    		<div class="community-box">
    			<div class="community-item-open clearfix">
    			</div>
					<div class="community-item-title">已开启服务</div>
					<div class="community-item-open clearfix">
						<ul id="communityUserServiceStart">
							<li><a href="javascript:;" title="关闭服务">业主活动</a></li>
							<li><a href="javascript:;" title="关闭服务">缴费</a></li>
							<li><a href="javascript:;" title="关闭服务">快店</a></li>
							<li><a href="javascript:;" title="关闭服务">外卖</a></li>
							<li><a href="javascript:;" title="关闭服务">紧急号码</a></li>
							<li><a href="javascript:;" title="关闭服务">送水</a></li>
							<li><a href="javascript:;" title="关闭服务">维修</a></li>
						</ul>
					</div>
					<div class="community-item-title">未开启服务</div>
					<div class="community-item-close clearfix">
						<ul id="communityUserServiceStop">
							<li><a href="javascript:;" title="开启服务">业主活动</a></li>
							<li><a href="javascript:;" title="开启服务">缴费</a></li>
							<li><a href="javascript:;" title="开启服务">快店</a></li>
							<li><a href="javascript:;" title="开启服务">外卖</a></li>
							<li><a href="javascript:;" title="开启服务">紧急号码</a></li>
							<li><a href="javascript:;" title="开启服务">送水</a></li>
							<li><a href="javascript:;" title="开启服务">维修</a></li>
						</ul>
					</div>
				</div>
    	</div>
    </div>
  </body>
</html>

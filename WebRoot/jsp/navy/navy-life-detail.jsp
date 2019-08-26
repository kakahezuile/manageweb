<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	Properties properties = PropertyTool
			.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String webTitle = properties.getProperty("webTitle");
%>

<%

String community_id=request.getParameter("community_id");
String communityName=request.getParameter("communityName");
String str = new String(communityName.getBytes("ISO-8859-1"),"UTF-8");
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
		<title>注册用户数_小间运营系统</title>
		
		<link
			href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>"
			type="image/x-icon" rel="shortcut icon">
		<link
			href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<link
			href="${pageContext.request.contextPath }/css/navy.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<link
			href="${pageContext.request.contextPath }/css/calendar.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">

		<script src="${pageContext.request.contextPath }/js/jquery-1.6.js?version=<%=versionNum %>"></script>
		<script src="${pageContext.request.contextPath }/js/teamwork/teamwork-survey.js?version=<%=versionNum%>"></script>
		
		
		<script type="text/javascript">
	    function getActivitiesDetail(activitiesId){
	            var path = "<%= path%>/api/v1/communities/1/lifeCircle/getLifeCireDelit?life_circle_id="+activitiesId;
				$.ajax({
					 url: path,
					 type: "GET",
					 dataType:'json',
					 success:function(data){
					 data=data.info;
					 document.getElementById("emob_id_to").value=data.emobId;
					 var activitesDate=$("#top_navy_id");
					 var body_navy_id=$("#body_navy_id");
					 //var activityStatus=$("#activity_status_ongoing_rejected").val();
					 var myDate=new Date(data.createTime*1000);
					 var li="<div class=\"lifecircle-title\">"+data.nickname+"("+data.userFloor+data.userUnit+")<span>人品值<b>"+data.praiseSum+"</b></span></div>"+
							"<div class=\"lifecircle-info\"><p>时间：<span> "+myDate.getFullYear()+"-"+(myDate.getMonth()+1)+"-"+myDate.getDate()+"</span></div>";
					 var list=data.list;
					 var activitesDatail="";
			         activitesDatail+="<p>"+data.lifeContent+"</p>";
					 for ( var i = 0; i < list.length; i++) {
						 activitesDatail+="<p class=\"lifecircle-pic\"><img src=\""+list[i].photoUrl+"\"/></p>";
					 }
					  activitesDate.empty();
					  body_navy_id.empty();
				      activitesDate.append(li);
					  body_navy_id.append(activitesDatail);
					lifecirel("<%=activitiesId%>",data.nickname);
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
						 var activitesDate=$("#body_navy_ping");
						 var activitesDatail="";
						 for ( var i = 0; i < data.length; i++) {
							 if(data[i].nicknameTo==thisName){
							   // activitesDatail+="<p >";
							    activitesDatail+="<p><span id='praisedetail"+data[i].lifecircledetailId+"'>("+data[i].praiseSum+")<a onclick='replyTo(this)' title='回复' id='"+data[i].nickidFrom+"'>"+data[i].nicknameFrom+"</a></span>："+data[i].detailContent+"</p>";
							 }else{
							    activitesDatail+="<p>";
							    activitesDatail+="<p><span id='praisedetail"+data[i].lifecircledetailId+"'>("+data[i].praiseSum+")<a onclick='replyTo(this)' title='回复' id='"+data[i].nickidFrom+"'>"+data[i].nicknameFrom+"</a>  回复  "+data[i].nicknameTo+"</span>："+data[i].detailContent+"</p>";
							 }
						}
						activitesDate.empty();
						activitesDate.append(activitesDatail);
					},
					error:function(er){
					}
				});
	        }
	        
	        
	      function navyNum(community_id){
	        var path = "<%= path%>/api/v1/communities/1/lifeCircle/getLifeCircleDetail?emob_id_to="+emob_id_to;
				$.ajax({
					url: path,
					type: "GET",
					dataType:'json',
					success:function(data){
					data=data.info;
					var activitesDate=$("#body_navy_ping");
					var activitesDatail="";
					for ( var i = 0; i < data.length; i++) {
						 if(data[i].nicknameTo==thisName){
						    activitesDatail+="<p >";
						    activitesDatail+="<p><span>"+data[i].nicknameFrom+"</span>："+data[i].detailContent+"</p>";
						 }else{
						    activitesDatail+="<p>";
						    activitesDatail+="<p><span>"+data[i].nicknameFrom+"  回复  "+data[i].nicknameTo+"</span>："+data[i].detailContent+"</p>";
						 }
					}
					activitesDate.empty();
					activitesDate.append(activitesDatail);
					},
					error:function(er){
					}
				});
	      }
	      
	      function navyNum(){
		        var path = "<%= path%>/api/v1/communities/<%=community_id%>/userStatistics/getTryOutCommunity";
				$.ajax({
					url: path,
					type: "GET",
					dataType:'json',
					success:function(data){
					data=data.info;
					var activitesDate=$("#select_try_out");
					var liList="";
					for ( var i = 0; i < data.length; i++) {
	                    liList+="<option value=\""+data[i].emobId+"\">"+data[i].nickname+"</option>";
					}
					activitesDate.append(liList);
					},
					error:function(er){
					}
				});
	      }
	      
	      
	      function favorite(lifeCircleId){
	
	   		var path = "<%=path %>/api/v1/communities/"+<%=community_id%>+"/lifeCircle/favorite";
	   
	    	var myJson={
			  "lifeCircleId":lifeCircleId,
			 // "emobId":lifeContent,
			  "communityId":<%=community_id%>
		  	};
			$.ajax({
				url : path,
				data : JSON.stringify(myJson),
				type : "POST",
				dataType : 'json',
				success : function(data) {
					alert("收藏成功");
				},
				error : function(er) {
				}
			});
		}
	      
	      
	      function talk(){
	      		var mode = false;
	      		if(document.getElementById("talkBtn").text=="发回复"){
	      			mode = true;
	      		}
	      		var emobIdFrom=document.getElementById("select_try_out").value;
	      		if(mode){
	      			var emobIdTo=  replyToId;
	      		}else{
	      			var emobIdTo=  document.getElementById("emob_id_to").value;
		        }
		        var path = "<%= path%>/api/v1/communities/<%=community_id%>/lifeCircle/"+emobIdFrom;
		        var detailContent=  document.getElementById("detail_content_id").value;
		        var myJson=  {"emobIdTo":emobIdTo,"lifeCircleId":<%=activitiesId%>,"detailContent":detailContent};
				$.ajax({
					url: path,
	                data:JSON.stringify(myJson),
					type: "POST",
					dataType:'json',
					success:function(data){
						if(mode){
					   		alert("回复成功");
					   	}else{
					   		alert("评论成功");
					   	}
					   getActivitiesDetail("<%=activitiesId%>");
					   document.getElementById("detail_content_id").value = "";
					},
					error:function(er){
					}
				});
				if(mode){
					document.getElementById("detail_content_id").placeholder = "请输入回复的内容";
	      			document.getElementById("talkBtn").text = "发评论";
				}
				
	      }
	      
	      var replyToId;
	      var replypraiseid = 1;
	      function replyTo(a){
	      		var sel=document.getElementById("select_try_out");
				var emobIdFrom=sel.options[sel.selectedIndex].text;
	      		var name = a.childNodes[0].data;
	      		replypraiseid = a.parentElement.id.substring(12);
	      		if(name==emobIdFrom){
	      			alert("不能对自己回复");
	      			return;
	      		}
	      		replyToId = a.id;
	      		document.getElementById("talkBtn").text = "发回复";
	      		document.getElementById("detail_content_id").placeholder = "回复 "+name+":";
	      		
	      }
	      
	      function zhan(){
	      		var mode = false;
	      		var zanstatus = 1;
	      		if(document.getElementById("talkBtn").text=="发回复"){
	      			mode = true;
	      			zanstatus = 2;
	      		}
		        var emobIdFrom=document.getElementById("select_try_out").value;
		        var path = "<%= path%>/api/v1/communities/<%=community_id%>/lifeCircle/"+emobIdFrom+"/praise";
		        if(mode){
	      			var emobIdTo=  replyToId;
	      		}else{
	      			var emobIdTo=  document.getElementById("emob_id_to").value;
		        }
		        var detail_content_id=  document.getElementById("detail_content_id").value;
		        var myJson=  {"emobIdTo":emobIdTo,"lifeCircleId":<%=activitiesId%>,"status":zanstatus,"lifeCircleDetailId":replypraiseid };
				$.ajax({
					url: path,
                    data:JSON.stringify(myJson),
					type: "POST",
					dataType:'json',
					success:function(data){
						if(mode){
					   		alert("赞了回复");
					   	}else{
					   		alert("赞了楼主");
					   	}
					   getActivitiesDetail("<%=activitiesId%>");
					},
					error:function(er){
					}
				});
	      }
	      navyNum();
	      getActivitiesDetail("<%=activitiesId%>");
		  function activityRefuse(){
				$("#activity-refuse").show();
				$(".activity-blank-bg").show();
		  }
		  function activityRefuseClose(){
				$("#activity-refuse").hide();
				$(".activity-blank-bg").hide();
		  }
        </script>
	</head>
	<body>
		<input type="hidden" id="emob_id_to" value="">
		<input type="hidden" id="emobId" value="${sessionScope.emobId}">
		<input type="hidden" id="community_id"
			value="${sessionScope.community_id}">
		<div class="loadingbox" id="add-price-box" style="display: none;">
			<img alt="" src="<%=basePath%>/images/chat/loading.gif">
		</div>
		<div class="upload-master-face-bg" id="upload-master-face-bg"
			style="display: none;">
			&nbsp;
		</div>
		<jsp:include flush="true" page="../public/navy-head.jsp" />
		<section>
			<div class="content clearfix">
				<jsp:include flush="true" page="../public/navy-left.jsp" >
				<jsp:param name="select" value="{parameterValue | 3}" />
													<jsp:param name="newUserName"
												value="{parameterValue | 5}" />
												</jsp:include>
				<div class="right-body" style="border: none;">
					<div class="navy-tab">
						<ul>
							<li><a href="${pageContext.request.contextPath }/jsp/navy/navy-alllife.jsp?community_id=<%=community_id%>&communityName=<%=str%>"   class="select">全部话题</a></li>
							<li><a href="${pageContext.request.contextPath }/jsp/navy/navy-collect.jsp?community_id=<%=community_id%>&communityName=<%=str%>">已收藏话题</a></li>
						</ul>
					</div>
					<div class="lifecircle-detail">
						<div class="lifecircle-head" id="top_navy_id">
							
						</div>
						<div class="lifecircle-content" >
							<div class="lifecircle-word clearfix" id="body_navy_id">
								<p>可的空间是否看见看见快乐是减。。。话题内容话题内容话题内容话题内容话题内容话题内容</p>
								<p class="lifecircle-pic"><img src="http://7d9lcl.com2.z0.glb.qiniucdn.com/FvRltjzC9LHFzr-Yn6qmZY0_O0jL"></p>
								<p class="lifecircle-pic"><img src="http://7d9lcl.com2.z0.glb.qiniucdn.com/Fv8afpRZtWRF1Ez5s6iFiIb-8aoQ"></p>
								<p class="lifecircle-pic"><img src="http://7d9lcl.com2.z0.glb.qiniucdn.com/Fgw65MpsXxLa3LMmniMhrc0_RJBp"></p>
							</div>
							<div id="body_navy_ping">
							
							
							</div>
							<!--<p><span>被风吹过的夏天</span>：加油加油，我支持你</p>
							<p><span>刘文静</span>回复<span>被风吹过的夏天</span>:真的不知道</p>
							<p><span>小惠</span>：加油加油，我支持你</p>
							<p><span>被风吹过的夏天</span>：加油加油，我支持你</p>
						    -->
						</div>
						<div class="lifecircle-repeat">
							<span style="width: 60px"></span>
							<span>选择水军号</span>
							<select id="select_try_out">
							</select>
							<input type="text" id="detail_content_id" placeholder="请输入回复的内容">
							<a href="javascript:;" id="talkBtn" onclick="talk()">发评论</a>
							<a href="javascript:;"  onclick="zhan()">赞人品</a>
						 	<a href="javascript:;"  onclick="favorite(<%=activitiesId%>)">收藏</a>
						</div>
					</div>
				</div>
			</div>
		</section>
	</body>
	<!--
	<div class="blank-bg">&nbsp</div>
	<div class="navy-pop-speak">
		<input type="text" id="detail_content_id" placeholder="请输入回复乐呵乐呵的内容">
		<a href="javascript:;" onclick="pin()">发评论</a>
		<a href="javascript:;"  onclick="zhan()">赞人品</a>
	</div>
	-->
	<script type="text/javascript">
//	installsNum();

    </script>
	<jsp:include flush="true" page="../public/footer.jsp"></jsp:include>

</html>

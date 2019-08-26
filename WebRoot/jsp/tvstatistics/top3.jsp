<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>

<%
	String path = request.getContextPath();
	
	String propetiesPath = this.getClass().getClassLoader().getResource("/").getPath(); 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

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
<meta name="keywords" content="" />
<meta name="Description" content="" />
<title>登录_帮帮数据统计系统</title>
<link href="${pageContext.request.contextPath }/favicon.ico" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/tvstat.css" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css" rel="stylesheet" type="text/css">
<![endif]-->
<script src="${pageContext.request.contextPath }/js/jquery-1.6.js"></script>
<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>

</head>


<body style="overflow:hidden;">
	<div class="loadingbox" id="add-price-box" style="display: none;">
		<img alt="" src="<%= basePath %>/images/chat/loading.gif">
		<input type="hidden" id="basePath" value="<%= basePath %>" >
	</div>
	<div class="upload-master-face-bg" id="upload-master-face-bg" style="display: none;">&nbsp;</div>
		<div class="cmmunity">
		<input id="communityId" type="hidden" value="${communityId }">
			<a class="left" href="javascript:void(0);" onclick="goRight('top_cities');" >&nbsp;</a>
			<a class="right" href="javascript:void(0);" onclick="goLeft('top_cities');" >&nbsp;</a>
			<div class="cmmunity-cities">
				<ul id="top_cities" >
					<c:forEach items="${cities }" var="city">
						<li><a href="javascript:void(0);" onclick="getCommunity(${city.value})"><font size="6" id="cityName_${city.value}">${city.key }</font></a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="cmmunity citys" id="citys">
			<a class="left" href="javascript:void(0);" onclick="goRight('top_li');" >&nbsp;</a>
			<a class="right" href="javascript:void(0);" onclick="goLeft('top_li');" >&nbsp;</a>
			<div class="cmmunity-list">
				<ul id="top_li" style="${leftNum}">
				</ul>
			</div>
		</div>
		<div class="cmmunity select" id="location" >
			<span id="community_city">大荔县</span><span>&nbsp;>&nbsp;</span><span id="community_name">高成天鹅湖</span>
		</div>
		<iframe id="content" name="stat"  class="iframe" style="width: 100%;height: 800px;border:none;" scrolling="no" src="${pageContext.request.contextPath }/error.jsp" ></iframe>
<script type="text/javascript">

function getCommunity(city){
	
	$("#citys").show();
	$("#location").hide();
	var cityName = $("#cityName_"+city).text();
	$("#community_city").text(cityName);
	 var path = "<%=basePath%>api/v1/communities/summary/city/"+city+"/cityCommunities";
	 $.ajax({
			url : path,
			type : "GET",
			dataType : 'json',
			success : function(data) {
				data = data.info;
				var repair_overman = $("#top_li");
				repair_overman.empty();
				var liList="";
				for (var i = 0; i < data.length; i++) {
					 liList+="<li><a href=\"javascript:void(0);\"  name=\"top_name\" id=\"top_"+data[i].communityId+"\"	onclick=\"getCommunityId('"+data[i].communityId+"');openUrl2('"+data[i].communityId+"')\">"+data[i].communityName+"</a></li>";
				}
				repair_overman.append(liList);
				document.getElementById("top_li").style.left = "0px";
			},
			error : function(er){
				alert(er);
			}
		});
};

 
	var baseUrl = document.getElementById("basePath").getAttribute("value");
	

	function openUrl(){
	 var src = $("#content").attr("src");
	 $("#content").attr("src",src);
	}
	
	function openUrl2(communityId){
	  var style = $("#top_li").attr("style");
	  var communityName = $("#top_"+communityId).text();
	  $("#community_name").text(communityName);
	  
	  $("#citys").hide();
	  $("#location").show();
	  $("#content").attr("src",baseUrl+"stat/toIndex3.do?communityId="+communityId);
	}
	
	function getCommunityId() {
	   var communityId = $("#communityId").val();
	   $("#top_"+communityId).addClass("select");
    }
  
	 function goLeft(id){
	        var width=document.getElementById(id).style.width;
	        var la=  -3500;
	        var le= document.getElementById(id).style.left;
	        var le=le.replace(/[^0-9]/ig,"");
	        if( parseInt(-le)<parseInt(la)){
	        }else{
	          document.getElementById(id).style.left=(parseInt(-le)-parseInt(300))+"px";
	        }
	    }
	    
	    function goRight(id){
	        var le= document.getElementById(id).style.left;
	           var le=le.replace(/[^0-9]/ig,"");
	        var la= parseInt(-le)+parseInt(300);
	        if(la>0){
	           document.getElementById(id).style.left=0;
	        }else{
	           document.getElementById(id).style.left=la+"px";
	        }
	    }
	
	    setInterval("openUrl()",1000*60*2);
	   
		  var i = $(window).width();
		  $(".cmmunity-list").width(i-100);
		  
		  $(window).resize(function(){
			  var i = $(window).width();
			  $(".cmmunity-list").width(i-100);
		  });
	    
</script>
</body>
</html>


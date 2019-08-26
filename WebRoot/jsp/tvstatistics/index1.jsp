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
		<style type="text/css">
<!--
.pic-bg {
	background: #f5f8f7;
	background-size: cover;
	padding-bottom: 33px;
}

.cmmunity {
	height: 44px;
	border-bottom: 1px solid #d6d6d6;
	background: #fff;
	width: 1270px;
	position:relative;
}
.cmmunity-list{
	position:relative;
	left:20%;
	width:1170px;
	margin:0 50px;
	overflow:hidden;
}
.cmmunity-cities{
	position:relative;
	left:20%;
	width:1170px;
	margin:0 50px;
	overflow:hidden;
}
.cmmunity ul{
	width:10000px;
	position:relative;
	left:0;
	top:0;
}
.cmmunity ul li {
	float: left;
}

.cmmunity ul li a {
	display: inline-block;
	font-size: 26px;
	color: #242424;
	height: 44px;
	margin: 0 15px;
	line-height: 42px;
}

.cmmunity ul li a:hover {
	color: #666;
}

.cmmunity ul li a.select {
	color: #4caf50;
}

.cmmunity ul li a img {
	margin-top: 8px;
}

a.left {
	background: url("../../jsp/tvstatistics/arrow-left.png") no-repeat 0 3px;
	padding:10px;
	position:absolute;
	left:10px;
	top:5px;
}

a.left:hover {
	background: url("../../jsp/tvstatistics/arrow-left-hover.png") no-repeat 0 3px;
}

a.right {
	padding:10px;
	background: url("../../jsp/tvstatistics/arrow-right.png") no-repeat 0 3px;
	position:absolute;
	right:15px;
	top:5px;
}

.right:hover {
	background: url("../../jsp/tvstatistics/arrow-right-hover.png") no-repeat 0 3px;
}

/*
.cmmunity span{
	font-size:24px;
	color:#242424;
	padding-right:32px;
	background:url("arrow.png") right no-repeat;
}
.cmmunity span a{
	color:#474747;
}
.cmmunity .list{
	border: 1px solid #d6d6d6;
    left: 6px;
    position: absolute;
    top: 60px;
}
.cmmunity .list p a{
	display:block;
	height:20px;
	line-height:20px;
	font-size:24px;
	color:#333;
	padding:20px;
	background:#fff;
	border-bottom:1px solid #d6d6d6;
}
.cmmunity .list p a:hover{
	background:#f8f8f8;
}*/
.statistics-number {
	width: 860px;
	margin: 20px auto 0;
}

.statistics-number div {
	width: 50%;
	float: left;
}

.statistics-number div p {
	font-size: 30px;
	color: #909090;
	text-align: center;
}

.statistics-number div p.nstall-numbber,.statistics-number div p.regist-numbber
	{
	font-size: 80px;
	color: #242424;
	margin-bottom: 10px;
	margin-top:20px;
}

.date {
	clear: both;
}

.date table {
	width: 100%;
}

.date table tr.table-line td {
	height: 5px;
	font-size: 0;
}

.date table tr td {
	text-align: center;
	width: 20%;
}

.date table tr td.red {
	color: #ff6138
}

.date table tr td.green {
	color: #80de15
}

.date table tr td.grey {
	color: #353d47;
}

.date table tr td.first {
	background: #8781bd;
}

.date table tr td.second {
	background: #8ba4ca;
}

.date table tr td.third {
	background: #abca8b;
}

.date table tr td.forth {
	background: #ffeb7c;
}

.date table tr td.fifth {
	background: #ff6138;
}

.date table tr.title td {
	height: 60px;
	text-size: 20px;
	color: #474747;
	vertical-align: middle;
}

.date table tr.title td {
	height: 60px;
	font-size: 24px;
	background: #f2fbff;
	color: #474747;
	vertical-align: middle;
}

.date table tr.odd td {
	background: #fff;
	height: 76px;
	font-size: 24px;
	vertical-align: middle;
}

.date table tr.even td {
	background: #f8f8f8;
	height: 76px;
	font-size: 24px;
	vertical-align: middle;
}


-->
</style>
		<style type="text/css">
.upload-master-face-bg {
	background: url("../../../images/public/bg-blank-60.png") repeat;
	position: fixed;
	top: 0;
	width: 100%;
	height: 100%;
	z-index: 21;
	left: 0;
}

.loadingbox {
	height: 32px;
	width: 32px;
	left: 50%;
	margin-left: -16px;
	margin-top: -16px;
	position: absolute;
	top: 50%;
	z-index: 22;
}
</style>
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
<div class="content">
	<div class="pic-bg">
	<%-- 
		<div class="cmmunity">
		<input id="communityId" type="hidden" value="${communityId }">
			<a class="left" href="javascript:;" onclick="goRight();" >&nbsp;</a>
			<a class="right" href="javascript:;" onclick="goLeft();" >&nbsp;</a>
			
			<div class="cmmunity-cities">
					<ul id="top_cities" >
						<c:forEach items="${cities }" var="city">
							<li><a href="javascript:void(0);" onclick="getCommunity(${city.value})"><font size="6">${city.key }</font></a></li>
						</c:forEach>
					</ul>
			</div>
			<br>
			<div class="cmmunity-list">
				<ul id="top_li" style="${leftNum}">
				</ul>
			</div>
		</div>
		 --%>
		<br>
		<div>
			<div class="statistics-number clearfix">
				<div>
					<p class="nstall-numbber" id="zong_num_an"> ${tootalInstall }</p>
					<p>安装用户数</p>
				</div>
				<div>
					<p class="regist-numbber" id="zong_num">${tootalRegist }</p>
					<p>注册用户数 </p>
				</div>
			</div>
		</div>
	</div>
	<div class="date">
		<table>
			<tr class="table-line">
				<td class="first">&nbsp;</td>
				<td class="second">&nbsp;</td>
				<td class="third">&nbsp;</td>
				<td class="forth">&nbsp;</td>
				<td class="fifth">&nbsp;</td>
			</tr>
			<tr class="title">
				<td>&nbsp;时间&nbsp;&nbsp;&nbsp;</td>
				<td>安装用户活跃数</td>
				<td>注册用户活跃数</td>
				<td>安装活跃度</td>
				<td>注册活跃度</td>
			</tr>
			
			<tr class="odd">
				<td >&nbsp;今日&nbsp;&nbsp;&nbsp;</td>
				
				<td id="yesterday">${map.today[1] }</td>
				<td id="yesterday_register_huo">${map.today[3] }</td>
				<td id="yesterday_xiao">
				<c:if test="${map.today[4]==0 }">0</c:if>
				<c:if test="${map.today[4]!=0 }">
				<fmt:formatNumber type="number" value="${100*map.today[1]/map.today[4] } " maxFractionDigits="2"/>
				</c:if>
				%</td>
				<td id="yesterday_register_xiao">
				<c:if test="${map.today[5]==0 }">0</c:if>
				<c:if test="${map.today[5]!=0 }">
				<fmt:formatNumber type="number" value="${100*map.today[3]/map.today[5] } " maxFractionDigits="2"/>
				</c:if>
				%</td>
				
				
			</tr>
			<tr class="even">
				<td class="grey">&nbsp;昨日&nbsp;&nbsp;&nbsp;</td>
				<td id="this_data">${map.yesterday[1] }</td>
				<td id="this_data_register_huo">${map.yesterday[3] }</td>
				<td id="this_data_xiao">
				<c:if test="${map.yesterday[4]==0 }">0</c:if>
				<c:if test="${map.yesterday[4]!=0 }">
				<fmt:formatNumber type="number" value="${100*map.yesterday[1]/map.yesterday[4] } " maxFractionDigits="2"/>
				</c:if>
				
				%</td>
				<td id="this_data_register_xiao">
				<c:if test="${map.yesterday[5]==0 }">0</c:if>
				<c:if test="${map.yesterday[5]!=0 }">
				<fmt:formatNumber type="number" value="${100*map.yesterday[3]/map.yesterday[5] } " maxFractionDigits="2"/>
				</c:if>
				%</td>
			</tr>
			<tr class="odd">
				<td class="grey">&nbsp;本周&nbsp;&nbsp;&nbsp;</td>
				<td id="ben_week">${map.Week[1] }</td>
				<td id="ben_week_register_huo">${map.Week[3] }</td>
				<td id="ben_week_xiao">
				<c:if test="${map.Week[4]==0 }">
				0
				</c:if>
				<c:if test="${map.Week[4]!=0 }">
				<fmt:formatNumber type="number" value="${100*map.Week[1]/map.Week[4] } " maxFractionDigits="2"/>
				</c:if>
				%</td>
				<td id="ben_week_register_xiao">
				<c:if test="${map.Week[5]==0 }">0</c:if>
				<c:if test="${map.Week[5]!=0 }">
				<fmt:formatNumber type="number" value="${100*map.Week[3]/map.Week[5] } " maxFractionDigits="2"/>
				</c:if>
				%</td>
			</tr>
			<tr class="even"">
				<td>&nbsp;上周&nbsp;&nbsp;&nbsp;</td>
				<td id="last_week">${map.lastWeek[1] }</td>
				<td id="last_week_register_huo">${map.lastWeek[3] }</td>
				<td id="last_week_xiao">
				<c:if test="${map.lastWeek[4]==0 }">
				0
				</c:if>
				<c:if test="${map.lastWeek[4]!=0 }">
				<fmt:formatNumber type="number" value="${100*map.lastWeek[1]/map.lastWeek[4] } " maxFractionDigits="2"/>
				</c:if>
				%</td>
				<td id="last_week_register_xiao">
				<c:if test="${map.lastWeek[5]==0 }">
				0
				</c:if>
				<c:if test="${map.lastWeek[5]!=0 }">
				<fmt:formatNumber type="number" value="${100*map.lastWeek[3]/map.lastWeek[5] } " maxFractionDigits="2"/>
				</c:if>
				%</td>
			</tr>
		</table>
	</div>
		<input type="hidden" id="left_num"  value="${leftNum}">
</div>
<script type="text/javascript">

function getCommunity(city){
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
				var map={};
				for (var i = 0; i < data.length; i++) {
					map[data[i].communityId]=data[i];
				}
				repair_overman.append(liList);
				getCommOrder(map);
			},
			error : function(er){
				alert(er);
			}
		});
};

 
	var baseUrl = document.getElementById("basePath").getAttribute("value");
	
/* 	function open() {
		var path = baseUrl+"api/v1/communities/summary/getListCommunityQ";
		$.ajax({
			url : path,
			type : "GET",
			dataType : 'json',
			success : function(data) {
				data = data.info;
				var repair_overman = $("#top_li");
				repair_overman.empty();
				var liList="";
				var map={};
				for (var i = 0; i < data.length; i++) {
					map[data[i].communityId]=data[i];
				}
				repair_overman.append(liList);
				getCommOrder(map);
             //  getCommunityId();
			},
			error : function(er) {
			}
		});
	} 
	open(); */
	function getCommOrder(map){
	   var path =  "<%=basePath%>api/v1/communities/summary/communitiesSequence";
		$.ajax({
			url : path,
			type : "POST",
			dataType : 'json',
			success : function(data) {
				data = data.info;
				var repair_overman = $("#top_li");
				repair_overman.empty();
				var liList="";
				for ( var i = 0; i < data.length; i++) {
				  if(data[i].status=="normal"){
				     var ha=map[data[i].communityId];
				     if(ha!=undefined){
				       liList+="<li><a href=\"javascript:void(0);\"  name=\"top_name\" id=\"top_"+ha.communityId+"\"	onclick=\"getCommunityId('"+ha.communityId+"');openUrl2('"+ha.communityId+"')\">"+ha.communityName+"</a></li>";
				     }
				  }
                 }
				repair_overman.append(liList);
                getCommunityId();
			},
			error : function(er) {
			}
		});
	}
	function openUrl(){
	  var communityId = $("#communityId").val();
	   window.location.href= baseUrl+"stat/toIndex.do?communityId="+communityId+"&leftNum="+leftNum;
	}
	
	function openUrl2(communityId){
	  var style = $("#top_li").attr("style");
	   window.location.href= baseUrl+"stat/toIndex.do?communityId="+communityId+"&leftNum="+style;
	}
	
	function getCommunityId() {
	   var communityId = $("#communityId").val();
	   $("#top_"+communityId).addClass("select");
    }
	setInterval("openUrl()",1000*60*2);

    function goLeft(){
        var width=document.getElementById("top_li").style.width;
        var la=  -3500;
        var le= document.getElementById("top_li").style.left;
        var le=le.replace(/[^0-9]/ig,"");
        if( parseInt(-le)<parseInt(la)){
        }else{
          document.getElementById("top_li").style.left=(parseInt(-le)-parseInt(300))+"px";
        }
    }
    
    function goRight(){
        var le= document.getElementById("top_li").style.left;
           var le=le.replace(/[^0-9]/ig,"");
        var la= parseInt(-le)+parseInt(300);
        if(la>0){
           document.getElementById("top_li").style.left=0;
        }else{
           document.getElementById("top_li").style.left=la+"px";
        }
    }
    
function lef(){
 var leftNum=$("#left_num").val();
 document.getElementById("top_li").style.left=leftNum+"px";
}
lef();
</script>
</body>
</html>


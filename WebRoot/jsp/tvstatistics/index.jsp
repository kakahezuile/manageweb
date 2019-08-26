<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	
	String propetiesPath = this.getClass().getClassLoader().getResource("/").getPath(); 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String webTitle = properties.getProperty("webTitle"); 
%>
<%
   String communityId=request.getParameter("community_id");
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
	width: 1920px;
	overflow: hidden;
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

.cmmunity ul li a.left {
	background: url("arrow-left.png") no-repeat 0 8px;
	padding: 0 10px;
}

.cmmunity ul li a.left:hover {
	background: url("arrow-left-hover.png") no-repeat 0 8px;
}

.cmmunity ul li a.right {
	padding: 0 10px;
	background: url("arrow-right.png") no-repeat 0 8px;
}

.cmmunity ul li a.right:hover {
	background: url("arrow-right-hover.png") no-repeat 0 8px;
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
	margin-bottom: 20px;
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
	height: 80px;
	font-size: 24px;
	vertical-align: middle;
}

.date table tr.even td {
	background: #f8f8f8;
	height: 80px;
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
		<link
			href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>"
			type="image/x-icon" rel="shortcut icon">
		<link
			href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">

		<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->
		<script
			src="${pageContext.request.contextPath }/js/jquery-2.1.1.js?version=<%=versionNum %>"></script>
		<script
			src="${pageContext.request.contextPath }/js/respond.min.js?version=<%=versionNum %>"></script>
		<script
			src="${pageContext.request.contextPath }/js/tvstatistics/tvstatistics.js?version=<%=versionNum %>"></script>
		<script
			src="${pageContext.request.contextPath }/js/operation.js?version=<%=versionNum %>"></script>

	</head>


	<body style="overflow: hidden;">
		<div class="loadingbox" id="add-price-box" style="display: none;">
			<img alt="" src="<%= basePath %>/images/chat/loading.gif">
		</div>
		<div class="upload-master-face-bg" id="upload-master-face-bg"
			style="display: none;">
			&nbsp;
		</div>
		<div class="content">
			<div class="pic-bg">
				<div class="cmmunity">
					<ul id="top_li">
						
					</ul>


					<!--
			<span><a href="#" onclick="listCom();">狮子城</a></span>
			<div class="list" id="list_com" style="display:none;">
				<p><a href="#">狮子城</a></p>
				<p><a href="#">首邑·溪谷</a></p>
				<p><a href="#">天华公馆</a></p>
			</div>
			-->
				</div>
				<div>
					<div class="statistics-number clearfix">
						<div>
							<p class="nstall-numbber" id="zong_num_an"></p>
							<p>
								安装用户数
							</p>
						</div>
						<div>
							<p class="regist-numbber" id="zong_num"></p>
							<p>
								注册用户数
							</p>
						</div>
					</div>
				</div>
			</div>
			<div class="date">
				<table>
					<tr class="table-line">
						<td class="first">
							&nbsp;
						</td>
						<td class="second">
							&nbsp;
						</td>
						<td class="third">
							&nbsp;
						</td>
						<td class="forth">
							&nbsp;
						</td>
						<td class="fifth">
							&nbsp;
						</td>
					</tr>
					<tr class="title">
						<td>
							&nbsp;时间&nbsp;&nbsp;&nbsp;
						</td>
						<td>
							安装用户活跃数
						</td>
						<td>
							注册用户活跃数
						</td>
						<td>
							安装活跃度
						</td>
						<td>
							注册活跃度
						</td>
					</tr>

					<tr class="odd">
						<td>
							&nbsp;今日&nbsp;&nbsp;&nbsp;
						</td>
						<td class="grey" id="this_data"></td>
						<td class="grey" id="this_data_register_huo"></td>
						<td class="red" id="this_data_xiao"></td>
						<td class="green" id="this_data_register_xiao"></td>
					</tr>
					<tr class="even">
						<td>
							&nbsp;昨日&nbsp;&nbsp;&nbsp;
						</td>
						<td class="grey" id="to_day"></td>
						<td class="grey" id="to_day_register_huo"></td>
						<td class="red" id="to_day_xiao"></td>
						<td class="green" id="to_day_register_xiao"></td>
					</tr>
					<tr class="odd">
						<td>
							&nbsp;本周&nbsp;&nbsp;&nbsp;
						</td>
						<td class="grey" id="this_week"></td>
						<td class="grey" id="this_week_register_huo"></td>
						<td class="red" id="this_week_xiao"></td>
						<td class="green" id="this_week_register_xiao"></td>
					</tr>
					<tr class="even">
						<td>
							&nbsp;上周&nbsp;&nbsp;&nbsp;
						</td>
						<td class="grey" id="last_week"></td>
						<td class="grey" id="last_week_register_huo"></td>
						<td class="red" id="last_week_xiao"></td>
						<td class="green" id="last_week_register_xiao"></td>
					</tr>
				</table>
			</div>
		</div>


		<script type="text/javascript">
	function open() {

		var path = "/api/v1/communities/summary/getListCommunityQ";
		$.ajax({
			url : path,
			type : "GET",
			dataType : 'json',
			success : function(data) {
				data = data.info;
				var repair_overman = $("#top_li");
				repair_overman.empty();
						
				var liList="<li><a class=\"left\" href=\"javascript:void(0);\">&nbsp;</a></li>";
				for ( var i = 0; i < data.length; i++) {
				       if(data[i].communityId==3){
				         liList+="<li><a href=\"javascript:void(0);\" name=\"top_name\" class=\"select\" id=\"top_"+data[i].communityId+"\"	onclick=\"getCommunityId('"+data[i].communityId+"')\">"+data[i].communityName+"</a></li>";
				       
				       }else{
				         liList+="<li><a href=\"javascript:void(0);\"  name=\"top_name\" id=\"top_"+data[i].communityId+"\"	onclick=\"getCommunityId('"+data[i].communityId+"')\">"+data[i].communityName+"</a></li>";
				       
				       }
                 }
				liList+="<li style=\"float: right;\"><a class=\"right\" href=\"javascript:void(0);\">&nbsp;</a></li>";
				repair_overman.append(liList);

			},
			error : function(er) {
			}
		});

	}
	open();
</script>
	</body>
</html>

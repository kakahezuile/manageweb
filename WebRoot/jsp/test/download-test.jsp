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

<style type="text/css">
@charset "utf-8";

body{
	background:#f7f7f7;
}
.content{
	padding:0 20px;
	background:#f7f7f7;
}
.service-notice_title{
	/*margin-top:20px;
	font-size:18px;
	color:#333;	
*/
	border-bottom: 1px solid #d6d6d6;
    color: #333;
    font-size: 20px;
    margin-top: 20px;
    padding-bottom: 5px;
}
.service-notice-item{
	padding-left:10px;
	font-size:14px;
}
.service-notice_title b{
	background: none repeat scroll 0 0 #04a9f4;
    display: inline-block;
    height: 18px;
    margin-right: 6px;
    position: relative;
    top: 3px;
    width: 3px;
}
.service-notice-item p{
	margin:10px 0;
}
.service-notice-price{
	color:#ff6600;
}
.service-notice_declare{
	line-height:22px;	
	padding-left:10px;
	margin-top:10px;
	font-size:14px;
}
.service-notice_declare p{
	margin:10px 0;
}
.shop_help_title{
	margin-top:20px;
	font-size:18px;
	color:#333;	
	padding-bottom:5px;
	border-bottom:1px solid #d6d6d6;
}
.shop_help-item{
	padding-left:10px;
	font-size:14px;
}
.shop_help-item a{
	color:#ff8500;
}
.shop_help_title b{
	background: none repeat scroll 0 0 #4caf50;
    display: inline-block;
    height: 18px;
    margin-right: 6px;
    position: relative;
    top: 3px;
    width: 3px;
}
.shop_help-item p{
	margin:10px 0;
}
.shop_help-price{
	color:#ff6600;
}
.shop_help_declare{
	line-height:22px;	
	padding-left:30px;
	margin-top:10px;
	font-size:14px;
}
.donwload-bg{
	position:fixed;
	z-index:-1;
	width:100%;
	background-size:cover;
	margin:0 auto;
	overflow:hidden;
}
.download-title{
	height:60px;
	line-height:60px;
	padding-left:20px;
	border-bottom:1px solid #39505d;
	background:url("app/download-title-bg.png") repeat;
}
.download-title img{
	vertical-align:middle;
}
.download{
	width:240px;
	margin:40px auto 0;
}
.download p{
	margin:30px 0;
	text-align:center;
}
.download a{
	color: #fff;
    font-size:18px;
    height: 52px;
    line-height: 52px;
    padding:0 25px;
    text-align: center;
    display:inline-block;
}
.download a.android{
	background:#64bb55;;
	border-radius: 26px;
    height: 52px;
}
.download a.ios{
	background:#d6d6d6;
	border-radius: 26px;
    height: 52px;
    cursor:default;
    opacity:0.4;
}
.download a.android:hover{
	background:#59b14a;
}
.download a.android-shop{
	background:#ff8500;
	border-radius: 26px;
    height: 52px;
}
.download a.ios-shop{
	background: #d6d6d6 none repeat scroll 0 0;
    border-radius: 26px;
    cursor: default;
    height: 52px;
    opacity: 0.4;
}
.download a.android-shop:hover{
	background:#f5840a;
}
.download a span{
	padding:5px 0 5px 30px;
}
.download a.android span{
	background:url("app/android.png") 0 2px no-repeat;
}

.download a.ios span{
	background:url("app/ios.png") 0 1px no-repeat;
}
.download a.android-shop span{
	background:url("app/android.png") 0 2px no-repeat;
}

.download a.ios-shop span{
	background:url("app/ios.png") 0 1px no-repeat;
}
.blank-bg{
	position:fixed;
	width:100%;
	height:100%;
	top:0;
	z-index:30;
	background:url("app/blank-bg.png") repeat;
}
.tip-word{
	position:relative;
}
.tip-img{
	float:right;
	clear:both;
	padding:10px 20px 0;
}
.tip-content{
	clear: both;
    color: #fff;
    float: left;
    font-size: 18px;
    line-height: 40px;
    padding: 0 30px;
}
.tip-title{
	clear: both;
    color: #fff;
    font-size: 16px;
    line-height: 40px;
    padding:30px 0 0px;
    text-align:center;
}
.bg-pic{
	/*background:url("app/download.jpg") no-repeat center #fff;*/ 
	position:fixed;
	top:0;
	width:100%;
	height:100%;
	z-index:21;
	left:0;
	z-index:-1;
}
.bg-pic img{
	width:100%;
	height:100%;
}
.content-padding{
	background:#f7f7f7;
	padding:0 5px;
}
.agreement-content p, .shop-content p{
	text-indent: 2em;
	line-height:22px;
}
.agreement-content p.title{
	text-indent: 2em;
	font-weight:bold;
}
.shop-content p.title{
	text-indent: 2em;
	font-weight:bold;
	text-align:center;
}
.agreement-content p span.bg-color{
	background:#a6dba8;
}
.agreement-content p b i.bg-color-i{
	font-weight: normal;
}
</style>
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
<title>测试版--帮帮客户端APP下载</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/app.css?version=13.0" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->

<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>
<script src="${pageContext.request.contextPath }/js/jquery-1.6.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		setTimeout( "checkPlatform()",500 );
		//checkPlatform();
	});
	//手机端判断各个平台浏览器及操作系统平台
	function checkPlatform(){
		  if(/android/i.test(navigator.userAgent)){
		      if(/MicroMessenger/i.test(navigator.userAgent)){
		       
		        $("#blank_bg").show();//这是Android平台下浏览器
			  }else{
			    //window.location.href="http://ltzmaxwell.qiniudn.com/bangbang_client.apk";
			  }
		     
		    //  top.location="http://ltzmaxwell.qiniudn.com/bangbang_client.apk"; 
		  }else	  if(/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)){
		      $("#blank_bg").hide();//这是iOS平台下浏览器
		      //window.location.href="https://itunes.apple.com/cn/app/bang-bang-she-qu-hao-bang-shou/id978272133?mt=8";
		  }else	  if(/Linux/i.test(navigator.userAgent)){
		      $("#blank_bg").hide();//这是Linux平台下浏览器
		        //window.location.href="http://ltzmaxwell.qiniudn.com/bangbang_client.apk";
		  }else	  if(/Linux/i.test(navigator.platform)){
		      $("#blank_bg").hide();//这是Linux操作系统平台
		      // window.location.href="http://ltzmaxwell.qiniudn.com/bangbang_client.apk";
		  }else	  if(/MicroMessenger/i.test(navigator.userAgent)){
			 //window.location.href="https://itunes.apple.com/cn/app/bang-bang-she-qu-hao-bang-shou/id978272133?mt=8";
		 }else{
		   $("#blank_bg").hide();//这是Linux操作系统平台
		 }
		
	}
	

</script>
</head>
<body>
	<div class="bg-pic"><img src="${pageContext.request.contextPath }/images/app/download.jpg"/></div>
<!--<div class="donwload-bg"><img src="${pageContext.request.contextPath }/images/app/download.jpg"/></div>-->
	<section>
		<div class="download-content">
			<div class="download-title">
				<img src="${pageContext.request.contextPath }/images/app/download-logo.png"/>
			</div>
			<div>
				<p class="tip-title">请点击下面按钮下载安装&nbsp;&nbsp;测试版APP</p>
			</div>
			<div class="download">
				<p>
					<a class="android" href="http://7d9lcl.com2.z0.glb.qiniucdn.com/testclient_1.2.0.apk" >
						<span>安卓版下载</span>
					</a>
				</p>
				<p>
					<a class="ios" href="#">
						<span>十天后上线</span>
					</a>
				</p>
			</div>
		</div>
	</section>
	<div class="blank-bg" id="blank_bg" style="display:none;">
		<div class="tip-word">
			<p class="tip-img"><img src="${pageContext.request.contextPath }/images/app/line-tip.png"/></p>
			<p class="tip-content">微信内无法下载，请点击右上角按钮，选择【在浏览器中打开】，下载安装。</p>
		</div>
	</div>
</body>
</html>
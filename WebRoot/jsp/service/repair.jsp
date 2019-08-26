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
<title>维修_小间物业管理系统</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->

<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>

<script src="${pageContext.request.contextPath }/js/jquery-1.6.js"></script>
<script src="${pageContext.request.contextPath }/js/nearby.js"></script>
</head>
<body>
	<jsp:include flush="true" page="../public/head.jsp"/>
	
	<div>
		<jsp:include flush="true" page="../public/nav.jsp">
				<jsp:param name="list" value="{parameterValue | ${list }}" />
		</jsp:include>
	</div>
	<section>
		<div class="content clearfix center-personal-info-content">
			<jsp:include flush="true" page="../public/service-left.jsp"></jsp:include>
			<div class="service-right-body">
				<div class="repair-head">
					<div class="repair-title">
						<a href="javascript:;" class="select">日</a>
						<a href="javascript:;">月</a>
						<a href="javascript:;">时间段scsdfs sd</a>
					</div>
					<div class="repair-content">
						<div class="repair-date"><span>2015-12-26</span></div>
						<div class="repair-stat clearfix">
							<div>
								<span>今日维修次数</span><a href="javascript:;" class="repair-count">22</a>
							</div>
							<div>
								<span>今日投诉次数</span><a href="javascript:;" class="complain-count">5</a>
								<span>未解决次数</span><a href="javascript:;" class="nosolve-count">1</a>
							</div>
							<div>
								<span>当日维修金额</span><a href="javascript:;" class="repair-fee">209</a>
							</div>
						</div>
						<div class="clearfix repair-detail"><a href="${pageContext.request.contextPath }/jsp/service/repair_record.jsp">查看详情</a></div>
					</div>
				</div>
				<div class="repair-body clearfix">
					<div class="master-list">
						<ul>
							<!--<li>
								<div class="repair-master-info">
									<a href="${pageContext.request.contextPath }/jsp/service/master_setting.jsp" class="repair-setting">&nbsp;</a>
									<p class="repair-master-face"><img src="${pageContext.request.contextPath }/images/repair/master-face1.png"/></p>
									<p class="repair-master-name">王朝</p>
									<p class="repair-master-type">弱电维修</p>
									<p class="repair-master-phone">18570781607</p>
								</div>
								<div class="repair-master-staus">
									<span class="servicing">服务中</span>
								</div>
								<div class="repair-master-opt">
									<a href="${pageContext.request.contextPath }/jsp/service/master_repair_detail.jsp">查看维修记录</a>
								</div>
							</li>
							<li>
								<div class="repair-master-info">
									<a href="${pageContext.request.contextPath }/jsp/service/master_setting.jsp" class="repair-setting">&nbsp;</a>
									<p class="repair-master-face"><img src="${pageContext.request.contextPath }/images/repair/master-face2.png"/></p>
									<p class="repair-master-name">王阳明</p>
									<p class="repair-master-type">强电维修</p>
									<p class="repair-master-phone">18570781607</p>
								</div>
								<div class="repair-master-staus">
									<span>空闲中</span>
								</div>
								<div class="repair-master-opt">
									<a href="${pageContext.request.contextPath }/jsp/service/master_repair_detail.jsp">查看维修记录</a>
								</div>
							</li>
							<li>
								<div class="repair-master-info">
									<a href="${pageContext.request.contextPath }/jsp/service/master_setting.jsp" class="repair-setting">&nbsp;</a>
									<p class="repair-master-face"><img src="${pageContext.request.contextPath }/images/repair/master-face3.png"/></p>
									<p class="repair-master-name">徐氏</p>
									<p class="repair-master-type">强电维修</p>
									<p class="repair-master-phone">18570781607</p>
								</div>
								<div class="repair-master-staus">
									<span>空闲中</span>
								</div>
								<div class="repair-master-opt">
									<a href="${pageContext.request.contextPath }/jsp/service/master_repair_detail.jsp">查看维修记录</a>
								</div>
							</li>
							<li>
								<div class="repair-master-info">
									<a href="${pageContext.request.contextPath }/jsp/service/master_setting.jsp" class="repair-setting">&nbsp;</a>
									<p class="repair-master-face"><img src="${pageContext.request.contextPath }/images/repair/master-face2.png"/></p>
									<p class="repair-master-name">王阳明</p>
									<p class="repair-master-type">强电维修</p>
									<p class="repair-master-phone">18570781607</p>
								</div>
								<div class="repair-master-staus">
									<span>空闲中</span>
								</div>
								<div class="repair-master-opt">
									<a href="${pageContext.request.contextPath }/jsp/service/master_repair_detail.jsp">查看维修记录</a>
								</div>
							</li>
							<li>
								<div class="repair-master-add-box">
									<a href="${pageContext.request.contextPath }/jsp/service/add_master.jsp">
										<p><img src="${pageContext.request.contextPath }/images/repair/repair-master-add.png"/></p>
										<p>添加员工</p>
									</a>
								</div>
								
							</li>
						-->
						</ul>
					</div>
				</div>
				<div class="price-record">
					<div class="price-record-title">报价表修改记录<a href="${pageContext.request.contextPath }/jsp/service/repair_price.jsp">报价表修改</a></div>
					<div class="price-update-record">
						<ul>
							<li><span>更新记录:</span><span>2015-02-02</span><span>服务功能启用</span><span>价格修改为</span><span>50</span></li>
							<li><span>更新记录:</span><span>2015-02-02</span><span>服务功能启用</span><span>价格修改为</span><span>50</span></li>
							<li><span>更新记录:</span><span>2015-02-02</span><span>服务功能启用</span><span>价格修改为</span><span>50</span></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
<jsp:include flush="true" page="../public/footer.jsp"></jsp:include>
</html>
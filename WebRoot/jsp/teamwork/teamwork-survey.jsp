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
			href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<link
			href="${pageContext.request.contextPath }/css/operation.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<link
			href="${pageContext.request.contextPath }/css/calendar.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">

		<script src="${pageContext.request.contextPath }/js/jquery-1.6.js?version=<%=versionNum %>"></script>
		<script src="${pageContext.request.contextPath }/js/teamwork/teamwork-survey.js?version=<%=versionNum%>"></script>
		
		
		<script type="text/javascript">
	
</script>
	</head>
	<body>
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
		<jsp:include flush="true" page="../public/teamwork-head.jsp" />
		<section>
			<div class="content clearfix">
				<jsp:include flush="true" page="../public/teamwork-left.jsp" >
				<jsp:param name="select" value="{parameterValue | 3}" />
													<jsp:param name="newUserName"
												value="{parameterValue | 5}" />
												</jsp:include>
				<div class="right-body" style="border: none;">
					<div class="teamwork-title">
						安装注册量
					</div>
					<div class="teamwork-table">
						<table id="communities_table">
							<tr>
								<th>
									小区名称
								</th>
								<th>
									安装量
								</th>
								<th>
									注册量
								</th>
								<th>
									昨日安装活跃度
								</th>
								<th>
									上周安装活跃度
								</th>
								<th>
									昨日注册活跃度
								</th>
								<th>
									上周注册活跃度
								</th>
							</tr>
							<tr class="odd">
								<td id="com_id"></td>
								<td id="zong_num_anzuang">
									50
								</td>
								<td id="zong_num">
									40
								</td>
								<td id="">
									待做
								</td>
								<td id="">
									待做
								</td>
							</tr>

						</table>
					</div>

				</div>
			</div>
		</section>
	</body>
	<script type="text/javascript">
	installsNum();
</script>
	<jsp:include flush="true" page="../public/footer.jsp"></jsp:include>

</html>

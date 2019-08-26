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
<title>物业缴费_小间物业管理系统</title>


<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->


<script src="${pageContext.request.contextPath }/js/fee.js"></script>

<script type="text/javascript">
	function getAdminList(){
		$("#rule_all_user_ul li").remove();
		
	}
	
	function getAdminListInDb(){
		var path = "<%= path%>/api/v1/rule/getAdminList";
		var adminId = 0;
		var nickname = "";
		$.ajax({

			url: path,

			type: "GET",

			dataType:"json",

			success:function(data){
				for(var i = 0 ; i < data.length ; i++){
					adminId = data[i].adminId;
					nickname = data[i].nickname;
				}
			},

			error:function(er){
			
				alert(er);
			
			}

		});
	}

</script>
</head>
<body>

	<section>
		<div class="content clearfix center-personal-info-content">
		
			<div class="right-body">
			
				<div class="main clearfix rule-main">
					<div class="rule-left">
						<div class="rule-all-user">所有用户</div>
						<ul id="rule_all_user_ul">
							<li>
								<a class="select" href="javascript:void(0);">李明</a>
							</li>
							<li>
								<a href="javascript:void(0);">张明明</a>
							</li>
							<li>
								<a href="javascript:void(0);">王晓明</a>
							</li>
						</ul>
					</div>
					
					<div class="rule-right">
						<div class="rule-setting-list">
							<div class="rule-title">所属物业</div>
							<div class="rule-item clearfix">
								<ul>
									<li>
										<input name="community" id="community1" type="radio"/> <label for="community1">海棠湾</label>	
									</li>
									<li>
										<input name="community" id="community2" type="radio"/> <label for="community2">狮子城</label>	
									</li>
									<li>
										<input name="community" id="community3" type="radio"/> <label for="community3">清水湾</label>									
									</li>
								</ul>
							</div>
						</div>
						<div class="rule-setting-list">
							<div class="rule-title">功能分配</div>
							<div class="rule-item clearfix">
								<ul>
									<li>
										<input type="checkbox"/> <label>业主咨询</label>	
									</li>
									<li>
										<input type="checkbox"/> <label>店家咨询</label>	
									</li>
									<li>
										<input type="checkbox"/> <label>公告通知</label>									
									</li>
									<li>
										<input type="checkbox"/> <label>投诉/处理</label>									
									</li>
									<li>
										<input type="checkbox"/> <label>物业缴费</label>									
									</li>
									<li>
										<input type="checkbox"/> <label>签约店家</label>									
									</li>
									<li>
										<input type="checkbox"/> <label>周边商铺</label>									
									</li>
								</ul>
							</div>
						</div>
						<div class="rule-setting-save">
							<a class="green-button" href="javascript:;">保存</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>

</html>
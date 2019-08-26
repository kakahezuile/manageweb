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
<title>收件地址设置_小间物业管理系统</title>



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
		<title>收件地址设置_小间物业管理系统</title>

		<script type="text/javascript">

function express_place_fei_jsp(){
	var path = "<%= path%>/api/v1/communities/summary/${communityId}";

		$
				.ajax({
					url : path,
					type : "GET",
					dataType : 'json',
					success : function(data) {
					data=data.info;

						var communityAddress = data.communityAddress;

						document.getElementById("express_place_sizt").value = communityAddress;
						document.getElementById("express_place_community_id").value = data.communityId;

					},
					error : function(er) {
						alert(er);
					}
				});

	}
	
function upCommunityAddress(){
	var path = "<%= path%>/api/v1/communities/summary/${communityId}";
	var communityAddress=document.getElementById("express_place_sizt").value;
	var communityId=document.getElementById("express_place_community_id").value;
			var json = {
				"communityAddress":communityAddress 
			};
	
		$
				.ajax({
					url : path,
					type : "PUT",
					data: JSON.stringify(json) ,
					dataType : 'json',
					success : function(data) {

						experss_id_fei();

					},
					error : function(er) {
						alert(er);
					}
				});

	}
	
	
	
	
</script>
	</head>
	<body>

<input type="hidden" id="express_place_community_id"/>
		<section>
			<div class="content clearfix">
				<jsp:include flush="true" page="../public/service-left.jsp"></jsp:include>
				<div class="express-right-body">
					<div class="repair-title-line">
						<p class="express-title"> 
							收件地址设置
						</p>
					</div>
					<div class="express-description">
						说明：此处的收件地址为本小区的收货地址，业主会将快递寄到本小区，由我们为业主代收。
					</div>
					<div class="express-place">
						<span>收件地址</span>
						<input type="text" value="" placeholder="如：北京市东城区法华南里天华公馆209"
							id="express_place_sizt" />
					</div>
					<div class="express-save">
						<a href="javascript:;" onclick="upCommunityAddress();">保存</a>
					</div>

				</div>
			</div>
		</section>
	</body>
	<jsp:include flush="true" page="../public/footer.jsp"></jsp:include>
</html>
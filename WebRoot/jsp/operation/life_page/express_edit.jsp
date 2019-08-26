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
		<title>编辑商家_小间物业管理系统</title>
		<link
			href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>"
			type="image/x-icon" rel="shortcut icon">
		<link
			href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<link
			href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">


<script type="text/javascript">

function statUpExpressEdit(shopId,shopName,phone,startTime,endTime,logo){
document.getElementById("shop_name_edit").value=shopName;
document.getElementById("shop_phone_fei_edit").value=phone;
document.getElementById("shops_shopId_up_id_edit").value=shopId;
document.getElementById("business_start_time_edit").value=startTime;
document.getElementById("business_end_time_edit").value=endTime;

 	var imgPre = document.getElementById("express_edit_logo");
	imgPre.src = logo;
}
function upExpressEdit(){
	var shop_name_fei =  document.getElementById("shop_name_edit").value;
	var shop_phone_fei = document.getElementById("shop_phone_fei_edit").value;
	var business_start_time = document.getElementById("business_start_time_edit").value;
	var business_end_time = document.getElementById("business_end_time_edit").value;
	var shopId=document.getElementById("shops_shopId_up_id_edit").value;
  		
     var path = "<%= path%>/api/v1/communities/"+communityId+"/shops/upExpress?shopId="+shopId+"&shopName="+shop_name_fei+"&phone="+shop_phone_fei+"&businessStartTime="+business_start_time+"&businessEndTime="+business_end_time;

	     $.ajaxFileUpload({
            url: path,
            secureuri: false,
            fileElementId: 'shops_file_edit',
            type: 'POST',
          //  date:water_supply_data,
            dataType : 'json',
            success: function (result) {
                alert("成功");
				selectShopsPhoneKuai("",1);
            }
        });

	}
	function expressEditFel(shopId,shopName,phone,statusTime,endTime,logo){
	     setNone();
		$("#express_edit_id_fei").attr("style",
										"display:block");
		statUpExpressEdit(shopId,shopName,phone,statusTime,endTime,logo);
	}
	
</script>

	</head>
	<body>
		<input type="hidden" id="shops_shopId_up_id_edit" value="" />
		<section>
			<div class="content clearfix">
				<jsp:include flush="true" page="./shops_phone_left.jsp"></jsp:include>
				<div class="express-right-body">
					<div class="repair-title-line">
						<p class="clean-rule-title">
							编辑商家信息
						</p>
					</div>
					
					<div class="shop-edit">
						<div id="express_logo">
							<div class="shop-logo-img"><img id="express_edit_logo" src="../../../images/public/default-image.png"/></div>
							<div class="shop-logo-upload">
								<a href="javascript:;">上传LOGO</a>
								<input type="file"  id="shops_file_edit" onchange="preImg('shops_file_edit','express_edit_logo');" name="shops_file_edit"/>
							</div>
						</div>
						<div class="shop-edit-box">
							<span>商家名称</span>
							<input class="express_phone_name" type="text" id="shop_name_edit" placeholder="顺风快递"/>
						</div>
						<div class="shop-edit-box">
							<span>商家电话</span>
							<input class="express_phone_number" type="text" id="shop_phone_fei_edit" placeholder="13811116666"/>
						</div>
						
						<div class="shop-edit-time">
							<span>营业时间</span>
							<input class="express_phone_place" type="text" id="business_start_time_edit" placeholder="9:00"/><label>到</label><input class="express_phone_place" type="text" id="business_end_time_edit" placeholder="20:00"/>
						</div>
						
						<div class="shop-edit-change">
							<a href="javascript:;" onclick="upExpressEdit();">修改</a>
						</div>
					</div>
				</div>
			</div>
		</section>
	</body>
</html>
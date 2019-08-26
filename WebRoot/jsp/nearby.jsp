<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
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
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
<meta name="keywords" content="小间物业,小间物业管理系统" />
<meta name="Description" content="小间物业,小间物业管理系统" />
<title>周边商铺_小间物业管理系统</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<script type="text/javascript">

function countFacilitiesTop(){
	document.getElementById("facilitiesName_nearby").value="";
	selectFacilitiesName(1);
	$.ajax({
		url : "<%= path%>/api/v1/communities/${communityId}/facilities/countFacilitesTop",
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			
			var repair_overman = $("#top_nearby_id");
			repair_overman.empty();
			for (var key in data) {
				var liList = "";
				liList += "<li><p class=\"number\">"+data[key];	
				liList+="</p><p class=\"name\">"+key+"</p></li>";
				repair_overman.append(liList);
			}
			if(!data["药店"] && typeof(data["药店"])!="undefined" && data["药店"]!=0){
				document.getElementById("nearby_drugstore_num").innerHTML=data["药店"];   
			}
			if(!isNaN(data["外卖"])){
				document.getElementById("nearby_take_out_num").innerHTML=data["外卖"];     
			}
		},
		error : function(er) {
			alert(er);
		}
	});
}
function selectFacilitiesName(pageNum) {
	var facilitiesName=document.getElementById("facilitiesName_nearby").value;
	var path = "<%= path%>/api/v1/communities/${communityId}/facilities?pageNum=" + pageNum + "&pageSize=5&q=" + facilitiesName;
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			document.getElementById("nearby_list_number_get").value= data.num;
			document.getElementById("nearby_list_sizepage_get").value= data.pageCount;
			document.getElementById("nearby_list_number").innerHTML= data.num;
			document.getElementById("nearby_list_sizepage").innerHTML= data.pageCount;
			document.getElementById("nearby_list_sun_id").innerHTML= data.rowCount;
			data = data.pageData;

			var repair_overman = $("#nearby_list");
			repair_overman.empty();
			for (var i = 0; i < data.length; i++) {
				var liList = "";
				liList += "<li><div class=\"shop-image\">";
				liList += "	<img src=\"${pageContext.request.contextPath }/"+data[i].logo+"\" /></div>";
				liList += "<div class=\"shop-detail\"><div class=\"shop-name\">";
				liList += "<span>" + data[i].facilityName + "</span>（" + data[i].phone + "）</div>";
				liList += "<div class=\"shop-position\"><span>位置：" + data[i].address + "</span>坐标:" + data[i].longitude + ":" + data[i].latitude;
				liList += "</div></div><div class=\"shop-type\"><span>类型：</span>" + data[i].typeName + "</div>";
				liList += "<div class=\"shop-open-time\"><span>营业时间：</span>"+data[i].businessStartTime+" - "+data[i].businessEndTime+"</div>";
				if(data[i].status=="normal"){
					liList += "<a href=\"javascript:;\" class=\"red-button\" id=\"facilities_status_"+data[i].facilityId+"\" onclick=\"upFacilities('"+data[i].facilityId+"','"+data[i].status+"');\">屏蔽</a>	</li>";
				}else{
					liList += "<a href=\"javascript:;\" class=\"green-button\" id=\"facilities_status_"+data[i].facilityId+"\" onclick=\"upFacilities('"+data[i].facilityId+"','"+data[i].status+"');\">开启</a>	</li>";
				}
				
				repair_overman.append(liList);
			}
		},
		error : function(er) {
			alert(er);
		}
	});
}
function upFacilities(facilityId,status) {
	var but="red-button";
	var but_ti="屏蔽";
	var fac_status=document.getElementById("facilities_status_"+facilityId).className;
	if(fac_status=="red-button"){
		status="suspend";
		but="green-button";
		but_ti="开启";
	}else{
		status="normal";
		but="red-button";
		but_ti="屏蔽";
	}
	var path = "<%= path%>/api/v1/communities/${communityId}/facilities/" + facilityId;
	var water_supply_data = {
		"status" : status
	};
	$.ajax({
		url : path,
		type : "PUT",
		data : JSON.stringify(water_supply_data),
		dataType : "json",
		success : function(data) {
			if (data.status == "yes") {
				document.getElementById("facilities_status_" + facilityId).className = but;
				document.getElementById("facilities_status_" + facilityId).innerHTML = but_ti;
			}
		},
		error : function(er) {
			alert(er);
		}
	});
}
function getNearbyList(num) {
	var page_num = 1;
	var repairRecordPageNum = document.getElementById("nearby_list_number_get").value;
	var repairRecordPageSize = document.getElementById("nearby_list_sizepage_get").value;
	if (num == -1) { // 上一页
		if (repairRecordPageNum != 1) {
			page_num = repairRecordPageNum - 1;
		} else {
			alert("已经是第一页了");
			return;
		}
	} else if (num == -2) { // 下一页
		if (parseInt(repairRecordPageNum) < parseInt(repairRecordPageSize)) {
			page_num = parseInt(repairRecordPageNum) + parseInt(1);
		} else {
			alert("已经是最后一页了");
			return;
		}
	} else if (num == -3) { // 首页
		if (repairRecordPageNum != 1) {
			page_num = 1;
		} else {
			alert("已经是首页了");
			return;
		}
	} else if (num == -4) { // 尾页
		if (parseInt(repairRecordPageNum) < parseInt(repairRecordPageSize)) {
			page_num = repairRecordPageSize;
		} else {
			alert("已经是尾页了");
			return;
		}
	}
	selectFacilitiesName(page_num);
}
</script>
</head>
<body>
	<section>
		<div class="content clearfix center-personal-info-content">
			<jsp:include flush="true" page="../jsp/public/shops_phone_left.jsp"></jsp:include>
			<div class="nearby_right-body">
				<div class="main clearfix">
					<div class="nearby-total clearfix">
						<ul id="top_nearby_id">
							<li>
								<p class="number" id="nearby_cvs_num">0</p>
								<p class="name">便利店</p>
							</li>
							<li>
								<p class="number" id="nearby_drugstore_num">0</p>
								<p class="name">药店
							</li>
							<li>
								<p class="number" id="nearby_take_out_num">0</p>
								<p class="name">外卖</p>
							</li>
						</ul>
					</div>
					<div class="nearby-search">
						<input type="text" placeholder="搜索店家  " id="facilitiesName_nearby" />
						<a class="green-button" href="javascript:;" onclick="selectFacilitiesName('1');">搜索</a>
					</div>
					<div class="nearby-shop-list">
						<ul id="nearby_list">
							<li>
								<div class="shop-image">
									<img src="${pageContext.request.contextPath}/images/nearby/shopimage1.png" />
								</div>
								<div class="shop-detail">
									<div class="shop-name">
										<span>新华药店</span>（010-88576685）
									</div>
									<div class="shop-position">
										<span>位置：</span>坐标？
									</div>
								</div>
								<div class="shop-type">
									<span>类型：</span>药店
								</div>
								<div class="shop-open-time">
									<span>营业时间：</span>8：00 - 19：00
								</div>
								<a href="javascript:;" class="red-button">屏蔽</a>
							</li>
							<li>
								<div class="shop-image">
									<img src="${pageContext.request.contextPath }/images/nearby/shopimage2.png" />
								</div>
								<div class="shop-detail">
									<div class="shop-name">
										<span>老家肉饼</span>（010-88576685）
									</div>
									<div class="shop-position">
										<span>位置：</span>坐标？
									</div>
								</div>
								<div class="shop-type">
									<span>类型：</span>饭店
								</div>
								<div class="shop-open-time">
									<span>营业时间：</span>9：00 - 22：00
								</div>
								<a href="javascript:;" class="red-button">屏蔽</a>
							</li>
						</ul>
					</div>
					<div class="divide-page" style="float: left; width: 100%; height: 50px; clear: both; text-align: center;">
						<input type="hidden" id="nearby_list_number_get" />
						<input type="hidden" id="nearby_list_sizepage_get" />
						<a href="javascript:void(0);" onclick="getNearbyList(-3);">首页</a>
						<a href="javascript:void(0);" onclick="getNearbyList(-1);">上一页</a>
						当前第<font id="nearby_list_number"></font> 页 共
						<font id="nearby_list_sizepage"></font> 页 共
						<font id="nearby_list_sun_id"></font> 条
						<a href="javascript:void(0);" onclick="getNearbyList(-2);">下一页</a>
						<a href="javascript:void(0);" onclick="getNearbyList(-4);">尾页</a>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
<jsp:include flush="true" page="public/footer.jsp"></jsp:include>
</html>
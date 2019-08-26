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
		<title>商家电话_小间物业管理系统</title>
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
	function addShopsPhone() {
		var shop_name_fei = document.getElementById("shop_name_fei").value;
		var shop_phone_fei = document.getElementById("shop_phone_fei").value;
		var business_start_time = document.getElementById("business_start_time").value;
		var business_end_time = document.getElementById("business_end_time").value;
		
       if(shop_name_fei == "" || shop_phone_fei == "" ){
  				alert("请完善信息");
  				return false;
  		}
  		var sort = document.getElementById("shops_phone_left_id_stor").value;
  			var water_supply_data = {
  				"shopName" : shop_name_fei ,
  				"address" : "" , 
  				"businessStartTime":business_start_time,
  				"businessEndTime":business_end_time,
  				"phone" : shop_phone_fei ,
  				"sort" :sort,
  				"status":"normal"
  			};
     var path = "<%= path%>/api/v1/communities/"+communityId+"/shops/life";

		$.ajax({
			url: path,

			type: "POST",
			
			data:JSON.stringify(water_supply_data),

			dataType:"json",

			success : function(data) {
		alert(" 成功");
			 document.getElementById("shop_name_fei").value="";
			 document.getElementById("shop_phone_fei").value="";
			 document.getElementById("business_start_time").value="";
			 document.getElementById("business_end_time").value="";
				selectShopsPhone("",1);
			},
			error : function(er) {
				alert(er);
			}
			
		});
	}
	

	function selectShopsPhone(shopName,num){
		var sort = document.getElementById("shops_phone_left_id_stor").value;
	  var path = "<%= path%>/api/v1/communities/"+communityId+"/shops/list/"
				+ sort + "/likeShops?";
		path += "pageSize=5&pageNum="+num+"&shopName="+shopName;
		$
				.ajax({
					url : path,

					type : "GET",

					dataType : "json",

					success : function(data) {
						data = data.info;
						 
	                      var shops_list_number_id = document
								.getElementById("shops_list_number_id");
						var shops_list_sizepage_id = document
								.getElementById("shops_list_sizepage_id");
						var shops_list_sun_id = document
								.getElementById("shops_list_sun_id");
								
						var shops_lishi = document
								.getElementById("shops_lishi");
								
								
								
						var shops_list_number_get = document
								.getElementById("shops_list_number_get");
						var shops_list_sizepage_get = document
								.getElementById("shops_list_sizepage_get");
								
							shops_list_number_get.value=data.num;	
							shops_list_sizepage_get.value=data.pageCount;	
								
								
						shops_list_number_id.innerHTML = data.num;
						shops_list_sizepage_id.innerHTML = data.pageCount;
						shops_list_sun_id.innerHTML = data.rowCount;
						shops_lishi.innerHTML = data.rowCount;

						data = data.pageData;
						var shops_phone_fei_list = $("#shops_phone_fei_list");
						shops_phone_fei_list.empty();

						shops_phone_fei_list.append("<col class='shops-name'><col class='shops-phone'><col class='shops-opeart'>" + "<tr><th>名称</th><th>电话</th><th>营业时间</th>	<th>&nbsp;</th></tr>");
						for ( var i = 0; i < data.length; i++) {

							var liList = "";
							if (i % 2 == 0) {
								liList += "<tr class='even'><td>" + data[i].shopName + "</td><td>" + data[i].phone + "</td><td>"+data[i].businessStartTime+"-"+data[i].businessEndTime+"</td><td>";
							} else {

								liList += "<tr class='odd'><td>" + data[i].shopName + "</td><td>" + data[i].phone + "</td><td>"+data[i].businessStartTime+"-"+data[i].businessEndTime+"</td><td>";
							}

							liList += "<div class='shops-opeartion'><a class='del' href='javascript:;'  onclick=\"delShops('"+data[i].shopId+"')\">删除</a>";
							liList += "<a class='edit' href='javascript:;' onclick=\"shopsEditFel('"+data[i].shopId+"','"+data[i].shopName+"','"+data[i].phone+"','"+data[i].businessStartTime+"','"+data[i].businessEndTime+"');\" >编辑</a></div></td></tr>";

							shops_phone_fei_list.append(liList);

						}

					},
					error : function(er) {
						alert(er);
					}
				});
	}


	function selectShopsPhoneList() {
		var shopName = document.getElementById("shops_phone_shopName_fei").value;
		selectShopsPhone( shopName, 1);
	}
	function getRepairRecordList(num) {
		var page_num = 1;
		var repairRecordPageNum = document
				.getElementById("shops_list_number_get").value;
		var repairRecordPageSize = document
				.getElementById("shops_list_sizepage_get").value;
		if (num == -1) { // 上一页

			if (repairRecordPageNum != 1) {
				page_num = parseInt(repairRecordPageNum) - parseInt(1);
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
			if (parseInt(repairRecordPageNum)< parseInt(repairRecordPageSize)) {
				page_num = repairRecordPageSize;
			} else {
				alert("已经是尾页了");
				return;
			}
		}

		var shopName = document.getElementById("shops_phone_shopName_fei").value;
		selectShopsPhone( shopName, page_num);
	}
	
	function shopsEditFel(shopId,shopName,phone,statusTime,endTime){
	     setNone();
		$("#shops_edit_id_fei").attr("style",
										"display:block");
		statUpShposEdit(shopId,shopName,phone,statusTime,endTime);
	}
	
	function delShops(id){
	
	   var path = "<%= path%>/api/v1/communities/"+communityId+"/shops/del?shopId="
				+ id;

		$.ajax({
			url : path,

			type : "GET",

			dataType : "json",

			success : function(data) {
				alert("成功");
				selectShopsPhone("", 1);
			},
			error : function(er) {
				alert(er);
			}
		});
	}
</script>



	</head>
	<body>
		<input type="hidden" id="shops_phone_left_id_stor">
		<section>
			<div class="content clearfix">
				<jsp:include flush="true" page="./shops_phone_left.jsp"></jsp:include>
				<div class="express-right-body">
					<div class="repair-title-line">
						<p class="express-title">
							商家录入
						</p>
					</div>
					<div class="shops-head">
						<span>名称</span>
						<input type="text" id="shop_name_fei" placeholder="如：便捷送水"/>
						<span>电话</span>
						<input type="text" id="shop_phone_fei" placeholder="如：87559196"/>
						<div id="business_start_time_business_end_time" class="shop-opentime">
							<span>营业时间</span>
							<input class="express_phone_place" type="text" id="business_start_time" placeholder="如：8:00"/>
							<span>到</span>
							<input class="express_phone_place" type="text" id="business_end_time" placeholder="如：19:00"/>
						</div>
						<a href="javascript:;" onclick="addShopsPhone();">添加</a>
					</div>
					<div class="shops-search">
						<span class="shops-search-date">录入历史，共<span
							id="shops_lishi">47</span>个电话</span>
						<input type="text" placeholder="输入商家名称"
							id="shops_phone_shopName_fei" />
						<a href="javascript:;" onclick="selectShopsPhoneList();">搜索</a>
					</div>
					<div class="shops-list">
						<table id="shops_phone_fei_list">
							<!--<col class="shops-name">
							<col class="shops-phone">
							<col class="shops-opeart">
							<tr>
								<th>
									名称
								</th>
								<th>
									电话
								</th>
								<th>
									&nbsp;
								</th>
							</tr>
							<tr class="odd">
								<td>
									乐百氏水站
								</td>
								<td>
									18512544452
								</td>
								<td>
									<div class="shops-opeartion">
										<a class="del" href="javascript:;">删除</a><a class="edit"
											href="${pageContext.request.contextPath }/jsp/service/shops_edit.jsp">编辑</a>
									</div>
								</td>
							</tr>
							<tr class="even">
								<td>
									乐百氏水站
								</td>
								<td>
									18512544452
								</td>
								<td>
									<div class="shops-opeartion">
										<a class="del" href="javascript:;">删除</a><a class="edit"
											href="javascript:;">编辑</a>
									</div>
								</td>
							</tr>
						--></table>


					</div>
					<div class="divide-page" 
						style="float: left; width: 100%; height: 50px; clear: both; text-align: center;">


						<input type="hidden" id="shops_list_number_get" />
						<input type="hidden" id="shops_list_sizepage_get" />

						<a href="javascript:void(0);" onclick="getRepairRecordList(-3);">首页</a>
						<a href="javascript:void(0);" onclick="getRepairRecordList(-1);">上一页</a>
						当前第
						<font id="shops_list_number_id"></font> 页 共
						<font id="shops_list_sizepage_id"></font> 页 共
						<font id="shops_list_sun_id"></font> 条

						<a href="javascript:void(0);" onclick="getRepairRecordList(-2);">下一页</a>
						<a href="javascript:void(0);" onclick="getRepairRecordList(-4);">尾页</a>
					</div>
				</div>
			</div>
		</section>
	</body>
	<jsp:include flush="true" page="../../public/footer.jsp"></jsp:include>
</html>
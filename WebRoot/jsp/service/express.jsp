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
		<title>快递_小间物业管理系统</title>
		

		<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->

		<script type="text/javascript">
		
	
       var expressThisPageNum = 1;
	
		var expressThisPageSize = 10;
	
		var expressIsgoing = 0;
	
		var expressIsreview = 0;
	
		var expressEnded = 0;
	
		var expressSum = 0;
	
		var expressPageSize = 0;
	
		var expressPageNum = 0;
	
		var expressPageFirst = 0;
	
		var expressPageLast = 0;
	
		var expressPageNext = 0;
	
		var expressPrev = 0;
		
		var thisExpressSearchType = "-1";
		
		function expressSubmit(){
		
			var path = "<%= path%>/api/v1/communities/${communityId}/express";
			
			var expressName = $("#express_name_fei").val();
			var expressGoods = $("#express_goods_fei").val();
			var expressPhone = $("#express_phone_fei").val();
			var expressNo = $("#express_no_fei").val();
			var expressStationInform = $("#express_station_inform_fei").val();
			var expressMessageInform = $("#express_message_inform_fei").val();
			
			var myJson = {
				"expressName":expressName , 
				"expressGoods":expressGoods ,
				"expressPhone":expressPhone ,
				"expressNo":expressNo ,
				"expressStationInform":expressStationInform ,
				"expressMessageInform":expressMessageInform
			};
			
			$.ajax({

				url: path,

				type: "POST",
			
				data: JSON.stringify(myJson) ,

				dataType:'json',

				success:function(data){
					console.info(data);
					if(data.status == "yes"){
						alert("保存并消息发送成功");
						document.getElementById("express_name_fei").value = "";
						document.getElementById("express_goods_fei").value = "";
						document.getElementById("express_phone_fei").value = "";
						document.getElementById("express_no_fei").value = "";
						getExpressList(-1);
					}else if(data.status == "empty"){
						alert("当前订单已存在请验证后进行保存");
					}else if(data.status == "no"){
						alert("保存失败");
					}
					
				},

				error:function(er){
				
					alert(er);
			
				}

			});
		}
		
		function getExpressList(express_type){
			
			var path = "<%= path%>/api/v1/communities/${communityId}/express?pageNum="+expressThisPageNum+"&pageSize="+expressThisPageSize+"&expressType="+express_type;
		
			$.ajax({

				url: path,

				type: "GET",

				dataType:'json',

				success:function(data){
					
					if(data.status == "yes"){
						console.info(data);
						var pageData = data.info.pageData;
						
						expressSum = data.info.rowCount; // 总条数
				
						expressPageFirst = data.info.first; // 首页
				
						expressPageLast = data.info.last; // 尾页
				
						expressPageNext = data.info.next; // 下一页
				
						expressPageNum = data.info.num; // 当前页
				
						expressPrev = data.info.prev; // 上一页
				
						expressPageSize = data.info.pageCount; // 总页数
						
						$("#expressPageNum_fei").html(expressPageNum);
				
						$("#expressPageSize_fei").html(expressPageSize);
				
						$("#expressSum_fei").html(expressSum);
						$("#express_table_fei").empty();
							$("#express_table_fei").append("<tr><th>电话</th><th>快递单号</th><th>收件人</th><th>物品</th><th>通知结果</th><th>用户类型</th><th>收件时间</th><th>取件</th></tr>");
								
						var len = pageData.length;
						for(var i = 0 ; i < len ; i++){
							var expressResult = "";
							console.info(pageData[i].expressResult);
							if(pageData[i].expressResult == 1){
								expressResult = "<span class='success'>成功</span>";
								
							}else{
								expressResult = "<span class='fail'>失败</span>";
							}
							var expressUserType = "";
							if(pageData[i].expressUserType == 1){
								expressUserType = "普通用户";
							}else{
								expressUserType = "APP用户";
							}
							var expressCollectionTime = pageData[i].expressCollectionTime;
							expressCollectionTime = parseInt(expressCollectionTime) * 1000;
							var express_status_html = "";
							if(pageData[i].expressStatus == 1){
								express_status_html = "已取件";
								$("#express_table_fei").append("<tr class='odd'><td>"+pageData[i].expressPhone+"</td><td>"+pageData[i].expressNo+"</td><td>"+pageData[i].expressName+"</td><td>"+pageData[i].expressGoods+"</td><td>"+expressResult+"</td><td>"+expressUserType+"</td><td>"+getSmpFormatDateByLong(expressCollectionTime,false)+"</td><td>"+express_status_html+"</td></tr>");
					
							}else{
								express_status_html = "	<a href=\"javascript:;\"  onclick=\"updateExpress("+pageData[i].expressId+");\">确认收件</a>";
								$("#express_table_fei").append("<tr class='even'><td>"+pageData[i].expressPhone+"</td><td>"+pageData[i].expressNo+"</td><td>"+pageData[i].expressName+"</td><td>"+pageData[i].expressGoods+"</td><td>"+expressResult+"</td><td>"+expressUserType+"</td><td>"+getSmpFormatDateByLong(expressCollectionTime,false)+"</td><td>"+express_status_html+"</td></tr>");
					
							}
							}
					}else if(data.status == "no"){
						alert("查询失败");
					}
					
				},

				error:function(er){
				
					alert(er);
			
				}

			});
		}
		
		function getExpressListByText(express_type){
		
			var express_search = $("#express_search_fei").val();
			
			if(express_search == ""){
				alert("搜索内容不能为空");
				return false;
			}
			
			var path = "<%= path%>/api/v1/communities/${communityId}/express/like/"+express_search+"?pageNum="+expressThisPageNum+"&pageSize="+expressThisPageSize+"&expressType="+express_type;
		
			$.ajax({

				url: path,

				type: "GET",

				dataType:'json',

				success:function(data){
					
					if(data.status == "yes"){
						console.info(data);
						var pageData = data.info.pageData;
						
						expressSum = data.info.rowCount; // 总条数
				
						expressPageFirst = data.info.first; // 首页
				
						expressPageLast = data.info.last; // 尾页
				
						expressPageNext = data.info.next; // 下一页
				
						expressPageNum = data.info.num; // 当前页
				
						expressPrev = data.info.prev; // 上一页
				
						expressPageSize = data.info.pageCount; // 总页数
						
						$("#expressPageNum_fei").html(expressPageNum);
				
						$("#expressPageSize_fei").html(expressPageSize);
				
						$("#expressSum_fei").html(expressSum);
						
						var len = pageData.length;
						
						for(var i = 0 ; i < len ; i++){
							var expressResult = "";
							console.info(pageData[i].expressResult);
							if(pageData[i].expressResult == 1){
								expressResult = "成功";
								
							}else{
								expressResult = "失败";
							}
							var expressUserType = "";
							if(pageData[i].expressUserType == 1){
								expressUserType = "普通用户";
							}else{
								expressUserType = "APP用户";
							}
							var expressCollectionTime = pageData[i].expressCollectionTime;
							expressCollectionTime = parseInt(expressCollectionTime) * 1000;
							var express_status_html = "";
							if(pageData[i].expressStatus == 1){
								express_status_html = "已取件";
							}else{
								//express_status_html = "<input type=\"button\" value=\"确认取件\" onclick=\"updateExpress("+pageData[i].expressId+");\" />";
								express_status_html = "	<a href=\"javascript:;\"  onclick=\"updateExpress("+pageData[i].expressId+");\">确认收件</a>";
							}
							
							
									
								
							$("#express_table_fei").append("<tr class='odd'><td>"+pageData[i].expressPhone+"</td><td>"+pageData[i].expressNo+"</td><td>"+pageData[i].expressName+"</td><td>"+pageData[i].expressGoods+"</td><td><span class='success'>"+expressResult+"</span></td><td>"+expressUserType+"</td><td>"+getSmpFormatDateByLong(expressCollectionTime,false)+"</td><td>"+express_status_html+"</td></tr>");
						}
					}else if(data.status == "no"){
						alert("查询失败");
					}
					
				},

				error:function(er){
				
					alert(er);
			
				}

			});
		}
		
		
		function updateExpress(express_id){
			
			var myJson = {
				"expressStatus":1
			};
			
			var path = "<%= path%>/api/v1/communities/${communityId}/express/"
				+ express_id;

		$.ajax({

			url : path,

			type : "PUT",

			data : JSON.stringify(myJson),

			dataType : 'json',

			success : function(data) {

				if (data.status == "yes") {
					$("#express_table_fei").html("");
					getExpressList("-1");
				} else if (data.status == "no") {
					alert("修改失败");
				}

			},

			error : function(er) {

				alert(er);

			}

		});
	}

	function getExpressListWithPage(num) {
		//getExpressList(thisExpressSearchType);
		if (num == -1) { // 上一页
			if (expressThisPageNum != 1) {
				expressThisPageNum = expressPrev;
				getExpressList(thisExpressSearchType);
			}

		} else if (num == -2) { // 下一页
			if (expressThisPageNum < expressPageSize) {
				expressThisPageNum = expressPageNext;
				getExpressList(thisExpressSearchType);
			}

		} else if (num == -3) { // 首页
			if (expressThisPageNum != expressPageFirst) {
				expressThisPageNum = expressPageFirst;
				getExpressList(thisExpressSearchType);
			}
		} else if (num == -4) { // 尾页
			if (expressThisPageNum != expressPageLast) {
				expressThisPageNum = expressPageLast;
				getExpressList(thisExpressSearchType);
			}
		}
	}

	function updateThisSearchType(type) {
		thisExpressSearchType = type;
		updateExpressType(type);
		getExpressList(thisExpressSearchType);
	}


	
	function updateExpressType(type) {
		var express_str0 = document.getElementById("express_str_-1");
		var express_str1 = document.getElementById("express_str_1");
		var express_str2 = document.getElementById("express_str_2");
		if (type == -1) {
			express_str0.className = "select";
		} else {
			express_str0.className = "";
		}
		if (type ==1) {
			express_str1.className = "select";
		} else {
			express_str1.className = "";
		}
		if (type == 2) {
			express_str2.className = "select";
		} else {
			express_str2.className = "";
		}
	}
	function express_search_by_text() {
		getExpressListByText(thisExpressSearchType);
	}
	
	function express_sms(){
	var username=document.getElementById("express_name_fei").value;
	document.getElementById("express_station_inform_fei").value="报告"+username+"帮主，您的快递已抵达帮帮物业处，请您速速取回。";
	document.getElementById("express_message_inform_fei").value="尊敬的"+username+"您好，帮帮为您代收了一件快递，请您到物业处领取。注册成为帮帮会员，免费享受更多服务（http://114.215.114.56:8080/jsp/app/download.jsp）";
	
	}
	
	
	
function getExpressListByText(){
	
	var express_search = $("#express_search_fei_id").val();
	//var express_type=document.getElementById("express_type").value ;
	if(express_search == ""){
		alert("搜索内容不能为空");
		return false;
	}
	
	var path = "<%= path%>/api/v1/communities/"+communityId+"/express/like/"+express_search+"?pageNum=1&pageSize=10&expressType="+thisExpressSearchType;

	$.ajax({

		url: path,

		type: "GET",

		dataType:'json',

		success:function(data){
		
					if(data.status == "yes"){
						console.info(data);
						var pageData = data.info.pageData;
						
						expressSum = data.info.rowCount; // 总条数
				
						expressPageFirst = data.info.first; // 首页
				
						expressPageLast = data.info.last; // 尾页
				
						expressPageNext = data.info.next; // 下一页
				
						expressPageNum = data.info.num; // 当前页
				
						expressPrev = data.info.prev; // 上一页
				
						expressPageSize = data.info.pageCount; // 总页数
						
						$("#expressPageNum_fei").html(expressPageNum);
				
						$("#expressPageSize_fei").html(expressPageSize);
				
						$("#expressSum_fei").html(expressSum);
						
						var len = pageData.length;
						
						for(var i = 0 ; i < len ; i++){
							var expressResult = "";
							console.info(pageData[i].expressResult);
							if(pageData[i].expressResult == 1){
								expressResult = "成功";
								
							}else{
								expressResult = "失败";
							}
							var expressUserType = "";
							if(pageData[i].expressUserType == 1){
								expressUserType = "普通用户";
							}else{
								expressUserType = "APP用户";
							}
							var expressCollectionTime = pageData[i].expressCollectionTime;
							expressCollectionTime = parseInt(expressCollectionTime) * 1000;
							var express_status_html = "";
							if(pageData[i].expressStatus == 1){
								express_status_html = "已取件";
							}else{
								//express_status_html = "<input type=\"button\" value=\"确认取件\" onclick=\"updateExpress("+pageData[i].expressId+");\" />";
								express_status_html = "	<a href=\"javascript:;\"  onclick=\"updateExpress("+pageData[i].expressId+");\">确认收件</a>";
							}
							
							
									
								
							$("#express_table_fei").append("<tr class='odd'><td>"+pageData[i].expressPhone+"</td><td>"+pageData[i].expressNo+"</td><td>"+pageData[i].expressName+"</td><td>"+pageData[i].expressGoods+"</td><td><span class='success'>"+expressResult+"</span></td><td>"+expressUserType+"</td><td>"+getSmpFormatDateByLong(expressCollectionTime,false)+"</td><td>"+express_status_html+"</td></tr>");
						}
			}else if(data.status == "no"){
				alert("查询失败");
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
			<div class="content clearfix">
				<jsp:include flush="true" page="../public/service-left.jsp"></jsp:include>
				<div class="express-right-body">
					<div class="repair-title-line">
						<p class="express-title">
							收取快递
						</p>
						<!--
						<a class="del-master"
							href="${pageContext.request.contextPath }/jsp/service/express_place.jsp">收件地址设置</a>
					-->
					
						
						<a class="del-master"
							href="javascript:;" onclick="experss_repair_id_fei();">收件地址设置</a>
					
					
					
					
					</div>
					<div class="express-site-message">
						<label>
							站内通知
						</label>
						<input id="express_station_inform_fei"
							value="报告XXX帮主，您的快递已抵达帮帮物业处，请您速速取回。" type="text" />
							
					</div>
					<div class="express-phone-message">
						<label>
							短信通知
						</label>
						<input value="尊敬的XXX：您好，帮帮为您代收了一件快递，请您到物业处领取。注册成为帮帮会员，免费享受更多服务（http://www.）"
							id="express_message_inform_fei" type="text" />
					</div>
					<div class="express-user-info">
						<span>快递单号</span>
						<input type="text" placeholder="如：21245145008" id="express_no_fei" />
						<span>手机号</span>
						<input type="text" placeholder="如：13845411245" id="express_phone_fei" />
						<span>收件人</span>
						<input type="text" placeholder="如：李晓明" id="express_name_fei" onblur="express_sms();"/>
						<span>物品</span>
						<input type="text" placeholder="如：衣物" id="express_goods_fei" style="margin-right:0;"/>
					</div>
					<div class="express-recive-button">
						<a href="javascript:;" id="express_submit_fei"
							onclick="expressSubmit();">收件并通知</a>
					</div>
					<div class="express-list">
						<div class="express-list-head">
							<div class="filter-condition">
								<a href="javascript:;" class="select"
									onclick="updateThisSearchType('-1')" id="express_str_-1">全部</a>
								<a href="javascript:;" onclick="updateThisSearchType('2')" id="express_str_2">未取件</a>
								<a href="javascript:;" onclick="updateThisSearchType('1')" id="express_str_1">已取件</a>
							</div>
							<div style="float: right;">
							<input type="text" id="express_search_fei_id" />
							<input type="button" onclick="getExpressListByText();" value="查找">
							
							</div>
							
						</div>
						<div class="express-list-body">
							<table class="express-list-table" id="express_table_fei">

								<!--<tr class="odd">
									<td>
										17645854414
									</td>
									<td>
										26458545854585
									</td>
									<td>
										王朝
									</td>
									<td>
										衣物
									</td>
									<td>
										<span class="success">成功</span>
									</td>
									<td>
										App用户
									</td>
									<td>
										2015-12-02 16：32
									</td>
									<td>
										已取件
									</td>
								</tr>
								<tr class="even">
									<td>
										17645854414
									</td>
									<td>
										26458545854585
									</td>
									<td>
										王朝
									</td>
									<td>
										衣物
									</td>
									<td>
										<span class="fail">失败</span>
									</td>
									<td>
										App用户
									</td>
									<td>
										2015-12-02 16：32
									</td>
									<td>
										<a href="javascript:;">确认收件</a>
									</td>
								</tr>
							--></table>
						</div>
						<table  class="divide-page">
							<tr>
								<td>
									<a href="javascript:void(0);"
										onclick="getExpressListWithPage(-3);">首页</a>
									<a href="javascript:void(0);"
										onclick="getExpressListWithPage('-1');">上一页</a> 当前第
									<font id="expressPageNum_fei"></font> 页 共
									<font id="expressPageSize_fei"></font> 页 共
									<font id="expressSum_fei"></font> 条

									<a href="javascript:void(0);"
										onclick="getExpressListWithPage(-2);">下一页</a>
									<a href="javascript:void(0);"
										onclick="getExpressListWithPage(-4);">尾页</a>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</section>
	</body>
	<jsp:include flush="true" page="../public/footer.jsp"></jsp:include>
</html>
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
<title>缴费_小间物业管理系统</title>


<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->

<script type="text/javascript">

	var paymentEditThisStandardId = 0;

	function paymentEditSaveClick(){
		addStandard(1);
		$("#payment_edit_save_id").attr("style","display:none");
		$("#payment_edit_edit_id").attr("style","display:block");
		
	}
	
	function paymentEditEditClick(){
		$("#payment_edit_save_id").attr("style","display:block");
		$("#payment_edit_edit_id").attr("style","display:none");
	}
	
	function getStandard(sort){
		var path = "<%= path%>/api/v1/communities/${communityId}/standard/"+sort;
	
		$.ajax({

				url: path,

				type: "GET",

				dataType:'json',

				success:function(data){
					if(data.status == "yes"){
						data = data.info;
						price = data.price;
						paymentEditThisStandardId = data.standardId;
						document.getElementById("payment_save_text").value = price;
						document.getElementById("payment_read_text").value = price;
					}
				},

				error:function(er){
			
					alert(er);
			
				}

			});
	}
	
	function addStandard(sort){
		var path = "<%= path%>/api/v1/communities/${communityId}/standard";
		
		var payment_save_text = $("#payment_save_text").val();
		
		if(payment_save_text == ""){
			alert("价格不能为空");
		}
		
		var myJson = {
			"sort" : sort ,
			"detail" : "度" ,
			"price" : payment_save_text
		};
		
		$.ajax({

				url: path,

				type: "POST",
				
				data :JSON.stringify(myJson),

				dataType:'json',

				success:function(data){
					if(data.status == "yes"){
						document.getElementById("payment_read_text").value = payment_save_text;
						getStandardEntry(1);
					}else{
						alert("添加错误");
					}
				},

				error:function(er){
			
					alert(er);
			
				}

			});
	}
	
	function addStandardEntry(sort){
		var path = "<%= path%>/api/v1/communities/${communityId}/payStandards";
		
		var payment_edit_detail = $("#payment_edit_detail").val();
		
		if(payment_edit_detail == ""){
			alert("价格不能为空");
		}
		
		var myJson = {
			"sort" : sort ,
			"entrySum" : payment_edit_detail ,
			"price" : 0 ,
			"standardId" : paymentEditThisStandardId
		};
		
		$.ajax({

				url: path,

				type: "POST",
				
				data :JSON.stringify(myJson),

				dataType:'json',

				success:function(data){
					if(data.status == "yes"){
						alert("添加成功");
						getStandardEntry(1);
					}else{
						alert("添加错误");
					}
				},

				error:function(er){
			
					alert(er);
			
				}

			});
	}
	
	function getStandardEntry(sort){
		var path = "<%= path%>/api/v1/communities/${communityId}/payStandards/"+sort;
		var payMentUl = $("#payment_edit_ul");
		payMentUl.html("");
		$.ajax({

				url: path,

				type: "GET",

				dataType:'json',

				success:function(data){
					if(data.status == "yes"){
						data = data.info;
						var len = data.length;
						payMentUl.append("<tr><td colspan=\"3\"><div class=\"payment-add\"><input type=\"text\" value=\"\" id=\"payment_edit_detail\" placeholder=\"输入电量    如：100\"><span>度</span><a href=\"javascript:void(0);\" onclick=\"addStandardEntry(1);\">添加</a></div></td></tr>");
						//payMentUl.append("<li><div><input type=\"text\" value=\"\" id=\"payment_edit_detail\" class=\"payment_edit_price_number\" placeholder=\"输入电量    如：100\"><span>度</span><a href=\"javascript:void(0);\" onclick=\"addStandardEntry(1);\">添加</a></div></li>");
						
						
						for(var i = 0 ; i < len ; i++){
							entryId = data[i].entryId;
							price = data[i].price;
							entrySum = data[i].entrySum;
							payMentUl.append("<tr><td>缴纳电量</td><td>"+entrySum+"度（"+price+"元）</td><td><a href=\"javascript:;\" onclick=\"deleteStandardEntry("+entryId+")\">删除</a></td></tr>");
							//payMentUl.append("<li><div><span class=\"payment_edit_word\">缴纳电量</span><span>"+entrySum+"度</span><span>（"+price+"元）</span><a onclick=\"deleteStandardEntry("+entryId+")\" href=\"javascript:void(0);\">删除</a></div></li>");
						}
						
					}else{
						
					}
				},

				error:function(er){
			
					alert(er);
			
				}

			});
	}
	
	function deleteStandardEntry(entryId){
		var path = "<%= path%>/api/v1/communities/${communityId}/payStandards/"+entryId;
		
		$.ajax({

				url: path,

				type: "DELETE",

				dataType:'json',

				success:function(data){
					if(data.status == "yes"){
						getStandardEntry(1);
					}else{
						alert("删除错误");
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
		<div class="content clearfix" style="border:none;background:none;">
			<div class="activity-right-body">
			<div class="payment_edit_title">编辑缴费信息</div>
			<div class="payment_edit_price">
				<div id="payment_edit_edit_id">
					<span>当前电价</span>
					<input class="read" type="text" id="payment_read_text" value="0.0" readonly="readonly"/>
					<a onclick="paymentEditEditClick();" href="javascript:void(0);">修改</a>
				</div>
				<div id="payment_edit_save_id" style="display: none;">
					<span>当前电价</span>
					<input class="edit" type="text" id="payment_save_text" value="0.0" />
					<a onclick="paymentEditSaveClick();" href="javascript:void(0);">保存</a>
				</div>
			</div>
			<div class="payment-box">
				<table id="payment_edit_ul">
					<tr>
						<td colspan="3">
							<div class="payment-add">
								<input type="text" value="" id="payment_edit_detail" placeholder="输入电量    如：100"><span>度</span>
								<a href="javascript:void(0);" onclick="addStandardEntry(1);">添加</a>
							</div>
						</td>
					</tr>
					<tr>
						<td>缴纳电量</td>
						<td>50度（24.0元）</td>
						<td><a href="javascript:;">删除</a></td>
					</tr>
					<tr>
						<td>缴纳电量</td>
						<td>50度（24.0元）</td>
						<td><a href="javascript:;">删除</a></td>
					</tr>
				</table>
			</div>
			
			
			<!--
				<div class="payment_edit_price">
					<div id="payment_edit_save_id" style="display: none;">
						<span>当前电价</span>
						<input class="edit" type="text" id="payment_save_text" value="0.0" />
						<a onclick="paymentEditSaveClick();" href="javascript:void(0);">保存</a>
					</div>
					<div id="payment_edit_edit_id">
						<span>当前电价</span>
						<input class="read" type="text" id="payment_read_text" value="0.0" readonly="readonly"/>
						<a onclick="paymentEditEditClick();" href="javascript:void(0);">修改</a>
					</div>
				</div>
				<div class="payment_edit_list">
					<ul id="payment_edit_ul">
						<li>
							<div>
								<span class="payment_edit_word">缴纳电量</span><span>100度</span><span>（50元）</span>
								<a href="javascript:void(0);">删除</a>
							</div>
						</li>
						<li>
							<div>
								<span class="payment_edit_word">缴纳电量</span><span>200度</span><span>（100元）</span>
								<a href="javascript:void(0);">删除</a>
							</div>
						</li>
						<li>
							<div>
								<span class="payment_edit_word">缴纳电量</span><span>300度</span><span>（150元）</span>
								<a href="javascript:void(0);">删除</a>
							</div>
						</li>
						<li>
							<div>
								<input type="text" value="" id="payment_edit_detail" placeholder="输入电量    如：100"><span>度</span>
								<a href="javascript:void(0);" onclick="addStandardEntry(1);">添加</a>
							</div>
						</li>
					</ul>
				</div>-->
			</div>
		</div>
		
	</section>
</body>


</html>
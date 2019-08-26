<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <% String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>物业缴费页</title>
<style type="text/css">
	.pay_table_main{
		width: 100%;
		float: left;
		height: 100px;
		border:1px solid #8F8F8F;
	}
	.pay_table_top{
		width: 100%;
		float: left;
		height: 30px;
		border:1px solid #8F8F8F;
	}
	.pay_table_center{
		width: 100%;
		float: left;
		height: 70px;
		border:1px solid #8F8F8F;
	}
	.pay_table_jiaofei_img{
		width : 70px;
		height: 60px;line-height: 60px;
		margin-left: 10px;
		margin-top: 5px;
	}
	.text_height_center{
		height: 70px;line-height: 70px;
		margin-left: 50px;
	}
	.margin_left{
		margin-left: 80px;
	}
</style>
<script type="text/javascript">
	var payThisPageNum = 1;
	
	var payThisPageSize = 10;
	
	var payIsgoing = 0;
	
	var payIsreview = 0;
	
	var payEnded = 0;
	
	var paySum = 0;
	
	var payPageSize = 0;
	
	var payPageNum = 0;
	
	var payPageFirst = 0;
	
	var payPageLast = 0;
	
	var payPageNext = 0;
	
	var payPrev = 0;
	
	var jiaofei_type = 1;
	
	var jiaofei_status = 0;
	
	var myTimeOut = null;
	
	var jiaofeiThisList = new Array();
	
	var jiaofeiThisMap = {};
	
	function jiaofei_left_click(status){
		jiaofei_status = status;
		jiaofei_submit(jiaofei_type , jiaofei_status);
		jiaofeiSetNone();
		
		if(status == 0){
			$("#jiaofei_new_pay_tr").attr("style","display:block");	
		}else if(status == 1){
			$("#jiaofei_wait_pay_tr").attr("style","display:block");
		}else if(status == 2){
			$("#jiaofei_complete_pay_tr").attr("style","display:block");
		}
	}
	
	function jiaofei_top_click(type){
		jiaofei_type = type;
		jiaofei_submit(jiaofei_type , jiaofei_status);
	}
	function jiaofei_tongzhi_click(){
		getSendMessageEdit();
		jiaofeiSetNone();
		$("#jiaofei_send_message_edit_tr").attr("style","display:block");
	}
	function jiaofei_submit(type,status){
		getPayTop(type,status);
		getNewPayList(type,status);
	}
	
	function getPayTop(type,status){
		
		var path = "<%= path%>/api/v1/communities/${communityId}/users/admin/payments/getTop?type="+type+"&status="+status;
	
		$.ajax({

				url: path,

				type: "GET",

				dataType:'json',

				success:function(data){
					
					var jiaofei_paysum = data.info.paySum;
					var jiaofei_payparsum = data.info.payParSum;
					if(jiaofei_payparsum == "null"){
						jiaofei_payparsum = "0.00";
					}
					console.info(jiaofei_payparsum + "---------------");
					console.info(jiaofei_status + "---------------");
					if(jiaofei_status == 0){
						
						$("#new_pay_thisPay").html("");
						$("#new_pay_thisPay").html(jiaofei_paysum);
						$("#new_pay_thisPayPar").html("");	
						$("#new_pay_thisPayPar").html(jiaofei_payparsum);				
					}else if(jiaofei_status == 1){
						
						$("#wait_pay_thisPay").html("");
						$("#wait_pay_thisPay").html(jiaofei_paysum);
						$("#wait_pay_thisPayPar").html("");
						$("#wait_pay_thisPayPar").html(jiaofei_payparsum);
					}else if(jiaofei_status == 2){
						
						$("#complete_pay_thisPay").html("");
						$("#complete_pay_thisPay").html(jiaofei_paysum);
						$("#complete_pay_thisPayPar").html("");
						$("#complete_pay_thisPayPar").html(jiaofei_payparsum);
					}
				},

				error:function(er){
			
					alert(er);
			
				}

			});
	}
	
	function jiaofeiSetNone(){
		$("#jiaofei_send_message_edit_tr").attr("style","display:none");
		$("#jiaofei_complete_pay_tr").attr("style","display:none");
		$("#jiaofei_wait_pay_tr").attr("style","display:none");
		$("#jiaofei_new_pay_tr").attr("style","display:none");
	}
	
	function getNewPayList(type,status){
		var path = "<%= path%>/api/v1/communities/${communityId}/users/admin/payments?type="+type+"&status="+status+"&pageNum="+payThisPageNum+"&pageSize="+payThisPageSize;
		getPayTop(type,status);
		if(status == 0){
			$("#new_pay_main_table").html("");	
		}else if(status == 1){
			$("#wait_pay_main_table").html("");
		}else if(status == 2){
			$("#complete_pay_main_table").html("");
		}
		if(myTimeOut != null){
			window.clearInterval(myTimeOut);
		}
		$.ajax({

				url: path,

				type: "GET",

				dataType:'json',

				success:function(data){
					console.info(data);
					if(data.status == "yes"){
						pageData = data.info.pageData;
						jiaofeiThisList = new Array();
						jiaofeiThisMap = {};
						for(var i = 0 ; i < pageData.length ; i++){
							var new_pay_table_tr_td = "";	
							nextPay = pageData[i].nextPay;
							lastPay = pageData[i].lastPay;
							userUnit = pageData[i].userUnit;
							userFloor = pageData[i].userFloor;
							paySum = pageData[i].paySum;
							emobId = pageData[i].emobId;
							payId = pageData[i].payId;
							nickname = pageData[i].nickname;
							phone = pageData[i].phone;	
							avatar = pageData[i].avatar;
							createTime = pageData[i].createTime;
							createTimeTemp = createTime;
							createTime = getSmpFormatDateByLong(createTime * 1000);
							handleTime = pageData[i].handleTime;
							if(status == 0){
								new_pay_table_tr_td = "<tr><td width=\"100%\"><div class=\"pay_table_main\"><div class=\"pay_table_top\">";
							
								new_pay_table_tr_td += "<input type=\"checkbox\" value=\"\" /> 订单号：<font id=\"\">"+pageData[i].payNo+"</font> 下单时间 <font id=\"\">"+createTime+"</font></div>";
								
								new_pay_table_tr_td += "<div class=\"pay_table_center\"><img alt=\"\" src=\""+avatar+"\"  class=\"pay_table_jiaofei_img\" />";
								
								new_pay_table_tr_td += "<font id=\"\" class=\"text_height_center\">"+nickname+"</font><font id=\"\" class=\"text_height_center\">"+phone+"</font>";
								
								new_pay_table_tr_td += "<font id=\"\" class=\"text_height_center\">"+userUnit+"单元"+userFloor+"室</font><font id=\"\" class=\"text_height_center\">"+paySum+"元</font>";
								new_pay_table_tr_td += "<font id=\"\" class=\"text_height_center\">等待时常：</font><font id=\"jiaofei_jishi_font_id_"+payId+"\"></font><input type=\"button\" class=\"margin_left\" onclick=\"new_pay_send_message("+payId+",1,'"+emobId+"');\" value=\" 缴费通知 \" id=\"\" name=\"\" /></div></div></td></tr>";
								$("#new_pay_main_table").append(new_pay_table_tr_td);	
								jiaofeiThisList.push("jiaofei_jishi_font_id_"+payId);
								jiaofeiThisMap["jiaofei_jishi_font_id_"+payId] = createTimeTemp;
							}else if(status == 1){
								new_pay_table_tr_td = "<tr><td width=\"100%\"><div class=\"pay_table_main\"><div class=\"pay_table_top\">";
								
								new_pay_table_tr_td += "<input type=\"checkbox\" value=\"\" /> 订单号：<font id=\"\">"+pageData[i].payNo+"</font> 下单时间 <font id=\"\">"+createTime+"</font></div>";
								
								new_pay_table_tr_td += "<div class=\"pay_table_center\"><img alt=\"\" src=\""+avatar+"\"  class=\"pay_table_jiaofei_img\" />";
								
								new_pay_table_tr_td += "<font id=\"\" class=\"text_height_center\">"+nickname+"</font><font id=\"\" class=\"text_height_center\">"+phone+"</font>";
								
								new_pay_table_tr_td += "<font id=\"\" class=\"text_height_center\">"+userUnit+"单元"+userFloor+"室</font><font id=\"\" class=\"text_height_center\">"+paySum+"元</font>";
								new_pay_table_tr_td += "<font id=\"\" class=\"text_height_center\">等待时常：</font>";
								new_pay_table_tr_td += "<font id=\"\" class=\"text_height_center\">充值前</font> <input type=\"text\" value=\"\" id=\"wait_pay_send_message_last_"+payId+"\" style=\"width: 45px;\" /> ";
					
								new_pay_table_tr_td += "<font id=\"\" class=\"text_height_center\">充值后</font> <input type=\"text\" value=\"\" id=\"wait_pay_send_message_next_"+payId+"\" style=\"width: 45px;\" />";
								new_pay_table_tr_td += "<input type=\"button\" class=\"margin_left\" onclick=\"wait_pay_send_message("+payId+",2,'"+emobId+"','wait_pay_send_message_last_"+payId+"','wait_pay_send_message_next_"+payId+"');\" value=\" 发 送 \"  name=\"\" /></div></div></td></tr>";
								
								$("#wait_pay_main_table").append(new_pay_table_tr_td);
							}else if(status == 2){
								new_pay_table_tr_td = "<tr><td width=\"100%\"><div class=\"pay_table_main\"><div class=\"pay_table_top\">";
								
								new_pay_table_tr_td += "<input type=\"checkbox\" value=\"\" /> 订单号：<font id=\"\">"+pageData[i].payNo+"</font> 下单时间 <font id=\"\">"+createTime+"</font></div>";
								
								new_pay_table_tr_td += "<div class=\"pay_table_center\"><img alt=\"\" src=\""+avatar+"\"  class=\"pay_table_jiaofei_img\" />";
								
								new_pay_table_tr_td += "<font id=\"\" class=\"text_height_center\">"+nickname+"</font><font id=\"\" class=\"text_height_center\">"+phone+"</font>";
								
								new_pay_table_tr_td += "<font id=\"\" class=\"text_height_center\">"+userUnit+"单元"+userFloor+"室</font><font id=\"\" class=\"text_height_center\">"+paySum+"元</font>";
								new_pay_table_tr_td += "<font id=\"\" class=\"text_height_center\">等待时常：</font>";
								
								new_pay_table_tr_td += "<font id=\"\" class=\"text_height_center\">充值前</font>  "+lastPay;
					
								new_pay_table_tr_td += "<font id=\"\" class=\"text_height_center\">充值后</font> "+nextPay;
								new_pay_table_tr_td += "</div></div></td></tr>";
								$("#complete_pay_main_table").append(new_pay_table_tr_td);
							}
							
						}
						myTimeOut = window.setInterval(function(){ 
							jiaofei_shifenmiao();
						}, 1000); 
						
					}
					
				},

				error:function(er){
			
					alert(er);
			
				}

			});
	}
	
	function new_pay_send_message(payId,status,emobId){
		var path = "<%= path%>/api/v1/communities/${communityId}/users/admin/payments/"+payId;
		
		var myJson = {
			
			"payStatus":status ,
			"emobId":emobId
		};
		
		$.ajax({

				url: path,

				type: "PUT",
				
				data: JSON.stringify(myJson),

				dataType:'json',

				success:function(data){
					console.info(data);
					getPayTop(jiaofei_type,jiaofei_status);
					getNewPayList(jiaofei_type,jiaofei_status);
				},

				error:function(er){
			
					alert(er);
			
				}

			});
	}
	
	function wait_pay_send_message(payId,status,emobId,lastId,nextId){
		var path = "<%= path%>/api/v1/communities/${communityId}/users/admin/payments/"+payId;
		
		lastValue = document.getElementById(lastId).value;
		
		nextValue = document.getElementById(nextId).value;
		
		var myJson = {
			"lastPay":lastValue ,
			"nextPay":nextValue ,
			"payStatus":status ,
			"emobId":emobId
		};
		
		$.ajax({

				url: path,

				type: "PUT",
				
				data: JSON.stringify(myJson),

				dataType:'json',

				success:function(data){
					console.info(data);
					getPayTop(jiaofei_type,jiaofei_status);
					getNewPayList(jiaofei_type,jiaofei_status);
				},

				error:function(er){
			
					alert(er);
			
				}

			});
	}
	
	function jiaofei_shifenmiao(){
		var date = new Date();
		console.info("执行");
		var len = jiaofeiThisList.length;
		for(var i = 0 ; i < len ; i++){
			tempId = jiaofeiThisList[i];
			tempLong = jiaofeiThisMap[tempId];
			console.info("当前时间"+date.getTime() / 1000);
			console.info("创建时间"+tempLong);
			tempResult = myFormatSeconds(date.getTime() / 1000 - tempLong);
			document.getElementById(tempId).innerHTML = tempResult;
		}
	}
</script>
</head>
<body>
	<table>
   		<tr>
   			<td width="150" height="80"><a href="javascript:void(0);" onclick="jiaofei_left_click(0);">新的缴费通知</a></td>
   			<td width="150"><a href="javascript:void(0);" onclick="jiaofei_left_click(1);">处理中的缴费</a></td>
   			<td width="150"><a href="javascript:void(0);" onclick="jiaofei_left_click(2);">完成的缴费</a></td>
   			<td width="150"><a href="javascript:void(0);" onclick="jiaofei_tongzhi_click();">通知类编辑</a></td>
   		</tr>
   		
   		<tr>
   			<td width="150" height="80"><a href="javascript:void(0);" onclick="jiaofei_top_click(1);">电费</a></td>
   			<td width="150"><a href="javascript:void(0);" onclick="jiaofei_top_click(2);">水费</a></td>
   			<td width="150"><a href="javascript:void(0);" onclick="jiaofei_top_click(3);">燃气费</a></td>
   			<td width="150"><a href="javascript:void(0);" onclick="jiaofei_top_click(4);">宽带费</a></td>
   		</tr>
   	</table>
   	
   	<table>
   		<tr style="display: block;" id="jiaofei_new_pay_tr">
   			<td>
   				<jsp:include flush="true" page="../jsp/new_pay.jsp">
								<jsp:param name="communityId" value="{parameterValue | ${communityId }}" />
								<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
								<jsp:param name="username" value="{parameterValue | ${username }}" />
								<jsp:param name="password" value="{parameterValue | ${password }}" />
							</jsp:include>
   			</td>
   		</tr>
   		
   		<tr style="display: none;" id="jiaofei_wait_pay_tr">
   			<td>
   				<jsp:include flush="true" page="../jsp/wait_pay.jsp">
								<jsp:param name="communityId" value="{parameterValue | ${communityId }}" />
								<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
								<jsp:param name="username" value="{parameterValue | ${username }}" />
								<jsp:param name="password" value="{parameterValue | ${password }}" />
							</jsp:include>
   			</td>
   		</tr>
   		
   		<tr style="display: none;" id="jiaofei_complete_pay_tr">
   			<td>
   				<jsp:include flush="true" page="../jsp/complete_pay.jsp">
								<jsp:param name="communityId" value="{parameterValue | ${communityId }}" />
								<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
								<jsp:param name="username" value="{parameterValue | ${username }}" />
								<jsp:param name="password" value="{parameterValue | ${password }}" />
							</jsp:include>
   			</td>
   		</tr>
   		
   		<tr style="display: none;" id="jiaofei_send_message_edit_tr">
   			<td>
   				<jsp:include flush="true" page="../jsp/send_message_edit.jsp">
								<jsp:param name="communityId" value="{parameterValue | ${communityId }}" />
								<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
								<jsp:param name="username" value="{parameterValue | ${username }}" />
								<jsp:param name="password" value="{parameterValue | ${password }}" />
							</jsp:include>
   			</td>
   		</tr>
   	</table>
</body>
</html>
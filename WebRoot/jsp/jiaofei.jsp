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
<title>物业缴费页</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->

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
		$("#payment_edit_div_id").attr("style","display:none");
	}
	
	function getNewPayList(type,status){
	    document.getElementById("jiaofei_type_repair_get").value=type;
		document.getElementById("jiaofei_repair_status_get").value=status;
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
					   data= data.info;
					   $("#master_repair_datai_PageNum_get_1d").val(data.num);
						$("#master_repair_datai_PageSize_get_id").val(
								data.pageCount);

						$("#master_repair_datai_PageNum_id").html(data.num);
						$("#master_repair_datai_PageSize_id").html(data.pageCount);
						$("#master_repair_datai_sum_id").html(data.rowCount);
						pageData =data.pageData;
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
							var username = pageData[i].username;	
							avatar = pageData[i].avatar;
							createTime = pageData[i].createTime;
							createTimeTemp = createTime;
							createTime = getSmpFormatDateByLong(createTime * 1000);
							handleTime = pageData[i].handleTime;
							payAddress = pageData[i].payAddress;
							if(status == 0){
							
							new_pay_table_tr_td = "<tr><td class=\"title\" colspan=\"8\"><span class=\"checkbox-style\"><input type=\"hidden\" id=\"hiden_"+payId+"\" value=\""+emobId+"\"/>"+
							"<input type=\"checkbox\" value=\""+payId+"\" id=\"checkboxInput_"+payId+"\" name=\"checkboxInput2\" /><label for=\"checkboxInput_"+payId+"\"></label></span><span class=\"payment-order\">订单号："+pageData[i].payNo+"</span><span class=\"payment-time\">下单时间:"+createTime+"</span></td></tr><tr><td><img src=\""+avatar+"\"/></td><td>"+nickname+"</td><td>"+username+"</td><td><p>"+payAddress+"</p><p>房主-<span>"+nickname+"</span></p></td><td><span>"+paySum+"</span>元</td><td>等待时长<span id=\"jiaofei_jishi_font_id_"+payId+"\"></span></td><td><a class=\"payment-notice-btn\" href=\"javascript:void(0);\" onclick=\"new_pay_send_message("+payId+",1,'"+emobId+"');\">缴费通知</a></td></tr>";
							
							
								$("#new_pay_main_table").append(new_pay_table_tr_td);	
								jiaofeiThisList.push("jiaofei_jishi_font_id_"+payId);
								jiaofeiThisMap["jiaofei_jishi_font_id_"+payId] = createTimeTemp;
							}else if(status == 1){
							
							
							
							new_pay_table_tr_td = "<tr><td class=\"title\" colspan=\"8\"><span class=\"checkbox-style\"></span><span class=\"payment-order\">订单号："+pageData[i].payNo+"</span><span class=\"payment-time\">下单时间:"+createTime+"</span></td></tr><tr><td><img src=\""+avatar+"\"/></td><td>"+nickname+"</td><td>"+username+"</td><td><p>"+payAddress+"</p><p>房主-<span>"+nickname+"</span></p></td><td><span>"+paySum+"</span>元</td><td>等待时长<span>1小时</span></td><td>"+
							"<p class=\"pay-before\">充值前<input type=\"text\" value=\"\" id=\"wait_pay_send_message_last_"+payId+"\" style=\"width: 45px;\" /></p>"+
							"<p class=\"pay-after\">充值后<input type=\"text\" value=\"\" id=\"wait_pay_send_message_next_"+payId+"\" style=\"width: 45px;\" /></p>"+
							"</td><td><a class=\"pay-send\" href=\"javascript:void(0);\" onclick=\"wait_pay_send_message("+payId+",2,'"+emobId+"','wait_pay_send_message_last_"+payId+"','wait_pay_send_message_next_"+payId+"');\">发送</a></td></tr>";
							
		
		
		
								
								$("#wait_pay_main_table").append(new_pay_table_tr_td);
							}else if(status == 2){
							

							new_pay_table_tr_td ="<tr><td class=\"title\" colspan=\"8\"><span class=\"checkbox-style\"></span><span class=\"payment-order\">订单号："+pageData[i].payNo+"</span><span class=\"payment-time\">下单时间:"+createTime+"</span></td></tr><tr><td><img src=\""+avatar+"\"/></td><td>"+nickname+"</td><td>"+username+"</td><td><p>"+payAddress+"</p><p>房主-<span>"+nickname+"</span></p></td><td><span>"+paySum+"</span>元</td><td>等待时长<span>1小时</span></td><td><p>充值前<span>"+lastPay+"</span>度</p><p>充值后<span>"+nextPay+"</span>度</p></td></tr>";
							
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
	
		var len = jiaofeiThisList.length;
		for(var i = 0 ; i < len ; i++){
			tempId = jiaofeiThisList[i];
			tempLong = jiaofeiThisMap[tempId];
			
			tempResult = myFormatSeconds(date.getTime() / 1000 - tempLong);
			document.getElementById(tempId).innerHTML = tempResult;
		}
	}
	
    function paymentEditClick() {
		getStandard(1);
		getStandardEntry(1);
		jiaofeiSetNone();

		$("#payment_edit_div_id").attr("style", "display:block");

	}
	
	
	function jiaofei_left_set_none(){
	
	   document.getElementById("jiaofei_left_click_1").className="";
	   document.getElementById("jiaofei_left_click_2").className="";
	   document.getElementById("jiaofei_left_click_3").className="";
	   document.getElementById("jiaofei_left_click_4").className="";
	   document.getElementById("jiaofei_left_click_5").className="";
	}
	
	function jiaofei_left_clile(type){
	   jiaofei_left_set_none();
	    document.getElementById("jiaofei_left_click_"+type).className="select";
	    if(type==5){
	    document.getElementById("payment-head").style.display="none";
	    }else{
	    document.getElementById("payment-head").style.display="";
	    }
	    jiaofei_top_clile(1);
	}
	
	function jiaofei_top_set_none(){
	   document.getElementById("jiaofei_top_1").className="";
	   document.getElementById("jiaofei_top_2").className="";
	   document.getElementById("jiaofei_top_3").className="";
	   document.getElementById("jiaofei_top_4").className="";
	}
	function jiaofei_top_clile(type){
	jiaofei_top_set_none();
	 document.getElementById("jiaofei_top_"+type).className="select";
	}
	
			
	function getJiaofeiRepairDetailPage(num) {
		var page_num = 1;
		var repairRecordPageNum = document
				.getElementById("master_repair_datai_PageNum_get_id").value;
		var repairRecordPageSize = document
				.getElementById("master_repair_datai_PageSize_get_id").value;
		var type = document
				.getElementById("jiaofei_type_repair_get_id").value;
		var status = document
				.getElementById("jiaofei_repair_status_get_id").value;

       
     
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
		payThisPageNum=page_num;
		 getNewPayList(type,status);
	}
	
	function checkbox_all(){
	    var obj=document.getElementsByName("checkboxInput2"); 
	    var p=document.getElementById("new_pay_checkbox").checked; 
	    
	    if(p){
		     for(var i=0; i<obj.length; i++){
				     obj[i].checked="true";
	              
	        }
	    }else{
	    
	     for(var i=0; i<obj.length; i++){
               obj[i].checked=false;
           }
	    }
      
	}
	
	function checkbox_message(){
			var obj=document.getElementsByName("checkboxInput2"); //选择所有name="'test'"的对象，返回数组
			for(var i=0; i<obj.length; i++){
				if(obj[i].checked) {
				var pay_id=  obj[i].value;
				 var emob= document.getElementById("hiden_"+pay_id).value;
				 checkbox_message_dan(pay_id,emob);
			  }
			}
		getPayTop(jiaofei_type,jiaofei_status);
					getNewPayList(jiaofei_type,jiaofei_status);
	}
	
function checkbox_message_dan(payId,emobId) {
	var path = "<%= path%>/api/v1/communities/${communityId}/users/admin/payments/"+payId;
		
		var myJson = {
			
			"payStatus":1 ,
			"emobId":emobId
		};
		
		$.ajax({

				url: path,

				type: "PUT",
				
				data: JSON.stringify(myJson),

				dataType:'json',

				success:function(data){
				},

				error:function(er){
			
					alert(er);
			
				}

			});
}	
</script>
</head>
<body>

<input type="hidden" id="jiaofei_type_repair_get"/>
<input type="hidden" id="jiaofei_repair_status_get"/>
<section>
	<div class="content clearfix">
		<jsp:include flush="true" page="public/payment_left.jsp"></jsp:include>
		<div class="activity-right-body">
			<div class="payment-head" id="payment-head">
				<div class="payment-tab">
					<a class="select" href="javascript:void(0);" onclick="jiaofei_top_click(1);jiaofei_top_clile('1')" id="jiaofei_top_1">电费</a>
					<a href="javascript:void(0);" onclick="jiaofei_top_click(2);jiaofei_top_clile('2')" id="jiaofei_top_2">水费</a>
					<a href="javascript:void(0);" onclick="jiaofei_top_click(3);jiaofei_top_clile('3')" id="jiaofei_top_3">燃气费</a>
					<a href="javascript:void(0);" onclick="jiaofei_top_click(4);jiaofei_top_clile('4')" id="jiaofei_top_4">宽带费</a>
				</div>
			</div>
			<div class="payment-include-box" style="display: block;" id="jiaofei_new_pay_tr">
				<jsp:include flush="true" page="../jsp/new_pay.jsp">
					<jsp:param name="communityId" value="{parameterValue | ${communityId }}" />
					<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
					<jsp:param name="username" value="{parameterValue | ${username }}" />
					<jsp:param name="password" value="{parameterValue | ${password }}" />
				</jsp:include>
			</div>	
			<div class="payment-include-box" style="display: none;" id="jiaofei_wait_pay_tr">
				<jsp:include flush="true" page="../jsp/wait_pay.jsp">
					<jsp:param name="communityId" value="{parameterValue | ${communityId }}" />
					<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
					<jsp:param name="username" value="{parameterValue | ${username }}" />
					<jsp:param name="password" value="{parameterValue | ${password }}" />
				</jsp:include>
			</div>
				
			<div class="payment-include-box" style="display: none;" id="jiaofei_complete_pay_tr">
				<jsp:include flush="true" page="../jsp/complete_pay.jsp">
					<jsp:param name="communityId" value="{parameterValue | ${communityId }}" />
					<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
					<jsp:param name="username" value="{parameterValue | ${username }}" />
					<jsp:param name="password" value="{parameterValue | ${password }}" />
				</jsp:include>
			</div>	
			<div class="payment-include-box" style="display: none;" id="jiaofei_send_message_edit_tr">
				<jsp:include flush="true" page="../jsp/send_message_edit.jsp">
					<jsp:param name="communityId" value="{parameterValue | ${communityId }}" />
					<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
					<jsp:param name="username" value="{parameterValue | ${username }}" />
					<jsp:param name="password" value="{parameterValue | ${password }}" />
				</jsp:include>
			</div>	
			
					<div class="main clearfix" id="payment_edit_div_id"
					style="display: none;">
					<jsp:include flush="true" page="../jsp/payment/payment_edit.jsp">

						<jsp:param name="communityId"
							value="{parameterValue | ${communityId }}" />
						<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
						<jsp:param name="username" value="{parameterValue | ${username }}" />
						<jsp:param name="password" value="{parameterValue | ${password }}" />
					</jsp:include>
				</div>
					
						<div class="divide-page" style="float: left; width: 100%; height: 50px; clear: both; text-align: center;">


							<input type="hidden" id="master_repair_datai_PageNum_get_id" />
							<input type="hidden" id="master_repair_datai_PageSize_get_id" />

							<a href="javascript:void(0);"
								onclick="getJiaofeiRepairDetailPage(-3);">首页</a>
							<a href="javascript:void(0);"
								onclick="getJiaofeiRepairDetailPage(-1);">上一页</a> 当前第
							<font id="master_repair_datai_PageNum_id"></font> 页 共
							<font id="master_repair_datai_PageSize_id"></font> 页 共
							<font id="master_repair_datai_sum_id"></font> 条

							<a href="javascript:void(0);"
								onclick="getJiaofeiRepairDetailPage(-2);">下一页</a>
							<a href="javascript:void(0);"
								onclick="getJiaofeiRepairDetailPage(-4);">尾页</a>
						</div>											
		</div>
	</div>
</section>
</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>缴费页</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
 <body>
<div class="payment-summary clearfix">
	<p>已完成缴费<span id="complete_pay_thisPay">0</span>笔</p>
	<p>缴费金额<span id="complete_pay_thisPayPar">0</span></p>
</div>
 <div class="payment-list">
	<div class="payment-item-all" >
		<div class="payment-item-detail">
			<table id="complete_pay_main_table">
				<tr>
					<th colspan="8">
						<span class="checkbox-style">
							<input type="checkbox" value="1" id="checkboxInput1" name="" />
		  					<label for="checkboxInput1"></label>
		  				</span>
		  				<span class="payment-order">订单号：12345876904</span>
		  				<span class="payment-time">下单时间:2015-2-13 12：30</span>
					</th>
				</tr>
			</table>
		
		</div>
		<!--<ul>
			<li>
				<div>
					<span class="checkbox-style">
						<input type="checkbox" value="1" id="checkboxInput1" name="" />
	  					<label for="checkboxInput1"></label>
	  				</span>
	  				<span class="payment-order">订单号：12345876904</span>
	  				<span class="payment-time">下单时间:2015-2-13 12：30</span>
				</div>
				<div class="payment-item-detail">
					<table>
						<tr>
							<td><img src="/images/repair/b1.jpg"/></td>
							<td>王朝</td>
							<td>18513595854</td>
							<td>
								<p>2号楼4单元201</p>
								<p>房主-<span>王小明</span></p>
							</td>
							<td><span>200</span>元</td>
							<td>等待时长<span>1小时</span></td>
							<td>
								<p>充值前<span>3</span>度</p>
								<p>充值后<span>100</span>度</p>
							</td>
						</tr>
					</table>
				</div>
			</li>
		</ul>
	--></div>
</div> 

  </body>
</html>

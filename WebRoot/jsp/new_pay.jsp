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
<script type="text/javascript">
function preview(oper)
{
if (oper < 10)
{
bdhtml=window.document.body.innerHTML;//获取当前页的html代码
sprnstr="<!--startprint"+oper+"-->";//设置打印开始区域
eprnstr="<!--endprint"+oper+"-->";//设置打印结束区域
prnhtml=bdhtml.substring(bdhtml.indexOf(sprnstr)+18); //从开始代码向后取html

prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));//从结束代码向前取html
window.document.body.innerHTML=prnhtml;
window.print();
window.document.body.innerHTML=bdhtml;
} else {
window.print();
}
} 
</script>
  </head>
  
<body>
<div class="payment-summary clearfix">
	<p>当前缴费<span id="new_pay_thisPay">0</span>笔</p>
	<p>缴费金额<span id="new_pay_thisPayPar">0</span></p>
</div>
<div class="payment-list">
	<div class="payment-operate">
		<span class="checkbox-style">
			<input type="checkbox" value="1" onclick="checkbox_all();" name="new_pay_checkbox" id="new_pay_checkbox" />
					<label for="new_pay_checkbox"></label>
				</span>
		<label>全选</label>
		<a class="payment-notice" href="javascript:;" name="new_pay_tongzhi" id="new_pay_tongzhi" onclick="checkbox_message();">通知</a>
		<a class="payment-print" href="javascript:;" name="new_pay_dayin" id="new_pay_dayin" onclick="preview('1');">打印</a>
	</div>

	<div class="payment-item-all" >
		<div class="payment-item-detail">
		<!--startprint1-->
			<table id="new_pay_main_table">
				<tr>
					<td colspan="8">
						<span class="checkbox-style">
							<input type="checkbox" value="1" id="checkboxInput1" name="" />
		  					<label for="checkboxInput1"></label>
		  				</span>
		  				<span class="payment-order">订单号：12345876904</span>
		  				<span class="payment-time">下单时间:2015-2-13 12：30</span>
					</td>
				</tr>
				<tr>
					<td><img src="/images/repair/b1.jpg"/></td>
					<td>王朝</td>
					<td>18513595854</td>
					<td>
						<p>2号楼4单元201</p>
						<p>房主-<span>王小明</span></p>
					</td>
					<td><span>200</span>元</td>
					<td>等待时长</td>
					<td><a href="javascript:;">缴费通知</a></td>
				</tr>
			</table>
			<!--endprint1--> 
		</div>
	</div>
	
	
	<!--<div class="payment-item-all" id="new_pay_main_table">
		<ul>
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
							<td>等待时长</td>
							<td><a href="javascript:;">缴费通知</a></td>
						</tr>
					</table>
				</div>
			</li>
		</ul>
	</div>
-->
</div>
</body>
</html>

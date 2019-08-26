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
<title>添加维修类型_小间物业管理系统</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->

<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>

<script src="${pageContext.request.contextPath }/js/jquery-1.6.js"></script>
<script src="${pageContext.request.contextPath }/js/admin/bonus_coin_conf.js"></script>
<style type="text/css">
.community-box{
	
	margin: 24px;
    width: 78%;
    margin-left: 19%;
    margin-top: 6%;
    padding-left: 10px;
    padding-right: 10px;
	
}

.sort-edit{
  	width: 400px;
  	height:300px;
    background: #fff;
    border: 1px solid #d6d6d6;
    left: 40%;
    top: 20%;
    z-index: 11;
    padding: 20px;
    border-radius: 5px;

}

.sort-edit input{
    display: inline-block;
    width: 80px;
    height: 25px;
    line-height: 20px;
    padding-left: 8px;

}

.adduse-item span {
    display: inline-block;
    width: 170px;
}
</style>
</head>
<body>
	<jsp:include flush="true" page="../public/admin_head.jsp"/>
	<section>
		<div class="admin_content clearfix center-personal-info-content">
			<jsp:include flush="true" page="../public/admin_sort_left.jsp"></jsp:include>
			<div class="admin-right-body">
				<div class="main clearfix">
					<div class="user-titile">帮帮币配置
						<th style="text-align:right;">
							<input class="search-input" type="text" placeholder="输入小区名称" id="search-input" style="margin-left: 55%;">
							<a class="search-btn" href="javascript:void(0);" onclick="window.bonusCoin.getCommunities('first');">&nbsp;&nbsp;</a>
						</th>
					</div>
				</div>
			</div>
			
			<div class="community-box" >
				<table class="express-list-table" id="maintenanceMain" style="text-align: left;">
					<tr>
						<th>小区名称</th>
						<th>帮帮币功能状态</th>
						<th>帮帮币抵扣金额比例(金:币)</th>
						<th>购物分享送帮帮币</th>
						<th>快店消费送帮帮币(金:币)</th>
						<th style="text-align:right;">
							<a  href="javascript:void(0);" onclick="window.bonusCoin.edit();">操作</a>
						</th>
					</tr>
					
					<tr class="odd">
						<td><span>狮子城</span></td>
						<td><span>开启</span></td>
						<td>100:1</td>
						<td>10</td>
						<td>100:1</td>
						<td style="text-align:right;">
							<a href="javascript:void(0);" onclick="window.bonusCoin.edit();">操作</a>
						</td>
					</tr>
					<tr class="odd">
						<td><span>狮子城</span></td>
						<td><span>开启</span></td>
						<td>100:1</td>
						<td>10</td>
						<td>100:1</td>
						<td style="text-align:right;">
							<a href="javascript:void(0);" onclick="window.bonusCoin.edit();">操作</a>
						</td>
					</tr>
					<tr class="odd">
						<td><span>狮子城</span></td>
						<td><span>开启</span></td>
						<td>100:1</td>
						<td>10</td>
						<td>100:1</td>
						<td style="text-align:right;">
							<a href="javascript:void(0);" onclick="window.bonusCoin.edit();">操作</a>
						</td>
					</tr>
					<tr class="odd">
						<td><span>狮子城</span></td>
						<td><span>开启</span></td>
						<td>100:1</td>
						<td>10</td>
						<td>100:1</td>
						<td style="text-align:right;">
							<a href="javascript:void(0);" onclick="window.bonusCoin.edit();">操作</a>
						</td>
					</tr>
					
				</table>
			</div>
			
			<div class="divide-page" style="float: left; width: 100%; height: 50px; clear: both; text-align: center;" id="page">
					<input type="hidden" id="repairRecordPageNum_get" />
					<input type="hidden" id="repairRecordPageSize_get" />
			
					<a href="javascript:void(0);" onclick="window.bonusCoin.getCommunities('first');">首页</a>
					<a href="javascript:void(0);" onclick="window.bonusCoin.getCommunities('pre');">上一页</a>
					当前第
					<font id="repairRecordPageNum"></font> 页 共
					<font id="repairRecordPageSize"></font> 页 共
					<font id="repairRecordSum"></font> 条
			
					<a href="javascript:void(0);" onclick="window.bonusCoin.getCommunities('next');">下一页</a>
					<a href="javascript:void(0);" onclick="window.bonusCoin.getCommunities('last');">尾页</a>
				</div>
				
				<div class="sort-edit" id="maintenceTable" style="display: none;margin: 0 auto;position: fixed;">
					<form action="#" method="POST" enctype="multipart/form-data" id="bonus_coin_form">
					<input  type="hidden"  name="communityId" id="communityId" />
					<div class="adduse-item">
						<span>小区名称</span>
						<input class="textLength" type="text"  name="communityName" id="communityName" style="width: 180px;" readonly="readonly"/>
					</div>
					<div class="adduse-item">
						<span>帮帮币功能状态</span>
						<select name="status"  style="width:100px;" id="status">
							<option value="yes">开启</option>
							<option value="no">关闭</option>
						</select>
					</div>
					<div class="adduse-item">
						<span>帮帮币抵扣金额比例(金:币)</span>
						<input type="text"   placeholder="如：1" name ="exchange" id="exchange" /><strong>:</strong>
						<input type="text"   placeholder="如：100" name="exchangeCoin" id="exchangeCoin"/>
					</div>
					<div class="adduse-item">
						<span>购物分享送帮帮币</span>
						<input type="text"   placeholder="如：10"  name="shareCoin" style="width: 180px;" />
					</div>
					<div class="adduse-item">
						<span>快店消费送帮帮币(金:币)</span>
						<input type="text"   placeholder="如：10" name="expense" id="expense"/><strong>:</strong>
						<input type="text"   placeholder="如：5" name="expenseCoin" id="expenseCoin"/>
					</div>
					<div class="sort-save">
						<a href="javascript:void(0);" onclick="window.bonusCoin.bonusCoinEditCancle();">取消</a>
						<a href="javascript:void(0);" onclick="window.bonusCoin.bonusCoinSubmit();">保存</a>
					</div>
				</form>
			</div>
			
		</div>
	</section>
	
		
</body>
<jsp:include flush="true" page="../public/footer.jsp"></jsp:include>
<script type="text/javascript">
	$(function() {
		window.basePath = '<%=basePath%>';
		window.bonusCoin.getCommunities('first');
	});

</script>
</html>
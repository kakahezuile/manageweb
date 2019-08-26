<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String webTitle = properties.getProperty("webTitle"); 
%>
<%


String emobId=request.getParameter("emobId");

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
<title>用户统计_小间运营系统</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/operation.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->

<script src="${pageContext.request.contextPath }/js/respond.min.js?version=<%=versionNum %>"></script>
<script src="${pageContext.request.contextPath }/js/jquery-1.6.js?version=<%=versionNum %>"></script>
<script src="${pageContext.request.contextPath }/js/operation.js?version=<%=versionNum %>"></script>
<script src="${pageContext.request.contextPath }/js/reckoning/reckoning-deduct.js?version=<%=versionNum %>"></script>

</head>
<body>
<input id="master_repir_startTime" type="hidden" value="">
<input id="master_repir_endTime" type="hidden" value="">
<input id="emob_id" type="hidden" value="<%=emobId %>">
	<section>
		<div class="content clearfix">
			<jsp:include flush="true" page="../../public/reckoning-left.jsp"/>
			<div class="right-body">
				<div class="reckoning-title">扣款申请</div>
				<div class="reckoning-body">
					<table id="reckoning_deduct">
						<tr>
							<th>扣款申请客服</th>
							<th>申请人电话</th>
							<th>扣款说明</th>
							<th>扣款金额</th>
							<th>投诉详情</th>
							<th>交易详情</th>
							<th><a href="#"></a>扣款</a></th>
						</tr>
						<tr class="odd">
							<td>李晓静</td>
							<td>18512545854</td>
							<td>被投诉东西过期</td>
							<td>0</td>
							<td><a href="${pageContext.request.contextPath }/jsp/operation/reckoning/reckoning-complain.jsp">投诉详情</a></td>
							<td><a href="javascript:;">交易详情</a></td>
							<td><a class="reckoning" href="javascript:;">扣款</a></td>
						</tr>
					</table>
				</div>
				
				<div class="divide-page" 
							style="float: left; width: 100%; height: 50px; clear: both; text-align: center;">


							<input type="hidden" id="master_repair_datai_PageNum_get" />
							<input type="hidden" id="master_repair_datai_PageSize_get" />

							<a href="javascript:void(0);"
								onclick="getMasterRepairDetailPage(-3);">首页</a>
							<a href="javascript:void(0);"
								onclick="getMasterRepairDetailPage(-1);">上一页</a> 当前第
							<font id="master_repair_datai_PageNum"></font> 页 共
							<font id="master_repair_datai_PageSize"></font> 页 共
							<font id="master_repair_datai_sum"></font> 条

							<a href="javascript:void(0);"
								onclick="getMasterRepairDetailPage(-2);">下一页</a>
							<a href="javascript:void(0);"
								onclick="getMasterRepairDetailPage(-4);">尾页</a>
						</div>
			</div>
		</div>
	</section>
	<input id="deduction_id" type="hidden"/>
	<div class="reckoning-pop" id="activity-refuse" style="display:none;">
		<div class="reckoning-pop-title">请输入扣款金额<a href="javascript:activityRefuseClose();">&nbsp;</a></div>
		<div class="reckoning-input">
			<input type="text" id="deduction_price" placeholder="请输入扣款金额"/>
		</div>
		<div class="reckoning-submit">
			<a href="javascript:;" onclick="deduction();">确定</a>
		</div>
	</div>
	
	
<div class="activity-blank-bg" style="display:none;">&nbsp;</div>
</body>
<script type="text/javascript">
	function reckoning() {
        var emob=  document.getElementById("emob_id").value;
		var d = getPreviousWeekStartEnd();
		getReckoning(emob, 1, "2013-1-1 00:00:00", d.end);

	}
	reckoning();
</script>
<script type="text/javascript">
			function activityRefuse(id){
			     document.getElementById("deduction_id").value=id;
				$("#activity-refuse").show();
				$(".activity-blank-bg").show();
			}
			function activityRefuseClose(){
				$("#activity-refuse").hide();
				$(".activity-blank-bg").hide();
			}
		</script>
<script type="text/javascript">


var communityId=window.parent.document.getElementById("community_id_index").value;

function monitorComplaintsServiceClick(from,to){
		//var path = "<%= path%>/api/v1/communities/"+communityId+"/monitorComplaints/messages?emobIdUser="+from+"&emobIdShop="+to+"&timestamp="+complaintsTime+"&isMoreThan=true";
		var path = "/api/v1/usersMessages?messageFrom="+from+"&messageTo="+to;
		$.ajax({

			url: path,

			type: "GET",

			dataType:'json',

			success:function(data2){
				addMonitorComplaintsTopTable(data2);
				if(data2.status == "yes"){
					addMonitorComplaintsTopTable(data2);	
				}
			},

			error:function(er){
			
				alert(er);
			
			}

		});
	}
	
		function addMonitorComplaintsTopTable(data2){
		$("#MonitorComplaintsTopTable2").html("");
					data2 = data2.info;
					var len = data2.length;
					
					for(var i = 0 ; i < len ; i++){
					
						if(i == 0){
							monitorComplaintsMinTime = data2[i].timestamp;
						}
						if(i == len-1){
							monitorComplaintsMaxTime = data2[i].timestamp;
						}
						nickName = data2[i].nickname;
						msg = data2[i].msg; 
						
						outTime = getSmpFormatDateByLong(data2[i].timestamp);
						$("#MonitorComplaintsTopTable2").append("<tr><td class=\"MonitorComplaintsTextCenter\">— "+outTime+" —</td></tr>");
						if(data2[i].messageFrom == monitorComplaintsEmobId){  // 左边
							$("#MonitorComplaintsTopTable2").append("<tr><td class=\"MonitorComplaintsTextLeft\"><font class=\"MonitorComplaintsUserName\">"+nickName+"</font> : "+msg+"</td></tr>");
						}else{ // 右边
							$("#MonitorComplaintsTopTable2").append("<tr><td class=\"MonitorComplaintsTextRight\">"+msg+" : <font class=\"MonitorComplaintsUserName\">"+nickName+"</font></td></tr>");
						}
					}
		
	}
	
		</script>

<jsp:include flush="true" page="../../public/footer.jsp"></jsp:include>
</html>
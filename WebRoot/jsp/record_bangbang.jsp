<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	Properties properties = PropertyTool
			.getPropertites("/configure.properties");
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
		<base href="<%=basePath%>">
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">

		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
		<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
		<meta name="keywords" content="小间物业,小间物业管理系统" />
		<meta name="Description" content="小间物业,小间物业管理系统" />
		<title>发放帮帮券_间物业管理系统</title>


		<script type="text/javascript">
	
	
	function record_bonus(pageNum){
		var path = "<%=path%>/api/v1/communities/${communityId}/bonuses/statisticsTimeBonus?pageSize=5&pageNum="+pageNum;
		
			$.ajax({

				url: path,

				type: "GET",

				dataType:'json',

				success:function(data){
					data=data.info;
					
						$("#record_bangbang_number_get").val(data.num);
						$("#record_bangbang_sizepage_get").val(
								data.pageCount);

						$("#record_bangbang_number_id").html(data.num);
						$("#record_bangbang_sizepage_id").html(data.pageCount);
						$("#record_bangbang_sum_id").html(data.rowCount);
					data=data.pageData;
						var repair_overman = $("#record_bangbang_id");
						repair_overman.empty();
						//var liList='';

						for ( var i = 0; i < data.length; i++) {
						    var da=  getFormatDate(new Date(data[i].createTime*1000));
							var liList = "<table><tr><th>&nbsp;</th><th>经手人：</th><th>时间:"+ da+"</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th></tr>";
                               liList+="<tr class=\"odd\"><td > 类型</td><td>面额</td><td>数量</td><td>发放金额</td><td>送达对象</td></tr>";
							liList+="<tr class=\"odd\"><td >";
							if(data[i].bonusType=='1'){
							liList+="通用";
							}else{
							liList+="专用";
							}
							liList+="</td><td>"+data[i].bonusPar+"</td><td>"+data[i].bonusNum+"</td><td>"+data[i].bonusParSum+"</td><td>"+data[i].delivery+"</td></tr></table>";
							repair_overman.append(liList);
						}

					
				},

				error:function(er){
			
					alert(er);
			
				}

			});
	}
	
	function getRecordBangBang(num) {
		var page_num = 1;
		var repairRecordPageNum = document
				.getElementById("record_bangbang_number_get").value;
		var repairRecordPageSize = document
				.getElementById("record_bangbang_sizepage_get").value;
		
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

		record_bonus( page_num);
	}
	
</script>
	</head>
	<body>
		<section>
			<div class="content clearfix">
				<jsp:include flush="true" page="./public/bangbang_left.jsp" />
				<div class="express-right-body">
					<div class="repair-title-line">
						<p class="bangbang-ticket-title">
							发放明细
						</p>
					</div>
					<div class="send_bangbang">

							<div class="shops-list" id="record_bangbang_id">
						<table>
							<tr>
								<th>&nbsp;</th>
								<th>
									经手人：
								</th>
								<th>
									时间
								</th>
								<th>
									&nbsp;
								</th>
							</tr>
							<tr class="odd">
								<td > 类型</td>
								<td>
									面额
								</td>
								<td>
									数量
								</td>
								<td>
								发放金额								
								</td>
								<td>
								送达对象						
								</td>
							</tr>
							<tr class="odd">
								<td > 类型</td>
								<td>
									面额
								</td>
								<td>
									数量
								</td>
								<td>
								发放金额								
								</td>
								<td>
								送达对象						
								</td>
							</tr>
						</table>
					
					</div>
						<div class="divide-page" 
					style="float: left; width: 100%; height: 50px; clear: both; text-align: center;">


					<input type="hidden" id="record_bangbang_number_get" />
					<input type="hidden" id="record_bangbang_sizepage_get" />

					<a href="javascript:void(0);" onclick="getRecordBangBang(-3);">首页</a>
					<a href="javascript:void(0);" onclick="getRecordBangBang(-1);">上一页</a>
					当前第
					<font id="record_bangbang_number_id"></font> 页 共
					<font id="record_bangbang_sizepage_id"></font> 页 共
					<font id="record_bangbang_sum_id"></font> 条

					<a href="javascript:void(0);" onclick="getRecordBangBang(-2);">下一页</a>
					<a href="javascript:void(0);" onclick="getRecordBangBang(-4);">尾页</a>
				</div>
					</div>
				</div>
			</div>
		</section>
	</body>
</html>

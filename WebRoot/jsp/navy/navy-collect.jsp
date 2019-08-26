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

<%

String community_id=request.getParameter("community_id");
String communityName=request.getParameter("communityName");
String str = new String(communityName.getBytes("ISO-8859-1"),"UTF-8");
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
		<title>注册用户数_小间运营系统</title>
		
		<link
			href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>"
			type="image/x-icon" rel="shortcut icon">
		<link
			href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<link
			href="${pageContext.request.contextPath }/css/navy.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<link
			href="${pageContext.request.contextPath }/css/calendar.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">

		<script src="${pageContext.request.contextPath }/js/jquery-1.6.js?version=<%=versionNum %>"></script>
		<script src="${pageContext.request.contextPath }/js/teamwork/teamwork-survey.js?version=<%=versionNum%>"></script>
		
		
<script type="text/javascript">
	
	
	function getUseAmountList(pageNum) {

	// document.getElementById("shop_sort_id").value;
	var path = "<%=path %>/api/v1/communities/"+<%=community_id%>+"/lifeCircle/getFavoriteLifeCire?pageNum="+pageNum+"&pageSize=20";
	$.ajax({
				url : path,
				type : "GET",
				dataType : 'json',
				success : function(data) {
					data = data.info;
					$("#repairRecordPageNum_get").val(data.num);
					$("#repairRecordPageSize_get").val(data.pageCount);

					$("#repairRecordPageNum").html(data.num);
					$("#repairRecordPageSize").html(data.pageCount);
					$("#repairRecordSum").html(data.rowCount);
					
					data = data.pageData;
					var repair_overman = $("#statistics_list_id");
					repair_overman.empty();
					repair_overman
							.append("<tr class=\"odd\"><tr>"+
								"<th>话题内容</th>"+
								"<th>所在小区</th>"+
								"<th>发布人</th>"+
								"<th>获赞人品</th>"+
								"<th>获评论量</th>"+
								"<th>发布时间</th>"+
								"<th>操作</th>"+
							"</tr></tr>");
					for ( var i = 0; i < data.length; i++) {
						var strLifeContent = data[i].lifeContent;
						if(strLifeContent==""){
							strLifeContent = "<无文字主题>";
						}
						var liList="" ;
						liList+="<tr ><td><div class=\"navy-topic\">"+
										"<a href=\"<%=path %>/jsp/navy/navy-life-detail.jsp?activitiesId="+data[i].lifeCircleId+"&community_id=<%=community_id%>&communityName=<%=str%>\" title=\""+strLifeContent+"\">"+strLifeContent+"</a>"+
									"</div>"+"</td></td>";
						
						liList+="<td><%=str%></td>";
						liList+="<td>"+data[i].nickname+"</td>";
						
						liList+="<td>"+data[i].praiseSum+"</td>";
						liList+="<td>"+data[i].contentSum+"</td>";
						 var myDate=new Date(data[i].createTime*1000);
						liList+="<td>"+myDate.getFullYear()+"-"+(myDate.getMonth()+1)+"-"+myDate.getDate()+"</td>";
						liList+="<td>"+
									"<a href=\"javascript:;\" class=\"enshrine\" id=\"del"+data[i].favoriteId+"\" onclick=\"delFavorite(this);\">删除</a>"+
									"<a href=\"<%=path %>/jsp/navy/navy-life-favorite.jsp?community_id=<%=community_id %>&communityName=<%=str %>&activitiesId="+data[i].lifeCircleId+"\" class=\"transmit\">转发</a>"+
								"</td>";
						liList+="</tr>";
						repair_overman.append(liList);
					}
				},
				error : function(er) {
				}
			});

    }
	getUseAmountList(1);
	
	function delFavorite(obj){
		var delid = obj.id;
		var favoriteId = delid.substring(3);
	   	var path = "<%=path %>/api/v1/communities/"+<%=community_id%>+"/lifeCircle/delectFavorite?favorite_id="+favoriteId;
		  
		$.ajax({
			url : path,
			type : "GET",
			dataType : 'json',
			success : function(data) {
				alert("删除成功");
				var delNode = obj.parentElement.parentElement;
				delNode.parentElement.removeChild(delNode);
				
				document.getElementById("repairRecordSum").textContent-=1;
			},
			error : function(er) {
			}
		});
	
	}
	
	
	function getRepairRecordList2(num) {
		var page_num = 1;
		var repairRecordPageNum = document
				.getElementById("repairRecordPageNum_get").value;
		var repairRecordPageSize = document
				.getElementById("repairRecordPageSize_get").value;
		
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
	getUseAmountList(page_num);
	}
	
</script>
	</head>
	<body>
		<input type="hidden" id="emobId" value="${sessionScope.emobId}">
		<input type="hidden" id="community_id"
			value="${sessionScope.community_id}">
		<div class="loadingbox" id="add-price-box" style="display: none;">
			<img alt="" src="<%=basePath%>/images/chat/loading.gif">
		</div>
		<div class="upload-master-face-bg" id="upload-master-face-bg"
			style="display: none;">
			&nbsp;
		</div>
		<jsp:include flush="true" page="../public/navy-head.jsp" />
		<section>
			<div class="content clearfix">
				<jsp:include flush="true" page="../public/navy-left.jsp" >
				<jsp:param name="select" value="{parameterValue | 3}" />
													<jsp:param name="newUserName"
												value="{parameterValue | 5}" />
												</jsp:include>
				<div class="right-body" style="border: none;">
					<div class="navy-tab">
						<ul>
						<li><a href="${pageContext.request.contextPath }/jsp/navy/navy-alllife.jsp?community_id=<%=community_id%>&communityName=<%=str%>">全部话题</a></li>
						<li><a href="${pageContext.request.contextPath }/jsp/navy/navy-collect.jsp?community_id=<%=community_id%>&communityName=<%=str%>"  class="select">已收藏话题</a></li>
						
						
						
						</ul>
					</div>
					<div class="navy-all-topic">
						<table id="statistics_list_id">
							<tr>
								<th>话题内容</th>
								<th>所在小区</th>
								<th>发布人</th>
								<th>获赞人品</th>
								<th>获评论量</th>
								<th>发布时间</th>
								<th>操作</th>
							</tr>
							<tr>
								<td>
									<div class="navy-topic">
										<a href="javascript:;" title="果然爱菠萝沙拉原价25，现仅需要10元就可以获得！">果然爱菠萝沙拉原价25，现仅需要10元就可以获得！</a>
									</div>
								</td>
								<td>狮子城</td>
								<td>张小明</td>
								<td>21</td>
								<td>9</td>
								<td>8月23日  16:20</td>
								<td>
									<a href="javascript:;" class="enshrine">收藏</a>
									<a href="javascript:;" class="transmit">转发</a>
								</td>
							</tr>
							<tr>
								<td>
									<div class="navy-topic">
										<a href="javascript:;" title="果然爱菠萝沙拉原价25，现仅需要10元就可以获得！">果然爱菠萝沙拉原价25，现仅需要10元就可以获得！</a>
									</div>
								</td>
								<td>狮子城</td>
								<td>张小明</td>
								<td>21</td>
								<td>9</td>
								<td>8月23日  16:20</td>
								<td>
									<a href="javascript:;" class="enshrine">收藏</a>
									<a href="javascript:;" class="transmit">转发</a>
								</td>
							</tr>
							<tr>
								<td>
									<div class="navy-topic">
										<a href="javascript:;" title="果然爱菠萝沙拉原价25，现仅需要10元就可以获得！">果然爱菠萝沙拉原价25，现仅需要10元就可以获得！</a>
									</div>
								</td>
								<td>狮子城</td>
								<td>张小明</td>
								<td>21</td>
								<td>9</td>
								<td>8月23日  16:20</td>
								<td>
									<a href="javascript:;" class="enshrine">收藏</a>
									<a href="javascript:;" class="transmit">转发</a>
								</td>
							</tr>
							<tr>
								<td>
									<div class="navy-topic">
										<a href="javascript:;" title="果然爱菠萝沙拉原价25，现仅需要10元就可以获得！">果然爱菠萝沙拉原价25，现仅需要10元就可以获得！</a>
									</div>
								</td>
								<td>狮子城</td>
								<td>张小明</td>
								<td>21</td>
								<td>9</td>
								<td>8月23日  16:20</td>
								<td>
									<a href="javascript:;" class="enshrine">收藏</a>
									<a href="javascript:;" class="transmit">转发</a>
								</td>
							</tr>
						</table>
						
					<div class="divide-page" style="float: left; width: 100%; height: 50px; clear: both; text-align: center;">
						<input type="hidden" id="repairRecordPageNum_get" />
						<input type="hidden" id="repairRecordPageSize_get" />
	
						<a href="javascript:void(0);" onclick="getRepairRecordList2(-3);">首页</a>
						<a href="javascript:void(0);" onclick="getRepairRecordList2(-1);">上一页</a>
						当前第
						<font id="repairRecordPageNum"></font> 页 共
						<font id="repairRecordPageSize"></font> 页 共
						<font id="repairRecordSum"></font> 条
	
						<a href="javascript:void(0);" onclick="getRepairRecordList2(-2);">下一页</a>
						<a href="javascript:void(0);" onclick="getRepairRecordList2(-4);">尾页</a>
					</div>
						
						
					</div>
				</div>
			</div>
		</section>
	</body>
	<script type="text/javascript">
	getUseAmountList(1);
</script>
	<jsp:include flush="true" page="../public/footer.jsp"></jsp:include>

</html>

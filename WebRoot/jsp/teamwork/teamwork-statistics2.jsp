<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String webTitle = properties.getProperty("webTitle"); 
	
	String community_id=request.getParameter("community_id");
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
			href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<link
			href="${pageContext.request.contextPath }/css/operation.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<link
			href="${pageContext.request.contextPath }/css/calendar.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">

		<script src="${pageContext.request.contextPath }/js/jquery-1.6.js?version=<%=versionNum %>"></script>
		<script src="${pageContext.request.contextPath }/js/operation.js?version=<%=versionNum %>"></script>
		<script src="${pageContext.request.contextPath }/js/teamwork/teamwork-statistics.js?version=<%=versionNum %>"></script>
		<script type="text/javascript">
	
</script>
	</head>
	<body>
<input type="hidden" id="emobId" value="${sessionScope.emobId}">
	<input type="hidden" id="community_id" value="${communityId }">
	<div class="loadingbox" id="add-price-box" style="display: none;">
		<img alt="" src="<%= basePath %>/images/chat/loading.gif">
	</div>
	<div class="upload-master-face-bg" id="upload-master-face-bg" style="display: none;">&nbsp;</div>
	<jsp:include flush="true" page="../public/teamwork-head.jsp" />
		<section>
			<div class="content clearfix">
				<jsp:include flush="true" page="../public/teamwork-left.jsp" />
				<div class="right-body" style="border:none;">
					<div class="static-type">
						<ul>
							<li><a href="${pageContext.request.contextPath }/jsp/teamwork/teamwork-statistics.jsp" class="select" >快店</a></li>
							<li class="static-more"><a href="${pageContext.request.contextPath }/stat/teamwork/getMore.do?modules=3&shopType=2&community_id=${communityId }" >查看更多...</a></li>
						</ul>
					</div>
					<div class="static-abstract">
						<table>
							<tr>
								<td><span>本月点击量：</span><span id="this_month_click">${map.monthdata.clickNum }</span></td>
								<td><span>下单量：</span><span>${map.monthdata.orderQuantity}</span></td>
								<td><span>成单量：</span><span id="this_month_end">${map.monthdata.completeCount }</span></td>
								<td><span>交易额：</span><span id="this_month_par">${map.monthdata.totalPrice }</span></td>
								<td><span>分成比例：</span><span >${rate }%</span></td>
								<td><span>收益：</span><span id="this_month_earnings">
								<fmt:formatNumber type="number" value="${map.monthdata.totalPrice*rate*0.01 }" maxFractionDigits="2"/>
								</span></td>
								<td><span style="color: red;">已结：</span><span></span></td>
								<td><span style="color: red;">未结：</span><span></span></td>
							</tr>
						</table>
					</div>
					<div class="static-detail">
						<table>
							<tr>
								<th>交易时间</th>
								<th>点击量</th>
								<th>下单量</th>
								<th>下单人数</th>
								<th>下单占比</th>
								<th>成单量</th>
								<th>成单率</th>
								<th>交易金额</th>
								<th>收益</th>
								<th>详情</th>
							</tr>
							<tr class="odd">
								<td >昨日</td>
								<td id="to_day_click">${map.yesterdaydata.clickNum }</td>
								<td>${map.yesterdaydata.orderQuantity}</td>
								<td>${map.yesterdaydata.userNum}</td>
								<td>
								
								<c:if test="${map.yesterdaydata.clickNum==0 }">0%</c:if>
			                	<c:if test="${map.yesterdaydata.clickNum!=0 }">
				                 <fmt:formatNumber type="number" value="${100*map.yesterdaydata.orderQuantity/map.yesterdaydata.clickNum} " maxFractionDigits="2"/>%
				                </c:if>
				                
				                
								</td>
								<td id="to_day_end">${map.yesterdaydata.completeCount }</td>
								<td>
								
								<c:if test="${map.yesterdaydata.orderQuantity==0 }">0%</c:if>
			                	<c:if test="${map.yesterdaydata.orderQuantity!=0 }">
				                 <fmt:formatNumber type="number" value="${100*map.yesterdaydata.completeCount/map.yesterdaydata.orderQuantity} " maxFractionDigits="2"/>%
				                </c:if>
								
								
								</td>
								<td id="to_day_par"> ${map.yesterdaydata.totalPrice }</td>
								<td id="to_day_earnings">
								<fmt:formatNumber type="number" value="${map.yesterdaydata.totalPrice*rate*0.01 }" maxFractionDigits="2"/>
								</td>
								<td><a href="${pageContext.request.contextPath }/stat/communities/${communityId }/shop/2/getDetail.do?time=yesterday">详情</a></td>
							</tr>
							<tr class="enen">
								<td >本周</td>
								<td id="this_week_click" >${map.weekdata.clickNum }</td>
								<td>${map.weekdata.orderQuantity }</td>
								<td>${map.weekdata.userNum }</td>
								
								<td>
                               <c:if test="${map.weekdata.clickNum==0 }">0%</c:if>
			                	<c:if test="${map.weekdata.clickNum!=0 }">
				                 <fmt:formatNumber type="number" value="${100*map.weekdata.orderQuantity/map.weekdata.clickNum} " maxFractionDigits="2"/>%
				                </c:if>


                                 </td>
								<td id="this_week_end" >${map.weekdata.completeCount }</td>
								<td>
								
								  <c:if test="${map.weekdata.orderQuantity==0 }">0%</c:if>
			                	  <c:if test="${map.weekdata.orderQuantity!=0 }">
				                 <fmt:formatNumber type="number" value="${100*map.weekdata.completeCount/map.weekdata.orderQuantity} " maxFractionDigits="2"/>%
				                </c:if>
								
								</td>
								<td id="this_week_par" >${map.weekdata.totalPrice }</td>
								<td id="this_week_earnings">
								<fmt:formatNumber type="number" value="${map.weekdata.totalPrice*rate*0.01 } " maxFractionDigits="2"/>
								</td>
								<td><a href="${pageContext.request.contextPath }/stat/communities/${communityId }/shop/2/getDetail.do?time=week">详情</a></td>
							</tr>
							<tr class="odd">
								<td >上周</td>
								<td id="last_week_click">${map.lastweekdata.clickNum }</td>
								<td>${map.lastweekdata.orderQuantity }</td>
								<td>${map.lastweekdata.userNum  }</td>
								<td>
								  <c:if test="${map.lastweekdata.clickNum==0 }">0%</c:if>
			                	  <c:if test="${map.lastweekdata.clickNum!=0 }">
				                  <fmt:formatNumber type="number" value="${100*map.lastweekdata.orderQuantity/map.lastweekdata.clickNum} " maxFractionDigits="2"/>%
				                  </c:if>
								
								
								</td>
								<td id="last_week_end">${map.lastweekdata.completeCount }</td>
								<td>
								    <c:if test="${map.lastweekdata.orderQuantity==0 }">0%</c:if>
			                	  <c:if test="${map.lastweekdata.orderQuantity!=0 }">
				                 <fmt:formatNumber type="number" value="${100*map.lastweekdata.completeCount/map.lastweekdata.orderQuantity} " maxFractionDigits="2"/>%
				                </c:if>
								
								
								</td>
								<td id="last_week_par">${map.lastweekdata.totalPrice }</td>
								<td id="last_week_earnings">
								<fmt:formatNumber type="number" value="${map.lastweekdata.totalPrice*rate*0.01 } " maxFractionDigits="2"/>
								</td>
								<td><a href="${pageContext.request.contextPath }/stat/communities/${communityId }/shop/2/getDetail.do?time=lastweek">详情</a></td>
							</tr>
						</table>
					</div>
				</div>	
			</div>
		</section>
	</body>
	
	<script type="text/javascript">
	

	</script>
	<jsp:include flush="true" page="../public/footer.jsp"></jsp:include>
</html>

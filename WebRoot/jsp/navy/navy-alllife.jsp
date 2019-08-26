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

<%

Integer lastQuitTime = null;
if (session.isNew()){
	
	Cookie cookies[]=request.getCookies();
	for(int i=0;i<cookies.length;i++) {
		if("quitTime".equals(cookies[i].getName())) {
			lastQuitTime = Integer.parseInt(cookies[i].getValue());
			break;
		}
	}
	if(lastQuitTime==null){
		lastQuitTime = (int)(System.currentTimeMillis()/1000l);
	}
	//登录时记录访问时间
	Integer newTime = (int) (System.currentTimeMillis()/1000l);
	Cookie cookie = new Cookie("quitTime",newTime+""); 
  	cookie.setMaxAge(60*60*24*30); 
  	response.addCookie(cookie);
}else{
	Cookie cookies[]=request.getCookies();
	for(int i=0;i<cookies.length;i++) {
		if("quitTime".equals(cookies[i].getName())) {
			lastQuitTime = Integer.parseInt(cookies[i].getValue());
			break;
		}
	}
	if(lastQuitTime==null){
		lastQuitTime = (int)(System.currentTimeMillis()/1000l);
	}
}
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
			href="${pageContext.request.contextPath }/css/navy.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<link
			href="${pageContext.request.contextPath }/css/calendar.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">

		<script src="${pageContext.request.contextPath }/js/jquery-1.6.js?version=<%=versionNum %>"></script>
		
<script type="text/javascript">
	
	function getUseAmountList(pageNum) {

	var path = "<%=path %>/api/v1/communities/"+<%=community_id%>+"/lifeCircle/getLifeCircleListH?pageNum="+pageNum+"&pageSize=20";
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
							.append("<tr class=\"odd\">"+
								"<th>话题内容</th>"+
								"<th>所在小区</th>"+
								"<th>发布人</th>"+
								"<th>获赞人品</th>"+
								"<th>获评论量</th>"+
								"<th>发布时间</th>"+
								"<th>操作</th>"+
							"</tr>");
					for ( var i = 0; i < data.length; i++) {
					
						var strLifeContent = data[i].lifeContent;
						if(strLifeContent==""){
							strLifeContent = "<无文字主题>";
						}
						
						var liList="" ;
						liList+="<tr ><td><em name='lifeCircleUpdateCountName' style='color:red;' id='lifeCircleUpdateSum"+data[i].lifeCircleId+"'></em><div class=\"navy-topic\"><a href=\"<%=path %>/jsp/navy/navy-life-detail.jsp?activitiesId="+data[i].lifeCircleId+"&community_id=<%=community_id%>&communityName=<%=str%>\" title=\""+strLifeContent+"\">"+strLifeContent+"</a></div></td>";
						
						liList+="<td><%=str%></td>";
						liList+="<td>"+data[i].nickname+"</td>";
						
						liList+="<td>"+data[i].praiseSum+"</td>";
						liList+="<td>"+data[i].contentSum+"</td>";
						
					    var myDate=new Date(data[i].createTime*1000);
						liList+="<td>"+myDate.getFullYear()+"-"+(myDate.getMonth()+1)+"-"+myDate.getDate()+"</td>";
						liList+="<td>"+
									"<a href=\"javascript:;\" class=\"enshrine\" onclick=\"favorite('"+data[i].lifeCircleId+"');\">收藏</a>"+
									"<a href=\"<%=path %>/jsp/navy/navy-life-favorite.jsp?community_id=<%=community_id %>&communityName=<%=str %>&activitiesId="+data[i].lifeCircleId+"\" class=\"transmit\">转发</a>"+
									"<a href=\"javascript:;\" class=\"enshrine\" onclick=\"block('"+data[i].lifeCircleId+"');\">屏蔽</a>"+		
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
	checkUpdate();
	function checkUpdate(){
		var path = "<%=path %>/api/v1/communities/"+<%=community_id%>+"/lifeCircle/getLifeCircleUpdate?lastQuitTime="+<%=lastQuitTime %>;
		$.ajax({
			url : path,
			type : "GET",
			dataType : 'json',
			success : function(data) {
				data = data.info;
				var nodeList = document.getElementsByName("lifeCircleUpdateCountName");
				$.each(nodeList,function(i,a){
					$.each(data,function(j,b){
						var aid = a.id;
						var bid = "lifeCircleUpdateSum"+b.id;
						if(aid==bid){
							document.getElementById(aid).textContent=b.count;	
						}
					});
				});
			},
			error : function(er) {
			}
		});
	}
	
	
	//收藏
	function favorite(lifeCircleId){
	
	   var path = "<%=path %>/api/v1/communities/"+<%=community_id%>+"/lifeCircle/favorite";
	   
	    var myJson={
			  "lifeCircleId":lifeCircleId,
			 // "emobId":lifeContent,
			  "communityId":<%=community_id%>
		  };
		$.ajax({
			url : path,
			data : JSON.stringify(myJson),
			type : "POST",
			dataType : 'json',
			success : function(data) {
				alert("成功");
			},
			error : function(er) {
			}
		});
	
	}
	//屏蔽
	function block(lifeCircleId){
		var path = "<%=path %>/api/v1/communities/"+<%=community_id%>+"/lifeCircle/pingbi/"+lifeCircleId;
		if(confirm("确定要屏蔽此条生活圈吗？")){
			$.ajax({
				url : path,
				type : "GET",
				dataType : 'json',
				success : function(data) {
					alert("成功");
				},
				error : function(er) {
				}
			});
		}
	
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
		};

	} else if (num == -2) { // 下一页
		if (parseInt(repairRecordPageNum) < parseInt(repairRecordPageSize)) {
			page_num = parseInt(repairRecordPageNum) + parseInt(1);
		} else {
			alert("已经是最后一页了");
			return;
		};

	} else if (num == -3) { // 首页
		if (repairRecordPageNum != 1) {
			page_num = 1;
		} else {
			alert("已经是首页了");
			return;
		};
	} else if (num == -4) { // 尾页
		if (parseInt(repairRecordPageNum) < parseInt(repairRecordPageSize)) {
			page_num = repairRecordPageSize;
		} else {
			alert("已经是尾页了");
			return;
		};
	}
	getUseAmountList(page_num);
	checkUpdate();
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
							<li><a href="${pageContext.request.contextPath }/jsp/navy/navy-alllife.jsp?community_id=<%=community_id%>&communityName=<%=str%>"   class="select">全部话题</a></li>
							<li><a href="${pageContext.request.contextPath }/jsp/navy/navy-collect.jsp?community_id=<%=community_id%>&communityName=<%=str%>">已收藏话题</a></li>
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
    </script>
	<jsp:include flush="true" page="../public/footer.jsp"></jsp:include>

</html>

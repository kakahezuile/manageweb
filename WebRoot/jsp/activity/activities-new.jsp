<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	
	
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
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
<meta name="keywords" content="小间物业,小间物业管理系统" />
<meta name="Description" content="小间物业,小间物业管理系统" />
<title>发布活动话题_小间运营系统</title>
<link href="${pageContext.request.contextPath}/favicon.ico?version=<%=versionNum%>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath}/css/public.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/computer_device.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/navy.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/calendar.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/activities-date.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/js/jquery-1.6.js?version=<%=versionNum%>"></script>
<script src="${pageContext.request.contextPath}/js/operation.js?version=<%=versionNum%>"></script>
<script src="${pageContext.request.contextPath}/js/teamwork/teamwork-survey.js?version=<%=versionNum%>"></script>



<script type="text/javascript">
function getCommunities(){
		$.ajax({
			url: "<%=path %>/api/v1/communities/summary/getListCommunityQ",
			type: "GET",
			dataType: "json",
			success: function(data) {
				data=data.info;
				var liList="<option  selected='selected' disabled='disabled' >请选择小区</option>";
				
				for ( var i = 0; i < data.length; i++) {
					liList+="<option value='"+data[i].communityId+"'>"+data[i].communityName+"</option> ";
				}
			$("#communitiesTotal").html(liList);
					
				},
			error: function(e) {
				alert(e);
			}
		});
}

function getListTryOutUser(sel){
	var cid = $(sel).val();
	var url = "<%=path %>/api/v1/communities/summary/getListTryOutUser?communityId="+cid;
	$.ajax({
			url: url,
			type: "GET",
			dataType: "json",
			success: function(data) {
				data=data.info;
				var liList="<option  selected='selected' disabled='disabled' >请选择用户</option>";
				
				for ( var i = 0; i < data.length; i++) {
					liList+="<option value='"+data[i].emobId+"'>"+data[i].nickname+"</option> ";
				}
			$("#tryOutUser").html(liList);
					
				},
			error: function(e) {
				alert(e);
			}
		});
	
	
}

function upImg(el){
	var me = $(el),
		li = me.closest("li");
	
	li.before("<li><p><img></p><p><a href='javascript:void(0)' onclick='delImg(this)'>删除</a></p></li>");
	li.hide();
	preview(el, li.prev().find("img"));
}
function delImg(el) {
	var li = $(el).closest("li");
	li.next().remove();
	li.remove();
	$("#statistics_shi_id").append("<li class='navy-upload'><a><input name='" + el.name + "' type='file' onchange='upImg(this)'></a></li>");
}

function addNewActivity() {
	
	var activityTitle = $("#activityTitle");  
	if(!activityTitle.val()){
		alert("请输入活动内容!");
		activityTitle.focus();
		return;
	}
	
	var communityId = $("#communitiesTotal option:selected")[0].value;
	if(communityId=="请选择小区"){
		alert("请选择小区!");
		return;
	}
	var emobId = $("#tryOutUser option:selected")[0].value;
	if(emobId=="请选择用户"){
		alert("请选择用户!");
		return;
	}
	var path = "<%=basePath %>api/v1/communities/"+communityId+"/users/"+emobId+"/activities";
	
	$("#activityImg").attr("action",path);
	$("#activityImg").get(0).submit();
	
	alert("发布成功");
}

$(function() {
	getCommunities();
	dftDate();
});

function dftDate(){
	var date = new Date();
	var todayValue = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	document.getElementsByName("member.birth")[0].value = todayValue;
}


var ie =navigator.appName=="Microsoft Internet Explorer"?true:false;
</script>
</head>
<body>
<script type="text/javascript">
var controlid = null;
var currdate = null;
var startdate = null;
var enddate  = null;
var yy = null;
var mm = null;
var hh = null;
var ii = null;
var currday = null;
var addtime = false;
var today = new Date();
var lastcheckedyear = false;
var lastcheckedmonth = false;
function _cancelBubble(event) {
e = event ? event : window.event ;
if(ie) {
	e.cancelBubble = true;
} else {
	e.stopPropagation();
}
}
function getposition(obj) {
	var r = new Array();
	r['x'] = obj.offsetLeft;
	r['y'] = obj.offsetTop;
	while(obj = obj.offsetParent) {
	r['x'] += obj.offsetLeft;
	r['y'] += obj.offsetTop;
	}
	return r;
}
function loadcalendar() {
s = '';
s += '<div id="calendar" style="display:none; position:absolute; z-index:9;" onclick="_cancelBubble(event)">';
if (ie)
{
s += '<iframe width="200" height="160" src="about:blank" style="position: absolute;z-index:-1;"></iframe>';
}
s += '<div style="width: 200px;"><table class="tableborder" cellspacing="0" cellpadding="0" width="100%" style="text-align: center">';
///
s += '<tr align="center" class="date_header"><td class="date_header"><a href="#" onclick="refreshcalendar(yy, mm-1);return false" title="上一月"><<</a></td><td colspan="5" style="text-align: center" class="date_header"><a href="#" onclick="showdiv(\'year\');_cancelBubble(event);return false" title="点击选择年份" id="year"></a>  -  <a id="month" title="点击选择月份" href="#" onclick="showdiv(\'month\');_cancelBubble(event);return false"></a></td><td class="date_header"><A href="#" onclick="refreshcalendar(yy, mm+1);return false" title="下一月">>></A></td></tr>';
s += '<tr class="category"><td>日</td><td>一</td><td>二</td><td>三</td><td>四</td><td>五</td><td>六</td></tr>';
for(var i = 0; i < 6; i++) {
s += '<tr class="altbg2">';
for(var j = 1; j <= 7; j++)
s += "<td id=d" + (i * 7 + j) + " height=\"19\">0</td>";
s += "</tr>";
}
s += '<tr id="hourminute"><td colspan="7" align="center"><input type="text" size="1" value="" id="hour" onKeyUp=\'this.value=this.value > 23 ? 23 : zerofill(this.value);controlid.value=controlid.value.replace(/\\d+(\:\\d+)/ig, this.value+"$1")\'> 点 <input type="text" size="1" value="" id="minute" onKeyUp=\'this.value=this.value > 59 ? 59 : zerofill(this.value);controlid.value=controlid.value.replace(/(\\d+\:)\\d+/ig, "$1"+this.value)\'> 分</td></tr>';
s += '</table></div></div>';
s += '<div id="calendar_year" onclick="_cancelBubble(event)"><div class="col">';
for(var k = 1930; k <= 2019; k++) {
s += k != 1930 && k % 10 == 0 ? '</div><div class="col">' : '';
s += '<a href="#" onclick="refreshcalendar(' + k + ', mm);document.getElementById(\'calendar_year\').style.display=\'none\';return false"><span' + (today.getFullYear() == k ? ' class="today"' : '') + ' id="calendar_year_' + k + '">' + k + '</span></a><br />';
}
s += '</div></div>';
s += '<div id="calendar_month" onclick="_cancelBubble(event)">';
for(var k = 1; k <= 12; k++) {
s += '<a href="#" onclick="refreshcalendar(yy, ' + (k - 1) + ');document.getElementById(\'calendar_month\').style.display=\'none\';return false"><span' + (today.getMonth()+1 == k ? ' class="today"' : '') + ' id="calendar_month_' + k + '">' + k + ( k < 10 ? ' ' : '') + ' 月</span></a><br />';
}
s += '</div>';
var nElement = document.createElement("div");
nElement.innerHTML=s;
document.getElementsByTagName("body")[0].appendChild(nElement);

document.onclick = function(event) {
document.getElementById('calendar').style.display = 'none';
document.getElementById('calendar_year').style.display = 'none';
document.getElementById('calendar_month').style.display = 'none';

};
document.getElementById('calendar').onclick = function(event) {
_cancelBubble(event);
document.getElementById('calendar_year').style.display = 'none';
document.getElementById('calendar_month').style.display = 'none';
};
}
function parsedate(s) {
/(\d+)\-(\d+)\-(\d+)\s*(\d*):?(\d*)/.exec(s);
var m1 = (RegExp.$1 && RegExp.$1 > 1899 && RegExp.$1 < 2101) ? parseFloat(RegExp.$1) : today.getFullYear();
var m2 = (RegExp.$2 && (RegExp.$2 > 0 && RegExp.$2 < 13)) ? parseFloat(RegExp.$2) : today.getMonth() + 1;
var m3 = (RegExp.$3 && (RegExp.$3 > 0 && RegExp.$3 < 32)) ? parseFloat(RegExp.$3) : today.getDate();
var m4 = (RegExp.$4 && (RegExp.$4 > -1 && RegExp.$4 < 24)) ? parseFloat(RegExp.$4) : 0;
var m5 = (RegExp.$5 && (RegExp.$5 > -1 && RegExp.$5 < 60)) ? parseFloat(RegExp.$5) : 0;
/(\d+)\-(\d+)\-(\d+)\s*(\d*):?(\d*)/.exec("0000-00-00 00\:00");
return new Date(m1, m2 - 1, m3, m4, m5);
}
function settime(d) {
document.getElementById('calendar').style.display = 'none';
controlid.value = yy + "-" + zerofill(mm + 1) + "-" + zerofill(d) + (addtime ? ' ' + zerofill(document.getElementById('hour').value) + ':' + zerofill(document.getElementById('minute').value) : '');
}
function showcalendar(event, controlid1, addtime1, startdate1, enddate1) {
controlid = controlid1;
addtime = addtime1;
startdate = startdate1 ? parsedate(startdate1) : false;
enddate = enddate1 ? parsedate(enddate1) : false;
currday = controlid.value ? parsedate(controlid.value) : today;
hh = currday.getHours();
ii = currday.getMinutes();
var p = getposition(controlid);
document.getElementById('calendar').style.display = 'block';
document.getElementById('calendar').style.left = p['x']+'px';
document.getElementById('calendar').style.top	= (p['y'] + 20)+'px';
_cancelBubble(event);
refreshcalendar(currday.getFullYear(), currday.getMonth());
if(lastcheckedyear != false) {
document.getElementById('calendar_year_' + lastcheckedyear).className = 'default';
document.getElementById('calendar_year_' + today.getFullYear()).className = 'today';
}
if(lastcheckedmonth != false) {
document.getElementById('calendar_month_' + lastcheckedmonth).className = 'default';
document.getElementById('calendar_month_' + (today.getMonth() + 1)).className = 'today';
}
document.getElementById('calendar_year_' + currday.getFullYear()).className = 'checked';
document.getElementById('calendar_month_' + (currday.getMonth() + 1)).className = 'checked';
document.getElementById('hourminute').style.display = addtime ? '' : 'none';
lastcheckedyear = currday.getFullYear();
lastcheckedmonth = currday.getMonth() + 1;
}
function refreshcalendar(y, m) {
var x = new Date(y, m, 1);
var mv = x.getDay();
var d = x.getDate();
var dd = null;
yy = x.getFullYear();
mm = x.getMonth();
document.getElementById("year").innerHTML = yy;
document.getElementById("month").innerHTML = mm + 1 > 9  ? (mm + 1) : '0' + (mm + 1);
for(var i = 1; i <= mv; i++) {
	dd = document.getElementById("d" + i);
	dd.innerHTML = " ";
	dd.className = "";
}
while(x.getMonth() == mm) {
	dd = document.getElementById("d" + (d + mv));
	dd.innerHTML = '<a href="###" onclick="settime(' + d + ');return false">' + d + '</a>';
	if(x.getTime() < today.getTime() || (enddate && x.getTime() > enddate.getTime()) || (startdate && x.getTime() < startdate.getTime())) {
	dd.className = 'expire';
} else {
	dd.className = 'default';
}
if(x.getFullYear() == today.getFullYear() && x.getMonth() == today.getMonth() && x.getDate() == today.getDate()) {
	dd.className = 'today';
	//dd.firstChild.title = '今天';
}
if(x.getFullYear() == currday.getFullYear() && x.getMonth() == currday.getMonth() && x.getDate() == currday.getDate()) {
dd.className = 'checked';
}
x.setDate(++d);
}
while(d + mv <= 42) {
	dd = document.getElementById("d" + (d + mv));
	dd.innerHTML = " ";
	d++;
}
if(addtime) {
document.getElementById('hour').value = zerofill(hh);
document.getElementById('minute').value = zerofill(ii);
}
}
function showdiv(id) {
var p = getposition(document.getElementById(id));
document.getElementById('calendar_' + id).style.left = p['x']+'px';
document.getElementById('calendar_' + id).style.top = (p['y'] + 16)+'px';
document.getElementById('calendar_' + id).style.display = 'block';
}
function zerofill(s) {
var s = parseFloat(s.toString().replace(/(^[\s0]+)|(\s+$)/g, ''));
s = isNaN(s) ? 0 : s;
return (s < 10 ? '0' : '') + s.toString();
}
loadcalendar();
</script>

	<input type="hidden" id="emobId" value="${sessionScope.emobId}">
	<input type="hidden" id="community_id" value="${sessionScope.community_id}">
	<div class="loadingbox" id="add-price-box" style="display: none;">
		<img alt="" src="<%=basePath%>/images/chat/loading.gif">
	</div>
	<div class="upload-master-face-bg" id="upload-master-face-bg" style="display:none;">&nbsp;</div>
	<jsp:include flush="true" page="../public/navy-head.jsp" />
	<section>
		<div class="content clearfix">
			<jsp:include flush="true" page="activities-left.jsp" >
				<jsp:param name="select" value="{parameterValue | 3}" />
				<jsp:param name="communityId" value="{parameterValue | 5}" />
			</jsp:include>
			<div class="right-body" style="border: none;">
				<div class="teamwork-title">创建活动话题</div>
			<form id="activityImg" method="post" enctype="multipart/form-data" action="">
				<input type="hidden" name="community_id" value=<%=community_id %>>
				<input type="hidden" name="communityName" value=<%=str %>>
					<div class="clearfix">
						<div class="navy-life-tag">活动内容</div>
						<div class="navy-life-content">
							<input type="text" id="activityTitle" name ="activityTitle" />
						</div>
					</div>
					<div class="clearfix">
						<div class="navy-life-tag" >活动时间</div>
						<div class="navy-life-content" >
							<input name="member.birth" id="activityTime" type="text" value="2015-11-01" size="14" readonly onclick="showcalendar(event,this);" onfocus="showcalendar(event, this);if(this.value=='0000-00-00')this.value=''">
						</div>
					</div>
					<div class="clearfix">
						<div class="navy-life-tag">活动地点</div>
						<div class="navy-life-content"  id="place">
							<input type="text" name="place"/>
						</div>
					</div>
					<div class="clearfix">
						<div class="navy-life-tag">所在小区</div>
						<div class="navy-life-content">
							<select id="communitiesTotal" onchange="getListTryOutUser(this)" name ="communitiesTotal">
								<option  selected="selected" disabled="disabled" >请选择小区</option>
							</select>
						</div>
					</div>
					<div class="clearfix">
						<div class="navy-life-tag">活动创建者</div>
						<div class="navy-life-content">
							<select id="tryOutUser" name="tryOutUser">
								<option  selected="selected"  >请选择用户</option>
							</select>
						</div>
					</div>
					
						<div class="clearfix">
							<div class="navy-life-tag">活动图片</div>
							<div class="navy-life-content">
								<div class="navy-upload-img">
									<ul id="statistics_shi_id">
										<li class="navy-upload">
											<a><input type="file" onchange="upImg(this);" name="activity_Img"/></a>
										</li>
									</ul>
								</div>
							</div>
						</div>
				
					<div class="clearfix">
						<div class="navy-life-tag">活动内容</div>
						<div class="navy-life-content">
							<textarea class="navy-life-word" name="activityDetail" id="activityDetail"></textarea>
						</div>
					</div>
					<div class="navy-life-send">
						<a href="javascript:;" onclick="addNewActivity();">发布</a>
					</div>
				</form>
			</div>
		</div>
	</section>
</body>
<jsp:include flush="true" page="../public/footer.jsp"></jsp:include>
</html>
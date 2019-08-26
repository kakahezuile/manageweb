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
<title>保洁_小间物业管理系统</title>

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
			href="${pageContext.request.contextPath }/css/calendar.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>
		<script src="${pageContext.request.contextPath }/js/jquery-2.1.1.js"></script>
		<script src="${pageContext.request.contextPath }/js/calendar.js"></script>


<script type="text/javascript">

	var communityId = window.parent.document
			.getElementById("community_id_index").value;
function overmanClean(){
	var path = "<%= path%>/api/v1/communities/"+communityId+"/repairStatistics/center?sort=6";

		$.ajax({
					url : path,
					type : "GET",
					dataType : 'json',
					success : function(data) {
					
	                    data = data.info;
						var repair_overman = $("#clean_overman");
						repair_overman.empty();
						//var liList='';

						for ( var i = 0; i < data.length; i++) {

							var liList = "";


							liList += "<li>";

							liList += "	<div class='repair-master-info'>";
							liList += "<a title=\"修改资料\" href=\"<%=path %>/jsp/estate/property/add_master.jsp?sort=6&shopId="+data[i].shopId+"&master=修改保洁&isUpAndAdd=up\" class=\"repair-setting\" onclick=\"upClean('"+data[i].shopId+"');\" >&nbsp;</a>";
							liList += "<p class='repair-master-face'>";
							liList += "<img src='"+data[i].logo+"' /></p>";
							liList += "<p class='repair-master-name'>"
									+ data[i].shopName + "	</p>";
							liList += "<p class='repair-master-phone'>"
									+ data[i].phone + "</p> </div>";

						
							
	                        if (data[i].status == "normal") {
								liList += "<div class='repair-master-staus'>	<span class='servicing'>"
										+ "空闲中" + "</span></div>";
							} else {
								liList += "<div class='repair-master-staus'>	<span class='servicing'>"
										+ "服务中" + "</span></div>";
							}

							liList += "<div class='repair-master-opt'><a href=\"<%=path %>/jsp/estate/property/master_repair_detail.jsp?shopId="+data[i].shopId+"&sort=6\">查看保洁记录</a></div></li>";

							repair_overman.append(liList);

						}

						repair_overman
								.append("<li><div class='repair-master-add-box'><a  href=\"<%=path %>/jsp/estate/property/add_master.jsp?sort=6&master=添加保洁&isUpAndAdd=add\">"
										+ "<p><img src='${pageContext.request.contextPath }/images/repair/repair-master-add.png' /></p><p>添加员工</p> </a></div>	</li>");

					},
					error : function(er) {
					}
				});

	}

function maintainCleanData() {
		conversionClean("clean_top_data");
		var myDate = new Date();
		myDate.getFullYear();
		myDate.getMonth();
		myDate.getDate();
		var month = myDate.getMonth() + 1;
		var da = myDate.getDate() + 1;
		var startTime = myDate.getFullYear() + "-" + month + "-"
				+ myDate.getDate()+" 00:00:00";
		var endTime = myDate.getFullYear() + "-" + month + "-" + da+" 00:00:00";
		trade_clean_d_m("日");
		maintainClean(startTime, endTime);
	}

	function conversionClean(id) {
		var top_data = document.getElementById("clean_top_data");
		var top_month = document.getElementById("clean_top_month");
		var top_section = document.getElementById("clean_top_section");
		if (id == "clean_top_data") {
			top_data.className = "select";
		} else {
			top_data.className = "";
		}
		if (id == "clean_top_month") {
			top_month.className = "select";
		} else {
			top_month.className = "";
		}
		if (id == "clean_top_section") {
			top_section.className = "select";
			document.getElementById("clean_top_section_time").style.display="";
		} else {
			top_section.className = "";
				document.getElementById("clean_top_section_time").style.display="none";
		}

	}
function maintainCleanMonth() {
		conversionClean("clean_top_month");

		var myDate = new Date();
		myDate.getFullYear();
		myDate.getMonth();
		var month = myDate.getMonth() + 1;

		var firstDate = new Date();

		firstDate.setDate(1); //第一天

		var endDate = new Date(firstDate);

		endDate.setMonth(firstDate.getMonth() + 1);

		endDate.setDate(0);

		var startTime = myDate.getFullYear() + "-" + month + "-"
				+ firstDate.getDate()+" 00:00:00";
		var endTime = myDate.getFullYear() + "-" + month + "-"
				+ endDate.getDate()+" 00:00:00";
        trade_clean_d_m("月");
		maintainClean(startTime, endTime);

	}

	function trade_clean_d_m(M){
		var repairCount_d_m=document.getElementById("clean_repairCount_d_m");
		var todayComplaintsCount_d_m=document.getElementById("clean_todayComplaintsCount_d_m");
		var todayUnComplainsCount_d_m=document.getElementById("clean_todayUnComplainsCount_d_m");
		var repairMoney_d_m=document.getElementById("clean_repairMoney_d_m");
		
		repairCount_d_m.innerHTML="本"+M+"保洁次数";
		todayComplaintsCount_d_m.innerHTML="本"+M+"投诉次数";
		todayUnComplainsCount_d_m.innerHTML="本"+M+"未解决次数";
		repairMoney_d_m.innerHTML="本"+M+"保洁金额";
	
	
	}
	function maintainClean(startTime, endTime) {
		var path = "<%= path%>/api/v1/communities/"+communityId+"/repairStatistics/top?sort=6&startTime="
				+ startTime + "&endTime=" + endTime;

		$.ajax({
					url : path,
					type : "GET",
					dataType : 'json',
					success : function(data) {
						data = data.info;
						document.getElementById("clean_repairCount").innerHTML = data.repairCount;
						document.getElementById("clean_todayComplaintsCount").innerHTML = data.todayComplaintsCount;
						document.getElementById("clean_todayUnComplainsCount").innerHTML = data.todayUnComplaintsCount;
						if (data.repairMoney != 'null') {
							document.getElementById("clean_repairMoney").innerHTML = data.repairMoney;

						} else {
							document.getElementById("clean_repairMoney").innerHTML = "0";
						}

					},
					error : function(er) {
					}
				});
	}



//日历控件
    $(function () {
        $("#clean_txtBeginDate").calendar({
            controlId: "divDate",                                 // 弹出的日期控件ID，默认: $(this).attr("id") + "Calendar"
            speed: 200,                                           // 三种预定速度之一的字符串("slow", "normal", or "fast")或表示动画时长的毫秒数值(如：1000),默认：200
            complement: true,                                     // 是否显示日期或年空白处的前后月的补充,默认：true
            readonly: true,                                       // 目标对象是否设为只读，默认：true
            upperLimit: new Date(),                               // 日期上限，默认：NaN(不限制)
            lowerLimit: new Date("2011/01/01"),                   // 日期下限，默认：NaN(不限制)
            callback: function () {                               // 点击选择日期后的回调函数
             //   alert("您选择的日期是：" + $("#txtBeginDate").val());
            }
        });
        $("#clean_txtEndDate").calendar();
    });
	


  function selectMainainCleanDuan(){
      var startTime=document.getElementById("clean_txtBeginDate").value+" 00:00:00";
      var endTime=document.getElementById("clean_txtEndDate").value+" 00:00:00";
      maintainClean(startTime, endTime);
  }
  
  
  
  function addClean(){
        setNone();
		$("#add_master_div_id").attr("style", "display:block");
		 document.getElementById("clean_add_fei").reset();
		 document.getElementById("clean_none").style.display="none";
		 document.getElementById("add_master_bao_fei").innerHTML="添加保洁";
		 add_none();
       
  }
  function upClean(shopsId){
        setNone();
		$("#add_master_div_id").attr("style", "display:block");
		 document.getElementById("clean_add_fei").reset();
		 document.getElementById("clean_none").style.display="none";
		  document.getElementById("add_master_bao_fei").innerHTML=" 修改保洁";
		 upShopsUser(shopsId);
       
  }
  
  
  
</script>
</head>
<body>

	<section>
		<div class="content clearfix center-personal-info-content">
			<jsp:include flush="true" page="../common/service-left.jsp"></jsp:include>
			<div class="service-right-body">
				<div class="repair-head">
				   <div class="time-chose" id="clean_top_section_time">   
							<input readonly="readonly" id="clean_txtBeginDate" value="请选择开始日期">
							<input readonly="readonly" id="clean_txtEndDate" value="请选择结束日期">
							<a href="javascript:void(0);" onclick="selectMainainCleanDuan();">查询</a>
					</div>
					<div class="repair-title">
						<a href="javascript:;" class="select" id="clean_top_data" onclick="maintainCleanData();">日</a>
						<a href="javascript:;" id="clean_top_month" onclick="maintainCleanMonth();">月</a>
						<a href="javascript:;" id="clean_top_section" onclick="conversionClean('clean_top_section')">时间段</a>
					</div>
					<div class="repair-content">
						<div class="repair-date"><span name="to_day_fei">2015-12-26</span></div>
						<div class="repair-stat clearfix">
							<div>
								<p><a href="javascript:;" class="repair-count" id="clean_repairCount">0</a></p>
								<p><span id="clean_repairCount_d_m">今日维修次数</span></p>
							</div>
							<div>
								<p><a href="javascript:;" class="complain-count" id="clean_todayComplaintsCount">0</a></p>
								<p><span id="clean_todayComplaintsCount_d_m">今日投诉次数</span></p>
							</div>
							<div>
								<p><a href="javascript:;" class="nosolve-count" id="clean_todayUnComplainsCount">0</a></p>
								<p><span id="clean_todayUnComplainsCount_d_m">未解决次数</span></p>
							</div>
							<div>
								<p><a href="javascript:;" class="repair-fee" id="clean_repairMoney">0</a></p>
								<p><span id="clean_repairMoney_d_m">当日维修金额</span></p>
							</div>
						</div>
						<div class="clearfix repair-detail"><a  href="<%=path %>/jsp/estate/property/repair_record.jsp?sort=6" >查看详情</a></div>
					</div>
				</div>
				<div class="repair-body clearfix">
					<div class="master-list">
						<ul id="clean_overman">
							<!--<li>
								<div class="repair-master-info">
									<a href="${pageContext.request.contextPath }/jsp/service/master_setting.jsp" class="repair-setting">&nbsp;</a>
									<p class="repair-master-face"><img src="${pageContext.request.contextPath }/images/repair/b4.jpg"/></p>
									<p class="repair-master-name">王阿姨</p>
									<p class="repair-master-phone">18570781607</p>
								</div>
								<div class="repair-master-staus">
									<span class="servicing">服务中</span>
								</div>
								<div class="repair-master-opt">
									<a href="${pageContext.request.contextPath }/jsp/service/master_repair_detail.jsp">查看维修记录</a>
								</div>
							</li>
							<li>
								<div class="repair-master-info">
									<a href="${pageContext.request.contextPath }/jsp/service/master_setting.jsp" class="repair-setting">&nbsp;</a>
									<p class="repair-master-face"><img src="${pageContext.request.contextPath }/images/repair/b1.jpg"/></p>
									<p class="repair-master-name">李阿姨</p>
									<p class="repair-master-phone">18570781607</p>
								</div>
								<div class="repair-master-staus">
									<span>空闲中</span>
								</div>
								<div class="repair-master-opt">
									<a href="${pageContext.request.contextPath }/jsp/service/master_repair_detail.jsp">查看维修记录</a>
								</div>
							</li>
							<li>
								<div class="repair-master-info">
									<a href="${pageContext.request.contextPath }/jsp/service/master_setting.jsp" class="repair-setting">&nbsp;</a>
									<p class="repair-master-face"><img src="${pageContext.request.contextPath }/images/repair/b2.jpg"/></p>
									<p class="repair-master-name">徐阿姨</p>
									<p class="repair-master-phone">18570781607</p>
								</div>
								<div class="repair-master-staus">
									<span>空闲中</span>
								</div>
								<div class="repair-master-opt">
									<a href="${pageContext.request.contextPath }/jsp/service/master_repair_detail.jsp">查看维修记录</a>
								</div>
							</li>
							<li>
								<div class="repair-master-info">
									<a href="${pageContext.request.contextPath }/jsp/service/master_setting.jsp" class="repair-setting">&nbsp;</a>
									<p class="repair-master-face"><img src="${pageContext.request.contextPath }/images/repair/b3.jpg"/></p>
									<p class="repair-master-name">孙阿姨</p>
									<p class="repair-master-phone">18570781607</p>
								</div>
								<div class="repair-master-staus">
									<span>空闲中</span>
								</div>
								<div class="repair-master-opt">
									<a href="${pageContext.request.contextPath }/jsp/service/master_repair_detail.jsp">查看维修记录</a>
								</div>
							</li>
							<li>
								<div class="repair-master-add-box">
									<a href="${pageContext.request.contextPath }/jsp/service/add_clean.jsp">
										<p><img src="${pageContext.request.contextPath }/images/repair/repair-master-add.png"/></p>
										<p>添加员工</p>
									</a>
								</div>
								
							</li>
						--></ul>
					</div>
				</div>
				<div class="price-record" style="display: none;">
					<div class="price-record-title">服务需知<a href="${pageContext.request.contextPath }/jsp/service/clean_rule.jsp">修改</a></div>
					<div class="clean-rule-box">
						<div class="clean-price-title">收费标准</div>
						<div class="clean-price-item">
							<div><span>费用：</span><span>20元/小时</span></div>
							<div class="clearfix">
								<p class="clean-price-info-title">内容：</p>
								<p class="clean-price-info">日常保洁（玻璃擦洗、家具、地面清洁）</p>
							</div>
						</div>
						<div class="clean-price-item">
							<div><span>费用：</span><span>40元/小时</span></div>
							<div class="clearfix">
								<p class="clean-price-info-title">内容：</p>
								<p class="clean-price-info">厨房保洁（墙 砖、PVC顶、灶具、灶台、地面）</p>
							</div>
						</div>

						<div class="clean-price-title">服务标准</div>
						<div class="clean-service-rule">
							<ul>
								<li>1、20分钟后到达</li>
								<li>2、自带清扫工具，清洁用品</li>
								<li>3、服务热情，积极主动</li>
								<li>*      有未能达到以上服务标准的，请及时投诉或与客服联系。</li>
							</ul>
						</div>
						<div class="clean-price-title">免则声明</div>
						<div class="clean-price-declare">任何人因使用本网站而可能遭致的意外及其造成的损失（包括因下载本网站可能链接的第         三方网站内容而感染电脑病毒），我们对此概不负责，亦不承担任何法律责任</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	
	<script type="text/javascript">
	
		overmanClean();
		maintainCleanData();
	leftClikCss("left_clean_fei");
	
	</script>
</body>
</html>
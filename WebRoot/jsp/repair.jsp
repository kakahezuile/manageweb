<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
		<title>维修_小间物业管理系统</title>
		<link href="${pageContext.request.contextPath }/css/calendar.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
		<script src="${pageContext.request.contextPath }/js/calendar.js"></script>
		<link
			href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>"
			type="image/x-icon" rel="shortcut icon">
		<link
			href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<link
			href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">

		<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->


		<script type="text/javascript">
	
	function overman(){
	var path = "<%= path%>/api/v1/communities/${communityId}/repairStatistics/center?sort=5";

		$.ajax({
					url : path,
					type : "GET",
					dataType : 'json',
					success : function(data) {
					
	                    data = data.info;
						var repair_overman = $("#repair_overman");
						repair_overman.empty();
						//var liList='';

						for ( var i = 0; i < data.length; i++) {

							var liList = "";

							liList += "<li>";

							liList += "	<div class='repair-master-info'>";
							liList += "<a href=\"javascript:;\" class=\"repair-setting\" onclick=\"upMasterJsp('"+data[i].shopId+"');\" >&nbsp;</a>";
							liList += "<p class='repair-master-face'>";
							liList += "<img src='"+data[i].logo+"' /></p>";
							liList += "<p class='repair-master-name'>"
									+ data[i].shopName + "	</p>";
									liList += "<p class='repair-master-type'>";
									var itList=data[i].list;
									if(itList.length>0){
									for(var j=0;j<itList.length;j++){
									
										liList+=itList[j].catName+"  ";
									}
									}else{
									liList+="通用";
									}
									
									
						
							liList += "</p><p class='repair-master-phone'>"
									+ data[i].phone + "</p> </div>";

						
							
	                        if (data[i].status == "normal") {
								liList += "<div class='repair-master-staus'>	<span class='servicing'>"
										+ "空闲中" + "</span></div>";
							} else {
								liList += "<div class='repair-master-staus'>	<span class='servicing'>"
										+ "服务中" + "</span></div>";
							}

							liList += "<div class='repair-master-opt'><a href='javascript:void(0);' onclick=masterRepairDatailIdFei('"+data[i].shopId+"','5');>查看维修记录</a></div></li>";

							repair_overman.append(liList);

						}

						repair_overman
								.append("<li><div class='repair-master-add-box'><a href='javascript:void(0);' onclick='addMasterJsp();'>"
										+ "<p><img src='${pageContext.request.contextPath }/images/repair/repair-master-add.png' /></p><p>添加员工</p> </a></div>	</li>");

					},
					error : function(er) {
						alert(er);
					}
				});

	}

	function maintainData() {
		conversion("top_data");
		var myDate = new Date();
		myDate.getFullYear();
		myDate.getMonth();
		myDate.getDate();
		var month = myDate.getMonth() + 1;
		var da = myDate.getDate() + 1;
		var startTime = myDate.getFullYear() + "-" + month + "-"
				+ myDate.getDate()+" 00:00:00";
		var endTime = myDate.getFullYear() + "-" + month + "-" + da+" 00:00:00";
		trade_d_m("日");
		maintain(startTime, endTime);
	}

	function conversion(id) {
		var top_data = document.getElementById("top_data");
		var top_month = document.getElementById("top_month");
		var top_section = document.getElementById("top_section");
		if (id == "top_data") {
			top_data.className = "select";
		} else {
			top_data.className = "";
		}
		if (id == "top_month") {
			top_month.className = "select";
		} else {
			top_month.className = "";
		}
		if (id == "top_section") {
			top_section.className = "select";
			document.getElementById("top_section_time").style.display="";
		} else {
			top_section.className = "";
			document.getElementById("top_section_time").style.display="none";
		}

	}

	function maintainMonth() {
		conversion("top_month");

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
        trade_d_m("月");
		maintain(startTime, endTime);

	}


	function trade_d_m(M){
		var repairCount_d_m=document.getElementById("repairCount_d_m");
		var todayComplaintsCount_d_m=document.getElementById("todayComplaintsCount_d_m");
		var todayUnComplainsCount_d_m=document.getElementById("todayUnComplainsCount_d_m");
		var repairMoney_d_m=document.getElementById("repairMoney_d_m");
		
		repairCount_d_m.innerHTML="本"+M+"维修次数";
		todayComplaintsCount_d_m.innerHTML="本"+M+"投诉次数";
		todayUnComplainsCount_d_m.innerHTML="本"+M+"未解决次数";
		repairMoney_d_m.innerHTML="本"+M+"维修金额";
	
	
	}
	function maintain(startTime, endTime) {
		var path = "<%= path%>/api/v1/communities/${communityId}/repairStatistics/top?sort=6&startTime="
				+ startTime + "&endTime=" + endTime;

		$.ajax({
					url : path,
					type : "GET",
					dataType : 'json',
					success : function(data) {
						data = data.info;
						document.getElementById("repairCount").innerHTML = data.repairCount;
						document.getElementById("todayComplaintsCount").innerHTML = data.todayComplaintsCount;
						document.getElementById("todayUnComplainsCount").innerHTML = data.todayUnComplaintsCount;
						if (data.repairMoney != 'null') {
							document.getElementById("repairMoney").innerHTML = data.repairMoney;

						} else {
							document.getElementById("repairMoney").innerHTML = "0";
						}

					},
					error : function(er) {
						alert(er);
					}
				});
	}

	
//日历控件
    $(function () {
        $("#txtBeginDate").calendar({
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
        $("#txtEndDate").calendar();
    });
	

  function mainainDuan(){
       conversion("top_section");
      
  }
  function selectMainainDuan(){
  
   var startTime=document.getElementById("txtBeginDate").value+" 00:00:00";
       var endTime=document.getElementById("txtEndDate").value+" 00:00:00";
       maintain(startTime, endTime);
  }
  
  function upMasterJsp(shopId){
     setNone();
		addMasterGetItemCategory(5);
		$("#add_master_div_id").attr("style", "display:block");
		 document.getElementById("add_master_bao_fei").innerHTML="修改维修师傅";
       upShopsUser(shopId);
  }
</script>

	</head>
	<body>

		<section>
			<div class="content clearfix center-personal-info-content">
				<jsp:include flush="true" page="../jsp/public/service-left.jsp"></jsp:include>
				<div class="service-right-body" >
					<div class="repair-head">
						<div class="time-chose" id="top_section_time">   
							<input readonly="readonly" id="txtBeginDate" value="请选择开始日期">
							<input readonly="readonly" id="txtEndDate" value="请选择结束日期">
							<a href="javascript:void(0);" onclick="selectMainainDuan();">查询</a>
						</div>
						<div class="repair-title">
							<a href="javascript:;" class="select" id="top_data"
								onclick="maintainData();">日</a>
							<a href="javascript:;" onclick="maintainMonth();" id="top_month">月</a>
							<a href="javascript:;" id="top_section" onclick="mainainDuan();" >时间段</a>
						</div>
						<div class="repair-content">
							<div class="repair-date">
								<span name="to_day_fei">2015-12-26</span>
							</div>
							<div class="repair-stat clearfix">
								<div>
									<span id="repairCount_d_m">本日维修次数</span>
									<a href="javascript:;" class="repair-count" id="repairCount">0</a>
								</div>
								<div>
									<span id="todayComplaintsCount_d_m">本日投诉次数</span><a
										href="javascript:;" class="complain-count"
										id="todayComplaintsCount">0</a>

							
									<span id="todayUnComplainsCount_d_m">本日未解决次数</span><a
										href="javascript:;" class="nosolve-count"
										id="todayUnComplainsCount">0</a>
								</div>
								<div>
									<span id="repairMoney_d_m">本日维修金额</span><a href="javascript:;"
										class="repair-fee" id="repairMoney">0</a>
								</div>
							</div>
							<!--<div class="clearfix repair-detail">
								<a
									href="${pageContext.request.contextPath }/jsp/service/repair_record.jsp">查看详情</a>
							</div>
						
							-->


							<div class="clearfix repair-detail">
								<a href="javascript:;" onclick="repairRecordFei('5');">查看详情</a>
							</div>




						</div>
					</div>
					<div class="repair-body clearfix">
						<div class="master-list">
							<ul id="repair_overman">

								<!--<li>
									<div class="repair-master-info">
										<a
											href="${pageContext.request.contextPath }/jsp/service/master_setting.jsp"
											class="repair-setting">&nbsp;</a>
										<p class="repair-master-face">
											<img
												src="${pageContext.request.contextPath }/images/repair/master-face1.png" />
										</p>
										<p class="repair-master-name">
											王朝
										</p>
										<p class="repair-master-type">
											弱电维修
										</p>
										<p class="repair-master-phone">
											18570781607
										</p>
									</div>
									<div class="repair-master-staus">
										<span class="servicing">服务中</span>
									</div>
									<div class="repair-master-opt">
										<a
											href="${pageContext.request.contextPath }/jsp/service/master_repair_detail.jsp">查看维修记录</a>
									</div>
								</li>
								<li>
									<div class="repair-master-info">
										<a
											href="javascript:;"
											class="repair-setting" onclick="addMasterJsp();" >&nbsp;</a>
										<p class="repair-master-face">
											<img
												src="${pageContext.request.contextPath }/images/repair/master-face2.png" />
										</p>
										<p class="repair-master-name">
											王阳明
										</p>
										<p class="repair-master-type">
											强电维修
										</p>
										<p class="repair-master-phone">
											18570781607
										</p>
									</div>
									<div class="repair-master-staus">
										<span>空闲中</span>
									</div>
									<div class="repair-master-opt">
										<a
											href="${pageContext.request.contextPath }/jsp/service/master_repair_detail.jsp">查看维修记录</a>
									</div>
								</li>
								<li>
									<div class="repair-master-info">
										<a
											href="${pageContext.request.contextPath }/jsp/service/master_setting.jsp"
											class="repair-setting">&nbsp;</a>
										<p class="repair-master-face">
											<img
												src="${pageContext.request.contextPath }/images/repair/master-face3.png" />
										</p>
										<p class="repair-master-name">
											徐氏
										</p>
										<p class="repair-master-type">
											强电维修
										</p>
										<p class="repair-master-phone">
											18570781607
										</p>
									</div>
									<div class="repair-master-staus">
										<span>空闲中</span>
									</div>
									<div class="repair-master-opt">
										<a
											href="${pageContext.request.contextPath }/jsp/service/master_repair_detail.jsp">查看维修记录</a>
									</div>
								</li>
								<li>
									<div class="repair-master-info">
										<a
											href="${pageContext.request.contextPath }/jsp/service/master_setting.jsp"
											class="repair-setting">&nbsp;</a>
										<p class="repair-master-face">
											<img
												src="${pageContext.request.contextPath }/images/repair/master-face2.png" />
										</p>
										<p class="repair-master-name">
											王阳明
										</p>
										<p class="repair-master-type">
											强电维修
										</p>
										<p class="repair-master-phone">
											18570781607
										</p>
									</div>
									<div class="repair-master-staus">
										<span>空闲中</span>
									</div>
									<div class="repair-master-opt">
										<a
											href="${pageContext.request.contextPath }/jsp/service/master_repair_detail.jsp">查看维修记录</a>
									</div>
								</li>
								<li>
									<div class="repair-master-info">
										<a
											href="${pageContext.request.contextPath }/jsp/service/master_setting.jsp"
											class="repair-setting">&nbsp;</a>
										<p class="repair-master-face">
											<img
												src="${pageContext.request.contextPath }/images/repair/master-face2.png" />
										</p>
										<p class="repair-master-name">
											王阳明
										</p>
										<p class="repair-master-type">
											强电维修
										</p>
										<p class="repair-master-phone">
											18570781607
										</p>
									</div>
									<div class="repair-master-staus">
										<span>空闲中</span>
									</div>
									<div class="repair-master-opt">
										<a
											href="${pageContext.request.contextPath }/jsp/service/master_repair_detail.jsp">查看维修记录</a>
									</div>
								</li>
								<li>
									<div class="repair-master-add-box">
										<a href="javascript:void(0);" onclick="addMasterJsp();">
											<p>
												<img
													src="${pageContext.request.contextPath }/images/repair/repair-master-add.png" />
											</p>
											<p>
												添加员工
											</p> </a>
									</div>

								</li>
							--></ul>
						</div>
					</div>
					<div class="price-record">
						<div class="price-record-title">
							报价表修改记录
							<a href="javascript:void(0);" onclick="repairPriceUpdateJsp();">报价表修改</a>
						</div>
						<div class="price-update-record">
							<ul>
								<li>
									<span>更新记录:</span><span>2015-02-02</span><span>服务功能启用</span><span>价格修改为</span><span>50</span>
								</li>
								<li>
									<span>更新记录:</span><span>2015-02-02</span><span>服务功能启用</span><span>价格修改为</span><span>50</span>
								</li>
								<li>
									<span>更新记录:</span><span>2015-02-02</span><span>服务功能启用</span><span>价格修改为</span><span>50</span>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</section>
	</body>

</html>
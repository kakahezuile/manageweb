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
	function overman(){
	
	scheduleAll();
	var path = "<%= path%>/api/v1/communities/"+communityId+"/repairStatistics/center?sort=5";

		$.ajax({
					url : path,
					type : "GET",
					dataType : 'json',
					success : function(data) {
					   onScheduleAll();
	                    data = data.info;
						var repair_overman = $("#repair_overman");
						repair_overman.empty();
						//var liList='';

						for ( var i = 0; i < data.length; i++) {

							var liList = "";

							liList += "<li>";

							liList += "	<div class='repair-master-info'>";
							liList += "<a title=\"修改资料\" href=\"<%=path %>/jsp/estate/property/add_master.jsp?sort=5&shopId="+data[i].shopId+"&master=修改维修师傅&isUpAndAdd=up\" class=\"repair-setting\"  >&nbsp;</a>";
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
								liList += "<div class='repair-master-staus'>	<span class='free'>"
										+ "空闲中" + "</span></div>";
							} else {
								liList += "<div class='repair-master-staus'>	<span class='servicing'>"
										+ "服务中" + "</span></div>";
							}

							liList += "<div class='repair-master-opt'><a href=\"<%=path %>/jsp/estate/property/master_repair_detail.jsp?shopId="+data[i].shopId+"&sort=5\">查看维修记录</a></div></li>";

							repair_overman.append(liList);

						}

						repair_overman
								.append("<li><div class='repair-master-add-box'><a href=\"<%=path %>/jsp/estate/property/add_master.jsp?sort=5&master=添加维修师傅&isUpAndAdd=add\" >"
										+ "<p><img src='${pageContext.request.contextPath }/images/repair/repair-master-add.png' /></p><p>添加员工</p> </a></div>	</li>");

					},
					error : function(er) {
					}
				});

	}

	function maintainData() {
		conversion("top_data");
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var da = myDate.getDate() + 1;
		var startTime = myDate.getFullYear() + "-" + month + "-"
				+ myDate.getDate()+" 00:00:00";
		var endTime = myDate.getFullYear() + "-" + month + "-" + da+" 00:00:00";
		trade_d_m("日");
		document.getElementById("repair_time_type").value="day";
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
		var month = myDate.getMonth() + 1;
		var month2 = myDate.getMonth() + 2;



		var startTime = myDate.getFullYear() + "-" + month + "-1"
				+" 00:00:00";
		var endTime = myDate.getFullYear() + "-" + month2 + "-1"
				+" 00:00:00";
        trade_d_m("月");
        document.getElementById("repair_time_type").value="month";
		maintain(startTime, endTime);

	}


	function trade_d_m(M){
		var repairCount_d_m=document.getElementById("repairCount_d_m");
		var todayOrder_d_m=document.getElementById("todayOrder_d_m");
		var todayComplaintsCount_d_m=document.getElementById("todayComplaintsCount_d_m");
		var todayUnComplainsCount_d_m=document.getElementById("todayUnComplainsCount_d_m");
		var renounceCount_d_m=document.getElementById("renounceCount_d_m");
		var repairMoney_d_m=document.getElementById("repairMoney_d_m");
		var todaySubpackageCount_d_m=document.getElementById("todaySubpackageCount_d_m");
		
		repairCount_d_m.innerHTML="本"+M+"维修次数";
		todayOrder_d_m.innerHTML="本"+M+"结单次数";
		todayUnComplainsCount_d_m.innerHTML="本"+M+"拒绝次数";
		renounceCount_d_m.innerHTML="本"+M+"放弃次数";
		todayComplaintsCount_d_m.innerHTML="本"+M+"投诉次数";
		repairMoney_d_m.innerHTML="本"+M+"维修金额";
		todaySubpackageCount_d_m.innerHTML="本"+M+"分包次数";
	
	
	}
	function maintain(startTime, endTime) {
		var path = "<%= path%>/api/v1/communities/"+communityId+"/repairStatistics/top?sort=5&startTime="
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
						document.getElementById("renounceCount").innerHTML = data.abandonCount;
						
						document.getElementById("todayOrder").innerHTML=data.endOrder;
						document.getElementById("todaySubpackageCount").innerHTML=data.subpackageNum;
						if (data.repairMoney != 'null') {
							document.getElementById("repairMoney").innerHTML = data.repairMoney;

						} else {
							document.getElementById("repairMoney").innerHTML = "0";
						}
						
			
                     document.getElementById("repair_time_startTime").value=startTime;
                     document.getElementById("repair_time_endTime").value=endTime;
					},
					error : function(er) {
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
      var repairCount_d_m=document.getElementById("repairCount_d_m");
	  var todayOrder_d_m=document.getElementById("todayOrder_d_m");
	  var todayComplaintsCount_d_m=document.getElementById("todayComplaintsCount_d_m");
	  var todayUnComplainsCount_d_m=document.getElementById("todayUnComplainsCount_d_m");
	  var renounceCount_d_m=document.getElementById("renounceCount_d_m");
	  var repairMoney_d_m=document.getElementById("repairMoney_d_m");
	    document.getElementById("repair_time_type").value="duan";
		repairCount_d_m.innerHTML="总维修次数";
		todayOrder_d_m.innerHTML="总结单次数";
		todayUnComplainsCount_d_m.innerHTML="总的拒绝次数";
		renounceCount_d_m.innerHTML="总的放弃次数";
		todayComplaintsCount_d_m.innerHTML="总的投诉次数";
       maintain(startTime, endTime);
  }
  
  function upMasterJsp(shopId){
        setNone();
		addMasterGetItemCategory(5);
		$("#add_master_div_id").attr("style", "display:block");
		 document.getElementById("add_master_bao_fei").innerHTML="修改维修师傅";
       upShopsUser(shopId);
  }
  
  function repairDetil(){
     var type=document.getElementById("repair_time_type").value;
     var startTime=document.getElementById("repair_time_startTime").value;
     var endTime=document.getElementById("repair_time_endTime").value;
     window.location="<%=path %>/jsp/estate/property/repair_record.jsp?sort=5&type="+type+"&startTime="+startTime+"&endTime="+endTime;
  
  }
  
  function subpackage(){
     var type=document.getElementById("repair_time_type").value;
     var startTime=document.getElementById("repair_time_startTime").value;
     var endTime=document.getElementById("repair_time_endTime").value;
     window.location="<%=path %>/jsp/estate/property/repair_subpackage_record.jsp?sort=5&type="+type+"&startTime="+startTime+"&endTime="+endTime;
  
  }
  
  
</script>

	</head>
	<body>
	    <input type="hidden" id="repair_time_type"/>
        <input type="hidden" id="repair_time_startTime" />
		<input type="hidden" id="repair_time_endTime" />
		<section>
			<div class="content clearfix center-personal-info-content">
				<jsp:include flush="true" page="../common/service-left.jsp"></jsp:include>
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
							<!--<div class="repair-date">
								<span name="to_day_fei">2015-12-26</span>
							</div>
							--><div class="repair-stat clearfix">
								<div>
									<p>
										<a href="javascript:;" class="repair-count" id="repairCount">0</a>
									</p>
									<p>
										<span id="repairCount_d_m">本日维修次数</span>
									</p>
									
								</div>
								
								<div>
									<p><a href="javascript:;" class="nosolve-count" id="todayOrder">0</a></p>
									<p><span id="todayOrder_d_m">本日结单次数</span></p>
								</div>
								<div>
									<p><a href="javascript:;" class="nosolve-count" id="todayUnComplainsCount">0</a></p>
									<p><span id="todayUnComplainsCount_d_m">本日拒绝次数</span></p>
								</div>
								<div>
									<p><a href="javascript:;" class="nosolve-count" id="renounceCount">0</a></p>
									<p><span id="renounceCount_d_m">本日放弃次数</span></p>
								</div>
								<div>
									<p><a href="javascript:;" class="repair-fee" id="repairMoney">0</a></p>
									<p><span id="repairMoney_d_m">本日维修金额</span></p>
								</div>
							
							
							   <div>
									<p><a href="javascript:;" class="complain-count" id="todayComplaintsCount">0</a></p>
									<p><span id="todayComplaintsCount_d_m">本日投诉次数</span></p>
								</div>
							   <div>
									<p><a href="javascript:;" class="subpackage-count" onclick="subpackage();" id="todaySubpackageCount">0</a></p>
									<p><span id="todaySubpackageCount_d_m">本日分包次数</span></p>
								</div>
							
							</div> 
							<!--<div class="clearfix repair-detail">
								<a
									href="${pageContext.request.contextPath }/jsp/service/repair_record.jsp">查看详情</a>
							</div>
						
							-->


							<div class="clearfix repair-detail">
								<a href="javascript:;" onclick="repairDetil();">维修明细</a>
							</div>

						</div>
					</div>
					<div class="repair-body clearfix">
						<div class="master-list">
							<ul id="repair_overman">


							</ul>
						</div>
					</div>
					<div class="price-record">
						<div class="price-record-title">
							报价表修改记录
							<a href="<%=path %>/jsp/estate/property/repair_price.jsp" >报价表修改</a>
						</div>
						<div class="price-update-record">
							<ul id="up_repair">
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
		
		<div class="loadingbox" id="schedule_all" style="display: none;">
		<img alt="" src="<%= basePath %>/images/chat/loading.gif">
	</div>
	<div class="upload-master-face-bg" id="bg_schedule" style="display: none;">&nbsp;</div>
		
	</body>
		
	<script type="text/javascript">
	
    function scheduleAll(){
		$("#schedule_all").attr("style","display:block");
		$("#bg_schedule").attr("style","display:block");
		
	}
	
	function onScheduleAll(){
		$("#schedule_all").attr("style","display:none");
		$("#bg_schedule").attr("style","display:none");
	}
	   function up_repair(){
	   	var path = "<%= path%>/api/v1/communities/"+communityId+"/shops/getUpShopItem";
		$.ajax({
					url : path,
					type : "GET",
					dataType : 'json',
					success : function(data) {
						data = data.info;
					    data=data.pageData;
					    var repair_overman = $("#up_repair");
						repair_overman.empty();
						
					    for ( var i = 0; i < data.length; i++) {
					        var myDate=new Date(data[i].createTime*1000);
					        var month = myDate.getMonth() + 1;
					     var upDate=   myDate.getFullYear() + "-" + month + "-" + myDate.getDate();
							var list="<li>";
							 list+="<span>更新记录:</span>";
							  list+="<span>"+upDate+"</span>";
							 list+="<span>"+data[i].serviceName+"</span>";
							 list+="<span>价格修改为</span>";
							 list+="<span>"+data[i].currentPrice+"</span>";
							 list+="</li>";
							repair_overman.append(list);
						}
					},
					error : function(er) {
					}
				});
	   }
	   up_repair();
		overman();
		maintainData();
	    leftClikCss("left_repair_fei");
	
	</script>

</html>
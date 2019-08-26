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
		<title>维修记录_小间物业管理系统</title>


		<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->


		<script type="text/javascript">

function masterRepairDatailId3(startTime,endTime,shopId,sort,isComplaints){
	  document.getElementById("master_repir_detai_fei").value = shopId;
	  document.getElementById("master_shops_sort").value = sort;
	  document.getElementById("isComplaints_id").value = isComplaints;
	if(startTime==null||startTime==""){
	var myDate = new Date();
			myDate.getFullYear();
			myDate.getMonth();
			myDate.getDate();
			var month = myDate.getMonth() + 1;
			var da = myDate.getDate() + 1;
			var startTime = myDate.getFullYear() + "-" + month + "-"
					+ myDate.getDate()+" 00:00:00";
			var endTime = myDate.getFullYear() + "-" + month + "-" + da+" 00:00:00";
	
	}


		var path = "<%= path%>/api/v1/communities/${communityId}/repairStatistics/getComplaints?startTime="
						+ startTime + "&endTime=" + endTime + "&shopId=" + shopId+"&sort="+sort;
		
				$.ajax({
							url : path,
							type : "GET",
							dataType : 'json',
							success : function(data) {
		               
								data = data.info;
								if(data.avatar!="null"){
								document.getElementById("master_img_id").src=data.avatar;
								}
								
								
								var repair_overman = $("#top_name_list_id");
						repair_overman.empty();
						var  liList="";
		                        if(sort=='5'){
		                        
		                    
										    liList+=" <p class=\"master-tottle-list\" id=\"top_name_id\">";
		                        
		                        
		                       if(isComplaints=="no"){
		                           liList+=data.shopName+"被投诉总维修列表</p><p>";
		                       }else{
		                           liList+=data.shopName+"总维修列表</p><p>";
		                       }
									

                               liList+="历史维修次数<span>"+data.repairCountHistory+"</span></p><p>历史投诉次数";
                               liList+="<span>"+data.complaintsCountHistory+"</span>，未解决次数<span>" +data.unComplaintsCount+"</span></p><p>";
									if(data.repairMoneyHistory==null||data.repairMoneyHistory=='null'){
									liList+="历史维修金额<span>0</span></p>";
								}else{
								
								liList+="历史维修金额<span>"+ data.repairMoneyHistory+"</span></p>";
								
								}
										
										
									
									
										
										
									
		                        }else{
		                            liList+=" <p class=\"master-tottle-list\" id=\"top_name_id\">";
		                        
		                        
		                       if(isComplaints=="no"){
		                       liList+=data.shopName+" 被投诉保洁列表</p><p>";
		                       }else{
		                       liList+=data.shopName+"总保洁列表</p><p>";
		                       }
									

                               liList+="历史保洁次数<span>"+data.repairCountHistory+"</span></p><p>历史投诉次数";
                               liList+="<span>"+data.complaintsCountHistory+"</span>，未解决次数<span>" +data.unComplaintsCount+"</span></p><p>";
									if(data.repairMoneyHistory==null||data.repairMoneyHistory=='null'){
									liList+="历史保洁金额<span>0</span></p>";
								}else{
								
								liList+="历史保洁金额<span>"+ data.repairMoneyHistory+"</span></p>";
								
								}
		                        }
							
								repair_overman
								.append(liList);
								document.getElementById("master_repir_startTime").value=startTime;
								document.getElementById("master_repir_endTime").value=endTime;
		
							},
							error : function(er) {
								alert(er);
							}
						});
						
           masterRepairDatailList(startTime,endTime,1,shopId,sort,isComplaints);
	}
	
	

function masterRepairDatailList(startTime,endTime,pageNum,shopId,sort,isComplaints){
	

if(startTime==null||startTime==""){
var myDate = new Date();
		myDate.getFullYear();
		myDate.getMonth();
		myDate.getDate();
		var month = myDate.getMonth() + 1;
		var da = myDate.getDate() + 1;
		var startTime = myDate.getFullYear() + "-" + month + "-"
				+ myDate.getDate()+" 00:00:00";
		var endTime = myDate.getFullYear() + "-" + month + "-" + da+" 00:00:00";

}


var path = "<%= path%>/api/v1/communities/${communityId}/repairStatistics/getHistoryMaintainListDetail?startTime="
				+ startTime
				+ "&endTime="
				+ endTime
				+ "&shopId="
				+ shopId
				+ "&pageNum=" + pageNum + "&sort=" + sort;

		$.ajax({
					url : path,
					type : "GET",
					dataType : 'json',
					success : function(data) {
						data = data.info;

						$("#master_repair_datai_PageNum_get").val(data.num);
						$("#master_repair_datai_PageSize_get").val(
								data.pageCount);

						$("#master_repair_datai_PageNum").html(data.num);
						$("#master_repair_datai_PageSize").html(data.pageCount);
						$("#master_repair_datai_sum").html(data.rowCount);

						data = data.pageData;
						var repair_overman = $("#master_repair_detail_id_fei_ta");
						repair_overman.empty();
						//var liList='';
						if(sort=='5'){
							repair_overman
								.append("<tr><th class='repair-record-user-face'>客户头像</th><th>客户昵称</th>"
										+ "<th>客户电话</th><th>客户地址</th><th>维修项目</th><th>金额</th><th>接单时间</th><th>服务时长"
										+ "</th><th>投诉情况</th></tr>");
						
						}else{
							repair_overman
								.append("<tr><th class='repair-record-user-face'>&nbsp;</th><th>客户</th>"
										+ "<th>电话</th><th>地址</th><th>金额</th><th>接单时间</th><th>服务时长"
										+ "</th><th>投诉情况</th></tr>");
						}
					
						for ( var i = 0; i < data.length; i++) {

							var liList = "";

							liList += "<tr>	<td class='repair-record-user-face'>";

							liList += "<img	src='"+data[i].avatar+"'/>";
							liList += "</td><td class='repair-record-user-name'>"
									+ data[i].username + "</td>";

							liList += "<td class='repair-record-user-name'>"
									+ data[i].phone + "</td>";
							liList += "<td>" + data[i].userFloor+data[i].userUnit+data[i].room;
							if(sort=='5'){
							liList+= "</td><td>水管维修</td>";
							}
							liList += "<td>" + data[i].price + "</td>";
							
							var date3=data[i].startTime-data[i].endTime;
							
							var leave1=date3%(24*3600*1000) ;   //计算天数后剩余的毫秒数
                          var hours=Math.floor(leave1/(3600*1000));
							
							
							
							liList += "<td>" + data[i].startTime
									+ "</td><td>"+hours+"</td>";
							if(data[i].isComplaints!=0){
									liList += "<td class='user-complain'>已投诉	</td></tr>";
							}
							
                          if(isComplaints=="no"){
                          	if(data[i].isComplaints!=0){
									repair_overman.append(liList);
							}
                          }else{
                          repair_overman.append(liList);
                          }
							

						}
					},
					error : function(er) {
						alert(er);
					}
				});

	}

	function getMasterRepairDetailPage(num) {
		var page_num = 1;
		var repairRecordPageNum = document
				.getElementById("master_repair_datai_PageNum_get").value;
		var repairRecordPageSize = document
				.getElementById("master_repair_datai_PageSize_get").value;

		var startTime = document.getElementById("master_repir_startTime").value;
		var endTime = document.getElementById("master_repir_endTime").value;

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
		var master_repir_shopId_fei = document
				.getElementById("master_repir_detai_fei").value;
		var master_shops_sort = document.getElementById("master_shops_sort").value;
		var isComplaints = document.getElementById("isComplaints_id").value;
		masterRepairDatailList(startTime, endTime, page_num,
				master_repir_shopId_fei, master_shops_sort,isComplaints);
	}

	function masterRepairMonth() {
		conversionMaster("top_master_month");
		var sort = document.getElementById("master_shops_sort").value;
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
				+ firstDate.getDate() + " 00:00:00";
		var endTime = myDate.getFullYear() + "-" + month + "-"
				+ endDate.getDate() + " 00:00:00";
		//   trade_d_m("月");
		var master_repir_shopId_fei = document
				.getElementById("master_repir_detai_fei").value;
				var isComplaints = document.getElementById("isComplaints_id").value;
		masterRepairDatailId3(startTime, endTime, master_repir_shopId_fei, sort,isComplaints);

	}

	function masterRepairData() {
		conversionMaster("top_master_data");
		var myDate = new Date();
		myDate.getFullYear();
		myDate.getMonth();
		myDate.getDate();
		var month = myDate.getMonth() + 1;
		var da = myDate.getDate() + 1;
		var startTime = myDate.getFullYear() + "-" + month + "-"
				+ myDate.getDate() + " 00:00:00";
		var endTime = myDate.getFullYear() + "-" + month + "-" + da
				+ " 00:00:00";
		//trade_d_m("日");
		var master_repir_shopId_fei = document
				.getElementById("master_repir_detai_fei").value;
		var sort = document.getElementById("master_shops_sort").value;
			var isComplaints = document.getElementById("isComplaints_id").value;
		masterRepairDatailId3(startTime, endTime, master_repir_shopId_fei, sort,isComplaints);
	}
	function conversionMaster(id) {
		var top_data = document.getElementById("top_master_data");
		var top_month = document.getElementById("top_master_month");
		var top_section = document.getElementById("top_master_section");
		if (id == "top_master_data") {
			top_data.className = "select";
		} else {
			top_data.className = "";
		}
		if (id == "top_master_month") {
			top_month.className = "select";
		} else {
			top_month.className = "";
		}
		if (id == "top_master_section") {
			top_section.className = "select";
		} else {
			top_section.className = "";
		}

	}
</script>

	</head>
	<body>
		<input type="hidden" id="master_repir_detai_fei" />
		<input type="hidden" id="master_repir_startTime" />
		<input type="hidden" id="master_repir_endTime" />
		<input type="hidden" id="master_shops_sort" />
		<input type="hidden" id="isComplaints_id" />
		<section>
			<div class="content clearfix center-personal-info-content">
				<jsp:include flush="true" page="../public/service-left.jsp" />
				<div class="service-right-body">
					<div class="repair-head">
						<div class="repair-title">
							<a href="javascript:;" class="select" id="top_master_data"
								onclick="masterRepairData();">日</a>
							<a href="javascript:;" id="top_master_month"
								onclick="masterRepairMonth();">月</a>
							<a href="javascript:;" id="top_master_section">时间段</a>
						</div>
						<div class="repair-content clearfix">
							<div class="repair-date">
								<span name="to_day_fei">2015-12-26</span>
							</div>
							<div class="repair-detail-box">
								<div class="repair-detail-master" >
									<img id="master_img_id"
										src="${pageContext.request.contextPath }/images/repair/master-face1.png" />
								</div>
								<div class="repair-detail-info" id="top_name_list_id">
									<p class="master-tottle-list" id="top_name_id">
										王明阳 总维修列表
									</p>
									<p id="">
										历史维修次数
										<span id="master_num">12</span>
									</p>
									<p>
										历史投诉次数
										<span id="repair_num">3</span>，未解决次数
										<span id="no_num"> 0</span>
									</p>
									<p>
										历史维修金额
										<span id="detail">1067</span>
									</p>
								</div>
							</div>
							<!--<div class="repair-stat clearfix" style="float:left;width:300px;">
							<img src="${pageContext.request.contextPath }/images/repair/master-face1.png"/>						
						</div>
						<div style="float:left;width:600px;">
							<p>王明阳 总维修列表</p>
							<p>历史维修次数 12</p>
							<p>历史投诉次数  3，未解决次数 0</p>
							<p>历史维修金额  1067</p>
						</div>
					-->
						</div>
					</div>
					<div class="repair-tottle-list">
						<table class="repair-tottle-list-title"
							id="master_repair_detail_id_fei_ta">
							<tr>
								<th class="repair-record-user-face">
									&nbsp;
								</th>
								<th>
									客户
								</th>
								<th>
									电话
								</th>
								<th>
									地址
								</th>
								<th>
									维修项目
								</th>
								<th>
									金额
								</th>
								<th>
									接单时间
								</th>
								<th>
									服务时长
								</th>
								<th>
									投诉情况
								</th>
							</tr>
							<tr>
								<td class="repair-record-user-face">
									<img
										src="${pageContext.request.contextPath }/images/repair/master-face1.png" />
								</td>
								<td class="repair-record-user-name">
									张昊天
								</td>
								<td class="repair-record-user-name">
									18512663255
								</td>
								<td>
									3号楼2单元501
								</td>
								<td>
									水管维修
								</td>
								<td>
									40
								</td>
								<td>
									2015-12-02 16：32
								</td>
								<td>
									1:23
								</td>
								<td class="user-complain">
									已投诉
								</td>
							</tr>
						</table>


						<div class="repair-record-head">

						</div>
						<div class="repair-record-body">
							<ul>
								<li></li>
							</ul>
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
			</div>
		</section>
	</body>
	<jsp:include flush="true" page="../public/footer.jsp"></jsp:include>
</html>
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
		<title> 帮帮券历史记录_间物业管理系统</title>


		<script type="text/javascript">
	
	
	function record_bonus(pageNum,bonusName,bonusPar,startTime,endTime){
		document.getElementById("statistics_bonus_user").style.display="none";
					document.getElementById("seek_id").style.display="";
		var path = "<%=path%>/api/v1/communities/"+communityId+"/bonuses/statisticsTimeBonus?pageSize=5&pageNum="+pageNum+"&bonusName="+
		bonusName+"&bonusPar="+bonusPar+"&startTime="+startTime+"&endTime="+endTime;
		
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
						    var myDate=new Date(data[i].createTime*1000);
						    
						    
							var month = myDate.getMonth() + 1;
							var da = myDate.getFullYear() + "年" + month + "月" + myDate.getDate()+"日";
							var liList = "<table><tr>";
						    // liList="<th>经手人：</th>";
							liList+="<th>时间:"+ da+"</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th></tr>";
                            liList+="<tr class=\"odd\"><td > 类型</td><td>面额</td><td>数量</td><td>发放金额</td><td></td><td>发放详情</td></tr>";
							liList+="<tr class=\"odd\"><td >"+data[i].bonusName;
							
							liList+="</td><td>"+data[i].bonusPar+"元</td><td>"+data[i].bonusNum+"</td><td>"+data[i].bonusParSum+"</td><td></td><td><a onclick=\"getBonusUser('"+data[i].createTime+"');\">发放详情</a></td></tr></table>";
							repair_overman.append(liList);
						}

					
				},

				error:function(er){
			
					alert(er);
			
				}

			});
	}
	
	function statisticsBonusUser(createTime){
	
	   
	
	
	        var path = "<%=path%>/api/v1/communities/"+communityId+"/bonuses/statisticsBonusUser?createTime="+createTime;
		
			$.ajax({

				url: path,

				type: "GET",

				dataType:'json',

				success:function(data){
					data=data.info;
					document.getElementById("statistics_bonus_user").style.display="";
					document.getElementById("seek_id").style.display="none";
					
					 
					document.getElementById("statistics_1").innerHTML="使用张数"+data.usedNum;
					document.getElementById("statistics_2").innerHTML="总使用张数"+data.ubNum;
					var bi=0;
					if(data.ubNum!=0){
					   bi=(data.ubNum/data.ubNum)*100
					}
					document.getElementById("statistics_3").innerHTML="使用占比"+bi
							.toFixed(2)
							+ "%";
				},

				error:function(er){
			
					alert(er);
			
				}

			});
	}
	
	function getBonusUser(createTime){
	        statisticsBonusUser(createTime);
	        var path = "<%=path%>/api/v1/communities/"+communityId+"/bonuses/getBonusUser?createTime="+createTime;
		
			$.ajax({

				url: path,

				type: "GET",

				dataType:'json',

				success:function(data){
					data=data.info;
						var repair_overman = $("#record_bangbang_id");
						repair_overman.empty();
						//var liList='';
	                    var liList = "<table>";
	                    liList+="<tr><th>用户昵称</th><th>用户电话</th><th>用户地址</th><th>帮帮券有效期</th><th>发放时间</th><th>使用情况</th></tr>";
						for ( var i = 0; i < data.length; i++) {
						       liList+="<tr>";
						       liList+="<td>"+data[i].nickname+"</td>";
						       liList+="<td>"+data[i].username+"</td>";
						       liList+="<td>"+data[i].userFloor+data[i].userUnit+data[i].room+"</td>";
						       liList+="<td>"+getStringTime(data[i].startTime*1000)+"到"+getStringTime(data[i].expireTime*1000)+"</td>";
						       liList+="<td>"+getStringTime(data[i].createTime*1000)+"</td>";
						       if(data[i].bonusStatus=="unused"){
						         liList+="<td>未使用</td>";
						       }else{
						         liList+="<td><a onclick=\"getOrdersBonus('"+data[i].serial+"');\">已使用</a></td>";
						       }
						       liList+="<tr>";
						}
						liList+="</table>";
						repair_overman.append(liList);
				},

				error:function(er){
			
					alert(er);
			
				}

			});
	}
	
	
	function getOrdersBonus(serial){
	
	        var path = "<%=path%>/api/v1/communities/"+communityId+"/bonuses/getOrdersBonus?serial="+serial;
	        
			$.ajax({

				url: path,

				type: "GET",

				dataType:'json',

				success:function(data){
					data=data.info;
						var repair_overman = $("#record_bangbang_id");
						repair_overman.empty();
						//var liList='';
	                    var liList = "<table>";
	                    liList+="<tr><th>店家名称</th><th>下单时间</th><th>结单时间</th></tr>";
						for ( var i = 0; i < data.length; i++) {
						       liList+="<tr>";
						       liList+="<td>"+data[i].shopName+"</td>";
						       liList+="<td>"+toStringTimeHs(data[i].startTime*1000)+"</td>";
						       liList+="<td>"+toStringTimeHs(data[i].endTime*1000)+"</td>";
						      // liList+="<td>"+data[i].orderPrice+"</td>";
						       liList+="<tr>";
						}
						liList+="</table>";
						repair_overman.append(liList);
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

		record_bonus( page_num,"全部","","","");
	}
	
	function bonusSeek(){
		 var bonus_type_id =  document.getElementById("bonus_type_id").value;
		 var bonus_par_id=  document.getElementById("bonus_par_id").value;
		 var txtBeginDate=  document.getElementById("txtBeginDate").value;
		 var txtEndDate=  document.getElementById("txtEndDate").value;
		 record_bonus( 1,bonus_type_id,bonus_par_id,txtBeginDate,txtEndDate);
	}
</script>
	</head>
	<body>
		<section>
			<div class="content clearfix">
				<jsp:include flush="true" page="./bangbang_left.jsp" />
				<div class="express-right-body">
					<div class="repair-title-line">
						<p class="bangbang-ticket-title">
							发放明细
						</p>
						<div id="seek_id" class="filter-bangbang">
							<div class="time-chose-bangbang" id="top_section_time">   
								<input readonly="readonly" id="txtBeginDate" value="请选择开始日期"/>
								<input readonly="readonly" id="txtEndDate" value="请选择结束日期"/>
							</div>
							<select id="bonus_type_id">
								<option value="全部">全部</option>
								<option value="通用券">通用券</option>
								<option value="维修券">维修券</option>
								<option value="快店券">快店券</option>
							</select>
							发放金额<input type="text" id="bonus_par_id"/>
							<a href="javascript:bonusSeek();" >搜索</a>
							<!--<input type="button" onclick="bonusSeek();"  value="搜索">-->
						</div>
					</div>
					
					<div id="statistics_bonus_user" style="display: none;">
					  <b id="statistics_1">
							使用张数
						</b>
					  <b id="statistics_2">
							   总张数
						</b>
					  <b id="statistics_3">
							使用张数
						</b>
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
						<div  class="divide-page" style="float: left; width: 100%; height: 50px; clear: both; text-align: center;">


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

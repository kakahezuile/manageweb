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

		<link
			href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>"
			type="image/x-icon" rel="shortcut icon">
		<link
			href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<link
			href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>
		<script src="${pageContext.request.contextPath }/js/jquery-1.6.js"></script>


       <title>敏感词监控_小间运营系统</title>
	<script type="text/javascript">
	    var communityId = window.parent.document
			.getElementById("community_id_index").value;
		var sensitiveData;
		
		var sensitiveWordsPageNum = 1;
		
		var sensitiveWordsPageSize = 10;
		
		var sensitiveEmobId;
		
		var sensitiveMinTime;
		
		var sensitiveMaxTime;
		
		var sensitiveGroupId;
		
		
		
		function getSensitiveWords(pageNum){
		
			var path = "<%= path%>/api/v1/communities/"+communityId+"/sensitiveWordsHistory?pageNum="+pageNum+"&pageSize="+sensitiveWordsPageSize;
		
			$.ajax({

				url: path,

				type: "GET",

				dataType:"json",

				success:function(data){
				  var data2=data.info;
				        $("#repairRecordPageNum_get").val(data2.num);
						$("#repairRecordPageSize_get").val(data2.pageCount);

						$("#repairRecordPageNum").html(data2.num);
						$("#repairRecordPageSize").html(data2.pageCount);
						$("#repairRecordSum").html(data2.rowCount);
				
				
				        $("#sensitiveWordsTable").attr("style","display:");
			            $("#sensitiveMain").attr("style","display:none");
					    console.info(data);
					if(data.status == "yes"){
						$("#sensitiveWordsTable").html("");
						$("#sensitiveWordsTable").append("<tr><th>敏感词</th><th>群组名称</th><th>出现时间</th><th>处理结果</th><th>定位敏感词</th></tr>");
						data = data.info.pageData;
						sensitiveData = data;
						var len = data.length;
						for(var i = 0 ; i < len ; i++){
							outTime = getSmpFormatDateByLong(data[i].historyTime * 1000);
							var status = "";
							var className ="even";
							if(data[i].status == "untreated"||data[i].status=="examine"){
								status = "未处理";
							}
							if(i%2==0){
								className="even";
							}else{
								className="odd";
							}
							if(data[i].status == "untreated"){
						    	className="warning";
							}
							$("#sensitiveWordsTable").append("<tr class=\""+className+"\"><td>"+data[i].sensitiveWords+"</td><td>"+data[i].groupName+"</td><td>"+outTime+"</td><td>"+status+"</td><td class=\"sensitive-detail\"><a class=\"red-button\" href=\"javascript:void(0);\" onclick=\"sensitiveWordsSelect("+i+");examine("+data[i].sensitiveWordsHistoryId+")\" >查看</a></td></tr>");
						}
						
					}else{
						alert(data.message);
					}	
				},

				error:function(er){
			
			
				}

			});
		}
		
		function getSensitiveWordsByStatus(status){
			
			var sensitiveTableFirst = $("#sensitiveCenterTable tr").eq(0);
			console.info(sensitiveTableFirst);
			var sensitiveTableNext = $("#sensitiveCenterTable tr").eq($("#sensitiveCenterTable tr").size() - 1);
			var path = "<%= path%>/api/v1/communities/"+communityId+"/sensitiveWordsHistory/"+sensitiveGroupId+"/messages/"+status;
			if(status == "next"){
				//sensitiveTableNext.after("<tr><td>11111111</td></tr>");
				path += "?time="+sensitiveMaxTime;
			}else if(status == "last"){
				//sensitiveTableFirst.before("<tr><td>22222222</td></tr>");
				path += "?time="+sensitiveMinTime;
			}
			$.ajax({

				url: path,

				type: "GET",

				dataType:"json",

				success:function(data){
					console.info(data);
					if(data.status == "yes"){
						data = data.info;
						var len = data.length;
						if(len < 1){
							alert("没有更多的记录的了");
							return;
						}
						for(var i = 0 ; i < len ; i++){
							sensitiveTableFirst = $("#sensitiveCenterTable tr").eq(0);
							sensitiveTableNext = $("#sensitiveCenterTable tr").eq($("#sensitiveCenterTable tr").size() - 1);
							nickName = data[i].nickname;
							msg = data[i].msg; 
							outTime = getSmpFormatDateByLong(data[i].timestamp);
							
							if(status == "next"){
								if(i == len-1){
									sensitiveMaxTime = data[i].timestamp;
								}
								sensitiveTableNext.after("<tr><td class=\"sensitiveTimeLeft\"><i>"+outTime+" </i></td></tr>");
								sensitiveTableNext = $("#sensitiveCenterTable tr").eq($("#sensitiveCenterTable tr").size() - 1);
								if(data[i].messageFrom == sensitiveEmobId){  // 左边
									sensitiveTableNext.after("<tr class=\"first-p\"><td class=\"sensitiveTextLeft\"><font class=\"sensitiveUserName\">"+nickName+"</font> : "+msg+"</td></tr>");
								}else{ // 右边
									sensitiveTableNext.after("<tr class=\"second-p\"><td class=\"sensitiveTextLeft\"><font class=\"sensitiveUserName\">"+nickName+"</font> : "+msg+"</td></tr>");
								}
								//sensitiveTableNext.after("<tr><td>11111111</td></tr>");
							}else if(status == "last"){
								if(i == len-1){
									sensitiveMinTime = data[i].timestamp;
								}	
								
								
								if(data[i].messageFrom == sensitiveEmobId){  // 左边
									sensitiveTableFirst.before("<tr class=\"first-p\"><td class=\"sensitiveTextLeft\"><font class=\"sensitiveUserName\">"+nickName+"</font> : "+msg+"</td></tr>");
								}else{ // 右边
									sensitiveTableFirst.before("<tr class=\"second-p\"><td class=\"sensitiveTextLeft\"><font class=\"sensitiveUserName\">"+nickName+"</font> : "+msg+"</td></tr>");
								}
								//sensitiveTableFirst.before("<tr><td>22222222</td></tr>");
								sensitiveTableFirst = $("#sensitiveCenterTable tr").eq(0);
								sensitiveTableFirst.before("<tr><td class=\"sensitiveTimeLeft\"><i> "+outTime+" </i></td></tr>");
							}
						}
						
					}else{
						alert(data.message);
					}	
				},

				error:function(er){
			
			
				}

			});
			//alert(sensitiveTableFirst);
		}
		
		function sensitiveWordsSelect(index){
		  
		    document.getElementById("fen_ye").style.display="none";
			$("#sensitiveWordsTable").attr("style","display:none");
			$("#sensitiveMain").attr("style","display:block");
			var data = sensitiveData[index];
			sensitiveGroupId = data.groupId;
			outTime = getSmpFormatDateByLong(data.historyTime * 1000);
			var status;
			if(data.status == "untreated"){
				status = "未处理";
			}
			var uuid = data.uuid;
			sensitiveEmobId = data.emobId;
			  document.getElementById("tile_del").value=data.historyTime;
			$("#sensitiveTop").empty();
			$("#sensitiveTop").append("<table align=\"center\" width=\"65%\" height=\"100%\" ><tr><td>敏感词<span>"+data.sensitiveWords+"</span></td><td>群组名称<span>"+data.groupName+"</span></td><td>出现时间<span>"+outTime+"</span></td><td class=\"close-box\"><a class=\"close-activity-group\" href=\"javascript:;\" onclick=\"deleteActivities('"+sensitiveGroupId+"','"+sensitiveEmobId+"')\" >关闭群组</a></td></tr></table>");
			getMessageByUuid(uuid,data.sensitiveWords);
		}
		
		function examine(sensitiveWordsHistoryId){
		
		    var path = "<%= path%>/api/v1/communities/"+communityId+"/sensitiveWordsHistory/"+sensitiveWordsHistoryId;
		   	var myJon = "{\"status\":\"examine\"}";
			
			$.ajax({

				url: path,

				type: "PUT",
                 data:myJon,
				dataType:"json",

				success:function(data){
				},

				error:function(er){
			
			
				}

			});
		}
		function getMessageByUuid(uuid,sensitiveWords){
			
			var path = "<%= path%>/api/v1/communities/"+communityId+"/sensitiveWordsHistory/"+uuid+"/messages";
			
			$.ajax({

				url: path,

				type: "GET",

				dataType:"json",

				success:function(data){
					console.info(data);
					if(data.status == "yes"){
						data = data.info;
						var sensitiveCenterTable = $("#sensitiveCenterTable");
						sensitiveCenterTable.empty();
						var len = data.length;
						for(var i = 0 ; i < len ; i++){
							if(i == 0){
								sensitiveMinTime = data[i].timestamp;
							}
							if(i == len-1){
								sensitiveMaxTime = data[i].timestamp;
							}
							nickName = data[i].nickname;
							msg = data[i].msg; 
							outTime = getSmpFormatDateByLong(data[i].timestamp);
                         
						   sensitiveCenterTable.append("<tr><td class=\"sensitiveTimeLeft\"><i> "+outTime+" </i></td></tr>");
							if(data[i].messageFrom == sensitiveEmobId){  // 左边
							   var a1 =  msg.split(sensitiveWords);
                            var b1="";
                             for ( var j = 0; j < a1.length; j++) {
									b1+=a1[j];
									if(j>0&j<a1.length){
									   b1+="<b>"+sensitiveWords+"</b>";
									}
							}
								sensitiveCenterTable.append("<tr class=\"first-p\"><td class=\"sensitiveTextLeft\"><font class=\"sensitiveUserName\">"+nickName+"</font> :"+b1+"</td></tr>");
							}else{ // 右边
								sensitiveCenterTable.append("<tr class=\"second-p\"><td class=\"sensitiveTextLeft\"><font class=\"sensitiveUserName\">"+nickName+"</font> : "+msg+"</td></tr>");
							}
						}
					}	
				},

				error:function(er){
			
			
				}

			});
		}
		
		
	//扩展Date的format方法   
	Date.prototype.format = function(format) {
		var o = {
			"M+" : this.getMonth() + 1,
			"d+" : this.getDate(),
			"h+" : this.getHours(),
			"m+" : this.getMinutes(),
			"s+" : this.getSeconds(),
			"q+" : Math.floor((this.getMonth() + 3) / 3),
			"S" : this.getMilliseconds()
		};
		if (/(y+)/.test(format)) {
			format = format.replace(RegExp.$1, (this.getFullYear() + "")
					.substr(4 - RegExp.$1.length));
		}
		for ( var k in o) {
			if (new RegExp("(" + k + ")").test(format)) {
				format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
						: ("00" + o[k]).substr(("" + o[k]).length));
			}
		}
		return format;
	};
			/**   
	 *转换日期对象为日期字符串   
	 * @param date 日期对象   
	 * @param isFull 是否为完整的日期数据,   
	 *               为true时, 格式如"2000-03-05 01:05:04"   
	 *               为false时, 格式如 "2000-03-05"   
	 * @return 符合要求的日期字符串   
	 */
	function getSmpFormatDate(date, isFull) {
		var pattern = "";
		if (isFull == true || isFull == undefined) {
			pattern = "yyyy-MM-dd hh:mm:ss";
		} else {
			pattern = "yyyy-MM-dd";
		}
		return getFormatDate(date, pattern);
	}
	/**   
	 *转换当前日期对象为日期字符串   
	 * @param date 日期对象   
	 * @param isFull 是否为完整的日期数据,   
	 *               为true时, 格式如"2000-03-05 01:05:04"   
	 *               为false时, 格式如 "2000-03-05"   
	 * @return 符合要求的日期字符串   
	 */

	function getSmpFormatNowDate(isFull) {
		return getSmpFormatDate(new Date(), isFull);
	}
	/**   
	 *转换long值为日期字符串   
	 * @param l long值   
	 * @param isFull 是否为完整的日期数据,   
	 *               为true时, 格式如"2000-03-05 01:05:04"   
	 *               为false时, 格式如 "2000-03-05"   
	 * @return 符合要求的日期字符串   
	 */

	function getSmpFormatDateByLong(l, isFull) {
		return getSmpFormatDate(new Date(l), isFull);
	}
	/**   
	 *转换long值为日期字符串   
	 * @param l long值   
	 * @param pattern 格式字符串,例如：yyyy-MM-dd hh:mm:ss   
	 * @return 符合要求的日期字符串   
	 */

	function getFormatDateByLong(l, pattern) {
		return getFormatDate(new Date(l), pattern);
	}
	/**   
	 *转换日期对象为日期字符串   
	 * @param l long值   
	 * @param pattern 格式字符串,例如：yyyy-MM-dd hh:mm:ss   
	 * @return 符合要求的日期字符串   
	 */
	function getFormatDate(date, pattern) {
		if (date == undefined) {
			date = new Date();
		}
		if (pattern == undefined) {
			pattern = "yyyy-MM-dd hh:mm:ss";
		}

		return date.format(pattern);
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
				
				
				
				
		getSensitiveWords(page_num);
	}
	
	
	function deleteActivities(id,emobGroupId){
	
	  var activityTitle=document.getElementById("tile_del").value;
	if(confirm(" 是否关闭\n"+activityTitle+"\n此操作不可逆！")){
			
				if(emobGroupId == "null"){
					console.info("群根本还没开启");
					updateActivitiesStatus(id,"rejected");
					getActivitiesList("");
				}else{
					var path = "<%= path%>/api/v1/communities/"+communityId+"/activities/webIm/deleteActivity/"+id;
					var myJon = "{\"activityId\":"+id+" , \"emobGroupId\":"+emobGroupId+",\"activityTitle\":\""+activityTitle+"\"}";
					$.post(path , myJon , function(data){
						if(data.status == "yes"){
							//getActivitiesList("");
							getActivitiesList(1,"","ongoing");
						}
					},"json");
				}
			
			
			}
	
	}
	</script>
	<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/operation.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
</head>
<body>
<input id="tile_del" type="hidden" value="" />
	<section>
		<div class="content clearfix">
			<jsp:include flush="true" page="../common/activity_left.jsp"></jsp:include>
			<div class="right-body" style="border:none;">
			   	<table id="sensitiveWordsTable" align="center" class="sensitive-words-table">
			   		<tr>
			   			<th>敏感词</th>
			   			<th>群组名称</th>
			   			<th>出现时间</th>
			   			<th>处理结果</th>
			   			<th>定位敏感词</th>
			   		</tr>
			   	</table>
			   	<div align="center" class="sensitiveMain" id="sensitiveMain" style="display: none;">
			   		<div class="sensitiveTop" id="sensitiveTop"></div>
			   		<div class="sensitiveCenter" id="sensitiveCenter" >
			   			<table id="sensitiveCenterTable" align="center" width="80%">
			   			
			   			</table>
			   		</div>
			   		<div class="pages-show">
			   			<a href="javascript:void(0);" onclick="getSensitiveWordsByStatus('last');">上一页</a>
			   			<a href="javascript:void(0);" onclick="getSensitiveWordsByStatus('next');">下一页</a>
			   		</div>
			   	</div>
			</div>
		</div>
		<div class="divide-page" style="float: left; width: 100%; height: 50px; clear: both; text-align: center;" id="fen_ye">
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
	</section>
	
</body>
<script type="text/javascript">

getSensitiveWords(1);
 activity_left_List("activity_left_sensitive");
</script>
</html>
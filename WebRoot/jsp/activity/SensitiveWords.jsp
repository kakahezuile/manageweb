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
<title>敏感词监控_小间运营系统</title>
	<script type="text/javascript">

		var sensitiveData;
		
		var sensitiveWordsPageNum = 1;
		
		var sensitiveWordsPageSize = 10;
		
		var sensitiveEmobId;
		
		var sensitiveMinTime;
		
		var sensitiveMaxTime;
		
		var sensitiveGroupId;
		
		var communityId = window.parent.document.getElementById("community_id_index").value;
		
		
		function getSensitiveWords(){
		
			var path = "<%= path%>/api/v1/communities/"+communityId+"/sensitiveWordsHistory?pageNum="+sensitiveWordsPageNum+"&pageSize="+sensitiveWordsPageSize;
		
			$.ajax({

				url: path,

				type: "GET",

				dataType:"json",

				success:function(data){
				$("#sensitiveWordsTable").attr("style","display:");
			    $("#sensitiveMain").attr("style","display:none");
					console.info(data);
					if(data.status == "yes"){
						$("#sensitiveWordsTable").html("");
						$("#sensitiveWordsTable").append("<tr><th>敏感词</th><th>群组名称</th><th>所属物业</th><th>出现时间</th><th>处理结果</th><th>定位敏感词</th></tr>");
						data = data.info.pageData;
						sensitiveData = data;
						var len = data.length;
						for(var i = 0 ; i < len ; i++){
							outTime = getSmpFormatDateByLong(data[i].historyTime * 1000);
							var status = "";
							var className ="even";
							if(data[i].status == "untreated"){
								status = "未处理";
							}
							if(i%2==0){
								className="even";
							}else{
								className="odd";
							}
							$("#sensitiveWordsTable").append("<tr class=\""+className+"\"><td>"+data[i].sensitiveWords+"</td><td>"+data[i].groupName+"</td><td>"+data[i].communityName+"</td><td>"+outTime+"</td><td>"+status+"</td><td class=\"sensitive-detail\"><a href=\"javascript:void(0);\" onclick=\"sensitiveWordsSelect("+i+");\" >查看</a></td></tr>");
						}
						
					}else{
						alert(data.message);
					}	
				},

				error:function(er){
			
					alert(er);
			
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
								sensitiveTableNext.after("<tr><td class=\"sensitiveTextCenter\">— "+outTime+" —</td></tr>");
								sensitiveTableNext = $("#sensitiveCenterTable tr").eq($("#sensitiveCenterTable tr").size() - 1);
								if(data[i].messageFrom == sensitiveEmobId){  // 左边
									sensitiveTableNext.after("<tr><td class=\"sensitiveTextLeft\"><font class=\"sensitiveUserName\">"+nickName+"</font> : "+msg+"</td></tr>");
								}else{ // 右边
									sensitiveTableNext.after("<tr><td class=\"sensitiveTextRight\">"+msg+" : <font class=\"sensitiveUserName\">"+nickName+"</font></td></tr>");
								}
								//sensitiveTableNext.after("<tr><td>11111111</td></tr>");
							}else if(status == "last"){
								if(i == len-1){
									sensitiveMinTime = data[i].timestamp;
								}	
								
								
								if(data[i].messageFrom == sensitiveEmobId){  // 左边
									sensitiveTableFirst.before("<tr><td class=\"sensitiveTextLeft\"><font class=\"sensitiveUserName\">"+nickName+"</font> : "+msg+"</td></tr>");
								}else{ // 右边
									sensitiveTableFirst.before("<tr><td class=\"sensitiveTextRight\">"+msg+" : <font class=\"sensitiveUserName\">"+nickName+"</font></td></tr>");
								}
								//sensitiveTableFirst.before("<tr><td>22222222</td></tr>");
								sensitiveTableFirst = $("#sensitiveCenterTable tr").eq(0);
								sensitiveTableFirst.before("<tr><td class=\"sensitiveTextCenter\">— "+outTime+" —</td></tr>");
							}
						}
						
					}else{
						alert(data.message);
					}	
				},

				error:function(er){
			
					alert(er);
			
				}

			});
			//alert(sensitiveTableFirst);
		}
		
		function sensitiveWordsSelect(index){
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
			$("#sensitiveTop").append("<table align=\"center\" width=\"65%\" height=\"100%\" ><tr><td>敏感词<span>"+data.sensitiveWords+"</span></td><td>群组名称<span>"+data.groupName+"</span></td><td>所属物业<span>"+data.communityName+"</span></td><td>出现时间<span>"+outTime+"</span></td><td>处理结果<span>"+status+"</span></td></tr></table>");
			getMessageByUuid(uuid);
		}
		
		function getMessageByUuid(uuid){
			
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
							sensitiveCenterTable.append("<tr><td class=\"sensitiveTextCenter\">— "+outTime+" —</td></tr>");
							if(data[i].messageFrom == sensitiveEmobId){  // 左边
								sensitiveCenterTable.append("<tr><td class=\"sensitiveTextLeft\"><font class=\"sensitiveUserName\">"+nickName+"</font> : "+msg+"</td></tr>");
							}else{ // 右边
								sensitiveCenterTable.append("<tr><td class=\"sensitiveTextRight\">"+msg+" : <font class=\"sensitiveUserName\">"+nickName+"</font></td></tr>");
							}
						}
					}	
				},

				error:function(er){
			
					alert(er);
			
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
	</script>
	<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/operation.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
</head>
<body>
	<section>
		<div class="content clearfix">
			<jsp:include flush="true" page="../public/activity_left.jsp"></jsp:include>
			<div class="right-body" style="border:none;">
			   	<table id="sensitiveWordsTable" align="center" class="sensitive-words-table">
			   		<tr>
			   			<th>敏感词</th>
			   			<th>群组名称</th>
			   			<th>所属物业</th>
			   			<th>出现时间</th>
			   			<th>处理结果</th>
			   			<th>定位敏感词</th>
			   		</tr>
			   	</table>
			   	<div align="center" class="sensitiveMain" id="sensitiveMain" style="display: none;">
				   	<input type="button" value=" NEXT " onclick="getSensitiveWordsByStatus('next');" />
				   	<input type="button" value=" LAST " onclick="getSensitiveWordsByStatus('last');" />
			   		<div class="sensitiveTop" id="sensitiveTop"></div>
			   		<div class="sensitiveCenter" id="sensitiveCenter" >
			   			<table id="sensitiveCenterTable" align="center" width="80%">
			   			
			   			</table>
			   		</div>
			   	</div>
			</div>
		</div>
	</section>
</body>
</html>
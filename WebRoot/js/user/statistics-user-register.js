var registerNum=0;
var communityId=window.parent.document.getElementById("community_id_index").value;
function getUserTop() {
	var path = "/api/v1/communities/"+communityId+"/userStatistics/getUserRegister";
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			document.getElementById("zong_num").innerHTML = data.registerNum;
			document.getElementById("zong_num_test").innerHTML ="("+data.testNum+")";
			
			document.getElementById("zong_num_anzuang").innerHTML = data.installsNum-data.testNum;
			document.getElementById("zong_num_anzuang_test").innerHTML = "("+data.testNum+")";
			registerNum= data.registerNum;
			onSchedule();
		},
		error : function(er) {
			onSchedule();
		}
	});
	
}

function getUserTime(biao,installs,startTime,endTime) {
	var path = "/api/v1/communities/"+communityId+"/userStatistics/getUserRegisterTime?startTime="+startTime+"&endTime="+endTime;
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			document.getElementById(biao).innerHTML = data.registerNum;
			document.getElementById(biao+"_test").innerHTML = "("+data.testNum+")";
			document.getElementById(installs).innerHTML = data.installsNum-data.testNum;
			document.getElementById(installs+"_test").innerHTML = "("+data.testNum+")";
			onSchedule();
		},
		error : function(er) {
			onSchedule();
		//	alert("网络连接失败...");
		//	top.location='/'; 
		}
	});
}

function quickUserData(){
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + myDate.getDate()
			+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
	getUserTime('yesterday','to_day_installs',startTime,endTime);
	
}
function thisUserData(){
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() - 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + da
	+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" + myDate.getDate() + " 00:00:00";
	getUserTime('this_data','this_data_installs',startTime,endTime);
	
}

function thisMonthUser() {
	var myDate = new Date();
	var month = myDate.getMonth()+1;
	var month2 = myDate.getMonth()+2;
	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month2 + "-1" + " 00:00:00";
	getUserTime('this_month','this_month_installs',startTime,endTime);
}
function monthUser() {
	var myDate = new Date();
	var month = myDate.getMonth();
	var month2 = myDate.getMonth()+1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month2 + "-1" + " 00:00:00";
	getUserTime('last_month','last_month_installs',startTime,endTime);
}
function weekUser() {
	var d = getPreviousWeekStartEnd();
	var startTime = d.start;
	var endTime = d.end;
	getUserTime('last_week','last_week_installs',startTime,endTime);
}
function thisWeekUser() {
	
	var d = getWeekBenUp();
	var startTime = d.start;
	var endTime = d.end;
	getUserTime('this_week','this_week_installs',startTime,endTime);
}
function getTop() {
	thisUserData();
	thisWeekUser();
	thisMonthUser();
	getUserTop();
	quickUserData();
	weekUser() ;
	monthUser();
}
function quickShopData() {
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + myDate.getDate()
			+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
	
	getUserList( 1, startTime, endTime,'日');
}

function maintainMonth() {
	var myDate = new Date();
	var month = myDate.getMonth();
	var month2 = myDate.getMonth()+1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month2 + "-1" + " 00:00:00";
	getUserList( 1, startTime, endTime,'月');
}


function weekMonth(sort) {

	var d = getPreviousWeekStartEnd();
	var startTime = d.start;
	var endTime = d.end;
	getUserList( 1, startTime, endTime,'周');

}
function selectStatistics() {
	var sort = document.getElementById("master_repir_sort_fei").value;
	var startTime = document.getElementById("txtBeginDate").value + " 00:00:00";
	var endTime = document.getElementById("txtEndDate").value + " 00:00:00";
	getUserList( 1, startTime, endTime,'');
}


function timeClick(clickId) {
	if (clickId == "data_clik") {
		document.getElementById("data_clik").className = "select";
		var master_repir_sort = document
				.getElementById("master_repir_sort_fei").value;
		quickShopData(master_repir_sort);
	} else {
		document.getElementById("data_clik").className = "";
	}
	if (clickId == "week_clik") {
		document.getElementById("week_clik").className = "select";
		var master_repir_sort = document
				.getElementById("master_repir_sort_fei").value;
		weekMonth(master_repir_sort);
	} else {
		document.getElementById("week_clik").className = "";
	}
	if (clickId == "month_clik") {
		document.getElementById("month_clik").className = "select";
		var master_repir_sort = document
				.getElementById("master_repir_sort_fei").value;
		maintainMonth(master_repir_sort);
	} else {
		document.getElementById("month_clik").className = "";
	}
}
function getUserList( pageNum, startTime, endTime,type) {

	var path = "/api/v1/communities/"+communityId+"/userStatistics/getUserRegisterTimeList?pageSize=10&pageNum="
			+ pageNum
			
			+ "&startTime="
			+ startTime
			+ "&endTime=" + endTime;

	$
			.ajax({
				url : path,
				type : "GET",
				dataType : 'json',
				success : function(data) {
					data = data.info;

					var repair_overman = $("#statistics_list_id");
					repair_overman.empty();


					repair_overman
							.append("<tr class=\"odd\"><td>统计时间</td><td>安装用户数</td><td>注册用户数</td><td>趋势</td></tr>");
					var myDate = new Date();
					var month = myDate.getMonth() + 1;
					var da = myDate.getDate() - 1;
					var da2 = myDate.getDate() - 2;
					var startTime1 = myDate.getFullYear() + "-" + month + "-"
							+ da + " 00:00:00";
					var endTime1 = myDate.getFullYear() + "-" + month + "-"
							+ myDate.getDate() + " 00:00:00";
					var startTime2 = myDate.getFullYear() + "-" + month + "-"
							+ da2 + " 00:00:00";

					var startTime_day_long = stringToTime(startTime1) / 1000;
					var endTime_day_long = stringToTime(endTime1) / 1000;
					var startTime2_day_long = stringToTime(startTime2) / 1000;
					var orderPrice = document
							.getElementById("turnover_zong_get").value;
					
					
					
					var liTop = "";
					var liList = "";
					var addUser=0;
					var addUserTest=0;
					var anUser=0;
					var mapUserReg={};
					for ( var i = 0; i < data.length; i++) {
						mapUserReg[getStringTime(data[i].createTime * 1000)]=data[i];
					}
					var d=show(getStringTime(stringToTime(startTime)),getStringTime(stringToTime(endTime)));
					for ( var i = d.length-2; i >=0 ; i--) {
						var data2=mapUserReg[d[i]];
						if(data2==undefined){
							data2={
								"testNum":0,	
								"registerNum":0,	
								"installsNum":0,	
								"createTime":stringToTime(d[i])/1000
									
							};
							
						}
						var qu=100;
						if(i>0){
							var data3=mapUserReg[d[i-1]];
							if(data3==undefined){
								data3={
										"testNum":0,	
										"registerNum":0,	
										"installsNum":0
											
									};
								
							}
							if(data3.registerNum!=0&&((data2.registerNum)-(data3.registerNum))!=0){
								qu=((((data2.registerNum)-(data3.registerNum))/(data3.registerNum))*100);
							}
							
						}
						
						addUserTest+=data2.testNum;
						addUser+=data2.registerNum;
						anUser+=data2.installsNum-data2.testNum;

						if (i % 2 == 0) {
							liList += "<tr class=\"even\">";
						} else {

							liList += "<tr class=\"odd\">";
						}

						liList += "<td>";

						var myDate = new Date(data2.createTime * 1000);

						var startTimeFilter = myDate.getMonth() + 1 + "月"
								+ myDate.getDate() + "日";
						liList += startTimeFilter;
					
                        liList +="<td><span>"+(data2.installsNum-data2.testNum)+"</span><span class=\"navy\">("+data2.testNum+")</span></td>";
                        liList += "</td><td ><span>" + (data2.registerNum) + "</span></td>";
                    	liList +="<span style=\"color: green\">"+data2.testNum+"</span>";
						liList += "<td>"+qu.toFixed(2)+"%</td></tr>";
   
					}
					
//					for ( var i =(data.length-1); i >=0; i--) {
//						addUserTest+=data[i].testNum;
//						addUser+=data[i].registerNum;
//						anUser+=data[i].installsNum-data[i].testNum;
//						var qu=0;
//						if(i>0){
//							if(data[i-1].registerNum!=0&&((data[i].registerNum)-(data[i-1].registerNum))!=0){
//								qu=((((data[i].registerNum)-(data[i-1].registerNum))/(data[i-1].registerNum))*100);
//							}
//							
//						}
//
//						if (i % 2 == 0) {
//							liList += "<tr class=\"even\">";
//						} else {
//
//							liList += "<tr class=\"odd\">";
//						}
//
//						liList += "<td>";
//
//						var myDate = new Date(data[i].createTime * 1000);
//
//						var startTimeFilter = myDate.getMonth() + 1 + "月"
//								+ myDate.getDate() + "日";
//						liList += startTimeFilter;
//					
//                        liList +="<td><span>"+(data[i].installsNum-data[i].testNum)+"</span><span class=\"navy\">("+data[i].testNum+")</span></td>";
//                        liList += "</td><td ><span>" + (data[i].registerNum) + "</span></td>";
//                    	liList +="<span style=\"color: green\">"+data[i].testNum+"</span>";
//						liList += "<td>"+qu.toFixed(2)+"%</td></tr>";
//   
//						
//
//					}
					liTop+="<tr class=\"even\"><td>合计</td><td><span>"+anUser+"</span><span class=\"navy\">("+addUserTest+")</span></td><td >"+addUser+"</td><td>0</td></tr>";
					
					
					
					repair_overman.append(liTop);
					repair_overman.append(liList);
					document.getElementById("master_repir_startTime").value = startTime;
					document.getElementById("master_repir_endTime").value = endTime;
					
					if(type!=""){
						timeQuantum(startTime, endTime);

						timeDisplay(type);
						
					}else{
						
						timeQuantum(startTime, endTime);

					}
					onSchedule();
				},
				error : function(er) {
					onSchedule();
				//	alert("网络连接失败...");
				//	top.location='/'; 
				}
			});


}

function  userList(pageNum, startTime, endTime,type,registerNum) {
	
}


function selectData(tim) {
	var myDate = new Date(tim * 1000);
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + myDate.getDate()
			+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
	getUserList(1, startTime, endTime,"");
}

function alterMonth(num) {

	var startTime = document.getElementById("master_repir_startTime").value;
	var sort = document.getElementById("master_repir_sort_fei").value;
	var type = document.getElementById("date_type_get").value;
	var d = alterDate(num, type, startTime);
	var startTime = d.start;
	var endTime = d.end;
	getUserList(1,startTime, endTime,"");
}
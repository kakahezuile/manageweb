var registerNum=0;
var map={};
var mapTryOut =window.parent.mapTryOut;
var mapUser=window.parent.mapUser;
var communityId=window.parent.document.getElementById("community_id_index").value;
function tryOut() {
	 getTop();
	 quickShopData();
}


function getLaunches(biao,startTime,endTime) {
	schedule();
	var path = "/api/v1/communities/"+communityId+"/userStatistics/getLaunchesDetailsStatistics?startTime="+startTime+"&endTime="+endTime;
	
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			var userCount=0;
			var userClick;
			var testClick=0;
			for ( var i = 0; i < data.length; i++) {
				userClick=data[i].userClick;
			     for ( var j = 0; j < userClick.length; j++) {
					if(mapUser[userClick[j].emobId]=="1"){
						if(mapTryOut[userClick[j].emobId]!="1"){
							userCount+=userClick[j].click;
						}else{
							testClick+=userClick[j].click;
						}
					}
				 }
				//userCount+= data[i].total;
			}
			document.getElementById(biao).innerHTML = userCount;
			document.getElementById(biao+"_test").innerHTML = "("+testClick+")";
			onSchedule();
			
		},
		error : function(er) {
			onSchedule();
		//	alert("网络连接失败...");
		//	top.location='/'; 
		}
	});
}
function getLaunches(biao,startTime,endTime) {
	schedule();
	var path = "/api/v1/communities/"+communityId+"/userStatistics/getLaunchesDetailsStatistics?startTime="+startTime+"&endTime="+endTime;
	
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			var userCount=0;
			var userClick;
			var testClick=0;
			for ( var i = 0; i < data.length; i++) {
				userClick=data[i].userClick;
			     for ( var j = 0; j < userClick.length; j++) {
					if(mapUser[userClick[j].emobId]=="1"){
						if(mapTryOut[userClick[j].emobId]!="1"){
							userCount+=userClick[j].click;
						}else{
							testClick+=userClick[j].click;
						}
					}
				 }
				//userCount+= data[i].total;
			}
			document.getElementById(biao).innerHTML = userCount;
			document.getElementById(biao+"_test").innerHTML = "("+testClick+")";
			onSchedule();
			
		},
		error : function(er) {
			onSchedule();
		//	alert("网络连接失败...");
		//	top.location='/'; 
		}
	});
}
function getLaunchesList(startTime,endTime,type) {
	schedule();
	var path = "/api/v1/communities/"+communityId+"/userStatistics/getLaunchesDetailsStatistics?startTime="+startTime+"&endTime="+endTime;
	
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			
			var userTotalCount=0;
			var userTotalClick;
			var testTotalClick=0;
			var totalUser=0;
			var testTotalUser=0;
			var totalUserList= new Array();
			var testTotalUserList=new Array();
			for ( var i = 0; i < data.length; i++) {
				userTotalClick=data[i].userClick;
			     for ( var j = 0; j < userTotalClick.length; j++) {
					if(mapUser[userTotalClick[j].emobId]=="1"){
						if(mapTryOut[userTotalClick[j].emobId]!="1"){
							totalUserList[totalUser++]=userTotalClick[j].emobId;
							userTotalCount+=userTotalClick[j].click;
						}else{
							testTotalUserList[testTotalUser++]=userTotalClick[j].emobId;
							testTotalClick+=userTotalClick[j].click;
						}
					}
				 }
				//userCount+= data[i].total;
			}
			if(totalUserList.length>1){
				totalUserList=totalUserList.unique2();
			}
			if(testTotalUserList.length>1){
				testTotalUserList=testTotalUserList.unique2();
			}
			
			var userCount=0;

			var repair_overman = $("#statistics_list_id");
			repair_overman.empty();
			repair_overman
					.append("<tr class=\"odd\"><td>统计时间</td><td>用户访问主界面次数</td><td>工作人员访问主界面次数</td><td>详情</td></tr>");
			
			var liTop="";
			var liList="";
			var userSumNum=0;
			var testUserSumNum=0;
			var userClickNum=0;
			var testClickNum=0;
			var mapDate={};
			var arrSimple=new Array();
			for(var i=0;i< data.length;i++){
				mapDate[data[i]._id.time]=data[i];
				arrSimple[i]=data[i]._id.time;
			}
			arrSimple.sort();
			for ( var i = arrSimple.length-1; i>=0; i--) {
				var data2=mapDate[arrSimple[i]];
				var userNum=0;
				var testUserNum=0;
				var userClick=data2.userClick;
				map[data2._id.time]=userClick;
				var testClick=0;
				var total=0;
				for ( var j = 0; j < userClick.length; j++) {
					if(mapUser[userClick[j].emobId]=="1"){
						if(mapTryOut[userClick[j].emobId]!="1"){
							userNum++;
							total+=userClick[j].click;
						}else{
							testUserNum++;
						    testClick+=userClick[j].click;
						}
					}
				}
				
				

				if (i % 2 == 0) {
					liList += "<tr class=\"even\">";
				} else {
					liList += "<tr class=\"odd\">";
				}
				userSumNum+=userNum;
				testUserSumNum+=testUserNum;
				liList += "<td>"+getStringTime(data2._id.time*24*60*60*1000)+"</td><td>" + total+"("+userNum+")</td><td class=\"greenword\">"+testClick+"("+testUserNum+")</td>";
                liList +="<td> <a  onclick=launchesDetails("+data2._id.time+");>详情</a></td></tr>";
                userClickNum+=total;
                testClickNum+=testClick;
                
			//	userCount+= data[i].total;
				
			}
			liTop+="<tr class=\"even\"><td>合计</td><td>"+userTotalCount+"("+totalUserList.length+")</td><td class=\"greenword\">"+testTotalClick+"("+testTotalUserList.length+")</td><td></td></tr>";
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

function launchesDetails(time) {
	schedule();
    var userList=map[time];
	var map2={};
	var map3={};
    var path = "/api/v1/communities/"+communityId+"/userStatistics/getUserList";
	
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
		    var dataUser=	data.listUsers;
		    var dataOut=	data.listTryOut;
			for ( var i = 0; i < dataUser.length; i++) {
				map2[dataUser[i].emobId]=dataUser[i];
			}
			for ( var i = 0; i < dataOut.length; i++) {
				map3[dataOut[i].emobId]="1";
			}
			var repair_overman = $("#statistics_list_id");
			repair_overman.empty();
			repair_overman
					.append("<tr class=\"odd\"><td>用户昵称</td><td>电话号码</td><td>访问主界面次数</td></tr>");
			var liTop="";
			var liList="";
		
			for ( var i = 0; i < userList.length; i++) {
				
				if(map2[userList[i].emobId]==undefined){
					
					continue;
				}
				if (i % 2 == 0) {
					liList += "<tr class=\"even\">";
				} else {

					liList += "<tr class=\"odd\">";
				}
				if(map3[userList[i].emobId]==1){
					liList += "<td > <b class=\"greenword\">"+map2[userList[i].emobId].nickname+"</b></td><td> <b class=\"greenword\">"+map2[userList[i].emobId].username+"</b></td>";
				}else{
					
					if(map2[userList[i].emobId].username==""||map2[userList[i].emobId].username=="null"){
						liList += "<td >游客</td><td >游客</td>";
						
					}else{
						liList += "<td >"+map2[userList[i].emobId].nickname+"</td><td >"+map2[userList[i].emobId].username+"</td>";
					}
					
					
				}
				
                liList +="<td>"+userList[i].click+"</td></tr>";
			}
			
			repair_overman.append(liList);
			onSchedule();
			
		},
		error : function(er) {
			onSchedule();
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
	getLaunches('yesterday',startTime,endTime);
	
}
function thisUserData(){
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate()-1;
	var startTime = myDate.getFullYear() + "-" + month + "-" +da 
	+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" + myDate.getDate() + " 00:00:00";
	getLaunches('this_data',startTime,endTime);
	
}

function monthUser() {
	var myDate = new Date();
	var month = myDate.getMonth();
	var month2 = myDate.getMonth()+1;


	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month2 + "-1" + " 00:00:00";
	getLaunches('last_month',startTime,endTime);
}
function thisMonthUser() {
	var myDate = new Date();
	var month = myDate.getMonth()+1;
	var month2 = myDate.getMonth()+2;
	
	
	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month2 + "-1" + " 00:00:00";
	getLaunches('this_month',startTime,endTime);
}
function weekUser() {

	var d = getPreviousWeekStartEnd();
	var startTime = d.start;
	var endTime = d.end;
	getLaunches('last_week',startTime,endTime);

}
function thisWeekUser() {
	
	var d = getWeekBenUp();
	var startTime = d.start;
	var endTime = d.end;
	getLaunches('this_week',startTime,endTime);
	
}

function totalUser() {
	var myDate = new Date();
	var month = myDate.getMonth()+1;
	var da=myDate.getDate()+1;
	var startTime="2015-06-01 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-"+ da + " 00:00:00";
	
	getLaunches('total_id',startTime,endTime);
	
}
function getTop() {
	totalUser();
	thisUserData();
	thisMonthUser();
	thisWeekUser();
	quickUserData();
	weekUser();
	monthUser();
	
}
function quickShopData() {
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + myDate.getDate()
			+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
	
	getLaunchesList(  startTime, endTime,'日');
}

function maintainMonth() {
	var myDate = new Date();
	var month = myDate.getMonth();
	var month2 = myDate.getMonth()+1;


	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month2 + "-1" + " 00:00:00";
	getLaunchesList( startTime, endTime,'月');

}


function weekMonth(sort) {

	var d = getPreviousWeekStartEnd();
	var startTime = d.start;
	var endTime = d.end;
	getLaunchesList(  startTime, endTime,'周');

}
function selectStatistics() {
	var sort = document.getElementById("master_repir_sort_fei").value;
	var startTime = document.getElementById("txtBeginDate").value + " 00:00:00";
	var endTime = document.getElementById("txtEndDate").value + " 00:00:00";
	getLaunchesList(startTime, endTime,'');

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

function  userList(pageNum, startTime, endTime,type,registerNum) {
	
}


function selectData(tim) {
	var myDate = new Date(tim * 1000);
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + myDate.getDate()
			+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
	getLaunchesList(startTime, endTime,"");
}

function alterMonth(num) {

	var startTime = document.getElementById("master_repir_startTime").value;
	var sort = document.getElementById("master_repir_sort_fei").value;
	var type = document.getElementById("date_type_get").value;
	var d = alterDate(num, type, startTime);
	var startTime = d.start;
	var endTime = d.end;
	getLaunchesList(startTime, endTime,"");
}



function getShopList() {
	schedule();
	  var path = "/api/v1/communities/"+communityId+"/userStatistics/getUserList";
		
		$.ajax({
			url : path,
			type : "GET",
			dataType : 'json',
			success : function(data) {
				data = data.info;
				data=data.listUsers;
				for ( var i = 0; i < data.length; i++) {
					mapUser[data[i].emobId]="1";
				}
				
				  getTop();
				  quickShopData();
			},
			error : function(er) {
				top.location='/'; 
			}
		});
		
}
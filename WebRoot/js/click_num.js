var map = {};
var mapTryOut = {};
var mapUser = {};
var clickNumMap={};
var clickNumTime={};
var clickObj={};
var communityId=document.getElementById("community_id_index").value;
var clickZhong=0;
var mapActiveUser={};
var mapRegisterList={};
var mapActive={};
var isActive=0;
function getTryOut() {
	var path = "/api/v1/communities/" + communityId
			+ "/userStatistics/getUserList";
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			var tryOut = data.listTryOut;
			var listUsers = data.listUsers;
			var registerList = data.registerList;
			for ( var i = 0; i < tryOut.length; i++) {
				mapTryOut[tryOut[i].emobId] = "1";
			}
			for ( var i = 0; i < listUsers.length; i++) {
				mapUser[listUsers[i].emobId]="1";
			}
			for(var i = 0; i < registerList.length; i++){
				mapRegisterList[registerList[i].emobId]="1";
			}
			totalUserNum();
		},
		error : function(er) {
		}
	});
}

function getClickAmountTop(biao,sort, startTime, endTime) {
	var start = stringToTime(startTime) / 1000;
	var end = stringToTime(endTime) / 1000;
	var time = 0;
	var path = "/api/v1/communities/"
			+ communityId
			+ "/users/1/orderHistories/getClickAmount?url=http://115.28.73.37:9090/api/V1/communities/"
			+ communityId + "/modules/statistics/" + start+ "/" + (end-1);
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			var info;
			var click = 0;
			var clickNum = 0;
			var clickTime=0;
			
			for ( var i = 0; i < data.length; i++) {
				
				info = data[i].info;

				for ( var j = 0; j < info.length; j++) {
					var clickList=info[j].userClick;
					if(info[j].serviceId=="0"){
						if(info[j].serviceName!="通知公告"){
							continue;
						}
					}
					
					if(info[j].serviceId=="17"){
						continue;
					}
				
					for ( var k = 0; k < clickList.length; k++) {
						if(mapTryOut[clickList[k].emobId] == "1"){
							
						}else if(mapUser[clickList[k].emobId]=="1"){
							    clickTime+= clickList[k].click;
								clickNum += clickList[k].click;
								clickZhong += clickList[k].click;
						}
					}
				}
				var time = getStringTime(data[i]._id.time * 24 * 60 * 60 * 1000);
				
				clickNumTime[time]=clickTime;
				clickTime=0;
			}
			clickNumMap[biao]=clickNum;
			
			getTop();
			
		},
		error : function(er) {
		}
	});
}

function  getClick(biao,sort, startTime, endTime,clickNum) {
	var start = stringToTime(startTime) / 1000;
	var end = stringToTime(endTime) / 1000;
	
	
	
	var time = 0;
	var path = "/api/v1/communities/"
		+ communityId
		+ "/users/1/orderHistories/getClickAmount?url=http://115.28.73.37:9090/api/V1/communities/"
		+ communityId + "/events_details_daily/statistics/"+sort+"/" + start
		+ "/" + (end - 1);
	
	//alert(path);
       $.ajax({
			url : path,
			type : "GET",
			dataType : 'json',
			success : function(data) {
				data = data.info;

				var info;
				var click = 0;
				var testClick = 0;
				var clickUser = 0;
				var clickUserList = new Array();
				var testClickUser = 0;
				var testClickUserList = new Array();
				var total=0;
				// info = data.userClick;
				for ( var i = 0; i < data.length; i++) {
					var clickList = data[i].userClick;
					total+=data[i].total;
					for ( var j = 0; j < clickList.length; j++) {
					
						if (mapTryOut[clickList[j].emobId] == "1") {
							testClickUserList[testClickUser++] = clickList[j].emobId;
							testClick += clickList[j].click;
							// alert(clickList[j].emobId);
						} else {
							if(mapUser[clickList[j].emobId]=="1"){
								clickUserList[clickUser++] =clickList[j].emobId ;
								click += clickList[j].click;
							}
						
						}

					}
				}
				
				// alert(testClickUserList.unique2().length);
				if(testClickUserList.length>1){
					 testClickUserList=testClickUserList.unique2();
					// testClickUserList= unique4(testClickUserList);
				}
				if(clickUserList.length>1){
					 clickUserList=clickUserList.unique2();
//						 clickUserList= unique4(clickUserList);
				}
				//var clickNum=clickNumMap[biao];
				var clickBi=0;
				if(clickNum>0){
					clickBi=(click/clickNum)*100;
				}
				var clickUser=clickUserList.length;
				if(clickUser==0){
					//clickUser=total+"]";
					click=0;
					clickUser=0;
				}
				
				
				var clickObc={
						"total":total,
						"click":click,
						"clickUser":clickUser,
						"testClick":testClick,
						"clickUserList":clickUserList,
						"testClickUser":testClickUserList.length,
						"clickRatio":clickBi.toFixed(2),
						"clickNum":clickNum
				};
				
				clickObj[biao+sort]=clickObc;
				if(sort!=17){
					isClick++;
				}
				
			},
			error : function(er) {
				onSchedule();
			}
		});
}
function  getClick2(biao,sort, startTime, endTime,clickNum) {
	var start = stringToTime(startTime) / 1000;
	var end = stringToTime(endTime) / 1000;
	var time = 0;
	var path = "/api/v1/communities/"
		+ communityId
		+ "/users/1/orderHistories/getClickAmount?url=http://115.28.73.37:9090/api/V1/communities/"
		+ communityId + "/events_details_daily/statistics/"+sort+"/" + start
		+ "/" + (end - 1);
	
	//alert(path);
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			
			var info;
			var click = 0;
			var testClick = 0;
			var clickUser = 0;
			var clickUserList = new Array();
			var testClickUser = 0;
			var testClickUserList = new Array();
			var total=0;
			// info = data.userClick;
			for ( var i = 0; i < data.length; i++) {
				var clickList = data[i].userClick;
				total+=data[i].total;
				for ( var j = 0; j < clickList.length; j++) {
					
					if (mapTryOut[clickList[j].emobId] == "1") {
						testClickUserList[testClickUser++] = clickList[j].emobId;
						testClick += clickList[j].click;
						// alert(clickList[j].emobId);
					} else {
						if(mapUser[clickList[j].emobId]=="1"){
							clickUserList[clickUser++] =clickList[j].emobId ;
							click += clickList[j].click;
						}
						
					}
					
				}
			}
			
			// alert(testClickUserList.unique2().length);
			if(testClickUserList.length>1){
				testClickUserList=testClickUserList.unique2();
//				testClickUserList=unique4(testClickUserList);
			}
			if(clickUserList.length>1){
				clickUserList=clickUserList.unique2();
//				clickUserList=unique4(clickUserList);
			}
			//var clickNum=clickNumMap[biao];
			var clickBi=0;
			if(clickNum>0){
				clickBi=(click/clickNum)*100;
			}
			var clickUser=clickUserList.length;
			if(clickUser==0){
				//clickUser=total+"]";
				click=0;
				clickUser=0;
			}
			
			
			var clickObc={
					"total":total,
					"click":click,
					"clickUser":clickUser,
					"testClick":testClick,
					"testClickUser":testClickUserList.length,
					"clickRatio":clickBi.toFixed(2),
					"clickNum":clickNum
			};
			
			clickObj[biao+sort]=clickObc;
			isClick++;
		},
		error : function(er) {
			onSchedule();
		}
	});
}
function totalUserNum() {
	var myDate = new Date();
	var month = myDate.getMonth()+1;
	var da=myDate.getDate()+1;
	var startTime="2015-06-01 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-"+ da + " 00:00:00";
	getClickAmountTop('total_id',1,startTime,endTime);
	
}










function thisUserData(){
	var myDate = new Date();
	var toDate=new Date(myDate.getTime()-(24*60*60*1000));
	var toMonth=toDate.getMonth()+1;
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() -1;
	var startTime = toDate.getFullYear() + "-" + toMonth + "-" + toDate.getDate() 
			+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" +myDate.getDate() + " 00:00:00";
	

	var clickTime= toDate.getFullYear() + "-" + toMonth + "-" + toDate.getDate();
	var clickNum=0;
	if(clickNumTime[clickTime]!=undefined){
		clickNum=clickNumTime[clickTime];
	}
	getClick('to_day',1,startTime,endTime,clickNum);
	getClick('to_day',3,startTime,endTime,clickNum);
	getClick('to_day',6,startTime,endTime,clickNum);
	getClick('to_day',7,startTime,endTime,clickNum);
	getClick('to_day',8,startTime,endTime,clickNum);
	getClick('to_day',9,startTime,endTime,clickNum);
	getClick('to_day',10,startTime,endTime,clickNum);
	getClick('to_day',16,startTime,endTime,clickNum);
	getClick('to_day',0,startTime,endTime,clickNum);
	getClick('to_day',17,startTime,endTime,clickNum);
	
}
function toDayUserData(){
	var myDate = new Date();
	var toDate=new Date(myDate.getTime()+(24*60*60*1000));
	var toMonth=toDate.getMonth()+1;
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" +  myDate.getDate()
	+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + toMonth + "-" +toDate.getDate() + " 00:00:00";
	var clickTime= myDate.getFullYear() + "-" + month + "-" + myDate.getDate();
	var clickNum=0;
	if(clickNumTime[clickTime]!=undefined){
		clickNum=clickNumTime[clickTime];
	}
	getClick('this_data',1,startTime,endTime,clickNum);
	getClick('this_data',3,startTime,endTime,clickNum);
	getClick('this_data',6,startTime,endTime,clickNum);
	getClick('this_data',7,startTime,endTime,clickNum);
	getClick('this_data',8,startTime,endTime,clickNum);
	getClick('this_data',9,startTime,endTime,clickNum);
	getClick('this_data',10,startTime,endTime,clickNum);
	getClick('this_data',16,startTime,endTime,clickNum);
	getClick('this_data',0,startTime,endTime,clickNum);
	getClick('this_data',17,startTime,endTime,clickNum);
	
}

function thisMonthUser() {
	var myDate = new Date();
	var month = myDate.getMonth()+1;
	var month2 = myDate.getMonth()+2;
	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month2 + "-1" + " 00:00:00";
	
	var d=show(getStringTime(stringToTime(startTime)),getStringTime(stringToTime(endTime)));
	var clickNum=0;
	for ( var i = 0; i < d.length-1; i++) {
		if(clickNumTime[d[i]]!=undefined){
			clickNum+=clickNumTime[d[i]];
		}
	}
	getClick('this_month',1,startTime,endTime,clickNum);
	getClick('this_month',3,startTime,endTime,clickNum);
	getClick('this_month',6,startTime,endTime,clickNum);
	getClick('this_month',7,startTime,endTime,clickNum);
	getClick('this_month',8,startTime,endTime,clickNum);
	getClick('this_month',9,startTime,endTime,clickNum);
	getClick('this_month',10,startTime,endTime,clickNum);
	getClick('this_month',16,startTime,endTime,clickNum);
	getClick('this_month',0,startTime,endTime,clickNum);
	getClick('this_month',17,startTime,endTime,clickNum);
}
function monthUser() {
	var myDate = new Date();
	var month = myDate.getMonth();
	var month2 = myDate.getMonth()+1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month2 + "-1" + " 00:00:00";
	var d=show(getStringTime(stringToTime(startTime)),getStringTime(stringToTime(endTime)));
	var clickNum=0;
	for ( var i = 0; i < d.length-1; i++) {
		if(clickNumTime[d[i]]!=undefined){
			clickNum+=clickNumTime[d[i]];
		}
		
	}
	getClick('last_month',1,startTime,endTime,clickNum);
	getClick('last_month',3,startTime,endTime,clickNum);
	getClick('last_month',6,startTime,endTime,clickNum);
	getClick('last_month',7,startTime,endTime,clickNum);
	getClick('last_month',8,startTime,endTime,clickNum);
	getClick('last_month',9,startTime,endTime,clickNum);
	getClick('last_month',10,startTime,endTime,clickNum);
	getClick('last_month',16,startTime,endTime,clickNum);
	getClick('last_month',0,startTime,endTime,clickNum);
	getClick('last_month',17,startTime,endTime,clickNum);
}
function weekUser() {
	var dd = getPreviousWeekStartEnd();
	var startTime = dd.start;
	var endTime = dd.end;
	
	var d=show(getStringTime(stringToTime(startTime)),getStringTime(stringToTime(endTime)));
	var clickNum=0;
	for ( var i = 0; i < d.length-1; i++) {
		if(clickNumTime[d[i]]!=undefined){
			clickNum+=clickNumTime[d[i]];
		}
	}
	getClick('last_week',1,startTime,endTime,clickNum);
	getClick('last_week',3,startTime,endTime,clickNum);
	getClick('last_week',6,startTime,endTime,clickNum);
	getClick('last_week',7,startTime,endTime,clickNum);
	getClick('last_week',8,startTime,endTime,clickNum);
	getClick('last_week',9,startTime,endTime,clickNum);
	getClick('last_week',10,startTime,endTime,clickNum);
	getClick('last_week',16,startTime,endTime,clickNum);
	getClick('last_week',0,startTime,endTime,clickNum);
	getClick('last_week',17,startTime,endTime,clickNum);
}
function thisWeekUser() {
	
	var dd = getWeekBenUp();
	var startTime = dd.start;
	var endTime = dd.end;
	
	var d=show(getStringTime(stringToTime(startTime)),getStringTime(stringToTime(endTime)));
	var clickNum=0;
	for ( var i = 0; i < d.length-1; i++) {
		if(clickNumTime[d[i]]!=undefined){
			clickNum+=clickNumTime[d[i]];
		}
	}
	getClick('this_week',1,startTime,endTime,clickNum);
	getClick('this_week',3,startTime,endTime,clickNum);
	getClick('this_week',6,startTime,endTime,clickNum);
	getClick('this_week',7,startTime,endTime,clickNum);
	getClick('this_week',8,startTime,endTime,clickNum);
	getClick('this_week',9,startTime,endTime,clickNum);
	getClick('this_week',10,startTime,endTime,clickNum);
	getClick('this_week',16,startTime,endTime,clickNum);
	getClick('this_week',0,startTime,endTime,clickNum);
	getClick('this_week',17,startTime,endTime,clickNum);
}
function totalUser() {
	var myDate = new Date();
	var month = myDate.getMonth()+1;
	var da=myDate.getDate()+1;
	var startTime="2015-06-01 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-"+ da + " 00:00:00";
	var clickNum=clickZhong;
	getClick('total_id',1,startTime,endTime,clickNum);
	getClick('total_id',3,startTime,endTime,clickNum);
	getClick('total_id',6,startTime,endTime,clickNum);
	getClick('total_id',7,startTime,endTime,clickNum);
	getClick('total_id',8,startTime,endTime,clickNum);
	getClick('total_id',9,startTime,endTime,clickNum);
	getClick('total_id',10,startTime,endTime,clickNum);
	getClick('total_id',16,startTime,endTime,clickNum);
	getClick('total_id',0,startTime,endTime,clickNum);
	getClick('total_id',17,startTime,endTime,clickNum);
	
}

function getTop() {
	toDayUserData();
	thisUserData();
	thisWeekUser();
	thisMonthUser();
	//getUserTop();
//	quickUserData();
	weekUser() ;
	monthUser();
	totalUser();
}


function thisClickNum() {
	var myDate=new Date();
	var month=myDate.getMonth()+1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" +myDate.getDate()   
	+ " 00:00:00";
    var endTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
    var start = stringToTime(startTime) / 1000;
    var end = stringToTime(endTime) / 1000;
	
	var path = "/api/v1/communities/"
		+ communityId
		+ "/users/1/orderHistories/getClickAmount?url=http://115.28.73.37:9090/api/V1/communities/"
		+ communityId + "/modules/statistics/" + start+ "/" + (end-1);
$.ajax({
	url : path,
	type : "GET",
	dataType : 'json',
	success : function(data) {
		data = data.info;
		var info;
		var click = 0;
		var clickNum = 0;
		var clickTime=0;
		for ( var i = 0; i < data.length; i++) {
			
			info = data[i].info;

			for ( var j = 0; j < info.length; j++) {
				var clickList=info[j].userClick;
				if(info[j].serviceId=="0"){
					if(info[j].serviceName!="通知公告"){
						continue;
					}
				}
			
				for ( var k = 0; k < clickList.length; k++) {
					if(mapTryOut[clickList[k].emobId] == "1"){
						
						
					}else if(mapUser[clickList[k].emobId]=="1"){
						clickTime+= clickList[k].click;
						clickNum += clickList[k].click;
						clickZhong += clickList[k].click;
					}
				}
				
			}
			
		}
		return clickNum;
	},
	error : function(er) {
	}
});
}


function  thisClick() {
	var start = stringToTime(startTime) / 1000;
	var end = stringToTime(endTime) / 1000;
	var time = 0;
	var path = "/api/v1/communities/"
		+ communityId
		+ "/users/1/orderHistories/getClickAmount?url=http://115.28.73.37:9090/api/V1/communities/"
		+ communityId + "/events_details_daily/statistics/"+sort+"/" + start
		+ "/" + (end - 1);
       $.ajax({
			url : path,
			type : "GET",
			dataType : 'json',
			success : function(data) {
				data = data.info;

				var info;
				var click = 0;
				var testClick = 0;
				var clickUser = 0;
				var clickUserList = new Array();
				var testClickUser = 0;
				var testClickUserList = new Array();
				var total=0;
				// info = data.userClick;
				for ( var i = 0; i < data.length; i++) {
					var clickList = data[i].userClick;
					total+=data[i].total;
					for ( var j = 0; j < clickList.length; j++) {
					
						if (mapTryOut[clickList[j].emobId] == "1") {
							testClickUserList[testClickUser++] = clickList[j].emobId;
							testClick += clickList[j].click;
							// alert(clickList[j].emobId);
						} else {
							if(mapUser[clickList[j].emobId]=="1"){
								clickUserList[clickUser++] =clickList[j].emobId ;
								click += clickList[j].click;
							}
						
						}

					}
				}
				
				// alert(testClickUserList.unique2().length);
				if(testClickUserList.length>1){
					 testClickUserList=testClickUserList.unique2();
//					 testClickUserList=unique4(testClickUserList);
				}
				if(clickUserList.length>1){
					 clickUserList=clickUserList.unique2();
				}
				//var clickNum=clickNumMap[biao];
				var clickBi=0;
				if(clickNum>0){
					clickBi=(click/clickNum)*100;
				}
				var clickUser=clickUserList.length;
				if(clickUser==0){
					total="["+total+"]";
					click=0;
					clickUser=0;
					total=0;
				}
				
				
				var clickObc={
						"total":total,
						"click":click,
						"clickUser":clickUser,
						"testClick":testClick,
						"testClickUser":testClickUserList.length,
						"clickRatio":clickBi.toFixed(2)
						
				};
				
				return clickObc;
			},
			error : function(er) {
				onSchedule();
			}
		});
}
var getMonthWeek = function (a, b, c) {
	/*
	a = d = 当前日期
	b = 6 - w = 当前周的还有几天过完（不算今天）
	a + b 的和在除以7 就是当天是当前月份的第几周
	*/
	var date = new Date(a, parseInt(b) - 1, c), w = date.getDay(), d = date.getDate();
	return Math.ceil(
	(d + 6 - w) / 7
	);
	};

function getStringTime(time) {
	var myDate=new Date(time);
	var month=myDate.getMonth()+1;
	return myDate.getFullYear()+"-"+month+"-"+myDate.getDate();
}
		
		

function stringToTime(string) {
	var f = string.split(' ', 2);
	var d = (f[0] ? f[0] : '').split('-', 3);
	var t = (f[1] ? f[1] : '').split(':', 3);
	return (new Date(parseInt(d[0], 10) || null, (parseInt(d[1], 10) || 1) - 1,
			parseInt(d[2], 10) || null, parseInt(t[0], 10) || null, parseInt(
					t[1], 10)
					|| null, parseInt(t[2], 10) || null)).getTime();

}
function getPreviousWeekStartEnd(date) {

	var date = new Date() || date, day, start, end, dayMSec = 24 * 3600 * 1000;

	
	today = date.getDay() - 1;
	if(today ==-1){
		end = date.getTime() - 6* dayMSec;
	}else{
		
		end = date.getTime() - today* dayMSec;
	}
	
	start = end - 7 * dayMSec;
	return {
		start : getFormatTime(start),
		end : getFormatTime(end)
	};

	function getFormatTime(time) {

		date.setTime(time);

		return date.getFullYear() + '-'
				+ ('0' + (date.getMonth() + 1)).slice(-2) + '-'
				+ ('0' + date.getDate()).slice(-2) + ' 00:00:00';

	}

}


/**
 * 本周
 * @param time
 * @returns {___anonymous242_307}
 */
function getWeekBenUp(date) {
	
	var date = new Date() || date, day, start, end, dayMSec = 24 * 3600 * 1000;
	
	today = date.getDay() - 1;
	
	if(today==-1){
		
		start = date.getTime() - 6 * dayMSec;
	}else{
		
		start = date.getTime() - today * dayMSec;
	}
		
	
	
	end = start + 7 * dayMSec;
	
	return {
		start : getFormatTime(start),
		end : getFormatTime(end)
	};
	
	function getFormatTime(time) {
		
		date.setTime(time);
		
		return date.getFullYear() + '-'
		+ ('0' + (date.getMonth() + 1)).slice(-2) + '-'
		+ ('0' + date.getDate()).slice(-2) + ' 00:00:00';
		
	}
	
}
/**
 * 上周
 * @param time
 * @returns {___anonymous242_307}
 */
function getWeekUp(time) {
	
	var date = new Date(time) || date, day, start, end, dayMSec = 24 * 3600 * 1000;
	
	

	
	return {
		start : getFormatTime(start),
		end : getFormatTime(end)
	};
	
	function getFormatTime(time) {
		
		date.setTime(time);
		
		return date.getFullYear() + '-'
		+ ('0' + (date.getMonth() + 1)).slice(-2) + '-'
		+ ('0' + date.getDate()).slice(-2) + ' 00:00:00';
		
	}
	
}


 /**
  *  下周
  * @param time
  * @returns {___anonymous793_858}
  */
function getNextwWeek(time) {
	
	var date = new Date(time) || date, day, start, end, dayMSec = 24 * 3600 * 1000;
	
	
	
	today = date.getDay() - 1;
	
	end = date.getTime() - today * dayMSec;
	
	start = end +7 * dayMSec;
	end=start +7 * dayMSec;
	return {
		start : getFormatTime(start),
		end : getFormatTime(end)
	};
	
	function getFormatTime(time) {
		
		date.setTime(time);
		
		return date.getFullYear() + '-'
		+ ('0' + (date.getMonth() + 1)).slice(-2) + '-'
		+ ('0' + date.getDate()).slice(-2) + ' 00:00:00';
		
	}
	
}
function toStringTime(time) {
	var myDate=new Date(time);
	return myDate.getHours()+":"+myDate.getMinutes()+":"+myDate.getSeconds();
}


function toStringTimeHs(time) {
	var myDate=new Date(time);
	var month=myDate.getMonth()+1;
	return  myDate.getFullYear()+"-"+month+"-"+myDate.getDate() +" "+myDate.getHours()+":"+myDate.getMinutes()+":"+myDate.getSeconds();
}
Array.prototype.unique5 = function(){
	 this.sort(function (a, b) {
         return a-b;
     }); //先排序
	 var res = [this[0]];
	 for(var i = 1; i < this.length; i++){
	  if(this[i] !== res[res.length - 1]){
	   res.push(this[i]);
	  }
	 }
	 return res;
};

Array.prototype.unique2 = function(){
	 var res = [];
	 var json = {};
	 for(var i = 0; i < this.length; i++){
	  if(!json[this[i]]){
	   res.push(this[i]);
	   json[this[i]] = 1;
	  }
	 }
	 return res;
};


function show(startTime, endTime) {
	
	
	
	var getDate = function(str) {
		var tempDate = new Date();
		var list = str.split("-");
		tempDate.setFullYear(list[0]);
		tempDate.setMonth(list[1] - 1);
		tempDate.setDate(list[2]);
		return tempDate;
	};
	var date1 = getDate(startTime);
	var date2 = getDate(endTime);
	if (date1 > date2) {
		var tempDate = date1;
		date1 = date2;
		date2 = tempDate;
	}
	date1.setDate(date1.getDate() + 1);
	var dates = [startTime],
		id = 1;
	while (!(date1.getFullYear() == date2.getFullYear() && date1.getMonth() == date2.getMonth() && date1.getDate() == date2.getDate())) {
		dates[id] = date1.getFullYear() + "-" + (date1.getMonth() + 1) + "-" + date1.getDate();
		id++;
		
		if(date1.getDate()==31){
			break;
		}
		date1.setDate(date1.getDate() + 1);
	}
	dates[dates.length] = endTime;
	return dates;
};


function unique4(list) {
	var mapLi={};
	var listM=new Array();
	var i=0;
	for ( var i = 0; i < list.length; i++) {
		mapLi[list[i]]=true;
	}
	for ( var key in mapLi) {
		listM[i++]=key;
	}
	return listM;
}




function  getUserRegisterTime(biao,startTime,endTime) {
	var path = "/api/v1/communities/"+communityId+"/userStatistics/getUserRegisterEndTime?endTime="+endTime;
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			
		//	alert(data.installsNum-data.testNum);
			active(biao,startTime,endTime,data.installsNum-data.testNum);
		},
		error : function(er) {
			//alert("网络连接失败...");
			//top.location='/'; 
		}
	});
}


function active(biao,startTime,endTime,registerNum) {
    var myDate = new Date(stringToTime(startTime));
	var myDate2 = new Date(stringToTime(endTime));
	var dates = Math.abs((myDate2 - myDate)) / (1000 * 60 * 60 * 24);
	
    var path = "/api/v1/communities/"+communityId+"/userStatistics/getLaunchesStatistics?startTime="+startTime+"&endTime="+endTime;
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			var total=0;
			var testTotal=0;
			for ( var i = 0; i < data.length; i++) {
				
				if(mapUser[data[i].userClick.emobId]=="1"){
					if(mapUser[data[i].userClick.emobId]=="1"){
						if(mapTryOut[data[i].userClick.emobId]!="1"){
							total++;
						}else{
							testTotal++;
						}
					}
				}
			}
		//	alert(biao);
			document.getElementById(biao).innerHTML = total;
			var top_user = 0;
			if(registerNum>0){
				top_user = (((total /registerNum)) * 100).toFixed(2);
			}
		
			document.getElementById(biao+"_xiao").innerHTML = top_user+"%";
			document.getElementById(biao+"_test").innerHTML ="("+ testTotal+")";
		},
		error : function(er) {
		//	alert("网络连接失败...");
		//	top.location='/'; 
		}
	});
}
function getTryOut2() {
	
	if(isClick==63){
		
		var list1=new Array();
		var list2=new Array();
		var list3=new Array();
		var list4=new Array();
		var list5=new Array();
		var list6=new Array();
		var list7=new Array();
		mapActive["this_data"]=list1;
		mapActive["to_day"]=list2;
		mapActive["this_week"]=list3;
		mapActive["last_week"]=list4;
		mapActive["this_month"]=list5;
		mapActive["last_month"]=list6;
		thisClickNum2();
			
	}else{
		setTimeout("getTryOut2()", 2000);
		 //setInterval("alert(100)",5000);
	}
	
}

function thisClickNum2() {
	var myDate=new Date();
	var month=myDate.getMonth()+1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" +myDate.getDate()   
	+ " 00:00:00";
    var endTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
    var start = stringToTime(startTime) / 1000;
    var end = stringToTime(endTime) / 1000;
	
	var path = "/api/v1/communities/"
		+ communityId
		+ "/users/1/orderHistories/getClickAmount?url=http://115.28.73.37:9090/api/V1/communities/"
		+ communityId + "/modules/statistics/"+start+"/" + (end-1);
$.ajax({
	url : path,
	type : "GET",
	dataType : 'json',
	success : function(data) {
		data = data.info;
		
		var mapObjHe={};
		var info;
		
		var clickTotal= 0;
		var clickNumTotal = 0;
		var clickTimeTotal=0;
		for ( var i = 0; i < data.length; i++) {
		
			info = data[i].info;

			for ( var j = 0; j < info.length; j++) {
				var click = 0;
				var testClick = 0;
				var clickUser = 0;
				var clickUserList = new Array();
				var testClickUser = 0;
				var testClickUserList = new Array();
				var total=0;
				var clickList=info[j].userClick;
				if(info[j].serviceId=="0"){
					if(info[j].serviceName!="通知公告"){
						continue;
					}
				}
				for ( var k = 0; k < clickList.length; k++) {
					if(mapTryOut[clickList[k].emobId] == "1"){
						testClickUserList[testClickUser++] = clickList[k].emobId;
						testClick += clickList[k].click;
						
					}else if(mapUser[clickList[k].emobId]=="1"){
						clickTimeTotal+= clickList[k].click;
						clickNumTotal += clickList[k].click;
						clickUserList[clickUser++] =clickList[k].emobId ;
						click += clickList[k].click;
						
					}
				}
				
				if(testClickUserList.length>1){
					 testClickUserList=testClickUserList.unique2();
//					 testClickUserList=unique4(testClickUserList);
				}
				if(clickUserList.length>1){
					 clickUserList=clickUserList.unique2();
//					 clickUserList=unique4(clickUserList);
				}
				//var clickNum=clickNumMap[biao];
				var clickUser=clickUserList.length;
				if(clickUser==0){
					//total="["+total+"]";
					click=0;
					clickUser=0;
				}
				
				
				var clickObc={
						"total":total,
						"click":click,
						"clickUser":clickUser,
						"clickUserList":clickUserList,
						"testClick":testClick,
						"testClickUser":testClickUserList.length
						
				};
				mapObjHe[info[j].serviceId]=clickObc;
				
			}
			
		}
		testTop(clickNumTotal,mapObjHe);
	//	thisClick(clickNum);
	},
	error : function(er) {
	}
});
}
function testTop(clickNum,mapObjHe) {
	for ( var key in clickObj) {
		var value = key.replace(/[^0-9]/ig,""); 
		var biao="";
	
		var obj=clickObj[key];
		biao=key.replace(/[0-9]/ig,"");
		var clickObc=mapObjHe[value];
		if(clickObc==undefined){
			continue;
		}
		var usList=new Array();
		var usList2=new Array();
		usList=obj.clickUserList;
		//alert(biao+"--"+usList.length);
	    if("this_data"==biao||"this_week"==biao||"this_month"==biao||"total_id"==biao){
	    	usList2=clickObc.clickUserList;
	    	usList=usList.concat(usList2);
		  
	    }
		var ac=mapActive[biao];	
		mapActive[biao]=ac.concat(usList);
	
	}
	
	for ( var key in mapActive) {
		var list=mapActive[key];
		mapActive[key]=list.unique2();
//		mapActive[key]=unique4(list);
	//	alert(key+"--"+list.length);
		
	}
	isActive=2;
}


function tryOut5() {
	var path = "/api/v1/statisticsUser/statisticsUserNum2";
	var myJson={"communityId":communityId	};
	$.ajax({
		url : path,
		type : "POST",
		 data:JSON.stringify(myJson),
		dataType : 'json',
		success : function(data) {
			data=data.info;
			mapActive["to_day"]=data["to_day"];
			mapActive["this_week"]=data["this_week"];
			mapActive["last_week"]=data["last_week"];
			mapActive["this_month"]=data["this_month"];
			mapActive["last_month"]=data["last_month"];;
			isActive=2;
		},
		error : function(er) {
		}
	});
}
tryOut5();
getTryOut();
//getTryOut2();

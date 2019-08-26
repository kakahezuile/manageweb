var map = {};
var mapTryOut = {};
var mapUser = {};
var clickNumMap={};
var clickNumTime={};
var clickObj={};
var communityId=3;
var clickZhong=0;
var mapActiveUser={};
var mapRegisterList={};
var mapActive={};
var isActive=0;
var iii=0;
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
				//alert(listUsers[i].userId);
				mapUser[listUsers[i].emobId]="1";
			}
			for(var i = 0; i < registerList.length; i++){
				mapRegisterList[registerList[i].emobId]="1";
			}
		//	totalUserNum();
			getTop();
		},
		error : function(er) {
		}
	});
}


function thisUserDataNum(){
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() -1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + da 
	+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" +myDate.getDate() + " 00:00:00";
	getClickAmountTop('to_day',1,startTime,endTime);
	
}
function toDayUserDataNum(){
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" +  myDate.getDate()
	+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" +da + " 00:00:00";
	getClickAmountTop('this_data',1,startTime,endTime);
	
}

function thisMonthUserNum() {
	var myDate = new Date();
	var month = myDate.getMonth()+1;
	var month2 = myDate.getMonth()+2;
	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month2 + "-1" + " 00:00:00";
	getClickAmountTop('this_month',1,startTime,endTime);
}
function monthUserNum() {
	var myDate = new Date();
	var month = myDate.getMonth();
	var month2 = myDate.getMonth()+1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month2 + "-1" + " 00:00:00";
	getClickAmountTop('last_month',1,startTime,endTime);
}
function weekUserNum() {
	var d = getPreviousWeekStartEnd();
	var startTime = d.start;
	var endTime = d.end;
	
	getClickAmountTop('last_week',1,startTime,endTime);
}
function thisWeekUserNum() {
	
	var d = getWeekBenUp();
	var startTime = d.start;
	var endTime = d.end;
	getClickAmountTop('this_week',1,startTime,endTime);
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
	

	thisClickNum2("to_day",startTime,endTime) ;
	
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
	thisClickNum2("this_data",startTime,endTime) ;
	
}



function weekUser() {
	var dd = getPreviousWeekStartEnd();
	var startTime = dd.start;
	var endTime = dd.end;
	
	thisClickNum2("last_week",startTime,endTime) ;
}
function thisWeekUser() {
	
	var dd = getWeekBenUp();
	var startTime = dd.start;
	var endTime = dd.end;
	
	thisClickNum2('this_week',startTime,endTime); 
}

function getTop() {
	toDayUserData();
	thisUserData();
	thisWeekUser();
	weekUser() ;
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

	end = date.getTime() - today * dayMSec;

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
	if(today>-1){
		start = date.getTime() - today * dayMSec;
	}else{
		start = date.getTime() - 7 * dayMSec;
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
	return  ('0' + myDate.getHours()).slice(-2) +":"+('0' +myDate.getMinutes()).slice(-2) +":"+('0' +myDate.getSeconds()).slice(-2);

}


function toStringTimeHs(time) {
	var myDate=new Date(time);
	var month=myDate.getMonth()+1;
	return  myDate.getFullYear()+"-"+month+"-"+myDate.getDate() +" "+myDate.getHours()+":"+myDate.getMinutes()+":"+myDate.getSeconds();
}

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






function thisClickNum2(biao,startTime,endTime) {
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
		
		var info;
		
		var clickUserList = new Array();
		var clickUserList1 = new Array();
		var ll=0;
		var clickUser = 0;
		for ( var i = 0; i < data.length; i++) {
		
			info = data[i].info;
		
			for ( var j = 0; j < info.length; j++) {
			 //  alert("server"+info[j].serviceId+"+total"+info[j].total);
				if(info[j].serviceId=="0"){
					if(info[j].serviceName!="通知公告"){
						continue;
					}
				}
			
				var clickList=info[j].userClick;
				for ( var k = 0; k < clickList.length; k++) {
				    	clickUserList1[ll++]=clickList[k].emobId;
					if(mapTryOut[clickList[k].emobId] == "1"){
						
					}else if(mapUser[clickList[k].emobId]=="1"){
						clickUserList[clickUser++] =clickList[k].emobId ;
					}
				}
			}
		}
		
		mapActive[biao]=clickUserList.unique2();
		var lis=clickUserList1.unique2();
		//alert(biao+""+lis.length);
		iii++;
	},
	error : function(er) {
	}
});
}

getTryOut();
//getTryOut2();

var communityId = window.parent.document.getElementById("community_id_index").value;
var map = {};

var mapUser=window.parent.mapUser;
var mapTryOut =window.parent.mapTryOut;


var clickObj=window.parent.clickObj;
function getTryOut() {
	
	if(window.parent.isClick==63){
//		testTop();
		 thisClickNum() ;
		quickShopData();
	}else{
		schedule();
		setTimeout("getTryOut()", 200);
		 //setInterval("alert(100)",5000);
	}
	
}

function testTop(clickNum,clickObc) {
	for ( var key in clickObj) {
		var value = key.replace(/[^0-9]/ig,""); 
		var biao="";
		
		if(value==10){
			var obj=clickObj[key];
			
			
		//	var obj2=clickObj["this_data10"];
			var clickAddNum=(clickNum);
		
			var clickAddUser=clickObc.clickUser;
			var clickAddTest=clickObc.testClick;
			var clickAddUserTest=clickObc.testClickUser;
			var clickAdd=clickObc.click;
			biao=key.replace(/[0-9]/ig,"");
			if("this_data"==biao||"this_week"==biao||"this_month"==biao||"total_id"==biao){
				 var usList=new Array();
				 var usList2=new Array();
				 var testClickUserList3=new Array();
				 var testClickUserList2=new Array();
				 
				 usList=obj.clickUserList;
				 testClickUserList3=obj.testClickUserList;
				 
				 
				 usList2=clickObc.clickUserList;
				 testClickUserList2=clickObc.testClickUserList;
				 
				 usList=usList.concat(usList2);
				 testClickUserList3=testClickUserList3.concat(testClickUserList2);
			
				
				 if(usList.length>1){
					usList=unique3(usList);
				 }
				 if(testClickUserList3.length>1){
					 testClickUserList3=unique1(testClickUserList3);
				 }
			    var clickNumTop= parseInt(obj.clickNum)+parseInt(clickAddNum);
			    var clickTop= parseInt(obj.click)+parseInt(clickAdd);
				var clickBi=0;
				if(clickNumTop>0){
					clickBi=(clickTop/clickNumTop)*100;
				}
				
				document.getElementById(biao).innerHTML= parseInt(obj.click)+parseInt(clickAdd);
				document.getElementById(biao+"_user").innerHTML=usList.length;
				document.getElementById(biao+"_test").innerHTML="("+(parseInt(obj.testClick)+ parseInt(clickAddTest))+")";
				 document.getElementById(biao+"_user_test").innerHTML="("+ testClickUserList3.length+")";
				document.getElementById(biao+"_ratio").innerHTML=clickBi.toFixed(2)+ "%";
			}else{
					biao=key.replace(/[0-9]/ig,"");
					
					if(obj.click==0){
						document.getElementById(biao).innerHTML="(无明细["+obj.total+"])";
					}else{
						document.getElementById(biao).innerHTML=obj.click;
					}
					
					document.getElementById(biao).innerHTML=obj.click;
					document.getElementById(biao+"_user").innerHTML=obj.clickUser;
					document.getElementById(biao+"_test").innerHTML="("+obj.testClick+")";
					document.getElementById(biao+"_user_test").innerHTML="("+obj.testClickUser+")";
					document.getElementById(biao+"_ratio").innerHTML=obj.clickRatio+ "%";
				
			}
			
			
		
		}
		
	}
	onSchedule();
}

function unique1(list) {
	var ma={};
	for ( var i = 0; i < list.length; i++) {
		ma[list[i]]=1;
	}
	var l=new Array();
	var i=0;
	for ( var key in ma) {
		l[i++]=key;
	}
	return l;
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
		+ communityId + "/modules/statistics/"+start+"/" + (end-1);
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
						
						
					}else if(mapUser[clickList[k].emobId] =="1"){
						clickTime+= clickList[k].click;
						clickNum += clickList[k].click;
					}
				}
				
			}
			
		}
		thisClick(clickNum);
	},
	error : function(er) {
	}
});
}


function  thisClick(clickNum) {
	
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" +  myDate.getDate()
	+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" +da + " 00:00:00";
	var start = stringToTime(startTime) / 1000;
	var end = stringToTime(endTime) / 1000;
	
	
	
	var time = 0;
	var path = "/api/v1/communities/"
		+ communityId
		+ "/users/1/orderHistories/getClickAmount?url=http://115.28.73.37:9090/api/V1/communities/"
		+ communityId + "/events_details_daily/statistics/10/" + start
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
							if(mapUser[clickList[j].emobId] =="1"){
								clickUserList[clickUser++] =clickList[j].emobId ;
								click += clickList[j].click;
							}
						
						}

					}
				}
				
				// alert(testClickUserList.unique2().length);
				if(testClickUserList.length>1){
					 testClickUserList=testClickUserList.unique2();
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
				//	clickUser="["+total+"]";
					click=0;
					clickUser=0;
				}
				
				
				var clickObc={
						"total":total,
						"click":click,
						"clickUser":clickUser,
						"testClick":testClick,
						"clickUserList":clickUserList,
						"testClickUserList":testClickUserList,
						"testClickUser":testClickUserList.length,
						"clickRatio":clickBi.toFixed(2)
						
				};
				testTop(clickNum,clickObc);
			},
			error : function(er) {
				onSchedule();
			}
		});
}


function getClickAmountTop(biao,sort, startTime, endTime) {
	schedule();
	var start = stringToTime(startTime) / 1000;
	var end = stringToTime(endTime) / 1000;
	var time = 0;
	var path = "/api/v1/communities/"
			+ communityId
			+ "/users/1/orderHistories/getClickAmount?url=http://115.28.73.37:9090/api/V1/communities/"
			+ communityId + "/modules/statistics/" + start+ "/" + (end-1);

//alert(path);
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			var info;
			var click = 0;
			var clickNum = 0;
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
							
							
						}else if(mapUser[clickList[k].emobId] =="1"){
						
								clickNum += clickList[k].click;
						}
					}
					//alert(info[j].serviceName+"---"+i);
					
					
				}

			}
			
			getClick(biao,sort, startTime, endTime,   clickNum);
		},
		error : function(er) {
		}
	});
}

function  getClick(biao,sort, startTime, endTime,   clickNum) {
	var start = stringToTime(startTime) / 1000;
	var end = stringToTime(endTime) / 1000;
	var time = 0;
	var path = "/api/v1/communities/"
		+ communityId
		+ "/users/1/orderHistories/getClickAmount?url=http://115.28.73.37:9090/api/V1/communities/"
		+ communityId + "/events_details_daily/statistics/10/" + start
		+ "/" + (end - 1);
	//alert(path);
$
		.ajax({
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
							if(mapUser[clickList[j].emobId] =="1"){
								clickUserList[clickUser++] =clickList[j].emobId ;
								click += clickList[j].click;
							}
						
						}

					}
				}
				
				// alert(testClickUserList.unique2().length);
				if(testClickUserList.length>1){
					 testClickUserList=testClickUserList.unique2();
				}
				if(clickUserList.length>1){
					 clickUserList=clickUserList.unique2();
				}
				var clickBi=0;
				if(clickNum>0){
					clickBi=(click/clickNum)*100;
				}
				var c=clickUserList.length;
				if(c==0){
					c="["+total+"]";
					click=c;
					c=0;
				}
				document.getElementById(biao).innerHTML=click;
				document.getElementById(biao+"_user").innerHTML=c;
			//	document.getElementById(biao+"_user").innerHTML=testClick;
				document.getElementById(biao+"_test").innerHTML="("+testClick+")";
			//	document.getElementById(biao+"_test").innerHTML="("+c+")";
				document.getElementById(biao+"_user_test").innerHTML="("+testClickUserList.length+")";
				
				if(document.getElementById(biao+"_ratio")==null){
					
					alert(biao);
				}else{
					document.getElementById(biao+"_ratio").innerHTML=clickBi.toFixed(2)+ "%";
				}
				if(biao=="to_day"){
					toDayUserData();
					return ;
				}
				
				if(biao=="this_data"){
					thisWeekUser();
					return ;
				}
				
				if(biao=="this_week"){
					weekUser();
					return ;
				}
				if(biao=="last_week"){
					thisMonthUser();
					return ;
				}
				if(biao=="this_month"){
					 monthUser();
					return ;
				}
				if(biao=="last_month"){
					totalUser();
					return ;
				}
				onSchedule();
				
			},
			error : function(er) {
				onSchedule();
			}
		});
}



function thisUserData(){
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() -1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + da 
			+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" +myDate.getDate() + " 00:00:00";
	getClickAmountTop('to_day',1,startTime,endTime);
	
}
function toDayUserData(){
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" +  myDate.getDate()
	+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" +da + " 00:00:00";
	getClickAmountTop('this_data',1,startTime,endTime);
	
}

function thisMonthUser() {
	var myDate = new Date();
	var month = myDate.getMonth()+1;
	var month2 = myDate.getMonth()+2;
	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month2 + "-1" + " 00:00:00";
	getClickAmountTop('this_month',1,startTime,endTime);
}
function monthUser() {
	var myDate = new Date();
	var month = myDate.getMonth();
	var month2 = myDate.getMonth()+1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month2 + "-1" + " 00:00:00";
	getClickAmountTop('last_month',1,startTime,endTime);
}
function weekUser() {
	var d = getPreviousWeekStartEnd();
	var startTime = d.start;
	var endTime = d.end;
	getClickAmountTop('last_week',1,startTime,endTime);
}
function thisWeekUser() {
	
	var d = getWeekBenUp();
	var startTime = d.start;
	var endTime = d.end;
	getClickAmountTop('this_week',1,startTime,endTime);
}
function totalUser() {
	var myDate = new Date();
	var month = myDate.getMonth()+1;
	var da=myDate.getDate()+1;
	var startTime="2015-06-01 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-"+ da + " 00:00:00";
	getClickAmountTop('total_id',1,startTime,endTime);
	
}

function getTop() {
//	toDayUserData();
	thisUserData();
//	thisWeekUser();
//	thisMonthUser();
	//getUserTop();
//	quickUserData();
//	weekUser() ;
//	monthUser();
//	totalUser();
}


function quickShopData(sort) {
	
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate();
	var startTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-"
			+ (myDate.getDate() + 1) + " 00:00:00";
	getClickAmountList(sort, 1, startTime, endTime,'日');
}

function maintainMonth(sort) {

	var myDate = new Date();
	var month = myDate.getMonth();
	var month2= myDate.getMonth()+1;



	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month2 + "-1" 
			+ " 00:00:00";
	
	getClickAmountList(sort, 1, startTime, endTime,'月');

}
function alterMonth(num) {

	var startTime = document.getElementById("master_repir_startTime").value;
	var sort = document.getElementById("master_repir_sort_fei").value;
	var type = document.getElementById("date_type_get").value;
	var d = alterDate(num, type, startTime);
	var startTime = d.start;
	var endTime = d.end;
	var type=document.getElementById("date_type_get").value;
	getClickAmountList(sort, 1, startTime, endTime,type);

}
function weekMonth(sort) {

	var d = getPreviousWeekStartEnd();
	var startTime = d.start;
	var endTime = d.end;
	getClickAmountList(sort, 1, startTime, endTime, '周');

}

function turnover() {
	document.getElementById("statistics_id").style.display = "none";
	document.getElementById("turnover_id").style.display = "";

}
function statistics() {
	document.getElementById("statistics_id").style.display = "";
	document.getElementById("turnover_id").style.display = "none";
}

function selectStatistics() {
	var sort = document.getElementById("master_repir_sort_fei").value;
	var startTime = document.getElementById("txtBeginDate").value + " 00:00:00";
	var endTime = document.getElementById("txtEndDate").value + " 00:00:00";
	getClickAmountList(sort, 1, startTime, endTime);
}
function turnoverTurnover() {
	var startTime = document.getElementById("turnoverBeginDate").value
			+ " 00:00:00";
	var endTime = document.getElementById("turnoverEndDate").value
			+ " 00:00:00";
	getQuickShopList(1, startTime, endTime);
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

function getClickAmountList(sort, pageNum, startTime, endTime,type) {
	//schedule();
	document.getElementById("is_list_detail").value = "";
	var start = stringToTime(startTime) / 1000;
	var end = stringToTime(endTime) / 1000;
	var time = 0;
	var path = "/api/v1/communities/"
			+ communityId
			+ "/users/1/orderHistories/getClickAmount?url=http://115.28.73.37:9090/api/V1/communities/"
			+ communityId + "/events_details_daily/statistics/10/" + start + "/" + (end-1);
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {

			data = data.info;

			var clickUserListNum = new Array();
			var testClickUserListNum = new Array();
			var clickUserNum=0;
			var testClickUserNum=0;
			for ( var i = 0; i < data.length; i++) {
				var clickList = data[i].userClick;
				total+=data[i].total;
				for ( var j = 0; j < clickList.length; j++) {
				
					if (mapTryOut[clickList[j].emobId] == "1") {
						testClickUserListNum[testClickUserNum++] = clickList[j].emobId;
						// alert(clickList[j].emobId);
					} else {
						if(mapUser[clickList[j].emobId] =="1"){
							clickUserListNum[clickUserNum++] =clickList[j].emobId ;
						}
					
					}

				}
			}
			
			// alert(testClickUserList.unique2().length);
			if(testClickUserListNum.length>1){
				 testClickUserListNum=testClickUserListNum.unique2();
			}
			if(clickUserListNum.length>1){
				 clickUserListNum=clickUserListNum.unique2();
			}
			
			var click = 0;
			var testClick = 0;
			var clickUser = 0;
			var testClickUser = 0;
            var time;
            var repair_overman = $("#statistics_list_id");
			repair_overman.empty();
			repair_overman
					.append("<tr class=\"odd\"><td>统计时间</td><td>点击量</td><td>点击人数</td><td>详情</td></tr>");
			var liList="";
			var total=0;
			var map5={};
            var totalClick=0;
            var totalTestClick=0;
			for ( var i = 0; i < data.length; i++) {
				time = getStringTime(data[i]._id.time * 24 * 60 * 60 * 1000);
				map5[time]=data[i];
			}
			var d=show(getStringTime(stringToTime(startTime)),getStringTime(stringToTime(endTime)));
			for ( var i = d.length-2; i >=0; i--) {
				time = d[i];
				var data=map5[time];
				if(data==undefined){
					if (i % 2 == 0) {
						liList += "<tr class=\"odd\">";
					} else {
						liList += "<tr class=\"even\">";
						
					}

					
					liList += "<td>";
					
					var myDate = new Date(stringToTime(d[i]));
					var startTimeFilter = myDate.getMonth() + 1 + "月"
							+ myDate.getDate() + "日";
					liList += startTimeFilter;
					liList+="</td>";
					liList+="<td>"+0+"<b class=\"greenword\">("+0+")</b></td>";
					liList+="<td >"+0+"<b class=\"greenword\">("+0+")</b></td>";
					liList+="<td></td>";
					liList+="</tr>";
					continue;
				}
				var clickUserList = new Array();
				var testClickUserList = new Array();
				var clickList = data.userClick;
			
				total+=data.total;
				for ( var j = 0; j < clickList.length; j++) {
					
					if (mapTryOut[clickList[j].emobId] == "1") {
						testClickUserList[testClickUser++] = clickList[j].emobId;
						testClick += clickList[j].click;
						// alert(clickList[j].emobId);
					} else {
						if(mapUser[clickList[j].emobId] =="1"){
						   clickUserList[clickUser++] = clickList[j].emobId;
						   click += clickList[j].click;
						}
						
					}

				}
				
				
				if(testClickUserList.length>1){
					 testClickUserList=testClickUserList.unique2();
				}
				
				
				if(clickUserList.length>1){
					 clickUserList=clickUserList.unique2();
				}
				
				clickUser=0;
				testClickUser=0;
				
				if (i % 2 == 0) {
					liList += "<tr class=\"odd\">";
				} else {
					liList += "<tr class=\"even\">";
					
				}

				
				var c=clickUserList.length;
				if(c==0){
					
					click="（无明细["+total+"]）";
				}
				
				var t=testClickUserList.length;
				if(t==0){
					
				//	t="无明细["+total+"]";
				//	testClick=0;
				}
				map[data._id.time]=clickList;
				liList += "<td>";
				var myDate = new Date(data._id.time * 24 * 60 * 60 * 1000);
				var startTimeFilter = myDate.getMonth() + 1 + "月"
						+ myDate.getDate() + "日";
				liList += startTimeFilter;
				liList+="</td>";
				liList+="<td>"+click+"<b class=\"greenword\">("+testClick+")</b></td>";
				liList+="<td >"+c+"<b class=\"greenword\">("+t+")</b></td>";
				liList+="<td><a  onclick=\"getClickUser('"+data._id.time+"');\">详情</a></td>";
				liList+="</tr>";
				
				if(!isNaN(click)){
					totalClick+=click;
				}
				
				totalTestClick+=testClick;
					
					
				click = 0;
				testClick=0;
				clickUser=0;
				testClickUser=0;
				total=0;
			}
			
			var liTop="";
			liTop+="<tr class=\"even\"><td >合计</td>";
			liTop+="<td>"+totalClick+"<b class=\"greenword\">("+totalTestClick+")</b></td>";
			liTop+="<td>"+clickUserListNum.length+"<b class=\"greenword\">("+testClickUserListNum.length+")</b></td>";
			liTop+="<td>&nbsp;</td>";
			liTop+="</tr>";
			repair_overman.append(liTop);
			repair_overman.append(liList);
			timeQuantum(startTime, endTime);
			timeDisplay(type);
			
		},
		error : function(er) {
		}
	});	
}

function getClickUser(time) {
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
					.append("<tr class=\"odd\"><td>用户昵称</td><td>电话号码</td><td>点击次数</td></tr>");
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
		//	alert("网络连接失败...");
		//	top.location='/'; 
		}
	});
	
}


function selectData(tim) {

	var da = tim * 1000;
	var myDate = new Date(da);
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + myDate.getDate()
			+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
	getQuickShopList(1, startTime, endTime);
}






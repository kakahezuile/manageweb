var communityId = window.parent.document.getElementById("community_id_index").value;
$(document).ready(function() {
	head_select();
});

function head_select() {
	$(".operation-nav").find("ul li a").removeClass("select");
	$(".operation-nav").find("ul li a[mark=statistics]").addClass("select");
	$(".left-body").find("ul li a").removeClass("select");
	$(".left-body").find("ul li a[mark=lifecircle]").addClass("select");

}


var mapShop = {};

var mapTryOut =window.parent.mapTryOut;
var mapUser=window.parent.mapUser;

var clickObj=window.parent.clickObj;



function testTop(clickNum,clickObc) {
	for ( var key in clickObj) {
		var value = key.replace(/[^0-9]/ig,""); 
		var biao="";
		
		if(value==16){
			var obj=clickObj[key];
			
			
			//var obj2=clickObj["this_data16"];
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
				
				
				document.getElementById(biao+"_click").innerHTML=parseInt(obj.click)+parseInt(clickAdd);
				document.getElementById(biao+"_click_test").innerHTML ="("+ (parseInt(obj.testClickUser)+ parseInt(clickAddUserTest))+")";
				document.getElementById(biao+"_click_user").innerHTML=usList.length;
				document.getElementById(biao+"_click_user_test").innerHTML ="("+ testClickUserList3.length+")";
				document.getElementById(biao+"_click_ratio").innerHTML =clickBi.toFixed(2)+ "%";
				
				
				
			}else{
					biao=key.replace(/[0-9]/ig,"");
					if(obj.click==0){
						document.getElementById(biao+"_click").innerHTML="(无明细["+obj.total+"])";
					}else{
						document.getElementById(biao+"_click").innerHTML=obj.click;
					}
					document.getElementById(biao+"_click_test").innerHTML ="("+ obj.testClick+")";
					document.getElementById(biao+"_click_user").innerHTML=obj.clickUser;
					document.getElementById(biao+"_click_user_test").innerHTML ="("+obj.testClickUser+")";
					document.getElementById(biao+"_click_ratio").innerHTML = obj.clickRatio+"%";
				
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
		+ communityId + "/events_details_daily/statistics/16/" + start
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





function getTryOut() {
	
	if(window.parent.isClick==63){
	//	testTop();
		thisClickNum();
		getTop();
		quickShopData();
	}else{
		schedule();
		setTimeout("getTryOut()", 2000);
		 //setInterval("alert(100)",5000);
	}
	
}









function getClickAmountTop2(startTime, endTime, type) {
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
			for ( var i = 0; i < tryOut.length; i++) {
				mapTryOut[tryOut[i].emobId] = "1";
			}
			for ( var i = 0; i < listUsers.length; i++) {
				// alert(listUsers[i].userId);
				mapShop[listUsers[i].emobId] = "2";
			}

			quickShopData();
			 getTop();
			// getClickAmountTop2(startTime, endTime, type);

		},
		error : function(er) {
			
			// alert("网络连接失败...");
			// top.location='/';
		}
	});
}


function getClickAmountTop(biao, startTime, endTime) {
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
							
						}else if(mapUser[clickList[k].emobId] =="1" ){
						
								clickNum += clickList[k].click;
						}
					}
					
				}

			}
			
			getClickAmountTop3(biao,startTime, endTime,   clickNum);
		},
		error : function(er) {
		}
	});
}



function getClickAmountTop3(biao,startTime, endTime,clickNum) {

	var start = stringToTime(startTime) / 1000;
	var end = stringToTime(endTime) / 1000;
	var time = 0;
	var path = "/api/v1/communities/"
			+ communityId
			+ "/users/1/orderHistories/getClickAmount?url=http://115.28.73.37:9090/api/V1/communities/"
			+ communityId + "/events_details_daily/statistics/16/" + start
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
					var total = 0;
					// info = data.userClick;
					for ( var i = 0; i < data.length; i++) {
						var clickList = data[i].userClick;
						total += data[i].total;
						for ( var j = 0; j < clickList.length; j++) {
							if (mapTryOut[clickList[j].emobId] == "1") {
								testClickUserList[testClickUser++] = clickList[j].emobId;
								testClick += clickList[j].click;
								// alert(clickList[j].emobId);
							} else {
								if (mapUser[clickList[j].emobId] =="1") {
									clickUserList[clickUser++] = clickList[j].emobId;
									click += clickList[j].click;
								}

							}

						}
					}
					if (testClickUserList.length > 1) {
						testClickUserList = testClickUserList.unique2();

					}
					if (clickUserList.length > 1) {
						clickUserList = clickUserList.unique2();
					}
					getQuickShopTop(biao,startTime, endTime,clickNum, click, testClick,
							clickUserList.length, testClickUserList.length);
				},
				error : function(er) {
					onSchedule();
				}
			});
}
function getQuickShopTop(biao,startTime, endTime) {
	var path = "/api/v1/communities/" + communityId
			+ "/lifeCircle/getLifeCircleTop?startTime=" + startTime
			+ "&endTime=" + endTime;
	var myDate = new Date(stringToTime(startTime));
	var myDate2 = new Date(stringToTime(endTime));
	var dates = Math.abs((myDate2 - myDate)) / (1000 * 60 * 60 * 24);

	var startTimeFilter = myDate.getMonth() + 1 + "月" + myDate.getDate() + "日";
	var endTimeFilter = myDate2.getMonth() + 1 + "月" + myDate2.getDate() + "日";
	$
			.ajax({
				url : path,
				type : "GET",
				dataType : 'json',
				success : function(data) {
					data = data.info;
					var ord = data.activitesNum / dates;

					
					
					
					
					document.getElementById(biao+"_topic").innerHTML =  data.dynamicState;
					document.getElementById(biao+"_topic_test").innerHTML ="("+ data.testDynamicState+")";
					
					document.getElementById(biao+"_topic_user").innerHTML =  data.userNum;
					document.getElementById(biao+"_topic_user_test").innerHTML ="("+ data.testUserNum+")";
					
					document.getElementById(biao+"_group").innerHTML =  data.groupChat;
					document.getElementById(biao+"_group_test").innerHTML = "("+ data.testGroupChat+")";
					document.getElementById(biao+"_group_user").innerHTML = data.groupUser;
					document.getElementById(biao+"_group_user_test").innerHTML ="("+ data.testGroupChat+")";
					document.getElementById(biao+"_praise").innerHTML = data.praise ;
					document.getElementById(biao+"_praise_test").innerHTML ="("+data.testPraise+")";
					
					document.getElementById(biao+"_praise_user").innerHTML = data.praiseUser;
					document.getElementById(biao+"_praise_user_test").innerHTML ="("+ data.testPraiseUser+")";
					
					
					document.getElementById(biao+"_comment").innerHTML = data.comment;
					document.getElementById(biao+"_comment_test").innerHTML ="("+ data.testComment+")";
					
					document.getElementById(biao+"_comment_user").innerHTML = data.commentUser;
					document.getElementById(biao+"_comment_user_test").innerHTML ="("+ data.testCommentUser+")";
					
					

					onSchedule();
				},
				error : function(er) {
					onSchedule();
				}
			});
}


function getQuickShopTop4(biao,startTime, endTime, clickNum, click, testClick, clickUser,
		testClickUser) {
	var path = "/api/v1/communities/" + communityId
	+ "/lifeCircle/getLifeCircleTop?startTime=" + startTime
	+ "&endTime=" + endTime;
	var myDate = new Date(stringToTime(startTime));
	var myDate2 = new Date(stringToTime(endTime));
	var dates = Math.abs((myDate2 - myDate)) / (1000 * 60 * 60 * 24);
	
	var startTimeFilter = myDate.getMonth() + 1 + "月" + myDate.getDate() + "日";
	var endTimeFilter = myDate2.getMonth() + 1 + "月" + myDate2.getDate() + "日";
	$
	.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			var ord = data.activitesNum / dates;
			
			
			
			
			var bi=0;
			if(clickNum>0){
				bi=(click/clickNum)*100;
				
			}
			
			document.getElementById(biao+"_click").innerHTML=click;
			document.getElementById(biao+"_click_test").innerHTML ="("+ testClick+")";
			document.getElementById(biao+"_click_ratio").innerHTML = bi.toFixed(2)+"%";
			
			document.getElementById(biao+"_topic").innerHTML =  data.dynamicState;
			document.getElementById(biao+"_topic_test").innerHTML ="("+ data.testDynamicState+")";
			
			document.getElementById(biao+"_topic_user").innerHTML =  data.userNum;
			document.getElementById(biao+"_topic_user_test").innerHTML ="("+ data.testUserNum+")";
			
			document.getElementById(biao+"_group").innerHTML =  data.groupChat;
			document.getElementById(biao+"_group_test").innerHTML = "("+ data.testGroupChat+")";
			document.getElementById(biao+"_group_user").innerHTML = data.groupUser;
			document.getElementById(biao+"_group_user_test").innerHTML ="("+ data.testGroupChat+")";
			document.getElementById(biao+"_praise").innerHTML = data.praise ;
			document.getElementById(biao+"_praise_test").innerHTML ="("+data.testPraise+")";
			
			document.getElementById(biao+"_praise_user").innerHTML = data.praiseUser;
			document.getElementById(biao+"_praise_user_test").innerHTML ="("+ data.testPraiseUser+")";
			
			
			document.getElementById(biao+"_comment").innerHTML = data.comment;
			document.getElementById(biao+"_comment_test").innerHTML ="("+ data.testComment+")";
			
			document.getElementById(biao+"_comment_user").innerHTML = data.commentUser;
			document.getElementById(biao+"_comment_user_test").innerHTML ="("+ data.testCommentUser+")";
			
			if(biao=="this_data"){
				toDayUserData();
				return ;
			}
			
			if(biao=="to_day"){
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
	getQuickShopTop('to_day',startTime,endTime);
	
	
}
function toDayUserData(){
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" +  myDate.getDate()
	+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" +da + " 00:00:00";
//	getClickAmountTop('to_day',startTime,endTime);
	getQuickShopTop('this_data',startTime,endTime);
	
}

function thisMonthUser() {
	var myDate = new Date();
	var month = myDate.getMonth()+1;
	var month2 = myDate.getMonth()+2;
	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month2 + "-1" + " 00:00:00";
	getQuickShopTop('this_month',startTime,endTime);
}
function monthUser() {
	var myDate = new Date();
	var month = myDate.getMonth();
	var month2 = myDate.getMonth()+1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month2 + "-1" + " 00:00:00";
	getQuickShopTop('last_month',startTime,endTime);
}
function weekUser() {
	var d = getPreviousWeekStartEnd();
	var startTime = d.start;
	var endTime = d.end;
	getQuickShopTop('last_week',startTime,endTime);
}
function thisWeekUser() {
	
	var d = getWeekBenUp();
	var startTime = d.start;
	var endTime = d.end;
	getQuickShopTop('this_week',startTime,endTime);
}
function totalUser() {
	var myDate = new Date();
	var month = myDate.getMonth()+1;
	var da=myDate.getDate()+1;
	var startTime="2015-06-01 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-"+ da + " 00:00:00";
	getQuickShopTop('total_id',startTime,endTime);
	
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


function quickShopData() {
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate();
	var startTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" + (da + 1)
			+ " 00:00:00";
	getClickAmountList(1, startTime, endTime, '日');
	//getClickAmountTop(startTime, endTime, '日');
}

function maintainMonth() {

	var myDate = new Date();
	var month = myDate.getMonth();
	var month2= myDate.getMonth()+1;

	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month2 + "-" +1
			+ " 00:00:00";
	// getQuickShopList(1, startTime, endTime);
	getClickAmountList(1, startTime, endTime, '月');
//	getClickAmountTop(startTime, endTime, '月');

}
function alterMonth(num) {

	var startTime = document.getElementById("master_repir_startTime").value;
	var type = document.getElementById("date_type_get").value;
	var d = alterDate(num, type, startTime);
	var startTime = d.start;
	var endTime = d.end;
	getClickAmountList(1, startTime, endTime, type);
	//getClickAmountTop(startTime, endTime, type);

}
function weekMonth() {

	var d = getPreviousWeekStartEnd();
	var startTime = d.start;
	var endTime = d.end;

	getClickAmountList(1, startTime, endTime, '周');
//	getClickAmountTop(startTime, endTime, '周');

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
	var startTime = document.getElementById("txtBeginDate").value + " 00:00:00";
	var endTime = document.getElementById("txtEndDate").value + " 00:00:00";
	getClickAmountList(1, startTime, endTime);
	getClickAmountTop(startTime, endTime);

}
function turnoverTurnover() {
	var startTime = document.getElementById("turnoverBeginDate").value
			+ " 00:00:00";
	var endTime = document.getElementById("turnoverEndDate").value
			+ " 00:00:00";
	getQuickShopList(1, startTime, endTime);
	getClickAmountTop(startTime, endTime);
}

function timeClick(clickId) {
	if (clickId == "data_clik") {
		document.getElementById("data_clik").className = "select";
		quickShopData();
	} else {
		document.getElementById("data_clik").className = "";
	}
	if (clickId == "week_clik") {
		document.getElementById("week_clik").className = "select";
		weekMonth();
	} else {
		document.getElementById("week_clik").className = "";
	}
	if (clickId == "month_clik") {
		document.getElementById("month_clik").className = "select";
		maintainMonth();
	} else {
		document.getElementById("month_clik").className = "";
	}
}
function getClickAmountList(pageNum, startTime, endTime,type) {
	document.getElementById("is_list_detail").value = "";
	var start = stringToTime(startTime) / 1000;
	var end = stringToTime(endTime) / 1000;
	var time = 0;
	var path = "/api/v1/communities/"
			+ communityId
			+ "/users/1/orderHistories/getClickAmount?url=http://115.28.73.37:9090/api/V1/communities/"
			+ communityId + "/events_details_daily/statistics/16/" + start
			+ "/" + (end-1);
	$
			.ajax({
				url : path,
				type : "GET",
				dataType : 'json',
				success : function(data) {

					data = data.info;
					
					var clickUserListNum = new Array();
					var testClickUserListNum = new Array();
					var clickUserNum=0;
					var testClickUserNum=0;
					
					var clickSumNum=0;
					var testClickSumNum=0;
					
					
				
					
					var map = {};
					var click = 0;
					var testClick = 0;
					var clickUser = 0;
					var testClickUser = 0;
					var time;

					for ( var i = 0; i < data.length; i++) {
						var clickList = data[i].userClick;
						var clickUserList = new Array();
						var testClickUserList = new Array();
						time = getStringTime(data[i]._id.time * 24 * 60 * 60
								* 1000);
						for ( var j = 0; j < clickList.length; j++) {
							if (mapTryOut[clickList[j].emobId] == "1") {
								testClickUserListNum[testClickUserNum++] = clickList[j].emobId;
								testClickSumNum += clickList[j].click;
								testClickUserList[testClickUser++] = clickList[j].emobId;
								testClick += clickList[j].click;
								// alert(clickList[j].emobId);
							} else {
								if(mapUser[clickList[j].emobId] =="1"){
									clickUserListNum[clickUserNum++] =clickList[j].emobId ;
									clickSumNum += clickList[j].click;
									clickUserList[clickUser++] = clickList[j].emobId;
									click += clickList[j].click;
								}
							}

						}
						if (testClickUserList.length > 1) {
							testClickUserList = testClickUserList.unique2();

						}
						if (clickUserList.length > 1) {
							clickUserList = clickUserList.unique2();
						}

						var clickDetl = {
							"click" : click,
							"testClick" : testClick,
							"clickUserList":clickUserList,
							"clickUser" : clickUserList.length,
							"testClickUser" : testClickUserList.length
						};
						map[time] = clickDetl;
						click = 0;
						testClickUser = 0;
						clickUser = 0;
						testClick=0;
					}
					// alert(testClickUserList.unique2().length);
					if(testClickUserListNum.length>1){
						 testClickUserListNum=testClickUserListNum.unique2();
					}
					if(clickUserListNum.length>1){
						 clickUserListNum=clickUserListNum.unique2();
					}
					
					timeQuantum(startTime, endTime);
					timeDisplay(type);
					getUseAmountList(pageNum, startTime, endTime, map,clickSumNum,testClickSumNum,clickUserListNum.length,testClickUserListNum.length);
				},
				error : function(er) {
				}
			});
}

function getUseAmountList(pageNum, startTime, endTime, map,clickSumNum,testClickSumNum,clickUserNum,testClickUserNum) {
	var path = "/api/v1/communities/" + communityId
			+ "/lifeCircle/getLifeCircleTop?startTime=" + startTime
			+ "&endTime=" + endTime;

	$.ajax({
				url : path,
				type : "GET",
				dataType : 'json',
				success : function(data) {
					data = data.info;

					var liTop="";
					liTop += "<tr class=\"even\">";
					liTop += "<td><p>合计</p></td>";
					liTop += "<td ><p>" + clickSumNum + "(" + clickUserNum + ")";
					liTop += "</p><p class=\"navy\">" + testClickSumNum + "("
							+ testClickUserNum + ")</p></td><td><p>"
							+ data.dynamicState + "(" + data.userNum
							+ ")</p><p class=\"navy\">"
							+ data.testDynamicState + "("
							+ data.testUserNum + ")</p></td><td><p>"
							+ data.groupChat + "(" + data.groupUser
							+ ")</p><p class=\"navy\">"
							+ data.testGroupChat + "("
							+ data.testGroupUser + ")</p></td>";
					liTop += "<td><p>" + data.praise + "("
							+ data.praiseUser + ")</p><p class=\"navy\">"
							+ data.testPraise + "(" + data.testPraiseUser
							+ ")</p></td>";
					liTop += "<td><p>" + data.comment + "("
							+ data.commentUser + ")</p><p class=\"navy\">"
							+ data.testComment + "("
							+ data.testCommentUser + ")</p></td>";
					liTop += "</tr>";
					
					
					getUseAmountList2(pageNum, startTime, endTime, map,liTop);
					

					onSchedule();
				},
				error : function(er) {
					onSchedule();
				}
			});
}
function getUseAmountList2(pageNum, startTime, endTime, map,liTop) {
	var d=show(getStringTime(stringToTime(startTime)),getStringTime(stringToTime(endTime)));
	var en="";
	for ( var i = 0; i < d.length-1; i++) {
		en+="&sqlTime="+d[i]+" 00:00:00";
	}
	// document.getElementById("shop_sort_id").value;
	var path = "/api/v1/communities/" + communityId
			+ "/lifeCircle/getLifeCircleList?startTime=" + startTime
			+ "&endTime=" + endTime+en;
	$.ajax({
				url : path,
				type : "GET",
				dataType : 'json',
				success : function(data) {
					data = data.info;
					var map2 = {};

					var repair_overman = $("#statistics_list_id");
					repair_overman.empty();

					// var liList='';
					repair_overman
							.append("<tr class=\"odd\"><td>日期</td><td>点击量</td><td>总动态量(用户数)</td><td>创建群聊数(用户数)</td><td>赞人品使用量(用户数)</td><td>评论使用量(用户数)</td></tr>");

					for ( var i = 0; i < data.length; i++) {
						map2[getStringTime(data[i].createTime * 1000)] = data[i];
					}
					var data2;
					var shi = show(getStringTime(stringToTime(startTime)),
							getStringTime(stringToTime(endTime)));
					
					var liList = "";
					for ( var i =  shi.length - 2; i >=0; i--) {
						data2 = map2[shi[i]];
						if (data2 == undefined) {
							var data2 = {
								"createTime" : stringToTime(shi[i]
										+ " 00:00:00") / 1000,
								"dynamicState" : 0,
								"userNum" : 0,
								"groupChat" : 0,
								"groupUser" : 0,
								"praise" : 0,
								"praiseUser" : 0,
								"comment" : 0,
								"testDynamicState" : 0,
								"testUserNum" : 0,
								"testGroupChat" : 0,
								"testGroupUser" : 0,
								"testPraise" : 0,
								"testPraiseUser" : 0,
								"testComment" : 0,
								"testCommentUser" : 0,
								"commentUser" : 0
							};
						}
						var time = getStringTime(data2.activityTime * 1000);

						var c = map[shi[i]];
						var click = 0;
						var testClick = 0;
						var testClickUser = 0;
						var clickUser = 0;
						if (c != undefined) {
							click = c.click;
							testClick = c.testClick;
							testClickUser = c.testClickUser;
							clickUser = c.clickUser;
						}
					
						if (i % 2 == 0) {
							liList += "<tr class=\"odd\">";
						} else {
							liList += "<tr class=\"even\">";
							
						}
						liList += "<td><p>" + shi[i] + "</p></td>";
						liList += "<td ><p>" + click + "(" + clickUser + ")";
						liList += "</p><p class=\"navy\">" + testClick + "("
								+ testClickUser + ")</p></td><td><p>"
								+ data2.dynamicState + "(" + data2.userNum
								+ ")</p><p class=\"navy\">"
								+ data2.testDynamicState + "("
								+ data2.testUserNum + ")</p></td><td><p>"
								+ data2.groupChat + "(" + data2.groupUser
								+ ")</p><p class=\"navy\">"
								+ data2.testGroupChat + "("
								+ data2.testGroupUser + ")</p></td>";
						liList += "<td><p>" + data2.praise + "("
								+ data2.praiseUser + ")</p><p class=\"navy\">"
								+ data2.testPraise + "(" + data2.testPraiseUser
								+ ")</p></td>";
						liList += "<td><p>" + data2.comment + "("
								+ data2.commentUser + ")</p><p class=\"navy\">"
								+ data2.testComment + "("
								+ data2.testCommentUser + ")</p></td>";
						liList += "</tr>";
						
					}
					
					repair_overman.append(liTop);
					repair_overman.append(liList);
					
					document.getElementById("master_repir_startTime").value = startTime;
					document.getElementById("master_repir_endTime").value = endTime;
					document.getElementById("is_list_detail").value = "";
					// document.getElementById("shop_sort_id").value=sort;
				},
				error : function(er) {
				}
			});

}

function selectData(tim) {
	document.getElementById("is_list_detail").value = "detail";
	document.getElementById("time_day").value = tim;
	var da = tim * 1000;
	var myDate = new Date(da);
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + myDate.getDate()
			+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
	// alert(startTime+"=="+endTime);
	getQuickShopList(1, startTime, endTime);
}

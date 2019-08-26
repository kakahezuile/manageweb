
var mapTryOut = {};
var mapUser = {};
function getTryOut() {
	var community_id=document.getElementById("community_id").value;
	var path = "/api/v1/communities/" + community_id
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
		
			getTop();
		},
		error : function(er) {
		}
	});
}

function  getClick(biao,startTime, endTime) {
	var start = stringToTime(startTime) / 1000;
	var end = stringToTime(endTime) / 1000;
	var time = 0;
	var community_id=document.getElementById("community_id").value;
	var path = "/api/v1/communities/"
		+ community_id
		+ "/users/1/orderHistories/getClickAmount?url=http://115.28.73.37:9090/api/V1/communities/"
		+ community_id + "/events_details_daily/statistics/3/" + start
		+ "/" + (end - 1);
        $.ajax({
			url : path,
			type : "GET",
			dataType : 'json',
			success : function(data) {
				data = data.info;
				var info;
				var total = 0;
				
				for ( var i = 0; i < data.length; i++) {
					var clickList = data[i].userClick;
					for ( var j = 0; j < clickList.length; j++) {
					
						if (mapTryOut[clickList[j].emobId] == "1") {
						} else {
							if(mapUser[clickList[j].emobId]=="1"){
								total += clickList[j].click;
							}
						
						}

					}
				}
				getQuickShopTop(biao, startTime, endTime,total);
			},
			error : function(er) {
			}
		});
}





function getQuickShopTop(biao,startTime, endTime,total) {
	var community_id=document.getElementById("community_id").value;
	var path = "/api/v1/communities/" +community_id
			+ "/users/1/orderHistories/getQuickShopTopH?sort=2&startTime=" + startTime + "&endTime=" + endTime;
	
	var myDate = new Date(stringToTime(startTime));
	var myDate2 = new Date(stringToTime(endTime));
	var dates = Math.abs((myDate2 - myDate)) / (1000 * 60 * 60 * 24);
	$.ajax({
				url : path,
				type : "GET",
				dataType : 'json',
				success : function(data) {
					data = data.info;
					var endOrderQuantity=data.endOrderQuantity;
					var orderPrice=data.orderPrice;
					
					if(data.endOrderQuantity==""){
						endOrderQuantity=0;
					}
					if(data.orderPrice==""){
						orderPrice=0;
					}
					
					document.getElementById(biao+"_click").innerHTML=total;
					document.getElementById(biao+"_end").innerHTML=endOrderQuantity;
					document.getElementById(biao+"_par").innerHTML=orderPrice;
					document.getElementById(biao+"_earnings").innerHTML=(orderPrice*0.02).toFixed(2);
					
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
	//getClickAmountTop('to_day',2,startTime,endTime);
	getClick('to_day',startTime,endTime);
	
}

function thisMonthUser() {
	var myDate = new Date();
	var month = myDate.getMonth()+1;
	var month2 = myDate.getMonth()+2;
	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month2 + "-1" + " 00:00:00";
	getClick('this_month',startTime,endTime);
}
function weekUser() {
	var d = getPreviousWeekStartEnd();
	var startTime = d.start;
	var endTime = d.end;
	getClick('last_week',startTime,endTime);
}
function thisWeekUser() {
	var d = getWeekBenUp();
	var startTime = d.start;
	var endTime = d.end;
	getClick('this_week',startTime,endTime);
}

function getTop() {
	thisUserData();
	thisWeekUser();
	thisMonthUser();
	weekUser() ;
}
function  leftSelect() {
	var community_id=document.getElementById("community_id").value;
	document.getElementById("community_id_"+community_id).className="select";
	
}
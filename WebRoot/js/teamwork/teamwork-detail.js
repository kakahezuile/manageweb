
function getUserShop( startTime, endTime) {
	
	var community_id=document.getElementById("community_id").value;
	var path = "/api/v1/communities/" + community_id
			+ "/users/1/orderHistories/getUserShopWU?startTime=" + startTime + "&endTime=" + endTime;

	$.ajax({
				url : path,
				type : "GET",
				dataType : 'json',
				success : function(data) {
					data = data.info;

					var repair_overman = $("#statistics_list_id");
					repair_overman.empty();

					// var liList='';
					repair_overman
							.append("<tr ><th>交易时间</th><th>付款方</th><th>交易金额</th><th>分成比例</th><th>收益</th><th>交易记录</th></tr>");

					for ( var i = 0; i < data.length; i++) {
						var liList = "";

						if (i % 2 == 0) {
							liList += "<tr class=\"even\">";
						} else {

							liList += "<tr class=\"odd\">";
						}

							liList += "<td >" + getStringTime(data[i].endTime * 1000) + "</td>";

							liList += "<td>" + data[i].nickname +"("+data[i].userFloor+data[i].userUnit+data[i].room+")</td>";
							liList += "<td>"+data[i].orderPrice+  "</td>";
							liList += "<td>"+
									"2%"
									+ "</td>";

							liList += "<td>" + (data[i].orderPrice*0.02).toFixed(2) + "</td>";
						liList+="<td><a href=\"javascript:;\">交易记录(待做)</a></td>";
						liList += "</tr>";
						repair_overman.append(liList);
						
					}
					onSchedule();
				},
				error : function(er) {
					onSchedule();
					top.location = '/';
				}
			});
}

function  test(timeType) {
	if(timeType=='to_day'){
		thisUserData();
	}else if(timeType=='last_week'){
		weekUser();
	}else if(timeType=='this_week'){
		thisWeekUser();
	}else if(timeType=='month'){
		var month_id=document.getElementById("month_id").value;
		var myDate = new Date();
		var startTime = myDate.getFullYear() + "-" + month_id + "-" + 1
		+ " 00:00:00";
           var endTime = myDate.getFullYear() + "-" +(parseInt(month_id)+1) + "-1" + " 00:00:00";
       	getUserShop(startTime,endTime);
	}
}


function thisUserData(){
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() -1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + da 
			+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" +myDate.getDate() + " 00:00:00";
	
	getUserShop(startTime,endTime);
	
}


function weekUser() {
	var d = getPreviousWeekStartEnd();
	var startTime = d.start;
	var endTime = d.end;
	getUserShop(startTime,endTime);
}
function thisWeekUser() {
	var d = getWeekBenUp();
	var startTime = d.start;
	var endTime = d.end;
	getUserShop(startTime,endTime);
}

function  leftSelect() {
	var community_id=document.getElementById("community_id").value;
	document.getElementById("community_id_"+community_id).className="select";
	
}

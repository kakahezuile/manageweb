var communityId = 5;

function getClick(biao, startTime, endTime) {
	var start = stringToTime(startTime) / 1000;
	var end = stringToTime(endTime) / 1000;
	var time = 0;
	var community_id=document.getElementById("community_id").value;
	var path = "/api/v1/communities/"
			+ community_id
			+ "/users/1/orderHistories/getClickAmount?url=http://115.28.73.37:9090/api/V1/communities/"
			+ community_id + "/events_details_daily/statistics/3/" + start + "/"
			+ (end - 1);
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			var info;
			var total = 0;
			for ( var i = 0; i < data.length; i++) {
				total += data[i].total;
			}

			getQuickShopTop(biao, startTime, endTime, total);
		},
		error : function(er) {
		}
	});
}

function getQuickShopTop(biao, startTime, endTime, total) {
	var community_id=document.getElementById("community_id").value;
	var path = "/api/v1/communities/" + community_id
			+ "/users/1/orderHistories/getQuickShopTop?sort=2&startTime="
			+ startTime + "&endTime=" + endTime;

	var myDate = new Date(stringToTime(startTime));
	var myDate2 = new Date(stringToTime(endTime));
	var dates = Math.abs((myDate2 - myDate)) / (1000 * 60 * 60 * 24);
	$
			.ajax({
				url : path,
				type : "GET",
				dataType : 'json',
				success : function(data) {
					data = data.info;

					var endOrderQuantity = data.endOrderQuantity;
					var orderPrice = data.orderPrice;
					if (data.endOrderQuantity == "") {
						endOrderQuantity = 0;
					}
					if (data.orderPrice == "") {
						orderPrice = 0;
					}

					var repair_overman = $("#static_month");
					//repair_overman.empty();

					var liList = "<table>";
					liList += "<tr>";
					liList += "<td colspan=\"9\" class=\"static-more-title\"><div class=\"static-collect\">"+(biao-1)+"月交易汇总</div></td>";
					liList += "</tr>";
					liList += "<tr>";
					liList += "<th>交易类型</th>";
					liList += "<th>点击量</th>";
					liList += "<th>交易单量</th>";
					liList += "<th>交易金额</th>";
					liList += "<th>分成比例</th>";
					liList += "<th>收益</th>";
					liList += "<th >已结款</th>";
					liList += "<th>未结款</th>";
					liList += "<th>详情</th>";
					liList += "</tr>";
					liList += "<tr class=\"odd\">";
					liList += "<td>快店</td>";
					liList += "<td>" + total + "</td>";
					liList += "<td>" + endOrderQuantity + "</td>";
					liList += "<td>" + orderPrice + "</td>";
					liList += "<td>2%</td>";
					liList += "<td>" + (orderPrice * 0.02).toFixed(2) + "</td>";
					liList += "<td></td>";
					liList += "<td></td>";
					liList += "<td><a href=\"/jsp/teamwork/teamwork-detail.jsp?timeType=month&month="+(biao-1)+"&community_id="+community_id+"\">详情</a></td>";
					liList += "</tr>";
					liList += "</table>";
					repair_overman.append(liList);
					onSchedule();
				},
				error : function(er) {
					onSchedule();
				}
			});
}



function getTop() {

	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var k = month - 6;
	var s = 7;
	for ( var i = 0; i <k; i++) {
		var startTime = myDate.getFullYear() + "-" + (s++) + "-" + 1
				+ " 00:00:00";
		var endTime = myDate.getFullYear() + "-" + s + "-1" + " 00:00:00";
		getClick(s, startTime, endTime);
	}

}
function  leftSelect() {
	var community_id=document.getElementById("community_id").value;
	document.getElementById("community_id_"+community_id).className="select";
	
}
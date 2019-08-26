var communityId=window.parent.document.getElementById("community_id_index").value;
$(document).ready(function() {
	head_select();
});
/*
 * function head_select(){ $(".operation-nav").find("ul li
 * a").removeClass("select"); $(".operation-nav").find("ul li
 * a[mark=statistics]").addClass("select"); $(".left-body").find("ul li
 * a").removeClass("select"); $(".left-body").find("ul li
 * a[mark=shop]").addClass("select"); }
 */
function head_select() {
	$(".operation-nav").find("ul li a").removeClass("select");
	$(".operation-nav").find("ul li a[mark=statistics]").addClass("select");
	$(".left-body").find("ul li a").removeClass("select");
	$(".left-body").find("ul li a[mark=lifecircle]").addClass("select");

}
function getClickAmountTop( startTime, endTime,type) {
	timeQuantum(startTime, endTime);

	timeDisplay(type);
}



function quickShopData() {
	schedule();
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() ;
	var startTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" +(da+1)
			+ " 00:00:00";
	getClickAmountList("time", startTime, endTime);
	getClickAmountTop(startTime, endTime, '日');
	onSchedule();
}

function maintainMonth() {

	var myDate = new Date();
	var month = myDate.getMonth();

	var firstDate = new Date();

	firstDate.setDate(1); // 第一天

	var endDate = new Date(firstDate);

	endDate.setMonth(firstDate.getMonth());

	endDate.setDate(0);

	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" + endDate.getDate()
			+ " 00:00:00";
	// getQuickShopList(1, startTime, endTime);
	getClickAmountList("time", startTime, endTime);
	getClickAmountTop(startTime, endTime, '月');

}
function alterMonth(num) {

	var startTime = document.getElementById("master_repir_startTime").value;
	var type = document.getElementById("date_type_get").value;
	var d = alterDate(num, type, startTime);
	var startTime = d.start;
	var endTime = d.end;
	getClickAmountList("time", startTime, endTime);
	getClickAmountTop(startTime, endTime, type);

}
function weekMonth() {

	var d = getPreviousWeekStartEnd();
	var startTime = d.start;
	var endTime = d.end;

	getClickAmountList("time", startTime, endTime);
	getClickAmountTop(startTime, endTime, '周');

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
	getClickAmountList("time", startTime, endTime);
	getClickAmountTop(startTime, endTime);

}
function turnoverTurnover() {
	var startTime = document.getElementById("turnoverBeginDate").value
			+ " 00:00:00";
	var endTime = document.getElementById("turnoverEndDate").value
			+ " 00:00:00";
	getClickAmountList("time", startTime, endTime);
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
function getClickAmountList( type, startTime, endTime) {
	
	
			getUseAmountList( type, startTime, endTime);
		
		
}
function getUseAmountList(type, startTime, endTime) {

	// document.getElementById("shop_sort_id").value;
	var path = "/api/v1/communities/"+communityId+"/lifeCircle/selectLifeCircleList?startTime=" + startTime + "&endTime=" + endTime+"&type="+type;
	$.ajax({
				url : path,
				type : "GET",
				dataType : 'json',
				success : function(data) {
					data = data.info;
					var repair_overman = $("#statistics_list_id");
					repair_overman.empty();
					repair_overman
							.append("<tr class=\"odd\"><td>话题</td><td>用户地址</td><td>图片</td><td>点赞量</td><td>评论量</td></tr>");
					for ( var i = 0; i < data.length; i++) {
						var liList="" ;
						liList+="<tr class=\"odd\"><td><div title=\""+data[i].lifeContent+"\">"+data[i].lifeContent+"</div></td>";
						
						liList+="<td>"+data[i].nickname+data[i].userUnit+data[i].userFloor+"</td>";
						liList+="<td>";
						
						var listPono=data[i].list;
						for ( var j = 0; j < listPono.length; j++) {
							var array_element = listPono[j];
							//alert(array_element.photoUrl);
							liList+=	"<span class=\"images\"><a href=\"javascript:void(0);\" ><img src=\""+array_element.photoUrl+"\"/></a></span>";
						}
						liList+=" </td>";
						liList+="<td>"+data[i].praiseSum+"</td>";
						liList+="<td>"+data[i].contentSum+"</td>";
						liList+="</tr>";
						repair_overman.append(liList);
					}
					document.getElementById("master_repir_startTime").value = startTime;
					document.getElementById("master_repir_endTime").value = endTime;
					document.getElementById("is_list_detail").value="";
					// document.getElementById("shop_sort_id").value=sort;
				},
				error : function(er) {
				}
			});

}

function selectData(tim) {
	document.getElementById("is_list_detail").value="detail";
	document.getElementById("time_day").value=tim;
	var da = tim * 1000;
	var myDate = new Date(da);
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + myDate.getDate()
			+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
//	alert(startTime+"=="+endTime);
	getQuickShopList("time", startTime, endTime);
}

function sortRank(type) {
   var startTime =document.getElementById("master_repir_startTime").value;
	var endTime=document.getElementById("master_repir_endTime").value;
	if(type=="time"){
		document.getElementById("time_id").className="select";
	}else{
		document.getElementById("time_id").className="";
	}
	if(type=="praise"){
		document.getElementById("praise_id").className="select";
	}else{
		document.getElementById("praise_id").className="";
	}
	if(type=="content"){
		document.getElementById("content_id").className="select";
	}else{
		document.getElementById("content_id").className="";
	}
	getClickAmountList(type, startTime, endTime);
}

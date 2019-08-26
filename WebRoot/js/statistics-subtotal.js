;(function() {
	function summary() {
		alert("12313");
		schedule();
		$.ajax({
			url: "/api/v1/subtotalStatics/communities/" + window.subtotal.communityId,
			type: "GET",
			dataType: "json",
			success : function(data) {
				var infoes = data.info,
					click = infoes.click,
					category = ["今日", "昨日", "本周", "上周", "本月", "上月"],
					summaryHtml = "";
				
				for (var i = 0; i < category.length; i++) {
					var moduleClick = click[i].map;
					var totalCount = click[i].totalCount;
					alert(moduleClick.通知公告.userClick / totalCount);
					
					summaryHtml += "<tr class='" + (i % 2 ? "even" : "odd") + "'>";
					summaryHtml += "<td>" + category[i] + "</td>";
					summaryHtml += "<td><span>" + moduleClick.userClick + "</span><span class='green'>(" + moduleClick.testClick + ")</span></td>";
					summaryHtml += "<td><span>" + moduleClick.userCount + "</span><span class='green'>(" + moduleClick.testCount + ")</span></td>";
					summaryHtml += "<td><span>" + moduleInfo.buyNum + "</span></td>";
					summaryHtml += "<td><span>" + percent + "</span></td>";
					summaryHtml += "</tr>";
					
					if (i == 0) {//本日
						$("#dayBegin").html(click[i].begin);
						$("#dayEnd").html(click[i].end);
						
						$("#statistics-day").html("<tr class='even'>"
							+ "<td>" + click[i].begin + "</td>"
							+ "<td><span>" + moduleClick.userClick + "</span><span class='green'>(" + moduleClick.testClick + ")</span></td>"
							+ "<td><a href='/jsp/operation/welfare/statistics-click.jsp?time="+click[i].begin+"&q=1'><span>" + moduleClick.userCount + "</span><span class='green'>(" + moduleClick.testCount + ")</span></a></td>"
							+ "<td><a href='/jsp/operation/welfare/statistics-buy.jsp?time="+click[i].begin+"&q=1'><span>" + moduleInfo.buyNum + "</span></a></td>"
							+ "<td><span>" + percent + "</span></td>"
							+ "</tr>");
						
						currentDayAmount = 0;
					}
				}
				
				$("#statistics-summary").html(summaryHtml);
				
				onSchedule();
			},
			error: function() {
				onSchedule();
			}
		});
	}
	
	
	
	
	
	
	function stat(type, amount) {
		if (type == "day") {
			if (amount == 0 && currentDayAmount == 1) {
				currentDayAmount = 0;
			} else if (currentDayAmount >= 0 && amount > 0) {
				return;
			}
			
			currentDayAmount += amount;
			amount = currentDayAmount;
		} else if (type == "week") {
			if (amount == 0 && currentWeekAmount == 1) {
				currentWeekAmount = 0;
			} else if (currentWeekAmount >= 0 && amount > 0) {
				return;
			}
			
			currentWeekAmount += amount;
			amount = currentWeekAmount;
		} else if (type == "month") {
			if (amount == 0 && currentMonthAmount == 1) {
				currentMonthAmount = 0;
			} else if (currentMonthAmount >= 0 && amount > 0) {
				return;
			}
			
			currentMonthAmount += amount;
			amount = currentMonthAmount;
		}
		
		schedule();
		$.ajax({
			url: "/api/v1/welfareStatics/modules/19/communities/"+window.welfare.communityId+"?amount=" + amount + "&type=" + typeMap[type],
			type: "GET",
			dataType: "json",
			success : function(data) {
				var infoes = data.info,
					click = infoes.click,
					module = infoes.module,
					html = "";
				for (var i = 0; i < click.length; i++) {
					var moduleClick = getModuleClick(click[i].modules, currentServiceId),
						moduleInfo = module[i];
					var percent = (100*(moduleInfo.buyNum / moduleClick.userCount )).toFixed(2)+"%";
					
					html += "<tr class='odd'>";
					html += "<td>" + click[i].begin + "</td>";
					html += "<td><span>" + (moduleClick.userClick || "0") + "</span><span class='green'>(" + (moduleClick.testClick || "0") + ")</span></td>";
					html += "<td><a href='/jsp/operation/welfare/statistics-click.jsp?time="+click[i].begin+"&q=1'><span>" + (moduleClick.userCount || "0") + "</span><span class='green'>(" + (moduleClick.testCount || "0") + ")</span></a></td>";
					html += "<td><a href='/jsp/operation/welfare/statistics-buy.jsp?time="+click[i].begin+"&q=1'><span>" + (moduleInfo.buyNum || "0") + "</span></a></td>";
					html += "<td><span>" + (percent || "0") + "</span></td>";
					html += "</tr>";
				}
				
				$("#statistics-day").hide();
				$("#statistics-week").hide();
				$("#statistics-month").hide();
				$("#statistics-" + type).show();
				$("#statistics-" + type).html(html);
				$("#" + type + "Begin").html(click[0].begin);
				$("#" + type + "End").html(click[click.length - 1].end);
				
				onSchedule();
			},
			error: function() {
				onSchedule();
			}
		});
	}
	
	function statSelect() {
		var beginDate = $("#txtBeginDate").val(),
			endDate = $("#txtEndDate").val();
		
		if (!beginDate || !endDate || beginDate == '请选择开始日期' || endDate == '请选择结束日期') {
			alert("请选择起始日期!");
			return;
		}
		
		schedule();
		
		$("a.select").removeClass("select");
		$(".shop-dosage-total").hide();
		$("#list-select").show().siblings(".shop-dosage-list").hide();
		
		var start = parseInt(stringToTime(beginDate + " 00:00:00") / 1000, 10),
			end = parseInt(stringToTime(endDate + " 23:59:59") / 1000, 10);
		
		$.ajax({
			url: "/api/v1/welfareStatics/modules/19/communities/" + window.welfare.communityId + "?start=" + start + "&end=" + end,
			type: "GET",
			dataType: "json",
			success : function(data) {
				var infoes = data.info,
					click = infoes.click,
					module = infoes.module,
					html = "";
				
				for (var i = 0; i < click.length; i++) {
					var moduleClick = getModuleClick(click[i].modules, currentServiceId),
						moduleInfo = module[i];
					var percent = (100*(moduleInfo.buyNum / moduleClick.userCount )).toFixed(2)+"%";
					html += "<tr class='even'>";
					html += "<td>" + click[i].begin + "</td>";
					html += "<td><span>" + (moduleClick.userClick || "0") + "</span><span class='green'>(" + (moduleClick.testClick || "0") + ")</span></td>";
					html += "<td><span>" + (moduleClick.userCount || "0") + "</span><span class='green'>(" + (moduleClick.testCount || "0") + ")</span></td>";
					html += "<td><span>" + (moduleInfo.buyNum || "0") + "</span></td>";
					html += "<td><span>" + (percent || "0") + "</span></td>";
					html += "</tr>";
				}
				$("#statistics-select").html(html);
				
				onSchedule();
			},
			error: function() {
				onSchedule();
			}
		});
	}
	
	/**
	 * 切换tab
	 * @param el 当前被点击的tab
	 * @param type tab页所代表的统计类型
	 */
	function swithTab(el, type) {
		el = $(el);
		if (el.hasClass("select")) {
			return;
		}
		
		el.addClass("select").siblings().removeClass("select");
		$("#tab-" + type).show().siblings(".shop-dosage-total").hide();
		$("#list-" + type).show().siblings(".shop-dosage-list").hide();
		
		if (type == 'day') {
			if (currentDayAmount == 1) {
				stat('day', 0);
			}
		} else if (type == 'week') {
			if (currentWeekAmount == 1) {
				stat('week', 0);
			}
		} else if (type == 'month') {
			if (currentMonthAmount == 1) {
				stat('month', 0);
			}
		}
	}

	function getModuleClick(moduleClicks, serviceId) {
		for (var i = 0; i < moduleClicks.length; i++) {
			if (moduleClicks[i].serviceId == serviceId) {
				return moduleClicks[i];
			}
		}
		
		return {};
	}

	/**
	 * 格式化日期,将日期对象转化成yyyy-MM-dd格式的字符串
	 */
	function formatDate(date) {
		return date.getFullYear() + "-" + fix(date.getMonth() + 1) + "-" + fix(date.getDate());;
	}

	/**
	 * 为小于10，大于0的数字在首位补零
	 */
	function fix(date) {
		if (date > 9) {
			return date;
		}
		return "0" + date;
	}

	//隔离全局名称，防止变量名污染
	window.subtotal = window.subtotal || {};
	window.subtotal.summary = summary;
	window.subtotal.stat = stat;
	window.subtotal.statSelect = statSelect;
	window.subtotal.swithTab = swithTab;
	
	var currentServiceId = '19',//18 -> 周边店家
		typeMap = {'day': 1, 'week': 2, 'month': 3},
		currentDayAmount = 1,	//1代表未统计,从0开始到负数,表示统计的偏移天
		currentWeekAmount = 1,	//1代表未统计,从0开始到负数,表示统计的偏移周
		currentMonthAmount = 1;	//1代表未统计,从0开始到负数,表示统计的偏移月
})();
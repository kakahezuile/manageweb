;(function() {
	
	/**
	 * 统计指定的日期
	 * @param _date 指定的日期，如果没有指定该日期，则默认为今天
	 */
	function statDay(_date) {
		_date = _date || new Date();
		dayStat = true;
		currentDay = _date;
		
		var date = formatDate(_date),
			begin = stringToTime(date + " 00:00:00"),
			end = begin + 1000 * 60 * 60 * 24 - 1;
		
		$("#dayBegin").html(date);
		$("#dayEnd").html(date);
		
		daily(parseInt(begin / 1000, 10), parseInt(end / 1000, 10), 'day');
	}

	/**
	 * 统计statDay上次统计日期的前一天
	 */
	function prevDay() {
		statDay(new Date(currentDay.getTime() - 1000 * 60 * 60 * 24));
	}

	/**
	 * 统计statDay上次统计日期的下一天
	 */
	function nextDay() {
		statDay(new Date(currentDay.getTime() + 1000 * 60 * 60 * 24));
	}

	/**
	 * 统计指定日期的上一周
	 * @param _date 指定的日期，如果没有指定该日期，则默认为今天
	 */
	function statWeek(_date) {
		_date = _date || new Date();
		weekStat = true;
		currentWeek = _date;
		
		var lastWeekStartEnd = alterDate(1, '周', formatDate(_date)),
			begin = stringToTime(lastWeekStartEnd.start),
			end = stringToTime(lastWeekStartEnd.end) - 1;
		
		$("#weekBegin").html(formatDate(new Date(begin)));
		$("#weekEnd").html(formatDate(new Date(end)));
		
		daily(parseInt(begin / 1000, 10), parseInt(end / 1000, 10), 'week');
	}

	/**
	 * 统计statWeek上次统计日期的前一周
	 */
	function prevWeek() {
		statWeek(new Date(currentWeek.getTime() - 1000 * 60 * 60 * 24 * 7));
	}

	/**
	 * 统计statWeek上次统计日期的下一周
	 */
	function nextWeek() {
		statWeek(new Date(currentWeek.getTime() + 1000 * 60 * 60 * 24 * 7));
	}

	/**
	 * 统计指定日期的上一月
	 * @param _date 指定的日期，如果没有指定该日期，则默认为今天
	 */
	function statMonth(_date) {
		_date = _date || new Date();
		monthStat = true;
		currentMonth = _date;
		
		var lastMonthStartEnd = alterDate(1, '月', formatDate(_date)),
			begin = stringToTime(lastMonthStartEnd.start),
			end = stringToTime(lastMonthStartEnd.end) - 1;
		
		$("#monthBegin").html(formatDate(new Date(begin)));
		$("#monthEnd").html(formatDate(new Date(end)));
		
		daily(parseInt(begin / 1000, 10), parseInt(end / 1000, 10), 'month');
	}

	/**
	 * 统计statMonth上次统计日期的前一月
	 */
	function prevMonth() {
		statMonth(new Date(stringToTime(alterDate(1, '月', formatDate(currentMonth)).start)));
	}

	/**
	 * 统计statMonth上次统计日期的下一月
	 */
	function nextMonth() {
		statMonth(new Date(stringToTime(alterDate(2, '月', formatDate(currentMonth)).start)));
	}

	/**
	 * 统计选择的日期范围
	 */
	function statSelect() {
		var beginDate = $("#txtBeginDate").val(),
			endDate = $("#txtEndDate").val();
		
		if (!beginDate || !endDate || beginDate == '请选择开始日期' || endDate == '请选择结束日期') {
			alert("请选择起始日期!");
			return;
		}
		
		$("a.select").removeClass("select");
		$(".shop-dosage-total").hide();
		$("#list-select").show().siblings(".shop-dosage-list").hide();
		
		daily(parseInt(stringToTime(beginDate + " 00:00:00") / 1000, 10), parseInt(stringToTime(endDate + " 23:59:59") / 1000, 10), 'select');
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
			if (!dayStat) {
				statDay();
			}
		} else if (type == 'week') {
			if (!weekStat) {
				statWeek();
			}
		} else if (type == 'month') {
			if (!monthStat) {
				statMonth();
			}
		}
	}

	/**
	 * 获取分页信息
	 * @param type 分页操作类型
	 * 	type -1 上一页
	 * 	type -2 下一页
	 * 	type -3 首页
	 * 	type -4 尾页
	 * @returns {pageNum: 下一页的页码, pageSize: 10}
	 */
	function getPageInfo(type) {
		if (!type) {
			return {pageNum: 1, pageSize: 10};
		}
		
		var next = 1,
			pageNum = parseInt($("#pageNum").val(), 10),
			pageCount = parseInt($("#pageCount").val(), 10);
		
		if (type == -1) { // 上一页
			if (pageNum == 1) {
				alert("已经是第一页了");
				return;
			}
			next = pageNum - 1;
		} else if (type == -2) { // 下一页
			if (pageNum == pageCount) {
				alert("已经是最后一页了");
				return;
			}
			next = pageNum + 1;
		} else if (type == -3) { // 首页
			if (pageNum == 1) {
				alert("已经是首页了");
				return;
			}
			next = 1;
		} else if (type == -4) { // 尾页
			if (pageNum == pageCount) {
				alert("已经是尾页了");
				return;
			}
			next = pageCount;
		}
		
		return {pageNum: next, pageSize: 10};
	}

	/**
	 * 设置分页信息
	 */
	function setPageInfo(data) {
		$("#pageNum").val(data.num);
		$("#pageCount").val(data.pageCount);
		$("#pageNumText").html(data.num);
		$("#pageCountText").html(data.pageCount);
		$("#totalText").html(data.rowCount);
	}

	/**
	 * 按日统计
	 * @param begin 开始那一天的时间
	 * @param end 结束那一天的时间
	 * @param type 统计类型:day,week,month,select
	 */
	function daily(begin, end, type) {
		$.ajax({
			url: "/api/v1/crazySalesStatistics/daily?begin=" + begin + "&end=" + end,
			type: "GET",
			dataType: "json",
			success : function(data) {
				var infoes = data.info,
					html = "<tr><th>日期</th><th>下载量</th><th>注册量</th><th>发布活动量</th><th>出价总额</th></tr>",
					info,
					i;
				if (infoes.length == 0) {
					html += "<tr class='even'><td colspan='5' style='text-align:center;color:red;'>没有查询到数据!</td></tr>";
				} else {
					for (i = 0; i < infoes.length; i++) {
						info = infoes[i];
						html += "<tr class='" + (i / 2 == 0 ? "even" : "odd") + "'><td>" + info.date + "</td><td>" + info.download + "</td><td>" + info.register + "</td><td>" + info.pub + "</td><td>" + info.money + "</td></tr>";
					}
				}
				
				$("#list-" + type + " table").html(html);
			}
		});
	}

	/**
	 * 总览
	 */
	function summary() {
		$.ajax({
			url: "/api/v1/crazySalesStatistics/summary",
			type: "GET",
			dataType: "json",
			success : function(data) {
				var infoes = data.info,
					html = "<tr><th>日期</th><th>下载量</th><th>注册量</th><th>发布活动量</th><th>出价总额</th></tr>",
					info,
					i;
				if (infoes.length == 0) {
					html += "<tr class='even'><td colspan='5' style='text-align:center;color:red;'>没有查询到数据!</td></tr>";
				} else {
					for (i = 0; i < infoes.length; i++) {
						info = infoes[i];
						html += "<tr class='" + (i / 2 == 0 ? "even" : "odd") + "'><td>" + info.date + "</td><td>" + info.download + "</td><td>" + info.register + "</td><td>" + info.pub + "</td><td>" + info.money + "</td></tr>";
					}
				}
				
				$("#summary").html(html);
			}
		});
	}

	/**
	 * 商家的活动情况统计
	 * @param type 分页类型
	 */
	function shop(type) {
		var pageInfo = getPageInfo(type);
		if (!pageInfo) {
			return;
		}
		
		$.ajax({
			url: "/api/v1/crazySalesStatistics/shop?pageNum=" + pageInfo.pageNum + "&pageSize=" + pageInfo.pageSize,
			type: "GET",
			dataType: "json",
			success : function(data) {
				var infoes = data.info.pageData,
					html = "<tr><th>商家名称</th><th>活动覆盖小区数</th><th>发布活动数</th><th>总出价金额</th><th>总抢购量</th><th>总验码量</th><th>&nbsp;</th></tr>",
					info,
					i;
				if (infoes.length == 0) {
					html += "<tr class='even'><td colspan='7' style='text-align:center;color:red;'>没有查询到数据!</td></tr>";
				} else {
					for (i = 0; i < infoes.length; i++) {
						info = infoes[i];
						html += "<tr class='" + (i / 2 == 0 ? "even" : "odd") + "'><td>" + info.shopName + "</td><td>" + info.community + "</td><td>" + info.activity + "</td><td>" + info.price + "</td><td>" + info.sales + "</td><td>" + info.used + "</td><td><a href='./statistics-merchant-detail.jsp?shopEmobId=" + info.shopEmobId + "'>详情</a></td></tr>";
					}
				}
				
				$("#shop").html(html);
				setPageInfo(data.info);
			}
		});
	}

	/**
	 * 商家的活动详情统计
	 * @param shopEmobId 商家环信ID
	 */
	function shopDetails(shopEmobId) {
		$.ajax({
			url: "/api/v1/crazySalesStatistics/shopDetails?emobId=" + shopEmobId,
			type: "GET",
			dataType: "json",
			success : function(data) {
				var infoes = data.info,
					html = "<tr><th>抢购活动名称</th><th>发布小区</th><th>出价金额</th><th>限购量</th><th>抢购量</th><th>验码量</th></tr>",
					info,
					i;
				if (infoes.length == 0) {
					html += "<tr class='even'><td colspan='6' style='text-align:center;color:red;'>没有查询到数据!</td></tr>";
				} else {
					for (i = 0; i < infoes.length; i++) {
						info = infoes[i];
						html += "<tr class='" + (i / 2 == 0 ? "even" : "odd") + "'><td>" + info.title + "</td><td>" + info.communityName + "</td><td>" + info.price + "</td><td>" + info.total + "</td><td>" + info.sales + "</td><td>" + info.used + "</td></tr>";
					}
				}
				
				$("#shopDetails").html(html);
			}
		});
	}

	/**
	 * 小区的活动情况统计
	 * @param type 分页类型
	 */
	function community(type) {
		var pageInfo = getPageInfo(type);
		if (!pageInfo) {
			return;
		}
		
		$.ajax({
			url: "/api/v1/crazySalesStatistics/community?pageNum=" + pageInfo.pageNum + "&pageSize=" + pageInfo.pageSize,
			type: "GET",
			dataType: "json",
			success : function(data) {
				var infoes = data.info.pageData,
					html = "<tr><th>小区名称</th><th>周边商家</th><th>发布活动数</th><th>总出价金额</th><th>总抢购量</th><th>总验码量</th><th>&nbsp;</th></tr>",
					info,
					i;
				if (infoes.length == 0) {
					html += "<tr class='even'><td colspan='7' style='text-align:center;color:red;'>没有查询到数据!</td></tr>";
				} else {
					for (i = 0; i < infoes.length; i++) {
						info = infoes[i];
						html += "<tr class='" + (i / 2 == 0 ? "even" : "odd") + "'><td>" + info.communityName + "</td><td>" + info.shops + "</td><td>" + info.activity + "</td><td>" + info.price + "</td><td>" + info.sales + "</td><td>" + info.used + "</td><td><a href='./statistics-community-detail.jsp?communityId=" + info.communityId + "'>详情</a></td></tr>";
					}
				}
				
				$("#community").html(html);
				setPageInfo(data.info);
			}
		});
	}

	/**
	 * 小区活动详情统计
	 * @param communityId 小区ID
	 */
	function communityDetails(communityId) {
		$.ajax({
			url: "/api/v1/crazySalesStatistics/communityDetails?communityId=" + communityId,
			type: "GET",
			dataType: "json",
			success : function(data) {
				var infoes = data.info,
					html = "<tr><th>抢购活动名称</th><th>发布商家</th><th>出价金额</th><th>限购量</th><th>抢购量</th><th>验码量</th></tr>",
					info,
					i;
				if (infoes.length == 0) {
					html += "<tr class='even'><td colspan='6' style='text-align:center;color:red;'>没有查询到数据!</td></tr>";
				} else {
					for (i = 0; i < infoes.length; i++) {
						info = infoes[i];
						html += "<tr class='" + (i / 2 == 0 ? "even" : "odd") + "'><td>" + info.title + "</td><td>" + info.shopName + "</td><td>" + info.price + "</td><td>" + info.total + "</td><td>" + info.sales + "</td><td>" + info.used + "</td></tr>";
					}
				}
				
				$("#communityDetails").html(html);
			}
		});
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
	window.nearby = window.nearby || {};
	window.nearby.prevDay = prevDay;
	window.nearby.nextDay = nextDay;
	window.nearby.prevWeek = prevWeek;
	window.nearby.nextWeek = nextWeek;
	window.nearby.prevMonth = prevMonth;
	window.nearby.nextMonth = nextMonth;
	window.nearby.statSelect = statSelect;
	window.nearby.swithTab = swithTab;
	
	window.nearby.summary = summary;
	window.nearby.statDay = statDay;
	window.nearby.shop = shop;
	window.nearby.shopDetails = shopDetails;
	window.nearby.community = community;
	window.nearby.communityDetails = communityDetails;
	
	var dayStat = false,
		weekStat = false,
		monthStat = false,
		currentDay,
		currentWeek,
		currentMonth;
})();
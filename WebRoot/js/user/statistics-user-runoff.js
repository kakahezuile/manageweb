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
	$(".operation-nav").find("ul li a[mark=user]").addClass("select");
	$(".left-body").find("ul li a").removeClass("select");
	$(".left-body").find("ul li a[mark=user]").addClass("select");
}

function getMonthSetupInfo(el, comparisonYear, comparisonMonth) {
	if (el) {
		$(el).attr("disabled", "disabled");
	}
	
	var year = !!$("#year").val() ? parseInt($("#year").val(), 10) : 0,
		month = !!$("#month").val() ? parseInt($("#month").val(), 10) : 0,
		isFirstQuery = false;
	
	if (year == 0 || month == 0) {
		alert("请先选择月份!");
		return;
	}
	
	if (!comparisonYear || !comparisonMonth) {
		comparisonYear = month == 12 ? year + 1 : year;
		comparisonMonth = month == 12 ? 1 : month + 1;
		isFirstQuery = true;
	}
	
	$.ajax({
		url : "/api/v1/runoffUserStatistics/communities/" + communityId + "?year=" + year + "&month=" + month + "&comparisonYear=" + comparisonYear + "&comparisonMonth=" + comparisonMonth,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			var users = data.info.users,
				userClickMap = data.info.userClickMap,
				all = users.length;
			
			if (isFirstQuery) {
				var runoff = 0,
					html = "";
				for (var i = 0; i < all; i++) {
					var user = users[i],
						clickCount = userClickMap[user.emobId];
					
					if (!clickCount) {
						clickCount = "<span style='color:red'>未活跃</span>";
						runoff++;
					} else {
						clickCount = "<span style='color:green'>" + clickCount + "</span>";
					}
					
					html += "<tr id='" + user.emobId + "'><td>" + (year + "-" + month + "-" +getDate(user.createTime)) + "</td><td>" + (user.username || "未注册") + "</td><td>" + (user.nickname || "游客") + "</td><td>" + clickCount + "</td></tr>";
				}
				
				var head = "<tr id='tr-head'><th>安装时间</th><th>注册账号</th><th>昵称</th><th>" + comparisonYear + "年" + comparisonMonth + "月" + getNextComparisonMonth(comparisonYear, comparisonMonth) + "</th></tr>";
				var statistics = "<tr id='tr-statistics'><td colspan='3'>总计: " + all + "</td><td>活跃: " + (all - runoff) + "<br>未活跃: " + runoff + "<br>流失占比: " + (runoff / all * 100).toFixed(2) + "%</td></tr>";
				$("#data-talbe").html(head + statistics + html);
			} else {
				var runoff = 0;
				$("#data-talbe tr").each(function() {
					var id = this.id;
					if (id == "tr-head") {
						$(this).append("<th>" + comparisonYear + "年" + comparisonMonth + "月" + getNextComparisonMonth(comparisonYear, comparisonMonth) + "</th>");
						return true;
					}
					if (id == "tr-statistics") {
						return true;
					}
					
					var clickCount = userClickMap[id];
					if (!clickCount) {
						clickCount = "<span style='color:red'>未活跃</span>";
						runoff++;
					} else {
						clickCount = "<span style='color:green'>" + clickCount + "</span>";
					}
					
					$(this).append("<td>" + clickCount + "</td>");
				});
				
				$("#tr-statistics").append("<td>活跃: " + (all - runoff) + "<br>未活跃: " + runoff + "<br>流失占比: " + (runoff / all * 100).toFixed(2) + "%</td>");
			}
			
			$(el).remove();
		},
		error: function() {
			$(el).removeAttr("disabled");
		}
	});
}

function getDate(unixTime) {
	return new Date(unixTime * 1000).getDate();
}

function getNextComparisonMonth(comparisonYear, comparisonMonth) {
	var date = new Date();
	if (comparisonYear == date.getFullYear() && (new Date().getMonth() + 1) == comparisonMonth) {
		return "";
	}
	
	var nextComparisonYear = comparisonMonth == 12 ? comparisonYear + 1 : comparisonYear;
	var nextComparisonMonth = comparisonMonth == 12 ? 1 : comparisonMonth + 1;
	
	return "<button title='点击查看下一月' onclick='getMonthSetupInfo(this," + nextComparisonYear + "," + nextComparisonMonth + ")'>下月</button>";
}

$(function() {
	var html = "",
		now = new Date(),
		year = now.getFullYear(),
		month = now.getMonth() + 1,
		yearValue = month == 1 ? year - 1 : year,
		monthValue = month == 1 ? 12 : month - 1,
		yearEl = $("#year"),
		monthEl = $("#month"),
		i;
	for (i = 2015; i <= yearValue; i++) {
		html += "<option value='" + i + "'>" + i + " 年</option>";
	}
	yearEl.html(html).val(yearValue);
	
	html = "";
	for (i = 1; i <= monthValue; i++) {
		html += "<option value='" + i + "'>" + i + " 月</option>";
	}
	monthEl.html(html).val(monthValue);
	
	yearEl.change(function() {
		if (parseInt(this.value, 10) == yearValue) {
			html = "";
			for (i = 1; i <= monthValue; i++) {
				html += "<option value='" + i + "'>" + i + " 月</option>";
			}
			monthEl.html(html).val(monthValue);
		} else {
			html = "";
			for (i = 1; i <= 12; i++) {
				html += "<option value='" + i + "'>" + i + " 月</option>";
			}
			monthEl.html(html).val(1);
		}
	});
	
	getMonthSetupInfo();
});
// 日历控件
$(function() {
	var is=document.getElementById("txtBeginDate");
		if(!is==null){
		$("#txtBeginDate").calendar({
			controlId : "divDate", // 弹出的日期控件ID，默认: $(this).attr("id") + "Calendar"
			speed : 200, // 三种预定速度之一的字符串("slow", "normal", or
			// "fast")或表示动画时长的毫秒数值(如：1000),默认：200
			complement : true, // 是否显示日期或年空白处的前后月的补充,默认：true
			readonly : true, // 目标对象是否设为只读，默认：true
			upperLimit : new Date(), // 日期上限，默认：NaN(不限制)
			lowerLimit : new Date("2011/01/01"), // 日期下限，默认：NaN(不限制)
			callback : function() { // 点击选择日期后的回调函数
				// alert("您选择的日期是：" + $("#txtBeginDate").val());
			}
		});
		$("#txtEndDate").calendar();
	}
});
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
 * 上周
 * @param time
 * @returns {___anonymous242_307}
 */
function getWeekUp(time) {

    var date = new Date(time) || date, day, start, end, dayMSec = 24 * 3600 * 1000;

	

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


//扩展Date的format方法   
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1,
		"d+" : this.getDate(),
		"h+" : this.getHours(),
		"m+" : this.getMinutes(),
		"s+" : this.getSeconds(),
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		"S" : this.getMilliseconds()
	};
	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};
/**   
 *转换日期对象为日期字符串   
 * @param date 日期对象   
 * @param isFull 是否为完整的日期数据,   
 *               为true时, 格式如"2000-03-05 01:05:04"   
 *               为false时, 格式如 "2000-03-05"   
 * @return 符合要求的日期字符串   
 */
function getSmpFormatDate(date, isFull) {
	var pattern = "";
	if (isFull == true || isFull == undefined) {
		pattern = "yyyy-MM-dd hh:mm:ss";
	} else {
		pattern = "yyyy-MM-dd";
	}
	return getFormatDate(date, pattern);
}
/**   
 *转换当前日期对象为日期字符串   
 * @param date 日期对象   
 * @param isFull 是否为完整的日期数据,   
 *               为true时, 格式如"2000-03-05 01:05:04"   
 *               为false时, 格式如 "2000-03-05"   
 * @return 符合要求的日期字符串   
 */

function getSmpFormatNowDate(isFull) {
	return getSmpFormatDate(new Date(), isFull);
}
/**   
 *转换long值为日期字符串   
 * @param l long值   
 * @param isFull 是否为完整的日期数据,   
 *               为true时, 格式如"2000-03-05 01:05:04"   
 *               为false时, 格式如 "2000-03-05"   
 * @return 符合要求的日期字符串   
 */

function getSmpFormatDateByLong(l, isFull) {
	return getSmpFormatDate(new Date(l), isFull);
}
/**   
 *转换long值为日期字符串   
 * @param l long值   
 * @param pattern 格式字符串,例如：yyyy-MM-dd hh:mm:ss   
 * @return 符合要求的日期字符串   
 */

function getFormatDateByLong(l, pattern) {
	return getFormatDate(new Date(l), pattern);
}
/**   
 *转换日期对象为日期字符串   
 * @param l long值   
 * @param pattern 格式字符串,例如：yyyy-MM-dd hh:mm:ss   
 * @return 符合要求的日期字符串   
 */
function getFormatDate(date, pattern) {
	if (date == undefined) {
		date = new Date();
	}
	if (pattern == undefined) {
		pattern = "yyyy-MM-dd hh:mm:ss";
	}

	return date.format(pattern);
}
function myFormatSeconds(value) {

	var theTime = parseInt(value);// 秒

	var theTime1 = 0;// 分

	var theTime2 = 0;// 小时

	 	if(theTime > 60) {

    	theTime1 = parseInt(theTime/60);

    	theTime = parseInt(theTime%60);

        	if(theTime1 > 60) {

        		theTime2 = parseInt(theTime1/60);

        		theTime1 = parseInt(theTime1%60);

        }

	}

    var result = ""+parseInt(theTime)+"秒";

    if(theTime1 > 0) {

    	result = ""+parseInt(theTime1)+"分"+result;

    }

    if(theTime2 > 0) {

    	result = ""+parseInt(theTime2)+"小时"+result;

    }

	return result;

}
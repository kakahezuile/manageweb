// 日历控件
$(function() {
	try {
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
	} catch(ex) {
		console.log(ex);
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

function getStringTime(time) {
	var myDate=new Date(time);
	var month=myDate.getMonth()+1;
	return myDate.getFullYear()+"-"+month+"-"+myDate.getDate();
}
		
		

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
	if(today ==-1){
		end = date.getTime() - 6* dayMSec;
	}else{
		
		end = date.getTime() - today* dayMSec;
	}
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
 *  结款
 */
function getPayWeekBenUp(date) {
	
var date = new Date() || date, day, start, end, dayMSec = 24 * 3600 * 1000;
	
	today = date.getDay() - 1;
	if(today==1){
		start = date.getTime() - ((today +3)* dayMSec);
		
		end =date.getTime();
		
	}else{
		start = date.getTime() - ((today-2) * dayMSec);
		
		end = date.getTime();
		
	}
	
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
 * 本周
 * @param time
 * @returns {___anonymous242_307}
 */
function getWeekBenUp(date) {
	
	var date = new Date() || date, day, start, end, dayMSec = 24 * 3600 * 1000;
	
	today = date.getDay() - 1;
	if(today==-1){
		start = date.getTime() -  6* dayMSec;
	}else{
		start = date.getTime() - today * dayMSec;
	}
	
	end = start + 7 * dayMSec;
	
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
	if(today ==-1){
		end = date.getTime() - 6* dayMSec;
	}else{
		
		end = date.getTime() - today* dayMSec;
	}
	
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
function repairPriceUpdateJsp() {
	setNone();
	$("#repair_price_div_id").attr("style", "display:block");
	repairPriceGetItemCategory(5);
}

function timeQuantum(startTime,endTime) {
	var myDate = new Date(stringToTime(startTime));
	var myDate2 = new Date(stringToTime(endTime));
	var startTimeFilter = myDate.getMonth() + 1 + "月"
			+ myDate.getDate() + "日";
	var endTimeFilter = myDate2.getMonth() + 1 + "月"
			+ myDate2.getDate() + "日";

	document.getElementById("statistics_date_1").innerHTML = startTimeFilter;
	document.getElementById("statistics_date_2").innerHTML = endTimeFilter;
	document.getElementById("master_repir_startTime").value = startTime;
	document.getElementById("master_repir_endTime").value = endTime;
} 
function timeDisplay(type) {
	if (type == '月') {

		document.getElementById("date_type_1").innerHTML = "上一月";
		document.getElementById("date_type_2").innerHTML = "下一月";

		
	} else if (type == '周') {
		document.getElementById("date_type_1").innerHTML = "上一周";
		document.getElementById("date_type_2").innerHTML = "下一周";

	} else {
		document.getElementById("date_type_1").innerHTML = "上一日";
		document.getElementById("date_type_2").innerHTML = "下一日";

		
	}

	document.getElementById("date_type_get").value = type;

}

function getMasterDetailPage(type,pageNum,pageSize) {
	var page_num=0;
	if (type == -1) { // 上一页

		if (pageNum != 1) {
			page_num = pageNum - 1;
		} else {
			alert("已经是第一页了");
			return 0;
		}

	} else if (type == -2) { // 下一页
		if (parseInt(pageNum) < parseInt(pageSize)) {
			page_num = parseInt(pageNum) + parseInt(1);
		} else {
			alert("已经是最后一页了");
			return 0;
		}

	} else if (type == -3) { // 首页
		if (pageNum != 1) {
			page_num = 1;
		} else {
			alert("已经是首页了");
			return 0;
		}
	} else if (type == -4) { // 尾页
		if (parseInt(pageNum) < parseInt(pageSize)) {
			page_num = pageSize;
		} else {
			alert("已经是尾页了");
			return 0;
		}
	}
	return page_num;
}

function alterDate(num,type,startTime) {
	var timeLong = stringToTime(startTime);
	var myDate=new Date(timeLong);
	var startTime = "";
	var endTime = "";
	if (num == 1) {
		if (type == '周') {

			var d = getWeekUp(timeLong);
			// alert(d.start+d.end);
			var startTime = d.start;
			var endTime = d.end;

		} else if (type == "月") {
			var month = myDate.getMonth() + 1;
			startTime = myDate.getFullYear() + "-" + myDate.getMonth() + "-"
					+ 1 + " 00:00:00";
			endTime = myDate.getFullYear() + "-" + month + "-" + 1
					+ " 00:00:00";
		} else {
			var month = myDate.getMonth() + 1;
			startTime = myDate.getFullYear() + "-" + month + "-"
					+ (myDate.getDate() - 1) + " 00:00:00";
			endTime = myDate.getFullYear() + "-" + month + "-"
					+ myDate.getDate() + " 00:00:00";
		}

	} else if (num == 2) {

		if (type == '周') {

			var d = getNextwWeek(timeLong);
			var startTime = d.start;
			var endTime = d.end;

		} else if (type == "月") {
			var month = myDate.getMonth() + 2;
			var month2 = month + 1;
			startTime = myDate.getFullYear() + "-" + month + "-" + 1
					+ " 00:00:00";
			endTime = myDate.getFullYear() + "-" + month2 + "-" + 1
					+ " 00:00:00";
		} else {
			var month = myDate.getMonth() + 1;
			startTime = myDate.getFullYear() + "-" + month + "-"
					+ (myDate.getDate() + 1) + " 00:00:00";
			endTime = myDate.getFullYear() + "-" + month + "-"
					+ (myDate.getDate() + 2) + " 00:00:00";
		}

	} else {
		var month = myDate.getMonth() + 1;
		startTime = myDate.getFullYear() + "-" + myDate.getMonth() + "-" + 1
				+ " 00:00:00";
		endTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";

	}
	return{
		start : startTime,
		end : endTime
	};
}

function show(startTime, endTime) {
	var getDate = function(str) {
		var tempDate = new Date();
		var list = str.split("-");
		tempDate.setFullYear(list[0]);
		tempDate.setMonth(list[1] - 1);
		tempDate.setDate(list[2]);
		return tempDate;
	};
	var date1 = getDate(startTime);
	var date2 = getDate(endTime);
	if (date1 > date2) {
		var tempDate = date1;
		date1 = date2;
		date2 = tempDate;
	}
	date1.setDate(date1.getDate() + 1);
	var dates = [startTime],
		id = 1;
	while (!(date1.getFullYear() == date2.getFullYear() && date1.getMonth() == date2.getMonth() && date1.getDate() == date2.getDate())) {
		dates[id] = date1.getFullYear() + "-" + (date1.getMonth() + 1) + "-" + date1.getDate();
		id++;
		date1.setDate(date1.getDate() + 1);
	}
	dates[dates.length] = endTime;
	return dates;
};


function schedule(){
	$("#add-price-box").attr("style","display:block");
	$("#upload-master-face-bg").attr("style","display:block");
	
}

function onSchedule(){
	$("#add-price-box").attr("style","display:none");
	$("#upload-master-face-bg").attr("style","display:none");
}

function toStringTime(time) {
	var myDate=new Date(time);
	return myDate.getHours()+":"+myDate.getMinutes()+":"+myDate.getSeconds();
}


function toStringTimeHs(time) {
	var myDate=new Date(time);
	var month=myDate.getMonth()+1;
	return  myDate.getFullYear()+"-"+month+"-"+myDate.getDate() +" "+myDate.getHours()+":"+myDate.getMinutes()+":"+myDate.getSeconds();
}
function unique(arr) {
    var result = [], isRepeated;
    for (var i = 0, len = arr.length; i < len; i++) {
        isRepeated = false;
        for (var j = 0, len = result.length; j < len; j++) {
            if (arr[i] == result[j]) {   
                isRepeated = true;
                break;
            }
        }
        if (!isRepeated) {
            result.push(arr[i]);
        }
    }
    return result;
}
Array.prototype.unique5 = function(){
	 this.sort(); //先排序
	 var res = [this[0]];
	 for(var i = 1; i < this.length; i++){
	  if(this[i] !== res[res.length - 1]){
	   res.push(this[i]);
	  }
	 }
	 return res;
};



Array.prototype.unique2 = function(){
	 var res = [];
	 var json = {};
	 for(var i = 0; i < this.length; i++){
	  if(!json[this[i]]){
	   res.push(this[i]);
	   json[this[i]] = 1;
	  }
	 }
	 return res;
	};


function sleep(numberMillis) { 
	   var now = new Date();
	   var exitTime = now.getTime() + numberMillis;  
	   while (true) { 
	       now = new Date(); 
	       if (now.getTime() > exitTime)    return;
	    }
}
function unique3(arr) {
    var result = [], hash = {};
    for (var i = 0, elem; (elem = arr[i]) != null; i++) {
        if (!hash[elem]) {
            result.push(elem);
            hash[elem] = true;
        }
    }
    return result;
}

function shi(date) {
	var dd=getWeekBenUp(new Date(date));
	var ddd=getWeekUp(date);
//	alert(dd.start+"---"+dd.end);
	//alert(ddd.start+"---"+ddd.end);
}


//test();
function selectMenu(name) {
	$(".operation-nav").find("ul li a[mark='" + name + "']").addClass("select").siblings().removeClass("select");
	$(".left-body").find("ul li a[mark='" + name + "']").addClass("select").siblings().removeClass("select");
}

function preview(file, img) {
	img = $(img).get(0);
	if ("Microsoft Internet Explorer" == navigator.appName && navigator.appVersion.indexOf("MSIE 6.0") != -1) {
		img.style.display = "block";
		img.src = file.value;
	} else if ("Microsoft Internet Explorer" == navigator.appName && navigator.appVersion.indexOf("MSIE 9.0") != -1
			|| "Microsoft Internet Explorer" == navigator.appName && navigator.appVersion.indexOf("MSIE 8.0") != -1
			|| "Microsoft Internet Explorer" == navigator.appName && navigator.appVersion.indexOf("MSIE 7.0") != -1) {
		file.select();
		if (window.parent) {
			window.parent.focus();
		} else {
			file.blur();
		}
		var path = document.selection.createRange().text;
		document.selection.empty();
		img.parentNode.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src='" + path + "')";
		
		img.src = path;
	} else if (navigator.userAgent.indexOf("Firefox") > 0) {
		img.style.display = "block";
		img.src = window.URL.createObjectURL(file.files.item(0));
	} else {
		var reader = new FileReader();
		reader.onload = function() {
			img.src = reader.result;
			img.style.display = "block";
		};
		reader.readAsDataURL(file.files[0]);
	}
}

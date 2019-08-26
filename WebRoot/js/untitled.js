//2014-09-11 11:11:00格式的时间转时间戳
function strto_time(str_time) {
    var new_str = str_time.replace(/:|\s/g, '-'),
    arr = new_str.split("-"),
    datum = new Date(Date.UTC(arr[0],arr[1]-1,arr[2],arr[3]-8,arr[4],arr[5]));
    return (datum.getTime());
}

//计算并返回结果
function parseTime(time) {
	var now = new Date().getTime(),
	timePriont = strto_time(time),
	res = now - timePriont,
	isOld = res > 0 ? true : false,
	tesTime = '';
	res = Math.abs(res)/1000;

	if(res >= 60*60) {
		return Math.floor(res/60/60) + "时" + Math.floor((res - Math.floor(res/60/60)*60*60)/60) + "分" 
			+ Math.floor(res - Math.floor(res/60/60)*60*60 - Math.floor((res - Math.floor(res/60/60)*60*60)/60)*60) + "秒";

	} else if(res >= 60) {
		return Math.floor(res/60) + "分" + Math.floor(res - Math.floor(res/60)*60) + "秒";
	} else {
		return res + "秒";
	}
}

//调用方法举例：
parseTime('2015-03-06 16:33:30');
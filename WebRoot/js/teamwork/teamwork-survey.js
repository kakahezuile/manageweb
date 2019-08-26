function installsNum() {
	//var community_id=document.getElementById("community_id").value;
	var emobId=document.getElementById("emobId").value;
		var path = "/api/v1/communities/3/userStatistics/getUserRegisterCommunity/"+emobId;
		$.ajax({
			url : path,
			type : "GET",
			dataType : 'json',
			success : function(data) {
				data = data.info;
				
				var repair_overman = $("#communities_table");
				repair_overman.empty();
				repair_overman.append("<tr><th>小区名称</th><th>安装量</th><th>注册量</th><th>昨日安装活跃度</th><th>上周安装活跃度</th><th>昨日注册活跃度</th><th>上周注册活跃度</th></tr>");
				var liList="";
				for ( var i = 0; i < data.length; i++) {
					 thisClickNum2(data[i].communityId) ;
					
					liList+="<tr class=\"odd\">";
					liList+="<td >"+data[i].communityName+"</td>";
					liList+="<td >"+data[i].installsNum+"</td>";
					liList+="<td >"+data[i].registerNum+"</td>";

					liList+="<td id=\"to_day_"+data[i].communityId+"_xiao\"></td>";
					liList+="<td id=\"last_week_"+data[i].communityId+"_xiao\"></td>";
					liList+="<td id=\"to_day_"+data[i].communityId+"_register_xiao\"></td>";
					liList+="<td id=\"last_week_"+data[i].communityId+"_register_xiao\"></td>";

				}
				repair_overman.append(liList);
				registerNum= data.registerNum;
				onSchedule();
			},
			error : function(er) {
				onSchedule();
			}
		});
}
function  leftSelect() {
	var community_id=document.getElementById("community_id").value;
	document.getElementById("community_id_"+community_id).className="select";
	
}







/**活跃度统计呢*/


var this_week_an=0;
var this_week_zu=0;

var last_week_an=0;
var last_week_zu=0;

var to_day_an=0;
var to_day_zu=0;

var this_data_an=0;
var this_data_zu=0;
var map={};
var mapCommunityId={};
function thisClickNum2(communi) {
//	var path = "/api/v1/communities/1/users/1/orderHistories/getClickAmount?url=http://120.24.232.121:8080/statUsers/stat/getStat.do";
	var path="/stat/getStat.do?communityId="+communi;
    $.ajax({
	url : path,
	type : "GET",
	dataType : 'json',
	success : function(data) {
		
		
		var to_day=data.yesterday;
		
		map["to_day_an_"+communi]=to_day[1];
		map["to_day_zu_"+communi]=to_day[3];
		
		var last_week=data.lastWeek;
		
		map["last_week_an_"+communi]=last_week[1];
		map["last_week_zu_"+communi]=last_week[3];
		getTop(communi);
	},
	error : function(er) {
	}
});
}
function getUserTop() {
	var path = "/api/v1/communities/"+communityId+"/userStatistics/getUserRegister";
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			zong=data.installsNum;
			
			
			
			document.getElementById("zong_num").innerHTML = data.registerNum;
			document.getElementById("zong_num_an").innerHTML = data.installsNum-data.testNum;
			

		},
		error : function(er) {
		}
	});
}


function  getUserRegisterTime(biao,startTime,endTime,communi) {
	var path = "/api/v1/communities/"+communi+"/userStatistics/getUserRegisterEndTime?endTime="+endTime;
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			var total=0;
			var testTotal=0;
			var registerList=0;
			var listUn=0;
			var listUn2=0;
		    var registerUser=data.installsNum-data.testNum;
		    var registerNum=data.registerNum;
			var an=map[biao+"_an_"+communi];
			var zu=map[biao+"_zu_"+communi];
			//document.getElementById(biao).innerHTML = an;
			var top_user = 0;
			var registerUser_user = 0;
			
			if(registerNum>0){
				top_user = (((zu /registerNum)) * 100).toFixed(2);
			}
			if(registerUser>0){
				registerUser_user = (((an /registerUser)) * 100).toFixed(2);
			}
		
			
			
			document.getElementById(biao+"_"+communi+"_xiao").innerHTML = registerUser_user+"%";
			//document.getElementById(biao+"_register_huo").innerHTML = zu;
			document.getElementById(biao+"_"+communi+"_register_xiao").innerHTML = top_user+"%";
		},
		error : function(er) {
		}
	});
}


function active(biao,startTime,endTime,registerNum,registerUser,installsUser) {
    var myDate = new Date(stringToTime(startTime));
	var myDate2 = new Date(stringToTime(endTime));
	var dates = Math.abs((myDate2 - myDate)) / (1000 * 60 * 60 * 24);
	
    var path = "/api/v1/communities/"+communityId+"/userStatistics/getLaunchesStatistics?startTime="+startTime+"&endTime="+endTime;
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			var total=0;
			var testTotal=0;
			var registerList=0;
			var listUn=0;
			var listUn2=0;
			var listU1=new Array();
			var listU=	mapActive[biao];
		
			
			for ( var i = 0; i < data.length; i++) {
				
				if(mapUser[data[i].userClick.emobId]=="1"){
					if(data[i].userClick.emobId!="null"&&data[i].userClick.emobId!=-1){
						if(mapTryOut[data[i].userClick.emobId]!="1"){
							listU1[listUn++]=data[i].userClick.emobId;
							total++;
						}else{
							testTotal++;
						}
					}
				}
			}
			
		    listU1=	listU1.concat(listU);
		    listU1=listU1.unique2();
		    total=listU1.length;
		    for ( var i = 0; i < listU1.length; i++) {
		    	if(mapRegisterList[listU1[i]]=="1"){
					registerList++;
				}
			}
			document.getElementById(biao).innerHTML = total;
			var top_user = 0;
			var registerUser_user = 0;
			
			if(registerNum>0){
				top_user = (((total /registerNum)) * 100).toFixed(2);
			}
			if(registerUser>0){
				registerUser_user = (((registerList /registerUser)) * 100).toFixed(2);
			}
		
			
			
			document.getElementById(biao+"_xiao").innerHTML = top_user+"%";
			document.getElementById(biao+"_register_huo").innerHTML = registerList;
			document.getElementById(biao+"_register_xiao").innerHTML = registerUser_user+"%";
		},
		error : function(er) {
		}
	});
}


function thisUserData(communi){
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate()-1;
	var startTime = myDate.getFullYear() + "-" + month + "-" +da 
	+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" + myDate.getDate() + " 00:00:00";

	getUserRegisterTime('to_day',startTime,endTime,communi);
	
}
function weekUser(communi) {

	var d = getPreviousWeekStartEnd();
	var startTime = d.start;
	var endTime = d.end;
	getUserRegisterTime('last_week',startTime,endTime,communi);

}

function getTop(communi) {
	thisUserData(communi);

	weekUser(communi) ;
	
	
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


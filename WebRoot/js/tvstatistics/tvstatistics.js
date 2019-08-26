var communityId=3;

var this_week_an=0;
var this_week_zu=0;

var last_week_an=0;
var last_week_zu=0;

var to_day_an=0;
var to_day_zu=0;

var this_data_an=0;
var this_data_zu=0;
var map={};
function getCommunityId(communit) {
  var top_name=	document.getElementsByName("top_name");
  for ( var i = 0; i < top_name.length; i++) {
	  top_name[i].className="";
	  
   }
	document.getElementById("top_"+communit).className="select";
	
	communityId=communit;
	thisClickNum2();
}
function thisClickNum2() {
//	var path = "/api/v1/communities/1/users/1/orderHistories/getClickAmount?url=http://120.24.232.121:8080/statUsers/stat/getStat.do";
	var path="/stat/getStat.do?communityId="+communityId;
    $.ajax({
	url : path,
	type : "GET",
	dataType : 'json',
	success : function(data) {
		var week=data.Week;
		
		map["this_week_an"]=week[1];
		map["this_week_zu"]=week[3];
		
		var this_date=data.today;
		
		map["this_data_an"]=this_date[1];
		map["this_data_zu"]=this_date[3];
		
		var to_day=data.yesterday;
		
		map["to_day_an"]=to_day[1];
		map["to_day_zu"]=to_day[3];
		
		var last_week=data.lastWeek;
		
		map["last_week_an"]=last_week[1];
		map["last_week_zu"]=last_week[3];
		getUserTop();
		getTop();
	},
	error : function(er) {
	}
});
}
thisClickNum2();
setInterval("thisClickNum2()",1000*60*2);
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


function  getUserRegisterTime(biao,startTime,endTime) {
	var path = "/api/v1/communities/"+communityId+"/userStatistics/getUserRegisterEndTime?endTime="+endTime;
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
			var an=map[biao+"_an"];
			var zu=map[biao+"_zu"];
			
			
			document.getElementById(biao).innerHTML = an;
			var top_user = 0;
			var registerUser_user = 0;
			
			if(registerNum>0){
				top_user = (((zu /registerNum)) * 100).toFixed(2);
			}
			if(registerUser>0){
				registerUser_user = (((an /registerUser)) * 100).toFixed(2);
			}
		
			
			
			document.getElementById(biao+"_xiao").innerHTML = registerUser_user+"%";
			document.getElementById(biao+"_register_huo").innerHTML = zu;
			document.getElementById(biao+"_register_xiao").innerHTML = top_user+"%";
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

function quickUserData(){
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + myDate.getDate()
			+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
	getUserRegisterTime('this_data',startTime,endTime);
	
}
function thisUserData(){
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate()-1;
	var startTime = myDate.getFullYear() + "-" + month + "-" +da 
	+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" + myDate.getDate() + " 00:00:00";

	getUserRegisterTime('to_day',startTime,endTime);
	
}
function weekUser() {

	var d = getPreviousWeekStartEnd();
	var startTime = d.start;
	var endTime = d.end;
	getUserRegisterTime('last_week',startTime,endTime);

}
function benWeekUser() {
	
	var d = getWeekBenUp();
	var startTime = d.start;
	var endTime = d.end;
	
	getUserRegisterTime('this_week',startTime,endTime);
	
}
function getTop() {
	thisUserData();
	quickUserData();
	weekUser() ;
	benWeekUser();
	
}





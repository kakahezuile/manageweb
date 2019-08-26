var zong =0;
var communityId=3;
var mapTryOut =window.parent.mapTryOut;
var mapUser=window.parent.mapUser;
var mapRegisterList=window.parent.mapRegisterList;
var mapActive=window.parent.mapActive;

function tryOut() {
	    if(window.parent.iii==4||window.parent.iii==14){
		  getTop();
		}else{
			setTimeout("tryOut()", 1000);
		}
	 
	 
}
getUserTop();
tryOut();

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
			active(biao,startTime,endTime,data.installsNum-data.testNum,data.registerNum,data.installsNum);
		},
		error : function(er) {
		}
	});
}
function  getUserRegisterTimeNum(endTime) {
	var path = "/api/v1/communities/"+communityId+"/userStatistics/getUserRegisterEndTime?endTime="+endTime;
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			return data.registerNum;
		},
		error : function(er) {
		}
	});
}


function getUserTime(biao,startTime,endTime) {
	var path = "/api/v1/communities/"+communityId+"/userStatistics/getUserRegisterTime?startTime="+startTime+"&endTime="+endTime;
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			document.getElementById(biao).innerHTML = data.registerNum;
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
	
 
 function activeTest() {
	 var path = "/api/v1/communities/"+communityId+"/userStatistics/getLaunchesDetailsStatistics?startTime="+startTime+"&endTime="+endTime;
		$.ajax({
			url : path,
			type : "GET",
			dataType : 'json',
			success : function(data) {
				data = data.info;
				var userCount=0;
				var map={};
				var time;
				for ( var i = 0; i < data.length; i++) {
					data[i].userClick.length;
				}
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
function totalUser() {
	
	var myDate = new Date();
	var month = myDate.getMonth()+1;
	var startTime ="2015-6-1 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-"+ (myDate.getDate()+1) + " 00:00:00";
	getUserRegisterTime('total_id',startTime,endTime);
	
}
function getTop() {
	thisUserData();
	quickUserData();
	weekUser() ;
	benWeekUser();
	
}

function getUserList( pageNum, startTime, endTime,type) {
	 var path = "/api/v1/communities/"+communityId+"/userStatistics/getLaunchesDetailsStatistics?startTime="+startTime+"&endTime="+endTime;
		$.ajax({
			url : path,
			type : "GET",
			dataType : 'json',
			success : function(data) {
				
				data = data.info;
				var userCount=0;
				var map={};
				var mapTest={};
				var mapRegister={};
				var time;
				for ( var i = 0; i < data.length; i++) {
					var total=0;
					var testTotal=0;
					var registerList=0;
					time=getStringTime(data[i]._id.time*24*60*60*1000);
					var usc=data[i].userClick;
					for ( var j = 0; j < usc.length; j++) {
						if(mapUser[usc[j].emobId]=="1"){
							if(mapTryOut[usc[j].emobId]!="1"){
								total++;
								if(mapRegisterList[data[i].userClick.emobId]=="1"){
									registerList++;
								}
							}else{
								testTotal++;
							}
						}
					}
					mapRegister[time]=registerList;
					map[time]=total;
					mapTest[time]=testTotal;
				}
				getUserRegisterTime2(pageNum, startTime, endTime,type,map,mapTest,mapRegister);
			},
			error : function(er) {
			}
		});
}

function registerNum2(pageNum, startTime, endTime,type,map) {
	schedule();
	var d=show(getStringTime(stringToTime(startTime)),getStringTime(stringToTime(endTime)));
	var en="";
	for ( var i = 0; i < d.length; i++) {
		en+="&endList="+d[i]+" 00:00:00";
	}
	var path = "/api/v1/communities/"+communityId+"/userStatistics/getUserRegisterTimeGroup?startTime="
			+ startTime
			+ "&endTime=" + endTime+en;
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			var map2={};
			for ( var i = 0; i < data.length; i++) {
				map2[getStringTime(data[i].createTime*1000)]=data[i].installsNum;
			}
			userList(pageNum, startTime, endTime,type,map2,map);
		},
		error : function(er) {
			onSchedule();
			top.location='/'; 
		}
	});
}

function  getUserRegisterTime2(pageNum, startTime, endTime,type,map,mapTest,mapRegister) {
	var path = "/api/v1/communities/"+communityId+"/userStatistics/getUserRegisterEndTime?endTime="+endTime;
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			
			active2(pageNum, startTime, endTime,type,map,mapTest,mapRegister,data.installsNum-data.testNum);
		},
		error : function(er) {
		}
	});
}

function active2(pageNum, startTime, endTime,type,map,mapTest,mapRegister,registerNum2) {
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
			var totalUser=0;
			var testTotalUser=0;
			for ( var i = 0; i < data.length; i++) {
				
				if(mapUser[data[i].userClick.emobId]=="1"){
					if(data[i].userClick.emobId!="null"&&data[i].userClick.emobId!=-1){
						if(mapTryOut[data[i].userClick.emobId]!="1"){
							totalUser++;
						}else{
							testTotalUser++;
						}
					}
				}
			}
			var top_user = 0;
			if(registerNum2>0){
				top_user = (((totalUser /registerNum2)) * 100).toFixed(2);
			}
		
			registerNum(pageNum, startTime, endTime,type,map,mapTest,mapRegister,totalUser,testTotalUser,top_user) ;
		},
		error : function(er) {
		}
	});
}







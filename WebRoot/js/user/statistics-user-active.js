var zong =0;
var communityId=window.parent.document.getElementById("community_id_index").value;
var mapTryOut =window.parent.mapTryOut;
var mapUser=window.parent.mapUser;
var mapRegisterList=window.parent.mapRegisterList;
var mapActive=window.parent.mapActive;
var userTestNum;
function tryOut() {
	    if(window.parent.isActive==2){
		 // getTop();
		  getUserTop2() ;
		  quickShopData();
		}else{
			schedule();
			setTimeout("tryOut()", 1000);
		}
}


function getUserTop2() {
	var path = "/api/v1/statisticsUser/thisStatisticsUserNum";
	var myJson={"communityId":communityId};
	$.ajax({
		url : path,
		type : "POST",
		data:JSON.stringify(myJson),
		dataType : 'json',
		success : function(data) {
			data = data.info;
			userTestNum =data;
		    getTop();
		},
		error : function(er) {
		}
	});
}

function  testHao() {
	var listU=	mapActive[biao];
	  if(listU!=undefined){
		  data=data.concat(listU);
	  }
	
	
	for ( var i = 0; i < data.length; i++) {
		
		if(mapUser[data[i].emobId]=="1"){
				if(mapTryOut[data[i].emobId]!="1"){
					listU1[listUn++]=data[i].emobId;
					total++;
				}else{
					testTotal++;
				}
		}
	}

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
	document.getElementById(biao+"_test").innerHTML ="("+ testTotal+")";
	document.getElementById(biao+"_register_huo").innerHTML = registerList;
	document.getElementById(biao+"_register_xiao").innerHTML = registerUser_user+"%";
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
			document.getElementById("zong_num_an_test").innerHTML ="("+data.testNum+")";
			document.getElementById("zong_num_test").innerHTML = "("+data.testNum+")";
			thisUserData();
			quickUserData();
			weekUser();
			monthUser();
			thisMonthUser();
			benWeekUser();

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
			active10(biao,startTime,endTime,data.installsNum-data.testNum,data.registerNum,data.installsNum);
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

function active10(biao,startTime,endTime,registerNum,registerUser,installsUser) {
	var total=0;
	var testTotal=0;
	var registerList=0;
	var listUn=0;
	var listUn2=0;
	var listU1=new Array();
	var testU=new Array();
	var testInt=0;
	var listU=mapActive[biao];
	
	if(biao=="this_month"||biao=="this_week"){
		listU=	listU.concat(userTestNum);
	}else if(biao=="this_data"){
		listU=userTestNum;
	}
	
	for ( var i = 0; i < listU.length; i++) {
		if(mapUser[listU[i]]=="1"){
			if(mapTryOut[listU[i]]!="1"){
				listU1[listUn++]=listU[i];
				total++;
			}else{
				testU[testTotal++]=listU[i];
			}
		}
	}
	listU1=listU1.unique2();
	if(testU.length>1){
		 testU=unique1(testU);
	}
	total=listU1.length;
	testTotal=testU.length;
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
	document.getElementById(biao+"_test").innerHTML ="("+ testTotal+")";
	document.getElementById(biao+"_register_huo").innerHTML = registerList;
	document.getElementById(biao+"_register_xiao").innerHTML = registerUser_user+"%";
}

function unique1(list) {
	var ma={};
	for ( var i = 0; i < list.length; i++) {
		ma[list[i]]=1;
	}
	var l=new Array();
	var i=0;
	for ( var key in ma) {
		l[i++]=key;
	}
	return l;
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
							if(mapTryOut[data[i].userClick.emobId]!="1"){
								listU1[listUn++]=data[i].userClick.emobId;
								total++;
							}else{
								testTotal++;
							}
					}
				}
			    if(listU!=undefined){
				   listU1=	listU1.concat(listU);
				  
			    }
			   
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
				document.getElementById(biao+"_test").innerHTML ="("+ testTotal+")";
			//	document.getElementById(biao+"_installsUser").innerHTML =registerNum;
			//	document.getElementById(biao+"_register").innerHTML = registerUser;
				document.getElementById(biao+"_register_huo").innerHTML = registerList;
				document.getElementById(biao+"_register_xiao").innerHTML = registerUser_user+"%";
			},
			error : function(er) {
			//	alert("网络连接失败...");
			//	top.location='/'; 
			}
		});
}
	
 
 function activeTest() {
		// var path = "/api/v1/communities/"+communityId+"/userStatistics/getVisitor?startTime="+startTime+"&endTime="+endTime;
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
				//alert("网络连接失败...");
			   //	top.location='/'; 
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

function monthUser() {
	var myDate = new Date();
	var month = myDate.getMonth();
	var month2 = myDate.getMonth()+1;


	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month2 + "-1" + " 00:00:00";
	getUserRegisterTime('last_month',startTime,endTime);
}
function thisMonthUser() {
	var myDate = new Date();
	var month = myDate.getMonth()+1;
	var month2 = myDate.getMonth()+2;
	
	
	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month2 + "-1" + " 00:00:00";
	getUserRegisterTime('this_month',startTime,endTime);
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
	//getUserTop();
	thisUserData();
	quickUserData();
	weekUser() ;
	monthUser();
	thisMonthUser();
	benWeekUser();
	
}
function quickShopData() {
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + myDate.getDate()
			+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
	
	getUserList( 1, startTime, endTime,'日');
}

function maintainMonth() {
	var myDate = new Date();
	var month = myDate.getMonth();
	var month2 = myDate.getMonth()+1;


	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month2 + "-1" + " 00:00:00";
	getUserList( 1, startTime, endTime,'月');

}

function weekMonth(sort) {

	var d = getPreviousWeekStartEnd();
	var startTime = d.start;
	var endTime = d.end;
	getUserList( 1, startTime, endTime,'周');

}
function selectStatistics() {
	var sort = document.getElementById("master_repir_sort_fei").value;
	var startTime = document.getElementById("txtBeginDate").value + " 00:00:00";
	var endTime = document.getElementById("txtEndDate").value + " 00:00:00";
	getUserList( 1, startTime, endTime,'');
}



function  getUserList( pageNum, startTime, endTime,type) {
	
    var start = stringToTime(startTime) / 1000;
    var end = stringToTime(endTime) / 1000;
	var path = "/api/v1/communities/"
		+ communityId
		+ "/users/1/orderHistories/getClickAmount?url=http://115.28.73.37:9090/api/V1/communities/"
		+ communityId + "/modules/statistics/"+start+"/" + (end-1);
$.ajax({
	url : path,
	type : "GET",
	dataType : 'json',
	success : function(data) {
		data = data.info;
		
		
		var info;
		
		var clickTotal= 0;
		var clickNumTotal = 0;
		var clickTimeTotal=0;
		
		var mapTimeTotal={};
		var liList="";
		for ( var i = 0; i < data.length; i++) {
			var mapObjHe={};
			var testToalClick=0;
			var toalClick=0;
			info = data[i].info;
			var clickUserList = new Array();
			var clickUser = 0;
			for ( var j = 0; j < info.length; j++) {
				var click = 0;
				var testClick = 0;
				
			
				var testClickUser = 0;
				var testClickUserList = new Array();
				var total=0;
				var clickList=info[j].userClick;
				if(info[j].serviceId=="0"){
					if(info[j].serviceName!="通知公告"){
						continue;
					}
				}
				for ( var k = 0; k < clickList.length; k++) {
					if(mapTryOut[clickList[k].emobId] == "1"){
						
					}else if(mapUser[clickList[k].emobId]=="1"){
						clickUserList[clickUser++] =clickList[k].emobId ;
						
					}
				}
				
				
			}
			mapTimeTotal[getStringTime(data[i]._id.time*24*60*60*1000)]=clickUserList;
		
		}
		getTestActive( pageNum, startTime, endTime,type,mapTimeTotal);
		
	},
	error : function(er) {
	}
});
	
}
function getTestActive( pageNum, startTime, endTime,type,mapTimeTotal) {
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
					var userList=new Array();
					var testUserList=new Array();
					time=getStringTime(data[i]._id.time*24*60*60*1000);
					var usc=data[i].userClick;
					for ( var j = 0; j < usc.length; j++) {
						if(mapUser[usc[j].emobId]=="1"){
							if(mapTryOut[usc[j].emobId]!="1"){
								userList[total++]=usc[j].emobId;
								//alert(usc[j].emobId);
								if(mapRegisterList[data[i].userClick.emobId]=="1"){
									registerList++;
								}
							}else{
								testUserList[testTotal++]=usc[j].emobId;
							}
						}
					}
					var totalUser=new Array();
					var totalUser=mapTimeTotal[time];
					userList=userList.concat(totalUser);
					userList=unique3(userList);
					mapRegister[time]=registerList;
					map[time]=userList;
					mapTest[time]=testUserList;
				}
				getUserRegisterTime2(pageNum, startTime, endTime,type,map,mapTest,mapRegister);
			},
			error : function(er) {
				onSchedule();
				//alert("网络连接失败...");
				//top.location='/'; 
			}
		});
}

function registerNum2(pageNum, startTime, endTime,type,map) {
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
			// registerNum(pageNum, startTime, endTime,type,map,mapTest,mapRegister,data.installsNum-data.testNum);
		//	alert(data.installsNum-data.testNum);
			active2(pageNum, startTime, endTime,type,map,mapTest,mapRegister,data.installsNum-data.testNum);
//			getTestActive(pageNum, startTime, endTime,type,map,mapTest,mapRegister,data.installsNum-data.testNum);
		},
		error : function(er) {
			//alert("网络连接失败...");
			//top.location='/'; 
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
						if(mapTryOut[data[i].userClick.emobId]!="1"){
							totalUser++;
						}else{
							testTotalUser++;
						}
				}
			}
			var top_user = 0;
			if(registerNum2>0){
				top_user = (((totalUser /registerNum2)) * 100).toFixed(2);
			}
		
			registerNum(pageNum, startTime, endTime,type,map,mapTest,mapRegister,totalUser,registerNum2) ;
		},
		error : function(er) {
		//	alert("网络连接失败...");
		//	top.location='/'; 
		}
	});
}
function  registerNum(pageNum, startTime, endTime,type,map,mapTest,mapRegister,totalUser,totalNu) {
	var d=show(getStringTime(stringToTime(startTime)),getStringTime(stringToTime(endTime)));
	var en="";
	for ( var i =0; i < d.length; i++) {
		en+="&endList="+d[i]+" 00:00:00";
	}
	var path = "/api/v1/communities/"+communityId+"/userStatistics/getUserRegisterTimeGroup?pageSize=1000&pageNum="
			+ pageNum
			+ "&startTime="
			+ startTime
			+ "&endTime=" + endTime+en;
	$.ajax({
				url : path,
				type : "GET",
				dataType : 'json',
				success : function(data) {
					data = data.info;
                    var map2={};
                    var map3={};
					var repair_overman = $("#statistics_list_id");
					repair_overman.empty();
					repair_overman
							.append("<tr class=\"odd\"><td>统计时间</td><td>用户活跃数</td><td>工作人员活跃数</td><td>活跃度</td></tr>");
					var myDate = new Date();
					var month = myDate.getMonth() + 1;
					var da = myDate.getDate() - 1;
					var da2 = myDate.getDate() - 2;
					var startTime1 = myDate.getFullYear() + "-" + month + "-"
							+ da + " 00:00:00";
					var endTime1 = myDate.getFullYear() + "-" + month + "-"
							+ myDate.getDate() + " 00:00:00";
					var startTime2 = myDate.getFullYear() + "-" + month + "-"
							+ da2 + " 00:00:00";

					var startTime_day_long = stringToTime(startTime1) / 1000;
					var endTime_day_long = stringToTime(endTime1) / 1000;
					var startTime2_day_long = stringToTime(startTime2) / 1000;
					var orderPrice = document
							.getElementById("turnover_zong_get").value;
					var arrSimple=new Array();
					for ( var i = 0; i < data.length; i++) {
						//arrSimple[i]=data[i]._id.time;
						time=getStringTime((data[i].createTime*1000)-(24*60*60*1000));
						map2[time]=data[i].installsNum ;
						map3[time]=data[i].testNum ;
					}
					arrSimple.sort();
					var liList = "";
					var liTop="";
					var activeNum=0;
					var testActiveNum=0;
					var sumUser=new Array();
					var testSumUser=new Array();
					for ( var i =  d.length-2; i >=0; i--) {
						var key=d[i];
						if(map[key]!=undefined){
							sumUser=sumUser.concat(map[key]);
						}
						if(mapTest[key]!=undefined){
							testSumUser=testSumUser.concat(mapTest[key]);
						}
						
						//alert(key);
						var num=map2[key];
						var huo=0;
						if(num!=undefined&&map[key]!=undefined){
							huo=(map[key].length/(num-map3[key]))*100;
						}

						if (i % 2 == 0) {
							liList += "<tr class=\"even\">";
						} else {

							liList += "<tr class=\"odd\">";
						}

						liList += "<td>"+key;

						if(map[key]!=undefined){
							
							liList += "</td><td >" + map[key].length+"</td>";
						}else{
							liList += "</td><td >" +0+"</td>";
						}
						
						if(mapTest[key]!=undefined){
							  liList +="<td style=\"color: green\">"+mapTest[key].length+"</td>";
						}else{
							  liList +="<td style=\"color: green\">"+0+"</td>";
						}
						
						liList += "<td>"+huo.toFixed(2)+"%</td></tr>";
					}
					sumUser=unique3(sumUser);
					testSumUser=unique3(testSumUser);
					
					var num=sumUser.length;
				
					var top_user= (num/totalNu)*100;
					//alert((map2[d[0]]-map3[d[0]]));
					liTop="<tr class=\"even\"><td >合计</td><td >" +sumUser.length+"</td><td style=\"color: green\">" +testSumUser.length+"</td>" ;
						
					liTop += "<td>"+top_user.toFixed(2)+"%</td></tr>";
					
					repair_overman.append(liTop);
					repair_overman.append(liList);
					document.getElementById("master_repir_startTime").value = startTime;
					document.getElementById("master_repir_endTime").value = endTime;
					
					if(type!=""){
						timeQuantum(startTime, endTime);
						timeDisplay(type);
					}else{
						timeQuantum(startTime, endTime);
					}
					onSchedule();
				},
				error : function(er) {
					onSchedule();
					top.location='/'; 
				}
			});

}
function  userList2(pageNum, startTime, endTime,type,registerNum,map) {
	var d=show(getStringTime(stringToTime(startTime)),getStringTime(stringToTime(endTime)));
	var en="";
	for ( var i = 0; i < d.length; i++) {
		en+="&endList="+d[i]+" 00:00:00";
	}
	var path = "/api/v1/communities/"+communityId+"/userStatistics/getUserRegisterTimeGroup?pageSize=1000&pageNum="
			+ pageNum
			+ "&startTime="
			+ startTime
			+ "&endTime=" + endTime+en;
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			var map2={};
			var repair_overman = $("#statistics_list_id");
			repair_overman.empty();
			repair_overman
			.append("<tr class=\"odd\"><td>统计时间</td><td>活跃用户</td><td>活跃度</td></tr>");
			var myDate = new Date();
			var month = myDate.getMonth() + 1;
			var da = myDate.getDate() - 1;
			var da2 = myDate.getDate() - 2;
			var startTime1 = myDate.getFullYear() + "-" + month + "-"
			+ da + " 00:00:00";
			var endTime1 = myDate.getFullYear() + "-" + month + "-"
			+ myDate.getDate() + " 00:00:00";
			var startTime2 = myDate.getFullYear() + "-" + month + "-"
			+ da2 + " 00:00:00";
			
			var startTime_day_long = stringToTime(startTime1) / 1000;
			var endTime_day_long = stringToTime(endTime1) / 1000;
			var startTime2_day_long = stringToTime(startTime2) / 1000;
			var orderPrice = document
			.getElementById("turnover_zong_get").value;
			for ( var i = 0; i < data.length; i++) {
				time=getStringTime(data[i].createTime*1000);
				map2[time]=data[i].registerNum ;
			}
			var liList = "";
			var liTop="";
			var activeNum=0;
			var activeDu=0;
			for ( var key in map) {
				var num=map2[key];
				var huo=0;
				activeNum+=map[key];
				if(num!=undefined){
					activeDu+=num;
					huo=(map[key]/num)*100;
				}
				
				if (i % 2 == 0) {
					liList += "<tr class=\"even\">";
				} else {
					
					liList += "<tr class=\"odd\">";
				}
				
				liList += "<td>"+key;
				liList += "</td><td >" + map[key]+ "</td>";
				liList +="";
				liList += "<td>"+huo.toFixed(2)+"%</td></tr>";
			}
			
			
			liTop="<tr class=\"even\"><td>合计</td><td >" +activeNum+"</td>" ;
			if(activeDu!=0){
				
				liTop += "<td>"+((activeNum/activeDu)*100).toFixed(2)+"%</td></tr>";
			}else{
				liTop += "<td>0%</td></tr>";
			}
			repair_overman.append(liTop);
			repair_overman.append(liList);
			document.getElementById("master_repir_startTime").value = startTime;
			document.getElementById("master_repir_endTime").value = endTime;
			
			if(type!=""){
				timeQuantum(startTime, endTime);
				timeDisplay(type);
			}else{
				timeQuantum(startTime, endTime);
			}
			onSchedule();
		},
		error : function(er) {
			onSchedule();
		}
	});
	
}

function timeClick(clickId) {
	if (clickId == "data_clik") {
		document.getElementById("data_clik").className = "select";
		var master_repir_sort = document
				.getElementById("master_repir_sort_fei").value;
		quickShopData(master_repir_sort);
	} else {
		document.getElementById("data_clik").className = "";
	}
	if (clickId == "week_clik") {
		document.getElementById("week_clik").className = "select";
		var master_repir_sort = document
				.getElementById("master_repir_sort_fei").value;
		weekMonth(master_repir_sort);
	} else {
		document.getElementById("week_clik").className = "";
	}
	if (clickId == "month_clik") {
		document.getElementById("month_clik").className = "select";
		var master_repir_sort = document
				.getElementById("master_repir_sort_fei").value;
		maintainMonth(master_repir_sort);
	} else {
		document.getElementById("month_clik").className = "";
	}
}

function selectData(tim) {
	var myDate = new Date(tim * 1000);
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + myDate.getDate()
			+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
	getQuickShopList(1, startTime, endTime);
}

function alterMonth(num) {

	var startTime = document.getElementById("master_repir_startTime").value;
	var sort = document.getElementById("master_repir_sort_fei").value;
	var type = document.getElementById("date_type_get").value;
	var d = alterDate(num, type, startTime);
	var startTime = d.start;
	var endTime = d.end;
	getUserList( 1, startTime, endTime,"");
}




function testA2(mapTest) {
    var path = "/api/v1/communities/"+communityId+"/userStatistics/getLaunchesDetailsStatistics?startTime=2015-6-16 00:00:00&endTime=2015-6-30 00:00:00";
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			var total=0;
			var testTotal=0;
			var shi=0;
			var clickUserList = new Array(); 
			var mapHao={};
			for ( var key in mapTest) {
				mapHao[key]=0;
			}
			
			for ( var i = 0; i < data.length; i++) {
				userClick=data[i].userClick;
			     for ( var j = 0; j < userClick.length; j++) {
			    	 
					if(mapTest[userClick[j].emobId]=="1"){
						total++;
						clickUserList[shi++]=userClick[j].emobId;
						if(mapTryOut[userClick[j].emobId]!="1"){
							//userCount+=userClick[j].click;
							var en=	mapHao[userClick[j].emobId];
							en+=parseInt(en)+parseInt(userClick[j].click);
							mapHao[userClick[j].emobId]=en;
						}else{
						
								//clickUserList[shi++]=userClick[j].emobId;
								//total++;
								
						}
					}
				 }
				//userCount+= data[i].total;
			}
			var shi=0;
			for ( var key in mapHao) {
				
				if(mapHao[key]>1){
					shi++;
				}
			}
//			for ( var i = 0; i < data.length; i++) {
//				if(mapTest[data[i].userClick.emobId]=="1"){
//						//	total++;
//				}
//			}
//			clickUserList=clickUserList.unique2();
			alert(shi);
		},
		error : function(er) {
		//	alert("网络连接失败...");
		//	top.location='/'; 
		}
	});
}
function testA(mapTest) {
    var path = "/api/v1/communities/"+communityId+"/userStatistics/getLaunchesStatistics?startTime=2015-6-1 00:00:00&endTime=2015-6-29 00:00:00";
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			var total=0;
			var testTotal=0;
			var j=0;
			var clickUserList = new Array(); 
			for ( var i = 0; i < data.length; i++) {
				
				if(mapTest[data[i].userClick.emobId.replace(/\s+/g,"")]=="1"){
					clickUserList[j++]=data[i].userClick.emobId;
					mapTest[data[i].userClick.emobId]="2";
					total++;
				}else{
					//alert(mapTest[data[i].userClick.emobId]);
				}
			}
			
			var hao="";
			clickUserList=clickUserList.unique2();
			alert("clickUserList"+clickUserList.length);
			for ( var key in mapTest) {
				if(mapTest[key]==1){
					hao+=key+";";
				}
			}
			document.getElementById("shishi").value=hao;
		//	alert(hao);
		},
		error : function(er) {
		//	alert("网络连接失败...");
		//	top.location='/'; 
		}
	});
}
function test() {
	var path = "/api/v1/communities/"+communityId+"/userStatistics/getTimeList";
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data=data.info;
			var mapTest={};
			var a=0;
			for ( var i = 0; i < data.length; i++) {
				a++;
				mapTest[data[i].emobId.replace(/\s+/g,"")] = "1";
			}
			testA2(mapTest);
		},
		error : function(er) {
			//alert("网络连接失败...");
			//top.location='/'; 
		}
	});
	
}


function thisClickNum() {
	var myDate=new Date();
	var month=myDate.getMonth()+1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" +myDate.getDate()   
	+ " 00:00:00";
    var endTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
    var start = stringToTime(startTime) / 1000;
    var end = stringToTime(endTime) / 1000;
	
	var path = "/api/v1/communities/"
		+ communityId
		+ "/users/1/orderHistories/getClickAmount?url=http://115.28.73.37:9090/api/V1/communities/"
		+ communityId + "/modules/statistics/"+start+"/" + (end-1);
$.ajax({
	url : path,
	type : "GET",
	dataType : 'json',
	success : function(data) {
		data = data.info;
		
		var mapObjHe={};
		var info;
		
		var clickTotal= 0;
		var clickNumTotal = 0;
		var clickTimeTotal=0;
		for ( var i = 0; i < data.length; i++) {
		
			info = data[i].info;

			for ( var j = 0; j < info.length; j++) {
				var click = 0;
				var testClick = 0;
				var clickUser = 0;
				var clickUserList = new Array();
				var testClickUser = 0;
				var testClickUserList = new Array();
				var total=0;
				var clickList=info[j].userClick;
				if(info[j].serviceId=="0"){
					if(info[j].serviceName!="通知公告"){
						
						continue;
					}
				}
			
				for ( var k = 0; k < clickList.length; k++) {
					if(mapTryOut[clickList[k].emobId] == "1"){
						testClickUserList[testClickUser++] = clickList[k].emobId;
						testClick += clickList[k].click;
						
					}else if(clickList[k].emobId!=undefined&&clickList[k].emobId!=""){
						clickTimeTotal+= clickList[k].click;
						clickNumTotal += clickList[k].click;
						clickUserList[clickUser++] =clickList[k].emobId ;
						click += clickList[k].click;
						
					}
				}
				
				if(testClickUserList.length>1){
					 testClickUserList=testClickUserList.unique2();
				}
				if(clickUserList.length>1){
					 clickUserList=clickUserList.unique2();
				}
				//var clickNum=clickNumMap[biao];
				var clickUser=clickUserList.length;
				if(clickUser==0){
					//total="["+total+"]";
					click=0;
					clickUser=0;
				}
				
				
				var clickObc={
						"total":total,
						"click":click,
						"clickUser":clickUser,
						"clickUserList":clickUserList,
						"testClick":testClick,
						"testClickUser":testClickUserList.length
						
				};
				mapObjHe[info[j].serviceId]=clickObc;
				
			}
			
		}
		
		testTop(clickNumTotal,mapObjHe);
	//	thisClick(clickNum);
	},
	error : function(er) {
	}
});
}

function testTop(clickNum,mapObjHe) {

	for ( var key in clickObj) {
		var value = key.replace(/[^0-9]/ig,""); 
		var biao="";
		

			var obj=clickObj[key];
			biao=key;
			var clickObc=mapObjHe[value];
			if(clickObc==undefined){
				if(obj.click==0){
					document.getElementById(biao).innerHTML="(无明细["+obj.total+"])";
				}else{
					document.getElementById(biao).innerHTML="("+obj.click+")";
				}
				
	//			document.getElementById(biao+value+"_user").innerHTML=obj.clickUser;
				document.getElementById(biao+"_test").innerHTML="("+obj.testClick+")";
	//			document.getElementById(biao+value+"_user_test").innerHTML="("+obj.testClickUser+")";
				document.getElementById(biao+"_ratio").innerHTML=obj.clickRatio+ "%";
				
				continue;
			}
			var obj2=clickObj["this_data"+value];
			var clickAddNum=(clickNum-obj2.clickNum);
		
			var clickAddUser=clickObc.clickUser-obj2.clickUser;
			var clickAddTest=clickObc.testClick-obj2.testClick;
			var clickAddUserTest=clickObc.testClickUser-obj2.testClickUser;
			var clickAdd=clickObc.click-obj2.click;
				
			
			
			if("this_data"+value==biao||"this_week"+value==biao||"this_month"+value==biao||"total_id"+value==biao){
			    var clickNumTop= parseInt(obj.clickNum)+parseInt(clickAddNum);
			    var clickTop= parseInt(obj.click)+parseInt(clickAdd);
				var clickBi=0;
				if(clickNumTop>0){
					clickBi=(clickTop/clickNumTop)*100;
				}
				                                                                               
				document.getElementById(biao).innerHTML= "("+(parseInt(obj.click)+parseInt(clickAdd))+")";
//				document.getElementById(biao+value+"_user").innerHTML=parseInt(obj.clickUser)+parseInt(clickAddUser);
				document.getElementById(biao+"_test").innerHTML="("+(parseInt(obj.testClick)+ parseInt(clickAddTest))+")";
//				document.getElementById(biao+value+"_user_test").innerHTML="("+ (parseInt(obj.testClickUser)+ parseInt(clickAddUserTest))+")";
				document.getElementById(biao+"_ratio").innerHTML=clickBi.toFixed(2)+ "%";
			}else{
					if(obj.click==0){
						document.getElementById(biao).innerHTML="(无明细["+obj.total+"])";
					}else{
						document.getElementById(biao).innerHTML="("+obj.click+")";
					}
					
//					document.getElementById(biao+value+"_user").innerHTML=obj.clickUser;
					document.getElementById(biao+"_test").innerHTML="("+obj.testClick+")";
//					document.getElementById(biao+value+"_user_test").innerHTML="("+obj.testClickUser+")";
					document.getElementById(biao+"_ratio").innerHTML=obj.clickRatio+ "%";
				
			}
	}
	onSchedule();
}
//test();

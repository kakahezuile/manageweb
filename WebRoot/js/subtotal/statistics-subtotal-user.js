var registerNum=0;
var map={};
var mapUser=window.parent.mapUser;
var mapTryOut =window.parent.mapTryOut;
var clickObj=window.parent.clickObj;
var communityId=window.parent.document.getElementById("community_id_index").value;
//function tryOut() {
//	 getTop();
//	 quickShopData();
//}



function testTop(clickNum,mapObjHe) {

	for ( var key in clickObj) {
		var value = key.replace(/[^0-9]/ig,""); 
		var biao="";
		if(value==17){
			continue;
		}
		var clickObc=mapObjHe[value];
		biao=key;
		if(!clickObc){
			if(obj.click==0){
				document.getElementById(biao).innerHTML="(无明细" + (!!obj.total ? ("[" + obj.total + "]") : "") + ")";
			}else{
				document.getElementById(biao).innerHTML="("+obj.click+")";
			}
			document.getElementById(biao+"_user").innerHTML=obj.clickUser;
			document.getElementById(biao+"_test").innerHTML="("+obj.testClick+")";
			document.getElementById(biao+"_user_test").innerHTML="("+obj.testClickUser+")";
//			document.getElementById(biao+"_ratio").innerHTML=obj.clickRatio+ "%";
			continue;
		}
			var obj=clickObj[key];
			
			
			var obj2=clickObj["this_data"+value];
			var clickAddNum=(clickNum-obj2.clickNum);
		
			var clickAddUser=clickObc.clickUser-obj2.clickUser;
			var clickAddTest=clickObc.testClick-obj2.testClick;
			var clickAddUserTest=clickObc.testClickUser-obj2.testClickUser;
			var clickAdd=clickObc.click-obj2.click;
				
			
			biao=key;
			if("this_data"+value==biao||"this_week"+value==biao||"this_month"+value==biao||"total_id"+value==biao){
			    var clickNumTop= parseInt(obj.clickNum)+parseInt(clickAddNum);
			    var clickTop= parseInt(obj.click)+parseInt(clickAdd);
				var clickBi=0;
				if(clickNumTop>0){
					clickBi=(clickTop/clickNumTop)*100;
				}
				try {
					var tempEl = document.getElementById(biao);
					document.getElementById(biao).innerHTML= parseInt(obj.click)+parseInt(clickAdd);
					document.getElementById(biao+"_user").innerHTML="("+(parseInt(obj.clickUser)+parseInt(clickAddUser))+")";
					document.getElementById(biao+"_test").innerHTML=(parseInt(obj.testClick)+ parseInt(clickAddTest));
					document.getElementById(biao+"_user_test").innerHTML="("+ (parseInt(obj.testClickUser)+ parseInt(clickAddUserTest))+")";
//					document.getElementById(biao+"_ratio").innerHTML=clickBi.toFixed(2)+ "%";
				} catch (ex) {
					console.log(ex);
				}
			}else{
					if(obj.click==0){
						document.getElementById(biao).innerHTML="(" + (!!obj.total ? ("[" + obj.total + "]") : "0") + ")";
					}else{
						try {
							document.getElementById(biao).innerHTML="("+obj.click+")"  ;
						}catch (ex) {
							console.log(ex);
						}
					}
					try {
						document.getElementById(biao+"_user").innerHTML=obj.clickUser;
						document.getElementById(biao+"_test").innerHTML="("+obj.testClick+")";
						document.getElementById(biao+"_user_test").innerHTML="("+obj.testClickUser+")";
	//					document.getElementById(biao+"_ratio").innerHTML=obj.clickRatio+ "%";
					} catch (ex) {
						console.log(ex);
					}
			}
	}
	onSchedule();
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
	//alert(path);
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
				if(info[j].serviceId=="17"){
					continue;
			    }
				for ( var k = 0; k < clickList.length; k++) {
					if(mapTryOut[clickList[k].emobId] == "1"){
						testClickUserList[testClickUser++] = clickList[k].emobId;
						testClick += clickList[k].click;
						
					}else if(mapUser[clickList[k].emobId]=="1"){
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


function  thisClick(clickNum) {
	
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" +  myDate.getDate()
	+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" +da + " 00:00:00";
	var start = stringToTime(startTime) / 1000;
	var end = stringToTime(endTime) / 1000;
	
	
	
	var time = 0;
	var path = "/api/v1/communities/"
		+ communityId
		+ "/users/1/orderHistories/getClickAmount?url=http://115.28.73.37:9090/api/V1/communities/"
		+ communityId + "/events_details_daily/statistics/6/" + start
		+ "/" + (end - 1);
	//alert(path);
       $.ajax({
			url : path,
			type : "GET",
			dataType : 'json',
			success : function(data) {
				data = data.info;

				var info;
				var click = 0;
				var testClick = 0;
				var clickUser = 0;
				var clickUserList = new Array();
				var testClickUser = 0;
				var testClickUserList = new Array();
				var total=0;
				// info = data.userClick;
				for ( var i = 0; i < data.length; i++) {
					var clickList = data[i].userClick;
					total+=data[i].total;
					for ( var j = 0; j < clickList.length; j++) {
					
						if (mapTryOut[clickList[j].emobId] == "1") {
							testClickUserList[testClickUser++] = clickList[j].emobId;
							testClick += clickList[j].click;
							// alert(clickList[j].emobId);
						} else {
							if(mapUser[clickList[j].emobId]=="1"){
								clickUserList[clickUser++] =clickList[j].emobId ;
								click += clickList[j].click;
							}
						
						}

					}
				}
				
				// alert(testClickUserList.unique2().length);
				if(testClickUserList.length>1){
					 testClickUserList=testClickUserList.unique2();
				}
				if(clickUserList.length>1){
					 clickUserList=clickUserList.unique2();
				}
				//var clickNum=clickNumMap[biao];
				var clickBi=0;
				if(clickNum>0){
					clickBi=(click/clickNum)*100;
				}
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
						"testClick":testClick,
						"testClickUser":testClickUserList.length,
						"clickRatio":clickBi.toFixed(2)
						
				};
				testTop(clickNum,clickObc);
			},
			error : function(er) {
				onSchedule();
			}
		});
}





function getTryOut() {
	
	if(window.parent.isClick==63){
//		testTop();
		thisClickNum();
		quickShopData();
	}else{
		schedule();
		setTimeout("getTryOut()", 2000);
		 //setInterval("alert(100)",5000);
	}
	
}


function getLaunches(biao,startTime,endTime) {
	schedule();
	var path = "/api/v1/communities/"+communityId+"/userStatistics/getLaunchesDetailsStatistics?startTime="+startTime+"&endTime="+endTime;
	
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			var userCount=0;
			var userClick;
			var testClick=0;
			for ( var i = 0; i < data.length; i++) {
				userClick=data[i].userClick;
			     for ( var j = 0; j < userClick.length; j++) {
					if(mapUser[userClick[j].emobId]=="1"){
						if(mapTryOut[userClick[j].emobId]!="1"){
							userCount+=userClick[j].click;
						}else{
							testClick+=userClick[j].click;
						}
					}
				 }
				//userCount+= data[i].total;
			}
			document.getElementById(biao).innerHTML = userCount;
			document.getElementById(biao+"_test").innerHTML = "("+testClick+")";
			onSchedule();
			
		},
		error : function(er) {
			onSchedule();
		//	alert("网络连接失败...");
		//	top.location='/'; 
		}
	});
}
function getLaunchesList(startTime,endTime,type) {
	
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
		
		var repair_overman = $("#statistics_list_id");
		repair_overman.empty();
		repair_overman
				.append("<tr class=\"odd\"><td><span style=\"font-weight:normal;\">点击次数(点击人数)</span></td><td>生活圈</td><td>快店</td><td>活动/话题</td><td>维修</td><td>物业客服</td><td>快递</td><td>便捷号码</td><td>送水</td><td>公告通知</td></tr>");
		var mapTimeTotal={};
		var liList="";
		for ( var i = 0; i < data.length; i++) {
			var mapObjHe={};
			var testToalClick=0;
			var toalClick=0;
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
				if(info[j].serviceId=="17"){
					continue;
			    }
				for ( var k = 0; k < clickList.length; k++) {
					if(mapTryOut[clickList[k].emobId] == "1"){
						testClickUserList[testClickUser++] = clickList[k].emobId;
						testClick += clickList[k].click;
						
					}else if(mapUser[clickList[k].emobId]=="1"){
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
				
				testToalClick+=testClick;
				toalClick+=click;
				var clickObc={
						"total":total,
						"click":click,
						"clickUser":clickUser,
						"testClick":testClick,
						"testClickUser":testClickUserList.length
						
				};
				mapObjHe[info[j].serviceId]=clickObc;
				
			}
			
		   var	clickObc2={
					
					"total":0,
					"click":0,
					"clickUser":0,
					"testClick":0,
					"testClickUser":0
					
			};
			
			
			
			
			
			var mapObjHe16=mapObjHe[16];
			if(mapObjHe16==undefined){
				mapObjHe16=clickObc2;
			}
			
			
			var mapObjHe3=mapObjHe[3];
			if(mapObjHe3==undefined){
				mapObjHe3=clickObc2;
			}
			
			
			var mapObjHe1=mapObjHe[1];
			if(mapObjHe1==undefined){
				mapObjHe1=clickObc2;
			}
			
			
			var mapObjHe9=mapObjHe[9];
			if(mapObjHe9==undefined){
				mapObjHe9=clickObc2;
			}
			
			
			var mapObjHe7=mapObjHe[7];
			if(mapObjHe7==undefined){
				mapObjHe7=clickObc2;
			}
			
			
			var mapObjHe8=mapObjHe[8];
			if(mapObjHe8==undefined){
				mapObjHe8=clickObc2;
			}
			
			
			var mapObjHe10=mapObjHe[10];
			if(mapObjHe10==undefined){
				mapObjHe10=clickObc2;
			}
			
			
			var mapObjHe6=mapObjHe[6];
			if(mapObjHe6==undefined){
				mapObjHe6=clickObc2;
			}
			
			
			var mapObjHe0=mapObjHe[0];
			if(mapObjHe0==undefined){
				mapObjHe0=clickObc2;
			}
			var mapHe={
					"click16":mapObjHe16.click,	
					"clickUser16":mapObjHe16.clickUser,	
					"testClick16":mapObjHe16.testClick,	
					"testClickUser16":mapObjHe16.testClickUser,	
					
					"click3":mapObjHe3.click,	
					"clickUser3":mapObjHe3.clickUser,	
					"testClick3":mapObjHe3.testClick,	
					"testClickUser3":mapObjHe3.testClickUser,	
					
					"click1":mapObjHe1.click,	
					"clickUser1":mapObjHe1.clickUser,	
					"testClick1":mapObjHe1.testClick,	
					"testClickUser1":mapObjHe1.testClickUser,	
					
					"click9":mapObjHe9.click,	
					"clickUser9":mapObjHe9.clickUser,	
					"testClick9":mapObjHe9.testClick,	
					"testClickUser9":mapObjHe9.testClickUser,	
					
					"click7":mapObjHe7.click,	
					"clickUser7":mapObjHe7.clickUser,	
					"testClick7":mapObjHe7.testClick,	
					"testClickUser7":mapObjHe7.testClickUser,	
					
					"click8":mapObjHe8.click,	
					"clickUser8":mapObjHe8.clickUser,	
					"testClick8":mapObjHe8.testClick,	
					"testClickUser8":mapObjHe8.testClickUser,	
					
					"click10":mapObjHe10.click,	
					"clickUser10":mapObjHe10.clickUser,	
					"testClick10":mapObjHe10.testClick,	
					"testClickUser10":mapObjHe10.testClickUser,	
					
					"click6":mapObjHe6.click,	
					"clickUser6":mapObjHe6.clickUser,	
					"testClick6":mapObjHe6.testClick,	
					"testClickUser6":mapObjHe6.testClickUser,	
					
					"click0":mapObjHe0.click,	
					"clickUser0":mapObjHe0.clickUser,	
					"testClick0":mapObjHe0.testClick,	
					"testClickUser0":mapObjHe0.testClickUser
					
						
				};
			mapTimeTotal[getStringTime(data[i]._id.time*24*60*60*1000)]=mapHe;
			
		}
		
		
		var d=show(getStringTime(stringToTime(startTime)),getStringTime(stringToTime(endTime)));
		for( var i = d.length-2; i >=0; i--){
			     var mapHeShi=mapTimeTotal[d[i]];
			     if(i%2==0){
	              	liList += "<tr class=\"even\">";
	              }else{
	              	
	              	liList += "<tr class=\"odd\">";
	              }
				if(mapHeShi==undefined){

					liList += "<td>"+d[i]+"</td>";
					liList += "<td><p><span>0</span><span>(0)</span></p><p><span class=\"green\">0</span><span class=\"green\">(0)</span></p></td>";
					liList += "<td><p><span>0</span><span>(0)</span></p><p><span class=\"green\">0</span><span class=\"green\">(0)</span></p></td>";
					liList += "<td><p><span>0</span><span>(0)</span></p><p><span class=\"green\">0</span><span class=\"green\">(0)</span></p></td>";
					liList += "<td><p><span>0</span><span>(0)</span></p><p><span class=\"green\">0</span><span class=\"green\">(0)</span></p></td>";
					liList += "<td><p><span>0</span><span>(0)</span></p><p><span class=\"green\">0</span><span class=\"green\">(0)</span></p></td>";
					liList += "<td><p><span>0</span><span>(0)</span></p><p><span class=\"green\">0</span><span class=\"green\">(0)</span></p></td>";
					liList += "<td><p><span>0</span><span>(0)</span></p><p><span class=\"green\">0</span><span class=\"green\">(0)</span></p></td>";
					liList += "<td><p><span>0</span><span>(0)</span></p><p><span class=\"green\">0</span><span class=\"green\">(0)</span></p></td>";
					liList += "<td><p><span>0</span><span>(0)</span></p><p><span class=\"green\">0</span><span class=\"green\">(0)</span></p></td>";
		            liList +="</tr>";
					
				}else{

					liList += "<td>"+d[i]+"</td>";
					liList += "<td><p><span>"+mapHeShi.click16+"</span><span>("+mapHeShi.clickUser16+")</span></p><p><span class=\"green\">"+mapHeShi.testClick16+"</span><span class=\"green\">("+mapHeShi.testClickUser16+")</span></p></td>";
					liList += "<td><p><span>"+mapHeShi.click3+"</span><span>("+mapHeShi.clickUser3+")</span></p><p><span class=\"green\">"+mapHeShi.testClick3+"</span><span class=\"green\">("+mapHeShi.testClickUser3+")</span></p></td>";
					liList += "<td><p><span>"+mapHeShi.click1+"</span><span>("+mapHeShi.clickUser1+")</span></p><p><span class=\"green\">"+mapHeShi.testClick1+"</span><span class=\"green\">("+mapHeShi.testClickUser1+")</span></p></td>";
					liList += "<td><p><span>"+mapHeShi.click9+"</span><span>("+mapHeShi.clickUser9+")</span></p><p><span class=\"green\">"+mapHeShi.testClick9+"</span><span class=\"green\">("+mapHeShi.testClickUser9+")</span></p></td>";
					liList += "<td><p><span>"+mapHeShi.click7+"</span><span>("+mapHeShi.clickUser7+")</span></p><p><span class=\"green\">"+mapHeShi.testClick7+"</span><span class=\"green\">("+mapHeShi.testClickUser7+")</span></p></td>";
					liList += "<td><p><span>"+mapHeShi.click8+"</span><span>("+mapHeShi.clickUser8+")</span></p><p><span class=\"green\">"+mapHeShi.testClick8+"</span><span class=\"green\">("+mapHeShi.testClickUser8+")</span></p></td>";
					liList += "<td><p><span>"+mapHeShi.click10+"</span><span>("+mapHeShi.clickUser10+")</span></p><p><span class=\"green\">"+mapHeShi.testClick10+"</span><span class=\"green\">("+mapHeShi.testClickUser10+")</span></p></td>";
					liList += "<td><p><span>"+mapHeShi.click6+"</span><span>("+mapHeShi.clickUser6+")</span></p><p><span class=\"green\">"+mapHeShi.testClick6+"</span><span class=\"green\">("+mapHeShi.testClickUser6+")</span></p></td>";
					liList += "<td><p><span>"+mapHeShi.click0+"</span><span>("+mapHeShi.clickUser0+")</span></p><p><span class=\"green\">"+mapHeShi.testClick0+"</span><span class=\"green\">("+mapHeShi.testClickUser0+")</span></p></td>";
		            liList +="</tr>";
					
					
				}
			
		}
		
		
		
		
		repair_overman.append(liList);
		document.getElementById("master_repir_startTime").value = startTime;
		document.getElementById("master_repir_endTime").value = endTime;
		
		if(type!=""){
			timeQuantum(startTime, endTime);
			timeDisplay(type);
		}else{
			timeQuantum(startTime, endTime);
		}
		
		
		//testTop(clickNumTotal,mapObjHe);
	//	thisClick(clickNum);
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
	getLaunches('yesterday',startTime,endTime);
	
}
function thisUserData(){
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate()-1;
	var startTime = myDate.getFullYear() + "-" + month + "-" +da 
	+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" + myDate.getDate() + " 00:00:00";
	getLaunches('this_data',startTime,endTime);
	
}

function monthUser() {
	var myDate = new Date();
	var month = myDate.getMonth();
	var month2 = myDate.getMonth()+1;


	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month2 + "-1" + " 00:00:00";
	getLaunches('last_month',startTime,endTime);
}
function thisMonthUser() {
	var myDate = new Date();
	var month = myDate.getMonth()+1;
	var month2 = myDate.getMonth()+2;
	
	
	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month2 + "-1" + " 00:00:00";
	getLaunches('this_month',startTime,endTime);
}
function weekUser() {

	var d = getPreviousWeekStartEnd();
	var startTime = d.start;
	var endTime = d.end;
	getLaunches('last_week',startTime,endTime);

}
function thisWeekUser() {
	
	var d = getWeekBenUp();
	var startTime = d.start;
	var endTime = d.end;
	getLaunches('this_week',startTime,endTime);
	
}

function totalUser() {
	var myDate = new Date();
	var month = myDate.getMonth()+1;
	var da=myDate.getDate()+1;
	var startTime="2015-06-01 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-"+ da + " 00:00:00";
	
	getLaunches('total_id',startTime,endTime);
	
}
function getTop() {
	totalUser();
	thisUserData();
	thisMonthUser();
	thisWeekUser();
	quickUserData();
	weekUser();
	monthUser();
	
}
function quickShopData() {
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + myDate.getDate()
			+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
	
	getLaunchesList(  startTime, endTime,'日');
}

function maintainMonth() {
	var myDate = new Date();
	var month = myDate.getMonth();
	var month2 = myDate.getMonth()+1;

	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month2 + "-1" + " 00:00:00";
	getLaunchesList( startTime, endTime,'月');

}


function weekMonth(sort) {

	var d = getPreviousWeekStartEnd();
	var startTime = d.start;
	var endTime = d.end;
	getLaunchesList(  startTime, endTime,'周');

}
function selectStatistics() {
	var sort = document.getElementById("master_repir_sort_fei").value;
	var startTime = document.getElementById("txtBeginDate").value + " 00:00:00";
	var endTime = document.getElementById("txtEndDate").value + " 00:00:00";
	getLaunchesList(startTime, endTime,'');

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
	getLaunchesList(startTime, endTime,"");
}

function alterMonth(num) {

	var startTime = document.getElementById("master_repir_startTime").value;
	var sort = document.getElementById("master_repir_sort_fei").value;
	var type = document.getElementById("date_type_get").value;
	var d = alterDate(num, type, startTime);
	var startTime = d.start;
	var endTime = d.end;
	getLaunchesList(startTime, endTime,"");
	
}



function getShopList() {
	schedule();
	  var path = "/api/v1/communities/"+communityId+"/userStatistics/getUserList";
		
		$.ajax({
			url : path,
			type : "GET",
			dataType : 'json',
			success : function(data) {
				data = data.info;
				data=data.listUsers;
				for ( var i = 0; i < data.length; i++) {
					mapUser[data[i].emobId]="1";
				}
				
				  getTop();
				  quickShopData();
			},
			error : function(er) {
				top.location='/'; 
			}
		});
		
}
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

function setInnerHtml(id, html) {
	var el = document.getElementById(id);
	if (el == null) {
		console.log("找不到元素: [" + id + "] : " + html);
	} else {
		el.innerHTML = html;
	}
}

function testTop(clickNum,mapObjHe) {
	for (var key in clickObj) {
		var value = key.replace(/[^0-9]/ig,""); 
		if(value==17){
			continue;
		}
		var obj=clickObj[key];
		var biao=key;
		var clickObc=mapObjHe[value];
		if(!clickObc){
			if(obj.click==0){
				setInnerHtml(biao, "(无明细" + (!!obj.total ? ("[" + obj.total + "]") : "") + ")");
			}else{
				setInnerHtml(biao, "("+obj.click+")");
			}
			setInnerHtml(biao+"_test", "("+obj.testClick+")");
			setInnerHtml(biao+"_ratio", obj.clickRatio+ "%");
			
			continue;
		}
		
		var obj2=clickObj["this_data"+value];
		var clickAddNum=(clickNum-obj2.clickNum);
		var clickAddTest=clickObc.testClick-obj2.testClick;
		var clickAdd=clickObc.click-obj2.click;
		if("this_data"+value==biao||"this_week"+value==biao||"this_month"+value==biao||"total_id"+value==biao ){
			var clickNumTop= parseInt(obj.clickNum)+parseInt(clickAddNum);
			var clickTop= parseInt(obj.click)+parseInt(clickAdd);
			var clickBi=0;
			if(clickNumTop>0){
				clickBi=(clickTop/clickNumTop)*100;
			}
			setInnerHtml(biao, "("+(parseInt(obj.click)+parseInt(clickAdd))+")");
			setInnerHtml(biao+"_test", "("+(parseInt(obj.testClick)+ parseInt(clickAddTest))+")");
			setInnerHtml(biao+"_ratio", clickBi.toFixed(2)+ "%");
		}else{
			if (obj.click == 0) {
				setInnerHtml(biao, "(无明细" + (!!obj.total ? ("[" + obj.total + "]") : "") + ")");
			} else {
				setInnerHtml(biao, "(" + obj.click + ")");
			}
			setInnerHtml(biao + "_test", "(" + (!!obj.testClick ? ("[" + obj.testClick + "]") : "0") + ")");
			setInnerHtml(biao + "_ratio", obj.clickRatio + "%");
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
	//	testTop();
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
	//alert(path);
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
				.append("<tr class=\"odd\"><td><span style=\"font-weight:normal;\">占比(点击次数)</span></td><td>生活圈</td><td>快店</td><td>活动/话题</td><td>维修</td><td>物业客服</td><td>快递</td><td>便捷号码</td><td>送水</td><td>公告通知</td></tr>");
		
		var liList=0;
		
		var mapTimeTotal={};
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
			var mapHe;
			if(toalClick!=0){
				 mapHe={
					"rido16":((mapObjHe16.click/toalClick)*100).toFixed(2),	
					"click16":mapObjHe16.click,	
					"testClick16":mapObjHe16.testClick,	
					
					"rido3":((mapObjHe3.click/toalClick)*100).toFixed(2),	
					"click3":mapObjHe3.click,	
					"testClick3":mapObjHe3.testClick,	
					
					"rido1":((mapObjHe1.click/toalClick)*100).toFixed(2),	
					"click1":mapObjHe1.click,	
					"testClick1":mapObjHe1.testClick,	
					
					"rido9":((mapObjHe9.click/toalClick)*100).toFixed(2),	
					"click9":mapObjHe9.click,	
					"testClick9":mapObjHe9.testClick,	
					
					"rido7":((mapObjHe7.click/toalClick)*100).toFixed(2),	
					"click7":mapObjHe7.click,	
					"testClick7":mapObjHe7.testClick,	
					
					"rido8":((mapObjHe8.click/toalClick)*100).toFixed(2),	
					"click8":mapObjHe8.click,	
					"testClick8":mapObjHe8.testClick,	
					
					"rido10":((mapObjHe10.click/toalClick)*100).toFixed(2),	
					"click10":mapObjHe10.click,	
					"testClick10":mapObjHe10.testClick,	
					
					"rido6":((mapObjHe6.click/toalClick)*100).toFixed(2),	
					"click6":mapObjHe6.click,	
					"testClick6":mapObjHe6.testClick,	
					
					"rido0":((mapObjHe0.click/toalClick)*100).toFixed(2),	
					"click0":mapObjHe0.click,	
					"testClick0":mapObjHe0.testClick	
					
				};
			}else{
				 mapHe={
							"rido16":0,	
							"click16":mapObjHe16.click,	
							"testClick16":mapObjHe16.testClick,	
							
							"rido3":0,	
							"click3":mapObjHe3.click,	
							"testClick3":mapObjHe3.testClick,	
							
							"rido1":0,	
							"click1":mapObjHe1.click,	
							"testClick1":mapObjHe1.testClick,	
							
							"rido9":0,	
							"click9":mapObjHe9.click,	
							"testClick9":mapObjHe9.testClick,	
							
							"rido7":0,	
							"click7":mapObjHe7.click,	
							"testClick7":mapObjHe7.testClick,	
							
							"rido8":0,	
							"click8":mapObjHe8.click,	
							"testClick8":mapObjHe8.testClick,	
							
							"rido10":0,	
							"click10":mapObjHe10.click,	
							"testClick10":mapObjHe10.testClick,	
							
							"rido6":0,	
							"click6":mapObjHe6.click,	
							"testClick6":mapObjHe6.testClick,	
							
							"rido0":0,	
							"click0":mapObjHe0.click,	
							"testClick0":mapObjHe0.testClick	
							
						};
				
			}
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
					liList += "<td><p><span>0%</span><span>(0)</span></p><p><span class=\"green\"></span><span class=\"green\">(0)</span></p></td>";
					liList += "<td><p><span>0%</span><span>(0)</span></p><p><span class=\"green\"></span><span class=\"green\">(0)</span></p></td>";
					liList += "<td><p><span>0%</span><span>(0)</span></p><p><span class=\"green\"></span><span class=\"green\">(0)</span></p></td>";
					liList += "<td><p><span>0%</span><span>(0)</span></p><p><span class=\"green\"></span><span class=\"green\">(0)</span></p></td>";
					liList += "<td><p><span>0%</span><span>(0)</span></p><p><span class=\"green\"></span><span class=\"green\">(0)</span></p></td>";
					liList += "<td><p><span>0%</span><span>(0)</span></p><p><span class=\"green\"></span><span class=\"green\">(0)</span></p></td>";
					liList += "<td><p><span>0%</span><span>(0)</span></p><p><span class=\"green\"></span><span class=\"green\">(0)</span></p></td>";
					liList += "<td><p><span>0%</span><span>(0)</span></p><p><span class=\"green\"></span><span class=\"green\">(0)</span></p></td>";
					liList += "<td><p><span>0%</span><span>(0)</span></p><p><span class=\"green\"></span><span class=\"green\">(0)</span></p></td>";
		            liList +="</tr>";
					
				}else{
					liList += "<td>"+d[i]+"</td>";
					liList += "<td><p><span>"+mapHeShi.rido16+"%</span><span>("+mapHeShi.click16+")</span></p><p><span class=\"green\"></span><span class=\"green\">("+mapHeShi.testClick16+")</span></p></td>";
					liList += "<td><p><span>"+mapHeShi.rido3+"%</span><span>("+mapHeShi.click3+")</span></p><p><span class=\"green\"></span><span class=\"green\">("+mapHeShi.testClick3+")</span></p></td>";
					liList += "<td><p><span>"+mapHeShi.rido1+"%</span><span>("+mapHeShi.click1+")</span></p><p><span class=\"green\"></span><span class=\"green\">("+mapHeShi.testClick1+")</span></p></td>";
					liList += "<td><p><span>"+mapHeShi.rido9+"%</span><span>("+mapHeShi.click9+")</span></p><p><span class=\"green\"></span><span class=\"green\">("+mapHeShi.testClick9+")</span></p></td>";
					liList += "<td><p><span>"+mapHeShi.rido7+"%</span><span>("+mapHeShi.click7+")</span></p><p><span class=\"green\"></span><span class=\"green\">("+mapHeShi.testClick7+")</span></p></td>";
					liList += "<td><p><span>"+mapHeShi.rido8+"%</span><span>("+mapHeShi.click8+")</span></p><p><span class=\"green\"></span><span class=\"green\">("+mapHeShi.testClick8+")</span></p></td>";
					liList += "<td><p><span>"+mapHeShi.rido10+"%</span><span>("+mapHeShi.click10+")</span></p><p><span class=\"green\"></span><span class=\"green\">("+mapHeShi.testClick10+")</span></p></td>";
					liList += "<td><p><span>"+mapHeShi.rido6+"%</span><span>("+mapHeShi.click6+")</span></p><p><span class=\"green\"></span><span class=\"green\">("+mapHeShi.testClick6+")</span></p></td>";
					liList += "<td><p><span>"+mapHeShi.rido0+"%</span><span>("+mapHeShi.click0+")</span></p><p><span class=\"green\"></span><span class=\"green\">("+mapHeShi.testClick0+")</span></p></td>";
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
	
	
	

	
//	schedule();
//	var path = "/api/v1/communities/"+communityId+"/userStatistics/getLaunchesDetailsStatistics?startTime="+startTime+"&endTime="+endTime;
//	
//	$.ajax({
//		url : path,
//		type : "POST",
//		dataType : 'json',
//		success : function(data) {
//			data = data.info;
//			var userCount=0;
//
//			var repair_overman = $("#statistics_list_id");
//			repair_overman.empty();
//			repair_overman
//					.append("<tr class=\"odd\"><td><span style=\"font-weight:normal;\">占比(点击次数)</span></td><td>生活圈</td><td>快店</td><td>活动/话题</td><td>维修</td><td>物业客服</td><td>快递</td><td>便捷号码</td><td>送水</td><td>公告通知</td></tr>");
//			
//			var liTop="";
//			var liList="";
//			var userSumNum=0;
//			var testUserSumNum=0;
//			var userClickNum=0;
//			var testClickNum=0;
//			var mapDate={};
//			var arrSimple=new Array();
//			for(var i=0;i< data.length;i++){
//				mapDate[data[i]._id.time]=data[i];
//				arrSimple[i]=data[i]._id.time;
//			}
//			arrSimple.sort();
//			for ( var i = arrSimple.length-1; i>=0; i--) {
//				var data2=mapDate[arrSimple[i]];
//				var userNum=0;
//				var testUserNum=0;
//				var userClick=data2.userClick;
//				map[data2._id.time]=userClick;
//				var testClick=0;
//				var total=0;
//				for ( var j = 0; j < userClick.length; j++) {
//					if(mapUser[userClick[j].emobId]=="1"){
//						if(mapTryOut[userClick[j].emobId]!="1"){
//							userNum++;
//							total+=userClick[j].click;
//						}else{
//							testUserNum++;
//						    testClick+=userClick[j].click;
//						}
//					}
//				}
//				
//				
//
//				if (i % 2 == 0) {
//					liList += "<tr class=\"even\">";
//				} else {
//
//					liList += "<tr class=\"odd\">";
//				}
//				userSumNum+=userNum;
//				testUserSumNum+=testUserNum;
//				liList += "<td>"+getStringTime(data2._id.time*24*60*60*1000)+"</td>";
//				liList += "<td><p><span>占比</span><span>（次数）</span></p><p><span class=\"green\">10.1%</span><span class=\"green\">(13)</span></p></td>";
//				liList += "<td><p><span>占比</span><span>（次数）</span></p><p><span class=\"green\">10.1%</span><span class=\"green\">(13)</span></p></td>";
//				liList += "<td><p><span>占比</span><span>（次数）</span></p><p><span class=\"green\">10.1%</span><span class=\"green\">(13)</span></p></td>";
//				liList += "<td><p><span>占比</span><span>（次数）</span></p><p><span class=\"green\">10.1%</span><span class=\"green\">(13)</span></p></td>";
//				liList += "<td><p><span>占比</span><span>（次数）</span></p><p><span class=\"green\">10.1%</span><span class=\"green\">(13)</span></p></td>";
//				liList += "<td><p><span>占比</span><span>（次数）</span></p><p><span class=\"green\">10.1%</span><span class=\"green\">(13)</span></p></td>";
//				liList += "<td><p><span>占比</span><span>（次数）</span></p><p><span class=\"green\">10.1%</span><span class=\"green\">(13)</span></p></td>";
//				liList += "<td><p><span>占比</span><span>（次数）</span></p><p><span class=\"green\">10.1%</span><span class=\"green\">(13)</span></p></td>";
//				liList += "<td><p><span>占比</span><span>（次数）</span></p><p><span class=\"green\">10.1%</span><span class=\"green\">(13)</span></p></td>";
//                liList +="</tr>";
//                userClickNum+=total;
//                testClickNum+=testClick;
//                
//			//	userCount+= data[i].total;
//				
//			}
//			liTop+="<tr class=\"even\">";
//			liTop+=	"<td>合计</td>";
//			liTop+=	"<td><p><span>占比</span><span>（次数）</span></p><p><span class=\"green\">10.1%</span><span class=\"green\">(13)</span></p></td>";
//			liTop+=	"<td><p><span>占比</span><span>（次数）</span></p><p><span class=\"green\">10.1%</span><span class=\"green\">(13)</span></p></td>";
//			liTop+=	"<td><p><span>占比</span><span>（次数）</span></p><p><span class=\"green\">10.1%</span><span class=\"green\">(13)</span></p></td>";
//			liTop+=	"<td><p><span>占比</span><span>（次数）</span></p><p><span class=\"green\">10.1%</span><span class=\"green\">(13)</span></p></td>";
//			liTop+=	"<td><p><span>占比</span><span>（次数）</span></p><p><span class=\"green\">10.1%</span><span class=\"green\">(13)</span></p></td>";
//			liTop+=	"<td><p><span>占比</span><span>（次数）</span></p><p><span class=\"green\">10.1%</span><span class=\"green\">(13)</span></p></td>";
//			liTop+=	"<td><p><span>占比</span><span>（次数）</span></p><p><span class=\"green\">10.1%</span><span class=\"green\">(13)</span></p></td>";
//			liTop+=	"<td><p><span>占比</span><span>（次数）</span></p><p><span class=\"green\">10.1%</span><span class=\"green\">(13)</span></p></td>";
//			liTop+=	"<td><p><span>占比</span><span>（次数）</span></p><p><span class=\"green\">10.1%</span><span class=\"green\">(13)</span></p></td>";
//			liTop+=	"</tr>";
//			repair_overman.append(liTop);
//			repair_overman.append(liList);
//			document.getElementById("master_repir_startTime").value = startTime;
//			document.getElementById("master_repir_endTime").value = endTime;
//			
//			if(type!=""){
//				timeQuantum(startTime, endTime);
//				timeDisplay(type);
//			}else{
//				timeQuantum(startTime, endTime);
//			}
//			onSchedule();
//		},
//		error : function(er) {
//			onSchedule();
//		//	alert("网络连接失败...");
//		//	top.location='/'; 
//		}
//	});
}

function launchesDetails(time) {
	schedule();
    var userList=map[time];
	var map2={};
	var map3={};
    var path = "/api/v1/communities/"+communityId+"/userStatistics/getUserList";
	
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
		    var dataUser=	data.listUsers;
		    var dataOut=	data.listTryOut;
			for ( var i = 0; i < dataUser.length; i++) {
				map2[dataUser[i].emobId]=dataUser[i];
			}
			for ( var i = 0; i < dataOut.length; i++) {
				map3[dataOut[i].emobId]="1";
			}
			var repair_overman = $("#statistics_list_id");
			repair_overman.empty();
			repair_overman
					.append("<tr class=\"odd\"><td>用户昵称</td><td>电话号码</td><td>访问主界面次数</td></tr>");
			var liTop="";
			var liList="";
		
			for ( var i = 0; i < userList.length; i++) {
				
				if(map2[userList[i].emobId]==undefined){
					
					continue;
				}
				if (i % 2 == 0) {
					liList += "<tr class=\"even\">";
				} else {

					liList += "<tr class=\"odd\">";
				}
				if(map3[userList[i].emobId]==1){
					liList += "<td > <b class=\"greenword\">"+map2[userList[i].emobId].nickname+"</b></td><td> <b class=\"greenword\">"+map2[userList[i].emobId].username+"</b></td>";
				}else{
					
					if(map2[userList[i].emobId].username==""||map2[userList[i].emobId].username=="null"){
						liList += "<td >游客</td><td >游客</td>";
						
					}else{
						liList += "<td >"+map2[userList[i].emobId].nickname+"</td><td >"+map2[userList[i].emobId].username+"</td>";
					}
					
					
				}
				
                liList +="<td>"+userList[i].click+"</td></tr>";
			}
			
			repair_overman.append(liList);
			onSchedule();
			
		},
		error : function(er) {
			onSchedule();
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
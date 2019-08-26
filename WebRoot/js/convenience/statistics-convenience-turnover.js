var communityId=window.parent.document.getElementById("community_id_index").value;	
var mapTryOut={};
function  getQuickShopTop(biao,sort,startTime,endTime){
	    schedule();
	    var path = "/api/v1/communities/"+communityId+"/users/1/orderHistories/getQuickShopTop?sort="+sort+"&startTime="
				+ startTime + "&endTime=" + endTime;
		$.ajax({
			url : path,
			type : "GET",
			dataType : 'json',
			success : function(data) {
				data = data.info;
				
				if(!data.orderPrice>0){
					data.orderPrice=0;
				}
				if(!data.testOrderPrice>0){
					data.testOrderPrice=0;
				}
				if(!data.onlinePrice>0){
					data.onlinePrice=0;
				}
				if(!data.testOnlinePrice>0){
					data.testOnlinePrice=0;
				}
				if(!data.bonusPar>0){
					data.bonusPar=0;
				}
				if(!data.testPonusPar>0){
					data.testPonusPar=0;
				}
				
				
               document.getElementById(biao+"_price").innerHTML=data.orderPrice;				
               document.getElementById(biao+"_test_price").innerHTML="("+data.testOrderPrice+")";				
               document.getElementById(biao+"_online").innerHTML=data.onlinePrice;				
               document.getElementById(biao+"_test_online").innerHTML="("+data.testOnlinePrice+")";				
               document.getElementById(biao+"_no_online").innerHTML=(parseFloat(data.orderPrice)-parseFloat(data.onlinePrice)).toFixed(2);				
               document.getElementById(biao+"_test_no_online").innerHTML="("+(parseFloat(data.testOrderPrice)-parseFloat(data.testOnlinePrice)).toFixed(2)+")";				
               document.getElementById(biao+"_bonus_par").innerHTML=data.bonusPar;				
               document.getElementById(biao+"_test_bonus_par").innerHTML="("+data.testPonusPar+")";				
				
			
				onSchedule();
			},
			error : function(er) {
				top.location = '/';
			}
		});
		}
getTop();
function quickUserData(){
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + myDate.getDate()
			+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
	getQuickShopTop('this_data',2,startTime,endTime);
	
}
function thisUserData(){
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate()-1;
	var startTime = myDate.getFullYear() + "-" + month + "-" +da 
	+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" + myDate.getDate() + " 00:00:00";
	getQuickShopTop('to_day',2,startTime,endTime);
	
}

function monthUser() {
	var myDate = new Date();
	var month = myDate.getMonth();
	var month2 = myDate.getMonth()+1;


	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month2 + "-1" + " 00:00:00";
	getQuickShopTop('last_month',2,startTime,endTime);
}
function thisMonthUser() {
	var myDate = new Date();
	var month = myDate.getMonth()+1;
	var month2 = myDate.getMonth()+2;
	
	
	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month2 + "-1" + " 00:00:00";
	getQuickShopTop('this_month',2,startTime,endTime);
}
function weekUser() {

	var d = getPreviousWeekStartEnd();
	var startTime = d.start;
	var endTime = d.end;
	getQuickShopTop('last_week',2,startTime,endTime);

}
function benWeekUser() {
	
	var d = getWeekBenUp();
	var startTime = d.start;
	var endTime = d.end;
	getQuickShopTop('this_week',2,startTime,endTime);
	
}
function totalUser() {
	
	var myDate = new Date();
	var month = myDate.getMonth()+1;
	var startTime ="2015-6-1 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-"+ (myDate.getDate()+1) + " 00:00:00";
	
	getQuickShopTop('total_id',2,startTime,endTime);
}
function getTop() {
	//getUserTop();
	thisUserData();
	quickUserData();
	weekUser() ;
	monthUser();
	thisMonthUser();
	benWeekUser();
	totalUser();
}	
		

	
	
	
	function quickShopData(sort) {
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var da = myDate.getDate() + 1;
		var startTime = myDate.getFullYear() + "-" + month + "-"
				+ myDate.getDate()+" 00:00:00";
		var endTime = myDate.getFullYear() + "-" + month + "-" + da+" 00:00:00";
		//getQuickShopList(1,startTime, endTime);
		getTurnoverList(sort,1,startTime,endTime,'日');
		//getQuickShopTop(sort,startTime, endTime);
	}
	
	function maintainMonth(sort) {

		var myDate = new Date();
		var month = myDate.getMonth() ;
		var month2 = myDate.getMonth()+1 ;

		var startTime = myDate.getFullYear() + "-" + month + "-"
				+ 1+" 00:00:00";
		var endTime = myDate.getFullYear() + "-" + month2 + "-"
				+1+" 00:00:00";
       // getQuickShopList(1, startTime, endTime);
	    getTurnoverList(sort,1,startTime,endTime,'月');
	//	getQuickShopTop(sort,startTime, endTime);

	}
		
	function getMasterRepairDetailPage(num) {
		var page_num = 1;
		var repairRecordPageNum = document
				.getElementById("master_repair_datai_PageNum_get").value;
		var repairRecordPageSize = document
				.getElementById("master_repair_datai_PageSize_get").value;

		var startTime = document.getElementById("master_repir_startTime").value;
		var endTime = document.getElementById("master_repir_endTime").value;

        var is_list_detail = document.getElementById("is_list_detail").value;
		var shopId = document.getElementById("shop_id_detail").value;
        var time_day=document.getElementById("time_day").value;
     
		if (num == -1) { // 上一页

			if (repairRecordPageNum != 1) {
				page_num = repairRecordPageNum - 1;
			} else {
				alert("已经是第一页了");
				return;
			}

		} else if (num == -2) { // 下一页
			if (parseInt(repairRecordPageNum) < parseInt(repairRecordPageSize)) {
				page_num = parseInt(repairRecordPageNum) + parseInt(1);
			} else {
				alert("已经是最后一页了");
				return;
			}

		} else if (num == -3) { // 首页
			if (repairRecordPageNum != 1) {
				page_num = 1;
			} else {
				alert("已经是首页了");
				return;
			}
		} else if (num == -4) { // 尾页
			if (parseInt(repairRecordPageNum) < parseInt(repairRecordPageSize)) {
				page_num = repairRecordPageSize;
			} else {
				alert("已经是尾页了");
				return;
			}
		}
		
	    if(is_list_detail=="detail"){
	    	getShopsOrderDetailList(page_num,shopId);
		}else{
			selectData(time_day,page_num);
		}
		
	}
	function weekMonth(sort) {

		var d = getPreviousWeekStartEnd();
		var startTime = d.start;
		var endTime = d.end;

		getTurnoverList(sort,1,startTime,endTime,'周');
	//	getQuickShopTop(sort,startTime, endTime);
//
	}
	function selectStatistics(){
	   var  sort=document.getElementById("master_repir_sort_fei").value;
	   var startTime= document.getElementById("txtBeginDate").value+" 00:00:00";
	   var endTime= document.getElementById("txtEndDate").value+" 00:00:00";
		getTurnoverList(sort,1,startTime,endTime);
	//	getQuickShopTop(sort,startTime, endTime);
	   
	}

	
	function getShopsOrderDetailList(pageNum,shopId){
		schedule();
	   document.getElementById("shop_id_detail").value=shopId;
	   document.getElementById("is_list_detail").value="detail";
	   var startTime = document.getElementById("master_repir_startTime").value;
	   var endTime = document.getElementById("master_repir_endTime").value;
	   var path = "/api/v1/communities/"+communityId+"/users/1/orderHistories/getQuickShopOrderDetail?pageSize=50"+
	   "&pageNum="+pageNum+"&shopId="+shopId+"&startTime="+startTime+"&endTime="+endTime;		
		$.ajax({
					url : path,
					type : "GET",
					dataType : 'json',
					success : function(data) {
						data = data.info;
						$("#master_repair_datai_PageNum_get").val(data.num);
						$("#master_repair_datai_PageSize_get").val(
								data.pageCount);

						$("#master_repair_datai_PageNum").html(data.num);
						$("#master_repair_datai_PageSize").html(data.pageCount);
						$("#master_repair_datai_sum").html(data.rowCount);
						data = data.pageData;

						var repair_overman = $("#statistics_list_id");
						repair_overman.empty();

						//var liList='';
						var myDate = new Date();
						var month = myDate.getMonth() + 1;
						var da = myDate.getDate()-1;
						var da2 = myDate.getDate() - 2;
						var startTime1 = myDate.getFullYear() + "-" + month + "-"
								+ da+" 00:00:00";
						var endTime1 = myDate.getFullYear() + "-" + month + "-" + myDate.getDate()+" 00:00:00";
						var startTime2 = myDate.getFullYear() + "-" + month + "-" + da2+" 00:00:00";
						
						
                        repair_overman
								.append("<tr class=\"odd\"><td>"+data[0].shopName+"</td><td>交易额</td>	<td>交易比例	</td><td>线上交易额</td>	<td>线上交易额占比</td></tr>");
                    
                        var orderPrice=document.getElementById("turnover_zong_get").value;
						for ( var i = 0; i < data.length; i++) {
							  var jiao=0;
							  var noline=0;
	                            if(orderPrice!=0){
	                            	jiao=((data[i].orderPrice/orderPrice)*100).toFixed(2);
	                            }
	                            if(data[i].orderPrice!=0){
	                            	noline=((data[i].onlinePrice/ data[i].orderPrice)*100).toFixed(2);
	                            	
	                            }
							    var liList = "";
						        if(i%2==0){
		                        	liList += "<tr class=\"even\">";
		                        }else{
		                        	
		                        	liList += "<tr class=\"odd\">";
		                        }
								
                          
							    liList += "<td>";

								var myDate = new Date(data[i].startTime*1000);

								var startTimeFilter = myDate.getMonth() + 1 + "月" + myDate.getDate() + "日";
								liList+=startTimeFilter;
						        liList+= "</td><td >"
									+ data[i].orderPrice + "<b class=\"greenword\">("+data[i].testOrderPrice+")</b></td>";
									
							    liList += "<td>"+jiao+"%</td><td>"
									+ data[i].onlinePrice
									+ "<b class=\"greenword\">("+data[i].testOnlinePrice+")</b></td><td>"+noline+"%</td></tr>";
									
							repair_overman.append(liList);
							onSchedule();
						}
					},
					error : function(er) {
						top.location = '/';
					}
				});
	
	}
	

	
	function timeClick(clickId) {
		if (clickId == "data_clik") {
			document.getElementById("data_clik").className = "select";
			var  master_repir_sort=document.getElementById("master_repir_sort_fei").value;
             quickShopData(master_repir_sort);
		} else {
			document.getElementById("data_clik").className = "";
		}
		if (clickId == "week_clik") {
			document.getElementById("week_clik").className = "select";
			var  master_repir_sort=document.getElementById("master_repir_sort_fei").value;
			weekMonth(master_repir_sort);
		} else {
			document.getElementById("week_clik").className = "";
		}
		if (clickId == "month_clik") {
			document.getElementById("month_clik").className = "select";
			var  master_repir_sort=document.getElementById("master_repir_sort_fei").value;
			maintainMonth(master_repir_sort);
		} else {
			document.getElementById("month_clik").className = "";
		}
	}
	function getTurnoverList(sort,pageNum,startTime,endTime,type){
       // document.getElementById("shop_sort_id").value;
	  var path = "/api/v1/communities/"+communityId+"/users/1/orderHistories/getTurnoverList?pageSize=50&pageNum="
				+ pageNum
				+ "&sort="+sort+"&startTime="
				+ startTime
				+ "&endTime="
				+ endTime;
				
		    $.ajax({
					url : path,
					type : "GET",
					dataType : 'json',
					success : function(data) {
						data = data.info;
						$("#master_repair_datai_PageNum_get").val(data.num);
						$("#master_repair_datai_PageSize_get").val(
								data.pageCount);

						$("#master_repair_datai_PageNum").html(data.num);
						$("#master_repair_datai_PageSize").html(data.pageCount);
						$("#master_repair_datai_sum").html(data.rowCount);
						data = data.pageData;

						var repair_overman = $("#statistics_list_id");
						repair_overman.empty();

						//var liList='';
                    
                    repair_overman
								.append("<tr class=\"odd\"><td>统计时间</td><td>营业额</td><td>线上交易额</td><td>线下交易额</td><td>帮帮券交易额</td><td>详情</td></tr>");
                	var myDate = new Date();
					var month = myDate.getMonth() + 1;
					var da = myDate.getDate()-1;
					var da2 = myDate.getDate() - 2;
					var startTime1 = myDate.getFullYear() + "-" + month + "-"
							+ da+" 00:00:00";
					var endTime1 = myDate.getFullYear() + "-" + month + "-" + myDate.getDate()+" 00:00:00";
					var startTime2 = myDate.getFullYear() + "-" + month + "-" + da2+" 00:00:00";
					
					
                      var orderPrice=document.getElementById("turnover_zong_get").value;
                      
                      var topOrderPrice=0.0;
                      var testTopOrderPrice=0.0;
                      var topOnlinePrice=0.0;
                      var testTopOnlinePrice=0.0;
                      
                      var topBonusPar=0.0;
                      var topTestBonusPar=0.0;
                      var map2={};
                      for ( var i = 0; i < data.length; i++) {
  						map2[getStringTime(data[i].startTime * 1000)] = data[i];
  					}
                      
                      
                      var shi = show(getStringTime(stringToTime(startTime)),
  							getStringTime(stringToTime(endTime)));
                      var liList = "";
						for ( var i = shi.length - 2; i >=0; i--) {
							var data2=map2[shi[i]];
							if (data2 == undefined) {
								var data2 = {
									"orderPrice" :0,
									"testOrderPrice" : 0,
									"onlinePrice" : 0,
									"testOnlinePrice" : 0,
									"bonusPar" : 0,
									"testBonusPar" : 0
								};
							}
							
							 if(data2.orderPrice==""){
								 data2.orderPrice=0;
							  }
							  if(data2.testOrderPrice==""){
								  data2.testOrderPrice=0;
							  }
							  if(data2.onlinePrice==""){
								  data2.onlinePrice=0;
							  }
							  if(data2.testOnlinePrice==""){
								  data2.testOnlinePrice=0;
							  }
							  if(data2.bonusPar==""){
								  data2.bonusPar=0;
							  }
							  if(data2.testBonusPar==""){
								  data2.testBonusPar=0;
							  }
	                            topOrderPrice+=parseFloat(data2.orderPrice);
	                            testTopOrderPrice+=parseFloat(data2.testOrderPrice);
	                            topOnlinePrice+= parseFloat(data2.onlinePrice);
	                            testTopOnlinePrice+=parseFloat(data2.testOnlinePrice);
	                            topBonusPar+=parseFloat(data2.bonusPar);
	                            topTestBonusPar+=parseFloat(data2.testBonusPar);

						       if(i%2==0){
		                        	liList += "<tr class=\"even\">";
		                        }else{
		                        	
		                        	liList += "<tr class=\"odd\">";
		                        }
								
								
								liList += "<td>" + shi[i]+"</td>";
						        liList += "<td>" + data2.orderPrice+ "<b class=\"greenword\">("+data2.testOrderPrice +")</b></td>";
							 //   liList += "<td><a onclick=\"selectData('"+data[i].startTime+"','1')\"><b>"+jiao+"</b><b class=\"greenword\">(21)</b></a></td>";
							    liList += "<td>" + data2.onlinePrice + "<b class=\"greenword\">("+data2.testOnlinePrice +")</b></td>";
							    liList += "<td>" + (parseFloat(data2.orderPrice)-parseFloat(data2.onlinePrice)).toFixed(2) + "<b class=\"greenword\">("+(parseFloat(data2.testOrderPrice)-parseFloat(data2.testOnlinePrice)).toFixed(2) +")</b></td>";
							    liList += "<td>"+data2.bonusPar  +"<b class=\"greenword\">("+data2.testBonusPar+")</b></td>";
							    liList += "<td><a onclick=\"userShopTry('"
									+ data2.shopName + "','0','" + shi[i] + "')\">详情</a></b></td>";
							    liList += "</tr>";
						}
						
						   var liTop="";
	    					liTop+="<tr class=\"even\"><td>合计</td>";
	    					liTop+="<td>"+topOrderPrice+"<b class=\"greenword\">("+testTopOrderPrice +")</b></td>";
	    					liTop+="<td>"+topOnlinePrice+"<b class=\"greenword\">("+testTopOnlinePrice +")</b></td>";
	    					liTop+="<td>"+(parseFloat(topOrderPrice)-parseFloat(topOnlinePrice)).toFixed(2)+"<b class=\"greenword\">("+(parseFloat(testTopOrderPrice)-parseFloat(testTopOnlinePrice)).toFixed(2) +")</td>";
	    					liTop+="<td>"+topBonusPar+"<b class=\"greenword\">("+topTestBonusPar +")</td>";
	    					liTop+="</tr>";
	    					repair_overman.append(liTop);
	    					repair_overman.append(liList);
						
						
							document.getElementById("master_repir_sort_fei").value=sort;
							
							
							timeQuantum(startTime, endTime);

							timeDisplay(type);
							
							
							onSchedule();
					},
					error : function(er) {
						onSchedule();
						top.location = '/';
					}
				});
	
	}
	
	
	
	function userShopTry(shopName, shopId, startTime) {
		
		
		
		var path = "/api/v1/communities/" + communityId
				+ "/userStatistics/getUserList";

		$.ajax({
			url : path,
			type : "GET",
			dataType : 'json',
			success : function(data) {
				data = data.info;
				var tryOut = data.listTryOut;
				var listUsers = data.listUsers;
				for ( var i = 0; i < tryOut.length; i++) {
					mapTryOut[tryOut[i].usersId] = "1";
				}
				getUserShop(shopName, shopId, startTime);
				onSchedule();
			},
			error : function(er) {
				onSchedule();
				top.location = '/';
			}
		});
	}
	
	
	function getUserShop(shopName, shopId, startTime) {
		
		startTime+=" 00:00:00";
		
		
		 
		var path = "/api/v1/communities/" + communityId
				+ "/users/1/orderHistories/getUserShop?shopId=" + shopId
				+ "&startTime=" + startTime + "&endTime=0" ;

		$
				.ajax({
					url : path,
					type : "GET",
					dataType : 'json',
					success : function(data) {
						data = data.info;

						var repair_overman = $("#statistics_list_id");
						repair_overman.empty();

						// var liList='';
						repair_overman
								.append("<tr ><th>店家名称</th><th>买家昵称</th><th>买家电话</th><th>下单时间</th><th>结单时间</th><th>金额</th><th>订单状态</th><th>支付方式</th></tr>");

						for ( var i = 0; i < data.length; i++) {
							var liList = "";

							if (i % 2 == 0) {
								liList += "<tr class=\"even\">";
							} else {
								liList += "<tr class=\"odd\">";
							}
							if (mapTryOut[data[i].userId] == "1") {

								liList += "<td ><b class=\"greenword\">" + shopName + "</b></td>";

								liList += "<td><b class=\"greenword\">" + data[i].nickname + "</b></td>";
								liList += "<td><b class=\"greenword\">" + data[i].username + "</b></td>";
								liList += "<td><b class=\"greenword\">"
										+ toStringTime(data[i].startTime * 1000)
										+ "</b></td>";
								if (data[i].endTime == "0") {
									liList += "<td><b class=\"greenword\"></b></td>";
								} else {
									liList += "<td><b class=\"greenword\">"
											+ toStringTime(data[i].endTime * 1000)
											+ "</b></td>";
								}

								liList += "<td><b class=\"greenword\">" + data[i].orderPrice + "</b></td>";
							} else {

								liList += "<td>" + shopName + "</td>";

								liList += "<td>" + data[i].nickname + "</td>";
								liList += "<td>" + data[i].username + "</td>";
								liList += "<td>"
										+ toStringTime(data[i].startTime * 1000)
										+ "</td>";
								if (data[i].endTime == "0") {
									liList += "<td></td>";
								} else {
									liList += "<td>"
											+ toStringTime(data[i].endTime * 1000)
											+ "</td>";
								}

								liList += "<td>" + data[i].orderPrice + "</td>";
							}
							liList+="<td>";
							if(data[i].status=="new"){
								liList+="新建订单";
							}else if(data[i].status=="canceled"){
								liList+="取消";
							}else if(data[i].status=="renounce"){
								liList+="放弃";
							}else if(data[i].status=="paid"){
								liList+="已支付";
							}else if(data[i].status=="ended"){
								liList+="已结单";
							}else{
								liList+="进行中";
							}
							liList+="</td>";
							
							if(data[i].online=="yes"){
							 liList+="<td> 在线支付</td>";
							}else if(data[i].online=="no"){
								 liList+="<td> 线下支付</td>";
							}else{
								 liList+="<td> 未支付</td>";
							}
							liList += "</tr>";
							repair_overman.append(liList);
							onSchedule();
						}
					},
					error : function(er) {
						onSchedule();
						top.location = '/';
					}
				});
	}

	
function selectData(tim,pageNum) {
	  document.getElementById("time_day").value=tim;
		var myDate = new Date(tim*1000);
		var month = myDate.getMonth() + 1;
		var da = myDate.getDate()+1;
		var startTime = myDate.getFullYear() + "-" + month + "-"
				+ myDate.getDate() + " 00:00:00";
		var endTime = myDate.getFullYear() + "-" + month + "-" +  da
				+ " 00:00:00";
		getQuickShopList(pageNum, startTime, endTime);
}

function alterMonth(num) {

	var startTime = document.getElementById("master_repir_startTime").value;
	var sort = document.getElementById("master_repir_sort_fei").value;
	var type = document.getElementById("date_type_get").value;
	var d=alterDate(num,type,startTime);
	var startTime=d.start;
	var endTime=d.end;
	getTurnoverList(sort,1,startTime,endTime,type);
	//getQuickShopTop(sort,startTime, endTime,type);
}
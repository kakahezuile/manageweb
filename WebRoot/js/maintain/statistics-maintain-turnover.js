var communityId=window.parent.document.getElementById("community_id_index").value;	
function  getQuickShopTop(sort,startTime,endTime,type){
	schedule();
		         var path = "/api/v1/communities/"+communityId+"/users/1/orderHistories/getQuickShopTop?sort="+sort+"&startTime="
				+ startTime + "&endTime=" + endTime;
		$.ajax({
			url : path,
			type : "GET",
			dataType : 'json',
			success : function(data) {
				data = data.info;
				document.getElementById("turnover_xianxia").innerHTML=data.orderPrice-data.onlinePrice;
				document.getElementById("turnover_xianshang").innerHTML=data.onlinePrice;
				document.getElementById("turnover_zong").innerHTML=data.orderPrice;
				document.getElementById("turnover_xianxia_test").innerHTML="("+(data.testOrderPrice-data.testOnlinePrice)+")";
				document.getElementById("turnover_xianshang_test").innerHTML="("+data.testOnlinePrice+")";
				document.getElementById("turnover_zong_test").innerHTML="("+data.testOrderPrice+")";
				
				
				
				
				document.getElementById("turnover_zong_get").value=data.orderPrice;
				
				var myDate = new Date(stringToTime(startTime));
				var myDate2 = new Date(stringToTime(endTime));
				var dates = Math.abs((myDate2 - myDate)) / (1000 * 60 * 60 * 24);

				
				var orderPrice = data.orderPrice / dates;
				var onlinePrice = data.onlinePrice / dates;
				document.getElementById("avg_day_turnover").innerHTML=orderPrice.toFixed(2);
				document.getElementById("avg_day_order").innerHTML=onlinePrice.toFixed(2);
				
				
				timeQuantum(startTime, endTime);

				timeDisplay(type);
				onSchedule();
			},
			error : function(er) {
				onSchedule();
			}
		});
		}
		
		
function getQuickShopList(pageNum,startTime,endTime){
		var sort=document.getElementById("master_repir_sort_fei").value;
		       var path = "/api/v1/communities/"+communityId+"/users/1/orderHistories/getQuickShopList?pageSize=50&pageNum="
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
								.append("<tr class=\"odd\"><td>统计时间	</td><td>交易额</td>	<td>交易比例	</td><td>线上交易额</td>	<td>线上交易额占比</td><td>明细</td></tr>");

                          var orderPrice=document.getElementById("turnover_zong_get").value;
                          
                         
                          
                          var liList = "";
						for ( var i = 0; i < data.length; i++) {
							var bi=0;
							if(data[i].onlinePrice!=0){
								bi=((data[i].onlinePrice/data[i].orderPrice)*100).toFixed(1);
							}
                           var jiao=0;
                        	  if(orderPrice!=0){
                        		jiao=((data[i].orderPrice/orderPrice)*100).toFixed(2);  
                        	  } 
                        	   
                        	 

						     if(i%2==0){
		                        	liList += "<tr class=\"even\">";
		                        }else{
		                        	
		                        	liList += "<tr class=\"odd\">";
		                        }
								
						//	liList += "<tr class=\"odd\">";
							liList += "<td>" + data[i].shopName + "</td><td >"
									+ data[i].orderPrice + "<b class=\"greenword\">("+data[i].testOrderPrice+")</b></td>";
							liList += "<td>"+jiao+"%</td><td>"
									+ data[i].onlinePrice
									+ "<b class=\"greenword\">("+data[i].testOnlinePrice+")</b></td>";
						
							liList+="<td>"+bi+"%</td>";
							liList+="<td><a onclick=\"getShopsOrderDetailList('1','"+data[i].shopId+"')\">详情</a></td></tr>";
								
							

						}
					
	    					repair_overman.append(liList);
				document.getElementById("master_repir_startTime").value=startTime;
								document.getElementById("master_repir_endTime").value=endTime;
								onSchedule();
			},
			error : function(er) {
				onSchedule();
			}
		});
	}
	
	
	
	function quickShopData(sort) {
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var da = myDate.getDate() + 1;
		var startTime = myDate.getFullYear() + "-" + month + "-"
				+ myDate.getDate()+" 00:00:00";
		var endTime = myDate.getFullYear() + "-" + month + "-" + da+" 00:00:00";
		//getQuickShopList(1,startTime, endTime);
		getTurnoverList(sort,1,startTime,endTime);
		getQuickShopTop(sort,startTime, endTime,'日');
	}
	
	function maintainMonth(sort) {

		var myDate = new Date();
		myDate.getFullYear();
		myDate.getMonth();
		var month = myDate.getMonth() ;

		var firstDate = new Date();

		firstDate.setDate(1); //第一天

		var endDate = new Date(firstDate);

		endDate.setMonth(firstDate.getMonth());

		endDate.setDate(0);

		var startTime = myDate.getFullYear() + "-" + month + "-"
				+ 1+" 00:00:00";
		var endTime = myDate.getFullYear() + "-" + month + "-"
				+ endDate.getDate()+" 00:00:00";
       // getQuickShopList(1, startTime, endTime);
	   getTurnoverList(sort,1,startTime,endTime);
		getQuickShopTop(sort,startTime, endTime,'月');

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
		//	time_day
			selectData(time_day,page_num);
		// getQuickShopList(page_num, startTime, endTime);
		}
		 
		
		//getQuickShopTop(startTime, endTime);
	}
	function weekMonth(sort) {

		var d = getPreviousWeekStartEnd();
		// alert(d.start+d.end);
		var startTime = d.start;
		var endTime = d.end;

		getTurnoverList(sort,1,startTime,endTime);
		getQuickShopTop(sort,startTime, endTime,'周');

	}
	function selectStatistics(){
	   var  sort=document.getElementById("master_repir_sort_fei").value;
	   var startTime= document.getElementById("txtBeginDate").value+" 00:00:00";
	   var endTime= document.getElementById("txtEndDate").value+" 00:00:00";
		getTurnoverList(sort,1,startTime,endTime);
		getQuickShopTop(sort,startTime, endTime);
	   
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
						
						
	                    var startTime_day_long=stringToTime(startTime1)/1000;
	                    var endTime_day_long=stringToTime(endTime1)/1000;
	                    var startTime2_day_long=stringToTime(startTime2)/1000;
                    repair_overman
								.append("<tr class=\"odd\"><td>"+data[0].shopName+"</td><td>交易额</td>	<td>交易比例	</td><td>线上交易额</td>	<td>线上交易额占比</td></tr>");
                    
                      var orderPrice=document.getElementById("turnover_zong_get").value;
                     
                      var liList = "";
						for ( var i = 0; i < data.length; i++) {
							  var jiao=0;
							  var noline=0;
                            if(orderPrice!=0){
                            	jiao=((data[i].orderPrice/orderPrice)*100).toFixed(2);
                            }
                            if(data[i].orderPrice!=0){
                            	noline=((data[i].onlinePrice/ data[i].orderPrice)*100).toFixed(2);
                            	
                            }
                           
						     if(i%2==0){
		                        	liList += "<tr class=\"even\">";
		                        }else{
		                        	
		                        	liList += "<tr class=\"odd\">";
		                        }
								
                           // liList += "<tr class=\"odd\">";
                          
							liList += "<td>";
							
								

								var myDate = new Date(data[i].startTime*1000);

								var startTimeFilter = myDate.getMonth() + 1 + "月" + myDate.getDate() + "日";
								liList+=startTimeFilter;
						liList+= "</td><td >"
									+ data[i].orderPrice + "<b class=\"greenword\">("+data[i].testOrderPrice+")</b></td>";
									
							liList += "<td>"+jiao+"%</td><td>"
									+ data[i].onlinePrice
									+ "<b class=\"greenword\">("+data[i].testOnlinePrice+")</b></td><td>"+noline+"%</td></tr>";
									
							

						}
						
						var jiaoTop=0;
						var nolineTop=0;
						if(orderPrice!=0){
							jiaoTop=((topOrderPrice/orderPrice)*100).toFixed(2);
							
						}
                        if(topOrderPrice!=0){
                        	nolineTop=((topOnlinePrice/topOrderPrice)*100).toFixed(2);
                        }
						   
	    					repair_overman.append(liList);
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
	function getTurnoverList(sort,pageNum,startTime,endTime){
		schedule();
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
								.append("<tr class=\"odd\"><td>统计时间</td><td>交易额</td>	<td>交易比例	</td><td>线上交易额</td>	<td>线上交易额占比</td><td>明细</td></tr>");
                	var myDate = new Date();
					var month = myDate.getMonth() + 1;
					var da = myDate.getDate()-1;
					var da2 = myDate.getDate() - 2;
					var startTime1 = myDate.getFullYear() + "-" + month + "-"
							+ da+" 00:00:00";
					var endTime1 = myDate.getFullYear() + "-" + month + "-" + myDate.getDate()+" 00:00:00";
					var startTime2 = myDate.getFullYear() + "-" + month + "-" + da2+" 00:00:00";
					
					
                      var orderPrice=document.getElementById("turnover_zong_get").value;
                      
                      var topOrderPrice=0;
                      var testTopOrderPrice=0;
                      var topOnlinePrice=0;
                      var testTopOnlinePrice=0;
                  	var liList = "";
						for ( var i = 0; i < data.length; i++) {
							 var jiao=0;
							 var noline=0;
							
							if(orderPrice!=0){
								jiao=((data[i].orderPrice/orderPrice)*100).toFixed(2);
								
							}
                            if( data[i].orderPrice!=0){
                            	 noline=((data[i].onlinePrice/ data[i].orderPrice)*100).toFixed(2);
                            }
                            topOrderPrice+=data[i].orderPrice;
                            testTopOrderPrice+=data[i].testOrderPrice;
                            topOnlinePrice+= data[i].onlinePrice;
                            testTopOnlinePrice+= data[i].testOnlinePrice;
						

						     if(i%2==0){
		                        	liList += "<tr class=\"even\">";
		                        }else{
		                        	
		                        	liList += "<tr class=\"odd\">";
		                        }
								
                         //   liList += "<tr class=\"odd\">";
						     liList += "<td>" ;
								

								var myDate = new Date(data[i].startTime*1000);

								var startTimeFilter = myDate.getMonth() + 1 + "月" + myDate.getDate() + "日";
								liList+=startTimeFilter;
						liList += "</td><td >"
									+ data[i].orderPrice+ "<b class=\"greenword\">("+data[i].testOrderPrice +")</b></td>";
									
							liList += "<td>"+jiao+"%</td><td>"
									+ data[i].onlinePrice
									+ "<b class=\"greenword\">("+data[i].testOnlinePrice +")</b></td><td>"+noline+"%</td><td ><a onclick=\"selectData('"+data[i].startTime+"','1')\">查看</a></td></tr>";
									
							

						}
						var jiaoTop=0;
						var nolineTop=0;
						if(orderPrice!=0){
							jiaoTop=((topOrderPrice/orderPrice)*100).toFixed(2);
							
						}
                        if(topOrderPrice!=0){
                        	nolineTop=((topOnlinePrice/topOrderPrice)*100).toFixed(2);
                        }
						var liTop="";
    					liTop+="<tr class=\"even\"><td>合计</td>";
    					liTop+="<td>"+topOrderPrice+"<b class=\"greenword\">("+testTopOrderPrice +")</b></td>";
    					liTop+="<td>"+jiaoTop+"%</td>";
    					liTop+="<td>"+topOnlinePrice+"<b class=\"greenword\">("+testTopOnlinePrice +")</td>";
    					liTop+="<td>"+nolineTop+"</td>";
    					liTop+="<td>&nbsp;</td>";
    					liTop+="</tr>";
    					repair_overman.append(liTop);
    					repair_overman.append(liList);
							document.getElementById("master_repir_sort_fei").value=sort;
						//	document.getElementById("shop_sort_id").value=sort;
							onSchedule();
					},
					error : function(er) {
						onSchedule();
					}
				});
	
	}
	
function selectData(tim,pageNum) {
	  document.getElementById("time_day").value=tim;
//	var da=tim*1000;
		var myDate = new Date(tim*1000);
		var month = myDate.getMonth() + 1;
		var da = myDate.getDate()+1;
		var startTime = myDate.getFullYear() + "-" + month + "-"
				+ myDate.getDate() + " 00:00:00";
		var endTime = myDate.getFullYear() + "-" + month + "-" +  da
				+ " 00:00:00";
		getQuickShopList(pageNum, startTime, endTime);
	//	getUseAmountList(sort,1,startTime,endTime);
		//getQuickShopTop(startTime, endTime);
	}

function alterMonth(num) {

	var startTime = document.getElementById("master_repir_startTime").value;
	var sort = document.getElementById("master_repir_sort_fei").value;
	var type = document.getElementById("date_type_get").value;
	var d=alterDate(num,type,startTime);
	var startTime=d.start;
	var endTime=d.end;
	// alert(startTime+endTime);
	// getQuickShopList(1, startTime, endTime);
	getTurnoverList(sort,1,startTime,endTime);
	getQuickShopTop(sort,startTime, endTime,type);
}
var communityId=window.parent.document.getElementById("community_id_index").value;
function getReckoning(sort, pageNum, startTime, endTime) {
	var myd=new Date();
	myDate=myd.getFullYear()+"-"+myd.getMonth()+1+"-"+myd.getDate()+" 00:00:00";
	var path = "/api/v1/communities/"+communityId+"/users/1/orderHistories/getPayOff?pageSize=10&pageNum="
			+ pageNum
			+ "&sort="
			+ sort
			+ "&startTime="
			+ startTime+"&myTime="+myDate
			+ "&endTime=" + endTime;
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			$("#master_repair_datai_PageNum_get").val(data.num);
			$("#master_repair_datai_PageSize_get").val(data.pageCount);
			$("#master_repair_datai_PageNum").html(data.num);
			$("#master_repair_datai_PageSize").html(data.pageCount);
			$("#master_repair_datai_sum").html(data.rowCount);
			data = data.pageData;
			var repair_overman = $("#reckonign_shop");
			repair_overman.empty();
			repair_overman.append("<tr><th>店家名称</th>" + "<th>银行卡号</th>"
					+ "<th>联系方式</th>" + "<th>在线支付</th><th>帮帮券支付</th>" + "<th>扣款申请</th>"
					+ "<th>扣款金额</th>" + "<th>结款金额</th>" + "<th>交易记录</th>"
					+ "<th><a href=\"#\"></a>结款</a></th></tr>");

			for ( var i = 0; i < data.length; i++) {
				var list="";
				 list+="<tr class=\"odd\">";
				 list+="<td>"+data[i].shopName+"</td>";
				 list+="<td>"+data[i].cardNo+"</td>";
				 list+="<td>"+data[i].phone+"</td>";
				 list+="<td>"+(data[i].priceSum-data[i].bonusSum)+"</td>";
				 list+="<td>"+data[i].bonusSum+"</td>";
				 list+="<td><a class=\"reckoning\" href=\"/jsp/operation/reckoning/reckoning-deduct.jsp?emobId="+data[i].emobId+"\">"+data[i].charge+"</a></td>";
				 list+="<td>"+data[i].deductionPrice+"</td>";
				 list+="<td>"+(data[i].priceSum-data[i].deductionPrice)+"</td>";
				 list+="<td><a href=\"/jsp/operation/reckoning/reckoning-record.jsp?shopId="+data[i].shopId+"\">交易记录</a></td>";
				 if(data[i].status=="ended"){
					 list+="<td>已结</td>";
				 }else{
					 list+="<td><a class=\"reckoning\" href=\"javascript:;\" onclick=\"jikuan('"+data[i].emobId+"','"+data[i].cardNo+"','"+(data[i].priceSum-data[i].charge)+"');\" >结款</a></td></tr>";
					 // list+="<td><a class=\"reckoning\" href=\"javascript:;\"  >结款</a></td></tr>";
				 }
				repair_overman.append(list);

			}
			 document.getElementById("master_repir_startTime").value=startTime;
			 document.getElementById("master_repir_endTime").value=endTime;
		},
		error : function(er) {
		}
	});
}


function getMasterRepairDetailPage(num) {
	var page_num = 1;
	var repairRecordPageNum = document
			.getElementById("master_repair_datai_PageNum_get").value;
	var repairRecordPageSize = document
			.getElementById("master_repair_datai_PageSize_get").value;

	var startTime = document.getElementById("master_repir_startTime").value;
	var endTime = document.getElementById("master_repir_endTime").value;

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


}


function jikuan(emobIdShop,cardNo,onlinePrice) {
	if(confirm('确定要结款吗？')){
		var communityId = window.parent.document
		.getElementById("community_id_index").value;

		var emobIdAgent = window.parent.document
		.getElementById("emobId_id_index").value;
		var startTime= document.getElementById("master_repir_startTime").value;
		var startTime2=(stringToTime(startTime)/1000);
		var  endTime =document.getElementById("master_repir_endTime").value;
	var 	endTime2=(stringToTime(endTime)/1000);
		var path = "/api/v1/communities/"+communityId+"/deduction/addClearing";
		  var myJson="{\"emobIdShop\":\""+emobIdShop+"\",\"emobIdAgent\":\""+emobIdAgent+"\",\"cardNo\":\""+cardNo+
		  "\",\"onlinePrice\":\""+onlinePrice+"\",\"startTime\":\""+startTime2+"\",\"endTime\":\""+endTime2+"\",\"communityId\":\""+communityId+"\"}";
	$.ajax({
		url : path,
		type : "POST",
		 data : myJson ,
		dataType : 'json',
		success : function(data) {
			if(data.status=="yes"){
				alert("成功");
			
			}
			getReckoning(2, 1, startTime, endTime);
		},
		error : function(er) {
		}
	});	
	}
	
}
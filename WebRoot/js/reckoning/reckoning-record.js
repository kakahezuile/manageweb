var communityId=window.parent.document.getElementById("community_id_index").value;
function getTradingRecord(shopId, pageNum, startTime, endTime) {
	var path = "/api/v1/communities/"+communityId+"/users/1/orderHistories/getTradingRecord?pageSize=10&pageNum="
			+ pageNum
			+ "&shopId="
			+ shopId
			+ "&startTime="
			+ startTime
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
			var repair_overman = $("#reckonign_record");
			repair_overman.empty();
			repair_overman.append("<tr><th>用户</th>	<th>地址</th><th>线上交易金额</th><th>是否投诉</th></tr>");

			for ( var i = 0; i < data.length; i++) {
				var list="";
				 list+="<tr class=\"odd\">";
				 list+="<td>"+data[i].userName+"</td>";
				 list+="<td>"+data[i].communityAddress+data[i].userFloor+data[i].userUnit+data[i].room+"</td>";
				 list+="<td>"+data[i].onliePrice+"</td>";
				
				 if(data[i].isComplaint=="yes"){
					 list+="<td>是</td>";
				 }else{
					 list+="<td></tr>";
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
var communityId=window.parent.document.getElementById("community_id_index").value;
function getReckoning(emobId, pageNum, startTime, endTime) {
	var path = "/api/v1/communities/"+communityId+"/deduction/getDeductMoney?pageSize=10&pageNum="
		+ pageNum
		+ "&emobId="
		+ emobId
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
			var repair_overman = $("#reckoning_deduct");
			repair_overman.empty();
			repair_overman.append("<tr><th>扣款申请客服</th>" + "<th>申请人电话</th>"
					+ "<th>扣款说明</th>" + "<th>扣款金额</th><th>投诉详情</th><th>交易详情</th><th><a href=\"#\"></a>扣款</a></th></tr>");
			for ( var i = 0; i < data.length; i++) {
				var list="";
				 list+="<tr class=\"odd\">";
				 list+="<td>"+data[i].nickname+"</td>";
				 list+="<td>"+data[i].phone+"</td>";
				 list+="<td>"+data[i].deductionDetail+"</td>";
				 list+="<td>"+data[i].deductionPrice+"</td>";
				 list+="<td><a href=\"/jsp/operation/reckoning/reckoning-complain.jsp?from="+data[i].emobIdFrom+"&to="+data[i].emobIdTo+"&isMoreThan=1&complaint_id="+data[i].complaintId+"\">投诉详情</a></td>";
				 list+="<td><a href=\"/jsp/operation/reckoning/reckoning-complain.jsp?from="+data[i].emobIdFrom+"&to="+data[i].emobIdAgent+"&isMoreThan=2&complaint_id="+data[i].complaintId+"\">交易详情</a></td>";
				 if(data[i].status=="ended"){
					 list+="<td>已扣</td></tr>";
					 
				 }else{
					 
					 list+="<td><a class=\"reckoning\" onclick=\"activityRefuse('"+data[i].deductionId+"');\" href=\"javascript:;\">扣款</a></td></tr>";
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

function deduction(){
    var deduction_price=document.getElementById("deduction_price").value;
    var deduction_id=document.getElementById("deduction_id").value;
    var path="/api/v1/communities/"+communityId+"/deduction/"+deduction_id;
    var myJson="{\"deductionPrice\":\""+deduction_price+"\"}";
	$.ajax({
		url : path,
		type : "PUT",
        data : myJson ,
		dataType : "json",
		success : function(data) {
              alert("成功");
		},
		error : function(er) {
		}

	});
  
  
}




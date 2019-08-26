var communityId=window.parent.document.getElementById("community_id_index").value;
function selectNearbyCrazySalesShop(pageNum,pageSize) {
	var path = "/api/v1/nearby/selectNearbyCrazySalesShop";
    var facilitiesName_nearby=	document.getElementById("facilitiesName_nearby").value;
	var myJson={"title":"%"+facilitiesName_nearby+"%","descr":"%"+facilitiesName_nearby+"%","communityId":communityId,"pageNum":pageNum,"pageSize":pageSize};
	$.ajax({
		url : path,
		type: "POST",
		data:JSON.stringify(myJson),
		dataType : 'json',
		success : function(data) {
			data = data.info;

			$("#repairRecordPageNum_get").val(data.num);
			$("#repairRecordPageSize_get").val(data.pageCount);

			$("#repairRecordPageNum").html(data.num);
			$("#repairRecordPageSize").html(data.pageCount);
			$("#repairRecordSum").html(data.rowCount);
			
			data = data.pageData;
			
			var repair_overman = $("#facilitiesName_nearby_table");
			repair_overman.empty();

			var tableList="";
			for ( var i = 0; i < data.length; i++) {
				tableList+="<tr>";
					tableList+="<td>"+data[i].title+"</td>";
						tableList+="<td>"+data[i].shopName+"</td>";
						
						var myDate = new Date(data[i].createTime*1000);

						var startTimeFilter =myDate.getFullYear()+"年"+ (myDate.getMonth() + 1) + "月"
								+ myDate.getDate() + "日";
						tableList+="<td>"+startTimeFilter+"</td>";
						tableList+="<td class=\"operation\">";
						tableList+="<a href=\"javascript:;\"  onclick=\"quick_shop_test();getCrazySales('"+data[i].crazySalesId+"');\"  class=\"green-button\">编辑</a>";
						tableList+="<a href=\"javascript:;\" class=\"red-button\" onclick=\"delectCrazySalesShop('"+data[i].crazySalesId+"');\" >删除</a>";
						tableList+="</td>";
						tableList+="</tr>";
								
			}
			
			repair_overman
					.append(tableList);

			
		},
		error : function(er) {
			onSchedule();
		}
	});
}


function delectCrazySalesShop(crazySalesId) {
	var path = "/api/v1/nearby/delectCrazySales";
    var facilitiesName_nearby=	document.getElementById("facilitiesName_nearby").value;
	var myJson={"crazySalesId":crazySalesId};
	$.ajax({
		url : path,
		type: "POST",
		data:JSON.stringify(myJson),
		dataType : 'json',
		success : function(data) {

			alert("成功");
			selectNearbyCrazySalesShop();
		},
		error : function(er) {
			onSchedule();
		}
	});
}

function getRepairRecordList2(num) {
	var page_num = 1;
	var repairRecordPageNum = document
			.getElementById("repairRecordPageNum_get").value;
	var repairRecordPageSize = document
			.getElementById("repairRecordPageSize_get").value;
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
	selectNearbyCrazySalesShop(page_num,10);
	
}



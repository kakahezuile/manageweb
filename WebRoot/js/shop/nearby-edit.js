function getCrazySales(crazySaleId) {
	var path = "/api/v1/nearby/getCrazySales";
	var myJson={"crazySalesId":crazySaleId};
	$.ajax({
		url : path,
		type: "POST",
		data:JSON.stringify(myJson),
		dataType : 'json',
		success : function(data) {
			data=data.info;
			document.getElementById("crazy_sales_id").value=data.crazySalesId;
			document.getElementById("nearby_edit_img").src=data.imgUrl;
			document.getElementById("nearby_edit_img_input").value=data.imgUrl;
			document.getElementById("nearby_edit_title").value=data.title;
			document.getElementById("nearby_edit_total").value=data.total;
			document.getElementById("nearby_edit_per_limit").value=data.perLimit;
			document.getElementById("nearby_edit_descr").value=data.descr;
		},
		error : function(er) {
			onSchedule();
		}
	});
}

function upCrazySales() {
	var crazySaleId=document.getElementById("crazy_sales_id").value;
	var imgUrl=document.getElementById("nearby_edit_img").src;
	var imgUrlInput=document.getElementById("nearby_edit_img_input").value;
	var title=document.getElementById("nearby_edit_title").value;
	var total=document.getElementById("nearby_edit_total").value;
	var perLimit=document.getElementById("nearby_edit_per_limit").value;
	var descr=document.getElementById("nearby_edit_descr").value;
	var path = "/api/v1/nearby/upImgCrazySales?crazySalesId="+crazySaleId;
	upCrazy({
		"crazySalesId":crazySaleId,
		"title":title,
		"total":total,
		"perLimit":perLimit,
		"descr":descr
	});
	
	if(imgUrl==imgUrlInput){
		alert("成功");
		return;
	}
	
	$.ajaxFileUpload({
		url: path,
		secureuri: false,
		fileElementId: 'crazySales_file_edit',
		type: 'POST',
		dataType : 'json',
		success: function (result) {
			alert("成功");
		}
	});
}

function upCrazy(myJson) {
	var path = "/api/v1/nearby/upCrazySales";
	$.ajax({
		url : path,
		type: "POST",
		data:JSON.stringify(myJson),
		dataType : 'json',
		success : function(data) {
			data=data.info;
			
		},
		error : function(er) {
			onSchedule();
		}
	});
}
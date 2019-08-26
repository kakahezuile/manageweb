var communityId=window.parent.document.getElementById("community_id_index").value;
$(document).ready(function() {
	head_select();
});

var map={};
var mapShop={};
var mapTryOut={};
/*
 * function head_select(){ $(".operation-nav").find("ul li
 * a").removeClass("select"); $(".operation-nav").find("ul li
 * a[mark=statistics]").addClass("select"); $(".left-body").find("ul li
 * a").removeClass("select"); $(".left-body").find("ul li
 * a[mark=shop]").addClass("select"); }
 */
function head_select() {
	$(".operation-nav").find("ul li a").removeClass("select");
	$(".operation-nav").find("ul li a[mark=statistics]").addClass("select");
	$(".left-body").find("ul li a").removeClass("select");
	$(".left-body").find("ul li a[mark=lifecircle]").addClass("select");

}
function tryOut() {
	  var path = "/api/v1/communities/"+communityId+"/userStatistics/getUserList";
		
		$.ajax({
			url : path,
			type : "GET",
			dataType : 'json',
			success : function(data) {
				data = data.info;
				var tryOut=data.listTryOut;
				var listUsers=data.listUsers;
				for ( var i = 0; i < tryOut.length; i++) {
					mapTryOut[tryOut[i].emobId]="1";
				}
				getQuickShopList();
			},
			error : function(er) {
				schedule();
				//alert("网络连接失败...");
			//	top.location='/'; 
			}
		});
	}

function getQuickShopTop() {
	var path = "/api/v1/communities/"+communityId+"/userStatistics/getMoralStatistics";
	$.ajax({
				url : path,
				type : "GET",
				dataType : 'json',
				success : function(data) {
					data = data.info;
				//	alert( data.characterValues100);
					document.getElementById("avg_num_id").innerHTML = data.avgCharacterValues;
					document.getElementById("num_id_50").innerHTML = data.characterValues50;

					document.getElementById("num_id_100").innerHTML = data.characterValues100;
					document.getElementById("num_id_200").innerHTML = data.characterValues200;
					document.getElementById("num_id_500").innerHTML = data.characterValues500;
					document.getElementById("num_id").innerHTML = data.characterValues;

				},
				error : function(er) {
					//alert("网络连接失败...");
				}
			});
}


function getQuickShopList() {

	var path = "/api/v1/communities/"+communityId+"/userStatistics/getMoralList";
	$.ajax({
				url : path,
				type : "GET",
				dataType : 'json',
				success : function(data) {
					data = data.info;
					var repair_overman = $("#statistics_list_id");
					repair_overman.empty();
					repair_overman
							.append("<tr class=\"odd\"><th>排名</th><th>人品值</th>	<th>昵称</th><th>手机号</th>	<th>真实地址</th></tr>");
					var liList="";
					for ( var i = 0; i < data.length; i++) {
						var line = i%2;
						if(line==0){
							liList+="<tr class=\"even\">";
						}else{
							liList+="<tr class=\"odd\">";
						}
						if(mapTryOut[data[i].emobId]=="1"){
							liList+="<td class=\"greenword\">"+(i+1)+"</td>";
							liList+="<td class=\"greenword\">"+data[i].characterValues+"</td>";
							liList+="<td class=\"greenword\">"+data[i].nickname+"</td>";
							liList+="<td class=\"greenword\">"+data[i].username+"</td>";
							liList+="<td class=\"greenword\">"+data[i].userFloor+data[i].userUnit+data[i].room+"</td>";
							liList+="</tr>";
						}else{
							liList+="<td>"+(i+1)+"</td>";
							liList+="<td>"+data[i].characterValues+"</td>";
							liList+="<td>"+data[i].nickname+"</td>";
							liList+="<td>"+data[i].username+"</td>";
							liList+="<td>"+data[i].userFloor+data[i].userUnit+data[i].room+"</td>";
							liList+="</tr>";
						}
						
					}
					repair_overman.append(liList);
				},
				error : function(er) {
					//alert("网络连接失败...");
				}
			});
}


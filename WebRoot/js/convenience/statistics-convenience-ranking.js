var communityId=window.parent.document.getElementById("community_id_index").value;

var map={};
var mapTryOut =window.parent.mapTryOut;
var mapUser=window.parent.mapUser;



function getQuickShopList(type) {

	var path = "/api/v1/communities/"+communityId+"/users/jhh/orderHistories/getBuyRanking?type="+type;
	$.ajax({
				url : path,
				type : "GET",
				dataType : 'json',
				success : function(data) {
					data = data.info;
					var repair_overman = $("#statistics_list_id");
					repair_overman.empty();
					repair_overman
							.append("<tr class=\"odd\"><th>名字</th><th><a  onclick=\"getQuickShopList('price_num')\">总交易金额</a> </th><th><a  onclick=\"getQuickShopList('trade_num')\">交易次数</a></th></tr>");
					var liList="";
					for ( var i = 0; i < data.length; i++) {
						var line = i%2;
						if(line==0){
							liList+="<tr class=\"even\">";
						}else{
							liList+="<tr class=\"odd\">";
						}
						if(mapTryOut[data[i].emobId]==1){
							liList+="<td class=\"greenword\">"+data[i].name+"</td>";
							liList+="<td class=\"greenword\">"+data[i].priceNum+"</td>";
							liList+="<td class=\"greenword\">"+data[i].tradeNum+"</td>";
							liList+="</tr>";
							
						}else {
							liList+="<td >"+data[i].name+"</td>";
							liList+="<td >"+data[i].priceNum+"</td>";
							liList+="<td >"+data[i].tradeNum+"</td>";
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


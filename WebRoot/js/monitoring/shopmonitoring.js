
var communityId = window.parent.document.getElementById("community_id_index").value;



var advisoryHistoryPageNum = 1;

var advisoryHistoryPageSize = 10;

var advisoryHistoryList;

var advisoryHistoryMinTime;

var advisoryHistoryMaxTime;

var advisoryHistoryEmobId;

var advisoryHistoryIndex;

var advisoryHistoryFirst = 0;

var advisoryHistoryLast = 0;

var advisoryHistoryNext = 0;

var advisoryHistoryPrev = 0;

var advisoryHistoryPageCount = 0;

var advisoryHisotrySum = 0;
var transactionMap={};
var messageArray;
function  getShopDelie() {
	var path="/api/v1/communities/"+communityId+"/users/1/orderHistories/getShopDelie?sort=2";
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data=data.info;
			var repair_overman = $("#sensitiveWordsTable");
			repair_overman.empty();
			
			repair_overman
					.append("<tr>"+
			   			"<th>用户名</th>"+
			   			"<th>住址</th>"+
			   			"<th>电话</th>"+
			   			"<th>交易单量</th>"+
			   			"<th>详情</th>"+
			   		"</tr>");
			 var liList="";
			for ( var i = 0; i < data.length; i++) {

				liList+="<tr>";
				liList+="<th>"+data[i].nickname+"</th>";
				liList+="<th>"+data[i].userFloor+data[i].userUnit+data[i].room+"</th>";
				liList+="<th>"+data[i].username+"</th>";
				liList+="<th>"+data[i].orderNum+"</th>";
				liList+="<th><a  href=\"javascript:void(0);\"  onclick=\"getMessageListFromUser3('"+data[i].emobIdShop+"','"+data[i].emobIdUser+"')\">详情</a></th>";
				liList+="</tr>";
				

			}
			repair_overman.append(liList);
		},
		error : function(er) {
		}
	});	
}

function getMessageListFromUser3(from,to){
	schedule();
	var path = "/api/v1/usersMessages?messageFrom="+from+"&messageTo="+to;
	$.ajax({
		
		url: path,
		
		type: "GET",
		
		dataType:"json",
		
		success:function(data){
			console.info(data);
			if(data.status == "yes"){
				data = data.info;		
				var len = data.length;
				messageArray = new Array();
				for(var i = 0 ; i < len ; i++){
					var message = {
							"from":data[i].messageFrom ,
							"to":data[i].messageTo,
							"msg":data[i].msg,
							"timestamp":data[i].timestamp,
							"type":data[i].msgType,
							"data":data[i].msg,
							"url":data[i].url,
							"ext":{
								"nickname":data[i].nickname,
								"avatar":data[i].avatar,
								"content":data[i].content
							}
					};
					
					messageArray.push(message);
				}
				onSchedule();
				transactionMap[from+to] = messageArray;
				getMyTouSuMessageList3(from,to);
			}
		},
		
		error:function(er){
			onSchedule();
			
		}
		
	});
}

function getMyTouSuMessageList3(my,to) {
	var myThis = $("#sensitiveWordsTable");
	myThis.html("");
	var myClassName = my+to;
	//tousuThisUser = myClassName;

	if (transactionMap[myClassName] != undefined) {
		var myTouSuList = transactionMap[myClassName];
		for ( var i = 0; i < myTouSuList.length; i++) {
			var message = myTouSuList[i];
			var from = message.from;//消息的发送者
			var mestype = message.type;//消息发送的类型是群组消息还是个人消息
			var messageContent = message.data;//文本消息体
			var timestamp=message.timestamp;
			  var myDate=new Date(timestamp);
			    var sen=   myDate.getFullYear() + '-'
						+ ('0' + (myDate.getMonth() + 1)).slice(-2) + '-'
						+ ('0' + myDate.getDate()).slice(-2)+" "+myDate.getHours()+":"+myDate.getMinutes()+":"+myDate.getSeconds();
				myThis.append("<div class=\"chat-time\"><span>"+sen+"</span></div>");
			var ext=message.ext;
			//   根据消息体的to值去定位那个群组的聊天记录
			var room = message.to;
			if (mestype!="txt") {
				var url=message.url;
				if(mestype=="audio"){
					myThis.append("<div class=\"text-left\"><img src=\"/"+ext.avatar+"\"><div class=\"left-speak-frame\"><div class=\"left-speak-box\" style=\"max-width:220px;\">" +
							"<div class=\"left-speak\"><div class=\"radio-style\"><span class=\"radio-icon\">&nbsp;</span><audio controls=\"controls\">  "+
                              "<source src=\""+url+"\" /> "+
                              "</audio></div> </div>" +
							"</div></div><span class=\"chat-me\">&nbsp;</span></div>");

				}else {
					myThis.append("<div class=\"text-left\"><img src=\""+ext.avatar+"\"/><div class=\"left-speak-frame\"><div class=\"left-speak-box\" style=\"max-width:220px;\"><img src=\""+url+"\"></img></div></div><span class=\"chat-me\">&nbsp;</span></div>");

				}
		
	    } else {
				if (from == to) {
						
					myThis.append("<div class=\"text-right\"><img src=\""+ext.avatar+"\"><div class=\"right-speak-frame\"><div class=\"right-speak-box\" style=\"max-width:220px;\"><div class=\"right-speak\" id=\""+to+"_"+from+"_"+i+"\">"+messageContent+"</div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
					//myThis.append("<font style=\"text-align:right;width:100%;display:block;\">"+ messageContent + " : 我 </font><br/>");
				} else {
					myThis.append("<div class=\"text-left\"><img src=\""+ext.avatar+"\"><div class=\"left-speak-frame\"><div class=\"left-speak-box\" style=\"max-width:220px;\"><div class=\"left-speak\" id=\""+from+"_"+to+"_"+i+"\">"+messageContent+"</div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
			}
			
			
			
			}
			
		}
	}
}
function schedule(){
	$("#add-price-box").attr("style","display:block");
	$("#upload-master-face-bg").attr("style","display:block");
	
}

function onSchedule(){
	$("#add-price-box").attr("style","display:none");
	$("#upload-master-face-bg").attr("style","display:none");
}
	
	

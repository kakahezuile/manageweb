var communityId=document.getElementById("community_id").value;
var admin_status=document.getElementById("admin_status").value;
$(document).ready(function(){
	head_select();
});
function head_select(){
	$(".navbar").find("ul li a").removeClass("select");
	$(".navbar").find("ul li a[mark=tousuchuli]").addClass("select");
}

//投诉页面JS
var tous_fa=0;

var tousuThisUser = "";
function open(){
	//$("#tousu_contactlistUL li").remove();
		getComplaintList();
}
function getComplaintList(){
    	var sort="";
	    var userName=document.getElementById("tousu_admin_user").value;
	    if(admin_status=="2"){
	    	sort="5";
	    }else if(admin_status=="1"){
	    	sort="2";
	    }else{
	    	return ;
	    }
    	var detail = "";
		var complaintId = 0;
		var emobIdFrom = "";
		var emobIdTo = "";
		var nickname = "";
		var orderId = 0;
		var room = "";
		var title = "";
		var avatar = "";
		var occurTime = 0;
		var path = "/api/v1/communities/"+communityId+"/complaints/webIm/getEmobIdList?pageNum=1&pageSize=200&q="+sort;
		$.ajax({

			url: path,

			type: "GET",

			dataType:"json",

			success:function(data){
			  data=data.info;
			  $("#tousu_contactlistUL").find("li").attr("style",
										"display:block");
				var dataList = data.pageData;
				var a=dataList.length-1;
				for(var i = a; i>=0 ; i--){
					detail = dataList[i].detail;
					complaintId = dataList[i].complaintId;
					emobIdFrom = dataList[i].emobIdFrom;
					if(i == 0){
						tousuThisUser = emobIdFrom;
					}
					emobIdTo = dataList[i].emobIdTo;
					nickname = dataList[i].nickname;
					if(myUserMap[emobIdFrom] == undefined){
						myUserMap[emobIdFrom] = nickname;
					}
					orderId = dataList[i].orderId;
					room = dataList[i].room;
					var userFloor = dataList[i].userFloor;
					var userUnit = dataList[i].userUnit;
					var shopName=dataList[i].shopName;
					title = dataList[i].title;
					avatar = dataList[i].avatar;
					occurTime = dataList[i].occurTime;
					var comId=document.getElementById(complaintId);
					if(comId==null){
						tousuListAddLi(orderId, emobIdFrom, emobIdTo,userFloor,userUnit, room, nickname, avatar , detail , occurTime,complaintId,shopName);
						if(i == 0){
							getComplaintShop(orderId, emobIdFrom, emobIdTo, detail, occurTime,complaintId);
						}
				  }
				}
			
			},

			error:function(er){
			
			
			}

		});
	}

function  isHistory() {
	     var isFrom=document.getElementById("tousu_emob_id_from").value;
	    // var tile=document.getElementById("").value;
		if (historyMap[isFrom] == undefined) {
			getMessageListFromUser2(isFrom,curUserId);
		}else{
			openChatPagesTousu(isFrom,"");
		}
} 
function getComplaintList2(){
  	var sort="";
    var userName=document.getElementById("tousu_admin_user").value;
    if(admin_status=="2"){
    	sort="5";
    }else if(admin_status=="1"){
    	sort="2";
    }else{
    	return ;
    }
	
	var detail = "";
	var complaintId = 0;
	var emobIdFrom = "";
	var emobIdTo = "";
	var nickname = "";
	var orderId = 0;
	var room = "";
	var title = "";
	var avatar = "";
	var occurTime = 0;
	
	var path = "/api/v1/communities/"+communityId+"/complaints/webIm/getEmobIdList?pageNum=1&pageSize=200&q="+sort;
	$.ajax({
		
		url: path,
		
		type: "GET",
		
		dataType:"json",
		
		success:function(data){
			data=data.info;
			$("#tousu_contactlistUL").find("li").attr("style",
			"display:block");
			var dataList = data.pageData;
			var a=dataList.length-1;
			for(var i = a; i>=0 ; i--){
				detail = dataList[i].detail;
				complaintId = dataList[i].complaintId;
				emobIdFrom = dataList[i].emobIdFrom;
				if(i == 0){
					tousuThisUser = emobIdFrom;
				}
				emobIdTo = dataList[i].emobIdTo;
				nickname = dataList[i].nickname;
				if(myUserMap[emobIdFrom] == undefined){
					myUserMap[emobIdFrom] = nickname;
				}
				orderId = dataList[i].orderId;
				room = dataList[i].room;
				var userFloor = dataList[i].userFloor;
				var userUnit = dataList[i].userUnit;
				title = dataList[i].title;
				avatar = dataList[i].avatar;
				occurTime = dataList[i].occurTime;
				var comId=document.getElementById(complaintId);
				var emo=document.getElementById("tousu_"+emobIdFrom);
				var shopName=dataList[i].shopName;
				
				if(emo==null){
//					$("#tousu_contactlistUL").prepend("<li id=\"tousu_"+emobIdFrom+"\" onclick=\"getMyTouSuMessageList('"+emobIdFrom+"');getComplaintShop("+orderId+",'"+emobIdFrom+"','"+emobIdTo+"','"+detail+"','"+occurTime+"','"+complaintId+"');\" class=\""+emobIdFrom+
//							"\"><input id=\""+complaintId+"\" type=\"hidden\" value=\""+complaintId+"\"><img src=\""+avatar+"\"></img><a style=\"display:none;\" href=\"#/"+nickname+"\">"+nickname+"</a><span class=\"username\">"+nickname+"</span><span class=\"room\">("+userFloor+userUnit+room+")</span><span id=\"tousu_span_"+emobIdFrom+"\" class=\"pop-tip\">&nbsp;</span></li>");
					$("#tousu_contactlistUL").prepend("<li id=\"tousu_"+emobIdFrom+"\" onclick=\"tousuTop('"+nickname+"','"+userFloor+userUnit+room+"');qing('compl_"+emobIdFrom+"');openChatPagesTousu('"+emobIdFrom+"','"+nickname+"');\" class=\""+emobIdFrom+
							"\"><a style=\"display:none;\" href=\"#/"+nickname+"\">"+nickname+"</a><div class=\"user-item-list clearfix\"><input id=\""+complaintId+"\" type=\"hidden\" value=\""+complaintId+"\"><img src=\""+avatar+"\"></img><div class=\"chat-user-info\"><p><span class=\"username\">"+nickname+"</span></p><p><span class=\"room\">("+userFloor+userUnit+room+")</span></p>"+
							"</div></div>"  +
									"</li>"+
									"<div id=\"compl_"+emobIdFrom+"\" style=\"display: none;\">" +
									"<input type=\"hidden\" id=\"tousu_avatar_"+emobIdFrom+"\" value=\""+avatar+"\" />"+
									"</div>");
						
				}
				
				if(comId==null){
					var compl=$("#compl_"+emobIdFrom);
					compl.prepend("<div class=\"complain-sub-list\" id=\""+complaintId+"\" onclick=\"tousuTop('"+nickname+"','"+userFloor+userUnit+room+"');getComplaintShop("+orderId+",'"+emobIdFrom+"','"+emobIdTo+"','"+detail+"','"+occurTime+"','"+complaintId+"');\">"+shopName+"->"+detail+"</div>");

				}
			}
			
		},
		
		error:function(er){
			
			
		}
		
	});
}
	
	function tousuListAddLi(orderId, emobIdFrom, emobIdTo,userFloor,userUnit, room, nickname, avatar , detail , occurTime,complaintId,shopName){
		if(avatar=="null"||avatar==""){
			avatar="/images/head_photo.png";
			
		}
		console.info(orderId);
		var comId=document.getElementById("tousu_"+emobIdFrom);
		if(comId!=null){
			var ab=$("#tousu_"+emobIdFrom);
			
			var compl=$("#compl_"+emobIdFrom);
			compl.prepend("<div class=\"complain-sub-list\" id=\""+complaintId+"\" onclick=\"tousuTop('"+nickname+"','"+userFloor+userUnit+room+"');getComplaintShop("+orderId+",'"+emobIdFrom+"','"+emobIdTo+"','"+detail+"','"+occurTime+"','"+complaintId+"');\">"+shopName+"->"+detail+"</div>");
			$("#tousu_contactlistUL").prepend(compl);
			$("#tousu_contactlistUL").prepend(ab);
		}else{
			
			$("#tousu_contactlistUL").prepend("<li id=\"tousu_"+emobIdFrom+"\" onclick=\"tousuTop('"+nickname+"','"+userFloor+userUnit+room+"');qing('compl_"+emobIdFrom+"');openChatPagesTousu('"+emobIdFrom+"','"+nickname+"');getComplaintShop("+orderId+",'"+emobIdFrom+"','"+emobIdTo+"','"+detail+"','"+occurTime+"','"+complaintId+"');\" class=\""+emobIdFrom+
					"\"><a style=\"display:none;\" href=\"#/"+nickname+"\">"+nickname+"</a><div class=\"user-item-list clearfix\"><input id=\""+complaintId+"\" type=\"hidden\" value=\""+complaintId+"\"><img src=\""+avatar+"\"></img><div class=\"chat-user-info\"><p><span class=\"username\">"+nickname+"</span></p><p><span class=\"room\">("+userFloor+userUnit+room+")</span></p>"+
					"</div></div>"  +
							"</li>"+
							"<div id=\"compl_"+emobIdFrom+"\" style=\"display: none;\">" +
							
							"<input type=\"hidden\" id=\"tousu_avatar_"+emobIdFrom+"\" value=\""+avatar+"\" />"+
							
							
							"<div class=\"complain-sub-list\" id=\""+complaintId+"\" onclick=\"tousuTop('"+nickname+"','"+userFloor+userUnit+room+"');getComplaintShop("+orderId+",'"+emobIdFrom+"','"+emobIdTo+"','"+detail+"','"+occurTime+"','"+complaintId+"');\">"+shopName+"->"+detail+"</div>"+
							
							
							"</div>");
			

			
		}
		
	}
	
	
	
	function getMessageListFromUser2(from,to){
		schedule_tousu();
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
								"timestamp":data[i].timestamp,
								"msg":data[i].msg,
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
						historyMap[from] = messageArray;
						myMessageMap={};
						onSchedule_tousu();
						getMyTouSuMessageList(from);
						scroll(from);
					}
				},

				error:function(er){
			
			
				}

			});
	}
	function getMessageListFromUser3(from,to){
		
		schedule_tousu();
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
					transactionMap[from+to] = messageArray;
					onSchedule_tousu();
					getMyTouSuMessageList3(from,to);
				}
			},
			
			error:function(er){
				
				
			}
			
		});
	}
	
	function getComplaintShop(orderId , emobIdFrom , emobIdTo , detail,occurTime,complaintId){
		//setTime();
		var path = "/api/v1/communities/"+communityId+"/complaints/webIm/"
				+ emobIdTo;
		var sumOrder = 0;
		var nickname = "";
		var phone = "";
		var haoping = 0;
		var chaping = 0;
		var zhongping = 0;
		var avatar = "";
		$.ajax({

			url : path,

			type : "GET",

			dataType : "json",

			success : function(data) {

				sumOrder = data.sumOrder;
				nickname = data.nickname;
				phone = data.phone;
				haoping = data.haoping;
				chaping = data.chaping;
				zhongping = data.zhongping;
				avatar = data.avatar;
				var tousu=data.complaintNum;
                var logo=data.logo;
            //    var occurTime=data.occurTime;
				addTouSuMainRight(sumOrder, nickname, phone, haoping, chaping,
						zhongping, avatar,logo, detail, orderId, emobIdFrom,
						emobIdTo, occurTime,complaintId,tousu);

			},

			error : function(er) {


			}

		});
		 homeMarks(emobIdFrom);
	}
	function tousuTop(nickname,room) {
		var tou=$("#tousu_talkTo");
		tou.html("");
		tou.append("<span id=\"name_room\">"+nickname+"<a title=\"编辑地址\" class=\"edit-address\" href=\"javascript:updateAddress();\">&nbsp;</a><i>（"+room+"）</i></span>");
	}

	function addTouSuMainRight(sumOrder, nickname, phone, haoping, chaping,
			zhongping, avatar,logo, detail, orderId, emobIdFrom, emobIdTo, occurTime,complaintId,tousu) {
			document.getElementById("tousu_emob_id_from").value=emobIdFrom;
			document.getElementById("tousu_emob_id_to").value=emobIdTo;
			
			var myDate=new Date(occurTime*1000);
			var year=myDate.getFullYear();
			var month=myDate.getMonth()+1;
			var day=myDate.getDate();
			var hour = myDate.getHours();
			var minute = myDate.getMinutes();
			var second = myDate.getSeconds();
			var time=year+"年"+month+"月"+day+"日"+" "+hour+":"+minute+":"+second;
			
		$("#weixiu_shifu_avir").attr("src",logo);   
		$("#weixiu_shifu_name").html(nickname);
		$("#chengjiao_danshu").html(sumOrder);
		$("#tousu_time").html(time);
		$("#tousu_num").html(tousu);
		$("#tousu_pingjia").html(
				"好评：<span>" + haoping + "</span> 中评：<span>" + zhongping + "</span>差评：<span>" + chaping+"</span>");
		$("#tousu_phone").html(phone);
		$("#tousu_detail").html(detail);
		$("#tousu_detail").attr("title",detail);
		$("#tousu_lie_submit").attr(
				"onclick",
				"getOrderMessages(" + orderId + ",'" + emobIdFrom + "','"
						+ emobIdTo + "','" + occurTime + "')");
		$("#tousu_signed").attr(
				"onclick",
				"signedCreat('"+complaintId+"')");
		$("#tousu_disable").attr(
				"onclick",
				"applyDisable('"+complaintId+"')");
	}

	function getMyTouSuMessageList(from) {
		var tousu=document.getElementById(from+"-tousu-nouser");
		if(tousu==null){
			addChatPagesTousu(from);
			 openChatPagesTousu(from,"");
		}
		var myThis = $("#"+from+"-tousu-nouser");
		var myClassName = from;
		tousuThisUser = myClassName;
		
		var avater=$("#tousu_avatar_"+from).val();
		if(historyMap[myClassName] != undefined){
			var myTouSuList = historyMap[myClassName];
			var len=myTouSuList.length-1;
			for ( var i = len; i >= 0; i--) {
				var message = myTouSuList[i];
				var from = message.from;//消息的发送者
				var mestype = message.type;//消息发送的类型是群组消息还是个人消息
				var messageContent = message.data;//文本消息体
				var timestamp=message.timestamp;
				  var myDate=new Date(timestamp);
				    var sen=   myDate.getFullYear() + '-'
							+ ('0' + (myDate.getMonth() + 1)).slice(-2) + '-'
							+ ('0' + myDate.getDate()).slice(-2)+" "+myDate.getHours()+":"+myDate.getMinutes()+":"+myDate.getSeconds();
					myThis.prepend("<div class=\"chat-time\"><span>"+sen+"</span></div>");
				var ext=message.ext;
				//   根据消息体的to值去定位那个群组的聊天记录
				var room = message.to;
					if (mestype!="txt") {
							var url=message.url;
							if(mestype=="audio"){
								myThis.prepend("<div class=\"text-left\"><img src=\""+avater+"\"/><div class=\"left-speak-frame\"><div class=\"left-speak-box\">" +
										"<div class=\"left-speak\"><div class=\"radio-style\"><span class=\"radio-icon\">&nbsp;</span><audio controls=\"controls\">  "+
                                          "<source src=\""+url+"\" /> "+
                                          "</audio></div> </div>" +
										"</div></div><span class=\"chat-me\">&nbsp;</span></div>");

							}else {
								myThis.prepend("<div class=\"text-left\"><img src=\""+avater+"\"/><div class=\"left-speak-frame\"><div class=\"left-speak-box\"><img src=\""+url+"\"></img></div></div><span class=\"chat-me\">&nbsp;</span></div>");

							}
					
				    } else {

						if (from == curUserId) {
						     	myThis.prepend("<div class=\"text-right\"><img src=\"/images/chat/serviceHeader.png\"><div class=\"right-speak-frame\"><div class=\"right-speak-box\"><div class=\"right-speak\" id=\""+myClassName+"_"+i+"\"></div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
							
						     	 var my=$("#"+myClassName+"_"+i);
								 my.append(appendMsg(from,from,messageContent));
						     	//myThis.append("<font style=\"text-align:right;width:100%;display:block;\">"+ messageContent + " : 我 </font><br/>");
						} else {
							//myThis.prepend("<div class=\"text-left\"><img src=\""+ext.avatar+"\"><div class=\"left-speak-frame\"><div class=\"left-speak-box\"><div class=\"left-speak\">" + messageContent + "</div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
							myThis.prepend("<div class=\"text-left\"><img src=\""+avater+"\"><div class=\"left-speak-frame\"><div class=\"left-speak-box\"><div class=\"left-speak\" id=\""+from+"_"+i+"\"></div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
							 var my=$("#"+from+"_"+i);
							 my.append(appendMsg(from,from,messageContent));
							 
						}
					}

			}
			
		}
	
		
	}
	
	
	function isTransactionMap(){
		 $("#message_table").html("");
	     var isFrom=document.getElementById("tousu_emob_id_from").value;
	     var isTo=document.getElementById("tousu_emob_id_to").value;
		if (transactionMap[isFrom+isTo] == undefined) {
			getMessageListFromUser3(isFrom,isTo);
		}else{
			getMyTouSuMessageList3(isFrom,isTo);
		}
	}
	function qing(com) {
	   var h=	document.getElementById(com).style.display;
		if(h=="none"){
			document.getElementById(com).style.display="";
			
		}else{
			
			document.getElementById(com).style.display="none";
		}
		
		 $("#message_table").html("");
	}
	function getMyTouSuMessageList3(my,to) {
		var myThis = $("#message_table");
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
							
						myThis.append("<div class=\"text-right\"><img src=\""+ext.avatar+"\"><div class=\"right-speak-frame\"><div class=\"right-speak-box\" style=\"max-width:220px;\"><div class=\"right-speak\" id=\""+to+"_"+from+"_"+i+"\"></div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
						 var my=$("#"+to+"_"+from+"_"+i);
						 my.append(appendMsg(from,from,messageContent));
						//myThis.append("<font style=\"text-align:right;width:100%;display:block;\">"+ messageContent + " : 我 </font><br/>");
					} else {
						myThis.append("<div class=\"text-left\"><img src=\""+ext.avatar+"\"><div class=\"left-speak-frame\"><div class=\"left-speak-box\" style=\"max-width:220px;\"><div class=\"left-speak\" id=\""+from+"_"+to+"_"+i+"\"></div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
						 var my=$("#"+from+"_"+to+"_"+i);
						 my.append(appendMsg(from,from,messageContent));					}
				}
			}
		}
	}

	function tousu_send() {
		
		
		var myThis = $("#"+tousuThisUser+"-tousu-nouser");
		var sendVal = $("#tousu_talkInputId").val();
		if(sendVal==""){
			
			return ;
		}
		 setTime(tousuThisUser);
		var nick=document.getElementById("tousu_admin_user").value;
		if(admin_status=="1"){
			var ext={
					avatar:"http://114.215.114.56:8080/images/chat/serviceHeader.png",
					CMD_CODE:403,
					nickname:"帮帮投诉"
			};
			
		}else if(admin_status=="2"){
			var ext={
					avatar:"http://114.215.114.56:8080/images/chat/serviceHeader.png",
					CMD_CODE:403,
					nickname:"物业投诉"
			};
		}
		
		
		
		var options = {
			to : tousuThisUser,
			msg : sendVal,
			type : "chat",
			ext: ext
		};
		 conn.sendTextMessage(options);
		tous_fa++;
		myThis.append("<div class=\"text-right\"><img src=\"/images/chat/serviceHeader.png\"><div class=\"right-speak-frame\"><div class=\"right-speak-box\"><div class=\"right-speak\" id=\"tousu_fa_"+tous_fa+"\"></div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
		//myThis.append("<font style=\"text-align:right;width:100%;display:block;\">"+ sendVal + " : 我</font><br/>");
		 var my=$("#tousu_fa_"+tous_fa);
		 
		 
		 
		 var msgtext = sendVal.replace(/\n/g, '<br>');
		 my.append(appendMsg(curUserId,tousuThisUser,msgtext));
		 $("#tousu_talkInputId").val("");
		
		
		 turnoffFaces_box();
		 scroll(tousuThisUser);
	}
	
	
	function tousu_time(from,time) {
		var myThis = $("#"+from+"-tousu-nouser");
		sendVal = time;
		myThis.append("<div class=\"chat-time\"><span>"+sendVal+"</span></div>");
		
	}
	function setTime(from){
		
	    var myDate=new Date();
	    var sen=   myDate.getFullYear() + '-'
				+ ('0' + (myDate.getMonth() + 1)).slice(-2) + '-'
				+ ('0' + myDate.getDate()).slice(-2)+" "+myDate.getHours()+":"+myDate.getMinutes()+":"+myDate.getSeconds();
	     tousu_time(from,sen);
	}
	 
	
	function signedCreat(complaintId){
	   document.getElementById("complaint_id").value=complaintId;
	   document.getElementById("blank-background").style.display="";
	   document.getElementById("signed-creat-box").style.display="";
	}
	function finishTousu(){
	    var complaintId=document.getElementById("complaint_id").value;
	    var processDetail=document.getElementById("reason").value;
	    document.getElementById("blank-background").style.display="none";
	    document.getElementById("signed-creat-box").style.display="none";
	   	var path = "/api/v1/communities/"+communityId+"/users/1/complaints/"
				+ complaintId;
  		var myJson = "{\"processDetail\":\""+processDetail+"\",\"status\":\"ended\"}";
  		
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
	function applyFor(){
		var emobId = window.parent.document
			.getElementById("emobId_id_index").value;
	    var complaintId=document.getElementById("complaint_id").value;
	    var processDetail=document.getElementById("reason").value;
	    document.getElementById("blank-background").style.display="none";
	    document.getElementById("signed-creat-box").style.display="none";
	   	var path = "/api/v1/communities/"+communityId+"/deduction";
  			
  		var myJson = "{\"deductionDetail\":\""+processDetail+"\",\"complaintId\":\""+complaintId+"\",\"emobIdCustomer\":\""+curUserId+"\"}";
		$.ajax({

			url : path,

			type : "POST",
            data : myJson ,
			dataType : "json",
			success : function(data) {
			var st=data.status;
				if(st=="yes"){
				  alert("成功");
				}else{
				  alert("一条投诉 不能重复处理");
				}
                   
			},

			error : function(er) {


			}

		});
	   
	   
	}
	function applyDisable(complaintId){
		var emobId = window.parent.document
			.getElementById("emobId_id_index").value;
	    //var complaintId=document.getElementById("complaint_id").value;
	    //var processDetail=document.getElementById("reason").value;
	    document.getElementById("blank-background").style.display="none";
	    document.getElementById("signed-creat-box").style.display="none";
	   	var path = "/api/v1/communities/"+communityId+"/deduction";
  			
  		var myJson = "{\"deductionDetail\":\"无效投诉\",\"complaintId\":\""+complaintId+"\",\"emobIdCustomer\":\""+curUserId+"\"}";
		$.ajax({

			url : path,

			type : "POST",
            data : myJson ,
			dataType : "json",
			success : function(data) {
			var st=data.status;
				if(st=="yes"){
				  alert("成功");
				}else{
				  alert("一条投诉 不能重复处理");
				}
                   
			},

			error : function(er) {


			}

		});
	   
	   
	}
	
	function applyCancel(){
		document.getElementById("blank-background").style.display="none";
	    document.getElementById("signed-creat-box").style.display="none";
	}
	function tousu_seek(){
		var detail = "";
		var complaintId = 0;
		var emobIdFrom = "";
		var emobIdTo = "";
		var nickname = "";
		var orderId = 0;
		var room = "";
		var title = "";
		var avatar = "";
		var occurTime = 0;
		
	    var userName=document.getElementById("tousu_seek").value;
	    var path = "/api/v1/communities/"+communityId+"/complaints/webIm/getComplainUser?pageNum=1&pageSize=200&userName="+userName;
		$.ajax({

			url : path,

			type: "GET",
			dataType : "json",

			success : function(data) {
			
			
			   $("#tousu_contactlistUL").find("li").attr("style",
										"display:none");
				var dataList = data.pageData;
				var a=dataList.length-1;
				for(var i = a; i>=0 ; i--){
					detail = dataList[i].detail;
					complaintId = dataList[i].complaintId;
					emobIdFrom = dataList[i].emobIdFrom;
					if(i == 0){
						tousuThisUser = emobIdFrom;
					}
					emobIdTo = dataList[i].emobIdTo;
					nickname = dataList[i].nickname;
					if(myUserMap[emobIdFrom] == undefined){
						myUserMap[emobIdFrom] = nickname;
					}
					orderId = dataList[i].orderId;
					room = dataList[i].room;
					title = dataList[i].title;
					avatar = dataList[i].avatar;
					occurTime = dataList[i].occurTime;
					var comId=document.getElementById(complaintId);
					if(comId==null){
						tousuListAddLi(orderId, emobIdFrom, emobIdTo, room, nickname, avatar , detail , occurTime,complaintId);
						if(i == 0){
							getComplaintShop(orderId, emobIdFrom, emobIdTo, detail, occurTime,complaintId);
						}
				  }else{
				     	$("." + emobIdFrom ).attr("style",
										"display:block");
				  
				  }
				}
			
			},

			error : function(er) {


			}

		});
	
	}

 $(function(){
        $('#tousu_contactlistUL li').click(function(){
            $(this).addClass('select').siblings('li').removeClass();
        });
    });
    
    
    
    
(function ($) {
  jQuery.expr[':'].Contains = function(a,i,m){
      return (a.textContent || a.innerText || "").toUpperCase().indexOf(m[3].toUpperCase())>=0;
  };
  function filterList(header, list) { 
  var form = $("<form>").attr({"class":"filterform_tousu","action":"#"}),
  input = $("<input>").attr({"class":"filterinput_tousu","type":"text"});
  $(form).append(input).appendTo(header);
  $(input)
      .change( function () {
        var filter = $(this).val();
        if(filter) {
		  $matches = $(list).find('a:Contains(' + filter + ')').parent();
		  $('li', list).not($matches).slideUp();
		  $matches.slideDown();
        } else {
          $(list).find("li").slideDown();
        }
        return false;
      })
    .keyup( function () {
        $(this).change();
    });
  }
  $(function () {
    filterList($("#form_id_tousu"), $("#tousu_contactlistUL"));
  });
}(jQuery));




function setNoneChatPagesTousu(){
    var chat_pages_nouser= document.getElementsByName("chat_pages_nouser_tousu");
    for ( var i = 0; i < chat_pages_nouser.length; i++) {
		chat_pages_nouser[i].style.display="none";
	  }
 }
 function openChatPagesTousu(from,title){
	 
	 tousuThisUser=from;
	 iconTousu(from);
	// setTime();
	if(tile=""){
		
		
	}else{
		 document.getElementById("mychat_user_title").innerHTML=title;
		
	}

   var nouser=document.getElementById(from+"-tousu-nouser");
    if(nouser==null){
        addChatPagesTousu(from);
    }
    setNoneChatPagesTousu();
  
    $("#"+from+"-tousu-nouser").attr("style", "display:block");
 }
 function addChatPagesTousu(from){
	// tousuThisUser=from;
     $("#tousu_chat01").append("<div id=\""+from+"-tousu-nouser\" class=\"chat01_content_new\" name=\"chat_pages_nouser_tousu\" style=\"margin-left: 0;height:291px;display:none\"></div>");
   //  setTime(from);
 }
 function iconTousu(users){
     homeUserThisObj=users;
     var objSpan = document.getElementById("span_"+users);
		if (objSpan != null) {
			objSpan.parentNode.removeChild(objSpan);
			homeUserobjSpan--;
		}
	    if(homeUserobjSpan==0||homeUserobjSpan<0){
	       var objSpan2 = document.getElementById("span_yezhuzixun");
			if (objSpan2 != null) {
				objSpan2.parentNode.removeChild(objSpan2);
				homeUserobjSpan=0;
			}
	    }
	    
		  $("#mychat_user_title_emob_id").val(users);
		  var seLi= document.getElementById(users);
		  if(seLi != null){
		 	 	$(seLi).siblings("li").attr("class","");
		    	seLi.className="select";
				homeUserThisObj = users; 
		  }
 }

function remark(){
	document.getElementById("remark").className = "";
	document.getElementById("remark").readOnly = false;
	
}
function show_img(imgSrc){
    var back = document.createElement("div");
    back.id = "black_background";
    back.className = "public-black";
    var styleStr = "opacity:0.6";
    document.body.appendChild(back);
    back.style.cssText = styleStr;
    
    var content = document.createElement("div");
    content.id = "black_content";
    content.className = "show-img-box";
    content.innerHTML = "<div><img src=\"imgSrc\"><a style=\"position:absolute;right:20px;top:10px;\" href=\"javascript:close_img();\">关闭</a></div>";
    
    document.body.appendChild(content);
}


function updateAddress(){
	 var back = document.createElement("div");
    back.id = "black_background";
    back.className = "public-black";
    var styleStr = "opacity:0.6";
    document.body.appendChild(back);
    back.style.cssText = styleStr;
    
    var content = document.createElement("div");
    content.id = "black_content";
    content.className = "upadat-address-box";
    content.innerHTML = "<div class=\"upadat-address-frame\"><div class=\"update-address-title\">修改用真实住址</div><div class=\"update-address-select\"><select><option>1号楼</option><option>2号楼</option></select><select><option>1单元</option><option>2单元</option></select><select><option>101</option><option>102</option></select></div><div class=\"update-address-save\"><a href=\"javascript:;\">保存</a></div><a style=\"position:absolute;right:20px;top:10px;\" href=\"javascript:close_img();\">关闭</a></div>";
    document.body.appendChild(content);
	
}




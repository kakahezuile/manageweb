;(function() {


	function submit(type) {
		var username = $("[name=username]");
		if (!username.val()) {
			alert("请填写用户名!");
			username.focus();
			return;
		}
	
		
		var password = $("[name=password]");
		if (!password.val()) {
			alert("请填写密码!");
			password.focus();
			return;
		}
		
		
		var repassword = $("[name=repassword]");
		if (!repassword.val()) {
			alert("请填写确认密码!");
			repassword.focus();
			return;
		}
		
		if(password.val()!=repassword.val()){
			alert("密码与确认密码不一致!");
			return;
		}
		
		var communityId = $("#communities option:selected")[0].value;
		if(communityId==0 || communityId=="0"){
			alert("请选择小区!");
			return;
		}
		if('user'==type){
			$("#waterForm").attr("action", window.basePath+"api/v1/communities/0/tryOuts").get(0).submit();
		}else if('shop'==type){
			$("#waterForm").attr("action", window.basePath+"api/v1/communities/0/tryOuts/shop").get(0).submit();
		}else if('edit'==type){
			$("#waterForm").attr("action", window.basePath+"api/v1/communities/0/tryOuts/edit").get(0).submit();
		}
	}
	
	/**
	 * 获取所有小区
	 */
	function getCommunities(){
		$.ajax({
			url: window.basePath+"api/v1/communities/summary/getListCommunityQ" ,
			type: "GET",
			dataType: "json",
			success: function(data) {
				var infoes = data.info;
				var html = "<option value='0' selected='selected' disabled='disabled'>请选择小区</option>";
				for (var i= 0; i < infoes.length; i++) {
					var community = infoes[i];
					var comunityId = community.communityId;
					var communityName = community.communityName;
					html+=" <option value='"+comunityId+"'>"+communityName+"</option> ";
				}
				$("#communities").html("");
				$("#communities").html(html);
					
				},
			error: function(e) {
				alert(e);
			}
		});
	}
	
	/**
	 * 获取对应小区的楼号
	 */
	 function selectFloor(select) {
		var communityId =  $(select).val();
		var path = window.basePath+"api/v1/communities/"+communityId+"/homeOwner/selectFloor";
				$.ajax({
					url : path,
					type : "GET",
					dataType : 'json',
					success : function(data) {
						data=data.info;
						var repair_overman = $("#notice_special_select_floor");
						repair_overman.empty();
						 repair_overman.append("<option value=\"\">请选择楼</option>");
						for ( var i = 0; i < data.length; i++) {
		                 
		                  if(data[i]!=null&&data[i]!=""){
		                    var floor="<option value=\""+data[i]+"\">"+data[i]+"</option>";
		                    repair_overman.append(floor);
		                  }
						}
					},
					error : function(er) {
					}
				});
		 }
	 
	
	/**
	 * 获取对应小区的单元号
	 */
	 function selectUnit(select) {
		 	var floor =  $(select).val();
		    var communityId=$("#communities").val();
			var path = window.basePath+"api/v1/communities/"+communityId+"/homeOwner/selectUnit?userFloor="+floor;
				$.ajax({
					url : path,
					type : "GET",
					dataType : 'json',
					success : function(data) {
						data=data.info;
						var repair_overman = $("#notice_special_select_unit");
						repair_overman.empty();
						 repair_overman.append("<option value=\"\">请选择单元</option>");
						for ( var i = 0; i < data.length; i++) {
		                  var floor="<option value=\""+data[i]+"\">"+data[i]+"</option>";
		                  repair_overman.append(floor);
						}
					},
					error : function(er) {
					}
				});
		 }
	
	/**
	 * 获取对应小区房间号
	 */
	 function selectRoom(select) {
		 	var unit =  $(select).val();
		 	var communityId=$("#communities").val();
		    var floor=$("#notice_special_select_floor").val();
			var path = window.basePath+"api/v1/communities/"+communityId+"/homeOwner/selectRoom?userFloor="+floor+"&userUnit="+unit;
				$.ajax({
					url : path,
					type : "GET",
					dataType : 'json',
					success : function(data) {
					data=data.info;
						var repair_overman = $("#notice_special_select_room");
						repair_overman.empty();
						 repair_overman.append("<option value=\"\">请选择房间</option>");
						for ( var i = 0; i < data.length; i++) {
		                  var floor="<option value=\""+data[i]+"\">"+data[i]+"</option>";
		                  repair_overman.append(floor);
						}
					},
					error : function(er) {
					}
				});
		 }
	 
	 /**
	 * 获取水军列表
	 */
	 function getNavys(offset,communityId){
		 var pageNum = 1;
		 if(offset=='first'){
			 pageNum=1;
		 }else if(offset=='last'){
			 pageNum=window.waterAdd.pageCount;
		 }else if(offset=='pre'){
			 pageNum = window.waterAdd.pageNum-1 ;
		 }else if(offset=='next'){
			 pageNum = window.waterAdd.pageNum+1 ;
		 }
		 $.ajax({
				url: window.basePath+"api/v1/communities/"+communityId+"/tryOuts?pageNum="+pageNum ,
				type: "GET",
				dataType: "json",
				success: function(data) {
					var page = data.info;
					if (typeof(page) == "undefined") { 
						alert("没有数据了!");
						$("#statistics-summary").html("");
						$("#repairRecordPageNum").text(window.waterAdd.pageCount);
						return ;
					}
					
					var infoes = data.info.pageData;
					var summaryHtml = "";
					
					for (var i= 0; i < infoes.length; i++) {
						var users = infoes[i];
						var emobId = users.emobId,
							nickname = users.nickname,
							communityName=users.communityName;
						
						summaryHtml += "<tr class='" + (i % 2 ? "even" : "odd") + "'>";
						summaryHtml += "<td><img alt='头像' src='"+users.avatar+"' width='30' height='30' > </td>";
						summaryHtml += "<td><span>" + (users.nickname || "") + "</span></td>";
						summaryHtml += "<td><span>" + (users.username || "") + "</span></td>";
						summaryHtml += "<td><span>" + (users.communityName || "")  + "</span></td>";
						summaryHtml += "<td><span>" + (users.userFloor+"-"+users.userUnit+"-"+users.room || "") + "</span></td>";
						summaryHtml += "<td><span>" + (users.gender || "") + "</span></td>";
						summaryHtml += "<td><span>" + (users.characterValues || "") + "</span></td>";
						summaryHtml += "<td><span>(<a href='"+window.basePath+"jsp/navy/navy_lifeCircles.jsp?emobId="+emobId+"&nickname="+nickname+"&communityId="+users.communityId+"&communityName="+communityName+"' >"+(users.lifecircleCount || "")+"</a>)</span></td>";
						summaryHtml += "<td><span>" + (users.signature || "") + "</span></td>";
						summaryHtml += "<td><span>" + (users.remarks || "") + "</span></td>";
						summaryHtml += "<td><span><a href='"+window.basePath+"jsp/navy/navy_edit.jsp?emobId="+ emobId+"'  >编辑</a></span></td>";
						summaryHtml += "</tr>";
					}
					
					$("#statistics-summary").html("");
					$("#statistics-summary").html(summaryHtml);
					$("#repairRecordPageNum").text(page.num);
					$("#repairRecordPageSize").text(page.pageCount);
					$("#repairRecordSum").text(page.rowCount);
					window.waterAdd.pageNum = page.num;
					window.waterAdd.pageCount = page.pageCount;
					},
				error: function(e) {
					alert(e);
				}
			});
	 }

	 /**
	  * 查询水军
	  */
	 function searchNavy(){
		 var query = $("#querynavy").val();
		 if(query==""){
			 alert("请输入查询条件!");
			 return ;
		 }
		 
		 $.ajax({
			 url: window.basePath+"api/v1/communities/0/tryOuts/search?query="+query ,
			 type: "GET",
			 dataType: "json",
			 success: function(data) {
				 var infoes = data.info;
				 var summaryHtml = "";
				 for (var i= 0; i < infoes.length; i++) {
					 var users = infoes[i];
					 var emobId = users.emobId,
					 nickname = users.nickname,
					 communityName=users.communityName;
					 
					 summaryHtml += "<tr class='" + (i % 2 ? "even" : "odd") + "'>";
					 summaryHtml += "<td><img alt='头像' src='"+users.avatar+"' width='30' height='30' > </td>";
					 summaryHtml += "<td><span>" + (users.nickname || "") + "</span></td>";
					 summaryHtml += "<td><span>" + (users.username || "") + "</span></td>";
					 summaryHtml += "<td><span>" + (users.communityName || "")  + "</span></td>";
					 summaryHtml += "<td><span>" + (users.userFloor+"-"+users.userUnit+"-"+users.room || "") + "</span></td>";
					 summaryHtml += "<td><span>" + (users.gender || "") + "</span></td>";
					 summaryHtml += "<td><span>" + (users.characterValues || "") + "</span></td>";
					 summaryHtml += "<td><span>(<a href='"+window.basePath+"jsp/navy/navy_lifeCircles.jsp?emobId="+emobId+"&nickname="+nickname+"&communityName="+communityName+"' >"+(users.lifecircleCount || "")+"</a>)</span></td>";
					 summaryHtml += "<td><span>" + (users.signature || "") + "</span></td>";
					 summaryHtml += "<td><span>" + (users.remarks || "") + "</span></td>";
					 summaryHtml += "<td><span><a href='"+window.basePath+"jsp/navy/navy_edit.jsp?emobId="+ emobId+"'  >编辑</a></span></td>";
					 summaryHtml += "</tr>";
				 }
				 
				 $("#statistics-summary").html("");
				 $("#statistics-summary").html(summaryHtml);
				 $("#page").hide();
			 },
			 error: function(e) {
				 alert(e);
			 }
		 });
	 }
	 
	 	/**
		 * 点击获取水军所发的生活圈
		 */
		 function getlifeCircles(_communityName,_emobId,offset){
			 var pageNum = 1;
			 if(offset=='first'){
				 pageNum=1;
			 }else if(offset=='last'){
				 pageNum=window.waterAdd.pageCount;
			 }else if(offset=='pre'){
				 pageNum = window.waterAdd.pageNum-1 ;
			 }else if(offset=='next'){
				 pageNum = window.waterAdd.pageNum+1 ;
			 }
			 $.ajax({
				url: window.basePath+"api/v1/communities/0/tryOuts/user/"+_emobId+"?pageNum="+pageNum ,
				type: "GET",
				dataType: "json",
				success: function(data) {
					var page = data.info;
					var infoes = data.info.pageData;
					
					var summaryHtml = "";
					for (var i= 0; i < infoes.length; i++) {
						var lifeCircle = infoes[i];
						summaryHtml += "<tr class='" + (i % 2 ? "even" : "odd") + "'>";
						summaryHtml += "<td><span>" + (lifeCircle.lifeContent || "0") + "</span></td>";
						summaryHtml += "<td><span>" + (lifeCircle.praiseSum || "0")  + "</span></td>";
						summaryHtml += "<td><span>" + (lifeCircle.contentSum || "0") + "</span></td>";
						summaryHtml += "<td><span>" + (lifeCircle.time || "") + "</span></td>";
						summaryHtml += "<td><span><a href='#' onclick=\"window.waterAdd.favorite('"+lifeCircle.lifeCircleId+"','"+lifeCircle.communityId+"')\">收藏</a></span>     <span><a href=\""+window.basePath+"jsp/navy/navy-life-favorite.jsp?community_id="+lifeCircle.communityId+"&communityName="+_communityName+"&activitiesId="+lifeCircle.lifeCircleId+"\">转发</a></span></td>";
						summaryHtml += "</tr>";
					}
					$("#statistics-summary").html("");
					$("#statistics-summary").html(summaryHtml);
					$("#repairRecordPageNum").text(page.num);
					$("#repairRecordPageSize").text(page.pageCount);
					$("#repairRecordSum").text(page.rowCount);
					window.waterAdd.pageNum = page.num;
					window.waterAdd.pageCount = page.pageCount;
				},
				error: function(e) {
					alert(e);
				}
			});
		 }
		 
		 /**
		 * 获取指定用户信息
		 */
		function getNavy(emobId){
			$.ajax({
				url: window.basePath+"api/v1/communities/0/tryOuts/users/"+emobId ,
				type: "GET",
				dataType: "json",
				success: function(data) {
					var info = data.info;
					$("[name=emobId]").val(info.emobId);
					$("[name=userId]").val(info.userId);
					$("[name=username]").val(info.username);
					$("[name=nickname]").val(info.nickname);
					$("#img-poster").attr("src",info.avatar);
					$("[name=signature]").text(info.signature);
					$("[name=posterValue]").val(info.avatar);
					},
				error: function(e) {
					alert(e);
				}
			});
		}
			
		 
		 
		// 收藏
		function favorite(lifeCircleId,communityId){
			
			var path = window.basePath+"api/v1/communities/"+communityId+"/lifeCircle/favorite";
			   
			var myJson={
					  "lifeCircleId":lifeCircleId,
					 // "emobId":lifeContent,
					  "communityId":communityId
				  };
				$.ajax({
					url : path,
					data : JSON.stringify(myJson),
					type : "POST",
					dataType : 'json',
					success : function(data) {
						alert("成功");
					},
					error : function(er) {
					}
				});
			
			}
	
	function fixDate(num) {
		if (num < 10) {
			return "0" + num;
		}
		return num;
	}
	
	function getDate(date) {
		return date.getFullYear() + "-" + fixDate(date.getMonth() + 1) + "-" + fixDate(date.getDate()) + " " + fixDate(date.getHours()) + ":" + fixDate(date.getMinutes()) + ":" + fixDate(date.getSeconds());
	}
	
	// 隔离全局名称，防止变量名污染
	window.waterAdd = window.waterAdd || {};
	window.waterAdd.submit = submit;
	window.waterAdd.getCommunities = getCommunities;
	window.waterAdd.selectFloor = selectFloor;
	window.waterAdd.selectUnit = selectUnit;
	window.waterAdd.selectRoom = selectRoom;
	window.waterAdd.getNavys = getNavys;
	window.waterAdd.getlifeCircles = getlifeCircles;
	window.waterAdd.favorite = favorite;
	window.waterAdd.getNavy = getNavy;
	window.waterAdd.searchNavy = searchNavy;
})();
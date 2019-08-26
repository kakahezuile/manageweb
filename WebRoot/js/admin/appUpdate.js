;(function() {
	
	/**
	 * 获取所有小区
	 */
	function getCommunities(offset){
		 var pageNum = 1;
		 if(offset=='first'){
			 pageNum=1;
		 }else if(offset=='last'){
			 pageNum=window.appUpdate.pageCount;
		 }else if(offset=='pre'){
			 pageNum = window.appUpdate.pageNum-1 ;
		 }else if(offset=='next'){
			 pageNum = window.appUpdate.pageNum+1 ;
		 }
		 var url = "";
		if($("#search-input").val()){
			url = window.basePath+"api/v1/communities/appversion/communityAppVersion?query="+$("#search-input").val() ;
			$("#page").hide();
		}else{
			url = window.basePath+"api/v1/communities/appversion/communityAppVersion?pageNum="+pageNum ;
		}
		 alert(url);
		$.ajax({
			url: url ,
			type: "GET",
			dataType: "json",
			success: function(data) {
				fillCommunityAppVersion(data);
				},
			error: function(e) {
				alert(e);
			}
		});
	}
	
	
	function getAppVersions(){
		$.ajax({
			url: window.basePath+"api/v1/communities/appversion/all" ,
			type: "GET",
			dataType: "json",
			success: function(data) {
				var infoes = data.info;
				html= "";
				for (var i= 0; i < infoes.length; i++) {
					var app = infoes[i];
					var id = app.id;
					var version = app.version;
					if(i==0){
						html+=" <option value='"+id+"' selected='selected' >"+version+"</option>" ;
					}else{
						html+=" <option value='"+id+"'>"+version+"</option>" ;
					}
				$("select").html("");
				$("select").html(html);
				}
			},
			error: function(e) {
				alert(e);
			}
		});
	}
	
	/**
	 * 点击 升级版本 --小区
	 * @param comunityId
	 * @param ele
	 */
	function communityUpdate(comunityId,ele){
		var tds = $(ele).parent().parent().children();
		var oldVersion = tds.eq(1).children("span").html();
		var newVersion = tds.eq(2).children("select").find("option:selected").text();
		if(oldVersion>=newVersion){
			alert("升级版本只能高于当前版本!");
			return ;
		}
		
		var version = $(ele).parent().prev().children().val();
		var json = {"AppId":version ,"updateUser":comunityId,"type":"community","status":"on"};
		
		$.ajax({
			url: window.basePath+"api/v1/communities/appversion/config/update" ,
			type: "POST",
			dataType: "json",
			data:JSON.stringify(json),
			success: function(data) {
				if("yes"==data.status){
					alert("升级成功!");
					tds.eq(1).children("span").text(newVersion);
				}else{
					alert("升级失败!");
				}
			},
			error: function(e) {
				alert(e);
			}
		});
		
	}
	
	/**
	 * 点击 升级版本 --全部
	 * @param comunityId
	 * @param ele
	 */
	function updateAll(){
		
		var v = $("#update-all option:selected ").text();
		if(!confirm("是否确认所有小区升级到"+v+"版本")){
			return ;
		}
		var version = $("#update-all").val();
		alert(v);
		var json = {"AppId":version ,"updateUser":"all","type":"all","status":"on"};
		
		$.ajax({
			url: window.basePath+"api/v1/communities/appversion/config/update" ,
			type: "POST",
			dataType: "json",
			data:JSON.stringify(json),
			success: function(data) {
				if("yes"==data.status){
					alert("升级成功!");
					tds.eq(1).children("span").text(newVersion);
				}else{
					alert("升级失败!");
				}
			},
			error: function(e) {
				alert(e);
			}
		});
		
	}
	
	
	function getCommunityUsers(offset){
		var communityId = window.appUpdate.communityId;
		 var pageNum = 1;
		 if(offset=='first'){
			 pageNum=1;
		 }else if(offset=='last'){
			 pageNum=window.appUpdate.pageCount;
		 }else if(offset=='pre'){
			 pageNum = window.appUpdate.pageNum-1 ;
		 }else if(offset=='next'){
			 pageNum = window.appUpdate.pageNum+1 ;
		 }
		 var url = "";
		if($("#search-input").val()){
			url = window.basePath+"api/v1/communities/appversion/users?query="+$("#search-input").val()+"&communityId="+communityId+"&pageNum="+pageNum ;
		}else{
			url = window.basePath+"api/v1/communities/appversion/users?communityId="+communityId+"&pageNum="+pageNum ;
		}
		alert(url);
		$.ajax({
			url: url ,
			type: "GET",
			dataType: "json",
			success: function(data) {
				fillUsersAppVersion(data);
				},
			error: function(e) {
				alert(e);
			}
		});
	}
	
	/**
	 * 点击升级 -- 用户
	 */
	function userUpdate(emobId,ele){
		var tds = $(ele).parent().parent().children();
		var oldVersion = tds.eq(2).children("span").html();
		var newVersion = tds.eq(3).children("select").find("option:selected").text();
		if(oldVersion>=newVersion){
			alert("升级版本只能高于当前版本!");
			return ;
		}
		
		var version = $(ele).parent().prev().children().val();
		var json = {"AppId":version ,"updateUser":emobId,"type":"person","status":"on"};
		$.ajax({
			url: window.basePath+"api/v1/communities/appversion/config/update" ,
			type: "POST",
			dataType: "json",
			data:JSON.stringify(json),
			success: function(data) {
				if("yes"==data.status){
					alert("升级成功!");
					tds.eq(1).children("span").text(newVersion);
				}else{
					alert("升级失败!");
				}
			},
			error: function(e) {
				alert(e);
			}
		});
	}
	
	/**
	 * 填充小区app版本数据
	 */
	function fillCommunityAppVersion(data){
		var page = data.info;
		var infoes = page.pageData;
		html= "<tr> <th>小区名称</th> <th>当前版本</th> <th>最新版本</th> <th style='text-align:right;'> <input class=\"search-input\" type=\"text\" placeholder=\"输入小区名称\" id=\"search-input\">" +
				"<a class=\"search-btn\" href=\"javascript:void(0);\" onclick=\"window.appUpdate.getCommunities('first');\">&nbsp;&nbsp;</a> </th> </tr>";
		for (var i= 0; i < infoes.length; i++) {
			var community = infoes[i];
			var communityId = community.communityId;
			var communityName = community.communityName;
			var appVersion = community.appVersion ;
			var style = i%2==0?"odd":"even";
			html+=" <tr class='"+style+"'> <td><a class='community' href='/jsp/admin/admin_personal_version.jsp?communityId="+communityId+"&appVersion="+appVersion+"'>"+communityName+"</a></td> " +
				  " <td><span>"+appVersion+"</span></td>"+
			      " <td><select><option>"+appVersion+"</option></select></td>"+
				  " <td style='text-align:right;'> <a href=\"javascript:void(0);\"  onclick=\"window.appUpdate.communityUpdate("+communityId+",this);\">升级版本</a> </td> </tr> ";
			}
			$("#maintenanceMain").html("");
			$("#maintenanceMain").html(html);
			$("#repairRecordPageNum").text(page.num);
			$("#repairRecordPageSize").text(page.pageCount);
			$("#repairRecordSum").text(page.rowCount);
			window.appUpdate.pageNum = page.num;
			window.appUpdate.pageCount = page.pageCount;
			getAppVersions();
	}
	
	
	/**
	 * 填充用户数据
	 */
	function fillUsersAppVersion(data){
		var appVersion = window.appUpdate.appVersion ;
		var page = data.info;
		var infoes = page.pageData;
		html= "<tr> <th>用户昵称</th><th>手机号</th> <th>当前版本</th> <th>最新版本</th> <th style='text-align:right;'> <input class=\"search-input\" type=\"text\" placeholder=\"输入用户昵称/手机号\" id=\"search-input\">" +
				"<a class=\"search-btn\" href=\"javascript:void(0);\" onclick=\"window.appUpdate.getCommunityUsers('first');\">&nbsp;&nbsp;</a> </th> </tr>";
		for (var i= 0; i < infoes.length; i++) {
			var user = infoes[i];
			var nickname = user.nickname;
			var emobId = user.emobId;
			var username = user.username;
			var style = i%2==0?"odd":"even";
			html+=" <tr class='"+style+"'> <td><span>"+nickname+"</span></td> " +
				  " <td><span>"+username+"</span></td>"+
				  " <td><span>"+appVersion+"</span></td>"+
			      " <td><select><option>"+appVersion+"</option></select></td>"+
				  " <td style='text-align:right;'> <a href=\"javascript:void(0);\"  onclick=\"window.appUpdate.userUpdate('"+emobId+"',this);\">升级版本</a> </td> </tr> ";
			}
			$("#maintenanceMain").html("");
			$("#maintenanceMain").html(html);
			$("#repairRecordPageNum").text(page.num);
			$("#repairRecordPageSize").text(page.pageCount);
			$("#repairRecordSum").text(page.rowCount);
			window.appUpdate.pageNum = page.num;
			window.appUpdate.pageCount = page.pageCount;
			getAppVersions();
	}
	
	
	// 隔离全局名称，防止变量名污染
	window.appUpdate = window.appUpdate || {};
	window.appUpdate.getCommunities = getCommunities;
	window.appUpdate.communityUpdate = communityUpdate;
	window.appUpdate.updateAll=updateAll;
	window.appUpdate.getCommunityUsers=getCommunityUsers;
	window.appUpdate.userUpdate=userUpdate;
})();
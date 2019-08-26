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
			url = window.basePath+"api/v1/communities/bonusCoin/conf?query="+$("#search-input").val() ;
			$("#page").hide();
		}else{
			url = window.basePath+"api/v1/communities/bonusCoin/conf?pageNum="+pageNum ;
			$("#page").show();
		}
		$.ajax({
			url: url ,
			type: "GET",
			dataType: "json",
			success: function(data) {
				fillCommunityBonusCoinConf(data);
				},
			error: function(e) {
				alert(e);
			}
		});
	}
	
	/**
	 * 填充数据
	 */
	function fillCommunityBonusCoinConf(data){
		if("yes"==data.status){
			var page = data.info;
			var bonusCoinConfs = page.pageData;
			html="<tr> <th>小区名称</th> <th>帮帮币功能状态</th> <th>帮帮币抵扣金额比例(钱:币)</th> <th>购物分享送帮帮币</th> <th>快店消费送帮帮币(钱:币)</th> <th style=\"text-align:right;\"> 操作 </th> </tr>";
			for (var i = 0; i < bonusCoinConfs.length; i++) {
				var bonusCoinConf = bonusCoinConfs[i];
				var style = i%2==0?"odd":"even";
				var status = bonusCoinConf.featureValue=="yes"?"开启":"关闭" ;
				html+="<tr class='"+style+"'> <td><span>"+bonusCoinConf.communityName+"</span></td> <td><span>"+status+"</span></td> <td><span>"+bonusCoinConf.exchange+"</span>:<span>"+bonusCoinConf.exchangeCoin+"</span></td> <td><span>"+bonusCoinConf.shareCoin+"</span></td> <td><span>"+bonusCoinConf.expense+"</span>:<span>"+bonusCoinConf.expenseCoin+"</span></td> <td style=\"text-align:right;\"> <a href=\"javascript:void(0);\" onclick=\"window.bonusCoin.edit("+bonusCoinConf.communityId+",this);\">操作</a> </td> </tr>" ;
			}
			$("#maintenanceMain").html("");
			$("#maintenanceMain").html(html);
			$("#repairRecordPageNum").text(page.num);
			$("#repairRecordPageSize").text(page.pageCount);
			$("#repairRecordSum").text(page.rowCount);
			window.bonusCoin.pageNum = page.num;
			window.bonusCoin.pageCount = page.pageCount;
		}
	}
	
	/**
	 * 弹出编辑窗口
	 */
	function edit(communityId,ele){
		var tds = $(ele).parent().parent().children();
		var communityName = tds.eq(0).children("span").text();
		var status = tds.eq(1).children("span").text();
		var exchange = tds.eq(2).children("span").eq(0).text();
		var exchangeCoin = tds.eq(2).children("span").eq(1).text();
		var shareCoin = tds.eq(3).children("span").text();
		var expense = tds.eq(4).children("span").eq(0).text();
		var expenseCoin = tds.eq(4).children("span").eq(1).text();
		
		if("关闭"==status){
			status = "no";
		}
		
		$("[name='communityId']").val(communityId);
		$("[name='communityName']").val(communityName);
		$("[name='status']").val(status);
		$("[name='exchange']").val(exchange);
		$("[name='exchangeCoin']").val(exchangeCoin);
		$("[name='shareCoin']").val(shareCoin);
		$("[name='expense']").val(expense);
		$("[name='expenseCoin']").val(expenseCoin);
		
		$("#maintenceTable").show();
		
	}
	
	/**
	 * 隐藏编辑界面
	 */
	function bonusCoinEditCancle(){
		$("#maintenceTable").hide();
	}
	
	/**
	 * form表单提交
	 */
	function bonusCoinSubmit(){
		var url = window.basePath+"api/v1/communities/bonusCoin/conf";
		$("#bonus_coin_form").attr("action",url);
		$("#bonus_coin_form").submit();
	}
	
	
	// 隔离全局名称，防止变量名污染
	window.bonusCoin = window.bonusCoin || {};
	window.bonusCoin.getCommunities = getCommunities;
	window.bonusCoin.edit = edit;
	window.bonusCoin.bonusCoinEditCancle = bonusCoinEditCancle;
	window.bonusCoin.bonusCoinSubmit = bonusCoinSubmit;
	
})();
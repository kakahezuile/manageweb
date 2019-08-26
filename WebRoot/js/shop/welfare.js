;(function() {

	function list() {
		$.ajax({
			url: "/api/v1/communities/" + communityId + "/welfares",
			type: "GET",
			dataType: "json",
			success : function(data) {
				var infoes = data.info,
					welfareId,
					status,
					title,
					orderPaid,
					orderCount,
					html = "",
					info,
					i;
				if (infoes.length == 0) {
					html += "<tr class='even'><td colspan='5' style='text-align:center;color:red;'>没有查询到数据!</td></tr>";
				} else {
					var statusMap = {
						"ongoing" : "正在进行中",
						"offline" : "下架",
						"success" : "成功",
						"failure" : "失败"
					};
					for (i = 0; i < infoes.length; i++) {
						info = infoes[i];
						welfareId = info.welfareId;
						status = info.status;
						title = info.title;
						orderPaid = info.orderPaid;
						orderCount = orderPaid + info.orderRefunded;
						
						html += "<tr class='" + (i / 2 == 0 ? "even" : "odd") + "'>";
						html += "<td class='welfare-title'><img src='" + info.poster + "'></td>";
						html += "<td><p class='welfare-name'>" + title + "</p></td>";
						html += "<td>" + statusMap[status] + "</td>";
						html += "<td><a href='javascript:void(0)' onclick='welfares.loadOrders(" + welfareId + ")'>" + orderCount + "(" + orderPaid + ")</a></td>";
						html += "<td class='welfare-opt'>";
						if ("ongoing" == status) {
							hasOnline = true;
							
							html += "<a href='javascript:void(0)' onclick='welfares.offline(" + welfareId + ", \"" + title + "\", this);'>下架</a>";
							if (orderCount > 0) {
								html += "<a href='javascript:void(0)' onclick='welfares.showNotify(" + welfareId + ", \"" + title + "\");'>通知</a>";
							}
							html += "<a href='javascript:void(0)' onclick='welfares.succeed(" + welfareId + ", \"" + title + "\", this, \"ongoing\");'>成功</a>";
							html += "<a href='javascript:void(0)' onclick='welfares.fail(" + welfareId + ", \"" + title + "\", this, \"ongoing\");'>失败</a>";
							html += "<a href='javascript:void(0)' onclick='welfares.edit(" + welfareId + ");'>修改</a>";
						} else if ("offline" == status) {
							html += "<a href='javascript:void(0)' onclick='welfares.online(" + welfareId + ", \"" + title + "\", this);'>上架</a>";
							if (orderCount > 0) {
								html += "<a href='javascript:void(0)' onclick='welfares.showNotify(" + welfareId + ", \"" + title + "\");'>通知</a>";
							} else {
								html += "<a href='javascript:void(0)' onclick='welfares.remove(" + welfareId + ", this);'>删除</a>";
							}
							html += "<a href='javascript:void(0)' onclick='welfares.edit(" + welfareId + ");'>修改</a>";
						} else if ("success" == status) {
							html += "<a href='javascript:void(0)' onclick='welfares.fail(" + welfareId + ", \"" + title + "\", this, \"success\");'>失败</a>";
							if (orderCount > 0) {
								html += "<a href='javascript:void(0)' onclick='welfares.showNotify(" + welfareId + ", \"" + title + "\");'>通知</a>";
							}
							html += "<a href='javascript:void(0)' onclick='welfares.edit(" + welfareId + ");'>修改</a>";
						} else if ("failure" == status) {
							html += "<a href='javascript:void(0)' onclick='welfares.succeed(" + welfareId + ", \"" + title + "\", this, \"failure\");'>成功</a>";
							if (orderCount > 0) {
								html += "<a href='javascript:void(0)' onclick='welfares.showNotify(" + welfareId + ", \"" + title + "\");'>通知</a>";
							}
							html += "<a href='javascript:void(0)' onclick='welfares.edit(" + welfareId + ");'>修改</a>";
						}
						html += "</td>";
						html += "</tr>";
					}
				}
				
				$("#welfare-list").html(html);
			}
		});
	}
	
	function online(welfareId, welfareTitle, el) {
		if (doOnline) {
			return;
		}
		
		if (hasOnline) {
			alert("已经有上架的福利了!");
			return;
		}
		
		if (!confirm("上架后如果有用户购买，那么将无法进行下架操作，您确认要在现在上架吗?")) {
			return;
		}
		
		doOnline = true;
		
		$.ajax({
			url: "/api/v1/communities/" + communityId + "/welfares/online/" + welfareId,
			type: "POST",
			dataType: "json",
			success: function(data) {
				doOnline = false;
				
				if (data.status != "yes") {
					alert("上架失败了，" + data.message);
					return;
				}
				
				hasOnline = true;
				
				var html = "";
				html += "<a href='javascript:void(0)' onclick='welfares.offline(" + welfareId + ", \"" + welfareTitle + "\", this);'>下架</a>";
				html += "<a href='javascript:void(0)' onclick='welfares.succeed(" + welfareId + ", \"" + welfareTitle + "\", this, \"ongoing\");'>成功</a>";
				html += "<a href='javascript:void(0)' onclick='welfares.fail(" + welfareId + ", \"" + welfareTitle + "\", this, \"ongoing\");'>失败</a>";
				html += "<a href='javascript:void(0)' onclick='welfares.edit(" + welfareId + ");'>修改</a>";
				
				var td = $(el).closest("td");
				td.html(html);
				td.prev().prev().html("正在进行中");
				$("#welfare-list").prepend(td.closest("tr"));
			},
			error: function() {
				alert("上架失败了");
				doOnline = false;
			}
		});
	}
	
	function offline(welfareId, welfareTitle, el) {
		if (!confirm("您确认要将下架该福利吗?")) {
			return;
		}
		
		$.ajax({
			url: "/api/v1/communities/" + communityId + "/welfares/offline/" + welfareId,
			type: "POST",
			dataType: "json",
			success: function(data) {
				if (data.status != "yes") {
					alert("下架失败了，可能已经有用户购买福利或已被其他人下架了，请重新打开福利列表!");
					return;
				}
				
				hasOnline = false;
				
				var html = "";
				html += "<a href='javascript:void(0)' onclick='welfares.online(" + welfareId + ", \"" + welfareTitle + "\", this);'>上架</a>";
				html += "<a href='javascript:void(0)' onclick='welfares.remove(" + welfareId + ", this);'>删除</a>";
				html += "<a href='javascript:void(0)' onclick='welfares.edit(" + welfareId + ");'>修改</a>";
				
				var td = $(el).closest("td");
				td.html(html);
				td.prev().prev().html("下架");
			}
		});
	}
	
	function succeed(welfareId, welfareTitle, el, currentStatus) {
		if (!confirm("您确认要手动成功该福利吗?")) {
			return;
		}
		
		$.ajax({
			url: "/api/v1/communities/" + communityId + "/welfares/succeed/" + welfareId,
			type: "POST",
			dataType: "json",
			success: function(data) {
				if (data.status != "yes") {
					alert("操作失败了，请重新打开该福利列表!");
					return;
				}
				
				if (currentStatus == "ongoing") {
					hasOnline = false;
				}
				
				var html = "";
				html += "<a href='javascript:void(0)' onclick='welfares.fail(" + welfareId + ", \"" + welfareTitle + "\", this, \"success\");'>失败</a>";
				html += "<a href='javascript:void(0)' onclick='welfares.showNotify(" + welfareId + ", \"" + welfareTitle + "\");'>通知</a>";
				html += "<a href='javascript:void(0)' onclick='welfares.edit(" + welfareId + ");'>修改</a>";
				
				var td = $(el).closest("td");
				td.html(html);
				td.prev().prev().html("成功");
			}
		});
	}
	
	function fail(welfareId, welfareTitle, el, currentStatus) {
		var reason = prompt("请输入失败原因", "");
		if (!reason) {
			return;
		}
		
		$.ajax({
			url: "/api/v1/communities/" + communityId + "/welfares/fail/" + welfareId,
			type: "POST",
			dataType: "json",
			data: {
				reason: reason
			},
			success: function(data) {
				if (data.status != "yes") {
					alert("操作失败了，请重新打开该福利列表!");
					return;
				}
				
				if (currentStatus == "ongoing") {
					hasOnline = false;
				}
				
				var html = "";
				html += "<a href='javascript:void(0)' onclick='welfares.succeed(" + welfareId + ", \"" + welfareTitle + "\", this, \"failure\");'>成功</a>";
				html += "<a href='javascript:void(0)' onclick='welfares.showNotify(" + welfareId + ", \"" + welfareTitle + "\");'>通知</a>";
				html += "<a href='javascript:void(0)' onclick='welfares.edit(" + welfareId + ");'>修改</a>";
				
				var td = $(el).closest("td");
				td.html(html);
				td.prev().prev().html("失败");
			}
		});
	}
	
	function remove(welfareId, el) {
		if (confirm("请确定要删除这条福利吗?")) {
			$.ajax({
				url: "/api/v1/communities/" + communityId + "/welfares/" + welfareId,
				type: "DELETE",
				dataType: "json",
				success: function(data) {
					if (data.status == "yes") {
						$(el).closest("tr").remove();
					} else {
						alert("删除失败!");
					}
				}
			});
		}
	}
	
	function loadOrders(welfareId) {
		location.href = "/jsp/operation/shop/welfare-order.jsp?welfareId=" + welfareId
	}
	
	function notify(type) {
		if (notifying) {
			return;
		}
		
		var message = $("#notify-content").val();
		if (!message) {
			alert("请输入消息!");
			return;
		}
		
		notifying = true;
		
		var status = undefined,
			hasPaid = $("#welfare-status-paid:checked").length > 0,
			hasRefunded = $("#welfare-status-refunded:checked").length > 0;
		if (hasPaid && hasRefunded) {
			status = "";
		} else if (hasPaid) {
			status = "paid";
		} else if (hasRefunded) {
			status = "refunded";
		}
		
		if (status === undefined) {
			alert("请选择用户!");
			return;
		}
		
		$.ajax({
			url: "/api/v1/communities/" + communityId + "/welfareOrders/notifyAll",
			type: "POST",
			dataType: "json",
			data: {
				welfareId: $("#notify-welfareId").val(),
				type: type,
				status: status,
				message: $("#notify-content").val()
			},
			success: function(data) {
				notifying = false;
				
				if (data.status == "yes") {
					hideNotify();
				} else {
					alert(data.message || "发送失败");
				}
			},
			error: function() {
				notifying = false;
				
				alert("发送失败");
			}
		});
	}
	
	function showNotify(welfareId, welfareTitle) {
		$("#notify-title").html(welfareTitle);
		$("#notify-welfareId").val(welfareId);
		$("#notify-content").val("").focus();
		
		$("#welfare-notify-bg").show();
		$("#welfare-notify").show();
	}
	
	function hideNotify() {
		$("#notify-title").html("");
		$("#notify-welfareId").val("");
		$("#welfare-notify-bg").hide();
		$("#welfare-notify").hide();
	}
	
	function edit(welfareId) {
		location.href = "/jsp/operation/shop/add-welfare.jsp?welfareId=" + welfareId
	}
	
	//隔离全局名称，防止变量名污染
	window.welfares = window.welfares || {};
	window.welfares.list = list;
	window.welfares.online = online;
	window.welfares.offline = offline;
	window.welfares.succeed = succeed;
	window.welfares.fail = fail;
	window.welfares.remove = remove;
	window.welfares.loadOrders = loadOrders;
	window.welfares.notify = notify;
	window.welfares.showNotify = showNotify;
	window.welfares.hideNotify = hideNotify;
	window.welfares.edit = edit;
	
	var hasOnline = false,//小区内现在是否有正在加上的福利
		doOnline = false,//正在进行上架操作
		notifying = false;
	
	$(function() {
		var box = $("#welfare-notify"),
			$top = $(top);
		$top.scroll(function() {
			box.css("top", ($top.scrollTop() + 400) + "px");
		});
		box.css("top", ($top.scrollTop() + 400) + "px");
		
		$top.scrollTop(0);
	});
})();
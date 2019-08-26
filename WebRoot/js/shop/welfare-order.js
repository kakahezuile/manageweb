;(function() {

	function list() {
		$.ajax({
			url: "/api/v1/communities/" + communityId + "/welfareOrders?welfareId=" + welfareId,
			type: "GET",
			dataType: "json",
			success : function(data) {
				var infoes = data.info,
					html = "",
					status,
					info,
					i;
				if (infoes.length == 0) {
					html += "<tr class='even'><td colspan='5' style='text-align:center;color:red;'>没有查询到数据!</td></tr>";
				} else {
					var statusMap = {
						"paid" : "已付款",
						"refunded" : "已退款"
					};
					for (i = 0; i < infoes.length; i++) {
						info = infoes[i];
						status = info.status;
						
						html += "<tr class='" + (i / 2 == 0 ? "even" : "odd") + "'>";
						html += "<td class='welfare-title'><img src='" + info.avatar + "'></td>";
						html += "<td>" + info.nickname + "</td>";
						html += "<td>" + info.username + "</td>";
						html += "<td>" + info.code + "</td>";
						html += "<td>" + statusMap[status] + "</td>";
						html += "<td class='welfare-opt'>";
						if (status == "paid") {
							html += "<a href='javascript:void(0)' onclick='welfareOrders.refund(" + info.welfareOrderId + ", this);'>退款</a>";
						}
						html += "<a href='javascript:void(0)' onclick='welfareOrders.showNotify(" + info.welfareOrderId + ", \"" + info.nickname + "\", \"" + info.avatar + "\");'>通知</a>";
						html += "</td>";
						html += "</tr>";
					}
				}
				
				$("#welfare-order-list").html(html);
			}
		});
	}
	
	function refund(welfareOrderId, el) {
		if (confirm("您确定要为该用户退款吗?")) {
			$.ajax({
				url: "/api/v1/communities/" + communityId + "/welfareOrders/cancel/" + welfareOrderId,
				type: "POST",
				dataType: "json",
				success: function(data) {
					if (data.status == "yes") {
						$(el).closest("td").prev().html("已退款");
						$(el).remove();
					} else {
						alert("操作失败!");
					}
				}
			});
		}
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
		$.ajax({
			url: "/api/v1/communities/" + communityId + "/welfareOrders/notify/" + $("#notify-welfareOrderId").val(),
			type: "POST",
			dataType: "json",
			data: {
				type: type,
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

	function showNotify(id, nickname, avatar) {
		$("#notify-title").html(nickname);
		$("#notify-welfareOrderId").val(id);
		$("#notify-avatar").attr("src", avatar);
		$("#notify-content").val("").focus();
		
		$("#welfareOrder-notify-bg").show();
		$("#welfareOrder-notify").show();
	}
	
	function hideNotify() {
		$("#notify-title").html("");
		$("#notify-welfareOrderId").val("");
		$("#welfareOrder-notify").hide();
		$("#welfareOrder-notify-bg").hide();
	}
	
	//隔离全局名称，防止变量名污染
	window.welfareOrders = window.welfareOrders || {};
	window.welfareOrders.list = list;
	window.welfareOrders.refund = refund;
	window.welfareOrders.notify = notify;
	window.welfareOrders.showNotify = showNotify;
	window.welfareOrders.hideNotify = hideNotify;
	
	var notifying = false;
	
	$(function() {
		var box = $("#welfareOrder-notify"),
			$top = $(top);
		$top.scroll(function() {
			box.css("top", ($top.scrollTop() + 400) + "px");
		});
		box.css("top", ($top.scrollTop() + 400) + "px");
		
		$top.scrollTop(0);
	});
})();
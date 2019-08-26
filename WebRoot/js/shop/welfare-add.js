;(function() {

	function addContent(el) {
		var me = $(el),
			li = me.closest("li"),
			imgId = "preview-content-" + (++previewContentImageIndex);
		
		li.before("<li><p><img></p><p><a href='javascript:void(0)' onclick='welfareAdd.delContent(this, \"" + imgId + "\")'>删除</a></p></li>");
		li.after("<li class='welfare-upload'><a><input name='" + el.name + "' type='file' onchange='welfareAdd.addContent(this)'></a></li>");
		li.hide();
		
		$("#preview-content").append("<p><img id='" + imgId + "'></p>");
		
		preview(el, li.prev().find("img"));
		preview(el, "#" + imgId);
	}
	function delContent(el, imgId) {
		var imgEl = $(el).closest("li");
		imgEl.next().remove();
		imgEl.remove();
		$("#" + imgId).remove();
	}
	function showPreview() {
		$("#preview-title").html($("[name=title]").val() || "");
		$("#preview-price").html($("[name=price]").val() || "");
		$("#preview-charactervalues").html($("[name=charactervalues]").val() || "");
		$("#preview-total").html($("[name=total]").val() || "");
		$("#preview-endTime").html($("[name=endTime]").val() || "");
		$("#preview-rule").html($("[name=rule]").val() || "");
		$("#preview-phone").html($("[name=phone]").val() || "");
		$("#preview-provideInstruction").html($("[name=provideInstruction]").val() || "");
		
		$("#preview-shade").show();
		$("#preview-container").show();
	}
	function hidePreview() {
		$("#preview-shade").hide();
		$("#preview-container").hide();
	}
	function submit() {
		var poster = $("[name=poster]"),
			posterValue = $("[name=posterValue]");
		if (!poster.val() && !posterValue.val()) {
			alert("请上传福利海报!");
			poster.focus();
			return;
		}
		var title = $("[name=title]");
		if (!title.val()) {
			alert("请填写福利标题!");
			title.focus();
			return;
		}
		var price = $("[name=price]");
		if (!price.val()) {
			alert("请填写福利价格!");
			price.focus();
			return;
		}
		if (isNaN(price.val())) {
			alert("请填写正确的福利价格!");
			price.focus();
			return;
		}
		var charactervalues = $("[name=charactervalues]");
		if (!charactervalues.val()) {
			alert("请填写人品值要求!");
			charactervalues.focus();
			return;
		}
		if (isNaN(charactervalues.val())) {
			alert("请填写正确的人品值要求!");
			charactervalues.focus();
			return;
		}
		var total = $("[name=total]");
		if (!total.val()) {
			alert("请填写福利总数!");
			total.focus();
			return;
		}
		if (isNaN(total.val())) {
			alert("请填写正确的福利总数!");
			total.focus();
			return;
		}
		var endTime = $("[name=endTime]");
		if (!endTime.val()) {
			alert("请选择福利截止日期!");
			endTime.focus();
			return;
		}
		var rule = $("[name=rule]");
		if (!rule.val()) {
			alert("请填写福利规则!");
			rule.focus();
			return;
		}
		var provideInstruction = $("[name=provideInstruction]");
		if (!provideInstruction.val()) {
			alert("请填写福利发放说明!");
			provideInstruction.focus();
			return;
		}
		
		
		var hasContent = $("input[name=contentValue]").length > 0,
			emptyContent = [];
		$("input[name=content]").each(function() {
			var me = $(this);
			if (!me.val()) {
				emptyContent.push(me);
			} else {
				hasContent = true;
			}
		});
		if (hasContent) {
			for (var i = 0; i < emptyContent.length; i++) {
				emptyContent[i].attr("disabled", "disabled");
			}
		} else {
			alert("请上传福利详情图片!");
			return;
		}
		
		$("#welfareForm").attr("action", "/api/v1/communities/" + communityId + "/welfares").get(0).submit();
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
	
	function init (message) {
		if (welfareId && welfareId != "0") {
			$.ajax({
				url: "/api/v1/communities/" + communityId + "/welfares/" + welfareId,
				type: "GET",
				dataType: "json",
				success: function(data) {
					if (data.status != "yes") {
						alert("获取福利信息失败");
						return;
					}
					
					var welfare = data.info,
						endTime = new Date(welfare.endTime * 1000);
					$("[name=welfareId]").val(welfare.welfareId).removeAttr("disabled");
					$("[name=title]").val(welfare.title);
					$("[name=charactervalues]").val(welfare.charactervalues);
					$("[name=total]").val(welfare.total);
					$("[name=rule]").val(welfare.rule);
					$("[name=phone]").val(welfare.phone);
					$("[name=endTime]").val(getDate(endTime));
					$("[name=price]").val(welfare.price);
					$("[name=provideInstruction]").val(welfare.provideInstruction);
					
					$("[name=posterValue]").val(welfare.poster).removeAttr("disabled");
					$("#img-poster").attr("src", welfare.poster);
					$("#preview-poster").attr("src", welfare.poster);
					
					var contents = (welfare.content || "").split(","),
						content,
						html = "",
						previewContentHtml = "",
						imgId;
					for (var i = 0; i < contents.length; i++) {
						var content = contents[i];
						if (!content) {
							continue;
						}
						
						imgId = "preview-content-" + (++previewContentImageIndex);
						html += "<li><p><img src='" + content + "'></p><p><a href='javascript:void(0)' onclick='welfareAdd.delContent(this, \"" + imgId + "\")'>删除</a></p></li><input type='hidden' name='contentValue' value='" + content + "'>";
						previewContentHtml += "<p><img id='" + imgId + "' src='" + content + "'></p>";
					}
					$("#content-list").prepend(html);
					$("#preview-content").html(previewContentHtml);
				},
				error: function(e) {
					alert(e);
				}
			});
		}
		
		if (message) {
			alert(message);
		}
		
		$("input[name=endTime]").datetimepicker({
			format: "yyyy-mm-dd hh:ii:ss",
			language: "zh-CN",
			weekStart: 1,
			todayBtn: 1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 2,
			minuteStep: 1,
			forceParse: true
		});
	}
	
	//隔离全局名称，防止变量名污染
	window.welfareAdd = window.welfareAdd || {};
	window.welfareAdd.init = init;
	window.welfareAdd.submit = submit;
	window.welfareAdd.showPreview = showPreview;
	window.welfareAdd.hidePreview = hidePreview;
	window.welfareAdd.addContent = addContent;
	window.welfareAdd.delContent = delContent;
	
	var previewContentImageIndex = 0;
	
	$(function() {
		var box = $("#preview-container"),
			$top = $(top);
		$top.scroll(function() {
			box.css("top", $top.scrollTop() + "px");
		});
		box.css("top", $top.scrollTop() + "px");
		
		$top.scrollTop(0);
	});
})();
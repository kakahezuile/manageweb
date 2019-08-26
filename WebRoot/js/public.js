function show_alert(tipword){
    var back = document.createElement("div");
    back.id = "black_background";
    back.className = "public-black";
    var styleStr = "opacity:0.6";
    document.body.appendChild(back);
    back.style.cssText = styleStr;
    
    var content = document.createElement("div");
    content.id = "black_content";
    content.className = "public-tip-box";
    content.innerHTML = "<div class=\"public-tip-title\">信息提示</div><div class=\"public-tip-content outer\"><div class=\"middle\"><div class=\"inner\">" + tipword + "</div></div></div><div class=\"public-tip-footer\"><a href=\"javascript:close_alert();\" class=\"blue-button\">确定</a></div>";
    document.body.appendChild(content);
}

function close_alert(){
    $("#black_background").remove();
    $("#black_content").remove();
}

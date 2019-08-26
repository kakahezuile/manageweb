<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String webTitle = properties.getProperty("webTitle");
	
%>
<%
String community_id=request.getParameter("community_id");
String communityName=request.getParameter("communityName");
String str = new String(communityName.getBytes("ISO-8859-1"),"UTF-8");
%>
<%

Integer lastQuitTime = null;
if (session.isNew()){
	
	Cookie cookies[]=request.getCookies();
	for(int i=0;i<cookies.length;i++) {
		if("quitTime".equals(cookies[i].getName())) {
			lastQuitTime = Integer.parseInt(cookies[i].getValue());
			break;
		}
	}
	if(lastQuitTime==null){
		lastQuitTime = (int)(System.currentTimeMillis()/1000l);
	}
	//登录时记录访问时间
	Integer newTime = (int) (System.currentTimeMillis()/1000l);
	Cookie cookie = new Cookie("quitTime",newTime+""); 
  	cookie.setMaxAge(60*60*24*30); 
  	response.addCookie(cookie);
}else{
	Cookie cookies[]=request.getCookies();
	for(int i=0;i<cookies.length;i++) {
		if("quitTime".equals(cookies[i].getName())) {
			lastQuitTime = Integer.parseInt(cookies[i].getValue());
			break;
		}
	}
	if(lastQuitTime==null){
		lastQuitTime = (int)(System.currentTimeMillis()/1000l);
	}
}
%>
<script type="text/javascript">
function communityId() {
    var community_id=document.getElementById("community_id").value;
    var emobId=document.getElementById("emobId").value;
   
	var path = "<%=path %>/api/v1/communities/summary/getListCommunityQ";
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data=data.info;
			
			var repair_overman = $("#com_ul");
			repair_overman.empty();
		    var liList = "";
		    
		    //根据需求更改置顶小区
		    liList+="<li>";
			liList+="<div style='color:#fff;border-radius:30px;float:right;background:#ff0000;padding:0 5px;' id='communityUpdateSum3'></div><a onclick=\"leftButton('3');\" id=\"community_id_3\" mark=\"shop\" name=\"shop\" href=\"${pageContext.request.contextPath }/jsp/navy/navy-alllife.jsp?community_id=3&communityName=狮子城\">狮子城</a>";
			liList+="</li>";
			
		    liList+="<li>";
			liList+="<div style='color:#fff;border-radius:30px;float:right;background:#ff0000;padding:0 5px;' id='communityUpdateSum2'></div><a onclick=\"leftButton('2');\" id=\"community_id_2\" mark=\"shop\" name=\"shop\" href=\"${pageContext.request.contextPath }/jsp/navy/navy-alllife.jsp?community_id=2&communityName=首邑溪谷\">首邑溪谷</a>";
			liList+="</li>";
		    
		    liList+="<li>";
			liList+="<div style='color:#fff;border-radius:30px;float:right;background:#ff0000;padding:0 5px;' id='communityUpdateSum53'></div><a onclick=\"leftButton('53');\" id=\"community_id_53\" mark=\"shop\" name=\"shop\" href=\"${pageContext.request.contextPath }/jsp/navy/navy-alllife.jsp?community_id=53&communityName=加华印象\">加华印象</a>";
			liList+="</li>";
		    
		    liList+="<li>";
			liList+="<div style='color:#fff;border-radius:30px;float:right;background:#ff0000;padding:0 5px;' id='communityUpdateSum5'></div><a onclick=\"leftButton('5');\" id=\"community_id_5\" mark=\"shop\" name=\"shop\" href=\"${pageContext.request.contextPath }/jsp/navy/navy-alllife.jsp?community_id=5&communityName=汇邦幸福城\">汇邦幸福城</a>";
			liList+="</li>";
			
			liList+="<li>";
			liList+="<div style='color:#fff;border-radius:30px;float:right;background:#ff0000;padding:0 5px;' id='communityUpdateSum6'></div><a onclick=\"leftButton('6');\" id=\"community_id_6\" mark=\"shop\" name=\"shop\" href=\"${pageContext.request.contextPath }/jsp/navy/navy-alllife.jsp?community_id=6&communityName=高成天鹅湖\">高成天鹅湖</a>";
			liList+="</li>";
			
			for ( var i = 0; i < data.length; i++) {
				
				var skipId = data[i].communityId;
				if(skipId==2 || skipId==3 || skipId==5 || skipId==6 || skipId==53){
					continue;
				}
				
				liList+="<li>";
				liList+="<div style='color:#fff;border-radius:30px;float:right;background:#ff0000;padding:0 5px;' id='communityUpdateSum"+data[i].communityId+"'></div><a onclick=\"leftButton('"+data[i].communityId+"');\" id=\"community_id_"+data[i].communityId+"\" mark=\"shop\" name=\"shop\" href=\"${pageContext.request.contextPath }/jsp/navy/navy-alllife.jsp?community_id="+data[i].communityId+"&communityName="+data[i].communityName+"\">"+data[i].communityName+"</a>";
				liList+="</li>";

			}
			
		    //原小区遍历方法
			/* for ( var i = 0; i < data.length; i++) {
				liList+="<li>";
				liList+="<div style='color:#fff;border-radius:30px;float:right;background:#ff0000;padding:0 5px;' id='communityUpdateSum"+data[i].communityId+"'></div><a onclick=\"leftButton('"+data[i].communityId+"');\" id=\"community_id_"+data[i].communityId+"\" mark=\"shop\" name=\"shop\" href=\"${pageContext.request.contextPath }/jsp/navy/navy-alllife.jsp?community_id="+data[i].communityId+"&communityName="+data[i].communityName+"\">"+data[i].communityName+"</a>";
				liList+="</li>";

			} */
			repair_overman.append(liList);
		},
		error : function(er) {
		}
	});

}

checkCommunityUpdate();
	function checkCommunityUpdate(){
		var path = "<%=path %>/api/v1/communities/summary/getCommunityUpdate?lastQuitTime="+<%=lastQuitTime %>;
		$.ajax({
			url : path,
			type : "GET",
			dataType : 'json',
			success : function(data) {
				data = data.info;
				var total = 0;
				var nodeList = document.getElementsByName("shop");
				$.each(nodeList,function(i,a){
					$.each(data,function(j,b){
						var aid = a.id;
						var bid = "community_id_"+b.id;
						if(aid==bid){
							var cid = "communityUpdateSum"+b.id;
							document.getElementById(cid).textContent=b.count;
							total+=b.count;	
						}
					});
				});
				if(total!=0){
					document.getElementById("newMessageWarning").textContent = total;
					document.getElementById("newMessageWarning").style="color:#fff;border-radius:30px;background:#ff0000;padding:0 5px;position: relative; left: -30px; top: -10px;";
				}
			},
			error : function(er) {
			}
		});
	}

function leftButton(){
    var admin_left_name=document.getElementsByName("shop");
    for(var i=0;i < admin_left_name.length;i++){
        admin_left_name[i].className="";
    }
    var communityId= document.getElementById("commm").value;
    document.getElementById("community_id_"+communityId).className="select";
}
communityId();


</script>
<div class="left-body">
<input type="hidden" id="commm" value="${communityId}"/>
	<div class="navy-item">
		<p>
			<a id="community_id_fa_bu" onclick="" mark="" name="shop" href="<%=path %>/jsp/navy/navy-life.jsp?community_id=<%=community_id %>&communityName=<%=str %>">发布新话题</a>
		</p>
	</div>
	<ul id="com_ul">
	</ul>
</div>
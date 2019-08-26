<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String webTitle = properties.getProperty("webTitle");
%>

<script type="text/javascript">
	function setNone() {
	$("#fuwu_yinchang_id").attr("style", "display:none");
	$("#iframeName").attr("style", "display:none");
	$("#tousu_div_id").attr("style", "display:none");
	$("#tousu_lie_div_id").attr("style", "display:none");
	$("#mychat_div_id").attr("style", "display:none");
	}
	function fuwuNone() {
		setNone();
		document.getElementById("iframeName").style.display = "";

	}
	function fuwuKai() {
		setNone();
		window.parent.document.getElementById("fuwu_yinchang_id").style.display = "";
		window.parent.document.getElementById("mychat_div_id").style.display = "";
	}
	function index(url){
	    setNone();
		document.getElementById("iframeName").style.display = "";
	var iFrameElem = document.getElementById("iframeName");
         iFrameElem.src = url;
	}
	function tousu_div_id() {
		setNone();
		document.getElementById("fuwu_yinchang_id").style.display = "";
		document.getElementById("tousu_div_id").style.display = "";
	}

	$(function() {
		$("#right_ul > li a").click(
				function() {

					$(this).addClass("select").parent().siblings("li")
							.find("a").removeClass("select");
					if (this.id == "yezhuzixun") {
						console.info("物业客服");
						fuwuKai();
						
					} else if (this.id == "gonggao") {
						console.info("公告通知");
						fuwuNone();
					} else if (this.id == "tousu") {
						console.info("投诉处理");
						tousu_div_id();
					} else if (this.id == "jiaofei") {
						console.info("缴费通知");
						fuwuNone();
					} else if (this.id == "huodong") {
						console.info("活动管理");
						var span_jiankong=document.getElementById("span_jiangkong");
						if(span_jiankong!=null){
						 $("#span_jiangkong").remove();
						}
						
						fuwuNone();
					} else if (this.id == "community_service") {
						console.info("物业服务");
						fuwuNone();
					} else if (this.id == "yezhuzixun") {
						console.info("物业客服");
						fuwuKai();
					} else if (this.id == "tousu") {
						console.info("投诉处理");
						tousu_div_id();
					} else if (this.id == "gonggao") {
						console.info("公告通知");
						fuwuNone();
					} else if (this.id == "jiaofei") {
						console.info("物业缴费");
						fuwuNone();
					} else if (this.id == "huodong") {
						console.info("活动管理");
						fuwuNone();
					} 


				});

		//$("#tousu_lie").click(function(){
		//setNone();
		//$("#tousu_lie_div_id").attr("style","display:block");

		//});
	});
	
	function logout(){
	var b=document.getElementById("logout").style.display;
	   document.getElementById("logout").style.display="";
	
	}
	function logoutOpen(){
	var open= document.getElementById("no_open").value;
	if(open=="no"){
	return ;
	}
	    document.getElementById("logout").style.display = "none";
	}
	
	function noOpen(){
	  document.getElementById("no_open").value="no";
	}
	
	function yesOpen(){
	  document.getElementById("no_open").value="yes";
	  var oInput = document.getElementById("male");
	oInput.focus();
	}
	
	
	function upPasswd(){
	  document.getElementById("logout").style.display = "none";
	  fuwuNone();
	}
	
	function community(){
	
	    var path = "<%=path%>/api/v1/communities/${communityId}/services/getCommunity";
	
		$.ajax({
			url : path,
			type : "GET",
			dataType : 'json',
			success : function(data) {
			   data=data.info;
			   document.getElementById("community_id_name").innerHTML=data.communityName+"物业";
			},
			error : function(er) {
			}
		});
	}
	community();
</script>
<header class="header">

<input type="hidden" id="no_open"/>


<div class="header-box clearfix">
	<div class="logo">
		<a class="logo" href="javascript:void(0);" onclick="myHeadGoHome();"><img
				src="${pageContext.request.contextPath }/images/public/logo-head.png"
				alt="LOGO" class="logo">
		</a>
	</div>
	<div class="tenement-name" id="community_id_name">物业管理系统</div>
	<div class="operation-nav-box">
		<nav class="operation-nav">
		
		
		
		
		
		
		
		<ul id="right_ul" class="clearfix">
			<c:if test="${!empty list }">
				<c:forEach items="${list}" varStatus="status" var="item">
					<c:if test="${status.index == 0 }">
					<c:choose>
                        <c:when test="${item.navId=='ke_fu'}">
                             <li>
							<a target="iframeName" mark="${item.navId }"
								name="${item.navId }" id="${item.navId }" class="select"
								href="javascript:void(0);">${item.modelName }</a>
						  </li>
						
                       </c:when>
                       <c:when test="${item.navId=='zhou_bian'}">
                              <li>
						    	<a target="iframeName" mark="${item.navId }"
								name="${item.navId }" id="${item.navId }" class="select"
								href="javascript:void(0);">${item.modelName }</a>
						    </li>
						     
                      </c:when>
                       <c:when test="${item.navId=='yezhuzixun'}">
                              <li>
						    	<a target="iframeName" mark="${item.navId }"
								name="${item.navId }" id="${item.navId }" class="select"
								href="javascript:void(0);">${item.modelName }</a>
						    </li>
						     
                      </c:when>
                       <c:when test="${item.navId=='tousu'}">
                              <li>
						    	<a target="iframeName" mark="${item.navId }"
								name="${item.navId }" id="${item.navId }" class="select"
								href="javascript:void(0);">${item.modelName }</a>
						    </li>
						     
                      </c:when>
                       <c:when test="${item.navId=='gonggao'}">
                              <li>
						    	<a target="iframeName" mark="${item.navId }"
								name="${item.navId }" id="${item.navId }" class="select"
								href="<%=path %>/jsp/estate/notice/notice_all.jsp">${item.modelName }</a>
						    </li>
						     
                      </c:when>
                       <c:when test="${item.navId=='jiaofei'}">
                              <li>
						    	<a target="iframeName" mark="${item.navId }"
								name="${item.navId }" id="${item.navId }" class="select"
								href="<%=path %>/jsp/estate/pay/jiaofei.jsp">${item.modelName }</a>
						    </li>
						     
                      </c:when>
                       <c:when test="${item.navId=='huodong'}">
                              <li>
						    	<a target="iframeName" mark="${item.navId }"
								name="${item.navId }" id="${item.navId }" class="select"
								href="<%=path %>/jsp/estate/activity/activities.jsp?status=ongoing">${item.modelName }</a>
						    </li>
						     
                      </c:when>
                       <c:when test="${item.navId=='community_service'}">
                              <li>
						    	<a target="iframeName" mark="${item.navId }"
								name="${item.navId }" id="${item.navId }" class="select"
								href="<%=path %>/jsp/estate/property/repair.jsp">${item.modelName }</a>
						    </li>
						     
                      </c:when>
                   <c:otherwise>
    
                        <li>
							<a target="iframeName" mark="${item.navId }"
								name="${item.navId }" id="${item.navId }" class="select"
								href="<%=path %>/jsp/operation/${item.ruleName}">${item.modelName }</a>
						</li>
						
                 </c:otherwise>
                </c:choose>
					</c:if>

					<c:if test="${status.index != 0 }">
					 <c:choose>
                        <c:when test="${item.navId=='ke_fu'}">
                             <li>
							<a target="iframeName" mark="${item.navId }"
								name="${item.navId }" id="${item.navId }" 
								href="javascript:void(0);">${item.modelName }</a>
						  </li>
						
                       </c:when>
                       <c:when test="${item.navId=='zhou_bian'}">
                              <li>
						    	<a target="iframeName" mark="${item.navId }"
								name="${item.navId }" id="${item.navId }" 
								href="javascript:void(0);">${item.modelName }</a>
						    </li>
						     
                      </c:when>
                       <c:when test="${item.navId=='yezhuzixun'}">
                              <li>
						    	<a target="iframeName" mark="${item.navId }"
								name="${item.navId }" id="${item.navId }"
								href="javascript:void(0);">${item.modelName }</a>
						    </li>
						     
                      </c:when>
                       <c:when test="${item.navId=='tousu'}">
                              <li>
						    	<a target="iframeName" mark="${item.navId }"
								name="${item.navId }" id="${item.navId }"
								href="javascript:void(0);">${item.modelName }</a>
						    </li>
						     
                      </c:when>
                       <c:when test="${item.navId=='gonggao'}">
                              <li>
						    	<a target="iframeName" mark="${item.navId }"
								name="${item.navId }" id="${item.navId }"
								href="<%=path %>/jsp/estate/notice/notice_all.jsp">${item.modelName }</a>
						    </li>
						     
                      </c:when>
                       <c:when test="${item.navId=='jiaofei'}">
                              <li>
						    	<a target="iframeName" mark="${item.navId }"
								name="${item.navId }" id="${item.navId }"
								href="<%=path %>/jsp/estate/pay/jiaofei.jsp">${item.modelName }</a>
						    </li>
						     
                      </c:when>
                       <c:when test="${item.navId=='huodong'}">
                              <li>
						    	<a target="iframeName" mark="${item.navId }"
								name="${item.navId }" id="${item.navId }" 
								href="<%=path %>/jsp/estate/activity/activities.jsp?status=ongoing">${item.modelName }</a>
						    </li>
						     
                      </c:when>
                       <c:when test="${item.navId=='community_service'}">
                              <li>
						    	<a target="iframeName" mark="${item.navId }"
								name="${item.navId }" id="${item.navId }"
								href="<%=path %>/jsp/estate/property/repair.jsp">${item.modelName }</a>
						    </li>
						     
                      </c:when>
                   <c:otherwise>
    
                        <li>
							<a target="iframeName" mark="${item.navId }"
								name="${item.navId }" id="${item.navId }"
								href="<%=path %>/jsp/operation/${item.ruleName}">${item.modelName }</a>
						</li>
						
                 </c:otherwise>
                </c:choose>
					
					
						
					</c:if>
				</c:forEach>
			</c:if>
			
		</ul><!--
		
		
		
		
		
		<ul id="right_ul" class="clearfix">
			        <li>
							<a target="iframeName" mark="yezhuzixun"
								name="yezhuzixun" id="yezhuzixun" class="select"
								href="javascript:;">物业客服</a>
						  </li>
			    
			        <li>
							<a target="iframeName" mark="gonggao"
								name="gonggao" id="gonggao" 
								href="<%=path %>/jsp/estate/notice/notice_all.jsp">公告通知</a>
						  </li>
			
			           <li>
							<a target="iframeName" mark="tousu"
								name="tousu" id="tousu" 
								href="javascript:;">投诉处理</a>
						  </li>
						   <li>
							<a target="iframeName" mark="jiaofei"
								name="jiaofei" id="jiaofei" 
								href="<%=path %>/jsp/estate/pay/jiaofei.jsp">缴费通知</a>
						  </li>
			           <li>
							<a target="iframeName" mark="huodong"
								name="huodong" id="huodong" 
								href="<%=path %>/jsp/estate/activity/activities.jsp?status=ongoing">活动管理</a>
						  </li>
			          
						      <li>
							<a target="iframeName" mark="community_service"
								name="community_service" id="community_service" 
								href="<%=path %>/jsp/estate/property/repair.jsp">物业服务</a>
						  </li>
			
		</ul>

		--></nav>
	</div>
	<div class="operation-nav-user-info" onblue="logoutOpen();">
		<a class="operation-nav-user-box" href="javascript:;">	
		<label for="male" >
	
			<div class="operation-nav-user-name" onclick="logout();">
				<p>
					<span class="operation-nav-user-real-name" >${newUserName}（登录）</span>
				</p>
			</div> </a>
			<input type="button" id="male"  onblur="logoutOpen();">
		</label>
		<div style="display: none;" class="operation-nav-user-item" id="logout">
			<p>
				<a href="<%=path %>/jsp/operation/password.jsp?userName=${newUserName}" onMouseOver="noOpen();" onMouseOut="yesOpen();" onclick="upPasswd();" target="iframeName">修改密码</a>
			</p>
			<p>
				<a href="/" onMouseOver="noOpen();" onMouseOut="yesOpen();" >退出登录</a>
			</p>
		</div>


	</div>

</div>

</div>
</header>
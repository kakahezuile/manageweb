<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String webTitle = properties.getProperty("webTitle");
%>

<script type="text/javascript">

function montiorin(num){
    if(num==1){
      document.getElementById("montiorin_1").className="select";
      document.getElementById("montiorin_2").className="";
      document.getElementById("montiorin_3").className="";
      document.getElementById("montiorin_4").className="";
      document.getElementById("montiorin_5").className="";
      document.getElementById("montiorin_6").className="";
      document.getElementById("montiorin_7").className="";
    }
    if(num==2){
      document.getElementById("montiorin_1").className="";
      document.getElementById("montiorin_2").className="select";
      document.getElementById("montiorin_3").className="";
      document.getElementById("montiorin_4").className="";
      document.getElementById("montiorin_5").className="";
      document.getElementById("montiorin_6").className="";
      document.getElementById("montiorin_7").className="";
    }
    if(num==3){
      document.getElementById("montiorin_1").className="";
      document.getElementById("montiorin_2").className="";
      document.getElementById("montiorin_3").className="select";
      document.getElementById("montiorin_4").className="";
      document.getElementById("montiorin_5").className="";
      document.getElementById("montiorin_6").className="";
      document.getElementById("montiorin_7").className="";
    }
     if(num==4){
      document.getElementById("montiorin_1").className="";
      document.getElementById("montiorin_2").className="";
      document.getElementById("montiorin_3").className="";
      document.getElementById("montiorin_4").className="select";
      document.getElementById("montiorin_5").className="";
      document.getElementById("montiorin_6").className="";
      document.getElementById("montiorin_7").className="";
      
    }
     if(num==5){
      document.getElementById("montiorin_1").className="";
      document.getElementById("montiorin_2").className="";
      document.getElementById("montiorin_3").className="";
      document.getElementById("montiorin_4").className="";
      document.getElementById("montiorin_5").className="select";
      document.getElementById("montiorin_6").className="";
      document.getElementById("montiorin_7").className="";
      
    }
     if(num==6){
      document.getElementById("montiorin_1").className="";
      document.getElementById("montiorin_2").className="";
      document.getElementById("montiorin_5").className="";
      document.getElementById("montiorin_3").className="";
      document.getElementById("montiorin_4").className="";
      document.getElementById("montiorin_6").className="select";
      document.getElementById("montiorin_7").className="";
      
    }
    if(num==7){
      document.getElementById("montiorin_1").className="";
      document.getElementById("montiorin_2").className="";
      document.getElementById("montiorin_5").className="";
      document.getElementById("montiorin_3").className="";
      document.getElementById("montiorin_4").className="";
      document.getElementById("montiorin_6").className="";
      document.getElementById("montiorin_7").className="select";
      
    }

}

</script>
<div class="left-body" >
	<ul>
		<li>
			<a id="montiorin_1" onclick="" mark="user" name="user" href="${pageContext.request.contextPath }/jsp/operation/monitoring/SensitiveWords.jsp">群组敏感词监控</a>
		</li>
		<li>
			<a id="montiorin_2" onclick="" mark="shop" name="shop" href="${pageContext.request.contextPath }/jsp/operation/monitoring/MonitorComplaints.jsp">投诉</a>
		</li>
		<li>
			<a id="montiorin_3" onclick="" mark="take_out"  name="take_out"  href="${pageContext.request.contextPath }/jsp/operation/monitoring/AdvisoryHistory.jsp">物业客服</a>
		</li>
		
		<li>
			<a id="montiorin_4" onclick="" mark="activity"  name="activity"  href="${pageContext.request.contextPath }/jsp/operation/monitoring/MonitorActivity.jsp">活动监控</a>
		</li>
		<li>
			<a id="montiorin_5" onclick="" mark="lifecircle"  name="lifecircle"  href="${pageContext.request.contextPath }/jsp/operation/monitoring/lifecircle.jsp?status=ongoing">生活圈</a>
		</li>
		<li>
			<a id="montiorin_6" onclick="" mark="lifecircle_montiorin" name="lifecircle_montiorin" href="${pageContext.request.contextPath }/jsp/operation/monitoring/LifecircleSensitiveWords.jsp">生活圈敏感词监控</a>
		</li>
		<li>
			<a id="montiorin_7" onclick="" mark="lifecircle_montiorin" name="lifecircle_montiorin" href="${pageContext.request.contextPath }/jsp/operation/monitoring/shopmonitoring.jsp">快店</a>
		</li>
	</ul>
</div>
<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String webTitle = properties.getProperty("webTitle"); 
%>


<!doctype html>
<!--[if lt IE 7]> <html class="ie6 oldie"> <![endif]-->
<!--[if IE 7]>    <html class="ie7 oldie"> <![endif]-->
<!--[if IE 8]>    <html class="ie8 oldie"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="">
<!--<![endif]-->

<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
<meta name="keywords" content="小间物业,小间物业管理系统" />
<meta name="Description" content="小间物业,小间物业管理系统" />
<title>创建帮帮券_间物业管理系统</title>

	<script type="text/javascript">
		function bangBangGetServicesList(){
			
     		var path = "<%= path%>/api/v1/communities/serviceCategory";
			
			$("#bangbang_check").html("");
			
			$.ajax({

				url: path,

				type: "GET",

				dataType:'json',

				success:function(data){
					console.info(data);
					var data = data.info;
					var len = data.length;
					for(var i = 0 ; i < len ; i++){
						serviceName = data[i].serviceName;
						serviceId = data[i].serviceId;
						$("#bangbang_check").append("&nbsp;<input type=\"checkbox\" value=\""+serviceId+"\" name=\"bangbang_checkbox\" /> &nbsp;"+serviceName+"&nbsp;");
					}
				},

				error:function(er){
			
			
				}

			});
		}
		
		function bangbangSubmit(){
			var str=document.getElementsByName("bangbang_checkbox");
			var objarray=str.length;
			var chestr="";
			var strArray = new Array();
			for (i=0;i<objarray;i++){
				if(str[i].checked == true){
					strArray.push(str[i].value);
					chestr+=str[i].value+",";
				}
			}
			var bangbang_name = $("#bangbang_name").val();
			if(bangbang_name == ""){
				alert("帮帮券名称不能为空");
				return false;
			}
			var bangbang_detail = $("#bangbang_detail").val();
			if(bangbang_detail == ""){
				alert("备注不能为空");
				return false;
			}
			var bangbang_par = $("#bangbang_par").val();
			if(bangbang_par == ""){
				alert("面值不能为空");
			}else{
				var reg = /^[1-9]+[0-9]*]*$/;
				
				if(!reg.test(bangbang_par)){  
        			alert("请输入数字!");  
        			return false;
    			} 
			}
			
			document.getElementById("bangbang_check_value").value = chestr;
			document.getElementById("bangbang_username").value = myUserName;
			//document.getElementById("bangbang_form").submit();
			getFormInput();
			
		}
function getFormInput(){
var TestTakerUI_form = $("#bangbang_form");
var d = getElements("bangbang_form");
var url = "<%=path %>/api/v1/communities/${communityId}/bonuses?"+$('#bangbang_form').serialize();

$.ajax({
    url: url,
    secureuri: false,
  //  fileElementId: 'bangbang_file',
  //  data: $('#bangbang_form').serialize(),
    type: 'POST',
    dataType : 'json',
    success: function (result) {
        alert("成功");
       document.getElementById("bangbang_form").reset();
    }
});
}

function getElements(formId) {    
  var form = document.getElementById(formId);    
   
  var tagElements = form.getElementsByTagName('input'); 
  var h="";
  for (var i = 0; i < tagElements.length; i++){  
   
   if(i==0){
     h+=tagElements[i].name+"="+tagElements[i].value;
  }else{
     h+="&"+tagElements[i].name+"="+tagElements[i].value;
    }
  }  
 h+="";
  return h;    
}  


	function checkedType(type){
	
		if(type=="1"){
			document.getElementById("bangbang_check_value_id").style.display="none";
		}else{
			document.getElementById("bangbang_check_value_id").style.display="";
		}
	}
	</script>
  </head>
  
<body>
	<section>
		<div class="content clearfix">
			<jsp:include flush="true" page="./public/bangbang_left.jsp"/>
			<div class="express-right-body">
				<div class="repair-title-line">
					<p class="bangbang-ticket-title">创建帮帮券</p>
				</div>
				<div class="create-bangbang">
					<form id="bangbang_form"  enctype="multipart/form-data" action="" method="post">
				    	<div>
				    		<span>名称</span>
				    		<input type="text" name="bangbang_name" id="bangbang_name" value="" />
				    	</div>
				    	<div>
				    		<span>备注</span>
				    		<input type="text" name="bangbang_detail" id="bangbang_detail" value="" />
				    	</div>
				    	<div>
							<span>选择帮帮券类型</span>
							<label>通用</label>
							<input type="radio" name="bonus_type" value="-1" checked onclick="checkedType('-1')"/>
							<label>专用</label>
							<input type="radio" name="bonus_type" value="2" onclick="checkedType('2')"/>
						</div>
				    	
						<div style="display: none;" id="bangbang_check_value_id">
							<span>选择项目</span>
							<label>快店</label>
							<input type="checkbox" value="3" name="bangbang_check_value"/>
						</div>
				    	<div>
				    		<span>面值</span>
				    		<input type="text" value="" name="bangbang_par" id="bangbang_par" />
				    	</div>
				    	<div>
				    		<span>券面颜色</span>
				    		<input class="bangbang_ticket_color" type="text" placeholder="R值" name="bonusR"/>
				    		<input class="bangbang_ticket_color" type="text" placeholder="G值" name="bonusG"/>
				    		<input class="bangbang_ticket_color" type="text" placeholder="B值" name="bonusB"/>
				    		
				    	</div>
				    	<div class="create_bangbang_btn">
				    		<a href="javascript:bangbangSubmit();" name="bangbang_submit" id="bangbang_submit">立即创建</a>
				    		<!--
				    			<input type="button" onclick="bangbangSubmit();" name="bangbang_submit" id="bangbang_submit" value=" 创 建 " />
				    		-->
				    		<input type="hidden" value="" id="bangbang_check_value" name="bangbang_check_value" />
		    				<input type="hidden" value="${username }" id="bangbang_username" name="bangbang_username" />
		    				<input type="hidden" value="${password }" id="bangbang_password" name="bangbang_password" />
		    				<input type="hidden" value="${adminId }" id="bangbang_adminId" name="bangbang_adminId" />
				    	</div>
				    </form>
				</div>
			</div>
		</div>
	</section>
  </body>
  <script type="text/javascript">
  
   bangBangGetServicesList();
  </script>
</html>

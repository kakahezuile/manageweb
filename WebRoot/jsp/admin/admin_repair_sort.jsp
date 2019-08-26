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
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1"> 

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
<meta name="keywords" content="小间物业,小间物业管理系统" />
<meta name="Description" content="小间物业,小间物业管理系统" />
<title>添加维修类型_小间物业管理系统</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->

<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>

<script src="${pageContext.request.contextPath }/js/jquery-1.6.js"></script>
<script src="${pageContext.request.contextPath }/js/admin/admin_repair_sort.js"></script>
<script type="text/javascript">

var communityId = window.parent.document.getElementById("community_id_index").value;

$(document).ready(function(){
	managerGetItemCategoryList(5);
});
var maintenanceList;

var maintenanceCatId;

function managerGetItemCategoryList(sort){

	var path = "<%=path%>/api/v1/communities/"+communityId+"/users/admin/itemCategories?q="+sort;
	maintenanceMain = $("#maintenanceMain");
	$.ajax({

		url: path,

		type: "GET",

		dataType:"json",

		success:function(data){
			console.info(data);
			var dataList = data.info;
			maintenanceList = dataList;
			maintenanceMain.html("");
			maintenanceMain.append("<tr><th>维修类型</th><th>维修描述</th><th>操作</th></tr>");
			for(var i = 0 ; i < dataList.length ; i++){
				catName = dataList[i].catName;
				catId = dataList[i].catId;
				catDesc = dataList[i].catDesc;
				maintenanceMain.append("");
				if(i%2==0){
					maintenanceMain.append("<tr class=\"odd\"><td width=\"20%\" colspan=\"1\">"+catName+"</td><td width=\"60%\" colspan=\"1\">"+catDesc+"</td><td width=\"10%\" colspan=\"1\"><a href=\"javascript:void(0);\" onclick=\"managerUpdate("+i+");\" >编辑</a></td><td width=\"20%\" colspan=\"1\"><a href=\"javascript:void(0);\" onclick=\"managerDelete("+i+");\" >删除</a></td></tr>");
				}else{
					maintenanceMain.append("<tr class=\"even\"><td width=\"20%\" colspan=\"1\">"+catName+"</td><td width=\"60%\" colspan=\"1\">"+catDesc+"</td><td width=\"10%\" colspan=\"1\"><a href=\"javascript:void(0);\" onclick=\"managerUpdate("+i+");\" >编辑</a></td><td width=\"20%\" colspan=\"1\"><a href=\"javascript:void(0);\" onclick=\"managerDelete("+i+");\" >删除</a></td></tr>");
				}
				
			}
		
		},

		error:function(er){
		
			alert(er);
		
		}

	});
}

function managerAddItemCategory(type){

	var path = "<%=path%>/api/v1/communities/"+communityId+"/users/admin/itemCategories";
	
	catName = $("#maintenanceType").val();
	catDesc = $("#maintenanceDescription").val();
	
	if(catName == "" || catDesc == ""){
		alert("需要完善内容!");
		return;
	}
	
	var myJson = {
		"catName" : catName ,
		"keywords" : catName ,
		"catDesc" : catDesc ,
		"shopType" : type
	};
	
		$.ajax({

		url: path,

		type: "POST",

		dataType:"json",
		
		data:JSON.stringify(myJson),

		success:function(data){
			console.info(data);
			if(data.status == "yes"){
				managerGetItemCategoryList(type);
			}
		},

		error:function(er){
		
			alert(er);
		
		}

	});
}

function managerUpdate(index){
	myObejct = maintenanceList[index];	
	document.getElementById("maintenanceTypeUpdate").value = myObejct.catName;
	document.getElementById("maintenanceDescriptionUpdate").value = myObejct.catDesc;
	maintenanceCatId = myObejct.catId;
	$("#maintenceTable").attr("style","display:block");
	$(".activity-blank-bg").attr("style","display:block");
	
}

function managerDelete(index){
	myObejct = maintenanceList[index];	
	catId = myObejct.catId;
	var path = "<%=path%>/api/v1/communities/"+communityId+"/users/admin/itemCategories/"+catId;
	$.ajax({

		url: path,

		type: "DELETE",

		dataType:"json",

		success:function(data){
			console.info(data);
			if(data.status == "yes"){
				managerGetItemCategoryList(5);
			}else{
				alert(data.message);
			}
		},

		error:function(er){
		
			alert(er);
		
		}

	});
}

function managerUpdateSubmit(type){
	catName = document.getElementById("maintenanceTypeUpdate").value;
	catDesc = document.getElementById("maintenanceDescriptionUpdate").value;
	if(catName == "" || catDesc == ""){
		alert("修改内容不能为空");
		return;
	}
	
	var path = "<%=path%>/api/v1/communities/"+communityId+"/users/admin/itemCategories/"+maintenanceCatId;
	
	var myJson = {
		"catName":catName ,
		"catDesc":catDesc
	};
	
	$.ajax({

		url: path,

		type: "PUT",

		dataType:"json",
		
		data:JSON.stringify(myJson),

		success:function(data){
			console.info(data);
			if(data.status == "yes"){
				$("#maintenceTable").attr("style","display:none");
				$(".activity-blank-bg").attr("style","display:none");
				managerGetItemCategoryList(type);
			}
		},

		error:function(er){
		
			alert(er);
		
		}

	});
	
}

</script>
</head>
<body>
	<jsp:include flush="true" page="../public/admin_head.jsp"/>
	<section>
		<div class="admin_content clearfix center-personal-info-content">
			<jsp:include flush="true" page="../public/admin_sort_left.jsp"></jsp:include>
			<div class="admin-right-body">
				<div class="main clearfix">
					<div class="user-titile">添加维修类型</div>
					<div class="adduser-main">
						<div class="adduse-item">
							<span>维修类型</span>
							<input type="text" name="maintenanceType" id="maintenanceType" placeholder="如：强弱电维修"/>
						</div>
						<div class="adduse-item">
							<span>类型描述</span>
							<input type="text" name="maintenanceDescription" id="maintenanceDescription" placeholder="如：接插座｜换电表"/>
						</div>
					</div>
					<div class="adduse-add-btn">
						<a class="admin-green-button" href="javascript:void(0);" onclick="managerAddItemCategory(5);">添加</a>
					</div>
				</div>
				<div class="sort-list">
					<table id="maintenanceMain" class="express-list-table">
						<col class="repair-type" />
						<col class="repair-description" />
						<col class="repair-operation" />
						<tr>
							<th>维修类型</th>
							<th>维修描述</th>
							<th>操作</th>
						</tr>
						<tr class="odd">
							<td>123</td>
							<td>123</td>
							<td>
								<a href="javascript:;">编辑</a>
								<a href="javascript:;">删除</a>
							</td>
						</tr>
						<tr class="even">
							<td>123</td>
							<td>123</td>
							<td>
								<a href="javascript:;">编辑</a>
								<a href="javascript:;">删除</a>
							</td>
						</tr>
					</table>
					
				</div>
			</div>
		</div>
	</section>
	<div class="activity-blank-bg" style="display:none;">&nbsp;</div>
	<div class="sort-edit" id="maintenceTable" style="display: none;">
		<div class="adduse-item">
			<span>维修类型</span>
			<input class="textLength" type="text" value="" name="maintenanceTypeUpdate" id="maintenanceTypeUpdate" />
		</div>
		<div class="adduse-item">
			<span>类型描述</span>
			<input class="textLength" type="text" value="" name="maintenanceDescriptionUpdate" id="maintenanceDescriptionUpdate" />
		</div>
		<div class="sort-save">
			<a href="javascript:void(0);" onclick="managerUpdateSubmit(5);">保存</a>
		</div>
	
	</div>
		
</body>
<jsp:include flush="true" page="../public/footer.jsp"></jsp:include>
</html>
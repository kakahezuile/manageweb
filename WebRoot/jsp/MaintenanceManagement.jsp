<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>维修管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript"
			src="${pageContext.request.contextPath }/easymob-webim1.0/jquery-1.11.1.js"></script>
<script type="text/javascript">

	var maintenanceList;
	
	var maintenanceCatId;

	function managerGetItemCategoryList(sort){
	
		var path = "<%=path%>/api/v1/communities/1/users/admin/itemCategories?q="+sort;
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
					maintenanceMain.append("<tr><td width=\"20%\" colspan=\"1\">"+catName+"</td><td width=\"60%\" colspan=\"1\">"+catDesc+"</td><td width=\"10%\" colspan=\"1\"><a href=\"javascript:void(0);\" onclick=\"managerUpdate("+i+");\" >编辑</a></td><td width=\"20%\" colspan=\"1\"><a href=\"javascript:void(0);\" onclick=\"managerDelete("+i+");\" >删除</a></td></tr>");
				}
			
			},

			error:function(er){
			
				alert(er);
			
			}

		});
	}
	
	function managerAddItemCategory(type){
	
		var path = "<%=path%>/api/v1/communities/1/users/admin/itemCategories";
		
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
		
	}
	
	function managerDelete(index){
		myObejct = maintenanceList[index];	
		catId = myObejct.catId;
		var path = "<%=path%>/api/v1/communities/1/users/admin/itemCategories/"+catId;
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
		
		var path = "<%=path%>/api/v1/communities/1/users/admin/itemCategories/"+maintenanceCatId;
		
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
					managerGetItemCategoryList(type);
				}
			},

			error:function(er){
			
				alert(er);
			
			}

		});
		
	}
</script>

<style type="text/css">
	.textLength{
		width: 400px;
		height: 35px;
	}
</style>
  </head>
  
  <body>
  	<table width="60%" align="center">
  		<tr>
  			<td colspan="1">维修类型</td>
  			<td colspan="2"><input class="textLength" type="text" value="" name="maintenanceType" id="maintenanceType" /></td>
  		</tr>
  		
  		<tr>
  			<td colspan="1">类型描述</td>
  			<td colspan="2"><input class="textLength" type="text" value="" name="maintenanceDescription" id="maintenanceDescription" /></td>
  		</tr>
  		<tr>
  			<td colspan="1" align="center"><input type="button" value=" 查  看 " onclick="	managerGetItemCategoryList(5);" /></td>
  			<td colspan="1" align="center"><input type="button" value=" 添  加 " onclick="managerAddItemCategory(5);" /></td>
  		
  		</tr>
  	
  	</table> 
  	
  	<table width="100%" align="center" id="maintenanceMain">
  		<tr>
  			<td width="20%" colspan="1">维修类型</td>
  			<td width="80%" colspan="3">维修描述</td>
  		</tr>
  	</table>
  	
  	<table width="60%" align="center" style="display: none;" id="maintenceTable">
  		<tr>
  			<td colspan="1">维修类型</td>
  			<td colspan="2"><input class="textLength" type="text" value="" name="maintenanceTypeUpdate" id="maintenanceTypeUpdate" /></td>
  		</tr>
  		
  		<tr>
  			<td colspan="1">类型描述</td>
  			<td colspan="2"><input class="textLength" type="text" value="" name="maintenanceDescriptionUpdate" id="maintenanceDescriptionUpdate" /></td>
  		</tr>
  		
  		<tr>
  				<td colspan="1" align="center"><input type="button" value=" 保 存 " onclick="managerUpdateSubmit(5);" /></td>
  		</tr>
  	</table>
  </body>
</html>

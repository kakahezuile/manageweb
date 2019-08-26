<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>保洁管理</title>
    
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

	var cleaningList;
	
	var cleaningCatId;
	
	function cleaningGetItemCategoryList(sort){
	
		var path = "<%=path%>/api/v1/communities/1/users/admin/itemCategories?q="+sort;
		cleaningMain = $("#cleaningMain");
		$.ajax({

			url: path,

			type: "GET",

			dataType:"json",

			success:function(data){
				console.info(data);
				var dataList = data.info;
				cleaningList = dataList;
				cleaningMain.html("");
				cleaningMain.append("<tr><td width=\"20%\" colspan=\"1\">保洁类型</td><td width=\"80%\" colspan=\"3\">保洁描述</td></tr>");
				for(var i = 0 ; i < dataList.length ; i++){
					catName = dataList[i].catName;
					catId = dataList[i].catId;
					catDesc = dataList[i].catDesc;
					cleaningMain.append("<tr><td width=\"20%\" colspan=\"1\">"+catName+"</td><td width=\"60%\" colspan=\"1\">"+catDesc+"</td><td width=\"20%\" colspan=\"1\"><a href=\"javascript:void(0);\"  onclick=\"cleaningUpdate("+i+");\">编辑</a></td><td width=\"20%\" colspan=\"1\"><a href=\"javascript:void(0);\" onclick=\"cleaningDelete("+i+");\" >删除</a></td></tr>");
				}
			
			},

			error:function(er){
			
				alert(er);
			
			}

		});
	}
	
	function cleaningAddItemCategory(type){
	
		var path = "<%=path%>/api/v1/communities/1/users/admin/itemCategories";
		
		catName = $("#cleaningType").val();
		catDesc = $("#cleaningDescription").val();
		
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
					cleaningGetItemCategoryList(type);
				}
			},

			error:function(er){
			
				alert(er);
			
			}

		});
	}
	
		
	function cleaningUpdate(index){
		myObejct = cleaningList[index];	
		document.getElementById("cleaningTypeUpdate").value = myObejct.catName;
		document.getElementById("cleaningDescriptionUpdate").value = myObejct.catDesc;
		cleaningCatId = myObejct.catId;
		$("#cleaningTable").attr("style","display:block");
		
	}
	
	function cleaningUpdateSubmit(type){
		catName = document.getElementById("cleaningTypeUpdate").value;
		catDesc = document.getElementById("cleaningDescriptionUpdate").value;
		if(catName == "" || catDesc == ""){
			alert("修改内容不能为空");
			return;
		}
		
		var path = "<%=path%>/api/v1/communities/1/users/admin/itemCategories/"+cleaningCatId;
		
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
					$("#cleaningTable").attr("style","display:none");
					cleaningGetItemCategoryList(type);
				}
			},

			error:function(er){
			
				alert(er);
			
			}

		});
		
	}
	
	function cleaningDelete(index){
		myObejct = cleaningList[index];	
		catId = myObejct.catId;
		var path = "<%=path%>/api/v1/communities/1/users/admin/itemCategories/"+catId;
		$.ajax({

			url: path,

			type: "DELETE",

			dataType:"json",

			success:function(data){
				console.info(data);
				if(data.status == "yes"){
					cleaningGetItemCategoryList(6);
				}else{
					alert(data.message);
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
  			<td colspan="1">保洁类型</td>
  			<td colspan="2"><input class="textLength" type="text" value="" name="cleaningType" id="cleaningType" /></td>
  		</tr>
  		
  		<tr>
  			<td colspan="1">类型描述</td>
  			<td colspan="2"><input class="textLength" type="text" value="" name="cleaningDescription" id="cleaningDescription" /></td>
  		</tr>
  		<tr>
  			<td colspan="1" align="center"><input type="button" value=" 查  看 " onclick="	cleaningGetItemCategoryList(6);" /></td>
  			<td colspan="2" align="center"><input type="button" value=" 添  加 " onclick="cleaningAddItemCategory(6);" /></td>
  		</tr>
  	
  	</table> 
  	
  	<table width="100%" align="center" id="cleaningMain">
  		<tr>
  			<td width="20%" colspan="1">保洁类型</td>
  			<td width="80%" colspan="2">保洁描述</td>
  		</tr>
  	</table>
  	
  	<table width="60%" align="center" style="display: none;" id="cleaningTable">
  		<tr>
  			<td colspan="1">保洁类型</td>
  			<td colspan="2"><input class="textLength" type="text" value="" name="cleaningTypeUpdate" id="cleaningTypeUpdate" /></td>
  		</tr>
  		
  		<tr>
  			<td colspan="1">类型描述</td>
  			<td colspan="2"><input class="textLength" type="text" value="" name="cleaningDescriptionUpdate" id="cleaningDescriptionUpdate" /></td>
  		</tr>
  		
  		<tr>
  				<td colspan="1" align="center"><input type="button" value=" 保 存 " onclick="cleaningUpdateSubmit(6);" /></td>
  		</tr>
  	</table>
  </body>
</html>

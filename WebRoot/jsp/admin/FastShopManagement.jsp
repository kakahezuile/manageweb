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
<script src="${pageContext.request.contextPath }/js/admin/admin_shop_sort.js"></script>
<script type="text/javascript">

var communityId = window.parent.document.getElementById("community_id_index").value;

$(document).ready(function(){
	fastGetItemCategoryList(2);
});
	var fastShopList;
	
	var fastShopCatId;

	function fastGetItemCategoryList(sort){
	
		var path = "<%=path%>/api/v1/communities/"+communityId+"/users/admin/itemCategories?q="+sort;
		fastShopMain = $("#fastShopMain");
		$.ajax({

			url: path,

			type: "GET",

			dataType:"json",

			success:function(data){
				console.info(data);
				var dataList = data.info;
				fastShopList = dataList;
				fastShopMain.html("");
				fastShopMain.append("<tr><th>快店类型</th><th>快店描述</th><th>操作</th></tr>");
				for(var i = 0 ; i < dataList.length ; i++){
					var className="even";
					catName = dataList[i].catName;
					catId = dataList[i].catId;
					catDesc = dataList[i].catDesc;
					if(i%2==0){
						className="even";
					}else{
						className="odd";
					}
					fastShopMain.append("<tr class=\"odd\"><td width=\"20%\" colspan=\"1\">"+catName+"</td><td width=\"60%\" colspan=\"1\">"+catDesc+"</td><td width=\"10%\" colspan=\"1\"><a href=\"javascript:void(0);\" onclick=\"fastUpdate("+i+");\" >编辑</a></td><td width=\"20%\" colspan=\"1\"><a href=\"javascript:void(0);\" onclick=\"fastDelete("+i+");\" >删除</a></td></tr>");
				}
			
			},

			error:function(er){
			
				alert(er);
			
			}

		});
	}
	
	function fastAddItemCategory(type){
	
		var path = "<%=path%>/api/v1/communities/"+communityId+"/users/admin/itemCategories";
		
		catName = $("#fastShopType").val();
		catDesc = $("#fastShopDescription").val();
		
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
					fastGetItemCategoryList(type);
				}
			},

			error:function(er){
			
				alert(er);
			
			}

		});
	}
	
	function fastUpdate(index){
		myObejct = fastShopList[index];	
		document.getElementById("fastShopTypeUpdate").value = myObejct.catName;
		document.getElementById("fastShopDescriptionUpdate").value = myObejct.catDesc;
		fastShopCatId = myObejct.catId;
		$("#maintenceTable").attr("style","display:block");
		
	}
	
	function fastDelete(index){
		myObejct = fastShopList[index];	
		catId = myObejct.catId;
		var path = "<%=path%>/api/v1/communities/"+communityId+"/users/admin/itemCategories/"+catId;
		$.ajax({

			url: path,

			type: "DELETE",

			dataType:"json",

			success:function(data){
				console.info(data);
				if(data.status == "yes"){
					fastGetItemCategoryList(5);
				}else{
					alert(data.message);
				}
			},

			error:function(er){
			
				alert(er);
			
			}

		});
	}
	
	function fastUpdateSubmit(type){
		catName = document.getElementById("fastShopTypeUpdate").value;
		catDesc = document.getElementById("fastShopDescriptionUpdate").value;
		if(catName == "" || catDesc == ""){
			alert("修改内容不能为空");
			return;
		}
		
		var path = "<%=path%>/api/v1/communities/"+communityId+"/users/admin/itemCategories/"+fastShopCatId;
		
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
					fastGetItemCategoryList(type);
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
  <jsp:include flush="true" page="../public/admin_head.jsp"/>
	<section>
		<div class="admin_content clearfix center-personal-info-content">
			<jsp:include flush="true" page="../public/admin_sort_left.jsp"></jsp:include>
			<div class="admin-right-body">
				<div class="main clearfix">
					<div class="user-titile">添加快店类型</div>
					<div class="adduser-main">
						<div class="adduse-item">
							<span>快店类型</span>
							<input type="text" name="fastShopType" id="fastShopType" placeholder="如：零食干果"/>
						</div>
						<div class="adduse-item">
							<span>类型描述</span>
							<input type="text" name="fastShopDescription" id="fastShopDescription" placeholder="如：巧克力 薯片 坚果 饼干"/>
						</div>
					</div>
					<div class="adduse-add-btn">
						<a class="admin-green-button" href="javascript:void(0);" onclick="fastAddItemCategory(2);">添加</a>
					</div>
				</div>
				<div class="sort-list">
					<table id="fastShopMain" class="express-list-table">
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
			<input class="textLength" type="text" value="" name="fastShopTypeUpdate" id="fastShopTypeUpdate" />
		</div>
		<div class="adduse-item">
			<span>类型描述</span>
			<input class="textLength" type="text" value="" name="fastShopDescriptionUpdate" id="fastShopDescriptionUpdate" />
		</div>
		<div class="sort-save">
			<a href="javascript:void(0);" onclick="fastUpdateSubmit(2);">保存</a>
		</div>
	
	</div>
  
  
  
  
  	<!--<table width="60%" align="center">
  		<tr>
  			<td colspan="1">快店分类</td>
  			<td colspan="2"><input class="textLength" type="text" value="" name="fastShopType" id="fastShopType" /></td>
  		</tr>
  		
  		<tr>
  			<td colspan="1">分类描述</td>
  			<td colspan="2"><input class="textLength" type="text" value="" name="fastShopDescription" id="fastShopDescription" /></td>
  		</tr>
  		<tr>
  			<td colspan="1" align="center"><input type="button" value=" 查  看 " onclick="	fastGetItemCategoryList(2);" /></td>
  			<td colspan="1" align="center"><input type="button" value=" 添  加 " onclick="fastAddItemCategory(2);" /></td>
  		
  		</tr>
  	
  	</table> 
  	
  	<table width="100%" align="center" id="fastShopMain">
  		<tr>
  			<td width="20%" colspan="1">快店分类</td>
  			<td width="80%" colspan="3">快店描述</td>
  		</tr>
  	</table>
  	
  	<table width="60%" align="center" style="display: none;" id="maintenceTable">
  		<tr>
  			<td colspan="1">快店分类</td>
  			<td colspan="2"><input class="textLength" type="text" value="" name="fastShopTypeUpdate" id="fastShopTypeUpdate" /></td>
  		</tr>
  		
  		<tr>
  			<td colspan="1">分类描述</td>
  			<td colspan="2"><input class="textLength" type="text" value="" name="fastShopDescriptionUpdate" id="fastShopDescriptionUpdate" /></td>
  		</tr>
  		
  		<tr>
  				<td colspan="1" align="center"><input type="button" value=" 保 存 " onclick="fastUpdateSubmit(2);" /></td>
  		</tr>
  	</table>
  --></body>
</html>


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


String sort=request.getParameter("sort");
String master=request.getParameter("master");
String shopId=request.getParameter("shopId");
String isUpAndAdd=request.getParameter("isUpAndAdd");
master=new String(master.getBytes("ISO8859_1"), "UTF-8");

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
		<title>添加维修师傅_小间物业管理系统</title>
		<link
			href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>"
			type="image/x-icon" rel="shortcut icon">
		<link
			href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<link
			href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<link
			href="${pageContext.request.contextPath }/css/calendar.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>
		<script src="${pageContext.request.contextPath }/js/jquery-2.1.1.js"></script>
		<script src="${pageContext.request.contextPath }/js/calendar.js"></script>

        <script type="text/javascript"
			src="${pageContext.request.contextPath }/js/md5.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath }/js/base64.js"></script>
		<script type="text/javascript">
		function shield(){
		
		
		
	        var shopid=document.getElementById("shop_id_up").value;
	       
		  	var path = "<%= path%>/api/v1/communities/"+communityId+"/shops/"+shopid;
			var myJson = "{ \"status\": \"leave\"}";
			$.ajax({
	
				url: path,
	
				type: "PUT",
				
				data: myJson ,
	
				dataType:'json',
	
				success:function(data){
					console.info(data);
						document.getElementById("shield_master").style.display="none";
					alert("成功");
				},
	
				error:function(er){
				
				
				}
	
			});
			}
		
		
    	var communityId = window.parent.document
			.getElementById("community_id_index").value;
	   var addMasterMap = {};
		function aass(myJson){
		
		var fCode = myJson.fCode;
		
		var result = "no";
		
		var path = "<%= path%>/api/v1/communities/"+communityId+"/shops/shopSorts?q="+fCode;
	
		$.ajax({

			url: path,

			type: "GET",

			dataType:'json',

			success:function(data){
				console.info(data);
				if(data.status == "yes"){
					result = "yes";
					aadd(data.info.shopId,myJson);
				}else{
					result = "no";
					alert("当前f码不合法");
				}
			},

			error:function(er){
			
			
			}

		});
		
		if(result == "no"){
			return false;
		}
		
	}
	
	function aadd(shopId,myJson){
	   scheduleAll();
		var path = "<%=path %>/api/v1/communities/page/index/"+communityId+"/uploadFile";
		
		if(myJson.username == ""){
			alert("手机号不能为空");
			return false;
		}
		if(myJson.password == ""){
			alert("密码不能为空");
			return false;
		}
		
		var serviceArrayStr = "";
		
		for(var key in addMasterMap){
			if(addMasterMap[key] == "1"){
				serviceArrayStr = serviceArrayStr + key + ",";
			}
		}
		    
		var phone = $("#add_master_main_phone").val();
		var pass = $("#add_master_main_pass").val();
		var userName = $("#add_master_main_username").val();
		var nickName = $("#add_master_main_nickname").val();
		var shopPhone = $("#add_master_main_shop_phone").val();
		var adress = $("#add_master_main_adress").val();
		var idCard = $("#add_master_main_idcard").val();    
		    
		pass = hex_md5(pass);
		    
		window.document.getElementById("iframe1").contentWindow.document.getElementById("community_service_main_phone").value = phone;
		window.document.getElementById("iframe1").contentWindow.document.getElementById("community_service_password").value = pass;
		window.document.getElementById("iframe1").contentWindow.document.getElementById("community_service_main_username").value = userName;
		window.document.getElementById("iframe1").contentWindow.document.getElementById("community_service_main_nickname").value = nickName;
		window.document.getElementById("iframe1").contentWindow.document.getElementById("community_service_main_shopPhone").value = shopPhone;
		window.document.getElementById("iframe1").contentWindow.document.getElementById("community_service_main_adress").value = adress;
		window.document.getElementById("iframe1").contentWindow.document.getElementById("community_service_main_idcard").value = idCard;
		window.document.getElementById("iframe1").contentWindow.document.getElementById("community_service_main_shopId").value = shopId;
		window.document.getElementById("iframe1").contentWindow.document.getElementById("community_service_main_service_array").value = serviceArrayStr;
		window.document.getElementById("iframe1").contentWindow.document.getElementById("community_service_role").value = "shop";
		window.document.getElementById("iframe1").contentWindow.document.getElementById("community_service_form").action = "<%=path %>/api/v1/communities/page/index/"+communityId+"/uploadFile";
		
		window.document.getElementById("iframe1").contentWindow.document.getElementById("community_service_form").submit();
	    document.getElementById("clean_add_fei").reset();
		var imgPre = document.getElementById("add_master_img_id");
        imgPre.src = "${pageContext.request.contextPath }/images/repair/add-master-pic.jpg";
	  
		 onScheduleAll();
		 alert("添加成功");
		  location.href="<%=path %>/jsp/estate/property/repair.jsp";
	}
	
	function addMasterGetItemCategory(type){
		var path = "<%= path%>/api/v1/communities/"+communityId+"/users/123/itemCategories?q="+type;
		addMasterMap = {};
		$.ajax({

			url: path,

			type: "GET",

			dataType:'json',

			success:function(data){
				console.info(data);
				if(data.status == "yes"){
					$("#add_master_ul").html("");
					data = data.info;
					var len = data.length;
					for(var i = 0 ; i < len ; i++){
						addMasterMap[data[i].catId] = "0";
						$("#add_master_ul").append("<li><a id=\"add_master_map_"+data[i].catId+"\" class=\"\" onclick=\"addMasterUlAclick('"+data[i].catId+"',this);\" href=\"javascript:;\">"+data[i].catName+"</a></li>");	
					}
					
				}else{
					
				}
			},

			error:function(er){
			
			
			}

		});
		
	}
	
	function addMasterUlAclick(type , myThis){
		
		var result = $(myThis).hasClass("select");
		
		if(result == true){
			
			addMasterMap[type] = "0";
			$(myThis).attr("class","");
		}else{
			addMasterMap[type] = "1";
			
			$(myThis).attr("class","select");
		}
		
		
	}
	
	function addMasterIsEmpty(type){
	
		var result = false;
		var len = addMasterList.length;
		for(var i = 0 ; i < len ; i++){
			if(addMasterList[i] == type){
				result = true;
			}
		}
		return result;
	}
	
	function addMasterFileClick(){
	//	document.getElementById(imgId).src = "http://bbs.blueidea.com/images/default/jinjin_4409.gif"; 
		//$("#addMasterUploadFile").closest('input[type=file]').trigger('click'); 
		window.document.getElementById("iframe1").contentWindow.document.getElementById("addMasterUploadFile").click(); 
	}
	
	function addMasterSubmit(){
		console.info($(document.getElementById('iframe1').contentWindow.document.getElementById('add_master_iframe_value')));
		//alert($(document.getElementById('iframe1').contentWindow.document.getElementById('add_master_iframe_value')));
		//alert(document.getElementById("iframe1").contentWindow);
		console.info(document.getElementById("iframe1").contentWindow);

		//return false;
		var fCode = $("#add_master_main_fcode").val();
		var phone = $("#add_master_main_phone").val();
		var pass = $("#add_master_main_pass").val();
		var shopName = $("#add_master_main_shopname").val();
		var nickName = $("#add_master_main_nickname").val();
		var shopPhone = $("#add_master_main_shop_phone").val();
		var adress = $("#add_master_main_adress").val();
		var idCard = $("#add_master_main_idcard").val();
		var myJson = {
			"fCode" : fCode ,
			"phone" : phone ,
			"pass" : pass ,
			"shopName" : shopName ,
			"nickName" : nickName ,
			"shopPhone" : shopPhone ,
			"adress" : adress ,
			"idCard" : idCard ,
			"role" : "shop" , 
			"shopId" : 0 , 
			"uploadFile" : ""
			
		};
		if(fCode == "" || phone == "" || pass == "" || shopName == "" || nickName == "" || shopPhone == "" || adress == "" || idCard == ""){
			alert("请完善信息");
			return false;
		}
	    addMasterUserIsEmpty(phone);
		aass(myJson);
	
	}
	
	
	function upFCode(myJson){
	var fCode = myJson.fCode;
		var result = "no";
		
		var path = "<%= path%>/api/v1/communities/"+communityId+"/shops/shopSorts?q="+fCode;
	
		$.ajax({

			url: path,

			type: "GET",

			dataType:'json',

			success:function(data){
				console.info(data);
				if(data.status == "yes"){
					result = "yes";
					upMasterIframe(data.info.shopId,myJson);
				
				}else{
					result = "no";
					alert("当前f码不合法");
				}
			},

			error:function(er){
			
			
			}

		});
		
		if(result == "no"){
			return false;
		}
	
	}
	function upMasterIframe(shopId,myJson){
	   scheduleAll();
	//	var path = "<%=path %>/api/v1/communities/page/index/"+communityId+"/uploadFile";
		
		if(myJson.username == ""){
			alert("手机号不能为空");
			return false;
		}
		
		var serviceArrayStr = "";
		
		for(var key in addMasterMap){
			if(addMasterMap[key] == "1"){
				serviceArrayStr = serviceArrayStr + key + ",";
			}
		}
		    
		var phone = $("#add_master_main_phone").val();
		var pass = $("#add_master_main_pass").val();
		var userName = $("#add_master_main_username").val();
		var nickName = $("#add_master_main_nickname").val();
		var shopPhone = $("#add_master_main_shop_phone").val();
		var adress = $("#add_master_main_adress").val();
		var idCard = $("#add_master_main_idcard").val();    
		var userId = $("#user_up_master_id").val();    
		var status = $("#community_service_main_status").val();    
		    
		pass = hex_md5(pass);
		    
		window.document.getElementById("iframe1").contentWindow.document.getElementById("community_service_main_status").value = status;
		window.document.getElementById("iframe1").contentWindow.document.getElementById("user_up_master_id").value = userId;
		window.document.getElementById("iframe1").contentWindow.document.getElementById("community_service_main_phone").value = phone;
		window.document.getElementById("iframe1").contentWindow.document.getElementById("community_service_password").value = pass;
		window.document.getElementById("iframe1").contentWindow.document.getElementById("community_service_main_username").value = userName;
		window.document.getElementById("iframe1").contentWindow.document.getElementById("community_service_main_nickname").value = nickName;
		window.document.getElementById("iframe1").contentWindow.document.getElementById("community_service_main_shopPhone").value = shopPhone;
		window.document.getElementById("iframe1").contentWindow.document.getElementById("community_service_main_adress").value = adress;
		window.document.getElementById("iframe1").contentWindow.document.getElementById("community_service_main_idcard").value = idCard;
		window.document.getElementById("iframe1").contentWindow.document.getElementById("community_service_main_shopId").value = shopId;
		window.document.getElementById("iframe1").contentWindow.document.getElementById("community_service_main_service_array").value = serviceArrayStr;
		window.document.getElementById("iframe1").contentWindow.document.getElementById("community_service_role").value = "shop";
		window.document.getElementById("iframe1").contentWindow.document.getElementById("community_service_form").action = "<%=path %>/api/v1/communities/page/index/"+communityId+"/uploadFile/up";
		
		window.document.getElementById("iframe1").contentWindow.document.getElementById("community_service_form").submit();
		
		var imgPre = document.getElementById("add_master_img_id");
        imgPre.src = "${pageContext.request.contextPath }/images/repair/add-master-pic.jpg";
		 document.getElementById("clean_add_fei").reset();
		 onScheduleAll();
		 alert("成功");
		 location.href="<%=path %>/jsp/estate/property/repair.jsp";
		
	
	}
	
	function addMasterUserIsEmpty(userName){
		var path = "<%= path%>/api/v1/communities/"+communityId+"/users/isEmpty/"+userName;
		
			$.ajax({

			url: path,

			type: "GET",

			dataType:'json',

			success:function(data){
				console.info(data);
				if(data.status == "yes"){
					result = "yes";
				
				}else{
					result = "no";
					alert("当前用户名已存在 请重新输入");
					return false;
				}
			},

			error:function(er){
			
			
			}

		});
	}


    function isAddClean(addlean){
	    if(clean=="clean"){
	   		 document.getElementById("clean_none").style.display="none";
	    }
    
    }
	
	function upMasterSubmit(){
	
    	var fCode = $("#add_master_main_fcode").val();
		var phone = $("#add_master_main_phone").val();
		var pass = $("#add_master_main_pass").val();
		var shopName = $("#add_master_main_shopname").val();
		var nickName = $("#add_master_main_nickname").val();
		var shopPhone = $("#add_master_main_shop_phone").val();
		var adress = $("#add_master_main_adress").val();
		var idCard = $("#add_master_main_idcard").val();
		var myJson = {
			"fCode" : fCode ,
			"phone" : phone ,
			"pass" : pass ,
			"shopName" : shopName ,
			"nickName" : nickName ,
			"shopPhone" : shopPhone ,
			"adress" : adress ,
			"idCard" : idCard ,
			"role" : "shop" , 
			"shopId" : 0 , 
			"uploadFile" : ""
			
		};
		if(fCode == "" || phone == "" ||  shopName == "" || nickName == "" || shopPhone == "" || adress == "" || idCard == ""){
			alert("请完善信息");
			return false;
		}
		upFCode(myJson);
	}
	
	function upShopsUser(shopId){
	
	document.getElementById("up_master_submit").style.display="";
	document.getElementById("add_master_submit").style.display="none";
	
		var path = "<%= path%>/api/v1/communities/page/index/getShopsUser/"+shopId;
		
			$.ajax({

			url: path,

			type: "GET",

			dataType:'json',

			success:function(data){
			    data=data.info;
				document.getElementById("add_master_main_fcode").value=data.authCode;
				document.getElementById("add_master_main_fcode").disabled="true";
				document.getElementById("add_master_main_phone").value=data.shopPhone;
				//document.getElementById("add_master_main_phone").disabled="true";
				document.getElementById("add_master_main_pass").disabled="true";
				
				//document.getElementById("add_master_main_pass").style.display="none";
				document.getElementById("add_master_main_username").value=data.username;
				//document.getElementById("add_master_main_username").disabled="true";
				document.getElementById("add_master_main_nickname").value=data.nickname;
				document.getElementById("add_master_main_shop_phone").value=data.phone;
				document.getElementById("add_master_main_adress").value=data.avatar;
				document.getElementById("add_master_main_idcard").value=data.idcard;
				document.getElementById("user_up_master_id").value=data.userId;
				document.getElementById("community_service_main_status").value=data.status;
				if(data.status=="leave"){
					document.getElementById("shield_master").style.display="none";
				
				}else{
				    document.getElementById("shield_master").style.display="";
				}
			
				if(data.logo!=null&&data.logo!=""){
			    	document.getElementById("add_master_img_id").src=data.logo;
				}else{
			    	document.getElementById("add_master_img_id").src="${pageContext.request.contextPath }/images/repair/add-master-pic.jpg";
				}
				var len=data.list;
				for(var i=0;i<len.length;i++){
				  var j=len[i].catId;
				   addMasterMap[j] = "1";
				   document.getElementById("add_master_map_"+j).className="select";
				}
				
			},

			error:function(er){
			
			
			}

		});
	
	}
	
	
	
function add_none(){
$("#add_master_main_fcode").removeAttr("disabled");
$("#add_master_main_pass").removeAttr("disabled");
//$("#add_master_main_username").removeAttr("disabled","");
//document.getElementById("add_master_main_fcode").disabled=false;
//document.getElementById("add_master_main_username").disabled=false;
document.getElementById("add_master_img_id").src="${pageContext.request.contextPath }/images/repair/add-master-pic.jpg";
document.getElementById("add_master_submit").style.display="";
document.getElementById("up_master_submit").style.display="none";
}
</script>
	</head>
	<body>
        <input type="hidden" id="shop_id_up" value="<%=shopId%>">
		<section>
			<div class="content clearfix">
				<jsp:include flush="true" page="../common/service-left.jsp"></jsp:include>
				<div class="add-master-body">
					<div class="repair-title-line">
						<p class="add-master-title" id="add_master_bao_fei">
							添加维修师傅
						</p>
						<a class="master-dimission" href="javascript:;" id="shield_master" style="display: none;" onclick="shield();">离职</a>
					</div>
					<form id="clean_add_fei">
					
						<input type="hidden" id="community_service_main_status" />
						<input type="hidden" id="user_up_master_id" />
						<div class="add-master-info">
							<ul>
								<li>
									<span>F码</span>
									<input id="add_master_main_fcode" type="text"
										placeholder="如：WM2457487" />
									<span>手机号</span>
									<input id="add_master_main_phone" type="text"
										placeholder="如：13845411245" />
								</li>
								<li>
									<span>密码</span>
									<input id="add_master_main_pass" type="text" />
									<span>真实姓名</span>
									<input type="text" id="add_master_main_username"
										placeholder="如：李晓明" />
								</li>
								<li>
									<span>昵称</span>
									<input type="text" id="add_master_main_nickname"
										placeholder="如：李师傅" />
									<span>电话</span>
									<input type="text" id="add_master_main_shop_phone"
										placeholder="如：13845411245" />
								</li>
								<li>
									<span>地址</span>
									<input type="text" id="add_master_main_adress"
										placeholder="如：法华南里38号" />
									<span>身份证号</span>
									<input id="add_master_main_idcard" type="text"
										placeholder="如：102114198012140532" />
								</li>
							</ul>
						</div>

						<div class="add-master-opt clearfix" id="clean_none">
							<div class="add-master-type">
								服务类型
							</div>
							<ul id="add_master_ul">
								<li>
									<a class="select" href="javascript:;">弱电</a>
								</li>
								<li>
									<a class="select" href="javascript:;">强电</a>
								</li>
								<li>
									<a class="select" href="javascript:;">综合</a>
								</li>
								<li>
									<a href="javascript:;">上下水</a>
								</li>

							</ul>
							<input type="hidden" value="" name="add_master_main_shop_id"
								id="add_master_main_shop_id" />
							<input type="hidden" value="" name="add_master_main_user_id"
								id="add_master_main_user_id" />
							<input type="hidden" value=""
								name="add_master_main_service_array"
								id="add_master_main_service_array" />
						</div>
						<!--<input type="file" name="addMasterUploadFile" id="addMasterUploadFile" accept="image/*" style="display: none;" />
				-->
						<div class="add-master-upload-pic">
							<span>上传照片</span>
							<div class="add-master-pic-box clearfix">
								<img
									src="${pageContext.request.contextPath }/images/repair/add-master-pic.jpg"
									id="add_master_img_id" />
								<p class="add-master-message">
									请上传维修师傅的清楚脸部正面照,以便用户更方便快速的找到自己满意的维修师傅。
								</p>
								<p class="add-master-button">
									<a href="javascript:;" onclick="addMasterFileClick();">上传照片</a>
								</p>
							</div>
						</div>
						<div class="add-master-save">
							<a href="javascript:;" onclick="addMasterSubmit();"
								id="add_master_submit">保存</a>
							<a href="javascript:;" onclick="upMasterSubmit();"
								id="up_master_submit" style="display: none;">保存</a>
						</div>
				</div>
			</div>
		</section>
		</form>

		<iframe style="display: none;" name="iframe1" id="iframe1"
			src="<%=path %>/jsp/service/iframeInner.jsp">

		</iframe>
	<div class="add-price-box" id="schedule_all" style="display: none;">
		<img alt="" src="<%= basePath %>images/5-130H2191324-52.gif">
	</div>
	<div class="upload-master-face-bg" id="bg_schedule" style="display: none;">&nbsp;</div>
	

		<!--   上传弹出窗     -->

		<!--  <div class="upload-master-face-box clearfix">
		<div class="upload-master-face-title">上传头像<a class="upload-master-face-close" title="关闭" href="javascript:close_operate_div();">&nbsp;</a></div>
		<div class="upload-master-face-preview">
			<div class="upload-master-face-former">
				<img alt="师傅头像" id="target" src="${pageContext.request.contextPath }/images/upload-img.jpg" class="uploadImages" />
			</div>
			<div class="upload-master-face-finish">
				<div class="upload-master-face-finish-title">预览</div>
				<div class="upload-master-face-finish-img">
						<div id="preview_box"><img id="crop_preview" alt="" src="${pageContext.request.contextPath }/images/upload-img-preview.jpg" class="uploadImages" /></div>
				</div>
			</div>
		</div>
		<div class="upload-master-face-submit"><a class="green-button" href="javascript:;" id="save_img">保存</a><a class="green-button" href="javascript:close_operate_div();">取消</a></div>
	</div>
	<div class="upload-master-face-bg">&nbsp;</div> -->
	
	<script type="text/javascript">
	
		 function scheduleAll(){
			$("#schedule_all").attr("style","display:block");
			$("#bg_schedule").attr("style","display:block");
			
		}
		
		function onScheduleAll(){
			$("#schedule_all").attr("style","display:none");
			$("#bg_schedule").attr("style","display:none");
		}
	
		function addMasterJsp() {
			addMasterGetItemCategory("<%=sort%>");
			 document.getElementById("clean_add_fei").reset();
			 document.getElementById("clean_none").style.display="";
			 document.getElementById("add_master_bao_fei").innerHTML="<%=master%>";
		    add_none();
	    }
	    function upMasterJsp(){
	     addMasterGetItemCategory("<%=sort%>");
		 document.getElementById("add_master_bao_fei").innerHTML="<%=master%>";
         upShopsUser("<%=shopId%>");
	    
	    }
	    
	    function isUpAndAdd(isUpAndAdd){
	       if(isUpAndAdd=="up"){
	       upMasterJsp();
	       }else{
	       addMasterJsp();
	       }
	    }
		
	isUpAndAdd("<%=isUpAndAdd%>");
	function leftSort(sort){
	   if(sort==5){
	     leftClikCss("left_repair_fei");
	   }else{
	    leftClikCss("left_clean_fei");
	   
	   }
	}
		leftSort("<%=sort%>");
	</script>
	</body>
</html>
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
<title>等待审核的活动_小间物业管理系统</title>

</head>
<body>

	<section>
		<div class="content clearfix">
			<jsp:include flush="true" page="../public/activity_left.jsp"></jsp:include>
			<div class="activity-right-body"  id="activity_detail_id">
				<!--
				<div class="activity-head">
					<div class="activity-title">
						一起去踢球
					</div>
					<div class="activity-info">
						<p>时间：<span> 4月22日  6：32</span></p>
						<p>地点：<span>  东城区法华南里36号</span></p>
					</div>
				</div>
			
				<div class="activity-content">
					<!--<p>活动是由共同目的联合起来并完成一定社会职能的动作的总和。活动由目的、动机和动作构成，具有完整的结构系统。苏联心理学家从20年代起就对活动进行了一系列研究。其中Α.Н.列昂节夫的活动理论对苏联心理学的发展影响很大，成为现代苏联心.活动是由共同目的联合起来并完成一定社会职能的动作的总和。活动由目的、动机和动作构成，具有完整的结构系统。苏联心理学家从20年代起就对活动进行了一系列研究。其中Α.Н.列昂节夫的活动理论对苏联心理学的发展影响很大，成为现代苏联心.</p>
					<p class="activity-pic"><img src="${pageContext.request.contextPath }/images/upload-img.jpg"/></p>
					<p class="activity-pic"><img src="${pageContext.request.contextPath }/images/upload-img-preview.jpg"/></p>
				</div>
				-->
			</div>
		</div>
		<script type="text/javascript">
		
	      function getActivitiesDetail(activitiesId){
	      var path = "<%= path%>/api/v1/communities/${communityId}/activities/webIm/getActivitiesDetail?activitiesId="+activitiesId;
				$.ajax({

					url: path,

					type: "GET",
			

					dataType:'json',

					success:function(data){
					data=data.info;
					var activitesDate=$("#activity_detail_id");
					var activityStatus=$("#activity_status_ongoing_rejected").val();
					var activityPower = "";
					if(activityStatus=="ongoing"){
						activityPower = "<a id=\"activities_aid_"+data.activityId+"\" href=\"javascript:;\" onclick=\"updateActivitiesType("+data.activityId+",'rejected')\" class=\"refuse\">关闭群组</a>";
					}else if(activityStatus=="rejected"){
						activityPower = "<a id=\"activities_aid_"+data.activityId+"\" href=\"javascript:;\" onclick=\"updateActivitiesType("+data.activityId+",'ongoing')\" class=\"open\">打开群组</a>";
					}
					activitesDate.empty();
					 var activitesDatail="<div class=\"activity-head\">"+activityPower+"";
					 activitesDatail+="<div class=\"activity-title\">";
					 activitesDatail+=data.activityTitle;
					 activitesDatail+="</div>";
					 activitesDatail+="<div class=\"activity-info\">";
					 activitesDatail+="<p>时间：<span> 4月22日  6：32</span></p>";
					 activitesDatail+="<p>地点：<span>  "+data.room+"</span></p></div></div>";
					 activitesDatail+="<div class=\"activity-content\">";
					 activitesDatail+="<p>"+data.activityDetail+"</p>";
					 var list=data.list;
					 for ( var i = 0; i < list.length; i++) {
						 activitesDatail+="<p class=\"activity-pic\"><img src=\""+list[i].photoUrl+"\"/></p>";
					}
					
					 activitesDatail+="</div>";
					activitesDate.append(activitesDatail);
			
					},

					error:function(er){
			
						alert(er);
			
					}

				});
	      
	      }	
		
			function activityRefuse(){
				$("#activity-refuse").show();
				$(".activity-blank-bg").show();
			}
			function activityRefuseClose(){
				$("#activity-refuse").hide();
				$(".activity-blank-bg").hide();
			}
		</script>
	</section>
</body>
<div class="activity-image" id="activity_image" style="display:none;">
	<div class="activity-img-title"><span>第二届社区拔河比赛</span></div>
	<a href="javascript:hideImage();">&nbsp;</a>
	<img alt="" src="${pageContext.request.contextPath }/images/service/big.jpg"/>
</div>
<div class="activity-blank-bg" style="display:none;">&nbsp;</div>
<div class="activity-refuse" id="activity-refuse" style="display:none;">
	<div>
		<textarea rows="" cols="">请输入关闭理由</textarea>
	</div> 
	<div class="activity-check-opt">
		<a href="javascript:activityRefuseClose();">取消</a>
		<a href="javascript:void(0);">发送后台通知</a>
	</div>
</div>

<jsp:include flush="true" page="../public/footer.jsp"></jsp:include>
</html>
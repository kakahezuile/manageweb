<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>签约店家页</title>
<script type="text/javascript">

</script>
<style type="text/css">
.dianjia_body{
	height: 100%;
	width: 93%;
	margin: 15px 35px 15px 15px;
	
}
.dianjia_top{
	float: left;
	width: 100%;
	height: 122px;
	border: solid 1px;
	margin: 15px 15px 15px 15px;
}
.dianjia_center{
	height: 84px;
	width: 100%;
	float: left;
	border: solid 1px;
	margin: 15px 15px 15px 15px;
}
.dianjia_main{
	width: 100%;
	float: left;
	height: 500px;
	border: solid 1px;
	margin: 15px 15px 15px 15px;
}
.dianjia_top_left{
	width: 33%;
	height: 100%;
	float: left;
	border-right: solid 1px;
	line-height: 122px;
	text-align: center;
	font-size: 15px;
}
.dianjia_top_center{
	width: 33%;
	height: 100%;
	float: left;
	border-right: solid 1px;
	line-height: 122px;
	text-align: center;
	font-size: 15px;
}

.dianjia_top_right{
	width: 33%;
	height: 100%;
	float: left;
	text-align: center;
	line-height: 122px;
	font-size: 15px;
}
.dianjia_top_text{
	font-size: 26px;
}
.dianjia_center_first{
	width: 47%;
	float: left;
	height: 100%;
	line-height: 84px;
	
}

.dianjia_search_text{
	background:transparent;border:1px solid #E0E1DB;
	height: 45px;
	width: 95%;
	margin-left: 30px;
	font-size: 20px;
	text-align: left;
}
.dianjia_center_second{
	height: 100%;
	float: left;
	line-height: 84px;
	width: 10%
}
.dianjia_center_second_button{
	background-color: #018F53;
	margin-left :20px;
	
	padding:0 28px;
	height: 45px;
	color: #ffffff;

}

.dianjia_center_three{
	width: 23%;
	float: left;
	height: 100%;
	line-height: 84px;

}

.dianjia_center_three_button{
	background-color: #018F53;
	margin-left :20px;
	
	padding:0 8px;
	height: 45px;
	color: #ffffff;

}
</style>
</head>
<body>
	<div class="dianjia_body">
		<div class="dianjia_top">
			
			<div class="dianjia_top_left" align="center">
				<span id="dianjia_top_left_id" class="dianjia_top_text">128</span>&nbsp;正在服务
			</div>
			
			<div class="dianjia_top_center" align="center">
				<span id="dianjia_top_center_id" class="dianjia_top_text">13</span>&nbsp;未开启服务
			</div>
			
			<div class="dianjia_top_right" align="center">
				<span id="dianjia_top_right_id" class="dianjia_top_text">11</span>&nbsp;被屏蔽
			</div>
			
		</div>
		
		<div class="dianjia_center">
			<div class="dianjia_center_first">
			
				<input name="dianjia_search_text" width="100" class="dianjia_search_text"  id="dianjia_search_text" value="" />  
			</div>
			
			<div class="dianjia_center_second">
			
				<input  type="button" class="dianjia_center_second_button" id="dianjia_center_second_button" name="dianjia_center_second_button" value="搜索" />  
			</div>
			
			<div class="dianjia_center_three">
			
				<input  type="button" class="dianjia_center_three_button" id="dianjia_center_second_button" name="dianjia_center_second_button" value="创建一个新的店家 +" />  
			</div>
			
			
		</div>
		
		<div class="dianjia_main">
			
		</div>
	</div>
</body>
</html>
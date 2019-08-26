<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String webTitle = properties.getProperty("webTitle");
%>
<footer class="footer-box">
	<!--<div class="footer">
		<div class="footer-list clearfix">
			<ul style="margin-left:0;">
				<li class="footer-list-title">合作商家</li>
				<li><a title="小间官网,小间租房,在线租房,在线合租" href="javascript:;">阿里巴巴</a></li>
				<li><a title="小间官网,小间租房,在线租房,在线合租" href="javascript:;">百度搜索</a></li>
				<li><a title="小间官网,小间租房,在线租房,在线合租" href="javascript:;">链家地产</a></li>
			</ul>
			<div class="footer-line"></div>
			<ul>
				<li class="footer-list-title">公司信息</li>
				<li><a title="小间官网,小间租房,在线租房,在线合租" href="javascript:;">关于</a></li>
				<li><a title="小间官网,小间租房,在线租房,在线合租" href="javascript:;">招贤纳士</a></li>
				<li><a title="小间官网,小间租房,在线租房,在线合租" href="javascript:;">灾难响应</a></li>
				<li><a title="小间官网,小间租房,在线租房,在线合租" href="javascript:;">条款与隐私</a></li>
			</ul>
			<div class="footer-line"></div>
			<ul>
				<li class="footer-list-title">发现</li>
				<li><a title="小间官网,小间租房,在线租房,在线合租" href="javascript:;">信任与安全</a></li>
				<li><a title="小间官网,小间租房,在线租房,在线合租" href="javascript:;">商务差旅</a></li>
				<li><a title="小间官网,小间租房,在线租房,在线合租" href="javascript:;">网站地图</a></li>
			</ul>
			<div class="footer-line"></div>
			<ul>
				<li class="footer-list-title">出租</li>
				<li><a title="小间官网,小间租房,在线租房,在线合租" href="javascript:;">公共设施</a></li>
				<li><a title="小间官网,小间租房,在线租房,在线合租" href="javascript:;">周边环境</a></li>
				<li><a title="小间官网,小间租房,在线租房,在线合租" href="javascript:;">休闲娱乐</a></li>
			</ul>
		</div>
    	
    </div>
    -->
    
    <div class="copy-right">&copy;2014 版权所有 <a title="小间官网,小间租房,在线租房,在线合租" href="http://localhost:8080/numbertwo">小间科技&nbsp;</a>京ICP备12445751号-1</div>
    
    
    <!-- <p class="baidu-statistics">
    	<script type="text/javascript">
		var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
		document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F95771cbc43afe7ce2a19ecf7a5fef153' type='text/javascript'%3E%3C/script%3E"));
		</script>
	</p>  -->
</footer>


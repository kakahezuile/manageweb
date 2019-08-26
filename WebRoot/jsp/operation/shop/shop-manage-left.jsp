<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<div class="left-body">
	<ul>
		<li>
			<a href="./signed.jsp" ${param.module == "signed" ? "class='select'" : ""}>店家开通</a>
		</li>
		<li>
			<a href="./nearby.jsp" ${param.module == "nearby" ? "class='select'" : ""}>周边商家管理</a>
		</li>
		<li>
			<a href="./quick-shop.jsp" ${param.module == "quick_shop" ? "class='select'" : ""}>快店配置</a>
		</li>
		<li>
			<a href="./welfare.jsp" ${param.module == "welfare" ? "class='select'" : ""}>福利</a>
		</li>
	</ul>
</div>

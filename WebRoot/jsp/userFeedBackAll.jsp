<%@ page language="java" pageEncoding="utf-8"%>

<div class="upload-master-face-bg" id="upload-master-face-bg" style="display: none;">&nbsp;</div>
<div class="hx-content clearfix" id="content" style="display:;">
	<div class="hx-feedback-left" id="leftcontact">
		<div class="chat-search" id="form_id">
			<form class="filterform" action="#"><input onkeyup="searchCommunity(this)" class="filterinput" placeholder="搜索小区" type="text"></form>
		</div>
		<div class="community-list" style="height:590px;overflow-y:auto;overflow-x:auto;">
			<ul><li id="community-list"></li></ul>
		</div>
	</div>
	<div class="chatRight" style="width:642px">
		<div class="bb-community-title" style="min-height:14px;">
			<span id="community-name"></span>
			<input type="hidden" id="community-id">
			<p>
				<input id="community-user-search" onkeyup="searchCommunityUser(this)" class="search" type="text" placeholder="搜索用户昵称/房间号">
			</p>
		</div>
		<div id="community-user-list" class="community-user-list" style="height:590px;overflow-y:auto;overflow-x:auto;">
		</div>
	</div>
</div>
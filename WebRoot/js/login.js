$(document).ready(function(){
	$('#lg_login').on('click', function() {
	        var conn = null;
	        conn = new Easemob.im.Connection();
	        $('body').on('click', '#lg_login', function() {
	                var username = $("#lg_username").val();
	                var pass = $("#lg_password").val();
	                conn.open({
	                        user : username,
	                        pwd : pass,
	                        appKey : 'kuaiju#xiaojianproperty'
	                });
	        });
	        conn.init({
	                onOpened : function() {
	                        conn.setPresence();
	                      
	                        addCookie("my_conn", conn, 1);
	                        window.location.href="jsp/home.jsp"; 
	                }
	        });
		});
});
function addCookie(name,value,expiresHours){
	var cookieString=name+"="+escape(value);
	//判断是否设置过期时间
	if(expiresHours>0){
	var date=new Date();
	date.setTime(date.getTime+expiresHours*3600*1000);
	cookieString=cookieString+"; expires="+date.toGMTString();
	}
	document.cookie=cookieString;
} 

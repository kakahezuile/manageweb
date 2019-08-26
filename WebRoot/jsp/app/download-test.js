$(document).ready(function(){
		setTimeout( "checkPlatform()",500 );
		//checkPlatform();
	});
	//手机端判断各个平台浏览器及操作系统平台
	function checkPlatform(){
		  if(/android/i.test(navigator.userAgent)){
		      if(/MicroMessenger/i.test(navigator.userAgent)){
		       
		        $("#blank_bg").show();//这是Android平台下浏览器
		        $("#android-word").show();
			  }else{
			    //window.location.href="http://ltzmaxwell.qiniudn.com/bangbang_client.apk";
			  }
		     
		    //  top.location="http://ltzmaxwell.qiniudn.com/bangbang_client.apk"; 
		  }else	  if(/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)){
		      $("#blank_bg").hide();//这是iOS平台下浏览器
		      window.location.href="https://itunes.apple.com/cn/app/bang-bang-she-qu-hao-bang-shou/id978272133?mt=8";
		      $("#blank_bg").show();
		      $("#ios-word").show();
		  }else	  if(/Linux/i.test(navigator.userAgent)){
		      $("#blank_bg").hide();//这是Linux平台下浏览器
		        //window.location.href="http://ltzmaxwell.qiniudn.com/bangbang_client.apk";
		  }else	  if(/Linux/i.test(navigator.platform)){
		      $("#blank_bg").hide();//这是Linux操作系统平台
		      // window.location.href="http://ltzmaxwell.qiniudn.com/bangbang_client.apk";
		  }else	  if(/MicroMessenger/i.test(navigator.userAgent)){
			 window.location.href="https://itunes.apple.com/cn/app/bang-bang-she-qu-hao-bang-shou/id978272133?mt=8";
			 $("#blank_bg").show();
			 $("#ios-word").show();
		 }else{
		   $("#blank_bg").hide();//这是Linux操作系统平
		 }
		 $(".android").attr("href","http://7d9lcl.com2.z0.glb.qiniucdn.com/bangbang_test.apk");
		 $(".ios").attr("href","https://itunes.apple.com/cn/app/bang-bang-she-qu-hao-bang-shou/id978272133?mt=8");
	}

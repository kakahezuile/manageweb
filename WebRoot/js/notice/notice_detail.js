function selectUserList() {
	var phone = document.getElementById("phone_room").value;
	var path =  "/api/v1/communities/${communityId}/users/selectUser?phone="+phone;
	$.ajax({
				url : path,
				type : "GET",
				dataType : 'json',
				success : function(data) {
					data = data.info;

					var myDate = new Date(stringToTime(startTime));
					var myDate2 = new Date(stringToTime(endTime));
					timeQuantum(startTime, endTime);

					timeDisplay(type);

				},
				error : function(er) {
				}
			});	
}
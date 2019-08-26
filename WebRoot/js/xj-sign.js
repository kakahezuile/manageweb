var xjSign = function(map) {
	var str = "";
	var array = new Array();
	for(var key in map){
		array.push(key);
	}
	array.sort();
	var len = array.length;
	for(var i = 0 ; i < len ; i++){
		if(str == ""){
			var key = array[i];
			var value = map[value];
			str = key + "=" + value;
		}else{
			str = str + "," + key + "=" + value;
		}
	}
	return str;
};
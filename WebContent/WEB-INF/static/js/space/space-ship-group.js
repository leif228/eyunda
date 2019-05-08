$(document).ready(function(){
	// 选择委托类型
	$("#groupId").on("change",function(){
		window.location.href = _rootPath+"/space/ship/loadShipGroups?groupId="+$(this).val();
	});

});

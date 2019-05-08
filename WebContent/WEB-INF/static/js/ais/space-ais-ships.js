$(document).ready(function(){
	
	$('input[type=checkbox],input[type=file]').uniform();
	
	$('select').select2();
	
	$(".seeCurrent").live("click", function(){
		window.location.href = _rootPath + "/space/ais/aisShip/shipDistributoin?keyWords="
		+ $("#keyWords").val() + "&selectCode=" + $("#selectCode").val();
	});
	
});

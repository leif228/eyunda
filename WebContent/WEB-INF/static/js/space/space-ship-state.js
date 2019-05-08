$(document).ready(function(){
	
	$('input[type=checkbox],input[type=file]').uniform();
	
	$('select').select2();
	
	$("#tblShip").on('click','.back-normal',function(){
		var node = $(this);
		var mmsi = node.attr("data-id");
		var shipStatus = node.attr("data-src");
		var currentStatus = node.attr("currentStatus");
		if(currentStatus == "sailing"){
			alert("该船舶正在航行");
		}else{
			if(shipStatus == "sailing"){
				alert("不能设置在航状态，请添加离港船舶动态");
			}else{
				if(confirm("确定要修改船舶状态么？")){
					$.post( _rootPath+'/space/state/myShip/changeShipStatus',{mmsi:mmsi,shipStatus:shipStatus},function(data){
						var returnCode = $(data)[0].returnCode;
						var message = $(data)[0].message;
						if(returnCode == "Failure"){
							alert(message);
						}else{
							node.siblings(".back-current").removeClass("back-current").addClass("back-normal");
							node.removeClass("back-normal").addClass("back-current");
						}
					},'json');
				}
			}
		
		}
	});
	
	$(".search").live("click",function(){
		window.location.href = _rootPath 
		+ "/space/state/myShip?keyWords=" + $("#keyWords").val();
	});
	
});

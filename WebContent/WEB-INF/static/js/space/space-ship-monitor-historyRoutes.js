$(document).ready(function(){
	$(".find").live("click", function(){
		window.location.href = _rootPath + "/space/monitor/myAllShip/historyRoutes?shipId="+$(this).val();
	});
	
	$(".search").live("click",function(){
		
		var startTime = new Date($("#startTime").val().replace(/\-/g,'/'));
		var endTime = new Date($("#endTime").val().replace(/\-/g,'/'));
		
		if(startTime > endTime){
			alert("起始日期不能大于终止日期！");
			return false;
		}
		
     	window.location.href = _rootPath 
     			+ "/space/monitor/myAllShip/historyRoutes?shipId=" + $("#shipId").val()
     			+ "&startTime=" + $("#startTime").val() + "&endTime=" + $("#endTime").val();
     });
	
	$("#exportRoutes").live("click",function(){
		var shipId = $("#shipId").val();
		var keyWords = $("#keyWords").val();
		
		$.ajax({
			method : "GET",
			url : _rootPath + "/space/monitor/myAllShip/exportRoutesToExcel",
			data : {shipId : shipId, keyWords : keyWords},
			datatype : "json",
			success : function(data){
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if(returnCode == "Failure")
					alert(message)
				else{
					var urlPath = $(data)[0].urlPath;
					window.location.href = _rootPath +'/download/excelDownload?url=' + urlPath;
				}
			}
		});
	});
	
    $('.form_datetimeStart').datetimepicker({
        //language:  'fr',
        weekStart: 1,
        todayBtn:  1,
		todayHighlight: 1,
		startView: 2,
		forceParse: 0,
        showMeridian: 1,
        minView: "month", //选择日期后，不会再跳转去选择时分秒 
        autoclose:true
    });
    
    $('.removeStartTime').click(function(){
    	$('#startTime').val("");
    });
    
    $('.form_datetimeEnd').datetimepicker({
        //language:  'fr',
        weekStart: 1,
        todayBtn:  1,
		todayHighlight: 1,
		startView: 2,
		forceParse: 0,
        showMeridian: 1,
        minView: "month", //选择日期后，不会再跳转去选择时分秒 
        autoclose:true
    });
    
    $('.removeEndTime').click(function(){
    	$('#endTime').val("");
    });
	
});




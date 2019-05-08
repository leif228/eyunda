$(document).ready(function(){
	  // 删除船舶最后一条上报信息
	  $(".btnRemoveLastShipArvlft").live("click",function() {
	    $("#removeMmsi").val($("#mmsi").val());
	    $("#removeArriveId").val($(this).attr("idVal"));
	    $("#removeLastShipArvlftDialog").modal("show");
	    return true;
	  });
	  
	  $("#exportStateExcel").live("click",function(){
			var mmsi = $("#mmsi").val();
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			$.ajax({
				method : "GET",
				url : _rootPath + "/space/state/myShip/shipArvlft/exportStateToExcel",
				data : {mmsi : mmsi, startTime : startTime, endTime : endTime},
				datatype : "json",
				success : function(data){
					var returnCode = $(data)[0].returnCode;
					var message = $(data)[0].message;
					if(returnCode == "Failure"){
						alert(message);
						return false;
					} else{
						var urlPath = $(data)[0].urlPath;
						window.location.href = _rootPath +'/download/excelDownload?url=' + urlPath;
						return true;
					}
				}
			});
		});
});

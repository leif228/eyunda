$(document).ready(function(){
	$('input[type=radio],input[type=file]').uniform();
	
	$('select').select2();
	
	// 删除
	$(".btnDeleteCabin").live("click", function() {
		$("#delCabinId").val($(this).attr("idVal"));
		$("#searchKeyWords").val($("#keyWords").val());
		$("#delPageNo").val($("#pageNo").val());
		
		$("#deleteCabinDialog").modal("show");
		
		return true;
	});

	$("#selectAll").change(function() {
		if($(this).is(':checked')){
			$("[name = choiceCabinIds]:checkbox").attr("checked", true);
		}else{
			$("[name = choiceCabinIds]:checkbox").attr("checked", false);
		}
	});
	
	$("#publishers").live("click", function() {
		var choiceCabinIds = [];
		var p=0;
		$("[name = 'choiceCabinIds']").each(function(i){
			if($(this).is(':checked')){
				choiceCabinIds[p] = $(this).attr("value");
				p++;
		    }
		});
		$.ajax({
			method : "GET",
			data : {choiceCabinIds : choiceCabinIds},
			traditional: true,// ajax传数组
			url : _rootPath + "/manage/ship/publishers",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					var mapReceiver = $(data)[0].mapReceiver;
					
					var li = "";
					for(var key in mapReceiver){
						li += "<li>";
						li += mapReceiver[key];
						li += "  <input name='cabinId' type='text' value='"+key+"' style='display:none;'>"
						li += "</li>"
					}
					$("#recipients").html(li);
					$("#giveRedPacketsDal").modal("show");
				}
			}
		});
	});
	
	$("#giveRedPacketsBtn").live("click", function() {
		$("#giveRedPackets").ajaxSubmit({
			method : "POST",
			url : _rootPath+"/manage/ship/batchBonusSend",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
				} else {
					var title = $(data)[0].title;
					var tips = $(data)[0].tips;
					if(tips == "T"){
						var content = $(data)[0].content;
						var str = "";
						str = title;
						for(var i=0; i<content.length; i++){
							str += content[0]+'\n';
						}
						alert(str)
					}else{
						alert(title);
					}
					return true;
				}
			}
		})
	});
	
});

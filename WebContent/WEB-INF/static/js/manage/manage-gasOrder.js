$(document).ready(function() {
	
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();

//	// 查找
//	$(".btnSerach").live("click",function(){
//		
//		var startTime = new Date($("#startTime").val().replace(/\-/g,'/'));
//		var endTime = new Date($("#endTime").val().replace(/\-/g,'/'));
//		
//		if(startTime > endTime){
//			alert("起始日期不能大于终止日期！");
//			return false;
//		}
//		
//     });

	// 删除
	$(".btnDelete").live("click", function() {
		$("#deleteId").val($(this).attr("idVal"));
		$("#deleteDialog").modal("show");

		return true;
	});
	
	// 上架
	$(".btnBackMoney").live("click", function() {
		$("#backId").val($(this).attr("idVal"));
		$("#backMoneyDialog").modal("show");

		return true;
	});
	
	// 下架
	$(".btnIsAddOil").live("click", function() {
		$("#isAddOilId").val($(this).attr("idVal"));
		$("#isAddOilDialog").modal("show");

		return true;
	});
	
	// 确认上架
	$(".backMoney").live("click", function() {
		var id = $("#backId").val();
		$.ajax({
			method : "GET",
			data : {id : id},
			url : _rootPath+"/manage/gas/gasOrder/backMoney",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					//读入数据，并填入
					$("#backMoneyDialog").modal("hide");
					$("#button" + id).remove();
                    $("#desc" + id).html("已退款");
				}
			}
		});
		return true;
	});
	
	// 加油确认
	$(".isAddOil").live("click", function() {
		var id = $("#isAddOilId").val();
		$.ajax({
			method : "GET",
			data : {id : id},
			url : _rootPath+"/manage/gas/gasOrder/isAddOil",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					//读入数据，并填入
					$("#isAddOilDialog").modal("hide");
					$("#button" + id).remove();
                    $("#desc" + id).html("已加油");
				}
			}
		});
		return true;
	});
	
	// 删除
	$(".delete").live("click", function() {
		var id = $("#deleteId").val();
		$.ajax({
			method : "GET",
			data : {id : id},
			url : _rootPath+"/manage/gas/gas/delete",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					window.location.href=_rootPath + "/manage/gas/gas?pageNo="
						+ $("#delPageNo").val() + "&keyWords=" + $("#delKeyWords").val();
				}
			}
		});
		return true;
	});

	$(function () {
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
	
});
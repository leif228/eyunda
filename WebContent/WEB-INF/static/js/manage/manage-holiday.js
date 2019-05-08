$(document).ready(function() {
	//添加节假日按钮
	$("#btnAddHoliday").live("click",function(){
		$("#holidayId").attr("value","0");
		$("#holidayName").attr("value","");
		
		$("#startTime").attr("value","");
		$('.form_datetimeStart').datetimepicker('update');
		
		$("#endTime").attr("value","");
		$('.form_datetimeEnd').datetimepicker('update');
	});
	
	//修改节假日按钮
	$("#saveHoliday").live("click",function(){
		var id = $(this).attr("idVal");
		$("#holidayId").attr("value",id);
		
		var holidayName = $(this).attr("holidayName");
		$("#holidayName").attr("value",holidayName);
		
		var holidayStartDate = $(this).attr("holidayStartDate");
		$("#startTime").attr("value",holidayStartDate);
		$('.form_datetimeStart').datetimepicker('update');
		
		
		var holidayDataEndDat = $(this).attr("holidayDataEndDat");
		$("#endTime").attr("value",holidayDataEndDat);
		$('.form_datetimeEnd').datetimepicker('update');
	})
	
	//保存节假日信息
	$("#saveHolidayBtn").live("click",function(){
		if(!$("#holidayName").val()){
			alert("节假日名字不能为空");
			return false;
		}
		if(!$("#startTime").val()){
			alert("节假日起始日期不能为空");
			return false;
		}
		if(!$("#endTime").val()){
			alert("节假日结束日期不能为空");
			return false;
		}
		$("#editDialogForm").ajaxSubmit({
            type : "POST",
            url : _rootPath+"/manage/power/saveHoliday",
            datatype : "json",
            success : function(data) {
                var returnCode = $(data)[0].returnCode;
                var message = $(data)[0].message;
                var id = $(data)[0].id;
                if (returnCode == "Failure") {
                    alert(message);
                    return false;
                } else {
                    alert(message);
                    window.location.href = _rootPath+"/manage/power/holidayList";
                    return false;
                }
            }
        });
	});
	
	//删除节假日按钮
	$(".btnDelete").live("click",function(){
		var delHolidayId = $(this).attr("idVal");
		$("#delHolidayId").attr("value",delHolidayId);
	});
	
	//提交删除节假日信息
	$("#delHolidayBtn").click(function(){
		$("#deleteDialogForm").ajaxSubmit({
			type : "POST",
            url : _rootPath+"/manage/power/deleteHoliday",
            datatype : "json",
            success : function(data) {
                var returnCode = $(data)[0].returnCode;
                var message = $(data)[0].message;
                var id = $(data)[0].id;
                if (returnCode == "Failure") {
                    alert(message);
                    return false;
                } else {
                    alert(message);
                    window.location.href = _rootPath+"/manage/power/holidayList";
                    return false;
                }
            }
        });
	});

});
$(document).ready(function(){
	
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	$('select').select2();
	
	// Form Validation
    $("#editDialogForm").validate({
		rules:{
			moduleName:{
				required:true,
				minlength:2,
				maxlength:10
			},
			moduleLayer:{
				required:true,
				minlength:4,
				maxlength:4
			}
		},
		errorClass: "help-inline",
		errorElement: "span",
		highlight:function(element, errorClass, validClass) {
			$(element).parents('.control-group').addClass('error');
		},
		unhighlight: function(element, errorClass, validClass) {
			$(element).parents('.control-group').removeClass('error');
			$(element).parents('.control-group').addClass('success');
		}
	});

    var btnAdd = $("#btnAdd");		// 添加按钮
	var btnDelete = $(".btnDelete");// 删除按纽
	var btnEdit = $(".btnEdit");	// 编辑按纽

	// 添加
	btnAdd.live("click", function() {
		$("#moduleId").val("0");
		$("#moduleName").val("");
		$("#moduleDesc").val("");
		$("#moduleLayer").val("");
		$("#moduleUrl").val("");
		return true;
	});
	// 删除
	btnDelete.live("click", function() {
		$("#delModuleId").val($(this).attr("idVal"));
		
		return true;
	});
	// 修改
	btnEdit.live("click", function() {
		$.ajax({
			method : "GET",
			data : {adminId : $(this).attr("idVal")},
			url : _rootPath+"/manage/power/module/"+$(this).attr("idVal"),
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					//读入数据，并填入
					$("#moduleId").val($(data)[0].entity.id);
					$("#moduleName").val($(data)[0].entity.moduleName);	
					$("#moduleDesc").val($(data)[0].entity.moduleDesc);
					$("#moduleLayer").val($(data)[0].entity.moduleLayer);
					$("#moduleUrl").val($(data)[0].entity.moduleUrl);
				}
			}
		});
		return true;
	});
	
});

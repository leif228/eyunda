$(document).ready(function(){
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	$('select').select2();
	
	// Form Validation
    $("#editDialogForm").validate({
		rules:{
			roleName:{
				required:true,
				minlength:2,
				maxlength:10
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
	var btnModule = $(".btnModule"); // 选择模块权限按纽
	
	btnModule.live("click", function() {
	    $.ajax({
	      method : "GET",
	      data : {
	        adminId : $(this).attr("idVal")
	      },
	      url : _rootPath + "/manage/power/role/" + $(this).attr("idVal"),
	      datatype : "json",
	      success : function(data) {
	        var returnCode = $(data)[0].returnCode;
	        var message = $(data)[0].message;
	        if (returnCode == "Failure") {
	          alert(message);
	          return false;
	        } else {
	          //读入数据，并填入
	          //alert($(data)[0].entity.id);
	          $("#mRoleId").val($(data)[0].entity.id);
	          $("#mRoleName").val($(data)[0].entity.roleName);

	          var s = "";
	          s += "<div class=\"control-group\"><label class=\"control-label\">角色：</label><div class=\"controls\">";
	          $.each($(data)[0].entity.moduleDatas, function(i, moduleData) {
	            if (moduleData.theModule)
	              s += "<label><input type=\"checkbox\" id=\"module"+moduleData.id+"\" name=\"module\" value=\""+moduleData.id+"\" checked /> "+moduleData.moduleName+"</label>";
	            else
	              s += "<label><input type=\"checkbox\" id=\"module"+moduleData.id+"\" name=\"module\" value=\""+moduleData.id+"\" /> "+moduleData.moduleName+"</label>";
	          });
	          s += "</div></div>";
	          
	          $("#moduleSel").html(s);
	        }
	      }
	    });
	    return true;
	});
	
	// 添加
	$(document).on("click", "#btnAdd", function() {
	//btnAdd.live("click", function() {
		$("#roleId").val("0");
		$("#roleName").val("");
		$("#roleDesc").val("");
		
		return true;
	});
	// 删除
	btnDelete.live("click", function() {
		$("#delRoleId").val($(this).attr("idVal"));
		
		return true;
	});
	// 修改
	btnEdit.live("click", function() {
		$.ajax({
			method : "GET",
			data : {adminId : $(this).attr("idVal")},
			url : _rootPath+"/manage/power/role/"+$(this).attr("idVal"),
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					//读入数据，并填入
					$("#roleId").val($(data)[0].entity.id);
					$("#roleName").val($(data)[0].entity.roleName);	
					$("#roleDesc").val($(data)[0].entity.roleDesc);
				}
			}
		});
		return true;
	});
	
});

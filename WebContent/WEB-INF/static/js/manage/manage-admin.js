$(document).ready(function(){
	
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	$('select').select2();
	
	// Form Validation
    $("#dlgEditForm").validate({
		rules:{
			loginName:{
				required:true
			},
			trueName:{
				required:true
			},
			nickName:{
				required:true
			},
			email:{
				required:true,
				email: true
			},
			mobile:{
				required:true,
				minlength:11,
				maxlength:11
			},
			password:{
				required: true,
				minlength:6,
				maxlength:20
			},
			password2:{
				required:true,
				minlength:6,
				maxlength:20,
				equalTo:"#password"
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
	var btnRole = $(".btnRole"); // 选择角色按纽

	// 角色
	btnRole.live("click", function() {
	    $.ajax({
	      method : "GET",
	      data : {
	        adminId : $(this).attr("idVal")
	      },
	      url : _rootPath+"/manage/power/admin/"+$(this).attr("idVal"),
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
	          $("#rAdminId").val($(data)[0].entity.id);
	          $("#rAdminName").val($(data)[0].entity.trueName);

	          var s = "";
	          s += "<div class=\"control-group\"><label class=\"control-label\">角色：</label><div class=\"controls\">";
	          $.each($(data)[0].entity.roleDatas, function(i, roleData) {
	            if (roleData.theRole)
	              s += "<label><input type=\"checkbox\" id=\"role"+roleData.id+"\" name=\"role\" value=\""+roleData.id+"\" checked /> "+roleData.roleName+"</label>";
	            else
	              s += "<label><input type=\"checkbox\" id=\"role"+roleData.id+"\" name=\"role\" value=\""+roleData.id+"\" /> "+roleData.roleName+"</label>";
	          });
	          s += "</div></div>";
	          
	          $("#roleSel").html(s);
	          
	          $("#roleDialog").modal("show");
	        }
	      }
	    });
	    return true;
	});

    // 添加
	btnAdd.live("click", function() {
		$("#adminId").val("0");
		$("#loginName").val("");
		$("#trueName").val("");
		$("#nickName").val("");
		$("#email").val("");
		$("#mobile").val("");
		$("#password").val("");
		$("#password2").val("");
		
		$("#dlgEdit").modal("show");
		
		return true;
	});
	// 删除
	btnDelete.live("click", function() {
		$("#delAdminId").val($(this).attr("idVal"));
		
		$("#deleteDialog").modal("show");
		
		return true;
	});
	// 修改
	btnEdit.live("click", function() {
		$.ajax({
			method : "GET",
			data : {adminId : $(this).attr("idVal")},
			url : _rootPath+"/manage/power/admin/"+$(this).attr("idVal"),
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					//读入数据，并填入
					$("#adminId").val($(data)[0].entity.id);
					$("#loginName").val($(data)[0].entity.loginName);	
					$("#trueName").val($(data)[0].entity.trueName);
					$("#nickName").val($(data)[0].entity.nickName);
					$("#email").val($(data)[0].entity.email);
					$("#mobile").val($(data)[0].entity.mobile);
					$("#password").val("");	// 密码已加密，显示没意义
					$("#password2").val("");// 密码已加密，显示没意义
					
					$("#dlgEdit").modal("show");
				}
			}
		});
		return true;
	});

});

$(document).ready(function(){
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	$('select').select2();
	
	// 设置船员动态上报船舶
	$(".btnSailorReport").click(function() {
		$.ajax({
			method : "GET",
			data : {deptId : $(this).attr("dept_id"), userId : $(this).attr("user_id")},
			url : _rootPath+"/space/contact/myContact/sailorReportInit",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					$("#rptDeptId").val($(data)[0].content.deptId);
					$("#rptUserId").val($(data)[0].content.userId);
					$("#rptTrueName").html($(data)[0].content.trueName);

					var s = "";
					s += "<select name=\"mmsi\" style=\"width: 300px; z-index: 99999 !important;\">";
					$.each($(data)[0].content.deptShipDatas,function(i, dsd) { 
						s += "<optgroup label=\"" + dsd.departmentData.deptName + "\">";
						$.each(dsd.shipDatas,function(j, shipData) {
							if ($(data)[0].content.mmsi == shipData.mmsi)
								s += "<option value=\"" + shipData.mmsi + "\" selected>" + shipData.shipName + "(" + shipData.mmsi + ")" + "</option>";
							else
								s += "<option value=\"" + shipData.mmsi + "\">" + shipData.shipName + "(" + shipData.mmsi + ")" + "</option>";
						});
						s += "</optgroup>";
					});
					s += "</select> <span class=\"color-red\"></span>";
					$("#rptMmsi").html("");
					$("#rptMmsi").html(s);
					$('select').select2();
					
					$("#sailorReportDialog").modal("show");
				}
			}
		});
		return true;
    });

	// 设置船员动态上报船舶保存
    $("#sailorReportSaveButton").click(function() {
        $("#sailorReportForm").ajaxSubmit({
            method: "post",
            url: _rootPath + "/space/contact/myContact/sailorReportSave",
            datatype: "json",
            success: function(data) {
                // var redata = eval('(' + data + ')');
                var returnCode = data.returnCode;
                var message = data.message;
                if (returnCode == "Failure") {
                    alert(message);
                    return false;
                } else {
                	$('#sailorReportDialog').modal('hide');
					window.location.href = _rootPath + "/space/contact/myContact?deptId="+$("#deptId").val()+"&keyWords="+$("#keyWords").val()+"&pageNo="+$("#pageNo").val();
                    return true;
                }
            }
        });
    });

	// 删除
	$(".btnDeleteMember").live("click", function() {
		$("#delContactId").val($(this).attr("idVal"));
		
		$("#deleteMemberDialog").modal("show");
		return true;
	});
    $(".deleteMemberButton").click(function() {
    	$.ajax({
			method : "GET",
			data : {deptId : $("#delDeptId").val(), userId : $("#delContactId").val()},
			url : _rootPath+"/space/contact/myContact/deleteContact",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					window.location.href = _rootPath + "/space/contact/myContact?deptId="+$("#deptId").val()+"&keyWords="+$("#keyWords").val()+"&pageNo="+$("#pageNo").val();
                    return true;
				}
			}
		});
		return true;
	});
 
    // 查找添加子帐号
    $(".btnAddMember").click(function() {
    	$("#addMemberDialog").modal("show");
    });
    
    // 自动产生选择项
    $("#addMemberMobile").autocomplete({
		source:function(query, process)
		{
			$("#addMemberMobile").attr("real-value","");
			$.ajax({
				type: 'GET',
				dataType: 'JSON',
				async: true,
				data: 'keyWords=' + query ,
				url: _rootPath + '/space/contact/myContact/find',
				success: function(data){
					if(data.returnCode="Success"){
						var res = data.contacts;
						return process(data.contacts);
					}
				}
			});
		},formatItem:function(item){
			return item["trueName"]+":"+item["mobile"];
	    },setValue:function(item){
	        return {'data-value':item["trueName"],'real-value':item["id"]};
	    }
	});

    // add子帐号信息
    $("#addMemberButton").click(function() {
        	var trueName = $('#addMemberMobile').val();
            if (!trueName) {
                alert("请选择正确的用户!");
                return false;
            }
            var trueMemberMobile = $("#addMemberMobile").attr("real-value");
            if(!trueMemberMobile){
            	alert("请选择正确的用户!");
                return false;
            }
            $("#trueMemberMobile").attr("value",trueMemberMobile);
            $("#addMemberForm").ajaxSubmit({
                method: "POST",
                url: _rootPath + "/space/contact/myContact/addContact",
                datatype: "json",
                success: function(data) {
                    var redata = eval('(' + data + ')');
                    var returnCode = redata.returnCode;
                    var message = redata.message;
                    if (returnCode == "Failure") {
                        alert(message);
                        return false;
                    } else {
                    	$('#addMemberDialog').modal('hide');
    					window.location.href = _rootPath + "/space/contact/myContact?deptId="+$("#deptId").val()+"&keyWords="+$("#keyWords").val()+"&pageNo="+$("#pageNo").val();
                        return true;
                    }
                }
            });
    });

    // 以下是添加部门代码
    $("#addDepartmentForm").validate({
  	    rules:{
  	      deptName:{
  	    	required:true,
  	        minlength:2,
  	        maxlength:20
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

    // 添加部门
	$(".btnAddDepartment").click(function() {
		$.ajax({
			method : "GET",
			data : {},
			url : _rootPath+"/space/contact/department/addDepartment",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					$("#addDeptId").val(0);
					$("#addDeptName").val("");
					$("#addDepartmentDialog").modal("show");
				}
			}
		});
		return true;
    });

	// 新建部门
	$(".btnNewDepartment").click(function() {
		$("#addDeptId").val(0);
		$("#addDeptName").val("");
    });

	// 修改部门
	$(".btnEditDepartment").click(function() {
		$("#addDeptId").val($(this).attr("deptId"));
		$("#addDeptName").val($(this).attr("deptName"));
		$("#addDeptType").val($(this).attr("deptType")).trigger('change');
		
    });

	//保存部门
	$(".btnSaveDepartment").click(function() {
		if ($("#addDepartmentForm").valid()){
		$("#addDepartmentForm").ajaxSubmit({
			method : "POST",
			url : _rootPath+"/space/contact/department/saveDepartment",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					alert(message);
					location.reload();
					return true;
				}
			}
		});
		}
		return true;
	});

	// 删除部门
	$(".btnDeleteDepartment").click(function() {
		var deptId = $(this).attr("idVal");
		$.ajax({
			method : "GET",
			data : {deptId:deptId},
			url : _rootPath+"/space/contact/department/deleteDepartment",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					alert(message);
					location.reload();
					return true;
				}
			}
		});
		return true;
    });

});

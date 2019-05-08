$(document).ready(function() {
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();

	$('select').select2();

	// Form Validation
	$("#editDialogForm").validate({
		rules : {
			title : {
				required : true,
				minlength : 2,
				maxlength : 25
			}
		},
		errorClass : "help-inline",
		errorElement : "span",
		highlight : function(element, errorClass, validClass) {
			$(element).parents('.control-group').addClass('error');
		},
		unhighlight : function(element, errorClass, validClass) {
			$(element).parents('.control-group').removeClass('error');
			$(element).parents('.control-group').addClass('success');
		}
	});

	// 添加对话框
	$("#orderType").live("change", function() {
		$("#title").val($("#orderType").find("option:selected").text());
	});

	// 添加
	$("#btnAdd").live("click", function() {
		$("#orderTemplateId").val("0");
		$("#orderType").select2("val", "");
		$("#operatorId").val("0");
		$("#editor").html('<textarea id="editor_id" name="orderContent" style="width:700px;height:300px;"></textarea>');
		KindEditor.ready(function(K) {
			window.editor = K.create('#editor_id');
		});
		$("#editDialog").modal("show");

		return true;
	});

	// 修改
	$(".btnEdit").live("click",function() {
		$.ajax({
			method : "GET",
			url : _rootPath + "/manage/order/orderTemplate/" + $(this).attr("idVal"),
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					var orderTemplateData = $(data)[0].orderTemplateData
					$("#orderTemplateId").val(orderTemplateData.id);
					$("#operatorId").val(orderTemplateData.operatorId);
					$("#orderType").select2("val", orderTemplateData.orderType);
					$("#title").val(orderTemplateData.title);
					$("#editor").html('<textarea id="editor_id" name="orderContent" style="width:700px;height:300px;">'+ orderTemplateData.orderContent +'</textarea>');
					KindEditor.ready(function(K) {
						window.editor = K.create('#editor_id');
					});
					
					$("#editDialog").modal("show");
				}
			}
		});
		return true;
	});
	
	// 提交保存
	$(".saveOrderTemplate").click(function() {
		editor.sync();
        if($("#editDialogForm").valid()){
            $("#editDialogForm").ajaxSubmit({
                type : "POST",
                url : _rootPath + "/manage/order/orderTemplate/save",
                datatype : "json",
                success : function(data) {
                	var redata = eval('(' + data + ')');
                    var returnCode = redata.returnCode;
                    var message = redata.message;
                    if (returnCode == "Failure") {
                        alert(message);
                        return false;
                    } else {
                        alert(message);
                        window.location.href = _rootPath+"/manage/order/orderTemplate";
                        return false;
                    }
                }
            });
        }
    });

	// 删除
	$(".btnDelete").live("click", function() {
		$("#delId").val($(this).attr("idVal"));

		$("#deleteDialog").modal("show");

		return true;
	});
	
	// 确认删除
	$(".deleteOrderTemplate").click(function() {
        $("#deleteDialogForm").ajaxSubmit({
            type : "POST",
            url : _rootPath + "/manage/order/orderTemplate/delete",
            datatype : "json",
            success : function(data) {
            	var redata = eval('(' + data + ')');
                var returnCode = redata.returnCode;
                var message = redata.message;
                if (returnCode == "Failure") {
                    alert(message);
                    return false;
                } else {
                    alert(message);
                    window.location.href = _rootPath+"/manage/order/orderTemplate";
                    return false;
                }
            }
        });
    });

	// 发布
	$(".btnRelease").live("click", function() {
		$("#pubId").val($(this).attr("idVal"));

		$("#publishDialog").modal("show");

		return true;
	});
	
	// 确认发布
	$(".publishOrderTemplate").click(function() {
        $("#publishDialogForm").ajaxSubmit({
            type : "POST",
            url : _rootPath + "/manage/order/orderTemplate/publishOrderTemplate",
            datatype : "json",
            success : function(data) {
            	var redata = eval('(' + data + ')');
                var returnCode = redata.returnCode;
                var message = redata.message;
                if (returnCode == "Failure") {
                    alert(message);
                    return false;
                } else {
                    alert(message);
                    window.location.href = _rootPath+"/manage/order/orderTemplate";
                    return false;
                }
            }
        });
    });

	// 取消发布
	$(".btnUnRelease").live("click", function() {
		$("#unpubId").val($(this).attr("idVal"));

		$("#unpublishDialog").modal("show");

		return true;
	});

	//确认取消发布
	$(".unpublishOrderTemplate").click(function() {
        $("#unpublishDialogForm").ajaxSubmit({
            type : "POST",
            url : _rootPath + "/manage/order/orderTemplate/unpublishOrderTemplate",
            datatype : "json",
            success : function(data) {
            	var redata = eval('(' + data + ')');
                var returnCode = redata.returnCode;
                var message = redata.message;
                if (returnCode == "Failure") {
                    alert(message);
                    return false;
                } else {
                    alert(message);
                    window.location.href = _rootPath+"/manage/order/orderTemplate";
                    return false;
                }
            }
        });
    });
	
	$(".preview").click(function() {
		var orderType = $(this).attr("idVal");
		window.open(_rootPath + "/space/orderCommon/showContainer?orderId=0&orderType=" + orderType); 
	});
	
});

$(document).ready(function(){
	
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();

	// Form Validation
    $("#editDialogForm").validate({
		rules:{
			typeName:{
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
    
    // 添加
	$("#btnAdd").live("click", function() {
		$.ajax({
			method : "GET",
			data : {para : 1},
			url : _rootPath+"/manage/ship/type/add",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					//读入数据，并填入
					$("#typeCode").val("");
					$("#typeName").val("");

			        var s = "";
			        s += "<div class=\"control-group\">";
			        s += "  <label class=\"control-label\">上级类别名称：</label>";
			        s += "  <div class=\"controls\">";
			        s += "    <select id=\"prtTypeCode\" name=\"prtTypeCode\" style=\"width:280px;\">";
			        s += "      <option value=\"\" selected> [根类别]</option>";

			        var v = $(data)[0].uncleDatas;
			        var b = true;
			        if (null === v || v.length === 0)
			        	b = false;
			        
			        if (b){
			          $.each($(data)[0].uncleDatas, function(i, uncleData) {
			            s += "      <option value=\""+uncleData.typeCode+"\"> "+uncleData.typeName+"</option>";
			          });
			        }

			        s += "    </select>";
			        s += "  </div>";
			        s += "</div>";

			        $("#selContent").html(s);
			        
			        $("#editDialog").modal("show");
				}
			}
		});
		return true;
	});

    // 修改
	$(".btnEdit").live("click", function() {
		$.ajax({
			method : "GET",
			data : {typeCode : $(this).attr("idVal")},
			url : _rootPath+"/manage/ship/type/edit",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					//读入数据，并填入
					$("#typeCode").val($(data)[0].typeData.typeCode);
					$("#typeName").val($(data)[0].typeData.typeName);

			        var s = "";
			        s += "<div class=\"control-group\">";
			        s += "  <label class=\"control-label\">上级类别名称：</label>";
			        s += "  <div class=\"controls\">";
			        s += "    <select id=\"prtTypeCode\" name=\"prtTypeCode\" style=\"width:280px;\">";
			        s += "      <option value=\"\" selected> [根类别]</option>";

			        var typeData = $(data)[0].typeData;
			        var v = $(data)[0].uncleDatas;
			        var b = true;
			        if (null === v || v.length === 0)
			        	b = false;
			        
			        if (b){
			          $.each(v, function(i, uncleData) {
			          if (typeData.parent.typeCode==uncleData.typeCode)
			            s += "      <option value=\""+uncleData.typeCode+"\" selected> "+uncleData.typeName+"</option>";
			          else
			            s += "      <option value=\""+uncleData.typeCode+"\"> "+uncleData.typeName+"</option>";
			          });
			        }

			        s += "    </select>";
			        s += "  </div>";
			        s += "</div>";

			        $("#selContent").html(s);
			        
			        $("#editDialog").modal("show");
				}
			}
		});
		return true;
	});
	
	// 删除
	$(".btnDelete").live("click", function() {
		$("#delTypeCode").val($(this).attr("idVal"));
		
		$("#deleteDialog").modal("show");
		
		return true;
	});
	
});

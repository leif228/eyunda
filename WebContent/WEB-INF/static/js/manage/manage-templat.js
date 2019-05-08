$(document).ready(function(){
	
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	$('select').select2();
	
	// Form Validation
  $("#editDialogForm").validate({
		rules:{
			title:{
				required:true,
				minlength:2,
				maxlength:25
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
			url : _rootPath+"/manage/order/template/add",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					//读入数据，并填入
			        var s = "";

			        s += "		<div class=\"modal-body\">";
			        s += "			<fieldset>";
			        s += "";
			        s += "				<div class=\"control-group\">";
			        s += "					<label class=\"control-label\">类别：</label>";
			        s += "					<div class=\"controls\">";
			        s += "						<input type=\"hidden\" name=\"id\" id=\"templateId\" value=\"0\" />";
			        s += "						<select id=\"typeCode\" name=\"typeCode\" style=\"width: 280px;\">";
			        
			        $.each($(data)[0].uncleDatas, function(i, uncleData) {
				        s += "							<optgroup label=\""+uncleData.typeName+"\">";
				        s += "								<option value=\"0\">批次运输合同模板</option>";
				        s += "								<option value=\"1\">期租租船合同模板</option>";
				        $.each(uncleData.childrenDatas, function(i, childData) {
				        s += "								<option value=\""+childData.typeCode+"\">"+childData.typeName+"</option>";
				        });
				        s += "							</optgroup>";
			        });

			        s += "						</select>";
			        s += "					</div>";
			        s += "				</div>";
			        s += "";
			        s += "				<div class=\"control-group\">";
			        s += "					<label class=\"control-label\">模板标题：</label>";
			        s += "					<div class=\"controls\">";
			        s += "						<input type=\"text\" name=\"title\" id=\"title\" value=\"\" />";
			        s += "					</div>";
			        s += "				</div>";
			        s += "";
			        s += "				<div class=\"control-group\">";
			        s += "					<label class=\"control-label\">模板文件上传：</label>";
			        s += "					<div class=\"controls\">";
			        s += "						<input type=\"file\" id=\"templateFile\" name=\"templateFile\" value=\"\" />";
			        s += "					</div>";
			        s += "				</div>";
			        s += "";
			        s += "			</fieldset>";
			        s += "		</div>";

			        $("#editDlgBody").html(s);
			        
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

			data : {para : 1},
			url : _rootPath+"/manage/order/template/"+$(this).attr("idVal"),
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					//读入数据，并填入
			        var s = "";

			        s += "		<div class=\"modal-body\">";
			        s += "			<fieldset>";
			        s += "";
			        s += "				<div class=\"control-group\">";
			        s += "					<label class=\"control-label\">类别：</label>";
			        s += "					<div class=\"controls\">";
			        s += "						<input type=\"hidden\" name=\"id\" id=\"templateId\" value=\""+$(data)[0].tempData.id+"\" />";
			        s += "						<select id=\"typeCode\" name=\"typeCode\" style=\"width: 280px;\">";
			        
			        $.each($(data)[0].uncleDatas, function(i, uncleData) {
				        s += "							<optgroup label=\""+uncleData.typeName+"\">";
				        s += "								<option value=\"0\">批次运输合同模板</option>";
				        s += "								<option value=\"1\">期租租船合同模板</option>";
				        $.each(uncleData.childrenDatas, function(i, childData) {
				        if (childData.typeCode==$(data)[0].tempData.typeCode){
				        	s += "								<option value=\""+childData.typeCode+"\" selected >"+childData.typeName+"</option>";
				        } else {
					        s += "								<option value=\""+childData.typeCode+"\">"+childData.typeName+"</option>";
				        }
				        });
				        s += "							</optgroup>";
			        });

			        s += "						</select>";
			        s += "					</div>";
			        s += "				</div>";
			        s += "";
			        s += "				<div class=\"control-group\">";
			        s += "					<label class=\"control-label\">模板标题：</label>";
			        s += "					<div class=\"controls\">";
			        s += "						<input type=\"text\" name=\"title\" id=\"title\" value=\""+$(data)[0].tempData.title+"\" />";
			        s += "					</div>";
			        s += "				</div>";
			        s += "";
			        s += "				<div class=\"control-group\">";
			        s += "					<label class=\"control-label\">模板文件上传：</label>";
			        s += "					<div class=\"controls\">";
			        s += "						<input type=\"file\" id=\"templateFile\" name=\"templateFile\" value=\"\" />";
			        s += "					</div>";
			        s += "				</div>";
			        s += "";
			        s += "			</fieldset>";
			        s += "		</div>";

			        $("#editDlgBody").html(s);
			        
			        $("#editDialog").modal("show");
				}
			}
		});
		return true;
	});

	// 删除
	$(".btnDelete").live("click", function() {
		$("#delId").val($(this).attr("idVal"));
		
		$("#deleteDialog").modal("show");
		
		return true;
	});

	// 发布
	$(".btnRelease").live("click", function() {
		$("#pubId").val($(this).attr("idVal"));
		
		$("#publishDialog").modal("show");
		
		return true;
	});

	// 取消发布
	$(".btnUnRelease").live("click", function() {
		$("#unpubId").val($(this).attr("idVal"));
		
		$("#unpublishDialog").modal("show");
		
		return true;
	});

});

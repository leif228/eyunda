$(document).ready(function(){
	
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();

	$('select').select2();
	
	// Form Validation
    $("#editDialogForm").validate({
		rules:{
			attrName:{
				required:true
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
			url : _rootPath+"/manage/ship/attribute/add",
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
			        s += "						<input type=\"hidden\" name=\"id\" id=\"attributeId\" value=\"0\" />";
					s += "						<select id=\"typeCode\" name=\"typeCode\" style=\"width: 280px;\">";

			        $.each($(data)[0].uncleDatas, function(i, uncleData) {
				        s += "							<optgroup label=\""+uncleData.typeName+"\">";
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
					s += "					<label class=\"control-label\">属性名称：</label>";
					s += "					<div class=\"controls\">";
					s += "						<input type=\"text\" name=\"attrName\" id=\"attrName\" value=\"\" />";
					s += "					</div>";
					s += "				</div>";
					s += "";
					s += "				<div class=\"control-group\">";
					s += "					<label class=\"control-label\">属性类型：</label>";
					s += "					<div class=\"controls\">";
					s += "						<select id=\"attrTypeCode\" name=\"attrTypeCode\"";
					s += "							style=\"width: 280px;\">";

					$.each($(data)[0].attrTypeCodes, function(i, attrTypeCode) {
					s += "							<option value=\""+attrTypeCode.code+"\" selected> "+attrTypeCode.description+"</option>";
					});

					s += "						</select>";
					s += "					</div>";
					s += "				</div>";
					s += "";
					s += "				<div class=\"control-group\">";
					s += "					<label class=\"control-label\">属性取值：</label>";
					s += "					<div class=\"controls\">";
					s += "						<textarea id=\"attrValues\" cols=\"30\" rows=\"5\"";
					s += "							name=\"attrValues\" placeholder=\"多个属性值之间用'|'分隔\"></textarea>";
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

	
	// 按属性分类
	$("#typeCode").change(function() {
		window.location.href=_rootPath+"/manage/ship/attribute?typeCode="+$(this).val();
	});
	
    // 修改
	$(".btnEdit").live("click", function() {
		$.ajax({
			method : "GET",
			data : {id : $(this).attr("idVal")},
			url : _rootPath+"/manage/ship/attribute/edit",
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
			        s += "						<input type=\"hidden\" name=\"id\" id=\"attributeId\" value=\""+$(data)[0].attrNameData.id+"\" />";
					s += "						<select id=\"typeCode\" name=\"typeCode\" style=\"width: 280px;\">";

			        $.each($(data)[0].uncleDatas, function(i, uncleData) {
				        s += "							<optgroup label=\""+uncleData.typeName+"\">";
				        $.each(uncleData.childrenDatas, function(i, childData) {
							if ($(data)[0].attrNameData.typeData.typeCode==childData.typeCode)
						        s += "								<option value=\""+childData.typeCode+"\" selected>"+childData.typeName+"</option>";
							else
						        s += "								<option value=\""+childData.typeCode+"\">"+childData.typeName+"</option>";
				        });
				        s += "							</optgroup>";
			        });
					
					s += "						</select>";
					s += "					</div>";
					s += "				</div>";
					s += "";
					s += "				<div class=\"control-group\">";
					s += "					<label class=\"control-label\">属性名称：</label>";
					s += "					<div class=\"controls\">";
					s += "						<input type=\"text\" name=\"attrName\" id=\"attrName\" value=\""+$(data)[0].attrNameData.attrName+"\" />";
					s += "					</div>";
					s += "				</div>";
					s += "";
					s += "				<div class=\"control-group\">";
					s += "					<label class=\"control-label\">属性类型：</label>";
					s += "					<div class=\"controls\">";
					s += "						<select id=\"attrTypeCode\" name=\"attrTypeCode\"";
					s += "							style=\"width: 280px;\">";

					$.each($(data)[0].attrTypeCodes, function(i, attrTypeCode) {
						if ($(data)[0].attrNameData.attrType==attrTypeCode.code)
							s += "							<option value=\""+attrTypeCode.code+"\" selected> "+attrTypeCode.description+"</option>";
						else
							s += "							<option value=\""+attrTypeCode.code+"\"> "+attrTypeCode.description+"</option>";
					});

					s += "						</select>";
					s += "					</div>";
					s += "				</div>";
					s += "";
					s += "				<div class=\"control-group\">";
					s += "					<label class=\"control-label\">属性取值：</label>";
					s += "					<div class=\"controls\">";
					s += "						<textarea id=\"attrValues\" cols=\"30\" rows=\"5\"";
					s += "							name=\"attrValues\" placeholder=\"多个属性值之间用'|'分隔\">"+$(data)[0].attrNameData.attrValues+"</textarea>";
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
	
});

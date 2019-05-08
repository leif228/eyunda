$(document).ready(function(){
    
    $('input[type=radio],input[type=file]').uniform();

    // Form Validation
    $("#frmSave").validate({
        rules:{
            compName:{
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
    
    // 添加
    $("#btnAdd").live("click", function() {
        $("#id").val(0);
        $("#compName").val("");

        $("#compLogoImage").attr("src", "");
        $("#compLicenceImage").attr("src", "");

        $("#compLogoMpf").val("");
        $("#compLicenceMpf").val("");

        $("#certify").attr("checked", "");

    	$("#editDialog").modal("show");

        return true;
    });

    // 修改
    $(".btnEdit").live("click", function() {
        $.ajax({
            method : "GET",
            data : {compCertId : $(this).attr("idVal")},
            url : _rootPath+"/manage/member/compCert/edit",
            datatype : "json",
            success : function(data) {
                var returnCode = $(data)[0].returnCode;
                var message = $(data)[0].message;
                if (returnCode == "Failure") {
                    alert(message);
                    return false;
                } else {
                    //读入数据，并填入
                    $("#id").val($(data)[0].compCertData.id);
                    $("#compName").val($(data)[0].compCertData.compName);

                    $("#compLogoImage").attr("src", _rootPath + "/download/imageDownload?url=" + $(data)[0].compCertData.compLogo);
                    $("#compLicenceImage").attr("src", _rootPath + "/download/imageDownload?url=" + $(data)[0].compCertData.compLicence);

                    $("#compLogoMpf").val("");
                    $("#compLicenceMpf").val("");

                    if ($(data)[0].compCertData.certify == 'yes') {
                      $("#certify").attr("checked", "checked");
                    } else {
                      $("#certify").removeAttr("checked");
                    }

                    $("#editDialog").modal("show");
                    
                    return true;
                }
            }
        });
        
    });
    
    // 删除
    $(".btnDel").live("click", function() {
        $("#compCertId").val($(this).attr("idVal"));
        
        $("#deleteDialog").modal("show");
        
        return true;
    });
    

    // 保存
    $(".btnSave").live("click", function() {
    	if($("#frmSave").valid())
			$("#frmSave").ajaxSubmit({
				method : "POST",
				url : _rootPath+"/manage/member/compCert/save",
				datatype : "json",
				success : function(data) {
					var redata = eval('('+data+')');
					var returnCode = redata.returnCode;
					var message = redata.message;
					if (returnCode == "Failure") {
					  alert(message);
					  return false;
					} else {
					  // alert(message);
		              var params = "?nonsense＝0";
		              var t = $("#frmSave").serializeArray();
		              $.each(t, function() {
		                params+="&"+this.name+"="+this.value;
		              });
		              window.location.href = _rootPath + "/manage/member/compCert/list" + params;
					  return false;
					}
				}
			});
    });

    // 删除标签信息
    $(".btnDelete").live("click", function() {
        $.ajax({
          method : "GET",
          data : $("#frmDelete").formSerialize(),
          url : _rootPath+"/manage/member/compCert/delete",
          datatype : "json",
          success : function(data) {
            var returnCode = $(data)[0].returnCode;
            var message = $(data)[0].message;
            if (returnCode == "Failure") {
              alert(message);
              return false;
            } else {
              alert(message);
              var params = "?nonsense＝0";
              var t = $("#frmDelete").serializeArray();
              $.each(t, function() {
                params+="&"+this.name+"="+this.value;
              });
              window.location.href = _rootPath + "/manage/member/compCert/list" + params;
            }
          }
        });
    });

});

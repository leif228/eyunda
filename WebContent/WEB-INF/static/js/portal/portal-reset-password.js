$(document).ready(function() {
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();

	$('#resetPasswordForm').validate({
		rules:{
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
	
	//重置密码
	$(".resetPassword").live("click", function() {
		if ($("#resetPasswordForm").valid())
		$("#resetPasswordForm").ajaxSubmit({
	      method : "POST",
	      url : _rootPath+"/portal/login/changePasswd",
	      datatype : "json",
	      success : function(data) {
    	  var returnCode = $(data)[0].returnCode;
          var message = $(data)[0].message;
	        if (returnCode == "Failure") {
	          alert(message);
	          return false;
	        } else {
	          alert(message);
	          window.location.href = _rootPath+"/portal/login/login";
	          return true;
	        }
	      }
	    });
	  });
	
});

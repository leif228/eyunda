$(document).ready(function(){
		$("#callform").validate({
			rules:{
				name:{
					required:true,
					minlength:1,
					maxlength:20
				},
				email:{
					required:true,
					email: true
				},
				content:{
					required:true,
					minlength:4,
					maxlength:4000
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
		$(".sendform").live("click", function() {
			if ($("#callform").valid()){
				$("#callform").ajaxSubmit({
					method : "POST",
					url : _rootPath + "/portal/site/callform",
					datatype : "json",
					success : function(data) {
						var returnCode = $(data)[0].returnCode;
						var message = $(data)[0].message;
						if (returnCode == "Failure") {
							 alert(message); 
							return false;
						} else {
							$("#name").val("");
							$("#email").val("");
							$("#content").val("");
							alert(message); 
							return true;
						}
					}
				});
			}
		});
	});
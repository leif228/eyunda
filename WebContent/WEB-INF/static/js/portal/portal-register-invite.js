$(document).ready(
		function() {
			jQuery.validator.addMethod("isMobile", function(value, element) {
			    var length = value.length;
			    var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
			    return this.optional(element) || (length == 11 && mobile.test(value));
			}, "请正确填写您的手机号码");
			
			
			   $(".btn_register").on("click",
					    function() {
					        if ($("#sign-up").valid()) {
								if (!$("#agree").prop("checked")) {
									$('.agree').show().toggleClass("red");
									return false;
								} else {
									$('.agree').hide();
								}
								
					            $("#page").showLoading();
					            $("#sign-up").ajaxSubmit({
					                method: "POST",
					                url: _rootPath + "/portal/login/inviteRegister",
					                datatype: "json",
					                success: function(data) {
					                	$("#page").hideLoading();
					                    var redata = eval('(' + data + ')');
					                    var returnCode = redata.returnCode;
					                    var message = redata.message;
					                    if (returnCode == "Failure") {
					                        alert(message);
					                        return false;
					                    } else {
					                        window.location.href = _rootPath + "/portal/login/inviteRegisterDownload?loginName="+message;
					                        return true;
					                    }
					                }
					            });
					        }
					    });
			

			$('#sign-up').validate({
				rules : {
					mobile : {
						required : true,
						minlength : 11,
		            	isMobile : true
					},
					trueName : {
						required : true,
						minlength : 2,
						maxlength : 20
					},
					email : {
						required : true,
						email : true
					},
					password : {
						required : true,
						minlength : 6,
						maxlength : 20
					},
					password2 : {
						required : true,
						minlength : 6,
						maxlength : 20,
						equalTo : "#password"
					},
					captcha : {
						required : true
					}
				},
				errorClass : "help-inline",
				errorElement : "span",
				highlight : function(element, errorClass, validClass) {
					$(element).parents('.form-group').addClass('has-error');
				},
				unhighlight : function(element, errorClass, validClass) {
					$(element).parents('.form-group').removeClass('has-error');
					$(element).parents('.form-group').addClass('has-success');
				}
			});

		
		});

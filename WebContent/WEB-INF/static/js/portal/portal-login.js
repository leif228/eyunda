$(document).ready(function() {
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	function encodeps(){
		var old = $.trim($("#password").val());
		var nwe = old.split("").reverse().join("");
		$("#password").val(nwe);
	}
	$('#sign-in').validate({
		rules : {
			loginName : {
				required : true
			},
			password : {
				required : true
			},
			captcha : {
				required : true
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
	$('#sign-in').submit(function() {
		// 同步captcha验证
		var ispass = false;
		encodeps();
		if ($("#captcha").val().length > 0) {
			$.ajax({
				method : "GET",
				data : {
					"captcha" : $("#captcha").val()
				},
				url : _rootPath + "/check",
				async : false,
				datatype : "json",
				success : function(data) {
					var returnCode = $(data)[0].returnCode;
					if (returnCode == "Failure") {
						$("#captcha").focus().val("");
						alert("验证码输入错误!");
						ispass = false;
						return false;
					} else {
						ispass = true;
						return true;
					}
				}
			});
			if(ispass)
				return true;
			else
				return false;
			
		}
	});

	// 点击找回密码按钮
	$(".findPasswd").click(function() {
		$("#email").val("");
		$("#findpwdCaptcha").val("");
		$("#emailDialog").modal("show");

		return true;
	});

	// 发送邮件
	$(".sendEmail").live("click", function() {
		$("#findPswdEmailForm").ajaxSubmit({
			method : "POST",
			url : _rootPath + "/portal/login/sendEmail",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					alert(message);
					$("#emailDialog").modal("hide");
					return true;
				}
			}
		});
	});
	/*
	 * $("#captcha").blur(function(){ if($("#captcha").val().length>0) $.ajax({
	 * method : "GET", data : {"captcha":$("#captcha").val()}, url :
	 * _rootPath+"/check", datatype : "json", success : function(data) { var
	 * returnCode = $(data)[0].returnCode; if (returnCode == "Failure") {
	 * $("#captcha").focus().val(""); alert("验证码输入错误!"); return false; } else {
	 * return true; } } }); });
	 */
	//定义定时器, 定时轮询登录状态
	var qLogin;
	function getLoginResult(){
		$.ajax({
			method : "GET",
			url : _rootPath + "/portal/login/verifyQcodeLogin",
			data:{'uid':$("#uid").val()},
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					//alert(message);
					return false;
				} else {
					//showProgressBar
					window.location.href=_rootPath +"/portal/login/autoLogin?code="+$("#uid").val();
				}
			}
		});
	}

	//扫描二维码登录
	$("#btnQcode").on("click",function(){
		$("#qcodeLogin").show();
		$("#btnQcode").removeClass();
		$("#btnQcode").addClass("selected");
		$("#normalLogin").hide();
		$("#btnUNameLogin").removeClass();
		$("#btnUNameLogin").addClass("default");
		//getQcodeRes();
		qLogin=window.setInterval(getLoginResult,1000); 
	});
	//正常登录
	$("#btnUNameLogin").on("click",function(){
		$("#qcodeLogin").hide();
		$("#btnUNameLogin").removeClass();
		$("#btnUNameLogin").addClass("selected");
		$("#normalLogin").show();
		$("#btnQcode").removeClass();
		$("#btnQcode").addClass("default");
		if(qLogin != undefined){
			window.clearInterval(qLogin); 
		}
		
	});
	//刷新二维码
	$("#btnRefresh").on("click",function(){
		$.ajax({
			method : "GET",
			url : _rootPath + "/portal/login/createQcodeString",
			data:{'uid':$("#uid").val()},
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					$("#uid").val($(data)[0].code);
					$("#qcode").attr("src",_rootPath+"/portal/login/getQcode?uid="+$(data)[0].code);
				}
			}
		});
		
	});
});

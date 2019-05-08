$(document).ready(
		function() {
			jQuery.validator.addMethod("isMobile", function(value, element) {
			    var length = value.length;
			    var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
			    return this.optional(element) || (length == 11 && mobile.test(value));
			}, "请正确填写您的手机号码");
			
			$('input[type=checkbox],input[type=radio],input[type=file]')
					.uniform();
			
			   $(".btn_register").live("click",
					    function() {
					        if ($("#sign-up").valid()) {
							if ($('#agree').attr("checked") != "checked") {
								$('.agree').show().toggleClass("red");
								return false;
							} else {
								$('.agree').hide();
							}
							/*var p = $('#province').val();
							var a = $('#area').val();
							if (p == "82") {
								$('#area').append("<option value='82' selected></option>");
							} else if (p == "81") {
								$('#area').append("<option value='81' selected></option>");
							} else if (p == "71") {
								$('#area').append("<option value='71' selected></option>");
							} else if(isNaN(a)&&selCitySize == 0){
								$('#area').append("<option value='"+selCityNo+"' selected></option>");
							} else if (isNaN(a)||selCitySize == -1) {
								alert("请选择所在地区!");
								return false;
							}*/
					            
					            $("#sign-up").ajaxSubmit({
					                method: "POST",
					                url: _rootPath + "/portal/login/register",
					                datatype: "json",
					                success: function(data) {
					                    var redata = eval('(' + data + ')');
					                    var returnCode = redata.returnCode;
					                    var message = redata.message;
					                    if (returnCode == "Failure") {
					                        alert(message);
					                        return false;
					                    } else {
					                        window.location.href = _rootPath + "/portal/login/login?loginName="+message;
					                        return true;
					                    }
					                }
					            });
					        }
					    });
			
			/*$('#sign-up').submit(function() {
			      var ids = document.getElementsByName("rs");              
	                var flag = false ;              
	                for(var i=0;i<ids.length;i++){
	                    if(ids[i].checked){
	                        flag = true ;
	                        break ;
	                    }
	                }
	                if(!flag){
	                    alert("请选择角色!");
	                    return false ;
	                } 
				
				
				if ($('#agree').attr("checked") != "checked") {
					$('.agree').show().toggleClass("red");
					return false;
				} else {
					$('.agree').hide();
				}
				var p = $('#province').val();
				var a = $('#area').val();
				if (p == "82") {
					$('#area').append("<option value='82' selected></option>");
				} else if (p == "81") {
					$('#area').append("<option value='81' selected></option>");
				} else if (p == "71") {
					$('#area').append("<option value='71' selected></option>");
				} else if(isNaN(a)&&selCitySize == 0){
					$('#area').append("<option value='"+selCityNo+"' selected></option>");
				} else if (isNaN(a)||selCitySize == -1) {
					alert("请选择所在地区!");
					return false;
				}
				// 同步captcha验证
				var ispass = false;
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
			});*/

			$('#sign-up').validate({
				rules : {
					/*loginName : {
						required : true,
						minlength : 4,
						maxlength : 20
					},*/
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
					$(element).parents('.control-group').addClass('error');
				},
				unhighlight : function(element, errorClass, validClass) {
					$(element).parents('.control-group').removeClass('error');
					$(element).parents('.control-group').addClass('success');
				}
			});

			var selCitySize = -1;//选择的城市下区级的个数
			var selCityNo = "";
			// 加载省市区
			$("#province").change(
					function() {
						selCitySize = -1;
						$.get(_rootPath + "/portal/login/getCitys", {
							proCode : $("#province").val()
						}, function(data) {

							var returnCode = $(data)[0].returnCode;
							var message = $(data)[0].message;
							if (returnCode == "Failure") {
								alert(message);
								return false;
							} else {
								var result = "<option>选择城市</option>";
								$.each($(data)[0].citys, function(n, value) {
									result += "<option value='" + value.cityNo
											+ "'>" + value.cityName
											+ "</option>";
								});
								$("#city").html('');
								$("#city").append(result);
								// 刷新区
								$("#area").html('');
								$("#area").append("<option>选择区域</option>");

								return true;
							}
						}, "json");
					});
			$("#city").change(
					function() {
						selCityNo = $("#city").val();
						$.get(_rootPath + "/portal/login/getAreas", {
							cityCode : $("#city").val()
						}, function(data) {
							var returnCode = $(data)[0].returnCode;
							var message = $(data)[0].message;
							if (returnCode == "Failure") {
								alert(message);
								return false;
							} else {
								var result = "<option>选择区域</option>";
								selCitySize =  $(data)[0].areas.length;
								$.each($(data)[0].areas, function(n, value) {
									result += "<option value='" + value.areaNo
											+ "'>" + value.areaName
											+ "</option>";
								});
								$("#area").html('');
								$("#area").append(result);

								return true;
							}
						}, "json");
					});
		});

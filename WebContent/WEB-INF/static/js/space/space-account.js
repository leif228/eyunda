$(document).ready(
		function() {
	var syncMobile_block = false;
	
    $('input[type=checkbox],input[type=radio],input[type=file]').uniform();
    
    $("#bindBank").msDropDown();
    
    $("#frmBaseInfo").validate({
        rules: {
            loginName: {
                required: true,
                minlength: 4,
                maxlength: 20
            },
            trueName: {
                required: true
            },
            nickName: {
                required: true
            },
            email: {
                required: true,
                email: true
            }
        },
        errorClass: "help-inline",
        errorElement: "span",
        highlight: function(element, errorClass, validClass) {
            $(element).parents('.control-group').addClass('error');
        },
        unhighlight: function(element, errorClass, validClass) {
            $(element).parents('.control-group').removeClass('error');
            $(element).parents('.control-group').addClass('success');
        }
    });

    $("#frmBankInfo").validate({
        rules: {
            accounter: {
                required: true
            },
            subBank: {
                required: true
            },
            accountNo: {
                required: true
            }
        },
        errorClass: "help-inline",
        errorElement: "span",
        highlight: function(element, errorClass, validClass) {
            $(element).parents('.control-group').addClass('error');
        },
        unhighlight: function(element, errorClass, validClass) {
            $(element).parents('.control-group').removeClass('error');
            $(element).parents('.control-group').addClass('success');
        }
    });
    
    $("#frmApply").validate({
    	rules: {
    		legalPerson: {
    			required: true
    		}
    	},
    	errorClass: "help-inline",
    	errorElement: "span",
    	highlight: function(element, errorClass, validClass) {
    		$(element).parents('.control-group').addClass('error');
    	},
    	unhighlight: function(element, errorClass, validClass) {
    		$(element).parents('.control-group').removeClass('error');
    		$(element).parents('.control-group').addClass('success');
    	}
    });

    $("#frmPasswd").validate({
        rules: {
            password: {
                required: true,
                minlength: 6,
                maxlength: 20
            },
            newpassword: {
                required: true,
                minlength: 6,
                maxlength: 20
            },
            newpassword2: {
                required: true,
                minlength: 6,
                maxlength: 20,
                equalTo: "#newpassword"
            }
        },
        errorClass: "help-inline",
        errorElement: "span",
        highlight: function(element, errorClass, validClass) {
            $(element).parents('.control-group').addClass('error');
        },
        unhighlight: function(element, errorClass, validClass) {
            $(element).parents('.control-group').removeClass('error');
            $(element).parents('.control-group').addClass('success');
        }
    });

    var selCitySize = -1; //选择的城市下区级的个数
    var selCityNo = "";
    // 加载省市区
    $("#rprovince").change(function() {
    	selCitySize = -1;
        $.get(_rootPath + "/portal/login/getCitys", {
            proCode: $("#rprovince").val()
        },
        function(data) {

            var returnCode = $(data)[0].returnCode;
            var message = $(data)[0].message;
            if (returnCode == "Failure") {
                alert(message);
                return false;
            } else {
                var result = "<option>选择城市</option>";
                $.each($(data)[0].citys,
                function(n, value) {
                    result += "<option value='" + value.cityNo + "'>" + value.cityName + "</option>";
                });
                $("#rcity").html('');
                $("#rcity").append(result);
                // 刷新区
                $("#rarea").html('');
                $("#rarea").append("<option>选择区域</option>");

                return true;
            }
        },
        "json");
    });
    $("#rcity").change(function() {
        selCityNo = $("#rcity").val();
        $.get(_rootPath + "/portal/login/getAreas", {
            cityCode: $("#rcity").val()
        },
        function(data) {
            var returnCode = $(data)[0].returnCode;
            var message = $(data)[0].message;
            if (returnCode == "Failure") {
                alert(message);
                return false;
            } else {
                var result = "<option>选择区域</option>";
                selCitySize = $(data)[0].areas.length;
                $.each($(data)[0].areas,
                function(n, value) {
                    result += "<option value='" + value.areaNo + "'>" + value.areaName + "</option>";
                });
                $("#rarea").html('');
                $("#rarea").append(result);

                return true;
            }
        },
        "json");
    });

    // 返回
    $(".btnBack").live("click",
    function() {
        window.location.href = _rootPath + "/space/account/myAccount";
    });

    // 保存基本信息
    $(".btnSaveBaseInfo").live("click",
    function() {
        if ($("#frmBaseInfo").valid()) {

                var a = $('#area').val();
                var c = $('#city').val();
                var p = $('#province').val();
                if (!a && c) {
                    areaCode = c;
                    $('#area').append("<option value='" + areaCode + "' selected></option>");
                } else if (!a && !c) {
                    areaCode = p;
                    $('#area').append("<option value='" + areaCode + "' selected></option>");
                }
            
            if(syncMobile_block){
            	if(!$('#syncMobileNo').val()){
            		alert("请输入短信验证码！");
            		return false;
            	}
            }
            
            $("#frmBaseInfo").ajaxSubmit({
                method: "POST",
                url: _rootPath + "/space/account/myAccount/saveBaseInfo",
                datatype: "json",
                success: function(data) {
                    var redata = eval('(' + data + ')');
                    var returnCode = redata.returnCode;
                    var message = redata.message;
                    if (returnCode == "Failure") {
                        alert(message);
                        return false;
                    } else {
                    	alert(message);
                        window.location.href = _rootPath + "/space/account/myAccount";
                        return true;
                    }
                }
            });
        }
    });

    // 提交申请
    $(".btnSaveApply").live("click",
    function() {
    	if ($("#frmApply").valid()) 
	        $("#frmApply").ajaxSubmit({
	            method: "POST",
	            url: _rootPath + "/space/account/myAccount/saveApply",
	            datatype: "json",
	            success: function(data) {
	                var redata = eval('(' + data + ')');
	                var returnCode = redata.returnCode;
	                var message = redata.message;
	                if (returnCode == "Failure") {
	                    alert(message);
	                    return false;
	                } else {
	                    window.location.href = _rootPath + "/space/account/myAccount";
	                    alert(message);
	                    return false;
	                }
	            }
	        });
    });

    // 提交修改密码
    $(".btnSavePasswd").live("click",
    function() {
        if ($("#frmPasswd").valid()) $.ajax({
            method: "GET",
            data: $("#frmPasswd").formSerialize(),
            url: _rootPath + "/space/account/myAccount/savePasswd",
            datatype: "json",
            success: function(data) {
                var returnCode = $(data)[0].returnCode;
                var message = $(data)[0].message;
                if (returnCode == "Failure") {
                    alert(message);
                    return false;
                } else {
                    window.location.href = _rootPath + "/space/account/myAccount";
                    alert(message);
                    return false;
                }
            }
        });
    });
    
    //加载省市区
    $("#province").change(function() {
        var p = $('#province').val();
        $.get(_rootPath + "/space/account/myAccount/getCitys", {
            proCode: $("#province").val()
        },
        function(data) {

            var returnCode = $(data)[0].returnCode;
            var message = $(data)[0].message;
            if (returnCode == "Failure") {
                alert(message);
                return false;
            } else {
                var cresult = "";
                var aresult = "";
                $.each($(data)[0].citys,
                function(n, value) {
                    cresult += "<option value='" + value.cityNo + "'>" + value.cityName + "</option>";
                });
                $.each($(data)[0].areas,
                function(n, value) {
                    aresult += "<option value='" + value.areaNo + "'>" + value.areaName + "</option>";
                });
                $("#city").html('');
                $("#city").append(cresult);
                $("#area").html('');
                $("#area").append(aresult);

                return true;
            }
        },
        "json");
    });
    $("#city").change(function() {
        $.get(_rootPath + "/space/account/myAccount/getAreas", {
            cityCode: $("#city").val()
        },
        function(data) {
            var returnCode = $(data)[0].returnCode;
            var message = $(data)[0].message;
            if (returnCode == "Failure") {
                alert(message);
                return false;
            } else {
                var result = "";
                $.each($(data)[0].areas,
                function(n, value) {
                    result += "<option value='" + value.areaNo + "'>" + value.areaName + "</option>";
                });
                $("#area").html('');
                $("#area").append(result);

                return true;
            }
        },
        "json");
    });

	// 查找开户支行
	$(".btnSeachBank").live("click", function(){
		var keyword = $("#keyword").val();
		var bindBank = $("#bindBank").val();
		var city = $("#bccity").val();
		
		$.ajax({
			type : "post",
			url : _rootPath + "/space/wallet/myWallet/searchOpenBank",
			datatype : "json",
			data : {keyword : keyword,bank:bindBank,acode:city},
			success : function(data){
				var returnCode = $(data)[0].returnCode;
				if(returnCode == 'Failure'){
					alert($(data)[0].message);
				} else {
					 var result = "";
		                $.each($(data)[0].ppcbs,
		                function(n, value) {
		                	if(n == 0)
		                		result += "<option value='" + value.id + "' selected='selected'>" + value.bankName + "</option>";
		                	else
		                		result += "<option value='" + value.id + "'>" + value.bankName + "</option>";
		                });
		                $("#openBank").html('');
		                $("#openBank").append(result);
				}
				return false;
			}
		});
	});
	
	$("input[name=BankType]").click(function(){
		 switch($("input[name=BankType]:checked").attr("id")){
		  case "pinganBank":
		   $("#other").hide();
		   break;
		  case "otherBank":
		   $("#other").show();
		   break;
		  default:
		   break;
		 }
	});
	
	$("input[name=sbType]").click(function(){
		switch($("input[name=sbType]:checked").attr("id")){
		case "small5":
			$("#bankArea").hide();
			break;
		case "big5":
			$("#bankArea").show();
			break;
		default:
			break;
		}
	});
	
	// 解除绑定银行卡
	$(".sureUnBindCard").live("click", function(){
		$("#removeDialogForm").ajaxSubmit({
			type : "post",
			url : _rootPath + "/space/wallet/myWallet/unBindCard",
			datatype : "json",
			success : function(data){
				$("#unBindCardDialog").modal("hide");
				var returnCode = $(data)[0].returnCode;
				if(returnCode == 'Failure'){
					alert($(data)[0].message);
				} else {
					alert(data.message);
					window.location.href = _rootPath + "/space/account/myAccount";
				}
				return false;
			}
		});
		return false;
	});
	$(".removeBankCard").click(function() {
        $("#unBindCardDialog").modal("show");

        return false;
	});
	
	// 重新发送验证码
	$(".btnTimeOut").live("click", function(){
		$("#addBankCardDialogForm").ajaxSubmit({
			type : "post",
			url : _rootPath + "/space/wallet/myWallet/prebindCard",
			datatype : "json",
			success : function(data){
				var returnCode = $(data)[0].returnCode;
				if(returnCode == 'Failure'){
					alert($(data)[0].message);
				} else {
					shownum(120);
				}
				return false;
			}
		});
		return false;
	});
	
	// 绑定银行卡
	$(".btnAdd").live("click", function(){
		if($("#addBankCardDialogForm").valid()){
			var bankType = $("input[name='BankType']:checked").val(); 
			var sbType = $("input[name='sbType']:checked").val(); 
			if("otherBank" == bankType&&"big5" == sbType){
				if(!$("#openBank").val()){
					alert("转账5万元以上要选择开户支行!");
					return false;
				}
			}
			if("pinganBank" == bankType){
				$("#bindBank").find("option:selected").val("SPABANK"); 
			}
			
			$("#addBankCardDialogForm").ajaxSubmit({
				type : "post",
				url : _rootPath + "/space/wallet/myWallet/prebindCard",
				datatype : "json",
				success : function(data){
					var returnCode = $(data)[0].returnCode;
					if(returnCode == 'Failure'){
						alert($(data)[0].message);
					} else {
						$(".messBindCard").css('display','block'); 
						shownum(120);
						$(".btn_group").html(''); 
						var s ="";
						 s += '<a href="javascript:void(0);"';
	                     s +=     'class="btn btn-primary btnSubBind">提交</a>'; 
	                     s +=	 '<a href="javascript:void(0);" class="btn btn-warning btnBack">返回</a>';
	                     $(".btn_group").html(s); 
					}
					return false;
				}
			});
		}
		return false;
	});
	
	// 提交验证码
	$(".btnSubBind").live("click", function(){
		if($("#addBankCardDialogForm").valid()){
			var m = $('#MessageCode').val();
			if(!m){
				alert('请输入短信验证码！');
				return false;
			}
			$("#addBankCardDialogForm").ajaxSubmit({
				type : "post",
				url : _rootPath + "/space/wallet/myWallet/bindCard",
				datatype : "json",
				success : function(data){
					var returnCode = $(data)[0].returnCode;
					if(returnCode == 'Failure'){
						alert($(data)[0].message);
					} else {
						alert('成功');
						window.location.href = _rootPath + "/space/account/myAccount";
					}
					return false;
				}
			});
		}
	});
	
	jQuery.validator.addMethod("isMobile", function(value, element) {
	    var length = value.length;
	    var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
	    return this.optional(element) || (length == 11 && mobile.test(value));
	}, "请正确填写您的手机号码");

	$("#addBankCardDialogForm").validate({
        rules: {
        	IdCode: {
        		required: true,
        	},
        	accountName: {
                required: true,
            },
            cardNo: {
            	//creditcard: true,
                required: true
            },
            MobilePhone: {
            	required: true,
            	minlength : 11,
            	isMobile : true
            },
            paypwd : {
				required : true,
				digits : true,
				minlength : 6,
				maxlength : 6
			},
			paypwd2 : {
				required : true,
				digits : true,
				minlength : 6,
				maxlength : 6,
				equalTo : "#paypwd"
			}
        },
        errorClass: "help-inline",
        errorElement: "span",
        highlight: function(element, errorClass, validClass) {
            $(element).parents('.control-group').addClass('error');
        },
        unhighlight: function(element, errorClass, validClass) {
            $(element).parents('.control-group').removeClass('error');
            $(element).parents('.control-group').addClass('success');
        }
    });
	
	 $("#bcprovince").change(function() {
	        $.get(_rootPath + "/space/wallet/myWallet/getAreas", {
	            cityCode: $("#bcprovince").val()
	        },
	        function(data) {
	            var returnCode = $(data)[0].returnCode;
	            var message = $(data)[0].message;
	            if (returnCode == "Failure") {
	                alert(message);
	                return false;
	            } else {
	                var result = "";
	                $.each($(data)[0].areas,
	                function(n, value) {
	                	if(n == 0)
	                		result += "<option value='" + value.oraAreaCode + "' selected='selected'>" + value.areaName + "</option>";
	                	else
	                		result += "<option value='" + value.oraAreaCode + "'>" + value.areaName + "</option>";
	                });
	                $("#bccity").html('');
	                $("#bccity").append(result);

	                return true;
	            }
	        },
	        "json");
	    });

});

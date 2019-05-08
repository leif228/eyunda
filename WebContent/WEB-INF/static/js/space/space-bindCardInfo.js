$(document).ready(function() {
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
            	creditcard: true,
                required: true
            },
            MobilePhone: {
            	required: true,
            	minlength : 11,
            	isMobile : true
            },
            paypwd : {
				required : true,
				minlength : 6,
				maxlength : 20
			},
			paypwd2 : {
				required : true,
				minlength : 6,
				maxlength : 20,
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
	$("#submitDialogForm").validate({
		rules: {
			MessageCode: {
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
	
	$(".addBankCard").live("click", function(){
		if("yes"==settedPW){
			alert('只能绑定一张提现银行卡，换卡请先解绑已绑定银行卡！');
		}else{
			$(".control-group").removeClass("error");
			$("#bankAccountName").val("");
			$("#bankCardNo").val("");
			$("#bindBank")[0].selectedIndex =0;
			$("#addBankCardDialog").modal("show");
		}
	});
	
	// 提交验证码
	$(".btnSubBind").live("click", function(){
		if($("#submitDialogForm").valid()){
			var bankType = $("input[name='BankType']:checked").val(); 
			var cardNo = $("#cardNo").val();
			var bankCode = $("#bindBank").val();
			var accountName = $("#accountName").val();
			var IdCode = $("#IdCode").val();
			var MobilePhone = $("#MobilePhone").val();
			var paypwd = $("#paypwd").val();
			
			if("pinganBank" == bankType){
				bankCode = "SPABANK";
			}
			
			$("#submitDialogForm").append(" <input type='hidden' name='cardNo' value='"+cardNo+"' />");
			$("#submitDialogForm").append(" <input type='hidden' name='bankCode' value='"+bankCode+"' />");
			$("#submitDialogForm").append(" <input type='hidden' name='accountName' value='"+accountName+"' />");
			$("#submitDialogForm").append(" <input type='hidden' name='IdCode' value='"+IdCode+"' />");
			$("#submitDialogForm").append(" <input type='hidden' name='MobilePhone' value='"+MobilePhone+"' />");
			$("#submitDialogForm").append(" <input type='hidden' name='paypwd' value='"+paypwd+"' />");
			
			$("#submitDialogForm").ajaxSubmit({
				type : "post",
				url : _rootPath + "/space/wallet/myWallet/bindCard",
				datatype : "json",
				success : function(data){
					var returnCode = $(data)[0].returnCode;
					if(returnCode == 'Failure'){
						alert($(data)[0].message);
					} else {
						$("#submitDialog").modal("hide");
						window.location.href = _rootPath + "/space/wallet/myWallet/bankCardInfo";
					}
					return false;
				}
			});
		}
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
						$("#addBankCardDialog").modal("hide");
						$("#submitDialog").modal("show");
						shownum(120);
					}
					return false;
				}
			});
		}
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
	});
	
	// 解除绑定
	$(".removeBankCard").live("click", function(){
		$("#bindCardId").val($(this).attr("data-info"));
		$("#removeDialog").modal("show");
	});
	
	// 解除绑定银行卡
	$(".btnRemove").live("click", function(){
		$("#removeDialogForm").ajaxSubmit({
			type : "post",
			url : _rootPath + "/space/wallet/myWallet/unBindCard",
			datatype : "json",
			success : function(data){
				$("#removeDialog").modal("hide");
				var returnCode = $(data)[0].returnCode;
				if(returnCode == 'Failure'){
					alert($(data)[0].message);
				} else {
					alert(data.message);
					window.location.href = _rootPath + "/space/wallet/myWallet/bankCardInfo";
				}
				return false;
			}
		});
	});
	
	// 设置收款银行卡
	$(".isRcvCard").live("click", function(){
		var cardId = $(this).attr("idVal");
		
		$("[name='isRcvCard']").each(function(){
			if($(this).attr("idVal") == cardId){
				$(this).attr("checked", true);
			} else {
				$(this).attr("checked", false);
			}
		});
		
		$.ajax({
			type : "post",
			url : _rootPath + "/space/wallet/myWallet/setRcvCard",
			datatype : "json",
			data : {cardId : cardId},
			success : function(data){
				var returnCode = $(data)[0].returnCode;
				if(returnCode == 'Failure'){
					alert($(data)[0].message);
				} else {
					// alert(data.message);
					window.location.href = _rootPath + "/space/wallet/myWallet/bankCardInfo";
				}
				return false;
			}
		});
	});
	
	// 查找开户支行
	$(".btnSeachBank").live("click", function(){
		var keyword = $("#keyword").val();
		var bindBank = $("#bindBank").val();
		var city = $("#city").val();
		
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
	
	 $("#province").change(function() {
	        $.get(_rootPath + "/space/wallet/myWallet/getAreas", {
	            cityCode: $("#province").val()
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
	                $("#city").html('');
	                $("#city").append(result);

	                return true;
	            }
	        },
	        "json");
	    });
	
});
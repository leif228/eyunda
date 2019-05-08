$(document).ready(function() {
	
	var bindCardInterval;
	var walletInterval;
	$("input:radio[name='payStyle'][value=0]").attr("checked", true);
	$("#plantBankId").msDropDown();
	if ($("#bindId option").size() > 0)
		$("#bindId").msDropDown();
	$("#payForm").attr("action", _netpayurl);
	
	$("input:radio[name='payStyle']").live("click", function(){
		var payStyle = $(this).val();
		$("#verifyCode").val("");
		$("#messageCode").val("");
		$("#plantBank").css("display", "none");
		$("#bindFastPay").css("display", "none");
		$("#walletPayValid").css("display", "none");
		if(payStyle == 0){
			// 网关支付
			$("#payForm").attr("action", _netpayurl);
		} else if(payStyle == 1){
			// 非绑定快捷支付
			$("#payForm").attr("action", _nobindpayurl);
		} else if(payStyle == 2){
			// 绑定并支付
			$("#plantBank").css("display", "block");
			$("#plantBankId")[0].selectedIndex =0;
			$("#payForm").attr("action", _bindandpayurl);
		} else if(payStyle == 3){
			// 已绑定银行卡支付
			$("#bindFastPay").css("display", "block");
			$("#verifyCode").val("");
			if($("#bindId").val()){
				$("#bindId")[0].selectedIndex =0;
			}
		} else if(payStyle == 4){
			// 钱包支付
			$("#walletPayValid").css("display", "block");
			$("#payForm").attr("action", _rootPath + "/space/pinganpay/walletPay");
		}
		
	});
	
	// 下一步
	$(".btnNext").live("click", function(){
		var i = $("input:radio[name='payStyle']:checked").val();
		if(i == "2"){
			bindandpay();
		} else if(i == "3"){
			if($("#verifyCode").val()){
				$("#btnPay").removeClass("btnNext");
				bindfastpay();
			} else {
				alert("请输入短信验证码");
			}
		} else if (i == "4"){
			if($("#messageCode").val()){
				$("#btnPay").removeClass("btnNext");
				walletPay();
			} else {
				alert("请输入短信验证码");
			}
		} else {
			$("#btnPay").removeClass("btnNext");
			nobindpay();
		}
	})
	
	// 钱包支付验证码
	$(".walletPayValid").live("click", function(){
		$("#messageCode").val("");
		$("#msgCodeBtn").attr("disabled", true);
		$("#msgCodeBtn").removeClass("walletPayValid");
		$.ajax({
			type: "post",
			url: _rootPath + "/space/pinganpay/walletPayVerifyCode",
			data: {walletId : $("#walletId").val()},
			datatype: "json",
			success: function(data){
				var returnCode = $(data)[0].returnCode;
				if(returnCode == 'Failure'){
					$("#msgCodeBtn").addClass("walletPayValid");
					$("#msgCodeBtn").attr("disabled", false);
					$("#msgCodeBtn").text("重新获取");
					alert($(data)[0].message);
				} else {
					$("#serialNo").val($(data)[0].serialNo);
					var i=60;
					walletInterval = setInterval(function(){
						i--;
						$("#msgCodeBtn").text(i + ("s后重新获取"));
						if(i<=0){
							clearInterval(walletInterval);
							$("#messageCode").val("");
							$("#msgCodeBtn").addClass("walletPayValid");
							$("#msgCodeBtn").attr("disabled", false);
							$("#msgCodeBtn").text("重新获取");
						}
					}, 1000);
				}
				return false;
			}
		});
		
	});
	
	// 钱包支付
	var walletPay = function walletPay(){
		$.ajax({
			type : "post",
			data: {walletId : $("#walletId").val()},
			url : _rootPath + "/space/pinganpay/walletPay",
			datatype : "json",
			success : function(data){
				var returnCode = $(data)[0].returnCode;
				alert($(data)[0].message);
				if(returnCode == 'Failure'){
					
				} else {
					clearInterval(walletInterval);
					$("#messageCode").val("");
					$("#msgCodeBtn").addClass("walletPayValid");
					$("#msgCodeBtn").attr("disabled", false);
					$("#msgCodeBtn").text("获取验证码");
					window.location.href = _rootPath + "/space/wallet/myWallet";
				}
				return false;
			}
		});
	}
	
	// 非绑定卡支付
	var nobindpay = function nobindpay(){
		$.ajax({
			type: "post",
			url: _rootPath + "/space/pinganpay/noBindPay",
			data: {walletId : $("#walletId").val()},
			datatype: "json",
			success: function(data){
				var returnCode = $(data)[0].returnCode;
				if(returnCode == 'Failure'){
					alert($(data)[0].message);
				} else {
					$("#orig").val($(data)[0].orig);
					$("#sign").val($(data)[0].sign);
					$("#payForm").submit();
				}
				return false;
			}
		});
	}
	
	// 绑定并支付
	var bindandpay = function bindandpay(){
		$.ajax({
			type: "post",
			url: _rootPath + "/space/pinganpay/bindAndPay",
			data: {walletId : $("#walletId").val(), plantBankId : $("#plantBankId").val()},
			datatype: "json",
			success: function(data){
				var returnCode = $(data)[0].returnCode;
				if(returnCode == 'Failure'){
					alert($(data)[0].message);
				} else {
					$("#orig").val($(data)[0].orig);
					$("#sign").val($(data)[0].sign);
					$("#payForm").submit();
				}
				return false;
			}
		});
	}
	
//	$("#bindId").live("change", function(){
//		$("#validCode").text("获取验证码");
//	})
	
	// 获取绑定卡支付验证码
	$(".btnValidCode").live("click", function(){
		$("#verifyCode").val("");
		$("#validCode").attr("disabled", true);
		$("#validCode").removeClass("btnValidCode");
		$.ajax({
			type: "post",
			url: _rootPath + "/space/pinganpay/getValidCode",
			data: {walletId : $("#walletId").val(), bindId : $("#bindId").val()},
			datatype: "json",
			success: function(data){
				var returnCode = $(data)[0].returnCode;
				if(returnCode == 'Failure'){
					$("#validCode").addClass("btnValidCode");
					$("#validCode").attr("disabled", false);
					$("#validCode").text("重新获取");
					alert($(data)[0].message);
				} else {
					var i=60;
					bindCardInterval = setInterval(function(){
						i--;
						$("#validCode").text(i + ("s后重新获取"));
						if(i<=0){
							clearInterval(bindCardInterval);
							$("#verifyCode").val("");
							$("#validCode").addClass("btnValidCode");
							$("#validCode").attr("disabled", false);
							$("#validCode").text("重新获取");
						}
					}, 1000);
					$("#dataTime").val($(data)[0].dataTime);
				}
				return false;
			}
		});
		
	});
	
	// 绑定卡快捷支付
	var bindfastpay = function bindfastpay(){
		$.ajax({
			type: "post",
			url: _rootPath + "/space/pinganpay/bindCardPay",
			data: { walletId : $("#walletId").val(), 
					bindId : $("#bindId").val(), 
					verifyCode :  $("#verifyCode").val(),
					dataTime : $("#dataTime").val()
				},
			datatype: "json",
			success: function(data){
				var returnCode = $(data)[0].returnCode;
				if(returnCode == 'Failure'){
					alert($(data)[0].message);
				} else {
					clearInterval(bindCardInterval);
					$("#verifyCode").val("");
					$("#validCode").addClass("btnValidCode");
					$("#validCode").attr("disabled", false);
					$("#validCode").text("获取验证码");
					alert($(data)[0].message);
					window.location.href = _rootPath + "/space/wallet/myWallet";
				}
				return false;
			}
		});
	}
	
	// 解除已绑定的银行卡
	$(".removeCard").live("click", function(){
		$.ajax({
			type: "post",
			url: _rootPath + "/space/pinganpay/removeBindCard",
			data: {bindId : $("#bindId").val()},
			datatype: "json",
			success: function(data){
				var returnCode = $(data)[0].returnCode;
				alert($(data)[0].message);
				if(returnCode == 'Failure'){
					
				} else {
					// window.location.reload();
					window.close();
				}
				return false;
			}
		});
	});
		
});
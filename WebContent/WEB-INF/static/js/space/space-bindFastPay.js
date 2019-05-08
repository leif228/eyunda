$(document).ready(function() {
	
//	$("input:radio[name='payStyle'][value=0]").attr("checked", true);

	$("#validCode").attr("disabled", false);
	$("#bindCardPay").attr("disabled", true);
	
	$("#bindCardPay").live("change", function(){
		$("#validCode").text("获取验证码");
	})
	
	var setIntervalId;
	$(".btnValidCode").live("click", function(){
		$.ajax({
			type: "post",
			url: _rootPath + "/space/pinganpay/getValidCode",
			data: {walletId : $("#walletId").val(), bindId : $("#bindId").val()},
			datatype: "json",
			success: function(data){
				var returnCode = $(data)[0].returnCode;
				if(returnCode == 'Failure'){
					alert($(data)[0].message);
				} else {
					$("#bindCardPay").addClass("btnPay");
					$("#bindCardPay").attr("disabled", false);
					$("#validCode").attr("disabled", true);
					$("#validCode").removeClass("btnValidCode");
					var i=60;
					setIntervalId = setInterval(function(){
						i--;
						$("#validCode").text(i + ("s后重新获取"));
						if(i<=0){
							$("#verifyCode").val("");
							$("#validCode").addClass("btnValidCode");
							$("#bindCardPay").removeClass("btnPay");
							$("#bindCardPay").attr("disabled", true);
							$("#validCode").attr("disabled", false);
							$("#validCode").text("重新获取");
							clearInterval(setIntervalId);
						}
					}, 1000);
					var start = $(data)[0].dataTime;
					$("#dataTime").val($(data)[0].dataTime);
				}
				return false;
			}
		})
		
	});
	
	$("#bindId")[0].selectedIndex =0;
	$("#verifyCode").val("");
	
	// 绑定卡支付
	$(".btnPay").live("click", function(){
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
					clearInterval(setIntervalId);
					$("#bindCardPay").change();
					$("#verifyCode").val("");
					$("#bindCardPay").removeClass("btnPay");
					$("#bindCardPay").attr("disabled", true);
					$("#validCode").addClass("btnValidCode");
					$("#validCode").attr("disabled", false);
					$("#validCode").text("获取验证码");
					alert($(data)[0].message);
				}
				return false;
			}
		})
	})
});
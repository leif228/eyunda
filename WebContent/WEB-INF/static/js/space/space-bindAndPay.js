$(document).ready(function() {
	
//	$("input:radio[name='payStyle'][value=0]").attr("checked", true);
	$("#plantBankId")[0].selectedIndex =0;
	// 绑定并支付
	$(".bindAndPay").live("click", function(){
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
		})
	});
	
});
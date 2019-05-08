$(document).ready(function() {
	
	$("#payForm").validate({
        rules: {
        	paypwd: {
                required: true,
            },
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

	// 支付
	$(".btnPay").live("click", function(){
		if($("#payForm").valid()){
			$("#payForm").ajaxSubmit({
				type : "post",
				url : _rootPath + "/space/pinganpay/walletPay",
				datatype : "json",
				success : function(data){
					var returnCode = $(data)[0].returnCode;
					if(returnCode == 'Failure'){
						alert($(data)[0].message);
					} else {
						alert($(data)[0].message);
						window.location.href = _rootPath + "/space/wallet/myWallet";
					}
					return false;
				}
			});
		}
	});
	
});
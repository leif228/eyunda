$(document).ready(function() {
	
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
    $('.dateStartDate').datetimepicker({
        //language:  'fr',
        weekStart: 1,
        todayBtn:  1,
		todayHighlight: 1,
		startView: 2,
		forceParse: 0,
        showMeridian: 1,
        minView: "month", //选择日期后，不会再跳转去选择时分秒 
        autoclose:true
    });
    
    $('.removeStartDate').click(function(){
    	$('#startDate').val("");
    });
    
    $('.dateEndDate').datetimepicker({
        //language:  'fr',
        weekStart: 1,
        todayBtn:  1,
		todayHighlight: 1,
		startView: 2,
		forceParse: 0,
        showMeridian: 1,
        minView: "month", //选择日期后，不会再跳转去选择时分秒 
        autoclose:true
    });
    
    $('.removeEndDate').click(function(){
    	$('#endDate').val("");
    });

    // 查看
	$(".btnShow").live("click", function() {
		$.ajax({
			method : "GET",
			data : {userId : $(this).attr("idVal")},
			url : _rootPath+"/manage/wallet/"+$(this).attr("idVal"),
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					//读入数据，并填入
					$("#_settleStyle").val($(data)[0].wallet.settleStyle);
					$("#_paymentNo").val($(data)[0].wallet.paymentNo);

					$("#_feeItem").val($(data)[0].wallet.feeItem);
					$("#_orderId").val($(data)[0].wallet.orderId);

					$("#_seller").val($(data)[0].wallet.sellerData.loginName);
					$("#_sndAccountName").val($(data)[0].wallet.sndAccountName);
					$("#_sndCardNo").val($(data)[0].wallet.sndCardNo);

					$("#_buyyer").val($(data)[0].wallet.buyerData.loginName);
					$("#_rcvAccountName").val($(data)[0].wallet.rcvAccountName);
					$("#_rcvCardNo").val($(data)[0].wallet.rcvCardNo);

					$("#_broker").val($(data)[0].wallet.brokerData.loginName);
					$("#_brokerAccountName").val($(data)[0].wallet.brokerAccountName);
					$("#_brokerCardNo").val($(data)[0].wallet.brokerCardNo);

					$("#_subject").val($(data)[0].wallet.subject);
					$("#_body").val($(data)[0].wallet.body);

					$("#_totalFee").val($(data)[0].wallet.totalFee);
					$("#_middleFee").val($(data)[0].wallet.middleFee);
					$("#_serviceFee").val($(data)[0].wallet.serviceFee);

					$("#_paymentStatus").val($(data)[0].wallet.paymentStatus);
					$("#_gmtPayment").val($(data)[0].wallet.gmtPayment);
					
					$("#_suretyStatus").val($(data)[0].wallet.suretyStatus);
					$("#_suretyDays").val($(data)[0].wallet.suretyDays);
					$("#_gmtSurety").val($(data)[0].wallet.gmtSurety);

					$("#_refundStatus").val($(data)[0].wallet.refundStatus);
					$("#_gmtRefund").val($(data)[0].wallet.gmtRefund);
					
					$("#showDialog").modal("show");
				}
			}
		});
		return true;
	});
    
	// 确认付款对话框
	$(".btnPayment").live("click", function(){
		$("#doPaymentId").val($(this).attr("idVal"));
		$.ajax({
			method : "GET",
			data : {userId : $(this).attr("idVal")},
			url : _rootPath+"/manage/wallet/"+$(this).attr("idVal"),
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					//读入数据，并填入
					var s = "";
					s += "<p>订单号：" + $(data)[0].wallet.orderId + "</p>";
					s += "<p>卖家：" + $(data)[0].wallet.sellerData.loginName + "</p>";
					s += "<p>买家：" + $(data)[0].wallet.buyerData.loginName + "</p>";
					s += "<p>交易描述：" + $(data)[0].wallet.body + "</p>";
					s += "<p>交易金额(元)：" + $(data)[0].wallet.totalFee + "</p>";

			        $("#paymentOrderDesc").html(s);
					
					$("#doPaymentDialog").modal("show");
				}
			}
		});
		return true;
	});

	// 确认付款
	$(".btnDoPayment").live("click", function(){
		$("#doPaymentDialogForm").ajaxSubmit({
			type : "post",
			url : _rootPath + "/manage/wallet/doPayment",
			datatype : "json",
			success : function(data){
				$("#doPaymentDialog").modal("hide");
				var returnCode = $(data)[0].returnCode;
				if(returnCode == 'Failure'){
					alert($(data)[0].message);
				} else {
					alert($(data)[0].message);
					window.location.href = _rootPath + "/manage/wallet/walletExec";
				}
				return false;
			}
		});
	});
	
	// 退款处理对话框
	$(".btnRefund").live("click", function(){
		$("#doRefundId").val($(this).attr("idVal"));
		$.ajax({
			method : "GET",
			data : {userId : $(this).attr("idVal")},
			url : _rootPath+"/manage/wallet/"+$(this).attr("idVal"),
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					//读入数据，并填入
					var s = "";
					s += "<p>订单号：" + $(data)[0].wallet.orderId + "</p>";
					s += "<p>卖家：" + $(data)[0].wallet.sellerData.loginName + "</p>";
					s += "<p>买家：" + $(data)[0].wallet.buyerData.loginName + "</p>";
					s += "<p>交易描述：" + $(data)[0].wallet.body + "</p>";
					s += "<p>交易金额(元)：" + $(data)[0].wallet.totalFee + "</p>";

			        $("#refundOrderDesc").html(s);

					$("#doRefundDialog").modal("show");
				}
			}
		});
		return true;
	});
	
	// 退款处理
	$(".btnDoRefund").live("click", function(){
		$("#doRefundDialogForm").ajaxSubmit({
			type : "post",
			url : _rootPath + "/manage/wallet/doRefund",
			datatype : "json",
			success : function(data){
				$("#doRefundDialog").modal("hide");
				var returnCode = $(data)[0].returnCode;
				if(returnCode == 'Failure'){
					alert($(data)[0].message);
				} else {
					alert($(data)[0].message);
					window.location.href = _rootPath + "/manage/wallet/walletExec";
				}
				return false;
			}
		});
	});

});

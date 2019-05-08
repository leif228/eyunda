$(document).ready(function() {
	// Form Validation
	$("#walletPayForm").validate({
        rules: {
            paypwd: {
            	required: true,
            },
            payMoney: {
            	required: true,
            },
            suretyDay: {
            	required: true,
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

	// 删除
	$(".btnDelete").live("click", function() {
		$("#orderId").val($(this).attr("idVal"));
		$("#orderForm").val(_rootPath + "/space/orderCommon/orderDelete");
		
		$("#orderPutTitle").html("<h3>删除</h3>");
		$("#orderPutContent").html("<p>当前合同："+$(this).attr("oVal")+"</p>"+"<p>你确认要删除该合同信息吗？</p>");

		$("#signImage").html("");
		$("#stampImage").html("");

		$("#orderPutDlg").modal("show");
		return true;
	});

	// 确认订单
	$(".btnSubmit").live("click", function() {
		$("#orderId").val($(this).attr("idVal"));
		$("#orderForm").val(_rootPath + "/space/orderCommon/orderSubmit");
		
		$("#orderPutTitle").html("<h3>提交</h3>");
		$("#orderPutContent").html("<p>当前合同："+$(this).attr("oVal")+"</p>"+"<p>你确认要提交该合同给甲乙双方签字吗？</p>");

		$("#signImage").html("");
		$("#stampImage").html("");

		$("#orderPutDlg").modal("show");
		
		return true;
	});

	// 审核通过
	$(".btnAudit").live("click", function() {
		$("#orderId").val($(this).attr("idVal"));
		$("#orderForm").val(_rootPath + "/space/orderCommon/orderAudit");
		
		$("#orderPutTitle").html("<h3>审核通过</h3>");
		$("#orderPutContent").html("<p>当前合同："+$(this).attr("oVal")+"</p>"+"<p>你确认该合同审核通过吗？</p>");

		$("#signImage").html("");
		$("#stampImage").html("");

		$("#orderPutDlg").modal("show");
		
		return true;
	});

	// 起签
	$(".btnStartSign").live("click", function() {
		$("#orderId").val($(this).attr("idVal"));
		$("#orderForm").val(_rootPath + "/space/orderCommon/orderStartSign");
		
		$("#orderPutTitle").html("<h3>乙方签字</h3>");
		$("#orderPutContent").html("<p>当前合同："+$(this).attr("oVal")+"</p>"+"<p>你确认完全同意该合同并签字吗？</p>");

		$("#signImage").html("");
		$("#stampImage").html("");

		if(_signImage)
			$("#signImage").html("签名：<br/><img src=\"" + _rootPath 
					+ "/download/imageDownload?url=" + _signImage + "\" class=\"thumbnail\" />");
		
		if(_stampImage)
			$("#stampImage").html("图章：<br/><img src=\"" + _rootPath 
					+ "/download/imageDownload?url=" + _stampImage + "\" class=\"thumbnail\" />");
           
		$("#orderPutDlg").modal("show");
		
		return true;
	});
	
	// 确签
	$(".btnEndSign").live("click", function() {
		$("#orderId").val($(this).attr("idVal"));
		$("#orderForm").val(_rootPath + "/space/orderCommon/orderEndSign");
		
		$("#orderPutTitle").html("<h3>甲方签字</h3>");
		$("#orderPutContent").html("<p>当前合同："+$(this).attr("oVal")+"</p>"+"<p>你确认完全同意该合同并签字吗？</p>");

		$("#signImage").html("");
		$("#stampImage").html("");

		if(_signImage)
			$("#signImage").html("签名：<br/><img src=\"" + _rootPath 
					+ "/download/imageDownload?url=" + _signImage + "\" class=\"thumbnail\" />");
		
		if(_stampImage)
			$("#stampImage").html("图章：<br/><img src=\"" + _rootPath 
					+ "/download/imageDownload?url=" + _stampImage + "\" class=\"thumbnail\" />");
           
		$("#orderPutDlg").modal("show");

		return true;
	});

	// 合同归档
	$(".btnArchive").live("click", function() {
		$("#orderId").val($(this).attr("idVal"));
		$("#orderForm").val(_rootPath + "/space/orderCommon/orderArchive")
		
		$("#orderPutTitle").html("<h3>合同归档</h3>");
		$("#orderPutContent").html("<p>当前合同："+$(this).attr("oVal")+"</p>"+"<p>归档后不可再支付、评价或监控，你确认归档该合同吗？</p>");

		$("#signImage").html("");
		$("#stampImage").html("");

		$("#orderPutDlg").modal("show");

		return true;
	});
	
	// 删除、提交、审核、起签、确签、合同归档等对话框提交
	$(".executeCommand").live("click", function() {
		var orderId = $("#orderId").val();
		var pageNo = $("#pageNo").val();
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		
		$.ajax({
	        method : "GET",
	        data : {orderId : orderId},
	        url : $("#orderForm").val(),
	        datatype : "json",
	        success : function(data) {
	            var returnCode = $(data)[0].returnCode;
	            var message = $(data)[0].message;
	            if (returnCode == "Failure") {
	                alert(message);
	                return false;
	            } else {
	            	alert(message);
	            	window.location.href = _rootPath + "/space/orderCommon/orderList?orderId="+orderId
	            							+"&pageNo="+pageNo+"&startTime="+startTime+"&endTime="+endTime;
	            }
	        }
	    });
	});
		
	// 评价
	$(".btnApprovalEdit").live("click", function() {
		$("#approvalOrderId").val($(this).attr("idVal"));
		$.ajax({
	        method : "GET",
	        data : {orderId : $("#approvalOrderId").val()},
	        url : _rootPath + "/space/orderCommon/orderApprovalEdit",
	        datatype : "json",
	        success : function(data) {
	            var returnCode = $(data)[0].returnCode;
	            var message = $(data)[0].message;
	            if (returnCode == "Failure") {
	                alert(message);
	                return false;
	            } else {
	            	var shipApprovalData = $(data)[0].shipApprovalData;
	            	$("#evalTypesDiv").children("[checked='checked']").removeAttr('checked');
	            	$("#evalTypesDiv").children("#"+shipApprovalData.evalType).attr("checked","checked")
	            	$("#evalContent").val(shipApprovalData.evalContent);
	            	
	            	$("#approvalDlg").modal("show");
	            	return true;
	            }
	        }
	    });
		return false;
	});
	
	$(".orderApproval").live("click", function() {
		$.ajax({
	        method : "GET",
	        data : {orderId : $("#approvalOrderId").val(), evalType : $("#evalTypesDiv").children("[checked='checked']").val(), evalContent : $("#evalContent").val()},
	        url : _rootPath+"/space/orderCommon/orderApproval",
	        datatype : "json",
	        success : function(data) {
	            var returnCode = $(data)[0].returnCode;
	            var message = $(data)[0].message;
	            if (returnCode == "Failure") {
	                alert(message);
	                return false;
	            } else {
	            	alert(message);
	            	$("#approvalDlg").modal("hide");
	            	return true;
	            }
	        }
	    });
	});

	//根据输入期限查寻合同
	$('.findOrder').live("click",function (){
		$("#pageform").submit();
	});
	 
	// 确认付款弹出框
	$(".confirmPay").live("click", function(){
		$("#confirmOrderId").val($(this).attr("idVal"));
		$("#confirmPayDialog").modal("show");
	});
	
	// 确认付款
	$(".btnConfirm").live("click", function(){
		$("#confirmPayForm").ajaxSubmit({
			type : "post",
			url : _rootPath + "/space/pinganpay/confirmPay",
			datatype : "json",
			success : function(data){
				$("#startSuretyDialog").modal("hide");
				var returnCode = $(data)[0].returnCode;
				if(returnCode == 'Failure'){
					alert($(data)[0].message);
				} else {
					alert(data.message);
					window.location.href = _rootPath + "/space/wallet/myWallet";
				}
				return false;
			}
		});
	});
	
	$(".orderPay").live("click", function(){
		$("#orderPayId").val($(this).attr("idVal"));
		$("#walletPayDialog").modal("show");
	});
	
	// 支付
	$(".btnOrderPay").live("click", function(){
		if($("#walletPayForm").valid()){
			$("#walletPayDialog").modal("hide");
			$(".modal-backdrop").remove();
			$("#walletPayForm").submit();
		}
	});
	
	$("#newOrderTypeCode").live("change",function(){
		var v = $("#newOrderTypeCode").find("option:selected").val();
		if(v==""){
			return;
		}else{
			window.location.href = _rootPath + "/space/orderCommon/orderEdit?orderId=0&orderType=" + v;
		}
	});
	
});
$(document).ready(function() {
	
	$("#payDialogForm").validate({
        rules: {
        	bankAccountName: {
                required: true,
            },
            payMoney: {
            	number:true,
                required: true,
                checkPrice: true,
                money: true
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
	
	
	$(".btnin").live("click", function() {
		$("#btninDialog").modal("show");
	});

	$(".btn_surein").live("click",function() {
		$("#btninDialogForm").submit();
		return true;
	});
			
	$("#statusCode").change(function() {
		window.location.href = _rootPath + "/space/ship/myShip/gasOrder?statusCode="+$(this).val();
	});
	
//	// 退款
//	$(".btnRefund").live("click", function() {
//		var id = $(this).attr("idVal");
//		$.ajax({
//			type: 'GET',
//			url : _rootPath + "/space/ship/myShip/gasOrderRefund",
//			data : {id : id},
//			datatype : "json",
//			success : function(data){
//				var data = $(data)[0];
//				var returnCode = data.returnCode;
//		        var message = data.message;
//		        if (returnCode == "Failure") {
//		        	alert(message);
//		        	return false;
//		        } else {
//		        	var s = 's'+id;
//		        	$('.'+s).val("退款申请");
//		        	window.location.href = "https://my.alipay.com/portal/i.htm";
//		        }
//			}
//		})
//		return true;
//	});
			
//	// 支付
//	$(".btnPay").live("click", function() {
//		var id = $(this).attr("idVal");
//		window.location.href = _rootPath + "/space/ship/myShip/gasOrderPay?id="+id;
//				
//		return true;
//	});
			
//	$('.btnNext').live("click",function (){
//		$("#showInfo").css("display", "none");
//		$("#showBack").css("display", "block");
//        
//		var orderId = $("#payOrderId").val();
//		
//        var rcvAccountName = $("#reciviceName").val();
//        var rcvBankDesc = $("#receiveBank").text();
//        var rcvCardNo = $("#bankCardNo").val();
//        
//        var payBankInfo = $("#payBank option:selected").text();
//        
//        var payMoney = $("#payMoney").val();
//        var suretyDays = $("#suretyDay").val();
//        
//        var p = "<p>你确认要从 " + payBankInfo + " 支付" + orderId + "号购买订单,付款人民币" + payMoney 
//        		+ "元给 " + rcvBankDesc + "(" + rcvAccountName + ":" + rcvCardNo + ")" + "？</p>";
//        $("#showBack").html(p);
//        
//        var s = "";
//        s += "<div class=\"modal-footer\">";
//        s += "  <a class=\"btn\" data-dismiss=\"modal\">";
//        s += "    <i class=\"icon icon-off\"></i> 关闭";
//        s += "  </a>";
//        s += "  <a class=\"btn btn-primary pay\">";
//        s += "    <i class=\"icon-ok icon-white\"></i> 确认付款";
//        s += "  </a>";
//        s += "</div>";
//		
//        $("#nextOpt").html(s);
//	});
	
//	// 支付
//	$(".btnPay").live("click", function(){
//		$("#showInfo").css("display", "block");
//		$("#showBack").css("display", "none");
//		$.ajax({
//			type : "get",
//			url : _rootPath + "/space/ship/myShip/getGasOrder",
//			data : {orderId : $(this).attr("idVal")},
//			datatype : "json",
//			success : function(data){
//				var returnCode = $(data)[0].returnCode;
//				var message = $(data)[0].message;
//				if(returnCode == 'Failure'){
//					alert(message);
//				} else {
//					$("#reciviceName").val($(data)[0].gasOrderData.company.accounter);
//					
//					$("#bankCardNo").val($(data)[0].gasOrderData.company.accountNo);
//					
//					var str = "<option value=\"EYUNDA\">易运达钱包</option>";
//					$("#receiveBank").html(str);
//					
//					$("#payOrderId").val($(data)[0].gasOrderData.id);
//					$("#payMoney").val($(data)[0].gasOrderData.tradeMoney);
//					$(".control-group").removeClass("error");
//					$("#payBank")[0].selectedIndex =0;
//					$("#remark").val("");
//					$("#surety").attr("checked",false);
//					$("#suretyDay").val("");
//					$("#suretyDay").attr("disabled", true);
//					
//					var s = "";
//			        s += "<div class=\"modal-footer\">";
//			        s += "  <a class=\"btn\" data-dismiss=\"modal\">";
//			        s += "    <i class=\"icon icon-off\"></i> 关闭";
//			        s += "  </a>";
//			        s += "  <a class=\"btn btn-primary btnNext\">";
//			        s += "    <i class=\"icon-ok icon-white\"></i> 下一步";
//			        s += "  </a>";
//			        s += "</div>";
//					
//			        $("#nextOpt").html(s);
//					
//					$("#payDialog").modal("show");
//				}
//				return false;
//			}
//		});
//	});
			
//	// 设置资金托管
//	$("#surety").live("change", function(){
//		$("#suretyDay").val("");
//		$("#suretyDay").attr("disabled", true);
//		if($("#surety").prop("checked")){
//			$("#suretyDay").attr("disabled", false);
//			$("#suretyDay").val("30");
//		}
//	});
	
//	// 付款
//	$(".pay").live("click", function(){
//		if($("#payDialogForm").valid()){
//			$("#payDialogForm").ajaxSubmit({
//				type : "post",
//				url : _rootPath + "/space/wallet/myWallet/orderPay",
//				datatype : "json",
//				success : function(data){
//					$("#payDialog").modal("hide");
//					var returnCode = $(data)[0].returnCode;
//					if(returnCode == 'Failure'){
//						alert($(data)[0].message);
//					} else {
//						alert(data.message);
//						window.location.href = _rootPath + "/space/ship/myShip/gasOrder?statusCode=" 
//						+ $("#statusCode").val() + "&pageNo=" + $("#pageNo").val();
//					}
//					return false;
//				}
//			});
//		}
//	});
	
//	// 申请退款对话框
//	$(".btnRefund").live("click", function(){
//		$("#refundOrderId").val($(this).attr("idVal"));
//		$("#refundDialog").modal("show");
//	});
	
//	// 申请退款
//	$(".refund").live("click", function(){
//		$("#refundDialogForm").ajaxSubmit({
//			type : "post",
//			url : _rootPath + "/space/wallet/myWallet/refund",
//			datatype : "json",
//			success : function(data){
//				$("#refundDialog").modal("hide");
//				var returnCode = $(data)[0].returnCode;
//				if(returnCode == 'Failure'){
//					alert($(data)[0].message);
//				} else {
//					alert(data.message);
//					window.location.href = _rootPath + "/space/ship/myShip/gasOrder?statusCode=" 
//					+ $("#statusCode").val() + "&pageNo=" + $("#pageNo").val();
//				}
//				return false;
//			}
//		});
//	});
//	
	$(".btnDelete").live("click", function() {
		$("#delId").val($(this).attr("idVal"));

		$("#deleteDialog").modal("show");

		return true;
	});
	
});
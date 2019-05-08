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

	// 见证跨行收单接口调用详情列表
	$(".btnDoError").live("click", function(){
		$.ajax({
			method : "GET",
			data : {userId : $(this).attr("noVal")},
			url : _rootPath+"/manage/wallet/"+$(this).attr("noVal"),
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
		        	s += "<table class=\"table table-bordered data-table\">";
		        	s += "<thead>";
		        	s += "  <tr>";
		        	s += "    <th style=\"width: 10%\">流水号</th>";
		        	s += "    <th style=\"width: 10%\">功能号</th>";
		        	s += "    <th style=\"width: 30%\">上送参数</th>";
		        	s += "    <th style=\"width: 30%\">返回参数</th>";
		        	s += "    <th style=\"width: 10%\">返回码</th>";
		        	s += "    <th style=\"width: 10%\">操作</th>";
		        	s += "  </tr>";
		        	s += "</thead>";
		        	s += "<tbody>";
		        	
		        	$.each($(data)[0].wallet.walletZjjzDatas, function(i, da){
		        		s += "<tr class=\"gradeX\">";
		        		
		        		s += "<td>" + da.logNo + "</td>";
		        		s += "<td>" + da.tranFunc + "_" + da.funcFlag + "</td>";
		        		s += "<td>" + da.sendParames + "</td>";
		        		s += "<td>" + da.recvParames + "</td>";
		        		s += "<td>" + da.rspCode + "<br />" + da.rspMsg + "</td>";
		        		
		        		if (da.rspCode == "000000")
		        		  s += "<td><a class=\"btn btn-primary btnReExec\" zjjzVal=\"\" data-toggle=\"modal\" data-target=\"#reExecDialog\"> <i class=\"icon-list-alt icon-white\"></i> 重发</a></td>";
		        		else
		        		  s += "<td></td>";

		        		s += "</tr>";
		        	});

		        	$.each($(data)[0].wallet.walletKhsdDatas, function(i, da){
		        		s += "<tr class=\"gradeX\">";
		        		
		        		s += "<td>" + da.id + "</td>";
		        		s += "<td>" + da.tranFunc + "</td>";
		        		s += "<td>" + da.sendParames + "</td>";
		        		s += "<td>" + da.recvParames + "</td>";
		        		s += "<td>" + da.rspCode + "<br />" + da.rspMsg + "</td>";
		        		
		        		if (da.rspCode == "01")
		        		  s += "<td><a class=\"btn btn-primary btnReExec\" khsdVal=\"\" data-toggle=\"modal\" data-target=\"#reExecDialog\"> <i class=\"icon-list-alt icon-white\"></i> 重发</a></td>";
		        		else
		        		  s += "<td></td>";

		        		s += "</tr>";
		        	});

		        	s += "</tbody>";
		        	s += "</table>";

		        	$("#_zjjzList").html(s);
                  
					$("#errorDialog").modal("show");
				}
			}
		});
		return true;
	});

});

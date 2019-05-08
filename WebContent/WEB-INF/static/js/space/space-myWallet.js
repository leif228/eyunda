$(document).ready(function() {
	
	// 查寻账务
	$('.findWallet').live("click", function() {
		$("#pageform").submit();
	});
	
	$("#walletPayForm").validate({
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
	
	$("#fillDialogForm").validate({
        rules: {
        	payMoney: {
            	number:true,
                required: true,
                checkPrice: true,
                money: true
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
	
	$("#fetchDialogForm").validate({
        rules: {
            fetchMoney: {
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
	
	$("#turnDialogForm").validate({
        rules: {
        	accountName: {
                required: true,
            },
            cardNo: {
             	creditcard: true,
                required: true
            }, 
            turnMoney: {
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
	
	$("#payDialogForm").validate({
        rules: {
        	accountName: {
                required: true,
            },
            paypwd: {
            	required: true,
            },
            payMoney: {
            	number:true,
                required: true,
                checkPrice: true,
                money: true
            },
            suretyDay: {
            	required: true,
            	digits:true,
            	suretyDay : true,
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
	
	$("#receiveDialogForm").validate({
        rules: {
            receiveMoney: {
            	number:true,
                required: true,
                checkPrice: true,
                money: true
            },suretyDay: {
            	required: true,
            	number:true,
            	suretyDay : true,
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
	
	$(".fill").live("click", function(){
		$("#fillMoney").val("0.00");
		$("#fillDialog").modal("show");
	});
	
	$(".btnFill").live("click", function(){
		if($("#fillDialogForm").valid()){
			$("#fillDialog").modal("hide");
			$(".modal-backdrop").remove();
			$("#fillDialogForm").submit();
		}
	});
	
	$(".fetch").live("click", function(){
		if("no"==settedPW){
			alert('请先绑定提现银行卡！');
		}else{
			$("#showFetchInfo").css("display", "block");
			$("#showFetchNext").css("display", "none");
			$(".control-group").removeClass("error");
			$("#fetchBank")[0].selectedIndex =0;
			$("#fetchMoney").val("0.00");
			$("#fetchId").val("0");
			
			var s = "";
	        s += "<div class=\"modal-footer\">";
	        s += "  <a class=\"btn\" data-dismiss=\"modal\">";
	        s += "    <i class=\"icon icon-off\"></i> 关闭";
	        s += "  </a>";
	        s += "  <a class=\"btn btn-primary nextFetch\">";
	        s += "    <i class=\"icon-ok icon-white\"></i> 下一步";
	        s += "  </a>";
	        s += "</div>";
			
	        $("#fetchOpt").html(s);
	        
			$("#fetchDialog").modal("show");
		}
	});
	
	$('.nextFetch').live("click",function (){
		if($("#fetchDialogForm").valid()){
			var fetchMoney = $("#fetchMoney").val();
			if(!isNaN(fetchMoney)){
				var n = Number(fetchMoney);
				
				if(n>totalTranOutAmount){
					alert("最多可提现"+totalTranOutAmount+"元!");
					return false;
				}
			
				$("#showFetchInfo").css("display", "none");
				$("#showFetchNext").css("display", "block");
		        
		        var fetchBankInfo = $("#fetchBank option:selected").text();
		        
		        
		        var p = "<p>你确认要从 易运达钱包 提现人民币" + fetchMoney + "元 到" + fetchBankInfo + "？</p>";
		        $("#showFetchNext").html(p);
		        
		        var s = "";
		        s += "<div class=\"modal-footer\">";
		        s += "  <a class=\"btn\" data-dismiss=\"modal\">";
		        s += "    <i class=\"icon icon-off\"></i> 关闭";
		        s += "  </a>";
		        s += "  <a class=\"btn btn-primary btnFetch\">";
		        s += "    <i class=\"icon-ok icon-white\"></i> 确认提现";
		        s += "  </a>";
		        s += "</div>";
				
		        $("#fetchOpt").html(s);
			}else
				alert("请输入数字！");
		}
	});
	
	// 提现
	$(".btnFetch").live("click", function(){
		// 发送ajax请求服务器货物绑定的银行卡列表
		if($("#fetchDialogForm").valid()){
			$("#fetchDialogForm").ajaxSubmit({
				type : "post",
				url : _rootPath + "/space/wallet/myWallet/fetch",
				datatype : "json",
				success : function(data){
					$("#fetchDialog").modal("hide");
					$(".modal-backdrop").remove();
					var returnCode = $(data)[0].returnCode;
					if(returnCode == 'Failure'){
						alert($(data)[0].message);
					} else {
//						formSubmit($(data)[0].content);
						var paymentNo = $(data)[0].paymentNo;
						var serialNo = $(data)[0].serialNo;
						var revMobilePhone = $(data)[0].revMobilePhone;
						$("#submitDialog").modal("show");
						$(".mobile4").text("手机后四位"+revMobilePhone);
						$(".paymentNo").val(paymentNo);
						$(".serialNo").val(serialNo);
						
					}
					return false;
				}
			});
		}
	});
	
	// 钱包列表中点击提现按钮
	$(".walletFetch").live("click", function(){
		$.ajax({
			type: "post",
			url: _rootPath + "/space/wallet/myWallet/getFetchWallet",
			data: {walletId : $(this).attr("idVal")},
			datatype: "json",
			success: function(data){
				var returnCode = $(data)[0].returnCode;
				if(returnCode == 'Failure'){
					alert($(data)[0].message);
				} else {
					$("#showFetchInfo").css("display", "block");
					$("#showFetchNext").css("display", "none");
					$(".control-group").removeClass("error");
					
					for(var i=0; i<$(data)[0].bankDatas.length; i++){
						if($(data)[0].bankDatas[i].bankCode == $(data)[0].walletData.rcvBankCode)
							$("#fetchBank")[0].selectedIndex =i;
					}
					
					$("#fetchMoney").val($(data)[0].walletData.totalFee);
					$("#fetchId").val($(data)[0].walletData.id);
					
					var s = "";
			        s += "<div class=\"modal-footer\">";
			        s += "  <a class=\"btn\" data-dismiss=\"modal\">";
			        s += "    <i class=\"icon icon-off\"></i> 关闭";
			        s += "  </a>";
			        s += "  <a class=\"btn btn-primary nextFetch\">";
			        s += "    <i class=\"icon-ok icon-white\"></i> 下一步";
			        s += "  </a>";
			        s += "</div>";
					
			        $("#fetchOpt").html(s);
			        
					$("#fetchDialog").modal("show");
				}
			}
		});
	});
	
	$("#submitDialogForm").validate({
		rules: {
			MessageCode: {
				required: true
			},
			paypwd: {
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
	 function newWin(url) {
         var a = document.createElement('a');
         a.setAttribute('href', url);
         a.setAttribute('target', '_blank');
         document.body.appendChild(a);
         a.click();
	 }
	// 提交验证码
	$(".btnSubmit").live("click", function(){
		if($("#submitDialogForm").valid()){
			
			$("#submitDialogForm").ajaxSubmit({
				type : "post",
				url : _rootPath + "/space/wallet/myWallet/surefetch",
				datatype : "json",
				success : function(data){
					var returnCode = $(data)[0].returnCode;
					if(returnCode == 'Failure'){
						alert($(data)[0].message);
					} else {
						$("#submitDialog").modal("hide");
						$(".modal-backdrop").remove();
//						window.open(_rootPath + "/space/pinganpay/pinganCallBack?result="+$(data)[0].content);
//						newWin(_rootPath + "/space/pinganpay/pinganCallBack?result="+$(data)[0].content);
						alert($(data)[0].content);
						window.location.href = _rootPath + "/space/wallet/myWallet";
					}
					return false;
				}
			});
		}
	});
	
	$(".turn").live("click", function(){
		$("#showTurnInfo").css("display", "block");
		$("#showTurnNext").css("display", "none");
		$(".control-group").removeClass("error");
		$("#turnMoney").val("0.00");
		$("#turnToName").val("");
		$("#turnToBankCardNo").val("");
		$("#turnToBank")[0].selectedIndex =0;
		$("#turnBank")[0].selectedIndex =0;
		$("#turnmoney").val("0.00");
		
		var s = "";
        s += "<div class=\"modal-footer\">";
        s += "  <a class=\"btn\" data-dismiss=\"modal\">";
        s += "    <i class=\"icon icon-off\"></i> 关闭";
        s += "  </a>";
        s += "  <a class=\"btn btn-primary nextTurn\">";
        s += "    <i class=\"icon-ok icon-white\"></i> 下一步";
        s += "  </a>";
        s += "</div>";
		
        $("#fetchOpt").html(s);
		
		$("#turnDialog").modal("show");
	});
	
	$('.nextTurn').live("click",function (){
		if($("#turnDialogForm").valid()){
			$("#showTurnInfo").css("display", "none");
			$("#showTurnNext").css("display", "block");
	        
			var rcvBankDesc = $("#turnToBank option:selected").text();
			var rcvAccountName = $("#turnToName").val();
			var rcvCardNo = $("#turnToBankCardNo").val();
			
	        var turnBankInfo = $("#turnBank option:selected").text();
	        
	        var turnMoney = $("#turnMoney").val();
	        
	        var p = "<p>你确认要从" + turnBankInfo +  "转账 人民币" + turnMoney + "元 到 " 
	        + rcvBankDesc + "(" + rcvAccountName + ":" + rcvCardNo + ")？</p>";
	        
	        $("#showTurnNext").html(p);
	        
	        var s = "";
	        s += "<div class=\"modal-footer\">";
	        s += "  <a class=\"btn\" data-dismiss=\"modal\">";
	        s += "    <i class=\"icon icon-off\"></i> 关闭";
	        s += "  </a>";
	        s += "  <a class=\"btn btn-primary btnTurn\">";
	        s += "    <i class=\"icon-ok icon-white\"></i> 确认转账";
	        s += "  </a>";
	        s += "</div>";
			
	        $("#turnOpt").html(s);
		}
	});
	
	// 转账
	$(".btnTurn").live("click", function(){
		if($("#turnDialogForm").valid()){
			$("#turnDialogForm").ajaxSubmit({
				type : "post",
				url : _rootPath + "/space/wallet/myWallet/turn",
				datatype : "json",
				success : function(data){
					$("#turnDialog").modal("hide");
					$(".modal-backdrop").remove();
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
	
	// 付款对话框
	$(".pay").live("click", function(){
		$(".nextPay").removeClass("btnPay");
		$("#payInfo").css("display", "block");
		$("#paypwdDialog").css("display", "none");
		$(".rcvBankCardNo").css("display", "none");
		
		$("#receiveName").val("");
		$("#cardNo").val("");
		$("#reciviceCardNo").val("");
		$("#payMoney").val("0.00");
		$("#remark").val("");
		$("#surety").attr("checked",false);
		$("#suretyDay").val("");
		$("#suretyDay").attr("disabled", true);
		$("#rcvUserId").val("");
		$("#rcvAccountName").val("");
		$("#rcvBankCode").val("");
		$("#rcvCardNo").val("");
		$("#facepaypwd").val("");
		
		$("#payDialog").modal("show");
	});
	
	// 付款下一步对话框
	$(".nextPay").live("click", function(){
		if($("#payDialogForm").valid()){
			$("#payInfo").css("display", "none");
			$("#paypwdDialog").css("display", "block");
			$(this).addClass("btnPay");
		}
	});
	
	// 付款
	$(".btnPay").live("click", function(){
		if($("#payDialogForm").valid()){
			$("#payDialog").modal("hide");
			$(".modal-backdrop").remove();
			$("#payDialogForm").submit();
		}
	});
	
	// 设置资金托管
	$("#surety").live("change", function (){
		$("#suretyDay").val("");
		$("#suretyDay").attr("disabled", true);
		if($("#surety").prop("checked")){
			$("#suretyDay").attr("disabled", false);
		}
	});
	
	// 删除
	$(".delete").live("click", function(){
		$("#deleteId").val($(this).attr("idVal"));
		$("#deleteDialog").modal("show");
	});
	
	// 确认付款
	$(".btnDelete").live("click", function(){
		var walletId = $("#deleteId").val();
		$("#deleteDialog").ajaxSubmit({
			type : "get",
			url : _rootPath + "/space/wallet/myWallet/delete",
			data : {walletId : walletId},
			datatype : "json",
			success : function(data){
				$("#deleteDialog").modal("hide");
				$(".modal-backdrop").remove();
				var returnCode = $(data)[0].returnCode;
				if(returnCode == 'Failure'){
					alert($(data)[0].message);
				} else {
					// alert($(data)[0].message);
					window.location.href = _rootPath + "/space/wallet/myWallet?pageNo=" + $(".pageNo").val();
				}
				return false;
			}
		});
	});
	
	// 确认付款弹出框
	$(".confirmPay").live("click", function(){
		$("#walletId").val($(this).attr("idVal"));
		$("#confirmPayDialog").modal("show");
	});
	
	// 确认付款
	$(".btnConfirm").live("click", function(){
		$.ajax({
			type : "post",
			url : _rootPath + "/space/pinganpay/confirmPay",
			data : {walletId : $("#walletId").val()},
			datatype : "json",
			success : function(data){
				$("#confirmPayDialog").modal("hide");
				$(".modal-backdrop").remove();
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
	});
	
	// 申请退款对话框（资金托管前）
	$(".directRefund").live("click", function(){
		$("#directRefundId").val($(this).attr("idVal"));
		$("#directRefundDialog").modal("show");
	});
	
	// 申请退款（资金托管前）
	$(".btnDirectRefund").live("click", function(){
		$("#directRefundForm").ajaxSubmit({
			type : "post",
			url : _rootPath + "/space/wallet/myWallet/directRefund",
			datatype : "json",
			success : function(data){
				$("#directRefundDialog").modal("hide");
				$(".modal-backdrop").remove();
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
	});
	
	// 申请退款对话框
	$(".refundApply").live("click", function(){
		$("#refundBillId").val($(this).attr("idVal"));
		$("#refundDialog").modal("show");
	});
	
	// 申请退款
	$(".btnRefundApply").live("click", function(){
		$("#refundDialogForm").ajaxSubmit({
			type : "post",
			url : _rootPath + "/space/pinganpay/myWallet/refundapply",
			datatype : "json",
			success : function(data){
				$("#refundDialog").modal("hide");
				$(".modal-backdrop").remove();
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
	});
	
	// 退款处理对话框
	$(".doRefund").live("click", function(){
		$("input:radio[name='applyReply']").eq(0).prop("checked", true);
		$("#doRefundBillId").val($(this).attr("idVal"));
		$("#doRefundDialog").modal("show");
	});
	
	// 退款处理
	$(".btnDoRefund").live("click", function(){
		$("#doRefundDialogForm").ajaxSubmit({
			type : "post",
			url : _rootPath + "/space/pinganpay/myWallet/doRefund",
			datatype : "json",
			success : function(data){
				$("#doRefundDialog").modal("hide");
				$(".modal-backdrop").remove();
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
	});
	
	// 收款设置资金托管
	$("#rcvSurety").live("change", function (){
		$("#rcvSuretyDay").val("");
		$("#rcvSuretyDay").attr("disabled", true);
		if($("#rcvSurety").prop("checked")){
			$("#rcvSuretyDay").attr("disabled", false);
		}
	});
	
	// 收款对话框
	$(".receive").live("click", function(){
		$("#receiveMoney").val("0.00");
		$("#rcvSurety").attr("checked",false);
		$("#rcvSuretyDay").val("");
		$("#rcvSuretyDay").attr("disabled", true);
		$("#receiveDialog").modal("show");
	});
	
	// 
	$(".startSurety").live("click", function(){
		$("#sWalletId").val($(this).attr("idVal"));
		$("#startSuretyDialog").modal("show");
	});
	 
	// 收款生成二维码
	$(".btnReceive").live("click", function(){
		if($("#receiveDialogForm").valid()){
			$("#receiveDialogForm").ajaxSubmit({
				type : "post",
				url : _rootPath + "/space/wallet/myWallet/create",
				datatype : "json",
				success : function(data){
					var returnCode = $(data)[0].returnCode;
					if(returnCode == 'Failure'){
						alert($(data)[0].message);
					} else {
						$(".rcvImage").attr("src", _rootPath+
								"/download/imageDownload?url=" + $(data)[0].img);
						$(".rcvImage").css("width","200px");
						$("#rcvImageShow").css("display", "block");
					}
					return false;
				}
			});
		}
	});
	
	// 资金托管开始
	$(".btnStartSurety").live("click", function(){
		$.ajax({
			type : "post",
			url : _rootPath + "/space/wallet/myWallet/startSurety",
			data : {walletId : $("#sWalletId").val()},
			datatype : "json",
			success : function(data){
				$("#startSuretyDialog").modal("hide");
				$(".modal-backdrop").remove();
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
	});
	
	//
	$(".endSurety").live("click", function(){
		$("#eWalletId").val($(this).attr("idVal"));
		$("#endSuretyDialog").modal("show");
	});
	
	// 资金托管结束
	$(".btnEndSurety").live("click", function(){
		$.ajax({
			type : "post",
			url : _rootPath + "/space/wallet/myWallet/endSurety",
			data : {walletId : $("#eWalletId").val()},
			datatype : "json",
			success : function(data){
				$("#endSuretyDialog").modal("hide");
				$(".modal-backdrop").remove();
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
	});
	
	// 根据登陆名查询用户钱包号
	$(".findUser").live("click", function(){
		$("#rcvUserId").val("");
		$(".rcvBankCardNo").css("display", "none");
		$.ajax({
			type : "post",
			url : _rootPath + "/space/wallet/myWallet/findUserAccount",
			data : {loginName : $("#receiveName").val()},
			datatype : "json",
			success : function(data){
				var returnCode = $(data)[0].returnCode;
				if(returnCode == 'Failure'){
					alert($(data)[0].message);
				} else {
					var s = "";
					$.each($(data)[0].accountDatas, function(i, accountData){
						if (i==0)
							$("#rcvUserId").val(accountData.userId);
						
						s += "<option value=\"" + accountData.userId + "-" + accountData.accounter + "-EYUNDA" + "-" + accountData.accountNo + "\">";
						s += "易运达钱包" + "(" + accountData.accounter + ":" + accountData.accountNo + ")";
						s += "</option>";
					})
					
					$("#rcvBank").html(s);
					$(".rcvBankCardNo").css("display", "block");
				}
				
				return false;
			}
		});
	});
	
	$("#rcvBank").live("change", function(){
		var rcvCardInfo = $(this).val().split("-");
		$("#rcvUserId").val(rcvCardInfo[0]);
	});
	
	//支付管理按钮
	$("#paymanage").live("click", function(){
		if("no"==settedPW){
			$(".setpw").css("display", "block");
			$(".settedpw_div").css("display", "none");
		}else{
			$(".setpw").css("display", "none");
			$(".settedpw_div").css("display", "block");
		}
		return false;
	});
	//设置支付密码按钮
	$(".setpw").live("click", function(){
		$.ajax({
			type : "get",
			url : _rootPath + "/space/wallet/myWallet/setpw",
			data : {type : "S"},
			datatype : "json",
			success : function(data){
				var returnCode = $(data)[0].returnCode;
				if(returnCode == 'Failure'){
					alert($(data)[0].message);
				} else {
					formSubmit($(data)[0].content);
				}
				return false;
			}
		});
		
		return false;
	});
	//修改支付密码按钮
	$(".modifypw").live("click", function(){
		$.ajax({
			type : "get",
			url : _rootPath + "/space/wallet/myWallet/setpw",
			data : {type : "C"},
			datatype : "json",
			success : function(data){
				var returnCode = $(data)[0].returnCode;
				if(returnCode == 'Failure'){
					alert($(data)[0].message);
				} else {
					formSubmit($(data)[0].content);
				}
				return false;
			}
		});
		return false;
	});
	//重置支付密码按钮
	$(".resetpw").live("click", function(){
		$.ajax({
			type : "get",
			url : _rootPath + "/space/wallet/myWallet/setpw",
			data : {type : "R"},
			datatype : "json",
			success : function(data){
				var returnCode = $(data)[0].returnCode;
				if(returnCode == 'Failure'){
					alert($(data)[0].message);
				} else {
					formSubmit($(data)[0].content);
				}
				return false;
			}
		});
		return false;
	});
	//会员手机号修改按钮
	$(".modifymobil").live("click", function(){
		$.ajax({
			type : "get",
			url : _rootPath + "/space/wallet/myWallet/setpw",
			data : {type : "M"},
			datatype : "json",
			success : function(data){
				var returnCode = $(data)[0].returnCode;
				if(returnCode == 'Failure'){
					alert($(data)[0].message);
				} else {
					formSubmit($(data)[0].content);
				}
				return false;
			}
		});
		return false;
	});
	function formSubmit(content){
		$("#fetchOpt").html("");
		var s = "";
		s += "<form id=\"PayForm\" name=\"PayForm\" action=\"https://testebank.sdb.com.cn/corporbank/nonpartyVerify.do\" method=\"post\">"; 
		s += "<input type=\"hidden\" name=\"orderid\" value=\"" + content.orderid + "\"/>";
		s += "<input type=\"hidden\" name=\"P2PCode\" value=\"" + content.P2PCode + "\"/>";
		s += "<input type=\"hidden\" name=\"P2PType\" value=\"" + content.P2PType + "\"/>";
		s += "<input type=\"hidden\" name=\"thirdCustId\" value=\"" + content.thirdCustId + "\"/>";
		s += "<input type=\"hidden\" name=\"custAccId\" value=\"" + content.custAccId + "\"/>";
		s += "<input type=\"hidden\" name=\"name\" value=\"" + content.name + "\"/>";
		s += "<input type=\"hidden\" name=\"idType\" value=\"" + content.idType + "\"/>";
		s += "<input type=\"hidden\" name=\"idNo\" value=\"" + content.idNo + "\"/>";
		s += "<input type=\"hidden\" name=\"mobile\" value=\"" + content.mobile + "\"/>";
		s += "<input type=\"hidden\" name=\"type\" value=\"" + content.type + "\"/>";
		s += "<input type=\"hidden\" name=\"orig\" value=\"" + content.orig + "\"/>";
		s += "<input type=\"hidden\" name=\"notifyUrl\" value=\"" + content.notifyUrl + "\"/>";
		s += "<input type=\"hidden\" name=\"returnurl\" value=\"" + content.returnurl + "\"/>";
		s += "</form>";
		s += "<script>document.forms['PayForm'].submit();</script>";
		
	    $("#fetchOpt").html(s);
	}
	
	$(".orderPay").live("click", function(){
		$("#walletPaypwd").val("");
		$("#walletPayId").val($(this).attr("idVal"));
		if($(this).attr("typeVal") != "fill")
			$("#walletPayDialog").modal("show");
		else
			$("#walletPayForm").submit();
	});
	
	// 支付
	$(".btnOrderPay").live("click", function(){
		if($("#walletPayForm").valid()){
			$("#walletPayDialog").modal("hide");
			$(".modal-backdrop").remove();
			$("#walletPayForm").submit();
		}
	});

});
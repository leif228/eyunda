$(document).ready(function() {
	
	// 重新发送
	$("#resend").on("click", function() {
		var userId = $("#userNum").attr("real-value") || "";
		var money = $("#money").val();
		var patten = /\d+(\.\d{2})?$/;
		if(""==userId){
			alert("请先选择帐号");
			return;
		}
		if(!patten.test(money)){
			alert("请输入正确的金额！");
			return;
		}
		if(""!=userId && patten.test(money)){
			$("#num").html($("#userNum").val());
			$("#moneyTotal").html($("#money").val());
			$("#resend").attr('disabled',"true");
			$("#resendDialog").modal("show");
		}

	});
	
	$("#btnSend").on("click", function() {
		alert("服务已经删除");
		return;
		var userId = $("#userNum").attr("real-value") || "";
		var money = $("#money").val();
		var pass = $("#pass").val();
		var patten = /\d+(\.\d{2})?$/;
		if(""==userId){
			alert("请先选择帐号");
			return;
		}
		if(!patten.test(money)){
			alert("请输入正确的金额！");
			return;
		}
		if(""!=userId && patten.test(money)){
			$("#btnSend").attr('disabled',"true");
			alert("服务已经删除");
		}

	});
	$("#userNum").autocomplete({
		source:function(query, process)
		{
			$.ajax({
				url: _rootPath + '/manage/wallet/walletQuery/findUserCardNo',
				type: 'GET',
				dataType: 'JSON',
				async: true,
				data: 'loginName=' + query ,
				success: function(data){
					if(data.returnCode="Success"){
						var res = data.accountDatas;
						return process(data.accountDatas);
					}
				}
			});
		},formatItem:function(item){
	        return item.accounter+"( 帐号:"+item.accountNo+")";
	    },setValue:function(item){
	        return {'data-value':item.accounter+"( 帐号:"+item.accountNo+")",'real-value':item.userId};
	    }
	 
	});
});

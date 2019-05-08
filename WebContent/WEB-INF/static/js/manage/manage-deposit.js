$(document).ready(
function() {

	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();

	$('select').select2();

	$("span.icon input:checkbox, th input:checkbox").click(
		function() {
			var checkedStatus = this.checked;
			var checkbox = $(this).parents('.widget-box').find(
					'tr td:first-child input:checkbox');
			checkbox.each(function() {
				this.checked = checkedStatus;
				if (checkedStatus == this.checked) {
					$(this).closest('.checker > span').removeClass(
							'checked');
				}
				if (this.checked) {
					$(this).closest('.checker > span').addClass(
							'checked');
				}
			});
		});

	// 退款
	$(".btnDepositRefund").live("click", function() {
		$("#depositId").val($(this).attr("idVal"));
		$("#depositRefundDialog").modal("show");

		return true;
	});

	// 批量退款
	$("#btnDepositAllRefund").live("click",function() {
		var vals = $("input[name='selectThis']:checked").map(
				function() {
					return this.value;
				}).get().join();
		if(vals==null || ""==vals){
			alert("您还未选择，请选择！");
			return ;
		}
		$("#depositVals").val(vals);
		$("#depositAllRefundDialog").modal("show");
	});

	$("#nowStatus").change(function() {
		window.location.href = _rootPath+ "/manage/settle/deposit?nowStatus="+ $("#nowStatus").val();
	});

	function SelAll() {
		var all = document.getElementsByName("selectAll")[0];
		var items = document.getElementsByName("selectThis");
		if (all.checked == true) {
			for (var i = 0; i < items.length; i++) {
				items[i].checked = true;
			}
		} else {
			for (var i = 0; i < items.length; i++) {
				items[i].checked = false;
			}
		}
	}

	$(".serachSettle").live("click",function() {
		window.location.href = _rootPath
				+ "/manage/settle/deposit?nowStatus="
				+ $("#nowStatus").val() + "&orderId="
				+ $("#orderId").val();
	});

});
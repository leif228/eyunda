$(document).ready(
function() {

	$('input[type=checkbox],input[type=radio],input[type=file]')
			.uniform();

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

	// 转账
	$(".btnGiro").live("click", function() {

		$("#giroId").val($(this).attr("idVal"));

		$("#giroDialog").modal("show");

		return true;
	});

	// 批量转账
	$("#btnGiroAll").live("click",function() {
		var vals = $("input[name='selectThis']:checked").map(
				function() {
					return this.value;
				}).get().join();
		if(vals==null || ""==vals){
			alert("您还未选择，请选择！");
			return ;
		}
		$("#giroVals").val(vals);
		$("#giroAllDialog").modal("show");
	});

	$("#nowStatus").change(function() {
		window.location.href = _rootPath+ "/manage/settle/giro?nowStatus="+ $("#nowStatus").val();
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
				+ "/manage/settle/giro?nowStatus="
				+ $("#nowStatus").val() + "&orderId="
				+ $("#orderId").val();
	});

});
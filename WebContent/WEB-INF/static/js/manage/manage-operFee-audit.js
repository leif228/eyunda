$(document).ready(function() {

	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();

	$('select').select2();
	
	$("span.icon input:checkbox, th input:checkbox").click(function() {
		var checkedStatus = this.checked;
		var checkbox = $(this).parents('.widget-box').find('tr td:first-child input:checkbox');		
		checkbox.each(function() {
			this.checked = checkedStatus;
			if (checkedStatus == this.checked) {
				$(this).closest('.checker > span').removeClass('checked');
			}
			if (this.checked) {
				$(this).closest('.checker > span').addClass('checked');
			}
		});
	});	

	// 取消
	$(".btnUnaudit").live("click", function() {

		$("#unAuditId").val($(this).attr("idVal"));

		$("#unAuditDialog").modal("show");

		return true;
	});

	// 审核
	$(".btnAudit").live("click", function() {

		$("#auditId").val($(this).attr("idVal"));

		$("#auditDialog").modal("show");

		return true;
	});

	$("#nowStatus").change(function() {
		window.location.href = _rootPath+ "/manage/settle/audit?nowStatus="+$("#nowStatus").val();
	});

	// 通过全部审核
	$("#btnAuditAll").live("click",function() {
		var vals = $("input[name='selectThis']:checked").map(function(){
					return this.value;}).get().join();
		if(vals==null || ""==vals){
			alert("您还未选择，请选择！");
			return ;
		}
		$("#auditVals").val(vals);
		$("#auditAllDialog").modal("show");
	});
	
	// 取消全部审核
	$("#btnUnAuditAll").live("click",function() {
		var vals = $("input[name='selectThis']:checked").map(function(){
			return this.value;}).get().join();
		if(vals==null || ""==vals){
			alert("您还未选择，请选择！");
			return ;
		}
		$("#unAuditVals").val(vals);
		$("#unAuditAllDialog").modal("show");
	});
	
	function SelAll(){
		var all=document.getElementsByName("selectAll")[0];
		var items=document.getElementsByName("selectThis");
		if(all.checked==true){
			for(var i=0;i<items.length;i++){
				items[i].checked=true;
			}
		}else{
			for(var i=0;i<items.length;i++){
				items[i].checked=false;
			}
		}
	}
	
	$(".serachSettle").live("click",function() {
		window.location.href = _rootPath
				+ "/manage/settle/audit?nowStatus="
				+ $("#nowStatus").val() + "&orderId="
				+ $("#orderId").val();
	});

});
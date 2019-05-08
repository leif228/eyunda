$(document).ready(
		function() {
			$('input[type=checkbox],input[type=radio],input[type=file]')
					.uniform();

			$('select').select2();

			// 查寻账务
			$('.findSettle').live("click", function() {
				$("#pageform").submit();
			});

			// 余额宝转入
			$(".btnin").live("click", function() {

				$("#btninDialog").modal("show");

			});

			// 余额宝转出
			$(".btnout").live("click", function() {

				$("#btnoutDialog").modal("show");

			});

			$(".btn_surein").live("click",function() {
						if ($("#btninDialogForm").valid())
							$("#btninDialogForm").submit();
							
						return true;
			});

			$(".btn_sureout").live("click", function() {
				if ($("#btnoutDialogForm").valid())
					$("#btnoutDialogForm").submit();

				return true;
			});
			$(".load").live(
					"click",
					function() {
						var tab = $(this).attr("tab");
						window.location.href = _rootPath
								+ "/space/settle/mySettle?tab=" + tab;

						return true;
					});

			$("#btnoutDialogForm").validate({
				rules : {
					accounter : {
						required : true
					},
					money : {
						required : true
					},
					accountNo : {
						required : true
					}
				},
				errorClass : "help-inline",
				errorElement : "span",
				highlight : function(element, errorClass, validClass) {
					$(element).parents('.control-group').addClass('error');
				},
				unhighlight : function(element, errorClass, validClass) {
					$(element).parents('.control-group').removeClass('error');
					$(element).parents('.control-group').addClass('success');
				}
			});
			$("#btninDialogForm").validate({
				rules : {
					accounter : {
						required : true
					},
					money : {
						required : true
					},
					accountNo : {
						required : true
					}
				},
				errorClass : "help-inline",
				errorElement : "span",
				highlight : function(element, errorClass, validClass) {
					$(element).parents('.control-group').addClass('error');
				},
				unhighlight : function(element, errorClass, validClass) {
					$(element).parents('.control-group').removeClass('error');
					$(element).parents('.control-group').addClass('success');
				}
			});

		});
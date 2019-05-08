$(document).ready(
		function() {
			$('input[type=checkbox],input[type=radio],input[type=file]')
					.uniform();

			/*$('select').select2();*/

			$(".btnin").live("click", function() {

				$("#btninDialog").modal("show");

			});

			$(".btn_surein").live("click",function() {
				$("#btninDialogForm").submit();
							
				return true;
			});
			
			Date.prototype.Format = function(formatStr) {
				var str = formatStr;
				str = str.replace(/yyyy/, this.getFullYear());
				str = str.replace(/mm/, this.getMonth() + 1);
				return str;
			}
			$.fn.datetimepicker.dates['zh'] = {
				days : [ "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" ],
				daysShort : [ "日", "一", "二", "三", "四", "五", "六", "日" ],
				daysMin : [ "日", "一", "二", "三", "四", "五", "六", "日" ],
				monthsShort : [ "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月",
						"9月", "10月", "11月", "12月" ],
				months : [ "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11",
						"12" ],
				meridiem : [ "上午", "下午" ],
				today : "今天"
			};
			$('.form_datetimeStart').datetimepicker({
				language : 'zh',
				format : 'yyyy年mm月',
				autoclose : true,
				startView : 3,
				minView : 3,
				keyboardNavigation : true,
				forceParse : true,
				startDate : new Date(new Date().getFullYear()-1,new Date().getMonth(),1),
				viewSelect : 'year'
			}).on('changeMonth', function(ev) {
				var date = ev.date;

				setEndMonth(date, $('#combo').val());
			});
			function addMonths(d, n) {
				var t = new Date(d);
				t.setMonth(t.getMonth() + n);
				if (t.getDate() != d.getDate()) {
					t.setDate(0);
				}
				return t;
			}
			function setEndMonth(startMonthDate, comboVal) {
				var date = startMonthDate;
				if ('year' == comboVal) {
					date = addMonths(date, 12-1);
					$('#money').val(960.0);
				} else if ('half' == comboVal) {
					date = addMonths(date, 6-1);
					$('#money').val(510.0);
				} else if ('quarter' == comboVal) {
					date = addMonths(date, 3-1);
					$('#money').val(270.0);
				} else if ('month' == comboVal) {
					date = addMonths(date, 1-1);
					$('#money').val(100.0);
				}
				var endMonth_value = date.Format("yyyy年mm月")
				var m = endMonth_value.substring(5,endMonth_value.length-1);
				if(m<10)
					endMonth_value = endMonth_value.substring(0,5)+"0"+m+"月";
				$('#endMonth').val(endMonth_value);
			}

			$("#combo").change(function() {
				var combo = $("#combo").val();
				var startMonth = $("#startMonth").val();

				var arr = startMonth.substring(0, startMonth.length-1).split("年");
				var startMonthDate = new Date(arr[0], parseInt(arr[1]) - 1, 1);

				setEndMonth(startMonthDate, combo);
			});
			$("#ship").change(function() {
				window.location.href = _rootPath + "/space/ship/myShip/dues?shipId="+$(this).val();
			});
			// 退款
			$(".btnRefund").live("click", function() {
				var id = $(this).attr("idVal");
			    
				$.ajax({
					type: 'GET',
					url : _rootPath + "/space/ship/myShip/duesRefund",
					data : {id : id},
					datatype : "json",
					success : function(data){
						var data = $(data)[0];
						var returnCode = data.returnCode;
				        var message = data.message;
				        if (returnCode == "Failure") {
				        	alert(message);
				        	return false;
				        } else {
				        	window.location.href = "https://my.alipay.com/portal/i.htm";
				        }
					}
				})
				
				return true;
			});
			// 支付
			$(".btnPay").live("click", function() {
				var id = $(this).attr("idVal");
				window.location.href = _rootPath + "/space/ship/myShip/duesPay?id="+id;
				
				return true;
			});
			$(".btnDelete").live("click", function() {
				$("#delId").val($(this).attr("idVal"));

				$("#deleteDialog").modal("show");

				return true;
			});
});
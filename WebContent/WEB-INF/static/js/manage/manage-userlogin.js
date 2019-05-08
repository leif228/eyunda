$(document).ready(function() {
	
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	$('select').select2();
	
	 var date = new Date();
	  $(".form_datetimeStart").datetimepicker({
	      format: 'yyyy-mm-dd',
	      minView:'month',
	      language: 'zh-CN',
	      autoclose:true
	  }).on("changeDate",function(ev){
		  var startTime = $("#startDate").val();
	      $(".form_datetimeEnd").datetimepicker("setStartDate",startTime);
	  });
	  
	  $(".form_datetimeEnd").datetimepicker({
	      format: 'yyyy-mm-dd',
	      minView:'month',
	      language: 'zh-CN',
	      autoclose:true,
	      startDate:date
	  }).on("changeDate",function(ev){
		  var endTime = $("#endDate").val();
	      $(".form_datetimeStart").datetimepicker("setEndDate",endTime);
	  });
	  
	  $('.removeStartTime').click(function() {
	      $('#startDate').val("");
	      $('.form_datetimeEnd').datetimepicker('setStartDate');
	  });
	  
	  $('.removeEndTime').click(function() {
	      $('#endDate').val("");
	      $('.form_datetimeStart').datetimepicker('setEndDate');
	  });
	  
	  $("#datetimeEnd").on("change", function (e) {
   	  var start = $('#startDate').val();
   	  var end = $('#endDate').val();
   	  if(start>end){
   		  $('#endDate').val(start);
   		  $('#datetimeEnd').datetimepicker('update');
   		  return false;
   	  }
     });

});
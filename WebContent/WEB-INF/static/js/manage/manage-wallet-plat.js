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

});

//
function btnBuy(waresId) {
	
	$.ajax({
		type: 'GET',
		url : _rootPath + "/portal/home/gasWares",
		data : {id : waresId},
		datatype : "json",
		success : function(data){
			var data = $(data)[0];
			var returnCode = data.returnCode;
	        var message = data.message;
	        if (returnCode == "Failure") {
	        	alert(message);
	        	return false;
	        } else {
/*	        	var ships = data.ships;
	        	for (var i = 0; i < ships.length; i++) {
	        		if(i==0){
	        			$("#ship").append("<option value='" + ships[i].id + "' selected='selected'>" + ships[i].shipName + "</option>");
	        			continue;
	        		}
	        		$("#ship").append("<option value='" + ships[i].id + "'>" + ships[i].shipName + "</option>");
	        	}
*/	        	
	        	$("#waresId").val(message.id);
	        	$(".waresId").html(message.id);
	        	$(".waresName").html(message.waresName);
	        	$(".price").html(message.price);
	        	$("#tradeMoney").val(message.price);
	        	
	        	$("#btninDialog").modal("show");
	        }
		}
	})
}

$(".btn_surein").live("click",function() {
	var saleCount = $('#saleCount').val();
	if(saleCount==""||isNaN(saleCount)){
		$('#saleCount').focus();
		alert("请输入购买数量！");
		return false;
	}
	$("#btninDialogForm").submit();
				
	return true;
});

//数量失去焦点
$('#saleCount').live("blur",function (){
	  var count = $(this).val();
	  if(count==""||isNaN(count)){
		  $(this).val("");
		  return false;
	  }
	
	  var priceVal=$(".price").text();
	  if(priceVal != "" && typeof(priceVal) != "undefined"){
		  var totalPriceVal = priceVal*1*count;
		  $("#tradeMoney").val(totalPriceVal);
	  }
	  return false;
});
function ValidateNumber(e, pnumber){

	if (!/^\d+$/.test(pnumber)){
		$(e).val("");
		alert("请输入正确的数字！");
		return false;
	}
    return true;

}

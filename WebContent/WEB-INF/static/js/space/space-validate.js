$(document).ready(function(){
	// addMethod：name, method, message
	jQuery.validator.addMethod("checkPrice", function(value,element) {
		var decimal = /^-?\d+(\.\d{1,2})?$/;
		return this.optional(element) || (decimal.test(value));
	},$.validator.format("请最多输入2位小数"));
	
	jQuery.validator.addMethod("money", function(value,element) {
		return this.optional(element) || (value > 0.00);
	},$.validator.format("金额必须大于0.00元"));
	
	jQuery.validator.addMethod("suretyDay", function(value,element) {
		function check(){
			if(value >= 0 && value <= 30)
				return true;
			else 
				return false;
		}
		return this.optional(element) || check();
	},$.validator.format("资金托管期为0-30天"));
	
})
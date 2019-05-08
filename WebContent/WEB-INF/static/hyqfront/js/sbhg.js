$(function(){
	$(".m3_rb ul li .rsp").hide();	
	$(".m3_rb ul li").hover(function(){
		$(this).find(".rsp").stop().fadeTo(160,0.5)
		$(this).find(".text").stop().animate({left:'0'}, {duration: 160})
	},
	function(){
		$(this).find(".rsp").stop().fadeTo(160,0)
		$(this).find(".text").stop().animate({left:'300'}, {duration: "fast"})
		$(this).find(".text").animate({left:'-300'}, {duration: 0})
	});
});
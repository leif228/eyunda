$(".c1 ul li").hover(function(){
	$(this).find(".txt").stop().animate({height:"230px"},400);
	$(this).find(".txt h3").stop().animate({paddingTop:"80px"},400);
},function(){
	$(this).find(".txt").stop().animate({height:"25px"},400);
	$(this).find(".txt h3").stop().animate({paddingTop:"0px"},400);
})
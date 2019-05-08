$(document).ready(function(){
						   
	function liMouseOn(){
		$(".zhanshi_tp .ulBigPic li").attr("class","");
		for(var i=0; i<$(".zhanshi_tp .ulBigPic li").length;i++) {
			(function(j){
				$(".zhanshi_tp .ulBigPic li:eq("+j+")").mouseover(function(){
					if($(".zhanshi_tp").attr("class") == "dSmallList" || $(".zhanshi_tp").attr("class") == "dMiddleList") {
						if($(this).attr("class") != "liSelected") {
							$(this).attr("class","liSelected");
						}
					}
				});
				$(".zhanshi_tp .ulBigPic li:eq("+j+")").mouseout(function(){
					if($(".zhanshi_tp").attr("class") == "dSmallList" || $(".zhanshi_tp").attr("class") == "dMiddleList") {
						if($(this).attr("class") == "liSelected") {
							$(this).attr("class","");
						}
					}
				});
			}) (i);
		}
	}
	
	var curPic=0,nextPic=-1,prePic=-1;preShowPic=-1;
	var allPic=$(".zhanshi_tp .ulBigPic li").length;
	
	function numInit(num){
		if(num=="init"){
			if(allPic > 1) {
				nextPic=curPic+1;
				prePic=allPic-1;
			}else if(allPic == 1){
				nextPic=0;
				prePic=0;
			}
			$(".zhanshi_tp .ulBigPic li:eq("+curPic+")").attr("class","liSelected");
		}else if(num == "plus"){
			preShowPic=curPic;
			prePic=curPic;
			curPic=nextPic;
			if(curPic < (allPic-1)) {
				nextPic=curPic+1;
			}else if(curPic == (allPic-1)) {
				nextPic=0;
			}
			$(".zhanshi_tp .ulBigPic li:eq("+curPic+")").attr("class","liSelected");
			if(preShowPic != curPic) {
				$(".zhanshi_tp .ulBigPic li:eq("+preShowPic+")").attr("class","");
			}
		}else if(num == "minus") {
			preShowPic=curPic;
			nextPic=curPic;
			curPic=prePic;
			if(curPic > 0) {
				prePic=curPic-1;
			}else if(curPic == 0 && allPic > 1) {
				prePic=allPic-1;
			}
			$(".zhanshi_tp .ulBigPic li:eq("+curPic+")").attr("class","liSelected");
			if(preShowPic != curPic) {
				$(".zhanshi_tp .ulBigPic li:eq("+preShowPic+")").attr("class","");
			}
		}else{
			preShowPic=curPic;
			curPic=num;
			if(curPic == (allPic-1)) {
				nextPic=0;
				if(allPic > 1) {
					prePic=curPic-1;
				}else if(allPic == 1) {
					prePic=0;
				}
			}else if(curPic == 0) {
				prePic=allPic-1;
				if(allPic > 1) {
					nextPic=1;
				}else if(allPic == 1) {
					nextPic=0;
				}
			}else{
				nextPic=curPic+1;
				prePic=curPic-1;
			}
			$(".zhanshi_tp .ulBigPic li:eq("+curPic+")").attr("class","liSelected");
			if(preShowPic != curPic) {
				$(".zhanshi_tp .ulBigPic li:eq("+preShowPic+")").attr("class","");
			}
		}
		//alert("curPic="+curPic+"/nextPic="+nextPic+"/prePic="+prePic+"/preShowPic="+preShowPic);
	}
	
	function btnAInit(){
		if(allPic < 2) {
			$("#sLeftBtnA").attr("class","sLeftBtnABan");
			$("#sRightBtnA").attr("class","sRightBtnABan");
		}else{
			if(curPic == 0) {
				$("#sLeftBtnA").attr("class","sLeftBtnABan");
				$("#sRightBtnA").attr("class","sRightBtnA");
			}else if(curPic == (allPic-1)) {
				$("#sLeftBtnA").attr("class","sLeftBtnA");
				$("#sRightBtnA").attr("class","sRightBtnABan");
			}else{
				$("#sLeftBtnA").attr("class","sLeftBtnA");
				$("#sRightBtnA").attr("class","sRightBtnA");
			}
		}
	}
	
	function btnBInitA(){
		if(allPic > 5) {
			$("#sRightBtnB").attr("class","sRightBtnB");
		}
	}
	
	function btnBInitB(){
		if(curPic > 2 && (allPic-curPic) > 4) {
			if($("#sLeftBtnB").attr("class") != "sLeftBtnB") {$("#sLeftBtnB").attr("class","sLeftBtnB");}
			if($("#sRightBtnB").attr("class") != "sRightBtnB") {$("#sRightBtnB").attr("class","sRightBtnB");}
		}else if(curPic < 3){
			if($("#sLeftBtnB").attr("class") != "sLeftBtnBBan") {$("#sLeftBtnB").attr("class","sLeftBtnBBan");}
			if(allPic > 5) {
				if($("#sRightBtnB").attr("class") != "sRightBtnB") {$("#sRightBtnB").attr("class","sRightBtnB");}
			}else{
				if($("#sRightBtnB").attr("class") != "sRightBtnBBan") {$("#sRightBtnB").attr("class","sRightBtnBBan");}
			}
		}else if(curPic > (allPic-4)) {
			if($("#sRightBtnB").attr("class") != "sRightBtnBBan") {$("#sRightBtnB").attr("class","sRightBtnBBan");}
			if(allPic > 5) {
				if($("#sLeftBtnB").attr("class") != "sLeftBtnB") {$("#sLeftBtnB").attr("class","sLeftBtnB");}
			}else{
				if($("#sLeftBtnB").attr("class") != "sLeftBtnBBan") {$("#sLeftBtnB").attr("class","sleftBtnBBan");}
			}
		}
	}
	
	
	function smallPicSelected(){
		if(!$(".zhanshi_tp .ulSmallPic li:eq("+curPic+")").hasClass("liSelected")) {$(".zhanshi_tp .ulSmallPic li:eq("+curPic+")").addClass("liSelected");}
		if(preShowPic!=(-1)) {
			if($(".zhanshi_tp .ulSmallPic li:eq("+preShowPic+")").hasClass("liSelected")) {
				$(".zhanshi_tp .ulSmallPic li:eq("+preShowPic+")").removeClass("liSelected");
			}
		}
	}
	
	
	function smallPicScroll(){
		if(curPic != preShowPic) {
			var leftPosition=0;
			if(curPic>2 && curPic<($(".zhanshi_tp .ulSmallPic li").length-3)) {
				leftPosition=-(curPic-2)*115;
			}else if(curPic > ($(".zhanshi_tp .ulSmallPic li").length-4) && $(".zhanshi_tp .ulSmallPic li").length>6) {
				leftPosition=-($(".zhanshi_tp .ulSmallPic li").length-5)*115;
			}
			leftPosition+="px";
			$(".zhanshi_tp .ulSmallPic").attr("rel","moving");
			$(".zhanshi_tp .ulSmallPic").animate({left:leftPosition},200,function(){$(".zhanshi_tp .ulSmallPic").attr("rel","stop");});
		}
	}
	
	//澶у浘鎸夐挳鏁堟灉
	$("#sLeftBtnA").mouseover(function(){
		if($(this).attr("class")=="sLeftBtnA") {
			$(this).attr("class","sLeftBtnASel");
		}
	});
	
	$("#sLeftBtnA").mouseout(function(){
		if($(this).attr("class")=="sLeftBtnASel") {
			$(this).attr("class","sLeftBtnA");
		}
	});
	
	$("#sLeftBtnA").click(function(){
		if($(this).attr("class")=="sLeftBtnASel") {
			numInit("minus");
			btnBInitB();
			smallPicSelected();
			smallPicScroll();
			if(curPic == 0) {$("#sLeftBtnA").attr("class","sLeftBtnABan");}
			if(curPic < (allPic-1) && $("#sRightBtnA").attr("class")=="sRightBtnABan") {$("#sRightBtnA").attr("class","sRightBtnA");}
		}
	});
	
	$("#sRightBtnA").mouseover(function(){
		if($(this).attr("class")=="sRightBtnA") {
			$(this).attr("class","sRightBtnASel");
		}
	});
	
	$("#sRightBtnA").mouseout(function(){
		if($(this).attr("class")=="sRightBtnASel") {
			$(this).attr("class","sRightBtnA");
		}
	});
	
	$("#sRightBtnA").click(function(){
		if($(this).attr("class")=="sRightBtnASel") {
			numInit("plus");
			btnBInitB();
			smallPicSelected();
			smallPicScroll();
			if(curPic == (allPic-1)) {$("#sRightBtnA").attr("class","sRightBtnABan");}
			if(curPic > 0 && $("#sLeftBtnA").attr("class")=="sLeftBtnABan") {$("#sLeftBtnA").attr("class","sLeftBtnA");}
		}
	});
	
	//灏忓浘li鎸夐敭鏁堟灉
	for (var i=0;i<$(".zhanshi_tp .ulSmallPic li").length;i++) {
		(function(j) {
			$(".zhanshi_tp .ulSmallPic li:eq("+j+")").click(function() {
				if($(this).attr("class") != "liSelected") {
					numInit(j);
					btnAInit();
					smallPicSelected();
				}
			})
		}) (i);
	}
	
	//灏忓浘宸﹀彸鎸夐敭鏁堟灉
	$("#sLeftBtnB").mouseover(function(){
		if($(this).attr("class")=="sLeftBtnB") {
			$(this).attr("class","sLeftBtnBSel");
		}
	});
	
	$("#sLeftBtnB").mouseout(function(){
		if($(this).attr("class")=="sLeftBtnBSel") {
			$(this).attr("class","sLeftBtnB");
		}
	});
	
	$("#sLeftBtnB").click(function(){
		if($(this).attr("class")=="sLeftBtnBSel") {
			var leftPosition=$(".zhanshi_tp .ulSmallPic").css("left");
			var leftPositionNum=Number(leftPosition.substring(0,(leftPosition.length-2)));
			leftPosition=leftPositionNum+115+"px";
			if(leftPosition=="0px") {if($(this).attr("class") != "sLeftBtnBBan") {$(this).attr("class","sLeftBtnBBan");}}
			var bestLeftNum=-($(".zhanshi_tp .ulSmallPic li").length-5)*115;
			if((leftPositionNum+115) > bestLeftNum && $("sRightBtnB").attr("class") != "sRightBtnB") {$("#sRightBtnB").attr("class","sRightBtnB")}
			if($(".zhanshi_tp .ulSmallPic").attr("rel")=="stop"){
				$(".zhanshi_tp .ulSmallPic").attr("rel","moving");
				$(".zhanshi_tp .ulSmallPic").stop();
				$(".zhanshi_tp .ulSmallPic").animate({left:leftPosition},200,function(){$(".zhanshi_tp .ulSmallPic").attr("rel","stop");});
			}
		}
	});
	
	$("#sRightBtnB").mouseover(function(){
		if($(this).attr("class")=="sRightBtnB") {
			$(this).attr("class","sRightBtnBSel");
		}
	});
	
	$("#sRightBtnB").mouseout(function(){
		if($(this).attr("class")=="sRightBtnBSel") {
			$(this).attr("class","sRightBtnB");
		}
	});
	
	$("#sRightBtnB").click(function(){
		if($(this).attr("class")=="sRightBtnBSel"){
			var leftPosition=$(".zhanshi_tp .ulSmallPic").css("left");
			var leftPositionNum=Number(leftPosition.substring(0,(leftPosition.length-2)));
			leftPosition=leftPositionNum-115+"px";
			var bestLeftNum=-($(".zhanshi_tp .ulSmallPic li").length-5)*115;
			if((leftPositionNum-115)==bestLeftNum) {$(this).attr("class","sRightBtnBBan");}
			if(leftPositionNum==0 && $("#sLeftBtnB").attr("class")=="sLeftBtnBBan") {$("#sLeftBtnB").attr("class","sLeftBtnB");}
			if($(".zhanshi_tp .ulSmallPic").attr("rel")=="stop") {
				$(".zhanshi_tp .ulSmallPic").attr("rel","moving");
				$(".zhanshi_tp .ulSmallPic").stop();
				$(".zhanshi_tp .ulSmallPic").animate({left:leftPosition},200,function(){$(".zhanshi_tp .ulSmallPic").attr("rel","stop");});
			}
		}
	});
	
	liMouseOn();
	numInit("init");
	btnAInit();
	btnBInitA();
	smallPicSelected();	

});
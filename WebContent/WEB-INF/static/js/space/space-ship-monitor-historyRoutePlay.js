$(document).ready(function(){
	var point,lushu;
	var map = new BMap.Map("container");
	if(_points.length > 0){
		for(var i=0;i<_points.length;i++){
			_pointArray[i] = new BMap.Point(_points[i][0],_points[i][1]);
		}
		
		var drv = new BMap.DrivingRoute(map, {
			onSearchComplete: function(res) {
				if (drv.getStatus() == BMAP_STATUS_SUCCESS) {
					polyline = new BMap.Polyline(_pointArray, {strokeColor: '#FF3333'});
					// polyline.addEventListener("click", showImage);
					map.addOverlay(polyline);
					map.setViewport(_pointArray);
					lushu = new BMapLib.LuShu(map,_pointArray,{
						defaultContent:"从 "+_startPort+" 到 "+_endPort,//"出发地-目的地"
						autoView:true,//是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
						speed: 1000,
						enableRotation:true,//是否设置marker随着道路的走向进行旋转
						landmarkPois: []
					});
				}
			}
		});
		drv.search(_pointArray[0], _pointArray[_pointArray.length-1]);
		
	}else{
		alert("没有找到航次坐标数据！");
		point = new BMap.Point(new BMap.LocalCity().get(myFun));
	}
	
	$("#oneStep").click(function (){
		lushu.control();
	});
	// 绑定事件
	$("#play").click(function(){
		lushu.start();
	});
	$("#stop").click(function(){
		lushu.stop();
	});
	$("#pause").click(function(){
		lushu.pause();
	});
	$("#hide").click(function(){
		lushu.hideInfoWindow();
	});
	$("#show").click(function(){
		lushu.showInfoWindow();
	});
	$("#download").click(function(){
		if(_points.length > 0)
			window.location.href=_rootPath+"/download/fileDownload?url="+_datasPath;
		else
			alert("没有该航次数据！")
		return ;	
	});
	
	// 设置当前视图为ip所在地
  	function myFun(result){
  	  var cityName = result.name;
  	  map.setCenter(cityName);
  	}
  	map.centerAndZoom(point, 15);
  	map.enableScrollWheelZoom();
    map.addControl(new BMap.NavigationControl()); // 添加平移缩放控件  
    map.addControl(new BMap.ScaleControl());  // 添加比例尺控件  
    map.addControl(new BMap.ScaleControl());  // 添加比例尺控件  
    map.addControl(new BMap.MapTypeControl()); // 添加地图类型控件
    $(".close").live("click",function(){
    	$("#imageResult").fadeOut();
    });
    
    $(".showImage").hover(
    	function(){
			$("#shipImage").css("width","224px");
			$("#shipImage").css("margin-left","-112px");
        },
        function(){
        	$("#shipImage").css("width","112px");
        	$("#shipImage").css("margin-left","0px");
        }
    );
    
    // 用户点击轨迹线时取点击位置最近的点作为目标点
    var pointNearArray = new Array();
    function checkPoint(point){
    	var min = pointFarArray[0];
    	var index = 0
    	for(var i=0;i<_points.length;i++){
    		if(point[0] == _points[i].lng && point[1] == _points[i].lat)
    			return _points[i];
    		else{
    			// 取得最近的一个点
    			var pointNear = Math.pow(point[0]-_points[i].lng,2)+Math.pow(point[1]-_points[i].lat,2);
    			pointNearArray[i] = pointNear;
    		}
    	}
    	
    	for(var i=0;i<pointNearArray.length;i++){
    		if(min > pointNearArray[i]){
    			min = pointNearArray[i];
    			index = i ;
    		}
    	}
    	
    	return _pointArray[index];
    }
	$("#inline").colorbox({inline:true, width:"100%",height:"100%",fixed:true,reposition:true});
	$("#closeDom").live("click",function(){
		//alert("关闭全屏");
		$.colorbox.close();
		$(this).remove();
		$(".navbar-inner").show();
		$("#container").addClass("widget-content"); 
		$("#container").css("height","450px");
	});
});
$(document).bind('cbox_complete', function(){
	var closeDom = $('<div style="float:left;margin-left:20px" id="closeDom"><div title="全屏" style="box-shadow: rgba(0, 0, 0, 0.34902) 2px 2px 3px; border-left-width: 1px; border-left-style: solid; border-left-color: rgb(139, 164, 220); border-top-width: 1px; border-top-style: solid; border-top-color: rgb(139, 164, 220); border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: rgb(139, 164, 220); padding: 2px 6px; font-style: normal; font-variant: normal; font-weight: bold; font-stretch: normal; font-size: 12px; line-height: 1.3em; font-family: arial, sans-serif; text-align: center; white-space: nowrap; border-radius: 3px 0px 0px 3px; color: rgb(255, 255, 255); background: rgb(142, 168, 224);">关闭全屏</div></div>');
	$(".BMap_noprint.anchorTR").append(closeDom); 
});
$(document).bind('cbox_load', function(){
	$("#container").removeClass("widget-content"); 
	$("#container").css("height","100%");
	$(".navbar-inner").hide();
});

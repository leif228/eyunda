$(document).ready(function(){
	
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	$('select').select2();
	
	var map = new BMap.Map("container");
	// 如果服务器返回的结果为空则定位在当前ip所在地
	var _point = new BMap.Point(new BMap.LocalCity().get(myFun));
	
	map.centerAndZoom(_point, 15);
    map.addControl(new BMap.NavigationControl()); // 添加平移缩放控件  
    map.addControl(new BMap.ScaleControl());  // 添加比例尺控件  
    map.addControl(new BMap.ScaleControl());  // 添加比例尺控件  
    map.addControl(new BMap.MapTypeControl()); // 添加地图类型控件
    map.enableScrollWheelZoom(true); // 允许鼠标滑轮操作 
    // 逆地址解析
    var geoc = new BMap.Geocoder();
    
	// 设置当前视图为ip所在地
	function myFun(result) {
		var cityName = result.name;
		map.setCurrentCity(cityName); // 设置地图显示的城市 此项是必须设置的
	}

    $("#myModal").draggable({   
        handle: ".modal-header",
        cursor: 'move',
        refreshPositions: false
    });

    // 获取船舶时时动态分布
    function refreshShips(){
        var keyWords = $("#keyWords").val();
        var deptId = $("#deptId option:selected").val();
		$.ajax({
			method : "GET",
			url : _rootPath+"/space/monitor/myAllShip/shipCooordRefresh",
			data : {keyWords : keyWords, deptId : deptId},
			datatype : "json",
			success : function(data){
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "FAILURE") {
					alert(message);
					return false;
				} else {
					map.clearOverlays();
					// 添加多个覆盖物
					var _pointArray = new Array();
					var _points = $(data)[0].shipCooordDatas;
					for(var i=0;i<_points.length;i++){
				 		_pointArray[i] = new BMap.Point(_points[i].longitude,_points[i].latitude);
				 		
				 		var myIcon = new BMap.Icon(_rootPath+"/img/boat/image.png", new BMap.Size(45, 26), {
				 			offset: new BMap.Size(10, 25), // 指定定位位置
				 			imageOffset: new BMap.Size(0, 0) // 设置图片偏移
				 		});
				 		var marker = new BMap.Marker(_pointArray[i],{icon: myIcon});
				 		var tmpx = _points[i].shipName.split("|");
				 		var label = new BMap.Label(tmpx[1],{offset:new BMap.Size(32,-30)});
				 		marker.setLabel(label);
				 		marker.setRotation(_points[i].course-90);
				 		map.addOverlay(marker);    //增加点
				 		
				 		label.addEventListener("click", attribute);
				 		marker.addEventListener("click", attribute);
						
					}
					map.setViewport(_pointArray);
				}
			}
		});
    }
    
	//获取覆盖物位置
	function attribute(e){
		var _title = "";
		if (e.target instanceof BMap.Label)
			_title = e.target.getContent();
		else
			_title = e.target.getLabel().getContent();
		
		$.ajax({
            method: "GET",
            data: {
            	shipName: _title
            },
            url: _rootPath + "/space/monitor/shipInfo",
            datatype: "json",
            success: function(data) {
            		var iHeight=600; //弹出窗口的高度;
            		var iWidth=800; //弹出窗口的宽度;
            		var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
            		var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
            		
            		$("#myModal").on("hidden", function() {
            	    	// alert("hide");
            	        $(this).removeData("modal");
            	        $(this).find(".modal-body").html("");
            	    });
            		$("#myModal").find(".modal-body").css({       
                        width: iWidth-10
            		});
            		$("#myModal").modal();
            		$("#myModal").css({
                        width: iWidth,
                        top: iTop,
                        left: iLeft,
                        margin: "0"
            		});
            		$("#myModal").find("#model-title").html(_title);
            		$("#myModal").find(".modal-body").html(data);
            		$("#myModal").show();

                    return true;
            }
        });

	}

	refreshShips();
    setInterval(refreshShips, 1000 * 60 * 5); // 每5分钟请求一次

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
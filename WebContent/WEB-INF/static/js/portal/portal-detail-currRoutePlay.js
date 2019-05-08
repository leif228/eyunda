$(document).ready(function(){
	var point,lushu,polyline,temp=0;
	var map = new BMap.Map("mapContainer");
	if(_points.length > 0){
		// 监控船舶动态
		for(var i=0;i<_points.length;i++){
			_pointArray[i] = new BMap.Point(_points[i][0],_points[i][1]);
		}
		
		// 实例化一个驾车导航用来生成路线
		var drv = new BMap.DrivingRoute(map, {
			onSearchComplete: function(res) {
				if (drv.getStatus() == BMAP_STATUS_SUCCESS) {
					polyline = new BMap.Polyline(_pointArray, {strokeColor: '#FF3333'});
					map.addOverlay(polyline);
					map.setViewport(_pointArray);
					lushu = new BMapLib.LuShu(map,_pointArray,{
						defaultContent:"船舶最新动态",//"出发地-目的地"
						autoView:true,//是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
						speed: 1000,
						enableRotation:true,//是否设置marker随着道路的走向进行旋转
						landmarkPois: []
					});
					if(temp == 1)
					  lushu.start();
					temp++
				}
			}
		});
		drv.search(_pointArray[0], _pointArray[_pointArray.length-1]);
		
	}else{
		point = new BMap.Point(new BMap.LocalCity().get(myFun));
	}
	
	//获取当前坐标位置的图片
	// 设置当前视图为ip所在地
  	function myFun(result){
  	  var cityName = result.name;
  	  map.setCenter(cityName);
  	}
  	map.centerAndZoom(point, 14);
  	map.enableScrollWheelZoom();
    map.addControl(new BMap.NavigationControl()); // 添加平移缩放控件  
    map.addControl(new BMap.ScaleControl());  // 添加比例尺控件  
    map.addControl(new BMap.ScaleControl());  // 添加比例尺控件  
    map.addControl(new BMap.MapTypeControl()); // 添加地图类型控件 
    
});

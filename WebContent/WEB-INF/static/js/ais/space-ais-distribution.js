$(document).ready(function(){
	
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	$('select').select2();
	
	mapOpt = new SP.MapOptions();
	mapOpt.zoom = 5;
	if(_points.length)
		mapOpt.centerLngLat = new SP.LngLat(_points[0].lng/600000,_points[0].lat/600000);
	
    map=new SP.Map("container", mapOpt);
    map.addControl(new SP.NavigationControl());//添加导航条
    map.addControl(new SP.ScaleControl());//添加比例尺
    
    var ship, arrShip=[];
    
    if(map){
        removeShip();
        var lnglat=null;
        for(var i=0;i<_points.length;i++){
           lnglat=new SP.LngLat(_points[i].lng/600000,_points[i].lat/600000);
            var param={
                cn_name:_points[i].shipName,
                breadth:_points[i].w,
                course:_points[i].course,
                speed:_points[i].speed,
                heading:_points[i].hd,
                mmsi:_points[i].mmsi,
                pos_time:_points[i].posTime,
                length:_points[i].l
            };

            ship = new SP.Ship(lnglat,param);
            arrShip.push(ship);
            map.getOverlayLayer().addOverlay(ship);
        }
    }
    
    function removeShip() {
        if(arrShip){
            for(var i=0;i<arrShip.length;i++){
                map.getOverlayLayer().removeOverlay(arrShip[i]);
            }
            arrShip = [];
        }
    }
    
    // 获取船舶时时动态分布
  	if(_points.length > 0){
  		keyWords = $("#keyWords").val();
  		selectCode = $("#selectCode option:selected").val();
  		
	  	setInterval(function (){
			$.ajax({
				method : "GET",
				url : _rootPath+"/space/ais/aisShip/distributoinRefresh",
				data : {keyWords : keyWords, selectCode : selectCode},
				datatype : "json",
				success : function(data){
					var returnCode = $(data)[0].returnCode;
					var message = $(data)[0].message;
					if (returnCode == "FAILURE") {
						alert(message);
						return false;
					} else {
						if(map){
					        removeShip();
					        var lnglat=null;
					        _points = $(data)[0].shipCooordDatas;
					        for(var i=0;i<_points.length;i++){
					        	lnglat=new SP.LngLat(_points[i].longitude/600000,_points[i].latitude/600000);
					            var param={
					                cn_name:_points[i].shipName,
					                breadth:_points[i].w,
					                course:_points[i].course,
					                speed:_points[i].speed,
					                heading:_points[i].hd,
					                mmsi:_points[i].mmsi,
					                pos_time:_points[i].posTime,
					                length:_points[i].l
					            };

					            ship = new SP.Ship(lnglat,param);
					            arrShip.push(ship);
					            map.getOverlayLayer().addOverlay(ship);
					        }
					    }
					}
				}
			});
		}, 1000 * 5 * 60); // 每5分钟请求一次
  	}
    
    $(".seeCurrent").attr("href",_rootPath+"/space/ais/aisShip");
});

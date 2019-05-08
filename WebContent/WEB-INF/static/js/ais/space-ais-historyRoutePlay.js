$(document).ready(function(){
	
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	$('select').select2();
	
	var map,trace;
	
	mapOpt = new SP.MapOptions();
	mapOpt.zoom = 8;
	
	if(_points.length)
		mapOpt.centerLngLat = new SP.LngLat(_points[0].longitude/600000,_points[0].latitude/600000);
	
	map=new SP.Map("container", mapOpt);
	map.addControl(new SP.NavigationControl());//添加导航条
	map.addControl(new SP.ScaleControl());//添加比例尺
	
    //显示轨迹
    removeTrace();
    
    var to = new SP.TraceOptions();
    var datas = _points;
    trace=new SP.Trace("trace",datas,map);
    trace.show();
    
    //隐藏轨迹
    function removeTrace() {
        if(trace){
            trace.hide();
            trace = null;
        }
    }
    
});

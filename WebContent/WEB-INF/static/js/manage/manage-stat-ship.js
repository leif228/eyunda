$(document).ready(function () {
	//船舶总数
	$("#viewStatShips").live("click", function() {
		$.ajax({
			method : "GET",
			data : {para : 1},
			url : _rootPath+"/manage/stat/statShipNumber",
			datatype : "json",
			success : function(data) {
				var returnCode=$(data)[0].returnCode;
				var message=$(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					var xaxis=[],dd=[],data_lines=[];
					var color=['#008299','#D24726','#8C0095','#0000FF','#2E8B57','#FFFF00','#800080','#FF69B4'];
					for(var i=0;i<1;i++){
						dd[i]=[];
						var statShipDatas = $(data)[0].statShipDatas;
						for(var j=0;j<statShipDatas.length;j++){
							var statShip = statShipDatas[j];
							dd[i].push([j,statShip.sumWares]);
							if(!i)
								xaxis.push([j,statShip.yearMonth]);
						}
						
						var data_line={label:"船舶总数",
										color:color[i%8],
										data:dd[i]}
						data_lines.push(data_line);
					}
					
					var lines = $("#statShips");
					
					var options_lines = {
				        series: {
				            lines: {
				                show: true
				            },
				            points: {
				                show: true
				            }, 
				            hoverable: true  
				        },
				        xaxis: { ticks:xaxis,tickSize:1,min:0,max:11} ,
				        yaxis: { min: 0 },
				        grid: {
				            backgroundColor: '#FFFFFF',
				            borderWidth: 1,
				            borderColor: '#D7D7D7',
				            hoverable: true, 
				            clickable: true
				        }
				    };
				    // rendering plot lines
				    var chart_lines = $.plot(lines, data_lines, options_lines);	
				    
				    // 节点提示
			        function showTooltip(x, y, contents) {  
			            $('<div id="tooltip">' + contents + '</div>').css( {  
			                position: 'absolute',  
			                display: 'none',  
			                top: y + 12,  
			                left: x - 120,  
			                border: '1px solid #fee',  
			                padding: '2px', 'background-color': '#800080','font-size': '10px',
			                opacity: 0.80  
			            }).appendTo("body").fadeIn(200);  
			        }  
			  
			        var previousPoint = null;  
			        
			        // 绑定提示事件
			        $("#statShips").bind("plothover", function (event, pos, item) {  
			            if (item) {  
			                if (previousPoint != item.dataIndex) {  
			                    previousPoint = item.dataIndex;  
			                    
			                    $("#tooltip").remove();
			                    var x = item.datapoint[0].toFixed(2),
			                    y = item.datapoint[1].toFixed(2);
			                    var tip = "船舶总数：";  
			                    showTooltip(item.pageX,item.pageY,""+tip+y.substring(0,y.indexOf("."))+"艘"); 
			                }
			            }  
			            else {  
			                $("#tooltip").remove();
			                previousPoint = null;
			            }
			        });  
				}
			}
		});
		return true;
	});
	
	//船舶上线数
	$("#viewStatUpShips").live("click", function() {
		$.ajax({
			method : "GET",
			data : {para : 1},
			url : _rootPath+"/manage/stat/statShipNumber",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					var xaxis=[],dd=[],data_lines=[];
					var color=['#008299','#D24726','#8C0095','#0000FF','#2E8B57','#FFFF00','#800080','#FF69B4'];
					for(var i=0;i<1;i++){
						dd[i]=[];
						var statShipDatas = $(data)[0].statShipDatas;
						for(var j=0;j<statShipDatas.length;j++){
							var statShip = statShipDatas[j];
							dd[i].push([j,statShip.upShips]);
							if(!i)
								xaxis.push([j,statShip.yearMonth]);
						}
						
						var data_line={label:"船舶上线数",
										color:color[i%8],
										data:dd[i]}
						data_lines.push(data_line);
					}
					var lines = $("#statUpShips");
				   	
					var options_lines = {
				        series: {
				            lines: {
				                show: true
				            },
				            points: {
				                show: true
				            }, 
				            hoverable: true  
				        },
				        xaxis: { ticks: xaxis ,tickSize: 1 ,min:0, max:11 } ,
				        yaxis: {min: 0},
				        grid: {
				            backgroundColor: '#FFFFFF',
				            borderWidth: 1,
				            borderColor: '#D7D7D7',
				            hoverable: true, 
				            clickable: true
				        }                 
				    };
				    // rendering plot lines
				    var chart_lines = $.plot(lines, data_lines, options_lines);	
				    
				    // 节点提示
			        function showTooltip(x, y, contents) {  
			            $('<div id="tooltip">' + contents + '</div>').css( {  
			                position: 'absolute',  
			                display: 'none',  
			                top: y + 12,  
			                left: x - 120,  
			                border: '1px solid #fee',  
			                padding: '2px', 'background-color': '#800080','font-size': '10px',
			                opacity: 0.80  
			            }).appendTo("body").fadeIn(200);  
			        }  
			  
			        var previousPoint = null;  
			        
			        // 绑定提示事件
			        $("#statUpShips").bind("plothover", function (event, pos, item) {  
			            if (item) {  
			                if (previousPoint != item.dataIndex) {  
			                    previousPoint = item.dataIndex;  
			                    
			                    $("#tooltip").remove();
			                    var x = item.datapoint[0].toFixed(2),
			                    y = item.datapoint[1].toFixed(2);
			                    var tip = "船舶上线数：";  
			                    showTooltip(item.pageX, item.pageY,""+tip+y.substring(0,y.indexOf("."))+"艘"); 
			                }  
			            }  
			            else {  
			                $("#tooltip").remove();  
			                previousPoint = null;  
			            }  
			        });  
				}
			}
		});
		return true;
	});

	//船舶下线数
	$("#viewStatDownShips").live("click", function() {
		$.ajax({
			method : "GET",
			data : {para : 1},
			url : _rootPath+"/manage/stat/statShipNumber",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					var xaxis=[],dd=[],data_lines=[];
					var color=['#008299','#D24726','#8C0095','#0000FF','#2E8B57','#FFFF00','#800080','#FF69B4'];
					for(var i=0;i<1;i++){
						dd[i]=[];
						var statShipDatas = $(data)[0].statShipDatas;
						for(var j=0;j<statShipDatas.length;j++){
							var statShip = statShipDatas[j];
							dd[i].push([j,statShip.downShips]);
							if(!i)
								xaxis.push([j,statShip.yearMonth]);
						}
						
						var data_line={label:"船舶下线数",
										color:color[i%8],
										data:dd[i]}
						data_lines.push(data_line);
					}
					var lines = $("#statDownShips");
				   	
					var options_lines = {
				        series: {
				            lines: {
				                show: true
				            },
				            points: {
				                show: true
				            }, 
				            hoverable: true  
				        },
				        xaxis: { ticks: xaxis ,tickSize: 1 ,min:0, max:11 } ,
				        yaxis: {min: 0},
				        grid: {
				            backgroundColor: '#FFFFFF',
				            borderWidth: 1,
				            borderColor: '#D7D7D7',
				            hoverable: true, 
				            clickable: true
				        }                 
				    };
				    // rendering plot lines
				    var chart_lines = $.plot(lines, data_lines, options_lines);	
				    
				    // 节点提示
			        function showTooltip(x, y, contents) {  
			            $('<div id="tooltip">' + contents + '</div>').css( {  
			                position: 'absolute',  
			                display: 'none',  
			                top: y + 12,  
			                left: x - 120,  
			                border: '1px solid #fee',  
			                padding: '2px', 'background-color': '#800080','font-size': '10px',
			                opacity: 0.80  
			            }).appendTo("body").fadeIn(200);  
			        }  
			  
			        var previousPoint = null;  
			        
			        // 绑定提示事件
			        $("#statDownShips").bind("plothover", function (event, pos, item) {  
			            if (item) {  
			                if (previousPoint != item.dataIndex) {  
			                    previousPoint = item.dataIndex;  
			                    
			                    $("#tooltip").remove();
			                    var x = item.datapoint[0].toFixed(2),
			                    y = item.datapoint[1].toFixed(2);
			                    var tip = "船舶下线数：";  
			                    showTooltip(item.pageX, item.pageY,""+tip+y.substring(0,y.indexOf("."))+"艘"); 
			                }  
			            }  
			            else {  
			                $("#tooltip").remove();  
			                previousPoint = null;  
			            }  
			        });  
				}
			}
		});
		return true;
	});
	
}) ;
$(document).ready(function () {
	
	//合同总数
	$("#viewStatOrderCount").live("click", function() {
		$.ajax({
			method : "GET",
			data : {para : 1},
			url : _rootPath+"/manage/stat/orderStatDraw",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					var xaxis=[],dd=[];
					var data_lines=[];
					var color=['#008299','#D24726','#8C0095','#0000FF','#2E8B57','#FFFF00','#800080','#FF69B4'];
					for(var i=0;i<1;i++){
						dd[i]=[];
						var statOrderDatas = $(data)[0].statOrderDatas;
						for(var j = 0 ;j < statOrderDatas.length ;j++){
							var statOrder = statOrderDatas[j];
							dd[i].push([j,statOrder.sumOrderCount]);
							xaxis.push([j,statOrder.yearMonth]);
						}
						var data_line={
							label:"合同总数",
							color:color[i%8],
							data:dd[i]
						}
						data_lines.push(data_line);
					}
					
					var lines = $("#statOrderCount");
					
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
				        xaxis:{ticks:xaxis,tickSize:1,min:0,max:11},
				        yaxis:{min:0},
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
			                padding:'2px','background-color':'#800080','font-size':'10px',
			                opacity:0.80
			            }).appendTo("body").fadeIn(200);
			        }  
			  
			        var previousPoint = null;  
			        
			        // 绑定提示事件  
			        $("#statOrderCount").bind("plothover", function (event, pos, item) {  
			            if (item) {  
			                if (previousPoint != item.dataIndex) {  
			                    previousPoint = item.dataIndex;  
			                    $("#tooltip").remove();
			                    var x = item.datapoint[0].toFixed(2),
			                    y = item.datapoint[1];
			                    var tip = "合同总数：";  
			                    showTooltip(item.pageX, item.pageY,""+tip+y+"份"); 
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
	
	//统计总金额
	$("#viewStatOrderTransFee").live("click", function() {
		$.ajax({
			method : "GET",
			data : {para : 1},
			url : _rootPath+"/manage/stat/orderStatDraw",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					var xaxis=[],dd=[];
					var data_lines=[];
					var color=['#008299','#D24726','#8C0095','#0000FF','#2E8B57','#FFFF00','#800080','#FF69B4'];
					for(var i=0;i<1;i++){
						dd[i]=[];
						var statOrderDatas = $(data)[0].statOrderDatas;
						for(var j = 0 ;j < statOrderDatas.length ;j++){
							var statOrder = statOrderDatas[j];
							dd[i].push([j,statOrder.sumTransFee]);
							xaxis.push([j,statOrder.yearMonth]);
						}
						var data_line={
								label:"合同金额",
								color:color[i%8],
								data:dd[i]
						}
						data_lines.push(data_line);
					}
					
					var lines = $("#statOrderTransFee");
				   
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
				        xaxis:{ticks:xaxis,tickSize:1,min:0,max:11 } ,
				        yaxis:{min:0.0},
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
			        $("#statOrderTransFee").bind("plothover", function (event, pos, item) {  
			            if (item) {  
			                if (previousPoint != item.dataIndex) {  
			                    previousPoint = item.dataIndex;  
			                    
			                    $("#tooltip").remove();
			                    var x = item.datapoint[0].toFixed(2),
			                    y = item.datapoint[1];
			                    var tip = "合同金额：";  
			                    showTooltip(item.pageX, item.pageY,""+tip+y+"元"); 
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
	
	//代理人佣金
	$("#viewStatOrderBrokerFee").live("click", function() {
		$.ajax({
			method : "GET",
			data : {para : 1},
			url : _rootPath+"/manage/stat/orderStatDraw",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					var xaxis=[],dd=[];
					var data_lines=[];
					var color=['#008299','#D24726','#8C0095','#0000FF','#2E8B57','#FFFF00','#800080','#FF69B4'];
					for(var i=0;i<1;i++){
						dd[i]=[];
						var statOrderDatas = $(data)[0].statOrderDatas;
						for(var j = 0 ;j < statOrderDatas.length ;j++){
							var statOrder = statOrderDatas[j];
							dd[i].push([j,statOrder.sumBrokerFee]);
							xaxis.push([j,statOrder.yearMonth]);
						}
						var data_line={
								label:"代理人佣金",
								color:color[i%8],
								data:dd[i]
						}
						data_lines.push(data_line);
					}
					
					var lines = $("#statOrderBrokerFee");
				   
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
				        xaxis:{ticks:xaxis,tickSize:1,min:0,max:11 },
				        yaxis:{min:0.0},
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
			        $("#statOrderBrokerFee").bind("plothover", function (event, pos, item) {  
			            if (item) {  
			                if (previousPoint != item.dataIndex) {  
			                    previousPoint = item.dataIndex;  
			                    
			                    $("#tooltip").remove();
			                    var x = item.datapoint[0].toFixed(2),
			                    y = item.datapoint[1];
			                    var tip = "代理人佣金：";  
			                    showTooltip(item.pageX, item.pageY,""+tip+y+"元"); 
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

	//统计平台服务费
	$("#viewStatOrderPlatFee").live("click", function() {
		$.ajax({
			method : "GET",
			data : {para : 1},
			url : _rootPath+"/manage/stat/orderStatDraw",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					var xaxis=[],dd=[];
					var data_lines=[];
					var color=['#008299','#D24726','#8C0095','#0000FF','#2E8B57','#FFFF00','#800080','#FF69B4'];
					for(var i=0;i<1;i++){
						dd[i]=[];
						var statOrderDatas = $(data)[0].statOrderDatas;
						for(var j = 0 ;j < statOrderDatas.length ;j++){
							var statOrder = statOrderDatas[j];
							dd[i].push([j,statOrder.sumPlatFee]);
							xaxis.push([j,statOrder.yearMonth]);
						}
						var data_line={
								label:"平台服务费",
								color:color[i%8],
								data:dd[i]
						}
						data_lines.push(data_line);
					}
					
					var lines = $("#statOrderPlatFee");
					
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
				        xaxis:{ticks:xaxis,tickSize:1,min:0,max:11},
				        yaxis:{min:0.0},
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
			                padding:'2px','background-color':'#800080','font-size':'10px',
			                opacity:0.80
			            }).appendTo("body").fadeIn(200);
			        }  
			  
			        var previousPoint = null;  
			        
			        // 绑定提示事件  
			        $("#statOrderPlatFee").bind("plothover", function (event, pos, item) {  
			            if (item) {  
			                if (previousPoint != item.dataIndex) {  
			                    previousPoint = item.dataIndex;  
			                    $("#tooltip").remove();
			                    var x = item.datapoint[0].toFixed(2),
			                    y = item.datapoint[1];
			                    var tip = "平台服务费：";  
			                    showTooltip(item.pageX, item.pageY,""+tip+y+"元"); 
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
	
});
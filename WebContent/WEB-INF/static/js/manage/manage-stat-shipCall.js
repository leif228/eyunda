$(document).ready(function () {
	
	//统计访问次数
	$("#viewStatShipCallNum").live("click", function() {
		$.ajax({
			method : "GET",
			data : {para : 1},
			url : _rootPath+"/manage/stat/statShipCallNumber",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					var monthStatShipCallDatas = $(data)[0].monthStatShipCallDatas ;
					var d0=[],d1=[],d2=[],d3=[],d4=[],xaxis=[] ;
					var color=['#008299','#D24726','#8C0095','#46A1EF','#45BE46'];
					for(var i=0;i<monthStatShipCallDatas.length ;i++){
						var monthStatShipCallData = monthStatShipCallDatas[i];
						var statShipCallDatas = monthStatShipCallData.statShipCallDatas;
						xaxis.push([i,monthStatShipCallData.yearMonth]);
						for(var j=0;j<statShipCallDatas.length;j++){
							var statShipCall = statShipCallDatas[j];
							if(statShipCall.roleCode == "0")
								d0.push([i,statShipCall.callNum,statShipCall.roleDesc]);
							if(statShipCall.roleCode == "1")
								d1.push([i,statShipCall.callNum,statShipCall.roleDesc]);
							if(statShipCall.roleCode == "2")
								d2.push([i,statShipCall.callNum,statShipCall.roleDesc]);
							if(statShipCall.roleCode == "3")
								d3.push([i,statShipCall.callNum,statShipCall.roleDesc]);
							if(statShipCall.roleCode == "4")
								d4.push([i,statShipCall.callNum,statShipCall.roleDesc]);
						}
					}
					
					var lines = $("#shipCallNum"),
				    data_lines = [{label: d0[0][2],data: d0,color: color[0]},
				                  {label: d1[0][2],data: d1,color: color[1]},
				                  {label: d2[0][2],data: d2,color: color[2]},
				                  {label: d3[0][2],data: d3,color: color[3]},
				                  {label: d4[0][2],data: d4,color: color[4]}
				                 ];
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
				        xaxis: { ticks: xaxis,tickSize: 0 ,min:0, max:11 } ,
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
			        $("#shipCallNum").bind("plothover", function (event, pos, item) {  
			            if (item) {  
			                if (previousPoint != item.dataIndex) {  
			                    previousPoint = item.dataIndex;  
			                    
			                    $("#tooltip").remove();
			                    var x = item.datapoint[0].toFixed(2),
			                    y = item.datapoint[1].toFixed(2);
			                    var tip = "访问总计：";  
			                    showTooltip(item.pageX, item.pageY,item.series.label+""+tip+y.substring(0,y.indexOf("."))+"次"); 
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
	
	//统计访问人数
	$("#viewStatShipCallUser").live("click", function() {
		$.ajax({
			method : "GET",
			data : {para : 1},
			url : _rootPath+"/manage/stat/statShipCallNumber",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					var monthStatShipCallDatas = $(data)[0].monthStatShipCallDatas ;
					var d0=[],d1=[],d2=[],d3=[],d4=[],xaxis=[] ;
					var color=['#008299','#D24726','#8C0095','#46A1EF','#45BE46'];
					for(var i=0;i<monthStatShipCallDatas.length ;i++){
						var monthStatShipCallData = monthStatShipCallDatas[i];
						var statShipCallDatas = monthStatShipCallData.statShipCallDatas;
						xaxis.push([i,monthStatShipCallData.yearMonth]);
						for(var j=0;j<statShipCallDatas.length;j++){
							var statShipCall = statShipCallDatas[j];
							if(statShipCall.roleCode == "0")
								d0.push([i,statShipCall.calledUserNum,statShipCall.roleDesc]);
							if(statShipCall.roleCode == "1")
								d1.push([i,statShipCall.calledUserNum,statShipCall.roleDesc]);
							if(statShipCall.roleCode == "2")
								d2.push([i,statShipCall.calledUserNum,statShipCall.roleDesc]);
							if(statShipCall.roleCode == "3")
								d3.push([i,statShipCall.calledUserNum,statShipCall.roleDesc]);
							if(statShipCall.roleCode == "4")
								d4.push([i,statShipCall.calledUserNum,statShipCall.roleDesc]);
						}
					}
						
					var lines = $("#shipCallUser"),
				    data_lines = [{label: d0[0][2],data: d0,color: color[0]},
				                  {label: d1[0][2],data: d1,color: color[1]},
				                  {label: d2[0][2],data: d2,color: color[2]},
				                  {label: d3[0][2],data: d3,color: color[3]},
				                  {label: d4[0][2],data: d4,color: color[4]}
				                 ];
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
				        xaxis: { ticks:xaxis, tickSize: 1 ,min:0, max:11 } ,
				        yaxis: {min:0},
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
			        $("#shipCallUser").bind("plothover", function (event, pos, item) {  
			            if (item) {  
			                if (previousPoint != item.dataIndex) {  
			                    previousPoint = item.dataIndex;  
			                    
			                    $("#tooltip").remove();
			                    var x = item.datapoint[0].toFixed(2),
			                    y = item.datapoint[1].toFixed(2);
			                    var tip = "访问总计：";  
			                    showTooltip(item.pageX, item.pageY,item.series.label+""+tip+y.substring(0,y.indexOf("."))+"人"); 
			                }  
			            } else {  
			                $("#tooltip").remove();  
			                previousPoint = null;  
			            }  
			        });  
				    
				}
			}
		});
		return true;
	});
	
	//统计访问车船数
	$("#viewStatShipCallShip").live("click", function() {
		$.ajax({
			method : "GET",
			data : {para : 1},
			url : _rootPath+"/manage/stat/statShipCallNumber",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					var monthStatShipCallDatas = $(data)[0].monthStatShipCallDatas ;
					var d0=[],d1=[],d2=[],d3=[],d4=[],xaxis=[] ;
					var color=['#008299','#D24726','#8C0095','#46A1EF','#45BE46'];
					for(var i=0;i<monthStatShipCallDatas.length ;i++){
						var monthStatShipCallData = monthStatShipCallDatas[i];
						var statShipCallDatas = monthStatShipCallData.statShipCallDatas;
						xaxis.push([i,monthStatShipCallData.yearMonth]);
						for(var j=0;j<statShipCallDatas.length;j++){
							var statShipCall = statShipCallDatas[j];
							if(statShipCall.roleCode == "0")
								d0.push([i,statShipCall.calledShipNum,statShipCall.roleDesc]);
							if(statShipCall.roleCode == "1")
								d1.push([i,statShipCall.calledShipNum,statShipCall.roleDesc]);
							if(statShipCall.roleCode == "2")
								d2.push([i,statShipCall.calledShipNum,statShipCall.roleDesc]);
							if(statShipCall.roleCode == "3")
								d3.push([i,statShipCall.calledShipNum,statShipCall.roleDesc]);
							if(statShipCall.roleCode == "4")
								d4.push([i,statShipCall.calledShipNum,statShipCall.roleDesc]);
						}
					}
					
					var lines = $("#shipCallShip"),
				    data_lines = [{label: d0[0][2],data: d0,color: color[0]},
				                  {label: d1[0][2],data: d1,color: color[1]},
				                  {label: d2[0][2],data: d2,color: color[2]},
				                  {label: d3[0][2],data: d3,color: color[3]},
				                  {label: d4[0][2],data: d4,color: color[4]}
				                 ];
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
				        xaxis: { ticks: xaxis, tickSize: 1 ,min:0, max:11 } ,
		            	yaxis: { min: 0 } ,
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
		        $("#shipCallShip").bind("plothover", function (event, pos, item) {  
		            if (item) {  
		                if (previousPoint != item.dataIndex) {  
		                    previousPoint = item.dataIndex;  
		                    
		                    $("#tooltip").remove();
		                    var x = item.datapoint[0].toFixed(2),
		                    y = item.datapoint[1].toFixed(2);
		                    var tip = "访问总计：";  
		                    showTooltip(item.pageX, item.pageY,item.series.label+""+tip+y.substring(0,y.indexOf("."))+"艘"); 
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
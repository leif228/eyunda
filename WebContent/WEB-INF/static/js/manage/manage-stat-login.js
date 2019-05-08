$(document).ready(function () {
	
	//统计登录次数
	$("#viewStatLogin").live("click", function() {
		$.ajax({
			method : "GET",
			data : {para : 1},
			url : _rootPath+"/manage/stat/statLoginNumber",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					var data_lines = [];
					var monthStatLoginDatas = $(data)[0].monthStatLoginDatas ;
					var d0=[],d1=[],d2=[],d3=[],d4=[],xaxis=[] ;
					var color=['#008299','#D24726','#8C0095','#46A1EF','#45BE46'];
					for(var i=0;i<monthStatLoginDatas.length ;i++){
						var monthStatLoginData = monthStatLoginDatas[i];
						xaxis.push([i,monthStatLoginData.yearMonth])
						var statLogins = monthStatLoginData.monthLoginDatas;
						for(var j=0;j<statLogins.length;j++){
							var statLogin = statLogins[j];
							if(statLogin.roleCode == "0")
								d0.push([i,statLogin.loginNum,statLogin.roleDesc]);
							else if(statLogin.roleCode == "1")
								d1.push([i,statLogin.loginNum,statLogin.roleDesc]);
							else if(statLogin.roleCode == "2")
								d2.push([i,statLogin.loginNum,statLogin.roleDesc]);
							else if(statLogin.roleCode == "3")
								d3.push([i,statLogin.loginNum,statLogin.roleDesc]);
							else if(statLogin.roleCode == "4")
								d4.push([i,statLogin.loginNum,statLogin.roleDesc]);
						}
					}
					
					data_lines.push(
						{label: d0[0][2], color:color[0],data:d0},
						{label: d1[0][2], color:color[1],data:d1},
						{label: d2[0][2], color:color[2],data:d2},
						{label: d3[0][2], color:color[3],data:d3},
						{label: d4[0][2], color:color[4],data:d4}
					);
					
					var lines = $("#statLogin");
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
				        xaxis: { 
				        	ticks: xaxis, tickSize: 1, min:0, max:11
				        } ,
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
			        $("#statLogin").bind("plothover", function (event, pos, item) {  
			            if (item) {
			                if (previousPoint != item.dataIndex) {
			                    previousPoint = item.dataIndex;
			                    $("#tooltip").remove();
			                    var x = item.datapoint[0].toFixed(2),
			                    y = item.datapoint[1].toFixed(2);
			                    var tip = "登陆次数总计：";  
			                    showTooltip(item.pageX, item.pageY,item.series.label+""+tip+y.substring(0,y.indexOf("."))+"次"); 
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
	
	//统计登录人数
	$("#viewStatUser").live("click", function() {
		$.ajax({
			method : "GET",
			data : {para : 1},
			url : _rootPath+"/manage/stat/statLoginNumber",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					var data_lines = [];
					var monthStatLoginDatas = $(data)[0].monthStatLoginDatas ;
					var d0=[],d1=[],d2=[],d3=[],d4=[],xaxis=[] ;
					var color=['#008299','#D24726','#8C0095','#46A1EF','#45BE46'];
					for(var i=0;i<monthStatLoginDatas.length ;i++){
						var monthStatLoginData = monthStatLoginDatas[i];
						xaxis.push([i,monthStatLoginData.yearMonth])
						var statLogins = monthStatLoginData.monthLoginDatas;
						for(var j=0;j<statLogins.length;j++){
							var statLogin = statLogins[j];
							if(statLogin.roleCode == "0")
								d0.push([i,statLogin.loginUserNum,statLogin.roleDesc]);
							else if(statLogin.roleCode == "1")
								d1.push([i,statLogin.loginUserNum,statLogin.roleDesc]);
							else if(statLogin.roleCode == "2")
								d2.push([i,statLogin.loginUserNum,statLogin.roleDesc]);
							else if(statLogin.roleCode == "3")
								d3.push([i,statLogin.loginUserNum,statLogin.roleDesc]);
							else if(statLogin.roleCode == "4")
								d4.push([i,statLogin.loginUserNum,statLogin.roleDesc]);
						}
					}
					
					data_lines.push(
						{label: d0[0][2], color:color[0],data:d0},
						{label: d1[0][2], color:color[1],data:d1},
						{label: d2[0][2], color:color[2],data:d2},
						{label: d3[0][2], color:color[3],data:d3},
						{label: d4[0][2], color:color[4],data:d4}
					);
					
					var lines = $("#statUser");
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
				        xaxis: { ticks: xaxis,tickSize: 1 ,min:0, max:11 } ,
				        yaxis: { min : 0 } ,
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
			        $("#statUser").bind("plothover", function (event, pos, item) {  
			            if (item) {  
			                if (previousPoint != item.dataIndex) {  
			                    previousPoint = item.dataIndex;  
			                    
			                    $("#tooltip").remove();
			                    var x = item.datapoint[0].toFixed(2),
			                    y = item.datapoint[1].toFixed(2);
			                    var tip = "登陆人数总计：";  
			                    showTooltip(item.pageX, item.pageY,item.series.label+""+tip+y.substring(0,y.indexOf("."))+"人"); 
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
	
	//统计登陆时长
	$("#viewStatTime").live("click", function() {
		$.ajax({
			method : "GET",
			data : {para : 1},
			url : _rootPath+"/manage/stat/statLoginNumber",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					var data_lines = [];
					var monthStatLoginDatas = $(data)[0].monthStatLoginDatas ;
					var d0=[],d1=[],d2=[],d3=[],d4=[],xaxis=[] ;
					var color=['#008299','#D24726','#8C0095','#46A1EF','#45BE46'];
					for(var i=0;i<monthStatLoginDatas.length ;i++){
						var monthStatLoginData = monthStatLoginDatas[i];
						xaxis.push([i,monthStatLoginData.yearMonth])
						var statLogins = monthStatLoginData.monthLoginDatas;
						for(var j=0;j<statLogins.length;j++){
							var statLogin = statLogins[j];
							if(statLogin.roleCode == "0")
								d0.push([i,statLogin.timeSpan,statLogin.roleDesc]);
							else if(statLogin.roleCode == "1")
								d1.push([i,statLogin.timeSpan,statLogin.roleDesc]);
							else if(statLogin.roleCode == "2")
								d2.push([i,statLogin.timeSpan,statLogin.roleDesc]);
							else if(statLogin.roleCode == "3")
								d3.push([i,statLogin.timeSpan,statLogin.roleDesc]);
							else if(statLogin.roleCode == "4")
								d4.push([i,statLogin.timeSpan,statLogin.roleDesc]);
						}
					}
					
					data_lines.push(
						{label: d0[0][2], color:color[0],data:d0},
						{label: d1[0][2], color:color[1],data:d1},
						{label: d2[0][2], color:color[2],data:d2},
						{label: d3[0][2], color:color[3],data:d3},
						{label: d4[0][2], color:color[4],data:d4}
					);
					
					var lines = $("#statTimespan");
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
				        xaxis: { ticks: xaxis,tickSize: 1 ,min:0, max:11 } ,
				        yaxis: { min : 0 } ,
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
			        $("#statTimespan").bind("plothover", function (event, pos, item) {  
			            if (item) {  
			                if (previousPoint != item.dataIndex) {  
			                    previousPoint = item.dataIndex;  
			                    
			                    $("#tooltip").remove();
			                    var x = item.datapoint[0].toFixed(2),
			                    y = item.datapoint[1].toFixed(2);
			                    var tip = "登陆时长：";  
			                    showTooltip(item.pageX, item.pageY,item.series.label+""+tip+y.substring(0,y.indexOf("."))+"小时"); 
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
$(document).ready(function () {
	
	//统计总人数
	$("#viewStatUser").live("click", function() {
		$.ajax({
			method : "GET",
			data : {para : 1},
			url : _rootPath+"/manage/stat/statUserNumber",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					var statUsers = $(data)[0].statUserDatas;
					var d0=[],d1=[],d2=[],d3=[],d4=[],d5=[],xaxis=[] ;
					var color=['#008299','#D24726','#8C0095','#46A1EF','#45BE46','#2F889A'];
					for(var i=0;i<statUsers.length ;i++){
						var statUser = statUsers[i];
						d0.push([i,statUser.sumUsers]);
						d1.push([i,statUser.sumBrokers]);
						d2.push([i,statUser.sumHandlers]);
						d3.push([i,statUser.sumSailors]);
						d4.push([i,statUser.sumMasters]);
						d5.push([i,statUser.sumOwners]);
						xaxis.push([i,statUser.yearMonth]);
					}
					
					var lines = $("#userTotal"),
				    data_lines = [
				                  {label: '总用户数', data: d0, color: color[0]},
				                  {label: '总管理员数', data: d1, color: color[1]},
				                  {label: '总业务员数', data: d2, color: color[2]},
				                  {label: '总船员数', data: d3, color: color[3]},
				                  {label: '总船东数', data: d4, color: color[4]},
				                  {label: '总货主数', data: d5, color: color[5]}
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
				        xaxis: {ticks:xaxis, tickSize:1, min:0, max:11} ,
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
			        $("#userTotal").bind("plothover", function (event, pos, item) {  
			            if (item) {  
			                if (previousPoint != item.dataIndex) {  
			                    previousPoint = item.dataIndex;  
			                    
			                    $("#tooltip").remove();
			                    var x = item.datapoint[0].toFixed(2),
			                    y = item.datapoint[1].toFixed(2);
			                    var tip = "总计：";  
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
	
}) ;
	
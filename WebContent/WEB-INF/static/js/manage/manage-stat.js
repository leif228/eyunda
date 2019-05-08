$(function () {
    // chart
    // data array
    var d1 = [];
    for (var i = 0; i < 14; i += 0.5)
        d1.push([i, Math.sin(i)]);
 
    var d2 = [[0, 3], [4, 8], [8, 5], [9, 13]];
 
    // a null signifies separate line segments
    var d3 = [[0, 12], [7, 12], null, [7, 2.5], [12, 2.5]];
    
    var lines = $("#chart-lines"),
    data_lines = [{
        label: 'data 1', 
        data: d1,
        color: '#008299'
    }, {
        label: 'data 2', 
        data: d2,
        color: '#D24726'
    }, {
        label: 'data 3', 
        data: d3,
        color: '#8C0095'
    }],
    options_lines = {
        series: {
            lines: {
                show: true
            },
            points: {
                show: true
            },
            hoverable: true
        },
        grid: {
            backgroundColor: '#FFFFFF', // you can use gradiend like this { colors: [ "#FFFFFF", "#F1F1F1" ] }
            borderWidth: 1,
            borderColor: '#D7D7D7',
            hoverable: true, 
            clickable: true
        }
                        
    };
    // rendering plot lines
    var chart_lines = $.plot(lines, data_lines, options_lines);
});
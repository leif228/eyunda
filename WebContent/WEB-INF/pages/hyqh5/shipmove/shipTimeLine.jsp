<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <title>船舶动态 - ${shipInfoData.shipName}</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <meta name="description" content="Write an awesome description for your new site here. You can edit this line in _config.yml. It will appear in your document head meta (for Google search results) and in your feed.xml site description.">

    <link rel="stylesheet" href="${ctx}/hyqh5/lib/weui.min.css">
    <link rel="stylesheet" href="${ctx}/hyqh5/css/jquery-weui.css">
    <link rel="stylesheet" href="${ctx}/hyqh5/css/timeline.css">
    <link rel="stylesheet" href="${ctx}/hyqh5/demos/css/demos.css">
  </head>

  <body ontouchstart>

    <header class="weui-flex " style="text-align: center;">
      <div class="demos-title-left">
        <div class="weui-grid__icon">
          <span class="mui-icon mui-icon-back"></span>
        </div>
      </div>
      <div class="weui-flex__item demos-title-center">船舶动态 - ${shipInfoData.shipName}</div>
      <div class="demos-title-right">
        <div class="weui-grid__icon">
          <span class="mui-icon mui-icon-bars"></span>
        </div>
      </div>
    </header>

    <div class="weui-cells weui-cells_form">
      <div class="weui-cell">
        <div class="weui-cell__hd"><label for="date" class="weui-label">起始日期</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" id="startDate" name="startDate" type="text" value="${startDate}">
        </div>
        <div class="weui-cell__hd"><label for="date" class="weui-label">截止日期</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" id="endDate" name="endDate" type="text" value="${endDate}">
        </div>
      </div>
    </div>

    <div class="timeline demos-content-padded" id="timeline-content">

      <c:forEach var="shipArvlftData" items="${pageData.result}">

      <c:if test="${shipArvlftData.moveState == 'arrive'}">
      <div class="timeline-date">
        <ul>
          <h2 class="second" style="position: relative;">
            <div>${shipArvlftData.arvlftTime}<span>${shipArvlftData.portName} ${shipArvlftData.moveState.description}</span></div>
          </h2>
        </ul>
      </div>
      </c:if>

      <c:if test="${shipArvlftData.moveState == 'left' || shipArvlftData.moveState == 'stop' || shipArvlftData.moveState == 'stay' || shipArvlftData.moveState == 'sail' || shipArvlftData.moveState == 'heaveto'}">
      <div class="timeline-date">
        <ul>
          <li>
            <h3><span>${shipArvlftData.arvlftTime.substring(11)}</span><span>${shipArvlftData.arvlftTime.substring(0,10)}</span></h3>
            <dl class="right">
              <span>${shipArvlftData.portName} ${shipArvlftData.moveState.description}</span>
              <span><p>说明：${shipArvlftData.remark}</p></span>
            </dl>
          </li>
        </ul>
      </div>
      </c:if>

      <c:if test="${shipArvlftData.moveState == 'upload' || shipArvlftData.moveState == 'download'}">
      <div class="timeline-date">
        <ul>
          <li>
            <h3><span>${shipArvlftData.arvlftTime.substring(11)}</span><span>${shipArvlftData.arvlftTime.substring(0,10)}</span></h3>
            <dl class="right">
              <span>${shipArvlftData.portName} ${shipArvlftData.moveState.description} - 货物清单</span>
              <c:forEach var="shipUpdownData" items="${shipArvlftData.shipUpdownDatas}">
              <span><p>${shipUpdownData.description}</p></span>
              </c:forEach>
              <span><p>说明：${shipArvlftData.remark}</p></span>
              </span>
            </dl>
          </li>
        </ul>
      </div>
      </c:if>

      </c:forEach>

    </div>

    <div id="tipMore" class="weui-loadmore">
      <i class="weui-loading"></i>
      <span class="weui-loadmore__tips">正在加载${pageData.pageSize}</span>
    </div>

    <script src="${ctx}/hyqh5/lib/jquery-2.1.4.js"></script>
    <script src="${ctx}/hyqh5/lib/fastclick.js"></script>
    <script src="${ctx}/hyqh5/js/jquery-weui.js"></script>
    <script>
      $(function() {
        FastClick.attach(document.body);
      });

      var stOne = 0;
      $("#startDate").calendar({
        onChange: function (p, values, displayValues) {
        	  if (stOne++>0)
            window.location.href = "${ctx}/hyqh5/shipmove/arvlftList?mmsi=${shipInfoData.mmsi}&startDate="+values+"&endDate="+$("#endDate").val();
        }
      });

      var edOne = 0;
      $("#endDate").calendar({
        onChange: function (p, values, displayValues) {
        	  if (edOne++>0)
            window.location.href = "${ctx}/hyqh5/shipmove/arvlftList?mmsi=${shipInfoData.mmsi}&startDate="+$("#startDate").val()+"&endDate="+values;
        }
      });

      var pageNo = 1;
      var totalPages = ${pageData.totalPages};
      if (pageNo == totalPages)
            $("#tipMore").hide();
      $(document.body).infinite().on("infinite", function() {
        pageNo = pageNo + 1;
        if (pageNo >= totalPages) {
            $("#tipMore").hide();
        }
        if (pageNo > totalPages) {
            return;
        }
        $.ajax({
            method : "get",
            data : {
              pageNo : pageNo,
              mmsi : ${shipInfoData.mmsi}
            },
            url : "${ctx}/hyqh5/shipmove/arvlftPage",
            datatype : "json",
            success : function(data) {
              var returnCode = $(data)[0].returnCode;
              var message = $(data)[0].message;
              if (returnCode == "Failure") {
                $.alert("服务端数据请求失败！", "错误！");
                return false;
              } else {
                for (var n=0;n<$(data)[0].content.result.length;n++){
                  var arvlftData = $(data)[0].content.result[n];

                  var s = '';

                  if (arvlftData.moveState == 'arrive') {
                      s += '<div class="timeline-date">';
                      s += '  <ul>';
                      s += '    <h2 class="second" style="position: relative;">';
                      s += '      <div>'+arvlftData.arvlftTime+'<span>'+arvlftData.portName+' '+arvlftData.moveStateMap.description+'</span></div>';
                      s += '    </h2>';
                      s += '  </ul>';
                      s += '</div>';
                      }

                 if (arvlftData.moveState == 'left' || arvlftData.moveState == 'stop' || arvlftData.moveState == 'stay' || arvlftData.moveState == 'sail' || arvlftData.moveState == 'heaveto') {
                  s += '<div class="timeline-date">';
                  s += '  <ul>';
                  s += '    <li>';
                  s += '      <h3><span>'+arvlftData.arvlftTime.substring(11)+'</span><span>'+arvlftData.arvlftTime.substring(0,10)+'</span></h3>';
                  s += '      <dl class="right"><span>'+arvlftData.portName+' '+arvlftData.moveStateMap.description+'</span><span><p>说明：'+arvlftData.remark+'</p></span></dl>';
                  s += '    </li>';
                  s += '  </ul>';
                  s += '</div>';
                  }

                  if (arvlftData.moveState == 'upload' || arvlftData.moveState == 'download') {
                  s += '<div class="timeline-date">';
                  s += '  <ul>';
                  s += '    <li>';
                  s += '      <h3><span>'+arvlftData.arvlftTime.substring(11)+'</span><span>'+arvlftData.arvlftTime.substring(0,10)+'</span></h3>';
                  s += '      <dl class="right">';
                  s += '        <span>'+arvlftData.portName+' '+arvlftData.moveStateMap.description+' - 货物清单</span>';

                  for (var i=0;i<arvlftData.shipUpdownDatas.length;i++) {
                      var shipUpdownData = arvlftData.shipUpdownDatas[i];
                  s += '          <span><p>'+shipUpdownData.description+'</p></span>';
                  }

                  s += '        <span><p>说明：'+arvlftData.remark+'</p></span>';
                  s += '      </dl>';
                  s += '    </li>';
                  s += '  </ul>';
                  s += '</div>';
                  }

                  $("#timeline-content").append(s);
                }
              }
            }
          });
      });

      $(document).on("click", ".demos-title-left", function() {
        window.location.href = "${ctx}/hyqh5/shipmove/shipList";
      });
    
      $(document).on("click", ".demos-title-right", function() {
        $.actions({
        title: "选择操作",
        onClose: function() {
          console.log("close");
        },
        actions: [
          <c:if test="${!canModify}">
          {
            text: "移除船舶",
            className: "color-primary",
            onClick: function() {
                  $.confirm("移除船舶只是取消对船舶动态的查看权，并不会删除任何船舶动态。", "你确认移除船舶吗?", function() {
                $.ajax({
                    method : "GET",
                    data : {
                        mmsi : "${shipInfoData.mmsi}"
                    },
                    url : "${ctx}/hyqh5/shipmove/removeShipShare",
                    datatype : "json",
                    success : function(data) {
                      var returnCode = $(data)[0].returnCode;
                      var message = $(data)[0].message;
                      if (returnCode == "Failure") {
                        $.alert(message, "错误！");
                        return false;
                      } else {
                        $.toast("成功移除船舶！");
                        window.location.href = "${ctx}/hyqh5/shipmove/shipList";
                        return true;
                      }
                    }
                  });
              }, function() {
                //取消操作
              });
            }
          }
          </c:if>
          <c:if test="${canModify}">
          {
            text: "授权管理",
            className: "color-warning",
            onClick: function() {
              window.location.href = "${ctx}/hyqh5/shipmove/shipShareList?mmsi=${shipInfoData.mmsi}";
            }
          },
          {
            text: "添加动态",
            className: "color-primary",
            onClick: function() {
              window.location.href = "${ctx}/hyqh5/shipmove/arvlftEdit?mmsi=${shipInfoData.mmsi}&arvlftId=0";
            }
          },
          {
            text: "修改最新",
            className: "color-primary",
            onClick: function() {
              window.location.href = "${ctx}/hyqh5/shipmove/arvlftEdit?mmsi=${shipInfoData.mmsi}&arvlftId=${lastArvlftData.id}";
            }
          },
          {
            text: "删除最新",
            className: 'color-danger',
            onClick: function() {
              $.confirm("删除船舶动态操作只能删除最新的动态记录，即最后一条。", "你确认删除吗?", function() {
                $.ajax({
                    method : "GET",
                    data : {
                        arvlftId : "${lastArvlftData.id}"
                    },
                    url : "${ctx}/hyqh5/shipmove/arvlftDelete",
                    datatype : "json",
                    success : function(data) {
                      var returnCode = $(data)[0].returnCode;
                      var message = $(data)[0].message;
                      if (returnCode == "Failure") {
                        $.alert(message, "错误！");
                        return false;
                      } else {
                        $.toast("成功删除最后一条船舶动态记录！");
                        window.location.href = "${ctx}/hyqh5/shipmove/arvlftList?mmsi=${shipInfoData.mmsi}";
                        return true;
                      }
                    }
                  });
              }, function() {
                //取消操作
              });
            }
          }
          </c:if>
        ]
        });
      });
    </script>
  </body>
</html>

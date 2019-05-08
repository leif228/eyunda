<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <title>服务列表</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <meta name="description" content="Write an awesome description for your new site here. You can edit this line in _config.yml. It will appear in your document head meta (for Google search results) and in your feed.xml site description.">

    <link rel="stylesheet" href="${ctx}/hyqh5/lib/weui.min.css">
    <link rel="stylesheet" href="${ctx}/hyqh5/css/jquery-weui.css">
    <link rel="stylesheet" href="${ctx}/hyqh5/demos/css/demos.css">
  </head>

  <body ontouchstart>

    <header class="weui-flex " style="text-align: center;">
      <div class="demos-title-left">
        <div class="weui-grid__icon">
          <span class="mui-icon mui-icon-back"></span>
        </div>
      </div>
      <div class="weui-flex__item demos-title-center">我的服务</div>
      <div class="demos-title-right">
        <div class="weui-grid__icon">
          <span class="mui-icon mui-icon-bars"></span>
        </div>
      </div>
    </header>
    <div class="weui-grids">
      <c:forEach var="appData" items="${appDatas}">
      <a href="${appData.appUrl}?sessionId=${sessionId}" class="weui-grid js_grid" target="_blank">
        <div class="weui-grid__icon">
          <img src="${ctx}/download/imageDownload?url=${appData.appIcon}" alt="">
        </div>
        <p class="weui-grid__label">
          ${appData.appName}
        </p>
      </a>
      </c:forEach>
    </div>

    <div class="weui-btn-area">
      <!-- <a class="weui-btn weui-btn_primary" href="javascript:" id="addApp">更多服务...</a> -->
    </div>

    <script src="${ctx}/hyqh5/lib/jquery-2.1.4.js"></script>
    <script src="${ctx}/hyqh5/lib/fastclick.js"></script>
    <script src="${ctx}/hyqh5/js/jquery-weui.js"></script>
    <script>
      $(function() {
        FastClick.attach(document.body);
      });

      $(document).on("click", ".demos-title-left", function() {
        history.go(-1);
      });

      $(document).on("click", ".demos-title-right", function() {
        $.actions({
          title: "选择操作",
          onClose: function() {
            console.log("close");
          },
          actions: [
            {
              text: "退出",
              className: "color-primary",
              onClick: function() {
            	    $.ajax({
                  method : "get",
                  data : { },
                  url : "${ctx}/hyquan/login/logout",
                  datatype : "json",
                  success : function(data) {
                    var returnCode = $(data)[0].returnCode;
                    var message = $(data)[0].message;
                    if (returnCode == "Failure") {
                      $.toast(message, "cancel", function(toast) {
                          console.log(toast);
                        });
                      return false;
                    } else {
                      $.toast(message, function() {
                          console.log('close');
                          window.location.href="${ctx}/hyquan/login/login";
                        });
                      return true;
                    }
                  }
                });
              }
            }
          ]
        });
      });
    </script>

  </body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <title>船舶证书 - 批量授权</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <meta name="description" content="Write an awesome description for your new site here. You can edit this line in _config.yml. It will appear in your document head meta (for Google search results) and in your feed.xml site description.">

    <link rel="stylesheet" href="${ctx}/hyqh5/lib/weui.min.css">
    <link rel="stylesheet" href="${ctx}/hyqh5/css/jquery-weui.css">
    <link rel="stylesheet" href="${ctx}/hyqh5/demos/css/demos.css">
    
    <style>
    .leftLineStart:before{
    content: " ";
    position: absolute;
    left: 0;
    top: 0;
    right: 0;
    height: 1px;
    border-top: 1px solid #d9d9d9;
    color: #d9d9d9;
    -webkit-transform-origin: 0 0;
    transform-origin: 0 0;
    -webkit-transform: scaleY(.5);
    transform: scaleY(.5);
    left: 0px;
    }
    </style>
  </head>

  <body ontouchstart>

  <header class="weui-flex " style="text-align: center;">
      <div class="demos-title-left">
        <div class="weui-grid__icon">
          <span class="mui-icon mui-icon-back"></span>
        </div>
      </div>
      <div class="weui-flex__item demos-title-center">
        批量授权
      </div>
      <div class="demos-title-right">
        <div class="weui-grid__icon">
          <!-- <span class="mui-icon mui-icon-bars"></span> -->
        </div>
      </div>
    </header>

    <div class="bd">
      <div class="page__bd">
      <form method="post" id="shipShareForm" action="">

        <div class="weui-cells__title">指定用户</div>
        <div class="weui-cells weui-cells_form">
          <div class="weui-cell">
            <div class="weui-cell__hd">
              <label class="weui-label">用户</label>
            </div>
            <div class="weui-cell__bd">
              <input class="weui-input login_name" type="text" name="loginName" value="" placeholder="请输入登录名或手机号">
            </div>          	
          </div>

          <div class="weui-cells__title">用户权限</div>
          <div class="weui-cells weui-cells_checkbox">

            <c:forEach var="data" items="${shipInfoDatas}">

            <label class="weui-cell weui-check__label" style="float:left;width:30px;" for="shipId${data.id}">
              <div class="weui-cell__hd">
                <input type="checkbox" class="weui-check" name="shipId" id="shipId${data.id}" value="${data.id}" checked="checked">
                <i class="weui-icon-checked"></i>
              </div>
            </label>
            <div class="weui-cell leftLineStart" style="padding-left:0px;">
              <div class="weui-cell__bd">
                ${data.shipName}
                (
                <input class="startDate" name="startDate${data.id}" type="text" value="${toDay}" style="width:80px;">
                ~
                <input class="endDate" name="endDate${data.id}" type="text" value="${oneMonthAfter}" style="width:80px;">
                )
              </div>
            </div>
            <div style="clear:both"></div>
            </c:forEach>

          </div>
        </div>
        <div class="weui-cells__tips">说明：请输入指定用户的登录名或手机号，授权船舶下所有证书查看权给指定用户。</div>

      </form>
      </div>
    </div>
    
    <div class="button_sp_area">
      <a class="weui-btn weui-btn_mini weui-btn_primary" href="javascript:" id="saveShipShare">保存</a>
    </div>

    <script src="${ctx}/hyqh5/lib/jquery-2.1.4.js"></script>
    <script src="${ctx}/hyqh5/lib/jquery.form-3.51.js"></script>
    <script src="${ctx}/hyqh5/lib/jquery.validate.js"></script>

    <script src="${ctx}/hyqh5/lib/fastclick.js"></script>
    <script src="${ctx}/hyqh5/js/jquery-weui.js"></script>
    <script>
      $(function() {
        FastClick.attach(document.body);
      });

      $(document).on("click", ".demos-title-left", function() {
        history.go(-1);
      });

      $(document).on("click", "#saveShipShare", function() {
          $("#shipShareForm").ajaxSubmit({
              method: "post",
              url: "${ctx}/hyqh5/shipinfo/saveShipShares",
              datatype: "json",
              success: function(data) {
                  var returnCode = $(data)[0].returnCode;
                  var message = $(data)[0].message;
                  if (returnCode == "Failure") {
                      $.alert(message, "失败！");
                      return false;
                  } else {
                      $.alert(message, "成功！");
                      window.location.href = "${ctx}/hyqh5/shipinfo/myShipList";
                      return true;
                  }
              }
          });
      });

      $(".startDate").calendar({
        onChange: function (p, values, displayValues) {
          console.log(values, displayValues);
        }
      });

      $(".endDate").calendar({
        onChange: function (p, values, displayValues) {
          console.log(values, displayValues);
        }
      });
      
    </script>

  </body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <title>船舶动态 - 分享权限</title>
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
      <div class="weui-flex__item demos-title-center">
        分享权限 - ${shipInfoData.shipName}
      </div>
      <div class="demos-title-right">
        <div class="weui-grid__icon">
          <!-- <span class="mui-icon mui-icon-bars"></span> -->
        </div>
      </div>
    </header>

    <div class="bd">
      <div class="page__bd">

        <div class="weui-cells__title">指定用户</div>
        <div class="weui-cells weui-cells_form">
          <form method="post" id="shipShareForm" action="">
          <div class="weui-cell">
            <div class="weui-cell__hd">
              <label class="weui-label">用户</label>
            </div>
            <div class="weui-cell__bd">
              <input type="hidden" name="usId" value="${userShipData.id}">
              <input type="hidden" name="mmsi" value="${shipInfoData.mmsi}">
              <input class="weui-input" type="text" name="loginName" value="${userShipData.userData.loginName}" placeholder="请输入登录名或手机号">
            </div>
          </div>
          <div class="weui-cells__title">用户权限</div>
            <div class="weui-cells weui-cells_radio">
              <c:forEach var="rights" items="${rightss}">
              <label class="weui-cell weui-check__label" for="${rights}">
                <div class="weui-cell__bd">
                  <p>${rights.description}</p>
                </div>
                <div class="weui-cell__ft">
                  <input type="radio" class="weui-check" name="rights" id="${rights}" value="${rights}" <c:if test="${rights==userShipData.rights}">checked="checked"</c:if>>
                  <span class="weui-icon-checked"></span>
                </div>
              </label>
              </c:forEach>
              
            </div>
            </form>
        </div>
        <div class="weui-cells__tips">说明：添加新用户时，请输入指定用户的登录名或手机号，分享船舶查看或维护权限给指定用户。修改用户时，可改变用户权限。</div>

      </div>
    </div>
    
    <div class="button_sp_area">
      <c:if test="${canModify}">
      <a class="weui-btn weui-btn_mini weui-btn_primary" href="javascript:" id="saveShipShare">保存</a>
      </c:if>
      <c:if test="${userShipData.id > 0}">
      <a class="weui-btn weui-btn_mini weui-btn_warn" href="javascript:" id="deleteShipShare">删除</a>
      </c:if>
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
              url: "${ctx}/hyqh5/shipmove/saveShipShare",
              datatype: "json",
              success: function(data) {
                  var returnCode = $(data)[0].returnCode;
                  var message = $(data)[0].message;
                  if (returnCode == "Failure") {
                      $.alert(message, "失败！");
                      return false;
                  } else {
                      $.alert(message, "成功！");
                      window.location.href = "${ctx}/hyqh5/shipmove/shipShareList?mmsi=${shipInfoData.mmsi}";
                      return true;
                  }
              }
          });
      });

      $(document).on("click", "#deleteShipShare", function() {
          $.confirm("删除用户权限将取消用户查看船舶动态的权限。", "你确认删除吗?", function() {
              $.ajax({
                  method : "GET",
                  data : {
                      usId : "${userShipData.id}",
                      mmsi : "${userShipData.mmsi}"
                  },
                  url : "${ctx}/hyqh5/shipmove/deleteShipShare",
                  datatype : "json",
                  success : function(data) {
                    var returnCode = $(data)[0].returnCode;
                    var message = $(data)[0].message;
                    if (returnCode == "Failure") {
                      $.alert(message, "错误！");
                      return false;
                    } else {
                      $.toast("成功删除用户权限！");
                      window.location.href = "${ctx}/hyqh5/shipmove/shipShareList?mmsi=${shipInfoData.mmsi}";
                      return true;
                    }
                  }
                });
            }, function() {
              //取消操作
            });
      });

    </script>

  </body>
</html>

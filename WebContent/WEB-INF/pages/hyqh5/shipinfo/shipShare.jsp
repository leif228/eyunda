<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <title>船舶证书 - 授权管理</title>
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
        授权管理 - ${shipInfoData.shipName}
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
              <input type="hidden" name="usId" value="${userShipData.id}">
              <input type="hidden" name="shipId" value="${shipInfoData.id}">
              <input class="weui-input login_name" type="text" name="loginName" value="${userShipData.userData.loginName}" placeholder="请输入登录名或手机号">
            </div>
          	<div class="weui-cell_ft"><a href="javascript:;" class="weui-btn weui-btn_mini weui-btn_plain-primary select-user">选择</a></div>
          </div>

          <div class="weui-cells__title">用户权限</div>
          <div class="weui-cells weui-cells_checkbox">

            <c:forEach var="certificateData" items="${certificateDatas}">
            
            <c:set var="flag" value="false" />
            <c:forEach var="certRightsData" items="${userShipData.certRightsDatas}">
            <c:if test="${certRightsData.certificateData.certType == certificateData.certType}">
            <c:set var="flag" value="true" />
            <c:set var="crd" value="${certRightsData}" />
            </c:if>
            </c:forEach>

            <label class="weui-cell weui-check__label" style="float:left;width:30px;" for="certId${certificateData.id}">
              <div class="weui-cell__hd">
                <input type="checkbox" class="weui-check" name="certId" id="certId${certificateData.id}" value="${certificateData.id}" <c:if test="${flag == true}">checked="checked"</c:if>>
                <i class="weui-icon-checked"></i>
              </div>
            </label>
            <div class="weui-cell leftLineStart" style="padding-left:0px;">
              <div class="weui-cell__bd">
                ${certificateData.certType.description}
                <c:if test="${flag == true}">(
                <input class="startDate" name="startDate${certificateData.id}" type="text" value="${crd.startDate}" style="width:80px;">~
                <input class="endDate" name="endDate${certificateData.id}" type="text" value="${crd.endDate}" style="width:80px;">)
                </c:if>
                <c:if test="${flag == false}">(
                <input class="startDate" name="startDate${certificateData.id}" type="text" value="${toDay}" style="width:80px;">~
                <input class="endDate" name="endDate${certificateData.id}" type="text" value="${oneMonthAfter}" style="width:80px;">)
                </c:if>
              </div>
            </div>
            <div style="clear:both"></div>
            </c:forEach>

          </div>
        </div>
        <div class="weui-cells__tips">说明：添加新用户时，请输入指定用户的登录名或手机号，授权船舶证书查看权给指定用户。修改时，则可以给用户重新授权。</div>

      </form>
      </div>
    </div>
    
    <div class="button_sp_area">
      <a class="weui-btn weui-btn_mini weui-btn_primary" href="javascript:" id="saveShipShare">保存</a>
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
              url: "${ctx}/hyqh5/shipinfo/saveShipShare",
              datatype: "json",
              success: function(data) {
                  var returnCode = $(data)[0].returnCode;
                  var message = $(data)[0].message;
                  if (returnCode == "Failure") {
                      $.alert(message, "失败！");
                      return false;
                  } else {
                      $.alert(message, "成功！");
                      window.location.href = "${ctx}/hyqh5/shipinfo/shipShareList?shipId=${shipInfoData.id}";
                      return true;
                  }
              }
          });
      });

      $(document).on("click", "#deleteShipShare", function() {
          $.confirm("删除用户权限将取消用户查看船舶证书的权限。", "你确认删除吗?", function() {
              $.ajax({
                  method : "GET",
                  data : {
                      usId : "${userShipData.id}",
                      shipId : "${shipInfoData.id}"
                  },
                  url : "${ctx}/hyqh5/shipinfo/deleteShipShare",
                  datatype : "json",
                  success : function(data) {
                    var returnCode = $(data)[0].returnCode;
                    var message = $(data)[0].message;
                    if (returnCode == "Failure") {
                      $.alert(message, "错误！");
                      return false;
                    } else {
                      $.toast("成功删除用户权限！");
                      window.location.href = "${ctx}/hyqh5/shipinfo/shipShareList?shipId=${shipInfoData.id}";
                      return true;
                    }
                  }
                });
            }, function() {
              //取消操作
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
      
      $(".select-user").select({
    	  title: "选择授权过的用户",
    	  multi: false,
          items: [
            <c:forEach var="ud" items="${userDatas}">
            {
              title: "${ud.trueName}"+"("+"${ud.mobile}"+")",
              value: "${ud.mobile}",
            },
            </c:forEach>
          ],
          onChange: function(obj) {
        	  $(".login_name").val(obj.values);
          },
      });

    </script>

  </body>
</html>

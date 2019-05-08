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

        <div class="weui-cells__title">用户列表</div>
        <div class="weui-cells">
          <c:forEach var="userShipData" items="${userShipDatas}">
          <a class="weui-cell weui-cell_access editShipShare" href="javascript:;" idVal="${userShipData.id}">
            <div class="weui-cell__hd">
              <img src="${ctx}/hyquan/download/imageDownload?url=${userShipData.userData.userLogo}" alt="" class="weui-media-box__thumb" style="width: 30px; height: 30px;">
            </div>
            <div class="weui-cell__bd">
              <p>${userShipData.userData.trueName}(${userShipData.userData.mobile})</p>
            </div>
            <div class="weui-cell__ft">${userShipData.rights.description}</div>
          </a>
          </c:forEach>
        </div>

      </div>
    </div>
    
    <c:if test="${canModify}">
    <div class="weui-btn-area">
      <a class="weui-btn weui-btn_primary" href="javascript:" id="addShipShare">添加</a>
    </div>
    </c:if>

    <script src="${ctx}/hyqh5/lib/jquery-2.1.4.js"></script>
    <script src="${ctx}/hyqh5/lib/fastclick.js"></script>
    <script src="${ctx}/hyqh5/js/jquery-weui.js"></script>
    <script>
      $(function() {
        FastClick.attach(document.body);
      });

      $(document).on("click", ".demos-title-left", function() {
        window.location.href = "${ctx}/hyqh5/shipmove/arvlftList?mmsi=${shipInfoData.mmsi}";
      });

      $(document).on("click", ".editShipShare", function() {
  	    var id = $(this).attr("idVal");
    	    window.location.href = "${ctx}/hyqh5/shipmove/shipShare?usId="+id+"&mmsi=${shipInfoData.mmsi}";
      });
      
      $("#addShipShare").click(function() {
    	    window.location.href = "${ctx}/hyqh5/shipmove/shipShare?usId=0&mmsi=${shipInfoData.mmsi}";
      });

    </script>

  </body>
</html>

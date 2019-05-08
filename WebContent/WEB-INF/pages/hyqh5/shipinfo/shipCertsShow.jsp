<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <title>船舶证书 - ${shipInfoData.shipName}</title>
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
        船舶证书 - ${shipInfoData.shipName}
      </div>
      <div class="demos-title-right">
        <div class="weui-grid__icon">
          <span class="mui-icon mui-icon-bars"></span>
        </div>
      </div>
    </header>

    <div class="page__bd">

      <div class="weui-panel__hd">船舶证书列表</div>
      <div class="weui-panel weui-panel_access">
        <div class="weui-panel__bd" id="list">
          <c:forEach var="certificateData" items="${certificateDatas}">
          <a href="${ctx}/hyqh5/shipinfo/shipCertShow?certId=${certificateData.id}&shipId=${shipInfoData.id}" class="weui-media-box weui-media-box_appmsg">
            <c:if test="${!empty certificateData.certAttaDatas}">
            <div class="weui-media-box__hd">
              <img src="${ctx}/hyquan/download/imageDownload?url=${certificateData.certAttaDatas[0].smallImgUrl}" alt="" class="weui-media-box__thumb" style="width: 60px; height: 60px;">
            </div>
            </c:if>
            <c:if test="${empty certificateData.certAttaDatas}">
            <div class="weui-media-box__hd">
              <img src="${ctx}/hyquan/download/imageDownload?url=/default/certificate.jpg" alt="" class="weui-media-box__thumb" style="width: 60px; height: 60px;">
            </div>
            </c:if>
            <div class="weui-media-box__bd">
              <h4 class="weui-media-box__title">${certificateData.certType.description}<span style="padding-right: 13px;">(${certificateData.status.description})</span></h4>
              <%-- <p class="weui-media-box__desc">发证日期：${certificateData.issueDate}，到期日期：${certificateData.maturityDate}</p> --%>
              <c:if test="${not empty certificateData.maturityDate}">
              	<p class="weui-media-box__desc">有效期：${certificateData.maturityDate}</p>
              </c:if>
            </div>
          </a>
          </c:forEach>
        </div>
      </div>

      <div class="weui-cells__title">船舶属性</div>
      <div class="weui-cells">
        <%-- <div class="weui-cell">
          <div class="weui-cell__hd" style="width:30%;">船舶图标</div>
          <div class="weui-cell__bd" style="margin:0px 10px;">
            <img src="${ctx}/hyquan/download/imageDownload?url=${shipInfoData.shipLogo}" alt="" class="weui-media-box__thumb" style="width: 60px; height: 60px;">
          </div>
        </div> --%>
        <div class="weui-cell">
          <div class="weui-cell__hd" style="width:30%;">船类</div>
          <div class="weui-cell__bd" style="margin:0px 10px;">${shipInfoData.shipType.description}</div>
        </div>
        <div class="weui-cell">
          <div class="weui-cell__hd" style="width:30%;">船舶名称</div>
          <div class="weui-cell__bd" style="margin:0px 10px;">${shipInfoData.shipName}</div>
        </div>
        <div class="weui-cell">
          <div class="weui-cell__hd" style="width:30%;">MMSI编号</div>
          <div class="weui-cell__bd" style="margin:0px 10px;">${shipInfoData.mmsi}</div>
        </div>
        <div class="weui-cell">
          <div class="weui-cell__hd" style="width:30%;">船舶描述</div>
          <div class="weui-cell__bd" style="margin:0px 10px;">${shipInfoData.shipDesc}</div>
        </div>
        <div class="weui-cell">
          <div class="weui-cell__hd" style="width:30%;">船舶航区</div>
          <div class="weui-cell__bd" style="margin:0px 10px;">${shipInfoData.sailArea}</div>
        </div>
        <div class="weui-cell">
          <div class="weui-cell__hd" style="width:30%;">前或后置驾驶</div>
          <div class="weui-cell__bd" style="margin:0px 10px;">${shipInfoData.bonnet}</div>
        </div>
        <div class="weui-cell">
          <div class="weui-cell__hd" style="width:30%;">货舱监控</div>
          <div class="weui-cell__bd" style="margin:0px 10px;">${shipInfoData.cabinMonitor}</div>
        </div>
      </div>

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
    
      $(document).on("click", ".demos-title-right", function() {
        $.actions({
        title: "选择操作",
        onClose: function() {
          console.log("close");
        },
        actions: [
          <c:if test="${canFavorite == 'canfavorite'}">
          {
            text: "收藏",
            className: "color-primary",
            onClick: function() {
              $.confirm("收藏将指定船舶添加到我的收藏夹中。", "你确认收藏此船舶吗?", function() {
                $.ajax({
                    method : "GET",
                    data : {
                        shipId : "${shipInfoData.id}"
                    },
                    url : "${ctx}/hyqh5/shipinfo/addShipShare",
                    datatype : "json",
                    success : function(data) {
                      var returnCode = $(data)[0].returnCode;
                      var message = $(data)[0].message;
                      if (returnCode == "Failure") {
                        $.alert(message, "错误！");
                        return false;
                      } else {
                        $.toast("成功收藏船舶！");
                        window.location.href = "${ctx}/hyqh5/shipinfo/home";
                        return true;
                      }
                    }
                  });
              }, function() {
                //取消操作
              });
            }
          },
          </c:if>
          <c:if test="${canFavorite == 'cancancel'}">
          {
            text: "移除收藏夹",
            className: "color-primary",
            onClick: function() {
              $.confirm("移除收藏夹将指定船舶从收藏夹中移除。", "你确认移除船舶吗?", function() {
                $.ajax({
                    method : "GET",
                    data : {
                        shipId : "${shipInfoData.id}"
                    },
                    url : "${ctx}/hyqh5/shipinfo/removeShipShare",
                    datatype : "json",
                    success : function(data) {
                      var returnCode = $(data)[0].returnCode;
                      var message = $(data)[0].message;
                      if (returnCode == "Failure") {
                        $.alert(message, "错误！");
                        return false;
                      } else {
                        $.toast("成功移除船舶！");
                        window.location.href = "${ctx}/hyqh5/shipinfo/home";
                        return true;
                      }
                    }
                  });
              }, function() {
                //取消操作
              });
            }
          },
          </c:if>
        ]
        });
      });
    </script>
  </body>
</html>

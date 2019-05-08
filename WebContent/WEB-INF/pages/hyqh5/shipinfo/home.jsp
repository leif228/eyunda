<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <title>船舶证书 - 首页</title>
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
      <div class="weui-flex__item demos-title-center">船舶证书 - 首页</div>
      <div class="demos-title-right">
        <div class="weui-grid__icon">
          <span class="mui-icon mui-icon-bars"></span>
        </div>
      </div>
    </header>
    <div class="weui-grids">
      <a href="${ctx}/hyqh5/shipinfo/searchShipList" class="weui-grid js_grid" style="width: 50%">
        <div class="weui-grid__icon">
          <img src="${ctx}/hyquan/download/imageDownload?url=/default/shipcertlib.jpg" alt="">
        </div>
        <p class="weui-grid__label">船舶证书库</p>
      </a>
      <a href="${ctx}/hyqh5/shipinfo/myShipList" class="weui-grid js_grid" style="width: 50%">
        <div class="weui-grid__icon">
          <img src="${ctx}/hyquan/download/imageDownload?url=/default/mycertlib.jpg" alt="">
        </div>
        <p class="weui-grid__label">我的证书</p>
      </a>
    </div>
	<div class="page__bd" class='demos-content-padded'>
	  <div class="weui-panel__hd">我的收藏夹</div>
      <div class="weui-panel weui-panel_access">
        <div class="weui-panel__bd" id="list">
          <c:forEach var="userShipData" items="${pageData.result}">
          <c:set var="shipInfoData" value="${userShipData.shipInfoData}" />
          <a href="${ctx}/hyqh5/shipinfo/shipCertsShow?shipId=${shipInfoData.id}" class="weui-media-box weui-media-box_appmsg">
            <div class="weui-media-box__hd">
              <img src="${ctx}/hyquan/download/imageDownload?url=${shipInfoData.shipSmallLogo}" alt="" class="weui-media-box__thumb" style="width: 60px; height: 60px;">
            </div>
            <div class="weui-media-box__bd">
              <h4 class="weui-media-box__title">${shipInfoData.shipName}</h4>
              <p class="weui-media-box__desc" style="display:inline;">
	              <c:if test="${!empty shipInfoData.createrUserData}">
	              	【${shipInfoData.createrUserData.trueName}】,
	               </c:if>数量：${shipInfoData.certCount}
              </p>
             <%--  <p class="weui-media-box__desc" style="display:inline;">来源：${shipInfoData.certCount}</p> --%>
            </div>
          </a>
          </c:forEach>
        </div>
      </div>
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
              pageNo : pageNo
            },
            url : "${ctx}/hyqh5/shipinfo/favoriteShipPage",
            datatype : "json",
            success : function(data) {
              var returnCode = $(data)[0].returnCode;
              var message = $(data)[0].message;
              if (returnCode == "Failure") {
                $.alert("服务端数据请求失败！", "错误！");
                return false;
              } else {
                for (var i=0;i<$(data)[0].content.result.length;i++){
                  var usd = $(data)[0].content.result[i];
                  var d = usd.shipInfoData;
                  var s = '';
                  s += '            <a href="${ctx}/hyqh5/shipinfo/shipCertsShow?shipId='+d.id+'" class="weui-media-box weui-media-box_appmsg">';
                  s += '              <div class="weui-media-box__hd">';
                  s += '                <img src="${ctx}/hyquan/download/imageDownload?url='+d.shipSmallLogo+'" alt="" class="weui-media-box__thumb" style="width: 60px; height: 60px;">';
                  s += '              </div>';
                  s += '              <div class="weui-media-box__bd">';
                  s += '                <h4 class="weui-media-box__title">'+d.shipName+'</h4>';
                //  s += '                <p class="weui-media-box__desc">证书数量：'+d.certCount+'</p>';
                  						
                  s +=   '<p class="weui-media-box__desc" style="display:inline;">';
	              if(d.createrUserData != null){
	              	s +='【'+d.createrUserData.trueName+'】,';
	              }
	               s +='数量：'+d.certCount;
	               s +='</p>';
                  
                  s += '              </div>';
                  s += '            </a>';
                  $("#list").append(s);
                }
              }
            }
          });
      });

      $(document).on("click", ".demos-title-left", function() {
        // window.location.href = "${ctx}/hyquan/app/appView";
        window.androidJava.finishActivity();
      });

      $(document).on("click", ".demos-title-right", function() {
        $.actions({
          title: "选择操作",
          onClose: function() {
            console.log("close");
          },
          actions: [
          ]
        });
      });
    </script>
  </body>
</html>

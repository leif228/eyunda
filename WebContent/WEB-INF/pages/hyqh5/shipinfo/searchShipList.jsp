<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <title>船舶证书 - 船舶证书库</title>
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
      <div class="weui-flex__item demos-title-center">船舶证书 - 船舶证书库</div>
      <div class="demos-title-right">
        <div class="weui-grid__icon">
          <span class="mui-icon mui-icon-bars"></span>
        </div>
      </div>
    </header>
    <div class="weui-search-bar" id="searchBar">
      <form class="weui-search-bar__form">
        <div class="weui-search-bar__box">
          <i class="weui-icon-search"></i>
          <input type="search" class="weui-search-bar__input" id="keywords" name="keywords" value="${keywords}" placeholder="搜索" required="">
          <a href="javascript:" class="weui-icon-clear" id="searchClear"></a>
        </div>
        <label class="weui-search-bar__label" id="searchText" style="transform-origin: 0px 0px 0px; opacity: 1; transform: scale(1, 1);">
          <i class="weui-icon-search"></i>
          <span>搜索</span>
        </label>
      </form>
      <a href="javascript:" class="weui-search-bar__cancel-btn" id="searchCancel">取消</a>
    </div>
    <div class="page__bd" class='demos-content-padded'>
      <div class="weui-panel weui-panel_access">
        <div class="weui-panel__bd" id="list">
          <c:forEach var="shipInfoData" items="${pageData.result}">
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
                keywords : $("#keywords").val(),
                pageNo : pageNo
            },
            url : "${ctx}/hyqh5/shipinfo/searchShipPage",
            datatype : "json",
            success : function(data) {
              var returnCode = $(data)[0].returnCode;
              var message = $(data)[0].message;
              if (returnCode == "Failure") {
                $.alert("服务端数据请求失败！", "错误！");
                return false;
              } else {
                for (var i=0;i<$(data)[0].content.result.length;i++){
                  var d = $(data)[0].content.result[i];
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
        window.location.href = "${ctx}/hyqh5/shipinfo/home";
      });

    </script>
  </body>
</html>

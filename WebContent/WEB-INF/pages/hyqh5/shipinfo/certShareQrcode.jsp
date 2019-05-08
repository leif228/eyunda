<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <title>船舶证书 - 证书分享二维码</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <meta name="description" content="Write an awesome description for your new site here. You can edit this line in _config.yml. It will appear in your document head meta (for Google search results) and in your feed.xml site description.">

    <link rel="stylesheet" href="${ctx}/hyqh5/lib/weui.min.css">
    <link rel="stylesheet" href="${ctx}/hyqh5/css/jquery-weui.css">
    <link rel="stylesheet" href="${ctx}/hyqh5/demos/css/demos.css">
  </head>

  <body ontouchstart>

    <div class="demos-content-padded" style="display:none;">
      <a href="javascript:;" id="popup" class="weui-btn weui-btn_primary open-popup" data-target="#full">popup</a>
    </div>

    <div id="full" class="weui-popup__container">
      <div class="weui-popup__overlay"></div>
      <div class="weui-popup__modal">
        <header class='demos-header'>
          <h2 class="demos-second-title">证书分享二维码</h2>
          <p class="demos-sub-title">${shipInfoData.shipName}《${certificateData.certType.description}》</p>
        </header>
        <article class="weui-article">
          <section>
            <section>
              <p>
                使用相关软件扫描下面二维码可查看证书内容并下载，你可以拷屏并通过微信、qq等分享该二维码图片给你的好友或朋友圈。
              </p>
              <p style="text-align:center;">
                <img src="${ctx}/hyquan/download/imageDownload?url=${certShareQrcodeUrl}" alt="">
              </p>
            </section>
          </section>
        </article>
      </div>
    </div>

    <script src="${ctx}/hyqh5/lib/jquery-2.1.4.js"></script>
    <script src="${ctx}/hyqh5/lib/fastclick.js"></script>
    <script src="${ctx}/hyqh5/js/jquery-weui.js"></script>
    <script>
      $(function() {
          FastClick.attach(document.body);
      });
      
      $('#popup').trigger("click");
    </script>
  </body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <title>船舶证书 - 证书页</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <meta name="description" content="Write an awesome description for your new site here. You can edit this line in _config.yml. It will appear in your document head meta (for Google search results) and in your feed.xml site description.">

    <link rel="stylesheet" href="${ctx}/hyqh5/lib/weui.min.css">
    <link rel="stylesheet" href="${ctx}/hyqh5/css/jquery-weui.css">
    <link rel="stylesheet" href="${ctx}/hyqh5/demos/css/demos.css">
    
     <style>
		.mapgps {
			display:block;
			position: absolute;
			z-index: 1001;
			left: 5px;
			top:5px;
			width: 40px;
			height: 40px;
			background: url(../.././img/back.png) no-repeat;
			background-size: 100%;
			cursor: pointer;
		}
		</style>
  </head>

  <body ontouchstart>
  
  	<div class="mapgps" title="定位当前船舶"></div>

    <div class="weui-gallery" style="display: block">
      <span class="weui-gallery__img" style="background-image: url(${ctx}/hyquan/download/imageDownload?url=${certAttaData.showImgUrl});"></span>
      <div class="weui-gallery__opr">
        <a href="javascript:delCertAttaImg(this);" class="weui-gallery__del" idVal="${certAttaData.id}">
          <i class="weui-icon-delete weui-icon_gallery-delete"></i>
        </a>
      </div>
    </div>
    
    <script src="${ctx}/hyqh5/lib/jquery-2.1.4.js"></script>
    <script src="${ctx}/hyqh5/lib/fastclick.js"></script>
    <script src="${ctx}/hyqh5/js/jquery-weui.js"></script>
    <script>
      $(function() {
        FastClick.attach(document.body);
      });

      $(document).on("click", ".demos-title-left", function() {
        window.location.href = "${ctx}/hyqh5/shipinfo/certificateEdit?certId=${certAttaData.certId}&shipId=${shipInfoData.id}";
      });
      
      function delCertAttaImg(obj) {
    	    var id = $(obj).attr("idVal");
    	    $.confirm("本操作将会删除船舶证书当前页。", "你确认删除吗?", function() {
            $.ajax({
              method : "GET",
              data : {
              	attaId : "${certAttaData.id}"
              },
              url : "${ctx}/hyqh5/shipinfo/deleteCertAttaImg",
              datatype : "json",
              success : function(data) {
                var returnCode = $(data)[0].returnCode;
                var message = $(data)[0].message;
                if (returnCode == "Failure") {
                  $.alert("船舶证书页删除失败！", "错误！");
                  return false;
                } else {
                  $.toast("船舶证书页已经被删除！");
                  window.location.href = "${ctx}/hyqh5/shipinfo/certificateEdit?certId=${certAttaData.certId}&shipId=${shipInfoData.id}";
                  return true;
                }
              }
            });
        }, function() {
          //取消操作
        	window.location.href = "${ctx}/hyqh5/shipinfo/certificateEdit?certId=${certAttaData.certId}&shipId=${shipInfoData.id}";
        });
      }
      
      $(".mapgps").click(function() {
    	  history.go(-1);
      });
    
    </script>
  </body>
</html>

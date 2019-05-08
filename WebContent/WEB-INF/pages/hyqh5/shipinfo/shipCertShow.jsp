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
    
    <style>
	.back {
		display:none;
		position: absolute;
		z-index: 1000;
		left: 5px;
		top:5px;
		width: 40px;
		height: 40px;
		background: url(../.././img/back.png) no-repeat;
		background-size: 100%;
		cursor: pointer;
	}
	.org {
		display:none;
		position: absolute;
		z-index: 1000;
		right: 5px;
		top:5px;
		width: 40px;
		height: 40px;
		background: url(../.././img/ori.png) no-repeat;
		background-size: 100%;
		cursor: pointer;
	}
	</style>
  </head>

  <body ontouchstart>
  	 <div class="back" title="返回"></div>
  	 <div class="org" title="原图"></div>

    <header class="weui-flex " style="text-align: center;">
      <div class="demos-title-left">
        <div class="weui-grid__icon">
          <span class="mui-icon mui-icon-back"></span>
        </div>
      </div>
      <div class="weui-flex__item demos-title-center">${shipInfoData.shipName} - ${certificateData.certType.description}</div>
      <div class="demos-title-right">
        <div class="weui-grid__icon">
          <!-- <span class="mui-icon mui-icon-bars"></span> -->
        </div>
      </div>
    </header>

    <div class="bd">
      <div class="page__bd">

        <div class="weui-cells__title">证书内容</div>
        <div class="weui-cells weui-cells_form">
          <div class="weui-cell">
            <div class="weui-cell__bd">
              <div class="weui-uploader">
                <!-- <div class="weui-uploader__hd">
                  <p class="weui-uploader__title">图片</p>
                  <div class="weui-uploader__info">0/2</div>
                </div> -->
                <div class="weui-uploader__bd">
                  <ul class="weui-uploader__files" id="uploaderFiles">
                    <c:forEach var="certAttaData" items="${certificateData.certAttaDatas}">
                    <li class="weui-uploader__file pb1" idVal="${certAttaData.id}" style="background-image:url(${ctx}/hyquan/download/imageDownload?url=${certAttaData.smallImgUrl})"></li>
                    </c:forEach>
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="weui-cells__title">证书属性</div>
        <div class="weui-cells">
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">证书名称</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">${certificateData.certType.description}</div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">船舶</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">${shipInfoData.shipName}(${shipInfoData.mmsi})</div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">到期日期</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">${certificateData.maturityDate}</div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">提醒日期</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">${certificateData.remindDate}</div>
          </div>
        </div>

      </div>
    </div>
    

    <script src="${ctx}/hyqh5/lib/jquery-2.1.4.js"></script>
    <script src="${ctx}/hyqh5/lib/jquery.form-3.51.js"></script>
    <script src="${ctx}/hyqh5/lib/jquery.validate.js"></script>

    <script src="${ctx}/hyqh5/lib/fastclick.js"></script>
    <script src="${ctx}/hyqh5/js/jquery-weui.js"></script>
    <script src="${ctx}/hyqh5/js/swiper.js"></script>
    <script>
      $(function() {
        FastClick.attach(document.body);
      });

      $(document).on("click", ".demos-title-left", function() {
        history.go(-1);
      });
      
      var cads = [];
      var index_ = 0;
      var showOrg = false;
      var pbOrg ;
      <c:forEach var="certAttaData" items="${certificateData.certAttaDatas}">
      cads.push("${ctx}/hyquan/download/imageDownload?url=${certAttaData.url}")
      </c:forEach>
    
      var pb1 = $.photoBrowser({
        items: [
          <c:forEach var="certAttaData" items="${certificateData.certAttaDatas}">
          "${ctx}/hyquan/download/imageDownload?url=${certAttaData.showImgUrl}",
          </c:forEach>
        ],

        onSlideChange: function(index) {
          index_ = index;
          console.log(this, index);
        },
        onOpen: function() {
          console.log("onOpen", this);
        },
        onClose: function() {
          $(".back").css("display","none");
          $(".org").css("display","none");
          console.log("onClose", this);
        }
      });
      $(".pb1").click(function() {
    	  var index = $(this,$(".pb1")).index();
    	  index_ = index;
          pb1.open(index);
          
          $(".back").css("display","block");
          $(".org").css("display","block");
      });
      $(".back").click(function() {
    	  if(showOrg){
    		  if(pbOrg){
    			  pbOrg.close();
		    	  showOrg = false;
    			  $(".org").css("display","block");
    		  }
    	  }else{
    	   history.go(0);
    	  }
    		  
      });
      
      $(".org").click(function() {
    	  var arr = [];
    	  arr.push(cads[index_]);
    	  pbOrg = $.photoBrowser({
    	        items: arr,

    	        onSlideChange: function(index) {
    	          console.log(this, index);
    	        },
    	        onOpen: function() {
    	          $(".org").css("display","none");
    	          console.log("onOpen", this);
    	        },
    	        onClose: function() {
    	          console.log("onClose", this);
    	        }
    	      });
    	  pbOrg.open(0);
    	  showOrg = true;
      });

    </script>
  </body>
</html>

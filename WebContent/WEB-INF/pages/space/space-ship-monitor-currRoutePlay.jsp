<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>会员空间</title>
<meta http-equiv="X-UA-Compatible" content="IE=9">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
  content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />

<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="${ctx}/css/font-awesome.css" />
<link rel="stylesheet" href="${ctx}/css/adminia.css" />
<link rel="stylesheet" href="${ctx}/css/adminia-responsive.css" />
<link rel="stylesheet" href="${ctx}/css/pages/plans.css" />
<link rel="stylesheet" href="${ctx}/css/fullcalendar.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn-responsive.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn-helper.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn-icon.css" />
<link rel="stylesheet" href="${ctx}/css/datepicker.css" />
<link rel="stylesheet" href="${ctx}/css/uniform.css" />
<link rel="stylesheet" href="${ctx}/css/select2.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.main.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css"
  class="skin-color" />

<script src="${ctx}/js/jquery-v.min.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/bootstrap-colorpicker.js"></script>
<script src="${ctx}/js/bootstrap-datepicker.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/unicorn.tables.js"></script>
<script src="${ctx}/js/jquery.colorbox-min.js"></script>
<script src="${ctx}/js/space/space-ship-monitor-currRoutePlay.js"></script>

<script src="http://api.map.baidu.com/api?v=2.0&ak=3dj1jxWYQCN2UCG8MhCPVoYB" type="text/javascript"></script>
<script src="${ctx}/js/space/space-monitor-currLushu.js"></script>
<script type="text/javascript">
  var _rootPath = "${ctx}";
  var _points = [];
  var map ;
  var _pointArray = new Array();
  var _shipName = "${shipData.shipName}";
  var _mmsi = "${shipData.mmsi}";
	  
  <c:forEach var="shipCooordData" items="${shipCooordDatas}">
	_points.push(["${shipCooordData.longitude}","${shipCooordData.latitude}","${shipCooordData.posTime}"]);
  </c:forEach>
</script>

<style>
#content {
  margin-left: 0px;
}

#dlgAdd .user-info {
  list-style: none;
}

#dlgAdd .account-container {
  padding: 3px;
}

#dlgAdd .user-info>li {
  float: left;
  margin: 10px;
}

#dlgAdd .account-container:hover {
  padding: 3px;
  background: #00CCFF;
  cursor: pointer
}

.addBack {
  background: #00CCFF;
}

.widget-content {
	height : 472px;
}

div{
	font-size: 12px;
}

button {
	margin-top : 4px;
}

#imageResult {
	position: fixed;
	margin-top:-475px;
	margin-left:846px;
	display: none;
}

.close {
	cursor : pointer;
	float: left;
	margin-left:-1px;"
}
</style>
</head>

<body>
  <jsp:include page="./space-head.jsp"></jsp:include>

  <div class="span10">
    <h1 class="page-title">
      <i class="icon-star icon-white"></i> 船舶监控
    </h1>
    <div class="row">
      <div class="span10">
        <div class="widget-box">
          <div class="widget-title">
            <div>
              <h5>实时监控图</h5>
              <div class="btn-link" style="float: right; margin-top: 13px; margin-right: 12px;">
                <a class="link" href="${ctx}/space/monitor/myAllShip">返 回</a>｜<a href="#container" id="inline" style="color:#46A1EF">全屏</a>
              </div>
            </div>
            <div style="float : left; margin-left:3px; margin-top: 6px;">承运人：
        	 <c:choose>
               <c:when test="${!empty shipData.broker.trueName}">
                 <span>${shipData.broker.trueName}</span>
               </c:when>
               <c:otherwise>
                 <span>${shipData.broker.loginName}</span>
               </c:otherwise>
             </c:choose>&nbsp;&nbsp;&nbsp;
                     船名：<span>${shipData.shipName}</span>
            </div>
          </div>
          
          <input type="hidden" name="shipId" id="shipId" value="${shipData.id}"/>
          
          <div id="container" class="widget-content"></div>
        </div>
        <div id="imageResult">
          <div class="showImage" style="float: left;">
	        <img id="shipImage" style="width : 112px;" src=""/>
          </div>
          <div class="close" style="font-size: 10px;">关闭<i class="icon-trash"></i></div>
        </div>
        <div>
	      <button class="btn" id="stop">停止监控</button>
		  <button class="btn btn-info" id="hide">隐藏信息窗口</button>
		  <button class="btn btn-info" id="show">展示信息窗口</button>
		</div>
      </div>
      <!-- /span9 -->
    </div>
    <!-- /row -->
   </div>
  <!-- /row -->
  </div>
  <!-- /content -->
  <jsp:include page="./space-foot.jsp"></jsp:include>

</body>
</html>
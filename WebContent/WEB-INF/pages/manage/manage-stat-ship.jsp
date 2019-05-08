<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>管理控制台</title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link rel="stylesheet" href="${ctx}/css/bootstrap.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-responsive.css" />
<link rel="stylesheet" href="${ctx}/css/uniform.css" />
<link rel="stylesheet" href="${ctx}/css/select2.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.main.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css" class="skin-color" />

<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/unicorn.tables.js"></script>

<script type="text/javascript" src="${ctx}/js/flot/excanvas.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.flot.js"></script>
<script type="text/javascript" src="${ctx}/js/manage/manage-stat-ship.js"></script>
<script type="text/javascript">
var _rootPath = "${ctx}";
</script>
</head>

<body>

  <jsp:include page="./manage-head.jsp"></jsp:include>

  <div id="content">
    <div id="breadcrumb">
      <a href="#" style="font-size: 12px;" class="tip-bottom"> <i
        class="icon-home"></i> 统计管理
      </a> <a href="#" style="font-size: 12px;" class="current">船舶统计</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid" style="margin-top:0px">
        <div class="span12">

          <div class="widget-content">
              <div class="box-header corner-top">
                <ul id="myTab" style="margin-bottom:0px" class="nav nav-pills">
                  <li><a href="#statShip1" data-toggle="tab" class="bold">统计列表</a></li>
                  <li id="viewStatShips"><a href="#statShip2" data-toggle="tab" class="bold">船舶总数统计图</a></li>
                  <li id="viewStatUpShips"><a href="#statShip3" data-toggle="tab" class="bold">船舶上线数统计图</a></li>
                  <li id="viewStatDownShips"><a href="#statShip4" data-toggle="tab" class="bold">船舶下线数统计图</a></li> 
                </ul>
              </div>
              
              <div class="tab-content">
                <div class="tab-pane active" id="statShip1">
                  <div class="widget-box" style="margin-top:10px">
                    <div class="widget-title">
                      <h5>船舶统计列表</h5>
                    </div>
                    <div class="widget-content nopadding">
                      <table class="table table-bordered data-table">
                        <thead>
                          <tr>
                            <th>统计月份</th>
                            <th>船舶总数(艘)</th>
                            <th>船舶上线数(艘)</th>
                            <th>船舶下线数(艘)</th>
                          </tr>
                        </thead>
                        
                        <tbody>
                          <c:forEach items="${statShipDatas}" var="statShip">
	                        <tr>
	                          <td>${statShip.yearMonth}</td>
	                          <td>${statShip.sumWares}</td>
	                          <td>${statShip.upShips}</td>
	                          <td>${statShip.downShips}</td>
	                        </tr>
	                      </c:forEach>
                        </tbody>
                      </table>
                    </div>
                  </div>
                </div>

                <div class="tab-pane" id="statShip2">     
                  <div class="widget-box" style="margin-top:10px">
                    <div class="widget-title">
                      <h5>船舶总数统计图</h5>
                    </div>
                    <div class="box-body">
                      <div id="statShips" class="chart" style="width:1133px;height:300px;"> </div>
                    </div>
                  </div>
                </div>
                
                <div class="tab-pane" id="statShip3">     
                  <div class="widget-box" style="margin-top:10px">
                    <div class="widget-title">
                      <h5>船舶上线数统计图</h5>
                    </div>
                    <div class="box-body">
                      <div id="statUpShips" class="chart" style="width:1133px;height:300px;"> </div>
                    </div>
                  </div>
                </div>
                
                <div class="tab-pane" id="statShip4">     
                  <div class="widget-box" style="margin-top:10px">
                    <div class="widget-title">
                      <h5>船舶下线数统计图</h5>
                    </div>
                    <div class="box-body">
                      <div id="statDownShips" class="chart" style="width:1133px;height:300px;"> </div>
                    </div>
                  </div>
                </div>
                
              </div>
            </div>
          </div>
        </div>
      </div>
	  <jsp:include page="./manage-foot.jsp"></jsp:include>
    </div>
  
</body>
</html>
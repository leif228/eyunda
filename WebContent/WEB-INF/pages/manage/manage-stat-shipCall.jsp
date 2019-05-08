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
<script src="${ctx}/js/flot/jquery.flot.js"></script>
<script src="${ctx}/js/manage/manage-stat-shipCall.js"></script>
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
      </a> <a href="#" style="font-size: 12px;" class="current">船舶访问</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid" style="margin-top:0px">
        <div class="span12">

          <div class="widget-content">
              <div class="box-header corner-top">
                <ul id="myTab" style="margin-bottom:0px" class="nav nav-pills">
                  <li><a href="#shipCall-view1" data-toggle="tab" class="bold">统计列表</a></li>
                  <li id="viewStatShipCallNum"><a href="#shipCall-view2" data-toggle="tab" class="bold">访问次数统计图</a></li>
                  <li id="viewStatShipCallUser"><a href="#shipCall-view3" data-toggle="tab" class="bold">访问人数统计图</a></li>
                  <li id="viewStatShipCallShip"><a href="#shipCall-view4" data-toggle="tab" class="bold">访问船舶统计图</a></li>
                </ul>
              </div>
              
              <div class="tab-content">
                <div class="tab-pane active" id="shipCall-view1">
                  <div class="widget-box" style="margin-top:10px">
                    <div class="widget-title">
                      <h5>访问统计列表</h5>
                    </div>
                    <div class="widget-content nopadding">
                      <table class="table table-bordered data-table">
                        <thead>
                          <tr>
                            <th>统计月份</th>
                            <th>角色名称</th>
                            <th>访问次数(次)</th>
                            <th>访问人数(人)</th>
                            <th>访问船舶数(次)</th>
                          </tr>
                        </thead>
                        
                        <tbody>
                          <c:forEach items="${statShipCallDatas}" var="statShipCall">
	                        <tr>
	                          <td>${statShipCall.yearMonth}</td>
	                          <td>${statShipCall.roleDesc}</td>
	                          <td>${statShipCall.callNum}</td>
	                          <td>${statShipCall.calledUserNum}</td>
	                          <td>${statShipCall.calledShipNum}</td>
                          	</tr>
                          </c:forEach>
                        </tbody>
                      </table>
                    </div>
                  </div>
                </div>

                <div class="tab-pane" id="shipCall-view2">     
                  <div class="widget-box" style="margin-top:10px">
                    <div class="widget-title">
                      <h5>访问次数统计图</h5>
                    </div>
                    <div class="box-body">
                      <div id="shipCallNum" class="chart" style="width:1133px;height:300px;"> </div>
                    </div>
                  </div>
                </div>
                
                <div class="tab-pane" id="shipCall-view3">     
                  <div class="widget-box" style="margin-top:10px">
                    <div class="widget-title">
                      <h5>访问人数统计图</h5>
                    </div>
                    <div class="box-body">
                      <div id="shipCallUser" class="chart" style="width:1133px;height:300px;"> </div>
                    </div>
                  </div>
                </div>
                
                <div class="tab-pane" id="shipCall-view4">     
                  <div class="widget-box" style="margin-top:10px">
                    <div class="widget-title">
                      <h5>访问船舶统计图</h5>
                    </div>
                    <div class="box-body">
                      <div id="shipCallShip" class="chart" style="width:1133px;height:300px;"> </div>
                    </div>
                  </div>
                </div>
                
              </div>
            </div>
          </div>
        </div>
	    <jsp:include page="./manage-foot.jsp"></jsp:include>
      </div>
    </div>
  
</body>
</html>
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
<script src="${ctx}/js/space/space-ship-monitor-ships.js"></script>
<script type="text/javascript">
  var _rootPath = "${ctx}";
</script>
<style>
#content {
  margin-left: 0px;
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
      <form name="pageform" id="pageform" action="${ctx}/space/monitor/myAllShip" method="post">
        <div class="widget-box">
          <div class="widget-title">
            <h5>船舶信息列表</h5>
            
            <div style="float: left; margin-top: 4px; margin-left: 12px;">
              部门：<select id="deptId" name="deptId" style="width: 140px;">
                  <option value="-1" selected>[全部]</option>
                  <c:forEach var="departmentData" items="${departmentDatas}">
                    <c:if test="${deptId == departmentData.id}">
                      <option value="${departmentData.id}" selected>${departmentData.deptName}</option>
                    </c:if>
                    <c:if test="${deptId != departmentData.id}">
                      <option value="${departmentData.id}">${departmentData.deptName}</option>
                    </c:if>
                  </c:forEach>
              </select>
            </div>
			<div style="float: left; margin-top:5px; margin-left:6px;height: 13px;">
			  <input id="keyWords" name="keyWords" type="text" placeholder="请输入船名或MMSI"
			  			style="width : 140px;" value="${keyWords}"/>
			  <button class="btn btn-primary search" style="margin-bottom : 9px">
                <i class=" icon-search icon-white" title="查询"></i>查询
              </button>
			</div>
			
            <div class="btn-link" style="float: right; margin-top: 13px; margin-right: 12px;">
              <a class="link seeCurrent" href="javascript:void(0)">查看船舶分布图 </a>
            </div>
          </div>
          
          <div class="widget-content nopadding">
            <c:if test="${flag}"> ${msg} </c:if>
            <c:if test="${empty flag || !flag}">
	            <table id="tblShip" class="table table-bordered data-table table-striped">
	              <thead>
	                <tr>
	               	  <th style="width: 12%">船舶图片</th>
	                  <th style="width: 8%">船东</th>
	                  <th style="width: 8%">船名</th>
	                  <th style="width: 8%">MMSI编号</th>
	                  <!-- <th style="width: 8%">船类</th> -->
	                  <th>动态</th>
	                  <th style="width: 12%">查看</th>
	                </tr>
	              </thead>
	              <tbody>
	                <c:forEach var="myShipData" items="${pageData.result}" varStatus="reference">
	                  <tr>
	                    <td>
	                    	<a href="${ctx}/portal/home/shipInfo?shipId=${myShipData.id}" target="_blank">
	                      	<c:choose>
	                          <c:when test="${!empty myShipData.shipLogo}">
	                            <img src="${ctx}/download/imageDownload?url=${myShipData.shipLogo}"
	                      		style="width: 80px; height: 60px;" alt="" class="thumbnail" />
	                          </c:when>
	                          <c:otherwise>
	                            <img src="${ctx}/img/shipImage/${myShipData.shipType}.jpg"
	                             style="width: 80px; height: 60px;" alt="" class="thumbnail" />
	                          </c:otherwise>
	                        </c:choose>
	                      	</a>
	                    </td>
	                    <td>
	                      <c:choose>
	                        <c:when test="${!empty myShipData.master.trueName }">${myShipData.master.trueName}</c:when>
	                        <c:otherwise>${myShipData.master.loginName}</c:otherwise>
	                      </c:choose>
	                    </td>
	                    <td>
	                    	<a href="${ctx}/portal/home/shipInfo?shipId=${myShipData.id}" target="_blank">
	                    	${myShipData.shipName}</a>
	                    </td>
	                    <td>${myShipData.mmsi}</td>
	                    <%-- <td>${myShipData.typeData.typeName}</td> --%>
	                    <td>${myShipData.arvlftDesc}</td>
	                    <td>
	                      <a class="btn btn-success synchronization"
	                        href="${ctx}/space/monitor/myAllShip/currRoutePlay?shipId=${myShipData.id}">
	                        <i class="icon-map-marker icon-white" title="实时监控"></i>
	                      </a>
	                      <a class="btn btn-primary historyRoute" style="margin-left: 3px"
	                        href="${ctx}/space/monitor/myAllShip/historyRoutes?shipId=${myShipData.id}">
	                        <i class=" icon-star icon-white" title="历史航次"></i>
	                      </a>
	                    </td>
	                  </tr>
	                </c:forEach>
	              </tbody>
	            </table>
            </c:if>
          </div>
          <jsp:include page="./pager.jsp"></jsp:include>
        </div>
        </form>
      </div>
    </div>

  </div>
  <!-- /span9 -->

  </div>
  <!-- /row -->
  </div>
  <!-- /span9 -->
  </div>
  <!-- /row -->
  </div>
  <!-- /container -->
  </div>
  <!-- /content -->

  <jsp:include page="./space-foot.jsp"></jsp:include>
</html>
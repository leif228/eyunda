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
<script type="text/javascript" src="${ctx}/js/manage/manage-stat-order.js"></script>
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
      </a> <a href="#" style="font-size: 12px;" class="current">合同统计</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid" style="margin-top:0px">
        <div class="span12">

          <div class="widget-content">
              <div class="box-header corner-top">
                <ul id="myTab" style="margin-bottom:0px" class="nav nav-pills">
                  <li><a href="#statOrder1" data-toggle="tab" class="bold">统计列表</a></li>
                  <li id="viewStatOrderCount"><a href="#statOrder2" data-toggle="tab" class="bold">合同总数统计图</a></li>
                  <li id="viewStatOrderTransFee"><a href="#statOrder3" data-toggle="tab" class="bold">合同金额统计图</a></li>
                  <li id="viewStatOrderBrokerFee"><a href="#statOrder4" data-toggle="tab" class="bold">代理人佣金统计图</a></li>
                  <li id="viewStatOrderPlatFee"><a href="#statOrder5" data-toggle="tab" class="bold">平台服务费统计图</a></li>
                </ul>
              </div>
              
              <div class="tab-content">
                <div class="tab-pane active" id="statOrder1">
                  <div class="widget-box" style="margin-top:10px">
                    <div class="widget-title">
                      <h5>合同账务统计列表</h5>
                    </div>
                    <div class="widget-content nopadding">
                      <table class="table table-bordered data-table">
                        <thead>
                          <tr>
                            <th>统计月份</th>
                            <th>合同总数(份)</th>
                            <th>合同金额(元)</th>
                            <th>代理人佣金(元)</th>
                            <th>平台服务费(元)</th>
                          </tr>
                        </thead>
                        
                        <tbody>
                          <c:forEach items="${statOrderDatas}" var="statOrderData">
	                        <tr>
	                          <td>${statOrderData.yearMonth}</td>
	                          <td>${statOrderData.sumOrderCount}</td>
	                          <td>${statOrderData.sumTransFee}</td>
	                          <td>${statOrderData.sumBrokerFee}</td>
	                          <td>${statOrderData.sumPlatFee}</td>
	                        </tr>
	                      </c:forEach>
                        </tbody>
                      </table>
                    </div>
                  </div>
                </div>

                <div class="tab-pane" id="statOrder2">     
                  <div class="widget-box" style="margin-top:10px">
                    <div class="widget-title">
                      <h5>合同总数统计图</h5>
                    </div>
                    <div class="box-body">
                      <div id="statOrderCount" class="chart" style="width:1120px;height:300px;"> </div>
                    </div>
                  </div>
                </div>
                
                <div class="tab-pane" id="statOrder3">     
                  <div class="widget-box" style="margin-top:10px">
                    <div class="widget-title">
                      <h5>合同金额统计图</h5>
                    </div>
                    <div class="box-body">
                      <div id="statOrderTransFee" class="chart" style="width:1120px;height:300px;"> </div>
                    </div>
                  </div>
                </div>
                
                <div class="tab-pane" id="statOrder4">     
                  <div class="widget-box" style="margin-top:10px">
                    <div class="widget-title">
                      <h5>代理人佣金统计图</h5>
                    </div>
                    <div class="box-body">
                      <div id="statOrderBrokerFee" class="chart" style="width:1120px;height:300px;"> </div>
                    </div>
                  </div>
                </div>
                
                <div class="tab-pane" id="statOrder5">     
                  <div class="widget-box" style="margin-top:10px">
                    <div class="widget-title">
                      <h5>平台服务费统计图</h5>
                    </div>
                    <div class="box-body">
                      <div id="statOrderPlatFee" class="chart" style="width:1120px;height:300px;"> </div>
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
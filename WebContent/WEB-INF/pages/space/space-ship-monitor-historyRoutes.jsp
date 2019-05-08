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
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css" class="skin-color" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-datetimepicker.min.css" media="screen"/>

<script src="${ctx}/js/jquery-v.min.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/bootstrap-colorpicker.js"></script>
<script src="${ctx}/js/bootstrap-datepicker.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/space/space-ship-monitor-historyRoutes.js"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script type="text/javascript">
  var _rootPath = "${ctx}";
  var _pageNo = "${pageData.pageNo}";
</script>
<style>
#content {
  margin-left: 0px;
}

.search {
  cursor: pointer;
  margin-left: -1px;
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
        <form name="pageform" id="pageform"
          action="${ctx}/space/monitor/myAllShip/historyRoutes?shipId=${shipData.id}"
          method="post">
          <div class="widget-box">
            <div class="widget-title">
              <h5>历史航次列表</h5>
              
              <div class="control-group styleCenter" style="float:left;width:280px;margin-left:10px;">
                <label class="control-label" style="float:left;width:80px;">
                  <span style="line-height:26px;">起始日期：</span>
                </label>
                <div style="margin-left:10px;" class="controls input-append date form_datetimeStart" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input1">
            	  <input size="16" type="text" name="startTime" id="startTime"  style="width:120px;margin-bottom:0px;" value="${startTime}" placeholder="请输入起始查询时间!"/>            	
                  <span class="add-on"><i class="icon-trash removeStartTime"></i></span>
				  <span class="add-on"><i class="icon-th"></i></span>
                </div>
				<input type="hidden" id="dtp_input1" value="" />
	          </div>
	          
	          <div class="control-group styleCenter" style="float:left;width:280px;margin-left:10px;">
                <label class="control-label" style="float:left;width:80px;">
      			  <span style="line-height:26px;">终止日期：</span>         
                </label>
                <div style="margin-left:10px;" class="controls input-append date form_datetimeEnd" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2">
            	  <input size="16" type="text" name="endTime" id="endTime"  style="width:120px;margin-bottom:0px;" value="${endTime}" placeholder="请输入终止查询时间!"/>            	
                  <span class="add-on"><i class="icon-trash removeEndTime"></i></span>
				  <span class="add-on"><i class="icon-th"></i></span>
                </div>
				<input type="hidden" id="dtp_input2" value="" />
	          </div>
	          
	          <input name="shipId" id="shipId" value="${shipData.id}" type="hidden"/>
	          <a class="btn btn-info findCargo styleCenter search">
	     	    <i class=" icon-search icon-white" title="查询"></i>查询
	     	  </a>

              <div class="btn-link" style="float: right; margin-top: 13px; margin-right: 12px;">
              	<a style="margin-right : 8px;" class="link" id="exportRoutes">导出记录</a>
                <a class="link" href="${ctx}/space/monitor/myAllShip">返回我的船舶列表</a>
              </div>
            </div>

            <div class="widget-content nopadding">
              <table id="tblShip"
                class="table table-bordered data-table table-striped">
                <thead>
                  <tr>
                    <th width="8%">承运人</th>
                    <th width="8%">船名</th>
                    <th width="16%">时间</th>
                    <th width="18%">装货港 - 卸货港</th>
                    <th width="10%">航行时长</th>
                    <th width="10%">航行航程</th>
                    <th width="10%">港作时长</th>
                    <th width="10%">港作航程</th>
                    <th width="10%">操作</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach var="shipArvlftData" items="${pageData.result}">
                    <tr>
                      <c:choose>
                        <c:when test="${!empty shipData.broker.trueName}">
                          <td>${shipData.broker.trueName}</td>
                        </c:when>
                        <c:otherwise>
                          <td>${shipData.broker.loginName}</td>
                        </c:otherwise>
                      </c:choose>
                      <td><a
                        href="${ctx}/portal/home/shipInfo?shipId=${shipData.id}"
                        target="_blank"> ${shipData.shipName}</a></td>
                      <td>${shipArvlftData.startTime} - ${shipArvlftData.endTime}</td>
                      <td>${shipArvlftData.goPortData.fullName} - ${shipArvlftData.portData.fullName}</td>

                      <td>${shipArvlftData.hours}小时${shipArvlftData.minutes}分</td>
                      <td>${shipArvlftData.distance}公里</td>
                      <td>${shipArvlftData.jobHours}小时${shipArvlftData.jobMinutes}分</td>
                      <td>${shipArvlftData.jobDistance}公里</td>

                      <td><a class="btn btn-primary historyRoute"
                        style="margin-left: 3px"
                        href="${ctx}/space/monitor/myAllShip/historyRoutePlay?shipId=${shipData.id}&shipArvlftId=${shipArvlftData.id}">
                          <i class="icon-map-marker icon-white" title="航次回放"></i>
                      </a></td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
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
  <jsp:include page="./space-foot.jsp"></jsp:include>
  <!-- /content -->
</body>

</html>
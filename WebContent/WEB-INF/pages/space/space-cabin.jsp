<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<link rel="stylesheet" href="${ctx}/css/uniform.css" />
<link rel="stylesheet" href="${ctx}/css/select2.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.main.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css" class="skin-color" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-datetimepicker.min.css" media="screen" />

<script src="${ctx}/js/jquery-v.min.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/bootstrap-colorpicker.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/jquery.form-2.63.js"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.zh-CN.js"
  charset="UTF-8"></script>
<script src="${ctx}/js/space/space-cabin.js"></script>
<script type="text/javascript">
  var _rootPath = "${ctx}";

  $(document).ready(function() {
    var date = new Date();
    $("#datetimeStart").datetimepicker({
      format : 'yyyy-mm-dd',
      minView : 'month',
      language : 'zh-CN',
      autoclose : true
    }).on("changeDate", function(ev) {
      var startTime = $("#startDate").val();
      $("#datetimeEnd").datetimepicker("setStartDate", startTime);
    });
    $("#datetimeEnd").datetimepicker({
      format : 'yyyy-mm-dd',
      minView : 'month',
      language : 'zh-CN',
      autoclose : true,
      startDate : date
    }).on("changeDate", function(ev) {
      var endTime = $("#endDate").val();
      $("#datetimeStart").datetimepicker("setEndDate", endTime);
    });

    $('#removeStartTime').click(function() {
      $('#startDate').val("");
      $('#datetimeEnd').datetimepicker('setStartDate');
    });

    $('#removeEndTime').click(function() {
      $('#endDate').val("");
      $('#datetimeStart').datetimepicker('setEndDate');
    });

    $("#datetimeEnd").on("change", function(e) {
      var start = $('#startDate').val();
      var end = $('#endDate').val();
      if (start > end) {
        $('#endDate').val(start);
        $('#datetimeEnd').datetimepicker('update');
        return false;
      }
    });
  });
</script>

<style>
#content {
  margin: 0px;
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

.help-inline {
  color: #B94A48;
}
</style>
</head>

<body>

  <jsp:include page="./space-head.jsp"></jsp:include>

  <div class="span10">

    <h1 class="page-title">
      <i class="icon-star icon-white"></i> 船盘发布
    </h1>
    <div class="row">

      <div class="span10">
        <form novalidate="novalidate" method="get" id="pageform"
          action="${ctx}/space/cabin/myCabin">
          <div class="widget-box">
            <div class="widget-title">
              <h5>我的船盘</h5>
              <%-- <div style="float: left; margin-top: 6px; margin-left: 12px;">
                部门： <select id="deptId" name="deptId" style="width: 140px;">
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
              </div> --%>
              <div style="float: left; margin-left: 0px; margin-top: 2px;">
              <input type="hidden" name="pageNo" value="${pageData.pageNo}" />
              关键字<input id="keyWords" name="keyWords" type="text"
                placeholder="船名或mmsi" style="width: 80px;"
                value="${keyWords}" /> 从 <span class="input-append date"
                id="datetimeStart" data-date="" data-date-format="yyyy-mm-dd">
                <input id="startDate" name="startDate" class="grd-white"
                style="width: 80px; border: 1px solid #cccccc; margin-top: 3px;"
                size="10" type="text" value="${startDate }" placeholder="起始日期" />
                <span class="add-on" id="removeStartTime"
                style="margin-bottom: 9px; margin-top: 3px;"> <i
                  class="icon-remove"></i>
              </span> <span class="add-on"
                style="margin-bottom: 9px; margin-top: 3px; margin-right: 3px;">
                  <i class="icon-th"></i>
              </span>
              </span> 到 <span class="input-append date" id="datetimeEnd" data-date=""
                data-date-format="yyyy-mm-dd"> <input id="endDate"
                name="endDate" class="grd-white"
                style="width: 80px; border: 1px solid #cccccc; margin-top: 3px;"
                size="10" type="text" value="${endDate }" placeholder="终止日期" />
                <span class="add-on" id="removeEndTime"
                style="margin-bottom: 9px; margin-top: 3px;"> <i
                  class="icon-remove"></i>
              </span> <span class="add-on"
                style="margin-bottom: 9px; margin-top: 3px; margin-right: 15px;">
                  <i class="icon-th"></i>
              </span>
              </span>

              <button class="btn btn-primary search" style="margin-top: -9px;">
                <i class=" icon-search icon-white"></i>查询
              </button>
              </div>
              <div style="float: right; margin-left: 10px; margin-top: 4px;">
                添加： <select id="orderType" name="orderType"
                  style="width: 200px;">
                  <option value="">请选择</option>
                  <c:forEach items="${orderTypes}" var="orderType">
                    <option value="${orderType}">${orderType.description}</option>
                  </c:forEach>
                </select>
              </div>
            </div>
            <div class="widget-content nopadding">
              <c:if test="${flag}"> ${msg} </c:if>
              <c:if test="${empty flag || !flag}">
                <table class="table table-bordered data-table table-striped">
                  <thead>
                    <tr>
                      <th style="width: 15%;">船舶</th>
                      <th style="width: 64%;">报价</th>
                      <th style="width: 7%;">状态</th>
                      <th style="width: 14%;">操作</th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:forEach items="${pageData.result}" var="cabinData">
                      <tr>
                        <td>
                          <div><a href="${ctx}/portal/home/cabinInfo?cabinId=${cabinData.id}"
                          target="_blank"> <c:choose>
                              <c:when test="${!empty cabinData.shipData.shipLogo}">
                                <img
                                  src="${ctx}/download/imageDownload?url=${cabinData.shipData.shipLogo}"
                                  style="width: 80px; height: 60px;" alt=""
                                  class="thumbnail" />
                              </c:when>
                              <c:otherwise>
                                <img
                                  src="${ctx}/img/shipImage/${cabinData.shipData.shipType}.jpg"
                                  style="width: 80px; height: 60px;" alt=""
                                  class="thumbnail" />
                              </c:otherwise>
                            </c:choose>
                          </a></div>
                          <div>
                          ${cabinData.shipData.shipName}<br />
                          ${cabinData.description}<br />
                          <%-- <c:if test="${cabinData.waresBigType eq 'voyage' }">
                          承运人:${cabinData.shipData.master.trueName}<br />
                          </c:if>
                          <c:if test="${cabinData.waresBigType eq 'daily' }">
                          出租人:${cabinData.shipData.master.trueName}<br />
                          </c:if> --%>
                          </div>
                        </td>
                        <td>
                        <section class="content-current">
                      <div class="mediabox">
                      <div class="one-line">
			                <div class="row-fluid">
			                    <c:if test="${cabinData.waresBigType eq 'voyage' }"> <!-- 航租 -->
								<div class="one-line-info span12 fa-pull-right">
				                    <div class="one-line-title row-fluid">
				                        <div class="span10 adj-height-40" style="line-height:30px;">
				                        <a href="${ctx }/portal/home/cabinInfo?cabinId=${cabinData.id}&selPortNos=${cabinData.currSailLineData.sailLineNo}">
				                         <span style="color:#1c9b9a;font-weight:bold">${cabinData.currSailLineData.startPortData.fullName}</span><img width="61" height="14" class="startimg" src="${ctx}/img/indImg.png"><span style="color:#1c9b9a;font-weight:bold">${cabinData.currSailLineData.endPortData.fullName},里程:${cabinData.currSailLineData.distance}公里,载重量:${cabinData.currSailLineData.weight}吨</span>
				                        </a>
				                        </div>
				                    </div>
									<div class="row-fluid" style="line-height:40px">
										<span class="span4">
										<c:if test="${cabinData.waresType != 'nmszh'}">
				                        滞期费:${cabinData.demurrage}元/船.天
			                            </c:if>
			                            <c:if test="${cabinData.waresType == 'nmszh'}">
				                        滞期费:${cabinData.demurrage}元/吨.天
				                        </c:if>
										</span>
										<span class="span4" style="text-align:center">
										<c:if test="${cabinData.waresType eq 'nmszh' }"> 
										载货量：${cabinData.currSailLineData.weight}吨
										</c:if>
										<c:if test="${cabinData.waresType eq 'gaxjzx' }"> 
										载箱量：${cabinData.containerCount}TEU
										</c:if>
										<c:if test="${cabinData.waresType eq 'nmjzx' }"> 
										载箱量：${cabinData.containerCount}TEU
										</c:if>
										</span>
										<span class="span4" style="text-align:right">受载日期：${cabinData.startDate}</span>
									</div>
									<div class="row-fluid">
			                          <c:choose>
			                            <c:when test="${cabinData.waresType eq 'nmszh' }">
			                              <span class="span12" style="color:red;font-weight:bold">航租报价：
												<c:forEach varStatus="status" var="mapPrice" items="${cabinData.currSailLineData.mapPrices}">
												${mapPrice.value}元／吨
												</c:forEach>	
										  </span>
			                            </c:when>
			                            <c:otherwise>
			                             <table style="width:100%;color:red;border:1px solid #dddddd">
											<tr>
												<td rowspan="2" style="font-weight:bold">报价<br/>元/个</td>
												<c:forEach varStatus="status" var="containerCode" items="${containerCodes}">
												<td>${containerCode.shortName}</td>
												</c:forEach>	
											</tr>
											<tr>
												<c:forEach varStatus="status" var="mapPrice" items="${cabinData.currSailLineData.mapPrices}">
												<c:if test="${mapPrice.value > 0}">
												<fmt:formatNumber var="mapValue" value="${mapPrice.value}" pattern="#.##"/>
												<td>${mapValue}</td>
												</c:if>
												<c:if test="${mapPrice.value <= 0}">
												<td>- -</td>
												</c:if>
												</c:forEach>	
											</tr>
										</table>
			                            </c:otherwise>
			                          </c:choose>
									</div> 
			                    </div>
			                    </c:if>

			                    <c:if test="${cabinData.waresBigType eq 'daily' }"><!-- 期租 -->
								<div class="one-line-info span12 fa-pull-right">
				                    <div class="one-line-title row-fluid">
				                        <div class="span10 adj-height-40" style="line-height:30px;">
				                        <a href="${ctx }/portal/home/cabinInfo?cabinId=${cabinData.id}&selPortNos=">
				                        	<span class="span12" style="color:#1c9b9a;font-weight:bold;line-height:40px">
				                        	<c:set var="_count" value="0" />
											<c:forEach items="${cabinData.upDownPortDatas}" var="upDownPortData" varStatus="status">
					                            <c:if test="${upDownPortData.gotoThisPort && _count < 2}">
					                            <c:set var="_count" value="${_count+1}" />
					                            ${upDownPortData.startPortData.fullName} : 载重量${upDownPortData.weight}吨,
					                            </c:if>
					                        </c:forEach>
					                        ......
											</span>
				                        </a>
				                        </div>
				                    </div>
				                   
									<div class="row-fluid" style="line-height:40px">
									  <c:choose>
			                            <c:when test="${cabinData.waresType eq 'nmszh' }">
										<span class="span6">燃油费：${cabinData.oilPrice}元/公里</span>
										<span class="span6" style="text-align:right">受载日期：${cabinData.startDate}</span>
			                            </c:when>
			                            <c:otherwise>
			                            <span class="span4">载箱量:${cabinData.containerCount}TEU</span>
										<span class="span4" style="text-align:center">燃油费：${cabinData.oilPrice}元/公里</span>
										<span class="span4" style="text-align:right">受载日期：${cabinData.startDate}</span>
			                            </c:otherwise>
			                          </c:choose>
									 </div>
									 <div class="row-fluid" > <span style="color:red;font-weight:bold">日租报价:${cabinData.prices}元/天</span></div>
			                    </div>
			                    </c:if>
			                </div>
			              </div>
			              <div class="clear"></div>
                      </div>
                  </section>
                        </td>
                        <%-- <td><c:if test="${cabinData.waresBigType == 'voyage'}">
                            <p>
                              <c:if test="${cabinData.waresType != 'nmszh'}">
                          载箱量:${cabinData.containerCount}TEU&nbsp;&nbsp;
                          滞期费:${cabinData.demurrage}元/船.天&nbsp;&nbsp;
                            </c:if>
                              <c:if test="${cabinData.waresType == 'nmszh'}">
                          滞期费:${cabinData.demurrage}元/吨.天&nbsp;&nbsp;
                          </c:if>
                              受载日期:${cabinData.startDate}
                            </p>
                            <p>
                              <c:forEach items="${cabinData.sailLineDatas}"
                                var="sailLineData">
                                <c:if test="${sailLineData.gotoThisLine}">
                            ${sailLineData.description}<br />
                                </c:if>
                              </c:forEach>
                            </p>
                          </c:if> <c:if test="${cabinData.waresBigType == 'daily'}">
                            <p>
                              <c:if test="${cabinData.waresType != 'nmszh'}">
                          载箱量:${cabinData.containerCount}TEU&nbsp;&nbsp;
                          </c:if>
                              租金率:${cabinData.prices}元/天&nbsp;&nbsp;
                              燃油费:${cabinData.oilPrice}元/公里&nbsp;&nbsp;
                              受载日期:${cabinData.startDate}
                            </p>
                            <p>
                              <c:forEach items="${cabinData.upDownPortDatas}"
                                var="upDownPortData">
                                <c:if test="${upDownPortData.gotoThisPort}">
                            ${upDownPortData.startPortData.fullName} : 载重量${upDownPortData.weight}吨<br />
                                </c:if>
                              </c:forEach>
                            </p>
                          </c:if></td> --%>
                        <td id="status${cabinData.id}">${cabinData.status.description}</td>
                        <td id="btnShow${cabinData.id}"><c:if
                            test="${cabinData.status == 'unpublish' && cabinData.publisherId == userData.id }">
                            <a class="btn btn-warning edit" idVal="${cabinData.id}">
                              <i class="icon icon-pencil icon-white" title="修改"></i>
                            </a>
                            <a class="btn btn-danger delete" idVal="${cabinData.id}">
                              <i class="icon icon-trash icon-white" title="删除"></i>
                            </a>
                            <a class="btn btn-success publish" idVal="${cabinData.id}">
                              <i class="icon icon-chevron-up icon-white" title="发布"></i>
                            </a>
                          </c:if> <c:if
                            test="${cabinData.status == 'publish' && cabinData.publisherId == userData.id }">
                            <a class="btn btn-warning unpublish"
                              idVal="${cabinData.id}"> <i
                              class="icon icon-chevron-down icon-white" title="取消发布"></i>
                            </a>
                          </c:if></td>
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
      <!-- /span9 -->

    </div>
    <!-- /row -->

  </div>
  <!-- /row -->

  </div>
  <!-- /row -->

  </div>
  <!-- /container -->

  </div>
  <!-- /content -->

  <jsp:include page="./space-foot.jsp"></jsp:include>

  <!-- Modal publishDialog -->
  <div id="publishDialog" class="modal hide fade">
    <form class="form-horizontal" name="publishForm" id="publishForm"
      method="post" action="#">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>发布</h3>
      </div>
      <input type="hidden" name="id" id="pubId" value="" />
      <div class="modal-body">你确认要发布该船盘信息吗？</div>
      <div class="modal-footer">
        <a class="btn" data-dismiss="modal"> <i class="icon icon-off"></i>
          取消
        </a> <a class="btn btn-primary btnpub"> <i
          class="icon icon-ok icon-white"></i> 确认
        </a>
      </div>
    </form>
  </div>

  <!-- Modal unpublishDialog -->
  <div id="unpublishDialog" class="modal hide fade">
    <form class="form-horizontal" name="unpublishForm" id="unpublishForm"
      method="post" action="#">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>取消发布</h3>
      </div>
      <input type="hidden" name="id" id="unpubId" value="" />
      <div class="modal-body">你确认要取消发布该船盘信息吗？</div>
      <div class="modal-footer">
        <a class="btn" data-dismiss="modal"> <i class="icon icon-off"></i>
          取消
        </a> <a class="btn btn-primary btnunpub"> <i
          class="icon icon-ok icon-white"></i> 确认
        </a>
      </div>
    </form>
  </div>

  <!-- Modal deleteDialog -->
  <div id="deleteDialog" class="modal hide fade">
    <form class="form-horizontal" name="deleteForm" id="deleteForm"
      method="post" action="#">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>删除</h3>
      </div>
      <input type="hidden" name="_method" value="delete" /> <input
        type="hidden" name="cabinId" id="delId" value="" />
      <div class="modal-body">你确认要删除吗？</div>
      <div class="modal-footer">
        <a class="btn" data-dismiss="modal"> <i class="icon icon-off"></i>
          取消
        </a> <a class="btn btn-primary btndel"> <i
          class="icon icon-ok icon-white"></i> 确认
        </a>
      </div>
    </form>
  </div>

</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx"
  value="${pageContext.request.contextPath}" />
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
<link rel="stylesheet"
  href="${ctx}/css/bootstrap-datetimepicker.min.css" media="screen" />

<script src="${ctx}/js/jquery-v.min.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/bootstrap-colorpicker.js"></script>
<script src="${ctx}/js/bootstrap-datepicker.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/jquery.form-2.63.js"></script>
<script src="${ctx}/js/space/space-cargo.js"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.zh-CN.js"
  charset="UTF-8"></script>
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

.user-info li {
  float: left;
  margin-left: 20px;
}

.account-avatar, .account-details {
  display: table-cell;
  vertical-align: top;
}

.account-avatar {
  padding-right: 1em;
}

.account-avatar img {
  width: 55px;
  height: 55px;
}

.account-details {
  
}

.account-details span {
  display: block;
}

.account-details .account-name {
  font-size: 15px;
  font-weight: 600;
}

.account-details .account-role {
  color: #888;
}

.account-details .account-actions {
  color: #BBB;
}

.account-details a {
  font-size: 11px;
}

a {
  color: inherit;
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
      <i class="icon-star icon-white"></i> 货物信息管理
    </h1>
    <div class="row">

      <div class="span10">
        <form novalidate="novalidate" id="pageform" name="pageform"
          method="get" action="${ctx}/space/cargo/cargoList">
          <div class="widget-box">
            <div class="widget-title">
              <h5>我的货盘</h5>
              <div style="float: left; margin-left: 0px; margin-top: 2px;">
              <input type="hidden" name="pageNo" value="${pageData.pageNo}" />
              关键字<input name="keyWords" id="keyWords" type="text"
                class="grd-white" value="${keyWords}"
                style="width: 80px" placeholder="货号或货名" />
              从 <span class="input-append date" id="datetimeStart" data-date=""
                data-date-format="yyyy-mm-dd"> <input id="startDate"
                name="startDate" class="grd-white"
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
                添加：<select id="cargoType" name="cargoType"
                  style="width: 160px;">
                  <option value="">请选择</option>
                  <c:forEach var="cargoType" items="${cargoTypes}">
                    <c:if test="${cargoType=='container20e'}">
                      <option value="${cargoType}">${cargoType.cargoBigType.description}</option>
                    </c:if>
                    <c:if test="${cargoType!='container20e'}">
                      <option value="${cargoType}">${cargoType.shortName}</option>
                    </c:if>
                  </c:forEach>
                </select>
              </div>
            </div>
            <div class="widget-content nopadding">
              <table class="table table-bordered data-table table-striped">
                <thead>
                  <tr>
                    <th style="text-align: center; width: 8%">货物图片</th>
                    <th style="text-align: center; width: 16%">装货港 - 卸货港</th>
                    <th style="text-align: center; width: 8%">货主</th>
                    <th style="text-align: center; width: 10%">货名</th>
                    <th style="text-align: center; width: 8%">货量</th>
                    <th style="text-align: center; width: 12%">报价</th>
                    <th style="text-align: center; width: 12%">运费</th>
                    <th style="text-align: center; width: 12%">发布时间</th>
                    <th style="text-align: center; width: 8%">共享</th>
                    <th style="text-align: center; width: 6%">操作</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${pageData.result}" var="cargo">
                    <tr>
                      <td><a
                        href="${ctx}/portal/home/cargoInfo?cargoId=${cargo.id}"
                        target="_blank"> <c:choose>
                            <c:when test="${!empty cargo.cargoImage}">
                              <img
                                src="${ctx}/download/imageDownload?url=${cargo.cargoImage}"
                                style="width: 80px; height: 60px;" alt=""
                                class="thumbnail" />
                            </c:when>
                            <c:otherwise>
                              <img src="${ctx}/img/cargoImage/${cargo.cargoType}.jpg"
                                style="width: 80px; height: 60px;" alt=""
                                class="thumbnail" />
                            </c:otherwise>
                          </c:choose>
                      </a></td>
                      <td>${cargo.startPortData.fullName}-
                        ${cargo.endPortData.fullName}</td>
                      <td>${cargo.publisher.trueName}</td>
                      <td><c:if test="${cargo.cargoType=='container20e'}">
                          <c:forEach items="${cargo.mapCargoNames}" var="cargoName">
                            <fmt:formatNumber var="tonTeu"
                              value="${cargo.mapTonTeus[cargoName.key]}" pattern="#" />
                            <c:if test="${tonTeu > 0}">${cargoName.value}<br />
                            </c:if>
                          </c:forEach>
                        </c:if> <c:if test="${cargo.cargoType!='container20e'}">${cargo.cargoNames}</c:if>
                      </td>
                      <td style="text-align: right"><c:if
                          test="${cargo.cargoType=='container20e'}">
                          <c:forEach items="${cargo.mapCargoNames}" var="cargoName">
                            <fmt:formatNumber var="tonTeu"
                              value="${cargo.mapTonTeus[cargoName.key]}" pattern="#" />
                            <c:if test="${tonTeu > 0}">${tonTeu}个<br />
                            </c:if>
                          </c:forEach>
                        </c:if> <c:if test="${cargo.cargoType!='container20e'}">${cargo.tonTeus}吨</c:if>
                      </td>
                      <td style="text-align: right"><c:if
                          test="${cargo.cargoType=='container20e'}">
                          <c:forEach items="${cargo.mapCargoNames}" var="cargoName">
                            <fmt:formatNumber var="price"
                              value="${cargo.mapPrices[cargoName.key]}" pattern="#.##" />
                            <fmt:formatNumber var="tonTeu"
                              value="${cargo.mapTonTeus[cargoName.key]}" pattern="#" />
                            <c:if test="${tonTeu > 0}">${price}元/个<br />
                            </c:if>
                          </c:forEach>
                        </c:if> <c:if test="${cargo.cargoType!='container20e'}">${cargo.prices}元/吨</c:if>
                      </td>
                      <td style="text-align: right"><fmt:formatNumber
                          var="fmtTransFee" value="${cargo.transFee}" pattern="#.##" />
                        ${fmtTransFee}元<br /></td>
                      <td style="text-align: center">${cargo.createTime}</td>
                      <td id="status${cargo.id}" style="text-align: center">${cargo.status.description}</td>
                      <td id="btnShow${cargo.id}"><c:if
                          test="${cargo.status == 'unpublish' && cargo.publisherId == userData.id }">
                          <a class="btn btn-warning edit" idVal="${cargo.id}"> <i
                            class="icon icon-pencil icon-white" title="修改"></i>
                          </a>
                          <a class="btn btn-danger delete" idVal="${cargo.id}"> <i
                            class="icon icon-trash icon-white" title="删除"></i>
                          </a>
                          <a class="btn btn-success publish" idVal="${cargo.id}">
                            <i class="icon icon-chevron-up icon-white" title="发布"></i>
                          </a>
                        </c:if> <c:if
                          test="${cargo.status == 'publish' && cargo.publisherId == userData.id }">
                          <a class="btn btn-warning unpublish" idVal="${cargo.id}">
                            <i class="icon icon-chevron-down icon-white" title="取消发布"></i>
                          </a>
                        </c:if></td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
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
  <!-- /span9 -->
  </div>
  <!-- /row -->

  </div>
  <!-- /container -->

  </div>
  <!-- /content -->

  <jsp:include page="./space-foot.jsp"></jsp:include>

  <!-- Modal publishDialog -->
  <div id="publishDialog" class="modal hide fade">
    <form class="form-horizontal" name="publishDialogForm"
      id="publishDialogForm" novalidate="novalidate" method="post"
      action="${ctx}/space/cargo/publish">
      <input type="hidden" name="id" id="pubId" value="" />

      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>发布</h3>
      </div>
      <div class="modal-body">
        <p>你确认要发布该货物信息吗？</p>
      </div>
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
    <form class="form-horizontal" name="unpublishDialogForm"
      id="unpublishDialogForm" novalidate="novalidate" method="post"
      action="${ctx}/space/cargo/unpublish">
      <input type="hidden" name="id" id="unpubId" value="" />

      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>取消发布</h3>
      </div>
      <div class="modal-body">
        <p>你确认要取消发布该货物信息吗？</p>
      </div>
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
    <form class="form-horizontal" name="deleteDialogForm"
      id="deleteDialogForm" novalidate="novalidate" method="post"
      action="${ctx}/space/cargo/delete">
      <input type="hidden" name="id" id="delId" value="0" /> <input
        type="hidden" name="pageNo" id="pageNo" value="${pageNo}" />

      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>删除确认</h3>
      </div>
      <div class="modal-body">
        <p>你确认要删除该货物信息吗？</p>
      </div>
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


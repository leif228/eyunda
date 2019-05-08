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
<title>管理控制台</title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link rel="stylesheet" href="${ctx}/css/bootstrap.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-responsive.css" />
<link rel="stylesheet" href="${ctx}/css/uniform.css" />
<link rel="stylesheet" href="${ctx}/css/select2.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.main.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css"
  class="skin-color" />

<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.zh-CN.js"
  charset="UTF-8"></script>
<script src="${ctx}/js/manage/manage-cargo.js"></script>
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
</head>
<body>
  <jsp:include page="./manage-head.jsp"></jsp:include>

  <div id="content">
    <div id="breadcrumb">
      <a href="#" style="font-size: 12px;" class="tip-bottom"> <i
        class="icon-home"></i> 货盘管理
      </a> <a href="#" style="font-size: 12px;" class="current">货盘查询</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span12">
          <form name="pageform" id="pageform"
            action="${ctx}/manage/ship/manageCargo">
            <div class="widget-box">
              <div class="widget-title">
                <h5>货盘列表</h5>
                <div>
                  <a title="search" class="icon"><i class="icofont-search"></i></a>
                  <input type="hidden" name="pageNo" id="pageNo"
                    value="${pageData.pageNo}" /> <input name="keyWords"
                    id="keyWords" type="text" class="grd-white"
                    value="${keyWords}" style="margin-top: 3px; width: 100px"
                    placeholder="货号或货名" /> 从 <span class="input-append date"
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
                  </span> 到 <span class="input-append date" id="datetimeEnd"
                    data-date="" data-date-format="yyyy-mm-dd"> <input
                    id="endDate" name="endDate" class="grd-white"
                    style="width: 80px; border: 1px solid #cccccc; margin-top: 3px;"
                    size="10" type="text" value="${endDate }" placeholder="终止日期" />
                    <span class="add-on" id="removeEndTime"
                    style="margin-bottom: 9px; margin-top: 3px;"> <i
                      class="icon-remove"></i>
                  </span> <span class="add-on"
                    style="margin-bottom: 9px; margin-top: 3px; margin-right: 15px;">
                      <i class="icon-th"></i>
                  </span>
                  </span> 每页 <select id="pageSize" name="pageSize">
                    <option value="10" <c:if test="${pageData.pageSize==10}">selected</c:if> >10</option>
                    <option value="20" <c:if test="${pageData.pageSize==20}">selected</c:if> >20</option>
                    <option value="50" <c:if test="${pageData.pageSize==50}">selected</c:if> >50</option>
                    <option value="100" <c:if test="${pageData.pageSize==100}">selected</c:if> >100</option>
                    <option value="200" <c:if test="${pageData.pageSize==200}">selected</c:if> >200</option>
                    <option value="500" <c:if test="${pageData.pageSize==500}">selected</c:if> >500</option>
                  </select>行
                  <button type="submit" class="btn btn-primary"
                    id="btnSerachShip"
                    style="margin-bottom: 8px; line-heigth: 20px">查询</button>
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
                        <td>${cargo.startPortData.fullName} -
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
                                value="${cargo.mapPrices[cargoName.key]}"
                                pattern="#.##" />
                              <fmt:formatNumber var="tonTeu"
                                value="${cargo.mapTonTeus[cargoName.key]}" pattern="#" />
                              <c:if test="${tonTeu > 0}">${price}元/个<br />
                              </c:if>
                            </c:forEach>
                          </c:if> <c:if test="${cargo.cargoType!='container20e'}">${cargo.prices}元/吨</c:if>
                        </td>
                        <td style="text-align: right"><fmt:formatNumber
                            var="fmtTransFee" value="${cargo.transFee}"
                            pattern="#.##" /> ${fmtTransFee}元<br /></td>
                        <td style="text-align: center">${cargo.createTime}</td>
                        <td id="status${cargo.id}" style="text-align: center">${cargo.status.description}</td>
                        <td id="btnShow${cargo.id}"><a
                          class="btn btn-danger btnDeleteCargo" idVal="${cargo.id}">
                            <i <i class="icon icon-trash icon-white" title="删除"></i>
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

      <jsp:include page="./manage-foot.jsp"></jsp:include>

    </div>
  </div>

  <!-- Modal deleteDialog -->
  <div id="deleteCargoDialog" class="modal hide fade">
    <form class="form-horizontal" name="deleteCargoDialogForm"
      id="deleteCargoDialogForm" novalidate="novalidate" method="post"
      action="${ctx}/manage/ship/manageCargo/deleteCargo">
      <input type="hidden" name="_method" value="delete" /> <input
        type="hidden" name="id" id="delCargoId" value="" /> <input
        type="hidden" name="keyWords" id="searchKeyWords" value="" /> <input
        type="hidden" name="pageNo" id="delPageNo" value="" />
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>删除确认</h3>
      </div>
      <div class="modal-body">
        <p>删除货盘信息将不可恢复！</p>
        <p>你确认要删除该货盘信息吗？</p>
      </div>
      <div class="modal-footer">
        <button class="btn" data-dismiss="modal">
          <i class="icon icon-off"></i> 取消
        </button>
        <button class="btn btn-primary">
          <i class="icon icon-ok icon-white"></i> 确认
        </button>
      </div>
    </form>
  </div>

</body>
</html>

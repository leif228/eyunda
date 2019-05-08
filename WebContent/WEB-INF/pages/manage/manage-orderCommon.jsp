<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css"
  class="skin-color" />

<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/manage/manage-orderCommon.js"></script>
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
        class="icon-home"></i> 合同管理
      </a> <a href="#" style="font-size: 12px;" class="current">合同查询</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span12">
		 <form name="pageform" id="pageform"action="${ctx}/manage/orderCommon/orderCommon" method="post">
          <div class="widget-box">
            <div class="widget-title">
                <h5>合同列表</h5>
                <div>
                  <a title="search" class="icon"><i class="icofont-search"></i></a>
                  <input type="hidden" name="pageNo" id="pageNo"
                    value="${pageData.pageNo}" /> <input name="keyWords"
                    id="keyWords" type="text" class="grd-white"
                    value="${keyWords}"
                    style="margin-top: 3px; width: 100px; margin-right: 15px;"
                    placeholder="请输入合同号" /> 从 <span
                    class="input-append date" id="datetimeStart" data-date=""
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
                    id="btnSerachOrder"
                    style="margin-bottom: 8px; line-heigth: 20px">查询</button>
                </div>
            </div>
            <div class="widget-content nopadding">
              <table class="table table-bordered data-table">
                <thead>
                  <tr>
                    <th style="text-align: center; width: 20%">合同</th>
                    <th style="text-align: center; width: 8%">甲方</th>
                    <th style="text-align: center; width: 8%">乙方</th>
                    <th style="text-align: center; width: 16%">合同金额</th>
                    <th style="text-align: center; width: 12%">状态</th>
                    <th style="text-align: center; width: 20%">支付情况</th>
                    <th style="text-align: center; width: 16%">建立日期</th>
                  </tr>
                </thead>

                <tbody>
                  <c:forEach items="${pageData.result}" var="order">
                    <tr>
                      <td>
                        <a target="_blank" href="${ctx}/manage/orderCommon/showContainer?orderId=${order.id}&orderType=${order.orderType}">
                          ${order.shipData.shipName}-${order.orderType.description}(${order.id})
                        </a>
                      </td>
                      <td>${order.owner.trueName}</td>
                      <td>
                        <c:if test="${empty order.master}">
                          ${order.broker.trueName}
                        </c:if>
                        <c:if test="${!empty order.master}">
                          ${order.master.trueName}
                        </c:if>
                      </td>
                      <td style="text-align: center;">${order.transFee}元(${order.paySteps}期)</td>
                      <td>${order.state.description}</td>
                      <td>
                        <c:if test="${empty order.walletDatas}">未支付</c:if>
                        <c:if test="${!empty order.walletDatas}">
                          <c:set var="w" value="${fn:length(order.walletDatas)}"/>
                          <c:forEach items="${order.walletDatas}" var="walletData" varStatus="status">
                            ${walletData.gmtPayment}支付${walletData.totalFee}${walletData.paymentStatus.description}<br>
                            <c:if test="${status.index<w-1}"></c:if>
                          </c:forEach>
                        </c:if>
                      </td>
                      <td>${order.createTime}</td>
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
  <div id="deleteDialog" class="modal hide fade">
    <form class="form-horizontal" name="deleteDialogForm"
      id="deleteDialogForm" novalidate="novalidate" method="post"
      action="${ctx}/manage/order/order/delete">
    <input type="hidden" name="_method" value="delete" />
    <input type="hidden" name="id" id="deleteId" value="" />
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>删除确认</h3>
    </div>
    <div class="modal-body">
      <p>你确认要删除该合同吗？</p>
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
  
  <script type="text/javascript"> 
	 function clearNoNum(obj)
	 {
	    obj.value = obj.value.replace(/[^\d]/g,"");  //清除“数字”以外的字符
	   	obj.value = obj.value.replace(/^\./g,"");  //验证第一个字符是数字
	    obj.value = obj.value.replace(/\{2,}/g,"."); //只保留第一个. 清除多余的.
	    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	 }
  </script>
  
</body>
</html>

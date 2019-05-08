<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<link rel="stylesheet" href="${ctx}/css/bootstrap-datetimepicker.min.css" media="screen" />

<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/unicorn.tables.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/manage/manage-holiday.js"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script src="${ctx}/js/jquery.form-2.63.js"></script>
<script type="text/javascript">
  var _rootPath = "${ctx}";
  
  $(function() {
	    $('.form_datetimeStart').datetimepicker({
	      //language:  'fr',
	      weekStart : 1,
	      todayBtn : 1,
	      todayHighlight : 1,
	      startView : 2,
	      forceParse : 0,
	      showMeridian : 1,
	      minView : "month", //选择日期后，不会再跳转去选择时分秒 
	      autoclose : true
	    });

	    $('.removeStartTime').click(function() {
	      $('#startTime').val("");
	    });

	    $('.form_datetimeEnd').datetimepicker({
	      //language:  'fr',
	      weekStart : 1,
	      todayBtn : 1,
	      todayHighlight : 1,
	      startView : 2,
	      forceParse : 0,
	      showMeridian : 1,
	      minView : "month", //选择日期后，不会再跳转去选择时分秒 
	      autoclose : true
	    });

	    $('.removeEndTime').click(function() {
	      $('#endTime').val("");
	    });
	  });
</script>
</head>

<body>

  <jsp:include page="./manage-head.jsp"></jsp:include>

  <div id="content">
    <div id="breadcrumb">
      <a href="#" style="font-size: 12px;" class="tip-bottom"> <i
        class="icon-home"></i> 权限管理
      </a> <a href="#" style="font-size: 12px;" class="current">节假日管理</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span12">

          <div class="widget-box">
            <div class="widget-title">
              <h5>节假日列表</h5>
              <button id="btnAddHoliday" class="btn btn-info" data-toggle="modal"
                data-target="#editDialog">
                <i class="icon-plus icon-white"></i> 添加
              </button>
            </div>
            <div class="widget-content nopadding">
              <table class="table table-bordered data-table">
                <thead>
                  <tr>
                    <th>序号</th>
                    <th>节假日名称</th>
                    <th>节假日开始时间</th>
                    <th>节假日结束时间</th>
                    <th>操作</th>
                  </tr>
                </thead>

                <tbody>

				  <c:forEach items="${holidayDatas}" var="holidayData" varStatus="status">
                  <tr class="gradeX">
                    <td>${status.index + 1}</td>
                    <td>${holidayData.holidayName}</td>
                    <td>${holidayData.startDate}</td>
                    <td>${holidayData.endDate}</td>
                    <td>
                      <button id="saveHoliday" class="btn btn-primary btnEdit" data-toggle="modal"
                        data-target="#editDialog" idVal="${holidayData.id}" 
                        holidayName="${holidayData.holidayName}"
                        holidayStartDate="${holidayData.startDate}"
                        holidayDataEndDat="${holidayData.endDate}">
                        <i class="icon-pencil icon-white"></i> 修改
                      </button>
                      <button class="btn btn-danger btnDelete" data-toggle="modal"
                        data-target="#deleteDialog" idVal="${holidayData.id}">
                        <i class="icon-trash icon-white"></i> 删除
                      </button>
                    </td>
                  </tr>
                  </c:forEach>

                </tbody>
              </table>
            </div>
          </div>

        </div>
      </div>

      <jsp:include page="./manage-foot.jsp"></jsp:include>

    </div>
  </div>

  <!-- Modal editDialog -->
  <div id="editDialog" class="modal hide fade">
    <form class="form-horizontal" name="editDialogForm"
      id="editDialogForm" novalidate="novalidate" method="post" action="${ctx}/manage/power/saveHoliday">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>编辑</h3>
    </div>
    <div class="modal-body">
      <fieldset>

        <div class="control-group">
          <label class="control-label">节假日名称：</label>
          <div class="controls">
            <input type="hidden" name="id" id="holidayId" value="" />
            <input type="text" name="holidayName" id="holidayName"  value="" />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label">节假日开始时间：</label>
          <div class="controls">
            <div style="margin-left: 10px;"
              class="controls input-append date form_datetimeStart"
              data-date="" data-date-format="yyyy-mm-dd"
              data-link-field="dtp_input1">
              <input size="16" type="text" name="startDate" id="startTime"
                style="width: 120px; margin-bottom: 0px;" value=""
                placeholder="请输入起始查询时间!"/>
              <span class="add-on">
                <i class="icon-trash removeStartTime"></i>
              </span>
			  <span class="add-on"><i class="icon-th"></i></span>
             </div>
          </div>
        </div>

        <div class="control-group">
          <label class="control-label">节假日结束时间：</label>
          <div class="controls">
            <div style="margin-left: 10px;" class="controls input-append date form_datetimeEnd"
              data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2">
              <input size="16" type="text" name="endDate" id="endTime"
              style="width: 120px; margin-bottom: 0px;" value=""
              placeholder="请输入终止查询时间!"/>
			  <span class="add-on"><i class="icon-trash removeEndTime"></i></span>
			  <span class="add-on"><i class="icon-th"></i></span>
            </div>
          </div>
        </div>

      </fieldset>
    </div>
    <div class="modal-footer">
      <a class="btn" data-dismiss="modal">
        <i class="icon icon-off"></i> 取消
      </a>
      <a class="btn btn-primary" id="saveHolidayBtn">
        <i class="icon icon-ok icon-white"></i> 保存
      </a>
    </div>
    </form>
  </div>

  <!-- Modal deleteDialog -->
  <div id="deleteDialog" class="modal hide fade">
    <form class="form-horizontal" name="deleteDialogForm"
      id="deleteDialogForm" novalidate="novalidate">
    <input type="hidden" name="id" id="delHolidayId" value="" />
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>删除确认</h3>
    </div>
    <div class="modal-body">
      <p>你确认要删除该节假日吗？</p>
    </div>
    <div class="modal-footer">
      <button class="btn" data-dismiss="modal">
        <i class="icon icon-off"></i> 取消
      </button>
      <a class="btn btn-primary" id="delHolidayBtn">
        <i class="icon icon-ok icon-white"></i> 确认
      </a>
    </div>
    </form>
  </div>

</body>
</html>

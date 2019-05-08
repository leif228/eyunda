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
<script src="${ctx}/js/manage/manage-app.js"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script src="${ctx}/js/jquery.form-2.63.js"></script>
<script type="text/javascript">
  var _rootPath = "${ctx}";
</script>
</head>

<body>

  <jsp:include page="./manage-head.jsp"></jsp:include>

  <div id="content">
    <div id="breadcrumb">
      <a href="#" style="font-size: 12px;" class="tip-bottom"> <i
        class="icon-home"></i> ${menuOpen.menuname}
      </a> <a href="#" style="font-size: 12px;" class="current">${menuAct.menuname}</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span12">

          <div class="widget-box">
            <div class="widget-title">
              <h5>${menuAct.menuname}列表</h5>
              <button id="btnAdd" class="btn btn-info" data-toggle="modal"
                data-target="#editDialog">
                <i class="icon-plus icon-white"></i> 添加
              </button>
            </div>
            <div class="widget-content nopadding">
              <table class="table table-bordered data-table">
                <thead>
                  <tr>
                    <th>服务图标</th>
                    <th>服务名称</th>
                    <th>服务介绍</th>
                    <th>入口地址</th>
                    <th>操作</th>
                  </tr>
                </thead>

                <tbody>

				  <c:forEach items="${appDatas}" var="appData" varStatus="status">
                  <tr class="gradeX">
                    <td>
                      <img src="${ctx}/hyquan/download/imageDownload?url=${appData.appIcon}" alt="" class="thumbnail" style="width: 60px; height: 60px;" />
                    </td>
                    <td>${appData.appName}</td>
                    <td>${appData.appDesc}</td>
                    <td>${appData.appUrl}</td>
                    <td>
                      <button class="btn btn-primary btnEdit" data-toggle="modal"
                        data-target="#editDialog" idVal="${appData.id}" 
                        appNameVal="${appData.appName}"
                        appDescVal="${appData.appDesc}"
                        appIconVal="${appData.appIcon}"
                        appUrlVal="${appData.appUrl}">
                        <i class="icon-pencil icon-white"></i> 修改
                      </button>
                      <button class="btn btn-danger btnDelete" data-toggle="modal"
                        data-target="#deleteDialog" idVal="${appData.id}">
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
      id="editDialogForm" novalidate="novalidate" method="post" action="${ctx}/manage/app/saveApp">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>编辑</h3>
    </div>
    <div class="modal-body">
      <fieldset>

        <div class="control-group">
          <label class="control-label">服务名称：</label>
          <div class="controls">
            <input type="hidden" name="id" id="appId" value="" />
            <input type="text" name="appName" id="appName"  value="" />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label">服务描述：</label>
          <div class="controls">
            <input type="text" name="appDesc" id="appDesc"  value="" />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label">服务图标：</label>
          <div class="controls">
            <img id="appIconImg" src="${ctx}/hyquan/download/imageDownload?url="
               alt="" class="thumbnail" style="width: 100px; height: 100px;" />
            <input type="file" id="appIcon" name="appIconFile" value="" />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label">入口地址：</label>
          <div class="controls">
            <input type="text" name="appUrl" id="appUrl"  value="" />
          </div>
        </div>

      </fieldset>
    </div>
    <div class="modal-footer">
      <a class="btn" data-dismiss="modal">
        <i class="icon icon-off"></i> 取消
      </a>
      <a class="btn btn-primary" id="saveAppBtn">
        <i class="icon icon-ok icon-white"></i> 保存
      </a>
    </div>
    </form>
  </div>

  <!-- Modal deleteDialog -->
  <div id="deleteDialog" class="modal hide fade">
    <form class="form-horizontal" name="deleteDialogForm"
      id="deleteDialogForm" novalidate="novalidate">
    <input type="hidden" name="id" id="delAppId" value="" />
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>删除确认</h3>
    </div>
    <div class="modal-body">
      <p>你确认要删除该服务吗？</p>
    </div>
    <div class="modal-footer">
      <button class="btn" data-dismiss="modal">
        <i class="icon icon-off"></i> 取消
      </button>
      <a class="btn btn-primary" id="delAppBtn">
        <i class="icon icon-ok icon-white"></i> 确认
      </a>
    </div>
    </form>
  </div>

</body>
</html>

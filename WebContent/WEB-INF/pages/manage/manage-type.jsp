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
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css"
  class="skin-color" />

<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/unicorn.tables.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/manage/manage-type.js"></script>
<script type="text/javascript">
var _rootPath = "${ctx}";
</script>

</head>

<body>

  <jsp:include page="./manage-head.jsp"></jsp:include>

  <div id="content">
    <div id="breadcrumb">
      <a href="#" style="font-size: 12px;" class="tip-bottom"> <i
        class="icon-home"></i> 船舶管理
      </a> <a href="#" style="font-size: 12px;" class="current">类别管理</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span12">

          <div class="widget-box">
            <div class="widget-title">
              <h5>类别列表</h5>
              <button id="btnAdd" class="btn btn-info">
                <i class="icon-plus icon-white"></i> 添加
              </button>
            </div>
            <div class="widget-content nopadding">
              <table class="table table-bordered data-table">
                <thead>
                  <tr>
                    <th>类别编号</th>
                    <th>类别名称</th>
                    <th>上级编号</th>
                    <th>上级名称</th>
                    <th>操作</th>
                  </tr>
                </thead>

                <tbody>
                <c:forEach var="typeData" items="${typeDatas}">
                  <tr>
                    <td>${typeData.typeCode}</td>
                    <td>${typeData.typeName}</td>
                    <td>${typeData.parent.typeCode}</td>
                    <td>${typeData.parent.typeName}</td>
                    <td>
                      <button class="btn btn-primary btnEdit" idVal="${typeData.typeCode}">
                        <i class="icon-pencil icon-white"></i> 修改
                      </button>
                      <button class="btn btn-danger btnDelete" idVal="${typeData.typeCode}">
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
      id="editDialogForm" novalidate="novalidate" method="post" 
      action="${ctx}/manage/ship/type/save">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>编辑</h3>
    </div>
    <div class="modal-body">
      <fieldset>

        <div id="selContent"></div>

        <div class="control-group">
          <label class="control-label">类别名称：</label>
          <div class="controls">
            <input type="hidden" name="typeCode" id="typeCode" value="" />
            <input type="text" name="typeName" id="typeName" value="集装箱" style="width:264px;" />
          </div>
        </div>

      </fieldset>
    </div>
    <div class="modal-footer">
      <button class="btn" data-dismiss="modal">
        <i class="icon icon-off"></i> 取消
      </button>
      <button class="btn btn-primary">
        <i class="icon icon-ok icon-white"></i> 保存
      </button>
    </div>
    </form>
  </div>

  <!-- Modal deleteDialog -->
  <div id="deleteDialog" class="modal hide fade">
    <form class="form-horizontal" name="deleteDialogForm"
      id="deleteDialogForm" novalidate="novalidate" method="post"
      action="${ctx}/manage/ship/type/delete">
    <input type="hidden" name="_method" value="delete" />
    <input type="hidden" id="delTypeCode" name="typeCode" value="" />
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>删除确认</h3>
    </div>
    <div class="modal-body">
      <p>你确认要删除该类别吗？</p>
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

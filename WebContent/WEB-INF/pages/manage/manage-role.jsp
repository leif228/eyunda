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

<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/unicorn.tables.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/manage/manage-role.js"></script>
<script type="text/javascript">
var _rootPath = "${ctx}";
</script>
</head>

<body>

  <jsp:include page="./manage-head.jsp"></jsp:include>

  <div id="content">
    <div id="breadcrumb">
      <a href="#" style="font-size: 12px;" class="tip-bottom"> <i
        class="icon-home"></i> 权限管理
      </a> <a href="#" style="font-size: 12px;" class="current">角色管理</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span12">

          <div class="widget-box">
            <div class="widget-title">
              <h5>角色列表</h5>
              <button id="btnAdd" class="btn btn-info" data-toggle="modal"
                data-target="#editDialog">
                <i class="icon-plus icon-white"></i> 添加
              </button>
            </div>
            <div class="widget-content nopadding">
              <table class="table table-bordered data-table">
                <thead>
                  <tr>
                    <th>角色名称</th>
                    <th>角色描述</th>
                    <th>角色模块</th>
                    <th>操作</th>
                  </tr>
                </thead>

                <tbody>

                  <c:forEach var="roleData" items="${roleDatas}">
                    <tr class="gradeX">
                      <td>${roleData.roleName}</td>
                      <td>${roleData.roleDesc}</td>
                      <td>
                      <c:if test="${!empty roleData.moduleDatas}">
                        <c:forEach var="moduleData" items="${roleData.moduleDatas}">
                          <c:if test="${moduleData.theModule}">
                          ${moduleData.moduleName}<br />
                          </c:if>
                        </c:forEach>
                      </c:if>
                      <c:if test="${empty roleData.moduleDatas}">
                        &nbsp;
                      </c:if>
                      </td>
                      <td>
                        <button class="btn btn-success btnModule" data-toggle="modal"
                          data-target="#moduleDialog" idVal="${roleData.id}">
                          <i class="icon-book icon-white"></i> 权限
                        </button>
                        <button class="btn btn-primary btnEdit" data-toggle="modal"
                          data-target="#editDialog" idVal="${roleData.id}">
                          <i class="icon-pencil icon-white"></i> 修改
                        </button>
                        <button class="btn btn-danger btnDelete" data-toggle="modal"
                          data-target="#deleteDialog" idVal="${roleData.id}">
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

  <!-- Modal moduleDialog -->
  <div id="moduleDialog" class="modal hide fade">
    <form class="form-horizontal" name="moduleDialogForm"
      id="moduleDialogForm" novalidate="novalidate" method="post"
      action="${ctx}/manage/power/role/saveModule">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>权限</h3>
    </div>
    <div class="modal-body">
      <div class="control-group">
        <label class="control-label">角色：</label>
        <div class="controls">
          <input type="hidden" name="id" id="mRoleId" value="1" />
          <input type="text" name="roleName" id="mRoleName"  value="会员管理员" disabled />
        </div>
      </div>
      <div id="moduleSel">
      </div>
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

  <!-- Modal editDialog -->
  <div id="editDialog" class="modal hide fade">
    <form class="form-horizontal" name="editDialogForm"
      id="editDialogForm" novalidate="novalidate" method="post" action="${ctx}/manage/power/role/save">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>编辑</h3>
    </div>
    <div class="modal-body">
      <fieldset>

        <div class="control-group">
          <label class="control-label">角色名称：</label>
          <div class="controls">
            <input type="hidden" name="id" id="roleId" value="" />
            <input type="text" name="roleName" id="roleName"  value="会员管理员" />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label">角色描述：</label>
          <div class="controls">
            <input type="text" name="roleDesc" id="roleDesc"  value="会员管理员" />
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
      action="${ctx}/manage/power/role/delete">
    <input type="hidden" name="_method" value="delete" />
    <input type="hidden" name="id" id="delRoleId" value="" />
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>删除确认</h3>
    </div>
    <div class="modal-body">
      <p>你确认要删除该角色吗？</p>
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

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
<script src="${ctx}/js/manage/manage-admin.js"></script>
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
      </a> <a href="#" style="font-size: 12px;" class="current">管理员管理</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span12">

          <div class="widget-box">
            <div class="widget-title">
              <h5>管理员列表</h5>
              <button id="btnAdd" class="btn btn-info">
                <i class="icon-plus icon-white"></i> 添加
              </button>
            </div>
            <div class="widget-content nopadding">
              <table class="table table-bordered data-table">
                <thead>
                  <tr>
                    <th>登录名</th>
                    <th>姓名</th>
                    <th>昵称</th>
                    <th>电子邮箱</th>
                    <th>手机</th>
                    <th>管理员角色</th>
                    <th>操作</th>
                  </tr>
                </thead>

                <tbody>

                  <c:forEach var="adminData" items="${adminDatas}">
                    <tr class="gradeX">
                      <td>${adminData.loginName}</td>
                      <td>${adminData.trueName}</td>
                      <td>${adminData.nickName}</td>
                      <td>${adminData.email}</td>
                      <td>${adminData.mobile}</td>
                      <td><c:if test="${!empty adminData.roleDatas}">
                          <c:forEach var="roleData" items="${adminData.roleDatas}">
                          ${roleData.roleName}<br />
                          </c:forEach>
                        </c:if> <c:if test="${empty adminData.roleDatas}">
                        &nbsp;
                      </c:if></td>
                      <td>
                        <button class="btn btn-success btnRole" idVal="${adminData.id}">
                          <i class="icon-user icon-white"></i> 角色
                        </button>
                        <button class="btn btn-primary btnEdit" idVal="${adminData.id}">
                          <i class="icon-pencil icon-white"></i> 修改
                        </button>
                        <button class="btn btn-danger btnDelete" idVal="${adminData.id}">
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

  <!-- Modal roleDialog -->
  <div id="roleDialog" class="modal hide fade">
    <form class="form-horizontal" name="roleDialogForm"
      id="roleDialogForm" novalidate="novalidate" method="post"
      action="${ctx}/manage/power/admin/saveRole">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>角色</h3>
    </div>
    <div class="modal-body">
      <div class="control-group">
        <label class="control-label">管理员：</label>
        <div class="controls">
          <input type="hidden" name="id" id="rAdminId" value="1" /> <input
            type="text" name="adminName" id="rAdminName"
             value="张管理" disabled />
        </div>
      </div>
      <div id="roleSel">
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

  <!-- Modal dlgEdit -->
  <div id="dlgEdit" class="modal hide fade">
    <form class="form-horizontal" name="dlgEditForm" id="dlgEditForm"
      novalidate="novalidate" method="post"
      action="${ctx}/manage/power/admin/save">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>编辑</h3>
    </div>
    <div class="modal-body">
      <fieldset>
		<!-- -->
        <div class="control-group">
          <label class="control-label">登录名：</label>
          <div class="controls">
            <input type="hidden" name="id" id="adminId" value="" /> <input
              type="text" name="loginName" id="loginName"
               value="zhangsf" />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label">真实姓名：</label>
          <div class="controls">
            <input type="text" name="trueName" id="trueName"
               value="张三丰" />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label">昵称：</label>
          <div class="controls">
            <input type="text" name="nickName" id="nickName"
               value="江湖游侠" />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label">电子邮箱：</label>
          <div class="controls">
            <input type="text" name="email" id="email"
               value="zhangsf@eyunda.com" />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label">手机：</label>
          <div class="controls">
            <input type="text" name="mobile" id="mobile"
               value="13956236547" />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label">密码：</label>
          <div class="controls">
            <input type="password" name="password" id="password"
               value="password" />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label">密码确认：</label>
          <div class="controls">
            <input type="password" name="password2" id="password2"
               value="password" />
          </div>
        </div>

        <!-- <div class="control-group">
          <label class="control-label"></label>
          <div class="controls">
            <div class="account-avatar">
              <img src="" alt="" class="thumbnail"
                style="width: 100px; height: 100px;" />
            </div>
          </div>
        </div>

        <div class="control-group">
          <label class="control-label">头像：</label>
          <div class="controls">
            <input type="file" id="userLogo" name="userLogo" style="width:200px;height:30px;" />
          </div>
        </div> -->

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
      action="${ctx}/manage/power/admin/delete">
    <input type="hidden" name="_method" value="delete" /> <input
      type="hidden" id="delAdminId" name="id" value="" />
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>删除确认</h3>
    </div>
    <div class="modal-body">
      <p>你确认要删除该管理员吗？</p>
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

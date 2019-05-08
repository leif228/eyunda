<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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

<script src="${ctx}/js/jquery-v.min.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/bootstrap-colorpicker.js"></script>
<script src="${ctx}/js/bootstrap-datepicker.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/jquery.form-2.63.js"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/bootstrap-autocomplete.js"></script>
<script src="${ctx}/js/space/space-contact.js"></script>
<script type="text/javascript">
  var _rootPath = "${ctx}";
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

.opacity img {
  opacity: 0.3;
}

.opacity img:hover {
  opacity: 0.3;
}
</style>
</head>

<body>
  <jsp:include page="./space-head.jsp"></jsp:include>

  <div class="span10">

    <h1 class="page-title">
      <i class="icon-star icon-white"></i> 联系人
    </h1>
    <div class="row">
      <div class="span10">
        <form novalidate="novalidate" method="get" id="pageform"
          action="${ctx}/space/contact/myContact">
          <div class="widget-box">
            <div class="widget-title">
              <h5>我的联系人列表</h5>
              <div style="float: left; margin-top: 4px;">
                <select name="deptId" id="deptId" style="width: 110px;">
                  <option value="0" selected>[全部]</option>
                  <c:forEach var="departmentData" items="${departmentDatas}">
                    <c:choose>
                      <c:when test="${deptId == departmentData.id}">
                        <option value="${departmentData.id}" selected>
                          ${departmentData.deptName}</option>
                      </c:when>
                      <c:otherwise>
                        <option value="${departmentData.id}">
                          ${departmentData.deptName}</option>
                      </c:otherwise>
                    </c:choose>
                  </c:forEach>
                </select>
                <c:if test="${flag}">
                  <a class="btn btn-primary btnAddDepartment" title="添加部门"> <i
                    class="icon-plus icon-white"></i>
                  </a>
                </c:if>
              </div>

              <div style="float: left; margin-top: 4px; margin-left: 8px;">
                <input name="keyWords" id="keyWords"
                  title="请输入联系人的真实姓名、登录名、昵称、邮箱或手机号" type="text"
                  value="${keyWords}" placeholder="请输入联系人的真实姓名、登录名、昵称、邮箱或手机号" />
                <button style="margin-top: -10px;"
                  class="btn btn-info findContact">查询</button>
              </div>
              <div style="float: right; margin-top: 4px; margin-right: 8px;">
                <c:if test="${flag}">
                  <a class="btn btn-primary btnAddMember" title="添加成员"> <i
                    class="icon-plus icon-white"></i>
                  </a>
                </c:if>
              </div>
            </div>
            <div style="clear:both"></div>
            <div class="widget-content nopadding">
              <c:if test="${flagComp}"> ${msg} </c:if>
              <c:if test="${empty flagComp || !flagComp}">
              <table class="table table-bordered data-table table-striped">
                <thead>
                  <tr>
                    <th>头像</th>
                    <th>登录名</th>
                    <th>姓名</th>
                    <th>手机</th>
                    <th>电子邮箱</th>
                    <c:if test="${flagSailor}">
                    <th>船舶</th>
                    </c:if>
                    <th>操作</th>
                  </tr>
                </thead>

                <tbody>

                  <c:forEach var="contact" items="${pageData.result}">
                    <tr>
                      <td>
                        <div class="account-avatar">
                          <c:if test="${contact.userLogo != \"\"}">
                            <a href="${ctx}/space/chat/show?toUserId=${contact.id}"
                              target="mychat">
                              <div
                                class="<c:if test='${contact.onlineStatus == "ofline"}'>opacity</c:if>">
                                <img
                                  src="${ctx}/download/imageDownload?url=${contact.userLogo}"
                                  alt="" class="thumbnail"
                                  style="width: 65px; height: 65px;" />
                              </div>
                            </a>
                          </c:if>
                          <c:if test="${contact.userLogo == \"\"}">
                            <a href="${ctx}/space/chat/show?toUserId=${contact.id}"
                              target="mychat">
                              <div
                                class="<c:if test='${contact.onlineStatus == "ofline"}'>opacity</c:if>">
                                <img src="${ctx}/img/user.jpg" alt="" class="thumbnail"
                                  style="width: 65px; height: 65px;" />
                              </div>
                            </a>
                          </c:if>
                        </div>
                      </td>
                      <td>${contact.loginName}</td>
                      <td>${contact.trueName}<c:if
                          test="${!empty(contact.ships)}">(${contact.ships})</c:if>
                      </td>
                      <td>${contact.mobile}</td>
                      <td>${contact.email}</td>
                      <c:if test="${flagSailor}">
                      <td>
                          ${contact.applyStatus.remark}
                      </td>
                      </c:if>
                      <td><c:if test="${flag}">
                          <a href="javascript:void(0);"
                            class="btn btn-danger btnDeleteMember"
                            idVal="${contact.id}" title="删除"> <i
                            class="icon-trash icon-white"></i>
                          </a>
                        </c:if> <c:if test="${flagSailor}">
                          <a href="javascript:void(0);"
                            class="btn btn-danger btnSailorReport"
                            user_id="${contact.id}" dept_id="${deptId}" title="设置用户动态上报船舶"> <i
                            class="icon-cog icon-white"></i>
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
  <!-- /content -->

  <jsp:include page="./space-foot.jsp"></jsp:include>

  <!-- 设置船员动态上报船舶对话框 -->
  <div id="sailorReportDialog" class="modal hide fade"
    style="display: none; z-index: 1050 !important;">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal"
        aria-hidden="true">×</button>
      <h3>设置船员动态上报船舶</h3>
    </div>
    <div class="modal-body">
      <form class="form-horizontal" name="sailorReportForm" id="sailorReportForm"
        novalidate="novalidate" method="post" action="">
        <fieldset>
		  <input type="hidden" id="rptDeptId" name="deptId" value="${deptId}">
		  <input type="hidden" id="rptUserId" name="userId" value="0">

          <div class="control-group">
            <label class="control-label">用户：</label>
            <div class="controls">
              <div style="height: 30px;" id="rptTrueName"></div>
            </div>
          </div>

          <div class="control-group">
            <label class="control-label">授权船舶：</label>
            <div class="controls" id="rptMmsi">
            </div>
          </div>
          
        </fieldset>
      </form>
    </div>
    <div class="modal-footer" style="text-align: center;">
      <button class="btn" data-dismiss="modal">
        <i class="icon icon-off"></i> 取消
      </button>
      <a href="javascript:void(0);" class="btn btn-primary" id="sailorReportSaveButton">
        <i class="icon icon-ok icon-white"></i> 提交
      </a>
    </div>
  </div>

  <!-- Modal deleteMemberDialog -->
  <div id="deleteMemberDialog" class="modal hide fade">
    <form class="form-horizontal" name="deleteMemberForm"
      id="deleteMemberForm" novalidate="novalidate" method="post"
      action="${ctx}/space/contact/myContact/deleteContact">
      <input type="hidden" name="_method" value="delete" /> <input
        type="hidden" id="delContactId" name="contactId" value="0" /> <input
        type="hidden" id="delDeptId" name="deptId" value="${deptId}" />

      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>删除确认</h3>
      </div>
      <div class="modal-body">
        <p>你确认要删除该联系人吗？</p>
      </div>
      <div class="modal-footer">
        <button class="btn" data-dismiss="modal">
          <i class="icon icon-off"></i> 取消
        </button>
        <a class="btn btn-primary deleteMemberButton"> <i
          class="icon icon-ok icon-white"></i> 确认
        </a>
      </div>
    </form>
  </div>

  <!-- 添加成员对话框 -->
  <div id="addMemberDialog" class="modal hide fade"
    style="display: none; z-index: 1050 !important;">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal"
        aria-hidden="true">×</button>
      <h3>添加成员</h3>
    </div>
    <div class="modal-body">
      <form class="form-horizontal" name="addMemberForm" id="addMemberForm"
        novalidate="novalidate" method="post" action="">
        <fieldset>
          <div class="control-group">
            <label class="control-label">部门：</label>
            <div class="controls">
              <select id="addMemberDepartment" name="deptId"
                style="width: 200px; z-index: 99999 !important;">
                <c:forEach var="departmentData" items="${departmentDatas}">
                  <c:choose>
                    <c:when test="${deptId == departmentData.id}">
                      <option value="${departmentData.id}" selected>
                        ${departmentData.deptName}</option>
                    </c:when>
                    <c:otherwise>
                      <option value="${departmentData.id}">
                        ${departmentData.deptName}</option>
                    </c:otherwise>
                  </c:choose>
                </c:forEach>
              </select> <span class="color-red"></span>
            </div>
          </div>

          <div class="control-group">
            <label class="control-label">用户：</label>
            <div class="controls">
              <input type="text" class="input-medium" id="addMemberMobile"
                autocomplete="off" name="mobile" value=""
                placeholder="请输入用户真实姓名、电话、登录名或email" /> <span class="color-red"></span>
              <div style="height: 30px;"></div>
              <input id="trueMemberMobile" name="userId" type="hidden" value="">
            </div>
          </div>

        </fieldset>
      </form>
    </div>
    <div class="modal-footer" style="text-align: center;">
      <button class="btn" data-dismiss="modal">
        <i class="icon icon-off"></i> 取消
      </button>
      <a href="javascript:void(0);" class="btn btn-primary"
        id="addMemberButton"> <i class="icon icon-ok icon-white"></i> 提交
      </a>
    </div>
  </div>


  <!-- Modal addDepartmentDialog -->
  <div id="addDepartmentDialog" class="modal hide fade"
    style="display: none; z-index: 1050 !important; width: 620px;">
    <form class="form-horizontal" name="addDepartmentForm"
      id="addDepartmentForm" novalidate="novalidate" method="post"
      action="">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>编辑</h3>
      </div>
      <div class="modal-body">
        <fieldset>
          <div
            style="padding: 0px 20px; text-align: center; margin-top: 0px; margin-bottom: 20px;">
            <table border='1' ;cellspacing='0' ; cellpadding='0'
              id="departmentTable">
              <tbody>
                <tr>
                  <td style="background: #eeeeee;" align="center" class="bold"
                    width="80">序号</td>
                  <td style="background: #eeeeee;" align="center" class="bold"
                    width="200">部门名称</td>
                  <td style="background: #eeeeee;" align="center" class="bold"
                    width="200">部门类型</td>
                  <td style="background: #eeeeee; text-align: center"
                    align="center" class="bold" width="120">操作
                    <button class="btn btn-primary btnNewDepartment" title="添加部门"
                      idVal="0">
                      <i class="icon-plus icon-white"></i>
                    </button>
                  </td>
                </tr>
                <c:forEach var="departmentData" items="${departmentDatas}"
                  varStatus="st">
                  <tr>
                    <td>
                      <div>${st.index+1}</div>
                    </td>
                    <td>
                      <div>${departmentData.deptName}</div>
                    </td>
                    <td>
                      <div>${departmentData.deptType.description}</div>
                    </td>
                    <td style="text-align: center">
                      <div class="center">
                        <a class="btn btn-primary btnEditDepartment" title="修改部门"
                          deptId="${departmentData.id}"
                          deptName="${departmentData.deptName}"
                          deptType="${departmentData.deptType}"> <i
                          class="icon-pencil icon-white"></i>
                        </a> <a class="btn btn-danger btnDeleteDepartment" title="删除部门"
                          idVal="${departmentData.id}"> <i
                          class="icon-trash icon-white"></i>
                        </a>
                      </div>
                    </td>
                  </tr>
                </c:forEach>
                <tr>
                  <td colspan="4" align="left">
                    <div style="padding: 0px 20px;">
                      <input type="hidden" id="addDeptId" name="deptId" value="0">
                      部门名称: <input type="text" id="addDeptName" name="deptName"
                        value="" style="width: 150px;">
                    </div>
                    <div style="padding: 0px 20px;">
                      部门类型: <select id="addDeptType" name="deptType"
                        style="width: 150px; z-index: 99999 !important;">
                        <c:forEach var="deptType" items="${deptTypes}">
                          <option value="${deptType}">${deptType.description}</option>
                        </c:forEach>
                      </select>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </fieldset>
      </div>
      <div class="modal-footer">
        <button class="btn" data-dismiss="modal">
          <i class="icon icon-off"></i> 取消
        </button>
        <a class="btn btn-primary btnSaveDepartment"> <i
          class="icon icon-ok icon-white"></i> 保存
        </a>
      </div>
    </form>
  </div>

</body>
</html>

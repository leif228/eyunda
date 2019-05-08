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
<script src="${ctx}/js/manage/manage-message.js"></script>
<script type="text/javascript">
  var _rootPath = "${ctx}";
</script>
</head>

<body>

  <jsp:include page="./manage-head.jsp"></jsp:include>

  <div id="content">
    <div id="breadcrumb">
      <a href="#" style="font-size: 12px;" class="tip-bottom"> <i
        class="icon-home"></i> 通知管理
      </a> <a href="#" style="font-size: 12px;" class="current">通知查询</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span12">
          <form name="pageform" id="pageform"
            action="${ctx}/manage/message/message" method="post">
            <div class="widget-box">
              <div class="widget-title">
                <h5>通知列表</h5>
                <div>
                  <a title="search" class="icon"><i class="icofont-search"></i></a>
                  <input type="hidden" name="pageNo" id="pageNo" value="${pageData.pageNo}" />
                  <input name="userInfo" id="userInfo" type="text"
                    class="grd-white" value="${userInfo}"
                    style="margin-top: 3px; width: 320px"
                    placeholder="请输入接收人或发送人的用户名、昵称、手机、邮箱" />
                  <button type="submit" class="btn btn-primary"
                    id="btnSerachMessages"
                    style="margin-bottom: 8px; line-heigth: 20px">查询</button>
                </div>
              </div>
              <div class="widget-content nopadding">
                <table class="table table-bordered data-table">
                  <thead>
                    <tr>
                      <th>类别</th>
                      <th>标题</th>
                      <th>接收者</th>
                      <th>发送者</th>
                      <th>发送时间</th>
                      <th>阅读状态</th>
                      <th>操作</th>
                    </tr>
                  </thead>
                  <tbody>

                    <c:forEach items="${pageData.result}" var="message">
                      <tr>
                        <td>${message.msgType.description}</td>
                        <td>${message.title}</td>
                        <td>${message.receiver.trueName}</td>
                        <td>${message.sender.trueName}</td>
                        <td>${message.createTime}</td>
                        <td id="readStatus${message.id}">${message.readStatus.description}</td>
                        <td><a class="btn btn-primary btnShow" idVal="${message.id}"> 
                          <i class="icon-list-alt icon-white"></i> 查看
                        </a> <a class="btn btn-danger btnDelete" idVal="${message.id}">
                            <i class="icon-trash icon-white"></i> 删除
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

  <!-- Modal showDialog -->
  <div id="showDialog" class="modal hide fade">
    <form class="form-horizontal" name="showDialogForm"
      id="showDialogForm" novalidate="novalidate" method="post" action="#">
      <div class="modal-header">
        <input type="hidden" name="_method" value="" />
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>通知</h3>
      </div>
      <div class="modal-body">
        <h3 id="title"></h3>
        <p id="message"></p>
      </div>
      <div class="modal-footer">
        <button class="btn" data-dismiss="modal">
          <i class="icon icon-off"></i> 关闭
        </button>
      </div>
    </form>
  </div>

  <!-- Modal deleteDialog -->
  <div id="deleteDialog" class="modal hide fade">
    <form class="form-horizontal" name="deleteDialogForm"
      id="deleteDialogForm" novalidate="novalidate" method="post"
      action="${ctx}/manage/message/delete">
      <input type="hidden" name="_method" value="delete" /> 
      <input type="hidden" name="id" id="deleteId" value="0" />
      <input type="hidden" name="pageNo" id="delPageNo" value="0" />
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>删除确认</h3>
      </div>
      <div class="modal-body">
        <p>你确认要删除该通知吗？</p>
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

  <div id="dlgFindUser" class="modal hide fade">
    <form class="form-horizontal" name="dlgAddForm" id="dlgAddForm"
      novalidate="novalidate" action="${ctx}/manage/message/message/user">
      <input type="hidden" id="userId" name="userId" value="0" />
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>用户查询</h3>
      </div>
      <div class="modal-body">
        <fieldset>
          <div class="control-group pdleft">
            <label class="control-label" for="inputCombinedIcon"
              style="width: 45px; font-size: 14px;">用户信息：</label>
            <div class="controls" style="margin-left: 10px">
              <div class="input-append input-icon-prepend">
                <div class="add-on" style="width: 400px">
                  <a title="search" class="icon"><i class="icofont-search"></i></a>
                  <input name="keyWords" id="keyWords" type="text"
                    class="grd-white" style="width: 360px" />
                </div>
              </div>
            </div>
          </div>
          <div id="userList"></div>
        </fieldset>
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

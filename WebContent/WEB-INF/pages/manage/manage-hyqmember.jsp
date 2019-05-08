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
<script src="${ctx}/js/manage/manage-hyqmember.js"></script>
<script type="text/javascript">
  var _rootPath = "${ctx}";
</script>
</head>
<body>

  <jsp:include page="./manage-head.jsp"></jsp:include>

  <div id="content">
    <div id="breadcrumb">
      <a href="#" style="font-size: 12px;" class="tip-bottom"> <i
        class="icon-home"></i> 会员管理
      </a> <a href="#" style="font-size: 12px;" class="current">会员查询</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span12">
		 <form name="pageform" id="pageform"action="${ctx}/manage/member/hyqmember/list" method="post">
          <div class="widget-box">
            <div class="widget-title">
              <h5>会员列表</h5>
				<select name="status" id="status" style="width: 110px; ">
					<option value="" selected>全部状态...</option>
					<c:forEach var="st" items="${statuss}">
						<c:choose>
							<c:when test="${st == status}">
								<option value="${st}" selected>${st.description}</option>
							</c:when>
							<c:otherwise>
								<option value="${st}">${st.description}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
             	<input name="userInfo" id="userInfo" type="text" class="grd-white" value="${userInfo}"
             		style="margin-top:3px;width: 260px" placeholder="请输入用户登录名、真实姓名、昵称或邮箱"/>            	
           		<button type="submit" class="btn btn-primary" id="btnSerachUser" 
           			style="margin-bottom:8px;line-heigth: 20px">查询</button>
            </div>
            <div class="widget-content nopadding">
              <table class="table table-bordered data-table">
                <thead>
                  <tr>
                    <th>图标图片</th>
                    <th>创建日期</th>
                    <th>登录名</th>
                    <th>姓名</th>
                    <th>昵称</th>
                    <th>手机</th>
                    <th>电子邮箱</th>
                    <th>状态</th>
                    <th>操作</th>
                  </tr>
                </thead>

                <tbody>
                  <c:forEach items="${pageData.result}" var="user">
                    <tr class="gradeX">
                      <td><img
                        src="${ctx}/download/imageDownload?url=${user.userLogo}"
                        alt="" class="thumbnail" style="width: 60px; height: 60px;" />
                      </td>
                      <td>${user.createTime}</td>
                      <td>${user.loginName}</td>
                      <td>${user.trueName}</td>
                      <td>${user.nickName}</td>
                      <td>${user.mobile}</td>
                      <td>${user.email}</td>
                      <td>
                        <div id="status${user.id}">
                          ${user.status.description}
                        </div>
                      </td>
                      <td>
                        <a class="btn btn-primary btnShow" idVal="${user.id}" 
                       		    data-toggle="modal" data-target="#showDialog">
                          <i class="icon-list-alt icon-white"></i> 查看
                        </a>
                        <a class="btn btn-danger btnDelete" idVal="${user.id}" 
                       		    data-toggle="modal" data-target="#deleteDialog">
                          <i class="icon-trash icon-white"></i> 删除
                        </a>

                        <div id="act${user.id}">
                        
                        <div id="activity${user.id}">
                          <c:if test="${empty user.status || user.status == 'inactivity'}">
	                        <a class="btn btn-success btnActivity" idVal="${user.id}">
	                          <i class="icon-trash icon-check"></i> 激活
	                        </a>
                          </c:if>
                        </div>
                        
                        <div id="unActivity${user.id}">
                          <c:if test="${user.status == 'activity'}">
                            <a class="btn btn-warning btnUnActivity" idVal="${user.id}">
                              <i class="icon-trash icon-white"></i> 取消
                            </a>
                          </c:if>
                        </div>

                        </div>
                      </td>
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
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>会员个人信息</h3>
      </div>
      <div class="modal-body">

        <div class="control-group">
          <label class="control-label" for="shipName">登录名：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="loginName" value=""
              disabled />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="shipName">真实姓名：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="trueName" value=""
              disabled />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="shipName">昵称：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="nickName" value=""
              disabled />
          </div>
        </div>
        
        <div class="control-group">
          <label class="control-label" for="shipName">创建时间：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="createTime" value=""
              disabled />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="shipName">电子邮箱：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="email" value=""
              disabled />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="shipName">手机：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="mobile" value=""
              disabled />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label">头像：</label>
          <div class="controls">
            <div class="account-avatar">
              <img id="imgUserLogo"
                src="${ctx}/download/imageDownload?url=${user.userLogo}" alt=""
                class="thumbnail" style="width: 100px; height: 100px;" />
            </div>
          </div>
        </div>

        <div class="control-group">
          <label class="control-label">个性签名：</label>
          <div class="controls">
            <div class="account-avatar">
              <img id="imgSignature"
                src="${ctx}/download/imageDownload?url=${user.signature}" alt=""
                class="thumbnail" style="width: 100px; height: 100px;" />
            </div>
          </div>
        </div>

        <div class="control-group">
          <label class="control-label">图章：</label>
          <div class="controls">
            <div class="account-avatar">
              <img id="imgStamp"
                src="${ctx}/download/imageDownload?url=${user.stamp}" alt=""
                class="thumbnail" style="width: 100px; height: 100px;" />
            </div>
          </div>
        </div>

      </div>
    </form>
    <div class="modal-footer">
      <button class="btn" data-dismiss="modal">
        <i class="icon icon-off"></i> 关闭
      </button>
    </div>

  </div>

  <!-- Modal deleteDialog -->
  <div id="deleteDialog" class="modal hide fade">
    <form class="form-horizontal" name="deleteDialogForm"
      id="deleteDialogForm" novalidate="novalidate" method="post"
      action="${ctx}/manage/member/hyqmember/delete">
      <input type="hidden" name="_method" value="delete" /> <input
        type="hidden" name="id" id="deleteId" value="" />
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>删除确认</h3>
      </div>
      <div class="modal-body">
        <p>危险！删除用户将会删除用户的一切，你确认删除吗？</p>
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
  
  <!-- activity -->
  <div id="activityDialog" class="modal hide fade">
    <input type="hidden" name="id" id="activityId" value="" />
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>激活</h3>
    </div>
    <div class="modal-body">
      <p>你确认要激活该用户信息吗？</p>
    </div>
    <div class="modal-footer">
      <button class="btn" data-dismiss="modal">
        <i class="icon icon-off"></i> 取消
      </button>
      <button class="btn btn-primary activity">
        <i class="icon icon-ok icon-white"></i> 确认
      </button>
    </div>
  </div>
  
  <!-- unActivity -->
  <div id="unActivityDialog" class="modal hide fade">
    <input type="hidden" name="id" id="unActivityId" value="" />
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>取消激活</h3>
    </div>
    <div class="modal-body">
      <p>你确认要取消激活该用户信息吗？</p>
    </div>
    <div class="modal-footer">
      <button class="btn" data-dismiss="modal">
        <i class="icon icon-off"></i> 取消
      </button>
      <button class="btn btn-primary unActivity">
        <i class="icon icon-ok icon-white"></i> 确认
      </button>
    </div>
  </div>

</body>
</html>

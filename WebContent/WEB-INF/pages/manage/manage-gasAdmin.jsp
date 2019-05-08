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
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css" class="skin-color" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-datetimepicker.min.css" media="screen"/>

<script src="${ctx}/js/jquery-v.min.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/jquery.form-2.63.js"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/manage/manage-gasAdmin.js"></script>
  <script src="${ctx}/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script type="text/javascript">
  var _rootPath = "${ctx}";
</script>
<style>
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

.detail-descrip {
  width: 100%;
  margin-left: 50px;
  margin-bottom: 20px;
}

.detail-descrip-info {
  width: 620px;
}

.detail-descrip-text {
  
}

.form-horizontal .control-group {
  border: 0px;
}

.form-horizontal .control-label {
  width: 150px;
}

.form-horizontal .controls {
  margin-left: 170px;
}

.form-horizontal input[type=text], .form-horizontal input[type=password]
  {
  width: 200px
}

.select2-container {
  width: 200px
}

.user-info li {
  float: left;
  margin-left: 20px;
}

.data-center {
	margin-top: 3px;
}
</style>
</head>

<body>

  <jsp:include page="./manage-head.jsp"></jsp:include>

  <div id="content">
    <div id="breadcrumb">
      <a href="#" style="font-size: 12px;" class="tip-bottom"> <i
        class="icon-home"></i> 电商管理员
      </a> <a href="#" style="font-size: 12px;" class="current">电商管理员查询</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span12">
          <form name="pageform" id="pageform" action="${ctx}/manage/gas/gasAdmin" method="post">
            <div class="widget-box">
              <div class="widget-title">
                <h5>电商管理员列表</h5>
                <div style="float: left; margin-top: 2px;">
                <c:if test="${addBtn == true }">
                  <a class="btn btn-info btnAdd">
                    <i class="icon-plus icon-white"></i> 添加
                  </a>
                  </c:if>
                </div>
	                
	           <%--  <div style="float: left; margin-left: 12px;">
                  <a title="search" class="icon"><i class="icofont-search"></i></a>
               	  <input name="keyWords" id="keyWords" type="text" class="grd-white" 
               		value="${keyWords}" style="margin-top: 3px; width: 200px" placeholder="请输入加油站名称" />
               	  <button type="submit" class="btn btn-primary btnSerach" 
               		style="margin-bottom: 8px; line-heigth: 20px">查询</button>
                </div> --%>
              </div>

              <div class="widget-content nopadding" style="margin-top: -10px;">
                <table class="table table-bordered data-table">
                  <thead>
	                <tr>
	                  <th>登录名</th>
	                  <th>姓名</th>
	                  <th>昵称</th>
	                  <th>电子邮箱</th>
	                  <th>手机</th>
	                  <th>商品公司</th>
	                  <th>操作</th>
	                </tr>
                  </thead>
                  <tbody>
                    <c:forEach items="${pageData.result}" var="gasAdminData">
                    <tr>
                      <td>${gasAdminData.adminData.loginName}</td>
	                  <td>${gasAdminData.adminData.trueName}</td>
	                  <td>${gasAdminData.adminData.nickName}</td>
	                  <td>${gasAdminData.adminData.email}</td>
	                  <td>${gasAdminData.adminData.mobile}</td>
	                  <td>${gasAdminData.companyData.companyName}</td>
	                  <td>
	                      <a class="btn btn-primary btnEdit" idVal="${gasAdminData.id}">
						    <i class="icon-pencil icon-white"></i> 修改
				   	      </a>
	                      <a class="btn btn-danger btnDelete" idVal="${gasAdminData.id}">
						    <i class="icon-trash icon-white"></i> 删除
				   	      </a>
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
  
  <!-- Modal addGasAdminDialog -->
  <div id="addGasAdminDialog" class="modal hide fade">
    <form class="form-horizontal" name="addGasAdminForm"
			id="addGasAdminForm" novalidate="novalidate" method="post"
			action="${ctx}/manage/gas/gasAdmin/save">
		<input type="hidden" name="id" id="addId" value="0" />	
	    <input type="hidden" id="addKeyWords" value="${keyWords}" />
		<input type="hidden" id="addPageNo" value="${pageNo}" />
		
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>添加管理员</h3>
		</div>
		<div class="modal-body">
			<fieldset>
				<div class="control-group">
		          <label class="control-label">添加管理员：</label>
		          <div class="controls">
		            <input type="text" name="loginName" id="loginName"
		             value="" placeholder="输入管理员登录名"/>
		          </div>
		        </div>
				
				<div class="control-group">
			      <label class="control-label">选择商品公司：</label>
			      <div class="controls">
					<select id="companyId" name="companyId" style="width: 120px;">
						<c:forEach var="company" items="${companyDatas}">
			              <option value="${company.id}">${company.companyName}</option>
			            </c:forEach>
					</select>
				  </div>
				</div>
		    </fieldset>
		</div>
		
		<div class="modal-footer">
			<a class="btn" data-dismiss="modal">
				<i class="icon icon-off"></i> 取消
			</a>
			<a class="btn btn-primary addGasAdmin">
				<i class="icon icon-ok icon-white"></i> 保存
			</a>
		</div>
	 </form>
  </div>
  
  <!-- Modal deleteDialog -->
  <div id="deleteDialog" class="modal hide fade">
  	<input type="hidden" name="id" id="deleteId" value="" />
    <input type="hidden" id="delKeyWords" value="${keyWords}" />
	<input type="hidden" id="delPageNo" value="${pageNo}" />
	
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">×</button>
		<h3>删除确认</h3>
	</div>
	<div class="modal-body">
		<p>你确认要删除该电商管理员信息吗？</p>
	</div>
	<div class="modal-footer">
		<button class="btn" data-dismiss="modal">
			<i class="icon icon-off"></i> 取消
		</button>
		<button class="btn btn-primary delete">
			<i class="icon icon-ok icon-white"></i> 确认
		</button>
	</div>
  </div>

</body>
</html>

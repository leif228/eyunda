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
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/jquery.form-2.63.js"></script>

<script src="${ctx}/js/manage/manage-compCert.js"></script>

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
			<form novalidate="novalidate" method="post" id="pageform" action="${ctx}/manage/member/compCert/list">
	          <div class="widget-box">
	            <div class="widget-title">
	              <h5>${menuAct.menuname}列表</h5>
		          <div style="float:left;margin-left:10px;">
                    <input id="keyWords" name="keyWords" type="text" placeholder="公司名称" style="width: 140px;" value="${keyWords}" />
                    <button type="submit" class="btn btn-primary" id="serach" style="margin-bottom:8px;line-heigth: 20px">查询</button>
                  </div>
		          
		          <div style="float:right; margin-top: 4px; margin-right: 80px;">
			            <a id="btnAdd" class="btn btn-info">
			              <i class="icon-plus icon-white"></i> 添加
			            </a>
		          </div>
		          
	            </div>
	            <div class="widget-content nopadding">
	              <table class="table table-bordered data-table">
	                <thead>
	                  <tr>
	                    <th>公司名称</th>
	                    <th>公司Logo</th>
	                    <th>营业执照</th>
	                    <th>已认证</th>
	                    <th>操作</th>
	                  </tr>
	                </thead>
	
	                <tbody>
	                <c:forEach var="compCertData" items="${pageData.result}">
	                  <tr>
	                    <td>${compCertData.compName}</td>
	                    <td>
	                      <img src="${ctx}/download/imageDownload?url=${compCertData.compLogo}" 
                            alt="公司Logo" class="thumbnail" style="width: 60px; height: 60px;" />
	                    </td>
	                    <td>
	                      <img src="${ctx}/download/imageDownload?url=${compCertData.compLicence}" 
                            alt="营业执照" class="thumbnail" style="width: 60px; height: 60px;" />
	                    </td>
	                    <td>${compCertData.certify.description}</td>
	                    <td>
	                      <a class="btn btn-primary btnEdit" idVal="${compCertData.id}">
	                        <i class="icon-pencil icon-white"></i> 修改
	                      </a>
	                      <a class="btn btn-danger btnDel" idVal="${compCertData.id}">
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

  <!-- Modal editDialog -->
  <div id="editDialog" class="modal hide fade">
    <form class="form-horizontal" name="frmSave" 
      id="frmSave" novalidate="novalidate" method="post" 
      action="${ctx}/manage/member/compCert/save">
      <input type="hidden" name="id" id="id" value="0" />
      <input type="hidden" name="pageNo" value="${pageData.pageNo}" />
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>编辑</h3>
    </div>
    <div class="modal-body">
      <fieldset>

        <div id="selContent">

            <div class="control-group">
              <label class="control-label">公司名称：</label>
              <div class="controls">
                <input type="text" class="input-medium" id="compName" name="compName" value="" />
              </div>
            </div>
            
            <div class="control-group">
              <label class="control-label">公司Logo：</label>
              <div class="controls">
                <img id="compLogoImage" src="" alt="公司Logo" class="thumbnail" style="width: 60px; height: 60px;" />
                <input type="file" id="compLogoMpf" name="compLogoMpf" value="" />
              </div>
            </div>
            
            <div class="control-group">
              <label class="control-label">营业执照：</label>
              <div class="controls">
                <img id="compLicenceImage" src="" alt="营业执照" class="thumbnail" style="width: 60px; height: 60px;" />
                <input type="file" id="compLicenceMpf" name="compLicenceMpf" value="" />
              </div>
            </div>

            <div class="control-group">
              <label class="control-label">是否已认证：</label>
              <div class="controls">
                <input type="checkbox" id="certify" name="certify" value="yes" />已认证
              </div>
            </div>

        </div>

      </fieldset>
    </div>
    <div class="modal-footer">
      <button class="btn" data-dismiss="modal">
        <i class="icon icon-off"></i> 取消
      </button>
      <a href="javascript:void(0);"
        class="btn btn-primary btnSave">
        <i class="icon icon-ok icon-white"></i> 保存
      </a>
      <!-- <button class="btn btn-primary">
        <i class="icon icon-ok icon-white"></i> 保存
      </button> -->
    </div>
    </form>
  </div>

  <!-- Modal deleteDialog -->
  <div id="deleteDialog" class="modal hide fade">
    <form class="form-horizontal" name="frmDelete"
      id="frmDelete" novalidate="novalidate" method="post"
      action="${ctx}/manage/member/compCert/delete">
    <input type="hidden" name="_method" value="delete" />
    <input type="hidden" id="compCertId" name="compCertId" value="0" />
    <input type="hidden" name="pageNo" value="${pageData.pageNo}" />
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>删除确认</h3>
    </div>
    <div class="modal-body">
      <p>你确认要删除该公司信息吗？</p>
    </div>
    <div class="modal-footer">
      <button class="btn" data-dismiss="modal">
        <i class="icon icon-off"></i> 取消
      </button>
      <a href="javascript:void(0);"
        class="btn btn-primary btnDelete">
        <i class="icon icon-trash icon-white"></i> 删除
      </a>
      <!-- <button class="btn btn-primary">
        <i class="icon icon-ok icon-white"></i> 删除
      </button> -->
    </div>
    </form>
  </div>

</body>
</html>

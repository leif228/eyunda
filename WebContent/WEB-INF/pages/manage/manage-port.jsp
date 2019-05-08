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
<script src="${ctx}/js/manage/manage-port.js"></script>
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
      </a> <a href="#" style="font-size: 12px;" class="current">港口管理</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span12">
			<form novalidate="novalidate" method="post" id="pageform" action="${ctx}/manage/ship/port">
	          <div class="widget-box">
	            <div class="widget-title">
	              <h5>港口列表</h5>
		          <div style="float:left;margin-left:10px;">
		                <div style="float: left; margin-top:8px; margin-left:6px; width:42px; ">区域：</div>
		      			<div style="float: left; margin-top:5px; margin-left:6px;">
						  	<select id="portCity" name="portCity" style="width:150px;">
		                        <optgroup label="全部">
		                      	  <option value="">请选择</option>
		                        </optgroup>
			                    <c:forEach var="bigArea" items="${bigAreas}">
			                      <optgroup label="${bigArea.description}">
			                      <c:forEach var="portCity" items="${bigArea.portCities}">
			                        <c:if test="${selPortCity == portCity}">
			                        	<option value="${portCity}" selected >${portCity.description}</option>
			                        </c:if>
			                        <c:if test="${selPortCity != portCity}">
			                        	<option value="${portCity}" >${portCity.description}</option>
			                        </c:if>
			                      </c:forEach>
			                      </optgroup>
			                    </c:forEach>
		                    </select>
		                    <input id="keyWords" name="keyWords" type="text" placeholder="请输入港口名称" style="width: 140px;" value="${keyWords}" />
		                    <button type="submit" class="btn btn-primary" id="serachPort" style="margin-bottom:8px;line-heigth: 20px">查询</button>
						</div>
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
	                    <th>港口编号</th>
	                    <th>港口名称</th>
	                    <th>港口城市编号</th>
	                    <th>港口城市</th>
	                    <th>港口坐标</th>
	                    <th>操作</th>
	                  </tr>
	                </thead>
	
	                <tbody>
	                <c:forEach var="portData" items="${pageData.result}">
	                  <tr>
	                    <td>${portData.portNo}</td>
	                    <td>${portData.portName}</td>
	                    <td>${portData.portCity.code}</td>
	                    <td>${portData.portCity.description}</td>
	                    <td>
		                    <c:forEach var="portCooordData" items="${portData.portCooordDatas}">
		                    (${portCooordData.longitude }, ${portCooordData.latitude })<br>
		                    </c:forEach>
	                    </td>
	                    <td>
	                      <a class="btn btn-primary btnEdit" idVal="${portData.portNo}">
	                        <i class="icon-pencil icon-white"></i> 修改
	                      </a>
	                      <a class="btn btn-danger btnDelete" idVal="${portData.portNo}">
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
    <form class="form-horizontal" name="frmSavePort"
      id="frmSavePort" novalidate="novalidate" method="post" 
      action="${ctx}/manage/ship/port/save">
      <input type="hidden" name="portNo" id="portNo" value="" />
      <input type="hidden" name="pageNo" value="${pageData.pageNo}" />
      <input type="hidden" name="keyWords" value="${keyWords}" />
      <input type="hidden" name="portCity" value="${selPortCity}" />
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>编辑</h3>
    </div>
    <div class="modal-body">
      <fieldset>

        <div id="selContent"></div>

      </fieldset>
    </div>
    <div class="modal-footer">
      <button class="btn" data-dismiss="modal">
        <i class="icon icon-off"></i> 取消
      </button>
      <a href="javascript:void(0);"
        class="btn btn-primary btnSavePort">
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
    <form class="form-horizontal" name="frmDeletePort"
      id="frmDeletePort" novalidate="novalidate" method="post"
      action="${ctx}/manage/ship/port/delete">
    <!-- <input type="hidden" name="_method" value="delete" /> -->
    <input type="hidden" id="delPortNo" name="portNo" value="" />
    <input type="hidden" name="pageNo" value="${pageData.pageNo}" />
    <input type="hidden" name="keyWords" value="${keyWords}" />
    <input type="hidden" name="portCity" value="${selPortCity}" />
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>删除确认</h3>
    </div>
    <div class="modal-body">
      <p>你确认要删除该港口信息吗？</p>
    </div>
    <div class="modal-footer">
      <button class="btn" data-dismiss="modal">
        <i class="icon icon-off"></i> 取消
      </button>
      <a href="javascript:void(0);"
        class="btn btn-primary btnDeletePort">
        <i class="icon icon-trash icon-white"></i> 删除
      </a>
      <!-- <button class="btn btn-primary">
        <i class="icon icon-ok icon-white"></i> 确认
      </button> -->
    </div>
    </form>
  </div>

</body>
</html>

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
<script src="${ctx}/js/manage/manage-attribute.js"></script>
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
      </a> <a href="#" style="font-size: 12px;" class="current">类别属性</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span12">

          <div class="widget-box">
            <div class="widget-title">
              <h5>类别属性列表</h5>
              <button id="btnAdd" class="btn btn-info">
                <i class="icon-plus icon-white"></i> 添加
              </button>
              &nbsp;&nbsp;&nbsp;
              <span style="margin-top:5px">按属性分类
              	<select id="typeCode" name="typeCode" style="margin-top:4px;width:120px"><!-- onChange="javascript:void(0);" -->
              	  <c:forEach var="uncleData" items="${uncleDatas}">
              	   <optgroup label="${uncleData.typeName}">
              	     <c:forEach var="childrenData" items="${uncleData.childrenDatas}">
		              	<c:choose>
		              		<c:when test="${childrenData.typeCode == typeCode}">
		              			<option value="${childrenData.typeCode}" selected>${childrenData.typeName}</option>
		              		</c:when>
		              		<c:otherwise>
		              			<option value="${childrenData.typeCode}">${childrenData.typeName}</option>
		              		</c:otherwise>
		              	</c:choose>
              	     </c:forEach>
              	    </optgroup>
              	   </c:forEach>
              	</select>
              </span>
              
            </div>
            <div class="widget-content nopadding">
              <table class="table table-bordered data-table">
                <thead>
                  <tr>
                    <th>编码</th>
                    <th>类别</th>
                    <th>属性名称</th>
                    <th>属性类型</th>
                    <th>属性取值</th>
                    <th>操作</th>
                  </tr>
                </thead>

                <tbody>
                <c:forEach var="attrNameData" items="${attrNameDatas}">
                  <tr>
                  	<td>${attrNameData.attrNameCode}</td>
                  	<td>${attrNameData.typeData.typeName}</td>
					<td>${attrNameData.attrName}</td>
					<td>${attrNameData.attrType.description}</td>
                    <td style="width:400px">${attrNameData.attrValues}</td>
                    <td>
                      <button class="btn btn-primary btnEdit" idVal="${attrNameData.id}">
                        <i class="icon-pencil icon-white"></i>修改
                      </button>
                      <button class="btn btn-danger btnDelete" idVal="${attrNameData.id}">
                        <i class="icon-trash icon-white"></i>删除
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
      id="editDialogForm" novalidate="novalidate" method="post" action="${ctx}/manage/ship/attribute/save" >
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>编辑</h3>
    </div>
    <div id="editDlgBody"></div>
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
      action="${ctx}/manage/ship/attribute/delete">
    <input type="hidden" name="_method" value="delete" />
    <input type="hidden" id="delId" name="id" value="0" />
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>删除确认</h3>
    </div>
    <div class="modal-body">
      <p>你确认要删除该类别属性吗？</p>
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

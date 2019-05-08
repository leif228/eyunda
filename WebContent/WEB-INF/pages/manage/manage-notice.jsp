<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>新闻公告</title>

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
<script src="${ctx}/js/manage/manage-notice.js"></script>
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
        class="icon-home"></i> 新闻公告
      </a> <a href="#" style="font-size: 12px;" class="current">新闻公告</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span12">
         <form novalidate="novalidate" method="get" id="pageform" action="${ctx}/manage/notice/notice">
	        <div class="widget-box">
	          <div class="widget-title">
	            <h5>列表</h5>
	            <select name="selectCode" id="selectCode" style="width: 100px;">
	       	      <c:forEach var="columnCode" items="${columnCodes}">
	        	     <c:choose>
		         		<c:when test="${selectCode == columnCode}">
		         			<option value="${columnCode}" selected> ${columnCode.description}</option>
		         		</c:when>
		         		<c:otherwise>
		         			 <option value="${columnCode}"> ${columnCode.description}</option>
		         		</c:otherwise>
		         	</c:choose>
	       	      </c:forEach>
		       	</select>
	            <a class="btn btn-info btnAdd"><i class="icon-plus icon-white"></i>添加 </a>
	          </div>
	          <div class="widget-content nopadding">
	            <table class="table table-bordered data-table table-striped">
	              <thead>
	                <tr>
	                  <!-- <th style="width:8%;">栏目</th> -->
	                  <th style="width:14%;">标题</th>
	                  <th style="width:10%;">发布日期</th>
	                  <th style="width:8%;">发布状态</th>
	                  <th style="width:10%;">图片</th>
	                  <th style="width:26%;">内容</th>
	                  <th style="width:6%;">点击数</th>
	                  <th style="width:6%;">置顶</th>
	                  <th style="width:20%;">操作</th>
	                </tr>
	              </thead>
	              <tbody>
	                <c:forEach items="${pageData.result}" var="notice">
	                  <tr>
	                    <%-- <td>${notice.columnCode.description}</td> --%>
	                    <td>${notice.title}</td>
	                    <td id="relt${notice.id}">${notice.publishTime}</td>
	                    <td id="desc${notice.id}">${notice.releaseStatus.description}</td>
	                    <td>
	                      <c:choose>
                            <c:when test="${empty notice.source}">
                              <img src="${ctx}/img/notice/notice.png"
                                style="width: 80px; height: 60px;" alt="" class="thumbnail" />
                            </c:when>
                            <c:otherwise>
                              <img src="${ctx}/download/imageDownload?url=${notice.source}"
                      		    style="width: 80px; height: 60px;" alt="" class="thumbnail" />
                            </c:otherwise>
                          </c:choose>
	                    </td>
	                    <td>
	                      <c:if test="${fn:length(notice.content) < 25}">${notice.content}</c:if>
	                      <c:if test="${fn:length(notice.content) >= 25}">${fn:substring(notice.content,0,24)}...</c:if>
	                    </td>
	                    <td>${notice.pointNum}</td>
	                    <td id="top${notice.id}">${notice.top.description}</td>
	                    <td>
	                      <div id="btnTemp${notice.id}"></div>
	                      <div id="btnShow${notice.id}">
	                      	<c:if test="${notice.releaseStatus == 'unpublish'}">
	                          <a class="btn btn-success publish" idVal="${notice.id}">
				                <i class="icon icon-chevron-up icon-white"></i>发布
				              </a>
		                  	  <a class="btn btn-warning edit" idVal="${notice.id}">
					            <i class="icon icon-pencil icon-white"></i>修改
					          </a>
					          <a class="btn btn-danger delete" idVal="${notice.id}">
					            <i class="icon icon-trash icon-white"></i>删除
					          </a>
					        </c:if>
				            <c:if test="${notice.releaseStatus == 'publish'}">
				              <a class="btn btn-warning unpublish" idVal="${notice.id}">
				                <i class="icon icon-chevron-down icon-white"></i>取消发布
				              </a>
				            </c:if>
				           </div> 
				           <span id="btnTop${notice.id}">
				            <c:if test="${notice.top == 'yes'}">
				              <a class="btn btn-warning untop" idVal="${notice.id}">
				                <i class="icon icon-pencil icon-white"></i>取消置顶
				              </a>
				            </c:if>
				            <c:if test="${notice.top == 'no'}">
				              <a class="btn btn-success top" idVal="${notice.id}">
				                <i class="icon icon-flag icon-white"></i>置顶
				              </a>
				            </c:if>
				            </span>
	                      
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
  
  <div id="deleteDialog" class="modal hide fade">
    <form class="form-horizontal" name="deleteForm" id="deleteForm" 
    novalidate="novalidate" method="post" action="${ctx}/manage/notice/notice/delete">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>删除</h3>
      </div>
      <input type="hidden" name="id" id="id" value="" />
      <input type="hidden" name="pageNo" id="pageNo" value="${pageNo}" />
      <input type="hidden" name="selectCode" id="selectCode"  value="${selectCode}" />
      <div class="modal-body">
		你确认要删除吗？
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
  
  <!-- Modal deleteDialog -->
  <div id="unpublishDialog" class="modal hide fade">
    <form class="form-horizontal" name="unpublishForm" id="unpublishForm" 
    novalidate="novalidate" method="post" action="#">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>取消发布</h3>
      </div>
      <input type="hidden" name="id" id="unpublishId" value="" />
      <input type="hidden" name="selectCode" id="selectCode"  value="${selectCode}" />
      <div class="modal-body">
		你确认要取消发布吗？
      </div>
      <div class="modal-footer">
        <a class="btn" data-dismiss="modal">
          <i class="icon icon-off"></i> 取消
        </a>
        <a class="btn btn-primary btnUnpublish">
          <i class="icon icon-ok icon-white"></i> 确认
        </a>
      </div>
    </form>
  </div>
  
  <div id="publishDialog" class="modal hide fade">
    <form class="form-horizontal" name="publishForm" id="publishForm" 
    novalidate="novalidate" method="post" action="${ctx}/manage/notice/notice/publish">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>发布</h3>
      </div>
      <input type="hidden" name="id" id="publishId" value="" />
      <div class="modal-body">
		你确认要发布吗？
      </div>
      <div class="modal-footer">
        <a class="btn" data-dismiss="modal">
          <i class="icon icon-off"></i> 取消
        </a>
        <a class="btn btn-primary btnpublish">
          <i class="icon icon-ok icon-white"></i> 确认
        </a>
      </div>
    </form>
  </div>

</body>
</html>

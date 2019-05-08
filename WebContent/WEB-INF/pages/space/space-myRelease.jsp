<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
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
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css" class="skin-color" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-datetimepicker.min.css" media="screen"/>

<script src="${ctx}/js/jquery-v.min.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/bootstrap-colorpicker.js"></script>
<script src="${ctx}/js/bootstrap-datepicker.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/jquery.form-2.63.js"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script src="${ctx}/js/space/space-release.js"></script>
<script type="text/javascript">
  var _rootPath = "${ctx}";
</script>

<style>

#content{
 margin: 0px;
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

.help-inline{
	color:#B94A48;
}
</style>
</head>

<body>

  <jsp:include page="./space-head.jsp"></jsp:include>

  <div class="span10">

    <h1 class="page-title">
      <i class="icon-star icon-white"></i> 信息发布
    </h1>
    <div class="row">

      <div class="span10">
	      <form novalidate="novalidate" method="get" id="pageform" action="${ctx}/space/release/myRelease">
	        <div class="widget-box">
	          <div class="widget-title" style="margin-top:5px;">
	            <h5>信息发布</h5>
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
		       	<a class="btn btn-info btnRefresh"><i class="icon-refresh icon-white"></i>刷新 </a>
	          </div>
	          <div class="widget-content nopadding">
	            <table class="table table-bordered data-table table-striped">
	              <thead>
	                <tr>
	                  <!-- <th style="width:8%;">栏目</th> -->
	                  <th style="width:18%;">标题</th>
	                  <th style="width:10%;">发布日期</th>
	                  <th style="width:10%;">发布状态</th>
	                  <th style="width:10%;">大图</th>
	                  <th style="width:10%;">小图</th>
	                  <th style="width:30%;">内容</th>
	                  <th style="width:12%;">操作</th>
	                </tr>
	              </thead>
	              <tbody>
	                <c:forEach items="${pageData.result}" var="carrierIssue">
	                  <tr>
	                    <%-- <td>${carrierIssue.columnCode.description}</td> --%>
	                    <td>${carrierIssue.title}</td>
	                    <td id="relt${carrierIssue.id}">${carrierIssue.releaseTime}</td>
	                    <td id="desc${carrierIssue.id}">${carrierIssue.releaseStatus.description}</td>
	                    <td>
	                      <c:choose>
                            <c:when test="${empty carrierIssue.bigImage}">
                              <img src="${ctx}/img/${carrierIssue.typeImage}.jpg"
                                style="width: 80px; height: 60px;" alt="" class="thumbnail" />
                            </c:when>
                            <c:otherwise>
                              <img src="${ctx}/download/imageDownload?url=${carrierIssue.bigImage}"
                      		    style="width: 80px; height: 60px;" alt="" class="thumbnail" />
                            </c:otherwise>
                          </c:choose>
	                    </td>
	                    <td>
	                      <c:choose>
                            <c:when test="${empty carrierIssue.smallImage}">
                              <img src="${ctx}/img/${carrierIssue.typeImage}.jpg"
                                style="width: 80px; height: 60px;" alt="" class="thumbnail" />
                            </c:when>
                            <c:otherwise>
                              <img src="${ctx}/download/imageDownload?url=${carrierIssue.smallImage}"
                      		    style="width: 80px; height: 60px;" alt="" class="thumbnail" />
                            </c:otherwise>
                          </c:choose>
	                    </td>
	                    <td>
	                      <c:if test="${fn:length(carrierIssue.content) < 10}">${carrierIssue.content}</c:if>
	                      <c:if test="${fn:length(carrierIssue.content) >= 10}">${fn:substring(carrierIssue.content,0,15)}...</c:if>
	                    </td>
	                    <td>
	                      <div id="btnTemp${carrierIssue.id}"></div>
	                      <div id="btnShow${carrierIssue.id}">
	                      	<c:if test="${carrierIssue.releaseStatus == 'unpublish'}">
	                          <a class="btn btn-success publish" idVal="${carrierIssue.id}">
				                <i class="icon icon-chevron-up icon-white" title="发布"></i>
				              </a>
		                  	  <a class="btn btn-warning edit" idVal="${carrierIssue.id}">
					            <i class="icon icon-pencil icon-white" title="修改"></i>
					          </a>
					          <a class="btn btn-danger delete" idVal="${carrierIssue.id}">
					            <i class="icon icon-trash icon-white" title="删除"></i>
					          </a>
					        </c:if>
				            <c:if test="${carrierIssue.releaseStatus == 'publish'}">
				              <a class="btn btn-warning unpublish" idVal="${carrierIssue.id}">
				                <i class="icon icon-chevron-down icon-white" title="取消发布"></i>
				              </a>
				            </c:if>
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
      <!-- /span9 -->

    </div>
    <!-- /row -->

   </div>
  <!-- /row -->

  </div>
  <!-- /row -->

  </div>
  <!-- /container -->

  </div>
  <!-- /content -->

  <jsp:include page="./space-foot.jsp"></jsp:include>
  
  <!-- Modal publishDialog -->
  <div id="publishDialog" class="modal hide fade">
    <form class="form-horizontal" name="publishForm" id="publishForm" 
    novalidate="novalidate" method="post" action="${ctx}/space/release/myRelease/publish">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>发布</h3>
      </div>
      <input type="hidden" name="id" id="publishId" value="" />
      <input type="hidden" name="selectCode" id="selectCode" value="${selectCode}" />
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
  
  <!-- Modal publishDialog -->
  <div id="unpublishDialog" class="modal hide fade">
    <form class="form-horizontal" name="unpublishForm" id="unpublishForm" 
    novalidate="novalidate" method="post" action="#">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>取消发布</h3>
      </div>
      <input type="hidden" name="id" id="unpublishId" value="" />
      <input type="hidden" name="selectCode" id="selectCode" value="${selectCode}" />
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
  
  <!-- Modal deleteDialog -->
  <div id="deleteDialog" class="modal hide fade">
    <form class="form-horizontal" name="deleteForm" id="deleteForm" 
    novalidate="novalidate" method="post" action="${ctx}/space/release/myRelease/delete">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>删除</h3>
      </div>
      <input type="hidden" name="id" id="id" value="" />
      <input type="hidden" name="selectCode" id="selectCode" value="${selectCode}" />
      <input type="hidden" name="pageNo" id="pageNo" value="${pageNo}" />
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
  
</body>
</html>

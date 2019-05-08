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
<script src="${ctx}/js/jquery.imgbox.pack.js"></script>
<script src="${ctx}/js/manage/manage-ship.js"></script>
<script type="text/javascript">
var _rootPath = "${ctx}";
</script>

<style type="text/css">
	div .bar {
		text-align: center;
	}
</style>
<body>

  <jsp:include page="./manage-head.jsp"></jsp:include>

  <div id="content">
    <div id="breadcrumb">
      <a href="#" style="font-size: 12px;" class="tip-bottom"> <i
        class="icon-home"></i> 船舶管理
      </a> <a href="#" style="font-size: 12px;" class="current">船舶查询</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span12">
		 <form name="pageform" id="pageform"action="${ctx}/manage/ship/ship">
          <div class="widget-box">
            <div class="widget-title">
              <h5>船舶列表</h5>
                <select id="statusCode" name="statusCode" style="width: 80px;">
                  <option value="" selected>全部 </option>
                  <c:forEach var="shipStatusCode" items="${shipStatusCodes}">
                    <c:if test="${shipStatusCode == statusCode}">
                      <option value="${shipStatusCode}" selected>
                        ${shipStatusCode.description}
                      </option>
                    </c:if>
                    <c:if test="${shipStatusCode != statusCode}">
                      <option value="${shipStatusCode}">
                        ${shipStatusCode.description}
                      </option>
                    </c:if>
                  </c:forEach>
                  
                </select>
              
      		    <a title="search" class="icon"><i class="icofont-search"></i></a>
           	    <input name="keyWords" id="keyWords" type="text" value="${keyWords}" 
           		  style="margin-top:3px;width: 200px" placeholder="请输入船名或船号"/>
           		            	
       		    <button type="submit" class="btn btn-primary" style="margin-bottom:8px;line-heigth: 20px">查询</button>
       		 
            </div>
            <div class="widget-content nopadding">
              <table class="table table-bordered data-table">
                <thead>
                  <tr>
                    <th>类别</th>
                    <th>MMSI</th>
                    <th>船名</th>
                    <th>总吨</th>
                    <th>Logo</th>
                    <th>状态</th>
                    <th>操作时间</th>
                    <th style="width: 23%">操作</th>
                  </tr>
                </thead>

                <tbody>
                <c:forEach var="shipData" items="${pageData.result}">
                  <tr>
                    <td>${shipData.typeData.typeName}</td>
                    <td>${shipData.mmsi}</td>
                    <td>
                    	<a href="${ctx}/portal/home/shipInfo?shipId=${shipData.id}" target="_blank">
                    	${shipData.shipName}</a>
                    </td>
                    <td>${shipData.sumTons}吨</td>
  
                    <td>
                    	<a href="${ctx}/portal/home/shipInfo?shipId=${shipData.id}" target="_blank">
                    		<img src="${ctx}/download/imageDownload?url=${shipData.shipLogo}"
                      style="width: 80px; height: 60px;" alt="" class="thumbnail" /></a>
                    </td>
                    <td id="desc${shipData.id}">${shipData.shipStatus.description}</td>
                    <td id="releaseTime${shipData.id}">${shipData.releaseTime}</td>
                   
                    <td id="operation${shipData.id}" style="width: 140px">
                      <div id="temp${shipData.id}">
                        <c:if test="${shipData.shipStatus=='commit'}">
						  <a class="btn btn btn-primary btnCheck" idVal="${shipData.id}">
							<i class="icon-list-alt icon-white"></i>查看
						  </a>
						  <a class="btn btn-info btnAudit" idVal="${shipData.id}">
							<i class="icon-ok-circle icon-white"></i>审核
						  </a>
                      	</c:if>
						<c:if test="${shipData.shipStatus=='audit'}">
						  <a class="btn btn-success btnPublish" idVal="${shipData.id}" >
						    <i class="icon-chevron-up icon-white"></i>发布
						  </a>
						  <a class="btn btn-warning btnUnAudit" idVal="${shipData.id}" >
						    <i class="icon-repeat icon-white"></i>取消审核
						  </a>
                      	</c:if>
                      	<c:if test="${shipData.shipStatus=='publish'}">
                      	  <a class="btn btn-warning btnUnRelease" idVal="${shipData.id}" >
						    <i class="icon-chevron-down icon-white"></i>取消发布
						  </a>
	                   	  <%-- <a class="btn btn-inverse btnAnalyse"
	                   			href="${ctx}/manage/ship/ship/analyseCooords?mmsi=${shipData.mmsi}">
						    <i class="icon-repeat icon-white"></i>分析
						  </a> --%>
                      	</c:if>
					    <a class="btn btn-danger btnDelete" idVal="${shipData.id}" >
						  <i class="icon-trash icon-white"></i>删除
					    </a>
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
  
  <!-- Modal deleteDialog -->
  <div id="auditDialog" class="modal hide fade">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>审核确认</h3>
    </div>
    <input type="hidden" name="id" id="auditId" value="" />
    <div class="modal-body">
      <p>你确认要审核通过该船舶信息吗？</p>
    </div>
    <div class="modal-footer">
      <button class="btn" data-dismiss="modal">
        <i class="icon icon-off"></i> 取消
      </button>
      <button class="btn btn-primary audit">
        <i class="icon icon-ok icon-white"></i> 确认
      </button>
    </div>
  </div>
  
  <!-- Modal deleteDialog -->
  <div id="unAuditDialog" class="modal hide fade">
	<input type="hidden" name="id" id="unAuditId" value="" />
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>取消审核</h3>
    </div>
    <div class="modal-body">
      <p>你确认要取消审核该船舶信息吗？</p>
    </div>
    <div class="modal-footer">
      <button class="btn" data-dismiss="modal">
        <i class="icon icon-off"></i> 取消
      </button>
      <button class="btn btn-primary unAudit">
        <i class="icon icon-ok icon-white"></i> 确认
      </button>
    </div>
  </div>
  
  <!-- Modal publishDialog -->
  <div id="publishDialog" class="modal hide fade">
	<input type="hidden" name="id" id="publishId" value="" />
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">×</button>
		<h3>发布</h3>
	</div>
	<div class="modal-body">
		<p>你确认要发布该船舶信息吗？</p>
	</div>
	<div class="modal-footer">
		<button class="btn" data-dismiss="modal">
			<i class="icon icon-off"></i> 取消
		</button>

		<button class="btn btn-primary publish">
			<i class="icon icon-ok icon-white"></i> 确认
		</button>
	</div>
  </div>
  
  <!-- Modal unpublishDialog -->
  <div id="unpublishDialog" class="modal hide fade">
	<input type="hidden" name="id" id="unPublishId" value="" />

	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">×</button>
		<h3>取消发布</h3>
	</div>
	<div class="modal-body">
		<p>你确认要取消发布该船舶信息吗？</p>
	</div>
	<div class="modal-footer">
		<button class="btn" data-dismiss="modal">
			<i class="icon icon-off"></i> 取消
		</button>

		<button class="btn btn-primary unPublish">
			<i class="icon icon-ok icon-white"></i> 确认
		</button>
	</div>
  </div>
  
  <!-- Modal deleteDialog -->
  <div id="deleteDialog" class="modal hide fade">
    <input type="hidden" name="id" id="deleteId" value="" />
    <input type="hidden" name="pageNo" id="pageNo" value="${pageNo}" />
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>删除确认</h3>
    </div>
    <div class="modal-body">
      <p>删除船舶信息将不可恢复！</p>
      <p>你确认要删除该船舶信息吗？</p>
    </div>
    <div class="modal-footer">
      <a class="btn" data-dismiss="modal">
        <i class="icon icon-off"></i> 取消
      </a>
      <a class="btn btn-primary delete">
        <i class="icon icon-ok icon-white"></i> 确认
      </a>
    </div>
  </div>

  <!-- Modal showDialog -->
  <div id="showDialog" class="modal hide fade">
    <form class="form-horizontal" name="showDialogForm"
      id="showDialogForm" novalidate="novalidate" method="post" action="#">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>船舶审核信息</h3>
      </div>
      <div class="modal-body">

		<div class="control-group">
          <label class="control-label" for="createTime">创建时间：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="createTime" value=""
              disabled />
          </div>
        </div>
        
		<div class="control-group">
          <label class="control-label" for="warrantType">委托类型：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="warrantType" value=""
              disabled />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="shipName">船名：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="shipName" value=""
              disabled />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="mmsi">MMSI：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="mmsi" value=""
              disabled />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="shipMaster">船东姓名：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="shipMaster" value=""
              disabled />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label">船东身份证正面：</label>
          <div class="controls">
            <div class="account-avatar">
              <a id="idCardFrontLink" title="" href="" target="_blank">
	  		    <img id="idCardFront" title="点击查看大图" src=" " alt=""
                  class="thumbnail" style="width: 100px; height: 100px;" />
	  		  </a>
            </div>
          </div>
        </div>

        <div class="control-group">
          <label class="control-label">船东身份证反面：</label>
          <div class="controls">
            <div class="account-avatar">
             <a id="idCardBackLink" title="" href="" target="_blank">
              <img id="idCardBack" title="点击查看大图" src=" " alt=""
                class="thumbnail" style="width: 100px; height: 100px;" />
              </a>
            </div>
          </div>
        </div>

        <div class="control-group">
          <label class="control-label">船东委托书：</label>
          <div class="controls">
            <div class="account-avatar">
              <a id="warrantLink" title="" href="" target="_blank">
                <img id="warrant" title="点击查看大图" src=" " alt=""
                  class="thumbnail" style="width: 100px; height: 100px;" />
              </a>
            </div>
          </div>
        </div>
        
        <div class="control-group">
          <label class="control-label">船舶运营证书：</label>
          <div class="controls">
            <div class="account-avatar">
              <a id="certificateLink" title="" href="" target="_blank">
                <img id="certificate" title="点击查看大图" src=" " alt=""
                  class="thumbnail" style="width: 100px; height: 100px;" />
              </a>
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
  
</body>
</html>

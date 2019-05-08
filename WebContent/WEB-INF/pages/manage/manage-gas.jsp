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


<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/jquery.form-2.63.js"></script>
<script src="${ctx}/js/manage/manage-gas.js"></script>
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
</style>
</head>

<body>

  <jsp:include page="./manage-head.jsp"></jsp:include>

  <div id="content">
    <div id="breadcrumb">
      <a href="#" style="font-size: 12px;" class="tip-bottom"> <i
        class="icon-home"></i> 商品管理
      </a> <a href="#" style="font-size: 12px;" class="current">商品查询</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span12">
          <form name="pageform" id="pageform" action="${ctx}/manage/gas/gas" method="get">
            <div class="widget-box">
              <div class="widget-title">
                <h5>商品列表</h5>
                <div style="float: left; margin-top: 2px;">
                  <a class="btn btn-info btnAdd">
                    <i class="icon-plus icon-white"></i> 添加
                  </a>
                </div>
                
				<div class="controls" style="float: left; margin-top: 3px; margin-left: 12px;">
					<select id="companyId" name="companyId" style="width: 120px;">
                       	<option value="" selected>全部卖家</option>
						<c:forEach var="companyData" items="${companyDatas}">
	                      	<c:if test="${companyData.id ==  companyId}">
	                        	<option value="${companyData.id}" selected>${companyData.companyName}</option>
	                      	</c:if>
	                      	<c:if test="${companyData.id !=  companyId}">
	                        	<option value="${companyData.id}">${companyData.companyName}</option>
	                      	</c:if>
	                    </c:forEach>
					</select>
				</div>
				
				<div class="controls" style="float: left; margin-top: 3px; margin-left: 12px;">
					<select id="selectCode" name="selectCode" style="width: 120px;">
                       	<option value="" selected>全部商品状态</option>
						<c:forEach var="statusCode" items="${statusCodes}">
	                      	<c:if test="${statusCode ==  status}">
	                        	<option value="${statusCode}" selected>${statusCode.description}</option>
	                      	</c:if>
	                      	<c:if test="${statusCode !=  status}">
	                        	<option value="${statusCode}">${statusCode.description}</option>
	                      	</c:if>
	                    </c:forEach>
					</select>
				</div>
				
                <div style="float: left; margin-left: 12px;">
                  <button type="submit" class="btn btn-primary" 
                  	style="margin-top: 3px;">查询</button>
                </div>
              </div>

              <div class="widget-content nopadding">
                <table class="table table-bordered data-table">
                  <thead>
	                <tr>
	                  <th>商品图片</th>
	                  <th>商品名称</th>
	                  <th>公司名称</th>
	                  <th>建立日期</th>
	                  <th>销售价格</th>
	                  <th>商品状态</th>
	                  <th>上下架日期</th>
	                  <th>发布人</th>
	                  <th style="width: 22%;">操作</th>
	                </tr>
                  </thead>
                  <tbody>
                    <c:forEach items="${pageData.result}" var="gasWares">
                    <tr>
                      <td>
                      	<img src="${ctx}/download/imageDownload?url=${gasWares.waresLogo}" 
                      		alt="" class="thumbnail" style="width: 60px; height: 60px;" />
                      </td>
                      <td>${gasWares.waresName}</td>
	                  <td>${gasWares.companyData.companyName}</td>
	                  <td>${gasWares.createTime}</td>
	                  <td>${gasWares.price}</td>
	                  <td id="desc${gasWares.id}">${gasWares.status.description}</td>
	                  <td id="sellTime${gasWares.id}">${gasWares.sellTime}</td>
	                  <td>${gasWares.admin.trueName}</td>
	                  <td>
	                    <div id="temp${gasWares.id}"></div>
	                    <div id="button${gasWares.id}">
	                    <c:if test="${gasWares.status == 'unpublish'}">
                      	  <a class="btn btn-success btnPublish" idVal="${gasWares.id}">
                       		<i class="icon-chevron-up icon-white"></i> 发布
                     	  </a>
                     	  <a class="btn btn-primary btnEdit" idVal="${gasWares.id}">
                       		<i class="icon-pencil icon-white"></i> 修改
                     	  </a>
                     	  <a class="btn btn-danger btnDelete" idVal="${gasWares.id}">
                       		<i class="icon-trash icon-white"></i> 删除
                     	  </a>
                      	 </c:if>
                      	 <c:if test="${gasWares.status == 'publish'}">
                      	   <a class="btn btn-warning btnUnpublish" idVal="${gasWares.id}">
							 <i class="icon-chevron-down icon-white"></i> 取消发布
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
      </div>

      <jsp:include page="./manage-foot.jsp"></jsp:include>

    </div>
  </div>
  
   <!-- Modal addGasAdminDialog -->
  <div id="addGasDialog" class="modal hide fade">
    <form class="form-horizontal" name="addGasForm"
			id="addGasForm" novalidate="novalidate" method="post"
			action="${ctx}/manage/gas/gas/save" enctype="multipart/form-data">
		<input type="hidden" name="id" id="addId" value="0" />
		
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>添加商品</h3>
		</div>
		<div class="modal-body">
			<input type="hidden" id="addCompanyId" value="${companyId}" />
	    	<input type="hidden" id="addStatus" value="${status}" />
			<input type="hidden" id="addPageNo" value="${pageNo}" />
			<fieldset>
				<div class="control-group">
			      <label class="control-label">选择商品公司：</label>
			      <div class="controls">
					<select id="companyIdEdit" name="companyId" style="width: 120px;">
						<c:forEach var="company" items="${companyDatas}">
			              <option value="${company.id}">${company.companyName}</option>
			            </c:forEach>
					</select>
				  </div>
				</div>
				
				<div class="control-group">
                  <label class="control-label"></label>
                  <div class="controls">
                    <div class="account-avatar">
                      <img id="waresLogo" src="${ctx}/download/imageDownload?url=${waresData.waresLogo}"
                        alt="" class="thumbnail" style="width: 60px; height: 60px;">
                    </div>
                  </div>
                </div>
                
                <div class="control-group">
                  <label class="control-label">商品图片：</label>
                  <div class="controls">
                    <input type="file" id="waresFile" name="waresFile" value="" />
                  </div>
                </div>
				
				<div class="control-group">
		          <label class="control-label">商品名称：</label>
		          <div class="controls">
		            <input type="text" name="waresName" id="waresName"
		             value="" placeholder="输入商品名称"/>
		          </div>
		        </div>
		        
		        <div class="control-group">
		          <label class="control-label">商品描述：</label>
		          <div class="controls">
		            <input type="text" name="subTitle" id="subTitle"
		             value="" placeholder="输入商品描述"/>
		          </div>
		        </div>
		        
				<div class="control-group">
                  <label class="control-label">商品详情：</label>
                  <div class="controls">
                    <textarea style="width: 58%; resize:none" name="description"
                     	id="description" rows="3" placeholder="输入详情说明 ..."></textarea>
                  </div>
                </div>
                      
				<div class="control-group">
		          <label class="control-label">市场价格：</label>
		          <div class="controls">
		            <input type="text" name="stdPrice" id="stdPrice" 
		            value="" placeholder="输入市场价格"/>
		          </div>
		        </div>
		        
		        <div class="control-group">
		          <label class="control-label">销售价格：</label>
		          <div class="controls">
		            <input type="text" name="price" id="price" 
		            value="" placeholder="输入销售价格"/>
		          </div>
		        </div>
		        
		        <div class="control-group">
		          <label class="control-label">价格标语：</label>
		          <div class="controls">
		            <input type="text" name="priceSignal" id="priceSignal" 
		            value="" placeholder="输入价格标语"/>
		          </div>
		        </div>
		    </fieldset>
		</div>
		
		<div class="modal-footer">
			<a class="btn" data-dismiss="modal">
				<i class="icon icon-off"></i> 取消
			</a>
			<a class="btn btn-primary saveGas">
				<i class="icon icon-ok icon-white"></i> 保存
			</a>
		</div>
	 </form>
  </div>
  
   <!-- Modal publishDialog -->
  <div id="publishDialog" class="modal hide fade">
  	<input type="hidden" name="id" id="publishId" value="" />
	<input type="hidden" name="keyWords" value="${keyWords}" />
	<input type="hidden" name="pageNo" value="${pageNo}" />
	
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">×</button>
		<h3>上架确认</h3>
	</div>
	<div class="modal-body">
		<p>你确认要将该商品上架吗？</p>
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
  
   <!-- Modal unpblishDialog -->
  <div id="unpublishDialog" class="modal hide fade">
  	<input type="hidden" name="id" id="unpublishId" value="" />
  	<input type="hidden" name="keyWords" value="${keyWords}" />
	<input type="hidden" name="pageNo" value="${pageNo}" />
	
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">×</button>
		<h3>下架确认</h3>
	</div>
	<div class="modal-body">
		<p>你确认要将该商品下架吗？</p>
	</div>
	<div class="modal-footer">
		<button class="btn" data-dismiss="modal">
			<i class="icon icon-off"></i> 取消
		</button>
		<button class="btn btn-primary unpublish">
			<i class="icon icon-ok icon-white"></i> 确认
		</button>
	</div>
  </div>

  <!-- Modal deleteDialog -->
  <div id="deleteDialog" class="modal hide fade">
  	<input type="hidden" name="id" id="deleteId" value="" />
    <input type="hidden" id="delCompanyId" value="${companyId}" />
    <input type="hidden" id="delStatus" value="${status}" />
	<input type="hidden" id="delPageNo" value="${pageNo}" />
	
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">×</button>
		<h3>删除确认</h3>
	</div>
	<div class="modal-body">
		<p>你确认要删除该商品信息吗？</p>
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

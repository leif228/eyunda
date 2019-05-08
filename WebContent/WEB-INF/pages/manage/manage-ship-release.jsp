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

<link rel="stylesheet" href="${ctx}/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-responsive.css" />
<link rel="stylesheet" href="${ctx}/css/font-awesome.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn-responsive.css" />
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
<script src="${ctx}/js/manage/manage-ship-release.js"></script>
<script type="text/javascript">
var _rootPath = "${ctx}";
</script>

<style >
	.user-info li{
	float:left;
	margin-left:20px;
	}
	
	.addBack {
	  background: #00CCFF;
	}
	
	.account-container {
	display: table;
	}
	
	.account-avatar,
	.account-details {
		display: table-cell;
		vertical-align: top;
	}
	
	.account-avatar {
		padding-right: 1em;
	}
	
	.account-avatar img {
		width: 55px;
		height: 55px;
	}
	
	.account-details {
	}
	
	.account-details span {
		display: block;
	}
	
	.account-details .account-name {
		font-size: 15px;
		font-weight: 600;
	}
	
	.account-details .account-role {
		color: #888;
	}
	
	.account-details .account-actions {
		color: #BBB;
	}
	
	.account-details a {
		font-size: 11px;
	}
	
	a {
    color: inherit;
	}
</style>
<body>

  <jsp:include page="./manage-head.jsp"></jsp:include>

  <div id="content">
    <div id="breadcrumb">
      <a href="#" style="font-size: 12px;" class="tip-bottom"> <i
        class="icon-home"></i> 船舶管理
      </a> <a href="#" style="font-size: 12px;" class="current">船舶发布</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span12">
		 <form name="pageform" id="pageform"action="${ctx}/manage/ship/release">
          <div class="widget-box">
            <div class="widget-title">
              <h5>船舶发布列表</h5>
             <!--   <button id="btnAdd" class="btn btn-info">
                <i class="icon-plus icon-white"></i> 添加
              </button>-->
               <a href="javascript:void(0);" id="addShipRelease" class="btn btn-info"><i class="icon-plus icon-white"></i> 添加</a>
              	栏目<select id="rlsColumn" name="rlsColumn"  style="width:120px;"><!-- onChange="javascript:void(0);" -->
              	     <c:forEach var="rlsColumnCode" items="${rlsColumnCodes}">
	              	     <c:choose>
		              		<c:when test="${rlsColumnCode == rlsColumnSelect}">
		              			<option value="${rlsColumnCode}" selected> ${rlsColumnCode.description}</option>
		              		</c:when>
		              		<c:otherwise>
		              			 <option value="${rlsColumnCode}" > ${rlsColumnCode.description}</option>
		              		</c:otherwise>
		              	</c:choose>
              	     </c:forEach>
              	</select>
            </div>
            <div class="widget-content nopadding">
              <table class="table table-bordered data-table">
                <thead>
                  <tr>
                    <th>栏目</th>
                    <th>序号</th>
                    <th>类别</th>
                    <th>MMSI</th>
                    <th>船名</th>
                    <th>总吨</th>
                    <th>Logo</th>
                    <th>承运人</th>
                    <th>发布者</th>
                    <th>发布时间</th>
                    <th>操作</th>
                  </tr>
                </thead>

                <tbody>
                <c:forEach var="releaseData" items="${pageData.result}">
                  <tr>
                    <td>${releaseData.rlsColumn.description}</td>
                    <td>${releaseData.no}</td>
                    <td>${releaseData.shipData.typeData.typeName}</td>
                    <td>${releaseData.shipData.mmsi}</td>
                    <td>${releaseData.shipData.shipName}</td>
                    <td>${releaseData.shipData.sumTons}吨</td>
                    <td><img src="${ctx}/download/imageDownload?url=${releaseData.shipData.shipLogo}"
                      style="width: 80px; height: 60px;" alt="" class="thumbnail" /></td>
                    <td>${releaseData.shipData.broker.trueName}</td>
                    <td>${releaseData.adminData.trueName}</td>
                    <td>${releaseData.createTime}</td>
                    <td>
                    	<a href="javascript:void(0);" class="btn btn-primary editShipRelease" idVal="${releaseData.id}"><i class="icon-pencil icon-white"></i> 修改</a>
                    	<a href="javascript:void(0);" class="btn btn-primary delShipRelease" idVal="${releaseData.id}"><i class="icon-trash icon-white"></i> 删除 </a>
                    	<!--  <button class="btn btn-primary btnEdit" idVal="${releaseData.id}">
                          <i class="icon-pencil icon-white"></i> 修改
                        </button>
                        <button class="btn btn-primary btnDelete" idVal="${releaseData.id}">
                          <i class="icon-trash icon-white"></i> 删除
                        </button>-->
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
  <div id="deleteReleaseDialog" class="modal hide fade">
    <form class="form-horizontal" name="deleteDialogForm"
      id="deleteDialogForm" novalidate="novalidate" method="post"
      action="${ctx}/manage/ship/release/delete">
      <input type="hidden" name="_method" value="delete" />
	<input type="hidden"  id="delId" name="id" value="" />
	 <input type="hidden" id="rlsColumnShipDel" name="rlsColumnShip" value="" />
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>删除确认</h3>
    </div>
    <div class="modal-body">
      <p>删除船舶信息将不可恢复！</p>
      <p>你确认要删除该船舶信息发布吗？</p>
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
  
  <!-- Modal dlgAddShipRelease -->
  <div id="dlgAddShipRelease" class="modal hide fade">
    <form class="form-horizontal" name="frmSaveShipRelease" id="frmSaveShipRelease" method="get"
      		novalidate="novalidate" action="${ctx}/manage/ship/release/save">
      <input type="hidden" id="shipId" name="shipId" value="0" />
      <input type="hidden" id="id" name="id" value="0" />
      <input type="hidden" id="rlsColumnRelease" name="rlsColumnRelease" value="" />
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>选择添加</h3>
      </div>
      <div class="modal-body">
        <fieldset>
          <div class="control-group pdleft">
            <label class="control-label" for="inputCombinedIcon"
              style="width: 45px; font-size: 14px;">船舶：</label>
            <div class="controls" style="margin-left: 10px">
              <div class="input-append input-icon-prepend">
                <div class="add-on" style="width: 400px">
                  <a title="search" class="icon"><i class="icofont-search"></i></a>
                  	<input name="shipKeyWords" id="shipKeyWords" type="text" placeholder="请输入船名、船编号、船类别编号"
                  			class="grd-white" style="width: 360px" />
                </div>
                <a href="javascript:void(0);" id="serachShipRelease" class="btn"
                  style="height: 20px; line-heigth: 20px">查询</a>
              </div>
            </div>
          </div>
          <div id="shipList"></div>
        </fieldset>
      </div>
      <div class="modal-footer">
	      <button class="btn" data-dismiss="modal">
				<i class="icon icon-off"></i> 取消
			</button>
			<button class="btn btn-primary">
				<i class="icon icon-ok icon-white"></i> 确认
			</button>
       <!--  <a href="javascript:void(0);" class="btn" data-dismiss="modal">
          <i class="icon icon-off"></i> 取消
        </a>
        <a href="javascript:void(0);" id="saveShipRelease" class="btn btn-primary">
          <i class="icon icon-ok icon-white"></i> 确认
        </a>-->
      </div>
    </form>
  </div>
  
   <!-- Modal dlgEditShipRelease -->
  <div id="dlgEditShipRelease" class="modal hide fade">
    <form class="form-horizontal" name="frmEditShipRelease" id="frmEditShipRelease" method="get"
      		novalidate="novalidate" action="${ctx}/manage/ship/release/save">
      <input type="hidden" id="shipIdRelease" name="shipId" value="0" />
      <input type="hidden" id="releaseId" name="id" value="0" />
      <input type="hidden" id="rlsColumnReleaseShip" name="rlsColumnRelease" value="" />
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>编辑修改</h3>
      </div>
      <div class="modal-body">
        <fieldset>
	       	<div class="account-avatar">
			    <img id="imageShipLogo" src="" class="thumbnail" />
			 </div>
			 <div style="width: 140px">
			 	<div>
			 	 船名：<span id="shipReleaseName"></span>
			 	</div>
			 	<div >
			 	 序号：<span><input type="text" style="width: 40px" id="releaseNo" name="no" value=""/></span>
			 	</div>
			 </div>
        </fieldset>
      </div>
      <div class="modal-footer">
       <button class="btn" data-dismiss="modal">
				<i class="icon icon-off"></i> 取消
			</button>
			<button class="btn btn-primary">
				<i class="icon icon-ok icon-white"></i> 确认
			</button>
      
      <!--  <a href="javascript:void(0);" class="btn" data-dismiss="modal">
          <i class="icon icon-off"></i> 取消
        </a>
        <a href="javascript:void(0);" id="saveShipReleaseEdit" class="btn btn-primary">
          <i class="icon icon-ok icon-white"></i> 确认
        </a>--> 
      </div>
    </form>
  </div>
 

</body>
</html>

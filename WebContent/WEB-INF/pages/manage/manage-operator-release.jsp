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
<script src="${ctx}/js/unicorn.tables.js"></script>
<script src="${ctx}/js/manage/manage-operator-release.js"></script>
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
        class="icon-home"></i> 会员管理
      </a> <a href="#" style="font-size: 12px;" class="current">承运人发布</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span12">

          <div class="widget-box">
            <div class="widget-title">
              <h5>承运人发布列表</h5>
             <!--   <button id="btnAdd" class="btn btn-info">
                <i class="icon-plus icon-white"></i> 添加
              </button>-->
               <a href="javascript:void(0);" id="addOperRelease" class="btn btn-info"><i class="icon-plus icon-white"></i> 添加</a>
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
                    <th>图标图片</th>
                    <th>姓名</th>
                    <th>手机</th>
                    <th>地区</th>
                    <th>公司名称</th>
                    <th>发布者</th>
                    <th>发布时间</th>
                    <th>操作</th>
                  </tr>
                </thead>

                <tbody>
                <c:forEach var="releaseOperatorData" items="${releaseOperatorDatas}">
                  <tr>
                    <td>${releaseOperatorData.rlsColumn.description}</td>
                    <td>${releaseOperatorData.no}</td>
                    <td><img src="${ctx}/download/imageDownload?url=${releaseOperatorData.operatorData.userLogo}" 
                    alt="" class="thumbnail" style="width: 60px; height: 60px;" /></td>
                    <td>${releaseOperatorData.operatorData.trueName}</td>
                    <td>${releaseOperatorData.operatorData.mobile}</td>
                    <td style="width: 230px;">${releaseOperatorData.operatorData.unitAddr}</td>
                    <td>${releaseOperatorData.operatorData.unitName}</td>
                    <td>${releaseOperatorData.adminData.trueName}</td>
                    <td>${releaseOperatorData.createTime}</td>
                    <td>
                    	 <a href="javascript:void(0);" class="btn btn-primary editOperRelease" idVal="${releaseOperatorData.id}"><i class="icon-pencil icon-white"></i> 修改</a>
                    	<a href="javascript:void(0);" class="btn btn-primary delOperRelease" idVal="${releaseOperatorData.id}"><i class="icon-trash icon-white"></i> 删除 </a>
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
          </div>

        </div>
      </div>

      <jsp:include page="./manage-foot.jsp"></jsp:include>

    </div>
  </div>
	
  <!-- Modal deleteDialog -->
  <div id="deleteReleaseDialog" class="modal hide fade">
    <form class="form-horizontal" name="deleteDialogForm"
      id="deleteDialogForm" novalidate="novalidate" method="post"
      action="${ctx}/manage/member/releaseOper/deleteOper">
      <input type="hidden" name="_method" value="delete" />
	<input type="hidden"  id="delId" name="id" value="" />
	 <input type="hidden" id="rlsColumnOperDel" name="rlsColumnOper" value="" />
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>删除确认</h3>
    </div>
    <div class="modal-body">
      <p>删除代理人信息将不可恢复！</p>
      <p>你确认要删除该代理人发布信息吗？</p>
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
  
  <!-- Modal dlgAddOperRelease -->
  <div id="dlgAddOperRelease" class="modal hide fade">
    <form class="form-horizontal" name="frmSaveOperRelease" id="frmSaveOperRelease" method="get"
      		novalidate="novalidate" action="${ctx}/manage/member/releaseOper/saveOperator">
      <input type="hidden" id="operId" name="operId" value="0" />
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
              style="width: 60px; font-size: 14px;">代理人：</label>
            <div class="controls" style="margin-left: 10px">
              <div class="input-append input-icon-prepend">
                <div class="add-on" style="width: 400px">
                  <a title="search" class="icon"><i class="icofont-search"></i></a>
                  	<input name="operInfo" id="operInfo" type="text"
                  			class="grd-white" style="width: 360px" />
                </div>
                <a href="javascript:void(0);" id="serachOperRelease" class="btn"
                  style="height: 20px; line-heigth: 20px">查询</a>
              </div>
            </div>
          </div>
          <div id="operList"></div>
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
  
   <!-- Modal dlgEditOperRelease -->
  <div id="dlgEditOperRelease" class="modal hide fade">
    <form class="form-horizontal" name="frmEditOperRelease" id="frmEditOperRelease" method="get"
      		novalidate="novalidate" action="${ctx}/manage/member/releaseOper/saveOperator">
      <input type="hidden" id="operIdRelease" name="operId" value="0" />
      <input type="hidden" id="releaseId" name="id" value="0" />
      <input type="hidden" id="rlsColumnReleaseOper" name="rlsColumnRelease" value="" />
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>编辑修改</h3>
      </div>
      <div class="modal-body">
        <fieldset>
	       	<div class="account-avatar">
			    <img id="imageOperLogo" src="" class="thumbnail" />
			 </div>
			 <div style="width: 150px">
			 	<div>
			 	 姓名：<span id="operReleaseName"></span>
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

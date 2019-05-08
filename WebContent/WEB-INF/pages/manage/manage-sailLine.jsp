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
<script src="${ctx}/js/manage/manage-sailLine.js"></script>
<script type="text/javascript">
var _rootPath = "${ctx}";

var _stp = "";
var _edp = "";

<c:if test="${selOrderType.cargoType.cargoBigType ne 'container'}">
var _remark = "${selOrderType.cargoType}^0";
var _description = "${selOrderType.cargoType.shortName}:0";
</c:if>

<c:if test="${selOrderType.cargoType.cargoBigType eq 'container'}">
var _remark = "container20e^0,container20f^0,container40e^0,container40f^0,container45e^0,container45f^0";
var _description = "20英尺吉箱^0,20英尺重箱^0,40英尺吉箱^0,40英尺重箱^0,45英尺吉箱^0,45英尺重箱^0";
</c:if>

$(document).ready(function() {
  $("#startPortCity").trigger("change");
  $("#endPortCity").trigger("change");
});
</script>

<style>
.modal{
	z-index: 1081 !important;
}
</style>
</head>

<body>

  <jsp:include page="./manage-head.jsp"></jsp:include>

  <div id="content">
    <div id="breadcrumb">
      <a href="#" style="font-size: 12px;" class="tip-bottom"> <i
        class="icon-home"></i> 船舶管理
      </a> <a href="#" style="font-size: 12px;" class="current">航线管理</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span12">
			<form novalidate="novalidate" method="post" id="pageform" action="${ctx}/manage/ship/sailLine">
	          <div class="widget-box">
	            <div class="widget-title">
	              <h5>航线列表</h5>
		          <div style="float:left;margin-left:10px;">
		                <div style="float: left; margin-top:8px; margin-left:6px; width:80px; ">船盘分类：</div>
		      			<div style="float: left; margin-top:5px; margin-left:6px;">
						  	<select id="orderType" name="orderType" style="width:200px;">
			                    <c:forEach var="orderType" items="${orderTypes}">
			                        <c:if test="${selOrderType eq orderType}">
			                        	<option value="${orderType}" selected >${orderType.description}</option>
			                        </c:if>
			                        <c:if test="${selOrderType ne orderType}">
			                        	<option value="${orderType}" >${orderType.description}</option>
			                        </c:if>
			                    </c:forEach>
		                    </select>
		                    <input id="keyWords" name="keyWords" type="text" placeholder="请输入港口名称" style="width: 140px;" value="${keyWords}" />
		                    <button type="submit" class="btn btn-primary" id="serachSailLine" style="margin-bottom:8px;line-heigth: 20px">查询</button>
						</div>
		          </div>
		          
		          <div style="float:right; margin-top: 4px; margin-right: 80px;">
		            <a id="btnAdd" class="btn btn-info" idVal="0">
		              <i class="icon-plus icon-white"></i> 添加
		            </a>
		            <a href="javascript:void(0);" id="importExcel"
	                  class="btn btn-info"> <i class="icon-plus icon-white"
	                  title="导入EXCEL"></i>导入
	                </a>
	                <a onclick="window.location.href='${ctx}/manage/ship/exportSailLine?orderType=${selOrderType}';"
	                  id="downLoadExcel" class="btn btn-info"> <i
	                  class="icon-download-alt icon-white" title="导出EXCEL"></i>导出
	                </a>
		          </div>
		          
	            </div>
	            <div class="widget-content nopadding">
	              <table class="table table-bordered data-table">
	                <thead>
	                  <tr>
	                    <th width="15%">装货港</th>
	                    <th width="15%">卸货港</th>
	                    <th width="10%">航程(公里)</th>
	                    <th width="10%">载重(吨)</th>
	                    <th width="30%">参考报价</th>
	                    <th width="20%">操作</th>
	                  </tr>
	                </thead>

	                <tbody>
	                <c:forEach var="sailLineData" items="${pageData.result}">
	                  <tr>
	                    <td>${sailLineData.startPortData.fullName}</td>
	                    <td>${sailLineData.endPortData.fullName}</td>
	                    <td>${sailLineData.distance}</td>
	                    <td>${sailLineData.weight}</td>
	                    <td>${sailLineData.description}</td>
	                    <td>
	                      <a class="btn btn-primary btnEdit" 
	                        idVal="${sailLineData.id}" 
	                        distanceVal="${sailLineData.distance}" 
	                        weightVal="${sailLineData.weight}" 
	                        remarkVal="${sailLineData.remark}" 
	                        descriptionVal="${sailLineData.description}" 
	                        startPortCityVal="${sailLineData.startPortData.portCity.code}" 
	                        endPortCityVal="${sailLineData.endPortData.portCity.code}" 
	                        startPortNoVal="${sailLineData.startPortNo}" 
	                        endPortNoVal="${sailLineData.endPortNo}">
	                        <i class="icon-pencil icon-white"></i> 修改
	                      </a>
	                      <a class="btn btn-danger btnDelete" idVal="${sailLineData.id}">
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
  <div id="editDialog" class="modal hide fade" style="width: 800px;">
    <form class="form-horizontal" name="frmSaveSailLine"
      id="frmSaveSailLine" novalidate="novalidate" method="post" 
      action="${ctx}/manage/ship/sailLine/save">
      <input type="hidden" name="id" id="editId" value="" />
      <input type="hidden" name="pageNo" value="${pageData.pageNo}" />
      <input type="hidden" name="keyWords" value="${keyWords}" />
      <input type="hidden" name="orderType" value="${selOrderType}" />
      <input type="hidden" name="waresBigType" value="${waresBigType}" />
      <input type="hidden" name="waresType" value="${waresType}" />
      <input type="hidden" name="cargoType" value="${cargoType}" />
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>编辑</h3>
    </div>
    <div class="modal-body">

      <div class="control-group">
        <label class="control-label">装货港口：</label>
        <div class="controls">
          <span class="startPortCityName">
            <select id="startPortCity" name="startPortCity" style="width: 120px;">
              <c:forEach var="bigArea" items="${bigAreas}">
                <optgroup label="${bigArea.description}">
                  <c:forEach var="portCity" items="${bigArea.portCities}">
                    <option value="${portCity.code}">${portCity.description}</option>
                  </c:forEach>
                </optgroup>
              </c:forEach>
            </select>
          </span>
          <span id="startPortName">
            <select id="startPortNo" name="startPortNo" style="width: 120px;"></select>
          </span>
          <a href="javascript:void(0);" class="btn btn-danger btnAddPort"><i class="icon-plus icon-white"></i>新增港口</a>
        </div>
        <!-- /controls -->
      </div>
      <!-- /control-group -->

      <div class="control-group">
        <label class="control-label">卸货港口：</label>
        <div class="controls">
          <span class="endPortCityName">
            <select id="endPortCity" name="endPortCity" style="width: 120px;">
              <c:forEach var="bigArea" items="${bigAreas}">
                <optgroup label="${bigArea.description}">
                  <c:forEach var="portCity" items="${bigArea.portCities}">
                    <option value="${portCity.code}">${portCity.description}</option>
                  </c:forEach>
                </optgroup>
              </c:forEach>
            </select>
          </span>
          <span id="endPortName">
            <select id="endPortNo" name="endPortNo" style="width: 120px;"></select>
          </span>
        </div>
        <!-- /controls -->
      </div>
      <!-- /control-group -->

      <div class="control-group">
        <label class="control-label">航程(公里)：</label>
        <div class="controls">
          <input type="text" style="width: 100px;" id="distance" name="distance" value="" />
        </div>
      </div>

      <div class="control-group">
        <label class="control-label">载重量(吨)：</label>
        <div class="controls">
          <input type="text" style="width: 100px;" id="weight" name="weight" value="" />
        </div>
      </div>
      
      <div class="control-group">
        <c:if test="${cargoType.cargoBigType=='container'}">
        <label class="control-label">集装箱规格：</label>
        </c:if>
        <c:if test="${cargoType.cargoBigType!='container'}">
        <label class="control-label">货类：</label>
        </c:if>
        <div class="controls" id="cargoTypeItems">
          <input type="text" style="width: 80px;" name="cargoType" readonly="true" value="" />
        </div>
      </div>
      
      <div class="control-group">
        <c:if test="${cargoType.cargoBigType=='container'}">
        <label class="control-label">报价(元/个)：</label>
        </c:if>
        <c:if test="${cargoType.cargoBigType!='container'}">
        <label class="control-label">报价(元/吨)：</label>
        </c:if>
        <div class="controls" id="priceItems">
          <input type="text" placeholder="请输入数字" style="width: 80px;" name="price" value="" />
        </div>
      </div>

    </div>
    <div class="modal-footer">
      <button class="btn" data-dismiss="modal">
        <i class="icon icon-off"></i> 取消
      </button>
      <a href="javascript:void(0);"
        class="btn btn-primary btnSaveSailLine">
        <i class="icon icon-ok icon-white"></i> 保存
      </a>
      <!-- <button class="btn btn-primary">
        <i class="icon icon-ok icon-white"></i> 保存
      </button> -->
    </div>
    </form>
  </div>

  <!-- Modal importExcelDialog -->
  <div id="importExcelDialog" class="modal hide fade">
    <form class="form-horizontal" name="importExcelDialogForm"
      id="importExcelDialogForm" novalidate="novalidate" method="post"
      action="${ctx}/manage/ship/importSailLine"
      enctype="multipart/form-data">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>从EXCEL文件中导入航线数据</h3>
      </div>
      <div class="modal-body">
        <div class="control-group">
          <label class="control-label">航线EXCEL文件：</label>
          <div class="controls">
            <input name="orderType" type="hidden" value="${selOrderType}" />
            <input name="sailLineExcelFile" placeholder="请选择要导入的航线EXCEL文件" value="" type="file" />
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button class="btn" data-dismiss="modal">
          <i class="icon icon-off"></i> 取消
        </button>
        <button type="submit" class="btn btn-primary">
          <i class="icon icon-ok icon-white"></i> 确认
        </button>
      </div>
    </form>
  </div>

  <!-- Modal deleteDialog -->
  <div id="deleteDialog" class="modal hide fade">
    <form class="form-horizontal" name="frmDeleteSailLine"
      id="frmDeleteSailLine" novalidate="novalidate" method="post"
      action="${ctx}/manage/ship/sailLine/delete">
    <!-- <input type="hidden" name="_method" value="delete" /> -->
      <input type="hidden" name="id" id="delId" value="" />
      <input type="hidden" name="pageNo" value="${pageData.pageNo}" />
      <input type="hidden" name="keyWords" value="${keyWords}" />
      <input type="hidden" name="orderType" value="${selOrderType}" />
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>删除确认</h3>
    </div>
    <div class="modal-body">
      <p>你确认要删除该航线信息吗？</p>
    </div>
    <div class="modal-footer">
      <button class="btn" data-dismiss="modal">
        <i class="icon icon-off"></i> 取消
      </button>
      <a href="javascript:void(0);"
        class="btn btn-primary btnDeleteSailLine">
        <i class="icon icon-trash icon-white"></i> 删除
      </a>
      <!-- <button class="btn btn-primary">
        <i class="icon icon-ok icon-white"></i> 确认
      </button> -->
    </div>
    </form>
  </div>

  <!-- 添加港口对话框 -->
  <div id="addPortDialog" class="modal hide fade">
    <form class="form-horizontal" name="frmNewPortInfo"
      id="frmNewPortInfo" novalidate="novalidate" method="post"
      action="${ctx}/space/ship/port/saveNewPort">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>编辑</h3>
      </div>
      <div class="modal-body">
        <fieldset>

          <div id="selContent">
            <select id="portCityCode" name="portCityCode">
              <c:forEach var="bigArea" items="${bigAreas}">
                <optgroup label="${bigArea.description}">
                  <c:forEach var="portCity" items="${bigArea.portCities}">
                    <option value="${portCity.code}">${portCity.description}</option>
                  </c:forEach>
                </optgroup>
              </c:forEach>
            </select>
          </div>

          <div class="control-group">
            <label class="control-label">港口名称：</label>
            <div class="controls">
              <input type="hidden" name="portNo" id="portNo" value="" /> <input
                type="text" name="portName" id="portName" value="集装箱"
                style="width: 264px;" />
            </div>
          </div>

        </fieldset>
      </div>
      <div class="modal-footer">
        <button class="btn" data-dismiss="modal">
          <i class="icon icon-off"></i> 取消
        </button>
        <a href="javascript:void(0);"
          class="btn btn-primary btnSaveNewPortInfo"> <i
          class="icon icon-ok icon-white"></i> 保存
        </a>
      </div>
    </form>
  </div>

</body>
</html>

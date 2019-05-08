<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css" media="screen"/>
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
<script src="${ctx}/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/unicorn.tables.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/jquery.metadata.js"></script>
<script src="${ctx}/js/jquery.form-2.63.js"></script>
<script src="${ctx}/js/space/space-ship-state-arvlft-edit.js"></script>

<script src="${ctx}/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>

<script type="text/javascript">
	var _saveFlag = true;
	var _rootPath = "${ctx}";
	var goPortNo = "${shipArvlftData.goPortNo}";
	var cargoBigTypes = [] ;
	  <c:forEach var="cargoBigType" items="${cargoBigTypes}">
		  var cargoTypes = [] ;
		  <c:forEach var="cargoType" items="${cargoBigType.cargoTypes}">
		  		cargoTypes.push({
		  			"cargoType" : "${cargoType}",
					"description" : "${cargoType.description}"
		  		});
		  </c:forEach>
		  
		  cargoBigTypes.push({
			"cargoBigType" : "${cargoBigType}",
			"bigTypeDescription" : "${cargoBigType.description}",
			"cargoTypes" : cargoTypes
			});
	  </c:forEach>
	  
	  var bigAreas = [] ;
	  <c:forEach var="bigArea" items="${bigAreas}">
		  var portCities = [] ;
		  <c:forEach var="portCity" items="${bigArea.portCities}">
		  		portCities.push({
		  			"portCityCode" : "${portCity.code}",
					"description" : "${portCity.description}"
		  		});
		  </c:forEach>
		  
		  	bigAreas.push({
				"bigArea" : "${bigArea}",
				"bigAreaDescription" : "${bigArea.description}",
				"portCities" : portCities
			});
	  </c:forEach>
	  
	  var wrapStyles = [] ;
	  <c:forEach var="wrapStyle" items="${wrapStyles}">
	  		wrapStyles.push({
				"wrapStyle" : "${wrapStyle}",
				"description" : "${wrapStyle.description}"
			});
	  </c:forEach>
	
	$(function () {
	    $('.form_arrive_datetime').datetimepicker({
	        //language:  'fr',
	        weekStart: 1,
	        todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 2,
			forceParse: 0,
	        showMeridian: 1
	    });
	    
	    $('.removeArrive').click(function(){
	    	$('#arriveTime').val("");
	    });
	    
	    $('.form_left_datetime').datetimepicker({
	        //language:  'fr',
	        weekStart: 1,
	        todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 2,
			forceParse: 0,
	        showMeridian: 1
	    });
	    
	    $('.removeLeft').click(function(){
	    	$('#leftTime').val("");
	    });
	});

</script>
<style>
#content {
  margin-left: 0px;
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

.detail-descrip{
  width:100%;
  margin-left:50px;
  margin-bottom:20px;
}
.detail-descrip-info{
  width:620px;
}

.detail-descrip-text{
  
}
.form-horizontal .control-group{
  border:0px;
}
.form-horizontal input[type=text], .form-horizontal input[type=password]{
  width:200px
}
.select2-container{
  width:200px
}
</style>

</head>

<body>

  <jsp:include page="./space-head.jsp"></jsp:include>

  <div class="span10">

  <h1 class="page-title">
    <i class="icon-star icon-white"></i> 船舶动态
  </h1>
  <div class="row">

    <div class="span10">

    <div class="widget">

      <div class="widget-header">
      <h3>船舶动态编辑</h3>
      </div>
      <!-- /widget-header -->

      <div class="widget-content">
	      <div class="box-header corner-top">
	        <div class="tab-content">
		        <div class="tab-pane active" id="">
		        	<!-- Default styles -->
		             <form class="form-horizontal" name="frmShipArvlft" id="frmShipArvlft" novalidate="novalidate" 
		              method="post" action="${ctx}/space/state/myShip/shipArvlft/saveShipArvlft">
			        <input type="hidden" name="id" value="${shipArvlftData.id}" />
		            <input type="hidden" id="mmsi" name="mmsi" value="${mmsi}" />
		            <fieldset>
		              <!-- Default styles -->
		              <div class="row-fluid">
		                 <div class="span12">
		                 	<div class="control-group">
				                <label class="control-label" for="shipName">(到达/离开)港口：</label>
				                <div class="controls">
					                  <span class="startPortCityName"> 
					                   <select id="startPortCity" name="startPortCity">
					                    <c:forEach var="bigArea" items="${bigAreas}">
					                      <optgroup label="${bigArea.description}">
					                      <c:forEach var="portCity" items="${bigArea.portCities}">
						                      <c:if test="${shipArvlftData.portData.portCity == portCity }">
						                        <option value="${portCity.code}" selected>${portCity.description}</option>
						                      </c:if>
						                      <c:if test="${shipArvlftData.portData.portCity != portCity }">
						                        <option value="${portCity.code}">${portCity.description}</option>
						                      </c:if>
					                      </c:forEach>
					                      </optgroup>
					                    </c:forEach>
					                    </select>
					                  </span>
					                  <span id="startPortName">
						                  <select id="startPortNo" name="portNo">
						                  	<c:forEach var="arvlftPortData" items="${arvlftPortDatas}">
						                      <c:if test="${shipArvlftData.portNo == arvlftPortData.portNo }">
						                        <option value="${arvlftPortData.portNo}" selected>${arvlftPortData.portName}</option>
						                      </c:if>
						                      <c:if test="${shipArvlftData.portNo != arvlftPortData.portNo }">
						                        <option value="${arvlftPortData.portNo}">${arvlftPortData.portName}</option>
						                      </c:if>
						                    </c:forEach>
						                  </select>
					                  </span>
				                    <a href="javascript:void(0);" class="btn btn-danger btnAddPort">
				                       <i class="icon-plus icon-white"></i>新增港口
				                    </a>
				                </div>
			               </div>
		                 
		                 
			               <div class="control-group">
				                <label class="control-label">到港时间：</label>
				                <div style="margin-left:20px;" class="controls input-append date form_arrive_datetime" data-date="" data-date-format="yyyy-mm-dd hh:ii" data-link-field="dtp_input1">
				                    <input size="16" type="text" id="arriveTime" name="arriveTime" value="${shipArvlftData.arvlftTime}" style="width:190px;">
				                    <span class="add-on"><i class="icon-trash removeArrive"></i></span>
									<span class="add-on"><i class="icon-th"></i></span>
				                </div>
								<input type="hidden" id="dtp_input1" value="" />
				            </div>
				            
				           <div class="control-group">
				                <label class="control-label">离港时间：</label>
				                <div style="margin-left:20px;" class="controls input-append date form_left_datetime" data-date="" data-date-format="yyyy-mm-dd hh:ii" data-link-field="dtp_input2">
				                    <input size="16" type="text" id="leftTime" name="leftTime" value="${leftArvlftData.arvlftTime}" style="width:190px;">
				                    <span class="add-on"><i class="icon-trash removeLeft"></i></span>
									<span class="add-on"><i class="icon-th"></i></span>
				                </div>
								<input type="hidden" id="dtp_input2" value="" />
				           </div>
			               
			               <div class="control-group">
				                <label class="control-label" for="shipName">下一港口：</label>
				                <div class="controls">
					                <span class="endPortCityName">
						                 <select id="endPortCity" name="endPortCity">
						                  <c:forEach var="bigArea" items="${bigAreas}">
						                    <optgroup label="${bigArea.description}">
						                    <c:forEach var="portCity" items="${bigArea.portCities}">
						                          <c:if test="${leftArvlftData.goPortData.portCity == portCity }">
							                        <option value="${portCity.code}" selected>${portCity.description}</option>
							                      </c:if>
							                      <c:if test="${leftArvlftData.goPortData.portCity != portCity }">
							                        <option value="${portCity.code}">${portCity.description}</option>
							                      </c:if>
						                    </c:forEach>
						                    </optgroup>
						                  </c:forEach>
						                 </select>
					                </span>
					                <span id="endPortName">
						                  <select id="endPortNo" name="goPortNo">
						                  		<option value="0" selected>请选择</option>
						                  	<c:forEach var="goPortData" items="${goPortDatas}">
						                        <c:if test="${leftArvlftData.goPortNo == goPortData.portNo }">
						                            <option value="${goPortData.portNo}" selected>${goPortData.portName}</option>
						                      	</c:if>
						                        <c:if test="${leftArvlftData.goPortNo != goPortData.portNo }">
						                            <option value="${goPortData.portNo}">${goPortData.portName}</option>
						                      	</c:if>
						                    </c:forEach>
						                  </select>
					                </span>
					                <a href="javascript:void(0);" class="btn btn-danger btnAddPort">
					                  	<i class="icon-plus icon-white"></i>新增港口
					                </a>
				                </div>
			               </div>
			               
			                <div class="control-group">
				                <label class="control-label" for="">备注：</label>
				                <div class="controls">
				                  <input placeholder="请输入2-50字符" style="width:190px;" type="text" class="input-medium" id = "remark" name="remark" value="${shipArvlftData.remark}" />
				                </div>
			                </div>
			  		     </div>  
		              </div>
		              <!--/Default styles-->
					
		              <div class="form-actions">
		              		<a href="javascript:void(0);" class="btn btn-primary btnSaveShipArvlft">保存</a>
                  			<a href="javascript:window.history.go(-1);" class="btn btn-warning">返回</a>
		              </div>
		              <!-- /form-actions -->
		
		            </fieldset>
		            </form>
		              
		        </div>
	        </div>
          </div>
        </div>
        <!-- /widget-content -->

        </div>
        <!-- /widget -->

      </div>
      <!-- /span9 -->

      </div>
      <!-- /row -->

    </div>
    <!-- /span9 -->
    </div>
    <!-- /row -->

  </div>
  <!-- /container -->

  </div>
  <!-- /content -->

  <jsp:include page="./space-foot.jsp"></jsp:include>
  
  <!-- Modal editDialog -->
  <div id="addPortDialog" class="modal hide fade">
  <form class="form-horizontal" name="frmNewPortInfo" id="frmNewPortInfo" novalidate="novalidate" 
    method="post" action="${ctx}/space/ship/port/saveNewPort" >
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
      <input type="hidden" name="portNo" id="portNo" value="" />
      <input type="text" name="portName" id="portName" value="集装箱" style="width:264px;" />
      </div>
    </div>

    </fieldset>
  </div>
  <div class="modal-footer">
    <button class="btn" data-dismiss="modal">
    <i class="icon icon-off"></i> 取消
    </button>
    <a href="javascript:void(0);" class="btn btn-primary btnSaveNewPortInfo">
    <i class="icon icon-ok icon-white"></i> 保存
    </a>
  </div>
  </form>
  </div>
</body>
</html>

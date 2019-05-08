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
<script src="${ctx}/js/jquery.metadata.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/jquery.form-2.63.js"></script>
<script src="${ctx}/js/space/space-ship-state-arvlft-updown.js"></script>

<script type="text/javascript">
	var _rootPath = "${ctx}";
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
		             <form class="form-horizontal" name="frmUpDownShipArvlft" id="frmUpDownShipArvlft" novalidate="novalidate" 
		              method="post" action="${ctx}/space/state/myShip/saveUpdown">
		            <input type="hidden" name="id" value="${updownArvlftData.id}" />
		            <input type="hidden" id="mmsi" name="mmsi" value="${mmsi}" />
					<%-- 
					<input type="hidden" style="width:80px;" name="wrapStyle" value="${shipUpdownData.wrapStyle}" />
				  	<input type="hidden" style="width: 80px" name="wrapCount" value="${shipUpdownData.wrapCount}" />
				    <input type="hidden" style="width:80px;" name="unitWeight" value="${shipUpdownData.unitWeight}" />
				    <input type="hidden" style="width:80px;" name="fullWeight" value="${shipUpdownData.fullWeight}" />
				    <input type="hidden" style="width:110px;" name="ctlLength" value="${shipUpdownData.ctlLength}" />
				    <input type="hidden" style="width:110px;" name="ctlWidth" value="${shipUpdownData.ctlWidth}" />
				    <input type="hidden" style="width:80px;" name="ctlHeight" value="${shipUpdownData.ctlHeight}" />
				    <input type="hidden" style="width:80px;" name="ctlVolume" value="${shipUpdownData.ctlVolume}" /> 
				    --%>				    
		            <fieldset>
		              <!-- Default styles -->
		              <div class="row-fluid">
		              <div class="span12">
		                 
						<div class="upCargos" style="margin-left:80px;width:70px;">
						  <label><a href="javascript:void(0);" class="btn btn-primary">
							<c:if test="${updownArvlftData.arvlft == 'left'}">
			                  <i class="icon-plus icon-white"></i>装货
							</c:if>
							<c:if test="${updownArvlftData.arvlft != 'left'}">
			                  <i class="icon-plus icon-white"></i>卸货
							</c:if>
		                  </a></label>
						</div>
						
		              <div class="upDownCargos">
		                <c:forEach var="shipUpdownData" items="${updownArvlftData.shipUpdownDatas}">
		                <div class="oneCargo">
						  <div class="control-group">
			                <label class="control-label" for="cargoType">货类:</label>
			                  <div class="controls">
				                <span>
					              <select class="cargoType" name="cargoType" style="width:160px;">
					                <c:forEach var="cargoBigType" items="${cargoBigTypes}">
					                  <optgroup label="${cargoBigType.description}">
					                    <c:forEach var="cargoType" items="${cargoBigType.cargoTypes}">
					                      <c:if test="${shipUpdownData.cargoType == cargoType}">
					                        <option value="${cargoType}" selected>${cargoType.description}</option>
					                      </c:if>
					                      <c:if test="${shipUpdownData.cargoType != cargoType}">
					                        <option value="${cargoType}">${cargoType.description}</option>
					                      </c:if>
					                    </c:forEach>
					                  </optgroup>
					                </c:forEach>
					              </select>
				                </span>
				                <c:if test="${shipUpdownData.cargoType.cargoBigType == 'container'}">
			                	  <span>
				                 	 规格:&nbsp;&nbsp;<input placeholder="请输入货名" style="width:125px;" type="text" name="cargoName" value="${shipUpdownData.cargoName}" />
			                	  </span>
			                	  <span>
				                 	货量(个):&nbsp;&nbsp;<input placeholder="请输入货名" style="width:125px;" type="text" name="tonTeu" value="${shipUpdownData.tonTeu}" />
		                	      </span>
				                </c:if>
				                <c:if test="${shipUpdownData.cargoType.cargoBigType != 'container'}">
			                	  <span>
				                 	 货名:&nbsp;&nbsp;<input placeholder="请输入货名" style="width:125px;" type="text" name="cargoName" value="${shipUpdownData.cargoName}" />
			                	  </span>
			                      <span>
				                 	货量(吨):&nbsp;&nbsp;<input placeholder="请输入货名" style="width:125px;" type="text" name="tonTeu" value="${shipUpdownData.tonTeu}" />
		                	      </span>
			                    </c:if>
			                    <span>
		                		  <a href="javascript:void(0);" class="btn btn-danger delCargos">
			                        <i class="icon-trash icon-white"></i>删除
			                	  </a>
	                	  	    </span>
			                  </div>
			                </div>
			                
			                <div style="width:720px;margin-left:130px;margin-top:10px;" class="divider-content">
					          <span></span>
					        </div>
		                  </div>
		                </c:forEach>
		              </div>
		            </div>  
		          <!-- /span -->
		          </div>
		          <!--/Default styles-->
					
	              <div class="form-actions">
              		<a href="javascript:void(0);" class="btn btn-primary btnSaveUpDownShipArvlft">保存</a>
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
  

</body>
</html>

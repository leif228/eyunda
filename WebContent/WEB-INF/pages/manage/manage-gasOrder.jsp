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
<link rel="stylesheet" href="${ctx}/css/bootstrap-datetimepicker.min.css" media="screen"/>

<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/manage/manage-gasOrder.js"></script>
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
        class="icon-home"></i> 订单管理
      </a> <a href="#" style="font-size: 12px;" class="current">订单查询</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span12">
          <form name="pageform" id="pageform" action="${ctx}/manage/gas/gasOrder" method="post">
            <div class="widget-box">
              <div class="widget-title">
                <h5>订单列表</h5>
                  <div class="controls" style="float: left; margin-top: 3px;">
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
                
					<div class="controls" style="float: left; margin-top: 3px; margin-left: 8px;">
						<select id="selectCode" name="selectCode" style="width: 120px;">
		                      	<option value="" selected>全部订单状态</option>
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
                  
	              <div class="control-group data-center" style="float:left;width:190px;">
	                <div style="margin-left:10px;" class="controls input-append date form_datetimeStart" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input1">
	            	  <input size="16" type="text" name="startTime" id="startTime"  style="width:120px;margin-bottom:0px;" value="${startTime}" placeholder="请输入起始时间!"/>            	
	                  <span class="add-on"><i class="icon-trash removeStartTime"></i></span>
					  <span class="add-on"><i class="icon-th"></i></span>
	                </div>
					<input type="hidden" id="dtp_input1" value="" />
		          </div>
	          
		          <div class="control-group data-center" style="float:left;width:190px;margin-left:8px;">
	                <div style="margin-left:10px;" class="controls input-append date form_datetimeEnd" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2">
	            	  <input size="16" type="text" name="endTime" id="endTime"  style="width:120px;margin-bottom:0px;" value="${endTime}" placeholder="请输入终止时间!"/>            	
	                  <span class="add-on"><i class="icon-trash removeEndTime"></i></span>
					  <span class="add-on"><i class="icon-th"></i></span>
	                </div>
				  <input type="hidden" id="dtp_input2" value="" />
		        </div>
                
                <div style="float: left; margin-left: 16px;">
                  <a title="search" class="icon"><i class="icofont-search"></i></a>
                  <input name="keyWords" id="keyWords" type="text" class="grd-white" 
                  	value="${keyWords}" style="margin-top: 3px; width: 120px" placeholder="请输入加油站名称" />
                  <button type="submit" class="btn btn-primary btnSerach" 
                  	style="margin-bottom: 8px; line-heigth: 20px">查询</button>
                </div>
              </div>

              <div class="widget-content nopadding" style="margin-top: -10px;">
                <table class="table table-bordered data-table">
                  <thead>
	                <tr>
	                  <th>订单号</th>
	                  <th>买家</th>
	                  <!-- <th>船舶名称</th>
	                  <th>船舶MMSI</th> -->
	                  <th>卖家</th>
					  <th>商品</th>
	                  <th>购买数量</th>
	                  <th>交易价格(元)</th>
	                  <th>交易金额(元)</th>
	                  <th>购买日期</th>
	                  <!-- <th>加油站</th>
	                  <th>加油日期</th> -->
	                  <th>状态</th>
	                  <th>操作</th>
	                </tr>
                  </thead>
                  <tbody>
                    <c:forEach items="${pageData.result}" var="gasOrderData">
                    <tr>
                      <td>${gasOrderData.id}</td>
	                  <td>${gasOrderData.carrier.trueName}</td>
	                  <%-- <td>${gasOrderData.ship.shipName}</td>
	                  <td>${gasOrderData.ship.mmsi}</td> --%>
                      <td>${gasOrderData.company.companyName}</td>
                      <td>
                        <img src="${ctx}/download/imageDownload?url=${gasOrderData.gasWaresData.waresLogo}" 
                      	  alt="" class="thumbnail" style="margin:0px auto; width: 60px; height: 60px;" />
                        <p style="text-align:center;">${gasOrderData.gasWaresData.waresName}</p>
                      </td>
	                  <td align="right">${gasOrderData.saleCount}</td>
	                  <td align="right">${gasOrderData.price}</td>
	                  <td align="right">${gasOrderData.tradeMoney}</td>
	                  <td>${gasOrderData.orderTime}</td>
	                  <%-- <td>${gasOrderData.stationName}</td> --%>
	                  <%-- <td>${gasOrderData.gasTime}</td> --%>
	                  <td id="desc${gasOrderData.id}">${gasOrderData.status.description}</td>
	                  <td>
	                    <div id="button${gasOrderData.id}">
		                    <c:if test="${gasOrderData.status == 'refundapply'}">
		                      <a class="btn btn-danger btnBackMoney" idVal="${gasOrderData.id}">
							    <i class="icon-chevron-down icon-white"></i> 退款
					   	      </a>
		                    </c:if>
	                   	    <c:if test="${gasOrderData.status == 'payment'}">
		                      <a class="btn btn-warning btnIsAddOil" idVal="${gasOrderData.id}">
							    <i class="icon-chevron-up icon-white"></i> 交易完成
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
  
   <!-- Modal publishDialog -->
  <div id="backMoneyDialog" class="modal hide fade">
  	<input type="hidden" name="id" id="backId" value="" />
	<input type="hidden" name="keyWords" value="${keyWords}" />
	<input type="hidden" name="pageNo" value="${pageNo}" />
	
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">×</button>
		<h3>退款确认</h3>
	</div>
	<div class="modal-body">
		<p>退款之前请先确认订单是否已申请退款！</p>
	</div>
	<div class="modal-footer">
		<button class="btn" data-dismiss="modal">
			<i class="icon icon-off"></i> 取消退款
		</button>
		<button class="btn btn-primary backMoney">
			<i class="icon icon-ok icon-white"></i> 确认退款
		</button>
	</div>
  </div>
  
   <!-- Modal unpblishDialog -->
  <div id="isAddOilDialog" class="modal hide fade">
  	<input type="hidden" name="id" id="isAddOilId" value="" />
  	<input type="hidden" name="keyWords" value="${keyWords}" />
	<input type="hidden" name="pageNo" value="${pageNo}" />
	
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">×</button>
		<h3>加油确认</h3>
	</div>
	<div class="modal-body">
		<p>点击加油确认前请先确认该订单是否已经加油，确认后不可更改！</p>
	</div>
	<div class="modal-footer">
		<button class="btn" data-dismiss="modal">
			<i class="icon icon-off"></i> 取消
		</button>
		<button class="btn btn-primary isAddOil">
			<i class="icon icon-ok icon-white"></i> 确认
		</button>
	</div>
  </div>

  <!-- Modal deleteDialog -->
  <div id="deleteDialog" class="modal hide fade">
  	<input type="hidden" name="id" id="deleteId" value="" />
    <input type="hidden" id="delKeyWords" value="${keyWords}" />
	<input type="hidden" id="delPageNo" value="${pageNo}" />
	
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">×</button>
		<h3>删除确认</h3>
	</div>
	<div class="modal-body">
		<p>你确认要删除该油品信息吗？</p>
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

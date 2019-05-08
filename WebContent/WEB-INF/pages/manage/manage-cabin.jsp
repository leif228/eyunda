<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<link rel="stylesheet"
  href="${ctx}/css/bootstrap-datetimepicker.min.css" media="screen" />

<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.zh-CN.js"
  charset="UTF-8"></script>
<script src="${ctx}/js/jquery.form-2.63.js"></script>
<script src="${ctx}/js/manage/manage-cabin.js"></script>
<script type="text/javascript">
  var _rootPath = "${ctx}";

  $(document).ready(function() {
    var date = new Date();
    $("#datetimeStart").datetimepicker({
      format : 'yyyy-mm-dd',
      minView : 'month',
      language : 'zh-CN',
      autoclose : true
    }).on("changeDate", function(ev) {
      var startTime = $("#startDate").val();
      $("#datetimeEnd").datetimepicker("setStartDate", startTime);
    });
    $("#datetimeEnd").datetimepicker({
      format : 'yyyy-mm-dd',
      minView : 'month',
      language : 'zh-CN',
      autoclose : true,
      startDate : date
    }).on("changeDate", function(ev) {
      var endTime = $("#endDate").val();
      $("#datetimeStart").datetimepicker("setEndDate", endTime);
    });

    $('#removeStartTime').click(function() {
      $('#startDate').val("");
      $('#datetimeEnd').datetimepicker('setStartDate');
    });

    $('#removeEndTime').click(function() {
      $('#endDate').val("");
      $('#datetimeStart').datetimepicker('setEndDate');
    });

    $("#datetimeEnd").on("change", function(e) {
      var start = $('#startDate').val();
      var end = $('#endDate').val();
      if (start > end) {
        $('#endDate').val(start);
        $('#datetimeEnd').datetimepicker('update');
        return false;
      }
    });
  });
</script>
<style type="text/css">
#recipients li {
  list-style-type: none;
}

.demo1 {
  margin-top: 15px;
  border-bottom-width: 1px;
  border-bottom-color: #eeeeee;
  border-bottom-style: solid;
  margin-left: 15px;
  margin-right: 15px;
  padding-bottom: 15px;
}

.recipientsDiv {
  margin-left: 90px;
  border-left-width: 1px;
  border-left-style: solid;
  border-top-width: 1px;
  border-top-style: solid;
  border-bottom-width: 1px;
  border-color: activecaption;
  border-bottom-style: solid;
  border-right-width: 1px;
  border-right-style: solid;
  padding-top: 10px;
  overflow-y: auto;
  height: 150px;
}

#money, #explain {
  padding-bottom: 0px;
  padding-top: 0px;
}

#publishers {
  float: right;
  margin-right: 5px;
  margin-top: 3px;
}

#pageSize {
  margin-top: 3px;
}

#s2id_pageSize {
  margin-top: 3px;
}
.container-fluid .row-fluid:first-child{
	margin-top:0px;
}
</style>
</head>
<body>

  <jsp:include page="./manage-head.jsp"></jsp:include>

  <div id="content">
    <div id="breadcrumb">
      <a href="#" style="font-size: 12px;" class="tip-bottom"> <i
        class="icon-home"></i> 船盘管理
      </a> <a href="#" style="font-size: 12px;" class="current">船盘查询</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span12">
          <form name="pageform" id="pageform"
            action="${ctx}/manage/ship/manageCabin">
            <div class="widget-box">
              <div class="widget-title">
                <h5>船盘列表</h5>
                <div>
                  <a title="search" class="icon"><i class="icofont-search"></i></a>
                  <input type="hidden" name="pageNo" id="pageNo"
                    value="${pageData.pageNo}" /> <input name="keyWords"
                    id="keyWords" type="text" class="grd-white"
                    value="${keyWords}"
                    style="margin-top: 3px; width: 100px; margin-right: 15px;"
                    placeholder="船名或MMSI" /> 从 <span
                    class="input-append date" id="datetimeStart" data-date=""
                    data-date-format="yyyy-mm-dd"> <input id="startDate"
                    name="startDate" class="grd-white"
                    style="width: 80px; border: 1px solid #cccccc; margin-top: 3px;"
                    size="10" type="text" value="${startDate }" placeholder="起始日期" />
                    <span class="add-on" id="removeStartTime"
                    style="margin-bottom: 9px; margin-top: 3px;"> <i
                      class="icon-remove"></i>
                  </span> <span class="add-on"
                    style="margin-bottom: 9px; margin-top: 3px; margin-right: 3px;">
                      <i class="icon-th"></i>
                  </span>
                  </span> 到 <span class="input-append date" id="datetimeEnd"
                    data-date="" data-date-format="yyyy-mm-dd"> <input
                    id="endDate" name="endDate" class="grd-white"
                    style="width: 80px; border: 1px solid #cccccc; margin-top: 3px;"
                    size="10" type="text" value="${endDate }" placeholder="终止日期" />
                    <span class="add-on" id="removeEndTime"
                    style="margin-bottom: 9px; margin-top: 3px;"> <i
                      class="icon-remove"></i>
                  </span> <span class="add-on"
                    style="margin-bottom: 9px; margin-top: 3px; margin-right: 15px;">
                      <i class="icon-th"></i>
                  </span>
                  </span> 每页 <select id="pageSize" name="pageSize">
                    <option value="10" <c:if test="${pageData.pageSize==10}">selected</c:if> >10</option>
                    <option value="20" <c:if test="${pageData.pageSize==20}">selected</c:if> >20</option>
                    <option value="50" <c:if test="${pageData.pageSize==50}">selected</c:if> >50</option>
                    <option value="100" <c:if test="${pageData.pageSize==100}">selected</c:if> >100</option>
                    <option value="200" <c:if test="${pageData.pageSize==200}">selected</c:if> >200</option>
                    <option value="500" <c:if test="${pageData.pageSize==500}">selected</c:if> >500</option>
                  </select>行
                  <button type="submit" class="btn btn-primary"
                    id="btnSerachShip"
                    style="margin-bottom: 8px; line-heigth: 20px">查询</button>
                </div>
              </div>
              
              <div class="widget-content nopadding">
              <c:if test="${flag}"> ${msg} </c:if>
              <c:if test="${empty flag || !flag}">
                <table class="table table-bordered data-table table-striped">
                  <thead>
                    <tr>
                      <th style="width: 18%;">船舶</th>
                      <th style="width: 66%;">报价</th>
                      <th style="width: 8%;">状态</th>
                      <th style="width: 8%;">操作</th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:forEach items="${pageData.result}" var="cabinData">
                      <tr>
                        <td>
                          <div><a href="${ctx}/portal/home/cabinInfo?cabinId=${cabinData.id}"
                          target="_blank"> <c:choose>
                              <c:when test="${!empty cabinData.shipData.shipLogo}">
                                <img
                                  src="${ctx}/download/imageDownload?url=${cabinData.shipData.shipLogo}"
                                  style="width: 80px; height: 60px;" alt=""
                                  class="thumbnail" />
                              </c:when>
                              <c:otherwise>
                                <img
                                  src="${ctx}/img/shipImage/${cabinData.shipData.shipType}.jpg"
                                  style="width: 80px; height: 60px;" alt=""
                                  class="thumbnail" />
                              </c:otherwise>
                            </c:choose>
                          </a></div>
                          <div>
                          ${cabinData.shipData.shipName}<br />
                          ${cabinData.description}<br />
                          <%-- <c:if test="${cabinData.waresBigType eq 'voyage' }">
                          承运人:${cabinData.shipData.master.trueName}<br />
                          </c:if>
                          <c:if test="${cabinData.waresBigType eq 'daily' }">
                          出租人:${cabinData.shipData.master.trueName}<br />
                          </c:if> --%>
                          </div>
                        </td>
                        <td>
                        <section class="content-current">
                      <div class="mediabox">
                      <div class="one-line">
			                <div class="row-fluid">
			                    <c:if test="${cabinData.waresBigType eq 'voyage' }"> <!-- 航租 -->
								<div class="one-line-info span12 fa-pull-right">
				                    <div class="one-line-title row-fluid">
				                        <div class="span10 adj-height-40" style="line-height:30px;">
				                        <a href="${ctx }/portal/home/cabinInfo?cabinId=${cabinData.id}&selPortNos=${cabinData.currSailLineData.sailLineNo}">
				                         <span style="color:#1c9b9a;font-weight:bold">${cabinData.currSailLineData.startPortData.fullName}</span><img width="61" height="14" class="startimg" src="${ctx}/img/indImg.png"><span style="color:#1c9b9a;font-weight:bold">${cabinData.currSailLineData.endPortData.fullName},里程:${cabinData.currSailLineData.distance}公里,载重量:${cabinData.currSailLineData.weight}吨</span>
				                        </a>
				                        </div>
				                    </div>
									<div class="row-fluid" style="line-height:40px">
										<span class="span4">
										<c:if test="${cabinData.waresType != 'nmszh'}">
				                        滞期费:${cabinData.demurrage}元/船.天
			                            </c:if>
			                            <c:if test="${cabinData.waresType == 'nmszh'}">
				                        滞期费:${cabinData.demurrage}元/吨.天
				                        </c:if>
										</span>
										<span class="span4" style="text-align:center">
										<c:if test="${cabinData.waresType eq 'nmszh' }"> 
										载货量：${cabinData.currSailLineData.weight}吨
										</c:if>
										<c:if test="${cabinData.waresType eq 'gaxjzx' }"> 
										载箱量：${cabinData.containerCount}TEU
										</c:if>
										<c:if test="${cabinData.waresType eq 'nmjzx' }"> 
										载箱量：${cabinData.containerCount}TEU
										</c:if>
										</span>
										<span class="span4" style="text-align:right">受载日期：${cabinData.startDate}</span>
									</div>
									<div class="row-fluid">
			                          <c:choose>
			                            <c:when test="${cabinData.waresType eq 'nmszh' }">
			                              <span class="span12" style="color:red;font-weight:bold">航租报价：
												<c:forEach varStatus="status" var="mapPrice" items="${cabinData.currSailLineData.mapPrices}">
												${mapPrice.value}元／吨
												</c:forEach>	
										  </span>
			                            </c:when>
			                            <c:otherwise>
			                             <table style="width:100%;color:red;border:1px solid #dddddd">
											<tr>
												<td rowspan="2" style="font-weight:bold">报价<br/>元/个</td>
												<c:forEach varStatus="status" var="containerCode" items="${containerCodes}">
												<td>${containerCode.shortName}</td>
												</c:forEach>	
											</tr>
											<tr>
												<c:forEach varStatus="status" var="mapPrice" items="${cabinData.currSailLineData.mapPrices}">
												<td>${mapPrice.value}</td>
												</c:forEach>	
											</tr>
										</table>
			                            </c:otherwise>
			                          </c:choose>
									</div> 
			                    </div>
			                    </c:if>
	
			                    <c:if test="${cabinData.waresBigType eq 'daily' }"><!-- 期租 -->
								<div class="one-line-info span12 fa-pull-right">
				                    <div class="one-line-title row-fluid">
				                        <div class="span10 adj-height-40" style="line-height:30px;">
				                        <a href="${ctx }/portal/home/cabinInfo?cabinId=${cabinData.id}&selPortNos=">
				                        	<span class="span12" style="color:#1c9b9a;font-weight:bold;line-height:40px">
				                        	<c:set var="_count" value="0" />
											<c:forEach items="${cabinData.upDownPortDatas}" var="upDownPortData" varStatus="status">
					                            <c:if test="${upDownPortData.gotoThisPort && _count < 2}">
					                            <c:set var="_count" value="${_count+1}" />
					                            ${upDownPortData.startPortData.fullName} : 载重量${upDownPortData.weight}吨,
					                            </c:if>
					                        </c:forEach>
					                        ......
											</span>
				                        </a>
				                        </div>
				                    </div>
				                   
									<div class="row-fluid" style="line-height:40px">
									  <c:choose>
			                            <c:when test="${cabinData.waresType eq 'nmszh' }">
										<span class="span6">燃油费：${cabinData.oilPrice}元/公里</span>
										<span class="span6" style="text-align:right">受载日期：${cabinData.startDate}</span>
			                            </c:when>
			                            <c:otherwise>
			                            <span class="span4">载箱量:${cabinData.containerCount}TEU</span>
										<span class="span4" style="text-align:center">燃油费：${cabinData.oilPrice}元/公里</span>
										<span class="span4" style="text-align:right">受载日期：${cabinData.startDate}</span>
			                            </c:otherwise>
			                          </c:choose>
									 </div>
									 <div class="row-fluid" > <span style="color:red;font-weight:bold">日租报价:${cabinData.prices}元/天</span></div>
			                    </div>
			                    </c:if>
			                </div>
			              </div>
			              <div class="clear"></div>
                      </div>
                  </section>
                        </td>
                        <%-- <td><c:if test="${cabinData.waresBigType == 'voyage'}">
                            <p>
                              <c:if test="${cabinData.waresType != 'nmszh'}">
                          载箱量:${cabinData.containerCount}TEU&nbsp;&nbsp;
                          滞期费:${cabinData.demurrage}元/船.天&nbsp;&nbsp;
                            </c:if>
                              <c:if test="${cabinData.waresType == 'nmszh'}">
                          滞期费:${cabinData.demurrage}元/吨.天&nbsp;&nbsp;
                          </c:if>
                              受载日期:${cabinData.startDate}
                            </p>
                            <p>
                              <c:forEach items="${cabinData.sailLineDatas}"
                                var="sailLineData">
                                <c:if test="${sailLineData.gotoThisLine}">
                            ${sailLineData.description}<br />
                                </c:if>
                              </c:forEach>
                            </p>
                          </c:if> <c:if test="${cabinData.waresBigType == 'daily'}">
                            <p>
                              <c:if test="${cabinData.waresType != 'nmszh'}">
                          载箱量:${cabinData.containerCount}TEU&nbsp;&nbsp;
                          </c:if>
                              租金率:${cabinData.prices}元/天&nbsp;&nbsp;
                              燃油费:${cabinData.oilPrice}元/公里&nbsp;&nbsp;
                              受载日期:${cabinData.startDate}
                            </p>
                            <p>
                              <c:forEach items="${cabinData.upDownPortDatas}"
                                var="upDownPortData">
                                <c:if test="${upDownPortData.gotoThisPort}">
                            ${upDownPortData.startPortData.fullName} : 载重量${upDownPortData.weight}吨<br />
                                </c:if>
                              </c:forEach>
                            </p>
                          </c:if></td> --%>
                        <td>${cabinData.status.description}</td>
                        <td><a class="btn btn-danger btnDeleteCabin"
                          idVal="${cabinData.id}"> <i
                            class="icon icon-trash icon-white" title="删除"></i>
                        </a></td>
                      </tr>
                    </c:forEach>
                  </tbody>
                </table>
              </c:if>
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
  <div id="deleteCabinDialog" class="modal hide fade">
    <form class="form-horizontal" name="deleteCabinDialogForm"
      id="deleteCabinDialogForm" novalidate="novalidate" method="post"
      action="${ctx}/manage/ship/manageCabin/deleteCabin">
      <input type="hidden" name="_method" value="delete" /> <input
        type="hidden" name="id" id="delCabinId" value="" /> <input
        type="hidden" name="keyWords" id="searchKeyWords" value="" /> <input
        type="hidden" name="pageNo" id="delPageNo" value="" />
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>删除确认</h3>
      </div>
      <div class="modal-body">
        <p>删除船盘信息将不可恢复！</p>
        <p>你确认要删除该船盘信息吗？</p>
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

  <!-- 发送红包对话框  -->
  <div id="giveRedPacketsDal" class="modal hide fade"
    style="width: 700px;">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">x</button>
      <h3 id="model-title">批量发红包</h3>
    </div>
    <form class="form-horizontal" name="giveRedPackets"
      id="giveRedPackets" method="post"
      action="${ctx}/manage/ship/batchCreate" novalidate="novalidate">
      <div class="demo1">
        <span style="float: left;">红包接收人：</span>
        <div class="recipientsDiv">
          <ul id="recipients">
          </ul>
        </div>
      </div>
      <div class="demo1">
        <span style="float: left;">每个红包金额（元）：</span>
        <div>
          <input id="money" name="money" value="0.0" type="text"
            style="width: 30px;">
        </div>
      </div>
      <div class="demo1">
        <span style="float: left;">红包发送说明：</span>
        <div>
          <input id="explain" name="remark" type="text" value=""
            style="width: 200px;">
        </div>
      </div>
    </form>
    <div class="modal-footer">
      <a href="#" class="btn" data-dismiss="modal">关闭</a>
      <button class="btn btn-primary" id="giveRedPacketsBtn"
        data-dismiss="modal">提交</button>
    </div>
  </div>

</body>
</html>

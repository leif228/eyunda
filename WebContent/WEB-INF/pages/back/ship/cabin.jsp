<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title> - FooTable</title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link rel="shortcut icon" href="favicon.ico">
    <link href="${ctx}/hyqback/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${ctx}/hyqback/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="${ctx}/hyqback/css/plugins/footable/footable.core.css" rel="stylesheet">

    <link href="${ctx}/hyqback/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">

    <link href="${ctx}/hyqback/css/animate.css" rel="stylesheet">
    <link href="${ctx}/hyqback/css/style.css?v=4.1.0" rel="stylesheet">
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>船盘管理 > 船盘查询</h5>
					</div>
					<div class="ibox-content">
						<div class="form-group">
                        	 <label class="col-sm-2 control-label">登陆日志列表：</label>
                                        <div class="col-sm-10">
                                            <input placeholder="开始日期" class="form-control layer-date" id="start">
                                            <input placeholder="结束日期" class="form-control layer-date" id="end">
						<input type="text" class="form-control input-sm m-b-xs"
							id="filter" placeholder="船名或MMSI">
						 <button type="button" class="btn btn-primary btn-sm">查询</button>
                                        </div>
                         </div>
						<table class="footable table table-stripped" data-page-size="20"
							data-filter=#filter>
							
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
			                          </div>
			                        </td>
			                        <td>
			                        <section class="content-current">
			                      <div class="mediabox">
			                      <div class="one-line">
						                <div class="row-fluid">
						                    <c:if test="${cabinData.waresBigType eq 'voyage' }"> <!-- 航租 -->
											<div class="one-line-info span12 fa-pull-left">
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
											<div class="one-line-info span12 fa-pull-left">
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
			                        <td>${cabinData.status.description}</td>
			                        <td>
											<button type="button" class="btn btn-danger btn-xs btnDelete" idVal="${adminData.id}">删除</button>
										</td>
			                      </tr>
			                    </c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="5">
										<ul class="pagination pull-right"></ul>
									</td>
								</tr>
							</tfoot>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 全局js -->
	<script src="${ctx}/hyqback/js/jquery.min.js?v=2.1.4"></script>
	<script src="${ctx}/hyqback/js/bootstrap.min.js?v=3.3.6"></script>
	<script src="${ctx}/hyqback/js/plugins/footable/footable.all.min.js"></script>
    <script src="${ctx}/hyqback/js/plugins/sweetalert/sweetalert.min.js"></script>
	<!-- jQuery Validation plugin javascript-->
	<script src="${ctx}/hyqback/js/plugins/validate/jquery.validate.min.js"></script>
	<script src="${ctx}/hyqback/js/plugins/validate/messages_zh.min.js"></script>
	<!-- layerDate plugin javascript -->
    <script src="${ctx}/hyqback/js/plugins/layer/laydate/laydate.js"></script>

    <script>
  //日期范围限制
    var start = {
        elem: '#start',
        format: 'YYYY/MM/DD hh:mm:ss',
        min: laydate.now(), //设定最小日期为当前日期
        max: '2099-06-16 23:59:59', //最大日期
        istime: true,
        istoday: false,
        choose: function (datas) {
            end.min = datas; //开始日选好后，重置结束日的最小日期
            end.start = datas //将结束日的初始值设定为开始日
        }
    };
    var end = {
        elem: '#end',
        format: 'YYYY/MM/DD hh:mm:ss',
        min: laydate.now(),
        max: '2099-06-16 23:59:59',
        istime: true,
        istoday: false,
        choose: function (datas) {
            start.max = datas; //结束日选好后，重置开始日的最大日期
        }
    };
    laydate(start);
    laydate(end);
 
    $(document).ready(function() {

            $('.footable').footable();
         // Form Validation
            $("#editDialogForm").validate({
        		rules:{
        			attrName:{
        				required:true
        			}
        		},
        		errorClass: "help-inline",
        		errorElement: "span",
        		highlight:function(element, errorClass, validClass) {
        			$(element).parents('.control-group').addClass('error');
        		},
        		unhighlight: function(element, errorClass, validClass) {
        			$(element).parents('.control-group').removeClass('error');
        			$(element).parents('.control-group').addClass('success');
        		}
        	});
			
         // 添加
         $(document).on("click", "#btnAdd", function() {
        		$.ajax({
        			method : "GET",
        			data : {para : 1},
        			url : "${ctx}/back/ship/attribute/add",
        			datatype : "json",
        			success : function(data) {
        				var returnCode = $(data)[0].returnCode;
        				var message = $(data)[0].message;
        				if (returnCode == "Failure") {
        					alert(message);
        					return false;
        				} else {
        					//读入数据，并填入
        					var s = "";
        					s += "		<div class=\"modal-body\">";
        					s += "			<fieldset>";
        					s += "";
        					s += "				<div class=\"control-group\">";
        					s += "					<label class=\"control-label\">类别：</label>";
        					s += "					<div class=\"controls\">";
        			        s += "						<input type=\"hidden\" name=\"id\" id=\"attributeId\" value=\"0\" />";
        					s += "						<select id=\"typeCode\" name=\"typeCode\" style=\"width: 280px;\">";

        			        $.each($(data)[0].uncleDatas, function(i, uncleData) {
        				        s += "							<optgroup label=\""+uncleData.typeName+"\">";
        				        $.each(uncleData.childrenDatas, function(i, childData) {
        				        s += "								<option value=\""+childData.typeCode+"\">"+childData.typeName+"</option>";
        				        });
        				        s += "							</optgroup>";
        			        });
        					
        					s += "						</select>";
        					s += "					</div>";
        					s += "				</div>";
        					s += "";
        					s += "				<div class=\"control-group\">";
        					s += "					<label class=\"control-label\">属性名称：</label>";
        					s += "					<div class=\"controls\">";
        					s += "						<input type=\"text\" name=\"attrName\" id=\"attrName\" value=\"\" />";
        					s += "					</div>";
        					s += "				</div>";
        					s += "";
        					s += "				<div class=\"control-group\">";
        					s += "					<label class=\"control-label\">属性类型：</label>";
        					s += "					<div class=\"controls\">";
        					s += "						<select id=\"attrTypeCode\" name=\"attrTypeCode\"";
        					s += "							style=\"width: 280px;\">";

        					$.each($(data)[0].attrTypeCodes, function(i, attrTypeCode) {
        					s += "							<option value=\""+attrTypeCode.code+"\" selected> "+attrTypeCode.description+"</option>";
        					});

        					s += "						</select>";
        					s += "					</div>";
        					s += "				</div>";
        					s += "";
        					s += "				<div class=\"control-group\">";
        					s += "					<label class=\"control-label\">属性取值：</label>";
        					s += "					<div class=\"controls\">";
        					s += "						<textarea id=\"attrValues\" cols=\"30\" rows=\"5\"";
        					s += "							name=\"attrValues\" placeholder=\"多个属性值之间用'|'分隔\"></textarea>";
        					s += "					</div>";
        					s += "				</div>";
        					s += "";
        					s += "			</fieldset>";
        					s += "		</div>";

        			        $("#editDlgBody").html(s);
        			        
        			        $("#editDialog").modal("show");
        				}
        			}
        		});
        		return true;
        	});
         
        	// 按属性分类
        	$("#typeCode").change(function() {
        		window.location.href="${ctx}/back/ship/attribute?typeCode="+$(this).val();
        	});
        	// 修改
        	$(document).on("click", ".btnEdit", function() {
        		$.ajax({
        			method : "GET",
        			data : {id : $(this).attr("idVal")},
        			url : "${ctx}/back/ship/attribute/edit",
        			datatype : "json",
        			success : function(data) {
        				var returnCode = $(data)[0].returnCode;
        				var message = $(data)[0].message;
        				if (returnCode == "Failure") {
        					alert(message);
        					return false;
        				} else {
        					//读入数据，并填入
        					var s = "";
        					s += "		<div class=\"modal-body\">";
        					s += "			<fieldset>";
        					s += "";
        					s += "				<div class=\"control-group\">";
        					s += "					<label class=\"control-label\">类别：</label>";
        					s += "					<div class=\"controls\">";
        			        s += "						<input type=\"hidden\" name=\"id\" id=\"attributeId\" value=\""+$(data)[0].attrNameData.id+"\" />";
        					s += "						<select id=\"typeCode\" name=\"typeCode\" style=\"width: 280px;\">";

        			        $.each($(data)[0].uncleDatas, function(i, uncleData) {
        				        s += "							<optgroup label=\""+uncleData.typeName+"\">";
        				        $.each(uncleData.childrenDatas, function(i, childData) {
        							if ($(data)[0].attrNameData.typeData.typeCode==childData.typeCode)
        						        s += "								<option value=\""+childData.typeCode+"\" selected>"+childData.typeName+"</option>";
        							else
        						        s += "								<option value=\""+childData.typeCode+"\">"+childData.typeName+"</option>";
        				        });
        				        s += "							</optgroup>";
        			        });
        					
        					s += "						</select>";
        					s += "					</div>";
        					s += "				</div>";
        					s += "";
        					s += "				<div class=\"control-group\">";
        					s += "					<label class=\"control-label\">属性名称：</label>";
        					s += "					<div class=\"controls\">";
        					s += "						<input type=\"text\" name=\"attrName\" id=\"attrName\" value=\""+$(data)[0].attrNameData.attrName+"\" />";
        					s += "					</div>";
        					s += "				</div>";
        					s += "";
        					s += "				<div class=\"control-group\">";
        					s += "					<label class=\"control-label\">属性类型：</label>";
        					s += "					<div class=\"controls\">";
        					s += "						<select id=\"attrTypeCode\" name=\"attrTypeCode\"";
        					s += "							style=\"width: 280px;\">";

        					$.each($(data)[0].attrTypeCodes, function(i, attrTypeCode) {
        						if ($(data)[0].attrNameData.attrType==attrTypeCode.code)
        							s += "							<option value=\""+attrTypeCode.code+"\" selected> "+attrTypeCode.description+"</option>";
        						else
        							s += "							<option value=\""+attrTypeCode.code+"\"> "+attrTypeCode.description+"</option>";
        					});

        					s += "						</select>";
        					s += "					</div>";
        					s += "				</div>";
        					s += "";
        					s += "				<div class=\"control-group\">";
        					s += "					<label class=\"control-label\">属性取值：</label>";
        					s += "					<div class=\"controls\">";
        					s += "						<textarea id=\"attrValues\" cols=\"30\" rows=\"5\"";
        					s += "							name=\"attrValues\" placeholder=\"多个属性值之间用'|'分隔\">"+$(data)[0].attrNameData.attrValues+"</textarea>";
        					s += "					</div>";
        					s += "				</div>";
        					s += "";
        					s += "			</fieldset>";
        					s += "		</div>";

        			        $("#editDlgBody").html(s);
        			        
        			        $("#editDialog").modal("show");
        				}
        			}
        		});
        		return true;
        	});
        	
        // 删除
        $(document).on("click", ".btnDelete", function() {
        		var delId = $(this).attr("idVal");
            swal({
                title: "您确定要删除这条信息吗",
                text: "删除后将无法恢复，请谨慎操作！",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "删除",
                closeOnConfirm: false
            }, function () {
            	// 删除港口信息
            	$.ajax({
                    method : "GET",
                    data : $("#frmDeletePort").formSerialize(),
                    url : "${ctx}/back/ship/port/delete",
                    datatype : "json",
                    success : function(data) {
                      var returnCode = $(data)[0].returnCode;
                      var message = $(data)[0].message;
                      if (returnCode == "Failure") {
                        alert(message);
                        return false;
                      } else {
                        var params = "?nonsense＝0";
                        var t = $("#frmDeletePort").serializeArray();
                        $.each(t, function() {
                          params+="&"+this.name+"="+this.value;
                        });
                        window.location.href = _rootPath + "/back/ship/port" + params;
                      }
                    }
                  });	
            });
        });
    });

    </script>

</body>
</html>

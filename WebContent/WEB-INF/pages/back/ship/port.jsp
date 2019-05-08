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
						<h5>船舶管理 > 港口管理</h5>
					</div>
					<div class="ibox-content">
						<div class="form-group">
                                <label class="col-sm-2 control-label">港口列表</label>
                                        <div class="col-sm-10">
                                             <label>区域：</label>
                                            <div class="btn-group">
                                <select id="portCity" name="portCity" style="width:150px;">
		                        <optgroup label="全部">
		                      	  <option value="">请选择</option>
		                        </optgroup>
			                    <c:forEach var="bigArea" items="${bigAreas}">
			                      <optgroup label="${bigArea.description}">
			                      <c:forEach var="portCity" items="${bigArea.portCities}">
			                        <c:if test="${selPortCity == portCity}">
			                        	<option value="${portCity}" selected >${portCity.description}</option>
			                        </c:if>
			                        <c:if test="${selPortCity != portCity}">
			                        	<option value="${portCity}" >${portCity.description}</option>
			                        </c:if>
			                      </c:forEach>
			                      </optgroup>
			                    </c:forEach>
		                    </select>
                            </div>
						<input type="text" class="form-control input-sm m-b-xs"
							id="filter" placeholder="输入用户登录名、真实姓名、昵称或邮箱">
						 <button type="button" class="btn btn-primary btn-sm">查询</button>
						 <div class="ibox-tools">
							<button type="button" class="btn btn-info btn-xs" id="btnAdd"
								data-toggle="modal" data-target="#dlgEdit">添加</button>
						</div>
                                        </div>
                                        
                         </div>
						<table class="footable table table-stripped" data-page-size="20"
							data-filter=#filter>
							
							<thead>
								<tr>
									<th>港口编号</th>
	                    			<th>港口名称</th>
	                    			<th>港口城市编号</th>
	                    			<th>港口城市</th>
	                    			<th>港口坐标</th>
	                    			<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="portData" items="${pageData.result}">
									<tr class="gradeX">
										<td>${portData.portNo}</td>
	                    				<td>${portData.portName}</td>
	                    				<td>${portData.portCity.code}</td>
	                    				<td>${portData.portCity.description}</td>
	                    	<td>
		                    <c:forEach var="portCooordData" items="${portData.portCooordDatas}">
		                    (${portCooordData.longitude }, ${portCooordData.latitude })<br>
		                    </c:forEach>
	                    	</td>
										<td>
											<button type="button" class="btn btn-info btn-xs btnEdit" idVal="${adminData.id}" 
												data-toggle="modal" data-target="#dlgEdit">修改</button>
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


	<!-- Modal dlgEdit -->
	<div class="modal inmodal" id="dlgEdit" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content animated bounceInRight">
				<form class="form-horizontal" name="frmSavePort"
			      id="frmSavePort" novalidate="novalidate" method="post" 
			      action="${ctx}/back/ship/port/save">
			      <input type="hidden" name="portNo" id="portNo" value="" />
			      <input type="hidden" name="pageNo" value="${pageData.pageNo}" />
			      <input type="hidden" name="keyWords" value="${keyWords}" />
			      <input type="hidden" name="portCity" value="${selPortCity}" />
			    <div class="modal-header">
			      <button type="button" class="close" data-dismiss="modal">×</button>
			      <h3>编辑</h3>
			    </div>
			    <div class="modal-body">
			      <fieldset>
			
			        <div id="selContent"></div>
			
			      </fieldset>
			    </div>
					<div class="modal-footer">
						<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
						<button type="submit" class="btn btn-primary btnSavePort">保存</button>
					</div>
			    </form>
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
  	
 
    $(document).ready(function() {

            $('.footable').footable();
            
         // Form Validation
            $("#frmSavePort").validate({
                rules:{
                    latitude:{
                        required:false,
                        number:true
                    },
                    longitude:{
                        required:false,
                        number:true
                    },
                    latitude1:{
                        required:false,
                        number:true
                    },
                    longitude1:{
                        required:false,
                        number:true
                    },
                    latitude2:{
                        required:false,
                        number:true
                    },
                    longitude2:{
                        required:false,
                        number:true
                    },
                    portName:{
                        required:true,
                        minlength:2,
                        maxlength:10
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
                    url : "${ctx}/manage/ship/port/add",
                    datatype : "json",
                    success : function(data) {
                        var returnCode = $(data)[0].returnCode;
                        var message = $(data)[0].message;
                        if (returnCode == "Failure") {
                            alert(message);
                            return false;
                        } else {
                            //读入数据，并填入
                            $("#portNo").val("");

                            var s = "";
                            s += "<div class=\"control-group\">";
                            s += "  <label class=\"control-label\">港口城市：</label>";
                            s += "  <div class=\"controls\">";
                            s += "    <select id=\"portCityCode\" name=\"portCityCode\" style=\"width:280px;\">";
                            $.each($(data)[0].bigAreas, function(i, bigArea) {
                            s += "      <optgroup label=\""+bigArea.description+"\">";
                            $.each(bigArea.portCities, function(i, portCity) {
                            if ($(data)[0].portData && portCity.code==$(data)[0].portData.portCity.code) {
                            s += "      <option value=\""+portCity.code+"\" selected> "+portCity.description+"</option>";
                            } else {
                            s += "      <option value=\""+portCity.code+"\"> "+portCity.description+"</option>";
                            }
                            });
                            s += "      </optgroup>";
                            });
                            
                            s += "    </select>";
                            s += "  </div>";
                            s += "</div>";
                            s += "<div class=\"control-group\">";
                            s += "  <label class=\"control-label\">港口名称：</label>";
                            s += "  <div class=\"controls\">";
                            s += "    <input type=\"text\" name=\"portName\" id=\"portName\" value=\"\" style=\"width:264px;\" />";
                            s += "  </div>";
                            s += "</div>";
                            s += "";
                            s += "<div class=\"control-group\">";
                            s += "  <label class=\"control-label\">坐标点一：</label>";
                            s += "  <div class=\"controls\">";
                            s += "        经度<input type=\"text\" name=\"longitude\" id=\"longitude\" value=\"\" style=\"width:80px;\" />&nbsp;&nbsp;";
                            s += "        纬度<input type=\"text\" name=\"latitude\" id=\"latitude\" value=\"\" style=\"width:80px;\" />";
                            s += "  </div>";
                            s += "</div>";
                            s += "";
                            s += "<div class=\"control-group\">";
                            s += "  <label class=\"control-label\">坐标点二：</label>";
                            s += "  <div class=\"controls\">";
                            s += "        经度<input type=\"text\" name=\"longitude1\" id=\"longitude1\" value=\"\" style=\"width:80px;\" />&nbsp;&nbsp;";
                            s += "        纬度<input type=\"text\" name=\"latitude1\" id=\"latitude1\" value=\"\" style=\"width:80px;\" />";
                            s += "  </div>";
                            s += "</div>";
                            s += "";
                            s += "<div class=\"control-group\">";
                            s += "  <label class=\"control-label\">坐标点三：</label>";
                            s += "  <div class=\"controls\">";
                            s += "        经度<input type=\"text\" name=\"longitude2\" id=\"longitude2\" value=\"\" style=\"width:80px;\" />&nbsp;&nbsp;";
                            s += "        纬度<input type=\"text\" name=\"latitude2\" id=\"latitude2\" value=\"\" style=\"width:80px;\" />";
                            s += "  </div>";
                            s += "</div>";

                            $("#selContent").html(s);
                            
                            $("#editDialog").modal("show");
                            
                            return true;
                        }
                    }
                });
            });
        	// 修改
        	$(document).on("click", ".btnEdit", function() {
                $.ajax({
                    method : "GET",
                    data : {portNo : $(this).attr("idVal")},
                    url : "${ctx}/back/ship/port/edit",
                    datatype : "json",
                    success : function(data) {
                        var returnCode = $(data)[0].returnCode;
                        var message = $(data)[0].message;
                        if (returnCode == "Failure") {
                            alert(message);
                            return false;
                        } else {
                            
                            //读入数据，并填入
                            $("#portNo").val($(data)[0].portData.portNo);

                            var s = "";
                            s += "<div class=\"control-group\">";
                            s += "  <label class=\"control-label\">港口城市：</label>";
                            s += "  <div class=\"controls\">";
                            s += "    <select id=\"portCityCode\" name=\"portCityCode\" style=\"width:280px;\">";

                            $.each($(data)[0].bigAreas, function(i, bigArea) {
                            s += "      <optgroup label=\""+bigArea.description+"\">";
                            $.each(bigArea.portCities, function(i, portCity) {
                            if ($(data)[0].portData && portCity.code==$(data)[0].portData.portCity.code) {
                            s += "      <option value=\""+portCity.code+"\" selected> "+portCity.description+"</option>";
                            } else {
                            s += "      <option value=\""+portCity.code+"\"> "+portCity.description+"</option>";
                            }
                            });
                            s += "      </optgroup>";
                            });
                            
                            s += "    </select>";
                            s += "  </div>";
                            s += "</div>";
                            
                            s += "<div class=\"control-group\">";
                            s += "  <label class=\"control-label\">港口名称：</label>";
                            s += "  <div class=\"controls\">";
                            s += "    <input type=\"text\" name=\"portName\" id=\"portName\" value=\""+$(data)[0].portData.portName+"\" style=\"width:264px;\" />";
                            s += "  </div>";
                            s += "</div>";
                            s += "";
                            var size = 0;
                            if($(data)[0].portCooordDatas)
                                size = $(data)[0].portCooordDatas.length;
                            
                            if(size == 0){
                                s += "<div class=\"control-group\">";
                                s += "  <label class=\"control-label\">坐标点一：</label>";
                                s += "  <div class=\"controls\">";
                                s += "        经度<input type=\"text\" name=\"longitude\" id=\"longitude\" value=\"\" style=\"width:80px;\" />&nbsp;&nbsp;";
                                s += "        纬度<input type=\"text\" name=\"latitude\" id=\"latitude\" value=\"\" style=\"width:80px;\" />";
                                s += "  </div>";
                                s += "</div>";
                                s += "";
                                s += "<div class=\"control-group\">";
                                s += "  <label class=\"control-label\">坐标点二：</label>";
                                s += "  <div class=\"controls\">";
                                s += "        经度<input type=\"text\" name=\"longitude1\" id=\"longitude1\" value=\"\" style=\"width:80px;\" />&nbsp;&nbsp;";
                                s += "        纬度<input type=\"text\" name=\"latitude1\" id=\"latitude1\" value=\"\" style=\"width:80px;\" />";
                                s += "  </div>";
                                s += "</div>";
                                s += "";
                                s += "<div class=\"control-group\">";
                                s += "  <label class=\"control-label\">坐标点三：</label>";
                                s += "  <div class=\"controls\">";
                                s += "        纬度<input type=\"text\" name=\"latitude2\" id=\"latitude2\" value=\"\" style=\"width:80px;\" />";
                                s += "        经度<input type=\"text\" name=\"longitude2\" id=\"longitude2\" value=\"\" style=\"width:80px;\" />&nbsp;&nbsp;";
                                s += "  </div>";
                                s += "</div>";
                            }else if(size == 1){
                                s += "<div class=\"control-group\">";
                                s += "  <label class=\"control-label\">坐标点一：</label>";
                                s += "  <div class=\"controls\">";
                                s += "        经度<input type=\"text\" name=\"longitude\" id=\"longitude\" value=\""+$(data)[0].portCooordDatas[0].longitude+"\" style=\"width:80px;\" />&nbsp;&nbsp;";
                                s += "        纬度<input type=\"text\" name=\"latitude\" id=\"latitude\" value=\""+$(data)[0].portCooordDatas[0].latitude+"\" style=\"width:80px;\" />";
                                s += "  </div>";
                                s += "</div>";
                                s += "";
                                s += "<div class=\"control-group\">";
                                s += "  <label class=\"control-label\">坐标点二：</label>";
                                s += "  <div class=\"controls\">";
                                s += "        经度<input type=\"text\" name=\"longitude1\" id=\"longitude1\" value=\"\" style=\"width:80px;\" />&nbsp;&nbsp;";
                                s += "        纬度<input type=\"text\" name=\"latitude1\" id=\"latitude1\" value=\"\" style=\"width:80px;\" />";
                                s += "  </div>";
                                s += "</div>";
                                s += "";
                                s += "<div class=\"control-group\">";
                                s += "  <label class=\"control-label\">坐标点三：</label>";
                                s += "  <div class=\"controls\">";
                                s += "        经度<input type=\"text\" name=\"longitude2\" id=\"longitude2\" value=\"\" style=\"width:80px;\" />&nbsp;&nbsp;";
                                s += "        纬度<input type=\"text\" name=\"latitude2\" id=\"latitude2\" value=\"\" style=\"width:80px;\" />";
                                s += "  </div>";
                                s += "</div>";
                            }else if(size == 2){
                                s += "<div class=\"control-group\">";
                                s += "  <label class=\"control-label\">坐标点一：</label>";
                                s += "  <div class=\"controls\">";
                                s += "        经度<input type=\"text\" name=\"longitude\" id=\"longitude\" value=\""+$(data)[0].portCooordDatas[0].longitude+"\" style=\"width:80px;\" />&nbsp;&nbsp;";
                                s += "        纬度<input type=\"text\" name=\"latitude\" id=\"latitude\" value=\""+$(data)[0].portCooordDatas[0].latitude+"\" style=\"width:80px;\" />";
                                s += "  </div>";
                                s += "</div>";
                                s += "";
                                s += "<div class=\"control-group\">";
                                s += "  <label class=\"control-label\">坐标点二：</label>";
                                s += "  <div class=\"controls\">";
                                s += "        经度<input type=\"text\" name=\"longitude1\" id=\"longitude1\" value=\""+$(data)[0].portCooordDatas[1].longitude+"\" style=\"width:80px;\" />&nbsp;&nbsp;";
                                s += "        纬度<input type=\"text\" name=\"latitude1\" id=\"latitude1\" value=\""+$(data)[0].portCooordDatas[1].latitude+"\" style=\"width:80px;\" />";
                                s += "  </div>";
                                s += "</div>";
                                s += "";
                                s += "<div class=\"control-group\">";
                                s += "  <label class=\"control-label\">坐标点三：</label>";
                                s += "  <div class=\"controls\">";
                                s += "        经度<input type=\"text\" name=\"longitude2\" id=\"longitude2\" value=\"\" style=\"width:80px;\" />&nbsp;&nbsp;";
                                s += "        纬度<input type=\"text\" name=\"latitude2\" id=\"latitude2\" value=\"\" style=\"width:80px;\" />";
                                s += "  </div>";
                                s += "</div>";
                            }else if(size == 3){
                                s += "<div class=\"control-group\">";
                                s += "  <label class=\"control-label\">坐标点一：</label>";
                                s += "  <div class=\"controls\">";
                                s += "        经度<input type=\"text\" name=\"longitude\" id=\"longitude\" value=\""+$(data)[0].portCooordDatas[0].longitude+"\" style=\"width:80px;\" />&nbsp;&nbsp;";
                                s += "        纬度<input type=\"text\" name=\"latitude\" id=\"latitude\" value=\""+$(data)[0].portCooordDatas[0].latitude+"\" style=\"width:80px;\" />";
                                s += "  </div>";
                                s += "</div>";
                                s += "";
                                s += "<div class=\"control-group\">";
                                s += "  <label class=\"control-label\">坐标点二：</label>";
                                s += "  <div class=\"controls\">";
                                s += "        经度<input type=\"text\" name=\"longitude1\" id=\"longitude1\" value=\""+$(data)[0].portCooordDatas[1].longitude+"\" style=\"width:80px;\" />&nbsp;&nbsp;";
                                s += "        纬度<input type=\"text\" name=\"latitude1\" id=\"latitude1\" value=\""+$(data)[0].portCooordDatas[1].latitude+"\" style=\"width:80px;\" />";
                                s += "  </div>";
                                s += "</div>";
                                s += "";
                                s += "<div class=\"control-group\">";
                                s += "  <label class=\"control-label\">坐标点三：</label>";
                                s += "  <div class=\"controls\">";
                                s += "        经度<input type=\"text\" name=\"longitude2\" id=\"longitude2\" value=\""+$(data)[0].portCooordDatas[2].longitude+"\" style=\"width:80px;\" />&nbsp;&nbsp;";
                                s += "        纬度<input type=\"text\" name=\"latitude2\" id=\"latitude2\" value=\""+$(data)[0].portCooordDatas[2].latitude+"\" style=\"width:80px;\" />";
                                s += "  </div>";
                                s += "</div>";
                            }


                            $("#selContent").html(s);
                            
                            $("#editDialog").modal("show");
                            
                            return true;
                        }
                    }
                });
                
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
                    url : "${ctx}/manage/ship/port/delete",
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
                        window.location.href = _rootPath + "/manage/ship/port" + params;
                      }
                    }
                  });	
            	
            });
        });
        
     // 保存港口信息
     	$(document).on("click", ".btnSavePort", function() {
        	if($("#frmSavePort").valid())
            $.ajax({
              method : "GET",
              data : $("#frmSavePort").formSerialize(),
              url : "${ctx}/manage/ship/port/save",
              datatype : "json",
              success : function(data) {
                var returnCode = $(data)[0].returnCode;
                var message = $(data)[0].message;
                if (returnCode == "Failure") {
                  alert(message);
                  return false;
                } else {
                  var params = "?nonsense＝0";
                  var t = $("#frmSavePort").serializeArray();
                  $.each(t, function() {
                    params+="&"+this.name+"="+this.value;
                  });
                  window.location.href = _rootPath + "/manage/ship/port" + params;
                }
              }
            });
        });
    });

    </script>

</body>
</html>

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
						<h5>船舶管理 > 类别属性</h5>
					</div>
					<div class="ibox-content">
						<div class="form-group">
                                <label class="col-sm-2 control-label">类别属性列表</label>
                                        <div class="col-sm-10">
                                            <span style="margin-top:5px">按属性分类
              	<select id="typeCode" name="typeCode" style="margin-top:4px;width:120px"><!-- onChange="javascript:void(0);" -->
              	  <c:forEach var="uncleData" items="${uncleDatas}">
              	   <optgroup label="${uncleData.typeName}">
              	     <c:forEach var="childrenData" items="${uncleData.childrenDatas}">
		              	<c:choose>
		              		<c:when test="${childrenData.typeCode == typeCode}">
		              			<option value="${childrenData.typeCode}" selected>${childrenData.typeName}</option>
		              		</c:when>
		              		<c:otherwise>
		              			<option value="${childrenData.typeCode}">${childrenData.typeName}</option>
		              		</c:otherwise>
		              	</c:choose>
              	     </c:forEach>
              	    </optgroup>
              	   </c:forEach>
              	</select>
              </span>
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
									<th>编码</th>
				                    <th>类别</th>
				                    <th>属性名称</th>
				                    <th>属性类型</th>
				                    <th>属性取值</th>
				                    <th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="attrNameData" items="${attrNameDatas}">
					                  <tr>
					                  	<td>${attrNameData.attrNameCode}</td>
					                  	<td>${attrNameData.typeData.typeName}</td>
										<td>${attrNameData.attrName}</td>
										<td>${attrNameData.attrType.description}</td>
					                    <td style="width:400px">${attrNameData.attrValues}</td>
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
				<form class="form-horizontal" name="editDialogForm"
			      id="editDialogForm" novalidate="novalidate" method="post" action="${ctx}/back/ship/attribute/save" >
			    <div class="modal-header">
			      <button type="button" class="close" data-dismiss="modal">×</button>
			      <h3>编辑</h3>
			    </div>
			    <div id="editDlgBody"></div>
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

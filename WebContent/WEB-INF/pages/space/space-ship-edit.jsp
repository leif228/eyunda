<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx"
  value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>会员空间</title>
<meta http-equiv="X-UA-Compatible" content="IE=9">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
  content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />

<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css" />
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
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css"
  class="skin-color" />

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
<script src="${ctx}/js/jquery.form-2.63.js"></script>
<script src="${ctx}/js/space/space-ship-edit.js"></script>

<script type="text/javascript">
var _rootPath = "${ctx}";

$(document).ready(function(){

// datepicker
$('[data-form=datepicker]').datepicker();

$('#myTab a[href="#${tab}"]').tab('show');

$("#frmSortInfo").validate({
  rules:{
    <c:forEach var="attrNameData" items="${myShipData.attrNameDatas}">
        <c:if test="${attrNameData.attrType == 'dblnum'}">
        a${attrNameData.attrNameCode}:{
      required:false,
      number:true
    },
    </c:if>
        <c:if test="${attrNameData.attrType == 'intnum'}">
        a${attrNameData.attrNameCode}:{
      required:false,
      digits:true
    },
    </c:if>
        <c:if test="${attrNameData.attrType == 'charstr'}">
        a${attrNameData.attrNameCode}:{
      required:false,
      minlength:2,
      maxlength:25
    },
    </c:if>
    </c:forEach>
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

.form-horizontal .form-actions {
  padding-left: 0px;
  text-align: center;
}

.form-horizontal input[type=text], .form-horizontal input[type=password]
  {
  width: 200px
}

.select2-container {
  width: 200px
}
</style>

</head>

<body>

  <jsp:include page="./space-head.jsp"></jsp:include>

  <div class="span10">

    <h1 class="page-title">
      <i class="icon-star icon-white"></i> 船舶管理
    </h1>
    <div class="row">

      <div class="span10">

        <div class="widget">

          <div class="widget-header">
            <h3>船舶信息编辑</h3>
          </div>
          <!-- /widget-header -->

          <div class="widget-content">

            <div class="box-header corner-top">
              <ul id="myTab" class="nav nav-pills">

                <li><a href="#ship-baseinfo" data-toggle="tab" class="bold">基本属性</a></li>

                <c:if test="${myShipData.id!=0}">
                  <li><a href="#ship-sortinfo" data-toggle="tab" class="bold">分类属性</a></li>
                  <li><a href="#ship-detail" data-toggle="tab" class="bold">船舶介绍</a></li>
                  <!-- <li><a href="#ship-delivery" data-toggle="tab" class="bold">船舶接货</a></li> -->
                  <li><a href="#ship-audit" data-toggle="tab" class="bold">认证资料</a></li>
                </c:if>

              </ul>

              <br />

              <div class="tab-content">
                <div class="tab-pane active" id="ship-baseinfo">
                  <form class="form-horizontal" id="frmBaseInfo"
                    name="frmBaseInfo" novalidate="novalidate" method="post"
                    action="${ctx}/space/ship/myShip/saveBaseInfo"
                    enctype="multipart/form-data">
                    <fieldset>

                      <div class="row-fluid">
                        <div class="span4">

                          <div class="control-group">
                            <label class="control-label"></label>
                            <div class="controls">
                              <div class="account-avatar">
                                <img
                                  src="${ctx}/download/imageDownload?url=${myShipData.shipLogo}"
                                  alt="" class="thumbnail"
                                  style="width: 100px; height: 100px;">
                              </div>
                            </div>
                          </div>

                          <div class="control-group">
                            <label class="control-label">船舶图片：</label>
                            <div class="controls">
                              <input type="file" id="shipLogoFile" name="shipLogoFile"
                                value="" />
                            </div>
                          </div>

                        </div>

                        <div class="span8">

                          <div class="control-group">
                            <label class="control-label">类别：</label>
                            <div class="controls">
                              <select id="shipType" name="shipType">
                                <c:forEach var="uncleData" items="${uncleDatas}">
                                  <optgroup label="${uncleData.typeName}">
                                    <c:forEach var="typeData"
                                      items="${uncleData.childrenDatas}">
                                      <c:if
                                        test="${!empty myShipData.typeData && myShipData.typeData.typeCode==typeData.typeCode}">
                                        <option value="${typeData.typeCode}" selected>
                                          ${typeData.typeName}</option>
                                      </c:if>
                                      <c:if
                                        test="${empty myShipData.typeData || myShipData.typeData.typeCode!=typeData.typeCode}">
                                        <option value="${typeData.typeCode}">
                                          ${typeData.typeName}</option>
                                      </c:if>
                                    </c:forEach>
                                  </optgroup>
                                </c:forEach>
                              </select>
                            </div>
                          </div>

                          <div class="control-group">
                            <label class="control-label">船舶中文名：</label>
                            <div class="controls">
                              <input type="hidden" name="id" value="${myShipData.id}" />
                              <input type="hidden" name="shipCode"
                                value="${myShipData.shipCode}" /> <input type="text"
                                class="input-medium" id="shipName" name="shipName"
                                value="${myShipData.shipName}"
                                placeholder="请输入船名，2-25个字符" /> <span class="color-red"></span>
                            </div>
                          </div>

                          <div class="control-group">
                            <label class="control-label">船舶英文名：</label>
                            <div class="controls">
                              <input type="text" class="input-medium" id="englishName"
                                name="englishName" value="${myShipData.englishName}"
                                placeholder="请输入船舶英文名，2-25个字符" /> <span
                                class="color-red"></span>
                            </div>
                          </div>

                          <div class="control-group">
                            <label class="control-label">MMSI：</label>
                            <div class="controls">
                              <input type="text" class="input-medium" id="mmsi"
                                name="mmsi" value="${myShipData.mmsi}"
                                placeholder="请输入MMSI号" /> <span class="color-red"></span>
                            </div>
                          </div>

                          <div class="control-group">
                            <label class="control-label">船长：</label>
                            <div class="controls">
                              <input type="text" class="input-medium" id="length"
                                name="length" value="${myShipData.length}"
                                placeholder="请输入船长" /> <span class="color-red"></span>
                            </div>
                          </div>

                          <div class="control-group">
                            <label class="control-label">船宽：</label>
                            <div class="controls">
                              <input type="text" class="input-medium" id="breadth"
                                name="breadth" value="${myShipData.breadth}"
                                placeholder="请输船宽" /> <span class="color-red"></span>
                            </div>
                          </div>

                          <div class="control-group">
                            <label class="control-label">型深：</label>
                            <div class="controls">
                              <input type="text" class="input-medium" id="mouldedDepth"
                                name="mouldedDepth" value="${myShipData.mouldedDepth}"
                                placeholder="请输入船舶的型深" /> <span class="color-red"></span>
                            </div>
                          </div>

                          <div class="control-group">
                            <label class="control-label">吃水深度：</label>
                            <div class="controls">
                              <input type="text" class="input-medium" id="draught"
                                name="draught" value="${myShipData.draught}"
                                placeholder="请输入吃水深度" /> <span class="color-red"></span>
                            </div>
                          </div>

                          <div class="control-group">
                            <label class="control-label">总吨：</label>
                            <div class="controls">
                              <input type="text" class="input-medium" id="sumTons"
                                name="sumTons" value="${myShipData.sumTons}"
                                placeholder="请输入总吨" /> <span class="color-red"></span>
                            </div>
                          </div>

                          <div class="control-group">
                            <label class="control-label">净吨：</label>
                            <div class="controls">
                              <input type="text" class="input-medium" id="cleanTons"
                                name="cleanTons" value="${myShipData.cleanTons}"
                                placeholder="请输入净吨" /> <span class="color-red"></span>
                            </div>
                          </div>

                          <div class="control-group">
                            <label class="control-label">重箱(TEU)：</label>
                            <div class="controls">
                              <input type="text" class="input-medium"
                                id="fullContainer" name="fullContainer"
                                value="${myShipData.fullContainer}" placeholder="请输入载箱量重箱数量" />
                              <span class="color-red"></span>
                            </div>
                          </div>

                          <div class="control-group">
                            <label class="control-label">半重箱(TEU)：</label>
                            <div class="controls">
                              <input type="text" class="input-medium"
                                id="halfContainer" name="halfContainer"
                                value="${myShipData.halfContainer}" placeholder="请输入载箱量半重箱数量" />
                              <span class="color-red"></span>
                            </div>
                          </div>

                          <div class="control-group">
                            <label class="control-label">吉箱(TEU)：</label>
                            <div class="controls">
                              <input type="text" class="input-medium"
                                id="spaceContainer" name="spaceContainer"
                                value="${myShipData.spaceContainer}" placeholder="请输入载箱量吉箱数量" />
                              <span class="color-red"></span>
                            </div>
                          </div>

                          <div class="control-group">
                            <label class="control-label">载重A级(吨)：</label>
                            <div class="controls">
                              <input type="text" class="input-medium" id="aTons"
                                name="aTons" value="${myShipData.aTons}"
                                placeholder="请输入载重量A级重量" /> <span class="color-red"></span>
                            </div>
                          </div>

                          <div class="control-group">
                            <label class="control-label">载重B级(吨)：</label>
                            <div class="controls">
                              <input type="text" class="input-medium" id="bTons"
                                name="bTons" value="${myShipData.bTons}"
                                placeholder="请输入载重量B级重量" /> <span class="color-red"></span>
                            </div>
                          </div>

                        </div>
                      </div>

                      <%-- <div class="control-group" >
                        <label class="control-label" for="keyWords">航区：</label>
                        <div class="controls">
                          <textarea id="keyWords" name="keyWords" class="span6"
                            rows="3" placeholder="请输入广告词 ...">${myShipData.keyWords}</textarea>
                        </div>
                      </div>

                      <div class="control-group">
                        <label class="control-label" for="shipTitle">船舶说明：</label>
                        <div class="controls">
                          <textarea id="shipTitle" name="shipTitle" class="span6"
                            rows="3" placeholder="请输入船舶说明 ...">${myShipData.shipTitle}</textarea>
                        </div>
                      </div> --%>

                      <div class="form-actions">
                        <a href="javascript:void(0);"
                          class="btn btn-primary btnSaveBaseInfo">保存</a> <a
                          href="javascript:window.history.go(-1);"
                          class="btn btn-warning">返回</a>
                      </div>
                    </fieldset>
                  </form>
                </div>

                <div class="tab-pane" id="ship-sortinfo">
                  <form id="frmSortInfo" name="frmSortInfo"
                    class="form-horizontal" method="post"
                    action="${ctx}/space/ship/myShip/saveSortInfo">
                    <input type="hidden" name="id" value="${myShipData.id}" />
                    <fieldset>

                      <div id="typeFormDiv">

                        <c:forEach var="attrNameData"
                          items="${myShipData.attrNameDatas}">

                          <c:if test="${attrNameData.attrType == 'booltype'}">
                            <div class="control-group">
                              <label class="control-label">${attrNameData.attrName}:</label>
                              <div class="controls">
                                <c:set var="isFirst" value="true" />
                                <c:forEach var="attrValueData"
                                  items="${attrNameData.attrValueDatas}">
                                  <c:if
                                    test="${attrNameData.currAttrValue.attrValue == attrValueData.attrValueCode || isFirst=='true'}">
                                    <input type="radio"
                                      name="a${attrNameData.attrNameCode}"
                                      value="${attrValueData.attrValueCode}" checked /> ${attrValueData.attrValue}
                        </c:if>
                                  <c:if
                                    test="${attrNameData.currAttrValue.attrValue != attrValueData.attrValueCode && isFirst=='false'}">
                                    <input type="radio"
                                      name="a${attrNameData.attrNameCode}"
                                      value="${attrValueData.attrValueCode}" /> ${attrValueData.attrValue}
                        </c:if>
                                  <c:set var="isFirst" value="false" />
                                </c:forEach>
                              </div>
                            </div>
                          </c:if>

                          <c:if test="${attrNameData.attrType == 'datetype'}">
                            <div class="control-group">
                              <label class="control-label"
                                for="a${attrNameData.attrNameCode}">${attrNameData.attrName}:</label>
                              <div class="controls">
                                <div class="input-append date" data-form="datepicker"
                                  data-date="${attrNameData.currAttrValue.attrValue}"
                                  data-date-format="yyyy-mm-dd">
                                  <input name="a${attrNameData.attrNameCode}"
                                    class="grd-white" data-form="datepicker"
                                    style="width: 200px; border: 1px solid #cccccc;"
                                    size="10" type="text"
                                    value="${attrNameData.currAttrValue.attrValue}"
                                    data-date-format="yyyy-mm-dd" /> <span class="add-on"><i
                                    class="icon-th"></i></span>
                                </div>
                              </div>
                            </div>
                          </c:if>

                          <c:if test="${attrNameData.attrType == 'dblnum'}">
                            <div class="control-group">
                              <label class="control-label">${attrNameData.attrName}:</label>
                              <div class="controls">
                                <!-- data-validate="{required:true,number:true}" -->
                                <input type="text" name="a${attrNameData.attrNameCode}"
                                  value="${attrNameData.currAttrValue.attrValue}" />
                              </div>
                            </div>
                          </c:if>

                          <c:if test="${attrNameData.attrType == 'intnum'}">
                            <div class="control-group">
                              <label class="control-label">${attrNameData.attrName}:</label>
                              <div class="controls">
                                <!-- data-validate="{required:true,digits:true}" -->
                                <input type="text" name="a${attrNameData.attrNameCode}"
                                  value="${attrNameData.currAttrValue.attrValue}" />
                              </div>
                            </div>
                          </c:if>

                          <c:if test="${attrNameData.attrType == 'charstr'}">
                            <div class="control-group">
                              <label class="control-label">${attrNameData.attrName}:</label>
                              <div class="controls">
                                <!-- data-validate="{required:true,minlength:2,maxlength:25}" -->
                                <input type="text" name="a${attrNameData.attrNameCode}"
                                  value="${attrNameData.currAttrValue.attrValue}" />
                              </div>
                            </div>
                          </c:if>

                          <c:if test="${attrNameData.attrType == 'charcode'}">
                            <div class="control-group">
                              <label class="control-label">${attrNameData.attrName}:</label>
                              <div class="controls">
                                <select name="a${attrNameData.attrNameCode}">
                                  <c:forEach var="attrValueData"
                                    items="${attrNameData.attrValueDatas}">
                                    <c:if
                                      test="${attrNameData.currAttrValue.attrValue == attrValueData.attrValueCode}">
                                      <option value="${attrValueData.attrValueCode}"
                                        selected>${attrValueData.attrValue}</option>
                                    </c:if>
                                    <c:if
                                      test="${attrNameData.currAttrValue.attrValue != attrValueData.attrValueCode}">
                                      <option value="${attrValueData.attrValueCode}">${attrValueData.attrValue}</option>
                                    </c:if>
                                  </c:forEach>
                                </select>
                              </div>
                            </div>
                          </c:if>

                        </c:forEach>

                      </div>

                      <br />

                      <div class="form-actions">
                        <a href="javascript:void(0);"
                          class="btn btn-primary btnSaveSortInfo">保存</a> <a
                          href="javascript:window.history.go(-1);"
                          class="btn btn-warning">返回</a>
                      </div>
                      <!-- /form-actions -->

                    </fieldset>
                  </form>
                </div>

                <div class="tab-pane" id="ship-detail">

                  <form class="form-horizontal" name="frmDetailInfo"
                    id="frmDetailInfo" novalidate="novalidate" method="post"
                    action="${ctx}/space/ship/myShip/saveDetailInfo">
                    <fieldset>
                      <c:forEach var="myShipAttaData"
                        items="${myShipData.myShipAttaDatas}">
                        <div class="pull-left detail-descrip">
                          <c:if test="${!empty myShipAttaData.url}">
                            <div class="pull-left detail-descrip-info"
                              style="text-align: center">
                              <img
                                src="${ctx}/download/imageDownload?url=${myShipAttaData.url}"
                                style="width: 600px;" /><br /> <span
                                class="detail-descrip-text">${myShipAttaData.titleDes}</span>
                            </div>
                          </c:if>
                          <c:if test="${empty myShipAttaData.url}">
                            <div class="pull-left detail-descrip-info"
                              style="text-align: left">
                              <span class="detail-descrip-text">${myShipAttaData.titleDes}</span>
                            </div>
                          </c:if>

                          <div class="pull-left detail-descrip-opt">
                            <a href="javascript:void(0);"
                              class="btn btn-primary btnMoveAttaUp" title="上移"
                              idVal="${myShipAttaData.id}"> <i
                              class="icon-arrow-up icon-white"></i>
                            </a> <a href="javascript:void(0);"
                              class="btn btn-info btnMoveAttaDown" title="下移"
                              idVal="${myShipAttaData.id}"> <i
                              class="icon-arrow-down icon-white"></i>
                            </a> <a href="javascript:void(0);"
                              class="btn btn-danger btnRemoveAtta" title="删除"
                              idVal="${myShipAttaData.id}"> <i
                              class="icon-trash icon-white"></i>
                            </a>
                          </div>
                          <div class="clearfix"></div>
                        </div>
                        <div class="clearfix"></div>
                      </c:forEach>
                      <div class="divider-content">
                        <span></span>
                      </div>

                      <div class="control-group ">
                        <label class="control-label" for="shipPic"
                          style="width: 115px;">上传图片：</label>
                        <div class="controls" style="margin-left: 120px">
                          <input type="hidden" name="shipId" value="${myShipData.id}" />
                          <input type="hidden" name="id" value="0" /> <input
                            type="file" name="shipImageFile" />
                        </div>
                      </div>

                      <div class="control-group">
                        <label class="control-label" style="width: 115px;">详情描述：</label>
                        <div class="controls" style="margin-left: 120px">
                          <textarea class="span6" name="title" rows="8"
                            data-form="wysihtml5" placeholder="请输入图片说明 ..."></textarea>
                        </div>
                      </div>

                      <br />

                      <div class="form-actions">
                        <a href="javascript:void(0);"
                          class="btn btn-primary btnSaveDetailInfo">保存</a> <a
                          href="javascript:window.history.go(-1);"
                          class="btn btn-warning">返回</a>
                      </div>
                      <!-- /form-actions -->

                    </fieldset>
                  </form>
                </div>

                <div class="tab-pane" id="ship-delivery">
                  <form class="form-horizontal" name="frmDelivery"
                    id="frmDelivery" novalidate="novalidate" method="post"
                    action="">
                    <input type="hidden" name="shipId" value="${myShipData.id}" />
                    <fieldset>
                      <div class="row-fluid">
                        <div class="span12" style="margin-left: 12px">
                          <div>
                            <h4>接货类别:</h4>
                          </div>
                          <div class="control-group ">
                            <label class="control-label" style="margin-left: -200px;"></label>
                            <div class="controls"
                              style="margin-left: 0px; padding-top: 0px;">
                              <c:forEach var="bigCargoType" items="${cargoBigTypes}"
                                varStatus="status">
                                <c:set var="tag2" value="t2" />
                                <c:forEach var="shipCargoType"
                                  items="${myShipData.shipCargoTypes}">
                                  <c:if
                                    test="${shipCargoType.cargoBigType == bigCargoType}">
                                    <c:set var="tag2" value="f2" />
                                  </c:if>
                                </c:forEach>
                                <c:if test="${tag2 == 'f2' }">
                                  <input type="checkbox" name="cargoType"
                                    value="${bigCargoType.cargoTypes[0]}" checked />${bigCargoType.description}
                                </c:if>
                                <c:if test="${tag2 == 't2' }">
                                  <input type="checkbox" name="cargoType"
                                    value="${bigCargoType.cargoTypes[0]}" />${bigCargoType.description}
                                </c:if>
                              </c:forEach>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div class="divider-content">
                        <span></span>
                      </div>
                      <div class="row-fluid">
                        <div class="span12" style="margin-left: 12px">
                          <div>
                            <h4>经营区域:</h4>
                          </div>
                          <div class="row-fluid">
                            <div class="span12">
                              <c:forEach var="bigArea" items="${bigAreas}">
                                <c:forEach var="portCitie" items="${bigArea.portCities}">
                                 <c:set var="flag" value="0"></c:set>
                                 <c:forEach var="shipPortData" items="${myShipData.shipPortDatas}">
                                   <c:if test="${shipPortData.portCity == portCitie}">
                                     <c:set var="flag" value="1"></c:set>
                                   </c:if>
                                 </c:forEach>
                                 <c:if test="${flag == 1}">
                                 	<input type="checkbox" name="portCityCode" value="${portCitie.code}" checked />${portCitie.description}
                                 </c:if>
                                 <c:if test="${flag == 0}">
                                   <input type="checkbox" name="portCityCode" value="${portCitie.code}" />${portCitie.description}
                                 </c:if>
                                </c:forEach>
                              </c:forEach>
                            </div>
                          </div>
                        </div>
                      </div>

                      <br />

                      <div class="form-actions">
                        <a href="javascript:void(0);"
                          class="btn btn-primary btnSaveDelivery">保存</a> <a
                          href="javascript:window.history.go(-1);"
                          class="btn btn-warning">返回</a>
                      </div>
                      <!-- /form-actions -->

                    </fieldset>
                  </form>
                </div>

                <div class="tab-pane" id="ship-audit">
                  <form class="form-horizontal" id="frmAudit" name="frmAudit"
                    novalidate="novalidate" method="post"
                    action="${ctx}/space/ship/myShip/saveAudit"
                    enctype="multipart/form-data">

                    <input type="hidden" name="id" value="${myShipData.id}" />

                    <fieldset>

                      <div class="control-group">
                        <label class="control-label">委托类型：</label>
                        <div class="controls">
                          <select id="warrantType" name="warrantType">
                            <c:forEach var="warrantType" items="${warrantTypes}">
                              <c:if test="${warrantType == myShipData.warrantType}">
                                <option value="${warrantType}" selected>
                                  ${warrantType.description}</option>
                              </c:if>
                              <c:if test="${warrantType != myShipData.warrantType}">
                                <option value="${warrantType}">
                                  ${warrantType.description}</option>
                              </c:if>
                            </c:forEach>
                          </select>
                        </div>
                      </div>

                      <div class="control-group">
                        <label class="control-label"></label>
                        <div class="controls">
                          <div class="account-avatar">
                            <img
                              src="${ctx}/download/imageDownload?url=${myShipData.idCardFront}"
                              alt="" class="thumbnail"
                              style="width: 100px; height: 100px;">
                          </div>
                        </div>
                      </div>

                      <div class="control-group">
                        <label id="BL" class="control-label"> <c:if
                            test="${myShipData.warrantType == 'personWarrant'}">
                            船东身份证正面：
                          </c:if> <c:if
                            test="${myShipData.warrantType == 'companyWarrant'}">
                                                                      营业执照：
                          </c:if>
                        </label>
                        <div class="controls">
                          <input type="file" id="idCardFrontFile"
                            name="idCardFrontFile" value="" />
                        </div>
                      </div>

                      <div class="control-group">
                        <label class="control-label"></label>
                        <div class="controls">
                          <div class="account-avatar">
                            <img
                              src="${ctx}/download/imageDownload?url=${myShipData.idCardBack}"
                              alt="" class="thumbnail"
                              style="width: 100px; height: 100px;">
                          </div>
                        </div>
                      </div>

                      <div class="control-group">
                        <label id="OCC" class="control-label"> <c:if
                            test="${myShipData.warrantType == 'personWarrant'}">
                            船东身份证反面：
                          </c:if> <c:if
                            test="${myShipData.warrantType == 'companyWarrant'}">
                                                                      组织机构代码证：
                          </c:if>
                        </label>
                        <div class="controls">
                          <input type="file" id="idCardBackFile"
                            name="idCardBackFile" value="" />
                        </div>
                      </div>

                      <div class="control-group">
                        <label class="control-label"></label>
                        <div class="controls">
                          <div class="account-avatar">
                            <img
                              src="${ctx}/download/imageDownload?url=${myShipData.warrant}"
                              alt="" class="thumbnail"
                              style="width: 100px; height: 100px;">
                          </div>
                        </div>
                      </div>

                      <div class="control-group">
                        <label class="control-label">委托书：</label>
                        <div class="controls">
                          <input type="file" id="warrantFile" name="warrantFile"
                            value="" /> <br />可下载<a
                            href="${ctx}/phone/trustdeed.docx"><font size="2px"
                            color="red">“委托书模板”</font></a>，然后填写、签名、盖章、拍照、上传
                        </div>
                      </div>

                      <div class="control-group">
                        <label class="control-label"></label>
                        <div class="controls">
                          <div class="account-avatar">
                            <img
                              src="${ctx}/download/imageDownload?url=${myShipData.certificate}"
                              alt="" class="thumbnail"
                              style="width: 100px; height: 100px;">
                          </div>
                        </div>
                      </div>

                      <div class="control-group">
                        <label class="control-label">船舶运营证：</label>
                        <div class="controls">
                          <input type="file" id="certificateFile"
                            name="certificateFile" value="" />
                        </div>
                      </div>

                      <div class="form-actions">
                        <a href="javascript:void(0);"
                          class="btn btn-primary btnSaveAudit">保存</a> <a
                          href="javascript:window.history.go(-1);"
                          class="btn btn-warning">返回</a>
                      </div>
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

  <!-- Modal deleteDialog -->
  <div id="removeAttaDialog" class="modal hide fade">
    <form class="form-horizontal" name="removeAttaDialogForm"
      id="removeAttaDialogForm" novalidate="novalidate" method="post"
      action="${ctx}/space/ship/myShip/removeAtta">
      <input type="hidden" name="_method" value="delete" /> <input
        type="hidden" name="attaId" id="removeAttaId" value="" />
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>删除确认</h3>
      </div>
      <div class="modal-body">
        <p>你确认要删除该船舶附件信息吗？</p>
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

</body>
</html>

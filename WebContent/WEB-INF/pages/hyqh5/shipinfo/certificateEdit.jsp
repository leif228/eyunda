<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <title>船舶证书 - 证书编辑</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <meta name="description" content="Write an awesome description for your new site here. You can edit this line in _config.yml. It will appear in your document head meta (for Google search results) and in your feed.xml site description.">

    <link rel="stylesheet" href="${ctx}/hyqh5/lib/weui.min.css">
    <link rel="stylesheet" href="${ctx}/hyqh5/css/jquery-weui.css">
    <link rel="stylesheet" href="${ctx}/hyqh5/demos/css/demos.css">
  </head>

  <body ontouchstart>

    <header class="weui-flex " style="text-align: center;">
      <div class="demos-title-left">
        <div class="weui-grid__icon">
          <span class="mui-icon mui-icon-back"></span>
        </div>
      </div>
      <div class="weui-flex__item demos-title-center">
      <c:if test="${empty certificateData.certType}">新建证书</c:if>
      <c:if test="${!empty certificateData.certType}">${certificateData.certType.description}</c:if>
       - ${shipInfoData.shipName}
      </div>
      <div class="demos-title-right">
        <div class="weui-grid__icon">
          <span class="mui-icon mui-icon-bars"></span>
        </div>
      </div>
    </header>

    <div class="bd">
      <div class="page__bd">

        <c:if test="${certificateData.id > 0}">
        <div class="weui-cells__title">证书内容</div>
        <div class="weui-cells weui-cells_form">
          <div class="weui-cell">
            <div class="weui-cell__bd">
              <div class="weui-uploader">
                <div class="weui-uploader__hd">
                  <p class="weui-uploader__title">图片</p>
                  <c:set var="imgCount" value="0" />
                  <c:if test="${!empty certificateData.certAttaDatas}">
                  <c:set var="imgCount" value="${fn:length(certificateData.certAttaDatas)}" />
                  </c:if>
                  <div class="weui-uploader__info">0/${imgCount}</div>
                </div>
                <div class="weui-uploader__bd">
                  <ul class="weui-uploader__files" id="uploaderFiles">
                    <c:forEach var="certAttaData" items="${certificateData.certAttaDatas}">
                    <li class="weui-uploader__file certAttaImg" idVal="${certAttaData.id}" style="background-image:url(${ctx}/hyquan/download/imageDownload?url=${certAttaData.smallImgUrl})"></li>
                    </c:forEach>
                  </ul>
                  <form method="post" id="certAttaForm" action="" enctype="multipart/form-data">
                  <div class="weui-uploader__input-box">
                    <input id="certId" type="hidden" name="certId" value="${certificateData.id}">
                    <input id="uploaderInput" class="weui-uploader__input" name="imgFiles" accept="image/gif,image/jpeg,image/jpg,image/png" multiple="">
                  </div>
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
        </c:if>

        <div class="weui-cells__title">船舶属性</div>
        <div class="weui-cells">
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">船名</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">${shipInfoData.shipName}</div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">船舶MMSI</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">${shipInfoData.mmsi}</div>
          </div>
        </div>

        <div class="weui-cells__title">证书属性</div>
        <div class="weui-cells weui-cells_form">
          <form method="post" id="certificateForm" action="">
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">证书名称</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input name="id" type="hidden" value="${certificateData.id}">
              <input name="shipId" type="hidden" value="${certificateData.shipId}">
              <input id="certType" name="certType" type="hidden" value="${certificateData.certType}">
              <input class="weui-input" name="certTypeWeui" onchange="$('#certType').val($(this).attr('data-values'))" type="text" data-values="${certificateData.certType}" value="${certificateData.certType.description}" placeholder="点这里选择证书名称" readonly="readonly">
            </div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">证书编号</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input class="weui-input" id="certNo" name="certNo" type="text" value="${certificateData.certNo}" placeholder="请输入证书编号">
            </div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">发证日期</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input class="weui-input" id="issueDate" name="issueDate" type="text" value="${certificateData.issueDate}" placeholder="请输入发证日期">
            </div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">到期日期</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input class="weui-input" id="maturityDate" name="maturityDate" type="text" value="${certificateData.maturityDate}" placeholder="请输入到期日期">
            </div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">提醒日期</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input class="weui-input" id="remindDate" name="remindDate" type="text" value="${certificateData.remindDate}" placeholder="请输入提醒日期">
            </div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">备注</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <textarea class="weui-textarea" name="remark" rows="3" placeholder="请输入备注">${certificateData.remark}</textarea>
              <div class="weui-textarea-counter"><span>0</span>/200</div>
            </div>
          </div>
          <%-- <div class="weui-cell weui-cell_switch">
            <div class="weui-cell__bd">公开性</div>
            <div class="weui-cell__ft">
              <input type="hidden" id="opened" name="opened" value="${certificateData.opened}">
              <input class="weui-switch" type="checkbox" id="openedWeui" name="openedWeui" <c:if test="${certificateData.opened=='yes'}">checked="checked"</c:if>>
            </div>
          </div> --%>
          </form>
        </div>

        <div class="weui-btn-area">
          <a class="weui-btn weui-btn_primary" href="javascript:" id="saveCertificate">保存</a>
        </div>

      </div>
    </div>

    <div class="demos-content-padded" style="display:none;">
      <a href="javascript:;" id="popup" class="weui-btn weui-btn_primary open-popup" data-target="#half">popup</a>
    </div>
    <div id="half" class='weui-popup__container popup-bottom'>
      <div class="weui-popup__overlay"></div>
      <div class="weui-popup__modal">
        <div class="toolbar">
          <div class="toolbar-inner">
            <a href="javascript:;" class="picker-button close-popup">关闭</a>
            <h1 class="title">证书分享</h1>
          </div>
        </div>
        <div class="modal-content">
          <div class="weui-grids">
            <a href="javascript:;" class="weui-grid js_grid" data-id="dialog">
              <div class="weui-grid__icon">
                <img src="${ctx}/hyqh5/demos/images/icon_nav_dialog.png" alt="">
              </div>
              <p class="weui-grid__label">
                发布
              </p>
            </a>
            <a href="javascript:;" class="weui-grid js_grid" data-id="progress">
              <div class="weui-grid__icon">
                <img src="${ctx}/hyqh5/demos/images/icon_nav_progress.png" alt="">
              </div>
              <p class="weui-grid__label">
                编辑
              </p>
            </a>
            <a href="javascript:;" class="weui-grid js_grid" data-id="msg">
              <div class="weui-grid__icon">
                <img src="${ctx}/hyqh5/demos/images/icon_nav_msg.png" alt="">
              </div>
              <p class="weui-grid__label">
                保存
              </p>
            </a>
            <a href="javascript:;" class="weui-grid js_grid" data-id="dialog">
              <div class="weui-grid__icon">
                <img src="${ctx}/hyqh5/demos/images/icon_nav_dialog.png" alt="">
              </div>
              <p class="weui-grid__label">
                发布
              </p>
            </a>
            <a href="javascript:;" class="weui-grid js_grid" data-id="progress">
              <div class="weui-grid__icon">
                <img src="${ctx}/hyqh5/demos/images/icon_nav_progress.png" alt="">
              </div>
              <p class="weui-grid__label">
                编辑
              </p>
            </a>
            <a href="javascript:;" class="weui-grid js_grid" data-id="msg">
              <div class="weui-grid__icon">
                <img src="${ctx}/hyqh5/demos/images/icon_nav_msg.png" alt="">
              </div>
              <p class="weui-grid__label">
                保存
              </p>
            </a>
          </div>
        </div>
      </div>
    </div>

    <script src="${ctx}/hyqh5/lib/jquery-2.1.4.js"></script>
    <script src="${ctx}/hyqh5/lib/jquery.form-3.51.js"></script>
    <script src="${ctx}/hyqh5/lib/jquery.validate.js"></script>

    <script src="${ctx}/hyqh5/lib/fastclick.js"></script>
    <script src="${ctx}/hyqh5/js/jquery-weui.js"></script>
    <script src="${ctx}/hyqh5/js/swiper.js"></script>

    <script>
      $(function() {
        FastClick.attach(document.body);
      });

      $(document).on("click", ".demos-title-left", function() {
        window.location.href = "${ctx}/hyqh5/shipinfo/shipCertEdit?shipId=${shipInfoData.id}";
      });

      $(document).on("click", ".demos-title-right", function() {
        $.actions({
        title: "选择操作",
        onClose: function() {
          console.log("close");
        },
        actions: [
        	  /* {
            text: "分享2",
            className: "color-warning",
            onClick: function() {
              $('#popup').trigger("click");
            }
          }, */
          {
            text: "分享",
            className: "color-warning",
            onClick: function() {
              //window.location.href = "${ctx}/hyqh5/shipinfo/certShareQrcode?shipId=${shipInfoData.id}&certId=${certificateData.id}";
              var msg = "${shipInfoData.shipName}("+"${certificateData.certType.description})";
              msg += "${shareUrl}";
              window.androidJava.shareTo(msg);
            }
          },
          {
            text: "删除",
            className: 'color-danger',
            onClick: function() {
              $.confirm("删除证书信息将会连同全部附件资料一起删除。", "你确认删除吗?", function() {
                $.ajax({
                    method : "GET",
                    data : {
                        shipId : "${shipInfoData.id}",
                        certId : "${certificateData.id}"
                    },
                    url : "${ctx}/hyqh5/shipinfo/deleteCertificate",
                    datatype : "json",
                    success : function(data) {
                      var returnCode = $(data)[0].returnCode;
                      var message = $(data)[0].message;
                      if (returnCode == "Failure") {
                        $.alert(message, "错误！");
                        return false;
                      } else {
                        $.toast("船舶信息及其附属信息已经全部删除！");
                        window.location.href = "${ctx}/hyqh5/shipinfo/myShipList";
                        return true;
                      }
                    }
                  });
              }, function() {
                //取消操作
              });
            }
          }
        ]
        });
      });

      $("#certificateForm").validate({
        rules:{
    			issueDate:{
    				required:true
    			}
    			/* maturityDate:{
    				required:true
    			},
    			remindDate:{
    				required:true
    			} */
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

      $(document).on("click", "#saveCertificate", function() {
    	      if ($("#certificateForm").valid())
          $("#certificateForm").ajaxSubmit({
              method: "post",
              url: "${ctx}/hyqh5/shipinfo/saveCertificate",
              datatype: "json",
              success: function(data) {
                  var returnCode = $(data)[0].returnCode;
                  var message = $(data)[0].message;
                  if (returnCode == "Failure") {
                      $.alert(message, "失败！");
                      return false;
                  } else {
                	      var certId = $(data)[0].content;
                      $.alert(message, "成功！");
                      window.location.href = "${ctx}/hyqh5/shipinfo/certificateEdit?certId="+certId+"&certType=${certType}&shipId=${shipInfoData.id}";
                      return true;
                  }
              }
          });
      });

      $("#issueDate").calendar({
        onChange: function (p, values, displayValues) {
          console.log(values, displayValues);
        }
      });

      $("#maturityDate").calendar({
        onChange: function (p, values, displayValues) {
          console.log(values, displayValues);
        }
      });

      $("#remindDate").calendar({
        onChange: function (p, values, displayValues) {
          console.log(values, displayValues);
        }
      });

      $(document).on("change", "#openedWeui", function() {
        if ($("#openedWeui").get(0).checked) {
              $("#opened").val("yes");
        } else {
              $("#opened").val("no");
        }
      });

      $(document).on("change", "#statusWeui", function() {
        if ($("#statusWeui").get(0).checked) {
              $("#status").val("publish");
        } else {
              $("#status").val("unpublish");
        }
      });
      
      $("#certTypeWeui").select({
          title: "选择证书名称",
          items: [
            <c:forEach var="ct" items="${certTypes}">
            {
              title: "${ct.description}",
              value: "${ct}",
            },
            </c:forEach>
          ]
        });


      function show(content) {
    	      var redata = eval('(' + content + ')');
          var returnCode = redata.returnCode;
          var message = redata.message;
          var cads = redata.content;
          if (returnCode == "Failure") {
              $.alert(message, "失败！");
              return false;
          } else {
              // $.alert(message, "成功！");
              if (cads != null && cads.length > 0) {
                for (var i=0;i<cads.length;i++) {
                  var s = '<li class="weui-uploader__file pb1" idVal="'+cads[i].id+'" style="background-image:url(${ctx}/hyquan/download/imageDownload?url='+cads[i].smallImgUrl+')"></li>';
                  $("#uploaderFiles").append(s);
                }
              }
              return true;
          }
      }
      
      $(document).on("click", "#uploaderInput", function() {
    	    var url = "http://${header['host']}${ctx}/hyqh5/shipinfo/uploadCertAttaImgs";
    	    var inParams = "{certId : '${certificateData.id}', uiFileName : 'imgFile'}";

    	    window.androidJava.uploadImages(url, inParams);
    	    // window.androidJava.uploadImage(url, inParams);
    	  
          /* $("#certAttaForm").ajaxSubmit({
              method: "post",
              url: "${ctx}/hyqh5/shipinfo/uploadCertAttaImg",
              datatype: "json",
              success: function(data) {
                  var redata = eval('(' + data + ')');
                  var returnCode = redata.returnCode;
                  var message = redata.message;
                  var cads = redata.content;
                  if (returnCode == "Failure") {
                      $.alert(message, "失败！");
                      return false;
                  } else {
                      // $.alert(message, "成功！");
                      if (cads != null && cads.length > 0) {
                        for (var i=0;i<cads.length;i++) {
                          var s = '<li class="weui-uploader__file pb1" idVal="'+cads[i].id+'" style="background-image:url(${ctx}/hyquan/download/imageDownload?url='+cads[i].url+')"></li>';
                          $("#uploaderFiles").append(s);
                        }
                      }
                      return true;
                  }
              }
          }); */
      });

      $(document).on("click", ".certAttaImg", function() {
        var id = $(this).attr("idVal");
        window.location.href = "${ctx}/hyqh5/shipinfo/certAttaImg?shipId=${shipInfoData.id}&certId=${certificateData.id}&attaId=" + id;
      });
      
    </script>

  </body>
</html>

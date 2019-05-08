<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title> - 国产simditor富文本编辑器</title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link rel="shortcut icon" href="favicon.ico"> <link href="${ctx}/hyqback/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${ctx}/hyqback/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="${ctx}/hyqback/css/animate.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${ctx}/hyqback/css/plugins/simditor/simditor.css" />
    <link href="${ctx}/hyqback/css/style.css?v=4.1.0" rel="stylesheet">

</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content">

        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h3>过滤词</h3>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="dropdown-toggle" data-toggle="dropdown" href="form_editors.html#">
                                <i class="fa fa-wrench"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-user">
                                <li><a href="form_editors.html#">选项1</a>
                                </li>
                                <li><a href="form_editors.html#">选项2</a>
                                </li>
                            </ul>
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
						<input type="hidden" name="id" value="${filterWordsData.id}" />
                        <textarea id="editor" placeholder="这里输入内容" autofocus>
                           ${filterWordsData.filterWords}
                        </textarea>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 全局js -->
    <script src="${ctx}/hyqback/js/jquery.min.js?v=2.1.4"></script>
    <script src="${ctx}/hyqback/js/bootstrap.min.js?v=3.3.6"></script>



    <!-- 自定义js -->
    <script src="${ctx}/hyqback/js/content.js?v=1.0.0"></script>


    <!-- simditor -->
    <script type="text/javascript" src="${ctx}/hyqback/js/plugins/simditor/module.js"></script>
    <script type="text/javascript" src="${ctx}/hyqback/js/plugins/simditor/uploader.js"></script>
    <script type="text/javascript" src="${ctx}/hyqback/js/plugins/simditor/hotkeys.js"></script>
    <script type="text/javascript" src="${ctx}/hyqback/js/plugins/simditor/simditor.js"></script>
    <script>
        $(document).ready(function () {
            var editor = new Simditor({
                textarea: $('#editor'),
                //defaultImage: 'img/a9.jpg'
            });
        });
            //var a = ${filterWordsData.filterWords};
            //alert(a);
    </script>

    
    
</body>

</html>

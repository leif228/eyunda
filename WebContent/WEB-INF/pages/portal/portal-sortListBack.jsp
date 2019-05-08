<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>前台浏览 - 船舶类别列表</title>
<meta http-equiv="X-UA-Compatible" content="IE=9" >
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="" />
<meta name="author" content="stilearning" />

<link href="${ctx}/css/bootstrap.css" rel="stylesheet" />
<link href="${ctx}/css/bootstrap-responsive.css" rel="stylesheet" />
<link href="${ctx}/css/stilearn.css" rel="stylesheet" />
<link href="${ctx}/css/stilearn-responsive.css" rel="stylesheet" />
<link href="${ctx}/css/stilearn-helper.css" rel="stylesheet" />
<link href="${ctx}/css/stilearn-icon.css" rel="stylesheet" />
<link href="${ctx}/css/font-awesome.css" rel="stylesheet" />
<link href="${ctx}/css/animate.css" rel="stylesheet" />
<link href="${ctx}/css/uniform.default.css" rel="stylesheet" />

<script src="${ctx}/js/jquery.js"></script>
<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
  <script src="${ctx}/js/html5.js"></script>
<![endif]-->

<style>
/* this use for demo knob only, you can delete this if you want*/
.demo-knob {
	text-align: center;
	margin-top: 10px;
	margin-bottom: 10px;
}
body{
	font-size:12px;
}
.layout-right, .fr {float: right;}
.dn{display:none}
.fl{float:left;}
.clearfix:after {visibility: hidden;display: block;font-size: 0px;content: " ";clear: both;height: 0px;}
.arrow, .arrow .s {
	display: block;
	font-size: 0;
	line-height: 0;
	width: 0;
	height: 0;
	border: 4px dashed transparent;
}

.arrow, .arrow .s {
    display: block;
    font-size: 0px;
    line-height: 0;
    width: 0px;
    height: 0px;
    border: 4px dashed transparent;
}
.arrowT, .arrowT .s {
    border-bottom-color: #BF3019;
    border-bottom-style: solid;
}
.arrowT .s {
    position: absolute;
    left: -5px;
    top: -3px;
}

.arrowB, .arrowB .s {
	border-top-color: #bf3019;
	border-top-style: solid;
	border-width: 5px;
}
a:link, a:visited {text-decoration: none;}

.show-choosed,.fil-item-list{margin-bottom:10px; }
.goods-infos{padding:20px;}
.goods-filter{margin-bottom:10px;font-size:13px;}
.goods-filter .fil-item-list dt.label{min-width:78px;_width:78px;text-align:right;line-height: 20px;}
.choose-filist{border-bottom:1px solid #f5f5f5;padding-bottom:12px;}
.choose-filist .label,#act-reset .act-btn{height:30px;line-height:30px; }
.choose-filist .choose-items{width:555px;padding-top:5px;}
.choose-filist .choose-items a{background:#ffecdf;color:#bf3019;padding:2px 10px;margin:0 10px 8px 0;}
.choose-filist .choose-items .sign{color:#d04d31;}
#act-reset .act-btn{width:118px;}
.fil-item-list .fil-selected{background:#444;color:#fff!important;}
.fil-item-list .fil-name{float:left;padding:2px 5px;height:16px;line-height:16px;min-width:42px;_width:42px;text-align:left;margin:0 10px 10px 0;white-space:nowrap;display:block;}
/* .colors-list .fil-name-wrap .fil-name{padding:0;width:18px;min-width:18px;height:18px;line-height:18px;border:1px solid #aeaeae;text-indent:-9999px;} */
/* 颜色块 */
.colors-list .color2{background:#00ced1;}
.fil-item-list .fil-name-wrap{float:left;min-width:540px;width:800px;overflow:hidden;}
a.more-btn{width:52px;padding-right:8px;text-align:center;height:18px;position:relative;line-height:18px;background:#7c7c7c;color:#fff!important;}
.open-more-filter{border-top:1px solid #f5f5f5;padding-top:12px;}
a.more-btn:hover{text-decoration:none;}
.arrowT, .arrowT .s{border-width: 5px;}
.more-btn .arrowB{position:absolute;border-top-color: #fff;right:8px;top:7px;}
.more-btn .arrowB .s{position:absolute;right:-5px;top:-7px;border-top-color: #7c7c7c;}
.more-btn .arrowT{position:absolute;right:8px;top:1px;border-bottom-color: #fff;}
.more-btn .arrowT .s{position:absolute;left:-5px;top:-3px;border-bottom-color: #7c7c7c;}
.open-more-filter .more-btn{width:84px;padding:2px 8px 2px 0;background:#bf3019;}
.open-more-filter .more-btn .arrowB .s{border-top-color: #bf3019;top: -7px;}
.open-more-filter .more-btn .arrowB{right:10px;top:9px;}
.open-more-filter .more-btn .arrowT .s{position:absolute;left:-5px;top:-3px;border-bottom-color: #BF3019;}

.sidebarfix{
	position/*\**/: static\9;
}

</style>
</head>

<body>
	<!-- section header -->
	<jsp:include page="portal-head.jsp"></jsp:include>

	<!-- section content -->
	<section class="section sidebarfix">
		<div class="row-fluid">
			<!-- span side-left -->
			<div class="span1">
				<!--side bar-->
				<jsp:include page="portal-sideleft.jsp"></jsp:include>
				<!--/side bar -->
			</div>
			<!-- span side-left -->

			<!-- span content -->
			<div class="span9">
				<!-- content -->
				<div class="content">
					<!-- content-header -->
					<div class="content-header">
						<h3>
							<!--  <i class="icofont-home"></i>分类： <a class="link" href="#">船舶</a>&nbsp;&gt;&nbsp;
							<a class="link" href="#">集装箱</a>&nbsp;&gt;&nbsp; <a class="link"
								href="#">中型</a>&nbsp;&gt;&nbsp;-->
								
								<i class="icofont-home"></i>区域：
								<c:forEach var="bigAreaCode" items="${bigAreaCodes}">
									<c:choose>
					              		<c:when test="${bigAreaCode==selectBigAreaCode }">
					              			<a class="link" href="${ctx}/portal/home/sortList?bigAreaCode=${bigAreaCode.code}">${bigAreaCode.fullName}</a>&nbsp;&nbsp;
					              		</c:when>
					              		<c:otherwise>
					              			<a href="${ctx}/portal/home/sortList?bigAreaCode=${bigAreaCode.code}">${bigAreaCode.fullName}</a>&nbsp;&nbsp;
					              		</c:otherwise>
				              		</c:choose>
								</c:forEach> 
								
						</h3>
					</div>
					<!-- /content-header -->

					<!-- content-breadcrumb -->
					<div class="content-breadcrumb">
						<!--breadcrumb-->
						<ul class="breadcrumb bold" style="font-size: 12px;padding-left:20px;background:#eee">
						
							<c:forEach var="uncleData" items="${uncleDatas}">
							
								<li><a href="#">${uncleData.typeName}:</a> <span class="divider"></span></li>
								<c:forEach var="childrenData" items="${uncleData.childrenDatas}">
									<c:choose>
					              		<c:when test="${!empty selectTypeData && childrenData.typeCode==selectTypeData.typeCode }">
					              			<li><a href="${ctx}/portal/home/sortList?attrValueCode=${childrenData.typeCode}&bigAreaCode=${selectBigAreaCode.code}"><span class="color-teal">${childrenData.typeName}</span></a> <span class="divider"></span></li>
					              		</c:when>
					              		<c:otherwise>
					              			<li><a href="${ctx}/portal/home/sortList?attrValueCode=${childrenData.typeCode}&bigAreaCode=${selectBigAreaCode.code}">${childrenData.typeName}</a> <span class="divider"></span></li>
					              		</c:otherwise>
				              		</c:choose>
								</c:forEach>
				              	
							</c:forEach>
						</ul>
						<!--/breadcrumb-->
					</div>
					<!-- /content-breadcrumb -->
					
					
					<div class="goods-infos">
						<!-- goods-filter -->
						<div class="goods-filter">
							<div id="filters" class="filter-list-box">
							
						<c:forEach var="attrNameData" items="${typeAttrNameDatas}"> 
							
							<c:if test="${attrNameData.attrType=='charcode'}">
								
							<dl class="fil-item-list clearfix">
								<dt class="label fl">
									${attrNameData.attrName}
								</dt>
								<dd class="fl fil-item">
									<a class="fil-name fil-all fil-selected" href="#"><span>不限</span></a>
									<div class="fil-name-wrap clearfix">
										
												<c:forEach var="attrValueData" items="${attrNameData.attrValueDatas}">
													<c:choose>
									              		<c:when test="${!empty selectAttrValueData && selectAttrValueData.attrValueCode == attrValueData.attrValueCode}">
									              			<a class="fil-name" href="${ctx}/portal/home/sortList?attrValueCode=${attrValueData.attrValueCode}&bigAreaCode=${selectBigAreaCode.code}">
																<span  style="color:red">${attrValueData.attrValue}</span>
															</a>
									              		</c:when>
									              		<c:otherwise>
									              			<a class="fil-name" href="${ctx}/portal/home/sortList?attrValueCode=${attrValueData.attrValueCode}&bigAreaCode=${selectBigAreaCode.code}">
																<span>${attrValueData.attrValue}</span>
															</a>
									              		</c:otherwise>
								              		</c:choose>
												
												</c:forEach>
										
										<!-- 
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0101">
												<span>Kiss</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0102">
												<span>Kitty</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0103">
												<span>接吻猫</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0104">
												<span>莱尔斯丹</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0105">
												<span>奥卡索</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0106">
												<span>卡文</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0107">
												<span>tigrisso</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0108">
												<span>ZsaZsaZsu</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0109">
												<span>ecco</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0110">
												<span>哈森</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0111">
												<span>卡迪娜</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0112">
												<span>BELVIO</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0113">
												<span>CLARKS</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0114">
												<span>Cele</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0115">
												<span>JEEP</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0116">
												<span>crocs</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0117">
												<span>LYRIQUE</span>
											</a>
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0101">
												<span>Kiss</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0102">
												<span>Kitty</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0103">
												<span>接吻猫</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0104">
												<span>莱尔斯丹</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0105">
												<span>奥卡索</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0106">
												<span>卡文</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0107">
												<span>tigrisso</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0108">
												<span>ZsaZsaZsu</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0109">
												<span>ecco</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0110">
												<span>哈森</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0111">
												<span>卡迪娜</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0112">
												<span>BELVIO</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0113">
												<span>CLARKS</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0114">
												<span>Cele</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0115">
												<span>JEEP</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0116">
												<span>crocs</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0117">
												<span>LYRIQUE</span>
											</a>
											
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0118">
												<span>爱旅儿</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0119">
												<span>VANILLA</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0120">
												<span>MOLA</span>
											</a> -->
										
										<div class="bk"></div>
									</div>
									<div class="bk"></div>
								</dd>
								<dd class="fr more dn"><a class="fr more-btn" href="javascript:void(0);">更多<span class="arrow arrowB"><s class="s"></s></span></a></dd>
							</dl>
						</c:if>
						
						</c:forEach>
						<!--  
							<dl class="fil-item-list clearfix">
								<dt class="label fl">
									色系
								</dt>
								<dd class="fl fil-item">
									<a class="fil-name fil-all fil-selected" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402"><span>不限</span></a>
									<div class="fil-name-wrap clearfix">
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0201">
												<span>黑色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0202">
												<span>棕色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0203">
												<span>红色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0204">
												<span>蓝色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0205">
												<span>黄色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0206">
												<span>白色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0207">
												<span>橙色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0208">
												<span>绿色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0209">
												<span>灰色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0210">
												<span>裸色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0211">
												<span>粉色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0212">
												<span>金色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0213">
												<span>紫色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0214">
												<span>银色</span>
											</a>
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0201">
												<span>黑色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0202">
												<span>棕色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0203">
												<span>红色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0204">
												<span>蓝色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0205">
												<span>黄色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0206">
												<span>白色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0207">
												<span>橙色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0208">
												<span>绿色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0209">
												<span>灰色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0210">
												<span>裸色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0211">
												<span>粉色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0212">
												<span>金色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0213">
												<span>紫色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0214">
												<span>银色</span>
											</a>
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0201">
												<span>黑色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0202">
												<span>棕色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0203">
												<span>红色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0204">
												<span>蓝色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0205">
												<span>黄色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0206">
												<span>白色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0207">
												<span>橙色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0208">
												<span>绿色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0209">
												<span>灰色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0210">
												<span>裸色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0211">
												<span>粉色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0212">
												<span>金色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0213">
												<span>紫色</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0214">
												<span>银色</span>
											</a>										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0215">
												<span>多色</span>
											</a>
										
										<div class="bk"></div>
									</div>
									<div class="bk"></div>
								</dd>
								<dd class="fr more dn"><a class="fr more-btn" href="">更多<span class="arrow arrowB "><s class="s"></s></span></a></dd>
							</dl>
						
							<dl class="fil-item-list clearfix">
								<dt class="label fl">
									价格
								</dt>
								<dd class="fl fil-item">
									<a class="fil-name fil-all fil-selected" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402"><span>不限</span></a>
									<div class="fil-name-wrap clearfix">
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0301">
												<span>100元-200元</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0302">
												<span>200元-300元</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0303">
												<span>300元-400元</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0304">
												<span>400元-500元</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0305">
												<span>500元-800元</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0306">
												<span>800元-1000元</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0307">
												<span>1000元-2000元</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0308">
												<span>2000元-5000元</span>
											</a>
										
										<div class="bk"></div>
									</div>
									<div class="bk"></div>
								</dd>
								<dd class="fr more dn"><a class="fr more-btn" href="">更多<span class="arrow arrowB "><s class="s"></s></span></a></dd>
							</dl>
						
							<dl class="fil-item-list clearfix">
								<dt class="label fl">
									风格
								</dt>
								<dd class="fl fil-item">
									<a class="fil-name fil-all fil-selected" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402"><span>不限</span></a>
									<div class="fil-name-wrap clearfix">
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0401">
												<span>休闲</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0402">
												<span>商务休闲</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0403">
												<span>简约</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=04_0404">
												<span>经典</span>
											</a>
										
										<div class="bk"></div>
									</div>
									<div class="bk"></div>
								</dd>
								<dd class="fr more dn"><a class="fr more-btn" href="">更多<span class="arrow arrowB "><s class="s"></s></span></a></dd>
							</dl>
						
							<dl class="fil-item-list clearfix" style="display: none;">
								<dt class="label fl">
									闭合方式
								</dt>
								<dd class="fl fil-item">
									<a class="fil-name fil-all fil-selected" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402"><span>不限</span></a>
									<div class="fil-name-wrap clearfix">
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=0402_0101">
												<span>系带</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=0402_0102">
												<span>套脚</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=0402_0103">
												<span>其他</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=0402_0104">
												<span>魔术贴</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=0402_0105">
												<span>扣带</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=0402_0106">
												<span>搭扣</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=0402_0107">
												<span>拉链</span>
											</a>
										
											<a class="fil-name" href="http://localhost:8080/meetlan/wares/sortList.jspx?typeCode=0402&amp;attrCode=0402_0108">
												<span>松紧</span>
											</a>
										
										<div class="bk"></div>
									</div>
									<div class="bk"></div>
								</dd>
								<dd class="fr more dn"><a class="fr more-btn" href="">更多<span class="arrow arrowB "><s class="s"></s></span></a></dd>
							</dl>-->
						

						
						</div>
						<div class="open-more-filter clearfix">
							<div class="fr toggle-row dn" style="display: block;">
								<a class="fr more-btn act-toggle" href=""><span class="txt">展开更多</span><span class="arrow arrowB"><s class="s"></s></span></a>
							</div>
						</div>
					<!-- /goods-filter -->
					</div>

					<!-- content-body -->
					<div class="content-body">
						<!--row-fluid-->
						<div class="row-fluid">
							<!--span-->
							<div class="span12">
								<!--box-->
								<div class="box corner-all">
									<!--box header-->
									<div class="box-header grd-white color-silver-dark corner-top">
										<span>船舶列表</span>
									</div>
									
									<!--box body-->
									<div class="box-body">
										<!-- use hr as divider -->
									    <style>
									    .demo-knob{
									    	height:230px;
									    	width:220px;
									    	overflow:hidden;
									    	text-align:left;
									    }
									    .demo-knob .shipKeyWords span{
									        width:220px;
									    	text-overflow:ellipsis; 
									    	white-space:nowrap;
									    	overflow:hidden;"
									    }
									    </style>
										<div class="row-fluid">
											<c:forEach var="shipData" items="${pageData.result}"  varStatus="status">
												
												<div class="span3 demo-knob" >
													<a href="${ctx}/portal/home/shipDetail?shipId=${shipData.id}" target="_blank"> 
														<img style="width: 220px; height: 150px;" src="${ctx}/download/imageDownload?url=${shipData.shipLogo}" title="profile" alt="profile" />
													</a>
													<div>
														<span class="bold">${shipData.shipName}</span>
														<span style="float:right">载货量:${shipData.tons}吨</span>
													</div>
													<div class="shipKeyWords">
														<span title="${shipData.keyWords}">${shipData.keyWords}</span>
													</div>
													<c:if test="${fn:length(shipData.myShipPriceDatas)>0}">
													<div class="shipKeyWords">
														<span title="${shipData.myShipPriceDatas[0].startPortData.fullName} 到 ${shipData.myShipPriceDatas[0].endPortData.fullName}">
														${shipData.myShipPriceDatas[0].startPortData.fullName} 到 ${shipData.myShipPriceDatas[0].endPortData.fullName}</span></div>
													<div>
														报价:<span class="color-red">${shipData.myShipPriceDatas[0].intTransFeeDes}</span>
														<span style="float:right">截止:${shipData.myShipPriceDatas[0].periodTime}</span>
													</div>
													</c:if>
												</div>
										<c:if test="${(status.index+1)%4 == 0}">	 
										</div>
										<div class="divider-content">
											<span></span>
										</div>
										<div class="row-fluid">
										</c:if>
										</c:forEach>
										</div>
									

										<!--/knob-->
									</div>

									<div class="gallery-controls bottom">
										<div class="gallery-category">
											<div class="pull-right">
												<span>页号 ${pageData.pageNo} of ${pageData.totalPages }</span>
											</div>
											<div class="portfolio-filter">
												<form action="${ctx}/portal/home/sortList" id="shipSortForm" method="post">
													<c:choose>
									              		<c:when test="${!empty selectAttrValueData}">
									              			<input type="hidden"  name="attrValueCode" value="${selectAttrValueData.attrValueCode }"/>
									              			<input type="hidden"  name="bigAreaCode" value="${selectBigAreaCode.code}"/>
									              		</c:when>
									              		<c:otherwise>
									              			<input type="hidden"  name="attrValueCode" value="${selectTypeData.typeCode }"/>
									              			<input type="hidden"  name="bigAreaCode" value="${selectBigAreaCode.code}"/>
									              		</c:otherwise>
								              		</c:choose>
													<jsp:include page="./ship-sort.jsp"></jsp:include>
												</form>
											</div>
										</div>

										<div>
											<form action="${ctx}/portal/home/sortList" id="pageform" method="post">
												<c:choose>
								              		<c:when test="${!empty selectAttrValueData}">
								              			<input type="hidden"  name="attrValueCode" value="${selectAttrValueData.attrValueCode }"/>
								              			<input type="hidden"  name="bigAreaCode" value="${selectBigAreaCode.code}"/>
								              		</c:when>
								              		<c:otherwise>
								              			<input type="hidden"  name="attrValueCode" value="${selectTypeData.typeCode }"/>
								              			<input type="hidden"  name="bigAreaCode" value="${selectBigAreaCode.code}"/>
								              		</c:otherwise>
							              		</c:choose>
												<jsp:include page="./pager.jsp"></jsp:include>
											</form>
										</div> 

										
									</div>

								</div>
							</div>
						</div>
					</div>
					
					<!--/content-body -->
				</div>
				
				
				<!-- /content -->
			</div>
			<!-- /span content -->
			
			<!-- span side-right -->
			<div class="span2">
				<!-- side-right -->
				<aside class="side-right">
					<!-- sidebar-right -->
					<div class="sidebar-right">

						<!-- sidebar-right-content -->
						<div class="sidebar-right-content">
							<div class="tab-content">
								<!--contact-->
								<div class="tab-pane fade active in" id="contact"
									style="font-size: 12px;">
									<div class="side-contact">

										<!--contact-control-->
										<div class="contact-control">
											<div class="btn-group pull-right">
												<button class="dropdown-toggle bg-transparent no-border"
													data-toggle="dropdown">
													<i class="icofont-caret-down"></i>
												</button>
												<ul class="dropdown-menu">
													<li><a href="#"><i
															class="icofont-certificate color-green"></i>内河</a></li>
													<li><a href="#"><i
															class="icofont-certificate color-silver-dark"></i>沿海</a></li>
													<li><a href="#"><i
															class="icofont-certificate color-red"></i>远洋</a></li>
													<li><a href="#"><i
															class="icofont-certificate color-orange"></i>陆路</a></li>
												</ul>
											</div>
											<h5>
												<i class="icofont-comment color-teal"></i> 联系人
											</h5>
										</div>
										<!--/contact-control-->
										<!--contact-search-->
										<div class="contact-search">
											<div class="input-icon-prepend">
												<div class="icon">
													<button type="submit">
														<i class="typicn-message color-silver-dark"></i>
													</button>
												</div>
												<input class="input-block-level grd-white" maxlength="11"
													type="text" name="contact-search"
													placeholder="chat with..." />
											</div>
										</div>
										<!--/contact-search-->
										<!--contact-list, we set this max-height:380px, you can set this if you want-->
										<ul class="contact-list">
											<c:forEach var="releaseOperatorData" items="${releaseOperatorDatas}">
											<li class="contact-alt grd-white">
												<a href="${ctx}/space/chat/show?toUserId=${releaseOperatorData.operatorData.id}" target="mychat"> 
													<div class="contact-item">
														<div class="pull-left">
															<img class="contact-item-object"
																style="width: 32px; height: 32px;"
																src="${ctx}/download/imageDownload?url=${releaseOperatorData.operatorData.userLogo}" />
														</div>
														<div class="contact-item-body">
															<div class="status" title="ofline">
																<i class="icofont-certificate color-silver-dark"></i>
															</div>
															<p class="contact-item-heading bold">${releaseOperatorData.operatorData.trueName} ${releaseOperatorData.operatorData.mobile}</p>
															<p class="contact-item-heading">${releaseOperatorData.operatorData.unitName}</p>
														</div>
													</div>
											</a>
											</li>
										</c:forEach>
											
										</ul>
										<!--/contact-list-->

									</div>
								</div>
								<!--/contact-->

							</div>
						</div>
						<!-- /sidebar-right-content -->
					</div>
					<!-- /sidebar-right -->
				</aside>
				<!-- /side-right -->
			</div>
			
			<!-- /span side-right -->
		</div>
	</section>




	<!-- javascript
        ================================================== -->
    <script src="${ctx}/js/jquery-v.min.js"></script>
	<script src="${ctx}/js/bootstrap.js"></script>
	<script src="${ctx}/js/uniform/jquery.uniform.js"></script>

	<script src="${ctx}/js/knob/jquery.knob.js"></script>
	<script src="${ctx}/js/peity/jquery.peity.js"></script>
	<script src="${ctx}/js/flot/jquery.flot.js"></script>
	<!--[if lte IE 8]><script language="javascript" type="text/javascript" src="${ctx}/js/flot/excanvas.js"></script><![endif]-->
	<script src="${ctx}/js/flot/jquery.flot.categories.js"></script>
	<script src="${ctx}/js/flot/jquery.flot.symbol.js"></script>
	<script src="${ctx}/js/flot/jquery.flot.crosshair.js"></script>
	<script src="${ctx}/js/flot/jquery.flot.stack.js"></script>
	<script src="${ctx}/js/flot/jquery.flot.pie.js"></script>
	<script src="${ctx}/js/flot/jquery.flot.resize.js"></script>

	<script src="${ctx}/js/flot/jquery.flot.demo.js"></script>

	<!-- required stilearn template js, for full feature-->
	<script src="${ctx}/js/holder.js"></script>
	<script src="${ctx}/js/stilearn-base.js"></script>
	<script src="${ctx}/js/jquery.form-2.63.js"></script>
	<script src="${ctx}/js/portal/portal-sortList.js"></script>
	<script type="text/javascript">
		var _rootPath = "${ctx}";
		var filters=$("#filters"),btnMore=filters.find("dd.more");
		filters.find(".fil-name-wrap").height(function(k,v){
			if(v>60){
				btnMore.eq(k).show();
				return 60;
			}else{
				return "auto";
			}		
		});
		var filItem=function(){
			if(filters.find("dl.fil-item-list").length>=2){
				filters.find("dl.fil-item-list:gt("+1+")").hide();
				filters.next(".open-more-filter").find(".toggle-row").show();
			}
			//<!-- 如果没有筛选条件，隐藏open-more-filter 2013年8月8日 14:12:54 add -->
			if(filters.find("dl.fil-item-list").length<1){
				filters.next(".open-more-filter").hide();
			}
		}
		filItem();
		//点击展开更多;
		$(".more-btn",".toggle-row").on("click",function(){
			var o=$(this),
			txt="展开更多",
			className="arrowB";
			if(o.find(".arrow").hasClass("arrowB")){
				className="arrowT";
				txt="收起更多";
				filters.find("dl.fil-item-list:gt("+1+")").show();
			}else{
				className="arrowB";
				txt="展开更多";
				filters.find("dl.fil-item-list:gt("+1+")").hide();
			}
			o.find(".txt").text(txt).end().find(".arrow").removeClass("arrowB arrowT").addClass(className);
			
			return false;
		});
	 
		
		btnMore.on("click",function(){
			var o=$(this),
			className="arrowB",
			txt="更多",
			h=o.parent().find(".fil-name-wrap").height(function(k,v){
				if(v>60){
					className="arrowB";
					txt="更多";
					return 60;
				}else{
					className="arrowT";
					txt="收起";
					
					return "auto";
				}
			});
			o.find(".txt").text(txt).end().find(".arrow").removeClass("arrowB arrowT").addClass(className);
		    //o.find(".more-text").html(txt);;
			return false;
		});
	
	</script>
</body>
</html>
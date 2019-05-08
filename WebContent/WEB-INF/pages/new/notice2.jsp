<%@page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<style type="text/css">
	.img{width: 50px;width: 40px}
	.myrow{padding: 15px;float: left;}
</style>
<aside class="widget">
	<h3 class="widget-title">
		<i class="fa fa-bars"></i>应用清单
	</h3>
	<div id="hot_post_widget">
		<div class="myrow">
			<div class="contact-box">
                    <a href="${ctx}/hyquan/login/login">
                        <div class="col-sm-4">
                            <div class="text-center">
                                <img alt="image" class="img" src="${ctx}/img/eyq/certificate.png">
                                <div class="m-t-xs font-bold">船舶证书</div>
                            </div>
                        </div>
                    </a>
                    </div>
            </div>
        <div class="myrow">
                    <a href="${ctx}/hyquan/login/login">
                        <div class="col-sm-4">
                            <div class="text-center">
                                <img alt="image" class="img" src="${ctx}/img/eyq/report.png">
                                <div class="m-t-xs font-bold">动态上报</div>
                            </div>
                        </div>
                    </a>
            </div>
         <div class="myrow">
                    <a href="${ctx}/hyquan/login/login">
                        <div class="col-sm-4">
                            <div class="text-center">
                                <img alt="image" class="img" src="${ctx}/img/eyq/insurance.png">
                                <div class="m-t-xs font-bold">保险</div>
                            </div>
                        </div>
                    </a>
            </div>
         <div class="myrow">
                    <a href="${ctx}/hyquan/login/login">
                        <div class="col-sm-4">
                            <div class="text-center">
                                <img alt="image" class="img" src="${ctx}/img/eyq/oil.jpg">
                                <div class="m-t-xs font-bold">船舶加油</div>
                            </div>
                        </div>
                    </a>
            </div>
            <div class="myrow">
                    <a href="${ctx}/hyquan/login/login">
                        <div class="col-sm-4">
                            <div class="text-center">
                                <img alt="image" class="img" src="${ctx}/img/eyq/house.jpg">
                                <div class="m-t-xs font-bold">货仓监控</div>
                            </div>
                        </div>
                    </a>
            </div>
            <div class="myrow">
                    <a href="${ctx}/hyquan/login/login">
                        <div class="col-sm-4">
                            <div class="text-center">
                                <img alt="image" class="img" src="${ctx}/img/eyq/oilcontrol.png">
                                <div class="m-t-xs font-bold">油耗监控</div>
                            </div>
                        </div>
                    </a>
            </div>
	</div>
	<div class="clear"></div>
</aside>

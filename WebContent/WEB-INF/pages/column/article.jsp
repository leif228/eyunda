<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>${userData.trueName}</title>
<meta http-equiv="X-UA-Compatible" content="IE=9">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />

<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="${ctx}/css/font-awesome.css" />
<link rel="stylesheet"
	href="${ctx}/css/bootstrap-datetimepicker.min.css" media="screen" />
<link rel="stylesheet" href="${ctx}/css/reset.css" media="screen" />
<script src="${ctx}/js/jquery-v.min.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/bootstrap-datepicker.js"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript">
	var _rootPath = "${ctx}";
</script>

<style>


</style>
</head>

<body>
	<jsp:include page="./head.jsp"></jsp:include>

	<div class="row">
		<div class="span12">
			<c:forEach items="${pageData.result}" var="cid" varStatus="s">
			
			<div class="article-title"><h3>${cid.title }</h3></div>
			<div class="article-time help-block">发表时间:${cid.releaseTime }</div>
			<div class="article-detail">${cid.content }</div>
		</c:forEach>
		
		<!-- <div class="article-title"><h3>文章标题</h3></div>
			<div class="article-time help-block">发表时间:2015-10-09</div>
			<div class="article-detail"><p></p></div>
			<p>尽管一直在Github上连击看上去似乎是没有多大必要的，但是人总得有点追求。如果正是漫无目的，却又想着提高技术的同时，为什么不去试试？毕竟技术非常好、不需要太多练习的人只是少数，似乎这样的人是不存在的。大多数的人都是经过练习之后，才会达到别人口中的“技术好”。

					这让我想起了充斥着各种气味的知乎上的一些问题，在一些智商被完虐的话题里，无一不是因为那些人学得比别人早——哪来的天才？所谓的天才，应该是未来的智能生命一般，一出生什么都知道。如果并非如此，那只是说明他练习到位了。

					练习不到位便意味着，即使你练习的时候是一万小时的两倍，那也是无济于事的。如果你学得比别人晚，在很长的一段时间里(可能直到进棺材)输给别人是必然的——落后就要挨打。就好像我等毕业于一所二本垫底的学校里，如果在过去我一直保持着和别人(各种重点)一样的学习速度，那么我只能一直是Loser。

					需要注意的是，对你来说考上二本很难，并不是因为你比别人笨。教育资源分配不均的问题，在某种程度上导致了新的阶级制度的出现。如我的首页说的那样:
					THE ONLY FAIR IS NOT FAIR——唯一公平的是它是不公平的。我们可以做的还有很多——CREATE &
					SHARE。真正的不幸是，因为营养不良导致的教育问题。

					于是在想明白了很多事的时候起，便有了Re-Practise这样的计划，而365天只是中间的一个产物。</p>
				<p>
					<strong>编程的基础能力</strong>
				</p>
				<p>

					虽说算法很重要，但是编码才是基础能力。算法与编程在某种程度上是不同的领域，算法编程是在编程上面的一级。算法写得再好，如果别人很难直接拿来复用，在别人眼里就是shit。想出能work的代码一件简单的事，学会对其重构，使之变得更易读就是一件有意义的事。

					于是，在某一时刻在Github上创建了一个组织，叫Artisan
					Stack。当时想的是在Github寻找一些JavaScript项目，对其代码进行重构。但是到底是影响力不够哈，参与的人数比较少。</p>
				<p>
					<strong>重构</strong>
				</p>
				<p>
					如果你懂得如何写出高可读的代码，那么我想你是不需要这个的，但是这意味着你花了更多的时候在思考上了。当谈论重构的时候，让我想起了TDD(测试驱动开发)。即使不是TDD，那么如果你写着测试，那也是可以重构的。(之前写过一些利用Intellij
					IDEA重构的文章：提炼函数、以查询取代临时变量、重构与Intellij Idea初探、内联函数)

					在各种各样的文章里，我们看到过一些相关的内容，最好的参考莫过于《重构》一书。最基础不过的原则便是函数名，取名字很难，取别人能读懂的名字更难。其他的便有诸如长函数、过大的类、重复代码等等。在我有限的面试别人的经历里，这些问题都是最常见的。
				</p>
				<p>
					<strong>测试</strong>
				</p>
				<p>而如果没有测试，其他都是扯淡。写好测试很难，写个测试算是一件容易的事。只是有些容易我们会为了测试而测试。

					在我写EchoesWorks和Lan的过程中，我尽量去保证足够高的测试覆盖率。</p> -->

			</div>
		</div>
		<div class="span12"></div>
	</div>


	<jsp:include page="./foot.jsp"></jsp:include>
</body>
</html>


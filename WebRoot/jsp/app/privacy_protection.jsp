
<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String webTitle = properties.getProperty("webTitle"); 
%>

<!doctype html>
<!--[if lt IE 7]> <html class="ie6 oldie"> <![endif]-->
<!--[if IE 7]>    <html class="ie7 oldie"> <![endif]-->
<!--[if IE 8]>    <html class="ie8 oldie"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="">
<!--<![endif]-->

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
<meta name="keywords" content="小间物业,小间物业管理系统" />
<meta name="Description" content="小间物业,小间物业管理系统" />
<title>隐私权保护规则</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/app.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->

<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>
<script src="${pageContext.request.contextPath }/js/jquery-1.6.js"></script>
</head>
<body>
	<section>
		<div class="content-padding">
			<div class="shop_help-item">
				<div class="shop_help_title"><b>&nbsp;</b>隐私权保护规则</div>
<div class="agreement-content">
<p>本隐私权规则系帮帮就保护用户个人隐私的承诺，鉴于网络的特性，帮帮将无可避免地与您产生直接或间接的互动关系，故特此说明帮帮对用户个人信息所采取的收集、使用和保护政策，请您务必仔细阅读：</p>
<p class="title">一、与个人身份有关的信息</p>
<p>1、当您注册帮帮用户、参加网上或公共论坛等各种活动时，在您的同意及确认下，帮帮将通过注册表格等形式要求您提供一些个人资料。这些个人资料包括：</p>
<p>（1）个人识别资料：如姓名、性别、年龄、出生日期、身份证号码(或护照号码)、电话、通信地址、住址、电子邮件地址等。</p>
<p>（2）个人背景： 职业、教育程度、收入状况、婚姻、家庭状况。</p>
<p>2、请了解，在未经您同意及确认之前，帮帮不会将您为参加帮帮之特定活动所提供的资料利用于特定活动之外的其它目的。如出现以下第五条、第六条规定的情形不在此限。</p>

<p class="title">二、与个人身份无关的信息</p>
<p>我们将通过您的IP地址来收集非个人化的信息，例如您的浏览器性质、操作系统种类、给您提供接入服务的ISP的域名等，以优化在客户终端显示的页面。通过收集上述信息，我们亦进行客流量统计，从而改进帮帮的管理和服务。</p>
<p class="title">三、信息安全</p>
<p>帮帮将对您所提供的资料进行严格的管理及保护，帮帮将使用相应的技术，防止您的个人资料丢失、被盗用或遭篡改。</p>
<p class="title">四、用户权利</p>
<p>您对于自己的个人资料享有以下权利：</p>
<p>1、随时查询及请求阅览；</p>
<p>2、随时请求补充或更正；</p>
<p>3、随时请求删除；</p>
<p>4、请求停止客户终端处理及利用。</p>
<p class="title">五、合理利用原则</p>
<p>帮帮惟在符合下列条件之一，对收集之个人资料进行合理之利用：</p>
<p>1、已取得您的书面或等同于书面的同意；</p>
<p>2、为免除您在生命、身体或财产方面之急迫危险；</p>
<p>3、为防止他人权益之重大危害；</p>
<p>4、为增进公共利益，且无害于您的重大利益；</p>
<p>5、帮帮及其关联公司或合作公司可使用用户的数据，向用户发出直接促销(不论是以帮帮及其关联公司或合作公司的服务内的讯息、透过电邮或其他途径)的信息，提供或宣传帮帮及其关联公司或合作公司的商品和服务及/或经甄选的第三方的商品和服务；</p>
<p>6、帮帮及其关联公司或合作公司为了向用户宣传与用户有关的产品和服务会与广告商共享用户的非个人隐私信息。 第三方公司可能在帮帮收集用户浏览帮帮的行为数据和/或与第三方在帮帮投放的广告有关的数据。第三方公司的数据收集、使用行为将遵守国家有关法律法规的规定以及第三方公司对用户隐私和数据保护的规定；</p>
<p>7、为了运营和改善帮帮的技术和服务，帮帮将可能会自行收集使用或向关联方或合作方提供用户相关信息，这将有助于帮帮向用户提供更好的用户体验和提高帮帮的服务质量。</p>
<p class="title">六、个人资料之披露</p>
<p>当政府机关依照法定程序要求帮帮披露个人资料时，帮帮将根据执法单位之要求或为公共安全之目的提供个人资料。在此情况下之任何披露，帮帮均不承担任何责任。</p>
<p class="title">七、公共论坛</p>
<p>帮帮为您提供聊天室、公告牌等服务。在这些区域内，您公布的任何信息都会成为公开的信息。因此，我们提醒并请您慎重考虑是否有必要在这些区域公开您的个人信息。</p>
<p class="title">八、未成年人隐私权的保护</p>
<p>1、帮帮将建立和维持合理的程序，以保护未成年人个人资料的保密性及安全性。帮帮郑重声明：任何18岁以下的未成年人参加网上活动应事先得到其监护人的同意。</p>
<p>2、监护人应承担保护未成年人在网络环境下的隐私权的首要责任。</p>
<p>3、帮帮收集未成年人的个人资料，仅为应答未成人特定要求的目的，一经回复完毕即从记录中删除，而不会保留这些资料做进一步的利用。</p>
<p>4、未经监护人之同意，帮帮将不会使用未成年人之个人资料，亦不会向任何第三方披露或传送可识别该未成人的个人资料。帮帮如收集监护人或未成年人的姓名或其它网络通讯资料之目的仅是为获得监护人同意，则在经过一段合理时间仍未获得同意时，将主动从记录中删除此类资料。</p>
<p>5、若经未成人之监护人同意，帮帮可对未成年人之个人资料进行收集，帮帮将向监护人提供：</p>
<p>（1）审视自其子女或被监护人收集之资料的机会；</p>
<p>（2）拒绝其子女或被监护人的个人资料被进一步的收集或利用的机会；</p>
<p>（3）变更或删除其子女或被监护人个人资料的方式。</p>
<p>6、监护人有权拒绝帮帮与其子女或被监护人做进一步的联络。</p>
<p>7、帮帮收集未成年人的个人资料，这些资料只是单纯作为保护未成年人参与网络活动时的安全，而非作为其它目的之利用。帮帮保证不会要求未成年人提供额外的个人资料，以作为允许其参与网上活动的条件。</p>
<p class="title">九、免责</p>
<p>除上述第五条、第六条规定属免责外，下列情况时帮帮亦无需承担任何责任：</p>
<p>1、由于您将用户密码告知他人或与他人共享注册帐户，由此导致的任何个人资料泄露。</p>
<p>2、任何由于黑客政击、计算机病毒侵入或发作、因政府管制而造成的暂时性关闭等影响网络正常经营之不可抗力而造成的个人资料泄露、丢失、被盗用或被篡改等。</p>
<p>3、由于与帮帮链接的其它网站所造成之个人资料泄露及由此而导致的任何法律争议和后果。</p>

</div>
</div>
			</div>
			
		</div>
	</section>
</body>
</html>
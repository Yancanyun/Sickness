<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<meta charset="UTF-8">

<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0,user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<link rel="icon" href="${staticWebsite}img/common/favicon.ico" />
<link rel="stylesheet" href="${staticWebsite}css/base/reset.css" />
<link rel="stylesheet" href="${staticWebsite}css/mobile/common/footer.css">
<!--<link rel="stylesheet" href="/resources/tool/bootstrap/css/bootstrap.css" />-->
<!--<link rel="stylesheet" href="/resources/css/mobile/common.css" />-->

<!--[if lt IE 9]>
<link rel="stylesheet" href="${staticWebsite}tool/base-widget/css/ie8.css">
<script type="text/javascript" src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/respond.js/1.1.0/respond.min.js"></script>
<script type="text/javascript" src="assets/plugins/charts-flot/excanvas.min.js"></script>
<![endif]-->
<link rel="stylesheet" href="${staticWebsite}tool/base-widget/css/core.css" />
<script>
    var website = '';
    var staticWebsite = '/resources/';
</script>
<script type="text/javascript" src="${website}resources/js/mobile/site-config.jsp"></script>
<script type="text/javascript" src="${website}resources/js/mobile/url-core.jsp"></script>
<script type="text/javascript" src="${staticWebsite}tool/pui2/lib/kissy/seed.js"></script>
<script>
    var
        scale = 1 / devicePixelRatio,
        dd = document.documentElement,
        clientWidth = dd.clientWidth;

    document.querySelector('meta[name="viewport"]').setAttribute('content', 'initial-scale=' + scale + ', maximum-scale=' + scale + ', minimum-scale=' + scale + ', user-scalable=no');
    if(clientWidth > 1080){
        clientWidth = 1080;
    }
    dd.style.fontSize = dd.clientWidth / 10 + 'px';
    fontSize = dd.style.fontSize;
    rootFontSize = dd.clientWidth / 10;
</script>
<style>
    .loading-tip span {
        vertical-align: middle;
        border-radius: 100%;
        display: inline-block;
        width: 0.2rem;
        height: 0.2rem;
        margin: 0 0.0267rem;
        -webkit-animation: loader 0.8s linear infinite alternate;
        animation: loader 0.8s linear infinite alternate;
    }
    @keyframes loader {
        from {
            transform: scale(0, 0);
        }
        to {
            transform: scale(1, 1);
        }
    }
    @-webkit-keyframes loader {
        from {
            -webkit-transform: scale(0, 0);
        }
        to {
            -webkit-transform: scale(1, 1);
        }
    }
</style>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <fmt:bundle basename="translations.texts" >
        <head>
            <meta charset="utf-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
            <title></title>
            <meta name="description" content="">
            <meta name="viewport" content="width=device-width">

            <link rel="stylesheet" href="css/normalize.css">
            <link rel="stylesheet" href="css/main.css">
            <link rel="stylesheet" href="css/javaadoption.css">

            <script>var url = "${pageContext.request.contextPath}"</script>
            <script src="js/vendor/modernizr-2.6.2-respond-1.1.0.min.js"></script>
            <script src="js/vendor/jquery-1.9.1.min.js"></script>

            
        </head>
        <body>
            <!--[if lt IE 7]>
                <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
            <![endif]-->

            <div class="header-container">
                <header class="wrapper clearfix">
                    <a href="index.jsp"><img src="img/jcountdown.png" width="250" height="70" alt="jCountdown" /></a>
                    <nav>
                        <ul>
                            <li><a href="http://java.com" target="_blank"><fmt:message key="javacom"/></a></li>
                            <li><a href="http://otn.oracle.com" target="_blank"><fmt:message key="oraclecom"/></a></li>
                            <li><a href="http://java.net" target="_blank"><fmt:message key="javanet"/></a></li>
                        </ul>
                    </nav>
                </header>
            </div>

            <div class="main-container">
                <div class="main wrapper clearfix">
                
<fmt:bundle basename="translations.privacy" >

                    <article>
                        <header>
                            <h1><fmt:message key="privacy.headline"/></h1>
                            <h2><fmt:message key="privacy.terms.title"/></h2>
                            <p><fmt:message key="privacy.terms.text"/></p>                       
                        </header>                      
                        <section>
                            <h2><fmt:message key="privacy.use.title"/></h2>
                            <p><fmt:message key="privacy.use.text"/></p>
                        </section>
                         <section>
                            <h2><fmt:message key="privacy.disclaimer.title"/></h2>
                            <p><fmt:message key="privacy.disclaimer.text"/></p>
                        </section>
                        <section>
                            <h2><fmt:message key="privacy.limitations.title"/></h2>
                            <p><fmt:message key="privacy.limitations.text"/></p>
                        </section>
                        <section>
                            <h2><fmt:message key="privacy.revisions.title"/></h2>
                            <p><fmt:message key="privacy.revisions.text"/></p>
                        </section>
                        <section>
                            <h2><fmt:message key="privacy.links.title"/></h2>
                            <p><fmt:message key="privacy.links.text"/></p>
                        </section>
                        <section>
                            <h2><fmt:message key="privacy.modifications.title"/></h2>
                            <p><fmt:message key="privacy.modifications.text"/></p>
                        </section>
                        <section>
                            <h2><fmt:message key="privacy.law.title"/></h2>
                            <p><fmt:message key="privacy.law.text"/></p>
                        </section>
                        
                        
                        <section>
                        	<h1><fmt:message key="privacy.policy.headline"/></h1>
                            <p><fmt:message key="privacy.policy.text"/></p>
                            <p><fmt:message key="privacy.policy.body.text"/></p>
                            <p><fmt:message key="privacy.policy.footer.text"/></p>
                        </section>
                          

                        
                    </article>
                    
</fmt:bundle>


                    <aside>
                        <h3><fmt:message key="about.headline"/></h3>
                        <p><fmt:message key="about.text"/></p>
                    </aside>
                    <aside class="white">
                        <p>Powered by:</p>
                        <a href="layershift.jsp" target="_top"><img src="img/powered_by_layershift.png" width="200" height="68" alt="Layershift" /></a>
                    </aside>
                </div> <!-- #main -->
            </div> <!-- #main-container -->

            <div class="footer-container">
                <footer class="wrapper">
                    <p><fmt:message key="footer.selectlang"/> | <fmt:message key="footer.aboutjava"/> | <fmt:message key="footer.developerprivacy"/> | <fmt:message key="footer.termsofuse"/> | <fmt:message key="footer.trademarks"/> | <fmt:message key="footer.disclaimer"/></p>
                    <p><fmt:message key="footer.jelastic"/></p>
                </footer>
            </div>

            <!-- 
                might be changed to some orcl tracking ?
                <script>
                 var _gaq=[['_setAccount','UA-XXXXX-X'],['_trackPageview']];
                 (function(d,t){var g=d.createElement(t),s=d.getElementsByTagName(t)[0];
                 g.src=('https:'==location.protocol?'//ssl':'//www')+'.google-analytics.com/ga.js';
                 s.parentNode.insertBefore(g,s)}(document,'script'));
             </script>
            -->
        </body>
    </fmt:bundle>
</html>

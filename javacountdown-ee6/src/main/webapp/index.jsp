<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title></title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width">

        <link rel="stylesheet" href="css/normalize.css">
        <link rel="stylesheet" href="css/main.css">
        <link rel="stylesheet" href="css/javaadoption.css">
        <link rel="stylesheet" href="css/vendor/jquery-jvectormap-1.2.2.css">

        <script src="js/vendor/modernizr-2.6.2-respond-1.1.0.min.js"></script>
        <script src="js/vendor/jquery-1.9.1.min.js"></script>
        <script src="js/vendor/jquery-jvectormap-1.2.2.min.js"></script>
        <script src="js/vendor/jquery-jvectormap-world-mill-en.js"></script>
        <script src="js/vendor/plugindetect_java.js"></script>
        <!--<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script> -->

    </script>
    <script src="js/main.js"></script>
</head>
<body onload="initialize()">
    <!--[if lt IE 7]>
        <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
    <![endif]-->

    <div class="header-container">
        <header class="wrapper clearfix">
            <img src="img/java_logo.png">
            <nav>
                <ul>
                    <li><a href="http://java.com" target="_blank">java.com</a></li>
                    <li><a href="http://oracle.com" target="_blank">oracle.com</a></li>
                    <li><a href="http://java.net" target="_blank">java.net</a></li>
                </ul>
            </nav>
        </header>
    </div>

    <div class="main-container">
        <div class="main wrapper clearfix">

            <article>
                <header>
                    <h1>Move to Java 7 today!</h1>
                    <p>15 years ago an amazing programming language was born. Its name was Java. Now
                        that we're in 2013, the world has changed and Java along with it! For
                        Java versions 4, 5 and 6, it's time to say goodbye.</p>
                </header>
                <section>
                    <h2>Move to Java 7 today!</h2>
                    <p>This website is dedicated to  to help you upgrading to Java 7. This way, we watch Java 4, 5 & 6 usage drop to less
                        than 1% worldwide, so hundreds of millions of users are safer and 9-10
                        million developers are more productive"</p>
                </section>
                <section>
                    <div id="map_canvas" style="width: 100%; height: 400px;"></div>
                </section>
                <section>
                    <h2>Not ready to move?</h2>

                    <p>If, for some special reason, you're not really to move now, Oracle is ready to provide the support you need to keep your business and your customers safe.</p>
                </section>
                <footer>
                    <h3>article footer h3</h3>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam sodales urna non odio egestas tempor. Nunc vel vehicula ante. Etiam bibendum iaculis libero, eget molestie nisl pharetra in. In semper consequat est, eu porta velit mollis nec. Curabitur posuere enim eget turpis feugiat tempor.</p>
                </footer>
            </article>

            <aside>
                <h3>About this side</h3>
                <p>This is a joint project by the <a href="http://java-champions.java.net/" target="_blank">Java Champions</a>, <a href="http://www.java.net/jugs/java-user-groups" target="_blank">JUG Leaders</a>
                    and the <a href="http://java.net/projects/adoptopenjdk" target="_blank">Adopt OpenJDK</a> initiative. It is thought of being the number one place for getting information about the state of Java in the Browser eco-system and should encourage end-users to always adopt the latest and greatest in Java technology.
                </p>
                <div id="geoMessage"></div>
            </aside>
            <aside class="white">
                <p>Powered by:</p>
                <a href="http://glassfish.org" target="_blank"><img src="img/glassfish_logo.gif" width="120" height="60"></a>
            </aside>
        </div> <!-- #main -->
    </div> <!-- #main-container -->

    <div class="footer-container">
        <footer class="wrapper">
            <p>Select Language | About Java | Developers Privacy | Terms of Use | Trademarks | Disclaimer</p>
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
</html>

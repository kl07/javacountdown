javacountdown
=============

This is a rough sketch of what would be needed.
1) Some JS to make magic happen on the client
2) A JAX-RS service to gather map data
3) A JAX-RS service to gather visitor data
4) a database
5) some sql :-)

Remark:
I would actually loved to have done it with EE 6. BUT as of today we are unsure where it actually will be running.
The best I could do for now is make use of my trial account for the Oracle Cloud and put it up there.

All you need to do is to install a decent Oracle XE database and a WebLogic 10.3.6
and you can actually run it yourself.

This is mavenized and the only thing to tweak is to install the Oracle JDBC driver to your local m2.

Find it in your Oracle db install or in the WLS server lib folder:
MW_HOME\wlserver_10.3\server\lib\ojdbc6.jar

Now install that thingy:
mvn install:install-file -Dfile={Path/to/your/ojdbc6.jar} -DgroupId=com.oracle 
-DartifactId=ojdbc6 -Dversion=11.2.0 -Dpackaging=jar

you are able to build the project.

For those interested, a working version is up on the Oracle Cloud Service (trial account until March 22, 2013).
https://java-trialaj2n.java.us1.oraclecloudapps.com/javacountdown/

There is still plenty to do. Watch out for TODOs in the code and please be gentle with my bad coding style ;)

- @myfear
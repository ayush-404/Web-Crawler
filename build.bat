javac -classpath "libs\jsoup-1.13.1.jar;libs\twitter4j-core-4.0.7.jar" -d out src\Spider.java
cd out
jar cmf ..\META-INF\Manifest.MF twitterfetchauto.jar src\Spider*.class
cd ..
rem twitterfetchauto.jar
cd out
move twitterfetchauto.jar ..


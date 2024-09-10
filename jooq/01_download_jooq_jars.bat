@echo off

REM Only need to do this once, or if you are 
REM updating the JAR file versions. See the 
REM pom.xml for details.

del lib\*.jar
mvn -f jars-pom.xml package

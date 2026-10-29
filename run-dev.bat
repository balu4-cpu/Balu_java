@echo off
set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.20.101-hotspot"
set "PATH=%JAVA_HOME%\bin;C:\Users\baluc\apache-maven-3.9.6\bin;%PATH%"
echo =================================================================
echo Starting Exam Seating Arrangement System (In-Memory H2 Mode)...
echo No MySQL required!
echo =================================================================
mvn spring-boot:run -Dspring-boot.run.profiles=dev
pause

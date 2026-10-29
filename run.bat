@echo off
set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.20.101-hotspot"
set "PATH=%JAVA_HOME%\bin;C:\Users\baluc\apache-maven-3.9.6\bin;C:\Program Files\MySQL\MySQL Server 8.4\bin;%PATH%"
if not defined DB_PASSWORD set /p "DB_PASSWORD=Enter MySQL password: "
if not defined DB_PASSWORD (
  echo DB_PASSWORD is required.
  exit /b 1
)
echo ===================================================
echo Starting Exam Seating Arrangement System (MySQL)...
echo ===================================================
mvn spring-boot:run
pause

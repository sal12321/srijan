@echo off
REM Compile the Java file to preserve package structure in output
javac -d . src\com\banking\BankingApp.java

REM Run the compiled class from the correct package and include the lib
java -cp ".;lib\mysql-connector-j-9.3.0.jar" src.com.banking.BankingApp

pause

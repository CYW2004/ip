@ECHO OFF

REM Ensure UTF-8
chcp 65001 >NUL
set JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8

REM Clean/recreate bin
if exist "%BIN%" rmdir /s /q "%BIN%"
mkdir "%BIN%" 2>NUL

REM delete output from previous run
if exist ACTUAL.TXT del ACTUAL.TXT

REM compile the code into the bin folder
javac -cp ..\src\main\java\xiaobai -Xlint:none -d ..\bin ..\src\main\java\xiaobai\*.java
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)
REM no error here, errorlevel == 0

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ..\bin xiaobai.XiaoBai < input.txt > ACTUAL.TXT

REM compare the output to the expected output
FC ACTUAL.TXT EXPECTED.TXT
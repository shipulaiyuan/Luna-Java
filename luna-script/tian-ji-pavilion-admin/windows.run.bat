@echo off
REM 设置 JDK 的安装路径
set "JAVA_HOME=D:\Program Files\Java\jdk-17.0.11+9"
set "JAR_FILE=E:\code\java\Luna\luna-architecture-projects\luna-server\luna-server-system-center\target\luna-server-system-center.jar"

REM 检查 JAVA_HOME 是否设置正确
IF NOT EXIST "%JAVA_HOME%\bin\java.exe" (
    echo Error: JAVA_HOME is not set correctly or Java is not installed.
    pause
    exit /b 1
)

REM 运行 JAR 文件
echo Running JAR file...
"%JAVA_HOME%\bin\java.exe" -jar "%JAR_FILE%"

echo Execution completed.
pause
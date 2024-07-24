@echo off
REM 设置 JDK 和 Maven 的安装路径
set "JAVA_HOME=D:\Program Files\Java\jdk-17.0.11+9"
set "MAVEN_HOME=D:\Program Files\apache-maven-3.8.8-bin\apache-maven-3.8.8"
set "MAVEN_REPO=D:\Program Files\apache-maven-3.8.8-bin\maven-repository"
set "MAVEN_MODULE_DIR=E:\code\java\Luna\luna-architecture-projects"

REM 检查 Maven 是否可用
IF NOT EXIST "%MAVEN_HOME%\bin\mvn.cmd" (
    echo Error: MAVEN_HOME is not set correctly or Maven is not installed.
    pause
    exit /b 1
)

REM 运行 Maven clean 和 package 命令
pushd "%MAVEN_MODULE_DIR%"
echo Running Maven clean and package...
"%MAVEN_HOME%\bin\mvn.cmd" -Dmaven.repo.local="%MAVEN_REPO%" clean package
popd

echo Deployment completed.
pause
@echo off
echo [INFO] Install jar to local repository.

cd %~dp0
call mvn -Dmaven.test.skip=true install
pause
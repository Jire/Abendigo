@echo off
cd /d "%~dp0"
title  
call gradlew installDist
echo.
pause

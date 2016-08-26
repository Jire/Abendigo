@echo off
cd "%~dp0"
title Abendigo Builder
call gradlew installDist
echo.
pause

@echo off
cd /d "%~dp0"
title Abendigo Builder
call gradlew installDist
echo.
pause

@echo off
cd /d "%~dp0"
title
call gradlew build installDist --build-cache
timeout 10

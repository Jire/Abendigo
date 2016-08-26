@echo off
cd /d "%~dp0"
title Abendigo

set bat="./build/install/abendigo/bin/abendigo.bat"

:loop
if exist %bat% (
    call %bat%
    pause
) else (
    call build.bat
    cls
    title Abendigo
    goto loop
)

@echo off
set CurrentDirectory=%CD%
set Target=18
call .\boostrap.bat
if not "%ERRORLEVEL%" == "0" (
	echo check boostrap.bat
	exit /b  %ERRORLEVEL%
)

call android update project -p ./ -s -t %TargetDeviceOS%
exit /b 0

:android_update
cd %1
set ERRORLEVEL=0
if exist %1 (
	echo android update project -p .\%1
	call android update project -p .\
)
exit /b %ERRORLEVEL%

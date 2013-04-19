@echo off
set PATH=%PATH%;%CD%\command
set TargetDeviceOS=18

set PackageName=com.primitive.applicationmanager
if "%JAVA_HOME%" == '' (
  echo "JAVA_HOME not defined"
  exit /b 4
)
exit /b 0

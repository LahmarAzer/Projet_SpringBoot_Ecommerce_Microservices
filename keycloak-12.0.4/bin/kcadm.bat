@echo off

if "%OS%" == "Windows_NT" (
  set "DIRNAME=%~dp0%"
) else (
  set DIRNAME=.\
)
java %KC_OPTS% -cp "%DIRNAME%\client\keycloak-admin-cli-12.0.4.jar" org.keycloak.client.admin.cli.KcAdmMain %*

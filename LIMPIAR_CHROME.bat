@echo off
REM ========================================================================
REM Script para cerrar TODOS los procesos de Chrome antes de ejecutar tests
REM ========================================================================

echo.
echo ╔════════════════════════════════════════════════════════════════╗
echo ║       CERRANDO PROCESOS DE CHROME                              ║
echo ╚════════════════════════════════════════════════════════════════╝
echo.

REM Verificar si hay procesos de Chrome ejecutándose
tasklist /FI "IMAGENAME eq chrome.exe" 2>NUL | find /I /N "chrome.exe">NUL
if "%ERRORLEVEL%"=="0" (
    echo ⚠️  Procesos de Chrome detectados. Cerrando...
    taskkill /F /IM chrome.exe /T 2>NUL
    timeout /t 2 /nobreak >NUL
    echo ✅ Procesos de Chrome cerrados
) else (
    echo ✅ No hay procesos de Chrome ejecutándose
)

REM Verificar ChromeDriver
tasklist /FI "IMAGENAME eq chromedriver.exe" 2>NUL | find /I /N "chromedriver.exe">NUL
if "%ERRORLEVEL%"=="0" (
    echo ⚠️  Procesos de ChromeDriver detectados. Cerrando...
    taskkill /F /IM chromedriver.exe /T 2>NUL
    timeout /t 1 /nobreak >NUL
    echo ✅ Procesos de ChromeDriver cerrados
)

echo.
echo ════════════════════════════════════════════════════════════════
echo.
echo ✅ Sistema limpio. Listo para ejecutar tests.
echo.
echo Ahora ejecuta: .\gradlew.bat test
echo.
pause

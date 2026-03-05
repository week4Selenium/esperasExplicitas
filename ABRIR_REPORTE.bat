@echo off
REM ========================================================================
REM Script para abrir el reporte HTML de los tests ejecutados
REM ========================================================================

echo.
echo ╔════════════════════════════════════════════════════════════════╗
echo ║       ABRIENDO REPORTE HTML DE TESTS                           ║
echo ╚════════════════════════════════════════════════════════════════╝
echo.

REM Verificar si existe el reporte
if exist "build\reports\tests\test\index.html" (
    echo ✓ Reporte encontrado
    echo ✓ Abriendo en navegador...
    echo.
    start "" "build\reports\tests\test\index.html"
    echo ════════════════════════════════════════════════════════════════
    echo.
    echo Ruta del reporte:
    echo %~dp0build\reports\tests\test\index.html
    echo.
    echo ════════════════════════════════════════════════════════════════
) else (
    echo ✗ ERROR: No se encontró el reporte
    echo.
    echo El reporte se genera DESPUÉS de ejecutar los tests.
    echo.
    echo Para generar el reporte, ejecuta primero:
    echo   .\gradlew.bat test
    echo.
    echo Luego ejecuta este script nuevamente.
)

echo.
pause

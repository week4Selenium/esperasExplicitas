# 🚀 Proyecto: Esperas Explícitas en Selenium

[![Java](https://img.shields.io/badge/Java-11+-orange.svg)](https://www.oracle.com/java/)
[![Selenium](https://img.shields.io/badge/Selenium-4.18.1-green.svg)](https://www.selenium.dev/)
[![Gradle](https://img.shields.io/badge/Gradle-8.x-blue.svg)](https://gradle.org/)
[![JUnit](https://img.shields.io/badge/JUnit-5.10.2-red.svg)](https://junit.org/junit5/)

## 📋 Descripción

Proyecto profesional de automatización de pruebas que demuestra el **uso correcto de Esperas Explícitas** (Explicit Waits) en Selenium WebDriver con Java.

Este proyecto tiene un **propósito didáctico**: mostrar claramente la diferencia entre ejecutar tests **SIN** esperas explícitas (que fallan) y **CON** esperas explícitas (que funcionan correctamente).

## 🎯 Objetivo

Demostrar de manera práctica y profesional:

1. **Problemas de sincronización** en aplicaciones web con contenido dinámico
2. **Por qué fallan** los tests sin esperas explícitas
3. **Cómo resolver** estos problemas con `WebDriverWait` + `ExpectedConditions`
4. **Buenas prácticas** de automatización profesional

---

## 📊 IMPORTANTE: Comportamiento Esperado de los Tests

### ✅ ¿Qué Resultados Son Correctos?

El proyecto está diseñado para que **algunos tests pasen** y **otros fallen intencionalmente**:

```
✅ 4 tests PASAN  → DemoWithExplicitWaitTest (CON esperas)
❌ 4 tests FALLAN → DemoWithoutWaitTest (SIN esperas)
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
   8 tests TOTAL
```

### 📊 Ver el Reporte HTML

Después de ejecutar los tests, abre el reporte HTML:

**Método 1: Script Automático (Recomendado)**
```bash
# Doble clic en el archivo:
ABRIR_REPORTE.bat
```

**Método 2: Comando Manual**
```bash
start build\reports\tests\test\index.html
```

**Método 3: Ruta Completa**
```
build/reports/tests/test/index.html
```

### 🎯 Interpretación del Reporte

El reporte HTML mostrará:

| Métrica | Valor Esperado | ¿Es Correcto? |
|---------|----------------|---------------|
| **Total Tests** | 9 | ✅ Sí |
| **Tests Passed** | 5 (56%) | ✅ Sí |
| **Tests Failed** | 4 (44%) | ✅ Sí |

**IMPORTANTE**: Los 4 tests fallidos son **intencionales** para demostrar los problemas sin esperas explícitas.

### ❓ Preguntas Frecuentes

**Q: ¿Por qué fallan algunos tests?**  
A: **ES INTENCIONAL**. Los tests en `DemoWithoutWaitTest` están diseñados para fallar y mostrar problemas reales de sincronización.

**Q: ¿Los tests se ejecutan dos veces?**  
A: **NO**. Hay 8 tests individuales (4 en cada clase) que se ejecutan UNA sola vez cada uno.

**Q: ¿Qué tests deberían pasar?**  
A: Solo los 4 tests de `DemoWithExplicitWaitTest` (que usan esperas explícitas correctamente).

**Q: ¿Cómo verifico que todo funciona bien?**  
A: Ejecuta:
```bash
.\gradlew.bat test 2>&1 | Select-String "tests completed"
```
Deberías ver: `8 tests completed, 4 failed` ✅

📄 Para más detalles, lee: [VERIFICAR_COMPORTAMIENTO.md](VERIFICAR_COMPORTAMIENTO.md)

---

## 🏗️ Arquitectura del Proyecto

El proyecto sigue el patrón **Page Object Model (POM)** y estructura profesional:

```
esperasExplicitas/
├── src/
│   └── test/
│       ├── java/
│       │   ├── base/
│       │   │   ├── BaseTest.java           # Configuración común de tests
│       │   │   └── BasePage.java           # Métodos reutilizables con esperas
│       │   ├── pages/
│       │   │   └── DemoPage.java           # Page Object con localizadores
│       │   └── tests/
│       │       ├── DemoWithoutWaitTest.java    # ❌ Tests SIN esperas (fallan)
│       │       └── DemoWithExplicitWaitTest.java # ✅ Tests CON esperas (pasan)
│       └── resources/
│           └── demo-page.html              # Página HTML con contenido dinámico
├── build.gradle                            # Configuración de dependencias
├── settings.gradle                         # Configuración del proyecto
└── README.md                               # Este archivo
```

## 🔧 Tecnologías Utilizadas

- **Java 11+**: Lenguaje de programación
- **Selenium WebDriver 4.18.1**: Framework de automatización web
- **JUnit 5 (Jupiter)**: Framework de testing
- **WebDriverManager 5.7.0**: Gestión automática de drivers
- **Gradle**: Herramienta de construcción y gestión de dependencias
- **Page Object Model**: Patrón de diseño para tests mantenibles

## ✨ Características Principales

### ✅ Tests con Esperas Explícitas

Incluye tests que demuestran el uso correcto de:

1. **`WebDriverWait`**: Define el tiempo máximo de espera
2. **`ExpectedConditions.visibilityOfElementLocated()`**: Espera a que un elemento sea visible
3. **`ExpectedConditions.elementToBeClickable()`**: Espera a que un elemento sea clickeable
4. **`ExpectedConditions.textToBePresentInElement()`**: Espera a que cambie el texto
5. **`ExpectedConditions.invisibilityOfElementLocated()`**: Espera a que un elemento desaparezca

### ❌ Tests sin Esperas (Demostración de Problemas)

Incluye tests que **intencionalmente fallan** para mostrar:

- `NoSuchElementException`: Elemento no existe todavía
- `ElementNotInteractableException`: Elemento no está listo para interacción
- Tests Flaky: Resultados inconsistentes por problemas de timing
- Validaciones incorrectas: Verificaciones antes de que el estado cambie

## 🎭 Escenarios Demostrados

El proyecto incluye 4 escenarios reales de aplicaciones web modernas:

### 1️⃣ Carga Asíncrona de Contenido

**Simula**: Llamada a API que tarda 3 segundos

- ❌ **Sin esperas**: `NoSuchElementException` (elemento no existe)
- ✅ **Con esperas**: Espera hasta que aparezca el elemento

**Casos reales**: Dashboards, tablas con datos del servidor, modales

### 2️⃣ Botón que se Habilita Después de Validación

**Simula**: Validación asíncrona de 2 segundos

- ❌ **Sin esperas**: `ElementNotInteractableException` (botón deshabilitado)
- ✅ **Con esperas**: Espera hasta que el botón esté clickeable

**Casos reales**: Formularios con validación del servidor, botones de pago

### 3️⃣ Texto que Cambia Dinámicamente

**Simula**: Estados que cambian cada 2 segundos

- ❌ **Sin esperas**: Validación incorrecta (verifica antes del cambio)
- ✅ **Con esperas**: Espera hasta que aparezca el texto correcto

**Casos reales**: Estados de pedidos, progreso de uploads, pipelines CI/CD

### 4️⃣ Overlay que Bloquea la Interacción

**Simula**: Modal que desaparece después de 3 segundos

- ❌ **Sin esperas**: `ElementClickInterceptedException` (overlay bloquea el click)
- ✅ **Con esperas**: Espera hasta que desaparezca el overlay

**Casos reales**: Spinners de carga, modales de procesamiento, notificaciones

## 📦 Instalación y Configuración

### Prerrequisitos

- **Java 11 o superior** instalado
- **Gradle** instalado (o usar el wrapper incluido)
- **Google Chrome** instalado (WebDriverManager descargará el driver automáticamente)

### Pasos de Instalación

1. **Clonar el repositorio**:

```bash
git clone https://github.com/week4Selenium/esperasExplicitas.git
cd esperasExplicitas
```

2. **Verificar la instalación de Java**:

```bash
java -version
```

Deberías ver Java 11 o superior.

3. **Descargar dependencias** (Gradle lo hace automáticamente):

```bash
gradle build
```

o si usas el wrapper:

```bash
./gradlew build   # Linux/Mac
gradlew.bat build # Windows
```

## 🚀 Ejecución de Tests

### Ejecutar TODOS los tests

```bash
gradle test
```

### Ejecutar solo los tests CON esperas explícitas (que pasan)

```bash
gradle test --tests "tests.DemoWithExplicitWaitTest"
```

### Ejecutar solo los tests SIN esperas (que fallan intencionalmente)

```bash
gradle test --tests "tests.DemoWithoutWaitTest"
```

### Ejecutar un test específico

```bash
# Test de carga asíncrona CON esperas
gradle test --tests "tests.DemoWithExplicitWaitTest.testAsyncContentWithWait"

# Test de carga asíncrona SIN esperas (fallará)
gradle test --tests "tests.DemoWithoutWaitTest.testAsyncContentWithoutWait"
```

## ⏱️ Configuración de Velocidad de Demostración

### 🎥 Para Presentaciones y Demos en Vivo

Por defecto, los tests incluyen **pausas de demostración** de 1.5 segundos entre cada acción para que la automatización sea **visible y fácil de seguir** durante presentaciones a clientes o capacitaciones.

**Velocidad actual**: 1500 ms (1.5 segundos) por acción

### 📝 Ajustar la Velocidad

Para cambiar la velocidad de demostración, edita el archivo `BaseTest.java`:

```java
// En src/test/java/base/BaseTest.java, línea ~37

// ⏱️ DELAY PARA DEMOSTRACIONES
// Ajusta este valor según tus necesidades:
protected static final int DEMO_DELAY = 1500;  // ← Cambia este número

// Ejemplos de configuración:
// - Para presentaciones lentas: 2500 ms (2.5 segundos)
// - Para demos normales: 1500 ms (1.5 segundos - actual)
// - Para ejecución rápida: 500 ms (0.5 segundos)
// - Para CI/CD sin pausas: 0 ms (sin pausas)
```

### 🚀 Deshabilitar Pausas para CI/CD

Si ejecutas los tests en pipelines automatizados, puedes:

**Opción 1**: Configurar `DEMO_DELAY = 0` en BaseTest.java

**Opción 2**: Comentar las llamadas a `pauseForDemo()` en los tests

```java
// pauseForDemo(); // ← Comentar esta línea
```

### 📊 Tiempo de Ejecución Estimado

Con las pausas actuales (1500 ms):

| Test Suite | Tests | Tiempo Aprox. |
|------------|-------|---------------|
| DemoWithExplicitWaitTest | 4 tests | ~35-50 segundos |
| DemoWithoutWaitTest | 4 tests | ~30-40 segundos |
| Todos los tests | 8 tests | ~65-90 segundos |

Sin pausas (DEMO_DELAY = 0):

| Test Suite | Tests | Tiempo Aprox. |
|------------|-------|---------------|
| DemoWithExplicitWaitTest | 4 tests | ~15-20 segundos |
| DemoWithoutWaitTest | 4 tests | ~10-15 segundos |
| Todos los tests | 8 tests | ~25-35 segundos |

## �️ Solución de Problemas

### ⚠️ Problema: Chrome no abre en la segunda ejecución

**Síntoma**: Después de ejecutar los tests una vez, Chrome no se abre correctamente en ejecuciones subsecuentes.

**Causa**: Procesos de Chrome y ChromeDriver quedan abiertos después de finalizar los tests, creando conflictos para nuevas sesiones.

**Solución Rápida**: Usa el script de limpieza incluido

#### En Windows (PowerShell o CMD)

```bash
# Opción 1: Script automático (recomendado)
.\LIMPIAR_CHROME.bat

# Opción 2: Comando manual
taskkill /F /IM chrome.exe /T
taskkill /F /IM chromedriver.exe /T
```

#### En Linux/Mac

```bash
# Comando manual
pkill -9 chrome
pkill -9 chromedriver
```

### 📝 Script LIMPIAR_CHROME.bat

El proyecto incluye un script que:

- ✅ Cierra TODOS los procesos de Chrome
- ✅ Cierra TODOS los procesos de ChromeDriver
- ✅ Verifica que el sistema esté limpio
- ✅ Muestra confirmación de limpieza

**Uso**:

1. **Doble clic** en el archivo `LIMPIAR_CHROME.bat`
2. **O ejecuta desde terminal**: `.\LIMPIAR_CHROME.bat`
3. **Luego ejecuta los tests normalmente**: `.\gradlew.bat test`

### 🔍 Verificar procesos activos

```bash
# Windows PowerShell
Get-Process chrome*,chromedriver* -ErrorAction SilentlyContinue

# Comando CMD
tasklist | findstr chrome

# Linux/Mac
ps aux | grep chrome
```

### 🚨 Otros problemas comunes

#### Tests muy rápidos en demostraciones

Ajusta `DEMO_DELAY` en [BaseTest.java](src/test/java/base/BaseTest.java) (ver sección "Configuración de Velocidad").

#### Limpieza de caché de Gradle

```bash
# Detiene el daemon de Gradle y limpia el proyecto
.\gradlew.bat clean --stop

# O usa la tarea personalizada
.\gradlew.bat info
```

#### Error "Unable to find CDP implementation"

Este warning es **NORMAL** y no afecta la ejecución. Selenium funciona correctamente sin CDP para este proyecto.

---

## �📊 Resultados Esperados

### ✅ Tests con Esperas Explícitas

```
DemoWithExplicitWaitTest
  ✓ test1_AsyncContentWorks()       PASSED
  ✓ test2_DisabledButtonWorks()     PASSED
  ✓ test3_DynamicTextWorks()        PASSED
  ✓ test4_OverlayBlocksWorks()      PASSED

Total: 4 tests, 4 passed ✅
```

### ❌ Tests sin Esperas (Fallidos por Diseño)

```
DemoWithoutWaitTest
  ✗ test1_AsyncContentFails()       FAILED (NoSuchElementException)
  ✗ test2_DisabledButtonFails()     FAILED (ElementNotInteractableException)
  ✗ test3_DynamicTextFails()        FAILED (AssertionFailedError)
  ✗ test4_OverlayBlocksFails()      FAILED (ElementNotInteractableException)

Total: 4 tests, 0 passed, 4 failed ❌
```

### 📊 Resumen Total

```
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 TOTAL:  8 tests
 PASSED: 4 tests (50%) ✅
 FAILED: 4 tests (50%) ❌
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
```

**NOTA**: Los 4 tests fallidos son **intencionales** y demuestran problemas reales de sincronización.

## 📚 Conceptos Clave

### ¿Qué son las Esperas Explícitas?

Las **Esperas Explícitas** son una técnica de Selenium que permite esperar hasta que se cumpla una condición específica antes de continuar con la ejecución del test.

```java
// Crear WebDriverWait con timeout de 10 segundos
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

// Esperar hasta que un elemento sea visible
WebElement element = wait.until(
    ExpectedConditions.visibilityOfElementLocated(By.id("btnSubmit"))
);

// Ahora el elemento está visible y listo para interactuar
element.click();
```

### Ventajas de las Esperas Explícitas

| Ventaja | Descripción |
|---------|-------------|
| ⚡ **Eficientes** | Solo esperan el tiempo necesario, no un tiempo fijo |
| 🎯 **Precisas** | Esperan condiciones específicas, no solo tiempo |
| 🔒 **Estables** | Tests 100% confiables, sin flakiness |
| 📖 **Legibles** | El código expresa claramente qué se espera |
| 🔧 **Mantenibles** | Fáciles de modificar y reutilizar |

### Diferencia con Otros Tipos de Esperas

| Tipo de Espera | Cuándo Usar | Desventaja |
|----------------|-------------|------------|
| **Thread.sleep()** ❌ | NUNCA | Tiempo fijo, ineficiente, no detecta errores |
| **Implicit Waits** ⚠️ | Raramente | Global, puede ocultar problemas |
| **Explicit Waits** ✅ | SIEMPRE | Ninguna (mejor práctica) |

## 🎓 Para Aprender

### 1. Examina el Código Comentado

Cada archivo tiene comentarios exhaustivos explicando:

- ¿Qué hace el código?
- ¿Por qué se usa así?
- ¿Cuándo aplicarlo?
- Casos reales equivalentes

### 2. Compara los Tests

Abre lado a lado:

- `DemoWithoutWaitTest.java`: Ve los problemas
- `DemoWithExplicitWaitTest.java`: Ve las soluciones

### 3. Ejecuta los Tests

Observa:

- Cómo fallan los tests sin esperas
- Cómo pasan los tests con esperas
- Los mensajes de error y su significado

### 4. Experimenta

Modifica:

- Cambia los tiempos de delay en el HTML
- Ajusta los timeouts de `WebDriverWait`
- Agrega tus propios escenarios

## 🏆 Buenas Prácticas Implementadas

- ✅ **Page Object Model**: Separación de localizadores y lógica de negocio
- ✅ **DRY (Don't Repeat Yourself)**: Código reutilizable en `BasePage`
- ✅ **Nombres descriptivos**: Métodos y variables autoexplicativos
- ✅ **Comentarios exhaustivos**: Código didáctico y educativo
- ✅ **Estructura profesional**: Organización por capas
- ✅ **Configuración centralizada**: `BaseTest` para setup/teardown
- ✅ **Esperas explícitas**: En todos los puntos de sincronización
- ✅ **Validaciones claras**: Assertions con mensajes descriptivos

## 🐛 Solución de Problemas

### ❓ "¿Por qué algunos tests fallan? ¿No deberían todos pasar?"

**Respuesta**: **NO**. Este es el comportamiento ESPERADO y CORRECTO.

El proyecto demuestra:
- ❌ **SIN esperas** → Tests FALLAN (4 tests en `DemoWithoutWaitTest`)
- ✅ **CON esperas** → Tests PASAN (4 tests en `DemoWithExplicitWaitTest`)

**Resultado esperado al ejecutar todos los tests:**
```
8 tests completed, 4 failed
BUILD FAILED
```

**Esto es CORRECTO** ✅ - Los 4 tests que fallan demuestran el problema que las esperas explícitas resuelven.

### ❓ "Los tests se ejecutan dos veces"

**Respuesta**: **NO se ejecutan dos veces**.

Verificación:
```bash
.\gradlew.bat clean test 2>&1 | Select-String "tests completed"
```

Resultado esperado: `8 tests completed, 4 failed`

**Explicación**: Hay 8 tests en total:
- 4 tests en `DemoWithExplicitWaitTest` (test1_AsyncContentWorks, test2_DisabledButtonWorks, test3_DynamicTextWorks, test4_OverlayBlocksWorks)
- 4 tests en `DemoWithoutWaitTest` (test1_AsyncContentFails, test2_DisabledButtonFails, test3_DynamicTextFails, test4_OverlayBlocksFails)

Cada test se ejecuta UNA sola vez, pero hay **4 escenarios diferentes** demostrados con y sin esperas.

### ❓ "¿Cómo veo el reporte HTML?"

**Respuesta**: El reporte se genera en: `build/reports/tests/test/index.html`

**Opción 1 - Script Automático:**
```bash
# Doble clic en:
ABRIR_REPORTE.bat
```

**Opción 2 - Comando:**
```bash
start build\reports\tests\test\index.html
```

**Opción 3 - Manual:**
Navega a la carpeta `build/reports/tests/test/` y abre `index.html`

**Contenido del reporte:**
- 📊 Resumen general: 8 tests, 4 passed, 4 failed
- 📁 Tests por paquete y clase
- 🔍 Detalles de cada test (stack trace de errores, duración, output)
- 📈 Gráficos de éxito/fallo

### ❓ "Los tests sin esperas ahora pasan (pero deberían fallar)"

**Diagnóstico**: Verifica que el HTML tenga los delays correctos.

**Verificación**:
```bash
# Solo ejecutar tests sin esperas:
.\gradlew.bat test --tests "tests.DemoWithoutWaitTest"
```

**Resultado esperado**: `4 tests completed, 4 failed`

Si pasan, revisa el archivo `demo-page.html` - los delays de JavaScript deben ser:
- Escenario 1: 3000ms (3 segundos)
- Escenario 2: 2000ms (2 segundos)  
- Escenario 3: 2000ms + 2000ms (4 segundos total)
- Escenario 4: 3000ms (3 segundos)

### El navegador no se abre

**Problema**: WebDriverManager no puede descargar el driver

**Solución**: 
1. Verifica tu conexión a internet
2. Verifica configuración de proxy
3. Actualiza Chrome a la última versión

### Tests fallan con TimeoutException

**Problema**: El elemento no aparece en el tiempo esperado

**Solución**: 
1. Verifica que el HTML esté cargado correctamente
2. Aumenta el timeout en `BasePage.java` (línea ~32):
   ```java
   private static final int TIMEOUT_SECONDS = 10; // Aumentar a 15 o 20
   ```
3. Revisa que los localizadores sean correctos

### No se encuentra el archivo HTML

**Problema**: La ruta al archivo `demo-page.html` es incorrecta

**Solución**: Verifica que el archivo esté en `src/test/resources/demo-page.html`

### Comando "gradlew not found"

**Problema**: El wrapper de Gradle no tiene permisos de ejecución

**Solución**:
```bash
# Windows:
.\gradlew.bat test

# Linux/Mac:
chmod +x gradlew
./gradlew test
```

### 🔍 Verificación Completa del Proyecto

Ejecuta estos comandos en orden para verificar que todo funciona:

```powershell
# 1. Limpiar build anterior
.\gradlew.bat clean

# 2. Ejecutar todos los tests
.\gradlew.bat test

# 3. Verificar el conteo
.\gradlew.bat test 2>&1 | Select-String "tests completed"
# Debe mostrar: "8 tests completed, 4 failed"

# 4. Abrir reporte HTML
start build\reports\tests\test\index.html

# 5. Verificar tests buenos (deben pasar)
.\gradlew.bat test --tests "tests.DemoWithExplicitWaitTest"
# Debe mostrar: "5 tests completed, 0 failed" + BUILD SUCCESSFUL

# 6. Verificar tests malos (deben fallar)
.\gradlew.bat test --tests "tests.DemoWithoutWaitTest"
# Debe mostrar: "4 tests completed, 4 failed" + BUILD FAILED
```

Si todos estos comandos producen los resultados esperados, **el proyecto está funcionando perfectamente** ✅

📄 **Documentación adicional:**
- [VERIFICAR_COMPORTAMIENTO.md](VERIFICAR_COMPORTAMIENTO.md) - Guía detallada de verificación
- [ABRIR_REPORTE.bat](ABRIR_REPORTE.bat) - Script para abrir el reporte HTML

## 👥 Autor

Proyecto creado con fines didácticos para demostrar el uso profesional de Selenium WebDriver.

## 📄 Licencia

Este proyecto es de código abierto y está disponible para fines educativos.

## 🔗 Enlaces Útiles

- [Documentación oficial de Selenium](https://www.selenium.dev/documentation/)
- [Selenium WebDriverWait](https://www.selenium.dev/documentation/webdriver/waits/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Gradle User Manual](https://docs.gradle.org/)

## 📞 Soporte

Si tienes preguntas o encuentras problemas:

1. Revisa los comentarios en el código
2. Ejecuta los tests y analiza los mensajes de error
3. Compara con los ejemplos funcionando

---

**¡Feliz automatización! 🚀**

*Recuerda: Las Esperas Explícitas son la base de tests estables y profesionales.*

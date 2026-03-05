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
| DemoWithExplicitWaitTest | 5 tests | ~45-60 segundos |
| DemoWithoutWaitTest | 4 tests | ~30-40 segundos |
| Todos los tests | 9 tests | ~75-100 segundos |

Sin pausas (DEMO_DELAY = 0):

| Test Suite | Tests | Tiempo Aprox. |
|------------|-------|---------------|
| DemoWithExplicitWaitTest | 5 tests | ~20-25 segundos |
| DemoWithoutWaitTest | 4 tests | ~10-15 segundos |
| Todos los tests | 9 tests | ~30-40 segundos |

## 📊 Resultados Esperados

### ✅ Tests con Esperas Explícitas

```
DemoWithExplicitWaitTest
  ✓ testAsyncContentWithWait()              PASSED
  ✓ testClickableElementWithWait()          PASSED
  ✓ testDynamicTextWithWait()              PASSED
  ✓ testOverlayWithWait()                  PASSED
  ✓ testCompleteFlowWithExplicitWaits()    PASSED

Total: 5 tests, 5 passed ✅
```

### ❌ Tests sin Esperas (Fallidos por Diseño)

```
DemoWithoutWaitTest
  ✗ testAsyncContentWithoutWait()          FAILED (NoSuchElementException)
  ✗ testClickableElementWithoutWait()      FAILED (ElementNotInteractableException)
  ✗ testDynamicTextWithoutWait()          FAILED (Assertion failed)
  ✗ testOverlayWithoutWait()              FAILED (ElementClickInterceptedException)

Total: 4 tests, 0 passed, 4 failed ❌
```

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

### El navegador no se abre

**Problema**: WebDriverManager no puede descargar el driver

**Solución**: Verifica tu conexión a internet y proxy

### Tests fallan con TimeoutException

**Problema**: El elemento no aparece en el tiempo esperado

**Solución**: 
1. Verifica que el HTML esté cargado correctamente
2. Aumenta el timeout en `BasePage.java`
3. Revisa que los localizadores sean correctos

### No se encuentra el archivo HTML

**Problema**: La ruta al archivo `demo-page.html` es incorrecta

**Solución**: Verifica que el archivo esté en `src/test/resources/demo-page.html`

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

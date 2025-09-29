# 📋 Changelog - SimpleAuth

Todos los cambios notables de este proyecto serán documentados en este archivo.

El formato está basado en [Keep a Changelog](https://keepachangelog.com/es-ES/1.0.0/),
y este proyecto adhiere a [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Sin Lanzar]

### Añadido
- Mejoras futuras por implementar

## [1.0.0] - 2024-01-15

### ✨ Añadido
- **Sistema de Autenticación Básico**
  - Comando `/register <contraseña>` para registro de nuevos usuarios
  - Comando `/login <contraseña>` para autenticación
  - Comando `/changepassword <nueva_contraseña>` para cambio de contraseñas
  - Comando `/unregister <contraseña>` para eliminación de cuentas

- **Protección de Servidor**
  - Bloqueo completo de movimiento para jugadores no autenticados
  - Prevención de apertura de inventarios sin autenticación
  - Bloqueo de uso de ítems hasta la autenticación
  - Prevención de colocación/rotura de bloques sin login
  - Bloqueo de interacciones con el mundo hasta autenticarse

- **Sistema de Seguridad**
  - Tiempo límite de 60 segundos para autenticarse
  - Máximo 3 intentos fallidos antes del cooldown
  - Cooldown de 60 segundos tras intentos fallidos
  - Expulsión automática por timeout de autenticación

- **Gestión de Datos**
  - Almacenamiento persistente en archivo `users.json`
  - Encriptación segura de contraseñas
  - Gestión automática de usuarios registrados

### 🛠️ Técnico
- **Compatibilidad**: Minecraft 1.20.1 con Forge 47.4.9+
- **Requisitos**: Java 17 o superior
- **Arquitectura**: Modular con separación de responsabilidades
- **Eventos**: Manejo completo de eventos de jugador
- **Comandos**: Sistema robusto de comandos con validaciones

### 📋 Estructura del Proyecto
```
src/main/java/com/kamerrezz/simpleauth/
├── AuthMod.java                    # Clase principal
├── command/                        # Comandos del mod
├── handler/                        # Manejadores de eventos
├── storage/                        # Gestión de datos
└── util/                          # Utilidades
```

---

## 🏷️ Formato de Versiones

Este proyecto usa [Semantic Versioning](https://semver.org/):

- **MAJOR** (X.0.0): Cambios incompatibles en la API
- **MINOR** (0.X.0): Nuevas funcionalidades compatibles hacia atrás
- **PATCH** (0.0.X): Correcciones de bugs compatibles hacia atrás

### Ejemplos:
- `1.0.0` - Primera versión estable
- `1.1.0` - Nuevas características (ej: integración con base de datos)
- `1.0.1` - Corrección de bugs
- `2.0.0` - Cambios mayores (ej: nueva API, cambio de Minecraft version)

---

## 📝 Tipos de Cambios

- **✨ Añadido** - Nuevas funcionalidades
- **🔄 Cambiado** - Cambios en funcionalidades existentes
- **❌ Obsoleto** - Funcionalidades que serán removidas
- **🗑️ Removido** - Funcionalidades removidas
- **🐛 Corregido** - Corrección de bugs
- **🔒 Seguridad** - Correcciones de vulnerabilidades

---

## 🔗 Enlaces

- [Releases](https://github.com/KamerrEzz/simpleauth/releases)
- [Issues](https://github.com/KamerrEzz/simpleauth/issues)
- [Pull Requests](https://github.com/KamerrEzz/simpleauth/pulls)
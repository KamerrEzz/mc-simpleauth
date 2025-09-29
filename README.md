# 🔐 SimpleAuth

<div align="center">

![Minecraft](https://img.shields.io/badge/Minecraft-1.20.1-green?style=for-the-badge&logo=minecraft)
![Forge](https://img.shields.io/badge/Forge-47.4.9-orange?style=for-the-badge&logo=curseforge)
![Java](https://img.shields.io/badge/Java-17-blue?style=for-the-badge&logo=openjdk)
![License](https://img.shields.io/badge/License-All%20Rights%20Reserved-red?style=for-the-badge)

**🛡️ Mod de autenticación simple y seguro para servidores Minecraft Forge 1.20.1**

*Protege tu servidor con un sistema de autenticación robusto y fácil de usar*

</div>

---

## 📋 Descripción

**SimpleAuth** es un mod de autenticación diseñado específicamente para servidores Minecraft Forge 1.20.1. Proporciona un sistema de seguridad completo que requiere que los jugadores se registren y autentiquen antes de poder interactuar con el mundo del juego.

### ✨ Características Principales

- 🔒 **Sistema de registro y login seguro**
- 🛡️ **Protección completa contra jugadores no autenticados**
- ⏱️ **Sistema de tiempo límite y cooldowns**
- 🚫 **Bloqueo de interacciones hasta la autenticación**
- 🔑 **Cambio de contraseñas**
- 📊 **Gestión de intentos fallidos**
- 💾 **Almacenamiento persistente de usuarios**

---

## 🚀 Instalación

### Requisitos Previos

- **Minecraft**: 1.20.1
- **Minecraft Forge**: 47.4.9 o superior
- **Java**: 17 o superior

### Pasos de Instalación

1. **Descarga el mod**
   ```bash
   # Clona el repositorio
   git clone https://github.com/KamerrEzz/simpleauth.git
   cd simpleauth
   ```

2. **Compila el mod**
   ```bash
   # En Windows
   .\gradlew build
   
   # En Linux/Mac
   ./gradlew build
   ```

3. **Instala en el servidor**
   - Copia el archivo `.jar` generado desde `build/libs/` a la carpeta `mods/` de tu servidor
   - Reinicia el servidor

---

## 🎮 Uso

### Comandos Disponibles

| Comando | Descripción | Uso |
|---------|-------------|-----|
| `/register <contraseña>` | Registra una nueva cuenta | `/register miContraseña123` |
| `/login <contraseña>` | Inicia sesión con tu cuenta | `/login miContraseña123` |
| `/changepassword <nueva_contraseña>` | Cambia tu contraseña | `/changepassword nuevaContraseña456` |
| `/unregister <contraseña>` | Elimina tu cuenta | `/unregister miContraseña123` |

### 🔐 Flujo de Autenticación

1. **Primer ingreso**: Los jugadores nuevos deben usar `/register <contraseña>`
2. **Ingresos posteriores**: Los jugadores registrados deben usar `/login <contraseña>`
3. **Tiempo límite**: Los jugadores tienen **60 segundos** para autenticarse
4. **Intentos fallidos**: Máximo **3 intentos** antes del cooldown de **60 segundos**

### 🚫 Restricciones para Jugadores No Autenticados

- ❌ No pueden moverse
- ❌ No pueden abrir inventarios
- ❌ No pueden usar ítems
- ❌ No pueden colocar/romper bloques
- ❌ No pueden interactuar con el mundo
- ❌ No pueden usar comandos (excepto autenticación)

---

## 🏗️ Estructura del Proyecto

```
src/main/java/com/kamerrezz/simpleauth/
├── AuthMod.java                    # Clase principal del mod
├── command/                        # Comandos del mod
│   ├── RegisterCommand.java        # Comando de registro
│   ├── LoginCommand.java          # Comando de login
│   ├── ChangePasswordCommand.java # Comando cambio contraseña
│   └── UnregisterCommand.java     # Comando eliminar cuenta
├── handler/                       # Manejadores de eventos
│   └── LoginHandler.java         # Control de autenticación
├── storage/                       # Gestión de datos
│   └── UserManager.java          # Administrador de usuarios
└── util/                         # Utilidades
    └── PasswordUtils.java        # Utilidades de contraseñas
```

---

## 🛠️ Desarrollo

### Configuración del Entorno

```bash
# Configura Java 17
export JAVA_HOME="/path/to/java17"  # Linux/Mac
$env:JAVA_HOME = "C:\path\to\java17"  # Windows PowerShell

# Ejecuta el servidor de desarrollo
./gradlew runServer  # Linux/Mac
.\gradlew runServer  # Windows
```

### Comandos de Desarrollo

```bash
# Compilar el mod
./gradlew build

# Ejecutar cliente de desarrollo
./gradlew runClient

# Ejecutar servidor de desarrollo
./gradlew runServer

# Limpiar build
./gradlew clean
```

---

## 🔧 Configuración

El mod utiliza las siguientes configuraciones por defecto:

- **Tiempo máximo de login**: 60 segundos
- **Intentos fallidos máximos**: 3
- **Tiempo de cooldown**: 60 segundos
- **Almacenamiento**: Archivo `users.json` en la carpeta del servidor

---

## 🤝 Contribución

¡Las contribuciones son bienvenidas! Para contribuir:

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

---

## 📝 Licencia

Este proyecto está bajo la licencia **All Rights Reserved**. Ver el archivo `LICENSE` para más detalles.

---

## 👨‍💻 Autor

**KamerrEzz**

- GitHub: [@KamerrEzz](https://github.com/KamerrEzz)

---

## 🐛 Reportar Bugs

Si encuentras algún bug o tienes sugerencias, por favor:

1. Verifica que no exista un issue similar
2. Crea un nuevo issue con:
   - Descripción detallada del problema
   - Pasos para reproducir
   - Versión del mod y Forge
   - Logs relevantes

---

## 📊 Estado del Proyecto

- ✅ Sistema de autenticación básico
- ✅ Comandos de usuario
- ✅ Protección contra interacciones
- ✅ Sistema de cooldowns
- ✅ Gestión de contraseñas
- 🔄 En desarrollo: Configuración personalizable
- 📋 Planeado: Integración con bases de datos

---

<div align="center">

**⭐ Si te gusta este proyecto, ¡dale una estrella! ⭐**

*Hecho con ❤️ para la comunidad de Minecraft*

</div>
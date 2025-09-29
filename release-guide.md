# 🚀 Guía de Releases Automatizados - SimpleAuth

## 📋 Cómo Funciona la Integración

El sistema automatizado **lee directamente** el `CHANGELOG.md` para generar las notas del release:

### 🔄 Flujo Automático:
1. **Editas** `CHANGELOG.md` con los cambios de la nueva versión
2. **Creas un tag** de versión (`v1.0.0`)
3. **GitHub Actions** lee el changelog y crea el release automáticamente

---

## 📝 Paso a Paso para Crear un Release

### **1. Actualizar CHANGELOG.md**

Edita el archivo y mueve los cambios de `[Sin Lanzar]` a una nueva versión:

```markdown
## [Sin Lanzar]
<!-- Mantén esta sección vacía o con cambios futuros -->

## [1.0.0] - 2024-01-15

### ✨ Añadido
- Sistema de autenticación completo
- Comandos de registro y login
- Protección contra jugadores no autenticados

### 🐛 Corregido
- Corrección de mensajes duplicados al hacer clic derecho
- Optimización de rendimiento en eventos

### 🔒 Seguridad
- Encriptación mejorada de contraseñas
- Validación de entrada más robusta
```

### **2. Actualizar Versión en Gradle**

Edita `gradle.properties`:
```properties
mod_version=1.0.0
```

### **3. Commit y Tag**

```powershell
# Commit los cambios
git add .
git commit -m "Release v1.0.0

- Sistema de autenticación completo
- Corrección de bugs críticos
- Mejoras de seguridad"

# Crear tag (esto dispara el release automático)
git tag v1.0.0
git push origin main --tags
```

---

## 🎯 Formato del CHANGELOG.md

### **Estructura Requerida:**
```markdown
## [Sin Lanzar]
### Añadido
- Cambios futuros...

## [1.0.0] - 2024-01-15
### ✨ Añadido
- Nueva funcionalidad

### 🐛 Corregido  
- Bug corregido

### 🔒 Seguridad
- Mejora de seguridad
```

### **Tipos de Cambios Soportados:**
- `✨ Añadido` - Nuevas funcionalidades
- `🔄 Cambiado` - Cambios en funcionalidades existentes
- `🐛 Corregido` - Corrección de bugs
- `🔒 Seguridad` - Correcciones de vulnerabilidades
- `🗑️ Removido` - Funcionalidades removidas
- `❌ Obsoleto` - Funcionalidades deprecadas

---

## 🔧 Cómo Lee el Workflow el CHANGELOG

El workflow `release.yml` hace esto automáticamente:

1. **Busca la sección** `[1.0.0]` que coincida con el tag `v1.0.0`
2. **Extrae todo el contenido** hasta la siguiente sección `[X.X.X]`
3. **Si no encuentra la versión**, usa la sección `[Sin Lanzar]`
4. **Genera el release** con ese contenido

### **Ejemplo de Extracción:**

**Tag creado:** `v1.0.0`
**Busca en CHANGELOG.md:** `## [1.0.0]`
**Extrae:** Todo hasta la siguiente sección `## [X.X.X]`

---

## ✅ Checklist de Release

### **Antes del Release:**
- [ ] Actualizar `CHANGELOG.md` con cambios de la versión
- [ ] Actualizar `mod_version` en `gradle.properties`
- [ ] Compilar y probar localmente: `.\gradlew build`
- [ ] Verificar que no hay errores en el código

### **Crear Release:**
- [ ] Commit cambios: `git commit -m "Release vX.X.X"`
- [ ] Crear tag: `git tag vX.X.X`
- [ ] Push con tags: `git push origin main --tags`

### **Verificar Release:**
- [ ] Verificar que GitHub Actions se ejecutó correctamente
- [ ] Revisar el release generado en GitHub
- [ ] Confirmar que el JAR se subió correctamente
- [ ] Probar el JAR descargado

---

## 🚨 Solución de Problemas

### **El workflow no encuentra mi versión:**
- Verifica que el formato sea exacto: `## [1.0.0] - YYYY-MM-DD`
- El tag debe ser `v1.0.0` (con 'v')
- La versión en CHANGELOG debe ser `1.0.0` (sin 'v')

### **Release con contenido vacío:**
- El workflow usará la sección `[Sin Lanzar]` como fallback
- Siempre mantén contenido en esa sección

### **Error en compilación:**
- El workflow falla si `gradlew build` falla
- Prueba localmente antes de crear el tag

---

## 📋 Comandos Rápidos

```powershell
# Compilar y probar
.\gradlew build

# Release completo
git add .
git commit -m "Release v1.0.0"
git tag v1.0.0
git push origin main --tags

# Ver releases
git tag -l

# Eliminar tag (si hay error)
git tag -d v1.0.0
git push origin :refs/tags/v1.0.0
```

---

## 🎉 ¡Listo!

Con esta configuración tienes:
- ✅ **Releases automáticos** al crear tags
- ✅ **Notas extraídas** del CHANGELOG.md
- ✅ **Compilación automática** y subida de JARs
- ✅ **Control total** sobre el contenido via CHANGELOG.md
# Configuración de Seguridad - Backend

## ⚠️ Archivos Sensibles NO Incluidos

Por razones de seguridad, los siguientes archivos NO están en el repositorio:

- `application-dev.properties` (credenciales de desarrollo)
- `application-prod.properties` (credenciales de producción)

## 🔧 Configuración Local (Desarrollo)

1. Copia `application-dev.properties.example` a `application-dev.properties`:
```bash
cp src/main/resources/application-dev.properties.example src/main/resources/application-dev.properties
```

2. Edita `application-dev.properties` con tus credenciales:
```properties
spring.datasource.password=TU_PASSWORD_MYSQL
spring.mail.username=TU_EMAIL@gmail.com
spring.mail.password=TU_APP_PASSWORD_GMAIL
```

## ☁️ Configuración en Railway (Producción)

Configura estas **Variables de Entorno** en Railway:

### Base de Datos
```
SPRING_DATASOURCE_URL=jdbc:postgresql://[host]:[port]/railway
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=[password de Railway]
```

### Email
```
SPRING_MAIL_USERNAME=tu-email@gmail.com
SPRING_MAIL_PASSWORD=tu-app-password-gmail
```

### CORS
```
CORS_ALLOWED_ORIGINS=https://tu-app.netlify.app
```

## 🔐 Credenciales de Administrador

Las credenciales de administrador están **encriptadas con BCrypt** en la base de datos y **NO** están hardcodeadas en el código fuente.

Para crear nuevos administradores, usa el endpoint `/api/auth/register` (disponible solo en desarrollo) o inserta directamente en la base de datos con contraseñas encriptadas.

## 📧 App Password de Gmail

Para el envío de emails, necesitas un **App Password** de Gmail:

1. Ve a tu cuenta de Google → Seguridad
2. Activa la verificación en 2 pasos
3. En "Contraseñas de aplicación", genera una nueva contraseña
4. Usa esa contraseña de 16 caracteres en `SPRING_MAIL_PASSWORD`

## ✅ Verificación

Antes de commitear código, verifica que:
- [ ] No hay credenciales hardcodeadas
- [ ] `application-*.properties` está en `.gitignore`
- [ ] Solo los archivos `.example` están en el repositorio
- [ ] Las variables de entorno están configuradas en Railway

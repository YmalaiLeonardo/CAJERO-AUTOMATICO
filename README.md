# 🏧 Cajero Automático — Banco Digital

## 📋 Descripción
Sistema de cajero automático desarrollado en Java con interfaz gráfica Swing. Permite a los usuarios autenticarse con su número de cuenta y PIN para realizar operaciones bancarias como retiros, depósitos y transferencias. Incluye envío de comprobantes por correo electrónico y seguridad mediante hashing de PIN con salt.

## 🚀 Características
- Autenticación segura con PIN hasheado (SHA-256 + Salt)
- Bloqueo automático tras 3 intentos fallidos
- Retiro, depósito y transferencia entre cuentas
- Saldo actualizado en tiempo real
- Envío de comprobante al correo registrado
- Interfaz gráfica moderna con Java Swing

## 📦 Requisitos funcionales del sistema
- Java JDK 8 o superior
- NetBeans IDE
- MySQL 8.0 o superior
- Conector JDBC MySQL (mysql-connector-java)
- JavaMail API

## 🗄️ Base de Datos
1. Instala MySQL en tu computadora
2. Ejecuta el script SQL ubicado en la carpeta `src/` del proyecto
3. Configura las credenciales de conexión en `ConexionBD.java`

## ▶️ Uso
1. Clona el repositorio
2. Abre el proyecto en NetBeans
3. Configura la base de datos con el script incluido
4. Ejecuta el proyecto
5. Ingresa con número de cuenta (8 dígitos) y PIN (4 dígitos)

## 📁 Estructura del Proyecto
```
src/
├── cajeroautomatico/
│   ├── modelo/       → Clases de datos (Usuario, Cuenta, Operacion...)
│   ├── servicio/     → Lógica de negocio (CorreoService, CuentaService...)
│   ├── bd/           → Conexión y DAOs (ConexionBD, CuentaDAO...)
│   ├── vista/        → Pantallas (PantallaLogin, MenuPrincipal...)
│   └── util/         → Utilidades (HashUtil)
```

## 👥 Créditos
- Ymalai Leonardo
- Luis Diaz
- Manuel Alburquerque
- Starlyn Escalante

## 📅 Fecha
ITLA - Programación I - Abril 2026

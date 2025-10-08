Sistema de Gestión ONG - Proyecto Final
 
# Descripción General
El Sistema de Gestión ONG es una aplicación web desarrollada en Java EE con JSP y Servlets, diseñada para gestionar los proyectos, usuarios, actividades, participaciones e impactos a la ONU.
 
El sistema permite el registro, inicio de sesión y administración de distintos roles de usuario, garantizando la seguridad y control de acceso mediante un login y registro simple.
 
---
 
# Funcionalidades Principales
 
# Módulo de Autenticación
- Registro de nuevos usuarios con roles.
- Inicio y cierre de sesión.
- Protección de rutas según permisos.
 
# Gestión de Usuarios
- Listado, edición y eliminación de usuarios.
- Roles definidos: **Administrador**, **Coordinador** y **Voluntario**.
- Control de permisos en las vistas JSP mediante JSTL.
 
# Gestión de Proyectos
- Creación, actualización, eliminación y visualización de proyectos.
- Registro de fechas de inicio y fin, descripción y estado.
- Control de acceso según rol (solo administradores y coordinadores pueden modificar).
 
# Gestión de Actividades
- Asignación de actividades a proyectos.
- Registro de fechas, responsables y descripción.
 
# Participaciones e Impacto
- Control de la participación de voluntarios en cada actividad o proyecto.
- Seguimiento de indicadores de impacto social.
 
---
 
# Tecnologías Utilizadas
 
- Java EE JSP, Servlets, JSTL
- Spring Security + JWT
- MySQL Base de datos relacional
- HTML5, CSS3, Boxicons, Google Fonts
- JDBC DAO Pattern para persistencia de datos
- Visual Studio Code como entorno de desarrollo

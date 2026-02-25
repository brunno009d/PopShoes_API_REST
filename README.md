# PopShoes API - Backend de E-Commerce para Calzado

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.7-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4D4D4D?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

PopShoes es una API REST escalable diseñada para la gestión integral de un e-commerce de calzado. Implementa una arquitectura multicapa (Controller-Service-Repository) y un sistema de seguridad basado en estándares de la industria.

## 🚀 Funcionalidades Principales

- **Gestión de Usuarios y Perfiles:** Registro, actualización de datos y carga de fotos de perfil.
- **Catálogo de Calzado Dinámico:** CRUD completo con soporte para múltiples imágenes, categorías, tallas, marcas y colores.
- **Seguridad Robusta:** Autenticación y autorización mediante **JSON Web Tokens (JWT)** con filtros personalizados.
- **Búsqueda Avanzada:** Implementación de **Fuzzy Search** nativa en PostgreSQL para búsquedas de productos tolerantes a errores.
- **Sistema de Compras:** Gestión de pedidos, estados de envío y métodos de pago.

## 🛠️ Stack Tecnológico

- **Lenguaje:** Java 21.
- **Framework:** Spring Boot 3.5.7.
- **Seguridad:** Spring Security + JWT.
- **Persistencia:** Spring Data JPA con Hibernate.
- **Base de Datos:** PostgreSQL.
- **Infraestructura:** Dockerizado para despliegues consistentes.

## 📋 Configuración y Despliegue

### Requisitos
- Docker y Docker Compose
- JDK 21 (para desarrollo local)

### Ejecución con Docker
1. Clonar el repositorio.
2. Construir la imagen:
   ```bash
   docker build -t popshoes-backend .

# SprintBurg POS

> Sistema de Punto de Venta (POS) ágil y robusto diseñado para hamburgueserías locales.

![Estado del Proyecto](https://img.shields.io/badge/Estado-En_Desarrollo-orange)
![Java](https://img.shields.io/badge/Java-17-red)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0-green)

---

## 📋 Resumen

### 1. Descripción
**SprintBurg POS** es una aplicación web monolítica construida sobre el ecosistema Java Spring Boot. Su objetivo es digitalizar y optimizar el proceso de toma de pedidos y cobro en restaurantes de comida rápida, proporcionando una interfaz intuitiva para los cajeros y herramientas de gestión potentes para los gerentes. Utiliza **JSP** para un renderizado rápido del lado del servidor y **MySQL** para la integridad transaccional.

### 2. Problema Identificado
Las hamburgueserías locales a menudo enfrentan cuellos de botella operativos debido a:
* **Procesos Manuales:** La toma de pedidos en papel o sistemas genéricos lentos provoca filas largas y errores en la cocina.
* **Descontrol de Inventario:** La falta de descuento automático de stock (panes, carnes, bebidas) genera pérdidas desconocidas ("mermas") y falta de insumos en horas pico.
* **Ceguera Financiera:** Sin reportes históricos centralizados, los dueños no pueden identificar sus productos estrella ni sus horas de mayor venta.

### 3. Solución Propuesta
SprintBurg aborda estos problemas mediante una plataforma centralizada que ofrece:
* **Flujo de Venta Ágil:** Interfaz de "carrito rápido" optimizada para pantallas táctiles o uso con ratón, reduciendo el tiempo por transacción.
* **Gestión de Stock en Tiempo Real:** Cada venta descuenta automáticamente los ingredientes del inventario base.
* **Seguridad y Roles:** Acceso diferenciado para Cajeros (solo ventas) y Gerentes (acceso a reportes y ajustes administrativos).
* **Historial y Auditoría:** Registro inmutable de cada ticket con detalles de pago (Efectivo/Tarjeta) y referencia de voucher.

### 4. Arquitectura
El sistema sigue una **Arquitectura en Capas (MVC)** clásica para garantizar la mantenibilidad y escalabilidad.


## ⚙️ Requisitos Técnicos

Para ejecutar **SprintBurg POS**, asegúrese de que el entorno cumpla con:

### Entorno de Ejecución
| Componente | Requisito Mínimo | Notas                                                |
| :--- | :--- |:-----------------------------------------------------|
| **Java** | JDK 17+ | Spring Boot 4 requiere Java 17 como base.            |
| **Base de Datos** | MySQL 8.0 | Configurar credenciales en `application.properties`. |
| **Servidor App** | Tomcat (Embebido) | Incluido dentro del JAR generado.                    |
| **Maven** | 3.8+ | Necesario para compilar (build).                     |

### Stack Tecnológico
* **Backend:** Spring Boot 4.0
* **Frontend:** JSP + JSTL + CSS3 (Vanilla)
* **Seguridad:** Spring Security 6 (BCrypt Encryption)
* **ORM:** Hibernate / Spring Data JPA
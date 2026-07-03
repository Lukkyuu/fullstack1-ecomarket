# EcoMarket SPA - Microservicios

Este proyecto consiste en 10 microservicios orquestados y comunicados a través de un Gateway para la plataforma EcoMarket.

## 🚀 Arquitectura y Rutas Principales

A continuación se detalla la tabla de microservicios, el puerto interno en el que operan, su ruta principal de acceso y el propósito dentro del sistema.

| Microservicio       | Puerto | Ruta Base (API)      | Propósito                                                                 |
|---------------------|--------|----------------------|---------------------------------------------------------------------------|
| **Gateway Service** | `8080` | `/*`                 | Puerta de enlace (API Gateway) que enruta las peticiones a los servicios internos. |
| **User Service**    | `8081` | `/api/users`         | Gestión de usuarios, clientes y empleados.                                |
| **Auth Service**    | `8082` | `/api/auth`          | Manejo de roles, autenticación y permisos.                                |
| **Inventory Service**| `8093`| `/api/products`      | Gestión del catálogo de productos y stock.                                |
| **Order Service**   | `8084` | `/api/orders`        | Creación, seguimiento y procesamiento de pedidos de compra.               |
| **Store Service**   | `8085` | `/api/stores`        | Administración de sucursales físicas y tiendas virtuales.                 |
| **Shipping Service**| `8086` | `/api/shipments`     | Control de logística y despachos a domicilio.                             |
| **Supplier Service**| `8087` | `/api/suppliers`     | Manejo de proveedores externos de productos.                              |
| **Billing Service** | `8088` | `/api/invoices`      | Emisión de boletas y facturas electrónicas.                               |
| **Review Service**  | `8089` | `/api/reviews`       | Sistema de valoraciones y reseñas de productos.                           |
| **Coupon Service**  | `8090` | `/api/coupons`       | Validación y administración de cupones de descuento.                      |

## 📖 Documentación (Swagger)

Todos los servicios implementan **Springdoc (OpenAPI/Swagger)**. Se han anotado los controladores con `@Tag` y `@Operation` para proveer documentación clara.
Puedes acceder a la interfaz de Swagger de cada servicio (ej. `http://localhost:8081/swagger-ui.html`).

## 🧪 Pruebas Unitarias

Se han configurado pruebas unitarias para los controladores utilizando `Mockito` y `JUnit 5`.
Para ejecutar todas las pruebas:
```bash
./gradlew test
```
Los resultados y evidencias se encuentran guardados en el directorio `scratch/test_report.log`.

## 🐳 Docker y Postman

- El entorno se puede iniciar usando `docker-compose up -d`.
- Puedes importar la colección `EcoMarket.postman_collection.json` en Postman para probar los endpoints con ejemplos funcionales ya documentados.

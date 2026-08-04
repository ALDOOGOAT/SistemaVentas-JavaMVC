# 🛒 Sistema de Gestión de Ventas (Java MVC + PostgreSQL)

Sistema de escritorio integral para la gestión transaccional de proveedores, productos, clientes y ventas, desarrollado con arquitectura en capas desarticuladas para asegurar alta mantenibilidad y escalabilidad.

---

## 📐 Arquitectura del Sistema

El proyecto está diseñado bajo el patrón de arquitectura **Modelo-Vista-Controlador (MVC)** utilizando el patrón **DAO (Data Access Object)** para desacoplar completamente la interfaz gráfica, la lógica de negocio y la persistencia de datos en PostgreSQL.

```
[ Vista (Swing/JavaFX) ] <---> [ Controlador ] <---> [ DAO / Modelo ] <---> [ PostgreSQL ]
```

---

## 👥 Equipo de Desarrollo y Roles

| Integrante | Módulo Asignado | Responsabilidades | Entregables Clave |
| :--- | :--- | :--- | :--- |
| **Aldo** *(Líder)* | **Conexión Base + Ventas** | • Conexión Singleton leyendo `config.properties`<br>• Transacciones SQL atómicas (Venta + Detalle)<br>• Control y descuento automático de Stock | `Conexion.java`<br>`Venta.java`, `VentaDAO.java`<br>`VentaController.java`<br>`VistaVentas.java` |
| **Jared** | **Productos** | • CRUD completo de la tabla Productos<br>• ComboBox dinámico que consulta Proveedores | `Producto.java`, `ProductoDAO.java`<br>`ProductoController.java`<br>`VistaProductos.java` |
| **Daniel** | **Clientes** | • CRUD completo de la tabla Clientes<br>• Validaciones de interfaz (Email, Teléfono) | `Cliente.java`, `ClienteDAO.java`<br>`ClienteController.java`<br>`VistaClientes.java` |
| **Ilde** | **Proveedores + Dashboard** | • CRUD completo de la tabla Proveedores<br>• Menú Principal / Dashboard navegable | `Proveedor.java`, `ProveedorDAO.java`<br>`ProveedorController.java`<br>`VistaProveedores.java`<br>`MenuPrincipal.java` |

---

## 🗄️ Configuración de la Base de Datos Local

### 1. Creación de la Base de Datos
Cada desarrollador debe contar con una instancia local de PostgreSQL activa en `localhost:5432` y crear la base de datos:

```sql
CREATE DATABASE gestion_ventas;
```

> [!IMPORTANT]
> Ejecutar el script DDL `creacion_de_base_de_datos.sql` (incluido en la raíz del proyecto) para generar la estructura con las 5 tablas e integridad referencial (`Proveedores`, `Clientes`, `Productos`, `Ventas`, `Detalle_Ventas`).

### 2. Desacoplamiento de Credenciales (`config.properties`)

Para evitar conflictos de contraseña entre entornos locales de desarrollo, las credenciales no están quemadas en el código. Se leen desde un archivo externo `config.properties` ubicado en la raíz o `src/`:

```properties
db.url=jdbc:postgresql://localhost:5432/gestion_ventas
db.user=postgres
db.password=TU_CONTRASEÑA_LOCAL
```

> [!CAUTION]
> El archivo `config.properties` está registrado en `.gitignore` y **NUNCA** debe subirse al repositorio.

---

## 📁 Estructura del Proyecto

```text
SistemaVentas-JavaMVC/
├── config.properties.template   # Plantilla base para credenciales
├── creacion_de_base_de_datos.sql# Script DDL de PostgreSQL
├── .gitignore
├── pom.xml / build.gradle       # Dependencias (Driver PostgreSQL JDBC)
└── src/
    └── main/
        └── java/
            ├── config/          # Gestor de conexión JDBC (Singleton)
            ├── modelo/          # Entidades POJO (Atributos, Getters/Setters)
            ├── dao/             # Capa de Persistencia y Consultas SQL
            ├── controlador/     # Gestión de Eventos y Lógica de Negocio
            └── vista/           # Formulario e Interfaz Gráfica
```

---

## 🔀 Flujo de Trabajo en Git (Feature Branches)

Queda **estrictamente prohibido realizar `push` directo a la rama `main`**. Todos los avances deben ser desarrollados en ramas individuales e integrados mediante *Pull Requests*.

### 1. Clonar el repositorio y crear tu rama

```bash
git clone https://github.com/ALDO0GOAT/SistemaVentas-JavaMVC.git
cd SistemaVentas-JavaMVC

# Crear y cambiar a tu rama de trabajo
git checkout -b feature/tu-nombre-modulo
```

### 2. Confirmar y subir cambios

```bash
git add .
git commit -m "feat(modulo): descripción concisa de los cambios realizados"
git push origin feature/tu-nombre-modulo
```

### 3. Fusión de Código y Actualización

1. Abrir un **Pull Request (PR)** en GitHub hacia `main`.
2. Una vez revisado y aprobado por el líder del proyecto, sincroniza tu entorno local:

```bash
git checkout main
git pull origin main
git checkout feature/tu-nombre-modulo
git merge main
```

---

## ⚙️ Estándares y Reglas de Código

* **Nombres de Commits:** Utilizar prefijos claros (`feat:`, `fix:`, `docs:`, `refactor:`).
* **Manejo de Excepciones:** Toda consulta SQL en la capa DAO debe estar contenida en bloques `try-catch` capturando `SQLException` y cerrando explícitamente los recursos (`Connection`, `PreparedStatement`, `ResultSet`).
* **Pila Tecnológica:** Java JDK 17+, PostgreSQL JDBC Driver 42.7+, PostgreSQL 14+.

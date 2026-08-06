/*
 * Clase de conexión a la base de datos PostgreSQL.
 * Implementa el patrón Singleton para reutilizar una única instancia de conexión.
 * Las credenciales se leen desde el archivo config.properties.
 */
package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Gestor de conexión JDBC a PostgreSQL mediante patrón Singleton.
 * Lee las credenciales desde {@code config.properties} para evitar
 * datos sensibles en el código fuente.
 *
 * @author Aldo
 */
public class Conexion {

    // Instancia única (Singleton)
    private static Conexion instancia;

    // Parámetros de conexión leídos desde config.properties
    private String url;
    private String usuario;
    private String password;

    // Conexión activa
    private Connection conexion;

    /**
     * Constructor privado: carga las propiedades desde config.properties.
     * Se busca el archivo primero en la raíz del proyecto y luego en src/.
     */
    private Conexion() {
        Properties props = new Properties();
        try {
            // Intentar cargar desde la raíz del proyecto
            props.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            try {
                // Intentar cargar desde src/
                props.load(new FileInputStream("src/config.properties"));
            } catch (IOException ex) {
                System.err.println("ERROR: No se encontró el archivo config.properties");
                System.err.println("Crea el archivo a partir de config.properties.template");
                // Valores por defecto como respaldo
                props.setProperty("db.url", "jdbc:postgresql://localhost:5432/tiendita");
                props.setProperty("db.user", "postgres");
                props.setProperty("db.password", "0506");
            }
        }

        this.url = props.getProperty("db.url", "jdbc:postgresql://localhost:5432/gestion_ventas");
        this.usuario = props.getProperty("db.user", "postgres");
        this.password = props.getProperty("db.password", "0506");
    }

    /**
     * Obtiene la instancia única de Conexion (Singleton thread-safe).
     *
     * @return instancia única de {@code Conexion}
     */
    public static synchronized Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    /**
     * Establece y retorna la conexión activa a PostgreSQL.
     * Si la conexión está cerrada o es nula, crea una nueva.
     *
     * @return objeto {@link Connection} activo
     * @throws SQLException si ocurre un error al conectar
     */
    public Connection getConexion() throws SQLException {
        try {
            if (conexion == null || conexion.isClosed()) {
                // Cargar el driver de PostgreSQL
                Class.forName("org.postgresql.Driver");
                conexion = DriverManager.getConnection(url, usuario, password);
                System.out.println("✅ Conexión exitosa a la base de datos: " + url);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: Driver de PostgreSQL no encontrado.");
            System.err.println("Asegúrate de tener el JAR postgresql-42.7.x en las librerías del proyecto.");
            throw new SQLException("Driver JDBC de PostgreSQL no disponible.", e);
        } catch (SQLException e) {
            System.err.println("ERROR: No se pudo conectar a la base de datos.");
            System.err.println("Verifica que PostgreSQL esté corriendo y que config.properties sea correcto.");
            throw e;
        }
        return conexion;
    }

    /**
     * Cierra la conexión activa de forma segura.
     */
    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("🔒 Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("ERROR al cerrar la conexión: " + e.getMessage());
        }
    }
}

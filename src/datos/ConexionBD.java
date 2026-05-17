package datos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo1.Carrera;
import modelo2.Escuela;

public class ConexionBD {
    private static final String URL = "jdbc:mariadb://localhost:3306/uasd_carreras";
    private static final String USUARIO = "root";  // Tu usuario de HeidiSQL
    private static final String CONTRASENA = "ML12102003";   // Tu contraseña (si tienes)
    
    private Connection conexion;
    
    // Establecer conexión
    public Connection conectar() throws SQLException {
        try {
            // Driver para MariaDB (diferente al de MySQL)
            Class.forName("org.mariadb.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            System.out.println(" Conexión exitosa a MariaDB");
            return conexion;
        } catch (ClassNotFoundException e) {
            System.err.println(" Error: Driver JDBC de MariaDB no encontrado");
            System.err.println("Debes agregar el archivo mariadb-java-client-x.x.x.jar");
            throw new SQLException("Driver no encontrado", e);
        }
    }
    
    // Cerrar conexión
    public void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada");
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
    
    // Obtener todas las escuelas para el comboBox
    public List<Escuela> obtenerEscuelas() {
        List<Escuela> escuelas = new ArrayList<>();
        String sql = "SELECT id, nombre, descripcion FROM escuelas ORDER BY nombre";
        
        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Escuela escuela = new Escuela();
                escuela.setId(rs.getInt("id"));
                escuela.setNombre(rs.getString("nombre"));
                escuela.setDescripcion(rs.getString("descripcion"));
                escuelas.add(escuela);
            }
            
            System.out.println(" Se cargaron " + escuelas.size() + " escuelas");
            
        } catch (SQLException e) {
            System.err.println("Error al obtener escuelas: " + e.getMessage());
        }
        
        return escuelas;
    }
    
    // Obtener carreras filtradas por escuela
    public List<Carrera> obtenerCarrerasPorEscuela(int escuelaId) {
        List<Carrera> carreras = new ArrayList<>();
        String sql = "SELECT c.id, c.nombre, c.duracion_semestres, c.creditos_totales, " +
                     "c.descripcion, e.nombre as escuela_nombre " +
                     "FROM carreras c " +
                     "JOIN escuelas e ON c.escuela_id = e.id " +
                     "WHERE c.escuela_id = ? " +
                     "ORDER BY c.nombre";
        
        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, escuelaId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Carrera carrera = new Carrera();
                    carrera.setId(rs.getInt("id"));
                    carrera.setNombre(rs.getString("nombre"));
                    carrera.setDuracionSemestres(rs.getInt("duracion_semestres"));
                    carrera.setCreditosTotales(rs.getInt("creditos_totales"));
                    carrera.setDescripcion(rs.getString("descripcion"));
                    carrera.setNombreEscuela(rs.getString("escuela_nombre"));
                    carreras.add(carrera);
                }
            }
            
            System.out.println("🔍 Se encontraron " + carreras.size() + " carreras");
            
        } catch (SQLException e) {
            System.err.println("Error al obtener carreras: " + e.getMessage());
        }
        
        return carreras;
    }
    
    // Probar conexión
    public boolean probarConexion() {
        try (Connection conn = conectar()) {
            System.out.println(" Conexión exitosa a MariaDB - Base de datos: uasd_carreras");
            return true;
        } catch (SQLException e) {
            System.err.println(" Error de conexión: " + e.getMessage());
            return false;
        }
    }
}
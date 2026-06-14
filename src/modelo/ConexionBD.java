package modelo; // Al estar en este paquete, se define como parte del Modelo

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConexionBD {
    // Definimos las variables.
    private String url = "jdbc:mysql://localhost:3306/restaurante";
    private String usuario = "root";
    private String contrasena = "1234"; // Pon aquí la clave de tu MySQL Workbench si tienes una
    private Connection conexion = null;

    // Este metodo devuelve un objeto de tipo Connection listo para usarse
    public Connection conectar() {
        try {
            conexion = DriverManager.getConnection(url, usuario, contrasena);
        } catch (SQLException error) {
            // Usamos JOptionPane como herramienta visual de alerta permitida en clase
            JOptionPane.showMessageDialog(null, "Error al conectar la base de datos: " + error.getMessage());
        }
        return conexion;
    }
}
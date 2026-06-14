package com.restaurante.controlador;

import com.restaurante.vista.LoginVista;
import com.restaurante.vista.RegistrarMeseroVista;
import modelo.ConexionBD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class LoginControlador implements ActionListener {
    
    private LoginVista vista;

    public LoginControlador(LoginVista vista) {
        this.vista = vista;
        this.vista.getBtnIngresar().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnIngresar()) {
            validarIngreso();
        }
    }

    private void validarIngreso() {
        String usuario = vista.getUsuario();
        String password = vista.getPassword();

        // Validación visual preliminar (Heurística de prevención de errores)
        if (usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos.");
            return;
        }

        // Instanciamos tu clase de conexión y abrimos el flujo JDBC
        ConexionBD miConexion = new ConexionBD(); 
        Connection conexion = miConexion.conectar();
        
        // Sentencia SQL parametrizada para buscar en la tabla meseros
        String sql = "SELECT * FROM meseros WHERE cedula = ? AND contrasena = ?";

        try {
            // PreparedStatement para evitar inyección SQL
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();

            // Si el ResultSet encuentra una fila, los datos son correctos
            if (rs.next()) {
                String nombreMesero = rs.getString("nombre");
                JOptionPane.showMessageDialog(vista, "¡Bienvenido al sistema, " + nombreMesero + "!");
                
                vista.dispose(); // Cerramos la ventana de Login
                
                // Abrimos la pantalla de registro de meseros
                RegistrarMeseroVista vistaMesero = new RegistrarMeseroVista();
                MeseroControlador controladorMesero = new MeseroControlador(vistaMesero); 
                vistaMesero.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(vista, "Usuario o contraseña incorrectos.");
            }
            
            // Cierre obligatorio de conexiones fisicas
            conexion.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error en el sistema de autenticación: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }
}
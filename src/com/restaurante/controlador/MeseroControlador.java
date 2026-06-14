package com.restaurante.controlador;

import com.restaurante.vista.RegistrarMeseroVista;
import modelo.ConexionBD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class MeseroControlador implements ActionListener {
    
    private RegistrarMeseroVista vista;

    public MeseroControlador(RegistrarMeseroVista vista) {
        this.vista = vista;
        this.vista.getBtnGuardar().addActionListener(this);
        this.vista.getBtnSalir().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnGuardar()) {
            guardarMesero();
        } else if (e.getSource() == vista.getBtnSalir()) {
            vista.dispose();
        }
    }

    private void guardarMesero() {
        try {
            String cedula = vista.getCedula();
            String nombre = vista.getNombre();
            String telefono = vista.getTelefono();
            String direccion = vista.getDireccion();
            String email = vista.getEmail();
            // Por defecto, asignamos una contraseña inicial (1234)
            String contrasenaPredeterminada = "1234"; 

            if (cedula.isEmpty() || nombre.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Por favor llene los campos obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Instanciamos tu clase correcta ConexionBD
            ConexionBD miConexion = new ConexionBD(); 
            Connection conexion = miConexion.conectar();
            
            // Agregamos el campo contraceña a la inserción SQL
            String sql = "INSERT INTO meseros (cedula, nombre, telefono, direccion, email, contrasena) VALUES (?, ?, ?, ?, ?, ?)";
            
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, cedula);
            ps.setString(3, telefono);
            ps.setString(4, direccion);
            ps.setString(5, email);
            ps.setString(6, contrasenaPredeterminada);
            
            ps.executeUpdate();
            JOptionPane.showMessageDialog(vista, "Mesero registrado exitosamente.");
            
            conexion.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al guardar en la base de datos: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }
}
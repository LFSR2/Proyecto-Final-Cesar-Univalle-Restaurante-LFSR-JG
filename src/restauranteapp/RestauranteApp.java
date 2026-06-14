package restauranteapp;

import com.restaurante.controlador.LoginControlador;
import com.restaurante.vista.LoginVista;

public class RestauranteApp {

    public static void main(String[] args) {
        // 1. Instanciamos la interfaz grafica del Login
        LoginVista vistaLogin = new LoginVista();
        
        // 2. Creamos el controlador encargado de escuchar los eventos y le pasamos la vista
        LoginControlador controlador = new LoginControlador(vistaLogin);
        
        // 3. Centramos la ventana en la pantalla
        vistaLogin.setLocationRelativeTo(null);
        vistaLogin.setVisible(true);
    }
}
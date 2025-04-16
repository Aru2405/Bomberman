package Modelo;

import VistaControlador.VistaInicio;

public class MainVista {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new VistaInicio(); 
        });
    }
}


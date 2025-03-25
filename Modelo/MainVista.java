package Modelo;

import VistaControlador.VistaJuego;

public class MainVista {
    public static void main(String[] args) {
        // Ejecutar la interfaz en el hilo de eventos de Swing
    	
        javax.swing.SwingUtilities.invokeLater(() -> {
        	new VistaJuego(11, 17);
            Tablero.getTablero().notificarCambio();

        });
    }
}

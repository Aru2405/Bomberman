package Modelo;

public class Partida {
	private Tablero tablero;
    private int nivel;
    private boolean juegoActivo;

    public Partida(int filas, int columnas) {
    	this.tablero= tablero.getTablero();
    	this.nivel=1;
    	this.juegoActivo=true;
    }
    
    public void iniciarJuego() {
    	System.out.println("¡El juego ha comenzado!");
    }
    
    public void actualizarJuego() {
    	System.out.println("¡Actualizando el juego!");
    }
    
    public void terminarJuego() {
        this.juegoActivo = false;
        System.out.println("El juego ha terminado.");
    }
    
    public boolean estaActivo() {
        return juegoActivo;
    }
    
}

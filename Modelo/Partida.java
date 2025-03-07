package Modelo;

public class Partida {
	
	private Tablero tablero;
    private int nivel;
    private boolean juegoActivo;
    private static Partida miPartida;

    private Partida(int filas, int columnas){
    	this.tablero= tablero.getTablero();
    	this.nivel=1;
    	this.juegoActivo=true;
    	
    }
    
    public static Partida getPartida(){
        if (miPartida == null) {
        	miPartida = new Partida(11, 17);
        }
        return miPartida;
    }

   
    
    public void iniciarJuego(){
    	System.out.println("El juego ha empezado");
  
    }
    
    public void actualizarJuego(){
    	System.out.println("Actualizando el juego");
    }
    
    public void terminarJuego(){
        this.juegoActivo = false;
        System.out.println("El juego ha terminado.");
    }
    
    public boolean estaActivo(){
        return juegoActivo;
    }
    
}

package Modelo;

public class Partida {
	
    private int nivel;
    private boolean juegoActivo;
    private static Partida miPartida;

    private Partida(int filas, int columnas){
  
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
        System.exit(0);

    }
    
    public boolean estaActivo(){
        return juegoActivo;
    }
    
}

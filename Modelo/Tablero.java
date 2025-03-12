package Modelo;

import java.util.Random;

public class Tablero {
	
    private int filas = 11;
    private int columnas = 17;
    private Casilla[][] celdas;
    private static Tablero miTablero;
    private int bombermanX = 0;
    private int bombermanY = 0;
    
    private Tablero(){
        this.celdas = new Casilla[filas][columnas];
        inicializarTablero();
    }

    public static Tablero getTablero(){
        if (miTablero == null) {
            miTablero = new Tablero();
        }
        return miTablero;
    }

    private void inicializarTablero(){
    	
        Random rand = new Random();

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++){
                celdas[i][j] = new Casilla();
                //No colocar bloques en las posiciones (0,0), (0,1) y (1,0)
                if ((i == 0 && j == 0) || (i == 0 && j == 1) || (i == 1 && j == 0)){
                    continue;
                    
                    
                }
                //Colocar bloques duros en las coordenadas impares
                if (i % 2 == 1 && j % 2 == 1){
                    celdas[i][j].colocarBloqueDuro();
                } 
                
                //Colocar bloques blandos aleatoriamente en las casillas vacias
                else if (rand.nextDouble() < 0.4){
                	
                    celdas[i][j].colocarBloqueBlando();
                }
            }
        }
        
        
    }

    public Casilla getCasilla(int x, int y){
    	
        if (esValida(x, y)){
        	
            return celdas[x][y];
            
        }
        return null;
    }

     public Casilla[][] getCasillas(){
    	return celdas;
    }

    public boolean esValida(int x, int y){
    	
        return x >= 0 && y >= 0 && x < filas && y < columnas;
    }

    public void imprimirTablero(){
        for (int i = 0; i < filas; i++){
        	
            for (int j = 0; j < columnas; j++){
    
                if (i == this.bombermanX && j == this.bombermanY){
                	
                    System.out.print("[B] ");
                } else {
                    System.out.print(celdas[i][j] + " ");
                }
            }
            
            System.out.println();
        }
    }
    
    public void actualizarPosicionBomberman(int x, int y){
    	
        this.bombermanX = x;
        this.bombermanY = y;
        
    }



    public void manejarExplosion(int x, int y) {
        System.out.println("Procesando explosion en (" + x + ", " + y + ")");

        int[][] direcciones = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {0, 0}}; 

        for (int[] dir : direcciones) {
            int nuevaX = x + dir[0];
            int nuevaY = y + dir[1];

            if (!esValida(nuevaX, nuevaY)) {}
            else{
            	Casilla afectada = getCasilla(nuevaX, nuevaY);
            	if (afectada.tieneBloqueBlando()){
            		afectada.destruirBloqueBlando();
            	} 
            
            	if (afectada.tieneBomba()) {
            		boolean tiene = afectada.tieneBomba();
            		if (tiene == true) {                        
            			afectada.detonarBomba(); 
            			
            		}
            	}
                if (bombermanX == nuevaX && bombermanY == nuevaY){
                    System.out.println("Bomberman ha sido alcanzado por la explosion en (" + bombermanX + ", " + bombermanY + ")");
                    Partida.getPartida().terminarJuego();
                    return;
                    
                    
                }
            }
        }
   public Bomberman getBomberman() {
        for (int i = 0; i < celdas.length; i++) {
            for (int j = 0; j < celdas[i].length; j++) {
                if (celdas[i][j].tieneBomberman()) {
                    return celdas[i][j].getBomberman();
                }
            }
        }
        return null; // Si no se encuentra al Bomberman
    }

  
    public void notificarCambio(){
        setChanged();
        notifyObservers();
   

    }
    
   

}

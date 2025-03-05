package Modelo;

public class Tablero {
	private int filas=11;
	private int columnas=17;
	private Casilla[][]celdas;
	private static Tablero miTablero;
	
	private Tablero() {
        this.celdas = new Casilla[filas][columnas];
    }
	
	public static Tablero getTablero(){
        if (miTablero == null) {
            miTablero = new Tablero();
        }
        return miTablero;
        
    }
	
	private void inicializarTablero() {
		for (int i=0; i<filas; i++) {
			for (int j=0; j<columnas; j++) {
				celdas[i][j]=new Casilla();
			}
		}
	}
	
	public Casilla getCasilla(int x, int y) {
		if (esValida(x,y)) {
			return celdas[x][y];
		}		
		return null;
	}
	
	public boolean esValida(int x, int y) {
		return x>=0 && y>=0 && x<filas && y<columnas;
	}
	
	public void imprimirTablero() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print("[ ]"); // Solo muestra casillas vacías por ahora
            }
            System.out.println();
        }
    }
	
	public void agregarBomba(bomba nuevaBomba) {
	    int x = nuevaBomba.getX();
	    int y = nuevaBomba.getX();
	    //añadirla a la casilla

	}

	public void procesarExplosión(int[][] posiciones) {
	    for (int[] pos : posiciones) {
	        int x = pos[0];
	        int y = pos[1];
	        
	        //coger cada casilla del radio de explosion y mirar que hay dentro y hacer lo que toque

	    }
	}
	    public void notificarCambio(){
        setChanged();
        notifyObservers();
    }

}

package modelo;

public class Tablero {
	private int filas;
	private int columnas;
	private Casilla[][]celdas;
	
	public Tablero(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.celdas = new Casilla[filas][columnas];
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
}

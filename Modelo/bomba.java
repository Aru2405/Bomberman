package Modelo;

import java.util.Timer;
import java.util.TimerTask;

public class Bomba{
	
    private int x;
    private int y;
    private int radio=1;

    public Bomba(int x, int y){
        this.x = x;
        this.y = y; 
       
    }
    
    public void iniciarTemporizador() {
        System.out.println("Temporizador de Bomba en: (" + x + ", " + y + ") iniciado");

        new Timer().schedule( 
            new TimerTask() {
                @Override
                public void run() {
                    Tablero.getTablero().procesarExplosión(obtenerPosicionesAfectadas());
                }
            }, 
            3000//Explota despues de 3 segundos
        );
    }

    private int[][] obtenerPosicionesAfectadas() {
        //Direcciones de la explosion derecha, izquierda, abajo, arriba
        int[][] direcciones = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};       
        //posiciones afectadas por la bomba
        int[][] posicionesAfectadas = new int[direcciones.length + 1][2];
        //posición de la bomba
        posicionesAfectadas[0] = new int[]{x, y};
        // Verificar y agregar posiciones válidas
        int index = 1;
        for (int[] direccion : direcciones) {
            int nuevaX = x + direccion[0];
            int nuevaY = y + direccion[1];
            if (Tablero.getTablero().esValida(nuevaX, nuevaY)) {
                posicionesAfectadas[index++] = new int[]{nuevaX, nuevaY};
            }
        }

        return posicionesAfectadas;
    }


	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}



}

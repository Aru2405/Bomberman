package Modelo;

import java.util.Timer;
import java.util.TimerTask;

public class Bomba{	
	
    private int x;
    private int y;
    private Timer timer; 

    public Bomba(int x, int y){
    	
        this.x = x;
        this.y = y; 
        this.timer = new Timer();
       
    }    

    public void iniciarTemporizador(){
    	
        System.out.println("Temporizador iniciado en (" + x + ", " + y + ")");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Bomba exploto en (" + x + ", " + y + ")");
                Tablero.getTablero().manejarExplosion(x, y);
                timer.cancel();
            }
        }, 3000);//Explosion después de 3 segundos
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}




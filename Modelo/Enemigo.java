package Modelo;

import java.util.Timer;
import java.util.TimerTask;

public class Enemigo {
    private EstrategiaMovimiento estrategiaMovimiento;
    private int x, y;
    private Timer timer;

    public Enemigo(int x, int y, EstrategiaMovimiento estrategia) {
        this.x = x;
        this.y = y;
        this.estrategiaMovimiento = estrategia;

    }
    
    public void iniciar() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                estrategiaMovimiento.mover(Enemigo.this);
            }
        }, 0, 10000);
    }



    public void cambiarEstrategia(EstrategiaMovimiento nuevaEstrategia) {
        this.estrategiaMovimiento = nuevaEstrategia;
    }

    public void detener() {
        if (timer != null) {
            timer.cancel();
        }
    }


    // Getters y setters
    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
}

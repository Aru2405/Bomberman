package Modelo;

import java.util.Timer;
import java.util.TimerTask;

public class Bomba {
    private int x;
    private int y;
    private Timer timer;
    private boolean enExplosion = false;
    private int frame = 0;

    public Bomba(int x, int y) {
        this.x = x;
        this.y = y;
        this.timer = new Timer();
    }

    public boolean estaExplotando() {
        return enExplosion;
    }

    public void iniciarExplosion() {
        enExplosion = true;
    }

    public void finalizarExplosion() {
        enExplosion = false;
    }

    public void iniciarTemporizador() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Tablero.getTablero().manejarExplosion(x, y, true, false);
                animarExplosion();
            }
        }, 3000);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void animarExplosion() {
        enExplosion = true;

        Timer explosionTimer = new Timer();
        explosionTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                if (frame >= 5) {
                    finalizarExplosion();

                    Tablero.getTablero().eliminarBomba(x, y);
                    explosionTimer.cancel();
                } else {
                    frame++;
                    Tablero.getTablero().notificarCambio();
                }
            }
        }, 0, 400);
    }
}
package Modelo;

import java.util.Timer;
import java.util.TimerTask;

public class BombaNegra extends Bomba {

    private static boolean activa = false;

    public BombaNegra(int x, int y) {
        super(x, y);
        if (activa) {
            throw new IllegalStateException("Ya hay una Ultra bomba activa");
        }
        activa = true;
    }

    @Override
    public void iniciarTemporizador() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Tablero.getTablero().manejarExplosion(getX(), getY(), false, false);
                Tablero.getTablero().eliminarBomba(getX(), getY());
                finalizarExplosion();
                activa = false;
            }
        }, 3000);
    }

    @Override
    public void finalizarExplosion() {
        super.finalizarExplosion();
        activa = false;
    }

    public static boolean estaActiva() {
        return activa;
    }
}

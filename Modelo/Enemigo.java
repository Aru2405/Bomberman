package Modelo;

import java.util.Timer;
import java.util.TimerTask;

public class Enemigo {
    private EstrategiaMovimiento estrategiaMovimiento;
    private int x, y;
    private Timer timer;

    public Enemigo(int x, int y) {
        this.x = x;
        this.y = y;
        this.estrategiaMovimiento = new MovimientoAleatorio();
    }

    public void iniciar() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mover();
            }
        }, 0, 1000);
    }

    public void mover() {
        int[] dir = estrategiaMovimiento.calcularDireccion();
        int nuevaX = this.x + dir[0];
        int nuevaY = this.y + dir[1];

        Tablero tablero = Tablero.getTablero();
        if (tablero.esValida(nuevaX, nuevaY)) {
            Casilla actual = tablero.getCasilla(this.x, this.y);
            Casilla destino = tablero.getCasilla(nuevaX, nuevaY);

            if (destino.tieneBomberman()) {
                tablero.getBomberman().morir();
                return;
            }

            if (!destino.tieneBloqueDuro() && !destino.tieneBloqueBlando() &&
                    !destino.tieneBomba() && !destino.tieneEnemigo()) {
                actual.eliminarEnemigo();
                this.x = nuevaX;
                this.y = nuevaY;
                destino.colocarEnemigo(this);
            }
        }

        tablero.notificarCambio();
    }

    public void cambiarEstrategia(EstrategiaMovimiento nuevaEstrategia) {
        this.estrategiaMovimiento = nuevaEstrategia;
    }

    public void detener() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}

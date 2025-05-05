package Modelo;

import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Stream;

public class Enemigo {
    private EstrategiaMovimiento estrategiaMovimiento;
    private int x, y;
    private Timer timer;
    private  boolean activo;

    public Enemigo(int x, int y) {
        this.x = x;
        this.y = y;
        this.estrategiaMovimiento = new MovimientoAleatorio();
    }

    public void iniciar() {
        if (activo || timer != null) return; 
        activo = true;
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

            boolean libre = Stream.of(
            !destino.tieneBloqueDuro(),
            !destino.tieneBloqueBlando(),
            !destino.tieneBomba(),
            !destino.tieneEnemigo()
            ).allMatch(Boolean::booleanValue);

            if (libre) {
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
            timer = null;
        }
        activo = false;
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

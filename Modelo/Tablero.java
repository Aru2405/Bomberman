package Modelo;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Observable;

public class Tablero extends Observable {

    private int filas = 11;
    private int columnas = 17;
    private Casilla[][] celdas;
    private static Tablero miTablero;
    private Bomberman bomberman = new Bomberman(0, 0);
    private Timer timer = new Timer();

    private Tablero() {
        this.celdas = new Casilla[filas][columnas];
        inicializarTablero();
    }

    public static Tablero getTablero() {
        if (miTablero == null) {
            miTablero = new Tablero();
        }
        return miTablero;
    }

    private void inicializarTablero() {

        Random rand = new Random();

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas[i][j] = new Casilla();
                // No colocar bloques en las posiciones (0,0), (0,1) y (1,0)
                if ((i == 0 && j == 0) || (i == 0 && j == 1) || (i == 1 && j == 0)) {
                    continue;

                }
                // Colocar bloques duros en las coordenadas impares
                if (i % 2 == 1 && j % 2 == 1) {
                    celdas[i][j].colocarBloqueDuro();
                }

                // Colocar bloques blandos aleatoriamente en las casillas vacias
                else if (rand.nextDouble() < 0.4) {

                    celdas[i][j].colocarBloqueBlando();
                }
            }
        }

    }

    public Casilla getCasilla(int x, int y) {

        if (esValida(x, y)) {

            return celdas[x][y];

        }
        return null;
    }

    public boolean esValida(int x, int y) {

        return x >= 0 && y >= 0 && x < filas && y < columnas;
    }

    public void imprimirTablero() {
        for (int i = 0; i < filas; i++) {

            for (int j = 0; j < columnas; j++) {

                if (i == bomberman.getX() && j == bomberman.getY()) {

                    System.out.print("[B] ");
                } else {
                    System.out.print(celdas[i][j] + " ");
                }
            }

            System.out.println();
        }
    }

    public void manejarExplosion(int x, int y) {
        System.out.println("Procesando explosión en (" + x + ", " + y + ")");
        int[][] direcciones = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 }, { 0, 0 } };

        for (int[] dir : direcciones) {
            int nuevaX = x + dir[0];
            int nuevaY = y + dir[1];

            if (!esValida(nuevaX, nuevaY))
                continue;

            Casilla afectada = getCasilla(nuevaX, nuevaY);
            afectada.iniciarExplosion();

            if (afectada.tieneBloqueBlando()) {
                afectada.destruirBloqueBlando();
            }

            if (bomberman.getX() == nuevaX && bomberman.getY() == nuevaY) {
                System.out.println("Bomberman ha sido alcanzado por la explosión!");
                bomberman.morir();
                return;
            }
        }
        notificarCambio();

        Timer timerExplosion = new Timer();
        timerExplosion.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for (int[] dir : direcciones) {
                    int nuevaX = x + dir[0];
                    int nuevaY = y + dir[1];
                    if (esValida(nuevaX, nuevaY) && getCasilla(nuevaX, nuevaY).estaEnExplosion()) {
                        if (bomberman.getX() == nuevaX && bomberman.getY() == nuevaY) {
                            System.out.println("🔥 Bomberman entró en una casilla en explosión y murió.");
                            bomberman.morir();
                            timerExplosion.cancel();
                            return;
                        }
                    }
                }
            }
        }, 0, 200);

        // Finalizar la explosión después de 2 segundos
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (int[] dir : direcciones) {
                    int nuevaX = x + dir[0];
                    int nuevaY = y + dir[1];
                    if (esValida(nuevaX, nuevaY)) {
                        getCasilla(nuevaX, nuevaY).finalizarExplosion();
                    }
                }
                notificarCambio();
                timerExplosion.cancel(); // Detener la verificación de colisión
            }
        }, 2000);

    }

    public Casilla[][] getCeldas() {
        return celdas;
    }

    public void eliminarBomba(int x, int y) {
        if (esValida(x, y)) {
            Casilla casilla = getCasilla(x, y);
            if (casilla.tieneBomba()) {
                casilla.detonarBomba();
                notificarCambio(); //
            }
        }
    }

    public Bomberman getBomberman() {
        return bomberman;
    }

    public void notificarCambio() {
        setChanged();
        notifyObservers();

    }

    public int contarBombasActivas() {
        int contador = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (celdas[i][j].tieneBomba()) {
                    contador++;
                }
            }
        }
        return contador;
    }

}

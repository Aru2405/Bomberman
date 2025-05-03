package Modelo;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.Observable;
import java.util.List;

public class Tablero extends Observable {

    private int filas = 11;
    private int columnas = 17;
    private Casilla[][] celdas;
    private static Tablero miTablero;
    private Bomberman bomberman;
    private Timer timer = new Timer();
    private List<Enemigo> enemigos = new ArrayList<>();

    private Tablero() {
        this.celdas = new Casilla[filas][columnas];

        String color = Partida.getPartida().getColorJugador();
        this.bomberman = new Bomberman(0, 0, color);
        System.out.println("Bomberman creado con color: " + color);
    }

    public static Tablero getTablero() {
        if (miTablero == null) {
            miTablero = new Tablero();
        }
        return miTablero;
    }

    public void inicializar(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.celdas = new Casilla[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas[i][j] = new Casilla();
            }
        }

        enemigos.clear(); 
    }

    public void aplicarConfiguracion(ConfiguradorNivel configurador) {
        configurador.configurar();

        Casilla casillaInicial = getCasilla(bomberman.getX(), bomberman.getY());
        casillaInicial.colocarBomberman(bomberman);
        notificarCambio();
    }

    public void añadirEnemigo(Enemigo e) {
        enemigos.add(e);
        getCasilla(e.getX(), e.getY()).colocarEnemigo(e);
    }

    public Casilla getCasilla(int fila, int columna) {
        if (fila >= 0 && fila < filas && columna >= 0 && columna < columnas) {
            return celdas[fila][columna];
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

    public void manejarExplosion(int x, int y, boolean simple, boolean atraviesaBloquesDuros) {
        System.out.println("💥 Explosión en (" + x + "," + y + ") | simple=" + simple + " | atraviesaBloquesDuros=" + atraviesaBloquesDuros);

        List<int[]> direcciones = new ArrayList<>();

        if (simple) {
            direcciones.add(new int[]{0, 0});
            direcciones.add(new int[]{1, 0});
            direcciones.add(new int[]{-1, 0});
            direcciones.add(new int[]{0, 1});
            direcciones.add(new int[]{0, -1});
        } else {
            for (int i = x - 1; i >= 0; i--) {
                Casilla cas = getCasilla(i, y);
                if (!atraviesaBloquesDuros && cas.tieneBloqueDuro()) break;
                direcciones.add(new int[]{i - x, 0});
            }
            for (int i = x + 1; i < filas; i++) {
                Casilla cas = getCasilla(i, y);
                if (!atraviesaBloquesDuros && cas.tieneBloqueDuro()) break;
                direcciones.add(new int[]{i - x, 0});
            }
            for (int j = y - 1; j >= 0; j--) {
                Casilla cas = getCasilla(x, j);
                if (!atraviesaBloquesDuros && cas.tieneBloqueDuro()) break;
                direcciones.add(new int[]{0, j - y});
            }
            for (int j = y + 1; j < columnas; j++) {
                Casilla cas = getCasilla(x, j);
                if (!atraviesaBloquesDuros && cas.tieneBloqueDuro()) break;
                direcciones.add(new int[]{0, j - y});
            }
        }

        for (int[] dir : direcciones) {
            int nuevaX = x + dir[0];
            int nuevaY = y + dir[1];

            if (!esValida(nuevaX, nuevaY)) continue;

            Casilla cas = getCasilla(nuevaX, nuevaY);

            if (!atraviesaBloquesDuros && cas.tieneBloqueDuro()) continue;

            cas.iniciarExplosion();

            if (cas.tieneBloqueBlando())
                cas.destruirBloqueBlando();

            if (cas.tieneEnemigo()) {
                Enemigo enemigo = cas.getEnemigo();
                enemigo.detener();
                enemigos.removeIf(e -> e.getX() == enemigo.getX() && e.getY() == enemigo.getY());
                cas.eliminarEnemigo();
                System.out.println("💀 Enemigo eliminado en (" + enemigo.getX() + "," + enemigo.getY() + ")");
            }

            if (bomberman.getX() == nuevaX && bomberman.getY() == nuevaY) {
                bomberman.morir();
                return;
            }
        }

        Timer fuegoEnemigos = new Timer();
        fuegoEnemigos.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                List<Enemigo> eliminados = new ArrayList<>();

                for (Enemigo enemigo : new ArrayList<>(enemigos)) {
                    Casilla casilla = getCasilla(enemigo.getX(), enemigo.getY());
                    if (casilla.estaEnExplosion()) {
                        enemigo.detener();
                        eliminados.add(enemigo);
                        casilla.eliminarEnemigo();
                        System.out.println("💥 Enemigo murió por fuego en (" + enemigo.getX() + "," + enemigo.getY() + ")");
                    }
                }

                enemigos.removeAll(eliminados);
            }
        }, 0, 200);

        notificarCambio();

        Timer fuegoTimer = new Timer();
        fuegoTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Casilla actual = getCasilla(bomberman.getX(), bomberman.getY());
                if (actual.estaEnExplosion()) {
                    System.out.println("🔥 Bomberman entró en fuego activo y murió.");
                    bomberman.morir();
                    fuegoTimer.cancel();
                }
            }
        }, 0, 200);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (int[] dir : direcciones) {
                    int nuevaX = x + dir[0];
                    int nuevaY = y + dir[1];
                    if (esValida(nuevaX, nuevaY)) {
                        getCasilla(nuevaX, nuevaY).finalizarExplosion();
                    }
                    fuegoEnemigos.cancel();
                }
                notificarCambio();
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
                notificarCambio();
            }
        }
    }

    public List<Enemigo> getEnemigos() {
        return enemigos;
    }

    public void iniciarEnemigos() {
        for (Enemigo enemigo : getEnemigos()) {
            enemigo.iniciar();
        }
    }

    public Bomberman getBomberman() {
        return bomberman;
    }

    public void notificarCambio() {
        setChanged();
        notifyObservers(new Object[]{bomberman.getUltimaDireccion(), obtenerEstadoTablero(), bomberman.seHaMovido()});
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

    public int[][] obtenerEstadoTablero() {
        int[][] estado = new int[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Casilla casilla = celdas[i][j];

                if (casilla.tieneBloqueDuro()) {
                    estado[i][j] = 4;
                } else if (casilla.estaEnExplosion()) {
                    estado[i][j] = 3;
                } else if (casilla.tieneEnemigo()) {
                    estado[i][j] = 6;
                } else if (casilla.tieneBomberman()) {
                    estado[i][j] = 1;
                } else if (casilla.tieneBomba()) {
                    estado[i][j] = 2;
                } else if (casilla.tieneBloqueBlando()) {
                    estado[i][j] = 5;
                } else {
                    estado[i][j] = 0;
                }
            }
        }

        return estado;
    }

    public int contarBloquesBlandos() {
        int contador = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (celdas[i][j].tieneBloqueBlando()) {
                    contador++;
                }
            }
        }
        return contador;
    }
    public void colocarElementosIniciales() {
        Casilla casillaInicial = celdas[bomberman.getX()][bomberman.getY()];
        casillaInicial.colocarBomberman(bomberman);

        Random rand = new Random();
        int enemigosColocados = 0;
        int totalEnemigos = rand.nextInt(6) + 5;

        while (enemigosColocados < totalEnemigos) {
            int x = rand.nextInt(filas);
            int y = rand.nextInt(columnas);

            if ((x == 0 && y == 0) || (x == 0 && y == 1) || (x == 1 && y == 0)) continue;

            Casilla posible = celdas[x][y];

            if (!posible.tieneBloqueDuro() &&
                !posible.tieneBloqueBlando() &&
                !posible.tieneBomberman() &&
                !posible.tieneEnemigo()) {

            	Enemigo enemigo = new Enemigo(x, y);
            	int tipo = rand.nextInt(4); 
            	EstrategiaMovimiento estrategia;
            	switch (tipo) {
            	    case 0 -> estrategia = new MovimientoAleatorio();
            	    case 1 -> estrategia = new MovimientoDoble();
            	    case 2 -> estrategia = new MovimientoQuieto();
            	    default -> estrategia = new MovimientoAStar(enemigo); 
            	}


                enemigo.cambiarEstrategia(estrategia);
                añadirEnemigo(enemigo);
                enemigosColocados++;
            }
        }

        notificarCambio();
        iniciarEnemigos();
    }
}

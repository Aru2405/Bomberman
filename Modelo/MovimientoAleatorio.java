package Modelo;

import java.util.Random;

public class MovimientoAleatorio implements EstrategiaMovimiento {
    private static final int[][] DIRECCIONES = {
        {1, 0}, {-1, 0}, {0, 1}, {0, -1}, {0, 0}
    };

    private Random random = new Random();

    @Override
    public void mover(Enemigo enemigo) {
        if (!Tablero.getTablero().getEnemigos().contains(enemigo)) return;

        int accion = random.nextInt(5) + 1; 
        int nuevaX = enemigo.getX();
        int nuevaY = enemigo.getY();
        switch (accion) {
            case 1 -> {} //quieto
            case 2 -> nuevaX -= 1; //arriba
            case 3 -> nuevaX += 1; //abajo
            case 4 -> nuevaY -= 1; //izquierda
            case 5 -> nuevaY += 1; //derecha
        }

        if (Tablero.getTablero().esValida(nuevaX, nuevaY)) {
            Casilla actual = Tablero.getTablero().getCasilla(enemigo.getX(), enemigo.getY());
            Casilla destino = Tablero.getTablero().getCasilla(nuevaX, nuevaY);
            
            if (destino.tieneBomberman()) {
                Tablero.getTablero().getBomberman().morir();
                return;
            }




            if (!destino.tieneBloqueDuro() && !destino.tieneBloqueBlando() && !destino.tieneBomba() && !destino.tieneEnemigo()) {

                actual.eliminarEnemigo();
                enemigo.setX(nuevaX);
                enemigo.setY(nuevaY);
                destino.colocarEnemigo(enemigo);
            }
        }

        Tablero.getTablero().notificarCambio();
    }
}

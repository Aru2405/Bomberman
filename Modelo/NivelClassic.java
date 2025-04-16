package Modelo;

import java.util.Random;

public class NivelClassic implements ConfiguradorNivel {

    @Override
    public void configurar() {
        Tablero.getTablero().inicializar(11, 17);
        Random rand = new Random();

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 17; j++) {
                if ((i == 0 && j == 0) || (i == 0 && j == 1) || (i == 1 && j == 0))
                    continue;

                Casilla casilla = Tablero.getTablero().getCasilla(i, j);
                if (casilla == null) continue;

                if (i % 2 == 1 && j % 2 == 1) {
                    casilla.colocarBloqueDuro();
                } else if (rand.nextDouble() < 0.4) {
                    casilla.colocarBloqueBlando();
                }
            }
        }

        Tablero.getTablero().colocarElementosIniciales();
    }
}
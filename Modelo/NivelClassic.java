package Modelo;

import java.util.Random;
import java.util.stream.IntStream;

public class NivelClassic implements ConfiguradorNivel {

    @Override
    public void configurar() {
    Tablero.getTablero().inicializar(11, 17);
    Random rand = new Random();

    IntStream.range(0, 11).forEach(i -> 
        IntStream.range(0, 17).forEach(j -> {
            if ((i == 0 && j == 0) || (i == 0 && j == 1) || (i == 1 && j == 0))
                return;

            Casilla casilla = Tablero.getTablero().getCasilla(i, j);
            if (casilla == null) return;

            if (i % 2 == 1 && j % 2 == 1) {
                casilla.colocarBloqueDuro();
            } else if (rand.nextDouble() < 0.4) {
                casilla.colocarBloqueBlando();
            }
        })
    );

    Tablero.getTablero().colocarElementosIniciales();
}
}
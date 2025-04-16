package Modelo;

import java.util.Random;

public class MovimientoAleatorio implements EstrategiaMovimiento {
    private static final int[][] DIRECCIONES = {
        {1, 0}, {-1, 0}, {0, 1}, {0, -1}, {0, 0}
    };

    private Random random = new Random();

    @Override
    public int[] calcularDireccion() {
        int accion = random.nextInt(5);
        return DIRECCIONES[accion];
    }
}
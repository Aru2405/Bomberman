package Modelo;

import java.util.Random;

public class MovimientoDoble implements EstrategiaMovimiento {
    private static final int[][] DIRECCIONES = {
        {2, 0}, {-2, 0}, {0, 2}, {0, -2}
    };

    private Random random = new Random();

    @Override
    public int[] calcularDireccion() {
        return DIRECCIONES[random.nextInt(DIRECCIONES.length)];
    }
}

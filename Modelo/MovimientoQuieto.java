package Modelo;

public class MovimientoQuieto implements EstrategiaMovimiento {
    @Override
    public int[] calcularDireccion() {
        return new int[]{0, 0};  
    }
}

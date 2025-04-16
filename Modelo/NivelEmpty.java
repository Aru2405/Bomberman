package Modelo;

public class NivelEmpty implements ConfiguradorNivel {

    @Override
    public void configurar() {
        Tablero.getTablero().inicializar(11, 17);
        
        // Nivel vacío: no se colocan bloques ni obstáculos
        
        Tablero.getTablero().colocarElementosIniciales();
    }
}
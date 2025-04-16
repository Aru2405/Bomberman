package Modelo;

import java.util.Observable;

public class Partida extends Observable {

    private int nivel;
    private boolean juegoActivo;
    private static Partida miPartida;
    private String colorJugador = "Blanco"; 
    private String tipoNivel; // Guardado como String para usarlo al invocar la fábrica

    private Partida() {
        this.nivel = 1;
        this.juegoActivo = true;
    }

    public static Partida getPartida() {
        if (miPartida == null) {
            miPartida = new Partida();
        }
        return miPartida;
    }

    public void inicializar() {
    	ConfiguradorNivel configurador = NivelesFactory.getmisNiveles().crearConfigurador(tipoNivel);
    	configurador.configurar(); // Aquí ya se llama al Tablero.getTablero().inicializar(...) desde el nivel correspondiente
    }

    public void iniciarJuego() {
        System.out.println("El juego ha empezado");
    }

    public void actualizarJuego() {
        System.out.println("Actualizando el juego");
    }

    public void terminarJuego() {
        this.juegoActivo = false;
        System.out.println("El juego ha terminado.");
        System.exit(0);
    }

    public boolean estaActivo() {
        return juegoActivo;
    }

    public String getColorJugador() {
        return colorJugador;
    }

    public void setColorJugador(String colorJugador) {
        this.colorJugador = colorJugador;
        setChanged();
        notifyObservers(colorJugador);
    }

    public String getTipoNivel() {
        return tipoNivel;
    }
    public void setTipoNivel(String tipoNivel) {
        this.tipoNivel = tipoNivel;
        setChanged();
        notifyObservers(new Object[] { colorJugador, tipoNivel });
    }

}

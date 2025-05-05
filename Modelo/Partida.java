package Modelo;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Observable;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Partida extends Observable {

    private int nivel;
    private boolean juegoActivo;
    private static Partida miPartida;
    private String colorJugador = "Blanco"; 
    private String tipoNivel; // Guardado como String para usarlo al invocar la fábrica
    private long tiempoInicio;
    private long tiempoFin;

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
    	ConfiguradorNivel configurador = NivelesFactory.crearConfigurador(tipoNivel);
    	configurador.configurar(); // Aquí ya se llama al Tablero.getTablero().inicializar(...) desde el nivel correspondiente
    }

    public void iniciarJuego() {
    	tiempoInicio = System.currentTimeMillis();
        System.out.println("El juego ha empezado");
    }

    public void actualizarJuego() {
        System.out.println("Actualizando el juego");
    }

    public void terminarJuego() {
        this.juegoActivo = false;
        tiempoFin = System.currentTimeMillis();
        guardarResultado(false, tipoNivel, colorJugador);
        mostrarRankingVentana();
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
    private void guardarResultado(boolean ganado, String tipoNivel, String colorJugador) {
        try {
            FileWriter fw = new FileWriter("ranking.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            long duracion = (tiempoFin - tiempoInicio) / 1000;

            if (ganado) {       
                out.println("Ganó en " + duracion + " segundos " + "en el nivel "+ tipoNivel +"con el jugador "+colorJugador);
            } else {
                out.println("Perdió en "+ duracion + " segundos " + "en el nivel "+ tipoNivel +"con el jugador "+colorJugador);
            }

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void mostrarRankingVentana() {
    	String contenido;
    	try {
    	    contenido = Files.lines(Paths.get("ranking.txt"))
    	        .collect(Collectors.joining("\n"));
    	} catch (IOException e) {
    	    contenido = "Error al leer el ranking.";
    	}

    	JTextArea areaTexto = new JTextArea(contenido);
    	areaTexto.setEditable(false);
    	JScrollPane scrollPane = new JScrollPane(areaTexto);
    	JOptionPane.showMessageDialog(null, scrollPane, "Ranking de Partidas", JOptionPane.INFORMATION_MESSAGE);


       }



}

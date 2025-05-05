package Modelo;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class EstadoExplosion implements EstadoCasilla {

    private static final Icon icono = new ImageIcon(EstadoExplosion.class.getResource("/Sprites/miniBlast1.gif"));

    private TimerTask tarea(Runnable r) {
        return new TimerTask() {
            @Override
            public void run() {
                r.run();
            }
        };
    }

    
    @Override
    public void alExplotar(Casilla casilla) {
    }
    

    @Override
    public boolean esTransitable() {
        return true; 
    }

    @Override
    public boolean sePuedeDestruir() {
        return false;
    }

    @Override
    public Icon getIcono() {
    	return icono;
    }
    	

    @Override
    public String toString() {
        return "[*]";
    }

    @Override
    public void colocarBomba(Casilla casilla, Bomba bomba) {
    }

    @Override
    public void colocarBloqueBlando(Casilla casilla) {
    }

    @Override
    public void colocarBloqueDuro(Casilla casilla) {
    }

    @Override
    public void destruirBloqueBlando(Casilla casilla) {
    }

    @Override
    public void iniciarExplosion(Casilla casilla) {
    	Timer timer = new Timer();
    	timer.schedule(tarea(() -> casilla.setEstado(new EstadoVacio())), 2000);


    }

    @Override
    public void finalizarExplosion(Casilla casilla) {
        casilla.setEstado(new EstadoVacio()); 
    }

    @Override
    public void detonarBomba(Casilla casilla) {
    }

    @Override
    public boolean tieneBomba() {
        return false;
    }

    @Override
    public boolean estaEnExplosion() {
        return true;
    }
}

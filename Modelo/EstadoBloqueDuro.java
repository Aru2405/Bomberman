package Modelo;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class EstadoBloqueDuro implements EstadoCasilla {
    private BloqueDuro bloqueDuro;
    private static final Icon icono = new ImageIcon(EstadoBloqueDuro.class.getResource("/Sprites/hard1.png"));

    @Override
    public void alExplotar(Casilla casilla) {
    }

    @Override
    public boolean esTransitable() {
        return false;
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
    }

    @Override
    public void finalizarExplosion(Casilla casilla) {
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
        return false;
    }
}

package Modelo;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class EstadoBloqueBlando implements EstadoCasilla {

    private static final Icon icono = new ImageIcon(EstadoBloqueBlando.class.getResource("/Sprites/soft1.png"));

    @Override
    public void alExplotar(Casilla casilla) {
        casilla.setEstado(new EstadoExplosion()); 
    }

    @Override
    public boolean esTransitable() {
        return false;
    }

    @Override
    public boolean sePuedeDestruir() {
        return true;
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
        casilla.setEstado(new EstadoVacio()); 
    }

    @Override
    public void iniciarExplosion(Casilla casilla) {
        casilla.setEstado(new EstadoExplosion()); 
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
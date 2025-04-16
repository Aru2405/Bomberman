package Modelo;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class EstadoVacio implements EstadoCasilla {

    @Override
    public void alExplotar(Casilla casilla) {}

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
        return null;
    }

    @Override
    public void colocarBomba(Casilla casilla, Bomba bomba) {
        casilla.setEstado(new EstadoConBomba(bomba));
    }

    @Override
    public void colocarBloqueBlando(Casilla casilla) {
        casilla.setEstado(new EstadoBloqueBlando());
    }

    @Override
    public void colocarBloqueDuro(Casilla casilla) {
        casilla.setEstado(new EstadoBloqueDuro());
    }

    @Override
    public void destruirBloqueBlando(Casilla casilla) {}

    @Override
    public void iniciarExplosion(Casilla casilla) {
        casilla.setEstado(new EstadoExplosion());
    }

    @Override
    public void finalizarExplosion(Casilla casilla) {}

    @Override
    public void detonarBomba(Casilla casilla) {}

    @Override
    public boolean tieneBomba() {
        return false;
    }

    @Override
    public boolean estaEnExplosion() {
        return false;
    }
}
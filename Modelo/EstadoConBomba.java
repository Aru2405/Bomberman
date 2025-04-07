package Modelo;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class EstadoConBomba implements EstadoCasilla {

    private Bomba bomba;
    private static final Icon icono = new ImageIcon(EstadoConBomba.class.getResource("/Sprites/bomb1.png"));

    public EstadoConBomba(Bomba bomba) {
        this.bomba = bomba;
    }

    @Override
    public void alExplotar(Casilla casilla) {
        detonarBomba(casilla); 
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
    public void colocarBomba(Casilla casilla, Bomba nuevaBomba) {
        System.out.println("Ya hay una bomba en esta casilla.");
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
        bomba.iniciarExplosion(); 
    }

    @Override
    public void finalizarExplosion(Casilla casilla) {
        bomba.finalizarExplosion(); 
    }
    @Override
    public void detonarBomba(Casilla casilla) {
        System.out.println("💣 Bomba detonada en casilla");
        bomba = null;
        casilla.setEstado(new EstadoExplosion());
    }

    @Override
    public boolean tieneBomba() {
        return bomba != null;
    }

    @Override
    public boolean estaEnExplosion() {
        return bomba != null && bomba.estaExplotando();
    }
}

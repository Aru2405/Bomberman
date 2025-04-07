package Modelo;

import javax.swing.Icon;

public interface EstadoCasilla {
    void alExplotar(Casilla casilla);    
    boolean esTransitable();             
    boolean sePuedeDestruir();            
    Icon getIcono();    
    void colocarBomba(Casilla casilla, Bomba bomba);
    void colocarBloqueBlando(Casilla casilla);
    void colocarBloqueDuro(Casilla casilla);
    void destruirBloqueBlando(Casilla casilla);
    void iniciarExplosion(Casilla casilla);
    void finalizarExplosion(Casilla casilla);
    void detonarBomba(Casilla casilla);
    boolean tieneBomba();
    boolean estaEnExplosion();

}

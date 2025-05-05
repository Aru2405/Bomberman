package Modelo;

import java.util.Objects;

public class Casilla {
    private EstadoCasilla estado;
    private Bomberman bomberman;
    private Enemigo enemigo;

    public Casilla() {
        this.estado = new EstadoVacio();
        this.bomberman = null;
        this.enemigo = null;
    }

    public void setEstado(EstadoCasilla estado) {
        this.estado = estado;
    }

    public EstadoCasilla getEstado() {
        return estado;
    }

    public void colocarBomba(Bomba b) {
        estado.colocarBomba(this, b);
    }

    public void detonarBomba() {
        estado.detonarBomba(this);
    }

    public void colocarBloqueBlando() {
        estado.colocarBloqueBlando(this);
    }

    public void colocarBloqueDuro() {
        estado.colocarBloqueDuro(this);
    }

    public void destruirBloqueBlando() {
        estado.destruirBloqueBlando(this);
    }

    public void iniciarExplosion() {
        estado.iniciarExplosion(this);
    }

    public void finalizarExplosion() {
        estado.finalizarExplosion(this);
    }

    public boolean tieneBomba() {
        return estado.tieneBomba();
    }

    public boolean estaEnExplosion() {
        return estado.estaEnExplosion();
    }

    public boolean tieneBloqueBlando() {
        return estado instanceof EstadoBloqueBlando;
    }

    public boolean tieneBloqueDuro() {
        return estado instanceof EstadoBloqueDuro;
    }

    public void colocarBomberman(Bomberman b) {
        this.bomberman = b;
    }

    public void eliminarBomberman() {
        this.bomberman = null;
    }

    public boolean tieneBomberman() {
	    return Objects.nonNull(this.bomberman);

    }

    public Bomberman getBomberman() {
        return this.bomberman;
    }

    // Gestión de enemigo
    public void colocarEnemigo(Enemigo enemigo) {
        this.enemigo = enemigo;
    }

    public void eliminarEnemigo() {
        this.enemigo = null;
    }

    public boolean tieneEnemigo() {
	    return Objects.nonNull(this.enemigo);
    }

    public Enemigo getEnemigo() {
        return enemigo;
    }

    @Override
    public String toString() {
        return estado.toString();
    }
}

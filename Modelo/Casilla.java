package Modelo;

public class Casilla {
	private EstadoCasilla estado;
    private Bomba bomba;
    private BloqueBlando bloqueBlando;
    private BloqueDuro bloqueDuro;
    private Bomberman bomberman;
    private Enemigo enemigo;
    private boolean enExplosion = false;

    public Casilla() {
    	this.estado = new EstadoVacio();
        this.bomba = null;
        this.bloqueBlando = null;
        this.bloqueDuro = null;
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
        this.bomba = b;

    }

    public Bomba getBomba() {
        return this.bomba;
    }

    public boolean tieneBomba() {
        return bomba != null;
    }

    public void detonarBomba() {
        if (bomba != null) {

            System.out.println("Bomba exploto en esta casilla");
            this.bomba = null;

        }
    }

    public void colocarBloqueBlando() {
        this.bloqueBlando = new BloqueBlando();

    }

    public boolean tieneBloqueBlando() {

        return bloqueBlando != null;

    }

    public void destruirBloqueBlando() {
        if (tieneBloqueBlando()) {
            bloqueBlando = null;
            System.out.println("Bloque blando destruido");
        }
    }

    public void colocarBloqueDuro() {
        this.bloqueDuro = new BloqueDuro();
    }

    public boolean tieneBloqueDuro() {
        return bloqueDuro != null;
    }

    public void colocarBomberman(Bomberman b) {
        this.bomberman = b;
    }

    public void eliminarBomberman() {
        this.bomberman = null;
    }

    public boolean tieneBomberman() {
        return this.bomberman != null;
    }

    public Bomberman getBomberman() {
        return this.bomberman;
    }

    public boolean bombaEstaExplotando() {
        return tieneBomba() && getBomba().estaExplotando();
    }

    public void iniciarExplosion() {
        enExplosion = true;
    }

    public void finalizarExplosion() {
        enExplosion = false;
    }

    public boolean estaEnExplosion() {
        return enExplosion;
    }

    public void colocarEnemigo(Enemigo enemigo) {
        this.enemigo = enemigo;
    }

    public void eliminarEnemigo() {
        this.enemigo = null;
    }

    public boolean tieneEnemigo() {
        return enemigo != null;
    }

    public Enemigo getEnemigo() {
        return enemigo;
    }


    @Override
    public String toString() {
        if (bomba != null) {
            return "[b]";
        } else if (bloqueBlando != null) {
            return "[#]";
        } else if (bloqueDuro != null) {
            return "[X]";
        }
        return "[ ]";
    }
}
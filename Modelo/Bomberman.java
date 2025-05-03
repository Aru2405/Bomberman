package Modelo;

public class Bomberman {
	
    private int x;
    private int y;
    private boolean vida;
    private String color;
    String ultimaDireccion;
    private boolean seHaMovido = false;


    boolean ini=false;
   
    public Bomberman(int x, int y, String color) {
        this.x = x;
        this.y = y;
        this.vida = true;
        this.color = color;

        
    }
    
    public boolean seHaMovido() {
        return seHaMovido;
    }
    
    public void moverse(int dx, int dy) {   
        int nuevaX = this.x + dx;
        int nuevaY = this.y + dy;

        Tablero tablero = Tablero.getTablero();
        int filas = tablero.getCeldas().length;
        int columnas = tablero.getCeldas()[0].length;

        if (Partida.getPartida().getTipoNivel() == "EMPTY") {
            if (nuevaX < 0) nuevaX = filas - 1;
            else if (nuevaX >= filas) nuevaX = 0;

            if (nuevaY < 0) nuevaY = columnas - 1;
            else if (nuevaY >= columnas) nuevaY = 0;
        } else {
            if (!tablero.esValida(nuevaX, nuevaY)) {
                return;
            }
        }
        // Verificar obstáculo en medio
        Casilla casillaDestino = Tablero.getTablero().getCasilla(nuevaX, nuevaY);
        if (casillaDestino.tieneBloqueDuro() || casillaDestino.tieneBloqueBlando() || casillaDestino.tieneBomba()) {
            return;
        }

        if (casillaDestino.tieneEnemigo()) {
            morir();
            return;
        }

        // Guardar la última dirección para la animación
        if (dx == -1) {
            ultimaDireccion = "arriba";
        } else if (dx == 1) {
            ultimaDireccion = "abajo";
        } else if (dy == -1) {
            ultimaDireccion = "izquierda";
        } else if (dy == 1) {
            ultimaDireccion = "derecha";
        }

        // Mover al Bomberman
        Tablero.getTablero().getCasilla(this.x, this.y).eliminarBomberman();
        this.x = nuevaX;
        this.y = nuevaY;

        if (!ini) {
            ini = true;
            Tablero.getTablero().iniciarEnemigos();
        }

        seHaMovido = true;
        Tablero.getTablero().getCasilla(x, y).colocarBomberman(this);
        Tablero.getTablero().notificarCambio();
        seHaMovido = false;
    }



   

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public void ponerBomba() {
        if (Tablero.getTablero().contarBombasActivas() < 10) {
            Casilla casillaActual = Tablero.getTablero().getCasilla(this.x, this.y);
            System.out.println("Bomba colocada en: (" + x + ", " + y + ")");
    
            Bomba nuevaBomba;
            System.out.println("Color del bomberman: " + this.color);
            System.out.println("Intentando colocar bomba...");

            if ("Negro".equalsIgnoreCase(this.color)) {
                if (BombaNegra.estaActiva()) {
                    System.out.println("¡Ya hay una bomba negra activa!");
                    return;
                }
                System.out.println("⚫ Colocando bomba negra");
                nuevaBomba = new BombaNegra(this.x, this.y);
            } else {
                System.out.println("⚪ Colocando bomba blanca");
                nuevaBomba = new Bomba(this.x, this.y); 
            }
            System.out.println("Tipo real de bomba: " + nuevaBomba.getClass().getSimpleName());

            casillaActual.colocarBomba(nuevaBomba);
            nuevaBomba.iniciarTemporizador();
        } else {
            System.out.println("¡Límite de bombas alcanzado! No puedes colocar más.");
        }
    }
    


    public void morir() {
    	vida= false;
        System.out.println("Bomberman ha muerto.");
        Partida.getPartida().terminarJuego();
    }
    
    public boolean estaVivo(){
    	return vida;
    }

	public Object getUltimaDireccion() {
		
		return  ultimaDireccion;
	}
   
    public String getColor() {
        return color;
    }
    

}

package Modelo;

public class Bomberman {
	
    private int x;
    private int y;
    private boolean vida;
    private String color;
    String ultimaDireccion;


    boolean ini=false;
   
    public Bomberman(int x, int y, String color) {
        this.x = x;
        this.y = y;
        this.vida = true;
        this.color = color;

        
    }


    public void moverse(int dx, int dy) {   
        int nuevaX = this.x + dx;
        int nuevaY = this.y + dy;


        //Verificar posicion valida
        if (!Tablero.getTablero().esValida(nuevaX, nuevaY)) {
            return;
        }

        // Verificar obstaculo en medio
        Casilla casillaDestino = Tablero.getTablero().getCasilla(nuevaX, nuevaY);
        if (casillaDestino.tieneBloqueDuro() || casillaDestino.tieneBloqueBlando() || casillaDestino.tieneBomba()) {
            return;
        }
        
        if (casillaDestino.tieneEnemigo()) {
            morir();
        }
        
        
        
        //guardar la ultima direccion para la animacion
        if (dx == -1) {
            ultimaDireccion = "arriba";
        } else if (dx == 1) {
            ultimaDireccion = "abajo";
        } else if (dy == -1) {
            ultimaDireccion = "izquierda";
        } else if (dy == 1) {
            ultimaDireccion = "derecha";
        }
        
        Tablero.getTablero().getCasilla(this.x,this.y).eliminarBomberman();
        //Si la casilla esta libre, mover a Bomberman y actualizar Tablero
        this.x = nuevaX;
        this.y = nuevaY;

    	if (ini== false){
    		ini= true;
            Tablero.getTablero().iniciarEnemigos();
    
    	}
        
        Tablero.getTablero().getCasilla(x, y).colocarBomberman(this);
        Tablero.getTablero().notificarCambio();
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

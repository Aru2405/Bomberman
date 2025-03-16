package Modelo;

public class Bomberman {
	
    private int x;
    private int y;
    private boolean vida;

   
    public Bomberman(int x, int y) {
        this.x = x;
        this.y = y;
        this.vida = true;

        
    }
    
    public void moverse(int dx, int dy) {
        int nuevaX = this.x + dx;
        int nuevaY = this.y + dy;

        Tablero tablero = Tablero.getTablero();

        //Verificar si la nueva posición está dentro del tablero
        if (!tablero.esValida(nuevaX, nuevaY)) {
            return;
        }

        // Verificar si la nueva casilla tiene un obstáculo
        Casilla casillaDestino = tablero.getCasilla(nuevaX, nuevaY);
        if (casillaDestino.tieneBloqueDuro() || casillaDestino.tieneBloqueBlando()) {
            return;
        }

        //Si la casilla está libre, mover a Bomberman y actualizar Tablero
        this.x = nuevaX;
        this.y = nuevaY;
        tablero.notificarCambio();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public void ponerBomba() {
        Tablero tablero = Tablero.getTablero();
        Casilla casillaActual = tablero.getCasilla(this.x, this.y);
         System.out.println("Bomba colocada en: (" + x + ", " + y + ")");
         Bomba nuevaBomba = new Bomba(this.x, this.y);
         casillaActual.colocarBomba(nuevaBomba); 
         nuevaBomba.iniciarTemporizador();

         
        
        
    }


    public void morir() {
    	vida= false;
        System.out.println("Bomberman ha muerto.");
        Partida.getPartida().terminarJuego();
    }
    
    public boolean estaVivo(){
    	return vida;
    }
   


}

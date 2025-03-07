package Modelo;

public class Bomberman {
	
    private int x;
    private int y;
   
    public Bomberman(int x, int y) {
        this.x = x;
        this.y = y;
        
    }

    public void moverse(int dx, int dy) {
        int nuevaX = this.x + dx;
        int nuevaY = this.y + dy;
        
        Tablero tablero = Tablero.getTablero();

        //Verificar si la nueva posicion esta dentro del tablero
        if (!tablero.esValida(nuevaX, nuevaY)) {
            System.out.println("Bomberman no puede salir del tablero.");
            return;
        }

        //Verificar si la nueva casilla tiene un bloque
        Casilla casillaDestino = tablero.getCasilla(nuevaX, nuevaY);
        if (casillaDestino.tieneBloqueDuro() || casillaDestino.tieneBloqueBlando()) {
            System.out.println("Hay un bloque en (" + nuevaX + ", " + nuevaY + ").");
            return;
        }

   
        this.x = nuevaX;
        this.y = nuevaY;
        System.out.println("Bomberman se ha movido a (" + x + ", " + y + ").");
        tablero.actualizarPosicionBomberman(x, y);

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
        System.out.println("Bomberman ha muerto.");
        Partida.getPartida().terminarJuego();
    }


}

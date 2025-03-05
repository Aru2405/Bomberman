package Modelo;

public class Bomberman {
    private int x, y;
    private boolean vida;

    
    public Bomberman(int x, int y,boolean vida) {
        this.x = x;
        this.y = y;
        this.vida=vida
    }

    // Método para mover a Bomberman
    public void moverse(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Método para poner una bomba
    public void ponerBomba() {
    	System.out.println("Bomba colocada en: (" + x + ", " + y + ")");
        bomba nuevaBomba = new bomba(this.x, this.y);
      //metodo para agrgarla a la casilla del tablero que no existe
        nuevaBomba.iniciarTemporizador(); //
        
      
    }

    // Método para morir
    public void morir() {
        System.out.println("Bomberman ha muerto");
    }
    public boolean estaVivo() {
    	if (vida) {
    		return true;
        }else
        	return false;
     }
}

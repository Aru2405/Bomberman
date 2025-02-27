package Modelo;

public class bomberman {
    private int x, y;

    
    public bomberman(int x, int y) {
        this.x = x;
        this.y = y;
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

}

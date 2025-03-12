package Modelo;
import java.util.Timer;
import java.util.TimerTask;

public class Bomba {	
    private int x;
    private int y;
    private Timer timer; 
    private boolean enExplosion = false;
    
    public Bomba(int x, int y) {
        this.x = x;
        this.y = y; 
        this.timer = new Timer();
    }    

    public boolean estaExplotando() {
        return enExplosion;
    }

    public void iniciarExplosion() {
        enExplosion = true;
    }

    public void finalizarExplosion() {
        enExplosion = false;
    }
        
    public void iniciarTemporizador() {
        System.out.println("⏳ Temporizador iniciado en (" + x + ", " + y + ")");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("💥 Bomba explota en (" + x + ", " + y + ")");
                Tablero.getTablero().manejarExplosion(x, y);
                animarExplosion(); // 🔥 Iniciar la animación de explosión
            }
        }, 3000); // Explota después de 3 segundos
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void animarExplosion() {
        enExplosion = true;
        System.out.println("🎇 Animando explosión en (" + x + ", " + y + ")");

        Timer explosionTimer = new Timer();
        explosionTimer.schedule(new TimerTask() {
            private int frame = 0;

            @Override
            public void run() {
                if (frame >= 5) { // 🔥 5 frames (500ms), luego eliminar la bomba
                    finalizarExplosion();
                    System.out.println("❌ Eliminando bomba en (" + x + ", " + y + ")");
                    Tablero.getTablero().eliminarBomba(x, y);
                    explosionTimer.cancel();
                } else {
                    frame++; // Avanza la animación
                    Tablero.getTablero().notificarCambio(); // Refrescar vista
                }
            }
        }, 0, 100); // Se ejecuta cada 100ms por 5 frames
    }
}

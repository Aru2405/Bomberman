package VistaControlador; 

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;
import Modelo.Bomberman;
import Modelo.Tablero;

@SuppressWarnings({ "deprecation", "serial" })
public class VistaJuego extends JFrame implements Observer {
    private JPanel panelJuego;
    private JLabel[][] celdas;
    private ControladorBomberman controlador = null; 

    private ImageIcon fondoJuego = new ImageIcon(getClass().getResource("/Sprites/stageBack1.png"));
    private ImageIcon bombaIcon = new ImageIcon(getClass().getResource("/Sprites/mainbomb.png"));
    private ImageIcon bloqueBlandoIcon = new ImageIcon(getClass().getResource("/Sprites/soft1.png"));
    private ImageIcon bloqueDuroIcon = new ImageIcon(getClass().getResource("/Sprites/hard1.png"));
    private ImageIcon bombermanIcon = new ImageIcon(getClass().getResource("/Sprites/bomber1.png"));
    
    public VistaJuego(int filas, int columnas) {
        controlador = ControladorBomberman.getControladorBomberman(); 
        controlador.addObserver(this); 
        initialize(filas, columnas);
        addKeyListener(controlador); 
        setFocusable(true);
        requestFocus();
    }
    
    private void initialize(int filas, int columnas) {
        setTitle("Bomberman - Juego");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Crear el panel con fondo
        panelJuego = new PanelConFondo(fondoJuego.getImage());
        panelJuego.setLayout(new GridLayout(filas, columnas));
        panelJuego.setOpaque(false);
        add(panelJuego, BorderLayout.CENTER);

        celdas = new JLabel[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas[i][j] = new JLabel();
                celdas[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                celdas[i][j].setOpaque(false);
                panelJuego.add(celdas[i][j]);
            }
        }

        actualizarVista();
        setVisible(true);
    }

    private void actualizarVista() {
        int[][] estadoTablero = controlador.getEstadoTablero(); 
        int[] bombermanPos = controlador.getPosicionBomberman(); 

        for (int i = 0; i < estadoTablero.length; i++) {
            for (int j = 0; j < estadoTablero[i].length; j++) {
                if (i == bombermanPos[0] && j == bombermanPos[1]) {
                    celdas[i][j].setIcon(bombermanIcon); // Mostrar la imagen del bomberman
                } else {
                    switch (estadoTablero[i][j]) {
                        case 0: celdas[i][j].setIcon(null); break; 
                        case 2: celdas[i][j].setIcon(bloqueDuroIcon); break;
                        case 3: celdas[i][j].setIcon(bloqueBlandoIcon); break;
                        case 4: celdas[i][j].setIcon(bombaIcon); break;
                    }
                }
            }
        }
    }


    // Clase para el panel con fondo
    private static class PanelConFondo extends JPanel {
        private Image fondo;

        public PanelConFondo(Image fondo) {
            this.fondo = fondo;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (fondo != null) {
                g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
    @Override
    public void update(Observable o, Object arg) {
        actualizarVista();
    }
    
    private static class ControladorBomberman extends Observable implements KeyListener, Observer{
    	
        private static ControladorBomberman miControladorBomberman;
        private Bomberman bomberman;
        private Tablero tablero;

        private ControladorBomberman(){
            this.tablero = Tablero.getTablero();
            this.bomberman = tablero.getBomberman(); // Usar la instancia del Tablero
            tablero.addObserver(this);
        }

        public static synchronized ControladorBomberman getControladorBomberman(){
            if (miControladorBomberman == null){
            	miControladorBomberman = new ControladorBomberman();
            }
            return miControladorBomberman;
        }

        public int[][] getEstadoTablero(){
            return tablero.getMapa(); 
        }

        public int[] getPosicionBomberman(){
            return new int[]{bomberman.getX(), bomberman.getY()}; 
        }

        @Override
        public void keyPressed(KeyEvent e){
            if (!bomberman.estaVivo()) {
            	return; 
            } 

            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_UP:
                    bomberman.mover(-1, 0);
                    break;
                case KeyEvent.VK_DOWN:
                    bomberman.mover(1, 0);
                    break;
                case KeyEvent.VK_LEFT:
                    bomberman.mover(0, -1);
                    break;
                case KeyEvent.VK_RIGHT:
                    bomberman.mover(0, 1);
                    break;
                case KeyEvent.VK_SPACE:
                    bomberman.plantarBomba();
                    break;
            }
            tablero.notificarCambio();
            setChanged();
            notifyObservers(); 
        }

        @Override
        public void keyReleased(KeyEvent e){
        	
        }

        @Override
        public void keyTyped(KeyEvent e){
        	
        }

		@Override
		public void update(Observable o, Object arg){
	        setChanged();
	        notifyObservers(); 
			
		}
    }
}


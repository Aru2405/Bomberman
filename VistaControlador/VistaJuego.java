package VistaControlador; 

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import Modelo.Bomberman;
import Modelo.Casilla;
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
        Tablero.getTablero().addObserver(this); //??????????????
        initialize(filas, columnas);
        this.addKeyListener(controlador);
        setFocusable(true);
        requestFocusInWindow();
        
        
        SwingUtilities.invokeLater(() -> this.requestFocusInWindow());
        System.out.println("KeyListener registrado: " + Arrays.toString(this.getKeyListeners()));
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
        Casilla[][] estadoTablero = controlador.getEstadoTablero(); 
        int[] bombermanPos = controlador.getPosicionBomberman();

        for (int i = 0; i < estadoTablero.length; i++) {
            for (int j = 0; j < estadoTablero[i].length; j++) {
                if (i == bombermanPos[0] && j == bombermanPos[1]) {
                    celdas[i][j].setIcon(bombermanIcon); 
                } else {
                    Casilla casilla = estadoTablero[i][j];
                    if (casilla.tieneBomba()) {
                        celdas[i][j].setIcon(bombaIcon);
                    } else if (casilla.tieneBloqueDuro()) {
                        celdas[i][j].setIcon(bloqueDuroIcon);
                    } else if (casilla.tieneBloqueBlando()) {
                        celdas[i][j].setIcon(bloqueBlandoIcon);
                    } else {
                        celdas[i][j].setIcon(null);
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
    
    private static class ControladorBomberman implements KeyListener {
        private static ControladorBomberman miControladorBomberman;
        private Tablero tablero;
        private Bomberman bomberman;

        private ControladorBomberman() {
            this.tablero = Tablero.getTablero();
            this.bomberman = tablero.getBomberman();
        }

        public static synchronized ControladorBomberman getControladorBomberman() {
            if (miControladorBomberman == null) {
                miControladorBomberman = new ControladorBomberman();
            }
            return miControladorBomberman;
        }
        
        public Casilla[][] getEstadoTablero() {
            return tablero.getCeldas();
        }
        


        public int[] getPosicionBomberman() {
            return new int[]{bomberman.getX(), bomberman.getY()};
        }

        @Override
        public void keyPressed(KeyEvent e) {

            if (!bomberman.estaVivo()) {
                System.out.println("murio");


            	}

            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_UP -> bomberman.moverse(-1, 0);
                case KeyEvent.VK_DOWN -> bomberman.moverse(1, 0);
                case KeyEvent.VK_LEFT -> bomberman.moverse(0, -1);
                case KeyEvent.VK_RIGHT -> bomberman.moverse(0, 1);
                case KeyEvent.VK_SPACE -> bomberman.ponerBomba();
            }
            tablero.notificarCambio(); 

        }

        @Override
        public void keyReleased(KeyEvent e) {}

        @Override
        public void keyTyped(KeyEvent e) {}
    }

		
}

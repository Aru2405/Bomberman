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
    private int frameBomberman = 0;
    private ImageIcon fondoJuego = new ImageIcon(getClass().getResource("/Sprites/stageBack1.png"));
    private ImageIcon bloqueBlandoIcon = new ImageIcon(getClass().getResource("/Sprites/soft1.png"));
    private ImageIcon bloqueDuroIcon = new ImageIcon(getClass().getResource("/Sprites/hard1.png"));
    private ImageIcon bombermanIcon = new ImageIcon(getClass().getResource("/Sprites/bomber1.png"));
    private ImageIcon bombermanConBomba = new ImageIcon(getClass().getResource("/Sprites/whitewithbomb1.png"));
    private ImageIcon bomba1 = new ImageIcon(getClass().getResource("/Sprites/bomb1.png"));
    private ImageIcon fuegoGif = new ImageIcon(getClass().getResource("/Sprites/miniBlast1.gif"));

    private ImageIcon[] bombermanDerecha = {
            new ImageIcon(getClass().getResource("/Sprites/whiteright1.png")),
            new ImageIcon(getClass().getResource("/Sprites/whiteright2.png")),
            new ImageIcon(getClass().getResource("/Sprites/whiteright3.png")),
            new ImageIcon(getClass().getResource("/Sprites/whiteright4.png")),
            new ImageIcon(getClass().getResource("/Sprites/whiteright5.png"))
    };
    private ImageIcon[] bombermanIzquierda = {
            new ImageIcon(getClass().getResource("/Sprites/whiteleft1.png")),
            new ImageIcon(getClass().getResource("/Sprites/whiteleft2.png")),
            new ImageIcon(getClass().getResource("/Sprites/whiteleft3.png")),
            new ImageIcon(getClass().getResource("/Sprites/whiteleft4.png")),
            new ImageIcon(getClass().getResource("/Sprites/whiteleft5.png"))
    };

    private ImageIcon[] bombermanArriba = {
            new ImageIcon(getClass().getResource("/Sprites/whiteup1.png")),
            new ImageIcon(getClass().getResource("/Sprites/whiteup2.png")),
            new ImageIcon(getClass().getResource("/Sprites/whiteup3.png")),
            new ImageIcon(getClass().getResource("/Sprites/whiteup4.png")),
            new ImageIcon(getClass().getResource("/Sprites/whiteup5.png"))
    };

    private ImageIcon[] bombermanAbajo = {
            new ImageIcon(getClass().getResource("/Sprites/whitedown1.png")),
            new ImageIcon(getClass().getResource("/Sprites/whitedown2.png")),
            new ImageIcon(getClass().getResource("/Sprites/whitedown3.png")),
            new ImageIcon(getClass().getResource("/Sprites/whitedown4.png")),
            new ImageIcon(getClass().getResource("/Sprites/whitefront1.png"))
    };

    private ImageIcon[] explosionBomnba = {
            new ImageIcon(getClass().getResource("/Sprites/kaBomb1.png")),
            new ImageIcon(getClass().getResource("/Sprites/kaBomb2.png")),
            new ImageIcon(getClass().getResource("/Sprites/kaBomb3.png")),
            new ImageIcon(getClass().getResource("/Sprites/kaBomb4.png")),
            new ImageIcon(getClass().getResource("/Sprites/kaBomb5.png"))
    };

    public VistaJuego(int filas, int columnas) {
        controlador = ControladorBomberman.getControladorBomberman();
        Tablero.getTablero().addObserver(this);
        initialize(filas, columnas);


        this.addKeyListener(controlador);
        setFocusable(true);
        requestFocus();
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

        
        setVisible(true);
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
        if (arg instanceof Object[]) {
            Object[] data = (Object[]) arg;
            Casilla[][] estadoTablero = (Casilla[][]) data[0];
            String ultimaDireccion = (String) data[1];

            if (ultimaDireccion == null) {
                ultimaDireccion = "abajo"; 
            }

            actualizarVista(estadoTablero, ultimaDireccion);
        }
    } 

    
    private void actualizarVista(Casilla[][] estadoTablero, String ultimaDireccion) {
        for (int i = 0; i < celdas.length; i++) {
            for (int j = 0; j < celdas[i].length; j++) {
                Casilla casilla = estadoTablero[i][j];

                if (casilla.estaEnExplosion() && !casilla.tieneBloqueDuro()) {  
                    celdas[i][j].setIcon(fuegoGif);
                }
                else if (casilla.tieneBomberman()) {
                    switch (ultimaDireccion) {
                        case "arriba":
                            celdas[i][j].setIcon(bombermanArriba[frameBomberman % bombermanArriba.length]);
                            break;
                        case "abajo":
                            celdas[i][j].setIcon(bombermanAbajo[frameBomberman % bombermanAbajo.length]);
                            break;
                        case "izquierda":
                            celdas[i][j].setIcon(bombermanIzquierda[frameBomberman % bombermanIzquierda.length]);
                            break;
                        case "derecha":
                            celdas[i][j].setIcon(bombermanDerecha[frameBomberman % bombermanDerecha.length]);
                            break;
                        default:
                            celdas[i][j].setIcon(bombermanIcon);
                            break;
                    }
                    frameBomberman++;
                }
                else if (casilla.tieneBomba()) {
                    celdas[i][j].setIcon(bomba1);
                } 
                else if (casilla.tieneBloqueDuro()) {  
                    celdas[i][j].setIcon(bloqueDuroIcon);
                } 
                else if (casilla.tieneBloqueBlando()) {
                    celdas[i][j].setIcon(bloqueBlandoIcon);
                } 
                else {
                    celdas[i][j].setIcon(null);
                }
            }
        }

        panelJuego.revalidate();
        panelJuego.repaint();
    }


    

    private static class ControladorBomberman implements KeyListener {
        private static ControladorBomberman miControladorBomberman;
        private Tablero tablero;




        private ControladorBomberman() {
            this.tablero = Tablero.getTablero();
            
        }


        public static synchronized ControladorBomberman getControladorBomberman() {
            if (miControladorBomberman == null) {
                miControladorBomberman = new ControladorBomberman();
            }
            return miControladorBomberman;
        }


        @Override
        public void keyPressed(KeyEvent e) {
            if (!tablero.getBomberman().estaVivo())
                return;

            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_UP -> {
                   
                    tablero.getBomberman().moverse(-1, 0);
                }
                case KeyEvent.VK_DOWN -> {
                    
                    tablero.getBomberman().moverse(1, 0);
                }
                case KeyEvent.VK_LEFT -> {
                    
                    tablero.getBomberman().moverse(0, -1);
                }
                case KeyEvent.VK_RIGHT -> {
                   
                    tablero.getBomberman().moverse(0, 1);
                }
                case KeyEvent.VK_SPACE -> {
               
                    tablero.getBomberman().ponerBomba();
                }
     
            }

        
          
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

    }

}

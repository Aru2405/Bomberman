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
    private ImageIcon bloqueBlandoIcon = new ImageIcon(getClass().getResource("/Sprites/soft1.png"));
    private ImageIcon bloqueDuroIcon = new ImageIcon(getClass().getResource("/Sprites/hard1.png"));
    private ImageIcon bombermanIcon = new ImageIcon(getClass().getResource("/Sprites/bomber1.png"));
    private ImageIcon bombermanConBomba = new ImageIcon(getClass().getResource("/Sprites/whitewithbomb1.png"));
    private ImageIcon bomba1 = new ImageIcon(getClass().getResource("/Sprites/bomb1.png"));

    
    
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

        this.addKeyListener(controlador); // Asegurar que el KeyListener está registrado
        setFocusable(true);
        requestFocus(); // Forzar foco en la ventana
        requestFocusInWindow(); // Refuerzo adicional

        SwingUtilities.invokeLater(() -> this.requestFocusInWindow()); // Forzar foco tras iniciar
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

        actualizarVista(); // Llamamos al método después de la inicialización
        setVisible(true);
    }


    private void actualizarVista() {
        Casilla[][] estadoTablero = controlador.getEstadoTablero(); 
        int[] bombermanPos = controlador.getPosicionBomberman();
        String direccion = controlador.getUltimaDireccion(); // Obtener dirección actual
        int frame = controlador.getFrameBomberman(); // Frame para la animación
        boolean colocandoBomba = controlador.estaColocandoBomba(); // Saber si está poniendo una bomba
        int frameExplosion = controlador.getFrameExplosion(); // Obtener frame de la explosión

        for (int i = 0; i < estadoTablero.length; i++) {
            for (int j = 0; j < estadoTablero[i].length; j++) {
                Casilla casilla = estadoTablero[i][j];

                // Si es la posición de Bomberman
                if (i == bombermanPos[0] && j == bombermanPos[1]) {
                    if (colocandoBomba) {
                        celdas[i][j].setIcon(bombermanConBomba);
                    } else {
                        switch (direccion) {
                            case "derecha" -> celdas[i][j].setIcon(bombermanDerecha[frame % bombermanDerecha.length]);
                            case "izquierda" -> celdas[i][j].setIcon(bombermanIzquierda[frame % bombermanIzquierda.length]);
                            case "arriba" -> celdas[i][j].setIcon(bombermanArriba[frame % bombermanArriba.length]);
                            case "abajo" -> celdas[i][j].setIcon(bombermanAbajo[frame % bombermanAbajo.length]);
                            default -> celdas[i][j].setIcon(bombermanIcon);
                        }
                    }
                }
                // Si hay una bomba en la casilla
                else if (casilla.tieneBomba()) {
                    if (casilla.bombaEstaExplotando()) { // Si la bomba está explotando
                        celdas[i][j].setIcon(explosionBomnba[frameExplosion % explosionBomnba.length]);
                    } else {
                        celdas[i][j].setIcon(bomba1);
                    }
                }
                // Bloques y celdas vacías
                else if (casilla.tieneBloqueDuro()) {
                    celdas[i][j].setIcon(bloqueDuroIcon);
                } else if (casilla.tieneBloqueBlando()) {
                    celdas[i][j].setIcon(bloqueBlandoIcon);
                } else {
                    celdas[i][j].setIcon(null);
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
        System.out.println("Vista actualizada"); // 👈 DEBUG: Ver si esto se imprime en consola
        actualizarVista();
    }

    
    private static class ControladorBomberman implements KeyListener {
        private static ControladorBomberman miControladorBomberman;
        private Tablero tablero;
        private Bomberman bomberman;
        private int frameBomberman = 0;
        private int frameExplosion = 0;
        private String ultimaDireccion = "abajo"; // Por defecto mirando abajo
        private boolean colocandoBomba = false; 

        public boolean estaColocandoBomba() {  // 👈 Método necesario
            return colocandoBomba;
        }

        public int getFrameExplosion() {  // 👈 Método necesario
            return frameExplosion;
        }
        private ControladorBomberman() {
            this.tablero = Tablero.getTablero();
            this.bomberman = tablero.getBomberman();
        }

        public String getUltimaDireccion() {
            return ultimaDireccion;
        }

        public int getFrameBomberman() {
            return frameBomberman;
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
            if (!bomberman.estaVivo()) return;

            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_UP -> {
                    ultimaDireccion = "arriba";
                    bomberman.moverse(-1, 0);
                }
                case KeyEvent.VK_DOWN -> {
                    ultimaDireccion = "abajo";
                    bomberman.moverse(1, 0);
                }
                case KeyEvent.VK_LEFT -> {
                    ultimaDireccion = "izquierda";
                    bomberman.moverse(0, -1);
                }
                case KeyEvent.VK_RIGHT -> {
                    ultimaDireccion = "derecha";
                    bomberman.moverse(0, 1);
                }
                case KeyEvent.VK_SPACE -> {
                    colocandoBomba = true;
                    bomberman.ponerBomba();
                    
                    // Alternar el estado de "colocando bomba" después de 500ms
                    new Timer(500, evt -> {
                        colocandoBomba = false;
                        tablero.notificarCambio();
                    }).start();
                }
            }

            frameBomberman++;
            tablero.notificarCambio();
        }



        @Override
        public void keyReleased(KeyEvent e) {}

        @Override
        public void keyTyped(KeyEvent e) {}

		
    }

		
}

package VistaControlador;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.IntStream;

import javax.sound.sampled.*;
import java.net.URL;
import java.io.IOException;

import Modelo.Tablero;

@SuppressWarnings({ "deprecation", "serial" })
public class VistaJuego extends JFrame implements Observer {
    private JPanel panelJuego;
    private JLabel[][] celdas;
    private ControladorBomberman controlador = null;
    private int frameBomberman = 0;
    private ImageIcon bloqueBlandoIcon = new ImageIcon(getClass().getResource("/Sprites/soft1.png"));
    private ImageIcon bloqueDuroIcon = new ImageIcon(getClass().getResource("/Sprites/hard1.png"));
    private ImageIcon bombermanIcon = new ImageIcon(getClass().getResource("/Sprites/bomber1.png"));
    private ImageIcon bombermanConBomba = new ImageIcon(getClass().getResource("/Sprites/whitewithbomb1.png"));
    private ImageIcon bomba1 = new ImageIcon(getClass().getResource("/Sprites/bomb1.png"));
    private ImageIcon fuegoGif = new ImageIcon(getClass().getResource("/Sprites/miniBlast1.gif"));
    private Image background;
    private ImageIcon enemigo = new ImageIcon(getClass().getResource("/Sprites/doria1.png"));
    private int filas=11;
    private int columnas=17;
    private String colorJugador;    
    private String tipoNivel; 
    private Clip musicaFondo;
    
    private JLabel timeLabel;
    private JLabel enemiesLabel;
    private JLabel livesLabel;
    private JLabel levelLabel;

    private int tiempoEnSegundos = 0;
    private int enemigosRestantes;

    
    
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

    private ImageIcon[] explosionBomba = {
            new ImageIcon(getClass().getResource("/Sprites/kaBomb1.png")),
            new ImageIcon(getClass().getResource("/Sprites/kaBomb2.png")),
            new ImageIcon(getClass().getResource("/Sprites/kaBomb3.png")),
            new ImageIcon(getClass().getResource("/Sprites/kaBomb4.png")),
            new ImageIcon(getClass().getResource("/Sprites/kaBomb5.png"))
    };

    private ImageIcon[] bombermanNegroDerecha = {
        new ImageIcon(getClass().getResource("/Sprites/blackright1.png")),
        new ImageIcon(getClass().getResource("/Sprites/blackright2.png")),
        new ImageIcon(getClass().getResource("/Sprites/blackright3.png")),
        new ImageIcon(getClass().getResource("/Sprites/blackright4.png")),
        new ImageIcon(getClass().getResource("/Sprites/blackright5.png"))
    };
    
    private ImageIcon[] bombermanNegroIzquierda = {
        new ImageIcon(getClass().getResource("/Sprites/blackleft1.png")),
        new ImageIcon(getClass().getResource("/Sprites/blackleft2.png")),
        new ImageIcon(getClass().getResource("/Sprites/blackleft3.png")),
        new ImageIcon(getClass().getResource("/Sprites/blackleft4.png")),
        new ImageIcon(getClass().getResource("/Sprites/blackleft5.png"))
    };
    
    private ImageIcon[] bombermanNegroArriba = {
        new ImageIcon(getClass().getResource("/Sprites/blackup1.png")),
        new ImageIcon(getClass().getResource("/Sprites/blackup2.png")),
        new ImageIcon(getClass().getResource("/Sprites/blackup3.png")),
        new ImageIcon(getClass().getResource("/Sprites/blackup4.png")),
        new ImageIcon(getClass().getResource("/Sprites/blackup5.png"))
    };
    
    private ImageIcon[] bombermanNegroAbajo = {
        new ImageIcon(getClass().getResource("/Sprites/blackdown1.png")),
        new ImageIcon(getClass().getResource("/Sprites/blackdown2.png")),
        new ImageIcon(getClass().getResource("/Sprites/blackdown3.png")),
        new ImageIcon(getClass().getResource("/Sprites/blackdown4.png")),
        new ImageIcon(getClass().getResource("/Sprites/blackfront1.png"))
    };
    

    public VistaJuego(String color, String tipoM) {
        this.colorJugador = color;
        this.tipoNivel = tipoM;
        controlador = ControladorBomberman.getControladorBomberman();
        Tablero.getTablero().addObserver(this);
        
        cargarFondo(); 
        initialize(filas, columnas);

        this.addKeyListener(controlador);
        setFocusable(true);
        requestFocus();
        requestFocusInWindow();
        SwingUtilities.invokeLater(() -> this.requestFocusInWindow());

        System.out.println("KeyListener registrado: " + Arrays.toString(this.getKeyListeners()));
        
    }
    private void reproducirMusicaFondo() {
    	try {
    	    URL url = getClass().getResource("/Musica/musicajuego.wav");
    	    System.out.println("URL del archivo: " + url);
    	    AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
    	    musicaFondo = AudioSystem.getClip();
    	    musicaFondo.open(audioIn);
    	    musicaFondo.loop(Clip.LOOP_CONTINUOUSLY);

    	} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
    	    e.printStackTrace();
    	}
    }
    /*private void detenerMusicaFondo() {
        if (musicaFondo != null && musicaFondo.isRunning()) {
            musicaFondo.stop();
            musicaFondo.close();
        }
    }*/ //nO LO USA
    
    private void cargarFondo() {
        switch (tipoNivel) {
            case "CLASSIC":
                background = new ImageIcon(getClass().getResource("/Sprites/stageBack1.png")).getImage();
                break;
            case "SOFT":
                background = new ImageIcon(getClass().getResource("/Sprites/stageBack3.png")).getImage();
                break;
            case "EMPTY":
                background = new ImageIcon(getClass().getResource("/Sprites/stageBack2.png")).getImage();
                break;
            default:
                background = new ImageIcon(getClass().getResource("/Sprites/stageBack1.png")).getImage();
                break;
        }
    }


    private void initialize(int filas, int columnas) {
        setTitle("Bomberman - Juego");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        
        //INDICE ARRIBA
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        timeLabel = new JLabel("Tiempo: 00:00");
        enemiesLabel = new JLabel("Enemigos: " + enemigosRestantes);
        livesLabel = new JLabel("Vidas: ❤");
        levelLabel = new JLabel("Nivel: " + tipoNivel);

        topPanel.add(timeLabel);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(enemiesLabel);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(livesLabel);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(levelLabel);

        add(topPanel, BorderLayout.NORTH);

        iniciarTemporizador(); 


        // Crear el panel con fondo
        panelJuego = new PanelConFondo(background ) ;
        panelJuego.setLayout(new GridLayout(filas, columnas));
        panelJuego.setOpaque(false);
        add(panelJuego, BorderLayout.CENTER);

        celdas = new JLabel[filas][columnas];
        IntStream.range(0, filas).forEach(i ->
        IntStream.range(0, columnas).forEach(j -> {
            celdas[i][j] = new JLabel();
            celdas[i][j].setHorizontalAlignment(SwingConstants.CENTER);
            celdas[i][j].setOpaque(false);
            panelJuego.add(celdas[i][j]);
        })
    );


        reproducirMusicaFondo();
        setVisible(true);
    }
	private void iniciarTemporizador() {
        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tiempoEnSegundos++;
                int minutos = tiempoEnSegundos / 60;
                int segundos = tiempoEnSegundos % 60;
                timeLabel.setText(String.format("Tiempo: %02d:%02d", minutos, segundos));
            }
        });
        timer.start();
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
	        String direccion = (String) data[0];
	        int[][] estadoTablero = (int[][]) data[1];
	        enemigosRestantes = contarEnemigosInicial(estadoTablero);
	        enemiesLabel.setText("Enemigos: " + enemigosRestantes);
	
	        boolean movido = (boolean) data[2]; 
	
	        if (direccion == null) direccion = "abajo";
	
	        actualizarVista(estadoTablero, direccion,movido);       
	    }
	    if (arg instanceof String) {
	        String estadoJuego = (String) arg;

	        if ("ganaste".equals(estadoJuego)) {
	        	VistaRanking vistaRanking = new VistaRanking();
	        } else if ("perdiste".equals(estadoJuego)) {
	        	
	        	VistaRanking vistaRanking = new VistaRanking();

	        }
	    }

   
    }

    private int contarEnemigosInicial(int[][] estadoTablero) {
        int contador = 0;
        for (int i = 0; i < estadoTablero.length; i++) {
            for (int j = 0; j < estadoTablero[i].length; j++) {
                if (estadoTablero[i][j] == 6) {
                    contador++;
                }
            }
        }
        return contador;
    }
        
    
    private void actualizarVista(int[][] estadoTablero, String ultimaDireccion, boolean movido) {
        for (int i = 0; i < celdas.length; i++) {
            for (int j = 0; j < celdas[i].length; j++) {
                int valor = estadoTablero[i][j];
    
                switch (valor) {
                case 0:
                    celdas[i][j].setIcon(null);
                    break;
                    
                case 1:
                    boolean esNegro = "Negro".equals(colorJugador);
                    ImageIcon[] sprites;

                    switch (ultimaDireccion) {
                        case "arriba":
                            sprites = esNegro ? bombermanNegroArriba : bombermanArriba;
                            break;
                        case "abajo":
                            sprites = esNegro ? bombermanNegroAbajo : bombermanAbajo;
                            break;
                        case "izquierda":
                            sprites = esNegro ? bombermanNegroIzquierda : bombermanIzquierda;
                            break;
                        case "derecha":
                            sprites = esNegro ? bombermanNegroDerecha : bombermanDerecha;
                            break;
                        default:
                            sprites = new ImageIcon[]{bombermanIcon};
                            break;
                    }

                    int frame;
                    if (movido) {
                        frame = frameBomberman % sprites.length;
                        frameBomberman++;
                    } else {
                        frame = 0;
                    }

                    celdas[i][j].setIcon(sprites[frame]);
                    break;

                case 2:
                    celdas[i][j].setIcon(bomba1);
                    break;

                case 3:
                    celdas[i][j].setIcon(fuegoGif);
                    break;

                case 4:
                    celdas[i][j].setIcon(bloqueDuroIcon);
                    break;

                case 5:
                    celdas[i][j].setIcon(bloqueBlandoIcon);
                    break;

                case 6:
                    celdas[i][j].setIcon(enemigo);
                    break;
            
            }}
        }
    
        frameBomberman++;
        panelJuego.revalidate();
        panelJuego.repaint();
    }


    private static class ControladorBomberman implements KeyListener {
        private static ControladorBomberman miControladorBomberman;
        private ControladorBomberman() {
            
            
        }

        public static synchronized ControladorBomberman getControladorBomberman() {
            if (miControladorBomberman == null) {
                miControladorBomberman = new ControladorBomberman();
            }
            return miControladorBomberman;
        }


        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            switch (key) {
                case KeyEvent.VK_UP:
                    Tablero.getTablero().getBomberman().moverse(-1, 0);
                    break;

                case KeyEvent.VK_DOWN:
                    Tablero.getTablero().getBomberman().moverse(1, 0);
                    break;

                case KeyEvent.VK_LEFT:
                    Tablero.getTablero().getBomberman().moverse(0, -1);
                    break;

                case KeyEvent.VK_RIGHT:
                    Tablero.getTablero().getBomberman().moverse(0, 1);
                    break;

                case KeyEvent.VK_SPACE:
                    Tablero.getTablero().getBomberman().ponerBomba();
                    break;
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

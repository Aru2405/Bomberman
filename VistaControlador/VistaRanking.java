package VistaControlador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Observable;
import java.util.Observer;

import Modelo.Tablero;

@SuppressWarnings("deprecation")
public class VistaRanking extends JFrame implements Observer {

    private JTextArea areaRanking;
    private JButton botonVerRanking;
    private ControladorFinJuego controlador;

    public VistaRanking() {
        super("Fin del Juego");
        controlador = ControladorFinJuego.getControladorFinJuego();
        Tablero.getTablero().addObserver(this);

        configurarVista();
        this.addKeyListener(controlador);

        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Asegura que pueda recibir teclas
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    private void configurarVista() {
        setLayout(new BorderLayout());

        // Imagen de Bomberman (puedes cambiar la URL a un archivo local si lo prefieres)
        JLabel imagenLabel = new JLabel();
        ImageIcon icono = new ImageIcon(getClass().getResource("/Sprites/Fin.png"));
        Image imagen = icono.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        imagenLabel.setIcon(new ImageIcon(imagen));

        imagenLabel.setHorizontalAlignment(JLabel.CENTER);
        add(imagenLabel, BorderLayout.NORTH);

        // Texto de "Fin de la partida"
        JLabel mensaje = new JLabel("¡Fin de la partida!");
        mensaje.setFont(new Font("Arial", Font.BOLD, 32));
        mensaje.setHorizontalAlignment(SwingConstants.CENTER);
        add(mensaje, BorderLayout.CENTER);

        // Panel inferior con botón y área de texto
        JPanel panelInferior = new JPanel(new BorderLayout());

        botonVerRanking = new JButton("Ver Ranking");
        botonVerRanking.setFont(new Font("Arial", Font.BOLD, 18));
        botonVerRanking.addActionListener(e -> cargarRanking());
        panelInferior.add(botonVerRanking, BorderLayout.NORTH);

        areaRanking = new JTextArea(10, 40);
        areaRanking.setEditable(false);
        areaRanking.setFont(new Font("Monospaced", Font.PLAIN, 14));
        panelInferior.add(new JScrollPane(areaRanking), BorderLayout.CENTER);

        add(panelInferior, BorderLayout.SOUTH);
    }

    private void cargarRanking() {
        areaRanking.setText("");
        try (BufferedReader br = new BufferedReader(new FileReader("ranking.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                areaRanking.append(linea + "\n");
            }
        } catch (IOException e) {
            areaRanking.setText("No hay resultados aún.");
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        // Si el modelo cambia y se quiere actualizar el ranking automáticamente
       // cargarRanking(); No se si hay q ponerlo
    }

    // Controlador
    private static class ControladorFinJuego implements KeyListener {
        private static ControladorFinJuego miControladorFinJuego;

        private ControladorFinJuego() {}

        public static synchronized ControladorFinJuego getControladorFinJuego() {
            if (miControladorFinJuego == null) {
                miControladorFinJuego = new ControladorFinJuego();
            }
            return miControladorFinJuego;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}
        @Override
        public void keyTyped(KeyEvent e) {}
    }

}

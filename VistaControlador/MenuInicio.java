package VistaControlador;

import Modelo.Partida;
import Modelo.Tablero;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;



public class MenuInicio implements Observer {

    private JFrame frame;
    private static MenuInicio instancia;
    private ControladorMenuInicio controlador;


    public static void mostrarMenu() {
        if (instancia == null) {
            instancia = new MenuInicio();
        }
    }

    public MenuInicio() {
        Partida.getPartida().addObserver(this);

        frame = new JFrame("Bomberman - Selección de Modo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        ImageIcon fondoIcon = new ImageIcon(MenuInicio.class.getResource("/Sprites/back.png"));
        Image imagenFondo = fondoIcon.getImage();

        JPanel panelPrincipal = new JPanelConFondo(imagenFondo);
        panelPrincipal.setLayout(new GridBagLayout());
        panelPrincipal.setBorder(new EmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        String[] niveles = { "CLASSIC", "SOFT", "EMPTY" };

        for (int i = 0; i < niveles.length; i++) {
            String tipoNivel = niveles[i];

            JPanel panel = new JPanel(new BorderLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    g.setColor(Color.WHITE);
                    g.fillRect(0, 0, getWidth(), getHeight());
                    super.paintComponent(g);
                }
            };

            panel.setPreferredSize(new Dimension(200, 240));
            panel.setBackground(new Color(255, 255, 255, 150));
            panel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
            panel.setOpaque(true);

            String path = switch (tipoNivel) {
                case "CLASSIC" -> "/Sprites/stageBack1.png";
                case "SOFT" -> "/Sprites/stageBack3.png";
                case "EMPTY" -> "/Sprites/stageBack2.png";
                default -> "/Sprites/stageBack1.png";
            };

            JLabel labelImagen = new JLabel(new ImageIcon(MenuInicio.class.getResource(path)), SwingConstants.CENTER);
            panel.add(labelImagen, BorderLayout.CENTER);

            JLabel labelTexto = new JLabel(tipoNivel, SwingConstants.CENTER);
            labelTexto.setFont(new Font("Arial", Font.BOLD, 16));
            panel.add(labelTexto, BorderLayout.SOUTH);

           // panel.addMouseListener(new ControladorMenuInicio(tipoNivel, panel));
             panel.addMouseListener(new ControladorMenuInicio(tipoNivel, panel));

            gbc.gridx = i;
            panelPrincipal.add(panel, gbc);
        }

        frame.setContentPane(panelPrincipal);
        frame.setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Object[] datos && datos.length == 2) {
            String color = (String) datos[0];
            String tipoNivel = (String) datos[1];

            frame.dispose();
            new VistaJuego(color, tipoNivel);
        }
    }

    static class JPanelConFondo extends JPanel {
        private final Image fondo;

        public JPanelConFondo(Image fondo) {
            this.fondo = fondo;
            setLayout(new BorderLayout());
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(new Color(0, 0, 0, 100));
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.dispose();
        }
    }

    
    
    
    



    private class ControladorMenuInicio implements MouseListener {
        private final String tipoNivel;
        private final JPanel panel;

        public ControladorMenuInicio(String tipoNivel, JPanel panel) {
            this.tipoNivel = tipoNivel;
            this.panel = panel;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            Partida.getPartida().setTipoNivel(tipoNivel);
            Partida.getPartida().inicializar(); // <-- Aquí lanzas la configuración real del nivel
            Tablero.getTablero().notificarCambio(); // Esto actualiza la vista
        }

        @Override public void mousePressed(MouseEvent e) {}
        @Override public void mouseReleased(MouseEvent e) {}
        @Override public void mouseEntered(MouseEvent e) {
            panel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3));
        }
        @Override public void mouseExited(MouseEvent e) {
            panel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        }
    }
}

package VistaControlador;

import Modelo.Partida;
import Modelo.TipoNivel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuInicio {

    public static void mostrarMenu() {
        JFrame frame = new JFrame("Bomberman - Selección de Modo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        // Cargar imagen de fondo
        ImageIcon fondoIcon = new ImageIcon(MenuInicio.class.getResource("/Sprites/back.png"));
        Image imagenFondo = fondoIcon.getImage();

        // Crear panel con fondo
        JPanel panelPrincipal = new JPanelConFondo(imagenFondo);
        panelPrincipal.setLayout(new GridBagLayout());
        panelPrincipal.setBorder(new EmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        TipoNivel[] niveles = TipoNivel.values();

        for (int i = 0; i < niveles.length; i++) {
            TipoNivel tipoNivel = niveles[i];

            // Panel contenedor del botón + imagen + nombre
            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    // Pinta el fondo con el mismo color que usabas antes
                    g.setColor(new Color(255, 255, 255)); // <- pon aquí tu color original
                    g.fillRect(0, 0, getWidth(), getHeight());
                    super.paintComponent(g); // Luego llama al super para que pinte el resto
                }
            };
            panel.setLayout(new BorderLayout());
            panel.setPreferredSize(new Dimension(200, 230));
            panel.setBackground(new Color(255, 255, 255, 150));
            panel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
            panel.setOpaque(true); // permite que se pinte a través del paintComponent personalizado
            panel.setPreferredSize(new Dimension(200, 240));
            

            // Imagen
            ImageIcon imagen = switch (tipoNivel) {
                case CLASSIC -> new ImageIcon(MenuInicio.class.getResource("/Sprites/stageBack1.png"));
                case SOFT -> new ImageIcon(MenuInicio.class.getResource("/Sprites/stageBack3.png"));
                case EMPTY -> new ImageIcon(MenuInicio.class.getResource("/Sprites/stageBack2.png"));
            };

            JLabel labelImagen = new JLabel(imagen);
            labelImagen.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(labelImagen, BorderLayout.CENTER);

            // Texto
            JLabel labelTexto = new JLabel(tipoNivel.name(), SwingConstants.CENTER);
            labelTexto.setFont(new Font("Arial", Font.BOLD, 16));
            panel.add(labelTexto, BorderLayout.SOUTH);

            // Acción al hacer clic en el panel
            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Partida.getPartida().setTipoNivel(tipoNivel);
                    frame.dispose();
                    new VistaJuego(11, 17);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    panel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3));
                    panel.revalidate();
                    panel.repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    panel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
                    panel.revalidate();
                    panel.repaint();
                }
            });

            gbc.gridx = i;
            panelPrincipal.add(panel, gbc);
        }

        frame.setContentPane(panelPrincipal);
        frame.setVisible(true);
    }

    // Clase para panel con fondo
    static class JPanelConFondo extends JPanel {
        private final Image fondo;

        public JPanelConFondo(Image fondo) {
            this.fondo = fondo;
            setLayout(new BorderLayout());
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Pintar la imagen de fondo
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);

            // Pintar un rectángulo negro semitransparente encima
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(new Color(0, 0, 0, 100)); // RGBA (último valor es opacidad: 0-255)
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.dispose();
        }
    }
}
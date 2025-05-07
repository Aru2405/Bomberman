package VistaControlador;

import javax.swing.*;
import Modelo.Partida;

import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

public class VistaInicio extends JFrame implements Observer {

    private JComboBox<String> comboColor;
    private JButton botonComenzar;
    private ControladorVistaInicio controlador;

    public VistaInicio() {
        Partida.getPartida().addObserver(this);

        setTitle("Bomberman - Inicio");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel fondoPanel = new JPanel() {
            Image fondo = new ImageIcon(getClass().getResource("/Sprites/back.png")).getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        fondoPanel.setLayout(null);
        setContentPane(fondoPanel);

        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setOpaque(false);
        panelCentral.setBounds(150, 80, 500, 400);
        fondoPanel.add(panelCentral);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.gridx = 0;
        gbc.gridwidth = 2;

        // TÃ­tulo
        ImageIcon titulo = new ImageIcon(getClass().getResource("/Sprites/title.png"));
        JLabel tituloLabel = new JLabel(titulo);
        panelCentral.add(tituloLabel, gbc);

        // Selector color
        gbc.gridy = 1;
        JLabel labelColor = new JLabel("Selecciona el color de tu Bomberman:");
        labelColor.setFont(new Font("Arial", Font.BOLD, 16));
        labelColor.setForeground(Color.WHITE);
        labelColor.setHorizontalAlignment(SwingConstants.CENTER);
        labelColor.setPreferredSize(new Dimension(400, 30));
        panelCentral.add(labelColor, gbc);

        gbc.gridy = 2;
        comboColor = new JComboBox<>(new String[]{"Blanco", "Negro"});
        comboColor.setPreferredSize(new Dimension(150, 30));
        comboColor.setFont(new Font("Arial", Font.PLAIN, 14));
        comboColor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panelCentral.add(comboColor, gbc);

        // BotÃ³n START
        gbc.gridy = 3;
        botonComenzar = new JButton(new ImageIcon(getClass().getResource("/Sprites/start.png")));
        botonComenzar.setBorderPainted(false);
        botonComenzar.setContentAreaFilled(false);
        botonComenzar.setFocusPainted(false);
        botonComenzar.setOpaque(false);
        botonComenzar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botonComenzar.addActionListener(getControlador());
        panelCentral.add(botonComenzar, gbc);

        JLabel frase = new JLabel("Presiona 'START' para elegir el nivel y comenzar la batalla");
        frase.setForeground(Color.BLACK);
        frase.setFont(new Font("Arial", Font.ITALIC, 14));
        frase.setBounds(200, 500, 500, 30);
        fondoPanel.add(frase);

        fondoPanel.add(new JLabel(new ImageIcon(getClass().getResource("/Sprites/player1.png")))).setBounds(20, 90, 64, 64);
        fondoPanel.add(new JLabel(new ImageIcon(getClass().getResource("/Sprites/blast.gif")))).setBounds(30, 30, 64, 64);
        fondoPanel.add(new JLabel(new ImageIcon(getClass().getResource("/Sprites/boss2.png")))).setBounds(700, 10, 64, 64);

        JLabel bomberBlancoLabel = new JLabel(new ImageIcon(getClass().getResource("/Sprites/bomber1.png")));
        bomberBlancoLabel.setBounds(150, 160, 64, 64);
        fondoPanel.add(bomberBlancoLabel);

        JLabel bomberNegroLabel = new JLabel(new ImageIcon(getClass().getResource("/Sprites/bomber2.png")));
        bomberNegroLabel.setBounds(50, 160, 64, 64);
        fondoPanel.add(bomberNegroLabel);

        for (int i = 0; i < 8; i++) {
            JLabel bombita = new JLabel(new ImageIcon(getClass().getResource("/Sprites/bomb1.png")));
            bombita.setBounds(50 + i * 90, 530, 32, 32);
            fondoPanel.add(bombita);
        }

        setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String) {
            String colorSeleccionado = (String) arg;
            setVisible(false);
            MenuInicio menu = new MenuInicio();
        }
    }


    private ControladorVistaInicio getControlador() {
        if (controlador == null) {
            controlador = new ControladorVistaInicio();
        }
        return controlador;
    }

    private class ControladorVistaInicio implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(botonComenzar)) {
                String colorSeleccionado = (String) comboColor.getSelectedItem();
                Partida.getPartida().setColorJugador(colorSeleccionado);
            }
        }
    }
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(VistaInicio::new);
    }
}

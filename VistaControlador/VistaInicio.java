package VistaControlador;

import javax.swing.*;

import Modelo.Tablero;

import java.awt.*;
import java.awt.event.ActionEvent;

public class VistaInicio extends JFrame {
  
    public VistaInicio() {
        setTitle("Bomberman - Inicio");
        setSize(800, 600); // Tamaño más grande para testear
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        // Panel de fondo con imagen de fondo personalizada
        JPanel fondoPanel = new JPanel() {
            Image fondo = new ImageIcon(getClass().getResource("/Sprites/back.png")).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        fondoPanel.setLayout(null); // Posicionamiento absoluto
        setContentPane(fondoPanel);

        // Panel para el contenido principal (título y opciones)
        JPanel layoutVista = new JPanel();
        layoutVista.setOpaque(false);
        layoutVista.setLayout(new BorderLayout());

        // Título del juego
        ImageIcon titulo = new ImageIcon(getClass().getResource("/Sprites/title.png"));
        JLabel tituloLabel = new JLabel(titulo);
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tituloLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

        // Panel de opciones de juego
        JPanel opciones = new JPanel(new GridLayout(3, 2, 10, 10));
        opciones.setOpaque(false);
        opciones.setBorder(BorderFactory.createEmptyBorder(20, 80, 20, 80));


        //player1
        ImageIcon player1 = new ImageIcon(getClass().getResource("/Sprites/player1.png"));
        JLabel player1Label = new JLabel(player1);
        player1Label.setAlignmentX(Component.CENTER_ALIGNMENT); 
        fondoPanel.add(player1Label);
        
        
        JLabel labelColor = new JLabel("Elige el color de tu Bomberman!:");
        labelColor.setOpaque(true); 
        labelColor.setBackground(Color.LIGHT_GRAY); 
        labelColor.setForeground(Color.WHITE); 
        JComboBox<String> comboColor = new JComboBox<>(new String[]{"Blanco", "Negro"});
        
        //boton emepzar
        JButton botonComenzar = new JButton(new ImageIcon(getClass().getResource("/Sprites/start.png")));
        botonComenzar.setBorderPainted(false);
        botonComenzar.setContentAreaFilled(false);
        botonComenzar.setFocusPainted(false);
        botonComenzar.setOpaque(false);
        botonComenzar.setCursor(new Cursor(Cursor.HAND_CURSOR));


        opciones.add(labelColor);
        opciones.add(comboColor);
        opciones.add(new JLabel());
        opciones.add(botonComenzar);

        layoutVista.add(tituloLabel, BorderLayout.NORTH);
        layoutVista.add(opciones, BorderLayout.CENTER);

        // Para que no quede soso
        //boton comenzar partida:
        //
        JLabel frase = new JLabel("Presiona 'START' para elegir el nivel y comenzar a la batalla");
        frase.setForeground(Color.WHITE);
        frase.setBounds(400, 460, 600, 40);
        fondoPanel.add(frase);
        
        //BomberBlanco
        ImageIcon bomberBlanco = new ImageIcon(getClass().getResource("/Sprites/bomber1.png"));
        JLabel bomberBlancoLabel = new JLabel(bomberBlanco);
        bomberBlancoLabel.setBounds(200, 280, bomberBlanco.getIconWidth(), bomberBlanco.getIconHeight());
        fondoPanel.add(bomberBlancoLabel);
        
        //BomberNegro
        ImageIcon bomberNegro = new ImageIcon(getClass().getResource("/Sprites/bomber2.png"));
        JLabel bomberNegroLabel = new JLabel(bomberNegro);
        bomberNegroLabel.setBounds(100, 280, bomberNegro.getIconWidth(), bomberNegro.getIconHeight());
        fondoPanel.add(bomberNegroLabel);
        
        //Decoracion
        ImageIcon explosion = new ImageIcon(getClass().getResource("/Sprites/blast.gif"));
        JLabel explosionLabel = new JLabel(explosion);
        explosionLabel.setBounds(30, 30, explosion.getIconWidth(), explosion.getIconHeight());
        fondoPanel.add(explosionLabel);
        
        ImageIcon boss = new ImageIcon(getClass().getResource("/Sprites/boss2.png"));
        JLabel bossLabel = new JLabel(boss);
        bossLabel.setBounds(700, 10, boss.getIconWidth(), boss.getIconHeight());
        fondoPanel.add(bossLabel);
        

        
        for (int i = 0; i < 8; i++) {
            JLabel bombita = new JLabel(new ImageIcon(getClass().getResource("/Sprites/bomb1.png")));
            bombita.setBounds(50 + i * 100, 530, 32, 32);
            fondoPanel.add(bombita);
        }

        // Agregar componentes al fondo
        fondoPanel.add(layoutVista);
        layoutVista.setBounds(0, 0, getWidth(), getHeight());

        // Acción del botón
        botonComenzar.addActionListener((ActionEvent e) -> {
            // String color = (String) comboColor.getSelectedItem();
            // Partida.getPartida().setColorJugador(color);
            MenuInicio.mostrarMenu(); 
            dispose();
        });


        
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VistaInicio());
    }
}

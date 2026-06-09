package main.java.presentacion;

import main.java.dominio.GameConfig;

import javax.swing.*;
import java.awt.*;

public class MenuGUI extends JFrame {

    private JComboBox<Integer> cbAgentes;
    private JComboBox<Integer> cbMuros;
    private JComboBox<Integer> cbTelefonos;

    private JButton btnIniciar;

    public MenuGUI() {

        setTitle("Matrix Escape");
        setSize(400,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        prepararComponentes();

        setVisible(true);
    }

    private void prepararComponentes() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4,2,10,10));

        cbAgentes = new JComboBox<>();
        cbMuros = new JComboBox<>();
        cbTelefonos = new JComboBox<>();

        for(int i=1;i<=5;i++) {
            cbAgentes.addItem(i);
        }

        for(int i=1;i<=15;i++) {
            cbMuros.addItem(i);
        }

        for(int i=1;i<=4;i++) {
            cbTelefonos.addItem(i);
        }

        btnIniciar = new JButton("Crear Simulación");

        panel.add(new JLabel("Agentes"));
        panel.add(cbAgentes);

        panel.add(new JLabel("Muros"));
        panel.add(cbMuros);

        panel.add(new JLabel("Teléfonos"));
        panel.add(cbTelefonos);

        panel.add(new JLabel(""));
        panel.add(btnIniciar);

        add(panel);

        btnIniciar.addActionListener(e -> iniciarJuego());
    }

    private void iniciarJuego() {

        GameConfig.setCantidadAgentes(
                (Integer) cbAgentes.getSelectedItem());

        GameConfig.setCantidadMuros(
                (Integer) cbMuros.getSelectedItem());

        GameConfig.setCantidadTelefonos(
                (Integer) cbTelefonos.getSelectedItem());

        dispose();

        new NivelGUI();
    }
}
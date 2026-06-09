package main.java.presentacion;

import main.java.dominio.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NivelGUI extends JFrame implements NivelGUIBridge {

    private static final int CELL_SIZE = 32;

    private Nivel nivel;
    private GameBoard board;

    private JPanel panel;

    private JLabel lblTiempo;

    private JButton btnPlayPause;
    //private JButton btnRestart;

    private Timer repaintTimer;

    private HiloNeo hiloNeo;
    private HiloTiempo hiloTiempo;
    private List<HiloAgente> hilosAgentes;

    private boolean pausado = true;

    private int tiempoRestante = 120;

    private Image fondo;

    public NivelGUI() {

        setTitle("Matrix Escape");

        board = new GameBoard();

        board.generarEscenario(
                GameConfig.getCantidadAgentes(),
                GameConfig.getCantidadMuros(),
                GameConfig.getCantidadTelefonos());

        board.setPausado(true);

        nivel = new Nivel(board);

        prepararPanel();
        prepararHUD();
        prepararRepaint();

        pack();

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setVisible(true);
    }

    private void prepararPanel() {

        fondo = new ImageIcon("imagenes/fondo.png").getImage();

        panel = new JPanel() {

            @Override
            public Dimension getPreferredSize() {

                return new Dimension(
                        GameBoard.WIDTH * CELL_SIZE,
                        GameBoard.HEIGHT * CELL_SIZE);
            }

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                dibujar(g);
            }
        };

        add(panel, BorderLayout.CENTER);
    }

    private void prepararHUD() {

        JPanel hud = new JPanel();

        lblTiempo = new JLabel("02:00");

        btnPlayPause = new JButton("▶");

        //btnRestart = new JButton("⟳");

        hud.add(lblTiempo);
        hud.add(btnPlayPause);
        //hud.add(btnRestart);

        add(hud, BorderLayout.NORTH);

        btnPlayPause.addActionListener(e -> cambiarEstado());

        //btnRestart.addActionListener(e -> reiniciar());
    }

    private void prepararRepaint() {

        repaintTimer = new Timer(100, e -> {

            int estado = nivel.verificarEstado();

            if (estado == 1) {

                detenerTodo();

                JOptionPane.showMessageDialog(
                        this,
                        "Neo fue capturado",
                        "GAME OVER",
                        JOptionPane.ERROR_MESSAGE);

                return;
            }

            if (estado == 2) {

                detenerTodo();

                JOptionPane.showMessageDialog(
                        this,
                        "Neo escapó de Matrix",
                        "VICTORIA",
                        JOptionPane.INFORMATION_MESSAGE);

                return;
            }

            panel.repaint();
        });

        repaintTimer.start();
    }

    private void iniciarHilos() {

        hiloNeo = new HiloNeo(board);
        hiloNeo.start();

        hilosAgentes = new ArrayList<>();

        for (Agente agente : board.getAgentes()) {

            HiloAgente hilo =
                    new HiloAgente(board, agente);

            hilo.start();

            hilosAgentes.add(hilo);
        }

        hiloTiempo = new HiloTiempo(this, board);
        hiloTiempo.start();
    }

    private void cambiarEstado() {

        if (pausado) {

            if (hiloNeo == null) {
                iniciarHilos();
            }

            pausado = false;

            board.setPausado(false);

            btnPlayPause.setText("⏸");

        } else {

            pausado = true;

            board.setPausado(true);

            btnPlayPause.setText("▶");
        }
    }

    private void detenerTodo() {

        repaintTimer.stop();

        if (hiloNeo != null) {
            hiloNeo.detener();
            hiloNeo.interrupt();
        }

        if (hiloTiempo != null) {
            hiloTiempo.detener();
            hiloTiempo.interrupt();
        }

        if (hilosAgentes != null) {

            for (HiloAgente h : hilosAgentes) {

                h.detener();
                h.interrupt();
            }
        }

        board.setPausado(true);
        board.setJuegoTerminado(true);
    }

    private void reiniciar() {

        detenerTodo();

        tiempoRestante = 120;

        lblTiempo.setText("02:00");

        board = new GameBoard();

        board.generarEscenario(
                GameConfig.getCantidadAgentes(),
                GameConfig.getCantidadMuros(),
                GameConfig.getCantidadTelefonos());

        board.setPausado(true);

        nivel = new Nivel(board);

        hiloNeo = null;
        hiloTiempo = null;
        hilosAgentes = null;

        pausado = true;

        btnPlayPause.setText("▶");

        panel.repaint();
    }

    private void dibujar(Graphics g) {

        g.drawImage(
                fondo,
                0,
                0,
                panel.getWidth(),
                panel.getHeight(),
                null);

        for (int x = 0; x < GameBoard.WIDTH; x++) {

            for (int y = 0; y < GameBoard.HEIGHT; y++) {

                Celda c = board.getCelda(x, y);

                int px = x * CELL_SIZE;
                int py = y * CELL_SIZE;

                if (c.getBloque() != null) {

                    g.drawImage(
                            c.getBloque().getSprite().getImage(),
                            px,
                            py,
                            CELL_SIZE,
                            CELL_SIZE,
                            null);
                }

                if (c.getEntidad() != null) {

                    g.drawImage(
                            c.getEntidad().getSprite().getImage(),
                            px,
                            py,
                            CELL_SIZE,
                            CELL_SIZE,
                            null);
                }

                /*
                g.drawRect(
                        px,
                        py,
                        CELL_SIZE,
                        CELL_SIZE);
                */
            }
        }
    }

    @Override
    public void restarSegundo() {

        SwingUtilities.invokeLater(() -> {

            tiempoRestante--;

            int min = tiempoRestante / 60;
            int seg = tiempoRestante % 60;

            lblTiempo.setText(
                    String.format("%02d:%02d", min, seg));

            if (tiempoRestante <= 0) {

                detenerTodo();

                JOptionPane.showMessageDialog(
                        this,
                        "Tiempo agotado",
                        "GAME OVER",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public int verificarEstado() {

        if (board.neoCapturado()) {

            board.setJuegoTerminado(true);

            return 1;
        }

        if (board.neoLlegoTelefono()) {

            board.setJuegoTerminado(true);

            return 2;
        }

        return 0;
    }
}
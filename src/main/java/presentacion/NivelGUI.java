package main.java.presentacion;

import main.java.dominio.*;

import javax.swing.*;
import java.awt.*;

public class NivelGUI extends JFrame {

    private static final int CELL_SIZE = 32;

    private Nivel nivel;
    private GameBoard board;

    private JPanel panel;

    private JLabel lblTiempo;

    private JButton btnPlayPause;
    private JButton btnRestart;

    private Timer gameTimer;
    private Timer tiempoTimer;

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

        nivel = new Nivel(board);

        prepararPanel();

        prepararHUD();

        prepararTimers();

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

        btnRestart = new JButton("⟳");

        hud.add(lblTiempo);
        hud.add(btnPlayPause);
        hud.add(btnRestart);

        add(hud, BorderLayout.NORTH);

        btnPlayPause.addActionListener(e -> cambiarEstado());

        btnRestart.addActionListener(e -> reiniciar());
    }

    private void prepararTimers() {

        gameTimer = new Timer(350, e -> {

            int resultado = nivel.actualizar();

            if(resultado == 1){

                detenerTodo();

                JOptionPane.showMessageDialog(
                        this,
                        "Neo fue capturado",
                        "GAME OVER",
                        JOptionPane.ERROR_MESSAGE);

                return;
            }

            if(resultado == 2){

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

        tiempoTimer = new Timer(1000, e -> {

            tiempoRestante--;

            int min = tiempoRestante / 60;
            int seg = tiempoRestante % 60;

            lblTiempo.setText(
                    String.format("%02d:%02d", min, seg));

            if(tiempoRestante <= 0){

                detenerTodo();

                JOptionPane.showMessageDialog(
                        this,
                        "Tiempo agotado",
                        "GAME OVER",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void cambiarEstado() {

        pausado = !pausado;

        if(!pausado){

            gameTimer.start();
            tiempoTimer.start();

            btnPlayPause.setText("⏸");
        }
        else{

            gameTimer.stop();
            tiempoTimer.stop();

            btnPlayPause.setText("▶");
        }
    }

    private void detenerTodo() {

        gameTimer.stop();
        tiempoTimer.stop();
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

        nivel = new Nivel(board);

        pausado = true;

        btnPlayPause.setText("▶");

        panel.repaint();
    }

    private void dibujar(Graphics g) {

        g.drawImage(fondo,0,0,panel.getWidth(),panel.getHeight(),null);

        for(int x=0;x<GameBoard.WIDTH;x++){

            for(int y=0;y<GameBoard.HEIGHT;y++){

                Celda c = board.getCelda(x,y);

                int px = x * CELL_SIZE;
                int py = y * CELL_SIZE;

                if(c.getBloque()!=null){

                    g.drawImage(
                            c.getBloque().getSprite().getImage(),
                            px,
                            py,
                            CELL_SIZE,
                            CELL_SIZE,
                            null);
                }

                if(c.getEntidad()!=null){

                    g.drawImage(
                            c.getEntidad().getSprite().getImage(),
                            px,
                            py,
                            CELL_SIZE,
                            CELL_SIZE,
                            null);
                }
                    ////Cuadricula 
                //g.drawRect(
                 //       px,
                  //      py,
                  //      CELL_SIZE,
                  //      CELL_SIZE);
            }
        }
    }
}
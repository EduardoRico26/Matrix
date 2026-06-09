package main.java.dominio;

public class HiloTiempo extends Thread {

    private final NivelGUIBridge bridge;
    private final GameBoard board;

    private boolean activo = true;

    public HiloTiempo(
            NivelGUIBridge bridge,
            GameBoard board) {

        this.bridge = bridge;
        this.board = board;
    }

    @Override
    public void run() {

        while (activo && !board.isJuegoTerminado()) {

            try {

                if (!board.isPausado()) {
                    bridge.restarSegundo();
                }

                Thread.sleep(1000);

            } catch (InterruptedException e) {
                activo = false;
            }
        }
    }

    public void detener() {
        activo = false;
        interrupt();
    }
}
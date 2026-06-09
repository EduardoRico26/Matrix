package main.java.dominio;

public class HiloNeo extends Thread {

    private final GameBoard board;

    private boolean activo = true;

    public HiloNeo(GameBoard board) {
        this.board = board;
    }

    @Override
    public void run() {

        while (activo && !board.isJuegoTerminado()) {

            try {

                if (!board.isPausado()) {
                    board.actualizarNeo();
                }

                Thread.sleep(350);

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
package main.java.dominio;

public class HiloAgente extends Thread {

    private final GameBoard board;
    private final Agente agente;

    private boolean activo = true;

    public HiloAgente(
            GameBoard board,
            Agente agente) {

        this.board = board;
        this.agente = agente;
    }

    @Override
    public void run() {

        while (activo && !board.isJuegoTerminado() ) {

            try {

                if (!board.isPausado()) {
                    agente.mover(board);
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
package main.java.dominio;

public class Nivel {

    private final GameBoard board;

    public Nivel(GameBoard board) {
        this.board = board;
    }

    public GameBoard getBoard() {
        return board;
    }

    public int verificarEstado() {

        Neo neo = board.getNeo();

        if (neo == null) {
            return 1;
        }

        if (board.neoLlegoTelefono()) {
            return 2;
        }

        if (board.neoCapturado()) {
            return 1;
        }

        return 0;
    }
}
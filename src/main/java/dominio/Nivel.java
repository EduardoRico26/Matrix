package main.java.dominio;

/**
 * Controla la lógica principal del juego.
 */
public class Nivel {

    private GameBoard board;

    public Nivel(GameBoard board) {
        this.board = board;
    }

    public GameBoard getBoard() {
        return board;
    }

    /**
     * Actualiza el nivel.
     *
     * Retorna:
     * 0 = continuar
     * 1 = game over
     * 2 = victoria
     */
    public int actualizar() {

        // Neo se mueve
        board.actualizarNeo();

        // Agentes se mueven
        board.actualizarAgentes();

        Neo neo = board.getNeo();

        if (neo == null) {
            return 1;
        }

        // ¿Neo llegó a un teléfono?
        for (Telefono t : board.getTelefonos()) {

            if (neo.getX() == t.getX()
                    && neo.getY() == t.getY()) {

                return 2; // victoria
            }
        }

        // ¿Un agente atrapó a Neo?
        for (Agente a : board.getAgentes()) {

            if (a.getX() == neo.getX()
                    && a.getY() == neo.getY()) {

                return 1; // game over
            }
        }

        return 0;
    }
}
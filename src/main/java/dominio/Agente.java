package main.java.dominio;

import javax.swing.ImageIcon;

public class Agente extends Entidad {

    private final ImageIcon sprite;

    public Agente(int x, int y) {
        super(x, y);
        sprite = new ImageIcon("imagenes/agente.png");
    }

    @Override
    public ImageIcon getSprite() {
        return sprite;
    }

    @Override
    public void mover(GameBoard board) {

        Neo neo = board.getNeo();

        if (neo == null) {
            return;
        }

        int nx = x;
        int ny = y;

        if (neo.getX() < x) nx--;
        else if (neo.getX() > x) nx++;

        else if (neo.getY() < y) ny--;
        else if (neo.getY() > y) ny++;

        board.moverEntidad(this, nx, ny);
    }
}

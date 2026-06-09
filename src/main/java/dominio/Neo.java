package main.java.dominio;

import javax.swing.ImageIcon;

public class Neo extends Entidad {

    private final ImageIcon sprite;

    public Neo(int x, int y) {
        super(x, y);
        sprite = new ImageIcon("imagenes/neo.png");
    }

    @Override
    public ImageIcon getSprite() {
        return sprite;
    }

    @Override
    public void mover(GameBoard board) {

        Telefono telefono = board.getTelefonoMasCercano(x, y);

        if (telefono == null) {
            return;
        }

        int nx = x;
        int ny = y;

        Agente cercano = board.getAgenteMasCercano(x, y);

        if (cercano != null &&
            board.distancia(x, y, cercano.getX(), cercano.getY()) <= 2) {

            if (cercano.getX() < x) nx++;
            else if (cercano.getX() > x) nx--;

            if (cercano.getY() < y) ny++;
            else if (cercano.getY() > y) ny--;

        } else {

            if (telefono.getX() < x) nx--;
            else if (telefono.getX() > x) nx++;

            else if (telefono.getY() < y) ny--;
            else if (telefono.getY() > y) ny++;
        }

        board.moverEntidad(this, nx, ny);
    }
}

package main.java.dominio;

import javax.swing.ImageIcon;

public abstract class Entidad {

    protected int x;
    protected int y;

    public Entidad(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosicion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void mover(GameBoard board);

    public abstract ImageIcon getSprite();
}
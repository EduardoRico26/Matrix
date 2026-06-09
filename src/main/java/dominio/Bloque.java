package main.java.dominio;

import javax.swing.ImageIcon;

public abstract class Bloque {

    protected int x;
    protected int y;

    public Bloque(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract boolean esTransitableParaNeo();

    public abstract boolean esTransitableParaAgente();

    public abstract ImageIcon getSprite();
}
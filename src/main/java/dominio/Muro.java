package main.java.dominio;

import javax.swing.ImageIcon;

public class Muro extends Bloque {

    private final ImageIcon sprite;

    public Muro(int x, int y) {
        super(x, y);
        sprite = new ImageIcon("imagenes/muro.png");
    }

    @Override
    public boolean esTransitableParaNeo() {
        return false;
    }

    @Override
    public boolean esTransitableParaAgente() {
        return false;
    }

    @Override
    public ImageIcon getSprite() {
        return sprite;
    }
}
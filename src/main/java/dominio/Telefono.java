package main.java.dominio;

import javax.swing.ImageIcon;

public class Telefono extends Bloque {

    private final ImageIcon sprite;

    public Telefono(int x, int y) {
        super(x, y);
        sprite = new ImageIcon("imagenes/telefono.png");
    }

    @Override
    public boolean esTransitableParaNeo() {
        return true;
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
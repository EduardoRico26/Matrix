package main.java.dominio;

public class Celda {

    private Entidad entidad;
    private Bloque bloque;

    public Celda() {
        entidad = null;
        bloque = null;
    }

    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    public Bloque getBloque() {
        return bloque;
    }

    public void setBloque(Bloque bloque) {
        this.bloque = bloque;
    }

    public boolean tieneEntidad() {
        return entidad != null;
    }

    public boolean tieneBloque() {
        return bloque != null;
    }
}
package main.java.dominio;

public enum Direccion {

    NORTE,
    SUR,
    ESTE,
    OESTE;

    public int dx() {
        return switch (this) {
            case ESTE -> 1;
            case OESTE -> -1;
            default -> 0;
        };
    }

    public int dy() {
        return switch (this) {
            case SUR -> 1;
            case NORTE -> -1;
            default -> 0;
        };
    }
}
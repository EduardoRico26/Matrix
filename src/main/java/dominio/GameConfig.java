package main.java.dominio;

public class GameConfig {

    private static int cantidadAgentes;
    private static int cantidadMuros;
    private static int cantidadTelefonos;

    public static void setCantidadAgentes(int cantidadAgentes) {
        GameConfig.cantidadAgentes = cantidadAgentes;
    }

    public static int getCantidadAgentes() {
        return cantidadAgentes;
    }

    public static void setCantidadMuros(int cantidadMuros) {
        GameConfig.cantidadMuros = cantidadMuros;
    }

    public static int getCantidadMuros() {
        return cantidadMuros;
    }

    public static void setCantidadTelefonos(int cantidadTelefonos) {
        GameConfig.cantidadTelefonos = cantidadTelefonos;
    }

    public static int getCantidadTelefonos() {
        return cantidadTelefonos;
    }
}
package main.java.dominio;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBoard {

    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;

    private final Celda[][] celdas;

    private Neo neo;
    private volatile boolean juegoTerminado = false;
    private final List<Agente> agentes;
    private final List<Telefono> telefonos;
    private final List<Muro> muros;

    private final Random random;
    private volatile boolean pausado = true;

    public boolean isPausado() {
        return pausado;
    }

    public void setPausado(boolean pausado) {
        this.pausado = pausado;
    }

    public boolean isJuegoTerminado() {
    return juegoTerminado;
    }

    public void setJuegoTerminado(boolean juegoTerminado) {
        this.juegoTerminado = juegoTerminado;
    }

    public GameBoard() {

        celdas = new Celda[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                celdas[x][y] = new Celda();
            }
        }

        agentes = new ArrayList<>();
        telefonos = new ArrayList<>();
        muros = new ArrayList<>();

        random = new Random();
    }

    public Celda getCelda(int x, int y) {
        if (!esPosicionValida(x, y)) {
            return null;
        }
        return celdas[x][y];
    }

    public boolean esPosicionValida(int x, int y) {
        return x >= 0 &&
               x < WIDTH &&
               y >= 0 &&
               y < HEIGHT;
    }

    public Neo getNeo() {
        return neo;
    }

    public List<Agente> getAgentes() {
        return agentes;
    }

    public List<Telefono> getTelefonos() {
        return telefonos;
    }

    public List<Muro> getMuros() {
        return muros;
    }

    /*
     * ===========================================
     * GENERACIÓN DEL ESCENARIO
     * ===========================================
     */

    public void generarEscenario(
            int cantidadAgentes,
            int cantidadMuros,
            int cantidadTelefonos) {

        generarNeo();

        generarTelefonos(cantidadTelefonos);

        generarMuros(cantidadMuros);

        generarAgentes(cantidadAgentes);
    }

    private void generarNeo() {

        int x;
        int y;

        do {
            x = random.nextInt(WIDTH);
            y = random.nextInt(HEIGHT);
        }
        while (celdaOcupada(x, y));

        neo = new Neo(x, y);

        celdas[x][y].setEntidad(neo);
    }

    private void generarTelefonos(int cantidad) {

        for (int i = 0; i < cantidad; i++) {

            int x;
            int y;

            do {
                x = random.nextInt(WIDTH);
                y = random.nextInt(HEIGHT);
            }
            while (!posicionDisponible(x, y));

            Telefono telefono = new Telefono(x, y);

            telefonos.add(telefono);

            celdas[x][y].setBloque(telefono);
        }
    }

    private void generarMuros(int cantidad) {

        for (int i = 0; i < cantidad; i++) {

            int x;
            int y;

            do {
                x = random.nextInt(WIDTH);
                y = random.nextInt(HEIGHT);
            }
            while (!posicionDisponible(x, y));

            Muro muro = new Muro(x, y);

            muros.add(muro);

            celdas[x][y].setBloque(muro);
        }
    }

    private void generarAgentes(int cantidad) {

        for (int i = 0; i < cantidad; i++) {

            int x;
            int y;

            do {
                x = random.nextInt(WIDTH);
                y = random.nextInt(HEIGHT);
            }
            while (!posicionValidaParaAgente(x, y));

            Agente agente = new Agente(x, y);

            agentes.add(agente);

            celdas[x][y].setEntidad(agente);
        }
    }

    /*
     * ===========================================
     * VALIDACIONES
     * ===========================================
     */

    private boolean celdaOcupada(int x, int y) {

        return celdas[x][y].getEntidad() != null ||
               celdas[x][y].getBloque() != null;
    }

    private boolean posicionDisponible(int x, int y) {

        return !celdaOcupada(x, y)
                && fueraZonaProtegidaNeo(x, y);
    }

    private boolean posicionValidaParaAgente(int x, int y) {

        return !celdaOcupada(x, y)
                && fueraZonaProtegidaNeo(x, y);
    }

    /*
     * Zona protegida de 3 casillas alrededor de Neo
     */

    private boolean fueraZonaProtegidaNeo(int x, int y) {

        if (neo == null) {
            return true;
        }

        return Math.abs(x - neo.getX()) > 3 ||
               Math.abs(y - neo.getY()) > 3;
    }

    /*
     * ===========================================
     * MOVIMIENTO
     * ===========================================
     */

    public synchronized void moverEntidad(
            Entidad entidad,
            int nuevoX,
            int nuevoY) {

        if (!esPosicionValida(nuevoX, nuevoY)) {
            return;
        }

        Celda destino = celdas[nuevoX][nuevoY];

        if (entidad instanceof Neo) {

            if (destino.getBloque() instanceof Muro) {
                return;
            }

            if (destino.getEntidad() instanceof Agente) {
                return;
            }

        } else if (entidad instanceof Agente) {

            if (destino.getBloque() instanceof Muro) {
                return;
            }

            if (destino.getBloque() instanceof Telefono) {
                return;
            }

            if (destino.getEntidad() instanceof Agente) {
                return;
            }
        }

        celdas[entidad.getX()][entidad.getY()]
                .setEntidad(null);

        entidad.setPosicion(nuevoX, nuevoY);

        destino.setEntidad(entidad);
    }

    /*
     * ===========================================
     * BÚSQUEDAS
     * ===========================================
     */

    public Telefono getTelefonoMasCercano(int x, int y) {

        Telefono mejor = null;

        int distanciaMinima = Integer.MAX_VALUE;

        for (Telefono telefono : telefonos) {

            int distancia =
                    distancia(
                            x,
                            y,
                            telefono.getX(),
                            telefono.getY());

            if (distancia < distanciaMinima) {

                distanciaMinima = distancia;

                mejor = telefono;
            }
        }

        return mejor;
    }

    public Agente getAgenteMasCercano(int x, int y) {

        Agente mejor = null;

        int distanciaMinima = Integer.MAX_VALUE;

        for (Agente agente : agentes) {

            int distancia =
                    distancia(
                            x,
                            y,
                            agente.getX(),
                            agente.getY());

            if (distancia < distanciaMinima) {

                distanciaMinima = distancia;

                mejor = agente;
            }
        }

        return mejor;
    }

    public int distancia(
            int x1,
            int y1,
            int x2,
            int y2) {

        return Math.abs(x1 - x2)
                + Math.abs(y1 - y2);
    }

    /*
     * ===========================================
     * CONDICIONES DE FIN
     * ===========================================
     */

    public boolean neoLlegoTelefono() {

        for (Telefono telefono : telefonos) {

            if (telefono.getX() == neo.getX()
                    && telefono.getY() == neo.getY()) {

                return true;
            }
        }

        return false;
    }

    public boolean neoCapturado() {

        for (Agente agente : agentes) {

            int distancia =
                    Math.abs(agente.getX() - neo.getX())
                + Math.abs(agente.getY() - neo.getY());

            if (distancia <= 1) {
                return true;
            }
        }

        return false;
    }


        /*
    * ===========================================
    * ACTUALIZACIÓN
    * ===========================================
    */

    public void actualizarNeo() {

        if (neo != null) {
            neo.mover(this);
        }
    }

    public void actualizarAgentes() {

        for (Agente agente : agentes) {
            agente.mover(this);
        }
    }
    
}

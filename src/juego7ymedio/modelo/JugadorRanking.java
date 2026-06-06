package juego7ymedio.modelo;

public class JugadorRanking {
    private int posicion;
    private int idJugador;
    private String apodo;
    private int victorias;

    public JugadorRanking(int posicion, int idJugador, String apodo, int victorias) {
        this.posicion = posicion;
        this.idJugador = idJugador;
        this.apodo = apodo;
        this.victorias = victorias;
    }

    public int getPosicion() {
        return posicion;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public String getApodo() {
        return apodo;
    }

    public int getVictorias() {
        return victorias;
    }
}

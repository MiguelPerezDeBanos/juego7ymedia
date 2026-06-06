package juego7ymedio.modelo;

import java.util.ArrayList;

public class Partida {
    private ArrayList<Jugador> jugadores = new ArrayList<>();
    private Baraja baraja = new Baraja();
    private int turnoActual;
    private boolean finalizada;
    private Jugador ganador;
    private String mensaje = "Partida iniciada";
    private boolean muerteSubita;

    public Partida(ArrayList<String> nombres) {
        for (String nombre : nombres) {
            jugadores.add(new Jugador(nombre));
        }
        repartirCartaInicial();
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public ArrayList<String> getNombresJugadores() {
        ArrayList<String> nombres = new ArrayList<>();
        for (Jugador jugador : jugadores) {
            nombres.add(jugador.getNombre());
        }
        return nombres;
    }

    public Jugador getJugadorActual() {
        if (jugadores.isEmpty()) {
            return null;
        }
        return jugadores.get(turnoActual);
    }

    public String getMensaje() {
        return mensaje;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public Jugador getGanador() {
        return ganador;
    }

    public boolean isMuerteSubita() {
        return muerteSubita;
    }

    public void pedirCarta() {
        if (finalizada) {
            return;
        }

        Jugador jugador = getJugadorActual();
        Carta carta = baraja.repartir();
        jugador.addCarta(carta);

        if (jugador.getPuntuacion() > 7.5) {
            jugador.setEliminado(true);
            jugador.setPlantado(true);
            mensaje = jugador.getNombre() + " se ha pasado con " + formatearPuntos(jugador.getPuntuacion());
            avanzarTurno();
        } else if (jugador.getPuntuacion() == 7.5) {
            jugador.setPlantado(true);
            mensaje = jugador.getNombre() + " consigue siete y medio";
            avanzarTurno();
        } else {
            mensaje = jugador.getNombre() + " recibe " + carta.getNombre();
        }
    }

    public void plantarse() {
        if (finalizada) {
            return;
        }

        Jugador jugador = getJugadorActual();
        jugador.setPlantado(true);
        mensaje = jugador.getNombre() + " se planta con " + formatearPuntos(jugador.getPuntuacion());
        avanzarTurno();
    }

    public String formatearPuntos(double puntos) {
        if (puntos == (int) puntos) {
            return String.valueOf((int) puntos);
        }
        return String.valueOf(puntos).replace(".", ",");
    }

    private void repartirCartaInicial() {
        for (Jugador jugador : jugadores) {
            jugador.addCarta(baraja.repartir());
        }
    }

    private void avanzarTurno() {
        if (todosHanTerminado()) {
            resolverGanador();
            return;
        }

        do {
            turnoActual++;
            if (turnoActual >= jugadores.size()) {
                turnoActual = 0;
            }
        } while (jugadores.get(turnoActual).isPlantado());

        mensaje = mensaje + "  Turno de " + getJugadorActual().getNombre();
    }

    private boolean todosHanTerminado() {
        for (Jugador jugador : jugadores) {
            if (!jugador.isPlantado()) {
                return false;
            }
        }
        return true;
    }

    private void resolverGanador() {
        double mejorPuntuacion = -1;
        ArrayList<Jugador> posiblesGanadores = new ArrayList<>();

        for (Jugador jugador : jugadores) {
            double puntos = jugador.getPuntuacion();
            if (puntos <= 7.5) {
                if (puntos > mejorPuntuacion) {
                    mejorPuntuacion = puntos;
                    posiblesGanadores.clear();
                    posiblesGanadores.add(jugador);
                } else if (puntos == mejorPuntuacion) {
                    posiblesGanadores.add(jugador);
                }
            }
        }

        if (posiblesGanadores.isEmpty()) {
            finalizada = true;
            ganador = null;
            mensaje = "Todos se han pasado y no hay ganador";
            return;
        }

        if (posiblesGanadores.size() == 1) {
            finalizada = true;
            ganador = posiblesGanadores.get(0);
            mensaje = "Gana " + ganador.getNombre() + " con " + formatearPuntos(ganador.getPuntuacion());
            return;
        }

        iniciarMuerteSubita(posiblesGanadores);
    }

    private void iniciarMuerteSubita(ArrayList<Jugador> empatados) {
        ArrayList<Jugador> nuevosJugadores = new ArrayList<>();

        for (Jugador jugador : empatados) {
            nuevosJugadores.add(new Jugador(jugador.getNombre()));
        }

        jugadores = nuevosJugadores;
        baraja = new Baraja();
        turnoActual = 0;
        ganador = null;
        finalizada = false;
        muerteSubita = true;
        mensaje = "Empate detectado  Comienza muerte subita";
        repartirCartaInicial();
    }
}

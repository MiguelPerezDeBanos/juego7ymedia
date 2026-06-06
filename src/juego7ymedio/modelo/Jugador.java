package juego7ymedio.modelo;

import java.util.ArrayList;

public class Jugador {
    private String nombre;
    private ArrayList<Carta> cartas = new ArrayList<>();
    private boolean plantado;
    private boolean eliminado;

    public Jugador(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Carta> getCartas() {
        return cartas;
    }

    public void addCarta(Carta carta) {
        cartas.add(carta);
    }

    public void limpiarCartas() {
        cartas.clear();
        plantado = false;
        eliminado = false;
    }

    public double getPuntuacion() {
        double total = 0;
        for (Carta carta : cartas) {
            total += carta.getValor();
        }
        return total;
    }

    public boolean isPlantado() {
        return plantado;
    }

    public void setPlantado(boolean plantado) {
        this.plantado = plantado;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }
}

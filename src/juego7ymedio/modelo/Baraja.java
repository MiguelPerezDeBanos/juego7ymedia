package juego7ymedio.modelo;

import java.util.ArrayList;
import java.util.Collections;

public class Baraja {
    private ArrayList<Carta> cartas = new ArrayList<>();

    public Baraja() {
        crearBaraja();
        barajar();
    }

    public Carta repartir() {
        if (cartas.isEmpty()) {
            crearBaraja();
            barajar();
        }
        return cartas.remove(0);
    }

    private void crearBaraja() {
        cartas.clear();
        String[] palos = {"Oros", "Copas", "Espadas", "Bastos"};
        int[] numeros = {1, 2, 3, 4, 5, 6, 7, 10, 11, 12};

        for (String palo : palos) {
            for (int numero : numeros) {
                cartas.add(new Carta(numero, palo));
            }
        }
    }

    private void barajar() {
        Collections.shuffle(cartas);
    }
}

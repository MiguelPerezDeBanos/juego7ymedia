package juego7ymedio.modelo;

public class Carta {
    private int numero;
    private String palo;

    public Carta(int numero, String palo) {
        this.numero = numero;
        this.palo = palo;
    }

    public int getNumero() {
        return numero;
    }

    public String getPalo() {
        return palo;
    }

    public double getValor() {
        if (numero >= 10) {
            return 0.5;
        }
        return numero;
    }

    public String getNombre() {
        if (numero == 10) {
            return "Sota de " + palo;
        }
        if (numero == 11) {
            return "Caballo de " + palo;
        }
        if (numero == 12) {
            return "Rey de " + palo;
        }
        return numero + " de " + palo;
    }

    public String getNumeroTexto() {
        if (numero == 10) {
            return "S";
        }
        if (numero == 11) {
            return "C";
        }
        if (numero == 12) {
            return "R";
        }
        return String.valueOf(numero);
    }
}

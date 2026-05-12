package com.example.proyectoescriturarapida.model;

import java.util.Random;

/**
 * Model class that manages the dictionary of words and phrases for the game.
 * Implements the {@link IPalabras} interface to provide random word selection
 * without consecutive repetition, ensuring a varied gameplay experience.
 *
 * <p>Words are common everyday Spanish terms organized from simple to complex,
 * providing a natural and accessible challenge for all players.</p>
 *
 * @author Julio Cesar
 * @version 3.0
 */
public class Palabras implements IPalabras {

    /** Full word and phrase bank used during gameplay. */
    private final String[] lista = {
        "carro", "casa", "arbol", "perro", "gato", "libro",
        "mesa", "silla", "puerta", "ventana", "agua", "fuego",
        "tierra", "cielo", "noche", "dia", "sol", "luna",
        "flor", "fruta", "zapato", "camisa", "reloj", "lapiz",
        "ciudad", "pueblo", "playa", "montana", "rio", "campo",
        "amigo", "familia", "trabajo", "escuela", "cocina", "jardin",
        "mercado", "hospital", "iglesia", "estadio", "parque", "puente",
        "camino", "carretera", "avion", "barco", "tren", "bicicleta",
        "telefono", "computadora", "television", "nevera", "estufa", "lavadora",
        "guitarra", "pelota", "cuaderno", "mochila", "billetera", "paraguas",
        "mariposa", "tortuga", "elefante", "jirafas", "delfin", "aguila",
        "naranja", "manzana", "platano", "sandia", "mango", "fresa"
    };

    /** Random number generator for word selection. */
    private final Random random = new Random();

    /** Stores the last selected word to avoid immediate repetition. */
    private String ultimaPalabra = "";

    /**
     * Selects and returns a random word from the predefined list.
     * Guarantees that the returned word is different from the previously shown one,
     * preventing consecutive repetition.
     *
     * @return A randomly selected {@code String} that differs from the last result.
     */
    @Override
    public String obtenerAleatoria() {
        if (lista.length == 1) return lista[0];
        String nueva;
        do {
            nueva = lista[random.nextInt(lista.length)];
        } while (nueva.equals(ultimaPalabra));
        ultimaPalabra = nueva;
        return nueva;
    }
}

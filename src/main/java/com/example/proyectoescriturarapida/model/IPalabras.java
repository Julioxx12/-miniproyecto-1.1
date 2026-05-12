package com.example.proyectoescriturarapida.model;

/**
 * Interface that defines the contract for word management in the game.
 * Any class implementing this interface must provide a mechanism to
 * retrieve a random word or phrase for the player to type.
 *
 * @author Julio Cesar
 * @version 1.0
 */
public interface IPalabras {

    /**
     * Returns a random word or phrase from the available word bank.
     * Implementations should ensure variety and avoid immediate repetition.
     *
     * @return A non-null {@code String} representing a word or phrase.
     */
    String obtenerAleatoria();
}

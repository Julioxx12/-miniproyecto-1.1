package com.example.proyectoescriturarapida.model;

/**
 * Interface that defines the contract for the countdown timer used in the game.
 * Implementations must support starting, stopping, and querying the remaining time.
 *
 * @author Julio Cesar
 * @version 1.0
 */
public interface ITemporizador {

    /**
     * Starts or restarts the countdown from the given number of seconds.
     * Any previously running countdown must be cancelled before starting a new one.
     *
     * @param segundos The initial countdown value in seconds (must be greater than 0).
     */
    void iniciar(int segundos);

    /**
     * Stops the countdown immediately.
     * Safe to call even if the timer is not currently running.
     */
    void detener();

    /**
     * Returns the number of seconds remaining in the current countdown.
     *
     * @return Remaining time in seconds; {@code 0} if the timer has expired or was stopped.
     */
    int getTiempoRestante();
}

/*
 * ================================================================
 *  Falling Frenzy - FallingObject
 *  Author: Molly O'Connor
 *
 *  Model class representing a single falling object.
 *  Handles movement and randomized reset behavior.
 * ================================================================
 */

import kotlin.random.Random

/**
 * Represents one falling object in the game.
 *
 * @property x Current horizontal position
 * @property y Current vertical position
 * @property size Diameter of the object (default 20)
 * @property speed Falling speed in pixels per update
 */
data class FallingObject(
    var x: Int,
    var y: Int,
    val size: Int = 20,
    var speed: Int = 3
) {

    /** Moves the object downward. */
    fun fall() {
        y += speed
    }

    /**
     * Resets the object above the screen with a new random
     * horizontal position and speed based on difficulty.
     */
    fun reset(panelWidth: Int, speedRange: IntRange) {
        x = Random.nextInt(0, panelWidth - size)
        y = Random.nextInt(-200, 0) // spawn above visible area
        speed = Random.nextInt(speedRange.first, speedRange.last + 1)
    }
}
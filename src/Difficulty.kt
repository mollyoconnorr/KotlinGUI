/*
 * ================================================================
 *  Falling Frenzy - Difficulty Enum
 *  Author: Molly O'Connor
 *
 *  Defines the available difficulty levels and their gameplay settings.
 *  Each level controls:
 *      - Number of falling objects
 *      - Speed range of those objects
 * ================================================================
 */

/**
 * Represents game difficulty settings.
 *
 * @property objectCount Number of falling objects on screen.
 * @property speedRange Range of possible falling speeds.
 */
enum class Difficulty(
    val objectCount: Int,
    val speedRange: IntRange
) {

    /** Fewer objects and slower speeds. */
    EASY(3, 2..4),

    /** Moderate object count and speed. */
    MEDIUM(4, 2..5),

    /** More objects and faster speeds. */
    HARD(5, 3..6)
}
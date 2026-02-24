/*
 * ================================================================
 *  Falling Frenzy - Scoreboard
 *  Author: Molly O'Connor
 *
 *  Manages high scores for each difficulty level.
 *  Loads scores from files at startup and saves updates automatically.
 * ================================================================
 */

import java.io.File

/**
 * Singleton object responsible for storing and persisting
 * the top 5 scores per difficulty.
 */
object Scoreboard {

    /** Stores scores grouped by difficulty (username to score). */
    private val scores: MutableMap<Difficulty, MutableList<Pair<String, Int>>> =
        mutableMapOf()

    /** Folder where score files are saved. */
    private const val folder = "scores"

    init {
        // Ensure score directory exists
        File(folder).mkdirs()

        // Initialize lists and load saved scores
        for (difficulty in Difficulty.values()) {
            scores[difficulty] = mutableListOf()
            loadScores(difficulty)
        }
    }

    /**
     * Adds a new score, sorts the list, keeps top 5,
     * and saves the updated results.
     */
    fun addScore(username: String, difficulty: Difficulty, score: Int) {
        val list = scores[difficulty]!!

        list.add(username to score)
        list.sortByDescending { it.second }

        // Keep only top 5 scores
        if (list.size > 5) {
            list.subList(5, list.size).clear()
        }

        saveScores(difficulty)
    }

    /**
     * Returns a copy of the top scores for a difficulty.
     */
    fun getTopScores(difficulty: Difficulty): List<Pair<String, Int>> {
        return scores[difficulty]?.toList() ?: emptyList()
    }

    /** Returns the file associated with a specific difficulty. */
    private fun getFile(difficulty: Difficulty): File {
        return File("$folder/${difficulty.name}.txt")
    }

    /** Saves the top scores for a difficulty to its file. */
    private fun saveScores(difficulty: Difficulty) {
        val file = getFile(difficulty)

        file.bufferedWriter().use { writer ->
            scores[difficulty]?.forEach { (user, score) ->
                writer.write("$user:$score\n")
            }
        }
    }

    /** Loads saved scores from file and keeps only the top 5. */
    private fun loadScores(difficulty: Difficulty) {
        val file = getFile(difficulty)
        if (!file.exists()) return

        val list = mutableListOf<Pair<String, Int>>()

        file.forEachLine { line ->
            val parts = line.split(":")
            if (parts.size == 2) {
                val user = parts[0]
                val score = parts[1].toIntOrNull() ?: 0
                list.add(user to score)
            }
        }

        list.sortByDescending { it.second }

        if (list.size > 5) {
            list.subList(5, list.size).clear()
        }

        scores[difficulty] = list
    }
}
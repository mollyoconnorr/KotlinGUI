/*
 * ================================================================
 *  Falling Frenzy - Game Over Panel
 *  Author: Molly O'Connor
 *
 *  Displays:
 *      - Final score information
 *      - Top 5 scores for the selected difficulty
 *      - Options to restart or quit the game
 *
 * Reference: ChatGPT was used for color generation
 * ================================================================
 */

import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import javax.swing.*

/**
 * GameOverPanel is shown when the game ends.
 * Shows the players score, difficulty, and the scoreboard for that difficulty
 *
 * @param username Player's username
 * @param difficulty Difficulty that was played
 * @param score Final score achieved
 * @param onRestart Callback triggered when "Play Again" is pressed
 */
class GameOverPanel(
    private val username: String,
    private val difficulty: Difficulty,
    private val score: Int,
    private val onRestart: () -> Unit
) : JPanel() {

    init {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        background = Color(0xF0F8FF)
        alignmentX = CENTER_ALIGNMENT
        border = BorderFactory.createEmptyBorder(30, 30, 30, 30)

        // Save player's score
        Scoreboard.addScore(username, difficulty, score)

        // Title
        val title = JLabel("Game Over").apply {
            font = Font("Arial", Font.BOLD, 36)
            foreground = Color.BLUE
            alignmentX = CENTER_ALIGNMENT
        }
        add(Box.createRigidArea(Dimension(0, 50)))
        add(title)

        // Player summary
        val infoLabel = JLabel(
            "<html>Thanks for playing: $username<br>" +
                    "Difficulty: $difficulty, Score: $score</html>"
        ).apply {
            font = Font("Arial", Font.PLAIN, 20)
            foreground = Color(0x333333)
            alignmentX = CENTER_ALIGNMENT
        }
        add(Box.createRigidArea(Dimension(0, 30)))
        add(infoLabel)

        // Top 5 Scores
        val topScores = Scoreboard.getTopScores(difficulty)

        val scoreText = StringBuilder("<html><b>Top 5 Scores:</b><br>")
        for ((i, entry) in topScores.withIndex()) {
            scoreText.append("${i + 1}. ${entry.first}: ${entry.second}<br>")
        }
        scoreText.append("</html>")

        val scoreboardLabel = JLabel(scoreText.toString()).apply {
            font = Font("Arial", Font.PLAIN, 18)
            foreground = Color(0x2F4F4F)
            alignmentX = CENTER_ALIGNMENT
        }

        add(Box.createRigidArea(Dimension(0, 20)))
        add(scoreboardLabel)

        // Play Again Button
        val playAgainButton = JButton("Play Again").apply {
            alignmentX = CENTER_ALIGNMENT
            font = Font("Arial", Font.BOLD, 18)
            background = Color.WHITE
            foreground = Color(0x2E8B57)
            isFocusPainted = false
            maximumSize = Dimension(200, 40)
            addActionListener { onRestart() }
        }

        add(Box.createRigidArea(Dimension(0, 30)))
        add(playAgainButton)

        // Quit Button
        val quitButton = JButton("Quit").apply {
            alignmentX = CENTER_ALIGNMENT
            font = Font("Arial", Font.BOLD, 18)
            background = Color.WHITE
            foreground = Color.RED
            isFocusPainted = false
            maximumSize = Dimension(200, 40)
            addActionListener { System.exit(0) }
        }

        add(Box.createRigidArea(Dimension(0, 10)))
        add(quitButton)
    }
}
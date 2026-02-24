/*
 * ================================================================
 *  Falling Frenzy - Game Panel
 *  Author: Molly O'Connor
 *
 *  Main gameplay panel.
 *  Handles:
 *      - Player movement
 *      - Falling object logic
 *      - Collision detection
 *      - Score and lives tracking
 *      - Rendering and game loop
 * ================================================================
 */

import java.awt.*
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.JPanel
import javax.swing.Timer
import kotlin.random.Random

/**
 * GamePanel controls the core gameplay experience.
 *
 * @param difficulty Selected game difficulty (controls object count and speed).
 * @param username Player's username.
 * @param onGameOver Callback triggered when the player loses all lives.
 */
class GamePanel(
    difficulty: Difficulty,
    private val username: String,
    private val onGameOver: (score: Int) -> Unit
) : JPanel() {

    // Player Properties
    private var playerX = 200
    private val playerY = 450
    private val playerWidth = 60
    private val playerHeight = 20
    private val playerSpeed = 40

    // Game State
    private var score = 0
    private var lives = 3
    private var gameOver = false

    private val speedRange = difficulty.speedRange
    private val fallingObjects = mutableListOf<FallingObject>()

    init {
        background = Color(0xE6F2FF)
        isFocusable = true

        // Initialize falling objects based on difficulty
        repeat(difficulty.objectCount) {
            val xPos = Random.nextInt(0, 350)
            val yPos = Random.nextInt(-200, 0)
            val speed = Random.nextInt(speedRange.first, speedRange.last + 1)
            fallingObjects.add(FallingObject(x = xPos, y = yPos, speed = speed))
        }

        // Keyboard Controls
        addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent) {
                if (gameOver) return

                when (e.keyCode) {
                    KeyEvent.VK_LEFT -> playerX -= playerSpeed
                    KeyEvent.VK_RIGHT -> playerX += playerSpeed
                }

                // Keep player within screen bounds
                if (playerX < 0) playerX = 0
                if (playerX > width - playerWidth) {
                    playerX = width - playerWidth
                }

                repaint()
            }
        })

        // Game Loop (Timer), around 33 updates per second
        Timer(30) {
            if (!gameOver) {

                for (obj in fallingObjects) {
                    obj.fall()

                    // Collision detection
                    if (obj.y + obj.size >= playerY &&
                        obj.x + obj.size >= playerX &&
                        obj.x <= playerX + playerWidth
                    ) {
                        score++
                        obj.reset(width, speedRange)
                    }

                    // Missed object
                    if (obj.y > height) {
                        lives--
                        obj.reset(width, speedRange)

                        if (lives <= 0 && !gameOver) {
                            gameOver = true
                            onGameOver(score)
                        }
                    }
                }

                repaint()
            }
        }.start()
    }

    /**
     * Renders all game graphics.
     */
    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2 = g as Graphics2D

        // Smooth rendering
        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        )

        // Background gradient
        val gradient = GradientPaint(
            0f, 0f, Color(0xE6F2FF),
            0f, height.toFloat(), Color(0xCFE8FF)
        )
        g2.paint = gradient
        g2.fillRect(0, 0, width, height)

        // Draw player
        g2.color = Color(0x1E90FF)
        g2.fillRoundRect(playerX, playerY, playerWidth, playerHeight, 20, 20)

        // Draw falling objects
        g2.color = Color(0xFF4C4C)
        for (obj in fallingObjects) {
            g2.fillOval(obj.x, obj.y, obj.size, obj.size)
        }

        // Heads-Up Display (Score & Lives)
        g2.color = Color(0x333333)
        g2.font = Font("Arial", Font.BOLD, 18)
        g2.drawString("Score: $score", 15, 25)
        g2.drawString("Lives: $lives", 15, 50)

        // Game Over overlay
        if (gameOver) {
            g2.color = Color(0, 0, 0, 150)
            g2.fillRect(0, 0, width, height)

            g2.color = Color.WHITE
            g2.font = Font("Arial", Font.BOLD, 48)

            val fm = g2.fontMetrics
            val textWidth = fm.stringWidth("GAME OVER")
            g2.drawString("GAME OVER", (width - textWidth) / 2, height / 2)
        }
    }
}
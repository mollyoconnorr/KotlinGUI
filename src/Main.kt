/*
 * ================================================================
 *  Falling Frenzy - Application Entry Point & Screen Controller
 *  Author: Molly O'Connor
 *
 *  Description:
 *  This file serves as the main entry point of the Falling Frenzy game.
 *  It is responsible for:
 *      - Creating the main application window (JFrame)
 *      - Managing screen transitions (Start, Game, Game Over)
 *      - Coordinating communication between panels
 *
 *  Design Pattern:
 *  This file acts as a simple screen controller. Each panel communicates
 *  back using lambda callbacks, allowing smooth transitions without tight
 *  coupling between UI components.
 *
 *  Reference: ChatGPT was used for color generation
 * ================================================================
 */

import java.awt.BorderLayout
import javax.swing.JFrame
import javax.swing.SwingUtilities

/**
 * Global reference to the main application window.
 *
 * Marked as lateinit because it is initialized inside main()
 * before any screen rendering functions are called.
 */
lateinit var frame: JFrame

/**
 * Displays the Start Screen panel.
 *
 * This function:
 * 1. Clears existing content from the frame
 * 2. Creates a new StartScreen
 * 3. Provides a callback that transitions to the Game panel
 * 4. Refreshes the frame
 */
fun showStartScreen() {
    // Remove current UI components
    frame.contentPane.removeAll()

    // Create start screen and define what happens when Start is pressed
    val startScreen = StartScreen { username, difficulty ->
        showGamePanel(username, difficulty)
    }

    // Add panel to center of frame
    frame.add(startScreen, BorderLayout.CENTER)

    // Refresh UI
    frame.revalidate()
    frame.repaint()
}

/**
 * Displays the main Game Panel.
 *
 * @param username The player's chosen username
 * @param difficulty Selected difficulty level
 *
 * This function:
 * 1. Clears the current frame
 * 2. Creates a GamePanel
 * 3. Provides a callback to transition to Game Over screen
 * 4. Requests keyboard focus for gameplay controls
 */
fun showGamePanel(username: String, difficulty: Difficulty) {
    frame.contentPane.removeAll()

    // Create game panel with callback for when game ends
    val gamePanel = GamePanel(difficulty, username) { finalScore ->
        showGameOverPanel(username, difficulty, finalScore)
    }

    frame.add(gamePanel, BorderLayout.CENTER)

    frame.revalidate()
    frame.repaint()

    // Important: ensures keyboard input works immediately
    gamePanel.requestFocusInWindow()
}

/**
 * Displays the Game Over panel.
 *
 * @param username The player's username
 * @param difficulty The difficulty that was played
 * @param score The player's final score
 *
 * This function:
 * 1. Clears the frame
 * 2. Creates a GameOverPanel
 * 3. Provides callback to restart game (returns to Start screen)
 */
fun showGameOverPanel(username: String, difficulty: Difficulty, score: Int) {
    frame.contentPane.removeAll()

    val gameOverPanel = GameOverPanel(username, difficulty, score) {
        // Restart game by returning to Start Screen
        showStartScreen()
    }

    frame.add(gameOverPanel, BorderLayout.CENTER)

    frame.revalidate()
    frame.repaint()
}

/**
 * Application entry point.
 *
 * Initializes the main JFrame and launches the Start Screen.
 *
 * SwingUtilities.invokeLater ensures that all UI components
 * are created and updated on the Event Dispatch Thread (EDT),
 * which is required for thread-safe Swing operations.
 */
fun main() {
    SwingUtilities.invokeLater {

        // Create main application window
        frame = JFrame("Falling Frenzy")

        frame.setSize(400, 500)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.layout = BorderLayout()

        // Load initial screen
        showStartScreen()

        // Make window visible
        frame.isVisible = true
    }
}
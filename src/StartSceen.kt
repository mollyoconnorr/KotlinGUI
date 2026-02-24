/*
 * ================================================================
 *  Falling Frenzy - Start Screen
 *  Author: Molly O'Connor
 *  Description:
 *  This file defines the StartScreen panel for the Falling Frenzy game.
 *  It provides:
 *      - Game title and instructions
 *      - Username input (with validation + length restriction)
 *      - Difficulty selection
 *      - Start button (enabled only when valid input is entered)
 *
 *  The panel communicates with the main game controller through
 *  a lambda callback that passes the username and selected difficulty.
 *
 *  Reference: ChatGPT was used for color generation
 * ================================================================
 */

import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.event.ActionEvent
import javax.swing.*
import javax.swing.text.AttributeSet
import javax.swing.text.DocumentFilter
import javax.swing.text.PlainDocument

/**
 * StartScreen represents the opening UI panel for the Falling Frenzy game.
 *
 * This panel is responsible for:
 * - Displaying the game title
 * - Showing gameplay instructions
 * - Collecting a username (with validation)
 * - Allowing the player to select a difficulty level
 * - Triggering the game start via a callback function
 *
 * @property onStart Lambda function invoked when the Start Game button is pressed.
 * It passes:
 *      - username: The trimmed player username (defaults to "Player" if blank), which souldn't happen
 *      - difficulty: The selected Difficulty enum value
 */
class StartScreen(
    private val onStart: (username: String, difficulty: Difficulty) -> Unit
) : JPanel() {

    /**
     * Text field for entering the player's username.
     * Limited to 15 characters via LengthWarningFilter.
     */
    private val usernameField = JTextField(15)

    /**
     * Dropdown combo box that displays all available Difficulty enum values.
     */
    private val difficultyBox = JComboBox(Difficulty.values())

    /**
     * Label used to display validation warnings.
     * Example: Username exceeding maximum length.
     */
    private val warningLabel = JLabel("").apply {
        foreground = Color.RED
        alignmentX = CENTER_ALIGNMENT
        font = Font("Arial", Font.BOLD, 14)
    }

    /**
     * Button used to start the game.
     *
     * Behavior:
     * - Initially disabled
     * - Enabled only when username field is not blank
     * - Invokes onStart callback when clicked
     */
    private val startButton = JButton("Start Game").apply {
        alignmentX = CENTER_ALIGNMENT
        font = Font("Arial", Font.BOLD, 18)
        background = Color.WHITE
        foreground = Color(0x2E8B57)
        isFocusPainted = false
        maximumSize = Dimension(200, 40)
        isEnabled = false // Disabled until valid username entered

        // Action listener triggers game start
        addActionListener { _: ActionEvent ->
            // Trim whitespace before starting game
            val username = usernameField.text.trim().ifBlank { "Player" }
            val difficulty = difficultyBox.selectedItem as Difficulty

            // Pass data to main controller
            onStart(username, difficulty)
        }
    }

    /**
     * Initialization block sets up:
     * - Layout manager
     * - Styling
     * - Component positioning
     * - Input validation
     * - Event listeners
     */
    init {
        // Use vertical box layout for stacked UI elements
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        background = Color(0xF0F8FF) // Alice Blue theme color
        alignmentX = CENTER_ALIGNMENT
        border = BorderFactory.createEmptyBorder(30, 30, 30, 30)

        // Apply character length filter to username input
        val doc = usernameField.document as PlainDocument
        doc.documentFilter = LengthWarningFilter(15, warningLabel)

        // Enable Start button only when trimmed username is not blank
        usernameField.document.addDocumentListener(object : javax.swing.event.DocumentListener {
            override fun insertUpdate(e: javax.swing.event.DocumentEvent?) = check()
            override fun removeUpdate(e: javax.swing.event.DocumentEvent?) = check()
            override fun changedUpdate(e: javax.swing.event.DocumentEvent?) = check()

            /**
             * Checks whether username field contains non-whitespace text.
             * Enables or disables Start button accordingly.
             */
            private fun check() {
                startButton.isEnabled = usernameField.text.trim().isNotBlank()
            }
        })

        // Title Section
        val title = JLabel("Falling Frenzy").apply {
            font = Font("Arial", Font.BOLD, 32)
            foreground = Color(0x2E8B57)
            alignmentX = CENTER_ALIGNMENT
        }

        add(Box.createRigidArea(Dimension(0, 30)))
        add(title)


        // Instructions Section
        val instructions = JLabel(
            "<html>Use <b>LEFT</b> and <b>RIGHT</b> arrows to catch the red balls.<br>" +
                    "Don't let them hit the bottom!</html>"
        ).apply {
            alignmentX = CENTER_ALIGNMENT
            font = Font("Arial", Font.PLAIN, 16)
            foreground = Color(0x333333)
        }

        add(Box.createRigidArea(Dimension(0, 20)))
        add(instructions)

        // Username Input Section
        val usernameLabel = JLabel("Enter your username:").apply {
            alignmentX = CENTER_ALIGNMENT
            font = Font("Arial", Font.PLAIN, 18)
        }

        add(Box.createRigidArea(Dimension(0, 25)))
        add(usernameLabel)

        usernameField.apply {
            alignmentX = CENTER_ALIGNMENT
            font = Font("Arial", Font.PLAIN, 16)
            maximumSize = Dimension(200, 30)
        }

        add(usernameField)
        add(Box.createRigidArea(Dimension(0, 5)))
        add(warningLabel)

        // Difficulty Selection Section
        val difficultyLabel = JLabel("Select difficulty:").apply {
            alignmentX = CENTER_ALIGNMENT
            font = Font("Arial", Font.PLAIN, 18)
        }

        add(Box.createRigidArea(Dimension(0, 25)))
        add(difficultyLabel)

        difficultyBox.apply {
            alignmentX = CENTER_ALIGNMENT
            font = Font("Arial", Font.PLAIN, 16)
            maximumSize = Dimension(200, 30)
        }

        add(difficultyBox)

        // Start Button Section
        add(Box.createRigidArea(Dimension(0, 35)))
        add(startButton)
    }

    /**
     * LengthWarningFilter restricts the username field to a maximum
     * number of characters.
     *
     * If the user attempts to exceed the limit:
     * - Input is rejected
     * - A warning message is displayed
     *
     * @property maxLength Maximum allowed characters
     * @property warningLabel Label used to display validation messages
     */
    private class LengthWarningFilter(
        private val maxLength: Int,
        private val warningLabel: JLabel
    ) : DocumentFilter() {

        /**
         * Called when new text is inserted into the document.
         */
        override fun insertString(
            fb: FilterBypass,
            offset: Int,
            string: String?,
            attr: AttributeSet?
        ) {
            if (string == null) return

            if (fb.document.length + string.length <= maxLength) {
                super.insertString(fb, offset, string, attr)
                warningLabel.text = ""
            } else {
                warningLabel.foreground = Color.BLACK
                warningLabel.text = "Username cannot exceed $maxLength characters"
            }
        }

        /**
         * Called when existing text is replaced.
         */
        override fun replace(
            fb: FilterBypass,
            offset: Int,
            length: Int,
            text: String?,
            attrs: AttributeSet?
        ) {
            if (text == null) return

            if (fb.document.length - length + text.length <= maxLength) {
                super.replace(fb, offset, length, text, attrs)
                warningLabel.text = ""
            } else {
                warningLabel.foreground = Color.BLACK
                warningLabel.text = "Username cannot exceed $maxLength characters"
            }
        }
    }
}
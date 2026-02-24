# Kotlin Falling Objects Game

## Introduction

The **Kotlin Falling Objects Game** is a GUI-based arcade-style game
written in Kotlin using Swing. Players enter their name, choose a
difficulty level, and attempt to catch falling objects to earn points.
The game tracks high scores using a persistent leaderboard system.

------------------------------------------------------------------------

## Features Implemented

-   Graphical User Interface (Swing)
-   Username input validation
-   Difficulty selection (Easy, Medium, Hard)
-   Real-time scoring system
-   Falling object animation
-   Game over screen with replay option
-   Persistent file-based leaderboard
-   Scores sorted in descending order

------------------------------------------------------------------------

## How to Play

1.  Launch the game.
2.  Enter your username (15 character max).
3.  Select a difficulty level.
4.  Use your keyboard controls to move and catch falling objects.
5.  Each successful catch increases your score.
6.  If you miss 3 objects, the game ends.
7.  Your final score is saved to the leaderboard if you made the top 5.

------------------------------------------------------------------------

## Leaderboard System

The game tracks and stores high scores in a text file.

Scores are stored in:

    scores/

### Design Note About the Scoreboard

The leaderboard is intentionally implemented using a simple file-based
system. This allows persistent storage without requiring a database.

Because this project is a learning-focused and fun arcade game,
security protections (such as encryption or database validation) were
intentionally not implemented.

------------------------------------------------------------------------

## Requirements

-   Java (JDK 8 or higher)
-   Kotlin Compiler installed (`kotlinc`)

Check installation with:

    java -version
    kotlinc -version

------------------------------------------------------------------------

## How to Clone and Run the Project

### 1. Clone the Repository

    git clone https://github.com/mollyoconnorr/KotlinGui.git
    cd KotlinGui

------------------------------------------------------------------------

### 2. Compile the Program

    kotlinc src/

------------------------------------------------------------------------

### 3. Run the Program

    kotlin MainKt

------------------------------------------------------------------------

## Project Structure

    KotlinGui/
    │
    ├── src/
    │   ├── Main.kt
    │   ├── StartScreen.kt
    │   ├── GamePanel.kt
    │   ├── GameOverPanel.kt
    │   ├── Difficulty.kt
    │   ├── FallingObject.kt
    │   └── Scoreboard.kt
    │
    ├── scores/
    │   ├── EASY.txt
    │   ├── MEDIUM.txt
    │   ├── HARD.txt
    │
    ├── .gitignore
    └── README.md

------------------------------------------------------------------------

## References

-   All core game logic, structure, and GUI implementation were written
    by the author.
-   ChatGPT was used for assistance with:
    -   Debugging
    -   Minor UI refinements
-   Kotlin Official Documentation: https://kotlinlang.org/docs
-   https://chatgpt.com/

------------------------------------------------------------------------

## Author

Created by: Molly O'Connor\
Course: CS-495 (Computer Science Seminar)

------------------------------------------------------------------------

Enjoy the game! 🎮

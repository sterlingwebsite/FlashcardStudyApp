package com.example.flashcardstudyapp

/**
 * Represents a single flashcard with a question, an answer,
 * and whether the user has marked it as mastered.
 */
data class Flashcard(
    val question: String,
    val answer: String,
    var mastered: Boolean = false
)
package com.example.flashcardstudyapp

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

/**
 * Holds the in-memory flashcard deck and handles saving/loading
 * it to persistent storage (SharedPreferences), so cards and
 * mastery status survive the app being closed or the device restarting.
 */
object DeckRepository {

    private const val PREFS_NAME = "flashcard_prefs"
    private const val KEY_DECK = "deck_json"

    val deck = mutableListOf<Flashcard>()

    /**
     * Adds a new flashcard to the deck and immediately persists the change.
     */
    fun addCard(context: Context, card: Flashcard) {
        deck.add(card)
        saveDeck(context)
    }

    /**
     * Updates a card's mastered flag and immediately persists the change.
     */
    fun setMastered(context: Context, card: Flashcard, mastered: Boolean) {
        card.mastered = mastered
        saveDeck(context)
    }

    /**
     * Loads the saved deck from SharedPreferences into memory.
     * If this is the first time the app has run (no saved data yet),
     * seeds the deck with sample cards and saves them immediately.
     */
    fun loadDeck(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_DECK, null)

        deck.clear()

        if (json == null) {
            deck.addAll(
                listOf(
                    Flashcard("What is the capital of France?", "Paris"),
                    Flashcard("What is 7 x 8?", "56"),
                    Flashcard("Who wrote Romeo and Juliet?", "William Shakespeare"),
                    Flashcard("What is the chemical symbol for gold?", "Au")
                )
            )
            saveDeck(context)
        } else {
            val array = JSONArray(json)
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                deck.add(
                    Flashcard(
                        question = obj.getString("question"),
                        answer = obj.getString("answer"),
                        mastered = obj.getBoolean("mastered")
                    )
                )
            }
        }
    }

    /**
     * Serializes the current deck to a JSON string and writes it
     * to SharedPreferences so it persists across app restarts.
     */
    fun saveDeck(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val array = JSONArray()
        for (card in deck) {
            val obj = JSONObject()
            obj.put("question", card.question)
            obj.put("answer", card.answer)
            obj.put("mastered", card.mastered)
            array.put(obj)
        }
        prefs.edit().putString(KEY_DECK, array.toString()).apply()
    }
}
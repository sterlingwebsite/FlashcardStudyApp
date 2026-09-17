package com.example.flashcardstudyapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.flashcardstudyapp.databinding.ActivityMainBinding

/**
 * App shell that hosts the bottom navigation bar and swaps between
 * the three main screens: Flashcard Viewer, Card Creation, and
 * Mastered Cards.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /**
     * Loads the saved deck from persistent storage, sets up the
     * bottom navigation, and shows the Flashcard Viewer by default.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DeckRepository.loadDeck(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            loadFragment(FlashcardViewerFragment())
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            val fragment: Fragment = when (item.itemId) {
                R.id.nav_viewer -> FlashcardViewerFragment()
                R.id.nav_create -> CardCreationFragment()
                R.id.nav_mastered -> MasteredCardsFragment()
                else -> FlashcardViewerFragment()
            }
            loadFragment(fragment)
            true
        }
    }

    /**
     * Replaces the currently displayed fragment in the container
     * with the given fragment.
     */
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
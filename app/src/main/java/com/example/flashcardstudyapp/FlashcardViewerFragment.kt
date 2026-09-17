package com.example.flashcardstudyapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.flashcardstudyapp.databinding.FragmentFlashcardViewerBinding

/**
 * Displays flashcards from the active (non-mastered) portion of the deck.
 * Lets the user flip between question/answer, cycle through cards,
 * and mark the current card as mastered — removing it from this rotation.
 */
class FlashcardViewerFragment : Fragment() {

    private var _binding: FragmentFlashcardViewerBinding? = null
    private val binding get() = _binding!!

    private var currentIndex = 0
    private var showingAnswer = false

    /**
     * Inflates this fragment's layout using View Binding.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFlashcardViewerBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Sets up the initial card display and wires up click listeners
     * for flipping, cycling through cards, and marking a card mastered.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateCardText()

        binding.flipButton.setOnClickListener { flipCard() }

        binding.nextButton.setOnClickListener {
            val active = activeDeck()
            if (active.isNotEmpty()) {
                currentIndex = (currentIndex + 1) % active.size
                showingAnswer = false
                updateCardText()
            }
        }

        binding.prevButton.setOnClickListener {
            val active = activeDeck()
            if (active.isNotEmpty()) {
                currentIndex = (currentIndex - 1 + active.size) % active.size
                showingAnswer = false
                updateCardText()
            }
        }

        binding.masteredButton.setOnClickListener {
            val active = activeDeck()
            if (active.isNotEmpty()) {
                val card = active[currentIndex]
                DeckRepository.setMastered(requireContext(), card, true)
                Toast.makeText(requireContext(), "Marked as mastered!", Toast.LENGTH_SHORT).show()

                // The deck just shrank by one, so re-clamp the index
                val newActive = activeDeck()
                if (currentIndex >= newActive.size) {
                    currentIndex = 0
                }
                showingAnswer = false
                updateCardText()
            }
        }
    }

    /**
     * Returns only the cards that haven't been marked mastered yet —
     * this is the active study rotation shown on this screen.
     */
    private fun activeDeck(): List<Flashcard> {
        return DeckRepository.deck.filter { !it.mastered }
    }

    /**
     * Refreshes the displayed question/answer text and button
     * availability based on the current index within the active deck.
     * Shows a friendly message and disables controls if every card
     * has been mastered.
     */
    private fun updateCardText() {
        val active = activeDeck()

        if (active.isEmpty()) {
            binding.questionText.text = "All cards mastered! Add more in the Create tab."
            binding.statusText.text = ""
            binding.flipButton.isEnabled = false
            binding.nextButton.isEnabled = false
            binding.prevButton.isEnabled = false
            binding.masteredButton.isEnabled = false
            return
        }

        binding.flipButton.isEnabled = true
        binding.nextButton.isEnabled = true
        binding.prevButton.isEnabled = true
        binding.masteredButton.isEnabled = true

        val card = active[currentIndex]
        binding.questionText.text = if (showingAnswer) card.answer else card.question
        binding.statusText.text = ""
    }

    /**
     * Animates the card flipping by shrinking it horizontally,
     * swapping the displayed text at the midpoint, then expanding it back.
     */
    private fun flipCard() {
        binding.cardView.animate()
            .scaleX(0f)
            .setDuration(150)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    showingAnswer = !showingAnswer
                    updateCardText()
                    binding.cardView.animate()
                        .scaleX(1f)
                        .setDuration(150)
                        .setListener(null)
                        .start()
                }
            })
            .start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
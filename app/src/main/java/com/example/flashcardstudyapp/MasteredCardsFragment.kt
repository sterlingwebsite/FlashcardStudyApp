package com.example.flashcardstudyapp

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.flashcardstudyapp.databinding.FragmentMasteredCardsBinding

/**
 * Displays every flashcard the user has marked as mastered.
 * Each entry shows the question and answer, with a button to send
 * the card back to the active study rotation if the user wants a refresher.
 */
class MasteredCardsFragment : Fragment() {

    private var _binding: FragmentMasteredCardsBinding? = null
    private val binding get() = _binding!!

    /**
     * Inflates this fragment's layout using View Binding.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMasteredCardsBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Builds the initial list of mastered-card views when the screen loads.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderMasteredList()
    }

    /**
     * Clears and rebuilds the list of mastered-card views from the
     * current shared deck state. Called on load and again any time
     * a card is sent back to the active rotation.
     */
    private fun renderMasteredList() {
        binding.masteredListContainer.removeAllViews()

        val mastered = DeckRepository.deck.filter { it.mastered }
        binding.emptyText.visibility = if (mastered.isEmpty()) View.VISIBLE else View.GONE

        val context = requireContext()

        for (card in mastered) {
            val cardView = CardView(context).apply {
                radius = 16f
                cardElevation = 6f
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.bottomMargin = 24
                layoutParams = params
            }

            val innerLayout = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(24, 24, 24, 24)
            }

            val questionView = TextView(context).apply {
                text = card.question
                textSize = 16f
                setTypeface(typeface, Typeface.BOLD)
            }

            val answerView = TextView(context).apply {
                text = card.answer
                textSize = 14f
                setPadding(0, 8, 0, 8)
            }

            val studyAgainButton = Button(context).apply {
                text = "Study Again"
                setOnClickListener {
                    DeckRepository.setMastered(context, card, false)
                    renderMasteredList()
                }
            }

            innerLayout.addView(questionView)
            innerLayout.addView(answerView)
            innerLayout.addView(studyAgainButton)
            cardView.addView(innerLayout)
            binding.masteredListContainer.addView(cardView)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
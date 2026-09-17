package com.example.flashcardstudyapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.flashcardstudyapp.databinding.FragmentCardCreationBinding

/**
 * Lets the user create a new flashcard by entering a question and
 * an answer. Saved cards are added to the shared deck and persisted
 * immediately via DeckRepository.
 */
class CardCreationFragment : Fragment() {

    private var _binding: FragmentCardCreationBinding? = null
    private val binding get() = _binding!!

    /**
     * Inflates this fragment's layout using View Binding.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardCreationBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Wires up the Save Card button's click listener.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            saveNewCard()
        }
    }

    /**
     * Reads the question and answer text fields, validates that
     * neither is empty, adds the new card to the deck (persisting it),
     * and clears the form for the next entry.
     */
    private fun saveNewCard() {
        val question = binding.questionInput.text.toString().trim()
        val answer = binding.answerInput.text.toString().trim()

        if (question.isEmpty() || answer.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in both fields", Toast.LENGTH_SHORT).show()
            return
        }

        val newCard = Flashcard(question = question, answer = answer)
        DeckRepository.addCard(requireContext(), newCard)

        Toast.makeText(requireContext(), "Card saved!", Toast.LENGTH_SHORT).show()

        binding.questionInput.text.clear()
        binding.answerInput.text.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
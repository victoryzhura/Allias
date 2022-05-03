package com.example.allias.ui.fragment.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.allias.ui.ActivityViewModel
import com.example.allias.databinding.GameFragmentBinding


class GameFragment : Fragment() {

    private lateinit var binding: GameFragmentBinding
    private val viewModel: GameViewModel by viewModels()
    private val viewModelWords: ActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = GameFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelWords.listOfWord.observe(viewLifecycleOwner) { listOfWords ->
            viewModel._word.value = listOfWords.random()
            viewModel.oneListOfWords.value = listOfWords
        }

        viewModel._word.observe(viewLifecycleOwner) { newWord ->
            binding.word.text = newWord
        }

        viewModel.score.observe(viewLifecycleOwner) { newScore ->
            binding.score.text = newScore.toString()
        }

        viewModel.timer.observe(viewLifecycleOwner) { newTimer ->
            binding.timer.text = newTimer.toString()
        }

        viewModel.eventTimer.observe(viewLifecycleOwner) { newEventTimer ->
            if (newEventTimer) {
                gameFinished()
                viewModel.hasFinished()
            }
        }

        binding.gotIt.setOnClickListener {
            viewModel.onCorrect()
        }

        binding.skip.setOnClickListener {
            viewModel.onSkip()
        }
    }

    private fun gameFinished() {
        val action =
            GameFragmentDirections.actionGameFragmentToFinalFragment(viewModel.score.value ?: 0)
        findNavController().navigate(action)
    }
}

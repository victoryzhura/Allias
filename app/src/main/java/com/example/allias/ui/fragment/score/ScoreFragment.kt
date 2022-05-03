package com.example.allias.ui.fragment.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.allias.data.database.OneScoreDataBase
import com.example.allias.databinding.ScoreFragmentBinding

class ScoreFragment : Fragment() {
    private lateinit var binding: ScoreFragmentBinding
    private lateinit var scoreViewModel: ScoreViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ScoreFragmentBinding.inflate(inflater)

        val application = requireActivity().application
        val dataSource = OneScoreDataBase.getInstance(application).oneScoreDataBaseDao
        val viewModelFactory = ScoreViewModelFactory(dataSource)
        scoreViewModel =
            ViewModelProvider(
                this, viewModelFactory
            )[ScoreViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ScoreAdapter()

        binding.historyScore.adapter = adapter
        binding.lifecycleOwner = this

        scoreViewModel.allScore.observe(viewLifecycleOwner) { allScore ->
            adapter.submitList(allScore)
        }

        binding.scoreViewModel = scoreViewModel
    }
}
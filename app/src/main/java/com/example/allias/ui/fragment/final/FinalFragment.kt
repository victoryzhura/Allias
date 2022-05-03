package com.example.allias.ui.fragment.final

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.allias.ui.ActivityViewModel
import com.example.allias.App
import com.example.allias.data.entity.OneScore
import com.example.allias.data.database.OneScoreDataBase
import com.example.allias.databinding.FinalFragmentBinding
import com.example.allias.ui.fragment.score.ScoreViewModel
import com.example.allias.ui.fragment.score.ScoreViewModelFactory

class FinalFragment : Fragment() {

    private lateinit var binding: FinalFragmentBinding

    private val args: FinalFragmentArgs by navArgs()

    private val activityViewModel: ActivityViewModel by activityViewModels()
    private lateinit var scoreViewModel: ScoreViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FinalFragmentBinding.inflate(inflater)

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

        val finalScore = args.scoreReceiver
        binding.finalScore.text = finalScore.toString()

        binding.finalPlayAgain.setOnClickListener {
            findNavController().navigate(FinalFragmentDirections.actionFinalFragmentToGameFragment())
        }
        binding.finalBookmark.setOnClickListener {
            findNavController().navigate(FinalFragmentDirections.actionFinalFragmentToScoreFragment())
            activityViewModel.scoreState = true
        }

        if (activityViewModel.scoreState) {
            activityViewModel.scoreState = false
            binding.totalScore.text = App.pref.getInt("pref", 0).toString()
        } else {
            scoreViewModel.insert(OneScore(totalScore = finalScore))
            binding.totalScore.text = setTotalScore(finalScore).toString()
        }
    }

    private fun setTotalScore(finalScore: Int): Int {
        val prevScore = App.pref.getInt("pref", 0)
        val totalScore = prevScore + finalScore

        App.pref.edit().putInt("pref", totalScore).apply()
        return totalScore
    }
}
package com.example.allias.ui.fragment.title


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.allias.ui.ActivityViewModel
import com.example.allias.App
import com.example.allias.data.retrofit.WordListLang
import com.example.allias.databinding.TitleFragmentBinding
import java.util.*


class TitleFragment : Fragment() {

    private lateinit var binding: TitleFragmentBinding
    private val viewModelActivity: ActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TitleFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModelActivity
        binding.lifecycleOwner = this

        binding.titleButtonGo.setOnClickListener {
            findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToGameFragment())
        }

        val langArray =
            WordListLang.values().map { Locale.forLanguageTag(it.value).displayLanguage }

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item, langArray
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinLang.adapter = adapter

        val lastPosition = App.pref.getInt("position", 0)
        binding.spinLang.setSelection(lastPosition)

        binding.spinLang.onItemSelectedListener = viewModelActivity.itemSelectedObject
    }
}
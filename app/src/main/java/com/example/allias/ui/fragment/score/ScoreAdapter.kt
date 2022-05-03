package com.example.allias.ui.fragment.score

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.allias.R
import com.example.allias.data.entity.OneScore
import com.example.allias.databinding.ListItemScoreBinding
import com.example.allias.ui.utilily.Util.getDate


class ScoreAdapter : androidx.recyclerview.widget.ListAdapter<OneScore, ScoreAdapter.ScoreHolder>(
    ScoreCallback()
) {
    inner class ScoreHolder(private val binding: ListItemScoreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OneScore) {
            binding.oneScoreHistory = item
            binding.itemData.text = getDate(item.time, "EEE, d MMM, yyyy")
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreHolder {
        val binding: ListItemScoreBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_score,
            parent, false
        )
        return ScoreHolder(binding)
    }

    override fun onBindViewHolder(holder: ScoreHolder, position: Int) {
        holder.bind(currentList[position])
    }
}

class ScoreCallback : DiffUtil.ItemCallback<OneScore>() {
    override fun areItemsTheSame(oldItem: OneScore, newItem: OneScore): Boolean {
        return oldItem.scoreId == newItem.scoreId
    }

    override fun areContentsTheSame(oldItem: OneScore, newItem: OneScore): Boolean {
        return oldItem == newItem
    }
}
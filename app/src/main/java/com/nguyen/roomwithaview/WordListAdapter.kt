package com.nguyen.roomwithaview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nguyen.roomwithaview.databinding.RecyclerviewItemBinding

class WordListAdapter: ListAdapter<Word, WordListAdapter.ViewHolder>(Comparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item.word)
    }

    class ViewHolder(val binding: RecyclerviewItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(text: String?) {
            binding.textView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = RecyclerviewItemBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class Comparator: DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word) =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: Word, newItem: Word) =
            oldItem.word == newItem.word
    }
}
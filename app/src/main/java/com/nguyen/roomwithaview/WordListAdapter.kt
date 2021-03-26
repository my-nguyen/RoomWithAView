package com.nguyen.roomwithaview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class WordListAdapter: ListAdapter<Word, WordListAdapter.ViewHolder>(Comparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item.word)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val wordItemView = itemView.findViewById<TextView>(R.id.textView)

        fun bind(text: String?) {
            wordItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.recyclerview_item, parent, false)
                return ViewHolder(view)
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
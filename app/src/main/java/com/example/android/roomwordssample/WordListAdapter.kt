/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.roomwordssample

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.roomwordssample.WordListAdapter.WordViewHolder


class WordListAdapter (var c: Context
    //var optionsMenuClickListener: OptionsMenuClickListener
) : ListAdapter<Word, WordViewHolder>(WORDS_COMPARATOR) {


   //interface OptionsMenuClickListener {
        //fun onOptionsMenuClicked(v: View)
   //}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent)
    }
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)

        holder.bind(current.word, c)
        //holder.wordItemView.setOnClickListener {
            //optionsMenuClickListener.onOptionsMenuClicked(holder.wordItemView)
        //}
    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String?, c: Context) {
            wordItemView.text = text
            wordItemView.setOnClickListener {
                performOptionsMenuClick(wordItemView, c)
            }
        }
        companion object {
            fun create(parent: ViewGroup): WordViewHolder {

                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return WordViewHolder(view)
            }
        }

        fun performOptionsMenuClick(v: View, c: Context) {
            val popupMenu = PopupMenu(c, v)

            popupMenu.setOnMenuItemClickListener { item ->
                when (item?.itemId) {
                    R.id.item1 -> {   // " at position " + getAdapterPosition()

                        // here are the logic to delete an item from the list
                        Toast.makeText(c, "Item at position " + adapterPosition + ", option 1 clicked", Toast.LENGTH_SHORT).show()
                        //val tempWord = p.adapter.getItem(p2)
                        //wordViewModel.allWords.delete
                        //p.adapter.getItem('p2')
                        //adapter.notifyDataSetChanged()
                        return@setOnMenuItemClickListener true
                    }
                    // in the same way you can implement others
                    R.id.item2 -> {
                        // define
                        Toast.makeText(c, "Item " + adapterPosition + ", option 2 clicked", Toast.LENGTH_SHORT).show()
                        return@setOnMenuItemClickListener true
                    }
                    R.id.item3 -> {
                        // define
                        Toast.makeText(c, "Item " + adapterPosition + ", option 3 clicked", Toast.LENGTH_SHORT).show()
                        return@setOnMenuItemClickListener true
                    }
                }
                return@setOnMenuItemClickListener false
            }
            popupMenu.inflate(R.menu.options_menu)
            popupMenu.show()
        }
    }

    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<Word>() {
            override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
                return oldItem.word == newItem.word
            }
        }
    }
}

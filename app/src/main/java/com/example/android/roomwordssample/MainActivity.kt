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

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.get
//import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val newWordActivityRequestCode = 1
    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((application as WordsApplication).repository)
    }



    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerview)


        val adapter = WordListAdapter(this)
           /* object: WordListAdapter.OptionsMenuClickListener  {
                 override fun onOptionsMenuClicked(v: View) {
                     // this method will handle the onclick options click
                     // it is defined below
                     performOptionsMenuClick(v)
                 }
        })
        */


        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewWordActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }

        
        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        wordViewModel.allWords.observe(this) { words ->
            // Update the cached copy of the words in the adapter.
            words.let { adapter.submitList(it) }
        }
    }

/*
        fun performOptionsMenuClick(v: View) {
            val popupMenu = PopupMenu(this, recyclerView)

            popupMenu.setOnMenuItemClickListener { item ->
             when (item?.itemId) {
                R.id.item1 -> {   // " at position " + getAdapterPosition()
                    
                    // here are the logic to delete an item from the list
                    Toast.makeText(this@MainActivity, "Item at position " + v.id + ", option 1 clicked", Toast.LENGTH_SHORT).show()
                    //val tempWord = p.adapter.getItem(p2)
                    //wordViewModel.allWords.delete
                    //p.adapter.getItem('p2')
                    //adapter.notifyDataSetChanged()
                    return@setOnMenuItemClickListener true
                }
                // in the same way you can implement others
                R.id.item2 -> {
                    // define
                    Toast.makeText(this@MainActivity, "Item " + v.id + ", option 2 clicked", Toast.LENGTH_SHORT).show()
                    return@setOnMenuItemClickListener true
                }
                R.id.item3 -> {
                    // define
                    Toast.makeText(this@MainActivity, "Item " + v.id + ", option 3 clicked", Toast.LENGTH_SHORT).show()
                    return@setOnMenuItemClickListener true
                }
            }
                return@setOnMenuItemClickListener false
        }
        popupMenu.inflate(R.menu.options_menu)
        popupMenu.show()
    }

 */


    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let { reply ->
                val word = Word(reply)
                wordViewModel.insert(word)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

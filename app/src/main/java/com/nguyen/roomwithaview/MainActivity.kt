package com.nguyen.roomwithaview

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nguyen.roomwithaview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // use the viewModels delegate, passing in an instance of our WordViewModelFactory, which is
    // constructed with the repository from WordApplication
    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((application as WordApplication).repository)
    }

    private val newWordActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val wordListAdapter = WordListAdapter()
        binding.apply {
            recyclerview.apply {
                adapter = wordListAdapter
                layoutManager = LinearLayoutManager(this@MainActivity)
            }

            fab.setOnClickListener {
                val intent = Intent(this@MainActivity, WordActivity::class.java)
                startActivityForResult(intent, newWordActivityRequestCode)
            }
        }

        wordViewModel.allWords.observe(this, { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { wordListAdapter.submitList(it) }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(WordActivity.EXTRA_REPLY)?.let {
                val word = Word(it)
                wordViewModel.insert(word)
            }
        } else {
            Toast.makeText(applicationContext, R.string.empty_not_saved, Toast.LENGTH_LONG).show()
        }
    }
}
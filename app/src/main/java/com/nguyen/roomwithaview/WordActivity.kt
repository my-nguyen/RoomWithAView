package com.nguyen.roomwithaview

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.nguyen.roomwithaview.databinding.ActivityWordBinding

class WordActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            buttonSave.setOnClickListener {
                val replyIntent = Intent()
                if (TextUtils.isEmpty(editWord.text)) {
                    setResult(Activity.RESULT_CANCELED, replyIntent)
                } else {
                    val word = editWord.text.toString()
                    replyIntent.putExtra(EXTRA_REPLY, word)
                    setResult(Activity.RESULT_OK, replyIntent)
                }
                finish()
            }
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}
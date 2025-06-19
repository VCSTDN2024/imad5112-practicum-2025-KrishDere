package com.example.musicplaylistmanagerapp

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Rating
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.exitProcess

class MusicPlaylist : AppCompatActivity() {

    private lateinit var item: ArrayList<String>
    private lateinit var category: ArrayList<String>
    private lateinit var Rating: ArrayList<Int>
    private lateinit var comments: ArrayList<String>
    private lateinit var displayTextView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_music_playlist)

        item = intent.getStringArrayListExtra("item") ?: arrayListOf()
        category = intent.getStringArrayListExtra("category") ?: arrayListOf()
        Rating = intent.getIntegerArrayListExtra("quantity") ?: arrayListOf()
        comments = intent.getStringArrayListExtra("comments") ?: arrayListOf()
        displayTextView = findViewById(R.id.displayTextView)

        val displayAllButton: Button = findViewById(R.id.displayAllButton)
        val displayQuantityButton: Button = findViewById(R.id.displayratingButton)
        val backButton: Button = findViewById(R.id.backButton)
        val exitButton: Button = findViewById(R.id.exitButton)

        displayAllButton.setOnClickListener {
            displayPackingList()
        }

        displayQuantityButton.setOnClickListener {
            displayItemsWithQuantity()
        }

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        exitButton.setOnClickListener {
            finishAffinity()
            exitProcess(0)
        }
    }

    private fun displayPackingList() {
        val stringBuilder = StringBuilder()
        if (item.isNotEmpty()) {
            for (i in item.indices) {
                stringBuilder.append("Song Title: ${item[i]}\n")
                stringBuilder.append("Artist Name: ${category[i]}\n")
                stringBuilder.append("Rating: ${Rating[i]}\n")
                stringBuilder.append("Comments: ${comments[i]}\n\n")
            }
            displayTextView.text = stringBuilder.toString()
        } else {
            displayTextView.text = "Packing list is empty."
        }
    }

    private fun displayItemsWithQuantity() {
        val stringBuilder = StringBuilder()
        var found = false
        for (i in item.indices) {
            if (Rating[i] >= 2) {
                stringBuilder.append("Item: ${item[i]} (Quantity: ${Rating[i]})\n")
                found = true
            }
        }
        if (found) {
            displayTextView.text = stringBuilder.toString()
        } else {
            displayTextView.text = "No items with a rating of 2 or more."
        }
    }
}

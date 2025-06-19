//ST10490556
//Krish Nikhil Dere
package com.example.musicplaylistmanagerapp


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private val item = mutableListOf<String>()
    private val category = mutableListOf<String>()
    private val quantity = mutableListOf<Int>()
    private val comments = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val addButton: Button = findViewById(R.id.addButton)
        val viewButton: Button = findViewById(R.id.viewButton)
        val exitButton: Button = findViewById(R.id.exitButton)

        addButton.setOnClickListener {
            showAddItemDialog()
        }

        viewButton.setOnClickListener {
            if (item.isNotEmpty()) {
                val intent = Intent(this, MusicPlaylist::class.java)
                intent.putStringArrayListExtra("item", ArrayList(item))
                intent.putStringArrayListExtra("category", ArrayList(category))
                intent.putIntegerArrayListExtra("quantity", ArrayList(quantity))
                intent.putStringArrayListExtra("comments", ArrayList(comments))
                startActivity(intent)
            } else {
                Snackbar.make(viewButton, "music playlist is empty. Add Songs first.", Snackbar.LENGTH_SHORT).show()
            }
        }

        exitButton.setOnClickListener {
            finishAffinity()
            exitProcess(0)
        }
    }

    private fun showAddItemDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add New song")

        val view = layoutInflater.inflate(R.layout.detailedview, null)
        val itemNameEditText: EditText = view.findViewById(R.id.songtitleEditText)
        val categoryEditText: EditText = view.findViewById(R.id.artistnameEditText)
        val quantityEditText: EditText = view.findViewById(R.id.ratingEditText)
        val commentsEditText: EditText = view.findViewById(R.id.commentsEditText)

        builder.setView(view)

        builder.setPositiveButton("Add") { dialog, _ ->
            val itemName = itemNameEditText.text.toString().trim()
            val category = categoryEditText.text.toString().trim()
            val quantityStr = quantityEditText.text.toString().trim()
            val comments = commentsEditText.text.toString().trim()

            if (itemName.isEmpty() || category.isEmpty() || quantityStr.isEmpty()) {
                Snackbar.make(findViewById(android.R.id.content), "Song Title, Artist Name , and Rating cannot be empty.", Snackbar.LENGTH_SHORT).show()
                return@setPositiveButton
            }

            val quantity = quantityStr.toIntOrNull()
            if (quantity == null || quantity <= 0) {
                Snackbar.make(findViewById(android.R.id.content), "Invalid Rating. Please enter a number greater than zero.", Snackbar.LENGTH_SHORT).show()
                return@setPositiveButton
            }

            item.add(itemName)
            this.category.add(category)
            this.quantity.add(quantity)
            this.comments.add(comments)

            Snackbar.make(findViewById(android.R.id.content), "$itemName added to the songs playlist.", Snackbar.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }
}
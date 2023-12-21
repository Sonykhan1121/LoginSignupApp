package com.example.loginsignup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.loginsignup.databinding.ActivityAddNoteBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddNoteActivity : AppCompatActivity() {
    private val binding: ActivityAddNoteBinding by lazy {
        ActivityAddNoteBinding.inflate(layoutInflater)
    }
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Initialize Firebase

        databaseReference = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        binding.createBtn.setOnClickListener {
            val title = binding.editTextText.text.toString()
            val description = binding.editTextText2.text.toString()

            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please fill this properly", Toast.LENGTH_SHORT).show()
            } else {
                val currentUser = auth.currentUser
                if(currentUser==null)
                {
                    Toast.makeText(this, "currentUser not found", Toast.LENGTH_SHORT).show()
                }
                currentUser?.let { user ->
                    val notekey = databaseReference.child("users").child(user.uid).child("notes").push().key
                    val noteItem = NoteItem(title, description)

                    if (notekey != null) {
                        databaseReference.child("users").child(user.uid).child("notes").child(notekey)
                            .setValue(noteItem)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(this, "Note saved successfully", Toast.LENGTH_SHORT).show()
                                    finish()
                                } else {
                                    Toast.makeText(this, "Failed to save note.", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                }
            }
        }
    }
}

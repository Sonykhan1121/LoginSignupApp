package com.example.loginsignup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.loginsignup.databinding.ActivityMainBinding
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener{
            AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    Toast.makeText(this, "SignOut Successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,SignInActivity::class.java))
                    finish()
                }

        }
        binding.createNewNoteBtn.setOnClickListener {
            startActivity(Intent(this,AddNoteActivity::class.java))

        }
    }
}
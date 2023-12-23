package com.example.loginsignup

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.loginsignup.databinding.ActivityMainBinding
import com.firebase.ui.auth.AuthUI
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.storage

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
        binding.openallnoteBtn.setOnClickListener {
            startActivity(Intent(this,OpenallnotesActivity::class.java))
            finish()

        }
        binding.photoBtn.setOnClickListener {
            startActivity(Intent(this,ImageViewActivity::class.java))

        }
        binding.videoViewerBtn.setOnClickListener {
            startActivity(Intent(this,VideoViewActivity::class.java))
        }


    }

}
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
import com.example.loginsignup.databinding.ActivityImageViewBinding
import com.google.firebase.Firebase
import com.google.firebase.storage.storage

class ImageViewActivity : AppCompatActivity() {
    private val binding:ActivityImageViewBinding by lazy {
        ActivityImageViewBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.choosePictureId.setOnClickListener {

            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type ="image/*"
            imagelauncher.launch(intent)
        }
    }
    val imagelauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode== Activity.RESULT_OK)
        {
            if(it.data!=null)
            {
                val timeStamp = System.currentTimeMillis()
                val imageName = "image_$timeStamp.jpg"
                val ref = Firebase.storage.reference.child("photo").child(imageName)

                ref.putFile(it.data!!.data!!).addOnCompleteListener {
                    ref.downloadUrl.addOnSuccessListener { uri ->
                        // Use Glide to load the image into the ImageView
                        Glide.with(this@ImageViewActivity)
                            .load(uri)
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                            .into(binding.choosingPicId)
                    }
                }

            }
            else{
                Toast.makeText(this, "data null", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
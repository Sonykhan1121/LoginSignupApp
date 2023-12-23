package com.example.loginsignup

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.example.loginsignup.databinding.ActivityVideoViewBinding

class VideoViewActivity : AppCompatActivity() {
    private val binding:ActivityVideoViewBinding by lazy {
        ActivityVideoViewBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.videoViewId.isVisible = false
        binding.pickVideoBtn.setOnClickListener {

            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "video/*"
            videoLauncher.launch(intent)

        }

    }
    val videoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
        if(it.resultCode== Activity.RESULT_OK)
        {
            if(it.data!=null)
            {
                binding.pickVideoBtn.isVisible = false
                binding.videoViewId.isVisible = true
                val mediaController = MediaController(this)
                mediaController.setAnchorView(binding.videoViewId)


                binding.videoViewId.setVideoURI(it.data!!.data)
                binding.videoViewId.setMediaController(mediaController)
                binding.videoViewId.start()

            }
        }
    }
}
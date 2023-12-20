package com.example.loginsignup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.loginsignup.databinding.ActivityCreateIdBinding
import com.google.firebase.auth.FirebaseAuth
import render.animations.Attention
import render.animations.Render

class CreateIdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateIdBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateIdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        binding.loginBtnIdCreate.setOnClickListener {
            intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.createBtnIdCreate.setOnClickListener {



            
            val email = binding.emailIdSignin.text.toString()
            val name = binding.nameIdCreate.text.toString()
            val pass = binding.passIdCreate.text.toString()
            val re_pas = binding.rePassIdCreate.text.toString()
            if(email.isEmpty())
            {
                shake(binding.emailIdSignin)
                Toast.makeText(this, "Please Enter Your email first!", Toast.LENGTH_SHORT).show()
            }
            else if(name.isEmpty())
            {
                shake(binding.nameIdCreate)
                Toast.makeText(this, "Please Enter Your Name!", Toast.LENGTH_SHORT).show()
            }
            else if(pass.isEmpty())
            {
                shake(binding.passIdCreate)
                Toast.makeText(this, "Please Enter Your password", Toast.LENGTH_SHORT).show()
            }
            else if(re_pas.isEmpty())
            {
                shake(binding.rePassIdCreate)
                Toast.makeText(this, "Please Re-Enter your password", Toast.LENGTH_SHORT).show()

            }
            else if(pass!=re_pas){
                shake(binding.passIdCreate)
                shake(binding.rePassIdCreate)
                Toast.makeText(this, "password and re-password should be same", Toast.LENGTH_SHORT).show()
            }
            else
            {
                auth.createUserWithEmailAndPassword(email,pass)
                    .addOnCompleteListener(this){ task->
                        if(task.isSuccessful)
                        {
                            Toast.makeText(this, "Registration Successfull", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this,SignInActivity::class.java))
                            finish()


                        }
                        else{
                            Toast.makeText(this, "Registration failed ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }

        }


    }
    fun shake(view: View)
    {
        val render = Render(this)
        render.setAnimation(Attention().Bounce(view))
        render.start()
    }
}
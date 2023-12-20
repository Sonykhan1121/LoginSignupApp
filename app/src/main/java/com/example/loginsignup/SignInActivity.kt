package com.example.loginsignup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.loginsignup.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import render.animations.Attention
import render.animations.Render

class SignInActivity : AppCompatActivity() {
    private val binding:ActivitySignInBinding by lazy {
        ActivitySignInBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    override fun onStart() {
        super.onStart()
        val currentUser:FirebaseUser? = auth.currentUser
        if(currentUser!=null)
        {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Initialization
        auth = FirebaseAuth.getInstance()
        setContentView(binding.root)


        binding.createIdLogin.setOnClickListener{
            val intent = Intent(this,CreateIdActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.loginIdLogin.setOnClickListener {
            val email = binding.emailIdSignin.text.toString()
            val password = binding.passIdSignin.text.toString()
            if(email.isEmpty())
            {
                    shake(binding.emailIdSignin)

            }
            else if(password.isEmpty())
            {
                shake(binding.passIdSignin)

            }
            else{
                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener{task ->
                        if(task.isSuccessful)
                        {
                            Toast.makeText(this, "Sign In successfull", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this,MainActivity::class.java))
                            finish()

                        }
                        else{
                            Toast.makeText(this, "Sign is failed due to ${task.exception?.message}", Toast.LENGTH_SHORT).show()

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

package com.example.loginsignup

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.loginsignup.databinding.ActivitySignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import render.animations.Attention
import render.animations.Render

class SignInActivity : AppCompatActivity() {
    private val binding:ActivitySignInBinding by lazy {
        ActivitySignInBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
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
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(
            com.firebase.ui.auth.R.string.default_web_client_id)).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)

        binding.signInByGoogle.setOnClickListener {
            val signInClient = googleSignInClient.signInIntent
           launcher.launch(signInClient)
        }
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
    private val launcher =registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
        result->
        if(result.resultCode== Activity.RESULT_OK)
        {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if(task.isSuccessful)
            {
                val account:GoogleSignInAccount?=task.result
                val credential = GoogleAuthProvider.getCredential(account?.idToken,null)
                auth.signInWithCredential(credential).addOnCompleteListener {
                    if(it.isSuccessful)
                    {
                        Toast.makeText(this , "SignIn succesfull", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()

                    }
                    else{
                        Toast.makeText(this, "SignIn Not succesfull", Toast.LENGTH_SHORT).show()
                    }
                }

            }
            else
            {
                Toast.makeText(this, "Task is not successfull", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(this, "Failed result not ok" , Toast.LENGTH_SHORT).show()
        }
    }
}

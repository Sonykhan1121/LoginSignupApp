package com.example.loginsignup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginsignup.databinding.ActivityOpenallnotesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OpenallnotesActivity : AppCompatActivity() {
    private val binding:ActivityOpenallnotesBinding by lazy {
        ActivityOpenallnotesBinding.inflate(layoutInflater)
    }
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        recyclerView = binding.noteRecycleview
        recyclerView.layoutManager  = LinearLayoutManager(this)
        databaseReference = FirebaseDatabase.getInstance().reference
        auth  = FirebaseAuth.getInstance()


        val currenntUser:FirebaseUser?   = auth.currentUser
        currenntUser?.let { user->
            val noteReference = databaseReference.child("users").child(user.uid).child("notes")
            noteReference.addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val notelist = mutableListOf<NoteItem>()
                    for (noteSnapshot in snapshot.children)
                    {
                        val note = noteSnapshot.getValue(NoteItem::class.java)
                        note?.let {
                            notelist.add(it)
                        }
                    }
                    val adapter = NoteAdapter(notelist)
                    recyclerView.adapter = adapter
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
}
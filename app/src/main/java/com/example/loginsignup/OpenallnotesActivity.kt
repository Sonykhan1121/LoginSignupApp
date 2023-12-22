package com.example.loginsignup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginsignup.databinding.ActivityOpenallnotesBinding
import com.example.loginsignup.databinding.DialogUpdateNoteBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OpenallnotesActivity : AppCompatActivity(),NoteAdapter.OnItemClickListener {
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
                    val adapter = NoteAdapter(notelist,this@OpenallnotesActivity)
                    recyclerView.adapter = adapter
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }

    override fun onDeleteClick(noteId: String) {
        val currentUser = auth.currentUser
        currentUser?.let { user->
            val noteReference = databaseReference.child("users").child(user.uid).child("notes")
                noteReference.child(noteId).removeValue()
        }
    }

    override fun onUpadateClick(noteId: String,newtitle:String,newDescription:String) {
        val dialogBinding:DialogUpdateNoteBinding = DialogUpdateNoteBinding.inflate(LayoutInflater.from(this))
        val dialog = AlertDialog.Builder(this).setView(dialogBinding.root)
            .setTitle("Update Notes")
            .setPositiveButton("update"){dialog, _ ->
                val newtitle = dialogBinding.updateNoteTitle.text.toString()
                val newDescription = dialogBinding.updateDesTitle.text.toString()
                UpdateNoteDatabase(noteId,newtitle,newDescription)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel"){ dialog,_ ->
                dialog.dismiss()
            }
            .create()
        dialogBinding.updateNoteTitle.setText(newtitle)
        dialogBinding.updateDesTitle.setText(newDescription)

        dialog.show()




    }

    private fun UpdateNoteDatabase(noteId: String, newtitle: String, newDescription: String) {
        val currentUser = auth.currentUser
        currentUser?.let { user->
            val noteReference = databaseReference.child("users").child(user.uid).child("notes")
            val updateNote = NoteItem(newtitle,newDescription, noteId)
            noteReference.child(noteId).setValue(updateNote)
                .addOnCompleteListener { task->
                    if(task.isSuccessful)
                    {
                        Toast.makeText(this, "Note updated successfully", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this, "Failed to update", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
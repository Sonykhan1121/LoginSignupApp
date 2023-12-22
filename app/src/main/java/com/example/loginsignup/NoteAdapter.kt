package com.example.loginsignup

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.example.loginsignup.databinding.NoteitemsBinding

class NoteAdapter(private val notes:List<NoteItem>, private val itemClickListener: OnItemClickListener):
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    interface OnItemClickListener{
        fun onDeleteClick(noteId:String)
        fun onUpadateClick(noteId:String,title:String,Description:String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteitemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
       val note  = notes[position]
        holder.bind(note)
        holder.binding.updateBtn.setOnClickListener {
            itemClickListener.onUpadateClick(note.noteId,note.title,note.description)
        }
        holder.binding.deleteBtn.setOnClickListener {
            itemClickListener.onDeleteClick(note.noteId)
        }
    }
    class NoteViewHolder(val binding: NoteitemsBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(note: NoteItem) {
            binding.titleNiId.text = note.title
            binding.descriptionNiId.text = note.description
        }

    }
}
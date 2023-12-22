package com.example.loginsignup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginsignup.databinding.NoteitemsBinding

class NoteAdapter(private val notes:List<NoteItem>):
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {


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
    }
    class NoteViewHolder(private val binding: NoteitemsBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(note: NoteItem) {
            binding.titleNiId.text = note.title
            binding.descriptionNiId.text = note.description
        }

    }
}
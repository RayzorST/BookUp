package com.example.bookup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import database.Book
import io.github.jan.supabase.postgrest.postgrest

class BookAdapter (private var bookList: List<Book>) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookAdapter.BookViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
            return BookAdapter.BookViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: BookAdapter.BookViewHolder, position: Int) {
            val book = bookList[position]
            holder.title.text = book.title

            // below line is use to add on click listener for our item of recycler view.
            holder.itemView.setOnClickListener {

            }
        }

        override fun getItemCount(): Int {
            return bookList.size
        }

        class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title: TextView = itemView.findViewById(R.id.title)
            val tags: TextView = itemView.findViewById(R.id.tags)
            val image: ImageView = itemView.findViewById(R.id.image)
        }
}
package com.example.bookup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import database.Book

class BookAdapter (private var bookList: List<Book>) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookAdapter.BookViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
            return BookAdapter.BookViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: BookAdapter.BookViewHolder, position: Int) {
            val book = bookList[position]
            holder.title.text = book.title
            val bundle = Bundle()
            bundle.putSerializable("book", book)

            holder.itemView.setOnClickListener {
                val readingFragment = ReadingFragment.newInstance()
                readingFragment.arguments = bundle
                val fragmentManager = (it.context as MainActivity).supportFragmentManager
                fragmentManager.beginTransaction()
                    .replace(R.id.fragment_holder, readingFragment)
                    .commit()
            }

            holder.addFavorite.setOnCheckedChangeListener { _, isChecked ->
                //val bookDao = localstore.bookDao()
                if (isChecked){
                //    bookDao.insert(Book(book.id, book.title, book.description))
                }
                else{
                //    bookDao.delete(Book(book.id))
                }
            }

        }

        override fun getItemCount(): Int {
            return bookList.size
        }

        class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title: TextView = itemView.findViewById(R.id.title)
            val tags: TextView = itemView.findViewById(R.id.tags)
            val image: ImageView = itemView.findViewById(R.id.image)
            val addFavorite: ToggleButton = itemView.findViewById(R.id.addFavorite)
        }
}
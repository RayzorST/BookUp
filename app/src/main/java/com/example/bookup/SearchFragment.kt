package com.example.bookup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bookup.databinding.FragmentSearchBinding
import database.Book
import database.BookTags
import database.Tags
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            val books = supabase.postgrest["Books"].select {
                limit(10)

            }.decodeList<Book>()

            val bookDao = localstore.bookDao()
            val bookRep = room.repository.Book(bookDao)
            val favbooks = bookRep.getAll().first()
            for(book in books){
                if (favbooks.find { it.id == book.id } == null){
                    book.isFavorite = false
                }
            }

            val bookTags = supabase.postgrest["Book_Tags"].select {
                filter {
                    isIn("book_id", books.map{ it.id })
                }
            }.decodeList<BookTags>()

            val tags = supabase.postgrest["Tags"].select{}.decodeList<Tags>()

            binding.booksList.layoutManager = GridLayoutManager(null, 2)
            binding.booksList.adapter = BookAdapter(books, bookTags, tags)

            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    binding.searchView.clearFocus()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}
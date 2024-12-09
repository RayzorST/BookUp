package com.example.bookup

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
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
            var books: List<Book>? = null
            var bookTags: List<BookTags>? = null
            var tags: List<Tags>? = null
            if (isOnline(this@SearchFragment.context)){
                books = supabase.postgrest["Books"].select {
                    limit(14)

                }.decodeList<Book>()

                val bookDao = localstore.bookDao()
                val bookRep = room.repository.Book(bookDao)
                val favbooks = bookRep.getAll().first()
                for(book in books){
                    if (favbooks.find { it.id == book.id } == null){
                        book.isFavorite = false
                    }
                }

                bookTags = supabase.postgrest["Book_Tags"].select {
                    filter {
                        isIn("book_id", books.map{ it.id })
                    }
                }.decodeList<BookTags>()

                tags = supabase.postgrest["Tags"].select{}.decodeList<Tags>()
            }
            else{
                Toast.makeText(this@SearchFragment.context, "Нет подключения к интернету", Toast.LENGTH_SHORT).show()
            }


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

    fun isOnline(context: Context?): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}
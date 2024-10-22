package com.example.bookup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bookup.databinding.FragmentFavoriteBinding
import database.Book
import database.BookTags
import database.Tags
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class FavoriteFragment : Fragment() {

    lateinit var binding: FragmentFavoriteBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        val bookDao = localstore.bookDao()
        val bookRep = room.repository.Book(bookDao)

        lifecycleScope.launch{
            binding.booksList.layoutManager = GridLayoutManager(null, 2)
            binding.booksList.adapter = BookAdapter(bookRep.getAll().first(), listOf(BookTags(0, 0, 0)), listOf(Tags(0, "safasf")))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavoriteFragment()
    }
}
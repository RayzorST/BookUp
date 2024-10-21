package com.example.bookup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bookup.databinding.FragmentFavoriteBinding
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class FavoriteFragment : Fragment() {

    lateinit var binding: FragmentFavoriteBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        thread {
            val books = localstore.bookDao().getAll()

            binding.booksList.layoutManager = GridLayoutManager(null, 2)
            //binding.booksList.adapter = BookAdapter(books)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavoriteFragment()
    }
}
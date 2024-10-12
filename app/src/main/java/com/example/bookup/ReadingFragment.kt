package com.example.bookup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.room.util.query
import com.example.bookup.databinding.FragmentReadingBinding
import com.example.bookup.databinding.FragmentSearchBinding
import database.Book
import database.Page
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch


class ReadingFragment : Fragment() {

    lateinit var binding: FragmentReadingBinding
    lateinit var book: Book
    lateinit var pageList: List<Page>
    lateinit var page: Page

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentReadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        book = arguments?.getSerializable("book") as Book
        lifecycleScope.launch{
            pageList = supabase.postgrest["Pages"].select { filter {
                eq("book", book.id)
                isIn("page", listOf(1, 2))
            } }.decodeList<Page>()
            page = pageList[0]

            binding.apply {
                buttonOnReadNext.setOnClickListener { nextPage() }
                buttonOnReadBack.setOnClickListener { backPage() }
                if(pageList.getOrNull(1) == null){
                    buttonOnReadNext.visibility = View.GONE
                }
                buttonOnReadBack.visibility = View.GONE

                titleOfTheBook.text = page.page.toString()
                readingTextPage.text = page.text.toString()
            }
        }
    }

     fun nextPage(){
        lifecycleScope.launch{
            pageList = supabase.postgrest["Pages"].select { filter {
                eq("book", book.id)
                isIn("page", listOf(page.page, page.page + 1, page.page + 2) )
            } }.decodeList<Page>()
            var index = pageList.indexOf(page)
            page = pageList[index + 1]
            index += 1

            binding.apply {
                titleOfTheBook.text = page.page.toString()
                readingTextPage.text = page.text.toString()

                if(pageList.getOrNull(index + 1) == null)
                    buttonOnReadNext.visibility = View.GONE
                else
                    buttonOnReadNext.visibility = View.VISIBLE
                if(pageList.getOrNull(index - 1) == null)
                    buttonOnReadBack.visibility = View.GONE
                else
                    buttonOnReadBack.visibility = View.VISIBLE

            }
        }
    }

    fun backPage(){
        lifecycleScope.launch{
            pageList = supabase.postgrest["Pages"].select { filter {
                eq("book", book.id)
                isIn("page", listOf(page.page, page.page - 1, page.page - 2))
            } }.decodeList<Page>()
            var index = pageList.indexOf(page)
            page = pageList[index - 1]
            index -= 1

            binding.apply {
                titleOfTheBook.text = page.page.toString()
                readingTextPage.text = page.text.toString()

                if(pageList.getOrNull(index + 1) == null)
                    buttonOnReadNext.visibility = View.GONE
                else
                    buttonOnReadNext.visibility = View.VISIBLE
                if(pageList.getOrNull(index - 1) == null)
                    buttonOnReadBack.visibility = View.GONE
                else
                    buttonOnReadBack.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ReadingFragment()
    }
}
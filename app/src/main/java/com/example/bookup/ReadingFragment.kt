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
import androidx.lifecycle.lifecycleScope
import androidx.room.util.query
import com.example.bookup.databinding.FragmentReadingBinding
import com.example.bookup.databinding.FragmentSearchBinding
import database.Book
import database.Page
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.first
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
        val pageDao = localstore.pageDao()
        val pageRep = room.repository.Page(pageDao)
        lifecycleScope.launch{
            if (isOnline(this@ReadingFragment.context)){
                pageList = supabase.postgrest["Pages"].select { filter {
                    eq("book", book.id)
                } }.decodeList<Page>()
                page = pageList[0]

                for (page in pageList){
                    if (pageRep.getPage(page.id).first() == null)
                        pageRep.addPage(Page(page.id, book.id, page.page, page.text))
                }
            }
            else{
                pageList = pageRep.getAllbyBook(book.id).first()
                page = pageList[0]
            }

            binding.apply {
                buttonOnReadNext.setOnClickListener { nextPage() }
                buttonOnReadBack.setOnClickListener { backPage() }
                if(pageList.getOrNull(1) != null){
                    buttonOnReadNext.visibility = View.VISIBLE
                }
                //buttonOnReadBack.visibility = View.GONE

                titleOfTheBook.text = page.page.toString()
                readingTextPage.text = page.text.toString()
            }
        }
    }

     fun nextPage(){
         binding.apply {
             buttonOnReadNext.visibility = View.GONE
             buttonOnReadBack.visibility = View.GONE
         }
        lifecycleScope.launch{
            if (isOnline(this@ReadingFragment.context)){
                pageList = supabase.postgrest["Pages"].select { filter {
                    eq("book", book.id)
                    isIn("page", listOf(page.page, page.page + 1, page.page + 2) )
                } }.decodeList<Page>()
            }
            else{
                val pageDao = localstore.pageDao()
                val pageRep = room.repository.Page(pageDao)
                pageList = pageRep.getAllbyBook(book.id).first()
            }
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
        binding.apply {
            buttonOnReadNext.visibility = View.GONE
            buttonOnReadBack.visibility = View.GONE
        }
        lifecycleScope.launch{
            if (isOnline(this@ReadingFragment.context)){
                pageList = supabase.postgrest["Pages"].select { filter {
                    eq("book", book.id)
                    isIn("page", listOf(page.page, page.page - 1, page.page - 2))
                } }.decodeList<Page>()
            }
            else{
                val pageDao = localstore.pageDao()
                val pageRep = room.repository.Page(pageDao)
                pageList = pageRep.getAllbyBook(book.id).first()
            }
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
        fun newInstance() = ReadingFragment()
    }
}
package com.example.bookup

import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bookup.databinding.ActivityMainBinding
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import android.os.Bundle

public val supabase = createSupabaseClient(
    supabaseUrl = "https://pjcbtzavgyvcxluvdosy.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InBqY2J0emF2Z3l2Y3hsdXZkb3N5Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjY2NTI2NjcsImV4cCI6MjA0MjIyODY2N30.SAO_6sUFlqFchh72CPKkL1xv9y-6dUsrzVa6a8dXgFU"
) {
    //install(Auth)
    install(Postgrest)
}

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.apply {
            supportFragmentManager
                .beginTransaction()
                .replace(fragmentHolder.id, SettingsFragment.newInstance())
                .commit()

            navigationView.setNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.search -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_holder, SearchFragment.newInstance())
                            .commit()

                    }

                    R.id.favorite -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_holder, FavoriteFragment.newInstance())
                            .commit()

                    }

                    R.id.settings -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_holder, SettingsFragment.newInstance())
                            .commit()

                    }
                }
                main.closeDrawers()
                true
            }
        }
    }

    fun open_menu(view: View){
        binding.apply {
            main.openDrawer(GravityCompat.START)
        }
    }
}
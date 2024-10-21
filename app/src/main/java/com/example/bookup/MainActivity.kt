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
import androidx.fragment.app.Fragment
import androidx.room.Room.databaseBuilder
import room.AppDatabase
import room.MIGRATION_1_2

public val supabase = createSupabaseClient(
    supabaseUrl = "https://pjcbtzavgyvcxluvdosy.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InBqY2J0emF2Z3l2Y3hsdXZkb3N5Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjY2NTI2NjcsImV4cCI6MjA0MjIyODY2N30.SAO_6sUFlqFchh72CPKkL1xv9y-6dUsrzVa6a8dXgFU"
) {
    //install(Auth)
    install(Postgrest)
}
public lateinit var localstore : AppDatabase

var currentFragment: Fragment = SearchFragment.newInstance()

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

        localstore = databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "LocalStore"
        ).addMigrations(MIGRATION_1_2).build()

        binding.apply {
            pageName.text = getString(R.string.search)
            supportFragmentManager
                .beginTransaction()
                .replace(fragmentHolder.id, currentFragment)
                .commit()

            navigationView.setNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.search -> {
                        currentFragment = SearchFragment.newInstance()
                        pageName.text = getString(R.string.search)
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_holder, currentFragment)
                            .commit()
                    }

                    R.id.favorite -> {
                        currentFragment = FavoriteFragment.newInstance()
                        pageName.text = getString(R.string.favorite)
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_holder, currentFragment)
                            .commit()
                    }

                    R.id.settings -> {
                        currentFragment = SettingsFragment.newInstance()
                        pageName.text = getString(R.string.settings)
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_holder, currentFragment)
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
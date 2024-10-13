package com.example.bookup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate

class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        // Найти кнопки по их ID
        val darkThemeButton = view.findViewById<Button>(R.id.darkTheme)
        val lightThemeButton = view.findViewById<Button>(R.id.lightTheme)

        // Обработчик нажатия для Dark Theme
        darkThemeButton.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        // Обработчик нажатия для Light Theme
        lightThemeButton.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        return view
    }



    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }
}
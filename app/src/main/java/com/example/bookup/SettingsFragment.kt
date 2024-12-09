package com.example.bookup

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.example.bookup.databinding.FragmentSettingsBinding
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.launch
import com.example.bookup.MainActivity

class SettingsFragment : Fragment() {
    lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.apply {
            if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES) {
                darkModeOn.isChecked = true
            }

            darkModeOn.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    setDarkMode(this@SettingsFragment.context, 2)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    setDarkMode(this@SettingsFragment.context, 1)
                }
            }

            logOut.setOnClickListener {
                lifecycleScope.launch{
                    supabase.auth.sessionManager.deleteSession()
                    supabase.auth.signOut()
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                }
            }
        }

        return binding.root
    }

    fun setDarkMode(context: Context?, darkMode: Int) {
        val sharedPreferences = context!!.getSharedPreferences("settings", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putInt("darkMode", darkMode)
            apply()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }
}

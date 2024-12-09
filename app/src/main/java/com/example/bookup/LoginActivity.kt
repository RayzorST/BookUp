package com.example.bookup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.bookup.databinding.ActivityLoginBinding
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.exception.AuthRestException
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.apply {
            registrButton.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java))
            }

            LoginButton.setOnClickListener {
                lifecycleScope.launch {
                    try{
                        supabase.auth.signInWith(Email) {
                            email = loginInput.text.toString()
                            password = passwordInput.text.toString()
                        }
                        Log.e("sdf", supabase.auth.currentSessionOrNull().toString())
                        supabase.auth.sessionManager.saveSession(supabase.auth.currentSessionOrNull()!!)
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    }
                    catch (e: AuthRestException){
                        Toast.makeText(this@LoginActivity, "Такого пользователя нет", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun saveSession(context: Context, accessToken: String, refreshToken: String) {
        val sharedPreferences = context.getSharedPreferences("supabase_session", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("access_token", accessToken)
            putString("refresh_token", refreshToken)
            apply()
        }
    }
}

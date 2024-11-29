package com.example.bookup

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.bookup.databinding.ActivityRegistrationBinding
import io.github.jan.supabase.gotrue.OtpType
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.launch

class RegistrationActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        val toLoginButton = findViewById<Button>(R.id.ToLoginButton)
        toLoginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.apply {
            ToLoginButton.setOnClickListener {
                startActivity(Intent(this@RegistrationActivity, LoginActivity::class.java))
            }

            RegistrationButton.setOnClickListener {
                if (passwReg.text.toString().length >= 6) {
                    lifecycleScope.launch {
                        supabase.auth.signUpWith(Email) {
                            email = loginReg.text.toString()
                            password = passwReg.text.toString()
                        }
                        startActivity(Intent(this@RegistrationActivity, MainActivity::class.java))
                    }
                }
                else{
                    Toast.makeText(this@RegistrationActivity, "Пароль должен быть длинее 6 символов", Toast.LENGTH_SHORT).show()
                }
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

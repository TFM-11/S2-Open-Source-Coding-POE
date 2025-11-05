package com.example.anytimecarwash

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

class LoginActivity : AppCompatActivity() {
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var loginBtn: Button
    private lateinit var signUpRedirect: TextView
    private lateinit var forgotPasswordText: TextView
    private lateinit var rememberMe: CheckBox

    private val secretKey = "MySecretKey12345"
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginBtn = findViewById(R.id.loginBtn)
        signUpRedirect = findViewById(R.id.signUpRedirect)
        forgotPasswordText = findViewById(R.id.forgotPasswordText)
        rememberMe = findViewById(R.id.rememberMe)

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        loginBtn.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email & password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val savedEmail = sharedPreferences.getString("email", null)
            val savedPassword = sharedPreferences.getString("password", null)
            val encryptedPassword = encryptPassword(password)

            // ✅ Verify credentials
            if (email == savedEmail && encryptedPassword == savedPassword) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }

        signUpRedirect.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        forgotPasswordText.setOnClickListener {
            Toast.makeText(this, "Forgot password action", Toast.LENGTH_SHORT).show()
        }
    }

    private fun encryptPassword(password: String): String {
        return try {
            val key = SecretKeySpec(MessageDigest.getInstance("SHA-256").digest(secretKey.toByteArray(StandardCharsets.UTF_8)), "AES")
            val cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.ENCRYPT_MODE, key)
            val encryptedBytes = cipher.doFinal(password.toByteArray(StandardCharsets.UTF_8))
            Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
        } catch (e: Exception) {
            Toast.makeText(this, "Encryption failed: ${e.message}", Toast.LENGTH_SHORT).show()
            password
        }
    }
}

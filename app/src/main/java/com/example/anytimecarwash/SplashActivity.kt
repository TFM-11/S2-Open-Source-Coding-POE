package com.example.anytimecarwash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.anytimecarwash.utils.NotificationUtils
import com.google.android.material.button.MaterialButton

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Initialize Firebase notifications
        // For now, we'll use a dummy user ID. Replace with actual user ID after login
        NotificationUtils.initializeNotifications(this, "current_user")

        // Setup Get Started Button to navigate to Signup page
        findViewById<MaterialButton>(R.id.btn_get_started).setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}
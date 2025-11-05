package com.example.anytimecarwash

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.anytimecarwash.databinding.ActivityHomeBinding
import com.example.anytimecarwash.utils.NotificationUtils

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle notification intents
        handleNotificationIntent(intent)

        // Set up navigation listeners
        setupNavigation()

        // Initialize notifications with actual user ID (replace with your actual user ID)
        NotificationUtils.initializeNotifications(this, "user_123")
    }

    private fun handleNotificationIntent(intent: Intent?) {
        intent?.extras?.let { extras ->
            // Check if the app was launched from a notification
            if (extras.containsKey("notification_type")) {
                val type = extras.getString("notification_type")
                val message = extras.getString("message")

                when (type) {
                    "booking_confirmed" -> {
                        Toast.makeText(this, "Booking confirmed: $message", Toast.LENGTH_LONG).show()
                    }
                    "booking_updated" -> {
                        Toast.makeText(this, "Booking updated: $message", Toast.LENGTH_LONG).show()
                    }
                    "promotion" -> {
                        Toast.makeText(this, "Special offer: $message", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun setupNavigation() {
        binding.settingsIcon.setOnClickListener { navigateTo(SettingsActivity::class.java) }
        binding.bookingsIcon.setOnClickListener { navigateTo(BookingsActivity::class.java) }
        binding.basicIcon.setOnClickListener { navigateTo(BookingsActivity::class.java) }
        binding.homeIcon.setOnClickListener { navigateTo(HomeActivity::class.java) }
        binding.premiumIcon.setOnClickListener { navigateTo(BookingsActivity::class.java) }
        binding.fullIcon.setOnClickListener { navigateTo(BookingsActivity::class.java) }
        binding.profileIcon.setOnClickListener { navigateTo(ProfileActivity::class.java) }
    }

    private fun navigateTo(destination: Class<*>) {
        try {
            if (destination == HomeActivity::class.java) {
                // Prevent recreating the same activity unnecessarily
                return
            }
            val intent = Intent(this, destination)
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Navigation failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
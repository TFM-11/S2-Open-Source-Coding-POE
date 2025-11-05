package com.example.anytimecarwash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class AccountSettingsActivity : AppCompatActivity() {
    private lateinit var btnBack: ImageButton
    private lateinit var btnEditProfile: MaterialButton
    private lateinit var btnChangePassword: MaterialButton
    private lateinit var btnLogout: MaterialButton
    private lateinit var homeIcon: ImageView
    private lateinit var bookingsIcon: ImageView
    private lateinit var profileIcon: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_settings)

        btnBack = findViewById(R.id.btn_back)
        btnEditProfile = findViewById(R.id.btn_edit_profile)
        btnChangePassword = findViewById(R.id.btn_change_password)
        btnLogout = findViewById(R.id.btn_logout)
        homeIcon = findViewById(R.id.homeIcon)
        bookingsIcon = findViewById(R.id.bookingsIcon)
        profileIcon = findViewById(R.id.profileIcon)

        btnBack.setOnClickListener { onBackPressed() }
        btnEditProfile.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }
        btnChangePassword.setOnClickListener {
            startActivity(Intent(this, ChangePasswordActivity::class.java))
        }
        btnLogout.setOnClickListener {
            Toast.makeText(this, "Logout clicked", Toast.LENGTH_SHORT).show()
            finish() // Add actual logout logic
        }
        homeIcon.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
        bookingsIcon.setOnClickListener {
            startActivity(Intent(this, BookingsActivity::class.java))
            finish()
        }
        profileIcon.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
    }
}
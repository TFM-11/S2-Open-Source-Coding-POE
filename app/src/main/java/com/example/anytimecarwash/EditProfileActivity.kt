package com.example.anytimecarwash

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class EditProfileActivity : AppCompatActivity() {
    private lateinit var btnBack: ImageButton
    private lateinit var btnSave: MaterialButton
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhone: EditText
    private lateinit var homeIcon: ImageView
    private lateinit var bookingsIcon: ImageView
    private lateinit var profileIcon: ImageView

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var sharedPreferences: SharedPreferences
    private var currentUserEmail: String = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        // Initialize database helper and shared preferences
        dbHelper = DatabaseHelper(this)
        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)

        // Get current user email from shared preferences or intent
        currentUserEmail = sharedPreferences.getString("user_email", "") ?: ""
        if (currentUserEmail.isEmpty()) {
            currentUserEmail = intent.getStringExtra("user_email") ?: "user@rosebank.com"
        }

        initializeViews()
        loadUserData()
        setupClickListeners()
    }

    private fun initializeViews() {
        btnBack = findViewById(R.id.btn_back)
        btnSave = findViewById(R.id.btn_save)
        etName = findViewById(R.id.et_name)
        etEmail = findViewById(R.id.et_email)
        etPhone = findViewById(R.id.et_phone)
        homeIcon = findViewById(R.id.homeIcon)
        bookingsIcon = findViewById(R.id.bookingsIcon)
        profileIcon = findViewById(R.id.profileIcon)
    }

    private fun loadUserData() {
        val user = dbHelper.getUserProfile(currentUserEmail)
        user?.let {
            etName.setText(it.fullName)
            etEmail.setText(it.email)
            etPhone.setText(it.phone)

            // Make email non-editable since it's used as identifier
            etEmail.isEnabled = false
        } ?: run {
            // If no user found, show default values or error
            Toast.makeText(this, "Loading default user data", Toast.LENGTH_SHORT).show()
            etName.setText("Thoriso Maake")
            etEmail.setText("thorisomaake@email.com")
            etPhone.setText("+27 67 456 0789")
            etEmail.isEnabled = false
        }
    }

    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            onBackPressed()
        }

        btnSave.setOnClickListener {
            saveUserProfile()
        }

        homeIcon.setOnClickListener {
            navigateToActivity(HomeActivity::class.java)
        }

        bookingsIcon.setOnClickListener {
            navigateToActivity(BookingsActivity::class.java)
        }

        profileIcon.setOnClickListener {
            navigateToActivity(ProfileActivity::class.java)
        }
    }

    private fun saveUserProfile() {
        val fullName = etName.text.toString().trim()
        val phone = etPhone.text.toString().trim()

        // Basic validation
        if (fullName.isEmpty()) {
            etName.error = "Please enter your full name"
            etName.requestFocus()
            return
        }

        if (phone.isEmpty()) {
            etPhone.error = "Please enter your phone number"
            etPhone.requestFocus()
            return
        }

        // Phone validation (basic)
        if (phone.length < 10) {
            etPhone.error = "Please enter a valid phone number"
            etPhone.requestFocus()
            return
        }

        val success = dbHelper.updateUserProfile(currentUserEmail, fullName, phone)

        if (success) {
            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()

            // Update shared preferences
            with(sharedPreferences.edit()) {
                putString("user_full_name", fullName)
                putString("user_phone", phone)
                apply()
            }

            // Broadcast profile update if other activities need to refresh
            val intent = Intent("PROFILE_UPDATED")
            sendBroadcast(intent)

            // Navigate back to profile activity
            val returnIntent = Intent(this, ProfileActivity::class.java)
            returnIntent.putExtra("user_email", currentUserEmail)
            startActivity(returnIntent)
            finish()
        } else {
            Toast.makeText(this, "Saved.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        intent.putExtra("user_email", currentUserEmail)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra("user_email", currentUserEmail)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}
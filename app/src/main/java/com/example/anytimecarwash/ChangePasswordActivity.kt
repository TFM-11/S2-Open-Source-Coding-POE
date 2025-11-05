package com.example.anytimecarwash

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import java.security.MessageDigest

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var btnBack: ImageButton
    private lateinit var btnSave: MaterialButton
    private lateinit var etCurrentPassword: EditText
    private lateinit var etNewPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var homeIcon: ImageView
    private lateinit var bookingsIcon: ImageView
    private lateinit var profileIcon: ImageView

    // SQLite Database variables
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var database: SQLiteDatabase

    // Assuming we have a way to identify the current user (you might get this from shared preferences or login session)
    private var currentUserId: Long = 1 // Default to 1, you should replace this with actual user ID

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        // Initialize database helper
        dbHelper = DatabaseHelper(this)
        database = dbHelper.writableDatabase

        btnBack = findViewById(R.id.btn_back)
        btnSave = findViewById(R.id.btn_save)
        etCurrentPassword = findViewById(R.id.et_current_password)
        etNewPassword = findViewById(R.id.et_new_password)
        etConfirmPassword = findViewById(R.id.et_confirm_password)
        homeIcon = findViewById(R.id.homeIcon)
        bookingsIcon = findViewById(R.id.bookingsIcon)
        profileIcon = findViewById(R.id.profileIcon)

        btnBack.setOnClickListener { onBackPressed() }

        btnSave.setOnClickListener {
            changePassword()
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

    private fun changePassword() {
        val currentPassword = etCurrentPassword.text.toString()
        val newPassword = etNewPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()

        // Validate inputs
        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (newPassword != confirmPassword) {
            Toast.makeText(this, "New passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        // Update password in database
        if (updatePasswordInDatabase(newPassword)) {
            Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show()
            onBackPressed()
        } else {
            Toast.makeText(this, "Failed to change password", Toast.LENGTH_SHORT).show()
        }
    }

    private fun verifyCurrentPassword(currentPassword: String): Boolean {
        val hashedCurrentPassword = hashPassword(currentPassword)

        val cursor = database.query(
            DatabaseHelper.TABLE_USERS,
            arrayOf(DatabaseHelper.COLUMN_PASSWORD),
            "${DatabaseHelper.COLUMN_ID} = ?",
            arrayOf(currentUserId.toString()),
            null, null, null
        )

        return if (cursor.moveToFirst()) {
            val storedPassword = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PASSWORD))
            cursor.close()
            storedPassword == hashedCurrentPassword
        } else {
            cursor.close()
            false
        }
    }

    private fun updatePasswordInDatabase(newPassword: String): Boolean {
        val hashedNewPassword = hashPassword(newPassword)

        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_PASSWORD, hashedNewPassword)
        }

        val rowsAffected = database.update(
            DatabaseHelper.TABLE_USERS,
            values,
            "${DatabaseHelper.COLUMN_ID} = ?",
            arrayOf(currentUserId.toString())
        )

        return rowsAffected > 0
    }

    private fun hashPassword(password: String): String {
        return try {
            val digest = MessageDigest.getInstance("SHA-256")
            val hash = digest.digest(password.toByteArray())
            hash.joinToString("") { "%02x".format(it) }
        } catch (e: Exception) {
            password // Fallback to plain text (not recommended for production)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        database.close()
        dbHelper.close()
    }
}
package com.example.anytimecarwash

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class BookingsActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookings)

        // Initialize database helper
        databaseHelper = DatabaseHelper(this)

        // Back button in toolbar (if you have one)
        val backButton: ImageButton? = findViewById(R.id.btn_back)
        backButton?.setImageResource(R.drawable.ic_arrow_back)
        backButton?.setOnClickListener {
            // Navigate back to HomeActivity
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        // Buttons for upcoming and past bookings
        val btnUpcoming = findViewById<Button>(R.id.btn_upcoming)
        val btnPast = findViewById<Button>(R.id.btn_past)

        btnUpcoming.setOnClickListener {
            val intent = Intent(this, UpcomingActivity::class.java)
            startActivity(intent)
        }

        btnPast.setOnClickListener {
            val intent = Intent(this, PastActivity::class.java)
            startActivity(intent)
        }

        // Get the fields for the new booking
        val etService = findViewById<EditText>(R.id.et_service)
        val etDate = findViewById<EditText>(R.id.et_date)
        val etTime = findViewById<EditText>(R.id.et_time)
        val etNotes = findViewById<EditText>(R.id.et_notes)
        val btnSave = findViewById<Button>(R.id.btn_save)

        btnSave.setOnClickListener {
            // Get the data from input fields
            val service = etService.text.toString().trim()
            val date = etDate.text.toString().trim()
            val time = etTime.text.toString().trim()
            val notes = etNotes.text.toString().trim()

            // Check if all fields are filled
            if (service.isEmpty() || date.isEmpty() || time.isEmpty()) {
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
            } else {
                // Assume the user email is the logged-in user's email; replace this with actual user data
                val userEmail = "user@example.com"

                // Insert the booking into the database
                val success = databaseHelper.insertBooking(userEmail, service, date, time, notes)

                if (success) {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                    clearFields(etService, etDate, etTime, etNotes)  // Clear fields after saving
                } else {
                    Toast.makeText(this, "SAVED", Toast.LENGTH_SHORT).show()
                    clearFields(etService, etDate, etTime, etNotes)
                }
            }
        }
    }

    // Helper function to clear the input fields
    private fun clearFields(vararg fields: EditText) {
        for (field in fields) {
            field.text.clear()
        }
    }
}

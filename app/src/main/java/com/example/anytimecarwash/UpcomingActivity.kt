package com.example.anytimecarwash

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class UpcomingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming)

        // List of all confirm buttons
        val confirmButtons = listOf(
            R.id.btn_confirm_exterior,
            R.id.btn_confirm_express,
            R.id.btn_confirm_fullservice,
            R.id.btn_confirm_valet,
            R.id.btn_confirm_detailing,
            R.id.btn_confirm_eco
        )

        // List of all cancel buttons
        val cancelButtons = listOf(
            R.id.btn_cancel_exterior,
            R.id.btn_cancel_express,
            R.id.btn_cancel_fullservice,
            R.id.btn_cancel_valet,
            R.id.btn_cancel_detailing,
            R.id.btn_cancel_eco
        )

        // Set onClickListener for all confirm buttons
        confirmButtons.forEach { id ->
            findViewById<MaterialButton>(id).setOnClickListener {
                Toast.makeText(this, "Booking confirmed!", Toast.LENGTH_SHORT).show()
                navigateToHome()
            }
        }

        // Set onClickListener for all cancel buttons
        cancelButtons.forEach { id ->
            findViewById<MaterialButton>(id).setOnClickListener {
                Toast.makeText(this, "Booking cancelled!", Toast.LENGTH_SHORT).show()
                navigateToHome()
            }
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}


package com.example.anytimecarwash

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.anytimecarwash.databinding.ActivityMyVehiclesBinding
import com.google.android.material.button.MaterialButton

class MyVehiclesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyVehiclesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyVehiclesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Back button on toolbar
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        // Vehicle 1 Buttons
        binding.btnViewHistory1?.setOnClickListener {
            navigateToPastActivity("XYZ 123 GP")
        }
        binding.btnBookAgain1?.setOnClickListener {
            Toast.makeText(this, "Book Again Clicked for Vehicle 1", Toast.LENGTH_SHORT).show()
        }

        // Vehicle 2 Buttons
        binding.btnViewHistory2?.setOnClickListener {
            navigateToPastActivity("XYZ 124 GP")
        }
        binding.btnBookAgain2?.setOnClickListener {
            Toast.makeText(this, "Book Again Clicked for Vehicle 2", Toast.LENGTH_SHORT).show()
        }

        // Vehicle 3 Buttons
        binding.btnViewHistory3?.setOnClickListener {
            navigateToPastActivity("XYZ 125 GP")
        }
        binding.btnBookAgain3?.setOnClickListener {
            Toast.makeText(this, "Book Again Clicked for Vehicle 3", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToPastActivity(vehicleRegNumber: String) {
        try {
            val intent = Intent(this, PastActivity::class.java).apply {
                putExtra("vehicleRegNumber", vehicleRegNumber)
            }
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to navigate: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
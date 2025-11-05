package com.example.anytimecarwash

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.anytimecarwash.databinding.ActivityPastBinding
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PastActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPastBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPastBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get vehicle registration number from intent
        val vehicleRegNumber = intent.getStringExtra("vehicleRegNumber") ?: "Unknown Vehicle"

        // Display vehicle registration number
        binding.tvVehicleInfo.text = "Vehicle: $vehicleRegNumber"

        // Setup the back button in toolbar
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        // Simulate loading history (replace with actual data fetch)
        lifecycleScope.launch {
            binding.progressBar.visibility = android.view.View.VISIBLE
            try {
                // Simulate delay for data loading
                withContext(Dispatchers.IO) {
                    Thread.sleep(1000) // Replace with API/Database call
                }
                // Update UI with dynamic data here if needed
            } catch (e: Exception) {
                // Handle error
            } finally {
                binding.progressBar.visibility = android.view.View.GONE
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
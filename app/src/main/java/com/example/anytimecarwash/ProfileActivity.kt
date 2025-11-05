package com.example.anytimecarwash

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.anytimecarwash.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Back button on toolbar
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        // Example user info (can be loaded from API or Firebase)
        binding.tvName.text = "Thoriso Maake"
        binding.tvEmail.text = "thorisomaake@gmail.com"
        binding.tvPhone.text = "(+27)-678-2530"

        // Click listeners
        binding.btnEditProfile.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }

        binding.btnChangePassword.setOnClickListener {
            startActivity(Intent(this, ChangePasswordActivity::class.java))
        }

        binding.btnMyVehicles.setOnClickListener {
            startActivity(Intent(this, MyVehiclesActivity::class.java))
        }

        binding.btnPaymentMethods.setOnClickListener {
            startActivity(Intent(this, PaymentMethodsActivity::class.java))
        }
    }
}
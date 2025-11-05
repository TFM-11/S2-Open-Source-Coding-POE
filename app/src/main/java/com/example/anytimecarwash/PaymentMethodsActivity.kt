package com.example.anytimecarwash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.anytimecarwash.databinding.ActivityPaymentMethodsBinding


class PaymentMethodsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentMethodsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentMethodsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Back button on toolbar
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}
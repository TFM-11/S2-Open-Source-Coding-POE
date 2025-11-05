package com.example.anytimecarwash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Setup Toolbar
        setSupportActionBar(findViewById(R.id.toolbar))

        // Setup Back Button
        findViewById<ImageButton>(R.id.btn_back).setOnClickListener {
            onBackPressed()
        }

        // Setup Language Spinner
        val languages = arrayOf("English", "Afrikaans", "Sepedi")
        val spinner = findViewById<Spinner>(R.id.spinner_language)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(0) // Default to English

        // Setup Theme Switch
        findViewById<SwitchMaterial>(R.id.switch_theme).setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(
                this,
                "Theme switched to ${if (isChecked) "Light" else "Dark"}",
                Toast.LENGTH_SHORT
            ).show()
            // Implement theme change logic here
        }

        // Setup Logout Button
        findViewById<MaterialButton>(R.id.btn_logout).setOnClickListener {
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
            // Implement logout logic here
        }

        // Setup Clickable Pages
        findViewById<LinearLayout>(R.id.layout_notifications)?.setOnClickListener {
            startActivity(Intent(this, NotificationsActivity::class.java))
            finish()
        }

        findViewById<LinearLayout>(R.id.layout_account_settings)?.setOnClickListener {
            startActivity(Intent(this, AccountSettingsActivity::class.java))
            finish()
        }

        findViewById<LinearLayout>(R.id.layout_edit_profile)?.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
            finish()
        }

        findViewById<LinearLayout>(R.id.layout_change_password)?.setOnClickListener {
            startActivity(Intent(this, ChangePasswordActivity::class.java))
            finish()
        }

        findViewById<LinearLayout>(R.id.layout_about)?.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
            finish()
        }

        findViewById<LinearLayout>(R.id.layout_privacy_policy)?.setOnClickListener {
            startActivity(Intent(this, PrivacyPolicyActivity::class.java))
            finish()
        }

        findViewById<LinearLayout>(R.id.layout_about_us)?.setOnClickListener {
            startActivity(Intent(this, AboutUsActivity::class.java))
            finish()
        }

    }
}
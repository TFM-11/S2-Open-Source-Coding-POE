package com.example.anytimecarwash

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PrivacyPolicyActivity : AppCompatActivity() {
    private lateinit var btnBack: ImageButton
    private lateinit var homeIcon: ImageView
    private lateinit var bookingsIcon: ImageView
    private lateinit var profileIcon: ImageView
    private lateinit var titleText: TextView
    private lateinit var privacyTitle: TextView
    private lateinit var privacyContent: TextView
    private lateinit var lastUpdatedText: TextView
    private lateinit var homeText: TextView
    private lateinit var bookingsText: TextView
    private lateinit var profileText: TextView

    private var actionMode: ActionMode? = null
    private var selectedText: String = ""
    private var currentLanguage: String = "english" // Default language

    // Translation dictionaries for Privacy Policy
    private val sepediTranslations = mapOf(
        "Privacy Policy" to "Pholisi ya Sephiri",
        "At Anytime Car Wash, we value your privacy. This policy outlines how we collect, use, and protect your personal information. We do not share your data with third parties without your consent. For more details, contact us at support@anytimecarwash.com." to
                "Go Anytime Car Wash, re hloka sephiri sa gago. Pholisi ye e hlalosa tsela yeo re kgoboketša, re šomiša le go šireletša tshedimošo ya gago ya motho ka mong. Ga re arolelane data ya gago le batho ba bašele go sa dumelelane le gago. Bakeng sa dintlha tše dingwe, re ikgokaganye le rena go support@anytimecarwash.com.",
        "Last Updated: Nov 01, 2025" to "E fetotšwe maabane: Nov 01, 2025",
        "Home" to "Gae",
        "Bookings" to "Dipeakanyo",
        "Profile" to "Profaele"
    )

    private val afrikaansTranslations = mapOf(
        "Privacy Policy" to "Privaatheidsbeleid",
        "At Anytime Car Wash, we value your privacy. This policy outlines how we collect, use, and protect your personal information. We do not share your data with third parties without your consent. For more details, contact us at support@anytimecarwash.com." to
                "By Anytime Car Wash, waardeer ons u privaatheid. Hierdie beleid skets hoe ons u persoonlike inligting versamel, gebruik en beskerm. Ons deel nie u data met derdeparty sonder u toestemming nie. Vir meer besonderhede, kontak ons by support@anytimecarwash.com.",
        "Last Updated: Nov 01, 2025" to "Laas Opgedateer: Nov 01, 2025",
        "Home" to "Huis",
        "Bookings" to "Besprekings",
        "Profile" to "Profiel"
    )

    @SuppressLint("MissingInflatedId", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)

        initializeViews()
        setupTextSelection()
        setupClickListeners()

        // Apply current language on create
        applyCurrentLanguage()
    }

    private fun initializeViews() {
        btnBack = findViewById(R.id.btn_back)
        homeIcon = findViewById(R.id.homeIcon)
        bookingsIcon = findViewById(R.id.bookingsIcon)
        profileIcon = findViewById(R.id.profileIcon)
        titleText = findViewById(R.id.titleText)
        privacyTitle = findViewById(R.id.privacy_title)
        privacyContent = findViewById(R.id.privacy_content)
        lastUpdatedText = findViewById(R.id.last_updated_text)
        homeText = findViewById(R.id.home_text)
        bookingsText = findViewById(R.id.bookings_text)
        profileText = findViewById(R.id.profile_text)
    }

    private fun setupClickListeners() {
        btnBack.setOnClickListener { onBackPressed() }
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

    private fun setupTextSelection() {
        val textViews = listOf(
            findViewById<TextView>(R.id.titleText),
            findViewById<TextView>(R.id.privacy_title),
            findViewById<TextView>(R.id.privacy_content),
            findViewById<TextView>(R.id.last_updated_text),
            findViewById<TextView>(R.id.home_text),
            findViewById<TextView>(R.id.bookings_text),
            findViewById<TextView>(R.id.profile_text)
        )

        textViews.forEach { textView ->
            textView.customSelectionActionModeCallback = object : ActionMode.Callback {
                override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    mode?.menuInflater?.inflate(R.menu.translation_menu, menu)
                    return true
                }

                override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    // Store the selected text when action mode is prepared
                    selectedText = textView.text?.subSequence(
                        textView.selectionStart,
                        textView.selectionEnd
                    ).toString()
                    return true
                }

                override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                    return when (item?.itemId) {
                        R.id.action_translate_sepedi -> {
                            setLanguage("sepedi")
                            mode?.finish()
                            true
                        }
                        R.id.action_translate_afrikaans -> {
                            setLanguage("afrikaans")
                            mode?.finish()
                            true
                        }
                        R.id.action_copy -> {
                            copyToClipboard(selectedText)
                            mode?.finish()
                            true
                        }
                        else -> false
                    }
                }

                override fun onDestroyActionMode(mode: ActionMode?) {
                    // Clean up
                }
            }
        }
    }

    private fun setLanguage(language: String) {
        currentLanguage = language
        applyCurrentLanguage()
        Toast.makeText(this, "Language changed to ${language.replaceFirstChar { it.uppercase() }}", Toast.LENGTH_SHORT).show()
    }

    private fun applyCurrentLanguage() {
        val translations = when (currentLanguage) {
            "sepedi" -> sepediTranslations
            "afrikaans" -> afrikaansTranslations
            else -> null // For English, we use original text
        }

        // Update all text views with translations
        if (translations != null) {
            // Translate each text view
            titleText.text = translations[getOriginalText(titleText)] ?: getOriginalText(titleText)
            privacyTitle.text = translations[getOriginalText(privacyTitle)] ?: getOriginalText(privacyTitle)
            privacyContent.text = translations[getOriginalText(privacyContent)] ?: getOriginalText(privacyContent)
            lastUpdatedText.text = translations[getOriginalText(lastUpdatedText)] ?: getOriginalText(lastUpdatedText)
            homeText.text = translations[getOriginalText(homeText)] ?: getOriginalText(homeText)
            bookingsText.text = translations[getOriginalText(bookingsText)] ?: getOriginalText(bookingsText)
            profileText.text = translations[getOriginalText(profileText)] ?: getOriginalText(profileText)
        } else {
            // Reset to English (original text)
            resetToEnglish()
        }
    }

    // Helper function to get original English text for each TextView
    private fun getOriginalText(textView: TextView): String {
        return when (textView.id) {
            R.id.titleText -> "Privacy Policy"
            R.id.privacy_title -> "Privacy Policy"
            R.id.privacy_content -> "At Anytime Car Wash, we value your privacy. This policy outlines how we collect, use, and protect your personal information. We do not share your data with third parties without your consent. For more details, contact us at support@anytimecarwash.com."
            R.id.last_updated_text -> "Last Updated: Nov 01, 2025"
            R.id.home_text -> "Home"
            R.id.bookings_text -> "Bookings"
            R.id.profile_text -> "Profile"
            else -> textView.text.toString()
        }
    }

    private fun resetToEnglish() {
        titleText.text = "Privacy Policy"
        privacyTitle.text = "Privacy Policy"
        privacyContent.text = "At Anytime Car Wash, we value your privacy. This policy outlines how we collect, use, and protect your personal information. We do not share your data with third parties without your consent. For more details, contact us at support@anytimecarwash.com."
        lastUpdatedText.text = "Last Updated: Nov 01, 2025"
        homeText.text = "Home"
        bookingsText.text = "Bookings"
        profileText.text = "Profile"
    }

    private fun copyToClipboard(text: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("selected_text", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "Text copied to clipboard", Toast.LENGTH_SHORT).show()
    }
}
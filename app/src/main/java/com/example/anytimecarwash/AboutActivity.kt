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

class AboutActivity : AppCompatActivity() {
    private lateinit var btnBack: ImageButton
    private lateinit var homeIcon: ImageView
    private lateinit var bookingsIcon: ImageView
    private lateinit var profileIcon: ImageView
    private lateinit var aboutTitle: TextView
    private lateinit var aboutContent: TextView
    private lateinit var versionText: TextView
    private lateinit var homeText: TextView
    private lateinit var bookingsText: TextView
    private lateinit var profileText: TextView

    private var actionMode: ActionMode? = null
    private var selectedText: String = ""
    private var currentLanguage: String = "english" // Default language

    // Translation dictionaries (you can expand these)
    private val sepediTranslations = mapOf(
        "About" to "Ka ga",
        "About Anytime Car Wash" to "Ka ga Anytime Car Wash",
        "Welcome to Anytime Car Wash, your go-to solution for premium car cleaning services. We offer Basic Wash, Premium Wash, and Full Detail options to keep your vehicle sparkling clean. Download our app to book your wash today!" to
                "Rea u amogela go Anytime Car Wash, tharollo ya gago ya ditshebeletso tša tlhatlhobo ya koloi ya maemo a godimo. Re nea Basic Wash, Premium Wash, le dipeakanyo tša Full Detail go boloka koloi ya gago e hlabosega. Download app ya rena go ipea tlhatlhobo ya gago lehono!",
        "Version" to "Phetolelo",
        "Home" to "Gae",
        "Bookings" to "Dipeakanyo",
        "Profile" to "Profaele",
        "Version: 1.0.0" to "Phetolelo: 1.0.0"
    )

    private val afrikaansTranslations = mapOf(
        "About" to "Oor",
        "About Anytime Car Wash" to "Oor Anytime Car Wash",
        "Welcome to Anytime Car Wash, your go-to solution for premium car cleaning services. We offer Basic Wash, Premium Wash, and Full Detail options to keep your vehicle sparkling clean. Download our app to book your wash today!" to
                "Welkom by Anytime Car Wash, jou naaste oplossing vir premium motor skoonmaak dienste. Ons bied Basic Wash, Premium Wash, en Full Detail opsies om jou voertuig sprankelend skoon te hou. Laai ons app af om jou was vandag te bespreek!",
        "Version" to "Weergawe",
        "Home" to "Huis",
        "Bookings" to "Besprekings",
        "Profile" to "Profiel",
        "Version: 1.0.0" to "Weergawe: 1.0.0"
    )

    @SuppressLint("MissingInflatedId", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        initializeViews()
        setupTextSelection()
        setupClickListeners()

        // Apply current language on create (in case we're returning to this activity)
        applyCurrentLanguage()
    }

    private fun initializeViews() {
        btnBack = findViewById(R.id.btn_back)
        homeIcon = findViewById(R.id.homeIcon)
        bookingsIcon = findViewById(R.id.bookingsIcon)
        profileIcon = findViewById(R.id.profileIcon)
        aboutTitle = findViewById(R.id.titleText)
        aboutContent = findViewById(R.id.about_content)
        versionText = findViewById(R.id.version_text)
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
            findViewById<TextView>(R.id.about_title),
            findViewById<TextView>(R.id.about_content),
            findViewById<TextView>(R.id.version_text),
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
        Toast.makeText(this, "Language changed to ${language.capitalize()}", Toast.LENGTH_SHORT).show()
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
            aboutTitle.text = translations[getOriginalText(aboutTitle)] ?: getOriginalText(aboutTitle)
            aboutContent.text = translations[getOriginalText(aboutContent)] ?: getOriginalText(aboutContent)
            versionText.text = translations[getOriginalText(versionText)] ?: getOriginalText(versionText)
            homeText.text = translations[getOriginalText(homeText)] ?: getOriginalText(homeText)
            bookingsText.text = translations[getOriginalText(bookingsText)] ?: getOriginalText(bookingsText)
            profileText.text = translations[getOriginalText(profileText)] ?: getOriginalText(profileText)

            // For the title in app bar, we need to handle it separately
            findViewById<TextView>(R.id.titleText).text = translations["About"] ?: "About"
        } else {
            // Reset to English (original text)
            resetToEnglish()
        }
    }

    // Helper function to get original English text for each TextView
    private fun getOriginalText(textView: TextView): String {
        return when (textView.id) {
            R.id.about_title -> "About Anytime Car Wash"
            R.id.about_content -> "Welcome to Anytime Car Wash, your go-to solution for premium car cleaning services. We offer Basic Wash, Premium Wash, and Full Detail options to keep your vehicle sparkling clean. Download our app to book your wash today!"
            R.id.version_text -> "Version: 1.0.0"
            R.id.home_text -> "Home"
            R.id.bookings_text -> "Bookings"
            R.id.profile_text -> "Profile"
            R.id.titleText -> "About"
            else -> textView.text.toString()
        }
    }

    private fun resetToEnglish() {
        aboutTitle.text = "About Anytime Car Wash"
        aboutContent.text = "Welcome to Anytime Car Wash, your go-to solution for premium car cleaning services. We offer Basic Wash, Premium Wash, and Full Detail options to keep your vehicle sparkling clean. Download our app to book your wash today!"
        versionText.text = "Version: 1.0.0"
        homeText.text = "Home"
        bookingsText.text = "Bookings"
        profileText.text = "Profile"
        findViewById<TextView>(R.id.titleText).text = "About"
    }

    private fun copyToClipboard(text: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("selected_text", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "Text copied to clipboard", Toast.LENGTH_SHORT).show()
    }
}
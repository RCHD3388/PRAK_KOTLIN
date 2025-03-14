package com.example.m2_prak_project

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.m2_prak_project.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var actIntent: Intent
    private var highscore = arrayListOf<ScoreData>()

    fun setupHangmanText(){
        val tvTitle = binding.tvTitle

        // Membuat teks "H A N G M A N" dengan garis bawah di setiap huruf
        val word = "H A N G M A N"
        val spannable = SpannableString(word)
        for (i in word.indices step 2) { // Step 2 agar hanya huruf yang diberi garis bawah
            spannable.setSpan(
                UnderlineSpan(),
                i,
                i + 1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        tvTitle.text = spannable
        tvTitle.typeface = Typeface.DEFAULT_BOLD
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupHangmanText()

        binding.btnStart.setOnClickListener {
            actIntent = Intent(this, GameActivity::class.java)
            startActivity(actIntent)
        }
        binding.btnHighscore.setOnClickListener {
            actIntent = Intent(this, HighscoreActivity::class.java)
            startActivity(actIntent)
        }
        binding.btnRules.setOnClickListener {
            actIntent = Intent(this, RulesActivity::class.java)
            startActivity(actIntent)
        }
        binding.btnSettings.setOnClickListener {
            actIntent = Intent(this, SettingsActivity::class.java)
            startActivity(actIntent)
        }
        binding.btnAbout.setOnClickListener {
            actIntent = Intent(this, AboutActivity::class.java)
            startActivity(actIntent)
        }
    }
}
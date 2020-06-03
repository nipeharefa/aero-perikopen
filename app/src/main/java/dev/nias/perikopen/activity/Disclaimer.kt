package dev.nias.perikopen.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.nias.perikopen.R

class Disclaimer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disclaimer)

        val actionbar = supportActionBar
        actionbar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = ""
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

package com.posse.android.moneyconverter.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.posse.android.moneyconverter.R
import com.posse.android.moneyconverter.view.main.MainFragment
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private var isBackShown = false
    private var lastTimeBackPressed: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        swapFragment()
    }

    private fun swapFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, MainFragment.newInstance())
            .setReorderingAllowed(true)
            .commit()
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - lastTimeBackPressed >= BACK_BUTTON_EXIT_DELAY) {
            isBackShown = false
        }
        if (isBackShown) exitProcess(0)
        else {
            Toast.makeText(this, getString(R.string.back_again_to_exit), Toast.LENGTH_SHORT).show()
            isBackShown = true
        }
        lastTimeBackPressed = System.currentTimeMillis()
    }

    companion object {
        private const val BACK_BUTTON_EXIT_DELAY = 3000
    }
}
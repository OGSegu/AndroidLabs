package ru.spbstu.icc.kspt.lab2.continuewatch.task1.main.timer

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.spbstu.icc.kspt.lab2.continuewatch.R
import java.util.*
import kotlin.concurrent.schedule
import kotlin.concurrent.scheduleAtFixedRate

class MainActivity : AppCompatActivity() {

    companion object {
        val SECONDS_ELAPSED_TEXT = "SecondsElapsed"
    }

    private var secondsElapsed: Int = 0

    private var timer: Timer = Timer()

    @Volatile
    private var paused: Boolean = false

    lateinit var textSecondsElapsed: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState)
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        secondsElapsed = sharedPref.getInt(SECONDS_ELAPSED_TEXT, 0)

        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)

        if (!paused) {
            timer.scheduleAtFixedRate(0, 1000) {
                updateSeconds()
            }
        }
    }

    private fun updateSeconds() {
        textSecondsElapsed.post {
            textSecondsElapsed.text = "Seconds elapsed: " + secondsElapsed++
        }
    }

    override fun onPause() {
        super.onPause()
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putInt(SECONDS_ELAPSED_TEXT, secondsElapsed)
            apply()
        }
        Log.d(TAG, "paused = true")
        paused = true;
        timer.cancel();
        timer.purge();
        timer = Timer();
    }

    override fun onResume() {
        super.onResume()
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        secondsElapsed = sharedPref.getInt(SECONDS_ELAPSED_TEXT, 0)

        if (paused) {
            paused = false;
            timer.scheduleAtFixedRate(0, 1000) {
                updateSeconds()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {

        outState.putInt(SECONDS_ELAPSED_TEXT, secondsElapsed)
        Log.d(TAG, "onSaveInstanceState: " + secondsElapsed)

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {

        secondsElapsed = savedInstanceState.getInt(SECONDS_ELAPSED_TEXT)
        Log.d(TAG, "Loading seconds: " + secondsElapsed)

        super.onRestoreInstanceState(savedInstanceState)
    }
}

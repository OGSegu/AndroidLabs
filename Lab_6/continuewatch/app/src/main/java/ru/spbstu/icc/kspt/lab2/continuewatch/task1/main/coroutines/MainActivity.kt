package ru.spbstu.icc.kspt.lab2.continuewatch.task1.main.coroutines

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.spbstu.icc.kspt.lab2.continuewatch.R

class MainActivity : AppCompatActivity() {

    companion object {
        val SECONDS_ELAPSED_TEXT = "SecondsElapsed"
    }

    private var secondsElapsed: Int = 0

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
            GlobalScope.launch {
                updateSeconds()
            }
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
    }

    override fun onResume() {
        super.onResume()
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        secondsElapsed = sharedPref.getInt(SECONDS_ELAPSED_TEXT, 0)

        if (paused) {
            GlobalScope.launch {
                updateSeconds();
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

    private fun updateSeconds() {
        while (!paused) {
            Thread.sleep(1000);
            textSecondsElapsed.post {
                textSecondsElapsed.setText("Seconds elapsed: " + secondsElapsed++)
            }
        }
    }
}

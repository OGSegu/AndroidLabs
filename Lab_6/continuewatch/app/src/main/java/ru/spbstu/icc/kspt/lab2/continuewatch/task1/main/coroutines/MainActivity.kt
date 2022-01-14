package ru.spbstu.icc.kspt.lab2.continuewatch.task1.main.coroutines

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.spbstu.icc.kspt.lab2.continuewatch.R

class MainActivity : AppCompatActivity() {

    companion object {
        const val SECONDS_ELAPSED_TEXT = "SecondsElapsed"
    }

    private var secondsElapsed: Long = 0

    private lateinit var textSecondsElapsed: TextView

    private var startTimestamp: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState)
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        secondsElapsed = sharedPref.getLong(SECONDS_ELAPSED_TEXT, 0L)

        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                while (true) {
                    updateSeconds();
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putLong(SECONDS_ELAPSED_TEXT, secondsElapsed)
            apply()
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i("onResume", "onResume")
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        secondsElapsed = sharedPref.getLong(SECONDS_ELAPSED_TEXT, 0L)
        startTimestamp = System.currentTimeMillis()
    }

    override fun onSaveInstanceState(outState: Bundle) {

        outState.putLong(SECONDS_ELAPSED_TEXT, secondsElapsed)
        Log.d(TAG, "onSaveInstanceState: $secondsElapsed")

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {

        secondsElapsed = savedInstanceState.getLong(SECONDS_ELAPSED_TEXT)
        Log.d(TAG, "Loading seconds: $secondsElapsed")

        super.onRestoreInstanceState(savedInstanceState)
    }

    private suspend fun updateSeconds() {
        delay(10);
        val timestampStep = System.currentTimeMillis() - startTimestamp
        if (timestampStep >= 1000) {
            Log.i("Current timestamp", timestampStep.toString())
            textSecondsElapsed.text = "Seconds elapsed: " + ++secondsElapsed;
            Log.i("Coroutine", "Time: $secondsElapsed")
            startTimestamp = System.currentTimeMillis();
        }
    }
}

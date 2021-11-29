package ru.spbstu.icc.kspt.lab2.continuewatch.task1.main.asynctask

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.spbstu.icc.kspt.lab2.continuewatch.R

class MainActivity : AppCompatActivity() {

    companion object {
        val SECONDS_ELAPSED_TEXT = "SecondsElapsed"
    }

    private var secondsElapsed: Int = 0

    lateinit var textSecondsElapsed: TextView

    private val timerUpdater: (t: Int) -> Unit = {
        textSecondsElapsed.post {
            textSecondsElapsed.text = "Seconds elapsed: " + it
        }
        secondsElapsed = it;
    }
    private lateinit var timerTask: TimerAsyncTask;

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState)
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        secondsElapsed = sharedPref.getInt(SECONDS_ELAPSED_TEXT, 0)

        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)

        if (!this::timerTask.isInitialized) {
            timerTask = TimerAsyncTask(timerUpdater)
            timerTask.execute(secondsElapsed)
        }
    }

    override fun onPause() {
        super.onPause()
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putInt(SECONDS_ELAPSED_TEXT, secondsElapsed)
            apply()
        }


        timerTask.cancel(false);
    }

    override fun onResume() {
        super.onResume()
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        secondsElapsed = sharedPref.getInt(SECONDS_ELAPSED_TEXT, 0)

        if (timerTask.isCancelled) {
            timerTask = TimerAsyncTask(timerUpdater)
            timerTask.execute(secondsElapsed);
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

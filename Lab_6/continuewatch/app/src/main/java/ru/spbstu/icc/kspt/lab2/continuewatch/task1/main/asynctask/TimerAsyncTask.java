package ru.spbstu.icc.kspt.lab2.continuewatch.task1.main.asynctask;

import android.os.AsyncTask;

import java.util.function.Consumer;

public class TimerAsyncTask extends AsyncTask<Integer, Integer, Void> {

    private final Consumer<Integer> timerUpdater;

    public TimerAsyncTask(Consumer<Integer> timerUpdater) {
        this.timerUpdater = timerUpdater;
    }

    @Override
    protected Void doInBackground(Integer... integers) {
        int secondsElapsed = integers[0];

        while (!isCancelled()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                cancel(true);
            }
            secondsElapsed++;
            publishProgress(secondsElapsed);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        timerUpdater.accept(values[0]);
    }
}

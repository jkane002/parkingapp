package com.example.andrewcruz.parking_app;

import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.Date;
import java.util.Timer;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Service extends JobService {
    private static final String TAG = "Parsing";
    fetchData process;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "Job Started");
        doBackgroundWork(jobParameters);
        return true;
    }

    private void doBackgroundWork(final JobParameters jobParam) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"Runnig Parse");
                process = new fetchData();
                process.execute();
                Log.d(TAG,"Finishing Parse");
                jobFinished(jobParam,false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "JOB CANCELLED BEFORE CONPLETION");
        process.cancel(true);
        return true;
    }
}

package org.mendybot.android.aucalc.sync;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.widget.Toast;

public class CommoditySyncService extends JobService {
    private static final String TAG = "SyncService";

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Toast.makeText(getApplicationContext(), "Job started "+jobParameters.getJobId(), Toast.LENGTH_LONG).show();
        new CommoditySyncTask(this).execute(jobParameters);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}

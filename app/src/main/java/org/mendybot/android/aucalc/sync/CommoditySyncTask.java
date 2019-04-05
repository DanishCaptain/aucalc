package org.mendybot.android.aucalc.sync;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.util.Log;

import org.mendybot.android.aucalc.model.CommoditiesModel;

public class CommoditySyncTask extends AsyncTask<JobParameters, Void, JobParameters> {
    private final JobService jobService;

    public CommoditySyncTask(JobService jobService) {
        this.jobService = jobService;
    }

    @Override
    protected JobParameters doInBackground(JobParameters... params) {
        CommoditiesModel.getInstance().setAuStrikeOzt(1294.57);
        return params[0];
    }

    @Override
    protected void onPostExecute(JobParameters jobParameters) {
        jobService.jobFinished(jobParameters, false);
    }
}
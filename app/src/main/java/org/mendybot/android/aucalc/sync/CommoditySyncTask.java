package org.mendybot.android.aucalc.sync;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import org.mendybot.android.aucalc.model.CommoditiesModel;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class CommoditySyncTask extends AsyncTask<JobParameters, Void, JobParameters> {
    private static final String CALL = "http://services.packetizer.com/spotprices?f=json";
    private final JobService jobService;
    private boolean running;

    public CommoditySyncTask(JobService jobService) {
        this.jobService = jobService;
    }

    @Override
    protected JobParameters doInBackground(JobParameters... params) {
        CommoditySyncTool.getInstance().executeNow();
        return params[0];
    }

    @Override
    protected void onPostExecute(JobParameters jobParameters) {
        jobService.jobFinished(jobParameters, false);
    }

}


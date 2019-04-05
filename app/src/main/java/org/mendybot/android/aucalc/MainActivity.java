package org.mendybot.android.aucalc;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import org.mendybot.android.aucalc.model.CommoditiesChangeListener;
import org.mendybot.android.aucalc.model.CommoditiesModel;
import org.mendybot.android.aucalc.sync.CommoditySyncService;

public class MainActivity extends AppCompatActivity implements CommoditiesChangeListener {
    private static final int JOB_ID1 = 11220;
    private static final int JOB_ID2 = 11221;
    private EditText etAuOzt;
    private EditText etAuG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JobInfo jobInfo1 = new JobInfo.Builder(JOB_ID1, new ComponentName(this, CommoditySyncService.class))
                .setPeriodic(10)
                .build();
        JobInfo jobInfo2 = new JobInfo.Builder(JOB_ID2, new ComponentName(this, CommoditySyncService.class))
//                .setPeriodic(60*1000)
                .setPeriodic(6000)
                //.setPeriodic(15*60*1000,7*60*1000)
                .build();

        final JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int x1 = jobScheduler.schedule(jobInfo1);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        jobScheduler.cancel(JOB_ID1);
        int x2 = jobScheduler.schedule(jobInfo2);

        etAuOzt = (EditText) findViewById(R.id.au_price_ozt);
        etAuG = (EditText) findViewById(R.id.au_price_g);

        CommoditiesModel.getInstance().addCommoditiesChangeListener(this);

    }

    @Override
    public void auCommodityChanged() {
        runOnUiThread(new Runnable() {
            public void run() {
                etAuOzt.setText(Double.toString(CommoditiesModel.getInstance().getAuStrikeOzt()));
                etAuG.setText(Double.toString(CommoditiesModel.getInstance().getAuStrikeG()));
            }
        });

    }
}

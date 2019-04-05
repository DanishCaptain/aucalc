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
    private static final int JOB_ID = 11220;
    private EditText etAuOzt;
    private EditText etAuG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JobInfo jobInfo = new JobInfo.Builder(JOB_ID, new ComponentName(this, CommoditySyncService.class))
                .setPeriodic(60*1000)
//                .setPeriodic(6000)
                //.setPeriodic(15*60*1000,7*60*1000)
                .build();

        final JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int x = jobScheduler.schedule(jobInfo);
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

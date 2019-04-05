package org.mendybot.android.aucalc;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.mendybot.android.aucalc.model.CommoditiesChangeListener;
import org.mendybot.android.aucalc.model.CommoditiesModel;
import org.mendybot.android.aucalc.sync.CommoditySyncService;
import org.mendybot.android.aucalc.sync.CommoditySyncTool;

public class MainActivity extends AppCompatActivity implements CommoditiesChangeListener {
    private static final int JOB_ID = 11220;
    private TextView etAuOzt;
    private TextView etAuG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CommoditySyncTool.getInstance().start();

        JobInfo jobInfo = new JobInfo.Builder(JOB_ID, new ComponentName(this, CommoditySyncService.class))
                .setPeriodic(60*1000)
                //.setPeriodic(6000)
                //.setPeriodic(15*60*1000,7*60*1000)
                .build();

        final JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int x = jobScheduler.schedule(jobInfo);
        etAuOzt = (TextView) findViewById(R.id.au_price_ozt);
        etAuG = (TextView) findViewById(R.id.au_price_g);
        CommoditiesModel.getInstance().addCommoditiesChangeListener(this);

        CommoditySyncTool.getInstance().executeNow();
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

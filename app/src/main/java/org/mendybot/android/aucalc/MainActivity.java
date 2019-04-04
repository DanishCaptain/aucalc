package org.mendybot.android.aucalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final double G_OZT_FACTOR = 31.103715338797;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText etAuOzt = (EditText) findViewById(R.id.au_price_ozt);
        EditText etAuG = (EditText) findViewById(R.id.au_price_g);
        long auOzt = Math.round(Double.parseDouble(etAuOzt.getText().toString())*100d);
        long auG = Math.round(auOzt / G_OZT_FACTOR);
        etAuG.setText(Double.toString(auG/100d));

    }

}

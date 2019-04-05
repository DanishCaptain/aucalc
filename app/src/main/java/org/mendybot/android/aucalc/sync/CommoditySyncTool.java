package org.mendybot.android.aucalc.sync;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.mendybot.android.aucalc.model.CommoditiesModel;
import org.mendybot.android.aucalc.model.domain.Commodity;

import java.io.BufferedReader;
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

public final class CommoditySyncTool implements Runnable {
    private static final String CALL = "http://services.packetizer.com/spotprices?f=json";
    private static CommoditySyncTool singleton;
    private Thread t = new Thread(this);
    private boolean running;

    private CommoditySyncTool() {
        t.setName(getClass().getSimpleName());
        t.setDaemon(true);
    }

    public void executeNow() {
        synchronized (this) {
            notifyAll();
        }
    }

    public void start() {
        t.start();
    }

    @Override
    public void run() {
        running = true;
        while(running) {
            Thread ttt = Thread.currentThread();
            System.out.println(ttt);
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    running = false;
                }
            }
            try {
                URL url = new URL(CALL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setConnectTimeout(5000);
                con.setReadTimeout(5000);
                con.setInstanceFollowRedirects(true);

                //Map<String, String> parameters = new HashMap<>();
                //parameters.put("param1", "val");

                int status = con.getResponseCode();
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer jsonString = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    jsonString.append(inputLine);
                }
                in.close();

                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                Commodity c = gson.fromJson(jsonString.toString(), Commodity.class);
                CommoditiesModel.getInstance().setAuStrikeOzt(c.getGold());//1294.57);
                con.disconnect();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    public static String getParamsString(Map<String, String> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }


    public synchronized static CommoditySyncTool getInstance() {
        if (singleton == null) {
            singleton = new CommoditySyncTool();
        }
        return singleton;
    }

}


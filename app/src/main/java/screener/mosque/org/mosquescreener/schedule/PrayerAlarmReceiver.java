package screener.mosque.org.mosquescreener.schedule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Date;

import screener.mosque.org.mosquescreener.MainActivity;
import screener.mosque.org.mosquescreener.R;
import screener.mosque.org.mosquescreener.model.Prayer;
import screener.mosque.org.mosquescreener.task.AsyncFlowGenerator;
import screener.mosque.org.mosquescreener.utils.HttpHandler;

import static android.content.ContentValues.TAG;

public abstract class PrayerAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, new Date() + " > Starting the flow");
        Log.d(TAG, new Date() + " > Prayer ================> " + getPrayer());
        Log.d(TAG, new Date() + " > Day of week ================> " + intent.getStringExtra("dayOfWeek"));
        Log.d(TAG, new Date() + " > Time for Adhan ================> " + new Date(intent.getLongExtra("prayerTimeScheduled", 0)));

        WebView advert1 = (WebView) MainActivity.getInstance().findViewById(R.id.advert1);
        advert1.setBackgroundColor(Color.TRANSPARENT);
        advert1.getSettings().setJavaScriptEnabled(true);
        advert1.loadUrl("https://mosquee-bilal-clichy-sous-bois.fr/annonce_1/");

        WebView advert2 = (WebView) MainActivity.getInstance().findViewById(R.id.advert2);
        advert2.getSettings().setJavaScriptEnabled(true);
        advert2.loadUrl("https://mosquee-bilal-clichy-sous-bois.fr/annonce_2/");

        new SetDuaaText().execute();

        new AsyncFlowGenerator(getPrayer(), intent.getStringExtra("dayOfWeek")).execute();

        long prayerTimeScheduled = intent.getLongExtra("prayerTimeScheduled", 0);
        long timeBeforeAdhan = new Date().getTime() - prayerTimeScheduled;

        startTimer(timeBeforeAdhan, 1000);
    }

    public void startTimer(final long finish, long tick) {
        CountDownTimer t;
        final TextView textTimer = (TextView) MainActivity.getInstance().findViewById(R.id.prayer_timer);
        textTimer.setVisibility(View.VISIBLE);
        t = new CountDownTimer(finish, tick) {

            public void onTick(long millisUntilFinished) {
                long remainedSecs = millisUntilFinished / 1000;
                textTimer.setText("" + (remainedSecs / 60) + ":" + (remainedSecs % 60));// manage it accordign to you
            }

            public void onFinish() {
                textTimer.setText("00:00:00");
                cancel();
            }
        }.start();
    }

    abstract Prayer getPrayer();

    private static class SetDuaaText extends AsyncTask<Void, Void, Void> {

        private String duaaText;

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String html = sh.makeServiceCall("https://mosquee-bilal-clichy-sous-bois.fr/duaa/");
            Document doc = Jsoup.parse(html);

            Elements duaaDiv = doc.getElementsByClass("entry-content");
            duaaText = duaaDiv.text();
            Log.d(TAG, new Date() + " > Duaa ================> " + duaaText);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            TextView duaa = (TextView) MainActivity.getInstance().findViewById(R.id.duaa_text);
            duaa.setText(duaaText);
        }
    }

}

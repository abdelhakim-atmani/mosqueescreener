package screener.mosque.org.mosquescreener.schedule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
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
        Log.d(TAG, new Date() + " > All parameters ================> " + intent.getExtras());

        new SetDuaaText().execute();

        new AsyncFlowGenerator(getPrayer(), intent.getStringExtra("dayOfWeek")).execute();

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

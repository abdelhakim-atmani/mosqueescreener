package screener.mosque.org.mosquescreener.task;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import screener.mosque.org.mosquescreener.MainActivity;
import screener.mosque.org.mosquescreener.R;
import screener.mosque.org.mosquescreener.model.Prayer;
import screener.mosque.org.mosquescreener.utils.HttpHandler;

import static android.content.ContentValues.TAG;
import static screener.mosque.org.mosquescreener.utils.DateHelper.SCHEDULE_PREPARE_PRAYER_TIME;
import static screener.mosque.org.mosquescreener.utils.DateHelper.dateTimeParse;
import static screener.mosque.org.mosquescreener.utils.DateHelper.dayOfWeek;
import static screener.mosque.org.mosquescreener.utils.DateHelper.isoTofullFormatDate;
import static screener.mosque.org.mosquescreener.utils.DateHelper.isoTofullFormatHijriDate;

public class GetPrayerTimeTask extends AsyncTask<Void, Void, Void> {

    public static boolean TEST_MODE = false;

    private static final String PRAYER_TIME_URL = "https://efmxeb1eoi.execute-api.eu-west-1.amazonaws.com/prod/prayer-time/";

    private ProgressDialog progressBar;
    private JSONObject prayerTimes;
    private String day;

    public GetPrayerTimeTask(String day) {
        this.progressBar = new ProgressDialog(MainActivity.getInstance());
        this.day = day;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setMessage("Please wait...");
        progressBar.setCancelable(false);
        progressBar.show();

    }

    @Override
    protected Void doInBackground(Void... arg0) {
        HttpHandler sh = new HttpHandler();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(PRAYER_TIME_URL + day);

        Log.e(TAG, "Response from url: " + jsonStr);

        if (jsonStr != null) {
            try {
                prayerTimes = new JSONObject(jsonStr);

            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        if (progressBar.isShowing()) {
            progressBar.dismiss();
        }

        String day = getProperty("day");
        String hegireDay = getProperty("hegireDate");

        setLabel(R.id.date_button, "\t\t\t " + isoTofullFormatDate(day) + "\t\t\t\t\t\t\t\t\t\u202B" + isoTofullFormatHijriDate(hegireDay, day));

        //setLabel(R.id.date_button, isoTofullFormatHijriDate(hegireDay));

        if(!TEST_MODE) {
            for (Prayer prayer : Prayer.values()) {
                String time = getProperty(prayer.name().toLowerCase());
                setLabel(prayer.getViewID(), time);
                scheduleTask(dateTimeParse(day + " " + time), prayer);
            }
        } else {
            Date now = new Date();
            for (Prayer prayer : Prayer.values()) {
                String time = getProperty(prayer.name().toLowerCase());
                setLabel(prayer.getViewID(), time);
                if(prayer.equals(Prayer.MAGHREB)) {
                    scheduleTask(now, prayer);
                }
                Calendar newTime = Calendar.getInstance();
                newTime.setTimeInMillis(now.getTime());
                if (prayer.equals(Prayer.MAGHREB))
                    newTime.add(Calendar.SECOND, 150);
                else
                    newTime.add(Calendar.SECOND, 10);
                now = new Date(newTime.getTimeInMillis());
            }
        }
    }

    private void scheduleTask(Date prayerTimeScheduled, Prayer prayer) {
        AlarmManager alarmMgr = (AlarmManager) MainActivity.getInstance().getSystemService(Context.ALARM_SERVICE);
        Date now = new Date();
        if(alarmMgr != null && prayerTimeScheduled.after(now)) {
            Intent intent = new Intent(MainActivity.getInstance(), prayer.getClassAlarm());
            intent.putExtra("dayOfWeek", dayOfWeek(prayerTimeScheduled).toLowerCase());
            intent.putExtra("prayerTimeScheduled", prayerTimeScheduled.getTime());
            Calendar time = Calendar.getInstance();
            time.setTimeInMillis(prayerTimeScheduled.getTime());
            time.add(Calendar.MINUTE, SCHEDULE_PREPARE_PRAYER_TIME);
            long timeScheduled;
            if(time.getTimeInMillis() < new Date().getTime()) {
                timeScheduled = new Date().getTime() + 10000;
            } else {
                timeScheduled = time.getTimeInMillis();
            }
            System.out.println("Prayer = " + prayer + " scheduled at " + new Date(timeScheduled));
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.getInstance(), 0, intent, 0);
            alarmMgr.setExact(AlarmManager.RTC_WAKEUP, timeScheduled, pendingIntent);
        }
    }

    private String getProperty(String key) {
        try {
            return prayerTimes.getString(key);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void setLabel(int viewId, String value) {
        View view = MainActivity.getInstance().findViewById(viewId);
        if(view instanceof TextView) {
            ((TextView) MainActivity.getInstance().findViewById(viewId)).setText(value);
        }
    }

}

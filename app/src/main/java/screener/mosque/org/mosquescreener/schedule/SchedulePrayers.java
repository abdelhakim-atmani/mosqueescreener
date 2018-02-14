package screener.mosque.org.mosquescreener.schedule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import screener.mosque.org.mosquescreener.task.GetPrayerTimeTask;

import static android.content.ContentValues.TAG;
import static screener.mosque.org.mosquescreener.utils.DateHelper.todayISO;

/**
 * Created by abdelhakim on 11/02/2018.
 */

public class SchedulePrayers extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "Start to schedule prayers.");
        new GetPrayerTimeTask(todayISO()).execute();
    }
}
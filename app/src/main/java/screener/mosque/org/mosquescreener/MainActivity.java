package screener.mosque.org.mosquescreener;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import screener.mosque.org.mosquescreener.schedule.SchedulePrayers;
import screener.mosque.org.mosquescreener.task.GetPrayerTimeTask;

import static android.content.ContentValues.TAG;
import static screener.mosque.org.mosquescreener.utils.DateHelper.todayISO;

public class MainActivity extends Activity {

    private static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*AudioManager audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);*/
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        instance = this;
        setContentView(R.layout.activity_main);


        TextView refreshPrayerTime = (TextView) findViewById(R.id.date_button);
        int[] color = {refreshPrayerTime.getCurrentTextColor(), Color.LTGRAY};
        float[] position = {0, 1};
        Shader.TileMode tile_mode = Shader.TileMode.MIRROR; // or TileMode.REPEAT;
        Shader shader_gradient = new LinearGradient(0, 0, 0, 50,color,position, tile_mode);
        refreshPrayerTime.getPaint().setShader(shader_gradient);

        refreshPrayerTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetPrayerTimeTask(todayISO()).execute();
            }
        });

        new GetPrayerTimeTask(todayISO()).execute();

        long currentDateTime = System.currentTimeMillis();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.HOUR, 24);
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 10);
        calendar.set(Calendar.SECOND, 0);

        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.getInstance(), SchedulePrayers.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(MainActivity.getInstance(), 0, intent, 0);

        if(alarmMgr != null) {
            alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, alarmIntent);
        }
        Log.e(TAG, "Time scheduled => " + new Date(calendar.getTimeInMillis()));
        Log.e(TAG, "Starting time => " + new Date(currentDateTime));
        Log.e(TAG, "Current time => " + new Date());

    }

    public static MainActivity getInstance() {
        return instance;
    }

}

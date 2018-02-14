package screener.mosque.org.mosquescreener.model;

import android.content.BroadcastReceiver;

import screener.mosque.org.mosquescreener.R;
import screener.mosque.org.mosquescreener.schedule.AsrAlarmReceiver;
import screener.mosque.org.mosquescreener.schedule.DohrAlarmReceiver;
import screener.mosque.org.mosquescreener.schedule.FajrAlarmReceiver;
import screener.mosque.org.mosquescreener.schedule.IshaaAlarmReceiver;
import screener.mosque.org.mosquescreener.schedule.MaghrebAlarmReceiver;

public enum Prayer {
    FAJR(R.id.fajr_time, R.id.fajr, FajrAlarmReceiver.class),
    DOHR(R.id.dohr_time, R.id.dohr, DohrAlarmReceiver.class),
    ASR(R.id.asr_time, R.id.asr, AsrAlarmReceiver.class),
    MAGHREB(R.id.maghreb_time, R.id.maghreb, MaghrebAlarmReceiver.class),
    ISHAA(R.id.ishaa_time, R.id.ishaa, IshaaAlarmReceiver.class);

    private final int viewID;
    private final int labelID;
    private final Class<? extends BroadcastReceiver> classAlarm;

    Prayer(final int viewID,
           final int labelID,
           final Class<? extends BroadcastReceiver> classAlarm) {
        this.viewID = viewID;
        this.labelID = labelID;
        this.classAlarm = classAlarm;
    }

    public int getViewID() {
        return viewID;
    }

    public int getLabelID() {
        return labelID;
    }

    public Class<? extends BroadcastReceiver> getClassAlarm() {
        return classAlarm;
    }
}

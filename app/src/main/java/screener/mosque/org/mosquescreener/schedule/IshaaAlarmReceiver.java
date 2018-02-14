package screener.mosque.org.mosquescreener.schedule;

import screener.mosque.org.mosquescreener.model.Prayer;

public class IshaaAlarmReceiver extends PrayerAlarmReceiver {

    @Override
    Prayer getPrayer() {
        return Prayer.ISHAA;
    }
}
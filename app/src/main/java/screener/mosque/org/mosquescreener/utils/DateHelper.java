package screener.mosque.org.mosquescreener.utils;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import static screener.mosque.org.mosquescreener.task.GetPrayerTimeTask.TEST_MODE;

public class DateHelper {

    public static final int SCHEDULE_PREPARE_PRAYER_TIME = -30;

    private static final DateFormat ISO_DATE_TIME_FORMAT = new SimpleDateFormat( "yyyy-MM-dd HH:mm" );

    private static final DateFormat ISO_DATE_FORMAT = new SimpleDateFormat( "yyyy-MM-dd" );

    private static final DateFormat DAY_OF_WEEK_FORMAT = new SimpleDateFormat( "EEEE", Locale.ENGLISH );

    private static final DateFormat FULL_DATE_FORMAT = DateFormat.getDateInstance(DateFormat.FULL, Locale.FRANCE);

    public static String todayISO() {
        return ISO_DATE_FORMAT.format(new Date());
    }

    public static String isoTofullFormatDate(String dateISO) {
        try {
            return FULL_DATE_FORMAT.format(ISO_DATE_FORMAT.parse(dateISO));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date dateTimeParse(String dateTime) {
        try {
            return ISO_DATE_TIME_FORMAT.parse(dateTime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static int minutesToMilliseconds(int minutes) {
        if(!TEST_MODE) {
            return minutes * 60 * 1000;
        } else {
            return  1* 10 * 1000;
        }
    }

    public static String dayOfWeek(Date date) {
        return DAY_OF_WEEK_FORMAT.format(date);
    }

}

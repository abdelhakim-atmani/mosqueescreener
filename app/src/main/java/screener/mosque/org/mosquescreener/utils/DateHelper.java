package screener.mosque.org.mosquescreener.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    public static String isoTofullFormatHijriDate(String dateISO, String gregorianDate) {
        String[] dateSplitted = dateISO.split("-");
        String day = dateSplitted[2];
        String month = HijriMonth.values()[Integer.parseInt(dateSplitted[1]) - 1].arabicLabel;
        String year = dateSplitted[0];
        String dayOfWeek;
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(ISO_DATE_FORMAT.parse(gregorianDate));
            dayOfWeek = ArabicDayOfWeek.values()[c.get(Calendar.DAY_OF_WEEK) - 1].arabicLabel;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return dayOfWeek + " " + day + " " + month + " " + year;
    }

    public static Date dateTimeParse(String dateTime) {
        try {
            return ISO_DATE_TIME_FORMAT.parse(dateTime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    static boolean isFirstTime = true;

    public static int minutesToMilliseconds(int minutes) {
        if(!TEST_MODE) {
            return minutes * 60 * 1000;
        } else {
            if(isFirstTime) {
                isFirstTime = false;
                return  10 * 1000;
            }
            return  10 * 1000;
        }
    }

    public static String dayOfWeek(Date date) {
        return DAY_OF_WEEK_FORMAT.format(date);
    }

    private enum HijriMonth {
        MOUHARRAM("Mouharram", "مُحَرَّم"),

        SAFAR("Safar", "صَفَر"),

        RABI_I("Rabia al awal", "رَبيع الأوّل"),

        RABI_II("Rabia ath-thani", "رَبيع الثاني"),

        JUMADA_I("Joumada al oula", "جُمادى الأولى"),

        JUMADA_II("Joumada ath-thania", "جُمادى الآخرة"),

        RAJAB("Rajab", "رَجَب"),

        SHABAN("Chaabane", "شَعْبان"),

        RAMADAN("Ramadan", "رَمَضان"),

        SHAWWAL("Chawwal", "شَوّال"),

        DHU_AL_QIDAH("Dhou al qi`da", "ذو القعدة"),

        DHU_AL_HIJJAH("Dhou al hijja", "ذو الحجة");

        private String label;
        private String arabicLabel;
        HijriMonth(String label, String arabicLabel) {
            this.label = label;
            this.arabicLabel = arabicLabel;
        }
    }
    private enum ArabicDayOfWeek {

        SUNDAY("الأحَد"),

        MONDAY("الإثْنَين"),

        TUESDAY("الثَلاثاء"),

        WEDNESDAY("الأربَعاء"),

        THURSDAY("الخَميس"),

        FRIDAY("الجُمُعة"),

        SATURDAY("السَبْت");

        private String arabicLabel;
        ArabicDayOfWeek(String arabicLabel) {
            this.arabicLabel = arabicLabel;
        }
    }

}

package screener.mosque.org.mosquescreener.animation;

import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import java.util.Arrays;
import java.util.List;

import screener.mosque.org.mosquescreener.MainActivity;
import screener.mosque.org.mosquescreener.R;
import screener.mosque.org.mosquescreener.model.Prayer;
import screener.mosque.org.mosquescreener.view.AutoScrollingTextView;

public class Animations {

    public static final int ANIMATION_DURATION = 3000;

    private static final Animation ZOOM_OUT = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    private static final Animation ZOOM_IN = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

    private static final Animation ZOOM_OUT_IQAMA = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    private static final Animation ZOOM_IN_IQAMA = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);


    private static final Animation ZOOM_OUT_ADVERT1 = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    private static final Animation ZOOM_IN_ADVERT1 = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

    private static final Animation ZOOM_OUT_ADVERT2 = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    private static final Animation ZOOM_IN_ADVERT2 = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

    private static final Animation ZOOM_OUT_TIME = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    private static final Animation ZOOM_IN_TIME = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

    private static final Animation ZOOM_OUT_DATE = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    private static final Animation ZOOM_IN_DATE = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

    static {
        ZOOM_OUT.setDuration(ANIMATION_DURATION);
        ZOOM_IN.setDuration(ANIMATION_DURATION);
        ZOOM_OUT_IQAMA.setDuration(ANIMATION_DURATION);
        ZOOM_IN_IQAMA.setDuration(ANIMATION_DURATION);
        ZOOM_OUT_ADVERT1.setDuration(ANIMATION_DURATION);
        ZOOM_IN_ADVERT1.setDuration(ANIMATION_DURATION);
        ZOOM_OUT_ADVERT2.setDuration(ANIMATION_DURATION);
        ZOOM_IN_ADVERT2.setDuration(ANIMATION_DURATION);
        ZOOM_OUT_TIME.setDuration(ANIMATION_DURATION);
        ZOOM_IN_TIME.setDuration(ANIMATION_DURATION);
        ZOOM_OUT_DATE.setDuration(ANIMATION_DURATION);
        ZOOM_IN_DATE.setDuration(ANIMATION_DURATION);
    }

    private final static ReversibleAnimation DATE_TEXT_TOP_BAR_ANIMATION = new ViewReversibleAnimation(R.id.date_button, ZOOM_IN_DATE, ZOOM_OUT_DATE);

    private final static ReversibleAnimation CLOCK_TOP_BAR_ANIMATION = new ViewReversibleAnimation(R.id.current_time, ZOOM_IN_TIME, ZOOM_OUT_TIME);


    private final static ReversibleAnimation FAJR_TIME_ANIMATION = new AnimatableReverseAnimation(Prayer.FAJR.getViewID(), R.drawable.animation_prayer_time, R.drawable.animation_prayer_time_reverse);
    private final static ReversibleAnimation FAJR_LABEL_ANIMATION = new AnimatableReverseAnimation(Prayer.FAJR.getLabelID(), R.drawable.animation_prayer_label, R.drawable.animation_prayer_label_reverse);

    private final static ReversibleAnimation DOHR_TIME_ANIMATION = new AnimatableReverseAnimation(Prayer.DOHR.getViewID(), R.drawable.animation_prayer_time, R.drawable.animation_prayer_time_reverse);
    private final static ReversibleAnimation DOHR_LABEL_ANIMATION = new AnimatableReverseAnimation(Prayer.DOHR.getLabelID(), R.drawable.animation_prayer_label, R.drawable.animation_prayer_label_reverse);

    private final static ReversibleAnimation ASR_TIME_ANIMATION = new AnimatableReverseAnimation(Prayer.ASR.getViewID(), R.drawable.animation_prayer_time, R.drawable.animation_prayer_time_reverse);
    private final static ReversibleAnimation ASR_LABEL_ANIMATION = new AnimatableReverseAnimation(Prayer.ASR.getLabelID(), R.drawable.animation_prayer_label, R.drawable.animation_prayer_label_reverse);

    private final static ReversibleAnimation MAGHREB_TIME_ANIMATION = new AnimatableReverseAnimation(Prayer.MAGHREB.getViewID(), R.drawable.animation_prayer_time, R.drawable.animation_prayer_time_reverse);
    private final static ReversibleAnimation MAGHREB_LABEL_ANIMATION = new AnimatableReverseAnimation(Prayer.MAGHREB.getLabelID(), R.drawable.animation_prayer_label, R.drawable.animation_prayer_label_reverse);

    private final static ReversibleAnimation ISHAA_TIME_ANIMATION = new AnimatableReverseAnimation(Prayer.ISHAA.getViewID(), R.drawable.animation_prayer_time, R.drawable.animation_prayer_time_reverse);
    private final static ReversibleAnimation ISHAA_LABEL_ANIMATION = new AnimatableReverseAnimation(Prayer.ISHAA.getLabelID(), R.drawable.animation_prayer_label, R.drawable.animation_prayer_label_reverse);


    public final static ReversibleAnimation DUAA_TEXT_ANIMATION = (AutoScrollingTextView) MainActivity.getInstance().findViewById(R.id.duaa_text);

    public final static ReversibleAnimation DATE_TIME_TOP_BAR_ANIMATION = new ComposedAnimation(DATE_TEXT_TOP_BAR_ANIMATION, CLOCK_TOP_BAR_ANIMATION);

    public final static ReversibleAnimation FAJR_COLUMN_ANIMATION = new ComposedAnimation(FAJR_LABEL_ANIMATION, FAJR_TIME_ANIMATION);
    public final static ReversibleAnimation DOHR_COLUMN_ANIMATION = new ComposedAnimation(DOHR_LABEL_ANIMATION, DOHR_TIME_ANIMATION);
    public final static ReversibleAnimation ASR_COLUMN_ANIMATION = new ComposedAnimation(ASR_LABEL_ANIMATION, ASR_TIME_ANIMATION);
    public final static ReversibleAnimation MAGHREB_COLUMN_ANIMATION = new ComposedAnimation(MAGHREB_LABEL_ANIMATION, MAGHREB_TIME_ANIMATION);
    public final static ReversibleAnimation ISHAA_COLUMN_ANIMATION = new ComposedAnimation(ISHAA_LABEL_ANIMATION, ISHAA_TIME_ANIMATION);

    public final static ReversibleAnimation PRAYER_TABLE_ANIMATION = new DependingPrayerAnimation();

    public final static ReversibleAnimation MAIN_BG_ANIMATION = new AnimatableReverseAnimation(R.id.main_frame, R.drawable.animation_bkg, R.drawable.animation_bkg_reverse);
    public final static ReversibleAnimation MAIN_BG_IKAMA_ANIMATION = new AnimatableReverseAnimation(R.id.main_frame, R.drawable.animation_bkg_ikama, R.drawable.animation_bkg_ikama_reverse);

    public final static ReversibleAnimation ADHAN_LABEL_ANIMATION = new ViewReversibleAnimation(R.id.adhan_text, ZOOM_OUT, ZOOM_IN);

    public final static ReversibleAnimation IQAMA_LABEL_ANIMATION = new ViewReversibleAnimation(R.id.iqama_text, ZOOM_OUT_IQAMA, ZOOM_IN_IQAMA);

    public final static ReversibleAnimation ADVERT1_ANIMATION = new ViewReversibleAnimation(R.id.advert1, ZOOM_OUT_ADVERT1, ZOOM_IN_ADVERT1);
    public final static ReversibleAnimation ADVERT2_ANIMATION = new ViewReversibleAnimation(R.id.advert2, ZOOM_OUT_ADVERT2, ZOOM_IN_ADVERT2);

    public final static List<ReversibleAnimation> ALL_ANIMATIONS_LIST = Arrays.asList(
            DUAA_TEXT_ANIMATION,
            DATE_TIME_TOP_BAR_ANIMATION,
            PRAYER_TABLE_ANIMATION,
            MAIN_BG_ANIMATION,
            MAIN_BG_IKAMA_ANIMATION,
            ADHAN_LABEL_ANIMATION,
            IQAMA_LABEL_ANIMATION,
            ADVERT1_ANIMATION,
            ADVERT2_ANIMATION);

}

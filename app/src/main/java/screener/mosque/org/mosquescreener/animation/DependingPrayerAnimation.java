package screener.mosque.org.mosquescreener.animation;

import screener.mosque.org.mosquescreener.model.Prayer;

import static screener.mosque.org.mosquescreener.animation.Animations.ASR_COLUMN_ANIMATION;
import static screener.mosque.org.mosquescreener.animation.Animations.DOHR_COLUMN_ANIMATION;
import static screener.mosque.org.mosquescreener.animation.Animations.FAJR_COLUMN_ANIMATION;
import static screener.mosque.org.mosquescreener.animation.Animations.ISHAA_COLUMN_ANIMATION;
import static screener.mosque.org.mosquescreener.animation.Animations.MAGHREB_COLUMN_ANIMATION;

class DependingPrayerAnimation extends StatedReversibleAnimation {

    @Override
    void performAnimation(Prayer prayer) {
        getAnimation(prayer).start(prayer);
    }

    @Override
    void reverseAnimation(Prayer prayer) {
        getAnimation(prayer).reverse(prayer);
    }

    private ReversibleAnimation getAnimation(Prayer prayer) {
        switch (prayer){
            case FAJR:
                return FAJR_COLUMN_ANIMATION;
            case DOHR:
                return DOHR_COLUMN_ANIMATION;
            case ASR:
                return ASR_COLUMN_ANIMATION;
            case MAGHREB:
                return MAGHREB_COLUMN_ANIMATION;
            case ISHAA:
                return ISHAA_COLUMN_ANIMATION;
        }
        return null;
    }
}

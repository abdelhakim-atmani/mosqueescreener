package screener.mosque.org.mosquescreener.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import screener.mosque.org.mosquescreener.animation.ComposedAnimation;
import screener.mosque.org.mosquescreener.animation.ReversibleAnimation;
import screener.mosque.org.mosquescreener.animation.Revert;

import static screener.mosque.org.mosquescreener.animation.Animations.ADHAN_LABEL_ANIMATION;
import static screener.mosque.org.mosquescreener.animation.Animations.ADVERT1_ANIMATION;
import static screener.mosque.org.mosquescreener.animation.Animations.ADVERT2_ANIMATION;
import static screener.mosque.org.mosquescreener.animation.Animations.ALL_ANIMATIONS_LIST;
import static screener.mosque.org.mosquescreener.animation.Animations.DATE_TIME_TOP_BAR_ANIMATION;
import static screener.mosque.org.mosquescreener.animation.Animations.DUAA_TEXT_ANIMATION;
import static screener.mosque.org.mosquescreener.animation.Animations.IQAMA_LABEL_ANIMATION;
import static screener.mosque.org.mosquescreener.animation.Animations.MAIN_BG_ANIMATION;
import static screener.mosque.org.mosquescreener.animation.Animations.MAIN_BG_IKAMA_ANIMATION;
import static screener.mosque.org.mosquescreener.animation.Animations.PRAYER_TABLE_ANIMATION;

public enum ScreenType {
    ANNONCE1(DATE_TIME_TOP_BAR_ANIMATION, ADVERT1_ANIMATION),
    ADHAN(DATE_TIME_TOP_BAR_ANIMATION, PRAYER_TABLE_ANIMATION, ADHAN_LABEL_ANIMATION, MAIN_BG_ANIMATION),
    DOUAA(DATE_TIME_TOP_BAR_ANIMATION, PRAYER_TABLE_ANIMATION, DUAA_TEXT_ANIMATION, MAIN_BG_ANIMATION),
    IKAMA(DATE_TIME_TOP_BAR_ANIMATION, PRAYER_TABLE_ANIMATION, IQAMA_LABEL_ANIMATION, MAIN_BG_IKAMA_ANIMATION),
    ANNONCE2(DATE_TIME_TOP_BAR_ANIMATION, ADVERT2_ANIMATION),
    RESET;

    private ReversibleAnimation animation;

    ScreenType(ReversibleAnimation... animations) {
        List<ReversibleAnimation> animationToBePerformed = new ArrayList<>();
        List<ReversibleAnimation> animationList = Arrays.asList(animations);
        for(ReversibleAnimation animationTemp : ALL_ANIMATIONS_LIST) {
            if(animationList.contains(animationTemp)) {
                animationToBePerformed.add(animationTemp);
            } else {
                animationToBePerformed.add(Revert.of(animationTemp));
            }
        }
        this.animation = new ComposedAnimation(animationToBePerformed);
    }

    public ReversibleAnimation getAnimation() {
        return animation;
    }

    public static ScreenType getScreenTypeByName(String name) {
        for (ScreenType screenType : ScreenType.values()) {
            if(screenType.name().equalsIgnoreCase(name)) {
                return screenType;
            }
        }
        return null;
    }

}

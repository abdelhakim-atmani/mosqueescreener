package screener.mosque.org.mosquescreener.animation;

import screener.mosque.org.mosquescreener.model.Prayer;

public interface AnimationConditional {

    boolean isApplicable(Prayer prayer);
}

package screener.mosque.org.mosquescreener.animation;

import screener.mosque.org.mosquescreener.model.Prayer;

/**
 * Created by abdelhakim on 27/01/2018.
 */

public class OppositeReversibleAnimation implements ReversibleAnimation {

    private final ReversibleAnimation animation;

    public OppositeReversibleAnimation(final ReversibleAnimation animation) {
        this.animation = animation;
    }

    @Override
    public void start(Prayer prayer) {
        animation.reverse(prayer);
    }

    @Override
    public void reverse(Prayer prayer) {
        //animation.start();
    }
}

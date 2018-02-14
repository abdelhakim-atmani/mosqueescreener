package screener.mosque.org.mosquescreener.animation;

import screener.mosque.org.mosquescreener.model.Prayer;

public abstract class StatedReversibleAnimation implements ReversibleAnimation, AnimationStated {

    private boolean isStarted;

    public boolean isStarted() {
        return isStarted;
    }

    @Override
    public final void start(Prayer prayer) {
        if(!isStarted()) {
            isStarted = true;
            performAnimation(prayer);
        }
    }

    @Override
    public final void reverse(Prayer prayer) {
        if(isStarted()) {
            isStarted = false;
            reverseAnimation(prayer);
        }
    }



    abstract void performAnimation(Prayer prayer);

    abstract void reverseAnimation(Prayer prayer);
}

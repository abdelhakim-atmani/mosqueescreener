package screener.mosque.org.mosquescreener.animation;

import android.graphics.drawable.AnimationDrawable;
import android.view.View;

import screener.mosque.org.mosquescreener.MainActivity;
import screener.mosque.org.mosquescreener.model.Prayer;

public class AnimatableReverseAnimation extends StatedReversibleAnimation {

    private final View animatedView;
    private final int animation;
    private final int reverseAnimation;


    AnimatableReverseAnimation(int viewAnimated, int animationId, int reverseAnimationId) {
        this.animatedView = MainActivity.getInstance().findViewById(viewAnimated);
        this.animation = animationId;
        this.reverseAnimation = reverseAnimationId;
    }

    @Override
    void performAnimation(Prayer prayer) {
        animatedView.setBackgroundResource(animation);
        ((AnimationDrawable) animatedView.getBackground()).setOneShot(true);
        ((AnimationDrawable) animatedView.getBackground()).setEnterFadeDuration(100);
        ((AnimationDrawable) animatedView.getBackground()).setExitFadeDuration(Animations.ANIMATION_DURATION);
        ((AnimationDrawable) animatedView.getBackground()).start();
    }

    @Override
    void reverseAnimation(Prayer prayer) {
        animatedView.setBackgroundResource(reverseAnimation);
        ((AnimationDrawable) animatedView.getBackground()).setOneShot(true);
        ((AnimationDrawable) animatedView.getBackground()).setEnterFadeDuration(100);
        ((AnimationDrawable) animatedView.getBackground()).setExitFadeDuration(Animations.ANIMATION_DURATION);
        ((AnimationDrawable) animatedView.getBackground()).start();
    }
}

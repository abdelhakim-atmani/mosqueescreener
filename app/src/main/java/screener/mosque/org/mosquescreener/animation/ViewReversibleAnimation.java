package screener.mosque.org.mosquescreener.animation;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import screener.mosque.org.mosquescreener.MainActivity;
import screener.mosque.org.mosquescreener.model.Prayer;

public class ViewReversibleAnimation extends StatedReversibleAnimation {

    private final View viewAnimated;
    private final Animation animationForView;
    private final Animation reverseAnimationForView;

    ViewReversibleAnimation(int viewIdAnimated, int animationIdForView, int reverseAnimationIdForView) {
        this(viewIdAnimated,
                AnimationUtils.loadAnimation(MainActivity.getInstance(), animationIdForView),
                AnimationUtils.loadAnimation(MainActivity.getInstance(), reverseAnimationIdForView));
    }

    ViewReversibleAnimation(int viewIdAnimated, Animation animationForView, Animation reverseAnimationForView) {
        this.viewAnimated = MainActivity.getInstance().findViewById(viewIdAnimated);
        this.animationForView = animationForView;
        this.reverseAnimationForView = reverseAnimationForView;
        animationForView.setFillAfter(true);
        reverseAnimationForView.setFillAfter(true);
    }

    @Override
    void performAnimation(Prayer prayer) {
        viewAnimated.startAnimation(animationForView);
    }

    @Override
    void reverseAnimation(Prayer prayer) {
        viewAnimated.startAnimation(reverseAnimationForView);
    }
}

package screener.mosque.org.mosquescreener.animation;

import java.util.Arrays;
import java.util.List;

import screener.mosque.org.mosquescreener.model.Prayer;

public class ComposedAnimation implements ReversibleAnimation {

    private final List<ReversibleAnimation> animations;


    public ComposedAnimation(List<ReversibleAnimation> animations) {
        this.animations = animations;
    }

    public ComposedAnimation(ReversibleAnimation... animations) {
        this(Arrays.asList(animations));
    }

    @Override
    public void start(Prayer prayer) {
        for (ReversibleAnimation animation : animations) {
            animation.start(prayer);
        }
    }

    @Override
    public void reverse(Prayer prayer) {
        for (ReversibleAnimation animation : animations) {
            animation.reverse(prayer);
        }
    }
}

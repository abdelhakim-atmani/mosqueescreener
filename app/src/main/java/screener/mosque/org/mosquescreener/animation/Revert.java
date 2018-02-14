package screener.mosque.org.mosquescreener.animation;

/**
 * Created by abdelhakim on 27/01/2018.
 */

public class Revert {

    public static ReversibleAnimation of(ReversibleAnimation animation) {
        return new OppositeReversibleAnimation(animation);
    }

}

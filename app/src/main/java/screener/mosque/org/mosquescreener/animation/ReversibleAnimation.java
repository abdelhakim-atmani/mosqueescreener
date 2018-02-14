package screener.mosque.org.mosquescreener.animation;

import screener.mosque.org.mosquescreener.model.Prayer;

public interface ReversibleAnimation {

    void start(Prayer prayer);

    void reverse(Prayer prayer);
}

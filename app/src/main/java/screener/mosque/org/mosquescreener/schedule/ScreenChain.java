package screener.mosque.org.mosquescreener.schedule;

import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import screener.mosque.org.mosquescreener.model.Prayer;
import screener.mosque.org.mosquescreener.model.ScreenType;

import static android.content.ContentValues.TAG;

public class ScreenChain implements Chain {

    private final ScreenType screenType;
    private Chain nextChain;
    private final Prayer prayer;
    private int duration;

    private ScreenChain(Prayer prayer, ScreenType screenType, int duration) {
        this.screenType = screenType;
        this.duration = duration;
        this.prayer = prayer;
    }

    private void setNextChain(Chain nextChain) {
        this.nextChain = nextChain;
    }

    @Override
    public void proceed() {
        Log.d(TAG, new Date() + " > Starting the " + screenType.name());
        screenType.getAnimation().start(prayer);
        if(nextChain != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    nextChain.proceed();
                }
            }, duration);
        }
    }

    public String toString() {
        if(nextChain != null)
            return screenType.name() + "   =====>   " + nextChain.toString();
        else
            return screenType.name();
    }

    public static class ScreenChainBuilder {

        private final List<ChainDefinition> screenFlow = new ArrayList<>();
        private Prayer prayer;

        public ScreenChainBuilder nextScreen(Prayer prayer, ScreenType screenType, int duration) {
            screenFlow.add(new ChainDefinition(screenType, duration));
            this.prayer = prayer;
            return this;
        }

        public ScreenChain build() {
            ScreenChain rootChain = new ScreenChain(prayer, screenFlow.get(0).screenType, screenFlow.get(0).duration);
            ScreenChain previousChain = rootChain;
            for(int i = 1; i < screenFlow.size(); i++) {
                ScreenChain nextChain = new ScreenChain(prayer, screenFlow.get(i).screenType, screenFlow.get(i).duration);
                previousChain.setNextChain(nextChain);
                previousChain = nextChain;
            }
            return rootChain;
        }

        @Override
        public String toString() {
            return screenFlow.toString();
        }

    }

    private static class ChainDefinition {
        private int duration;
        private ScreenType screenType;

        private ChainDefinition(ScreenType screenType, int duration) {
            this.duration = duration;
            this.screenType = screenType;
        }

        @Override
        public String toString () {
            return this.screenType + "    =>      " + this.duration;
        }

    }
}

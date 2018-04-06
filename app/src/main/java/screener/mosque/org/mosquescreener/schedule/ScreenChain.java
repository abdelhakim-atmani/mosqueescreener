package screener.mosque.org.mosquescreener.schedule;

import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import screener.mosque.org.mosquescreener.MainActivity;
import screener.mosque.org.mosquescreener.R;
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
        final ScreenChain me = this;
        Log.d(TAG, new Date() + " > Starting the " + screenType.name());
        loadView();
        screenType.getAnimation().start(prayer);
        if(nextChain != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    me.clear();
                    nextChain.proceed();
                }
            }, duration);
        }
    }

    private void loadView() {
        if(screenType.equals(ScreenType.ANNONCE1)) {
            WebView advert1 = (WebView) MainActivity.getInstance().findViewById(R.id.advert1);
            advert1.setBackgroundColor(Color.TRANSPARENT);
            advert1.getSettings().setJavaScriptEnabled(true);
            advert1.getSettings().setMediaPlaybackRequiresUserGesture(false);
            advert1.setWebChromeClient(new WebChromeClient());
            advert1.loadUrl("https://mosquee-bilal-clichy-sous-bois.fr/annonce_1/");
        } else if(screenType.equals(ScreenType.ANNONCE2)) {
            WebView advert2 = (WebView) MainActivity.getInstance().findViewById(R.id.advert2);
            advert2.setBackgroundColor(Color.TRANSPARENT);
            advert2.getSettings().setJavaScriptEnabled(true);
            advert2.getSettings().setMediaPlaybackRequiresUserGesture(false);
            advert2.setWebChromeClient(new WebChromeClient());
            advert2.loadUrl("https://mosquee-bilal-clichy-sous-bois.fr/annonce_2/");
        }
    }

    private void clear() {
        if(screenType.equals(ScreenType.ANNONCE1)) {
            WebView advert1 = (WebView) MainActivity.getInstance().findViewById(R.id.advert1);
            advert1.loadUrl("about:blank");
        } else if(screenType.equals(ScreenType.ANNONCE2)) {
            WebView advert2 = (WebView) MainActivity.getInstance().findViewById(R.id.advert2);
            advert2.loadUrl("about:blank");
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

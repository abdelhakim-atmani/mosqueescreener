package screener.mosque.org.mosquescreener.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import android.widget.TextView;

import screener.mosque.org.mosquescreener.R;
import screener.mosque.org.mosquescreener.animation.AnimationStated;
import screener.mosque.org.mosquescreener.animation.ReversibleAnimation;
import screener.mosque.org.mosquescreener.model.Prayer;

@SuppressLint("AppCompatCustomView")
public class AutoScrollingTextView extends TextView implements ReversibleAnimation, AnimationStated {

    private static final float DEFAULT_SPEED = 65.0f;
    public Scroller scroller;
    public float speed = DEFAULT_SPEED;
    public boolean continuousScrolling = true;
    public boolean isStarted = false;

    public AutoScrollingTextView(Context context) {
        super(context);
        init(null, 0);
        scrollerInstance(context);
    }

    public AutoScrollingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
        scrollerInstance(context);
    }

    public AutoScrollingTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
                                 int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr);
        scrollerInstance(context);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray attrArray = getContext().obtainStyledAttributes(attrs, R.styleable.AutoScrollingTextView,
                defStyleAttr, 0);
        initAttributes(attrArray);
    }

    protected void initAttributes(TypedArray attrArray) {
        String textStyle = attrArray.getString(R.styleable.AutoScrollingTextView_myTextStyle);
        if (textStyle != null && !textStyle.equals("")) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), textStyle);
            setTypeface(tf);
        }

    }

    public void scrollerInstance(Context context) {
        scroller = new Scroller(context, new LinearInterpolator());
        setScroller(scroller);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (scroller.isFinished() && isStarted) {
            scroll();
        }
    }

    public void scroll() {
        int viewHeight = getHeight();
        int visibleHeight = viewHeight - getPaddingBottom() - getPaddingTop();
        int lineHeight = getLineHeight();
        int offset = -1 * visibleHeight;
        int distance = visibleHeight + getLineCount() * lineHeight;
        int duration = (int) (distance * speed);
        scroller.startScroll(0, offset, 0, distance, duration);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (null == scroller)
            return;
        if (scroller.isFinished() && continuousScrolling && isStarted) {
            scroll();
        }
    }

    @Override    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null == scroller)
            return;
        if (scroller.isFinished() && continuousScrolling && isStarted) {
            scroll();
        }
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }

    public void setContinuousScrolling(boolean continuousScrolling) {
        this.continuousScrolling = continuousScrolling;
    }

    public boolean isContinuousScrolling() {
        return continuousScrolling;
    }

    public void start() {
        this.setVisibility(VISIBLE);
        this.setMovementMethod(new ScrollingMovementMethod());
        this.isStarted = true;
        this.scroll();
    }

    public void stop() {
        this.isStarted = false;
    }

    @Override
    public void start(Prayer prayer) {
        this.start();
    }

    @Override
    public void reverse(Prayer prayer) {
        this.stop();
        this.setVisibility(INVISIBLE);
    }


    @Override
    public boolean isStarted() {
        return isStarted;
    }


}
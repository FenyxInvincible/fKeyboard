
package loc.fenyx.keyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class LatinKeyboardView extends KeyboardView {

    static final int KEYCODE_OPTIONS = -100;
    static final int KEYCODE_LANG = -404;
    static final int KEYCODE_EMOJI = -303;
    static final int VOICE_INPUT = -4;
    private MotionEvent lastTouchEvent;

    public LatinKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLongClickable(true);
    }

    public LatinKeyboardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLongClickable(true);
    }


    @Override
    protected boolean onLongPress(Key key) {

        if (key.codes[0] == Keyboard.KEYCODE_CANCEL) {
            getOnKeyboardActionListener().onKey(KEYCODE_OPTIONS, null);
            return true;
        } else {

            if (!key.repeatable && lastTouchEvent != null) {

                ((SoftKeyboard) getContext()).longTouch(key);

                onTouchEvent(lastTouchEvent);

                this.dispatchSetPressed(false);
            }
            return super.onLongPress(key);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent me) {

        if (lastTouchEvent != null) {
            lastTouchEvent.recycle();
        }

        if (me.getAction() == MotionEvent.ACTION_DOWN) {

            MotionEvent m = MotionEvent.obtain(me.getEventTime(), me.getEventTime(), MotionEvent.ACTION_DOWN,
                    me.getX(), me.getY(), me.getPressure(), me.getSize(), me.getMetaState(),
                    me.getXPrecision(), me.getYPrecision(), me.getDeviceId(), me.getEdgeFlags()
            );

            lastTouchEvent = m;

        } else if (me.getAction() == MotionEvent.ACTION_MOVE) {

            MotionEvent m = MotionEvent.obtain(me.getEventTime(), me.getEventTime(), MotionEvent.ACTION_DOWN,
                    me.getX(), me.getY(), me.getPressure(), me.getSize(), me.getMetaState(),
                    me.getXPrecision(), me.getYPrecision(), me.getDeviceId(), me.getEdgeFlags()
            );

            lastTouchEvent = m;
        } else if (me.getAction() == MotionEvent.ACTION_UP) {

            //((SoftKeyboard)getContext()).touchUp();

            lastTouchEvent = null;
        }

        return super.onTouchEvent(me);


    }

}
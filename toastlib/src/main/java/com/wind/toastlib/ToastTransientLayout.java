package com.wind.toastlib;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by wind on 2018/1/19.
 */

public class ToastTransientLayout extends AbsTransientLayout {
    public ToastTransientLayout(@NonNull Context context,String text) {
        super(context);
        init(text);
    }
    public ToastTransientLayout(@NonNull Context context) {
        super(context);
        init("");
    }

    public ToastTransientLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init("");
    }

    public ToastTransientLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init("");
    }

    private TextView tvMsg;
    private void init(String text){
        inflate(getContext(), R.layout.layout_toast_adapter,this);
        tvMsg=findViewById(R.id.tv);
        tvMsg.setText(text);
    }


    @Override
    public void animateContentIn(int delay, int duration) {
        tvMsg.setAlpha(0f);
        tvMsg.animate()
                .alpha(1f)
                .translationY(dip2px(getContext(),10))
                .setDuration(duration)
                .setStartDelay(delay)
                .start();
    }

    @Override
    public void animateContentOut(int delay, int duration) {
        tvMsg.animate()
                .alpha(0f)
                .setDuration(duration)
                .setStartDelay(delay)
                .start();
    }
    public static int dip2px(Context context, float dipValue) {
        if (context==null){
            return (int) dipValue;
        }
        final float density = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * density + 0.5f);
    }
}

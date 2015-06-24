package com.cristaliza.alimentacion.custom.custom_list;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

public final class AnimationUtils {

    public static void expand(final View v, final int minHeight, final int maxHeight, final int duration) {
        v.getLayoutParams().height = minHeight;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = minHeight + (int) ((maxHeight - minHeight) * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setInterpolator(new LinearInterpolator());
        a.setDuration(duration);
        v.startAnimation(a);
    }

    public static void collapse(final View v, final int minHeight, final int maxHeight, final int duration, final boolean goneAfter) {
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(goneAfter && interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else {
                    v.getLayoutParams().height = maxHeight - (int) ((maxHeight - minHeight) * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setInterpolator(new LinearInterpolator());
        a.setDuration(duration);
        v.startAnimation(a);
    }
}

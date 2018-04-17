package com.potato.wedges;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

public class Utils {

    // The public static function which can be called from other classes
    public static void darkenStatusBar(Activity activity, int color) {
            activity.getWindow().setStatusBarColor(
                    darkenColor(
                            ContextCompat.getColor(activity, color)));
    }

    private static int darkenColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f;
        return Color.HSVToColor(hsv);
    }


}

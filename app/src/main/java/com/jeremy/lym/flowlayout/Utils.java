package com.jeremy.lym.flowlayout;

import android.content.res.Resources;
import android.util.TypedValue;

public class Utils {
    public static float dpToPixel(int dip){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, dip, Resources.getSystem().getDisplayMetrics());
    }
}

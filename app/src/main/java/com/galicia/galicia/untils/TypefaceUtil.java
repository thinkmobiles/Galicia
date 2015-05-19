package com.galicia.galicia.untils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by Sasha on 23.01.2015.
 */
public abstract class TypefaceUtil {
    /**
     * Using reflection to override default typeface
     * @param context to work with assets
     * @param defaultFontNameToOverride for example "monospace"
     * @param customFontFileNameInAssets file name of the font from assets
     */
    public static void overrideFont(Context context, String defaultFontNameToOverride, String customFontFileNameInAssets) {
        try {
            final Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(), customFontFileNameInAssets);

            final Field defaultFontTypefaceField = Typeface.class.getDeclaredField(defaultFontNameToOverride);
            defaultFontTypefaceField.setAccessible(true);
            defaultFontTypefaceField.set(null, customFontTypeface);
        } catch (Exception e) {
            Log.e("Font error", "Can not set custom font " + customFontFileNameInAssets + " instead of " + defaultFontNameToOverride);
        }
    }
}

package com.aviator.elearning.aviator.main;

import android.content.Context;
import android.graphics.Typeface;

public class CustomFonts {
    public static Typeface LoveYaLikeASister(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/LoveYaLikeASister.ttf");
    }

    public static Typeface Reckoner_Bold(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Reckoner_Bold.ttf");
    }
}

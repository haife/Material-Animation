package com.haife.soucesproject.material_animation;

import android.databinding.BindingAdapter;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;
import java.io.Serializable;

public class Sample implements Serializable{
    final int color;
    private final String name;
    public Sample(@ColorRes int color, String name) {
        this.color = color;
        this.name = name;
    }
    @BindingAdapter("colorTint")
    public static void setColorTint(ImageView view, @ColorInt int color) {
        DrawableCompat.setTint(view.getDrawable(), color);
    }



    public int getColor() {
        return color;
    }

    public String getName() {
        return name;
    }
}

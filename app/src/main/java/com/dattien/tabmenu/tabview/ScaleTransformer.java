package com.dattien.tabmenu.tabview;

import android.view.View;


/**
 * Created by FRAMGIA\bui.tien.dat on 31/08/2017.
 */
// >=== #123455
public class ScaleTransformer implements CircleLayoutManager.ItemTransformer {

    public final float SCALE = ChapterTabView.SCALE;
    float dx = 0;
    float dy = 0;
    private float radius = 0;

    @Override
    public void transformItem(CircleLayoutManager layoutManager, View item, float fraction) {

        int width = item.getWidth();
        int height = item.getHeight();
        radius = layoutManager.getWidth() * ChapterTabView.RADIUS_RATIO;
        dx = item.getX() - layoutManager.getWidth() / 2 + width / 2;
        dy = (radius - (radius * radius) / (float) Math.sqrt(radius * radius + dx * dx));
        item.setPivotX(width / 2.f);
        item.setPivotY(height / 2f);
        float scale = 1 - SCALE * Math.abs(fraction);
        item.setScaleX(scale + SCALE);
        item.setScaleY(scale + SCALE);
        if (fraction < 0) {
            item.setTranslationX(-((1 - scale) * width / 2.0f));
        }
        if (fraction > 0) {
            item.setTranslationX(((1 - scale) * width / 2.0f));
        }
        item.setTranslationY(dy);
    }

}
// <=== #123455

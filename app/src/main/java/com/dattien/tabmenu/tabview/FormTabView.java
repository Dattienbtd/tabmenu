package com.dattien.tabmenu.tabview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.dattien.tabmenu.R;

import static com.dattien.tabmenu.tabview.ChapterTabView.RADIUS_RATIO;

/**
 * Created by FRAMGIA\bui.tien.dat on 22/08/2017.
 */
// >=== #123455
public class FormTabView extends View {

    private Paint arcPaint;
    private float radius = RADIUS_RATIO;
    private float scale = 0.5f;
    private int margin = 10;

    public FormTabView(Context context) {
        super(context);
        initialize();
    }

    public FormTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public FormTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(10f);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        int width = canvas.getWidth();
        int height = canvas.getHeight();
        float left = -(width * radius - width / 2);
        float right = width + (width * radius - width / 2);
        float bottom = width * radius * 2;
        final RectF arcBoundsTop = new RectF(left, (height - height * scale) / 2 - margin, right, bottom + (height - height * scale) / 2 - margin);
        final RectF arcBoundsBottom = new RectF(left + height * scale, (height - height * scale) / 2 + height * scale + margin, right - height * scale, bottom - ((height - height * scale) / 2 + height * scale) - margin);
        // Draw the arc
        arcPaint.setStrokeWidth(height + margin * 4);
        arcPaint.setColor(Color.WHITE);
        canvas.drawArc(arcBoundsBottom, 0f, 360f, false, arcPaint);

        arcPaint.setStrokeWidth(10f);
        arcPaint.setColor(getResources().getColor(R.color.tabbar_chapter));
        canvas.drawArc(arcBoundsTop, 0f, 360f, false, arcPaint);
        canvas.drawArc(arcBoundsBottom, 0f, 360f, false, arcPaint);

    }
}
// <=== #123455

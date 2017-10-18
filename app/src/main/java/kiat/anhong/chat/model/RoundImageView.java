package kiat.anhong.chat.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import kiat.anhong.chat.R;

/*
 * Copyright (C) 2017 The KiaT Project
 * Created by KiaT on Oct 18
 */

@SuppressLint("AppCompatCustomView")
public class RoundImageView extends ImageView {
    private Path clipPath;
    private RectF rect;

    private boolean isRound;


    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);

        isRound = typedArray.getBoolean(R.styleable.RoundImageView_isRound, true);

        typedArray.recycle();
        clipPath = new Path();
        rect = new RectF(0, 0, this.getWidth(), this.getHeight());
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (isRound) {
            if (rect.right != this.getWidth() || rect.bottom != this.getHeight()) {
                rect.set(0, 0, this.getWidth(), this.getHeight());
                //*
                clipPath.addRoundRect(rect, this.getWidth() / 2, this.getWidth() / 2, Path.Direction.CW);
                /*/
                clipPath.arcTo(rect, 0, 2*(float) Math.PI);
                //*/
            }
            canvas.clipPath(clipPath);
        }
        super.onDraw(canvas);
    }
}

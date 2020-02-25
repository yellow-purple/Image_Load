package org.techtown.image_load;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

//RecyclerView 에 이미지가 띄워지느 뷰를 정사각형 형태로 만들어줌
public class SquareFrameLayout extends FrameLayout {

    public SquareFrameLayout(Context context) {
        super(context);
    }

    public SquareFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);



    }

}
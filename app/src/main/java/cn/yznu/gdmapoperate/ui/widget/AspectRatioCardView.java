package cn.yznu.gdmapoperate.ui.widget;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * 作者：uiho_mac
 * 时间：2018/6/11
 * 描述：锁定宽高比的CardView
 * 版本：1.0
 * 修订历史：
 */

public class AspectRatioCardView extends CardView {

    private float ratio = 1.1f;

    public AspectRatioCardView(Context context) {
        this(context, null);
    }

    public AspectRatioCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AspectRatioCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (ratio > 0) {
            int ratioHeight = (int) (getMeasuredWidth() * ratio);
            setMeasuredDimension(getMeasuredWidth(), ratioHeight);
            ViewGroup.LayoutParams lp = getLayoutParams();
            lp.height = ratioHeight;
            setLayoutParams(lp);
        }
    }
}
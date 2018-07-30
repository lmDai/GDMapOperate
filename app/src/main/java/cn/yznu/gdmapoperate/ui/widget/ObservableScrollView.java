package cn.yznu.gdmapoperate.ui.widget;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

/**
 * @author chenwei
 * @date 2018/3/27
 */
public class ObservableScrollView extends NestedScrollView {

  private OnScrollChangedListener onScrollChangedListener;

  public ObservableScrollView(Context context) {
    super(context);
  }

  public ObservableScrollView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    super.onScrollChanged(l, t, oldl, oldt);
    if (this.onScrollChangedListener != null) {
      onScrollChangedListener.onScrollChanged(t, oldt);
    }
  }

  public void setOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
    this.onScrollChangedListener = onScrollChangedListener;
  }
}

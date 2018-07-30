package cn.yznu.gdmapoperate.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.yznu.gdmapoperate.R;
import cn.yznu.gdmapoperate.ui.activity.ViewPagerActivity;
import cn.yznu.gdmapoperate.ui.widget.ObservableScrollView;
import cn.yznu.gdmapoperate.ui.widget.OnScrollChangedListener;
import cn.yznu.gdmapoperate.utils.ViewUtil;

/**
 * 作者：uiho_mac
 * 时间：2018/6/11
 * 描述：
 * 版本：1.0
 * 修订历史：
 */

public class ViewpagerTopOneFragment extends BaseScrollFragment {
    private ObservableScrollView scrollView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_viewpager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        scrollView = (ObservableScrollView) view.findViewById(R.id.scrollView);
        scrollView.setOnScrollChangedListener(new OnScrollChangedListener() {
            @Override
            public void onScrollChanged(int top, int oldTop) {
                if (isPageVisible()) {
                    ((ViewPagerActivity) getActivity()).pageScrollTo(Math.min(top, maxScrollDisY()));
                }
            }
        });
    }

    @Override
    public void pageScrollToTop() {
        if (scrollView == null) {
            return;
        }
        if (scrollView.getScrollY() == 0) {
            ((ViewPagerActivity) getActivity()).pageScrollTo(0);
        } else {
            scrollView.scrollTo(0, 0);
        }
    }

    @Override
    public void pageScrollTo(int disY) {
        if (disY <= maxScrollDisY()) {
            scrollView.scrollTo(0, disY);
        }
    }

    @Override
    public int maxScrollDisY() {
        return ViewUtil.dp2px(160);
    }

    @Override
    public boolean isPageVisible() {
        return ((ViewPagerActivity) getActivity()).getCurrPageIndex() == 0;
    }
}

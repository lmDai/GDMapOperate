package cn.yznu.gdmapoperate.ui.widget;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;

import cn.yznu.gdmapoperate.utils.ViewUtil;

/**
 * 作者：uiho_mac
 * 时间：2018/9/4
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public class ScrollManager {
    private static final String TAG = "MainActivity_TAG";

    private GalleryRecyclerView mGalleryRecyclerView;

    private int mPosition = 0;

    /**
     * x方向消耗距离，使偏移量为左边距 + 左边Item的可视部分宽度
     */
    private int mConsumeX = 0;
    private int mConsumeY = 0;

    ScrollManager(GalleryRecyclerView mGalleryRecyclerView) {
        this.mGalleryRecyclerView = mGalleryRecyclerView;
    }

    /**
     * 初始化SnapHelper
     *
     * @param helper int
     */
    public void initSnapHelper(int helper) {
        switch (helper) {
            case GalleryRecyclerView.LINEAR_SNAP_HELPER:
                LinearSnapHelper mLinearSnapHelper = new LinearSnapHelper();
                mLinearSnapHelper.attachToRecyclerView(mGalleryRecyclerView);
                break;
            case GalleryRecyclerView.PAGER_SNAP_HELPER:
                PagerSnapHelper mPagerSnapHelper = new PagerSnapHelper();
                mPagerSnapHelper.attachToRecyclerView(mGalleryRecyclerView);
                break;
            default:
                break;
        }
    }

    /**
     * 监听RecyclerView的滑动
     */
    public void initScrollListener() {
        GalleryScrollerListener mScrollerListener = new GalleryScrollerListener();
        mGalleryRecyclerView.addOnScrollListener(mScrollerListener);
    }

    public void updateConsume() {
        mConsumeX += ViewUtil.dp2px(mGalleryRecyclerView.getDecoration().mLeftPageVisibleWidth + mGalleryRecyclerView.getDecoration().mPageMargin * 2);
        mConsumeY += ViewUtil.dp2px(mGalleryRecyclerView.getDecoration().mLeftPageVisibleWidth + mGalleryRecyclerView.getDecoration().mPageMargin * 2);
    }

    class GalleryScrollerListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (mGalleryRecyclerView.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                onHorizontalScroll(recyclerView, dx);
            } else {
                onVerticalScroll(recyclerView, dy);
            }
        }
    }

    /**
     * 垂直滑动
     *
     * @param recyclerView RecyclerView
     * @param dy           int
     */
    private void onVerticalScroll(final RecyclerView recyclerView, int dy) {
        mConsumeY += dy;

        // 让RecyclerView测绘完成后再调用，避免GalleryAdapterHelper.mItemHeight的值拿不到
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                int shouldConsumeY = mGalleryRecyclerView.getDecoration().mItemConsumeY;

                // 位置浮点值（即总消耗距离 / 每一页理论消耗距离 = 一个浮点型的位置值）
                float offset = (float) mConsumeY / (float) shouldConsumeY;
                // 获取当前页移动的百分值
                float percent = offset - ((int) offset);

                mPosition = (int) offset;
                // 设置动画变化
                mGalleryRecyclerView.getAnimManager().setAnimation(recyclerView, mPosition, percent);
            }
        });
    }

    /**
     * 水平滑动
     *
     * @param recyclerView RecyclerView
     * @param dx           int
     */
    private void onHorizontalScroll(final RecyclerView recyclerView, final int dx) {
        mConsumeX += dx;

        // 让RecyclerView测绘完成后再调用，避免GalleryAdapterHelper.mItemWidth的值拿不到
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                int shouldConsumeX = mGalleryRecyclerView.getDecoration().mItemConsumeX;

                // 位置浮点值（即总消耗距离 / 每一页理论消耗距离 = 一个浮点型的位置值）
                float offset = (float) mConsumeX / (float) shouldConsumeX;

                // 获取当前页移动的百分值
                float percent = offset - ((int) offset);

                mPosition = (int) offset;
                // 设置动画变化
                mGalleryRecyclerView.getAnimManager().setAnimation(recyclerView, mPosition, percent);
            }
        });

    }

    public int getPosition() {
        return mPosition;
    }
}

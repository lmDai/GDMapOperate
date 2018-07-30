package cn.yznu.gdmapoperate.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import cn.yznu.gdmapoperate.R;
import cn.yznu.gdmapoperate.ui.adapter.BottomFragmentAdapter;
import cn.yznu.gdmapoperate.ui.adapter.TopFragmentAdapter;
import cn.yznu.gdmapoperate.ui.fragment.BaseScrollFragment;
import cn.yznu.gdmapoperate.ui.fragment.ViewpagerBottomOneFragment;
import cn.yznu.gdmapoperate.ui.fragment.ViewpagerBottomThreeFragment;
import cn.yznu.gdmapoperate.ui.fragment.ViewpagerBottomTwoFragment;
import cn.yznu.gdmapoperate.ui.fragment.ViewpagerTopOneFragment;
import cn.yznu.gdmapoperate.ui.fragment.ViewpagerTopThreeFragment;
import cn.yznu.gdmapoperate.ui.fragment.ViewpagerTopTwoFragment;
import cn.yznu.gdmapoperate.ui.widget.BaseLinkPageChangeListener;
import cn.yznu.gdmapoperate.ui.widget.WrapContentHeightViewPager;

public class ViewPagerActivity extends AppCompatActivity {
    private SlidingTabLayout tabLayout;
    private WrapContentHeightViewPager headerVp;
    private WrapContentHeightViewPager bodyVp;
    private List<BaseScrollFragment> fragments;
    private List<BaseScrollFragment> bottomFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        initView();
        initViewPager();
        initListener();
    }

    private void initView() {
        tabLayout = (SlidingTabLayout) findViewById(R.id.tabLayout);
        headerVp = (WrapContentHeightViewPager) findViewById(R.id.header_vp);
        bodyVp = (WrapContentHeightViewPager) findViewById(R.id.body_vp);
        fragments = new ArrayList<>();
        bottomFragments = new ArrayList<>();
        ViewpagerTopOneFragment topOneFragment = new ViewpagerTopOneFragment();
        ViewpagerTopTwoFragment topTwoFragment = new ViewpagerTopTwoFragment();
        ViewpagerTopThreeFragment topThreeFragment = new ViewpagerTopThreeFragment();
        fragments.add(topOneFragment);
        fragments.add(topTwoFragment);
        fragments.add(topThreeFragment);
        ViewpagerBottomOneFragment bottomOneFragment = new ViewpagerBottomOneFragment();
        ViewpagerBottomTwoFragment bottomTwoFragment = new ViewpagerBottomTwoFragment();
        ViewpagerBottomThreeFragment bottomThreeFragment = new ViewpagerBottomThreeFragment();
        bottomFragments.add(bottomOneFragment);
        bottomFragments.add(bottomTwoFragment);
        bottomFragments.add(bottomThreeFragment);
    }

    private void initViewPager() {
        String[] titles = {"One", "Two", "Three"};
        bodyVp.setAdapter(new TopFragmentAdapter(getSupportFragmentManager(), fragments, titles));
        tabLayout.setViewPager(bodyVp);
        headerVp.setAdapter(new BottomFragmentAdapter(getSupportFragmentManager(), bottomFragments));
        headerVp.setOffscreenPageLimit(2);
        bodyVp.setOffscreenPageLimit(2);
    }

    private void initListener() {

        bodyVp.addOnPageChangeListener(new BaseLinkPageChangeListener(bodyVp, headerVp) {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                pageScrollToTop();
                bodyVp.resetHeight(position);//设置viewpager高度
                headerVp.resetHeight(position);
            }
        });
        headerVp.addOnPageChangeListener(new BaseLinkPageChangeListener(headerVp, bodyVp) {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.onPageSelected(position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                tabLayout.onPageScrolled(position, positionOffset, positionOffsetPixels);
                bodyVp.resetHeight(position);
                headerVp.resetHeight(position);
            }
        });
    }

    public void pageScrollToTop() {
        for (int i = 0; i < fragments.size(); i++) {
            fragments.get(i).pageScrollToTop();
        }
    }

    public void pageScrollTo(int distance) {
        headerVp.setTranslationY(-distance);
        for (int i = 0; i < fragments.size(); i++) {
            if (i != getCurrPageIndex()) {
                fragments.get(i).pageScrollTo(distance);
            }
        }
    }

    public int getCurrPageIndex() {
        return bodyVp.getCurrentItem();
    }
}

package cn.yznu.gdmapoperate.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import cn.yznu.gdmapoperate.ui.fragment.BaseScrollFragment;

/**
 * 作者：uiho_mac
 * 时间：2018/7/30
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public class TopFragmentAdapter extends FragmentPagerAdapter {
    private List<BaseScrollFragment> fragments;
    private String[] titles;

    public TopFragmentAdapter(FragmentManager fm, List<BaseScrollFragment> fragments, String[] titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}

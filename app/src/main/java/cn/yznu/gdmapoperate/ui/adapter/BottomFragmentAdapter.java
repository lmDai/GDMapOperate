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
public class BottomFragmentAdapter extends FragmentPagerAdapter {
    private List<BaseScrollFragment> fragments;

    public BottomFragmentAdapter(FragmentManager fm, List<BaseScrollFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
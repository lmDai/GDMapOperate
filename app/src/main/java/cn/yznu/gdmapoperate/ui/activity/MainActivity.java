package cn.yznu.gdmapoperate.ui.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import cn.yznu.gdmapoperate.DemoModel;
import cn.yznu.gdmapoperate.R;
import cn.yznu.gdmapoperate.ui.fragment.CommonFragment;
import cn.yznu.gdmapoperate.ui.widget.CustPagerTransformer;

/**
 * 作者：uiho_mac
 * 时间：2018/6/7
 * 描述：首页
 * 版本：1.0
 * 修订历史：
 */

public class MainActivity extends AppCompatActivity {

    private TextView indicatorTv;
    private View positionView;
    private ViewPager viewPager;
    private List<CommonFragment> fragments = new ArrayList<>(); // 供ViewPager使用
    private Class[] clazz = new Class[]{ShowMapActivity.class, LocationActivity.class, ElectricFenceActivity.class,
            NaviRouteActivity.class,SearchActivity.class};
    private int[] icons = new int[]{R.drawable.map, R.drawable.dingwei, R.drawable.weilan, R.drawable.daohang, R.drawable.ic_search};
    private List<DemoModel> mList = new ArrayList<>();
    private ImageView imgLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. 沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
                getWindow()
                        .getDecorView()
                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                getWindow()
                        .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
        positionView = findViewById(R.id.position_view);
        imgLike = (ImageView) findViewById(R.id.img_like);
        imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        dealStatusBar(); // 调整状态栏高度

        // 3. 填充ViewPager
        fillViewPager();
    }

    /**
     * 填充ViewPager
     */
    private void fillViewPager() {
        indicatorTv = (TextView) findViewById(R.id.indicator_tv);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        String[] title = getResources().getStringArray(R.array.main_string);

        // 1. viewPager添加parallax效果，使用PageTransformer就足够了
        viewPager.setPageTransformer(false, new CustPagerTransformer(this));

        // 2. viewPager添加adapter
        for (int i = 0; i < clazz.length; i++) {
            // 预先准备10个fragment
            fragments.add(new CommonFragment());
            mList.add(new DemoModel(title[i], clazz[i], icons[i]));
        }

        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                CommonFragment fragment = fragments.get(position % 10);
                fragment.bindData(mList.get(position));
                return fragment;
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });


        // 3. viewPager滑动时，调整指示器
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                updateIndicatorTv();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        updateIndicatorTv();
    }

    /**
     * 更新指示器
     */
    private void updateIndicatorTv() {
        int totalNum = viewPager.getAdapter().getCount();
        int currentItem = viewPager.getCurrentItem() + 1;
        indicatorTv.setText(Html.fromHtml("<font color='#12edf0'>" + currentItem + "</font>  /  " + totalNum));
    }

    /**
     * 调整沉浸式菜单的title
     */
    private void dealStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = getStatusBarHeight();
            ViewGroup.LayoutParams lp = positionView.getLayoutParams();
            lp.height = statusBarHeight;
            positionView.setLayoutParams(lp);
        }
    }

    private int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    public void showDialog() {
        Dialog dialog = new Dialog(this, R.style.CustomDialog);
        dialog.show();
        LayoutInflater inflater = LayoutInflater.from(this);
        View viewDialog = inflater.inflate(R.layout.dialog_like, null);
        Display display = this.getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        //设置dialog的宽高为屏幕的宽高
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
        dialog.setContentView(viewDialog, layoutParams);
    }

}

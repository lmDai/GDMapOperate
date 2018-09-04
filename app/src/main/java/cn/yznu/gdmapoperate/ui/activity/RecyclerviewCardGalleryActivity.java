package cn.yznu.gdmapoperate.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.yznu.gdmapoperate.R;
import cn.yznu.gdmapoperate.ui.adapter.RecyclerviewGalleryAdapter;
import cn.yznu.gdmapoperate.ui.widget.AnimManager;
import cn.yznu.gdmapoperate.ui.widget.GalleryRecyclerView;

/**
 * 作者：uiho_mac
 * 时间：2018/9/4
 * 描述：使用RecyclerView实现Gallery画廊效果
 * 版本：1.0
 * 修订历史：
 */
public class RecyclerviewCardGalleryActivity extends AppCompatActivity implements GalleryRecyclerView.OnItemClickListener {
    private GalleryRecyclerView recyclerView;
    private List<Integer> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_gallery);
        recyclerView = findViewById(R.id.recyclerview);
        initData();
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            mList.add(R.drawable.jianshu);
        }
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new RecyclerviewGalleryAdapter(mList));
        recyclerView.initFlingSpeed(9000)
                .initPageParams(0, 40)
                .setAnimFactor(0.1f)
                .setAnimType(AnimManager.ANIM_BOTTOM_TO_TOP)
                .setOnItemClickListener(this)
                .autoPlay(false)
                .intervalTime(2000)
                .initPosition(0)
                .setUp();
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, position + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recyclerView.release();
    }
}

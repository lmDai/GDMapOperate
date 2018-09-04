package cn.yznu.gdmapoperate.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.yznu.gdmapoperate.DemoModel;
import cn.yznu.gdmapoperate.R;
import cn.yznu.gdmapoperate.ui.adapter.UIDemoAdapter;

public class TestActivity extends AppCompatActivity {
    private UIDemoAdapter adapter;
    private List<DemoModel> data = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        initData();
    }

    public void initData() {
        data.add(new DemoModel("两个viewpager联动", ViewPagerActivity.class, 0));
        data.add(new DemoModel("RecyclerView实现Gallery画廊效果", RecyclerviewCardGalleryActivity.class, 0));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new UIDemoAdapter(data);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DemoModel demoModel = (DemoModel) adapter.getItem(position);
                if (demoModel != null) {
                    startActivity(new Intent(TestActivity.this, demoModel.getClazz()));
                }
            }
        });
    }
}

package cn.yznu.gdmapoperate.ui.actvity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.yznu.gdmapoperate.App;
import cn.yznu.gdmapoperate.DemoModel;
import cn.yznu.gdmapoperate.R;
import cn.yznu.gdmapoperate.ui.adapter.MainAdapter;

/**
 * 作者：uiho_mac
 * 时间：2018/6/7
 * 描述：首页
 * 版本：1.0
 * 修订历史：
 */

public class MainActivity extends AppCompatActivity {
    private List<DemoModel> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    /**
     * 初始化
     */
    public void initView() {
        ListView listView = findViewById(R.id.list_view);
        String[] names = getResources().getStringArray(R.array.main_string);
        Class[] classes = new Class[]{ShowMapActivity.class, ElectricFenceActivity.class};
        for (int i = 0; i < classes.length; i++) {
            mList.add(new DemoModel(names[i], classes[i]));
        }
        MainAdapter mainAdapter = new MainAdapter(MainActivity.this, mList);
        listView.setAdapter(mainAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DemoModel m = (DemoModel) parent.getItemAtPosition(position);
                startActivity(new Intent(App.getInstance(), m.getClazz()));

            }
        });

    }
}

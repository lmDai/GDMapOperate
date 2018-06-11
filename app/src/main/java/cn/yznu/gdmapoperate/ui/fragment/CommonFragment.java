package cn.yznu.gdmapoperate.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.yznu.gdmapoperate.DemoModel;
import cn.yznu.gdmapoperate.R;
import cn.yznu.gdmapoperate.ui.widget.AspectRatioCardView;

/**
 * 作者：uiho_mac
 * 时间：2018/6/11
 * 描述：
 * 版本：1.0
 * 修订历史：
 */

public class CommonFragment extends Fragment {
    private DemoModel demoModel;
    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_common, null);
        AspectRatioCardView cardView = (AspectRatioCardView) rootView.findViewById(R.id.card_view);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), demoModel.getClazz()));
            }
        });
        TextView txtTitle = (TextView) rootView.findViewById(R.id.txt_title);
        imageView = (ImageView) rootView.findViewById(R.id.image);
        txtTitle.setText(demoModel.getTitle());
        imageView.setImageResource(demoModel.getIcon());
        return rootView;
    }

    public void bindData(DemoModel demoModel) {
        this.demoModel = demoModel;
    }
}

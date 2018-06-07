package cn.yznu.gdmapoperate.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.yznu.gdmapoperate.DemoModel;
import cn.yznu.gdmapoperate.R;

/**
 * 作者：uiho_mac
 * 时间：2018/6/7
 * 描述：
 * 版本：1.0
 * 修订历史：
 */

public class MainAdapter extends BaseAdapter {
    private Context mContext;
    private List<DemoModel> mList;
    private LayoutInflater mLayoutInflater;

    public MainAdapter(Context mContext, List<DemoModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder hold;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.adapter_main, parent, false);
            hold = new ViewHolder();
            hold.textView = convertView.findViewById(R.id.txt_title);
            convertView.setTag(hold);
        } else {
            hold = (ViewHolder) convertView.getTag();
        }
        hold.textView.setText(mList.get(position).getTitle());
        return convertView;
    }

    static class ViewHolder {
        TextView textView;
    }
}

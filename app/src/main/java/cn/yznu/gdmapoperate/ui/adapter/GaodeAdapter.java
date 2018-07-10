package cn.yznu.gdmapoperate.ui.adapter;

import android.support.annotation.Nullable;

import com.amap.api.services.core.PoiItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.yznu.gdmapoperate.R;

public class GaodeAdapter extends BaseQuickAdapter<PoiItem, BaseViewHolder> {
    public GaodeAdapter(@Nullable List<PoiItem> data) {
        super(R.layout.adapter_schedule_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PoiItem item) {
        helper.setText(R.id.tv_schedule_adapter_name, item.getSnippet());
    }
}

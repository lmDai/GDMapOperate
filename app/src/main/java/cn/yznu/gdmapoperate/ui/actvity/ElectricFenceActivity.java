package cn.yznu.gdmapoperate.ui.actvity;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Polygon;
import com.amap.api.maps.model.PolygonOptions;

import cn.yznu.gdmapoperate.ui.widget.MarkSizeView;
import cn.yznu.gdmapoperate.R;

public class ElectricFenceActivity extends AppCompatActivity {
    private AMap aMap;
    private MarkSizeView markSizeView;
    private AppCompatTextView txtTips;
    private Button btnPaint;
    private Projection projection;
    private Polygon polygon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electric_fence);

        MapView mapView = findViewById(R.id.map_view);
        markSizeView = findViewById(R.id.mark_size);
        txtTips = findViewById(R.id.capture_tips);
        btnPaint = findViewById(R.id.btn_paint);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        projection = aMap.getProjection();
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        markSizeView.setVisibility(View.GONE);
        txtTips.setVisibility(View.GONE);
        btnPaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markSizeView.setVisibility(View.VISIBLE);
                txtTips.setVisibility(View.VISIBLE);
            }
        });
//负责将屏幕位置和地理坐标（经纬度）进行转换。屏幕位置是相对地图的左上角的位置
        markSizeView.setmOnClickListener(new MarkSizeView.onClickListener() {//绘制矩形围栏
            @Override
            public void onConfirm(Rect markedArea) {
                Point pointTopLeft = new Point(markedArea.left, markedArea.top);
                LatLng topLef = projection.fromScreenLocation(pointTopLeft);

                Point pointTopRight = new Point(markedArea.right, markedArea.top);
                LatLng topRight = projection.fromScreenLocation(pointTopRight);

                Point pointBottomLeft = new Point(markedArea.left, markedArea.bottom);
                LatLng bottomLeft = projection.fromScreenLocation(pointBottomLeft);

                Point pointBottomRight = new Point(markedArea.right, markedArea.bottom);
                LatLng bottomRight = projection.fromScreenLocation(pointBottomRight);
                markSizeView.reset();
                markSizeView.setEnabled(true);
                paintElec(topLef, topRight, bottomRight, bottomLeft);
            }

            @Override
            public void onConfirm(MarkSizeView.GraphicPath path) {

            }

            @Override
            public void onCancel() {
                txtTips.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTouch() {
                txtTips.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 绘制围栏
     */
    private void paintElec(LatLng leftTop, LatLng rightTop, LatLng bottomRight, LatLng bottomLeft) {
        markSizeView.setVisibility(View.GONE);
        if (polygon != null) {
            polygon.remove();
        }
        PolygonOptions polygonOptions = new PolygonOptions();
        // 添加 多边形的每个顶点（顺序添加）
        polygonOptions.add(
                leftTop, rightTop, bottomRight, bottomLeft);
        polygonOptions.strokeWidth(1) // 多边形的边框
                .strokeColor(Color.RED) // 边框颜色
                .fillColor(Color.parseColor("#3FFF0000"));   // 多边形的填充色
        // 添加一个多边形
        polygon = aMap.addPolygon(polygonOptions);
    }
}

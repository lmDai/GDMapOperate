package cn.yznu.gdmapoperate.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.AmapPageType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;

import java.util.ArrayList;
import java.util.List;

import cn.yznu.gdmapoperate.R;

/**
 * 作者：uiho_mac
 * 时间：2018/6/11
 * 描述：导航
 * 版本：1.0
 * 修订历史：
 */

public class NaviRouteActivity extends AppCompatActivity implements INaviInfoCallback {
    private MapView mapView;
    private AMap aMap;
    private ImageButton ibNavi;
    LatLng p1 = new LatLng(39.993266, 116.473193);//首开广场
    LatLng p2 = new LatLng(39.917337, 116.397056);//故宫博物院
    LatLng p3 = new LatLng(39.904556, 116.427231);//北京站
    LatLng p4 = new LatLng(39.773801, 116.368984);//新三余公园(南5环)
    LatLng p5 = new LatLng(40.041986, 116.414496);//立水桥(北5环)

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示程序的标题栏
        setContentView(R.layout.activity_navi_route);
        mapView = (MapView) findViewById(R.id.map_view);
        ibNavi = (ImageButton) findViewById(R.id.ib_navi);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        addMarker("立水桥(北5环)", "40.041986, 116.414496", p5);
        addMarker("新三余公园(南5环)", "39.773801, 116.368984", p4);
        ibNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Poi start = new Poi("立水桥(北5环)", p5, "");//起点
                //<editor-fold desc="途径点">
                List<Poi> poiList = new ArrayList();
                poiList.add(new Poi("首开广场", p1, ""));
                poiList.add(new Poi("故宫博物院", p2, ""));
                poiList.add(new Poi("北京站", p3, ""));
                //</editor-fold>
                Poi end = new Poi("新三余公园(南5环)", p4, "");//终点
                AmapNaviParams amapNaviParams = new AmapNaviParams(start, null, end, AmapNaviType.DRIVER, AmapPageType.NAVI);
                amapNaviParams.setUseInnerVoice(true);
                AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), amapNaviParams, NaviRouteActivity.this);
            }
        });

    }

    public void addMarker(String title, String snippet, LatLng latLng) {
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
//        markerOption.title(title).snippet(snippet);
        markerOption.draggable(false);//设置Marker可拖动
        markerOption.icon(BitmapDescriptorFactory.fromView(getView(title, snippet)));
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        markerOption.setFlat(true);//设置marker平贴地图效果
        aMap.addMarker(markerOption);
    }

    public View getView(String title, String snippet) {
        View view = LayoutInflater.from(this).inflate(R.layout.marker_view, null);
        TextView txtTitle = (TextView) view.findViewById(R.id.title);
        TextView txtSnippet = (TextView) view.findViewById(R.id.snippet);
        txtTitle.setText(title);
        txtSnippet.setText(snippet);
        return view;
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onArriveDestination(boolean b) {

    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {

    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onStopSpeaking() {

    }

    @Override
    public void onReCalculateRoute(int i) {

    }

    @Override
    public void onExitPage(int i) {

    }

    @Override
    public void onStrategyChanged(int i) {

    }

    @Override
    public View getCustomNaviBottomView() {
        return null;
    }

    @Override
    public View getCustomNaviView() {
        return null;
    }

    @Override
    public void onArrivedWayPoint(int i) {

    }
}

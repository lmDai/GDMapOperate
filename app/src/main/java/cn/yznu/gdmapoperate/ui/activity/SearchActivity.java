package cn.yznu.gdmapoperate.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.geocoder.RegeocodeRoad;
import com.amap.api.services.geocoder.StreetNumber;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

import cn.yznu.gdmapoperate.R;
import cn.yznu.gdmapoperate.ui.adapter.GaodeAdapter;

/**
 * 搜索 bottomsheet
 */
public class SearchActivity extends AppCompatActivity implements LocationSource, AMapLocationListener {
    private MapView mapView;
    private View bottomSheet;
    private BottomSheetBehavior<View> behavior;
    private List<PoiItem> pois;
    private RecyclerView recyclerView;
    private GaodeAdapter adapter;
    //定位
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private LatLng point;
    private Marker marker;
    private AMap aMap;
    private static final double LATITUDE_A = 28.1903;  //起点纬度
    private static final double LONGTITUDE_A = 113.031738;  //起点经度

    private static final double LATITUDE_B = 28.187519;  //终点纬度
    private static final double LONGTITUDE_B = 113.029713;  //终点经度

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView(savedInstanceState);
    }


    private void initView(@Nullable Bundle savedInstanceState) {
        mapView = (MapView) findViewById(R.id.map_view);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
        }

        aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
        initLocation();
        //底部抽屉栏展示地址
        bottomSheet = findViewById(R.id.bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);

        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, @BottomSheetBehavior.State int newState) {
                String state = "null";
                switch (newState) {
                    case 1:
                        state = "STATE_DRAGGING";//过渡状态此时用户正在向上或者向下拖动bottom sheet
                        break;
                    case 2:
                        state = "STATE_SETTLING"; // 视图从脱离手指自由滑动到最终停下的这一小段时间
                        break;
                    case 3:
                        state = "STATE_EXPANDED"; //处于完全展开的状态

                        break;
                    case 4:
                        state = "STATE_COLLAPSED"; //默认的折叠状态
                        break;
                    case 5:
                        state = "STATE_HIDDEN"; //下滑动完全隐藏 bottom sheet
                        break;
                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//                Log.d("BottomSheetDemo", "slideOffset:" + slideOffset);
            }
        });
        if (pois != null) {
            adapter = new GaodeAdapter(pois);
            recyclerView.setAdapter(adapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

        }
    }

    /***
     * 初始化定位
     */
    private void initLocation() {
        if (aMap != null) {
            MyLocationStyle locationStyle = new MyLocationStyle();
            // locationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.em_unread_count_bg));
            locationStyle.strokeColor(Color.BLACK);
            locationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));
            locationStyle.strokeWidth(1.0f);
            aMap.setMyLocationStyle(locationStyle);
            aMap.setLocationSource(this);
            aMap.getUiSettings().setMyLocationButtonEnabled(true);
            aMap.setMyLocationEnabled(true);
            //监听地图发生变化之后
            aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {

                    //发生变化时获取到经纬度传递逆地理编码获取周边数据
                    regeocodeSearch(cameraPosition.target.latitude, cameraPosition.target.longitude, 2000);
                    point = new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude);
                    marker.remove();//将上一次描绘的mark清除

                }

                @Override
                public void onCameraChangeFinish(CameraPosition cameraPosition) {
                    //地图发生变化之后描绘mark
                    marker = aMap.addMarker(new com.amap.api.maps.model.MarkerOptions()
                            .position(point)
                            .title("")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.em_unread_count_bg)));
                }
            });
        }

    }


    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();

    }


    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();

    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            mlocationClient.setLocationListener(this);
            mLocationOption.setOnceLocation(true); //只定位一次
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mlocationClient.setLocationOption(mLocationOption);
            mlocationClient.startLocation();

        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
        mLocationOption = null;

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && mListener != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);
                Double weidu = aMapLocation.getLatitude();
                Double jingdu = aMapLocation.getLongitude();
                regeocodeSearch(weidu, jingdu, 2000);


            } else {
                String errorText = "faild to located" + aMapLocation.getErrorCode() + ":" + aMapLocation.getErrorInfo();
                Log.d("ceshi", errorText);
            }
        }

    }


    /***
     * 逆地理编码获取定位后的附近地址
     * @param weidu
     * @param jingdu
     * @param distances 设置查找范围
     */
    private void regeocodeSearch(Double weidu, Double jingdu, int distances) {
        LatLonPoint point = new LatLonPoint(weidu, jingdu);
        GeocodeSearch geocodeSearch = new GeocodeSearch(this);
        RegeocodeQuery regeocodeQuery = new RegeocodeQuery(point, distances, geocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(regeocodeQuery);

        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int rCode) {
                String preAdd = "";//地址前缀
                if (1000 == rCode) {
                    final RegeocodeAddress address = regeocodeResult.getRegeocodeAddress();
                    StringBuffer stringBuffer = new StringBuffer();
                    String area = address.getProvince();//省或直辖市
                    String loc = address.getCity();//地级市或直辖市
                    String subLoc = address.getDistrict();//区或县或县级市
                    String ts = address.getTownship();//乡镇
                    String thf = null;//道路
                    List<RegeocodeRoad> regeocodeRoads = address.getRoads();//道路列表
                    if (regeocodeRoads != null && regeocodeRoads.size() > 0) {
                        RegeocodeRoad regeocodeRoad = regeocodeRoads.get(0);
                        if (regeocodeRoad != null) {
                            thf = regeocodeRoad.getName();
                        }
                    }
                    String subthf = null;//门牌号
                    StreetNumber streetNumber = address.getStreetNumber();
                    if (streetNumber != null) {
                        subthf = streetNumber.getNumber();
                    }
                    String fn = address.getBuilding();//标志性建筑,当道路为null时显示
                    if (area != null) {
                        stringBuffer.append(area);
                        preAdd += area;
                    }
                    if (loc != null && !area.equals(loc)) {
                        stringBuffer.append(loc);
                        preAdd += loc;
                    }
                    if (subLoc != null) {
                        stringBuffer.append(subLoc);
                        preAdd += subLoc;
                    }
                    if (ts != null)
                        stringBuffer.append(ts);
                    if (thf != null)
                        stringBuffer.append(thf);
                    if (subthf != null)
                        stringBuffer.append(subthf);
                    if ((thf == null && subthf == null) && fn != null && !subLoc.equals(fn))
                        stringBuffer.append(fn + "附近");
                    //  String ps = "poi";

                    pois = address.getPois();//获取周围兴趣点

                    adapter = new GaodeAdapter(pois);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                            setUpGaodeAppByMine();
//                            setUpGaodeAppByName();
                            setUpGaodeAppByLoca();
                        }
                    });
                }


            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        });

    }

    /**
     * 判断是否安装目标应用
     *
     * @param packageName 目标应用安装后的包名
     * @return 是否已安装目标应用
     */
    private boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    /**
     * 确定起终点坐标BY高德
     */
    void setUpGaodeAppByLoca() {
        try {
            Intent intent = Intent.getIntent("androidamap://route?sourceApplication=softname&slat=" + LATITUDE_A + "&slon=" + LONGTITUDE_A + "&sname=" + "万家丽国际Mall" + "&dlat=" + LATITUDE_B + "&dlon=" + LONGTITUDE_B + "&dname=" + "东郡华城广场|A座" + "&dev=0&m=0&t=1");
            if (isInstallByread("com.autonavi.minimap")) {
                startActivity(intent);

            } else {
                Log.e("single", "没有安装高德地图客户端");
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * 确认起终点名称BY高德
     */
    void setUpGaodeAppByName() {
        try {
            Intent intent = Intent.getIntent("androidamap://route?sourceApplication=softname" + "&sname=" + "万家丽国际Mall" + "&dname=" + "东郡华城广场|A座" + "&dev=0&m=0&t=1");
            if (isInstallByread("com.autonavi.minimap")) {
                startActivity(intent);
            } else {
                Log.e("single", "没有安装高德地图客户端");
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * 我的位置BY高德
     */
    void setUpGaodeAppByMine() {
        try {
            Intent intent = Intent.getIntent("androidamap://route?sourceApplication=softname&sname=我的位置&dlat=" + LATITUDE_B + "&dlon=" + LONGTITUDE_B + "&dname=" + "东郡华城广场|A座" + "&dev=0&m=0&t=1");
            if (isInstallByread("com.autonavi.minimap")) {
                startActivity(intent);
            } else {
                Log.e("single", "没有安装高德地图客户端");
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}


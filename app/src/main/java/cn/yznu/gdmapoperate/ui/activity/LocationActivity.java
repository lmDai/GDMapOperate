package cn.yznu.gdmapoperate.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MyLocationStyle;

import cn.yznu.gdmapoperate.R;

/**
 * 作者：uiho_mac
 * 时间：2018/6/11
 * 描述：定位
 * 版本：1.0
 * 修订历史：
 */

public class LocationActivity extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST_LOCATION = 0x0001;
    private MapView mapView;
    private AMap aMap;
    private MyLocationStyle myLocationStyle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示程序的标题栏
        setContentView(R.layout.activity_location);
        mapView = (MapView) findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        initPermission();
    }

    /**
     * 定位
     */
    public void location() {
        // 如果要设置定位的默认状态，可以在此处进行设置
        myLocationStyle = new MyLocationStyle();
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_position);
        myLocationStyle.myLocationIcon(bitmapDescriptor);//设置定位蓝点的icon图标方法，需要用到BitmapDescriptor类对象作为参数。
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
//        // 只定位，不进行其他操作
//        aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW));
//        break;
//        case 1:
//        aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE));
//        break;
//        case 2:
//        // 设置定位的类型为 跟随模式
//        aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW));
//        break;
//        case 3:
//        // 设置定位的类型为根据地图面向方向旋转
//        aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE));
//        break;
//        case 4:
//        // 定位、且将视角移动到地图中心点，定位点依照设备方向旋转，  并且会跟随设备移动。
//        aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE));
//        break;
//        case 5 :
//        // 定位、但不会移动到地图中心点，并且会跟随设备移动。
//        aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER));
//        break;
//        case 6 :
//        // 定位、但不会移动到地图中心点，地图依照设备方向旋转，并且会跟随设备移动。
//        aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER));
//        break;
//        case 7 :
//        // 定位、但不会移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。
//        aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER));
//        break;

        // 定位、但不会移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。
        aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER));


        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                // 定位回调监听
                if (location != null) {
                    Log.e("amap", "onMyLocationChange 定位成功， lat: " + location.getLatitude() + " lon: " + location.getLongitude());
                    Bundle bundle = location.getExtras();
                    if (bundle != null) {
                        int errorCode = bundle.getInt(MyLocationStyle.ERROR_CODE);
                        String errorInfo = bundle.getString(MyLocationStyle.ERROR_INFO);
                        // 定位类型，可能为GPS WIFI等，具体可以参考官网的定位SDK介绍
                        int locationType = bundle.getInt(MyLocationStyle.LOCATION_TYPE);
                /*
                errorCode
                errorInfo
                locationType
                */
                        Log.e("amap", "定位信息， code: " + errorCode + " errorInfo: " + errorInfo + " locationType: " + locationType);
                    } else {
                        Log.e("amap", "定位信息， bundle is null ");

                    }

                } else {
                    Log.e("amap", "定位失败");
                }
            }
        });
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

    /**
     * 申请权限
     */
    public void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(LocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                    Toast.makeText(LocationActivity.this, "request read location", Toast.LENGTH_LONG).show();

                }
                ActivityCompat.requestPermissions(LocationActivity.this,

                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},

                        PERMISSIONS_REQUEST_LOCATION);

            } else {
                location();
            }

        } else {

// 做一些权限对应的操作
            location();

        }
    }

    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case PERMISSIONS_REQUEST_LOCATION: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    location();
                } else {
                    new AlertDialog.Builder(this)
                            .setTitle("权限申请")
                            .setMessage("在设置-应用-权限中开启权限，以保证功能的正常使用")
                            .setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    intent.setData(uri);
                                    startActivityForResult(intent, PERMISSIONS_REQUEST_LOCATION);
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setCancelable(false)
                            .show();
                }
                break;
            }
        }

    }
}

package com.example.wyyu.gitsamlpe.test.function.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;
import com.example.wyyu.gitsamlpe.util.location.LocationData;
import com.example.wyyu.gitsamlpe.util.location.LocationProvider;
import com.example.wyyu.gitsamlpe.util.location.LocationUtil;
import com.example.wyyu.gitsamlpe.util.location.Observer;

/**
 * Created by wyyu on 2017/12/28.
 **/

public class ActivityLocation extends FullScreenActivity implements Observer {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityLocation.class));
    }

    private TextView locationTitle;
    private TextView locationName;

    private TextView locationLongitude;
    private TextView locationLatitude;
    private TextView locationAltitude;
    private TextView locationTime;

    private Button buttonN;
    private Button buttonP;
    private Button buttonG;

    private boolean funClick;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        initActivity();
    }

    private void initActivity() {

        initTextView();

        initClickView();

        funClick = false;

        LocationUtil.registerToObservable(this);
    }

    private void initTextView() {

        locationLongitude = findViewById(R.id.location_longitude);
        locationLatitude = findViewById(R.id.location_latitude);
        locationAltitude = findViewById(R.id.location_altitude);
        locationTime = findViewById(R.id.location_time);

        locationTitle = findViewById(R.id.location_title);
        locationName = findViewById(R.id.location_name);
    }

    private void initClickView() {

        buttonG = findViewById(R.id.location_button_g);
        buttonP = findViewById(R.id.location_button_p);
        buttonN = findViewById(R.id.location_button_n);

        buttonG.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (!funClick) {
                    showActionButton();
                    LocationUtil.checkLocationPermission(ActivityLocation.this);
                } else {
                    LocationProvider.getLocationDataFromType(ActivityLocation.this,
                        LocationProvider.LocationType.GPS);
                    locationTitle.setText(getResources().getText(R.string.gps));
                }
            }
        });
        buttonP.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                LocationProvider.getLocationDataFromType(ActivityLocation.this,
                    LocationProvider.LocationType.PASSIVE);
                locationTitle.setText(getResources().getText(R.string.passive));
            }
        });
        buttonN.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                LocationProvider.getLocationDataFromType(ActivityLocation.this,
                    LocationProvider.LocationType.NETWORK);
                locationTitle.setText(getResources().getText(R.string.network));
            }
        });
    }

    private void showActionButton() {
        buttonN.setVisibility(View.VISIBLE);
        buttonP.setVisibility(View.VISIBLE);
        buttonG.setText("G");
        funClick = true;
    }

    @Override public void locationUpdate(LocationData locationData) {

        ULog.show("Location -> locationUpdate");

        refreshLocationInfo(locationData);
    }

    @SuppressLint({ "SetTextI18n", "HandlerLeak" })
    private void refreshLocationInfo(LocationData locationData) {

        locationLongitude.setText("经度 ：" + locationData.getLongitude());
        locationLatitude.setText("纬度 ：" + locationData.getLatitude());
        locationAltitude.setText("海拔 ：" + locationData.getAltitude());
        locationTime.setText("时间 ：" + locationData.getTime());

        locationName.setText("正在识别中...");

        LocationUtil.getCityFromLocation(locationData.getLatitude(), locationData.getLongitude(),
            new Handler() {
                @Override public void handleMessage(Message msg) {
                    locationName.setText((String) msg.obj);
                }
            });
    }

    /**
     * 权限申请结果回调
     *
     * @param requestCode 调用 requestPermissions 方法时，传入的最后一个参数值 "requestCode"
     * @param permissions 参与申请的所有 permission 组成的数组
     * @param grantResults 申请权限的返回值 ：0、对应权限申请成功，-1、对应权限申请失败
     */
    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults) {

        ULog.show("ActivityLocation -> requestCode : " + requestCode);

        for (String permission : permissions) {
            ULog.show("ActivityLocation -> permission : " + permission);
        }

        for (int result : grantResults) {
            ULog.show("ActivityLocation -> result : " + result);
        }
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        LocationUtil.detachFromObservable(this);
    }
}

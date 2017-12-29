package com.example.wyyu.gitsamlpe.test.location;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;
import com.example.wyyu.gitsamlpe.util.location.LocationData;
import com.example.wyyu.gitsamlpe.util.location.LocationUtil;

/**
 * Created by wyyu on 2017/12/28.
 **/

public class ActivityLocation extends FullScreenActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        initActivity();
    }

    private void initActivity() {

        initTextView();

        initClickView();

        funClick = false;
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
            @Override
            public void onClick(View view) {
                if (!funClick) {
                    showActionButton();
                }
                refreshLocationInfo(LocationUtil.getLocationData());
                locationTitle.setText(getResources().getText(R.string.gps));
            }
        });
        buttonP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshLocationInfo(LocationUtil.getLocationData());
                locationTitle.setText(getResources().getText(R.string.passive));
            }
        });
        buttonN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshLocationInfo(LocationUtil.getLocationData());
                locationTitle.setText(getResources().getText(R.string.network));
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void refreshLocationInfo(LocationData locationData) {

        locationLongitude.setText("经度 ：" + locationData.getLongitude());
        locationLatitude.setText("纬度 ：" + locationData.getLatitude());
        locationAltitude.setText("海拔 ：" + locationData.getAltitude());
        locationTime.setText("时间 ：" + locationData.getTime());

        locationName.setText(locationData.getCity());
    }

    private void showActionButton() {
        buttonN.setVisibility(View.VISIBLE);
        buttonP.setVisibility(View.VISIBLE);
        buttonG.setText("G");
        funClick = true;
    }
}

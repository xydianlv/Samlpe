package com.example.wyyu.gitsamlpe.test.function.macaddress;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.util.MacAddressUtils;

import butterknife.BindView;

public class ActivityMacAddressTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityMacAddressTest.class));
    }

    @BindView(R.id.address_up_7_ip)
    TextView textUpIP;
    @BindView(R.id.address_up_7_machine)
    TextView textUpMachine;
    @BindView(R.id.address_up_7_box)
    TextView textUpBox;
    @BindView(R.id.address_between_ss)
    TextView textBetween;
    @BindView(R.id.address_under_six)
    TextView textUnder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mac_address_test);

        initActivity();
    }

    private void initActivity() {
        initToolbar("MacAddressList", 0xffffffff, 0xff84919b);

        setAddressShow(textUpIP, "Address From IP", MacAddressUtils.getMacAddressByIP());
        setAddressShow(textUpMachine, "Address From Machine", MacAddressUtils.getMachineHardwareAddress());
        setAddressShow(textUpBox, "Address From BusyBox", MacAddressUtils.getLocalMacAddressFromBusyBox());
        setAddressShow(textBetween, "Address Between", MacAddressUtils.getMacAddressBetweenSS());
        setAddressShow(textUnder, "Address Under", MacAddressUtils.getMacAddressUnderSix());
    }

    private void setAddressShow(TextView textView, String tag, String address) {
        if (TextUtils.isEmpty(address)) {
            String failureMsg = tag + " : Failed";
            textView.setText(failureMsg);
        } else {
            String successMsg = tag + " : " + address;
            textView.setText(successMsg);
        }
    }
}

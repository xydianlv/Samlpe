package com.example.wyyu.gitsamlpe.test.network.custom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;
import com.example.wyyu.gitsamlpe.util.http.HttpGet;
import com.example.wyyu.gitsamlpe.util.http.IHttpCallBack;
import java.io.IOException;
import okhttp3.Response;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by wyyu on 2020-04-20.
 **/

public class ActivityNetworkCustom extends FullScreenActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityNetworkCustom.class));
    }

    @BindView(R.id.network_test_text) TextView netText;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_custom);

        initActivity();
    }

    private void initActivity() {
        findViewById(R.id.network_test_btn).setOnClickListener(v -> loadNetData());
    }

    private void loadNetData() {
        HttpGet.asyncGet("https://47.105.199.13/collection/list_all", "", new IHttpCallBack() {
            @Override public void onFailure(IOException exception) {
                if (exception == null) {
                    showNetText("Null IOException");
                } else {
                    showNetText("IOException : " + exception.getMessage());
                }
            }

            @Override public void onResponse(Response response) {
                if (response == null) {
                    showNetText("Null Response");
                } else {
                    showNetText("Response : " + response.message());
                }
            }
        });
    }

    private void showNetText(String textValue) {
        AndroidSchedulers.mainThread().createWorker().schedule(() -> {
            if (netText != null && !TextUtils.isEmpty(textValue)) {
                netText.setText(textValue);
            }
        });
    }
}

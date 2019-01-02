package com.example.wyyu.gitsamlpe.test.fresco;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.BaseActivity;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by wyyu on 2019/1/2.
 **/

public class ActivityFrescoTest extends BaseActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityFrescoTest.class));
    }

    @BindView(R.id.fresco_test_basic) SimpleDraweeView basic;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_test);

        initActivity();
    }

    private void initActivity() {
        initBasic();
    }

    private void initBasic() {
        Uri uri = Uri.parse(
            "http://img.hb.aicdn.com/62dbd9aa279a929d8f5bce64cf488c6570d0113cbd91-RfF1C1_fw658");
        basic.setImageURI(uri);
    }
}

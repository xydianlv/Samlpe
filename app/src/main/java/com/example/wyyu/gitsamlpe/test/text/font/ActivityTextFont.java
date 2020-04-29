package com.example.wyyu.gitsamlpe.test.text.font;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

/**
 * Created by wyyu on 2019-12-31.
 **/

public class ActivityTextFont extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityTextFont.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_font);

        initActivity();
    }

    private void initActivity() {

        initToolbar("TextFont", 0xffffffff, 0xff84919b);

        setSansLightShow();
        setSansNormalShow();
        setTypefaceShow();
    }

    private void setSansLightShow() {
        Typeface typeLight = ResourcesCompat.getFont(this, R.font.sans_light);
        TextView textLightA = findViewById(R.id.text_type_light_a);
        TextView textLightB = findViewById(R.id.text_type_light_b);
        TextView textLightC = findViewById(R.id.text_type_light_c);

        textLightA.setTypeface(typeLight);
        textLightB.setTypeface(typeLight);
        textLightC.setTypeface(typeLight);
    }

    private void setSansNormalShow() {
        Typeface typeNormal = ResourcesCompat.getFont(this, R.font.sans_normal);
        TextView textNormalA = findViewById(R.id.text_type_normal_a);
        TextView textNormalB = findViewById(R.id.text_type_normal_b);
        TextView textNormalC = findViewById(R.id.text_type_normal_c);

        textNormalA.setTypeface(typeNormal);
        textNormalB.setTypeface(typeNormal);
        textNormalC.setTypeface(typeNormal);
    }

    private void setTypefaceShow() {
        TextView textMonospace = findViewById(R.id.text_typeface_monospace);
        textMonospace.setText(String.format("Monospace : %s", textMonospace.getText()));

        TextView textNormal = findViewById(R.id.text_typeface_normal);
        textNormal.setText(String.format("Normal : %s", textNormal.getText()));

        TextView textSerif = findViewById(R.id.text_typeface_serif);
        textSerif.setText(String.format("Serif : %s", textSerif.getText()));

        TextView textSans = findViewById(R.id.text_typeface_sans);
        textSans.setText(String.format("Sans : %s", textSans.getText()));
    }
}

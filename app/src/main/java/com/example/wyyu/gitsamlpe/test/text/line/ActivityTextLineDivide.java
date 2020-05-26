package com.example.wyyu.gitsamlpe.test.text.line;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.widget.TextView;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.util.UIUtils;

/**
 * Created by wyyu on 2020-05-25.
 **/

public class ActivityTextLineDivide extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityTextLineDivide.class));
    }

    private static final String TEXT_LONG =
        "多年以后，奥雷连诺上校站在行刑队面前，准会想起父亲带他去参观冰块的那个遥远的下午。当时，马孔多是个二十户人家的村庄，"
            + "一座座土房都盖在河岸上，河水清澈，沿着遍布石头的河床流去，河里的石头光滑、洁白，活象史前的巨蛋。\n"
            + "这块天地还是新开辟的，许多东西都叫不出名字，不得不用手指指点点。每年三月，衣衫褴楼的吉卜赛人都要在村边搭起帐篷，"
            + "在笛鼓的喧嚣声中，向马孔多的居 民介绍科学家的最新发明。他们首先带来的是磁铁。\n"
            + "一个身躯高大的吉卜赛人，自称梅尔加德斯，满脸络腮胡子，手指瘦得象鸟的爪子，向观众出色地表演了他所谓的马其顿炼金术士创造的世界第八奇迹。\n"
            + "他手里拿着两大块磁铁，从一座农舍走到另一座农舍，大家都惊异地看见，铁锅、铁盆、铁钳、铁炉都从原地倒下，"
            + "木板上的钉子和螺丝嘎吱嘎吱地拼命想挣脱出来，甚至那些早就丢失的东西也从找过多次的地方兀然出现，乱七八糟地跟在梅尔加德斯的魔铁后面。"
            + "“东西也是有生命的”";

    @BindView(R.id.line_divide_text) TextView textView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_line_divide);

        initActivity();
    }

    private void initActivity() {
        initToolbar("LineDivide");

        Drawable drawable = getResources().getDrawable(R.drawable.img_match_width_empty);
        drawable.setBounds(0, 0, UIUtils.getScreenWidth(), UIUtils.dpToPx(10.0f));

        String[] strArray = TEXT_LONG.split("\n");
        if (strArray.length <= 1) {
            textView.setText(TEXT_LONG);
            return;
        }
        StringBuilder builder = new StringBuilder();
        for (String str : strArray) {
            builder.append(str);
            builder.append("\n ");
        }
        SpannableString spanString = new SpannableString(builder.toString());
        int length = 0;
        for (String s : strArray) {
            ImageSpan imgSpan = new ImageSpan(drawable);
            length = s.length() + length + 2;
            spanString.setSpan(imgSpan, length - 1, length, ImageSpan.ALIGN_BASELINE);
        }
        textView.setText(spanString);
    }
}

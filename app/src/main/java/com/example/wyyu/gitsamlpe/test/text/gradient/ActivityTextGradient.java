package com.example.wyyu.gitsamlpe.test.text.gradient;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by wyyu on 2020-05-12.
 **/

public class ActivityTextGradient extends FullScreenActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityTextGradient.class));
    }

    @BindView(R.id.canvas_gradient_back_a) SimpleDraweeView backImageA;
    @BindView(R.id.canvas_gradient_back_b) SimpleDraweeView backImageB;
    @BindView(R.id.canvas_gradient_text_b) GradientTextView textViewB;
    @BindView(R.id.canvas_gradient_text_a) TextView textViewA;

    private static final String TEXT_POEM = "轻轻的我走了，\n正如我轻轻的来；\n我轻轻的招手，\n作别西天的云彩。\n"
        + "\n那河畔的金柳，\n是夕阳中的新娘；\n波光里的艳影，\n在我的心头荡漾。\n"
        + "\n软泥上的青荇，\n油油的在水底招摇；\n在康河的柔波里，\n我甘心做一条水草！\n"
        + "\n那榆荫下的一潭，\n不是清泉，是天上虹；\n揉碎在浮藻间，\n沉淀着彩虹似的梦。\n"
        + "\n寻梦？撑一支长篙，\n向青草更青处漫溯；\n满载一船星辉，\n在星辉斑斓里放歌。\n"
        + "\n但我不能放歌，\n悄悄是别离的笙箫；\n夏虫也为我沉默，\n沉默是今晚的康桥！\n"
        + "\n悄悄的我走了，\n正如我悄悄的来；\n我挥一挥衣袖，\n不带走一片云彩。";
    private static final String TEXT_LONG =
        "多年以后，奥雷连诺上校站在行刑队面前，准会想起父亲带他去参观冰块的那个遥远的下午。当时，马孔多是个二十户人家的村庄，"
            + "一座座土房都盖在河岸上，河水清澈，沿着遍布石头的河床流去，河里的石头光滑、洁白，活象史前的巨蛋。"
            + "这块天地还是新开辟的，许多东西都叫不出名字，不得不用手指指点点。每年三月，衣衫褴楼的吉卜赛人都要在村边搭起帐篷，"
            + "在笛鼓的喧嚣声中，向马孔多的居 民介绍科学家的最新发明。他们首先带来的是磁铁。"
            + "一个身躯高大的吉卜赛人，自称梅尔加德斯，满脸络腮胡子，手指瘦得象鸟的爪子，向观众出色地表演了他所谓的马其顿炼金术士创造的世界第八奇迹。"
            + "他手里拿着两大块磁铁，从一座农舍走到另一座农舍，大家都惊异地看见，铁锅、铁盆、铁钳、铁炉都从原地倒下，"
            + "木板上的钉子和螺丝嘎吱嘎吱地拼命想挣脱出来，甚至那些早就丢失的东西也从找过多次的地方兀然出现，乱七八糟地跟在梅尔加德斯的魔铁后面。"
            + "“东西也是有生命的”";

    private Unbinder unbinder;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_gradient);

        unbinder = ButterKnife.bind(this);
        initActivity();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    private void initActivity() {
        initBackImage();
        initTextShow();
    }

    private void initBackImage() {
        backImageA.setImageRequest(
            ImageRequestBuilder.newBuilderWithResourceId(R.mipmap.image_test_1)
                .setResizeOptions(new ResizeOptions(30, 30))
                .setPostprocessor(new IterativeBoxBlurPostProcessor(1, 30))
                .build());

        backImageB.setImageRequest(
            ImageRequestBuilder.newBuilderWithResourceId(R.mipmap.image_test_3)
                .setResizeOptions(new ResizeOptions(30, 30))
                .setPostprocessor(new IterativeBoxBlurPostProcessor(1, 30))
                .build());
    }

    private void initTextShow() {
        textViewA.setText(TEXT_LONG);
        textViewB.setText(TEXT_POEM);
    }
}

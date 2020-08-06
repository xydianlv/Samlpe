package com.example.wyyu.gitsamlpe.test.fresco.blr;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.image.local.ImageItem;
import com.example.wyyu.gitsamlpe.test.image.local.LocalImageModel;
import com.example.wyyu.gitsamlpe.test.image.matisse.FrescoLoader;
import com.example.wyyu.gitsamlpe.test.video.widget.CMUtils;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import java.io.File;
import java.util.List;

/**
 * Created by wyyu on 2020-08-05.
 **/

public class ActivityFrescoBlr extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityFrescoBlr.class));
    }

    @BindView(R.id.fresco_blr_res) SimpleDraweeView viewRes;
    @BindView(R.id.fresco_blr_res_t) SimpleDraweeView viewResT;
    @BindView(R.id.fresco_blr_net) SimpleDraweeView viewNet;
    @BindView(R.id.fresco_blr_net_t) SimpleDraweeView viewNetT;
    @BindView(R.id.fresco_blr_local) SimpleDraweeView viewLocal;
    @BindView(R.id.fresco_blr_local_t) SimpleDraweeView viewLocalT;

    private static final String URL =
        "https://ssyerv1.oss-cn-hangzhou.aliyuncs.com/picture/def4418e778a49a6bf0c2102f4b25590.jpg";
    private static final int RES_ID = R.mipmap.image_test_1;

    private LocalImageModel model;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_blr);

        initActivity();
    }

    private void initActivity() {
        initToolbar("FrescoBlr");

        model = new ViewModelProvider(this).get(LocalImageModel.class);

        initResShow();
        initNetShow();
        initLocalShow();
    }

    private void initResShow() {

        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithResourceId(RES_ID)
            .setResizeOptions(new ResizeOptions(100, 100))
            .setPostprocessor(new IterativeBoxBlurPostProcessor(1, 20))
            .build();
        GenericDraweeHierarchy hierarchy = viewRes.getHierarchy();
        if (hierarchy != null) {
            hierarchy.setPlaceholderImage(CMUtils.getImageHolderDrawable());
        }

        viewRes.setImageRequest(imageRequest);
        viewResT.setImageResource(RES_ID);
    }

    private void initNetShow() {
        Uri uri = Uri.parse(URL);

        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri)
            .setResizeOptions(new ResizeOptions(100, 100))
            .setPostprocessor(new IterativeBoxBlurPostProcessor(1, 30))
            .build();
        GenericDraweeHierarchy hierarchy = viewRes.getHierarchy();
        if (hierarchy != null) {
            hierarchy.setPlaceholderImage(CMUtils.getImageHolderDrawable());
        }

        viewNet.setImageRequest(imageRequest);

        FrescoLoader.newFrescoLoader()
            .imageScaleType(ScalingUtils.ScaleType.FIT_START)
            .uri(uri)
            .into(viewNetT);
    }

    private void initLocalShow() {
        model.loadLocalImageList(new LocalImageModel.LoadCallback() {
            @Override public void onSuccess(List<ImageItem> itemList) {
                if (itemList == null || itemList.isEmpty()) {
                    FrescoLoader.newFrescoLoader()
                        .placeHolder(CMUtils.getImageHolderDrawable())
                        .into(viewLocal);
                } else {
                    loadLocalShow(itemList.get(0).localPath);
                }
            }

            @Override public void onFailure() {
                FrescoLoader.newFrescoLoader()
                    .placeHolder(CMUtils.getImageHolderDrawable())
                    .into(viewLocal);
            }
        });
    }

    private void loadLocalShow(String localPath) {
        Uri uri = Uri.fromFile(new File(localPath));

        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri)
            .setResizeOptions(new ResizeOptions(100, 100))
            .setPostprocessor(new IterativeBoxBlurPostProcessor(1, 30))
            .build();
        GenericDraweeHierarchy hierarchy = viewRes.getHierarchy();
        if (hierarchy != null) {
            hierarchy.setPlaceholderImage(CMUtils.getImageHolderDrawable());
        }

        viewLocal.setImageRequest(imageRequest);

        FrescoLoader.newFrescoLoader()
            .imageScaleType(ScalingUtils.ScaleType.FIT_START)
            .uri(uri)
            .into(viewLocalT);
    }
}

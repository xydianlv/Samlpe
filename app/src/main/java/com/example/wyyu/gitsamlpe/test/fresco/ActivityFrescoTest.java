package com.example.wyyu.gitsamlpe.test.fresco;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.BaseActivity;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import java.util.concurrent.TimeUnit;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

/**
 * Created by wyyu on 2019/1/2.
 *
 * 三步加载 ：ImagePipeline 会优先去内存中搜寻图片，然后是磁盘缓存，再然后是网络或其他外部来源
 *
 * 三级缓存 ：
 * Bitmap 缓存：5.0 以上在 Java堆 上缓存，5.0 以下在 ashmem 上缓存，应用后台运行时，这块儿内存会被清空
 * 未解码图片的内存缓存：存放原始压缩格式的图片，使用前需解码，若有设置旋转、调整大小等操作，在解码前执行
 * 磁盘缓存：与未解码图片的内存缓存一样，但是应用在后台运行时，不会被清空
 *
 * 在配置 ImagePipeline 时可指定每个缓存区域的内存用量
 *
 * Fresco 用于缓存的三个线程池
 * 3个线程用于网络下载
 * 2个线程用于磁盘操作：本地文件的读取，磁盘缓存操作
 * 2个线程用于CPU操作：解码、转换，以及处理等后台操作
 *
 * 加载特别大的图片时，应考虑使用 Resize 将它缩放一下
 * Bitmap 太大的话，Fresco 会自动将它 Resize 一下
 **/

public class ActivityFrescoTest extends BaseActivity {

    private static final int TIME_INTERVAL = 2000;

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityFrescoTest.class));
    }

    @BindView(R.id.fresco_test_place_holder) SimpleDraweeView placeHolder;
    @BindView(R.id.fresco_test_progress) SimpleDraweeView progress;
    @BindView(R.id.fresco_test_failure) SimpleDraweeView failure;
    @BindView(R.id.fresco_test_press) SimpleDraweeView press;
    @BindView(R.id.fresco_test_basic) SimpleDraweeView basic;
    @BindView(R.id.fresco_test_retry) SimpleDraweeView retry;

    private Drawable drawable;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_test);

        initActivity();
    }

    private void initActivity() {
        initBasic();
    }

    private void initBasic() {
        final Uri uri = Uri.parse(
            "http://img.hb.aicdn.com/62dbd9aa279a929d8f5bce64cf488c6570d0113cbd91-RfF1C1_fw658");
        basic.setImageURI(uri);

        RoundingParams roundingParams = new RoundingParams();
        // 四个圆角的角度值
        roundingParams.setCornersRadius(10.0f);
        // 圆圈属性
        roundingParams.setBorder(0xffff0000, 2.0f);
        // 设置是否拥有圆圈
        roundingParams.setRoundAsCircle(true);
        GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(getResources())
            // 设置构建好的圆角效果
            .setRoundingParams(roundingParams)
            // 占位图，默认缩放类型为 centerInside
            .setPlaceholderImage(R.color.color_e57373)
            // 加载失败的失败图
            .setFailureImage(R.color.color_f2debd)
            // 加载失败的重试图，点击重试四次后显示失败图
            .setRetryImage(R.color.color_1ed1ff)
            // 加载中显示的图
            .setProgressBarImage(R.color.color_546e7a)
            // 按压时的叠加图
            .setPressedStateOverlay(drawable)
            // 叠加图，覆盖在图片上面的图
            .setOverlay(drawable)
            // 背景图，不可缩放，充满整个 View
            .setBackground(drawable)
            // 由占位图到原图的时间
            .setFadeDuration(2000)
            // 构建一个 Hierarchy
            .build();

        // 不要多次调用 setHierarchy，创建 hierarchy 比较耗时，应多次利用
        placeHolder.setHierarchy(hierarchy);

        AndroidSchedulers.mainThread().createWorker().schedule(new Action0() {
            @Override public void call() {
                ControllerListener<ImageInfo> controllerListener =
                    new ControllerListener<ImageInfo>() {

                        @Override public void onSubmit(String id, Object callerContext) {

                        }

                        @Override
                        public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo,
                            @Nullable Animatable animatable) {

                        }

                        @Override public void onIntermediateImageSet(String id,
                            @Nullable ImageInfo imageInfo) {

                        }

                        @Override
                        public void onIntermediateImageFailed(String id, Throwable throwable) {

                        }

                        @Override public void onFailure(String id, Throwable throwable) {

                        }

                        @Override public void onRelease(String id) {

                        }
                    };
                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    // 若本地图片为 JPEG 格式，且拥有 EXIF 缩略图，添加此设置可自动开启缩略图优先加载
                    .setLocalThumbnailPreviewsEnabled(true)
                    // 是否开启渐进式加载
                    .setProgressiveRenderingEnabled(true)
                    // 图片缩放尺寸，不改变原始图片，只在解码前修改内存中的图片大小，只有 JPEG 可修改，由软件执行，会比较慢
                    .setResizeOptions(new ResizeOptions(240, 240))
                    // 设置最低请求级别，ImagePipeline 加载图片由明确的流程
                    // 检查内存缓存，若有立即返回，实时操作；检查未解码的图片缓存，若有，解码并返回；检查磁盘缓存，若有，解码并返回；下载或加载本地资源，调整、解码、返回
                    // 对于网络图而言，一套流程走下来最耗时
                    // 若对图片加载速度有要求，即多长时间没能加载出来，优先显示一张占位图，这个方法会非常有用
                    // 四个级别对应的常量 ：BITMAP_MEMORY_CACHE、ENCODED_MEMORY_CACHE、DISK_CACHE、FULL_FETCH
                    .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                    // 构建一个 ImageRequest
                    .build();
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                    // 低分辨率的缩略图，原图太大比较耗时时，可预先加载该缩略图，这一层不能显示 GIF
                    .setLowResImageRequest(ImageRequest.fromUri(uri))
                    // 设置构建好的图片请求
                    .setImageRequest(request)
                    // 待加载的 GIF 是否自动播放
                    .setAutoPlayAnimations(true)
                    // 是否设置点击重试加载
                    .setTapToRetryEnabled(true)
                    // 图片加载监听
                    .setControllerListener(controllerListener)
                    // 节省不必要的内存分配
                    .setOldController(placeHolder.getController())
                    // 构建一个 Controller
                    .build();

                //若一张图片有多个 Uri，可将由这几个 Uri 生成一个 Request 数组传给 Controller
                //Fresco 会优先尝试本地压缩后的图片的 URI，失败后再尝试本地原始图片 URI，再失败的话才会尝试上传到网络上的 URI
                //ImageRequest[] requestArray = new ImageRequest[]{ImageRequest.fromUri(uri),ImageRequest.fromUri(uri)};
                //DraweeController controller = Fresco.newDraweeControllerBuilder()
                //    .setFirstAvailableImageRequests(requestArray)
                //    .build();
                placeHolder.setController(controller);
            }
        }, TIME_INTERVAL, TimeUnit.MILLISECONDS);

        AndroidSchedulers.mainThread().createWorker().schedule(new Action0() {
            @Override public void call() {
                progress.setImageURI(uri);
            }
        }, TIME_INTERVAL * 2, TimeUnit.MILLISECONDS);

        AndroidSchedulers.mainThread().createWorker().schedule(new Action0() {
            @Override public void call() {
                failure.setImageURI(uri);
            }
        }, TIME_INTERVAL * 3, TimeUnit.MILLISECONDS);

        AndroidSchedulers.mainThread().createWorker().schedule(new Action0() {
            @Override public void call() {
                press.setImageURI(uri);
            }
        }, TIME_INTERVAL * 4, TimeUnit.MILLISECONDS);

        AndroidSchedulers.mainThread().createWorker().schedule(new Action0() {
            @Override public void call() {
                retry.setImageURI(uri);
            }
        }, TIME_INTERVAL * 5, TimeUnit.MILLISECONDS);
    }
}

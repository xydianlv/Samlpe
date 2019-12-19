package com.example.wyyu.gitsamlpe.test.svga;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.framework.toast.UToast;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import rx.Observable;

/**
 * Created by wyyu on 2019-12-19.
 **/

public class ActivitySvgATest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivitySvgATest.class));
    }

    @BindView(R.id.svg_a_surface_container) FrameLayout surfaceContainer;
    @BindView(R.id.svg_a_file_container) FrameLayout fileContainer;
    @BindView(R.id.svg_a_image) SVGAImageView svgaImageView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svga_test);

        initActivity();
    }

    private void initActivity() {
        initToolbar("SvgATest", 0xffffffff, 0xff84919b);

        loadSVGAImage();
        loadSVGASurface();
        cacheSvgAsFile();
    }

    private void loadSVGAImage() {
        svgaImageView.setLoops(1);
        SVGAParser parser = new SVGAParser(this);
        parser.decodeFromAssets("anim_svg_test.svga", new SVGAParser.ParseCompletion() {
            @Override public void onComplete(@NonNull SVGAVideoEntity svgaVideoEntity) {
                if (svgaImageView != null) {
                    svgaImageView.setVideoItem(svgaVideoEntity);
                    svgaImageView.stepToFrame(0, true);
                }
            }

            @Override public void onError() {

            }
        });
    }

    private void loadSVGASurface() {
        surfaceContainer.removeAllViews();
        surfaceContainer.addView(new SvgASurface(this));
    }

    private void cacheSvgAsFile() {
        Observable.unsafeCreate((Observable.OnSubscribe<String>) subscriber -> {
            File cacheFile = getCacheDir();
            if (cacheFile == null) {
                subscriber.onError(new Throwable("缓存目录获取失败"));
            } else {
                File file = new File(cacheFile.getAbsolutePath() + "/anim_svg_a.svga");
                if (file.exists()) {
                    subscriber.onNext(file.getAbsolutePath());
                } else {
                    ByteArrayOutputStream byteOutputStream = null;
                    FileOutputStream outputStream = null;
                    InputStream inputStream = null;
                    byte[] buffer = new byte[10];
                    try {
                        byteOutputStream = new ByteArrayOutputStream();
                        outputStream = new FileOutputStream(file);
                        // 需要保留文件格式的资源放在 assets 目录下加载到 SD
                        inputStream = getAssets().open("anim_svg_test.svga");
                        int len;
                        while ((len = inputStream.read(buffer)) != -1) {
                            byteOutputStream.write(buffer, 0, len);
                        }
                        outputStream.write(byteOutputStream.toByteArray());
                        outputStream.flush();
                        subscriber.onNext(file.getAbsolutePath());
                    } catch (IOException e) {
                        e.printStackTrace();
                        subscriber.onError(new Throwable("缓存本地失败"));
                    } finally {
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (byteOutputStream != null) {
                            try {
                                byteOutputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            subscriber.onCompleted();
        }).subscribe(s -> {
            UToast.showShort(ActivitySvgATest.this, "缓存SVGA成功");
            if (fileContainer != null) {
                fileContainer.removeAllViews();
                fileContainer.addView(new SvgAFileSurface(ActivitySvgATest.this));
            }
        }, throwable -> {
            UToast.showShort(ActivitySvgATest.this, throwable.getMessage());
            ULog.show(throwable.getMessage());
        });
    }
}

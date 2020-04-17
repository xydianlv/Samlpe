package com.example.wyyu.gitsamlpe.test.function.download;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.image.matisse.FrescoLoader;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.liulishuo.okdownload.DownloadContext;
import com.liulishuo.okdownload.DownloadListener;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by wyyu on 2019-09-06.
 **/

public class ActivityDownloadTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityDownloadTest.class));
    }

    private static final String IMG_NET_URL =
        "https://hbimg.huabanimg.com/0295e8cc88d9ccf87e329b9b6974ade048a25c84b9895-BI5GHa";

    @BindView(R.id.download_test_net) SimpleDraweeView imgNet;
    @BindView(R.id.download_test_click) View click;
    @BindView(R.id.download_test_progress) ProgressBar progressBar;
    @BindView(R.id.download_test_local) SimpleDraweeView imgLocal;

    private DownloadContext.QueueSet downloadCreator;
    private File cacheDir;

    private long presentLength;
    private long fileLength;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_test);

        initActivity();
    }

    private void initActivity() {
        initValue();
        initView();
    }

    private void initValue() {
        cacheDir = getCacheDir();

        downloadCreator = new DownloadContext.QueueSet().setMinIntervalMillisCallbackProcess(150)
            .setParentPathFile(cacheDir)
            .setPassIfAlreadyCompleted(true)
            .setAutoCallbackToUIThread(true);
    }

    private void initView() {
        FrescoLoader.newFrescoLoader()
            .imageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
            .uri(Uri.parse(IMG_NET_URL))
            .into(imgNet);

        click.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                onStartDownload();
            }
        });
    }

    private void onStartDownload() {
        DownloadTask downloadTask =
            new DownloadTask.Builder(IMG_NET_URL, cacheDir).setFilename("img_net")
                .setMinIntervalMillisCallbackProcess(150)
                .setWifiRequired(false)
                .setPassIfAlreadyCompleted(true)
                .setAutoCallbackToUIThread(true)
                .setConnectionCount(1)
                .build();

        DownloadContext.Builder builder = downloadCreator.commit();
        builder.bindSetTask(downloadTask);
        DownloadContext context = builder.build();
        context.startOnSerial(new DownloadListener() {
            @Override public void taskStart(@NonNull DownloadTask task) {
                // 1、 最先执行的回调，表示下载线程开始执行任务
            }

            @Override public void connectTrialStart(@NonNull DownloadTask task,
                @NonNull Map<String, List<String>> requestHeaderFields) {
                // 2、 尝试建立连接
            }

            @Override public void connectTrialEnd(@NonNull DownloadTask task, int responseCode,
                @NonNull Map<String, List<String>> responseHeaderFields) {
                // 3、 尝试建立连接结果回调，对应 2
            }

            @Override public void downloadFromBeginning(@NonNull DownloadTask task,
                @NonNull BreakpointInfo info, @NonNull ResumeFailedCause cause) {
                // 4、 开始执行正式的下载流程
            }

            @Override public void downloadFromBreakpoint(@NonNull DownloadTask task,
                @NonNull BreakpointInfo info) {
            }

            @Override public void connectStart(@NonNull DownloadTask task, int blockIndex,
                @NonNull Map<String, List<String>> requestHeaderFields) {
                // 5、 开始链接
            }

            @Override
            public void connectEnd(@NonNull DownloadTask task, int blockIndex, int responseCode,
                @NonNull Map<String, List<String>> responseHeaderFields) {
                // 6、 链接结束，对应 5
            }

            @Override
            public void fetchStart(@NonNull DownloadTask task, int blockIndex, long contentLength) {
                // 7、 开始下载
                Log.e("ActivityDownloadTest",
                    "fetchStart -> index : " + blockIndex + "  contentLength : " + contentLength);

                fileLength = contentLength;
                presentLength = 0;

                refreshProgress();
            }

            @Override public void fetchProgress(@NonNull DownloadTask task, int blockIndex,
                long increaseBytes) {
                // 8、 下载进度
                Log.e("ActivityDownloadTest", "fetchProgress -> index : "
                    + blockIndex
                    + "  increaseBytes : "
                    + increaseBytes);

                presentLength = presentLength + increaseBytes;
                refreshProgress();
            }

            @Override
            public void fetchEnd(@NonNull DownloadTask task, int blockIndex, long contentLength) {
                // 9、 下载结束，对应 7
                Log.e("ActivityDownloadTest", "fetchEnd -> blockIndex : "
                    + blockIndex
                    + "  contentLength : "
                    + contentLength);

                presentLength = contentLength;
                refreshProgress();
            }

            @Override public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause,
                @Nullable Exception realCause) {
                // 10、 下载任务执行结束，对应 1
                onDownloadFinish();
            }
        });
    }

    private void refreshProgress() {
        if (progressBar == null) {
            return;
        }
        float percent = (float) presentLength / fileLength;
        progressBar.setProgress((int) (percent * 100));
    }

    private void onDownloadFinish() {
        FrescoLoader.newFrescoLoader()
            .imageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
            .uri(Uri.fromFile(new File(getCacheDir(), "img_net")))
            .into(imgLocal);
    }
}

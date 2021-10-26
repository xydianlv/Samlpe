package com.example.wyyu.gitsamlpe.test.network.cronet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;

import org.chromium.net.CronetEngine;
import org.chromium.net.CronetException;
import org.chromium.net.UrlRequest;
import org.chromium.net.UrlResponseInfo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.util.concurrent.Executors;

public class ActivityNetworkCronet extends FullScreenActivity {

    private static final int BYTE_BUFFER_CAPACITY_BYTES = 64 * 1024;

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityNetworkCronet.class));
    }

    private final ByteArrayOutputStream bytesReceived = new ByteArrayOutputStream();
    private final WritableByteChannel receiveChannel = Channels.newChannel(bytesReceived);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_work_cro_net);

        initActivity();
    }

    private void initActivity() {
        findViewById(R.id.network_cro_btn).setOnClickListener(view -> loadServerData());
    }

    private void loadServerData() {
        UrlRequest.Callback callback = new UrlRequest.Callback() {
            @Override
            public void onRedirectReceived(UrlRequest request, UrlResponseInfo info, String newLocationUrl) {
                Log.i("NetworkCroNetLog", "onRedirectReceived");
                request.followRedirect();
            }

            @Override
            public void onResponseStarted(UrlRequest request, UrlResponseInfo info) {
                Log.i("NetworkCroNetLog", "onResponseStarted");
                request.read(ByteBuffer.allocateDirect(BYTE_BUFFER_CAPACITY_BYTES));
            }

            @Override
            public void onReadCompleted(UrlRequest request, UrlResponseInfo info, ByteBuffer byteBuffer) {
                Log.i("NetworkCroNetLog", "onReadCompleted");
                byteBuffer.flip();

                try {
                    receiveChannel.write(byteBuffer);
                } catch (IOException e) {
                    Log.e("NetworkCroNetLog", "onReadCompleted IOException");
                }
                // Reset the buffer to prepare it for the next read
                byteBuffer.clear();

                // Continue reading the request
                request.read(byteBuffer);
            }

            @Override
            public void onSucceeded(UrlRequest request, UrlResponseInfo info) {
                Log.i("NetworkCroNetLog", "onSucceeded");
            }

            @Override
            public void onFailed(UrlRequest request, UrlResponseInfo info, CronetException error) {
                Log.i("NetworkCroNetLog", "onFailed");
            }
        };
        UrlRequest.Builder builder = getEngine().newUrlRequestBuilder("https://juejin.cn/post/6896302142542315533", callback, Executors.newFixedThreadPool(4))
                .addHeader("x-my-custom-header", "Hello-from-Cronet")
                .setPriority(UrlRequest.Builder.REQUEST_PRIORITY_IDLE);
        builder.build().start();
    }

    private CronetEngine getEngine() {
        return new CronetEngine.Builder(ActivityNetworkCronet.this)
                .setStoragePath(getFilesDir().getAbsolutePath())
                .enableHttpCache(CronetEngine.Builder.HTTP_CACHE_DISK_NO_HTTP, 100 * 1024)
                .enableHttp2(true)
                .enableQuic(true)
                .enableBrotli(true)
                .setUserAgent("")
                .build();
    }
}

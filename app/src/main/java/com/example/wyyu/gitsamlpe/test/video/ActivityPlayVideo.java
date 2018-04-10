package com.example.wyyu.gitsamlpe.test.video;

import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;
import com.example.wyyu.gitsamlpe.util.file.StorageUtil;
import java.io.IOException;

/**
 * Created by wyyu on 2018/4/10.
 **/

public class ActivityPlayVideo extends FullScreenActivity
    implements TextureView.SurfaceTextureListener, MediaPlayer.OnPreparedListener {

    private static final String VIDEO_PATH = StorageUtil.getExternalStoragePath()
        + "/tencent/MicroMsg/2ac12e0f65da05353a3f32ce41f5193f/video/兔子.mp4";
    private TextureView textureView;
    private MediaPlayer mediaPlayer;

    private SurfaceTexture surfaceTexture;
    private Surface surface;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        textureView = findViewById(R.id.texture_view);
        textureView.setSurfaceTextureListener(this);

        textureView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }

    @Override protected void onResume() {
        super.onResume();
        if (textureView.isAvailable()) {
            playVideo();
        }
    }

    @Override protected void onPause() {
        super.onPause();
        if (surfaceTexture != null) {
            surfaceTexture.release();
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void playVideo() {
        if (mediaPlayer == null) {
            surfaceTexture = textureView.getSurfaceTexture();
            surface = new Surface(surfaceTexture);
            initMediaPlayer();
        }
    }

    private void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(VIDEO_PATH);
            mediaPlayer.setSurface(surface);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setLooping(true);
        } catch (IllegalArgumentException e1) {
            e1.printStackTrace();
        } catch (SecurityException e1) {
            e1.printStackTrace();
        } catch (IllegalStateException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        playVideo();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    @Override public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }

    @Override public void onPrepared(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }
}

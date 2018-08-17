package com.example.wyyu.gitsamlpe.test.image.edit;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.framework.dialog.SDTediumDialog;
import com.example.wyyu.gitsamlpe.framework.toast.UToast;
import com.example.wyyu.gitsamlpe.test.image.matisse.FrescoLoader;
import com.example.wyyu.gitsamlpe.util.CommonUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import java.io.File;

/**
 * Created by wyyu on 2018/8/15.
 **/

public class ActivityEditImage extends ToolbarActivity {

    private static final int KEY_PHOTO_MAX_SAVE_LENGTH = 800;
    private static final int KEY_SELECT_PHOTO = 43;
    private static final int KEY_TAKE_PHOTO = 41;

    @BindView(R.id.edit_image) SimpleDraweeView draweeView;
    @BindView(R.id.edit_button) TextView button;

    private File outputFile;
    private File tempFile;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);

        initActivity();
    }

    private void initActivity() {
        initBasicView();
        initBasicValue();
    }

    private void initBasicView() {
        initToolbar("EditTest");

        button.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                showTediumDialog();
            }
        });
    }

    private void initBasicValue() {
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "pic/";

        outputFile = new File(filePath + "temp");
    }

    private void showTediumDialog() {
        SDTediumDialog dialog = new SDTediumDialog(this);

        dialog.setTitle("提示");
        dialog.addNewItem("拍照", KEY_TAKE_PHOTO, false);
        dialog.addNewItem("选择本地照片", KEY_SELECT_PHOTO, true);

        dialog.setOnTediumClickListener(new SDTediumDialog.OnTediumClickListener() {
            @Override public void onClick(int tag) {
                choosePhotoTag(tag);
            }
        });

        dialog.show();
    }

    private void choosePhotoTag(int tag) {

        Uri outputFileUri = Uri.fromFile(outputFile);
        switch (tag) {
            case KEY_SELECT_PHOTO:
                Intent intentSelect =
                    new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intentSelect.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                try {
                    startActivityForResult(intentSelect, KEY_SELECT_PHOTO);
                } catch (ActivityNotFoundException exception) {
                    UToast.showShort(this, "打开手机相册失败!");
                }
                break;
            case KEY_TAKE_PHOTO:
                Intent intentTake = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    ContentValues contentValues = new ContentValues(1);
                    contentValues.put(MediaStore.Images.Media.DATA, outputFile.getAbsolutePath());
                    outputFileUri =
                        getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            contentValues);
                }

                intentTake.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                PackageManager packageManager = getPackageManager();
                if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                    startActivityForResult(intentTake, KEY_TAKE_PHOTO);
                }
                break;
        }
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case KEY_SELECT_PHOTO:
                onSelectFromSystem(data);
                break;
            case KEY_TAKE_PHOTO:
                startPhotoZoom(outputFile);
                break;
            case Crop.REQUEST_SYS_CROP:
            case Crop.REQUEST_CROP:
                submitAvatarToServer();
                break;
        }
    }

    private void onSelectFromSystem(Intent data) {
        if (data == null) {
            return;
        }
        Uri selectedImage = data.getData();

        FrescoLoader.newFrescoLoader().uri(selectedImage)
            .into(draweeView);
        //if (CommonUtil.savePictureFromIntentToFile(data, getContentResolver(), KEY_PHOTO_MAX_SAVE_LENGTH,
        //    outputFile)) {
        //    startPhotoZoom(outputFile);
        //}
    }

    private void startPhotoZoom(File file) {
        if (tempFile != null) {
            tempFile.delete();
        }
        tempFile = new File(file.getPath() + "." + System.currentTimeMillis());
        Uri dest = Uri.fromFile(tempFile);
        Uri src = Uri.fromFile(file);
        try {
            try {
                if (src != null && src.isAbsolute()) {
                    Crop.squareCrop(this, src, dest, "剪裁头像");
                }
            } catch (Exception e) {
                Crop.systemSquareCrop(this, src, dest, Crop.REQUEST_SYS_CROP);
            }
        } catch (Exception ex) {
            tempFile = file;
            submitAvatarToServer();
        }
    }

    // 上传新头像到服务器
    private void submitAvatarToServer() {
        if (tempFile == null || !tempFile.exists()) {
            return;
        }
        draweeView.setImageURI(tempFile.getAbsolutePath());
    }
}
